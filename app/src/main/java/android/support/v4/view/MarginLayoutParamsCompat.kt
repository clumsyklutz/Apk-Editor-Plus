package android.support.v4.view

import android.os.Build
import android.view.ViewGroup

class MarginLayoutParamsCompat {
    private constructor() {
    }

    fun getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
        Int layoutDirection = Build.VERSION.SDK_INT >= 17 ? marginLayoutParams.getLayoutDirection() : 0
        if (layoutDirection == 0 || layoutDirection == 1) {
            return layoutDirection
        }
        return 0
    }

    fun getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return Build.VERSION.SDK_INT >= 17 ? marginLayoutParams.getMarginEnd() : marginLayoutParams.rightMargin
    }

    fun getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return Build.VERSION.SDK_INT >= 17 ? marginLayoutParams.getMarginStart() : marginLayoutParams.leftMargin
    }

    fun isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
        if (Build.VERSION.SDK_INT >= 17) {
            return marginLayoutParams.isMarginRelative()
        }
        return false
    }

    fun resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            marginLayoutParams.resolveLayoutDirection(i)
        }
    }

    fun setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            marginLayoutParams.setLayoutDirection(i)
        }
    }

    fun setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            marginLayoutParams.setMarginEnd(i)
        } else {
            marginLayoutParams.rightMargin = i
        }
    }

    fun setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            marginLayoutParams.setMarginStart(i)
        } else {
            marginLayoutParams.leftMargin = i
        }
    }
}
