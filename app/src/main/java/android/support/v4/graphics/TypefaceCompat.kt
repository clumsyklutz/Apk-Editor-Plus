package android.support.v4.graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.os.CancellationSignal
import android.os.Handler
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.content.res.FontResourcesParserCompat
import androidx.core.content.res.ResourcesCompat
import android.support.v4.provider.FontsContractCompat
import android.support.v4.util.LruCache

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompat {
    private static val TAG = "TypefaceCompat"
    private static final LruCache sTypefaceCache
    private static final TypefaceCompatBaseImpl sTypefaceCompatImpl

    static {
        if (Build.VERSION.SDK_INT >= 28) {
            sTypefaceCompatImpl = TypefaceCompatApi28Impl()
        } else if (Build.VERSION.SDK_INT >= 26) {
            sTypefaceCompatImpl = TypefaceCompatApi26Impl()
        } else if (Build.VERSION.SDK_INT >= 24 && TypefaceCompatApi24Impl.isUsable()) {
            sTypefaceCompatImpl = TypefaceCompatApi24Impl()
        } else if (Build.VERSION.SDK_INT >= 21) {
            sTypefaceCompatImpl = TypefaceCompatApi21Impl()
        } else {
            sTypefaceCompatImpl = TypefaceCompatBaseImpl()
        }
        sTypefaceCache = LruCache(16)
    }

    private constructor() {
    }

    @Nullable
    fun createFromFontInfo(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.Array<FontInfo> fontInfoArr, Int i) {
        return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fontInfoArr, i)
    }

    @Nullable
    fun createFromResourcesFamilyXml(@NonNull Context context, @NonNull FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, @NonNull Resources resources, Int i, Int i2, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, Boolean z) {
        Typeface typefaceCreateFromFontFamilyFilesResourceEntry
        Boolean z2 = true
        if (familyResourceEntry is FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry
            if (z) {
                if (providerResourceEntry.getFetchStrategy() != 0) {
                    z2 = false
                }
            } else if (fontCallback != null) {
                z2 = false
            }
            typefaceCreateFromFontFamilyFilesResourceEntry = FontsContractCompat.getFontSync(context, providerResourceEntry.getRequest(), fontCallback, handler, z2, z ? providerResourceEntry.getTimeout() : -1, i2)
        } else {
            typefaceCreateFromFontFamilyFilesResourceEntry = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, i2)
            if (fontCallback != null) {
                if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
                    fontCallback.callbackSuccessAsync(typefaceCreateFromFontFamilyFilesResourceEntry, handler)
                } else {
                    fontCallback.callbackFailAsync(-3, handler)
                }
            }
        }
        if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
            sTypefaceCache.put(createResourceUid(resources, i, i2), typefaceCreateFromFontFamilyFilesResourceEntry)
        }
        return typefaceCreateFromFontFamilyFilesResourceEntry
    }

    @Nullable
    fun createFromResourcesFontFile(@NonNull Context context, @NonNull Resources resources, Int i, String str, Int i2) {
        Typeface typefaceCreateFromResourcesFontFile = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, i, str, i2)
        if (typefaceCreateFromResourcesFontFile != null) {
            sTypefaceCache.put(createResourceUid(resources, i, i2), typefaceCreateFromResourcesFontFile)
        }
        return typefaceCreateFromResourcesFontFile
    }

    private fun createResourceUid(Resources resources, Int i, Int i2) {
        return resources.getResourcePackageName(i) + "-" + i + "-" + i2
    }

    @Nullable
    fun findFromCache(@NonNull Resources resources, Int i, Int i2) {
        return (Typeface) sTypefaceCache.get(createResourceUid(resources, i, i2))
    }
}
