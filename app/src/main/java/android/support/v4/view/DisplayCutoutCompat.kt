package android.support.v4.view

import android.graphics.Rect
import android.os.Build
import android.view.DisplayCutout
import java.util.List

class DisplayCutoutCompat {
    private final Object mDisplayCutout

    constructor(Rect rect, List list) {
        this(Build.VERSION.SDK_INT >= 28 ? DisplayCutout(rect, list) : null)
    }

    private constructor(Object obj) {
        this.mDisplayCutout = obj
    }

    static DisplayCutoutCompat wrap(Object obj) {
        if (obj == null) {
            return null
        }
        return DisplayCutoutCompat(obj)
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        DisplayCutoutCompat displayCutoutCompat = (DisplayCutoutCompat) obj
        return this.mDisplayCutout == null ? displayCutoutCompat.mDisplayCutout == null : this.mDisplayCutout.equals(displayCutoutCompat.mDisplayCutout)
    }

    public final List getBoundingRects() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getBoundingRects()
        }
        return null
    }

    public final Int getSafeInsetBottom() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetBottom()
        }
        return 0
    }

    public final Int getSafeInsetLeft() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetLeft()
        }
        return 0
    }

    public final Int getSafeInsetRight() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetRight()
        }
        return 0
    }

    public final Int getSafeInsetTop() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetTop()
        }
        return 0
    }

    public final Int hashCode() {
        if (this.mDisplayCutout == null) {
            return 0
        }
        return this.mDisplayCutout.hashCode()
    }

    public final String toString() {
        return "DisplayCutoutCompat{" + this.mDisplayCutout + "}"
    }
}
