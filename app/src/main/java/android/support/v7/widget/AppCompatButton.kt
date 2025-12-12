package android.support.v7.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.TintableBackgroundView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.ActionMode
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button

class AppCompatButton extends Button implements TintableBackgroundView, AutoSizeableTextView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper
    private final AppCompatTextHelper mTextHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle)
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

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getAutoSizeMaxTextSize() {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            return super.getAutoSizeMaxTextSize()
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeMaxTextSize()
        }
        return -1
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getAutoSizeMinTextSize() {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            return super.getAutoSizeMinTextSize()
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeMinTextSize()
        }
        return -1
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getAutoSizeStepGranularity() {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            return super.getAutoSizeStepGranularity()
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeStepGranularity()
        }
        return -1
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Array<Int> getAutoSizeTextAvailableSizes() {
        return PLATFORM_SUPPORTS_AUTOSIZE ? super.getAutoSizeTextAvailableSizes() : this.mTextHelper != null ? this.mTextHelper.getAutoSizeTextAvailableSizes() : new Int[0]
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getAutoSizeTextType() {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            return super.getAutoSizeTextType() == 1 ? 1 : 0
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeTextType()
        }
        return 0
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

    @Override // android.view.View
    fun onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent)
        accessibilityEvent.setClassName(Button.class.getName())
    }

    @Override // android.view.View
    fun onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo)
        accessibilityNodeInfo.setClassName(Button.class.getName())
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        super.onLayout(z, i, i2, i3, i4)
        if (this.mTextHelper != null) {
            this.mTextHelper.onLayout(z, i, i2, i3, i4)
        }
    }

    @Override // android.widget.TextView
    protected fun onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
        super.onTextChanged(charSequence, i, i2, i3)
        if (this.mTextHelper == null || PLATFORM_SUPPORTS_AUTOSIZE || !this.mTextHelper.isAutoSizeEnabled()) {
            return
        }
        this.mTextHelper.autoSizeText()
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setAutoSizeTextTypeUniformWithConfiguration(Int i, Int i2, Int i3, Int i4) {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            super.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4)
        } else if (this.mTextHelper != null) {
            this.mTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4)
        }
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setAutoSizeTextTypeUniformWithPresetSizes(@NonNull Array<Int> iArr, Int i) {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i)
        } else if (this.mTextHelper != null) {
            this.mTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i)
        }
    }

    @Override // android.widget.TextView, android.support.v4.widget.AutoSizeableTextView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setAutoSizeTextTypeWithDefaults(Int i) {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            super.setAutoSizeTextTypeWithDefaults(i)
        } else if (this.mTextHelper != null) {
            this.mTextHelper.setAutoSizeTextTypeWithDefaults(i)
        }
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

    fun setSupportAllCaps(Boolean z) {
        if (this.mTextHelper != null) {
            this.mTextHelper.setAllCaps(z)
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

    @Override // android.widget.TextView
    fun setTextAppearance(Context context, Int i) {
        super.setTextAppearance(context, i)
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, i)
        }
    }

    @Override // android.widget.TextView
    fun setTextSize(Int i, Float f) {
        if (PLATFORM_SUPPORTS_AUTOSIZE) {
            super.setTextSize(i, f)
        } else if (this.mTextHelper != null) {
            this.mTextHelper.setTextSize(i, f)
        }
    }
}
