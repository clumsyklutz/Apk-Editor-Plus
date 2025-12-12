package android.support.v7.widget

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.Nullable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.widget.CompoundButton

class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null
    private PorterDuff.Mode mButtonTintMode = null
    private Boolean mHasButtonTint = false
    private Boolean mHasButtonTintMode = false
    private Boolean mSkipNextApply
    private final CompoundButton mView

    interface DirectSetButtonDrawableInterface {
        Unit setButtonDrawable(Drawable drawable)
    }

    AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton
    }

    Unit applyButtonTint() throws NoSuchFieldException {
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView)
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable drawableMutate = DrawableCompat.wrap(buttonDrawable).mutate()
                if (this.mHasButtonTint) {
                    DrawableCompat.setTintList(drawableMutate, this.mButtonTintList)
                }
                if (this.mHasButtonTintMode) {
                    DrawableCompat.setTintMode(drawableMutate, this.mButtonTintMode)
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(this.mView.getDrawableState())
                }
                this.mView.setButtonDrawable(drawableMutate)
            }
        }
    }

    Int getCompoundPaddingLeft(Int i) {
        Drawable buttonDrawable
        return (Build.VERSION.SDK_INT >= 17 || (buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView)) == null) ? i : i + buttonDrawable.getIntrinsicWidth()
    }

    ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList
    }

    PorterDuff.Mode getSupportButtonTintMode() {
        return this.mButtonTintMode
    }

    Unit loadFromAttributes(AttributeSet attributeSet, Int i) {
        Int resourceId
        TypedArray typedArrayObtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(attributeSet, R.styleable.CompoundButton, i, 0)
        try {
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CompoundButton_android_button) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CompoundButton_android_button, 0)) != 0) {
                this.mView.setButtonDrawable(AppCompatResources.getDrawable(this.mView.getContext(), resourceId))
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CompoundButton_buttonTint)) {
                CompoundButtonCompat.setButtonTintList(this.mView, typedArrayObtainStyledAttributes.getColorStateList(R.styleable.CompoundButton_buttonTint))
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CompoundButton_buttonTintMode)) {
                CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(R.styleable.CompoundButton_buttonTintMode, -1), null))
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle()
        }
    }

    Unit onSetButtonDrawable() throws NoSuchFieldException {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false
        } else {
            this.mSkipNextApply = true
            applyButtonTint()
        }
    }

    Unit setSupportButtonTintList(ColorStateList colorStateList) throws NoSuchFieldException {
        this.mButtonTintList = colorStateList
        this.mHasButtonTint = true
        applyButtonTint()
    }

    Unit setSupportButtonTintMode(@Nullable PorterDuff.Mode mode) throws NoSuchFieldException {
        this.mButtonTintMode = mode
        this.mHasButtonTintMode = true
        applyButtonTint()
    }
}
