package android.support.v7.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import androidx.core.widget.ImageViewCompat
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.widget.ImageView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class AppCompatImageHelper {
    private TintInfo mImageTint
    private TintInfo mInternalImageTint
    private TintInfo mTmpInfo
    private final ImageView mView

    constructor(ImageView imageView) {
        this.mView = imageView
    }

    private fun applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = TintInfo()
        }
        TintInfo tintInfo = this.mTmpInfo
        tintInfo.clear()
        ColorStateList imageTintList = ImageViewCompat.getImageTintList(this.mView)
        if (imageTintList != null) {
            tintInfo.mHasTintList = true
            tintInfo.mTintList = imageTintList
        }
        PorterDuff.Mode imageTintMode = ImageViewCompat.getImageTintMode(this.mView)
        if (imageTintMode != null) {
            tintInfo.mHasTintMode = true
            tintInfo.mTintMode = imageTintMode
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState())
        return true
    }

    private fun shouldApplyFrameworkTintUsingColorFilter() {
        Int i = Build.VERSION.SDK_INT
        return i > 21 ? this.mInternalImageTint != null : i == 21
    }

    Unit applySupportImageTint() {
        Drawable drawable = this.mView.getDrawable()
        if (drawable != null) {
            DrawableUtils.fixDrawable(drawable)
        }
        if (drawable != null) {
            if (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(drawable)) {
                return
            }
            if (this.mImageTint != null) {
                AppCompatDrawableManager.tintDrawable(drawable, this.mImageTint, this.mView.getDrawableState())
            } else if (this.mInternalImageTint != null) {
                AppCompatDrawableManager.tintDrawable(drawable, this.mInternalImageTint, this.mView.getDrawableState())
            }
        }
    }

    ColorStateList getSupportImageTintList() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintList
        }
        return null
    }

    PorterDuff.Mode getSupportImageTintMode() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintMode
        }
        return null
    }

    Boolean hasOverlappingRendering() {
        return Build.VERSION.SDK_INT < 21 || !(this.mView.getBackground() instanceof RippleDrawable)
    }

    fun loadFromAttributes(AttributeSet attributeSet, Int i) {
        Int resourceId
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0)
        try {
            Drawable drawable = this.mView.getDrawable()
            if (drawable == null && (resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = AppCompatResources.getDrawable(this.mView.getContext(), resourceId)) != null) {
                this.mView.setImageDrawable(drawable)
            }
            if (drawable != null) {
                DrawableUtils.fixDrawable(drawable)
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.mView, tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.AppCompatImageView_tint))
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatImageView_tintMode, -1), null))
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle()
        }
    }

    fun setImageResource(Int i) {
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(this.mView.getContext(), i)
            if (drawable != null) {
                DrawableUtils.fixDrawable(drawable)
            }
            this.mView.setImageDrawable(drawable)
        } else {
            this.mView.setImageDrawable(null)
        }
        applySupportImageTint()
    }

    Unit setInternalImageTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalImageTint == null) {
                this.mInternalImageTint = TintInfo()
            }
            this.mInternalImageTint.mTintList = colorStateList
            this.mInternalImageTint.mHasTintList = true
        } else {
            this.mInternalImageTint = null
        }
        applySupportImageTint()
    }

    Unit setSupportImageTintList(ColorStateList colorStateList) {
        if (this.mImageTint == null) {
            this.mImageTint = TintInfo()
        }
        this.mImageTint.mTintList = colorStateList
        this.mImageTint.mHasTintList = true
        applySupportImageTint()
    }

    Unit setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.mImageTint == null) {
            this.mImageTint = TintInfo()
        }
        this.mImageTint.mTintMode = mode
        this.mImageTint.mHasTintMode = true
        applySupportImageTint()
    }
}
