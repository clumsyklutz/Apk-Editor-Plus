package android.support.v7.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.View

class AppCompatBackgroundHelper {
    private TintInfo mBackgroundTint
    private TintInfo mInternalBackgroundTint
    private TintInfo mTmpInfo
    private final View mView
    private Int mBackgroundResId = -1
    private val mDrawableManager = AppCompatDrawableManager.get()

    AppCompatBackgroundHelper(View view) {
        this.mView = view
    }

    private fun applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = TintInfo()
        }
        TintInfo tintInfo = this.mTmpInfo
        tintInfo.clear()
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.mView)
        if (backgroundTintList != null) {
            tintInfo.mHasTintList = true
            tintInfo.mTintList = backgroundTintList
        }
        PorterDuff.Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.mView)
        if (backgroundTintMode != null) {
            tintInfo.mHasTintMode = true
            tintInfo.mTintMode = backgroundTintMode
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState())
        return true
    }

    private fun shouldApplyFrameworkTintUsingColorFilter() {
        Int i = Build.VERSION.SDK_INT
        return i > 21 ? this.mInternalBackgroundTint != null : i == 21
    }

    Unit applySupportBackgroundTint() {
        Drawable background = this.mView.getBackground()
        if (background != null) {
            if (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(background)) {
                return
            }
            if (this.mBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable(background, this.mBackgroundTint, this.mView.getDrawableState())
            } else if (this.mInternalBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable(background, this.mInternalBackgroundTint, this.mView.getDrawableState())
            }
        }
    }

    ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList
        }
        return null
    }

    PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintMode
        }
        return null
    }

    Unit loadFromAttributes(AttributeSet attributeSet, Int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.ViewBackgroundHelper, i, 0)
        try {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.mBackgroundResId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1)
                ColorStateList tintList = this.mDrawableManager.getTintList(this.mView.getContext(), this.mBackgroundResId)
                if (tintList != null) {
                    setInternalBackgroundTint(tintList)
                }
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.mView, tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint))
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null))
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle()
        }
    }

    Unit onSetBackgroundDrawable(Drawable drawable) {
        this.mBackgroundResId = -1
        setInternalBackgroundTint(null)
        applySupportBackgroundTint()
    }

    Unit onSetBackgroundResource(Int i) {
        this.mBackgroundResId = i
        setInternalBackgroundTint(this.mDrawableManager != null ? this.mDrawableManager.getTintList(this.mView.getContext(), i) : null)
        applySupportBackgroundTint()
    }

    Unit setInternalBackgroundTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = TintInfo()
            }
            this.mInternalBackgroundTint.mTintList = colorStateList
            this.mInternalBackgroundTint.mHasTintList = true
        } else {
            this.mInternalBackgroundTint = null
        }
        applySupportBackgroundTint()
    }

    Unit setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = TintInfo()
        }
        this.mBackgroundTint.mTintList = colorStateList
        this.mBackgroundTint.mHasTintList = true
        applySupportBackgroundTint()
    }

    Unit setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = TintInfo()
        }
        this.mBackgroundTint.mTintMode = mode
        this.mBackgroundTint.mHasTintMode = true
        applySupportBackgroundTint()
    }
}
