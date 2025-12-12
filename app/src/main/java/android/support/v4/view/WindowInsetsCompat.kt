package android.support.v4.view

import android.graphics.Rect
import android.os.Build
import android.support.annotation.Nullable
import android.view.WindowInsets

class WindowInsetsCompat {
    private final Object mInsets

    constructor(WindowInsetsCompat windowInsetsCompat) {
        if (Build.VERSION.SDK_INT >= 20) {
            this.mInsets = windowInsetsCompat != null ? WindowInsets((WindowInsets) windowInsetsCompat.mInsets) : null
        } else {
            this.mInsets = null
        }
    }

    private constructor(Object obj) {
        this.mInsets = obj
    }

    static Object unwrap(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat == null) {
            return null
        }
        return windowInsetsCompat.mInsets
    }

    static WindowInsetsCompat wrap(Object obj) {
        if (obj == null) {
            return null
        }
        return WindowInsetsCompat(obj)
    }

    fun consumeDisplayCutout() {
        return Build.VERSION.SDK_INT >= 28 ? WindowInsetsCompat(((WindowInsets) this.mInsets).consumeDisplayCutout()) : this
    }

    fun consumeStableInsets() {
        if (Build.VERSION.SDK_INT >= 21) {
            return WindowInsetsCompat(((WindowInsets) this.mInsets).consumeStableInsets())
        }
        return null
    }

    fun consumeSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return WindowInsetsCompat(((WindowInsets) this.mInsets).consumeSystemWindowInsets())
        }
        return null
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        WindowInsetsCompat windowInsetsCompat = (WindowInsetsCompat) obj
        return this.mInsets == null ? windowInsetsCompat.mInsets == null : this.mInsets.equals(windowInsetsCompat.mInsets)
    }

    @Nullable
    fun getDisplayCutout() {
        if (Build.VERSION.SDK_INT >= 28) {
            return DisplayCutoutCompat.wrap(((WindowInsets) this.mInsets).getDisplayCutout())
        }
        return null
    }

    fun getStableInsetBottom() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetBottom()
        }
        return 0
    }

    fun getStableInsetLeft() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetLeft()
        }
        return 0
    }

    fun getStableInsetRight() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetRight()
        }
        return 0
    }

    fun getStableInsetTop() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetTop()
        }
        return 0
    }

    fun getSystemWindowInsetBottom() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetBottom()
        }
        return 0
    }

    fun getSystemWindowInsetLeft() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetLeft()
        }
        return 0
    }

    fun getSystemWindowInsetRight() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetRight()
        }
        return 0
    }

    fun getSystemWindowInsetTop() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetTop()
        }
        return 0
    }

    fun hasInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).hasInsets()
        }
        return false
    }

    fun hasStableInsets() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).hasStableInsets()
        }
        return false
    }

    fun hasSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).hasSystemWindowInsets()
        }
        return false
    }

    fun hashCode() {
        if (this.mInsets == null) {
            return 0
        }
        return this.mInsets.hashCode()
    }

    fun isConsumed() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).isConsumed()
        }
        return false
    }

    fun isRound() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).isRound()
        }
        return false
    }

    fun replaceSystemWindowInsets(Int i, Int i2, Int i3, Int i4) {
        if (Build.VERSION.SDK_INT >= 20) {
            return WindowInsetsCompat(((WindowInsets) this.mInsets).replaceSystemWindowInsets(i, i2, i3, i4))
        }
        return null
    }

    fun replaceSystemWindowInsets(Rect rect) {
        if (Build.VERSION.SDK_INT >= 21) {
            return WindowInsetsCompat(((WindowInsets) this.mInsets).replaceSystemWindowInsets(rect))
        }
        return null
    }
}
