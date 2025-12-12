package android.support.v4.view

import android.support.annotation.NonNull
import android.view.View
import android.view.ViewGroup

class NestedScrollingParentHelper {
    private Int mNestedScrollAxes
    private final ViewGroup mViewGroup

    constructor(@NonNull ViewGroup viewGroup) {
        this.mViewGroup = viewGroup
    }

    fun getNestedScrollAxes() {
        return this.mNestedScrollAxes
    }

    fun onNestedScrollAccepted(@NonNull View view, @NonNull View view2, Int i) {
        onNestedScrollAccepted(view, view2, i, 0)
    }

    fun onNestedScrollAccepted(@NonNull View view, @NonNull View view2, Int i, Int i2) {
        this.mNestedScrollAxes = i
    }

    fun onStopNestedScroll(@NonNull View view) {
        onStopNestedScroll(view, 0)
    }

    fun onStopNestedScroll(@NonNull View view, Int i) {
        this.mNestedScrollAxes = 0
    }
}
