package android.support.v7.widget

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.util.AttributeSet
import android.widget.SeekBar
import java.lang.reflect.InvocationTargetException

class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
    private Boolean mHasTickMarkTint
    private Boolean mHasTickMarkTintMode
    private Drawable mTickMark
    private ColorStateList mTickMarkTintList
    private PorterDuff.Mode mTickMarkTintMode
    private final SeekBar mView

    AppCompatSeekBarHelper(SeekBar seekBar) {
        super(seekBar)
        this.mTickMarkTintList = null
        this.mTickMarkTintMode = null
        this.mHasTickMarkTint = false
        this.mHasTickMarkTintMode = false
        this.mView = seekBar
    }

    private fun applyTickMarkTint() {
        if (this.mTickMark != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkTintMode) {
                this.mTickMark = DrawableCompat.wrap(this.mTickMark.mutate())
                if (this.mHasTickMarkTint) {
                    DrawableCompat.setTintList(this.mTickMark, this.mTickMarkTintList)
                }
                if (this.mHasTickMarkTintMode) {
                    DrawableCompat.setTintMode(this.mTickMark, this.mTickMarkTintMode)
                }
                if (this.mTickMark.isStateful()) {
                    this.mTickMark.setState(this.mView.getDrawableState())
                }
            }
        }
    }

    Unit drawTickMarks(Canvas canvas) {
        Int max
        if (this.mTickMark == null || (max = this.mView.getMax()) <= 1) {
            return
        }
        Int intrinsicWidth = this.mTickMark.getIntrinsicWidth()
        Int intrinsicHeight = this.mTickMark.getIntrinsicHeight()
        Int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1
        Int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1
        this.mTickMark.setBounds(-i, -i2, i, i2)
        Float width = ((this.mView.getWidth() - this.mView.getPaddingLeft()) - this.mView.getPaddingRight()) / max
        Int iSave = canvas.save()
        canvas.translate(this.mView.getPaddingLeft(), this.mView.getHeight() / 2)
        for (Int i3 = 0; i3 <= max; i3++) {
            this.mTickMark.draw(canvas)
            canvas.translate(width, 0.0f)
        }
        canvas.restoreToCount(iSave)
    }

    Unit drawableStateChanged() {
        Drawable drawable = this.mTickMark
        if (drawable != null && drawable.isStateful() && drawable.setState(this.mView.getDrawableState())) {
            this.mView.invalidateDrawable(drawable)
        }
    }

    @Nullable
    Drawable getTickMark() {
        return this.mTickMark
    }

    @Nullable
    ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList
    }

    @Nullable
    PorterDuff.Mode getTickMarkTintMode() {
        return this.mTickMarkTintMode
    }

    Unit jumpDrawablesToCurrentState() {
        if (this.mTickMark != null) {
            this.mTickMark.jumpToCurrentState()
        }
    }

    @Override // android.support.v7.widget.AppCompatProgressBarHelper
    Unit loadFromAttributes(AttributeSet attributeSet, Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.loadFromAttributes(attributeSet, i)
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.AppCompatSeekBar, i, 0)
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb)
        if (drawableIfKnown != null) {
            this.mView.setThumb(drawableIfKnown)
        }
        setTickMark(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.AppCompatSeekBar_tickMark))
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.mTickMarkTintMode = DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.mTickMarkTintMode)
            this.mHasTickMarkTintMode = true
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint)) {
            this.mTickMarkTintList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint)
            this.mHasTickMarkTint = true
        }
        tintTypedArrayObtainStyledAttributes.recycle()
        applyTickMarkTint()
    }

    Unit setTickMark(@Nullable Drawable drawable) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.mTickMark != null) {
            this.mTickMark.setCallback(null)
        }
        this.mTickMark = drawable
        if (drawable != null) {
            drawable.setCallback(this.mView)
            DrawableCompat.setLayoutDirection(drawable, ViewCompat.getLayoutDirection(this.mView))
            if (drawable.isStateful()) {
                drawable.setState(this.mView.getDrawableState())
            }
            applyTickMarkTint()
        }
        this.mView.invalidate()
    }

    Unit setTickMarkTintList(@Nullable ColorStateList colorStateList) {
        this.mTickMarkTintList = colorStateList
        this.mHasTickMarkTint = true
        applyTickMarkTint()
    }

    Unit setTickMarkTintMode(@Nullable PorterDuff.Mode mode) {
        this.mTickMarkTintMode = mode
        this.mHasTickMarkTintMode = true
        applyTickMarkTint()
    }
}
