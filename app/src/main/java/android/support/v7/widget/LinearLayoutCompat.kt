package android.support.v7.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import androidx.core.view.GravityCompat
import androidx.core.view.InputDeviceCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class LinearLayoutCompat extends ViewGroup {
    public static val HORIZONTAL = 0
    private static val INDEX_BOTTOM = 2
    private static val INDEX_CENTER_VERTICAL = 0
    private static val INDEX_FILL = 3
    private static val INDEX_TOP = 1
    public static val SHOW_DIVIDER_BEGINNING = 1
    public static val SHOW_DIVIDER_END = 4
    public static val SHOW_DIVIDER_MIDDLE = 2
    public static val SHOW_DIVIDER_NONE = 0
    public static val VERTICAL = 1
    private static val VERTICAL_GRAVITY_COUNT = 4
    private Boolean mBaselineAligned
    private Int mBaselineAlignedChildIndex
    private Int mBaselineChildTop
    private Drawable mDivider
    private Int mDividerHeight
    private Int mDividerPadding
    private Int mDividerWidth
    private Int mGravity
    private Array<Int> mMaxAscent
    private Array<Int> mMaxDescent
    private Int mOrientation
    private Int mShowDividers
    private Int mTotalLength
    private Boolean mUseLargestChild
    private Float mWeightSum

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface DividerMode {
    }

    class LayoutParams extends ViewGroup.MarginLayoutParams {
        public Int gravity
        public Float weight

        constructor(Int i, Int i2) {
            super(i, i2)
            this.gravity = -1
            this.weight = 0.0f
        }

        constructor(Int i, Int i2, Float f) {
            super(i, i2)
            this.gravity = -1
            this.weight = f
        }

        constructor(Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
            this.gravity = -1
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout)
            this.weight = typedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f)
            this.gravity = typedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1)
            typedArrayObtainStyledAttributes.recycle()
        }

        constructor(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams)
            this.gravity = -1
            this.weight = layoutParams.weight
            this.gravity = layoutParams.gravity
        }

        constructor(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
            this.gravity = -1
        }

        constructor(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams)
            this.gravity = -1
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface OrientationMode {
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mBaselineAligned = true
        this.mBaselineAlignedChildIndex = -1
        this.mBaselineChildTop = 0
        this.mGravity = 8388659
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, i, 0)
        Int i2 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1)
        if (i2 >= 0) {
            setOrientation(i2)
        }
        Int i3 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1)
        if (i3 >= 0) {
            setGravity(i3)
        }
        Boolean z = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true)
        if (!z) {
            setBaselineAligned(z)
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f)
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1)
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false)
        setDividerDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider))
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0)
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0)
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    private fun forceUniformHeight(Int i, Int i2) {
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824)
        for (Int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3)
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                if (layoutParams.height == -1) {
                    Int i4 = layoutParams.width
                    layoutParams.width = virtualChildAt.getMeasuredWidth()
                    measureChildWithMargins(virtualChildAt, i2, 0, iMakeMeasureSpec, 0)
                    layoutParams.width = i4
                }
            }
        }
    }

    private fun forceUniformWidth(Int i, Int i2) {
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824)
        for (Int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3)
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                if (layoutParams.width == -1) {
                    Int i4 = layoutParams.height
                    layoutParams.height = virtualChildAt.getMeasuredHeight()
                    measureChildWithMargins(virtualChildAt, iMakeMeasureSpec, 0, i2, 0)
                    layoutParams.height = i4
                }
            }
        }
    }

    private fun setChildFrame(View view, Int i, Int i2, Int i3, Int i4) {
        view.layout(i, i2, i + i3, i2 + i4)
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams is LayoutParams
    }

    Unit drawDividersHorizontal(Canvas canvas) {
        Int left
        Int virtualChildCount = getVirtualChildCount()
        Boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this)
        for (Int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i)
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                drawVerticalDivider(canvas, zIsLayoutRtl ? layoutParams.rightMargin + virtualChildAt.getRight() : (virtualChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerWidth)
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1)
            if (virtualChildAt2 == null) {
                left = zIsLayoutRtl ? getPaddingLeft() : (getWidth() - getPaddingRight()) - this.mDividerWidth
            } else {
                LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams()
                left = zIsLayoutRtl ? (virtualChildAt2.getLeft() - layoutParams2.leftMargin) - this.mDividerWidth : layoutParams2.rightMargin + virtualChildAt2.getRight()
            }
            drawVerticalDivider(canvas, left)
        }
    }

    Unit drawDividersVertical(Canvas canvas) {
        Int virtualChildCount = getVirtualChildCount()
        for (Int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i)
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((LayoutParams) virtualChildAt.getLayoutParams()).topMargin) - this.mDividerHeight)
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1)
            drawHorizontalDivider(canvas, virtualChildAt2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : ((LayoutParams) virtualChildAt2.getLayoutParams()).bottomMargin + virtualChildAt2.getBottom())
        }
    }

    Unit drawHorizontalDivider(Canvas canvas, Int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i)
        this.mDivider.draw(canvas)
    }

    Unit drawVerticalDivider(Canvas canvas, Int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding)
        this.mDivider.draw(canvas)
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return LayoutParams(-2, -2)
        }
        if (this.mOrientation == 1) {
            return LayoutParams(-1, -2)
        }
        return null
    }

    @Override // android.view.ViewGroup
    fun generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return LayoutParams(layoutParams)
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0044  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun getBaseline() {
        /*
            r5 = this
            r0 = -1
            Int r1 = r5.mBaselineAlignedChildIndex
            if (r1 >= 0) goto La
            Int r0 = super.getBaseline()
        L9:
            return r0
        La:
            Int r1 = r5.getChildCount()
            Int r2 = r5.mBaselineAlignedChildIndex
            if (r1 > r2) goto L1a
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds."
            r0.<init>(r1)
            throw r0
        L1a:
            Int r1 = r5.mBaselineAlignedChildIndex
            android.view.View r2 = r5.getChildAt(r1)
            Int r3 = r2.getBaseline()
            if (r3 != r0) goto L32
            Int r1 = r5.mBaselineAlignedChildIndex
            if (r1 == 0) goto L9
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline."
            r0.<init>(r1)
            throw r0
        L32:
            Int r0 = r5.mBaselineChildTop
            Int r1 = r5.mOrientation
            r4 = 1
            if (r1 != r4) goto L44
            Int r1 = r5.mGravity
            r1 = r1 & 112(0x70, Float:1.57E-43)
            r4 = 48
            if (r1 == r4) goto L44
            switch(r1) {
                case 16: goto L63
                case 80: goto L50
                default: goto L44
            }
        L44:
            r1 = r0
        L45:
            android.view.ViewGroup$LayoutParams r0 = r2.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r0 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r0
            Int r0 = r0.topMargin
            Int r0 = r0 + r1
            Int r0 = r0 + r3
            goto L9
        L50:
            Int r0 = r5.getBottom()
            Int r1 = r5.getTop()
            Int r0 = r0 - r1
            Int r1 = r5.getPaddingBottom()
            Int r0 = r0 - r1
            Int r1 = r5.mTotalLength
            Int r0 = r0 - r1
            r1 = r0
            goto L45
        L63:
            Int r1 = r5.getBottom()
            Int r4 = r5.getTop()
            Int r1 = r1 - r4
            Int r4 = r5.getPaddingTop()
            Int r1 = r1 - r4
            Int r4 = r5.getPaddingBottom()
            Int r1 = r1 - r4
            Int r4 = r5.mTotalLength
            Int r1 = r1 - r4
            Int r1 = r1 / 2
            Int r0 = r0 + r1
            r1 = r0
            goto L45
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutCompat.getBaseline():Int")
    }

    fun getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex
    }

    Int getChildrenSkipCount(View view, Int i) {
        return 0
    }

    fun getDividerDrawable() {
        return this.mDivider
    }

    fun getDividerPadding() {
        return this.mDividerPadding
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getDividerWidth() {
        return this.mDividerWidth
    }

    fun getGravity() {
        return this.mGravity
    }

    Int getLocationOffset(View view) {
        return 0
    }

    Int getNextLocationOffset(View view) {
        return 0
    }

    fun getOrientation() {
        return this.mOrientation
    }

    fun getShowDividers() {
        return this.mShowDividers
    }

    View getVirtualChildAt(Int i) {
        return getChildAt(i)
    }

    Int getVirtualChildCount() {
        return getChildCount()
    }

    fun getWeightSum() {
        return this.mWeightSum
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    protected fun hasDividerBeforeChildAt(Int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0
        }
        if ((this.mShowDividers & 2) == 0) {
            return false
        }
        for (Int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true
            }
        }
        return false
    }

    fun isBaselineAligned() {
        return this.mBaselineAligned
    }

    fun isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild
    }

    Unit layoutHorizontal(Int i, Int i2, Int i3, Int i4) {
        Int paddingLeft
        Int i5
        Int i6
        Int childrenSkipCount
        Int measuredHeight
        Boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this)
        Int paddingTop = getPaddingTop()
        Int i7 = i4 - i2
        Int paddingBottom = i7 - getPaddingBottom()
        Int paddingBottom2 = (i7 - paddingTop) - getPaddingBottom()
        Int virtualChildCount = getVirtualChildCount()
        Int i8 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK
        Int i9 = this.mGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall
        Boolean z = this.mBaselineAligned
        Array<Int> iArr = this.mMaxAscent
        Array<Int> iArr2 = this.mMaxDescent
        switch (GravityCompat.getAbsoluteGravity(i8, ViewCompat.getLayoutDirection(this))) {
            case 1:
                paddingLeft = getPaddingLeft() + (((i3 - i) - this.mTotalLength) / 2)
                break
            case 5:
                paddingLeft = ((getPaddingLeft() + i3) - i) - this.mTotalLength
                break
            default:
                paddingLeft = getPaddingLeft()
                break
        }
        if (zIsLayoutRtl) {
            i5 = -1
            i6 = virtualChildCount - 1
        } else {
            i5 = 1
            i6 = 0
        }
        Int i10 = 0
        while (i10 < virtualChildCount) {
            Int i11 = i6 + (i5 * i10)
            View virtualChildAt = getVirtualChildAt(i11)
            if (virtualChildAt == null) {
                paddingLeft += measureNullChild(i11)
                childrenSkipCount = i10
            } else if (virtualChildAt.getVisibility() != 8) {
                Int measuredWidth = virtualChildAt.getMeasuredWidth()
                Int measuredHeight2 = virtualChildAt.getMeasuredHeight()
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                Int baseline = (!z || layoutParams.height == -1) ? -1 : virtualChildAt.getBaseline()
                Int i12 = layoutParams.gravity
                if (i12 < 0) {
                    i12 = i9
                }
                switch (i12 & R.styleable.AppCompatTheme_ratingBarStyleSmall) {
                    case 16:
                        measuredHeight = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + layoutParams.topMargin) - layoutParams.bottomMargin
                        break
                    case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                        measuredHeight = paddingTop + layoutParams.topMargin
                        if (baseline != -1) {
                            measuredHeight += iArr[1] - baseline
                            break
                        }
                        break
                    case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                        measuredHeight = (paddingBottom - measuredHeight2) - layoutParams.bottomMargin
                        if (baseline != -1) {
                            measuredHeight -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - baseline)
                            break
                        }
                        break
                    default:
                        measuredHeight = paddingTop
                        break
                }
                Int i13 = (hasDividerBeforeChildAt(i11) ? this.mDividerWidth + paddingLeft : paddingLeft) + layoutParams.leftMargin
                setChildFrame(virtualChildAt, i13 + getLocationOffset(virtualChildAt), measuredHeight, measuredWidth, measuredHeight2)
                paddingLeft = i13 + layoutParams.rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt)
                childrenSkipCount = getChildrenSkipCount(virtualChildAt, i11) + i10
            } else {
                childrenSkipCount = i10
            }
            i10 = childrenSkipCount + 1
        }
    }

    Unit layoutVertical(Int i, Int i2, Int i3, Int i4) {
        Int paddingTop
        Int childrenSkipCount
        Int i5
        Int paddingLeft = getPaddingLeft()
        Int i6 = i3 - i
        Int paddingRight = i6 - getPaddingRight()
        Int paddingRight2 = (i6 - paddingLeft) - getPaddingRight()
        Int virtualChildCount = getVirtualChildCount()
        Int i7 = this.mGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall
        Int i8 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK
        switch (i7) {
            case 16:
                paddingTop = getPaddingTop() + (((i4 - i2) - this.mTotalLength) / 2)
                break
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                paddingTop = ((getPaddingTop() + i4) - i2) - this.mTotalLength
                break
            default:
                paddingTop = getPaddingTop()
                break
        }
        Int i9 = 0
        Int nextLocationOffset = paddingTop
        while (i9 < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(i9)
            if (virtualChildAt == null) {
                nextLocationOffset += measureNullChild(i9)
                childrenSkipCount = i9
            } else if (virtualChildAt.getVisibility() != 8) {
                Int measuredWidth = virtualChildAt.getMeasuredWidth()
                Int measuredHeight = virtualChildAt.getMeasuredHeight()
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                Int i10 = layoutParams.gravity
                if (i10 < 0) {
                    i10 = i8
                }
                switch (GravityCompat.getAbsoluteGravity(i10, ViewCompat.getLayoutDirection(this)) & 7) {
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
                Int i11 = (hasDividerBeforeChildAt(i9) ? this.mDividerHeight + nextLocationOffset : nextLocationOffset) + layoutParams.topMargin
                setChildFrame(virtualChildAt, i5, i11 + getLocationOffset(virtualChildAt), measuredWidth, measuredHeight)
                nextLocationOffset = i11 + layoutParams.bottomMargin + measuredHeight + getNextLocationOffset(virtualChildAt)
                childrenSkipCount = getChildrenSkipCount(virtualChildAt, i9) + i9
            } else {
                childrenSkipCount = i9
            }
            i9 = childrenSkipCount + 1
        }
    }

    Unit measureChildBeforeLayout(View view, Int i, Int i2, Int i3, Int i4, Int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5)
    }

    Unit measureHorizontal(Int i, Int i2) {
        Int i3
        Int i4
        Float f
        Int i5
        Int i6
        Boolean z
        Int i7
        Int i8
        Int i9
        Int iCombineMeasuredStates
        Float f2
        Int baseline
        View view
        Int childrenSkipCount
        Int i10
        Float f3
        Boolean z2
        Int iMax
        Int i11
        Boolean z3
        Int i12
        Int i13
        Boolean z4
        this.mTotalLength = 0
        Int i14 = 0
        Int i15 = 0
        Int i16 = 0
        Int i17 = 0
        Boolean z5 = true
        Float f4 = 0.0f
        Int virtualChildCount = getVirtualChildCount()
        Int mode = View.MeasureSpec.getMode(i)
        Int mode2 = View.MeasureSpec.getMode(i2)
        Boolean z6 = false
        Boolean z7 = false
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new Int[4]
            this.mMaxDescent = new Int[4]
        }
        Array<Int> iArr = this.mMaxAscent
        Array<Int> iArr2 = this.mMaxDescent
        iArr[3] = -1
        iArr[2] = -1
        iArr[1] = -1
        iArr[0] = -1
        iArr2[3] = -1
        iArr2[2] = -1
        iArr2[1] = -1
        iArr2[0] = -1
        Boolean z8 = this.mBaselineAligned
        Boolean z9 = this.mUseLargestChild
        Boolean z10 = mode == 1073741824
        Int iMax2 = 0
        Int childrenSkipCount2 = 0
        while (childrenSkipCount2 < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(childrenSkipCount2)
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount2)
            } else {
                if (virtualChildAt.getVisibility() != 8) {
                    if (hasDividerBeforeChildAt(childrenSkipCount2)) {
                        this.mTotalLength += this.mDividerWidth
                    }
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                    Float f5 = f4 + layoutParams.weight
                    if (mode == 1073741824 && layoutParams.width == 0 && layoutParams.weight > 0.0f) {
                        if (z10) {
                            this.mTotalLength += layoutParams.leftMargin + layoutParams.rightMargin
                        } else {
                            Int i18 = this.mTotalLength
                            this.mTotalLength = Math.max(i18, layoutParams.leftMargin + i18 + layoutParams.rightMargin)
                        }
                        if (z8) {
                            Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0)
                            virtualChildAt.measure(iMakeMeasureSpec, iMakeMeasureSpec)
                        } else {
                            z7 = true
                        }
                    } else {
                        Int i19 = Integer.MIN_VALUE
                        if (layoutParams.width == 0 && layoutParams.weight > 0.0f) {
                            i19 = 0
                            layoutParams.width = -2
                        }
                        Int i20 = i19
                        measureChildBeforeLayout(virtualChildAt, childrenSkipCount2, i, f5 == 0.0f ? this.mTotalLength : 0, i2, 0)
                        if (i20 != Integer.MIN_VALUE) {
                            layoutParams.width = i20
                        }
                        Int measuredWidth = virtualChildAt.getMeasuredWidth()
                        if (z10) {
                            this.mTotalLength += layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(virtualChildAt)
                        } else {
                            Int i21 = this.mTotalLength
                            this.mTotalLength = Math.max(i21, i21 + measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin + getNextLocationOffset(virtualChildAt))
                        }
                        if (z9) {
                            iMax2 = Math.max(measuredWidth, iMax2)
                        }
                    }
                    Boolean z11 = false
                    if (mode2 == 1073741824 || layoutParams.height != -1) {
                        z4 = z6
                    } else {
                        z4 = true
                        z11 = true
                    }
                    Int i22 = layoutParams.bottomMargin + layoutParams.topMargin
                    Int measuredHeight = virtualChildAt.getMeasuredHeight() + i22
                    Int iCombineMeasuredStates2 = View.combineMeasuredStates(i15, virtualChildAt.getMeasuredState())
                    if (z8) {
                        Int baseline2 = virtualChildAt.getBaseline()
                        if (baseline2 != -1) {
                            Int i23 = ((((layoutParams.gravity < 0 ? this.mGravity : layoutParams.gravity) & R.styleable.AppCompatTheme_ratingBarStyleSmall) >> 4) & (-2)) >> 1
                            iArr[i23] = Math.max(iArr[i23], baseline2)
                            iArr2[i23] = Math.max(iArr2[i23], measuredHeight - baseline2)
                        }
                    }
                    Int iMax3 = Math.max(i14, measuredHeight)
                    Boolean z12 = z5 && layoutParams.height == -1
                    if (layoutParams.weight > 0.0f) {
                        Int i24 = z11 ? i22 : measuredHeight
                        z2 = z12
                        iMax = Math.max(i17, i24)
                        i11 = i16
                        f3 = f5
                        i10 = iMax2
                        i12 = iCombineMeasuredStates2
                        z6 = z4
                        z3 = z7
                        i13 = iMax3
                    } else {
                        if (!z11) {
                            i22 = measuredHeight
                        }
                        Int iMax4 = Math.max(i16, i22)
                        z2 = z12
                        iMax = i17
                        i11 = iMax4
                        f3 = f5
                        i10 = iMax2
                        i12 = iCombineMeasuredStates2
                        z6 = z4
                        z3 = z7
                        i13 = iMax3
                    }
                } else {
                    i10 = iMax2
                    f3 = f4
                    z2 = z5
                    iMax = i17
                    i11 = i16
                    z3 = z7
                    i12 = i15
                    i13 = i14
                }
                childrenSkipCount2 += getChildrenSkipCount(virtualChildAt, childrenSkipCount2)
                z5 = z2
                i17 = iMax
                i16 = i11
                i15 = i12
                i14 = i13
                iMax2 = i10
                z7 = z3
                f4 = f3
            }
            childrenSkipCount2++
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth
        }
        Int iMax5 = (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1 && iArr[3] == -1) ? i14 : Math.max(i14, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))))
        if (z9 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0
            Int i25 = 0
            while (i25 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(i25)
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(i25)
                    childrenSkipCount = i25
                } else if (virtualChildAt2.getVisibility() == 8) {
                    childrenSkipCount = getChildrenSkipCount(virtualChildAt2, i25) + i25
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams()
                    if (z10) {
                        this.mTotalLength = layoutParams2.rightMargin + layoutParams2.leftMargin + iMax2 + getNextLocationOffset(virtualChildAt2) + this.mTotalLength
                        childrenSkipCount = i25
                    } else {
                        Int i26 = this.mTotalLength
                        this.mTotalLength = Math.max(i26, layoutParams2.rightMargin + i26 + iMax2 + layoutParams2.leftMargin + getNextLocationOffset(virtualChildAt2))
                        childrenSkipCount = i25
                    }
                }
                i25 = childrenSkipCount + 1
            }
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight()
        Int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), i, 0)
        Int i27 = (16777215 & iResolveSizeAndState) - this.mTotalLength
        if (z7 || (i27 != 0 && f4 > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f4 = this.mWeightSum
            }
            iArr[3] = -1
            iArr[2] = -1
            iArr[1] = -1
            iArr[0] = -1
            iArr2[3] = -1
            iArr2[2] = -1
            iArr2[1] = -1
            iArr2[0] = -1
            Int iMax6 = -1
            this.mTotalLength = 0
            Float f6 = f4
            Boolean z13 = z5
            Int i28 = i16
            Int i29 = i15
            Int i30 = 0
            while (i30 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i30)
                if (virtualChildAt3 == null || virtualChildAt3.getVisibility() == 8) {
                    f = f6
                    i5 = i27
                    i6 = i28
                    z = z13
                    i7 = i29
                    i8 = iMax6
                } else {
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt3.getLayoutParams()
                    Float f7 = layoutParams3.weight
                    if (f7 > 0.0f) {
                        Int measuredWidth2 = (Int) ((i27 * f7) / f6)
                        Float f8 = f6 - f7
                        Int i31 = i27 - measuredWidth2
                        Int childMeasureSpec = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + layoutParams3.topMargin + layoutParams3.bottomMargin, layoutParams3.height)
                        if (layoutParams3.width != 0 || mode != 1073741824) {
                            measuredWidth2 += virtualChildAt3.getMeasuredWidth()
                            if (measuredWidth2 < 0) {
                                measuredWidth2 = 0
                            }
                            view = virtualChildAt3
                        } else if (measuredWidth2 > 0) {
                            view = virtualChildAt3
                        } else {
                            measuredWidth2 = 0
                            view = virtualChildAt3
                        }
                        view.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824), childMeasureSpec)
                        iCombineMeasuredStates = View.combineMeasuredStates(i29, virtualChildAt3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK)
                        f2 = f8
                        i9 = i31
                    } else {
                        i9 = i27
                        iCombineMeasuredStates = i29
                        f2 = f6
                    }
                    if (z10) {
                        this.mTotalLength += virtualChildAt3.getMeasuredWidth() + layoutParams3.leftMargin + layoutParams3.rightMargin + getNextLocationOffset(virtualChildAt3)
                    } else {
                        Int i32 = this.mTotalLength
                        this.mTotalLength = Math.max(i32, virtualChildAt3.getMeasuredWidth() + i32 + layoutParams3.leftMargin + layoutParams3.rightMargin + getNextLocationOffset(virtualChildAt3))
                    }
                    Boolean z14 = mode2 != 1073741824 && layoutParams3.height == -1
                    Int i33 = layoutParams3.topMargin + layoutParams3.bottomMargin
                    Int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i33
                    Int iMax7 = Math.max(iMax6, measuredHeight2)
                    Int iMax8 = Math.max(i28, z14 ? i33 : measuredHeight2)
                    Boolean z15 = z13 && layoutParams3.height == -1
                    if (z8 && (baseline = virtualChildAt3.getBaseline()) != -1) {
                        Int i34 = ((((layoutParams3.gravity < 0 ? this.mGravity : layoutParams3.gravity) & R.styleable.AppCompatTheme_ratingBarStyleSmall) >> 4) & (-2)) >> 1
                        iArr[i34] = Math.max(iArr[i34], baseline)
                        iArr2[i34] = Math.max(iArr2[i34], measuredHeight2 - baseline)
                    }
                    f = f2
                    i6 = iMax8
                    i7 = iCombineMeasuredStates
                    z = z15
                    i8 = iMax7
                    i5 = i9
                }
                i30++
                z13 = z
                i28 = i6
                iMax6 = i8
                i29 = i7
                f6 = f
                i27 = i5
            }
            this.mTotalLength += getPaddingLeft() + getPaddingRight()
            if (iArr[1] != -1 || iArr[0] != -1 || iArr[2] != -1 || iArr[3] != -1) {
                iMax6 = Math.max(iMax6, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))))
            }
            z5 = z13
            i3 = i28
            i15 = i29
            i4 = iMax6
        } else {
            Int iMax9 = Math.max(i16, i17)
            if (z9 && mode != 1073741824) {
                Int i35 = 0
                while (true) {
                    Int i36 = i35
                    if (i36 >= virtualChildCount) {
                        break
                    }
                    View virtualChildAt4 = getVirtualChildAt(i36)
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(iMax2, 1073741824), View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824))
                    }
                    i35 = i36 + 1
                }
            }
            i3 = iMax9
            i4 = iMax5
        }
        if (z5 || mode2 == 1073741824) {
            i3 = i4
        }
        setMeasuredDimension(((-16777216) & i15) | iResolveSizeAndState, View.resolveSizeAndState(Math.max(i3 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, i15 << 16))
        if (z6) {
            forceUniformHeight(virtualChildCount, i)
        }
    }

    Int measureNullChild(Int i) {
        return 0
    }

    Unit measureVertical(Int i, Int i2) {
        Int i3
        Int i4
        Float f
        Boolean z
        Int i5
        Int i6
        Int i7
        Int iCombineMeasuredStates
        View view
        Int childrenSkipCount
        Int i8
        Float f2
        Boolean z2
        Int iMax
        Int i9
        Boolean z3
        Int i10
        Int i11
        Boolean z4
        this.mTotalLength = 0
        Int i12 = 0
        Int i13 = 0
        Int i14 = 0
        Int i15 = 0
        Boolean z5 = true
        Float f3 = 0.0f
        Int virtualChildCount = getVirtualChildCount()
        Int mode = View.MeasureSpec.getMode(i)
        Int mode2 = View.MeasureSpec.getMode(i2)
        Boolean z6 = false
        Boolean z7 = false
        Int i16 = this.mBaselineAlignedChildIndex
        Boolean z8 = this.mUseLargestChild
        Int iMax2 = 0
        Int childrenSkipCount2 = 0
        while (childrenSkipCount2 < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(childrenSkipCount2)
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount2)
            } else {
                if (virtualChildAt.getVisibility() != 8) {
                    if (hasDividerBeforeChildAt(childrenSkipCount2)) {
                        this.mTotalLength += this.mDividerHeight
                    }
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams()
                    Float f4 = f3 + layoutParams.weight
                    if (mode2 == 1073741824 && layoutParams.height == 0 && layoutParams.weight > 0.0f) {
                        Int i17 = this.mTotalLength
                        this.mTotalLength = Math.max(i17, layoutParams.topMargin + i17 + layoutParams.bottomMargin)
                        z7 = true
                    } else {
                        Int i18 = Integer.MIN_VALUE
                        if (layoutParams.height == 0 && layoutParams.weight > 0.0f) {
                            i18 = 0
                            layoutParams.height = -2
                        }
                        Int i19 = i18
                        measureChildBeforeLayout(virtualChildAt, childrenSkipCount2, i, 0, i2, f4 == 0.0f ? this.mTotalLength : 0)
                        if (i19 != Integer.MIN_VALUE) {
                            layoutParams.height = i19
                        }
                        Int measuredHeight = virtualChildAt.getMeasuredHeight()
                        Int i20 = this.mTotalLength
                        this.mTotalLength = Math.max(i20, i20 + measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(virtualChildAt))
                        if (z8) {
                            iMax2 = Math.max(measuredHeight, iMax2)
                        }
                    }
                    if (i16 >= 0 && i16 == childrenSkipCount2 + 1) {
                        this.mBaselineChildTop = this.mTotalLength
                    }
                    if (childrenSkipCount2 < i16 && layoutParams.weight > 0.0f) {
                        throw RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.")
                    }
                    Boolean z9 = false
                    if (mode == 1073741824 || layoutParams.width != -1) {
                        z4 = z6
                    } else {
                        z4 = true
                        z9 = true
                    }
                    Int i21 = layoutParams.rightMargin + layoutParams.leftMargin
                    Int measuredWidth = virtualChildAt.getMeasuredWidth() + i21
                    Int iMax3 = Math.max(i12, measuredWidth)
                    Int iCombineMeasuredStates2 = View.combineMeasuredStates(i13, virtualChildAt.getMeasuredState())
                    Boolean z10 = z5 && layoutParams.width == -1
                    if (layoutParams.weight > 0.0f) {
                        Int i22 = z9 ? i21 : measuredWidth
                        z2 = z10
                        iMax = Math.max(i15, i22)
                        i9 = i14
                        f2 = f4
                        i8 = iMax2
                        i10 = iCombineMeasuredStates2
                        z6 = z4
                        z3 = z7
                        i11 = iMax3
                    } else {
                        if (!z9) {
                            i21 = measuredWidth
                        }
                        Int iMax4 = Math.max(i14, i21)
                        z2 = z10
                        iMax = i15
                        i9 = iMax4
                        f2 = f4
                        i8 = iMax2
                        i10 = iCombineMeasuredStates2
                        z6 = z4
                        z3 = z7
                        i11 = iMax3
                    }
                } else {
                    i8 = iMax2
                    f2 = f3
                    z2 = z5
                    iMax = i15
                    i9 = i14
                    z3 = z7
                    i10 = i13
                    i11 = i12
                }
                childrenSkipCount2 += getChildrenSkipCount(virtualChildAt, childrenSkipCount2)
                z5 = z2
                i15 = iMax
                i14 = i9
                i13 = i10
                i12 = i11
                iMax2 = i8
                z7 = z3
                f3 = f2
            }
            childrenSkipCount2++
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerHeight
        }
        if (z8 && (mode2 == Integer.MIN_VALUE || mode2 == 0)) {
            this.mTotalLength = 0
            Int i23 = 0
            while (i23 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(i23)
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(i23)
                    childrenSkipCount = i23
                } else if (virtualChildAt2.getVisibility() == 8) {
                    childrenSkipCount = getChildrenSkipCount(virtualChildAt2, i23) + i23
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams()
                    Int i24 = this.mTotalLength
                    this.mTotalLength = Math.max(i24, layoutParams2.bottomMargin + i24 + iMax2 + layoutParams2.topMargin + getNextLocationOffset(virtualChildAt2))
                    childrenSkipCount = i23
                }
                i23 = childrenSkipCount + 1
            }
        }
        this.mTotalLength += getPaddingTop() + getPaddingBottom()
        Int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), i2, 0)
        Int i25 = (16777215 & iResolveSizeAndState) - this.mTotalLength
        if (z7 || (i25 != 0 && f3 > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f3 = this.mWeightSum
            }
            this.mTotalLength = 0
            Boolean z11 = z5
            Int i26 = i14
            Int i27 = i13
            Int i28 = i12
            Int i29 = 0
            Float f5 = f3
            Int i30 = i25
            while (i29 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i29)
                if (virtualChildAt3.getVisibility() != 8) {
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt3.getLayoutParams()
                    Float f6 = layoutParams3.weight
                    if (f6 > 0.0f) {
                        Int measuredHeight2 = (Int) ((i30 * f6) / f5)
                        Float f7 = f5 - f6
                        Int i31 = i30 - measuredHeight2
                        Int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + layoutParams3.leftMargin + layoutParams3.rightMargin, layoutParams3.width)
                        if (layoutParams3.height != 0 || mode2 != 1073741824) {
                            measuredHeight2 += virtualChildAt3.getMeasuredHeight()
                            if (measuredHeight2 < 0) {
                                measuredHeight2 = 0
                            }
                            view = virtualChildAt3
                        } else if (measuredHeight2 > 0) {
                            view = virtualChildAt3
                        } else {
                            measuredHeight2 = 0
                            view = virtualChildAt3
                        }
                        view.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824))
                        i7 = i31
                        iCombineMeasuredStates = View.combineMeasuredStates(i27, virtualChildAt3.getMeasuredState() & InputDeviceCompat.SOURCE_ANY)
                        f = f7
                    } else {
                        f = f5
                        i7 = i30
                        iCombineMeasuredStates = i27
                    }
                    Int i32 = layoutParams3.leftMargin + layoutParams3.rightMargin
                    Int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i32
                    Int iMax5 = Math.max(i28, measuredWidth2)
                    if (!(mode != 1073741824 && layoutParams3.width == -1)) {
                        i32 = measuredWidth2
                    }
                    Int iMax6 = Math.max(i26, i32)
                    z = z11 && layoutParams3.width == -1
                    Int i33 = this.mTotalLength
                    this.mTotalLength = Math.max(i33, layoutParams3.bottomMargin + virtualChildAt3.getMeasuredHeight() + i33 + layoutParams3.topMargin + getNextLocationOffset(virtualChildAt3))
                    i5 = iMax6
                    i6 = iMax5
                } else {
                    f = f5
                    z = z11
                    i5 = i26
                    i6 = i28
                    i7 = i30
                    iCombineMeasuredStates = i27
                }
                i29++
                z11 = z
                i26 = i5
                i27 = iCombineMeasuredStates
                i28 = i6
                i30 = i7
                f5 = f
            }
            this.mTotalLength += getPaddingTop() + getPaddingBottom()
            z5 = z11
            i3 = i26
            i13 = i27
            i4 = i28
        } else {
            Int iMax7 = Math.max(i14, i15)
            if (z8 && mode2 != 1073741824) {
                Int i34 = 0
                while (true) {
                    Int i35 = i34
                    if (i35 >= virtualChildCount) {
                        break
                    }
                    View virtualChildAt4 = getVirtualChildAt(i35)
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(iMax2, 1073741824))
                    }
                    i34 = i35 + 1
                }
            }
            i3 = iMax7
            i4 = i12
        }
        if (z5 || mode == 1073741824) {
            i3 = i4
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(i3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, i13), iResolveSizeAndState)
        if (z6) {
            forceUniformWidth(virtualChildCount, i2)
        }
    }

    @Override // android.view.View
    protected fun onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas)
        } else {
            drawDividersHorizontal(canvas)
        }
    }

    @Override // android.view.View
    fun onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent)
        accessibilityEvent.setClassName(LinearLayoutCompat.class.getName())
    }

    @Override // android.view.View
    fun onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo)
        accessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName())
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4)
        } else {
            layoutHorizontal(i, i2, i3, i4)
        }
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2)
        } else {
            measureHorizontal(i, i2)
        }
    }

    fun setBaselineAligned(Boolean z) {
        this.mBaselineAligned = z
    }

    fun setBaselineAlignedChildIndex(Int i) {
        if (i < 0 || i >= getChildCount()) {
            throw IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")")
        }
        this.mBaselineAlignedChildIndex = i
    }

    fun setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return
        }
        this.mDivider = drawable
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth()
            this.mDividerHeight = drawable.getIntrinsicHeight()
        } else {
            this.mDividerWidth = 0
            this.mDividerHeight = 0
        }
        setWillNotDraw(drawable == null)
        requestLayout()
    }

    fun setDividerPadding(Int i) {
        this.mDividerPadding = i
    }

    fun setGravity(Int i) {
        if (this.mGravity != i) {
            Int i2 = (8388615 & i) == 0 ? 8388611 | i : i
            if ((i2 & R.styleable.AppCompatTheme_ratingBarStyleSmall) == 0) {
                i2 |= 48
            }
            this.mGravity = i2
            requestLayout()
        }
    }

    fun setHorizontalGravity(Int i) {
        Int i2 = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK
        if ((this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) != i2) {
            this.mGravity = i2 | (this.mGravity & (-8388616))
            requestLayout()
        }
    }

    fun setMeasureWithLargestChildEnabled(Boolean z) {
        this.mUseLargestChild = z
    }

    fun setOrientation(Int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i
            requestLayout()
        }
    }

    fun setShowDividers(Int i) {
        if (i != this.mShowDividers) {
            requestLayout()
        }
        this.mShowDividers = i
    }

    fun setVerticalGravity(Int i) {
        Int i2 = i & R.styleable.AppCompatTheme_ratingBarStyleSmall
        if ((this.mGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall) != i2) {
            this.mGravity = i2 | (this.mGravity & (-113))
            requestLayout()
        }
    }

    fun setWeightSum(Float f) {
        this.mWeightSum = Math.max(0.0f, f)
    }

    @Override // android.view.ViewGroup
    fun shouldDelayChildPressedState() {
        return false
    }
}
