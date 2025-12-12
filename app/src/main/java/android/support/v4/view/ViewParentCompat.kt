package android.support.v4.view

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.view.accessibility.AccessibilityEvent

class ViewParentCompat {
    private static val TAG = "ViewParentCompat"

    private constructor() {
    }

    fun notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, Int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            viewParent.notifySubtreeAccessibilityStateChanged(view, view2, i)
        }
    }

    fun onNestedFling(ViewParent viewParent, View view, Float f, Float f2, Boolean z) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                return viewParent.onNestedFling(view, f, f2, z)
            } catch (AbstractMethodError e) {
                Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedFling", e)
            }
        } else if (viewParent is NestedScrollingParent) {
            return ((NestedScrollingParent) viewParent).onNestedFling(view, f, f2, z)
        }
        return false
    }

    fun onNestedPreFling(ViewParent viewParent, View view, Float f, Float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                return viewParent.onNestedPreFling(view, f, f2)
            } catch (AbstractMethodError e) {
                Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedPreFling", e)
            }
        } else if (viewParent is NestedScrollingParent) {
            return ((NestedScrollingParent) viewParent).onNestedPreFling(view, f, f2)
        }
        return false
    }

    fun onNestedPreScroll(ViewParent viewParent, View view, Int i, Int i2, Array<Int> iArr) {
        onNestedPreScroll(viewParent, view, i, i2, iArr, 0)
    }

    fun onNestedPreScroll(ViewParent viewParent, View view, Int i, Int i2, Array<Int> iArr, Int i3) {
        if (viewParent is NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedPreScroll(view, i, i2, iArr, i3)
            return
        }
        if (i3 == 0) {
            if (Build.VERSION.SDK_INT < 21) {
                if (viewParent is NestedScrollingParent) {
                    ((NestedScrollingParent) viewParent).onNestedPreScroll(view, i, i2, iArr)
                }
            } else {
                try {
                    viewParent.onNestedPreScroll(view, i, i2, iArr)
                } catch (AbstractMethodError e) {
                    Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedPreScroll", e)
                }
            }
        }
    }

    fun onNestedScroll(ViewParent viewParent, View view, Int i, Int i2, Int i3, Int i4) {
        onNestedScroll(viewParent, view, i, i2, i3, i4, 0)
    }

    fun onNestedScroll(ViewParent viewParent, View view, Int i, Int i2, Int i3, Int i4, Int i5) {
        if (viewParent is NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedScroll(view, i, i2, i3, i4, i5)
            return
        }
        if (i5 == 0) {
            if (Build.VERSION.SDK_INT < 21) {
                if (viewParent is NestedScrollingParent) {
                    ((NestedScrollingParent) viewParent).onNestedScroll(view, i, i2, i3, i4)
                }
            } else {
                try {
                    viewParent.onNestedScroll(view, i, i2, i3, i4)
                } catch (AbstractMethodError e) {
                    Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedScroll", e)
                }
            }
        }
    }

    fun onNestedScrollAccepted(ViewParent viewParent, View view, View view2, Int i) {
        onNestedScrollAccepted(viewParent, view, view2, i, 0)
    }

    fun onNestedScrollAccepted(ViewParent viewParent, View view, View view2, Int i, Int i2) {
        if (viewParent is NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedScrollAccepted(view, view2, i, i2)
            return
        }
        if (i2 == 0) {
            if (Build.VERSION.SDK_INT < 21) {
                if (viewParent is NestedScrollingParent) {
                    ((NestedScrollingParent) viewParent).onNestedScrollAccepted(view, view2, i)
                }
            } else {
                try {
                    viewParent.onNestedScrollAccepted(view, view2, i)
                } catch (AbstractMethodError e) {
                    Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedScrollAccepted", e)
                }
            }
        }
    }

    fun onStartNestedScroll(ViewParent viewParent, View view, View view2, Int i) {
        return onStartNestedScroll(viewParent, view, view2, i, 0)
    }

    fun onStartNestedScroll(ViewParent viewParent, View view, View view2, Int i, Int i2) {
        if (viewParent is NestedScrollingParent2) {
            return ((NestedScrollingParent2) viewParent).onStartNestedScroll(view, view2, i, i2)
        }
        if (i2 == 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    return viewParent.onStartNestedScroll(view, view2, i)
                } catch (AbstractMethodError e) {
                    Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onStartNestedScroll", e)
                }
            } else if (viewParent is NestedScrollingParent) {
                return ((NestedScrollingParent) viewParent).onStartNestedScroll(view, view2, i)
            }
        }
        return false
    }

    fun onStopNestedScroll(ViewParent viewParent, View view) {
        onStopNestedScroll(viewParent, view, 0)
    }

    fun onStopNestedScroll(ViewParent viewParent, View view, Int i) {
        if (viewParent is NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onStopNestedScroll(view, i)
            return
        }
        if (i == 0) {
            if (Build.VERSION.SDK_INT < 21) {
                if (viewParent is NestedScrollingParent) {
                    ((NestedScrollingParent) viewParent).onStopNestedScroll(view)
                }
            } else {
                try {
                    viewParent.onStopNestedScroll(view)
                } catch (AbstractMethodError e) {
                    Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onStopNestedScroll", e)
                }
            }
        }
    }

    @Deprecated
    fun requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return viewParent.requestSendAccessibilityEvent(view, accessibilityEvent)
    }
}
