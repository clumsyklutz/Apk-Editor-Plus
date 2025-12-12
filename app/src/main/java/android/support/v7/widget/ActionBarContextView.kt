package android.support.v7.widget

import android.content.Context
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import androidx.appcompat.R
import android.support.v7.view.ActionMode
import android.support.v7.view.menu.MenuBuilder
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.LinearLayout
import android.widget.TextView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActionBarContextView extends AbsActionBarView {
    private static val TAG = "ActionBarContextView"
    private View mClose
    private Int mCloseItemLayout
    private View mCustomView
    private CharSequence mSubtitle
    private Int mSubtitleStyleRes
    private TextView mSubtitleView
    private CharSequence mTitle
    private LinearLayout mTitleLayout
    private Boolean mTitleOptional
    private Int mTitleStyleRes
    private TextView mTitleView

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.actionModeStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.ActionMode, i, 0)
        ViewCompat.setBackground(this, tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionMode_background))
        this.mTitleStyleRes = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionMode_titleTextStyle, 0)
        this.mSubtitleStyleRes = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionMode_subtitleTextStyle, 0)
        this.mContentHeight = tintTypedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionMode_height, 0)
        this.mCloseItemLayout = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionMode_closeItemLayout, R.layout.abc_action_mode_close_item_material)
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    private fun initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.abc_action_bar_title_item, this)
            this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1)
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_title)
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_subtitle)
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes)
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes)
            }
        }
        this.mTitleView.setText(this.mTitle)
        this.mSubtitleView.setText(this.mSubtitle)
        Boolean z = !TextUtils.isEmpty(this.mTitle)
        Boolean z2 = TextUtils.isEmpty(this.mSubtitle) ? false : true
        this.mSubtitleView.setVisibility(z2 ? 0 : 8)
        this.mTitleLayout.setVisibility((z || z2) ? 0 : 8)
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout)
        }
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Unit animateToVisibility(Int i) {
        super.animateToVisibility(i)
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Boolean canShowOverflowMenu() {
        return super.canShowOverflowMenu()
    }

    fun closeMode() {
        if (this.mClose == null) {
            killMode()
        }
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Unit dismissPopupMenus() {
        super.dismissPopupMenus()
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2)
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet)
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Int getAnimatedVisibility() {
        return super.getAnimatedVisibility()
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Int getContentHeight() {
        return super.getContentHeight()
    }

    fun getSubtitle() {
        return this.mSubtitle
    }

    fun getTitle() {
        return this.mTitle
    }

    @Override // android.support.v7.widget.AbsActionBarView
    fun hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu()
        }
        return false
    }

    fun initForMode(final ActionMode actionMode) {
        if (this.mClose == null) {
            this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, (ViewGroup) this, false)
            addView(this.mClose)
        } else if (this.mClose.getParent() == null) {
            addView(this.mClose)
        }
        this.mClose.findViewById(R.id.action_mode_close_button).setOnClickListener(new View.OnClickListener() { // from class: android.support.v7.widget.ActionBarContextView.1
            @Override // android.view.View.OnClickListener
            fun onClick(View view) {
                actionMode.finish()
            }
        })
        MenuBuilder menuBuilder = (MenuBuilder) actionMode.getMenu()
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus()
        }
        this.mActionMenuPresenter = ActionMenuPresenter(getContext())
        this.mActionMenuPresenter.setReserveOverflow(true)
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1)
        menuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext)
        this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this)
        ViewCompat.setBackground(this.mMenuView, null)
        addView(this.mMenuView, layoutParams)
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Boolean isOverflowMenuShowPending() {
        return super.isOverflowMenuShowPending()
    }

    @Override // android.support.v7.widget.AbsActionBarView
    fun isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing()
        }
        return false
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Boolean isOverflowReserved() {
        return super.isOverflowReserved()
    }

    fun isTitleOptional() {
        return this.mTitleOptional
    }

    fun killMode() {
        removeAllViews()
        this.mCustomView = null
        this.mMenuView = null
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu()
            this.mActionMenuPresenter.hideSubMenus()
        }
    }

    @Override // android.support.v7.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ Boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent)
    }

    @Override // android.view.View
    fun onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            super.onInitializeAccessibilityEvent(accessibilityEvent)
            return
        }
        accessibilityEvent.setSource(this)
        accessibilityEvent.setClassName(getClass().getName())
        accessibilityEvent.setPackageName(getContext().getPackageName())
        accessibilityEvent.setContentDescription(this.mTitle)
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int iPositionChild
        Boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this)
        Int paddingRight = zIsLayoutRtl ? (i3 - i) - getPaddingRight() : getPaddingLeft()
        Int paddingTop = getPaddingTop()
        Int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom()
        if (this.mClose == null || this.mClose.getVisibility() == 8) {
            iPositionChild = paddingRight
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams()
            Int i5 = zIsLayoutRtl ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin
            Int i6 = zIsLayoutRtl ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin
            Int next = next(paddingRight, i5, zIsLayoutRtl)
            iPositionChild = next(positionChild(this.mClose, next, paddingTop, paddingTop2, zIsLayoutRtl) + next, i6, zIsLayoutRtl)
        }
        if (this.mTitleLayout != null && this.mCustomView == null && this.mTitleLayout.getVisibility() != 8) {
            iPositionChild += positionChild(this.mTitleLayout, iPositionChild, paddingTop, paddingTop2, zIsLayoutRtl)
        }
        if (this.mCustomView != null) {
            positionChild(this.mCustomView, iPositionChild, paddingTop, paddingTop2, zIsLayoutRtl)
        }
        Int paddingLeft = zIsLayoutRtl ? getPaddingLeft() : (i3 - i) - getPaddingRight()
        if (this.mMenuView != null) {
            positionChild(this.mMenuView, paddingLeft, paddingTop, paddingTop2, !zIsLayoutRtl)
        }
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Int i3 = 0
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)")
        }
        if (View.MeasureSpec.getMode(i2) == 0) {
            throw IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"")
        }
        Int size = View.MeasureSpec.getSize(i)
        Int size2 = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(i2)
        Int paddingTop = getPaddingTop() + getPaddingBottom()
        Int paddingLeft = (size - getPaddingLeft()) - getPaddingRight()
        Int i4 = size2 - paddingTop
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE)
        if (this.mClose != null) {
            Int iMeasureChildView = measureChildView(this.mClose, paddingLeft, iMakeMeasureSpec, 0)
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams()
            paddingLeft = iMeasureChildView - (marginLayoutParams.rightMargin + marginLayoutParams.leftMargin)
        }
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            paddingLeft = measureChildView(this.mMenuView, paddingLeft, iMakeMeasureSpec, 0)
        }
        if (this.mTitleLayout != null && this.mCustomView == null) {
            if (this.mTitleOptional) {
                this.mTitleLayout.measure(View.MeasureSpec.makeMeasureSpec(0, 0), iMakeMeasureSpec)
                Int measuredWidth = this.mTitleLayout.getMeasuredWidth()
                Boolean z = measuredWidth <= paddingLeft
                if (z) {
                    paddingLeft -= measuredWidth
                }
                this.mTitleLayout.setVisibility(z ? 0 : 8)
            } else {
                paddingLeft = measureChildView(this.mTitleLayout, paddingLeft, iMakeMeasureSpec, 0)
            }
        }
        if (this.mCustomView != null) {
            ViewGroup.LayoutParams layoutParams = this.mCustomView.getLayoutParams()
            Int i5 = layoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE
            if (layoutParams.width >= 0) {
                paddingLeft = Math.min(layoutParams.width, paddingLeft)
            }
            this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i5), View.MeasureSpec.makeMeasureSpec(layoutParams.height >= 0 ? Math.min(layoutParams.height, i4) : i4, layoutParams.height == -2 ? Integer.MIN_VALUE : 1073741824))
        }
        if (this.mContentHeight > 0) {
            setMeasuredDimension(size, size2)
            return
        }
        Int childCount = getChildCount()
        Int i6 = 0
        while (i3 < childCount) {
            Int measuredHeight = getChildAt(i3).getMeasuredHeight() + paddingTop
            if (measuredHeight <= i6) {
                measuredHeight = i6
            }
            i3++
            i6 = measuredHeight
        }
        setMeasuredDimension(size, i6)
    }

    @Override // android.support.v7.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ Boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent)
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ Unit postShowOverflowMenu() {
        super.postShowOverflowMenu()
    }

    @Override // android.support.v7.widget.AbsActionBarView
    fun setContentHeight(Int i) {
        this.mContentHeight = i
    }

    fun setCustomView(View view) {
        if (this.mCustomView != null) {
            removeView(this.mCustomView)
        }
        this.mCustomView = view
        if (view != null && this.mTitleLayout != null) {
            removeView(this.mTitleLayout)
            this.mTitleLayout = null
        }
        if (view != null) {
            addView(view)
        }
        requestLayout()
    }

    fun setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence
        initTitle()
    }

    fun setTitle(CharSequence charSequence) {
        this.mTitle = charSequence
        initTitle()
    }

    fun setTitleOptional(Boolean z) {
        if (z != this.mTitleOptional) {
            requestLayout()
        }
        this.mTitleOptional = z
    }

    @Override // android.support.v7.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ Unit setVisibility(Int i) {
        super.setVisibility(i)
    }

    @Override // android.support.v7.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ViewPropertyAnimatorCompat setupAnimatorToVisibility(Int i, Long j) {
        return super.setupAnimatorToVisibility(i, j)
    }

    @Override // android.view.ViewGroup
    fun shouldDelayChildPressedState() {
        return false
    }

    @Override // android.support.v7.widget.AbsActionBarView
    fun showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu()
        }
        return false
    }
}
