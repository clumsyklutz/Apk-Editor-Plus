package android.support.v4.graphics.drawable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.appcompat.R
import android.util.DisplayMetrics

abstract class RoundedBitmapDrawable extends Drawable {
    private static val DEFAULT_PAINT_FLAGS = 3
    final Bitmap mBitmap
    private Int mBitmapHeight
    private final BitmapShader mBitmapShader
    private Int mBitmapWidth
    private Float mCornerRadius
    private Boolean mIsCircular
    private Int mTargetDensity
    private Int mGravity = R.styleable.AppCompatTheme_colorError
    private val mPaint = Paint(3)
    private val mShaderMatrix = Matrix()
    val mDstRect = Rect()
    private val mDstRectF = RectF()
    private Boolean mApplyGravity = true

    RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        this.mTargetDensity = 160
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi
        }
        this.mBitmap = bitmap
        if (this.mBitmap != null) {
            computeBitmapSize()
            this.mBitmapShader = BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        } else {
            this.mBitmapHeight = -1
            this.mBitmapWidth = -1
            this.mBitmapShader = null
        }
    }

    private fun computeBitmapSize() {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity)
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity)
    }

    private fun isGreaterThanZero(Float f) {
        return f > 0.05f
    }

    private fun updateCircularCornerRadius() {
        this.mCornerRadius = Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.mBitmap
        if (bitmap == null) {
            return
        }
        updateDstRect()
        if (this.mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, this.mPaint)
        } else {
            canvas.drawRoundRect(this.mDstRectF, this.mCornerRadius, this.mCornerRadius, this.mPaint)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun getAlpha() {
        return this.mPaint.getAlpha()
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.mBitmap
    }

    @Override // android.graphics.drawable.Drawable
    fun getColorFilter() {
        return this.mPaint.getColorFilter()
    }

    fun getCornerRadius() {
        return this.mCornerRadius
    }

    fun getGravity() {
        return this.mGravity
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicHeight() {
        return this.mBitmapHeight
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicWidth() {
        return this.mBitmapWidth
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        Bitmap bitmap
        return (this.mGravity != 119 || this.mIsCircular || (bitmap = this.mBitmap) == null || bitmap.hasAlpha() || this.mPaint.getAlpha() < 255 || isGreaterThanZero(this.mCornerRadius)) ? -3 : -1
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint
    }

    Unit gravityCompatApply(Int i, Int i2, Int i3, Rect rect, Rect rect2) {
        throw UnsupportedOperationException()
    }

    fun hasAntiAlias() {
        return this.mPaint.isAntiAlias()
    }

    fun hasMipMap() {
        throw UnsupportedOperationException()
    }

    fun isCircular() {
        return this.mIsCircular
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        super.onBoundsChange(rect)
        if (this.mIsCircular) {
            updateCircularCornerRadius()
        }
        this.mApplyGravity = true
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i)
            invalidateSelf()
        }
    }

    fun setAntiAlias(Boolean z) {
        this.mPaint.setAntiAlias(z)
        invalidateSelf()
    }

    fun setCircular(Boolean z) {
        this.mIsCircular = z
        this.mApplyGravity = true
        if (!z) {
            setCornerRadius(0.0f)
            return
        }
        updateCircularCornerRadius()
        this.mPaint.setShader(this.mBitmapShader)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter)
        invalidateSelf()
    }

    fun setCornerRadius(Float f) {
        if (this.mCornerRadius == f) {
            return
        }
        this.mIsCircular = false
        if (isGreaterThanZero(f)) {
            this.mPaint.setShader(this.mBitmapShader)
        } else {
            this.mPaint.setShader(null)
        }
        this.mCornerRadius = f
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setDither(Boolean z) {
        this.mPaint.setDither(z)
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun setFilterBitmap(Boolean z) {
        this.mPaint.setFilterBitmap(z)
        invalidateSelf()
    }

    fun setGravity(Int i) {
        if (this.mGravity != i) {
            this.mGravity = i
            this.mApplyGravity = true
            invalidateSelf()
        }
    }

    fun setMipMap(Boolean z) {
        throw UnsupportedOperationException()
    }

    fun setTargetDensity(Int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = 160
            }
            this.mTargetDensity = i
            if (this.mBitmap != null) {
                computeBitmapSize()
            }
            invalidateSelf()
        }
    }

    fun setTargetDensity(@NonNull Canvas canvas) {
        setTargetDensity(canvas.getDensity())
    }

    fun setTargetDensity(@NonNull DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi)
    }

    Unit updateDstRect() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                Int iMin = Math.min(this.mBitmapWidth, this.mBitmapHeight)
                gravityCompatApply(this.mGravity, iMin, iMin, getBounds(), this.mDstRect)
                Int iMin2 = Math.min(this.mDstRect.width(), this.mDstRect.height())
                this.mDstRect.inset(Math.max(0, (this.mDstRect.width() - iMin2) / 2), Math.max(0, (this.mDstRect.height() - iMin2) / 2))
                this.mCornerRadius = iMin2 * 0.5f
            } else {
                gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect)
            }
            this.mDstRectF.set(this.mDstRect)
            if (this.mBitmapShader != null) {
                this.mShaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top)
                this.mShaderMatrix.preScale(this.mDstRectF.width() / this.mBitmap.getWidth(), this.mDstRectF.height() / this.mBitmap.getHeight())
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix)
                this.mPaint.setShader(this.mBitmapShader)
            }
            this.mApplyGravity = false
        }
    }
}
