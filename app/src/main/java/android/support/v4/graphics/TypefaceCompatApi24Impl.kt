package android.support.v4.graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.net.Uri
import android.os.CancellationSignal
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.content.res.FontResourcesParserCompat
import android.support.v4.provider.FontsContractCompat
import android.support.v4.util.SimpleArrayMap
import android.util.Log
import java.lang.reflect.Array
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.nio.ByteBuffer
import java.util.List

@RequiresApi(24)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi24Impl extends TypefaceCompatBaseImpl {
    private static val ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle"
    private static val CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault"
    private static val FONT_FAMILY_CLASS = "android.graphics.FontFamily"
    private static val TAG = "TypefaceCompatApi24Impl"
    private static final Method sAddFontWeightStyle
    private static final Method sCreateFromFamiliesWithDefault
    private static final Class sFontFamily
    private static final Constructor sFontFamilyCtor

    static {
        Method method
        Constructor<?> constructor
        Class<?> cls
        Method method2 = null
        try {
            Class<?> cls2 = Class.forName(FONT_FAMILY_CLASS)
            Constructor<?> constructor2 = cls2.getConstructor(new Class[0])
            Method method3 = cls2.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE)
            method = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(cls2, 1).getClass())
            method2 = method3
            constructor = constructor2
            cls = cls2
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, e.getClass().getName(), e)
            method = null
            constructor = null
            cls = null
        }
        sFontFamilyCtor = constructor
        sFontFamily = cls
        sAddFontWeightStyle = method2
        sCreateFromFamiliesWithDefault = method
    }

    TypefaceCompatApi24Impl() {
    }

    private fun addFontWeightStyle(Object obj, ByteBuffer byteBuffer, Int i, Int i2, Boolean z) {
        try {
            return ((Boolean) sAddFontWeightStyle.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue()
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    private fun createFromFamiliesWithDefault(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) sFontFamily, 1)
            Array.set(objNewInstance, 0, obj)
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, objNewInstance)
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    fun isUsable() {
        if (sAddFontWeightStyle == null) {
            Log.w(TAG, "Unable to collect necessary private methods.Fallback to legacy implementation.")
        }
        return sAddFontWeightStyle != null
    }

    private fun newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0])
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    fun createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, Int i) {
        Object objNewFamily = newFamily()
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
            ByteBuffer byteBufferCopyToDirectBuffer = TypefaceCompatUtil.copyToDirectBuffer(context, resources, fontFileResourceEntry.getResourceId())
            if (byteBufferCopyToDirectBuffer == null || !addFontWeightStyle(objNewFamily, byteBufferCopyToDirectBuffer, fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic())) {
                return null
            }
        }
        return createFromFamiliesWithDefault(objNewFamily)
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    fun createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.Array<FontInfo> fontInfoArr, Int i) throws Throwable {
        Object objNewFamily = newFamily()
        SimpleArrayMap simpleArrayMap = SimpleArrayMap()
        for (FontsContractCompat.FontInfo fontInfo : fontInfoArr) {
            Uri uri = fontInfo.getUri()
            ByteBuffer byteBufferMmap = (ByteBuffer) simpleArrayMap.get(uri)
            if (byteBufferMmap == null) {
                byteBufferMmap = TypefaceCompatUtil.mmap(context, cancellationSignal, uri)
                simpleArrayMap.put(uri, byteBufferMmap)
            }
            if (!addFontWeightStyle(objNewFamily, byteBufferMmap, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic())) {
                return null
            }
        }
        return Typeface.create(createFromFamiliesWithDefault(objNewFamily), i)
    }
}
