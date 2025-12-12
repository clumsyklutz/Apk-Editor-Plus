package android.support.v7.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.widget.TintableCompoundButton
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.widget.RadioButton

class AppCompatRadioButton extends RadioButton implements TintableCompoundButton {
    private final AppCompatCompoundButtonHelper mCompoundButtonHelper
    private final AppCompatTextHelper mTextHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.radioButtonStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(TintContextWrapper.wrap(context), attributeSet, i)
        this.mCompoundButtonHelper = AppCompatCompoundButtonHelper(this)
        this.mCompoundButtonHelper.loadFromAttributes(attributeSet, i)
        this.mTextHelper = AppCompatTextHelper(this)
        this.mTextHelper.loadFromAttributes(attributeSet, i)
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    fun getCompoundPaddingLeft() {
        Int compoundPaddingLeft = super.getCompoundPaddingLeft()
        return this.mCompoundButtonHelper != null ? this.mCompoundButtonHelper.getCompoundPaddingLeft(compoundPaddingLeft) : compoundPaddingLeft
    }

    @Override // android.support.v4.widget.TintableCompoundButton
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getSupportButtonTintList() {
        if (this.mCompoundButtonHelper != null) {
            return this.mCompoundButtonHelper.getSupportButtonTintList()
        }
        return null
    }

    @Override // android.support.v4.widget.TintableCompoundButton
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PorterDuff.Mode getSupportButtonTintMode() {
        if (this.mCompoundButtonHelper != null) {
            return this.mCompoundButtonHelper.getSupportButtonTintMode()
        }
        return null
    }

    @Override // android.widget.CompoundButton
    fun setButtonDrawable(@DrawableRes Int i) throws NoSuchFieldException {
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), i))
    }

    @Override // android.widget.CompoundButton
    fun setButtonDrawable(Drawable drawable) throws NoSuchFieldException {
        super.setButtonDrawable(drawable)
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.onSetButtonDrawable()
        }
    }

    @Override // android.support.v4.widget.TintableCompoundButton
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportButtonTintList(@Nullable ColorStateList colorStateList) throws NoSuchFieldException {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintList(colorStateList)
        }
    }

    @Override // android.support.v4.widget.TintableCompoundButton
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportButtonTintMode(@Nullable PorterDuff.Mode mode) throws NoSuchFieldException {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintMode(mode)
        }
    }
}
