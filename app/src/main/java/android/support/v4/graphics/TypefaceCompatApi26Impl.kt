package android.support.v4.graphics

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.fonts.FontVariationAxis
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.content.res.FontResourcesParserCompat
import android.util.Log
import java.lang.reflect.Array
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.nio.ByteBuffer

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static val ABORT_CREATION_METHOD = "abortCreation"
    private static val ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager"
    private static val ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer"
    private static val CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault"
    private static val DEFAULT_FAMILY = "sans-serif"
    private static val FONT_FAMILY_CLASS = "android.graphics.FontFamily"
    private static val FREEZE_METHOD = "freeze"
    private static val RESOLVE_BY_FONT_TABLE = -1
    private static val TAG = "TypefaceCompatApi26Impl"
    protected final Method mAbortCreation
    protected final Method mAddFontFromAssetManager
    protected final Method mAddFontFromBuffer
    protected final Method mCreateFromFamiliesWithDefault
    protected final Class mFontFamily
    protected final Constructor mFontFamilyCtor
    protected final Method mFreeze

    constructor() {
        Method methodObtainCreateFromFamiliesWithDefaultMethod
        Method method
        Method method2
        Method method3
        Constructor constructor
        Class cls
        Method method4 = null
        try {
            Class clsObtainFontFamily = obtainFontFamily()
            Constructor constructorObtainFontFamilyCtor = obtainFontFamilyCtor(clsObtainFontFamily)
            Method methodObtainAddFontFromAssetManagerMethod = obtainAddFontFromAssetManagerMethod(clsObtainFontFamily)
            Method methodObtainAddFontFromBufferMethod = obtainAddFontFromBufferMethod(clsObtainFontFamily)
            Method methodObtainFreezeMethod = obtainFreezeMethod(clsObtainFontFamily)
            Method methodObtainAbortCreationMethod = obtainAbortCreationMethod(clsObtainFontFamily)
            methodObtainCreateFromFamiliesWithDefaultMethod = obtainCreateFromFamiliesWithDefaultMethod(clsObtainFontFamily)
            method4 = methodObtainAbortCreationMethod
            method = methodObtainFreezeMethod
            method2 = methodObtainAddFontFromBufferMethod
            method3 = methodObtainAddFontFromAssetManagerMethod
            constructor = constructorObtainFontFamilyCtor
            cls = clsObtainFontFamily
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e)
            methodObtainCreateFromFamiliesWithDefaultMethod = null
            method = null
            method2 = null
            method3 = null
            constructor = null
            cls = null
        }
        this.mFontFamily = cls
        this.mFontFamilyCtor = constructor
        this.mAddFontFromAssetManager = method3
        this.mAddFontFromBuffer = method2
        this.mFreeze = method
        this.mAbortCreation = method4
        this.mCreateFromFamiliesWithDefault = methodObtainCreateFromFamiliesWithDefaultMethod
    }

    private fun abortCreation(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.mAbortCreation.invoke(obj, new Object[0])
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    private fun addFontFromAssetManager(Context context, Object obj, String str, Int i, Int i2, Int i3, @Nullable Array<FontVariationAxis> fontVariationAxisArr) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(obj, context.getAssets(), str, 0, false, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), fontVariationAxisArr)).booleanValue()
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    private fun addFontFromBuffer(Object obj, ByteBuffer byteBuffer, Int i, Int i2, Int i3) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Integer.valueOf(i3))).booleanValue()
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    private fun freeze(Object obj) {
        try {
            return ((Boolean) this.mFreeze.invoke(obj, new Object[0])).booleanValue()
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    private fun isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.")
        }
        return this.mAddFontFromAssetManager != null
    }

    private fun newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0])
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    protected fun createFromFamiliesWithDefault(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.mFontFamily, 1)
            Array.set(objNewInstance, 0, obj)
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, objNewInstance, -1, -1)
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    fun createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, fontFamilyFilesResourceEntry, resources, i)
        }
        Object objNewFamily = newFamily()
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
            if (!addFontFromAssetManager(context, objNewFamily, fontFileResourceEntry.getFileName(), fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFileResourceEntry.getVariationSettings()))) {
                abortCreation(objNewFamily)
                return null
            }
        }
        if (freeze(objNewFamily)) {
            return createFromFamiliesWithDefault(objNewFamily)
        }
        return null
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0055  */
    @Override // android.support.v4.graphics.TypefaceCompatApi21Impl, android.support.v4.graphics.TypefaceCompatBaseImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r11, @android.support.annotation.Nullable android.os.CancellationSignal r12, @android.support.annotation.NonNull android.support.v4.provider.FontsContractCompat.Array<FontInfo> r13, Int r14) throws java.lang.Throwable {
        /*
            r10 = this
            Int r0 = r13.length
            if (r0 > 0) goto L5
            r0 = 0
        L4:
            return r0
        L5:
            Boolean r0 = r10.isFontFamilyPrivateAPIAvailable()
            if (r0 != 0) goto L64
            android.support.v4.provider.FontsContractCompat$FontInfo r0 = r10.findBestInfo(r13, r14)
            android.content.ContentResolver r1 = r11.getContentResolver()
            android.net.Uri r2 = r0.getUri()     // Catch: java.io.IOException -> L4a
            java.lang.String r3 = "r"
            android.os.ParcelFileDescriptor r2 = r1.openFileDescriptor(r2, r3, r12)     // Catch: java.io.IOException -> L4a
            r1 = 0
            if (r2 != 0) goto L27
            if (r2 == 0) goto L25
            r2.close()     // Catch: java.io.IOException -> L4a
        L25:
            r0 = 0
            goto L4
        L27:
            android.graphics.Typeface$Builder r3 = new android.graphics.Typeface$Builder     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            java.io.FileDescriptor r4 = r2.getFileDescriptor()     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            Int r4 = r0.getWeight()     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            android.graphics.Typeface$Builder r3 = r3.setWeight(r4)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            Boolean r0 = r0.isItalic()     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            android.graphics.Typeface$Builder r0 = r3.setItalic(r0)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            android.graphics.Typeface r0 = r0.build()     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> Lbf
            if (r2 == 0) goto L4
            r2.close()     // Catch: java.io.IOException -> L4a
            goto L4
        L4a:
            r0 = move-exception
            r0 = 0
            goto L4
        L4d:
            r0 = move-exception
            throw r0     // Catch: java.lang.Throwable -> L4f
        L4f:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
        L53:
            if (r2 == 0) goto L5a
            if (r1 == 0) goto L60
            r2.close()     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5b
        L5a:
            throw r0     // Catch: java.io.IOException -> L4a
        L5b:
            r2 = move-exception
            r1.addSuppressed(r2)     // Catch: java.io.IOException -> L4a
            goto L5a
        L60:
            r2.close()     // Catch: java.io.IOException -> L4a
            goto L5a
        L64:
            java.util.Map r7 = android.support.v4.provider.FontsContractCompat.prepareFontData(r11, r13, r12)
            java.lang.Object r1 = r10.newFamily()
            r2 = 0
            Int r8 = r13.length
            r0 = 0
            r6 = r0
            r0 = r2
        L71:
            if (r6 >= r8) goto La4
            r5 = r13[r6]
            android.net.Uri r2 = r5.getUri()
            java.lang.Object r2 = r7.get(r2)
            java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
            if (r2 == 0) goto La0
            Int r3 = r5.getTtcIndex()
            Int r4 = r5.getWeight()
            Boolean r0 = r5.isItalic()
            if (r0 == 0) goto L9d
            r5 = 1
        L90:
            r0 = r10
            Boolean r0 = r0.addFontFromBuffer(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L9f
            r10.abortCreation(r1)
            r0 = 0
            goto L4
        L9d:
            r5 = 0
            goto L90
        L9f:
            r0 = 1
        La0:
            Int r2 = r6 + 1
            r6 = r2
            goto L71
        La4:
            if (r0 != 0) goto Lac
            r10.abortCreation(r1)
            r0 = 0
            goto L4
        Lac:
            Boolean r0 = r10.freeze(r1)
            if (r0 != 0) goto Lb5
            r0 = 0
            goto L4
        Lb5:
            android.graphics.Typeface r0 = r10.createFromFamiliesWithDefault(r1)
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r0, r14)
            goto L4
        Lbf:
            r0 = move-exception
            goto L53
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi26Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$Array<FontInfo>, Int):android.graphics.Typeface")
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    @Nullable
    fun createFromResourcesFontFile(Context context, Resources resources, Int i, String str, Int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, i, str, i2)
        }
        Object objNewFamily = newFamily()
        if (!addFontFromAssetManager(context, objNewFamily, str, 0, -1, -1, null)) {
            abortCreation(objNewFamily)
            return null
        }
        if (freeze(objNewFamily)) {
            return createFromFamiliesWithDefault(objNewFamily)
        }
        return null
    }

    protected fun obtainAbortCreationMethod(Class cls) {
        return cls.getMethod(ABORT_CREATION_METHOD, new Class[0])
    }

    protected fun obtainAddFontFromAssetManagerMethod(Class cls) {
        return cls.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Array<FontVariationAxis>.class)
    }

    protected fun obtainAddFontFromBufferMethod(Class cls) {
        return cls.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, Array<FontVariationAxis>.class, Integer.TYPE, Integer.TYPE)
    }

    protected fun obtainCreateFromFamiliesWithDefaultMethod(Class cls) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance((Class<?>) cls, 1).getClass(), Integer.TYPE, Integer.TYPE)
        declaredMethod.setAccessible(true)
        return declaredMethod
    }

    protected fun obtainFontFamily() {
        return Class.forName(FONT_FAMILY_CLASS)
    }

    protected fun obtainFontFamilyCtor(Class cls) {
        return cls.getConstructor(new Class[0])
    }

    protected fun obtainFreezeMethod(Class cls) {
        return cls.getMethod(FREEZE_METHOD, new Class[0])
    }
}
