package android.support.v7.widget

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StyleRes
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewDebug
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent

class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    static val GENERATED_ITEM_PADDING = 4
    static val MIN_CELL_SIZE = 56
    private static val TAG = "ActionMenuView"
    private MenuPresenter.Callback mActionMenuPresenterCallback
    private Boolean mFormatItems
    private Int mFormatItemsWidth
    private Int mGeneratedItemPadding
    private MenuBuilder mMenu
    MenuBuilder.Callback mMenuBuilderCallback
    private Int mMinCellSize
    OnMenuItemClickListener mOnMenuItemClickListener
    private Context mPopupContext
    private Int mPopupTheme
    private ActionMenuPresenter mPresenter
    private Boolean mReserveOverflow

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface ActionMenuChildView {
        Boolean needsDividerAfter()

        Boolean needsDividerBefore()
    }

    class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        fun onOpenSubMenu(MenuBuilder menuBuilder) {
            return false
        }
    }

    class LayoutParams extends LinearLayoutCompat.LayoutParams {

        @ViewDebug.ExportedProperty
        public Int cellsUsed

        @ViewDebug.ExportedProperty
        public Boolean expandable
        Boolean expanded

        @ViewDebug.ExportedProperty
        public Int extraPixels

        @ViewDebug.ExportedProperty
        public Boolean isOverflowButton

        @ViewDebug.ExportedProperty
        public Boolean preventEdgeOffset

        constructor(Int i, Int i2) {
            super(i, i2)
            this.isOverflowButton = false
        }

        LayoutParams(Int i, Int i2, Boolean z) {
            super(i, i2)
            this.isOverflowButton = z
        }

        constructor(Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
        }

        constructor(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams)
            this.isOverflowButton = layoutParams.isOverflowButton
        }

        constructor(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
        }
    }

    class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        fun onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem)
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        fun onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder)
            }
        }
    }

    public interface OnMenuItemClickListener {
        Boolean onMenuItemClick(MenuItem menuItem)
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        setBaselineAligned(false)
        Float f = context.getResources().getDisplayMetrics().density
        this.mMinCellSize = (Int) (56.0f * f)
        this.mGeneratedItemPadding = (Int) (f * 4.0f)
        this.mPopupContext = context
        this.mPopupTheme = 0
    }

    static Int measureChildForCells(View view, Int i, Int i2, Int i3, Int i4) {
        Int i5
        Boolean z = false
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3) - i4, View.MeasureSpec.getMode(i3))
        ActionMenuItemView actionMenuItemView = view is ActionMenuItemView ? (ActionMenuItemView) view : null
        Boolean z2 = actionMenuItemView != null && actionMenuItemView.hasText()
        if (i2 <= 0 || (z2 && i2 < 2)) {
            i5 = 0
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(i * i2, Integer.MIN_VALUE), iMakeMeasureSpec)
            Int measuredWidth = view.getMeasuredWidth()
            i5 = measuredWidth / i
            if (measuredWidth % i != 0) {
                i5++
            }
            if (z2 && i5 < 2) {
                i5 = 2
            }
        }
        if (!layoutParams.isOverflowButton && z2) {
            z = true
        }
        layoutParams.expandable = z
        layoutParams.cellsUsed = i5
        view.measure(View.MeasureSpec.makeMeasureSpec(i5 * i, 1073741824), iMakeMeasureSpec)
        return i5
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02f1 A[PHI: r7
  0x02f1: PHI (r7v9 Float) = (r7v8 Float), (r7v17 Float), (r7v17 Float) binds: [B:87:0x0202, B:94:0x022b, B:96:0x023d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun onMeasureExactFormat(Int r35, Int r36) {
        /*
            Method dump skipped, instructions count: 789
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionMenuView.onMeasureExactFormat(Int, Int):Unit")
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams is LayoutParams)
    }

    fun dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus()
        }
    }

    @Override // android.view.View
    fun dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup
    fun generateDefaultLayoutParams() {
        LayoutParams layoutParams = LayoutParams(-2, -2)
        layoutParams.gravity = 16
        return layoutParams
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup
    fun generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup
    fun generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return generateDefaultLayoutParams()
        }
        LayoutParams layoutParams2 = layoutParams is LayoutParams ? LayoutParams((LayoutParams) layoutParams) : LayoutParams(layoutParams)
        if (layoutParams2.gravity > 0) {
            return layoutParams2
        }
        layoutParams2.gravity = 16
        return layoutParams2
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun generateOverflowButtonLayoutParams() {
        LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams()
        layoutParamsGenerateDefaultLayoutParams.isOverflowButton = true
        return layoutParamsGenerateDefaultLayoutParams
    }

    fun getMenu() {
        if (this.mMenu == null) {
            Context context = getContext()
            this.mMenu = MenuBuilder(context)
            this.mMenu.setCallback(MenuBuilderCallback())
            this.mPresenter = ActionMenuPresenter(context)
            this.mPresenter.setReserveOverflow(true)
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : ActionMenuPresenterCallback())
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext)
            this.mPresenter.setMenuView(this)
        }
        return this.mMenu
    }

    @Nullable
    fun getOverflowIcon() {
        getMenu()
        return this.mPresenter.getOverflowIcon()
    }

    fun getPopupTheme() {
        return this.mPopupTheme
    }

    @Override // android.support.v7.view.menu.MenuView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getWindowAnimations() {
        return 0
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected fun hasSupportDividerBeforeChildAt(Int i) {
        Boolean zNeedsDividerAfter = false
        if (i == 0) {
            return false
        }
        KeyEvent.Callback childAt = getChildAt(i - 1)
        KeyEvent.Callback childAt2 = getChildAt(i)
        if (i < getChildCount() && (childAt is ActionMenuChildView)) {
            zNeedsDividerAfter = ((ActionMenuChildView) childAt).needsDividerAfter() | false
        }
        return (i <= 0 || !(childAt2 is ActionMenuChildView)) ? zNeedsDividerAfter : ((ActionMenuChildView) childAt2).needsDividerBefore() | zNeedsDividerAfter
    }

    fun hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu()
    }

    @Override // android.support.v7.view.menu.MenuView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder
    }

    @Override // android.support.v7.view.menu.MenuBuilder.ItemInvoker
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending()
    }

    fun isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isOverflowReserved() {
        return this.mReserveOverflow
    }

    @Override // android.view.View
    fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false)
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu()
                this.mPresenter.showOverflowMenu()
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dismissPopupMenus()
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int i5
        Int i6
        Int measuredWidth
        Int i7
        Int width
        Int paddingLeft
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4)
            return
        }
        Int childCount = getChildCount()
        Int i8 = (i4 - i2) / 2
        Int dividerWidth = getDividerWidth()
        Int i9 = 0
        Int paddingRight = ((i3 - i) - getPaddingRight()) - getPaddingLeft()
        Boolean z2 = false
        Boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this)
        Int i10 = 0
        while (i10 < childCount) {
            View childAt = getChildAt(i10)
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                if (layoutParams.isOverflowButton) {
                    Int measuredWidth2 = childAt.getMeasuredWidth()
                    if (hasSupportDividerBeforeChildAt(i10)) {
                        measuredWidth2 += dividerWidth
                    }
                    Int measuredHeight = childAt.getMeasuredHeight()
                    if (zIsLayoutRtl) {
                        paddingLeft = layoutParams.leftMargin + getPaddingLeft()
                        width = paddingLeft + measuredWidth2
                    } else {
                        width = (getWidth() - getPaddingRight()) - layoutParams.rightMargin
                        paddingLeft = width - measuredWidth2
                    }
                    Int i11 = i8 - (measuredHeight / 2)
                    childAt.layout(paddingLeft, i11, width, measuredHeight + i11)
                    measuredWidth = paddingRight - measuredWidth2
                    z2 = true
                    i7 = i9
                } else {
                    measuredWidth = paddingRight - (layoutParams.rightMargin + (childAt.getMeasuredWidth() + layoutParams.leftMargin))
                    hasSupportDividerBeforeChildAt(i10)
                    i7 = i9 + 1
                }
            } else {
                measuredWidth = paddingRight
                i7 = i9
            }
            i10++
            i9 = i7
            paddingRight = measuredWidth
        }
        if (childCount == 1 && !z2) {
            View childAt2 = getChildAt(0)
            Int measuredWidth3 = childAt2.getMeasuredWidth()
            Int measuredHeight2 = childAt2.getMeasuredHeight()
            Int i12 = ((i3 - i) / 2) - (measuredWidth3 / 2)
            Int i13 = i8 - (measuredHeight2 / 2)
            childAt2.layout(i12, i13, measuredWidth3 + i12, measuredHeight2 + i13)
            return
        }
        Int i14 = i9 - (z2 ? 0 : 1)
        Int iMax = Math.max(0, i14 > 0 ? paddingRight / i14 : 0)
        if (zIsLayoutRtl) {
            Int width2 = getWidth() - getPaddingRight()
            Int i15 = 0
            while (i15 < childCount) {
                View childAt3 = getChildAt(i15)
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams()
                if (childAt3.getVisibility() == 8 || layoutParams2.isOverflowButton) {
                    i6 = width2
                } else {
                    Int i16 = width2 - layoutParams2.rightMargin
                    Int measuredWidth4 = childAt3.getMeasuredWidth()
                    Int measuredHeight3 = childAt3.getMeasuredHeight()
                    Int i17 = i8 - (measuredHeight3 / 2)
                    childAt3.layout(i16 - measuredWidth4, i17, i16, measuredHeight3 + i17)
                    i6 = i16 - ((layoutParams2.leftMargin + measuredWidth4) + iMax)
                }
                i15++
                width2 = i6
            }
            return
        }
        Int paddingLeft2 = getPaddingLeft()
        Int i18 = 0
        while (i18 < childCount) {
            View childAt4 = getChildAt(i18)
            LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams()
            if (childAt4.getVisibility() == 8 || layoutParams3.isOverflowButton) {
                i5 = paddingLeft2
            } else {
                Int i19 = paddingLeft2 + layoutParams3.leftMargin
                Int measuredWidth5 = childAt4.getMeasuredWidth()
                Int measuredHeight4 = childAt4.getMeasuredHeight()
                Int i20 = i8 - (measuredHeight4 / 2)
                childAt4.layout(i19, i20, i19 + measuredWidth5, measuredHeight4 + i20)
                i5 = layoutParams3.rightMargin + measuredWidth5 + iMax + i19
            }
            i18++
            paddingLeft2 = i5
        }
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Boolean z = this.mFormatItems
        this.mFormatItems = View.MeasureSpec.getMode(i) == 1073741824
        if (z != this.mFormatItems) {
            this.mFormatItemsWidth = 0
        }
        Int size = View.MeasureSpec.getSize(i)
        if (this.mFormatItems && this.mMenu != null && size != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = size
            this.mMenu.onItemsChanged(true)
        }
        Int childCount = getChildCount()
        if (this.mFormatItems && childCount > 0) {
            onMeasureExactFormat(i, i2)
            return
        }
        for (Int i3 = 0; i3 < childCount; i3++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i3).getLayoutParams()
            layoutParams.rightMargin = 0
            layoutParams.leftMargin = 0
        }
        super.onMeasure(i, i2)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun peekMenu() {
        return this.mMenu
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setExpandedActionViewsExclusive(Boolean z) {
        this.mPresenter.setExpandedActionViewsExclusive(z)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback
        this.mMenuBuilderCallback = callback2
    }

    fun setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener
    }

    fun setOverflowIcon(@Nullable Drawable drawable) {
        getMenu()
        this.mPresenter.setOverflowIcon(drawable)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setOverflowReserved(Boolean z) {
        this.mReserveOverflow = z
    }

    fun setPopupTheme(@StyleRes Int i) {
        if (this.mPopupTheme != i) {
            this.mPopupTheme = i
            if (i == 0) {
                this.mPopupContext = getContext()
            } else {
                this.mPopupContext = ContextThemeWrapper(getContext(), i)
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter
        this.mPresenter.setMenuView(this)
    }

    fun showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu()
    }
}
