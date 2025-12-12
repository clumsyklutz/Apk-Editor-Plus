package com.android.apksig.internal.util

import android.support.v7.widget.ActivityChooserView
import java.util.ArrayList
import java.util.Collections
import java.util.List

class InclusiveIntRange {
    public final Int max
    public final Int min

    constructor(Int i, Int i2) {
        this.min = i
        this.max = i2
    }

    fun from(Int i) {
        return InclusiveIntRange(i, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)
    }

    fun fromTo(Int i, Int i2) {
        return InclusiveIntRange(i, i2)
    }

    fun getMax() {
        return this.max
    }

    fun getMin() {
        return this.min
    }

    public List<InclusiveIntRange> getValuesNotIn(List<InclusiveIntRange> list) {
        if (list.isEmpty()) {
            return Collections.singletonList(this)
        }
        Int i = this.min
        ArrayList arrayList = null
        for (InclusiveIntRange inclusiveIntRange : list) {
            Int i2 = inclusiveIntRange.max
            if (i <= i2) {
                Int i3 = inclusiveIntRange.min
                if (i < i3) {
                    if (arrayList == null) {
                        arrayList = ArrayList()
                    }
                    arrayList.add(fromTo(i, i3 - 1))
                }
                if (i2 >= this.max) {
                    return arrayList != null ? arrayList : Collections.emptyList()
                }
                i = i2 + 1
            }
        }
        if (i <= this.max) {
            if (arrayList == null) {
                arrayList = ArrayList(1)
            }
            arrayList.add(fromTo(i, this.max))
        }
        return arrayList != null ? arrayList : Collections.emptyList()
    }

    fun toString() {
        String str
        StringBuilder sb = StringBuilder()
        sb.append("[")
        sb.append(this.min)
        sb.append(", ")
        if (this.max < Integer.MAX_VALUE) {
            str = this.max + "]"
        } else {
            str = "∞)"
        }
        sb.append(str)
        return sb.toString()
    }
}
