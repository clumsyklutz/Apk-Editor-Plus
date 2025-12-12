package android.support.v7.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.widget.ActivityChooserView
import android.util.AttributeSet
import android.view.ActionMode
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActionBarContainer extends FrameLayout {
    private View mActionBarView
    Drawable mBackground
    private View mContextView
    private Int mHeight
    Boolean mIsSplit
    Boolean mIsStacked
    private Boolean mIsTransitioning
    Drawable mSplitBackground
    Drawable mStackedBackground
    private View mTabContainer

    constructor(Context context) {
        this(context, null)
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        Boolean z = true
        ViewCompat.setBackground(this, ActionBarBackgroundDrawable(this))
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar)
        this.mBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_background)
        this.mStackedBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundStacked)
        this.mHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_height, -1)
        if (getId() == R.id.split_action_bar) {
            this.mIsSplit = true
            this.mSplitBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundSplit)
        }
        typedArrayObtainStyledAttributes.recycle()
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false
        }
        setWillNotDraw(z)
    }

    private fun getMeasuredHeightWithMargins(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams()
        return layoutParams.bottomMargin + view.getMeasuredHeight() + layoutParams.topMargin
    }

    private fun isCollapsed(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        if (this.mBackground != null && this.mBackground.isStateful()) {
            this.mBackground.setState(getDrawableState())
        }
        if (this.mStackedBackground != null && this.mStackedBackground.isStateful()) {
            this.mStackedBackground.setState(getDrawableState())
        }
        if (this.mSplitBackground == null || !this.mSplitBackground.isStateful()) {
            return
        }
        this.mSplitBackground.setState(getDrawableState())
    }

    fun getTabContainer() {
        return this.mTabContainer
    }

    @Override // android.view.ViewGroup, android.view.View
    fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        if (this.mBackground != null) {
            this.mBackground.jumpToCurrentState()
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.jumpToCurrentState()
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.jumpToCurrentState()
        }
    }

    @Override // android.view.View
    fun onFinishInflate() {
        super.onFinishInflate()
        this.mActionBarView = findViewById(R.id.action_bar)
        this.mContextView = findViewById(R.id.action_context_bar)
    }

    @Override // android.view.View
    fun onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent)
        return true
    }

    @Override // android.view.ViewGroup
    fun onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent)
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Boolean z2
        Boolean z3 = true
        super.onLayout(z, i, i2, i3, i4)
        View view = this.mTabContainer
        Boolean z4 = (view == null || view.getVisibility() == 8) ? false : true
        if (view != null && view.getVisibility() != 8) {
            Int measuredHeight = getMeasuredHeight()
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams()
            view.layout(i, (measuredHeight - view.getMeasuredHeight()) - layoutParams.bottomMargin, i3, measuredHeight - layoutParams.bottomMargin)
        }
        if (!this.mIsSplit) {
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom())
                } else if (this.mContextView == null || this.mContextView.getVisibility() != 0) {
                    this.mBackground.setBounds(0, 0, 0, 0)
                } else {
                    this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom())
                }
                z2 = true
            } else {
                z2 = false
            }
            this.mIsStacked = z4
            if (!z4 || this.mStackedBackground == null) {
                z3 = z2
            } else {
                this.mStackedBackground.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())
            }
        } else if (this.mSplitBackground != null) {
            this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight())
        } else {
            z3 = false
        }
        if (z3) {
            invalidate()
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    fun onMeasure(Int i, Int i2) {
        if (this.mActionBarView == null && View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE && this.mHeight >= 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, View.MeasureSpec.getSize(i2)), Integer.MIN_VALUE)
        }
        super.onMeasure(i, i2)
        if (this.mActionBarView == null) {
            return
        }
        Int mode = View.MeasureSpec.getMode(i2)
        if (this.mTabContainer == null || this.mTabContainer.getVisibility() == 8 || mode == 1073741824) {
            return
        }
        setMeasuredDimension(getMeasuredWidth(), Math.min((!isCollapsed(this.mActionBarView) ? getMeasuredHeightWithMargins(this.mActionBarView) : !isCollapsed(this.mContextView) ? getMeasuredHeightWithMargins(this.mContextView) : 0) + getMeasuredHeightWithMargins(this.mTabContainer), mode == Integer.MIN_VALUE ? View.MeasureSpec.getSize(i2) : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED))
    }

    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent)
        return true
    }

    fun setPrimaryBackground(Drawable drawable) {
        Boolean z = true
        if (this.mBackground != null) {
            this.mBackground.setCallback(null)
            unscheduleDrawable(this.mBackground)
        }
        this.mBackground = drawable
        if (drawable != null) {
            drawable.setCallback(this)
            if (this.mActionBarView != null) {
                this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom())
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false
        }
        setWillNotDraw(z)
        invalidate()
    }

    fun setSplitBackground(Drawable drawable) {
        Boolean z = true
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setCallback(null)
            unscheduleDrawable(this.mSplitBackground)
        }
        this.mSplitBackground = drawable
        if (drawable != null) {
            drawable.setCallback(this)
            if (this.mIsSplit && this.mSplitBackground != null) {
                this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight())
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false
        }
        setWillNotDraw(z)
        invalidate()
    }

    fun setStackedBackground(Drawable drawable) {
        Boolean z = true
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setCallback(null)
            unscheduleDrawable(this.mStackedBackground)
        }
        this.mStackedBackground = drawable
        if (drawable != null) {
            drawable.setCallback(this)
            if (this.mIsStacked && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom())
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false
        }
        setWillNotDraw(z)
        invalidate()
    }

    fun setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer)
        }
        this.mTabContainer = scrollingTabContainerView
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView)
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams()
            layoutParams.width = -1
            layoutParams.height = -2
            scrollingTabContainerView.setAllowCollapse(false)
        }
    }

    fun setTransitioning(Boolean z) {
        this.mIsTransitioning = z
        setDescendantFocusability(z ? 393216 : 262144)
    }

    @Override // android.view.View
    fun setVisibility(Int i) {
        super.setVisibility(i)
        Boolean z = i == 0
        if (this.mBackground != null) {
            this.mBackground.setVisible(z, false)
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setVisible(z, false)
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setVisible(z, false)
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun startActionModeForChild(View view, ActionMode.Callback callback) {
        return null
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun startActionModeForChild(View view, ActionMode.Callback callback, Int i) {
        if (i != 0) {
            return super.startActionModeForChild(view, callback, i)
        }
        return null
    }

    @Override // android.view.View
    protected fun verifyDrawable(Drawable drawable) {
        return (drawable == this.mBackground && !this.mIsSplit) || (drawable == this.mStackedBackground && this.mIsStacked) || ((drawable == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(drawable))
    }
}
