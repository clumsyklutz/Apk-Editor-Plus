package android.support.v4.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import android.support.v4.util.Preconditions
import androidx.core.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class CircularProgressDrawable extends Drawable implements Animatable {
    private static val ANIMATION_DURATION = 1332
    private static val ARROW_HEIGHT = 5
    private static val ARROW_HEIGHT_LARGE = 6
    private static val ARROW_WIDTH = 10
    private static val ARROW_WIDTH_LARGE = 12
    private static val CENTER_RADIUS = 7.5f
    private static val CENTER_RADIUS_LARGE = 11.0f
    private static val COLOR_CHANGE_OFFSET = 0.75f
    public static val DEFAULT = 1
    private static val GROUP_FULL_ROTATION = 216.0f
    public static val LARGE = 0
    private static val MAX_PROGRESS_ARC = 0.8f
    private static val MIN_PROGRESS_ARC = 0.01f
    private static val RING_ROTATION = 0.20999998f
    private static val SHRINK_OFFSET = 0.5f
    private static val STROKE_WIDTH = 2.5f
    private static val STROKE_WIDTH_LARGE = 3.0f
    private Animator mAnimator
    Boolean mFinishing
    private Resources mResources
    private val mRing = Ring()
    private Float mRotation
    Float mRotationCount
    private static val LINEAR_INTERPOLATOR = LinearInterpolator()
    private static val MATERIAL_INTERPOLATOR = FastOutSlowInInterpolator()
    private static final Array<Int> COLORS = {ViewCompat.MEASURED_STATE_MASK}

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ProgressDrawableSize {
    }

    class Ring {
        Path mArrow
        Int mArrowHeight
        Int mArrowWidth
        Int mColorIndex
        Array<Int> mColors
        Int mCurrentColor
        Float mRingCenterRadius
        Boolean mShowArrow
        Float mStartingEndTrim
        Float mStartingRotation
        Float mStartingStartTrim
        val mTempBounds = RectF()
        val mPaint = Paint()
        val mArrowPaint = Paint()
        val mCirclePaint = Paint()
        Float mStartTrim = 0.0f
        Float mEndTrim = 0.0f
        Float mRotation = 0.0f
        Float mStrokeWidth = 5.0f
        Float mArrowScale = 1.0f
        Int mAlpha = 255

        Ring() {
            this.mPaint.setStrokeCap(Paint.Cap.SQUARE)
            this.mPaint.setAntiAlias(true)
            this.mPaint.setStyle(Paint.Style.STROKE)
            this.mArrowPaint.setStyle(Paint.Style.FILL)
            this.mArrowPaint.setAntiAlias(true)
            this.mCirclePaint.setColor(0)
        }

        Unit draw(Canvas canvas, Rect rect) {
            RectF rectF = this.mTempBounds
            Float fMin = this.mRingCenterRadius + (this.mStrokeWidth / 2.0f)
            if (this.mRingCenterRadius <= 0.0f) {
                fMin = (Math.min(rect.width(), rect.height()) / 2.0f) - Math.max((this.mArrowWidth * this.mArrowScale) / 2.0f, this.mStrokeWidth / 2.0f)
            }
            rectF.set(rect.centerX() - fMin, rect.centerY() - fMin, rect.centerX() + fMin, fMin + rect.centerY())
            Float f = (this.mStartTrim + this.mRotation) * 360.0f
            Float f2 = ((this.mEndTrim + this.mRotation) * 360.0f) - f
            this.mPaint.setColor(this.mCurrentColor)
            this.mPaint.setAlpha(this.mAlpha)
            Float f3 = this.mStrokeWidth / 2.0f
            rectF.inset(f3, f3)
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.mCirclePaint)
            rectF.inset(-f3, -f3)
            canvas.drawArc(rectF, f, f2, false, this.mPaint)
            drawTriangle(canvas, f, f2, rectF)
        }

        Unit drawTriangle(Canvas canvas, Float f, Float f2, RectF rectF) {
            if (this.mShowArrow) {
                if (this.mArrow == null) {
                    this.mArrow = Path()
                    this.mArrow.setFillType(Path.FillType.EVEN_ODD)
                } else {
                    this.mArrow.reset()
                }
                Float fMin = Math.min(rectF.width(), rectF.height()) / 2.0f
                Float f3 = (this.mArrowWidth * this.mArrowScale) / 2.0f
                this.mArrow.moveTo(0.0f, 0.0f)
                this.mArrow.lineTo(this.mArrowWidth * this.mArrowScale, 0.0f)
                this.mArrow.lineTo((this.mArrowWidth * this.mArrowScale) / 2.0f, this.mArrowHeight * this.mArrowScale)
                this.mArrow.offset((fMin + rectF.centerX()) - f3, rectF.centerY() + (this.mStrokeWidth / 2.0f))
                this.mArrow.close()
                this.mArrowPaint.setColor(this.mCurrentColor)
                this.mArrowPaint.setAlpha(this.mAlpha)
                canvas.save()
                canvas.rotate(f + f2, rectF.centerX(), rectF.centerY())
                canvas.drawPath(this.mArrow, this.mArrowPaint)
                canvas.restore()
            }
        }

        Int getAlpha() {
            return this.mAlpha
        }

        Float getArrowHeight() {
            return this.mArrowHeight
        }

        Float getArrowScale() {
            return this.mArrowScale
        }

        Float getArrowWidth() {
            return this.mArrowWidth
        }

        Int getBackgroundColor() {
            return this.mCirclePaint.getColor()
        }

        Float getCenterRadius() {
            return this.mRingCenterRadius
        }

        Array<Int> getColors() {
            return this.mColors
        }

        Float getEndTrim() {
            return this.mEndTrim
        }

        Int getNextColor() {
            return this.mColors[getNextColorIndex()]
        }

        Int getNextColorIndex() {
            return (this.mColorIndex + 1) % this.mColors.length
        }

        Float getRotation() {
            return this.mRotation
        }

        Boolean getShowArrow() {
            return this.mShowArrow
        }

        Float getStartTrim() {
            return this.mStartTrim
        }

        Int getStartingColor() {
            return this.mColors[this.mColorIndex]
        }

        Float getStartingEndTrim() {
            return this.mStartingEndTrim
        }

        Float getStartingRotation() {
            return this.mStartingRotation
        }

        Float getStartingStartTrim() {
            return this.mStartingStartTrim
        }

        Paint.Cap getStrokeCap() {
            return this.mPaint.getStrokeCap()
        }

        Float getStrokeWidth() {
            return this.mStrokeWidth
        }

        Unit goToNextColor() {
            setColorIndex(getNextColorIndex())
        }

        Unit resetOriginals() {
            this.mStartingStartTrim = 0.0f
            this.mStartingEndTrim = 0.0f
            this.mStartingRotation = 0.0f
            setStartTrim(0.0f)
            setEndTrim(0.0f)
            setRotation(0.0f)
        }

        Unit setAlpha(Int i) {
            this.mAlpha = i
        }

        Unit setArrowDimensions(Float f, Float f2) {
            this.mArrowWidth = (Int) f
            this.mArrowHeight = (Int) f2
        }

        Unit setArrowScale(Float f) {
            if (f != this.mArrowScale) {
                this.mArrowScale = f
            }
        }

        Unit setBackgroundColor(Int i) {
            this.mCirclePaint.setColor(i)
        }

        Unit setCenterRadius(Float f) {
            this.mRingCenterRadius = f
        }

        Unit setColor(Int i) {
            this.mCurrentColor = i
        }

        Unit setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter)
        }

        Unit setColorIndex(Int i) {
            this.mColorIndex = i
            this.mCurrentColor = this.mColors[this.mColorIndex]
        }

        Unit setColors(@NonNull Array<Int> iArr) {
            this.mColors = iArr
            setColorIndex(0)
        }

        Unit setEndTrim(Float f) {
            this.mEndTrim = f
        }

        Unit setRotation(Float f) {
            this.mRotation = f
        }

        Unit setShowArrow(Boolean z) {
            if (this.mShowArrow != z) {
                this.mShowArrow = z
            }
        }

        Unit setStartTrim(Float f) {
            this.mStartTrim = f
        }

        Unit setStrokeCap(Paint.Cap cap) {
            this.mPaint.setStrokeCap(cap)
        }

        Unit setStrokeWidth(Float f) {
            this.mStrokeWidth = f
            this.mPaint.setStrokeWidth(f)
        }

        Unit storeOriginals() {
            this.mStartingStartTrim = this.mStartTrim
            this.mStartingEndTrim = this.mEndTrim
            this.mStartingRotation = this.mRotation
        }
    }

    constructor(@NonNull Context context) {
        this.mResources = ((Context) Preconditions.checkNotNull(context)).getResources()
        this.mRing.setColors(COLORS)
        setStrokeWidth(STROKE_WIDTH)
        setupAnimators()
    }

    private fun applyFinishTranslation(Float f, Ring ring) {
        updateRingColor(f, ring)
        Float fFloor = (Float) (Math.floor(ring.getStartingRotation() / MAX_PROGRESS_ARC) + 1.0d)
        ring.setStartTrim(ring.getStartingStartTrim() + (((ring.getStartingEndTrim() - MIN_PROGRESS_ARC) - ring.getStartingStartTrim()) * f))
        ring.setEndTrim(ring.getStartingEndTrim())
        ring.setRotation(((fFloor - ring.getStartingRotation()) * f) + ring.getStartingRotation())
    }

    private fun evaluateColorChange(Float f, Int i, Int i2) {
        return (((i >>> 24) + ((Int) (((i2 >>> 24) - r0) * f))) << 24) | ((((i >> 16) & 255) + ((Int) ((((i2 >> 16) & 255) - r1) * f))) << 16) | ((((Int) ((((i2 >> 8) & 255) - r2) * f)) + ((i >> 8) & 255)) << 8) | (((Int) (((i2 & 255) - r3) * f)) + (i & 255))
    }

    private fun getRotation() {
        return this.mRotation
    }

    private fun setRotation(Float f) {
        this.mRotation = f
    }

    private fun setSizeParameters(Float f, Float f2, Float f3, Float f4) {
        Ring ring = this.mRing
        Float f5 = this.mResources.getDisplayMetrics().density
        ring.setStrokeWidth(f2 * f5)
        ring.setCenterRadius(f * f5)
        ring.setColorIndex(0)
        ring.setArrowDimensions(f3 * f5, f5 * f4)
    }

    private fun setupAnimators() {
        val ring = this.mRing
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f)
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.support.v4.widget.CircularProgressDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            fun onAnimationUpdate(ValueAnimator valueAnimator) {
                Float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue()
                CircularProgressDrawable.this.updateRingColor(fFloatValue, ring)
                CircularProgressDrawable.this.applyTransformation(fFloatValue, ring, false)
                CircularProgressDrawable.this.invalidateSelf()
            }
        })
        valueAnimatorOfFloat.setRepeatCount(-1)
        valueAnimatorOfFloat.setRepeatMode(1)
        valueAnimatorOfFloat.setInterpolator(LINEAR_INTERPOLATOR)
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: android.support.v4.widget.CircularProgressDrawable.2
            @Override // android.animation.Animator.AnimatorListener
            fun onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            fun onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            fun onAnimationRepeat(Animator animator) {
                CircularProgressDrawable.this.applyTransformation(1.0f, ring, true)
                ring.storeOriginals()
                ring.goToNextColor()
                if (!CircularProgressDrawable.this.mFinishing) {
                    CircularProgressDrawable.this.mRotationCount += 1.0f
                    return
                }
                CircularProgressDrawable.this.mFinishing = false
                animator.cancel()
                animator.setDuration(1332L)
                animator.start()
                ring.setShowArrow(false)
            }

            @Override // android.animation.Animator.AnimatorListener
            fun onAnimationStart(Animator animator) {
                CircularProgressDrawable.this.mRotationCount = 0.0f
            }
        })
        this.mAnimator = valueAnimatorOfFloat
    }

    Unit applyTransformation(Float f, Ring ring, Boolean z) {
        Float startingStartTrim
        Float interpolation
        if (this.mFinishing) {
            applyFinishTranslation(f, ring)
            return
        }
        if (f != 1.0f || z) {
            Float startingRotation = ring.getStartingRotation()
            if (f < SHRINK_OFFSET) {
                Float f2 = f / SHRINK_OFFSET
                interpolation = ring.getStartingStartTrim()
                startingStartTrim = (MATERIAL_INTERPOLATOR.getInterpolation(f2) * 0.79f) + MIN_PROGRESS_ARC + interpolation
            } else {
                Float f3 = (f - SHRINK_OFFSET) / SHRINK_OFFSET
                startingStartTrim = ring.getStartingStartTrim() + 0.79f
                interpolation = startingStartTrim - (((1.0f - MATERIAL_INTERPOLATOR.getInterpolation(f3)) * 0.79f) + MIN_PROGRESS_ARC)
            }
            Float f4 = startingRotation + (RING_ROTATION * f)
            Float f5 = GROUP_FULL_ROTATION * (this.mRotationCount + f)
            ring.setStartTrim(interpolation)
            ring.setEndTrim(startingStartTrim)
            ring.setRotation(f4)
            setRotation(f5)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        Rect bounds = getBounds()
        canvas.save()
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY())
        this.mRing.draw(canvas, bounds)
        canvas.restore()
    }

    @Override // android.graphics.drawable.Drawable
    fun getAlpha() {
        return this.mRing.getAlpha()
    }

    fun getArrowEnabled() {
        return this.mRing.getShowArrow()
    }

    fun getArrowHeight() {
        return this.mRing.getArrowHeight()
    }

    fun getArrowScale() {
        return this.mRing.getArrowScale()
    }

    fun getArrowWidth() {
        return this.mRing.getArrowWidth()
    }

    fun getBackgroundColor() {
        return this.mRing.getBackgroundColor()
    }

    fun getCenterRadius() {
        return this.mRing.getCenterRadius()
    }

    @NonNull
    public Array<Int> getColorSchemeColors() {
        return this.mRing.getColors()
    }

    fun getEndTrim() {
        return this.mRing.getEndTrim()
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        return -3
    }

    fun getProgressRotation() {
        return this.mRing.getRotation()
    }

    fun getStartTrim() {
        return this.mRing.getStartTrim()
    }

    @NonNull
    public Paint.Cap getStrokeCap() {
        return this.mRing.getStrokeCap()
    }

    fun getStrokeWidth() {
        return this.mRing.getStrokeWidth()
    }

    @Override // android.graphics.drawable.Animatable
    fun isRunning() {
        return this.mAnimator.isRunning()
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        this.mRing.setAlpha(i)
        invalidateSelf()
    }

    fun setArrowDimensions(Float f, Float f2) {
        this.mRing.setArrowDimensions(f, f2)
        invalidateSelf()
    }

    fun setArrowEnabled(Boolean z) {
        this.mRing.setShowArrow(z)
        invalidateSelf()
    }

    fun setArrowScale(Float f) {
        this.mRing.setArrowScale(f)
        invalidateSelf()
    }

    fun setBackgroundColor(Int i) {
        this.mRing.setBackgroundColor(i)
        invalidateSelf()
    }

    fun setCenterRadius(Float f) {
        this.mRing.setCenterRadius(f)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mRing.setColorFilter(colorFilter)
        invalidateSelf()
    }

    fun setColorSchemeColors(@NonNull Int... iArr) {
        this.mRing.setColors(iArr)
        this.mRing.setColorIndex(0)
        invalidateSelf()
    }

    fun setProgressRotation(Float f) {
        this.mRing.setRotation(f)
        invalidateSelf()
    }

    fun setStartEndTrim(Float f, Float f2) {
        this.mRing.setStartTrim(f)
        this.mRing.setEndTrim(f2)
        invalidateSelf()
    }

    fun setStrokeCap(@NonNull Paint.Cap cap) {
        this.mRing.setStrokeCap(cap)
        invalidateSelf()
    }

    fun setStrokeWidth(Float f) {
        this.mRing.setStrokeWidth(f)
        invalidateSelf()
    }

    fun setStyle(Int i) {
        if (i == 0) {
            setSizeParameters(CENTER_RADIUS_LARGE, STROKE_WIDTH_LARGE, 12.0f, 6.0f)
        } else {
            setSizeParameters(CENTER_RADIUS, STROKE_WIDTH, 10.0f, 5.0f)
        }
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Animatable
    fun start() {
        this.mAnimator.cancel()
        this.mRing.storeOriginals()
        if (this.mRing.getEndTrim() != this.mRing.getStartTrim()) {
            this.mFinishing = true
            this.mAnimator.setDuration(666L)
            this.mAnimator.start()
        } else {
            this.mRing.setColorIndex(0)
            this.mRing.resetOriginals()
            this.mAnimator.setDuration(1332L)
            this.mAnimator.start()
        }
    }

    @Override // android.graphics.drawable.Animatable
    fun stop() {
        this.mAnimator.cancel()
        setRotation(0.0f)
        this.mRing.setShowArrow(false)
        this.mRing.setColorIndex(0)
        this.mRing.resetOriginals()
        invalidateSelf()
    }

    Unit updateRingColor(Float f, Ring ring) {
        if (f > COLOR_CHANGE_OFFSET) {
            ring.setColor(evaluateColorChange((f - COLOR_CHANGE_OFFSET) / 0.25f, ring.getStartingColor(), ring.getNextColor()))
        } else {
            ring.setColor(ring.getStartingColor())
        }
    }
}
