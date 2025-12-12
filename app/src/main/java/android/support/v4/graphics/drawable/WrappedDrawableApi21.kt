package android.support.v4.graphics.drawable

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.graphics.drawable.WrappedDrawableApi14
import android.util.Log
import java.lang.reflect.Method

@RequiresApi(21)
class WrappedDrawableApi21 extends WrappedDrawableApi14 {
    private static val TAG = "WrappedDrawableApi21"
    private static Method sIsProjectedDrawableMethod

    class DrawableWrapperStateLollipop extends WrappedDrawableApi14.DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources)
        }

        @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable(@Nullable Resources resources) {
            return WrappedDrawableApi21(this, resources)
        }
    }

    WrappedDrawableApi21(Drawable drawable) {
        super(drawable)
        findAndCacheIsProjectedDrawableMethod()
    }

    WrappedDrawableApi21(WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, Resources resources) {
        super(drawableWrapperState, resources)
        findAndCacheIsProjectedDrawableMethod()
    }

    private fun findAndCacheIsProjectedDrawableMethod() {
        if (sIsProjectedDrawableMethod == null) {
            try {
                sIsProjectedDrawableMethod = Drawable.class.getDeclaredMethod("isProjected", new Class[0])
            } catch (Exception e) {
                Log.w(TAG, "Failed to retrieve Drawable#isProjected() method", e)
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    fun getDirtyBounds() {
        return this.mDrawable.getDirtyBounds()
    }

    @Override // android.graphics.drawable.Drawable
    fun getOutline(@NonNull Outline outline) {
        this.mDrawable.getOutline(outline)
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14
    protected fun isCompatTintEnabled() {
        if (Build.VERSION.SDK_INT != 21) {
            return false
        }
        Drawable drawable = this.mDrawable
        return (drawable is GradientDrawable) || (drawable is DrawableContainer) || (drawable is InsetDrawable) || (drawable is RippleDrawable)
    }

    @Override // android.graphics.drawable.Drawable
    fun isProjected() {
        if (this.mDrawable != null && sIsProjectedDrawableMethod != null) {
            try {
                return ((Boolean) sIsProjectedDrawableMethod.invoke(this.mDrawable, new Object[0])).booleanValue()
            } catch (Exception e) {
                Log.w(TAG, "Error calling Drawable#isProjected() method", e)
            }
        }
        return false
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14
    @NonNull
    WrappedDrawableApi14.DrawableWrapperState mutateConstantState() {
        return DrawableWrapperStateLollipop(this.mState, null)
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspot(Float f, Float f2) {
        this.mDrawable.setHotspot(f, f2)
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
        this.mDrawable.setHotspotBounds(i, i2, i3, i4)
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14, android.graphics.drawable.Drawable
    fun setState(@NonNull Array<Int> iArr) {
        if (!super.setState(iArr)) {
            return false
        }
        invalidateSelf()
        return true
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14, android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTint(Int i) {
        if (isCompatTintEnabled()) {
            super.setTint(i)
        } else {
            this.mDrawable.setTint(i)
        }
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14, android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTintList(ColorStateList colorStateList) {
        if (isCompatTintEnabled()) {
            super.setTintList(colorStateList)
        } else {
            this.mDrawable.setTintList(colorStateList)
        }
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14, android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTintMode(PorterDuff.Mode mode) {
        if (isCompatTintEnabled()) {
            super.setTintMode(mode)
        } else {
            this.mDrawable.setTintMode(mode)
        }
    }
}
