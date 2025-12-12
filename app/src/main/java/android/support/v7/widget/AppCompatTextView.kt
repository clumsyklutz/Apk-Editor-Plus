package android.support.v7.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import android.support.annotation.RestrictTo
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.text.PrecomputedTextCompat
import android.support.v4.view.TintableBackgroundView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import android.util.AttributeSet
import android.view.ActionMode
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.TextView
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

class AppCompatTextView extends TextView implements TintableBackgroundView, AutoSizeableTextView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper

    @Nullable
    private Future mPrecomputedTextFuture
    private final AppCompatTextHelper mTextHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(TintContextWrapper.wrap(context), attributeSet, i)
        this.mBackgroundTintHelper = AppCompatBackgroundHelper(this)
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, i)
        this.mTextHelper = AppCompatTextHelper(this)
        this.mTextHelper.loadFromAttributes(attributeSet, i)
        this.mTextHelper.applyCompoundDrawablesTints()
    }

    private fun consumeTextFutureAndSetBlocking() {
        if (this.mPrecomputedTextFuture != null) {
            try {
                Future future = this.mPrecomputedTextFuture
                this.mPrecomputedTextFuture = null
                TextViewCompat.setPrecomputedText(this, (PrecomputedTextCompat) future.get())
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            }
        }
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

    @Override // android.widget.TextView
    fun getFirstBaselineToTopHeight() {
        return TextViewCompat.getFirstBaselineToTopHeight(this)
    }

    @Override // android.widget.TextView
    fun getLastBaselineToBottomHeight() {
        return TextViewCompat.getLastBaselineToBottomHeight(this)
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

    @Override // android.widget.TextView
    fun getText() {
        consumeTextFutureAndSetBlocking()
        return super.getText()
    }

    @NonNull
    public PrecomputedTextCompat.Params getTextMetricsParamsCompat() {
        return TextViewCompat.getTextMetricsParams(this)
    }

    @Override // android.widget.TextView, android.view.View
    fun onCreateInputConnection(EditorInfo editorInfo) {
        return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(editorInfo), editorInfo, this)
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        super.onLayout(z, i, i2, i3, i4)
        if (this.mTextHelper != null) {
            this.mTextHelper.onLayout(z, i, i2, i3, i4)
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        consumeTextFutureAndSetBlocking()
        super.onMeasure(i, i2)
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

    @Override // android.widget.TextView
    fun setFirstBaselineToTopHeight(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) @Px Int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setFirstBaselineToTopHeight(i)
        } else {
            TextViewCompat.setFirstBaselineToTopHeight(this, i)
        }
    }

    @Override // android.widget.TextView
    fun setLastBaselineToBottomHeight(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) @Px Int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setLastBaselineToBottomHeight(i)
        } else {
            TextViewCompat.setLastBaselineToBottomHeight(this, i)
        }
    }

    @Override // android.widget.TextView
    fun setLineHeight(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) @Px Int i) {
        TextViewCompat.setLineHeight(this, i)
    }

    fun setPrecomputedText(@NonNull PrecomputedTextCompat precomputedTextCompat) {
        TextViewCompat.setPrecomputedText(this, precomputedTextCompat)
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

    fun setTextFuture(@NonNull Future future) {
        this.mPrecomputedTextFuture = future
        requestLayout()
    }

    fun setTextMetricsParamsCompat(@NonNull PrecomputedTextCompat.Params params) {
        TextViewCompat.setTextMetricsParams(this, params)
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
