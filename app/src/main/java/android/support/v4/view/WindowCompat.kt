package android.support.v4.view

import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.view.View
import android.view.Window

class WindowCompat {
    public static val FEATURE_ACTION_BAR = 8
    public static val FEATURE_ACTION_BAR_OVERLAY = 9
    public static val FEATURE_ACTION_MODE_OVERLAY = 10

    private constructor() {
    }

    @NonNull
    fun requireViewById(@NonNull Window window, @IdRes Int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            return window.requireViewById(i)
        }
        View viewFindViewById = window.findViewById(i)
        if (viewFindViewById == null) {
            throw IllegalArgumentException("ID does not reference a View inside this Window")
        }
        return viewFindViewById
    }
}
