package android.support.v4.view

import android.R
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.NonNull
import android.util.Log
import android.util.TypedValue
import android.view.ViewConfiguration
import java.lang.reflect.Method

class ViewConfigurationCompat {
    private static val TAG = "ViewConfigCompat"
    private static Method sGetScaledScrollFactorMethod

    static {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0])
            } catch (Exception e) {
                Log.i(TAG, "Could not find method getScaledScrollFactor() on ViewConfiguration")
            }
        }
    }

    private constructor() {
    }

    private fun getLegacyScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT >= 25 && sGetScaledScrollFactorMethod != null) {
            try {
                return ((Integer) sGetScaledScrollFactorMethod.invoke(viewConfiguration, new Object[0])).intValue()
            } catch (Exception e) {
                Log.i(TAG, "Could not find method getScaledScrollFactor() on ViewConfiguration")
            }
        }
        TypedValue typedValue = TypedValue()
        if (context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
            return typedValue.getDimension(context.getResources().getDisplayMetrics())
        }
        return 0.0f
    }

    fun getScaledHorizontalScrollFactor(@NonNull ViewConfiguration viewConfiguration, @NonNull Context context) {
        return Build.VERSION.SDK_INT >= 26 ? viewConfiguration.getScaledHorizontalScrollFactor() : getLegacyScrollFactor(viewConfiguration, context)
    }

    fun getScaledHoverSlop(ViewConfiguration viewConfiguration) {
        return Build.VERSION.SDK_INT >= 28 ? viewConfiguration.getScaledHoverSlop() : viewConfiguration.getScaledTouchSlop() / 2
    }

    @Deprecated
    fun getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledPagingTouchSlop()
    }

    fun getScaledVerticalScrollFactor(@NonNull ViewConfiguration viewConfiguration, @NonNull Context context) {
        return Build.VERSION.SDK_INT >= 26 ? viewConfiguration.getScaledVerticalScrollFactor() : getLegacyScrollFactor(viewConfiguration, context)
    }

    @Deprecated
    fun hasPermanentMenuKey(ViewConfiguration viewConfiguration) {
        return viewConfiguration.hasPermanentMenuKey()
    }

    fun shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration viewConfiguration, @NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return viewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent()
        }
        Resources resources = context.getResources()
        Int identifier = resources.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android")
        return identifier != 0 && resources.getBoolean(identifier)
    }
}
