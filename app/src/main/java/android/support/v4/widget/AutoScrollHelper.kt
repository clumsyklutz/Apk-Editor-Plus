package android.support.v4.widget

import android.content.res.Resources
import android.os.SystemClock
import android.support.annotation.NonNull
import androidx.core.view.ViewCompat
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator

abstract class AutoScrollHelper implements View.OnTouchListener {
    private static val DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout()
    private static val DEFAULT_EDGE_TYPE = 1
    private static val DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE
    private static val DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575
    private static val DEFAULT_MINIMUM_VELOCITY_DIPS = 315
    private static val DEFAULT_RAMP_DOWN_DURATION = 500
    private static val DEFAULT_RAMP_UP_DURATION = 500
    private static val DEFAULT_RELATIVE_EDGE = 0.2f
    private static val DEFAULT_RELATIVE_VELOCITY = 1.0f
    public static val EDGE_TYPE_INSIDE = 0
    public static val EDGE_TYPE_INSIDE_EXTEND = 1
    public static val EDGE_TYPE_OUTSIDE = 2
    private static val HORIZONTAL = 0
    public static val NO_MAX = Float.MAX_VALUE
    public static val NO_MIN = 0.0f
    public static val RELATIVE_UNSPECIFIED = 0.0f
    private static val VERTICAL = 1
    private Int mActivationDelay
    private Boolean mAlreadyDelayed
    Boolean mAnimating
    private Int mEdgeType
    private Boolean mEnabled
    private Boolean mExclusive
    Boolean mNeedsCancel
    Boolean mNeedsReset
    private Runnable mRunnable
    final View mTarget
    val mScroller = ClampedScroller()
    private val mEdgeInterpolator = AccelerateInterpolator()
    private Array<Float> mRelativeEdges = {0.0f, 0.0f}
    private Array<Float> mMaximumEdges = {Float.MAX_VALUE, Float.MAX_VALUE}
    private Array<Float> mRelativeVelocity = {0.0f, 0.0f}
    private Array<Float> mMinimumVelocity = {0.0f, 0.0f}
    private Array<Float> mMaximumVelocity = {Float.MAX_VALUE, Float.MAX_VALUE}

    class ClampedScroller {
        private Int mEffectiveRampDown
        private Int mRampDownDuration
        private Int mRampUpDuration
        private Float mStopValue
        private Float mTargetVelocityX
        private Float mTargetVelocityY
        private Long mStartTime = Long.MIN_VALUE
        private Long mStopTime = -1
        private Long mDeltaTime = 0
        private Int mDeltaX = 0
        private Int mDeltaY = 0

        ClampedScroller() {
        }

        private fun getValueAt(Long j) {
            if (j < this.mStartTime) {
                return 0.0f
            }
            if (this.mStopTime < 0 || j < this.mStopTime) {
                return AutoScrollHelper.constrain((j - this.mStartTime) / this.mRampUpDuration, 0.0f, AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY) * 0.5f
            }
            return (AutoScrollHelper.constrain((j - this.mStopTime) / this.mEffectiveRampDown, 0.0f, AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY) * this.mStopValue) + (AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY - this.mStopValue)
        }

        private fun interpolateValue(Float f) {
            return ((-4.0f) * f * f) + (4.0f * f)
        }

        fun computeScrollDelta() {
            if (this.mDeltaTime == 0) {
                throw RuntimeException("Cannot compute scroll delta before calling start()")
            }
            Long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis()
            Float fInterpolateValue = interpolateValue(getValueAt(jCurrentAnimationTimeMillis))
            Long j = jCurrentAnimationTimeMillis - this.mDeltaTime
            this.mDeltaTime = jCurrentAnimationTimeMillis
            this.mDeltaX = (Int) (j * fInterpolateValue * this.mTargetVelocityX)
            this.mDeltaY = (Int) (j * fInterpolateValue * this.mTargetVelocityY)
        }

        fun getDeltaX() {
            return this.mDeltaX
        }

        fun getDeltaY() {
            return this.mDeltaY
        }

        fun getHorizontalDirection() {
            return (Int) (this.mTargetVelocityX / Math.abs(this.mTargetVelocityX))
        }

        fun getVerticalDirection() {
            return (Int) (this.mTargetVelocityY / Math.abs(this.mTargetVelocityY))
        }

        fun isFinished() {
            return this.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + ((Long) this.mEffectiveRampDown)
        }

        fun requestStop() {
            Long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis()
            this.mEffectiveRampDown = AutoScrollHelper.constrain((Int) (jCurrentAnimationTimeMillis - this.mStartTime), 0, this.mRampDownDuration)
            this.mStopValue = getValueAt(jCurrentAnimationTimeMillis)
            this.mStopTime = jCurrentAnimationTimeMillis
        }

        fun setRampDownDuration(Int i) {
            this.mRampDownDuration = i
        }

        fun setRampUpDuration(Int i) {
            this.mRampUpDuration = i
        }

        fun setTargetVelocity(Float f, Float f2) {
            this.mTargetVelocityX = f
            this.mTargetVelocityY = f2
        }

        fun start() {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis()
            this.mStopTime = -1L
            this.mDeltaTime = this.mStartTime
            this.mStopValue = 0.5f
            this.mDeltaX = 0
            this.mDeltaY = 0
        }
    }

    class ScrollAnimationRunnable implements Runnable {
        ScrollAnimationRunnable() {
        }

        @Override // java.lang.Runnable
        fun run() {
            if (AutoScrollHelper.this.mAnimating) {
                if (AutoScrollHelper.this.mNeedsReset) {
                    AutoScrollHelper.this.mNeedsReset = false
                    AutoScrollHelper.this.mScroller.start()
                }
                ClampedScroller clampedScroller = AutoScrollHelper.this.mScroller
                if (clampedScroller.isFinished() || !AutoScrollHelper.this.shouldAnimate()) {
                    AutoScrollHelper.this.mAnimating = false
                    return
                }
                if (AutoScrollHelper.this.mNeedsCancel) {
                    AutoScrollHelper.this.mNeedsCancel = false
                    AutoScrollHelper.this.cancelTargetTouch()
                }
                clampedScroller.computeScrollDelta()
                AutoScrollHelper.this.scrollTargetBy(clampedScroller.getDeltaX(), clampedScroller.getDeltaY())
                ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this)
            }
        }
    }

    constructor(@NonNull View view) {
        this.mTarget = view
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics()
        Int i = (Int) ((1575.0f * displayMetrics.density) + 0.5f)
        Int i2 = (Int) ((displayMetrics.density * 315.0f) + 0.5f)
        setMaximumVelocity(i, i)
        setMinimumVelocity(i2, i2)
        setEdgeType(1)
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE)
        setRelativeEdges(DEFAULT_RELATIVE_EDGE, DEFAULT_RELATIVE_EDGE)
        setRelativeVelocity(DEFAULT_RELATIVE_VELOCITY, DEFAULT_RELATIVE_VELOCITY)
        setActivationDelay(DEFAULT_ACTIVATION_DELAY)
        setRampUpDuration(500)
        setRampDownDuration(500)
    }

    private fun computeTargetVelocity(Int i, Float f, Float f2, Float f3) {
        Float edgeValue = getEdgeValue(this.mRelativeEdges[i], f2, this.mMaximumEdges[i], f)
        if (edgeValue == 0.0f) {
            return 0.0f
        }
        Float f4 = this.mRelativeVelocity[i]
        Float f5 = this.mMinimumVelocity[i]
        Float f6 = this.mMaximumVelocity[i]
        Float f7 = f4 * f3
        return edgeValue > 0.0f ? constrain(edgeValue * f7, f5, f6) : -constrain((-edgeValue) * f7, f5, f6)
    }

    static Float constrain(Float f, Float f2, Float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f
    }

    static Int constrain(Int i, Int i2, Int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i
    }

    private fun constrainEdgeValue(Float f, Float f2) {
        if (f2 == 0.0f) {
            return 0.0f
        }
        switch (this.mEdgeType) {
            case 0:
            case 1:
                if (f < f2) {
                    if (f < 0.0f) {
                        if (this.mAnimating && this.mEdgeType == 1) {
                            break
                        }
                    } else {
                        break
                    }
                }
                break
            case 2:
                if (f < 0.0f) {
                    break
                }
                break
        }
        return 0.0f
    }

    private fun getEdgeValue(Float f, Float f2, Float f3, Float f4) {
        Float interpolation
        Float fConstrain = constrain(f * f2, 0.0f, f3)
        Float fConstrainEdgeValue = constrainEdgeValue(f2 - f4, fConstrain) - constrainEdgeValue(f4, fConstrain)
        if (fConstrainEdgeValue < 0.0f) {
            interpolation = -this.mEdgeInterpolator.getInterpolation(-fConstrainEdgeValue)
        } else {
            if (fConstrainEdgeValue <= 0.0f) {
                return 0.0f
            }
            interpolation = this.mEdgeInterpolator.getInterpolation(fConstrainEdgeValue)
        }
        return constrain(interpolation, -1.0f, DEFAULT_RELATIVE_VELOCITY)
    }

    private fun requestStop() {
        if (this.mNeedsReset) {
            this.mAnimating = false
        } else {
            this.mScroller.requestStop()
        }
    }

    private fun startAnimating() {
        if (this.mRunnable == null) {
            this.mRunnable = ScrollAnimationRunnable()
        }
        this.mAnimating = true
        this.mNeedsReset = true
        if (this.mAlreadyDelayed || this.mActivationDelay <= 0) {
            this.mRunnable.run()
        } else {
            ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, this.mActivationDelay)
        }
        this.mAlreadyDelayed = true
    }

    public abstract Boolean canTargetScrollHorizontally(Int i)

    public abstract Boolean canTargetScrollVertically(Int i)

    Unit cancelTargetTouch() {
        Long jUptimeMillis = SystemClock.uptimeMillis()
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0)
        this.mTarget.onTouchEvent(motionEventObtain)
        motionEventObtain.recycle()
    }

    fun isEnabled() {
        return this.mEnabled
    }

    fun isExclusive() {
        return this.mExclusive
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnTouchListener
    fun onTouch(View view, MotionEvent motionEvent) {
        if (!this.mEnabled) {
            return false
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mNeedsCancel = true
                this.mAlreadyDelayed = false
                this.mScroller.setTargetVelocity(computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth()), computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight()))
                if (!this.mAnimating && shouldAnimate()) {
                    startAnimating()
                    break
                }
                break
            case 1:
            case 3:
                requestStop()
                break
            case 2:
                this.mScroller.setTargetVelocity(computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth()), computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight()))
                if (!this.mAnimating) {
                    startAnimating()
                    break
                }
                break
        }
        return this.mExclusive && this.mAnimating
    }

    public abstract Unit scrollTargetBy(Int i, Int i2)

    @NonNull
    fun setActivationDelay(Int i) {
        this.mActivationDelay = i
        return this
    }

    @NonNull
    fun setEdgeType(Int i) {
        this.mEdgeType = i
        return this
    }

    fun setEnabled(Boolean z) {
        if (this.mEnabled && !z) {
            requestStop()
        }
        this.mEnabled = z
        return this
    }

    fun setExclusive(Boolean z) {
        this.mExclusive = z
        return this
    }

    @NonNull
    fun setMaximumEdges(Float f, Float f2) {
        this.mMaximumEdges[0] = f
        this.mMaximumEdges[1] = f2
        return this
    }

    @NonNull
    fun setMaximumVelocity(Float f, Float f2) {
        this.mMaximumVelocity[0] = f / 1000.0f
        this.mMaximumVelocity[1] = f2 / 1000.0f
        return this
    }

    @NonNull
    fun setMinimumVelocity(Float f, Float f2) {
        this.mMinimumVelocity[0] = f / 1000.0f
        this.mMinimumVelocity[1] = f2 / 1000.0f
        return this
    }

    @NonNull
    fun setRampDownDuration(Int i) {
        this.mScroller.setRampDownDuration(i)
        return this
    }

    @NonNull
    fun setRampUpDuration(Int i) {
        this.mScroller.setRampUpDuration(i)
        return this
    }

    @NonNull
    fun setRelativeEdges(Float f, Float f2) {
        this.mRelativeEdges[0] = f
        this.mRelativeEdges[1] = f2
        return this
    }

    @NonNull
    fun setRelativeVelocity(Float f, Float f2) {
        this.mRelativeVelocity[0] = f / 1000.0f
        this.mRelativeVelocity[1] = f2 / 1000.0f
        return this
    }

    Boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller
        Int verticalDirection = clampedScroller.getVerticalDirection()
        Int horizontalDirection = clampedScroller.getHorizontalDirection()
        return (verticalDirection != 0 && canTargetScrollVertically(verticalDirection)) || (horizontalDirection != 0 && canTargetScrollHorizontally(horizontalDirection))
    }
}
