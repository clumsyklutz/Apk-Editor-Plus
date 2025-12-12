package android.support.v4.view

import android.os.Build
import android.support.annotation.NonNull
import android.support.compat.R
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent

class ViewGroupCompat {
    public static val LAYOUT_MODE_CLIP_BOUNDS = 0
    public static val LAYOUT_MODE_OPTICAL_BOUNDS = 1

    private constructor() {
    }

    fun getLayoutMode(@NonNull ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 18) {
            return viewGroup.getLayoutMode()
        }
        return 0
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getNestedScrollAxes(@NonNull ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 21) {
            return viewGroup.getNestedScrollAxes()
        }
        if (viewGroup is NestedScrollingParent) {
            return ((NestedScrollingParent) viewGroup).getNestedScrollAxes()
        }
        return 0
    }

    fun isTransitionGroup(@NonNull ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 21) {
            return viewGroup.isTransitionGroup()
        }
        Boolean bool = (Boolean) viewGroup.getTag(R.id.tag_transition_group)
        return ((bool == null || !bool.booleanValue()) && viewGroup.getBackground() == null && ViewCompat.getTransitionName(viewGroup) == null) ? false : true
    }

    @Deprecated
    fun onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return viewGroup.onRequestSendAccessibilityEvent(view, accessibilityEvent)
    }

    fun setLayoutMode(@NonNull ViewGroup viewGroup, Int i) {
        if (Build.VERSION.SDK_INT >= 18) {
            viewGroup.setLayoutMode(i)
        }
    }

    @Deprecated
    fun setMotionEventSplittingEnabled(ViewGroup viewGroup, Boolean z) {
        viewGroup.setMotionEventSplittingEnabled(z)
    }

    fun setTransitionGroup(@NonNull ViewGroup viewGroup, Boolean z) {
        if (Build.VERSION.SDK_INT >= 21) {
            viewGroup.setTransitionGroup(z)
        } else {
            viewGroup.setTag(R.id.tag_transition_group, Boolean.valueOf(z))
        }
    }
}
