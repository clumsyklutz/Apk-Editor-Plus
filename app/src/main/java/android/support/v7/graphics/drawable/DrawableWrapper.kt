package android.support.v7.graphics.drawable

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Region
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import androidx.core.graphics.drawable.DrawableCompat

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class DrawableWrapper extends Drawable implements Drawable.Callback {
    private Drawable mDrawable

    constructor(Drawable drawable) {
        setWrappedDrawable(drawable)
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        this.mDrawable.draw(canvas)
    }

    @Override // android.graphics.drawable.Drawable
    fun getChangingConfigurations() {
        return this.mDrawable.getChangingConfigurations()
    }

    @Override // android.graphics.drawable.Drawable
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
    fun getPadding(Rect rect) {
        return this.mDrawable.getPadding(rect)
    }

    @Override // android.graphics.drawable.Drawable
    public Array<Int> getState() {
        return this.mDrawable.getState()
    }

    @Override // android.graphics.drawable.Drawable
    fun getTransparentRegion() {
        return this.mDrawable.getTransparentRegion()
    }

    fun getWrappedDrawable() {
        return this.mDrawable
    }

    @Override // android.graphics.drawable.Drawable.Callback
    fun invalidateDrawable(Drawable drawable) {
        invalidateSelf()
    }

    @Override // android.graphics.drawable.Drawable
    fun isAutoMirrored() {
        return DrawableCompat.isAutoMirrored(this.mDrawable)
    }

    @Override // android.graphics.drawable.Drawable
    fun isStateful() {
        return this.mDrawable.isStateful()
    }

    @Override // android.graphics.drawable.Drawable
    fun jumpToCurrentState() {
        DrawableCompat.jumpToCurrentState(this.mDrawable)
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        this.mDrawable.setBounds(rect)
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onLevelChange(Int i) {
        return this.mDrawable.setLevel(i)
    }

    @Override // android.graphics.drawable.Drawable.Callback
    fun scheduleDrawable(Drawable drawable, Runnable runnable, Long j) {
        scheduleSelf(runnable, j)
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        this.mDrawable.setAlpha(i)
    }

    @Override // android.graphics.drawable.Drawable
    fun setAutoMirrored(Boolean z) {
        DrawableCompat.setAutoMirrored(this.mDrawable, z)
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
    fun setHotspot(Float f, Float f2) {
        DrawableCompat.setHotspot(this.mDrawable, f, f2)
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
        DrawableCompat.setHotspotBounds(this.mDrawable, i, i2, i3, i4)
    }

    @Override // android.graphics.drawable.Drawable
    fun setState(Array<Int> iArr) {
        return this.mDrawable.setState(iArr)
    }

    @Override // android.graphics.drawable.Drawable
    fun setTint(Int i) {
        DrawableCompat.setTint(this.mDrawable, i)
    }

    @Override // android.graphics.drawable.Drawable
    fun setTintList(ColorStateList colorStateList) {
        DrawableCompat.setTintList(this.mDrawable, colorStateList)
    }

    @Override // android.graphics.drawable.Drawable
    fun setTintMode(PorterDuff.Mode mode) {
        DrawableCompat.setTintMode(this.mDrawable, mode)
    }

    @Override // android.graphics.drawable.Drawable
    fun setVisible(Boolean z, Boolean z2) {
        return super.setVisible(z, z2) || this.mDrawable.setVisible(z, z2)
    }

    fun setWrappedDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null)
        }
        this.mDrawable = drawable
        if (drawable != null) {
            drawable.setCallback(this)
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    fun unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable)
    }
}
