package android.support.v7.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.TintableBackgroundView
import android.support.v4.widget.TintableImageSourceView
import androidx.appcompat.R
import android.util.AttributeSet
import android.widget.ImageButton

class AppCompatImageButton extends ImageButton implements TintableBackgroundView, TintableImageSourceView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper
    private final AppCompatImageHelper mImageHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.imageButtonStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(TintContextWrapper.wrap(context), attributeSet, i)
        this.mBackgroundTintHelper = AppCompatBackgroundHelper(this)
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, i)
        this.mImageHelper = AppCompatImageHelper(this)
        this.mImageHelper.loadFromAttributes(attributeSet, i)
    }

    @Override // android.widget.ImageView, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint()
        }
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint()
        }
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList()
        }
        return null
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode()
        }
        return null
    }

    @Override // android.support.v4.widget.TintableImageSourceView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getSupportImageTintList() {
        if (this.mImageHelper != null) {
            return this.mImageHelper.getSupportImageTintList()
        }
        return null
    }

    @Override // android.support.v4.widget.TintableImageSourceView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PorterDuff.Mode getSupportImageTintMode() {
        if (this.mImageHelper != null) {
            return this.mImageHelper.getSupportImageTintMode()
        }
        return null
    }

    @Override // android.widget.ImageView, android.view.View
    fun hasOverlappingRendering() {
        return this.mImageHelper.hasOverlappingRendering() && super.hasOverlappingRendering()
    }

    @Override // android.view.View
    fun setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable)
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable)
        }
    }

    @Override // android.view.View
    fun setBackgroundResource(@DrawableRes Int i) {
        super.setBackgroundResource(i)
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(i)
        }
    }

    @Override // android.widget.ImageView
    fun setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap)
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint()
        }
    }

    @Override // android.widget.ImageView
    fun setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable)
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint()
        }
    }

    @Override // android.widget.ImageView
    fun setImageResource(@DrawableRes Int i) {
        this.mImageHelper.setImageResource(i)
    }

    @Override // android.widget.ImageView
    fun setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri)
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint()
        }
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList)
        }
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode)
        }
    }

    @Override // android.support.v4.widget.TintableImageSourceView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportImageTintList(@Nullable ColorStateList colorStateList) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setSupportImageTintList(colorStateList)
        }
    }

    @Override // android.support.v4.widget.TintableImageSourceView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportImageTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setSupportImageTintMode(mode)
        }
    }
}
