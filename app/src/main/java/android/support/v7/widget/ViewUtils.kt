package android.support.v7.widget

import android.graphics.Rect
import android.os.Build
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import android.util.Log
import android.view.View
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ViewUtils {
    private static val TAG = "ViewUtils"
    private static Method sComputeFitSystemWindowsMethod

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class)
                sComputeFitSystemWindowsMethod = declaredMethod
                if (declaredMethod.isAccessible()) {
                    return
                }
                sComputeFitSystemWindowsMethod.setAccessible(true)
            } catch (NoSuchMethodException e) {
                Log.d(TAG, "Could not find method computeFitSystemWindows. Oh well.")
            }
        }
    }

    private constructor() {
    }

    fun computeFitSystemWindows(View view, Rect rect, Rect rect2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (sComputeFitSystemWindowsMethod != null) {
            try {
                sComputeFitSystemWindowsMethod.invoke(view, rect, rect2)
            } catch (Exception e) {
                Log.d(TAG, "Could not invoke computeFitSystemWindows", e)
            }
        }
    }

    fun isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1
    }

    fun makeOptionalFitsSystemWindows(View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0])
                if (!method.isAccessible()) {
                    method.setAccessible(true)
                }
                method.invoke(view, new Object[0])
            } catch (IllegalAccessException e) {
                Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e)
            } catch (NoSuchMethodException e2) {
                Log.d(TAG, "Could not find method makeOptionalFitsSystemWindows. Oh well...")
            } catch (InvocationTargetException e3) {
                Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e3)
            }
        }
    }
}
