package android.support.v7.widget

import android.R
import android.content.Context
import android.support.annotation.DrawableRes
import androidx.core.widget.TextViewCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.ActionMode
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.CheckedTextView

class AppCompatCheckedTextView extends CheckedTextView {
    private static final Array<Int> TINT_ATTRS = {R.attr.checkMark}
    private final AppCompatTextHelper mTextHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkedTextViewStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(TintContextWrapper.wrap(context), attributeSet, i)
        this.mTextHelper = AppCompatTextHelper(this)
        this.mTextHelper.loadFromAttributes(attributeSet, i)
        this.mTextHelper.applyCompoundDrawablesTints()
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, TINT_ATTRS, i, 0)
        setCheckMarkDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(0))
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints()
        }
    }

    @Override // android.widget.TextView, android.view.View
    fun onCreateInputConnection(EditorInfo editorInfo) {
        return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(editorInfo), editorInfo, this)
    }

    @Override // android.widget.CheckedTextView
    fun setCheckMarkDrawable(@DrawableRes Int i) {
        setCheckMarkDrawable(AppCompatResources.getDrawable(getContext(), i))
    }

    @Override // android.widget.TextView
    fun setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback))
    }

    @Override // android.widget.TextView
    fun setTextAppearance(Context context, Int i) {
        super.setTextAppearance(context, i)
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, i)
        }
    }
}
