package android.support.v4.widget

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import android.support.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import android.support.v4.view.NestedScrollingChild
import android.support.v4.view.NestedScrollingChildHelper
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation
import android.widget.AbsListView
import android.widget.ListView

class SwipeRefreshLayout extends ViewGroup implements NestedScrollingChild, NestedScrollingParent {
    private static val ALPHA_ANIMATION_DURATION = 300
    private static val ANIMATE_TO_START_DURATION = 200
    private static val ANIMATE_TO_TRIGGER_DURATION = 200
    private static val CIRCLE_BG_LIGHT = -328966

    @VisibleForTesting
    static val CIRCLE_DIAMETER = 40

    @VisibleForTesting
    static val CIRCLE_DIAMETER_LARGE = 56
    private static val DECELERATE_INTERPOLATION_FACTOR = 2.0f
    public static val DEFAULT = 1
    private static val DEFAULT_CIRCLE_TARGET = 64
    public static val DEFAULT_SLINGSHOT_DISTANCE = -1
    private static val DRAG_RATE = 0.5f
    private static val INVALID_POINTER = -1
    public static val LARGE = 0
    private static val MAX_ALPHA = 255
    private static val MAX_PROGRESS_ANGLE = 0.8f
    private static val SCALE_DOWN_DURATION = 150
    private static val STARTING_PROGRESS_ALPHA = 76
    private Int mActivePointerId
    private Animation mAlphaMaxAnimation
    private Animation mAlphaStartAnimation
    private final Animation mAnimateToCorrectPosition
    private final Animation mAnimateToStartPosition
    private OnChildScrollUpCallback mChildScrollUpCallback
    private Int mCircleDiameter
    CircleImageView mCircleView
    private Int mCircleViewIndex
    Int mCurrentTargetOffsetTop
    Int mCustomSlingshotDistance
    private final DecelerateInterpolator mDecelerateInterpolator
    protected Int mFrom
    private Float mInitialDownY
    private Float mInitialMotionY
    private Boolean mIsBeingDragged
    OnRefreshListener mListener
    private Int mMediumAnimationDuration
    private Boolean mNestedScrollInProgress
    private final NestedScrollingChildHelper mNestedScrollingChildHelper
    private final NestedScrollingParentHelper mNestedScrollingParentHelper
    Boolean mNotify
    protected Int mOriginalOffsetTop
    private final Array<Int> mParentOffsetInWindow
    private final Array<Int> mParentScrollConsumed
    CircularProgressDrawable mProgress
    private Animation.AnimationListener mRefreshListener
    Boolean mRefreshing
    private Boolean mReturningToStart
    Boolean mScale
    private Animation mScaleAnimation
    private Animation mScaleDownAnimation
    private Animation mScaleDownToStartAnimation
    Int mSpinnerOffsetEnd
    Float mStartingScale
    private View mTarget
    private Float mTotalDragDistance
    private Float mTotalUnconsumed
    private Int mTouchSlop
    Boolean mUsingCustomStart
    private static val LOG_TAG = SwipeRefreshLayout.class.getSimpleName()
    private static final Array<Int> LAYOUT_ATTRS = {R.attr.enabled}

    public interface OnChildScrollUpCallback {
        Boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view)
    }

    public interface OnRefreshListener {
        Unit onRefresh()
    }

    constructor(@NonNull Context context) {
        this(context, null)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet)
        this.mRefreshing = false
        this.mTotalDragDistance = -1.0f
        this.mParentScrollConsumed = new Int[2]
        this.mParentOffsetInWindow = new Int[2]
        this.mActivePointerId = -1
        this.mCircleViewIndex = -1
        this.mRefreshListener = new Animation.AnimationListener() { // from class: android.support.v4.widget.SwipeRefreshLayout.1
            @Override // android.view.animation.Animation.AnimationListener
            fun onAnimationEnd(Animation animation) {
                if (!SwipeRefreshLayout.this.mRefreshing) {
                    SwipeRefreshLayout.this.reset()
                    return
                }
                SwipeRefreshLayout.this.mProgress.setAlpha(255)
                SwipeRefreshLayout.this.mProgress.start()
                if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                    SwipeRefreshLayout.this.mListener.onRefresh()
                }
                SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView.getTop()
            }

            @Override // android.view.animation.Animation.AnimationListener
            fun onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            fun onAnimationStart(Animation animation) {
            }
        }
        this.mAnimateToCorrectPosition = Animation() { // from class: android.support.v4.widget.SwipeRefreshLayout.6
            @Override // android.view.animation.Animation
            fun applyTransformation(Float f, Transformation transformation) {
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((((Int) (((!SwipeRefreshLayout.this.mUsingCustomStart ? SwipeRefreshLayout.this.mSpinnerOffsetEnd - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop) : SwipeRefreshLayout.this.mSpinnerOffsetEnd) - SwipeRefreshLayout.this.mFrom) * f)) + SwipeRefreshLayout.this.mFrom) - SwipeRefreshLayout.this.mCircleView.getTop())
                SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f)
            }
        }
        this.mAnimateToStartPosition = Animation() { // from class: android.support.v4.widget.SwipeRefreshLayout.7
            @Override // android.view.animation.Animation
            fun applyTransformation(Float f, Transformation transformation) {
                SwipeRefreshLayout.this.moveToStart(f)
            }
        }
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop()
        this.mMediumAnimationDuration = getResources().getInteger(R.integer.config_mediumAnimTime)
        setWillNotDraw(false)
        this.mDecelerateInterpolator = DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR)
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics()
        this.mCircleDiameter = (Int) (40.0f * displayMetrics.density)
        createProgressView()
        setChildrenDrawingOrderEnabled(true)
        this.mSpinnerOffsetEnd = (Int) (displayMetrics.density * 64.0f)
        this.mTotalDragDistance = this.mSpinnerOffsetEnd
        this.mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
        this.mNestedScrollingChildHelper = NestedScrollingChildHelper(this)
        setNestedScrollingEnabled(true)
        Int i = -this.mCircleDiameter
        this.mCurrentTargetOffsetTop = i
        this.mOriginalOffsetTop = i
        moveToStart(1.0f)
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS)
        setEnabled(typedArrayObtainStyledAttributes.getBoolean(0, true))
        typedArrayObtainStyledAttributes.recycle()
    }

    private fun animateOffsetToCorrectPosition(Int i, Animation.AnimationListener animationListener) {
        this.mFrom = i
        this.mAnimateToCorrectPosition.reset()
        this.mAnimateToCorrectPosition.setDuration(200L)
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator)
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener)
        }
        this.mCircleView.clearAnimation()
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition)
    }

    private fun animateOffsetToStartPosition(Int i, Animation.AnimationListener animationListener) {
        if (this.mScale) {
            startScaleDownReturnToStartAnimation(i, animationListener)
            return
        }
        this.mFrom = i
        this.mAnimateToStartPosition.reset()
        this.mAnimateToStartPosition.setDuration(200L)
        this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator)
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener)
        }
        this.mCircleView.clearAnimation()
        this.mCircleView.startAnimation(this.mAnimateToStartPosition)
    }

    private fun createProgressView() {
        this.mCircleView = CircleImageView(getContext(), CIRCLE_BG_LIGHT)
        this.mProgress = CircularProgressDrawable(getContext())
        this.mProgress.setStyle(1)
        this.mCircleView.setImageDrawable(this.mProgress)
        this.mCircleView.setVisibility(8)
        addView(this.mCircleView)
    }

    private fun ensureTarget() {
        if (this.mTarget == null) {
            for (Int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i)
                if (!childAt.equals(this.mCircleView)) {
                    this.mTarget = childAt
                    return
                }
            }
        }
    }

    private fun finishSpinner(Float f) {
        if (f > this.mTotalDragDistance) {
            setRefreshing(true, true)
            return
        }
        this.mRefreshing = false
        this.mProgress.setStartEndTrim(0.0f, 0.0f)
        animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, this.mScale ? null : new Animation.AnimationListener() { // from class: android.support.v4.widget.SwipeRefreshLayout.5
            @Override // android.view.animation.Animation.AnimationListener
            fun onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.mScale) {
                    return
                }
                SwipeRefreshLayout.this.startScaleDownAnimation(null)
            }

            @Override // android.view.animation.Animation.AnimationListener
            fun onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            fun onAnimationStart(Animation animation) {
            }
        })
        this.mProgress.setArrowEnabled(false)
    }

    private fun isAnimationRunning(Animation animation) {
        return (animation == null || !animation.hasStarted() || animation.hasEnded()) ? false : true
    }

    private fun moveSpinner(Float f) {
        this.mProgress.setArrowEnabled(true)
        Float fMin = Math.min(1.0f, Math.abs(f / this.mTotalDragDistance))
        Float fMax = (((Float) Math.max(fMin - 0.4d, 0.0d)) * 5.0f) / 3.0f
        Float fAbs = Math.abs(f) - this.mTotalDragDistance
        Float f2 = this.mCustomSlingshotDistance > 0 ? this.mCustomSlingshotDistance : this.mUsingCustomStart ? this.mSpinnerOffsetEnd - this.mOriginalOffsetTop : this.mSpinnerOffsetEnd
        Float fMax2 = Math.max(0.0f, Math.min(fAbs, f2 * DECELERATE_INTERPOLATION_FACTOR) / f2)
        Float fPow = ((Float) ((fMax2 / 4.0f) - Math.pow(fMax2 / 4.0f, 2.0d))) * DECELERATE_INTERPOLATION_FACTOR
        Int i = ((Int) ((f2 * fMin) + (f2 * fPow * DECELERATE_INTERPOLATION_FACTOR))) + this.mOriginalOffsetTop
        if (this.mCircleView.getVisibility() != 0) {
            this.mCircleView.setVisibility(0)
        }
        if (!this.mScale) {
            this.mCircleView.setScaleX(1.0f)
            this.mCircleView.setScaleY(1.0f)
        }
        if (this.mScale) {
            setAnimationProgress(Math.min(1.0f, f / this.mTotalDragDistance))
        }
        if (f < this.mTotalDragDistance) {
            if (this.mProgress.getAlpha() > 76 && !isAnimationRunning(this.mAlphaStartAnimation)) {
                startProgressAlphaStartAnimation()
            }
        } else if (this.mProgress.getAlpha() < 255 && !isAnimationRunning(this.mAlphaMaxAnimation)) {
            startProgressAlphaMaxAnimation()
        }
        this.mProgress.setStartEndTrim(0.0f, Math.min(MAX_PROGRESS_ANGLE, fMax * MAX_PROGRESS_ANGLE))
        this.mProgress.setArrowScale(Math.min(1.0f, fMax))
        this.mProgress.setProgressRotation(((-0.25f) + (fMax * 0.4f) + (fPow * DECELERATE_INTERPOLATION_FACTOR)) * DRAG_RATE)
        setTargetOffsetTopAndBottom(i - this.mCurrentTargetOffsetTop)
    }

    private fun onSecondaryPointerUp(MotionEvent motionEvent) {
        Int actionIndex = motionEvent.getActionIndex()
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            this.mActivePointerId = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0)
        }
    }

    private fun setColorViewAlpha(Int i) {
        this.mCircleView.getBackground().setAlpha(i)
        this.mProgress.setAlpha(i)
    }

    private fun setRefreshing(Boolean z, Boolean z2) {
        if (this.mRefreshing != z) {
            this.mNotify = z2
            ensureTarget()
            this.mRefreshing = z
            if (this.mRefreshing) {
                animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener)
            } else {
                startScaleDownAnimation(this.mRefreshListener)
            }
        }
    }

    private fun startAlphaAnimation(final Int i, final Int i2) {
        Animation animation = Animation() { // from class: android.support.v4.widget.SwipeRefreshLayout.4
            @Override // android.view.animation.Animation
            fun applyTransformation(Float f, Transformation transformation) {
                SwipeRefreshLayout.this.mProgress.setAlpha((Int) (i + ((i2 - i) * f)))
            }
        }
        animation.setDuration(300L)
        this.mCircleView.setAnimationListener(null)
        this.mCircleView.clearAnimation()
        this.mCircleView.startAnimation(animation)
        return animation
    }

    private fun startDragging(Float f) {
        if (f - this.mInitialDownY <= this.mTouchSlop || this.mIsBeingDragged) {
            return
        }
        this.mInitialMotionY = this.mInitialDownY + this.mTouchSlop
        this.mIsBeingDragged = true
        this.mProgress.setAlpha(76)
    }

    private fun startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 255)
    }

    private fun startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 76)
    }

    private fun startScaleDownReturnToStartAnimation(Int i, Animation.AnimationListener animationListener) {
        this.mFrom = i
        this.mStartingScale = this.mCircleView.getScaleX()
        this.mScaleDownToStartAnimation = Animation() { // from class: android.support.v4.widget.SwipeRefreshLayout.8
            @Override // android.view.animation.Animation
            fun applyTransformation(Float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(SwipeRefreshLayout.this.mStartingScale + ((-SwipeRefreshLayout.this.mStartingScale) * f))
                SwipeRefreshLayout.this.moveToStart(f)
            }
        }
        this.mScaleDownToStartAnimation.setDuration(150L)
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener)
        }
        this.mCircleView.clearAnimation()
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation)
    }

    private fun startScaleUpAnimation(Animation.AnimationListener animationListener) {
        this.mCircleView.setVisibility(0)
        this.mProgress.setAlpha(255)
        this.mScaleAnimation = Animation() { // from class: android.support.v4.widget.SwipeRefreshLayout.2
            @Override // android.view.animation.Animation
            fun applyTransformation(Float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f)
            }
        }
        this.mScaleAnimation.setDuration(this.mMediumAnimationDuration)
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener)
        }
        this.mCircleView.clearAnimation()
        this.mCircleView.startAnimation(this.mScaleAnimation)
    }

    fun canChildScrollUp() {
        return this.mChildScrollUpCallback != null ? this.mChildScrollUpCallback.canChildScrollUp(this, this.mTarget) : this.mTarget is ListView ? ListViewCompat.canScrollList((ListView) this.mTarget, -1) : this.mTarget.canScrollVertically(-1)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedFling(Float f, Float f2, Boolean z) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(f, f2, z)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedPreFling(Float f, Float f2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(f, f2)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedPreScroll(Int i, Int i2, Array<Int> iArr, Array<Int> iArr2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, Array<Int> iArr) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(i, i2, i3, i4, iArr)
    }

    @Override // android.view.ViewGroup
    protected fun getChildDrawingOrder(Int i, Int i2) {
        return this.mCircleViewIndex < 0 ? i2 : i2 == i + (-1) ? this.mCircleViewIndex : i2 >= this.mCircleViewIndex ? i2 + 1 : i2
    }

    @Override // android.view.ViewGroup, android.support.v4.view.NestedScrollingParent
    fun getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes()
    }

    fun getProgressCircleDiameter() {
        return this.mCircleDiameter
    }

    fun getProgressViewEndOffset() {
        return this.mSpinnerOffsetEnd
    }

    fun getProgressViewStartOffset() {
        return this.mOriginalOffsetTop
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent()
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled()
    }

    fun isRefreshing() {
        return this.mRefreshing
    }

    Unit moveToStart(Float f) {
        setTargetOffsetTopAndBottom((this.mFrom + ((Int) ((this.mOriginalOffsetTop - this.mFrom) * f))) - this.mCircleView.getTop())
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        reset()
    }

    @Override // android.view.ViewGroup
    fun onInterceptTouchEvent(MotionEvent motionEvent) {
        ensureTarget()
        Int actionMasked = motionEvent.getActionMasked()
        if (this.mReturningToStart && actionMasked == 0) {
            this.mReturningToStart = false
        }
        if (!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing || this.mNestedScrollInProgress) {
            return false
        }
        switch (actionMasked) {
            case 0:
                setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop())
                this.mActivePointerId = motionEvent.getPointerId(0)
                this.mIsBeingDragged = false
                Int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId)
                if (iFindPointerIndex < 0) {
                    return false
                }
                this.mInitialDownY = motionEvent.getY(iFindPointerIndex)
                break
            case 1:
            case 3:
                this.mIsBeingDragged = false
                this.mActivePointerId = -1
                break
            case 2:
                if (this.mActivePointerId == -1) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.")
                    return false
                }
                Int iFindPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId)
                if (iFindPointerIndex2 < 0) {
                    return false
                }
                startDragging(motionEvent.getY(iFindPointerIndex2))
                break
            case 6:
                onSecondaryPointerUp(motionEvent)
                break
        }
        return this.mIsBeingDragged
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int measuredWidth = getMeasuredWidth()
        Int measuredHeight = getMeasuredHeight()
        if (getChildCount() == 0) {
            return
        }
        if (this.mTarget == null) {
            ensureTarget()
        }
        if (this.mTarget != null) {
            View view = this.mTarget
            Int paddingLeft = getPaddingLeft()
            Int paddingTop = getPaddingTop()
            view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop)
            Int measuredWidth2 = this.mCircleView.getMeasuredWidth()
            this.mCircleView.layout((measuredWidth / 2) - (measuredWidth2 / 2), this.mCurrentTargetOffsetTop, (measuredWidth / 2) + (measuredWidth2 / 2), this.mCurrentTargetOffsetTop + this.mCircleView.getMeasuredHeight())
        }
    }

    @Override // android.view.View
    fun onMeasure(Int i, Int i2) {
        super.onMeasure(i, i2)
        if (this.mTarget == null) {
            ensureTarget()
        }
        if (this.mTarget == null) {
            return
        }
        this.mTarget.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824))
        this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824))
        this.mCircleViewIndex = -1
        for (Int i3 = 0; i3 < getChildCount(); i3++) {
            if (getChildAt(i3) == this.mCircleView) {
                this.mCircleViewIndex = i3
                return
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedFling(View view, Float f, Float f2, Boolean z) {
        return dispatchNestedFling(f, f2, z)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreFling(View view, Float f, Float f2) {
        return dispatchNestedPreFling(f, f2)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreScroll(View view, Int i, Int i2, Array<Int> iArr) {
        if (i2 > 0 && this.mTotalUnconsumed > 0.0f) {
            if (i2 > this.mTotalUnconsumed) {
                iArr[1] = i2 - ((Int) this.mTotalUnconsumed)
                this.mTotalUnconsumed = 0.0f
            } else {
                this.mTotalUnconsumed -= i2
                iArr[1] = i2
            }
            moveSpinner(this.mTotalUnconsumed)
        }
        if (this.mUsingCustomStart && i2 > 0 && this.mTotalUnconsumed == 0.0f && Math.abs(i2 - iArr[1]) > 0) {
            this.mCircleView.setVisibility(8)
        }
        Array<Int> iArr2 = this.mParentScrollConsumed
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0]
            iArr[1] = iArr2[1] + iArr[1]
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScroll(View view, Int i, Int i2, Int i3, Int i4) {
        dispatchNestedScroll(i, i2, i3, i4, this.mParentOffsetInWindow)
        if (this.mParentOffsetInWindow[1] + i4 >= 0 || canChildScrollUp()) {
            return
        }
        this.mTotalUnconsumed = Math.abs(r0) + this.mTotalUnconsumed
        moveSpinner(this.mTotalUnconsumed)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScrollAccepted(View view, View view2, Int i) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i)
        startNestedScroll(i & 2)
        this.mTotalUnconsumed = 0.0f
        this.mNestedScrollInProgress = true
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStartNestedScroll(View view, View view2, Int i) {
        return (!isEnabled() || this.mReturningToStart || this.mRefreshing || (i & 2) == 0) ? false : true
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStopNestedScroll(View view) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view)
        this.mNestedScrollInProgress = false
        if (this.mTotalUnconsumed > 0.0f) {
            finishSpinner(this.mTotalUnconsumed)
            this.mTotalUnconsumed = 0.0f
        }
        stopNestedScroll()
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        Int actionMasked = motionEvent.getActionMasked()
        if (this.mReturningToStart && actionMasked == 0) {
            this.mReturningToStart = false
        }
        if (!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing || this.mNestedScrollInProgress) {
            return false
        }
        switch (actionMasked) {
            case 0:
                this.mActivePointerId = motionEvent.getPointerId(0)
                this.mIsBeingDragged = false
                return true
            case 1:
                Int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId)
                if (iFindPointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.")
                    return false
                }
                if (this.mIsBeingDragged) {
                    Float y = (motionEvent.getY(iFindPointerIndex) - this.mInitialMotionY) * DRAG_RATE
                    this.mIsBeingDragged = false
                    finishSpinner(y)
                }
                this.mActivePointerId = -1
                return false
            case 2:
                Int iFindPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId)
                if (iFindPointerIndex2 < 0) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.")
                    return false
                }
                Float y2 = motionEvent.getY(iFindPointerIndex2)
                startDragging(y2)
                if (this.mIsBeingDragged) {
                    Float f = (y2 - this.mInitialMotionY) * DRAG_RATE
                    if (f <= 0.0f) {
                        return false
                    }
                    moveSpinner(f)
                }
                return true
            case 3:
                return false
            case 4:
            default:
                return true
            case 5:
                Int actionIndex = motionEvent.getActionIndex()
                if (actionIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.")
                    return false
                }
                this.mActivePointerId = motionEvent.getPointerId(actionIndex)
                return true
            case 6:
                onSecondaryPointerUp(motionEvent)
                return true
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestDisallowInterceptTouchEvent(Boolean z) {
        if (Build.VERSION.SDK_INT >= 21 || !(this.mTarget is AbsListView)) {
            if (this.mTarget == null || ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
                super.requestDisallowInterceptTouchEvent(z)
            }
        }
    }

    Unit reset() {
        this.mCircleView.clearAnimation()
        this.mProgress.stop()
        this.mCircleView.setVisibility(8)
        setColorViewAlpha(255)
        if (this.mScale) {
            setAnimationProgress(0.0f)
        } else {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCurrentTargetOffsetTop)
        }
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop()
    }

    Unit setAnimationProgress(Float f) {
        this.mCircleView.setScaleX(f)
        this.mCircleView.setScaleY(f)
    }

    @Deprecated
    fun setColorScheme(@ColorRes Int... iArr) {
        setColorSchemeResources(iArr)
    }

    fun setColorSchemeColors(@ColorInt Int... iArr) {
        ensureTarget()
        this.mProgress.setColorSchemeColors(iArr)
    }

    fun setColorSchemeResources(@ColorRes Int... iArr) {
        Context context = getContext()
        Array<Int> iArr2 = new Int[iArr.length]
        for (Int i = 0; i < iArr.length; i++) {
            iArr2[i] = ContextCompat.getColor(context, iArr[i])
        }
        setColorSchemeColors(iArr2)
    }

    fun setDistanceToTriggerSync(Int i) {
        this.mTotalDragDistance = i
    }

    @Override // android.view.View
    fun setEnabled(Boolean z) {
        super.setEnabled(z)
        if (z) {
            return
        }
        reset()
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun setNestedScrollingEnabled(Boolean z) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(z)
    }

    fun setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback onChildScrollUpCallback) {
        this.mChildScrollUpCallback = onChildScrollUpCallback
    }

    fun setOnRefreshListener(@Nullable OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener
    }

    @Deprecated
    fun setProgressBackgroundColor(Int i) {
        setProgressBackgroundColorSchemeResource(i)
    }

    fun setProgressBackgroundColorSchemeColor(@ColorInt Int i) {
        this.mCircleView.setBackgroundColor(i)
    }

    fun setProgressBackgroundColorSchemeResource(@ColorRes Int i) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), i))
    }

    fun setProgressViewEndTarget(Boolean z, Int i) {
        this.mSpinnerOffsetEnd = i
        this.mScale = z
        this.mCircleView.invalidate()
    }

    fun setProgressViewOffset(Boolean z, Int i, Int i2) {
        this.mScale = z
        this.mOriginalOffsetTop = i
        this.mSpinnerOffsetEnd = i2
        this.mUsingCustomStart = true
        reset()
        this.mRefreshing = false
    }

    fun setRefreshing(Boolean z) {
        if (!z || this.mRefreshing == z) {
            setRefreshing(z, false)
            return
        }
        this.mRefreshing = z
        setTargetOffsetTopAndBottom((!this.mUsingCustomStart ? this.mSpinnerOffsetEnd + this.mOriginalOffsetTop : this.mSpinnerOffsetEnd) - this.mCurrentTargetOffsetTop)
        this.mNotify = false
        startScaleUpAnimation(this.mRefreshListener)
    }

    fun setSize(Int i) {
        if (i == 0 || i == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics()
            if (i == 0) {
                this.mCircleDiameter = (Int) (displayMetrics.density * 56.0f)
            } else {
                this.mCircleDiameter = (Int) (displayMetrics.density * 40.0f)
            }
            this.mCircleView.setImageDrawable(null)
            this.mProgress.setStyle(i)
            this.mCircleView.setImageDrawable(this.mProgress)
        }
    }

    fun setSlingshotDistance(@Px Int i) {
        this.mCustomSlingshotDistance = i
    }

    Unit setTargetOffsetTopAndBottom(Int i) {
        this.mCircleView.bringToFront()
        ViewCompat.offsetTopAndBottom(this.mCircleView, i)
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop()
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun startNestedScroll(Int i) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i)
    }

    Unit startScaleDownAnimation(Animation.AnimationListener animationListener) {
        this.mScaleDownAnimation = Animation() { // from class: android.support.v4.widget.SwipeRefreshLayout.3
            @Override // android.view.animation.Animation
            fun applyTransformation(Float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f)
            }
        }
        this.mScaleDownAnimation.setDuration(150L)
        this.mCircleView.setAnimationListener(animationListener)
        this.mCircleView.clearAnimation()
        this.mCircleView.startAnimation(this.mScaleDownAnimation)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll()
    }
}
