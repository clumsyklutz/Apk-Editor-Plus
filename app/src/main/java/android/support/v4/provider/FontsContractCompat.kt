package android.support.v4.provider

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.content.pm.Signature
import android.content.res.Resources
import android.graphics.Typeface
import android.net.Uri
import android.os.CancellationSignal
import android.os.Handler
import android.provider.BaseColumns
import android.support.annotation.GuardedBy
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.annotation.VisibleForTesting
import android.support.v4.content.res.FontResourcesParserCompat
import androidx.core.content.res.ResourcesCompat
import android.support.v4.graphics.TypefaceCompat
import android.support.v4.graphics.TypefaceCompatUtil
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.provider.SelfDestructiveThread
import android.support.v4.util.LruCache
import android.support.v4.util.Preconditions
import android.support.v4.util.SimpleArrayMap
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.ArrayList
import java.util.Arrays
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.concurrent.Callable

class FontsContractCompat {

    companion object {
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        val PARCEL_FONT_RESULTS = "font_results"

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        val RESULT_CODE_PROVIDER_NOT_FOUND = -1

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        val RESULT_CODE_WRONG_CERTIFICATES = -2
        
        private val TAG = "FontsContractCompat"
        val sTypefaceCache = LruCache<String, Typeface>(16)
        private val BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000
        private val sBackgroundThread = SelfDestructiveThread("fonts", 10, BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS)
        val sLock = Any()

        @GuardedBy("sLock")
        val sPendingReplies = SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>>()
        
        private val sByteArrayComparator = Comparator<ByteArray> { bArr, bArr2 ->
            if (bArr.size != bArr2.size) {
                bArr.size - bArr2.size
            } else {
                for (i in bArr.indices) {
                    if (bArr[i] != bArr2[i]) {
                        return@Comparator bArr[i] - bArr2[i]
                    }
                }
                0
            }
        }
    }

    class Columns : BaseColumns {
        companion object {
            val FILE_ID = "file_id"
            val ITALIC = "font_italic"
            val RESULT_CODE = "result_code"
            val RESULT_CODE_FONT_NOT_FOUND = 1
            val RESULT_CODE_FONT_UNAVAILABLE = 2
            val RESULT_CODE_MALFORMED_QUERY = 3
            val RESULT_CODE_OK = 0
            val TTC_INDEX = "font_ttc_index"
            val VARIATION_SETTINGS = "font_variation_settings"
            val WEIGHT = "font_weight"
        }
    }

    class FontFamilyResult {
        companion object {
            val STATUS_OK = 0
            val STATUS_UNEXPECTED_DATA_PROVIDED = 2
            val STATUS_WRONG_CERTIFICATES = 1
        }
        
        private val mFonts: Array<FontInfo>?
        private val mStatusCode: Int

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(i: Int, fontInfoArr: Array<FontInfo>?) {
            this.mStatusCode = i
            this.mFonts = fontInfoArr
        }

        fun getFonts(): Array<FontInfo>? {
            return this.mFonts
        }

        fun getStatusCode(): Int {
            return this.mStatusCode
        }
    }

    class FontInfo {
        private val mItalic: Boolean
        private val mResultCode: Int
        private val mTtcIndex: Int
        private val mUri: Uri
        private val mWeight: Int

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        constructor(
            @NonNull uri: Uri,
            @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED.toLong()) i: Int,
            @IntRange(from = 1, to = 1000) i2: Int,
            z: Boolean,
            i3: Int
        ) {
            this.mUri = Preconditions.checkNotNull(uri) as Uri
            this.mTtcIndex = i
            this.mWeight = i2
            this.mItalic = z
            this.mResultCode = i3
        }

        fun getResultCode(): Int {
            return this.mResultCode
        }

        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED.toLong())
        fun getTtcIndex(): Int {
            return this.mTtcIndex
        }

        @NonNull
        fun getUri(): Uri {
            return this.mUri
        }

        @IntRange(from = 1, to = 1000)
        fun getWeight(): Int {
            return this.mWeight
        }

        fun isItalic(): Boolean {
            return this.mItalic
        }
    }

    abstract class FontRequestCallback {
        companion object {
            val FAIL_REASON_FONT_LOAD_ERROR = -3
            val FAIL_REASON_FONT_NOT_FOUND = 1
            val FAIL_REASON_FONT_UNAVAILABLE = 2
            val FAIL_REASON_MALFORMED_QUERY = 3
            val FAIL_REASON_PROVIDER_NOT_FOUND = -1
            val FAIL_REASON_SECURITY_VIOLATION = -4
            val FAIL_REASON_WRONG_CERTIFICATES = -2

            @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
            val RESULT_OK = 0
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        annotation class FontRequestFailReason

        abstract fun onTypefaceRequestFailed(i: Int)

        abstract fun onTypefaceRetrieved(typeface: Typeface)
    }

    class TypefaceResult {
        val mResult: Int
        val mTypeface: Typeface?

        constructor(@Nullable typeface: Typeface?, i: Int) {
            this.mTypeface = typeface
            this.mResult = i
        }
    }

    private constructor() {
    }

    @Nullable
    fun buildTypeface(@NonNull context: Context, @Nullable cancellationSignal: CancellationSignal?, @NonNull fontInfoArr: Array<FontInfo>): Typeface? {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fontInfoArr, 0)
    }

    private fun convertToByteArrayList(signatureArr: Array<Signature>): ArrayList<ByteArray> {
        val arrayList = ArrayList<ByteArray>()
        for (signature in signatureArr) {
            arrayList.add(signature.toByteArray())
        }
        return arrayList
    }

    private fun equalsByteArrayList(list: List<ByteArray>, list2: List<ByteArray>): Boolean {
        if (list.size != list2.size) {
            return false
        }
        for (i in list.indices) {
            if (!Arrays.equals(list[i], list2[i])) {
                return false
            }
        }
        return true
    }

    @NonNull
    fun fetchFonts(@NonNull context: Context, @Nullable cancellationSignal: CancellationSignal?, @NonNull fontRequest: FontRequest): FontFamilyResult {
        val provider = getProvider(context.packageManager, fontRequest, context.resources)
        return if (provider == null) {
            FontFamilyResult(1, null)
        } else {
            FontFamilyResult(0, getFontFromProvider(context, fontRequest, provider.authority, cancellationSignal))
        }
    }

    private fun getCertificates(fontRequest: FontRequest, resources: Resources?): List<List<ByteArray>> {
        return if (fontRequest.getCertificates() != null) {
            fontRequest.getCertificates() as List<List<ByteArray>>
        } else {
            FontResourcesParserCompat.readCerts(resources, fontRequest.getCertificatesArrayResId())
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0143  */
    @VisibleForTesting
    @NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun getFontFromProvider(context: Context, fontRequest: FontRequest, authority: String, cancellationSignal: CancellationSignal?): Array<FontInfo> {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.provider.FontsContractCompat.getFontFromProvider(android.content.Context, android.support.v4.provider.FontRequest, java.lang.String, android.os.CancellationSignal):android.support.v4.provider.FontsContractCompat\$Array<FontInfo>")
    }

    @NonNull
    fun getFontInternal(context: Context, fontRequest: FontRequest, i: Int): TypefaceResult {
        return try {
            val fontFamilyResultFetchFonts = fetchFonts(context, null, fontRequest)
            if (fontFamilyResultFetchFonts.getStatusCode() != 0) {
                TypefaceResult(null, if (fontFamilyResultFetchFonts.getStatusCode() == 1) -2 else -3)
            } else {
                val typefaceCreateFromFontInfo = TypefaceCompat.createFromFontInfo(context, null, fontFamilyResultFetchFonts.getFonts(), i)
                TypefaceResult(typefaceCreateFromFontInfo, if (typefaceCreateFromFontInfo != null) 0 else -3)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            TypefaceResult(null, -1)
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun getFontSync(context: Context, fontRequest: FontRequest, @Nullable fontCallback: ResourcesCompat.FontCallback?, @Nullable handler: Handler?, z: Boolean, i: Int, i2: Int): Typeface? {
        val str = fontRequest.getIdentifier() + "-" + i2
        val typeface = sTypefaceCache.get(str)
        if (typeface != null) {
            fontCallback?.onFontRetrieved(typeface)
            return typeface
        }
        if (z && i == -1) {
            val fontInternal = getFontInternal(context, fontRequest, i2)
            if (fontCallback != null) {
                if (fontInternal.mResult == 0) {
                    fontCallback.callbackSuccessAsync(fontInternal.mTypeface, handler)
                } else {
                    fontCallback.callbackFailAsync(fontInternal.mResult, handler)
                }
            }
            return fontInternal.mTypeface
        }
        val callable = Callable<TypefaceResult> {
            val fontInternal2 = getFontInternal(context, fontRequest, i2)
            if (fontInternal2.mTypeface != null) {
                sTypefaceCache.put(str, fontInternal2.mTypeface)
            }
            fontInternal2
        }
        if (z) {
            return try {
                sBackgroundThread.postAndWait(callable, i)?.mTypeface
            } catch (e: InterruptedException) {
                null
            }
        }
        val replyCallback: SelfDestructiveThread.ReplyCallback<TypefaceResult>? = fontCallback?.let {
            object : SelfDestructiveThread.ReplyCallback<TypefaceResult> {
                override fun onReply(typefaceResult: TypefaceResult?) {
                    if (typefaceResult == null) {
                        fontCallback.callbackFailAsync(1, handler)
                    } else if (typefaceResult.mResult == 0) {
                        fontCallback.callbackSuccessAsync(typefaceResult.mTypeface, handler)
                    } else {
                        fontCallback.callbackFailAsync(typefaceResult.mResult, handler)
                    }
                }
            }
        }
        synchronized(sLock) {
            if (sPendingReplies.containsKey(str)) {
                replyCallback?.let {
                    sPendingReplies[str]?.add(it)
                }
                return null
            }
            replyCallback?.let {
                val arrayList = ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>()
                arrayList.add(it)
                sPendingReplies.put(str, arrayList)
            }
            sBackgroundThread.postAndReply(callable, object : SelfDestructiveThread.ReplyCallback<TypefaceResult> {
                override fun onReply(typefaceResult: TypefaceResult?) {
                    synchronized(sLock) {
                        val arrayList2 = sPendingReplies[str]
                        if (arrayList2 == null) {
                            return
                        }
                        sPendingReplies.remove(str)
                        for (i3 in arrayList2.indices) {
                            arrayList2[i3].onReply(typefaceResult)
                        }
                    }
                }
            })
            return null
        }
    }

    @VisibleForTesting
    @Nullable
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun getProvider(@NonNull packageManager: PackageManager, @NonNull fontRequest: FontRequest, @Nullable resources: Resources?): ProviderInfo? {
        var i = 0
        val providerAuthority = fontRequest.getProviderAuthority()
        val providerInfoResolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0)
            ?: throw PackageManager.NameNotFoundException("No package found for authority: $providerAuthority")
        if (providerInfoResolveContentProvider.packageName != fontRequest.getProviderPackage()) {
            throw PackageManager.NameNotFoundException("Found content provider $providerAuthority, but package was not ${fontRequest.getProviderPackage()}")
        }
        val listConvertToByteArrayList = convertToByteArrayList(packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures)
        Collections.sort(listConvertToByteArrayList, sByteArrayComparator)
        val certificates = getCertificates(fontRequest, resources)
        while (true) {
            val i2 = i
            if (i2 >= certificates.size) {
                return null
            }
            val arrayList = ArrayList(certificates[i2])
            Collections.sort(arrayList, sByteArrayComparator)
            if (equalsByteArrayList(listConvertToByteArrayList, arrayList)) {
                return providerInfoResolveContentProvider
            }
            i = i2 + 1
        }
    }

    @RequiresApi(19)
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun prepareFontData(context: Context, fontInfoArr: Array<FontInfo>, cancellationSignal: CancellationSignal?): Map<Uri, ByteArray> {
        val map = HashMap<Uri, ByteArray>()
        for (fontInfo in fontInfoArr) {
            if (fontInfo.getResultCode() == 0) {
                val uri = fontInfo.getUri()
                if (!map.containsKey(uri)) {
                    map[uri] = TypefaceCompatUtil.mmap(context, cancellationSignal, uri)
                }
            }
        }
        return Collections.unmodifiableMap(map)
    }

    fun requestFont(@NonNull context: Context, @NonNull fontRequest: FontRequest, @NonNull fontRequestCallback: FontRequestCallback, @NonNull handler: Handler) {
        val handler2 = Handler()
        handler.post {
            try {
                val fontFamilyResultFetchFonts = fetchFonts(context, null, fontRequest)
                if (fontFamilyResultFetchFonts.getStatusCode() != 0) {
                    when (fontFamilyResultFetchFonts.getStatusCode()) {
                        1 -> {
                            handler2.post {
                                fontRequestCallback.onTypefaceRequestFailed(-2)
                            }
                        }
                        2 -> {
                            handler2.post {
                                fontRequestCallback.onTypefaceRequestFailed(-3)
                            }
                        }
                        else -> {
                            handler2.post {
                                fontRequestCallback.onTypefaceRequestFailed(-3)
                            }
                        }
                    }
                    return@post
                }
                val fonts = fontFamilyResultFetchFonts.getFonts()
                if (fonts == null || fonts.isEmpty()) {
                    handler2.post {
                        fontRequestCallback.onTypefaceRequestFailed(1)
                    }
                    return@post
                }
                for (fontInfo in fonts) {
                    if (fontInfo.getResultCode() != 0) {
                        val resultCode = fontInfo.getResultCode()
                        if (resultCode < 0) {
                            handler2.post {
                                fontRequestCallback.onTypefaceRequestFailed(-3)
                            }
                            return@post
                        } else {
                            handler2.post {
                                fontRequestCallback.onTypefaceRequestFailed(resultCode)
                            }
                            return@post
                        }
                    }
                }
                val typefaceBuildTypeface = buildTypeface(context, null, fonts)
                if (typefaceBuildTypeface == null) {
                    handler2.post {
                        fontRequestCallback.onTypefaceRequestFailed(-3)
                    }
                } else {
                    handler2.post {
                        fontRequestCallback.onTypefaceRetrieved(typefaceBuildTypeface)
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                handler2.post {
                    fontRequestCallback.onTypefaceRequestFailed(-1)
                }
            }
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun resetCache() {
        sTypefaceCache.evictAll()
    }
}
