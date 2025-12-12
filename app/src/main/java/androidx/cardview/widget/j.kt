package androidx.cardview.widget

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable

class j extends Drawable {
    private static val COS_45 = Math.cos(Math.toRadians(45.0d))
    private static val SHADOW_MULTIPLIER = 1.5f
    static k sRoundRectHelper
    private ColorStateList mBackground
    private final RectF mCardBounds
    private Float mCornerRadius
    private Paint mCornerShadowPaint
    private Path mCornerShadowPath
    private Paint mEdgeShadowPaint
    private final Int mInsetShadow
    private Float mRawMaxShadowSize
    private Float mRawShadowSize
    private final Int mShadowEndColor
    private Float mShadowSize
    private final Int mShadowStartColor
    private Boolean mDirty = true
    private Boolean mAddPaddingForCorners = true
    private Boolean mPrintedShadowClipWarning = false
    private Paint mPaint = Paint(5)

    j(Resources resources, ColorStateList colorStateList, Float f, Float f2, Float f3) {
        this.mShadowStartColor = resources.getColor(n.cardview_shadow_start_color)
        this.mShadowEndColor = resources.getColor(n.cardview_shadow_end_color)
        this.mInsetShadow = resources.getDimensionPixelSize(o.cardview_compat_inset_shadow)
        setBackground(colorStateList)
        this.mCornerShadowPaint = Paint(5)
        this.mCornerShadowPaint.setStyle(Paint.Style.FILL)
        this.mCornerRadius = (Int) (f + 0.5f)
        this.mCardBounds = RectF()
        this.mEdgeShadowPaint = Paint(this.mCornerShadowPaint)
        this.mEdgeShadowPaint.setAntiAlias(false)
        setShadowSize(f2, f3)
    }

    private fun buildComponents(Rect rect) {
        Float f = this.mRawMaxShadowSize * SHADOW_MULTIPLIER
        this.mCardBounds.set(rect.left + this.mRawMaxShadowSize, rect.top + f, rect.right - this.mRawMaxShadowSize, rect.bottom - f)
        buildShadowCorners()
    }

    private fun buildShadowCorners() {
        Float f = this.mCornerRadius
        RectF rectF = RectF(-f, -f, f, f)
        RectF rectF2 = RectF(rectF)
        Float f2 = this.mShadowSize
        rectF2.inset(-f2, -f2)
        Path path = this.mCornerShadowPath
        if (path == null) {
            this.mCornerShadowPath = Path()
        } else {
            path.reset()
        }
        this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD)
        this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0f)
        this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f)
        this.mCornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false)
        this.mCornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false)
        this.mCornerShadowPath.close()
        Float f3 = this.mCornerRadius
        Float f4 = this.mShadowSize
        Float f5 = f3 / (f3 + f4)
        Paint paint = this.mCornerShadowPaint
        Float f6 = f3 + f4
        Int i = this.mShadowStartColor
        paint.setShader(RadialGradient(0.0f, 0.0f, f6, new Array<Int>{i, i, this.mShadowEndColor}, new Array<Float>{0.0f, f5, 1.0f}, Shader.TileMode.CLAMP))
        Paint paint2 = this.mEdgeShadowPaint
        Float f7 = this.mCornerRadius
        Float f8 = this.mShadowSize
        Int i2 = this.mShadowStartColor
        paint2.setShader(LinearGradient(0.0f, (-f7) + f8, 0.0f, (-f7) - f8, new Array<Int>{i2, i2, this.mShadowEndColor}, new Array<Float>{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP))
        this.mEdgeShadowPaint.setAntiAlias(false)
    }

    static Float calculateHorizontalPadding(Float f, Float f2, Boolean z) {
        return z ? (Float) (f + ((1.0d - COS_45) * f2)) : f
    }

    static Float calculateVerticalPadding(Float f, Float f2, Boolean z) {
        return z ? (Float) ((f * SHADOW_MULTIPLIER) + ((1.0d - COS_45) * f2)) : f * SHADOW_MULTIPLIER
    }

    private fun drawShadow(Canvas canvas) {
        Float f = this.mCornerRadius
        Float f2 = (-f) - this.mShadowSize
        Float f3 = f + this.mInsetShadow + (this.mRawShadowSize / 2.0f)
        Float f4 = f3 * 2.0f
        Boolean z = this.mCardBounds.width() - f4 > 0.0f
        Boolean z2 = this.mCardBounds.height() - f4 > 0.0f
        Int iSave = canvas.save()
        canvas.translate(this.mCardBounds.left + f3, this.mCardBounds.top + f3)
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint)
        if (z) {
            canvas.drawRect(0.0f, f2, this.mCardBounds.width() - f4, -this.mCornerRadius, this.mEdgeShadowPaint)
        }
        canvas.restoreToCount(iSave)
        Int iSave2 = canvas.save()
        canvas.translate(this.mCardBounds.right - f3, this.mCardBounds.bottom - f3)
        canvas.rotate(180.0f)
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint)
        if (z) {
            canvas.drawRect(0.0f, f2, this.mCardBounds.width() - f4, (-this.mCornerRadius) + this.mShadowSize, this.mEdgeShadowPaint)
        }
        canvas.restoreToCount(iSave2)
        Int iSave3 = canvas.save()
        canvas.translate(this.mCardBounds.left + f3, this.mCardBounds.bottom - f3)
        canvas.rotate(270.0f)
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint)
        if (z2) {
            canvas.drawRect(0.0f, f2, this.mCardBounds.height() - f4, -this.mCornerRadius, this.mEdgeShadowPaint)
        }
        canvas.restoreToCount(iSave3)
        Int iSave4 = canvas.save()
        canvas.translate(this.mCardBounds.right - f3, this.mCardBounds.top + f3)
        canvas.rotate(90.0f)
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint)
        if (z2) {
            canvas.drawRect(0.0f, f2, this.mCardBounds.height() - f4, -this.mCornerRadius, this.mEdgeShadowPaint)
        }
        canvas.restoreToCount(iSave4)
    }

    private fun setBackground(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0)
        }
        this.mBackground = colorStateList
        this.mPaint.setColor(this.mBackground.getColorForState(getState(), this.mBackground.getDefaultColor()))
    }

    private fun setShadowSize(Float f, Float f2) {
        if (f < 0.0f) {
            throw IllegalArgumentException("Invalid shadow size " + f + ". Must be >= 0")
        }
        if (f2 < 0.0f) {
            throw IllegalArgumentException("Invalid max shadow size " + f2 + ". Must be >= 0")
        }
        Float even = toEven(f)
        Float even2 = toEven(f2)
        if (even > even2) {
            if (!this.mPrintedShadowClipWarning) {
                this.mPrintedShadowClipWarning = true
            }
            even = even2
        }
        if (this.mRawShadowSize == even && this.mRawMaxShadowSize == even2) {
            return
        }
        this.mRawShadowSize = even
        this.mRawMaxShadowSize = even2
        this.mShadowSize = (Int) ((even * SHADOW_MULTIPLIER) + this.mInsetShadow + 0.5f)
        this.mDirty = true
        invalidateSelf()
    }

    private fun toEven(Float f) {
        Int i = (Int) (f + 0.5f)
        return i % 2 == 1 ? i - 1 : i
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        if (this.mDirty) {
            buildComponents(getBounds())
            this.mDirty = false
        }
        canvas.translate(0.0f, this.mRawShadowSize / 2.0f)
        drawShadow(canvas)
        canvas.translate(0.0f, (-this.mRawShadowSize) / 2.0f)
        sRoundRectHelper.drawRoundRect(canvas, this.mCardBounds, this.mCornerRadius, this.mPaint)
    }

    ColorStateList getColor() {
        return this.mBackground
    }

    Float getCornerRadius() {
        return this.mCornerRadius
    }

    Unit getMaxShadowAndCornerPadding(Rect rect) {
        getPadding(rect)
    }

    Float getMaxShadowSize() {
        return this.mRawMaxShadowSize
    }

    Float getMinHeight() {
        Float f = this.mRawMaxShadowSize
        return (Math.max(f, this.mCornerRadius + this.mInsetShadow + ((f * SHADOW_MULTIPLIER) / 2.0f)) * 2.0f) + (((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) + this.mInsetShadow) * 2.0f)
    }

    Float getMinWidth() {
        Float f = this.mRawMaxShadowSize
        return (Math.max(f, this.mCornerRadius + this.mInsetShadow + (f / 2.0f)) * 2.0f) + ((this.mRawMaxShadowSize + this.mInsetShadow) * 2.0f)
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        return -3
    }

    @Override // android.graphics.drawable.Drawable
    fun getPadding(Rect rect) {
        Int iCeil = (Int) Math.ceil(calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners))
        Int iCeil2 = (Int) Math.ceil(calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners))
        rect.set(iCeil2, iCeil, iCeil2, iCeil)
        return true
    }

    Float getShadowSize() {
        return this.mRawShadowSize
    }

    @Override // android.graphics.drawable.Drawable
    fun isStateful() {
        ColorStateList colorStateList = this.mBackground
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful()
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        super.onBoundsChange(rect)
        this.mDirty = true
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onStateChange(Array<Int> iArr) {
        ColorStateList colorStateList = this.mBackground
        Int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor())
        if (this.mPaint.getColor() == colorForState) {
            return false
        }
        this.mPaint.setColor(colorForState)
        this.mDirty = true
        invalidateSelf()
        return true
    }

    Unit setAddPaddingForCorners(Boolean z) {
        this.mAddPaddingForCorners = z
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        this.mPaint.setAlpha(i)
        this.mCornerShadowPaint.setAlpha(i)
        this.mEdgeShadowPaint.setAlpha(i)
    }

    Unit setColor(ColorStateList colorStateList) {
        setBackground(colorStateList)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter)
    }

    Unit setCornerRadius(Float f) {
        if (f < 0.0f) {
            throw IllegalArgumentException("Invalid radius " + f + ". Must be >= 0")
        }
        Float f2 = (Int) (f + 0.5f)
        if (this.mCornerRadius == f2) {
            return
        }
        this.mCornerRadius = f2
        this.mDirty = true
        invalidateSelf()
    }

    Unit setMaxShadowSize(Float f) {
        setShadowSize(this.mRawShadowSize, f)
    }

    Unit setShadowSize(Float f) {
        setShadowSize(f, this.mRawMaxShadowSize)
    }
}
