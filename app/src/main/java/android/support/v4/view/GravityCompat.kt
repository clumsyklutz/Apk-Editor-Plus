package android.support.v4.view

import android.graphics.Rect
import android.os.Build
import android.view.Gravity

class GravityCompat {
    public static val END = 8388613
    public static val RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615
    public static val RELATIVE_LAYOUT_DIRECTION = 8388608
    public static val START = 8388611

    private constructor() {
    }

    fun apply(Int i, Int i2, Int i3, Rect rect, Int i4, Int i5, Rect rect2, Int i6) {
        if (Build.VERSION.SDK_INT >= 17) {
            Gravity.apply(i, i2, i3, rect, i4, i5, rect2, i6)
        } else {
            Gravity.apply(i, i2, i3, rect, i4, i5, rect2)
        }
    }

    fun apply(Int i, Int i2, Int i3, Rect rect, Rect rect2, Int i4) {
        if (Build.VERSION.SDK_INT >= 17) {
            Gravity.apply(i, i2, i3, rect, rect2, i4)
        } else {
            Gravity.apply(i, i2, i3, rect, rect2)
        }
    }

    fun applyDisplay(Int i, Rect rect, Rect rect2, Int i2) {
        if (Build.VERSION.SDK_INT >= 17) {
            Gravity.applyDisplay(i, rect, rect2, i2)
        } else {
            Gravity.applyDisplay(i, rect, rect2)
        }
    }

    fun getAbsoluteGravity(Int i, Int i2) {
        return Build.VERSION.SDK_INT >= 17 ? Gravity.getAbsoluteGravity(i, i2) : (-8388609) & i
    }
}
