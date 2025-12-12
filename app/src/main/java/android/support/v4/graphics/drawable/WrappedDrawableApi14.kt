package android.support.v4.graphics.drawable

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Region
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi

class WrappedDrawableApi14 extends Drawable implements Drawable.Callback, TintAwareDrawable, WrappedDrawable {
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN
    private Boolean mColorFilterSet
    private Int mCurrentColor
    private PorterDuff.Mode mCurrentMode
    Drawable mDrawable
    private Boolean mMutated
    DrawableWrapperState mState

    abstract class DrawableWrapperState extends Drawable.ConstantState {
        Int mChangingConfigurations
        Drawable.ConstantState mDrawableState
        ColorStateList mTint
        PorterDuff.Mode mTintMode

        DrawableWrapperState(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            this.mTint = null
            this.mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE
            if (drawableWrapperState != null) {
                this.mChangingConfigurations = drawableWrapperState.mChangingConfigurations
                this.mDrawableState = drawableWrapperState.mDrawableState
                this.mTint = drawableWrapperState.mTint
                this.mTintMode = drawableWrapperState.mTintMode
            }
        }

        Boolean canConstantState() {
            return this.mDrawableState != null
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun getChangingConfigurations() {
            return (this.mDrawableState != null ? this.mDrawableState.getChangingConfigurations() : 0) | this.mChangingConfigurations
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable() {
            return newDrawable(null)
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public abstract Drawable newDrawable(@Nullable Resources resources)
    }

    class DrawableWrapperStateBase extends DrawableWrapperState {
        DrawableWrapperStateBase(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources)
        }

        @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable(@Nullable Resources resources) {
            return WrappedDrawableApi14(this, resources)
        }
    }

    WrappedDrawableApi14(@Nullable Drawable drawable) {
        this.mState = mutateConstantState()
        setWrappedDrawable(drawable)
    }

    WrappedDrawableApi14(@NonNull DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
        this.mState = drawableWrapperState
        updateLocalState(resources)
    }

    private fun updateLocalState(@Nullable Resources resources) {
        if (this.mState == null || this.mState.mDrawableState == null) {
            return
        }
        setWrappedDrawable(this.mState.mDrawableState.newDrawable(resources))
    }

    private fun updateTint(Array<Int> iArr) {
        if (!isCompatTintEnabled()) {
            return false
        }
        ColorStateList colorStateList = this.mState.mTint
        PorterDuff.Mode mode = this.mState.mTintMode
        if (colorStateList == null || mode == null) {
            this.mColorFilterSet = false
            clearColorFilter()
            return false
        }
        Int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor())
        if (this.mColorFilterSet && colorForState == this.mCurrentColor && mode == this.mCurrentMode) {
            return false
        }
        setColorFilter(colorForState, mode)
        this.mCurrentColor = colorForState
        this.mCurrentMode = mode
        this.mColorFilterSet = true
        return true
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(@NonNull Canvas canvas) {
        this.mDrawable.draw(canvas)
    }

    @Override // android.graphics.drawable.Drawable
    fun getChangingConfigurations() {
        return (this.mState != null ? this.mState.getChangingConfigurations() : 0) | super.getChangingConfigurations() | this.mDrawable.getChangingConfigurations()
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public Drawable.ConstantState getConstantState() {
        if (this.mState == null || !this.mState.canConstantState()) {
            return null
        }
        this.mState.mChangingConfigurations = getChangingConfigurations()
        return this.mState
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    fun getCurrent() {
        return this.mDrawable.getCurrent()
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight()
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth()
    }

    @Override // android.graphics.drawable.Drawable
    fun getMinimumHeight() {
        return this.mDrawable.getMinimumHeight()
    }

    @Override // android.graphics.drawable.Drawable
    fun getMinimumWidth() {
        return this.mDrawable.getMinimumWidth()
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        return this.mDrawable.getOpacity()
    }

    @Override // android.graphics.drawable.Drawable
    fun getPadding(@NonNull Rect rect) {
        return this.mDrawable.getPadding(rect)
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Array<Int> getState() {
        return this.mDrawable.getState()
    }

    @Override // android.graphics.drawable.Drawable
    fun getTransparentRegion() {
        return this.mDrawable.getTransparentRegion()
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawable
    public final Drawable getWrappedDrawable() {
        return this.mDrawable
    }

    @Override // android.graphics.drawable.Drawable.Callback
    fun invalidateDrawable(@NonNull Drawable drawable) {
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(19)
    fun isAutoMirrored() {
        return this.mDrawable.isAutoMirrored()
    }

    protected fun isCompatTintEnabled() {
        return true
    }

    @Override // android.graphics.drawable.Drawable
    fun isStateful() {
        ColorStateList colorStateList = (!isCompatTintEnabled() || this.mState == null) ? null : this.mState.mTint
        return (colorStateList != null && colorStateList.isStateful()) || this.mDrawable.isStateful()
    }

    @Override // android.graphics.drawable.Drawable
    fun jumpToCurrentState() {
        this.mDrawable.jumpToCurrentState()
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    fun mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = mutateConstantState()
            if (this.mDrawable != null) {
                this.mDrawable.mutate()
            }
            if (this.mState != null) {
                this.mState.mDrawableState = this.mDrawable != null ? this.mDrawable.getConstantState() : null
            }
            this.mMutated = true
        }
        return this
    }

    @NonNull
    DrawableWrapperState mutateConstantState() {
        return DrawableWrapperStateBase(this.mState, null)
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        if (this.mDrawable != null) {
            this.mDrawable.setBounds(rect)
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onLevelChange(Int i) {
        return this.mDrawable.setLevel(i)
    }

    @Override // android.graphics.drawable.Drawable.Callback
    fun scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, Long j) {
        scheduleSelf(runnable, j)
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        this.mDrawable.setAlpha(i)
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(19)
    fun setAutoMirrored(Boolean z) {
        this.mDrawable.setAutoMirrored(z)
    }

    @Override // android.graphics.drawable.Drawable
    fun setChangingConfigurations(Int i) {
        this.mDrawable.setChangingConfigurations(i)
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter)
    }

    @Override // android.graphics.drawable.Drawable
    fun setDither(Boolean z) {
        this.mDrawable.setDither(z)
    }

    @Override // android.graphics.drawable.Drawable
    fun setFilterBitmap(Boolean z) {
        this.mDrawable.setFilterBitmap(z)
    }

    @Override // android.graphics.drawable.Drawable
    fun setState(@NonNull Array<Int> iArr) {
        return updateTint(iArr) || this.mDrawable.setState(iArr)
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTint(Int i) {
        setTintList(ColorStateList.valueOf(i))
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList
        updateTint(getState())
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTintMode(@NonNull PorterDuff.Mode mode) {
        this.mState.mTintMode = mode
        updateTint(getState())
    }

    @Override // android.graphics.drawable.Drawable
    fun setVisible(Boolean z, Boolean z2) {
        return super.setVisible(z, z2) || this.mDrawable.setVisible(z, z2)
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawable
    public final Unit setWrappedDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null)
        }
        this.mDrawable = drawable
        if (drawable != null) {
            drawable.setCallback(this)
            setVisible(drawable.isVisible(), true)
            setState(drawable.getState())
            setLevel(drawable.getLevel())
            setBounds(drawable.getBounds())
            if (this.mState != null) {
                this.mState.mDrawableState = drawable.getConstantState()
            }
        }
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable.Callback
    fun unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        unscheduleSelf(runnable)
    }
}
