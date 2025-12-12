package android.support.v7.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ButtonBarLayout extends LinearLayout {
    private static val PEEK_BUTTON_DP = 16
    private Boolean mAllowStacking
    private Int mLastWidthSize
    private Int mMinimumHeight

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.mLastWidthSize = -1
        this.mMinimumHeight = 0
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout)
        this.mAllowStacking = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ButtonBarLayout_allowStacking, true)
        typedArrayObtainStyledAttributes.recycle()
    }

    private fun getNextVisibleChildIndex(Int i) {
        Int childCount = getChildCount()
        for (Int i2 = i; i2 < childCount; i2++) {
            if (getChildAt(i2).getVisibility() == 0) {
                return i2
            }
        }
        return -1
    }

    private fun isStacked() {
        return getOrientation() == 1
    }

    private fun setStacked(Boolean z) {
        setOrientation(z ? 1 : 0)
        setGravity(z ? 5 : 80)
        View viewFindViewById = findViewById(R.id.spacer)
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(z ? 8 : 4)
        }
        for (Int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
            bringChildToFront(getChildAt(childCount))
        }
    }

    @Override // android.view.View
    fun getMinimumHeight() {
        return Math.max(this.mMinimumHeight, super.getMinimumHeight())
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Boolean z
        Int iMakeMeasureSpec
        Int measuredHeight
        Int size = View.MeasureSpec.getSize(i)
        if (this.mAllowStacking) {
            if (size > this.mLastWidthSize && isStacked()) {
                setStacked(false)
            }
            this.mLastWidthSize = size
        }
        if (isStacked() || View.MeasureSpec.getMode(i) != 1073741824) {
            z = false
            iMakeMeasureSpec = i
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE)
            z = true
        }
        super.onMeasure(iMakeMeasureSpec, i2)
        if (this.mAllowStacking && !isStacked()) {
            if ((getMeasuredWidthAndState() & ViewCompat.MEASURED_STATE_MASK) == 16777216) {
                setStacked(true)
                z = true
            }
        }
        if (z) {
            super.onMeasure(i, i2)
        }
        Int nextVisibleChildIndex = getNextVisibleChildIndex(0)
        if (nextVisibleChildIndex >= 0) {
            View childAt = getChildAt(nextVisibleChildIndex)
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams()
            measuredHeight = layoutParams.bottomMargin + childAt.getMeasuredHeight() + getPaddingTop() + layoutParams.topMargin + 0
            if (isStacked()) {
                Int nextVisibleChildIndex2 = getNextVisibleChildIndex(nextVisibleChildIndex + 1)
                if (nextVisibleChildIndex2 >= 0) {
                    measuredHeight += getChildAt(nextVisibleChildIndex2).getPaddingTop() + ((Int) (16.0f * getResources().getDisplayMetrics().density))
                }
            } else {
                measuredHeight += getPaddingBottom()
            }
        } else {
            measuredHeight = 0
        }
        if (ViewCompat.getMinimumHeight(this) != measuredHeight) {
            setMinimumHeight(measuredHeight)
        }
    }

    fun setAllowStacking(Boolean z) {
        if (this.mAllowStacking != z) {
            this.mAllowStacking = z
            if (!this.mAllowStacking && getOrientation() == 1) {
                setStacked(false)
            }
            requestLayout()
        }
    }
}
