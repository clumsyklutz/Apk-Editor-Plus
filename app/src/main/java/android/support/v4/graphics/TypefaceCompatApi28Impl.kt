package android.support.v4.graphics

import android.graphics.Typeface
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import java.lang.reflect.Array
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@RequiresApi(28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi28Impl extends TypefaceCompatApi26Impl {
    private static val CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault"
    private static val DEFAULT_FAMILY = "sans-serif"
    private static val RESOLVE_BY_FONT_TABLE = -1
    private static val TAG = "TypefaceCompatApi28Impl"

    @Override // android.support.v4.graphics.TypefaceCompatApi26Impl
    protected fun createFromFamiliesWithDefault(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.mFontFamily, 1)
            Array.set(objNewInstance, 0, obj)
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, objNewInstance, DEFAULT_FAMILY, -1, -1)
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RuntimeException(e)
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatApi26Impl
    protected fun obtainCreateFromFamiliesWithDefaultMethod(Class cls) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance((Class<?>) cls, 1).getClass(), String.class, Integer.TYPE, Integer.TYPE)
        declaredMethod.setAccessible(true)
        return declaredMethod
    }
}
