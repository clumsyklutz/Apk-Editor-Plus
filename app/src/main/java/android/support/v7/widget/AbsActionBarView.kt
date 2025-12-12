package android.support.v7.widget

import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import androidx.appcompat.R
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

abstract class AbsActionBarView extends ViewGroup {
    private static val FADE_DURATION = 200
    protected ActionMenuPresenter mActionMenuPresenter
    protected Int mContentHeight
    private Boolean mEatingHover
    private Boolean mEatingTouch
    protected ActionMenuView mMenuView
    protected final Context mPopupContext
    protected final VisibilityAnimListener mVisAnimListener
    protected ViewPropertyAnimatorCompat mVisibilityAnim

    class VisibilityAnimListener implements ViewPropertyAnimatorListener {
        private Boolean mCanceled = false
        Int mFinalVisibility

        protected constructor() {
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationCancel(View view) {
            this.mCanceled = true
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationEnd(View view) {
            if (this.mCanceled) {
                return
            }
            AbsActionBarView.this.mVisibilityAnim = null
            AbsActionBarView.super.setVisibility(this.mFinalVisibility)
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationStart(View view) {
            AbsActionBarView.super.setVisibility(0)
            this.mCanceled = false
        }

        fun withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, Int i) {
            AbsActionBarView.this.mVisibilityAnim = viewPropertyAnimatorCompat
            this.mFinalVisibility = i
            return this
        }
    }

    AbsActionBarView(Context context) {
        this(context, null)
    }

    AbsActionBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    AbsActionBarView(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mVisAnimListener = VisibilityAnimListener()
        TypedValue typedValue = TypedValue()
        if (!context.getTheme().resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true) || typedValue.resourceId == 0) {
            this.mPopupContext = context
        } else {
            this.mPopupContext = ContextThemeWrapper(context, typedValue.resourceId)
        }
    }

    protected static Int next(Int i, Int i2, Boolean z) {
        return z ? i - i2 : i + i2
    }

    fun animateToVisibility(Int i) {
        setupAnimatorToVisibility(i, 200L).start()
    }

    fun canShowOverflowMenu() {
        return isOverflowReserved() && getVisibility() == 0
    }

    fun dismissPopupMenus() {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus()
        }
    }

    fun getAnimatedVisibility() {
        return this.mVisibilityAnim != null ? this.mVisAnimListener.mFinalVisibility : getVisibility()
    }

    fun getContentHeight() {
        return this.mContentHeight
    }

    fun hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu()
        }
        return false
    }

    fun isOverflowMenuShowPending() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowPending()
        }
        return false
    }

    fun isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing()
        }
        return false
    }

    fun isOverflowReserved() {
        return this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved()
    }

    protected fun measureChildView(View view, Int i, Int i2, Int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2)
        return Math.max(0, (i - view.getMeasuredWidth()) - i3)
    }

    @Override // android.view.View
    protected fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0)
        setContentHeight(typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0))
        typedArrayObtainStyledAttributes.recycle()
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.onConfigurationChanged(configuration)
        }
    }

    @Override // android.view.View
    fun onHoverEvent(MotionEvent motionEvent) {
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 9) {
            this.mEatingHover = false
        }
        if (!this.mEatingHover) {
            Boolean zOnHoverEvent = super.onHoverEvent(motionEvent)
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.mEatingHover = true
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false
        }
        return true
    }

    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 0) {
            this.mEatingTouch = false
        }
        if (!this.mEatingTouch) {
            Boolean zOnTouchEvent = super.onTouchEvent(motionEvent)
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.mEatingTouch = true
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false
        }
        return true
    }

    protected fun positionChild(View view, Int i, Int i2, Int i3, Boolean z) {
        Int measuredWidth = view.getMeasuredWidth()
        Int measuredHeight = view.getMeasuredHeight()
        Int i4 = ((i3 - measuredHeight) / 2) + i2
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4)
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4)
        }
        return z ? -measuredWidth : measuredWidth
    }

    fun postShowOverflowMenu() {
        post(Runnable() { // from class: android.support.v7.widget.AbsActionBarView.1
            @Override // java.lang.Runnable
            fun run() {
                AbsActionBarView.this.showOverflowMenu()
            }
        })
    }

    fun setContentHeight(Int i) {
        this.mContentHeight = i
        requestLayout()
    }

    @Override // android.view.View
    fun setVisibility(Int i) {
        if (i != getVisibility()) {
            if (this.mVisibilityAnim != null) {
                this.mVisibilityAnim.cancel()
            }
            super.setVisibility(i)
        }
    }

    fun setupAnimatorToVisibility(Int i, Long j) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel()
        }
        if (i != 0) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAlpha = ViewCompat.animate(this).alpha(0.0f)
            viewPropertyAnimatorCompatAlpha.setDuration(j)
            viewPropertyAnimatorCompatAlpha.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorCompatAlpha, i))
            return viewPropertyAnimatorCompatAlpha
        }
        if (getVisibility() != 0) {
            setAlpha(0.0f)
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAlpha2 = ViewCompat.animate(this).alpha(1.0f)
        viewPropertyAnimatorCompatAlpha2.setDuration(j)
        viewPropertyAnimatorCompatAlpha2.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorCompatAlpha2, i))
        return viewPropertyAnimatorCompatAlpha2
    }

    fun showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu()
        }
        return false
    }
}
