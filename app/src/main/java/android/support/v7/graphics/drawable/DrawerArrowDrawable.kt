package android.support.v7.graphics.drawable

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.RestrictTo
import androidx.core.graphics.drawable.DrawableCompat
import androidx.appcompat.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class DrawerArrowDrawable extends Drawable {
    public static val ARROW_DIRECTION_END = 3
    public static val ARROW_DIRECTION_LEFT = 0
    public static val ARROW_DIRECTION_RIGHT = 1
    public static val ARROW_DIRECTION_START = 2
    private static val ARROW_HEAD_ANGLE = (Float) Math.toRadians(45.0d)
    private Float mArrowHeadLength
    private Float mArrowShaftLength
    private Float mBarGap
    private Float mBarLength
    private Float mMaxCutForBarSize
    private Float mProgress
    private final Int mSize
    private Boolean mSpin
    private val mPaint = Paint()
    private val mPath = Path()
    private Boolean mVerticalMirror = false
    private Int mDirection = 2

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ArrowDirection {
    }

    constructor(Context context) {
        this.mPaint.setStyle(Paint.Style.STROKE)
        this.mPaint.setStrokeJoin(Paint.Join.MITER)
        this.mPaint.setStrokeCap(Paint.Cap.BUTT)
        this.mPaint.setAntiAlias(true)
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle)
        setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DrawerArrowToggle_color, 0))
        setBarThickness(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0f))
        setSpinEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true))
        setGapSize(Math.round(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f)))
        this.mSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0)
        this.mBarLength = Math.round(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0f))
        this.mArrowHeadLength = Math.round(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0f))
        this.mArrowShaftLength = typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0f)
        typedArrayObtainStyledAttributes.recycle()
    }

    private fun lerp(Float f, Float f2, Float f3) {
        return ((f2 - f) * f3) + f
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        Boolean z
        Rect bounds = getBounds()
        switch (this.mDirection) {
            case 0:
                z = false
                break
            case 1:
                z = true
                break
            case 2:
            default:
                if (DrawableCompat.getLayoutDirection(this) != 1) {
                    z = false
                    break
                } else {
                    z = true
                    break
                }
            case 3:
                if (DrawableCompat.getLayoutDirection(this) != 0) {
                    z = false
                    break
                } else {
                    z = true
                    break
                }
        }
        Float fLerp = lerp(this.mBarLength, (Float) Math.sqrt(this.mArrowHeadLength * this.mArrowHeadLength * 2.0f), this.mProgress)
        Float fLerp2 = lerp(this.mBarLength, this.mArrowShaftLength, this.mProgress)
        Float fRound = Math.round(lerp(0.0f, this.mMaxCutForBarSize, this.mProgress))
        Float fLerp3 = lerp(0.0f, ARROW_HEAD_ANGLE, this.mProgress)
        Float fLerp4 = lerp(z ? 0.0f : -180.0f, z ? 180.0f : 0.0f, this.mProgress)
        Float fRound2 = Math.round(fLerp * Math.cos(fLerp3))
        Float fRound3 = Math.round(fLerp * Math.sin(fLerp3))
        this.mPath.rewind()
        Float fLerp5 = lerp(this.mBarGap + this.mPaint.getStrokeWidth(), -this.mMaxCutForBarSize, this.mProgress)
        Float f = (-fLerp2) / 2.0f
        this.mPath.moveTo(f + fRound, 0.0f)
        this.mPath.rLineTo(fLerp2 - (fRound * 2.0f), 0.0f)
        this.mPath.moveTo(f, fLerp5)
        this.mPath.rLineTo(fRound2, fRound3)
        this.mPath.moveTo(f, -fLerp5)
        this.mPath.rLineTo(fRound2, -fRound3)
        this.mPath.close()
        canvas.save()
        canvas.translate(bounds.centerX(), (this.mPaint.getStrokeWidth() * 1.5f) + this.mBarGap + ((((Int) ((bounds.height() - (3.0f * r2)) - (this.mBarGap * 2.0f))) / 4) << 1))
        if (this.mSpin) {
            canvas.rotate((z ^ this.mVerticalMirror ? -1 : 1) * fLerp4)
        } else if (z) {
            canvas.rotate(180.0f)
        }
        canvas.drawPath(this.mPath, this.mPaint)
        canvas.restore()
    }

    fun getArrowHeadLength() {
        return this.mArrowHeadLength
    }

    fun getArrowShaftLength() {
        return this.mArrowShaftLength
    }

    fun getBarLength() {
        return this.mBarLength
    }

    fun getBarThickness() {
        return this.mPaint.getStrokeWidth()
    }

    @ColorInt
    fun getColor() {
        return this.mPaint.getColor()
    }

    fun getDirection() {
        return this.mDirection
    }

    fun getGapSize() {
        return this.mBarGap
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicHeight() {
        return this.mSize
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicWidth() {
        return this.mSize
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        return -3
    }

    public final Paint getPaint() {
        return this.mPaint
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    fun getProgress() {
        return this.mProgress
    }

    fun isSpinEnabled() {
        return this.mSpin
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i)
            invalidateSelf()
        }
    }

    fun setArrowHeadLength(Float f) {
        if (this.mArrowHeadLength != f) {
            this.mArrowHeadLength = f
            invalidateSelf()
        }
    }

    fun setArrowShaftLength(Float f) {
        if (this.mArrowShaftLength != f) {
            this.mArrowShaftLength = f
            invalidateSelf()
        }
    }

    fun setBarLength(Float f) {
        if (this.mBarLength != f) {
            this.mBarLength = f
            invalidateSelf()
        }
    }

    fun setBarThickness(Float f) {
        if (this.mPaint.getStrokeWidth() != f) {
            this.mPaint.setStrokeWidth(f)
            this.mMaxCutForBarSize = (Float) ((f / 2.0f) * Math.cos(ARROW_HEAD_ANGLE))
            invalidateSelf()
        }
    }

    fun setColor(@ColorInt Int i) {
        if (i != this.mPaint.getColor()) {
            this.mPaint.setColor(i)
            invalidateSelf()
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter)
        invalidateSelf()
    }

    fun setDirection(Int i) {
        if (i != this.mDirection) {
            this.mDirection = i
            invalidateSelf()
        }
    }

    fun setGapSize(Float f) {
        if (f != this.mBarGap) {
            this.mBarGap = f
            invalidateSelf()
        }
    }

    fun setProgress(@FloatRange(from = 0.0d, to = 1.0d) Float f) {
        if (this.mProgress != f) {
            this.mProgress = f
            invalidateSelf()
        }
    }

    fun setSpinEnabled(Boolean z) {
        if (this.mSpin != z) {
            this.mSpin = z
            invalidateSelf()
        }
    }

    fun setVerticalMirror(Boolean z) {
        if (this.mVerticalMirror != z) {
            this.mVerticalMirror = z
            invalidateSelf()
        }
    }
}
