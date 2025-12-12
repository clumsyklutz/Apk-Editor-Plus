package android.support.v7.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.TintableBackgroundView
import androidx.core.widget.TextViewCompat
import androidx.appcompat.R
import android.text.Editable
import android.util.AttributeSet
import android.view.ActionMode
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.EditText

class AppCompatEditText extends EditText implements TintableBackgroundView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper
    private final AppCompatTextHelper mTextHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(TintContextWrapper.wrap(context), attributeSet, i)
        this.mBackgroundTintHelper = AppCompatBackgroundHelper(this)
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, i)
        this.mTextHelper = AppCompatTextHelper(this)
        this.mTextHelper.loadFromAttributes(attributeSet, i)
        this.mTextHelper.applyCompoundDrawablesTints()
    }

    @Override // android.widget.TextView, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint()
        }
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints()
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

    @Override // android.widget.EditText, android.widget.TextView
    @Nullable
    fun getText() {
        return Build.VERSION.SDK_INT >= 28 ? super.getText() : super.getEditableText()
    }

    @Override // android.widget.TextView, android.view.View
    fun onCreateInputConnection(EditorInfo editorInfo) {
        return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(editorInfo), editorInfo, this)
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

    @Override // android.widget.TextView
    fun setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback))
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

    @Override // android.widget.TextView
    fun setTextAppearance(Context context, Int i) {
        super.setTextAppearance(context, i)
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, i)
        }
    }
}
