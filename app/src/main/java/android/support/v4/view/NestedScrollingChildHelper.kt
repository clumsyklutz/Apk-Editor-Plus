package android.support.v4.view

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.View
import android.view.ViewParent

class NestedScrollingChildHelper {
    private Boolean mIsNestedScrollingEnabled
    private ViewParent mNestedScrollingParentNonTouch
    private ViewParent mNestedScrollingParentTouch
    private Array<Int> mTempNestedScrollConsumed
    private final View mView

    constructor(@NonNull View view) {
        this.mView = view
    }

    private fun getNestedScrollingParentForType(Int i) {
        switch (i) {
            case 0:
                return this.mNestedScrollingParentTouch
            case 1:
                return this.mNestedScrollingParentNonTouch
            default:
                return null
        }
    }

    private fun setNestedScrollingParentForType(Int i, ViewParent viewParent) {
        switch (i) {
            case 0:
                this.mNestedScrollingParentTouch = viewParent
                break
            case 1:
                this.mNestedScrollingParentNonTouch = viewParent
                break
        }
    }

    fun dispatchNestedFling(Float f, Float f2, Boolean z) {
        ViewParent nestedScrollingParentForType
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(0)) == null) {
            return false
        }
        return ViewParentCompat.onNestedFling(nestedScrollingParentForType, this.mView, f, f2, z)
    }

    fun dispatchNestedPreFling(Float f, Float f2) {
        ViewParent nestedScrollingParentForType
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(0)) == null) {
            return false
        }
        return ViewParentCompat.onNestedPreFling(nestedScrollingParentForType, this.mView, f, f2)
    }

    fun dispatchNestedPreScroll(Int i, Int i2, @Nullable Array<Int> iArr, @Nullable Array<Int> iArr2) {
        return dispatchNestedPreScroll(i, i2, iArr, iArr2, 0)
    }

    fun dispatchNestedPreScroll(Int i, Int i2, @Nullable Array<Int> iArr, @Nullable Array<Int> iArr2, Int i3) {
        Int i4
        Int i5
        Array<Int> iArr3
        if (isNestedScrollingEnabled()) {
            ViewParent nestedScrollingParentForType = getNestedScrollingParentForType(i3)
            if (nestedScrollingParentForType == null) {
                return false
            }
            if (i != 0 || i2 != 0) {
                if (iArr2 != null) {
                    this.mView.getLocationInWindow(iArr2)
                    Int i6 = iArr2[0]
                    i4 = iArr2[1]
                    i5 = i6
                } else {
                    i4 = 0
                    i5 = 0
                }
                if (iArr == null) {
                    if (this.mTempNestedScrollConsumed == null) {
                        this.mTempNestedScrollConsumed = new Int[2]
                    }
                    iArr3 = this.mTempNestedScrollConsumed
                } else {
                    iArr3 = iArr
                }
                iArr3[0] = 0
                iArr3[1] = 0
                ViewParentCompat.onNestedPreScroll(nestedScrollingParentForType, this.mView, i, i2, iArr3, i3)
                if (iArr2 != null) {
                    this.mView.getLocationInWindow(iArr2)
                    iArr2[0] = iArr2[0] - i5
                    iArr2[1] = iArr2[1] - i4
                }
                return (iArr3[0] == 0 && iArr3[1] == 0) ? false : true
            }
            if (iArr2 != null) {
                iArr2[0] = 0
                iArr2[1] = 0
            }
        }
        return false
    }

    fun dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, @Nullable Array<Int> iArr) {
        return dispatchNestedScroll(i, i2, i3, i4, iArr, 0)
    }

    fun dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, @Nullable Array<Int> iArr, Int i5) {
        ViewParent nestedScrollingParentForType
        Int i6
        Int i7
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(i5)) == null) {
            return false
        }
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            if (iArr != null) {
                iArr[0] = 0
                iArr[1] = 0
            }
            return false
        }
        if (iArr != null) {
            this.mView.getLocationInWindow(iArr)
            Int i8 = iArr[0]
            i6 = iArr[1]
            i7 = i8
        } else {
            i6 = 0
            i7 = 0
        }
        ViewParentCompat.onNestedScroll(nestedScrollingParentForType, this.mView, i, i2, i3, i4, i5)
        if (iArr != null) {
            this.mView.getLocationInWindow(iArr)
            iArr[0] = iArr[0] - i7
            iArr[1] = iArr[1] - i6
        }
        return true
    }

    fun hasNestedScrollingParent() {
        return hasNestedScrollingParent(0)
    }

    fun hasNestedScrollingParent(Int i) {
        return getNestedScrollingParentForType(i) != null
    }

    fun isNestedScrollingEnabled() {
        return this.mIsNestedScrollingEnabled
    }

    fun onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(this.mView)
    }

    fun onStopNestedScroll(@NonNull View view) {
        ViewCompat.stopNestedScroll(this.mView)
    }

    fun setNestedScrollingEnabled(Boolean z) {
        if (this.mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(this.mView)
        }
        this.mIsNestedScrollingEnabled = z
    }

    fun startNestedScroll(Int i) {
        return startNestedScroll(i, 0)
    }

    fun startNestedScroll(Int i, Int i2) {
        if (hasNestedScrollingParent(i2)) {
            return true
        }
        if (isNestedScrollingEnabled()) {
            View view = this.mView
            for (ViewParent parent = this.mView.getParent(); parent != null; parent = parent.getParent()) {
                if (ViewParentCompat.onStartNestedScroll(parent, view, this.mView, i, i2)) {
                    setNestedScrollingParentForType(i2, parent)
                    ViewParentCompat.onNestedScrollAccepted(parent, view, this.mView, i, i2)
                    return true
                }
                if (parent is View) {
                    view = (View) parent
                }
            }
        }
        return false
    }

    fun stopNestedScroll() {
        stopNestedScroll(0)
    }

    fun stopNestedScroll(Int i) {
        ViewParent nestedScrollingParentForType = getNestedScrollingParentForType(i)
        if (nestedScrollingParentForType != null) {
            ViewParentCompat.onStopNestedScroll(nestedScrollingParentForType, this.mView, i)
            setNestedScrollingParentForType(i, null)
        }
    }
}
