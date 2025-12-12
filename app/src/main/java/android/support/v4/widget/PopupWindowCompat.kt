package android.support.v4.widget

import android.os.Build
import android.support.annotation.NonNull
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class PopupWindowCompat {
    private static val TAG = "PopupWindowCompatApi21"
    private static Method sGetWindowLayoutTypeMethod
    private static Boolean sGetWindowLayoutTypeMethodAttempted
    private static Field sOverlapAnchorField
    private static Boolean sOverlapAnchorFieldAttempted
    private static Method sSetWindowLayoutTypeMethod
    private static Boolean sSetWindowLayoutTypeMethodAttempted

    private constructor() {
    }

    fun getOverlapAnchor(@NonNull PopupWindow popupWindow) throws NoSuchFieldException {
        if (Build.VERSION.SDK_INT >= 23) {
            return popupWindow.getOverlapAnchor()
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (!sOverlapAnchorFieldAttempted) {
                try {
                    Field declaredField = PopupWindow.class.getDeclaredField("mOverlapAnchor")
                    sOverlapAnchorField = declaredField
                    declaredField.setAccessible(true)
                } catch (NoSuchFieldException e) {
                    Log.i(TAG, "Could not fetch mOverlapAnchor field from PopupWindow", e)
                }
                sOverlapAnchorFieldAttempted = true
            }
            if (sOverlapAnchorField != null) {
                try {
                    return ((Boolean) sOverlapAnchorField.get(popupWindow)).booleanValue()
                } catch (IllegalAccessException e2) {
                    Log.i(TAG, "Could not get overlap anchor field in PopupWindow", e2)
                }
            }
        }
        return false
    }

    fun getWindowLayoutType(@NonNull PopupWindow popupWindow) throws NoSuchMethodException, SecurityException {
        if (Build.VERSION.SDK_INT >= 23) {
            return popupWindow.getWindowLayoutType()
        }
        if (!sGetWindowLayoutTypeMethodAttempted) {
            try {
                Method declaredMethod = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0])
                sGetWindowLayoutTypeMethod = declaredMethod
                declaredMethod.setAccessible(true)
            } catch (Exception e) {
            }
            sGetWindowLayoutTypeMethodAttempted = true
        }
        if (sGetWindowLayoutTypeMethod != null) {
            try {
                return ((Integer) sGetWindowLayoutTypeMethod.invoke(popupWindow, new Object[0])).intValue()
            } catch (Exception e2) {
            }
        }
        return 0
    }

    fun setOverlapAnchor(@NonNull PopupWindow popupWindow, Boolean z) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 23) {
            popupWindow.setOverlapAnchor(z)
            return
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (!sOverlapAnchorFieldAttempted) {
                try {
                    Field declaredField = PopupWindow.class.getDeclaredField("mOverlapAnchor")
                    sOverlapAnchorField = declaredField
                    declaredField.setAccessible(true)
                } catch (NoSuchFieldException e) {
                    Log.i(TAG, "Could not fetch mOverlapAnchor field from PopupWindow", e)
                }
                sOverlapAnchorFieldAttempted = true
            }
            if (sOverlapAnchorField != null) {
                try {
                    sOverlapAnchorField.set(popupWindow, Boolean.valueOf(z))
                } catch (IllegalAccessException e2) {
                    Log.i(TAG, "Could not set overlap anchor field in PopupWindow", e2)
                }
            }
        }
    }

    fun setWindowLayoutType(@NonNull PopupWindow popupWindow, Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 23) {
            popupWindow.setWindowLayoutType(i)
            return
        }
        if (!sSetWindowLayoutTypeMethodAttempted) {
            try {
                Method declaredMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE)
                sSetWindowLayoutTypeMethod = declaredMethod
                declaredMethod.setAccessible(true)
            } catch (Exception e) {
            }
            sSetWindowLayoutTypeMethodAttempted = true
        }
        if (sSetWindowLayoutTypeMethod != null) {
            try {
                sSetWindowLayoutTypeMethod.invoke(popupWindow, Integer.valueOf(i))
            } catch (Exception e2) {
            }
        }
    }

    fun showAsDropDown(@NonNull PopupWindow popupWindow, @NonNull View view, Int i, Int i2, Int i3) {
        if (Build.VERSION.SDK_INT >= 19) {
            popupWindow.showAsDropDown(view, i, i2, i3)
            return
        }
        if ((GravityCompat.getAbsoluteGravity(i3, ViewCompat.getLayoutDirection(view)) & 7) == 5) {
            i -= popupWindow.getWidth() - view.getWidth()
        }
        popupWindow.showAsDropDown(view, i, i2)
    }
}
