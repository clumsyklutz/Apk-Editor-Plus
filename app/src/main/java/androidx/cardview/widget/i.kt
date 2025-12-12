package androidx.cardview.widget

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable

class i extends Drawable {
    private ColorStateList mBackground
    private final RectF mBoundsF
    private final Rect mBoundsI
    private Float mPadding
    private Float mRadius
    private ColorStateList mTint
    private PorterDuffColorFilter mTintFilter
    private Boolean mInsetForPadding = false
    private Boolean mInsetForRadius = true
    private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN
    private val mPaint = Paint(5)

    i(ColorStateList colorStateList, Float f) {
        this.mRadius = f
        setBackground(colorStateList)
        this.mBoundsF = RectF()
        this.mBoundsI = Rect()
    }

    private fun createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null
        }
        return PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode)
    }

    private fun setBackground(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0)
        }
        this.mBackground = colorStateList
        this.mPaint.setColor(this.mBackground.getColorForState(getState(), this.mBackground.getDefaultColor()))
    }

    private fun updateBounds(Rect rect) {
        if (rect == null) {
            rect = getBounds()
        }
        this.mBoundsF.set(rect.left, rect.top, rect.right, rect.bottom)
        this.mBoundsI.set(rect)
        if (this.mInsetForPadding) {
            this.mBoundsI.inset((Int) Math.ceil(j.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius)), (Int) Math.ceil(j.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius)))
            this.mBoundsF.set(this.mBoundsI)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        Boolean z
        Paint paint = this.mPaint
        if (this.mTintFilter == null || paint.getColorFilter() != null) {
            z = false
        } else {
            paint.setColorFilter(this.mTintFilter)
            z = true
        }
        RectF rectF = this.mBoundsF
        Float f = this.mRadius
        canvas.drawRoundRect(rectF, f, f, paint)
        if (z) {
            paint.setColorFilter(null)
        }
    }

    fun getColor() {
        return this.mBackground
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        return -3
    }

    @Override // android.graphics.drawable.Drawable
    fun getOutline(Outline outline) {
        outline.setRoundRect(this.mBoundsI, this.mRadius)
    }

    Float getPadding() {
        return this.mPadding
    }

    fun getRadius() {
        return this.mRadius
    }

    @Override // android.graphics.drawable.Drawable
    fun isStateful() {
        ColorStateList colorStateList
        ColorStateList colorStateList2 = this.mTint
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.mBackground) != null && colorStateList.isStateful()) || super.isStateful()
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        super.onBoundsChange(rect)
        updateBounds(rect)
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onStateChange(Array<Int> iArr) {
        PorterDuff.Mode mode
        ColorStateList colorStateList = this.mBackground
        Int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor())
        Boolean z = colorForState != this.mPaint.getColor()
        if (z) {
            this.mPaint.setColor(colorForState)
        }
        ColorStateList colorStateList2 = this.mTint
        if (colorStateList2 == null || (mode = this.mTintMode) == null) {
            return z
        }
        this.mTintFilter = createTintFilter(colorStateList2, mode)
        return true
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        this.mPaint.setAlpha(i)
    }

    fun setColor(ColorStateList colorStateList) {
        setBackground(colorStateList)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter)
    }

    Unit setPadding(Float f, Boolean z, Boolean z2) {
        if (f == this.mPadding && this.mInsetForPadding == z && this.mInsetForRadius == z2) {
            return
        }
        this.mPadding = f
        this.mInsetForPadding = z
        this.mInsetForRadius = z2
        updateBounds(null)
        invalidateSelf()
    }

    Unit setRadius(Float f) {
        if (f == this.mRadius) {
            return
        }
        this.mRadius = f
        updateBounds(null)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList
        this.mTintFilter = createTintFilter(this.mTint, this.mTintMode)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode
        this.mTintFilter = createTintFilter(this.mTint, this.mTintMode)
        invalidateSelf()
    }
}
