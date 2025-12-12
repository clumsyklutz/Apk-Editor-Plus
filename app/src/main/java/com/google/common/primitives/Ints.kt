package com.google.common.primitives

import android.support.v7.widget.ActivityChooserView
import com.google.common.base.Preconditions

class Ints extends IntsMethodsForWeb {
    fun compare(Int i, Int i2) {
        if (i < i2) {
            return -1
        }
        return i > i2 ? 1 : 0
    }

    fun constrainToRange(Int i, Int i2, Int i3) {
        Preconditions.checkArgument(i2 <= i3, "min (%s) must be less than or equal to max (%s)", i2, i3)
        return Math.min(Math.max(i, i2), i3)
    }

    fun saturatedCast(Long j) {
        if (j > 2147483647L) {
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        }
        if (j < -2147483648L) {
            return Integer.MIN_VALUE
        }
        return (Int) j
    }
}
