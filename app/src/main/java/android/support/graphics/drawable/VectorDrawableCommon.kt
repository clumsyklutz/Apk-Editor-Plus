package android.support.graphics.drawable

import android.content.res.Resources
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Region
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.graphics.drawable.TintAwareDrawable

abstract class VectorDrawableCommon extends Drawable implements TintAwareDrawable {
    Drawable mDelegateDrawable

    VectorDrawableCommon() {
    }

    @Override // android.graphics.drawable.Drawable
    fun applyTheme(Resources.Theme theme) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.applyTheme(this.mDelegateDrawable, theme)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun clearColorFilter() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.clearColorFilter()
        } else {
            super.clearColorFilter()
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun getColorFilter() {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.getColorFilter(this.mDelegateDrawable)
        }
        return null
    }

    @Override // android.graphics.drawable.Drawable
    fun getCurrent() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getCurrent() : super.getCurrent()
    }

    @Override // android.graphics.drawable.Drawable
    fun getMinimumHeight() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getMinimumHeight() : super.getMinimumHeight()
    }

    @Override // android.graphics.drawable.Drawable
    fun getMinimumWidth() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getMinimumWidth() : super.getMinimumWidth()
    }

    @Override // android.graphics.drawable.Drawable
    fun getPadding(Rect rect) {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getPadding(rect) : super.getPadding(rect)
    }

    @Override // android.graphics.drawable.Drawable
    public Array<Int> getState() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getState() : super.getState()
    }

    @Override // android.graphics.drawable.Drawable
    fun getTransparentRegion() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getTransparentRegion() : super.getTransparentRegion()
    }

    @Override // android.graphics.drawable.Drawable
    fun jumpToCurrentState() {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.jumpToCurrentState(this.mDelegateDrawable)
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds(rect)
        } else {
            super.onBoundsChange(rect)
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onLevelChange(Int i) {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.setLevel(i) : super.onLevelChange(i)
    }

    @Override // android.graphics.drawable.Drawable
    fun setChangingConfigurations(Int i) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setChangingConfigurations(i)
        } else {
            super.setChangingConfigurations(i)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(Int i, PorterDuff.Mode mode) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter(i, mode)
        } else {
            super.setColorFilter(i, mode)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setFilterBitmap(Boolean z) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setFilterBitmap(z)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspot(Float f, Float f2) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setHotspot(this.mDelegateDrawable, f, f2)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setHotspotBounds(this.mDelegateDrawable, i, i2, i3, i4)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setState(Array<Int> iArr) {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.setState(iArr) : super.setState(iArr)
    }
}
