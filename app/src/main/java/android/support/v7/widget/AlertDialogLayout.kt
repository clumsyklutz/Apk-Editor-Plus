package android.support.v7.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class AlertDialogLayout extends LinearLayoutCompat {
    constructor(@Nullable Context context) {
        super(context)
    }

    constructor(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    private fun forceUniformWidth(Int i, Int i2) {
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824)
        for (Int i3 = 0; i3 < i; i3++) {
            View childAt = getChildAt(i3)
            if (childAt.getVisibility() != 8) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams()
                if (layoutParams.width == -1) {
                    Int i4 = layoutParams.height
                    layoutParams.height = childAt.getMeasuredHeight()
                    measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i2, 0)
                    layoutParams.height = i4
                }
            }
        }
    }

    private fun resolveMinimumHeight(View view) throws NoSuchFieldException {
        View childAt = view
        while (true) {
            Int minimumHeight = ViewCompat.getMinimumHeight(childAt)
            if (minimumHeight <= 0) {
                if (!(childAt is ViewGroup)) {
                    break
                }
                ViewGroup viewGroup = (ViewGroup) childAt
                if (viewGroup.getChildCount() != 1) {
                    break
                }
                childAt = viewGroup.getChildAt(0)
            } else {
                return minimumHeight
            }
        }
        return 0
    }

    private fun setChildFrame(View view, Int i, Int i2, Int i3, Int i4) {
        view.layout(i, i2, i + i3, i2 + i4)
    }

    private fun tryOnMeasure(Int i, Int i2) throws NoSuchFieldException {
        Int i3
        Int i4
        Int i5
        Int iCombineMeasuredStates
        Int i6
        Int i7
        Int iCombineMeasuredStates2
        View view
        View view2 = null
        View view3 = null
        Int childCount = getChildCount()
        Int i8 = 0
        View view4 = null
        while (i8 < childCount) {
            View childAt = getChildAt(i8)
            if (childAt.getVisibility() != 8) {
                Int id = childAt.getId()
                if (id == R.id.topPanel) {
                    View view5 = view3
                    view = childAt
                    childAt = view5
                } else if (id == R.id.buttonPanel) {
                    view = view2
                } else {
                    if ((id != R.id.contentPanel && id != R.id.customPanel) || view4 != null) {
                        return false
                    }
                    view4 = childAt
                    childAt = view3
                    view = view2
                }
            } else {
                childAt = view3
                view = view2
            }
            i8++
            view2 = view
            view3 = childAt
        }
        Int mode = View.MeasureSpec.getMode(i2)
        Int size = View.MeasureSpec.getSize(i2)
        Int mode2 = View.MeasureSpec.getMode(i)
        Int iCombineMeasuredStates3 = 0
        Int paddingBottom = getPaddingBottom() + getPaddingTop()
        if (view2 != null) {
            view2.measure(i, 0)
            paddingBottom += view2.getMeasuredHeight()
            iCombineMeasuredStates3 = View.combineMeasuredStates(0, view2.getMeasuredState())
        }
        Int iResolveMinimumHeight = 0
        if (view3 != null) {
            view3.measure(i, 0)
            iResolveMinimumHeight = resolveMinimumHeight(view3)
            Int measuredHeight = view3.getMeasuredHeight() - iResolveMinimumHeight
            paddingBottom += iResolveMinimumHeight
            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, view3.getMeasuredState())
            i3 = measuredHeight
        } else {
            i3 = 0
        }
        if (view4 != null) {
            view4.measure(i, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingBottom), mode))
            Int measuredHeight2 = view4.getMeasuredHeight()
            paddingBottom += measuredHeight2
            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, view4.getMeasuredState())
            i4 = measuredHeight2
        } else {
            i4 = 0
        }
        Int i9 = size - paddingBottom
        if (view3 != null) {
            Int i10 = paddingBottom - iResolveMinimumHeight
            Int iMin = Math.min(i9, i3)
            if (iMin > 0) {
                i9 -= iMin
                iResolveMinimumHeight += iMin
            }
            view3.measure(i, View.MeasureSpec.makeMeasureSpec(iResolveMinimumHeight, 1073741824))
            Int measuredHeight3 = view3.getMeasuredHeight() + i10
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates3, view3.getMeasuredState())
            Int i11 = i9
            i6 = measuredHeight3
            i5 = i11
        } else {
            i5 = i9
            iCombineMeasuredStates = iCombineMeasuredStates3
            i6 = paddingBottom
        }
        if (view4 == null || i5 <= 0) {
            i7 = i6
            iCombineMeasuredStates2 = iCombineMeasuredStates
        } else {
            view4.measure(i, View.MeasureSpec.makeMeasureSpec(i5 + i4, mode))
            Int measuredHeight4 = (i6 - i4) + view4.getMeasuredHeight()
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, view4.getMeasuredState())
            i7 = measuredHeight4
        }
        Int iMax = 0
        for (Int i12 = 0; i12 < childCount; i12++) {
            View childAt2 = getChildAt(i12)
            if (childAt2.getVisibility() != 8) {
                iMax = Math.max(iMax, childAt2.getMeasuredWidth())
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(iMax + getPaddingLeft() + getPaddingRight(), i, iCombineMeasuredStates2), View.resolveSizeAndState(i7, i2, 0))
        if (mode2 != 1073741824) {
            forceUniformWidth(childCount, i2)
        }
        return true
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int paddingTop
        Int i5
        Int paddingLeft = getPaddingLeft()
        Int i6 = i3 - i
        Int paddingRight = i6 - getPaddingRight()
        Int paddingRight2 = (i6 - paddingLeft) - getPaddingRight()
        Int measuredHeight = getMeasuredHeight()
        Int childCount = getChildCount()
        Int gravity = getGravity()
        Int i7 = gravity & R.styleable.AppCompatTheme_ratingBarStyleSmall
        Int i8 = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK
        switch (i7) {
            case 16:
                paddingTop = (((i4 - i2) - measuredHeight) / 2) + getPaddingTop()
                break
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                paddingTop = ((getPaddingTop() + i4) - i2) - measuredHeight
                break
            default:
                paddingTop = getPaddingTop()
                break
        }
        Drawable dividerDrawable = getDividerDrawable()
        Int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight()
        Int i9 = paddingTop
        for (Int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10)
            if (childAt != null && childAt.getVisibility() != 8) {
                Int measuredWidth = childAt.getMeasuredWidth()
                Int measuredHeight2 = childAt.getMeasuredHeight()
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams()
                Int i11 = layoutParams.gravity
                if (i11 < 0) {
                    i11 = i8
                }
                switch (GravityCompat.getAbsoluteGravity(i11, ViewCompat.getLayoutDirection(this)) & 7) {
                    case 1:
                        i5 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin
                        break
                    case 5:
                        i5 = (paddingRight - measuredWidth) - layoutParams.rightMargin
                        break
                    default:
                        i5 = paddingLeft + layoutParams.leftMargin
                        break
                }
                Int i12 = layoutParams.topMargin + (hasDividerBeforeChildAt(i10) ? i9 + intrinsicHeight : i9)
                setChildFrame(childAt, i5, i12, measuredWidth, measuredHeight2)
                i9 = i12 + layoutParams.bottomMargin + measuredHeight2
            }
        }
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        if (tryOnMeasure(i, i2)) {
            return
        }
        super.onMeasure(i, i2)
    }
}
