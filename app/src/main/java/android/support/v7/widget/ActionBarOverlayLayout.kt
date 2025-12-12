package android.support.v7.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RestrictTo
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.widget.ActivityChooserView
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.Window
import android.widget.OverScroller
import java.lang.reflect.InvocationTargetException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActionBarOverlayLayout extends ViewGroup implements NestedScrollingParent, DecorContentParent {
    private static val ACTION_BAR_ANIMATE_DELAY = 600
    static final Array<Int> ATTRS = {R.attr.actionBarSize, android.R.attr.windowContentOverlay}
    private static val TAG = "ActionBarOverlayLayout"
    private Int mActionBarHeight
    ActionBarContainer mActionBarTop
    private ActionBarVisibilityCallback mActionBarVisibilityCallback
    private final Runnable mAddActionBarHideOffset
    Boolean mAnimatingForFling
    private final Rect mBaseContentInsets
    private final Rect mBaseInnerInsets
    private ContentFrameLayout mContent
    private final Rect mContentInsets
    ViewPropertyAnimator mCurrentActionBarTopAnimator
    private DecorToolbar mDecorToolbar
    private OverScroller mFlingEstimator
    private Boolean mHasNonEmbeddedTabs
    private Boolean mHideOnContentScroll
    private Int mHideOnContentScrollReference
    private Boolean mIgnoreWindowContentOverlay
    private final Rect mInnerInsets
    private final Rect mLastBaseContentInsets
    private final Rect mLastBaseInnerInsets
    private final Rect mLastInnerInsets
    private Int mLastSystemUiVisibility
    private Boolean mOverlayMode
    private final NestedScrollingParentHelper mParentHelper
    private final Runnable mRemoveActionBarHideOffset
    final AnimatorListenerAdapter mTopAnimatorListener
    private Drawable mWindowContentOverlay
    private Int mWindowVisibility

    public interface ActionBarVisibilityCallback {
        Unit enableContentAnimations(Boolean z)

        Unit hideForSystem()

        Unit onContentScrollStarted()

        Unit onContentScrollStopped()

        Unit onWindowVisibilityChanged(Int i)

        Unit showForSystem()
    }

    class LayoutParams extends ViewGroup.MarginLayoutParams {
        constructor(Int i, Int i2) {
            super(i, i2)
        }

        constructor(Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
        }

        constructor(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
        }

        constructor(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams)
        }
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.mWindowVisibility = 0
        this.mBaseContentInsets = Rect()
        this.mLastBaseContentInsets = Rect()
        this.mContentInsets = Rect()
        this.mBaseInnerInsets = Rect()
        this.mLastBaseInnerInsets = Rect()
        this.mInnerInsets = Rect()
        this.mLastInnerInsets = Rect()
        this.mTopAnimatorListener = AnimatorListenerAdapter() { // from class: android.support.v7.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            fun onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null
                ActionBarOverlayLayout.this.mAnimatingForFling = false
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            fun onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null
                ActionBarOverlayLayout.this.mAnimatingForFling = false
            }
        }
        this.mRemoveActionBarHideOffset = Runnable() { // from class: android.support.v7.widget.ActionBarOverlayLayout.2
            @Override // java.lang.Runnable
            fun run() {
                ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations()
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener)
            }
        }
        this.mAddActionBarHideOffset = Runnable() { // from class: android.support.v7.widget.ActionBarOverlayLayout.3
            @Override // java.lang.Runnable
            fun run() {
                ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations()
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener)
            }
        }
        init(context)
        this.mParentHelper = NestedScrollingParentHelper(this)
    }

    private fun addActionBarHideOffset() {
        haltActionBarHideOffsetAnimations()
        this.mAddActionBarHideOffset.run()
    }

    private fun applyInsets(View view, Rect rect, Boolean z, Boolean z2, Boolean z3, Boolean z4) {
        Boolean z5 = false
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (z && layoutParams.leftMargin != rect.left) {
            layoutParams.leftMargin = rect.left
            z5 = true
        }
        if (z2 && layoutParams.topMargin != rect.top) {
            layoutParams.topMargin = rect.top
            z5 = true
        }
        if (z4 && layoutParams.rightMargin != rect.right) {
            layoutParams.rightMargin = rect.right
            z5 = true
        }
        if (!z3 || layoutParams.bottomMargin == rect.bottom) {
            return z5
        }
        layoutParams.bottomMargin = rect.bottom
        return true
    }

    /* JADX WARN: Multi-variable type inference failed */
    private fun getDecorToolbar(View view) {
        if (view is DecorToolbar) {
            return (DecorToolbar) view
        }
        if (view is Toolbar) {
            return ((Toolbar) view).getWrapper()
        }
        throw IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName())
    }

    private fun init(Context context) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(ATTRS)
        this.mActionBarHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0)
        this.mWindowContentOverlay = typedArrayObtainStyledAttributes.getDrawable(1)
        setWillNotDraw(this.mWindowContentOverlay == null)
        typedArrayObtainStyledAttributes.recycle()
        this.mIgnoreWindowContentOverlay = context.getApplicationInfo().targetSdkVersion < 19
        this.mFlingEstimator = OverScroller(context)
    }

    private fun postAddActionBarHideOffset() {
        haltActionBarHideOffsetAnimations()
        postDelayed(this.mAddActionBarHideOffset, 600L)
    }

    private fun postRemoveActionBarHideOffset() {
        haltActionBarHideOffsetAnimations()
        postDelayed(this.mRemoveActionBarHideOffset, 600L)
    }

    private fun removeActionBarHideOffset() {
        haltActionBarHideOffsetAnimations()
        this.mRemoveActionBarHideOffset.run()
    }

    private fun shouldHideActionBarOnFling(Float f, Float f2) {
        this.mFlingEstimator.fling(0, 0, 0, (Int) f2, 0, 0, Integer.MIN_VALUE, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)
        return this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight()
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun canShowOverflowMenu() {
        pullChildren()
        return this.mDecorToolbar.canShowOverflowMenu()
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams is LayoutParams
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun dismissPopups() {
        pullChildren()
        this.mDecorToolbar.dismissPopupMenus()
    }

    @Override // android.view.View
    fun draw(Canvas canvas) {
        super.draw(canvas)
        if (this.mWindowContentOverlay == null || this.mIgnoreWindowContentOverlay) {
            return
        }
        Int bottom = this.mActionBarTop.getVisibility() == 0 ? (Int) (this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5f) : 0
        this.mWindowContentOverlay.setBounds(0, bottom, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + bottom)
        this.mWindowContentOverlay.draw(canvas)
    }

    @Override // android.view.View
    protected fun fitSystemWindows(Rect rect) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        pullChildren()
        ViewCompat.getWindowSystemUiVisibility(this)
        Boolean zApplyInsets = applyInsets(this.mActionBarTop, rect, true, true, false, true)
        this.mBaseInnerInsets.set(rect)
        ViewUtils.computeFitSystemWindows(this, this.mBaseInnerInsets, this.mBaseContentInsets)
        if (!this.mLastBaseInnerInsets.equals(this.mBaseInnerInsets)) {
            this.mLastBaseInnerInsets.set(this.mBaseInnerInsets)
            zApplyInsets = true
        }
        if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets)
            zApplyInsets = true
        }
        if (zApplyInsets) {
            requestLayout()
        }
        return true
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateDefaultLayoutParams() {
        return LayoutParams(-1, -1)
    }

    @Override // android.view.ViewGroup
    fun generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return LayoutParams(layoutParams)
    }

    fun getActionBarHideOffset() {
        if (this.mActionBarTop != null) {
            return -((Int) this.mActionBarTop.getTranslationY())
        }
        return 0
    }

    @Override // android.view.ViewGroup, android.support.v4.view.NestedScrollingParent
    fun getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes()
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun getTitle() {
        pullChildren()
        return this.mDecorToolbar.getTitle()
    }

    Unit haltActionBarHideOffsetAnimations() {
        removeCallbacks(this.mRemoveActionBarHideOffset)
        removeCallbacks(this.mAddActionBarHideOffset)
        if (this.mCurrentActionBarTopAnimator != null) {
            this.mCurrentActionBarTopAnimator.cancel()
        }
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun hasIcon() {
        pullChildren()
        return this.mDecorToolbar.hasIcon()
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun hasLogo() {
        pullChildren()
        return this.mDecorToolbar.hasLogo()
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun hideOverflowMenu() {
        pullChildren()
        return this.mDecorToolbar.hideOverflowMenu()
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun initFeature(Int i) {
        pullChildren()
        switch (i) {
            case 2:
                this.mDecorToolbar.initProgress()
                break
            case 5:
                this.mDecorToolbar.initIndeterminateProgress()
                break
            case 109:
                setOverlayMode(true)
                break
        }
    }

    fun isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll
    }

    fun isInOverlayMode() {
        return this.mOverlayMode
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun isOverflowMenuShowPending() {
        pullChildren()
        return this.mDecorToolbar.isOverflowMenuShowPending()
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun isOverflowMenuShowing() {
        pullChildren()
        return this.mDecorToolbar.isOverflowMenuShowing()
    }

    @Override // android.view.View
    protected fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        init(getContext())
        ViewCompat.requestApplyInsets(this)
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        haltActionBarHideOffsetAnimations()
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int childCount = getChildCount()
        Int paddingLeft = getPaddingLeft()
        getPaddingRight()
        Int paddingTop = getPaddingTop()
        getPaddingBottom()
        for (Int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5)
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                Int measuredWidth = childAt.getMeasuredWidth()
                Int measuredHeight = childAt.getMeasuredHeight()
                Int i6 = layoutParams.leftMargin + paddingLeft
                Int i7 = layoutParams.topMargin + paddingTop
                childAt.layout(i6, i7, measuredWidth + i6, measuredHeight + i7)
            }
        }
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Int measuredHeight
        pullChildren()
        measureChildWithMargins(this.mActionBarTop, i, 0, i2, 0)
        LayoutParams layoutParams = (LayoutParams) this.mActionBarTop.getLayoutParams()
        Int iMax = Math.max(0, this.mActionBarTop.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin)
        Int iMax2 = Math.max(0, layoutParams.bottomMargin + this.mActionBarTop.getMeasuredHeight() + layoutParams.topMargin)
        Int iCombineMeasuredStates = View.combineMeasuredStates(0, this.mActionBarTop.getMeasuredState())
        Boolean z = (ViewCompat.getWindowSystemUiVisibility(this) & 256) != 0
        if (z) {
            measuredHeight = this.mActionBarHeight
            if (this.mHasNonEmbeddedTabs && this.mActionBarTop.getTabContainer() != null) {
                measuredHeight += this.mActionBarHeight
            }
        } else {
            measuredHeight = this.mActionBarTop.getVisibility() != 8 ? this.mActionBarTop.getMeasuredHeight() : 0
        }
        this.mContentInsets.set(this.mBaseContentInsets)
        this.mInnerInsets.set(this.mBaseInnerInsets)
        if (this.mOverlayMode || z) {
            Rect rect = this.mInnerInsets
            rect.top = measuredHeight + rect.top
            Rect rect2 = this.mInnerInsets
            rect2.bottom = rect2.bottom
        } else {
            Rect rect3 = this.mContentInsets
            rect3.top = measuredHeight + rect3.top
            Rect rect4 = this.mContentInsets
            rect4.bottom = rect4.bottom
        }
        applyInsets(this.mContent, this.mContentInsets, true, true, true, true)
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            this.mLastInnerInsets.set(this.mInnerInsets)
            this.mContent.dispatchFitSystemWindows(this.mInnerInsets)
        }
        measureChildWithMargins(this.mContent, i, 0, i2, 0)
        LayoutParams layoutParams2 = (LayoutParams) this.mContent.getLayoutParams()
        Int iMax3 = Math.max(iMax, this.mContent.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin)
        Int iMax4 = Math.max(iMax2, layoutParams2.bottomMargin + this.mContent.getMeasuredHeight() + layoutParams2.topMargin)
        Int iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.mContent.getMeasuredState())
        setMeasuredDimension(View.resolveSizeAndState(Math.max(iMax3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, iCombineMeasuredStates2), View.resolveSizeAndState(Math.max(iMax4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16))
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedFling(View view, Float f, Float f2, Boolean z) {
        if (!this.mHideOnContentScroll || !z) {
            return false
        }
        if (shouldHideActionBarOnFling(f, f2)) {
            addActionBarHideOffset()
        } else {
            removeActionBarHideOffset()
        }
        this.mAnimatingForFling = true
        return true
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreFling(View view, Float f, Float f2) {
        return false
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreScroll(View view, Int i, Int i2, Array<Int> iArr) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScroll(View view, Int i, Int i2, Int i3, Int i4) {
        this.mHideOnContentScrollReference += i2
        setActionBarHideOffset(this.mHideOnContentScrollReference)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScrollAccepted(View view, View view2, Int i) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, i)
        this.mHideOnContentScrollReference = getActionBarHideOffset()
        haltActionBarHideOffsetAnimations()
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStarted()
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStartNestedScroll(View view, View view2, Int i) {
        if ((i & 2) == 0 || this.mActionBarTop.getVisibility() != 0) {
            return false
        }
        return this.mHideOnContentScroll
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStopNestedScroll(View view) {
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                postRemoveActionBarHideOffset()
            } else {
                postAddActionBarHideOffset()
            }
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStopped()
        }
    }

    @Override // android.view.View
    fun onWindowSystemUiVisibilityChanged(Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i)
        }
        pullChildren()
        Int i2 = this.mLastSystemUiVisibility ^ i
        this.mLastSystemUiVisibility = i
        Boolean z = (i & 4) == 0
        Boolean z2 = (i & 256) != 0
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.enableContentAnimations(z2 ? false : true)
            if (z || !z2) {
                this.mActionBarVisibilityCallback.showForSystem()
            } else {
                this.mActionBarVisibilityCallback.hideForSystem()
            }
        }
        if ((i2 & 256) == 0 || this.mActionBarVisibilityCallback == null) {
            return
        }
        ViewCompat.requestApplyInsets(this)
    }

    @Override // android.view.View
    protected fun onWindowVisibilityChanged(Int i) {
        super.onWindowVisibilityChanged(i)
        this.mWindowVisibility = i
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(i)
        }
    }

    Unit pullChildren() {
        if (this.mContent == null) {
            this.mContent = (ContentFrameLayout) findViewById(R.id.action_bar_activity_content)
            this.mActionBarTop = (ActionBarContainer) findViewById(R.id.action_bar_container)
            this.mDecorToolbar = getDecorToolbar(findViewById(R.id.action_bar))
        }
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun restoreToolbarHierarchyState(SparseArray sparseArray) {
        pullChildren()
        this.mDecorToolbar.restoreHierarchyState(sparseArray)
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun saveToolbarHierarchyState(SparseArray sparseArray) {
        pullChildren()
        this.mDecorToolbar.saveHierarchyState(sparseArray)
    }

    fun setActionBarHideOffset(Int i) {
        haltActionBarHideOffsetAnimations()
        this.mActionBarTop.setTranslationY(-Math.max(0, Math.min(i, this.mActionBarTop.getHeight())))
    }

    fun setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.mActionBarVisibilityCallback = actionBarVisibilityCallback
        if (getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility)
            if (this.mLastSystemUiVisibility != 0) {
                onWindowSystemUiVisibilityChanged(this.mLastSystemUiVisibility)
                ViewCompat.requestApplyInsets(this)
            }
        }
    }

    fun setHasNonEmbeddedTabs(Boolean z) {
        this.mHasNonEmbeddedTabs = z
    }

    fun setHideOnContentScrollEnabled(Boolean z) {
        if (z != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = z
            if (z) {
                return
            }
            haltActionBarHideOffsetAnimations()
            setActionBarHideOffset(0)
        }
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setIcon(Int i) {
        pullChildren()
        this.mDecorToolbar.setIcon(i)
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setIcon(Drawable drawable) {
        pullChildren()
        this.mDecorToolbar.setIcon(drawable)
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setLogo(Int i) {
        pullChildren()
        this.mDecorToolbar.setLogo(i)
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setMenu(Menu menu, MenuPresenter.Callback callback) {
        pullChildren()
        this.mDecorToolbar.setMenu(menu, callback)
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setMenuPrepared() {
        pullChildren()
        this.mDecorToolbar.setMenuPrepared()
    }

    fun setOverlayMode(Boolean z) {
        this.mOverlayMode = z
        this.mIgnoreWindowContentOverlay = z && getContext().getApplicationInfo().targetSdkVersion < 19
    }

    fun setShowingForActionMode(Boolean z) {
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setUiOptions(Int i) {
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setWindowCallback(Window.Callback callback) {
        pullChildren()
        this.mDecorToolbar.setWindowCallback(callback)
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun setWindowTitle(CharSequence charSequence) {
        pullChildren()
        this.mDecorToolbar.setWindowTitle(charSequence)
    }

    @Override // android.view.ViewGroup
    fun shouldDelayChildPressedState() {
        return false
    }

    @Override // android.support.v7.widget.DecorContentParent
    fun showOverflowMenu() {
        pullChildren()
        return this.mDecorToolbar.showOverflowMenu()
    }
}
