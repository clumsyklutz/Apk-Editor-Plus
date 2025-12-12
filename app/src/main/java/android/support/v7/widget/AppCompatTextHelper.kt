package android.support.v7.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import androidx.appcompat.R
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.widget.TextView
import java.lang.ref.WeakReference

class AppCompatTextHelper {
    private static val MONOSPACE = 3
    private static val SANS = 1
    private static val SERIF = 2
    private Boolean mAsyncFontPending

    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper
    private TintInfo mDrawableBottomTint
    private TintInfo mDrawableEndTint
    private TintInfo mDrawableLeftTint
    private TintInfo mDrawableRightTint
    private TintInfo mDrawableStartTint
    private TintInfo mDrawableTopTint
    private Typeface mFontTypeface
    private Int mStyle = 0
    private final TextView mView

    AppCompatTextHelper(TextView textView) {
        this.mView = textView
        this.mAutoSizeTextHelper = AppCompatTextViewAutoSizeHelper(this.mView)
    }

    private fun applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable == null || tintInfo == null) {
            return
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState())
    }

    private fun createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, Int i) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i)
        if (tintList == null) {
            return null
        }
        TintInfo tintInfo = TintInfo()
        tintInfo.mHasTintList = true
        tintInfo.mTintList = tintList
        return tintInfo
    }

    private fun setTextSizeInternal(Int i, Float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i, f)
    }

    private fun updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle)
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null
            Int i = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily
            if (!context.isRestricted()) {
                val weakReference = WeakReference(this.mView)
                try {
                    this.mFontTypeface = tintTypedArray.getFont(i, this.mStyle, new ResourcesCompat.FontCallback() { // from class: android.support.v7.widget.AppCompatTextHelper.1
                        @Override // android.support.v4.content.res.ResourcesCompat.FontCallback
                        fun onFontRetrievalFailed(Int i2) {
                        }

                        @Override // android.support.v4.content.res.ResourcesCompat.FontCallback
                        fun onFontRetrieved(@NonNull Typeface typeface) {
                            AppCompatTextHelper.this.onAsyncTypefaceReceived(weakReference, typeface)
                        }
                    })
                    this.mAsyncFontPending = this.mFontTypeface == null
                } catch (Resources.NotFoundException e) {
                } catch (UnsupportedOperationException e2) {
                }
            }
            if (this.mFontTypeface != null || (string = tintTypedArray.getString(i)) == null) {
                return
            }
            this.mFontTypeface = Typeface.create(string, this.mStyle)
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
            this.mAsyncFontPending = false
            switch (tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1)) {
                case 1:
                    this.mFontTypeface = Typeface.SANS_SERIF
                    break
                case 2:
                    this.mFontTypeface = Typeface.SERIF
                    break
                case 3:
                    this.mFontTypeface = Typeface.MONOSPACE
                    break
            }
        }
    }

    Unit applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Array<Drawable> compoundDrawables = this.mView.getCompoundDrawables()
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint)
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint)
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint)
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint)
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (this.mDrawableStartTint == null && this.mDrawableEndTint == null) {
                return
            }
            Array<Drawable> compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative()
            applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint)
            applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint)
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText()
    }

    Int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize()
    }

    Int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize()
    }

    Int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity()
    }

    Array<Int> getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes()
    }

    Int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled()
    }

    @SuppressLint({"NewApi"})
    Unit loadFromAttributes(AttributeSet attributeSet, Int i) {
        ColorStateList colorStateList
        ColorStateList colorStateList2
        Boolean z
        Boolean z2
        ColorStateList colorStateList3 = null
        Context context = this.mView.getContext()
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get()
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextHelper, i, 0)
        Int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1)
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0))
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0))
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0))
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0))
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
                this.mDrawableStartTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0))
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.mDrawableEndTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0))
            }
        }
        tintTypedArrayObtainStyledAttributes.recycle()
        Boolean z3 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod
        if (resourceId != -1) {
            TintTypedArray tintTypedArrayObtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, R.styleable.TextAppearance)
            if (z3 || !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z = false
                z2 = false
            } else {
                z2 = tintTypedArrayObtainStyledAttributes2.getBoolean(R.styleable.TextAppearance_textAllCaps, false)
                z = true
            }
            updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes2)
            if (Build.VERSION.SDK_INT < 23) {
                colorStateList2 = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColor) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColor) : null
                colorStateList = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorHint) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null
                if (tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                    colorStateList3 = tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorLink)
                }
            } else {
                colorStateList = null
                colorStateList2 = null
            }
            tintTypedArrayObtainStyledAttributes2.recycle()
        } else {
            colorStateList = null
            colorStateList2 = null
            z = false
            z2 = false
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextAppearance, i, 0)
        if (!z3 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z2 = tintTypedArrayObtainStyledAttributes3.getBoolean(R.styleable.TextAppearance_textAllCaps, false)
            z = true
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                colorStateList2 = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColor)
            }
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorHint)
            }
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList3 = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorLink)
            }
        }
        if (Build.VERSION.SDK_INT >= 28 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArrayObtainStyledAttributes3.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f)
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes3)
        tintTypedArrayObtainStyledAttributes3.recycle()
        if (colorStateList2 != null) {
            this.mView.setTextColor(colorStateList2)
        }
        if (colorStateList != null) {
            this.mView.setHintTextColor(colorStateList)
        }
        if (colorStateList3 != null) {
            this.mView.setLinkTextColor(colorStateList3)
        }
        if (!z3 && z) {
            setAllCaps(z2)
        }
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle)
        }
        this.mAutoSizeTextHelper.loadFromAttributes(attributeSet, i)
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
            Array<Int> autoSizeTextAvailableSizes = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes()
            if (autoSizeTextAvailableSizes.length > 0) {
                if (this.mView.getAutoSizeStepGranularity() != -1.0f) {
                    this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0)
                } else {
                    this.mView.setAutoSizeTextTypeUniformWithPresetSizes(autoSizeTextAvailableSizes, 0)
                }
            }
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextView)
        Int dimensionPixelSize = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1)
        Int dimensionPixelSize2 = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1)
        Int dimensionPixelSize3 = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, -1)
        tintTypedArrayObtainStyledAttributes4.recycle()
        if (dimensionPixelSize != -1) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, dimensionPixelSize)
        }
        if (dimensionPixelSize2 != -1) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, dimensionPixelSize2)
        }
        if (dimensionPixelSize3 != -1) {
            TextViewCompat.setLineHeight(this.mView, dimensionPixelSize3)
        }
    }

    Unit onAsyncTypefaceReceived(WeakReference weakReference, Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface
            TextView textView = (TextView) weakReference.get()
            if (textView != null) {
                textView.setTypeface(typeface, this.mStyle)
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            return
        }
        autoSizeText()
    }

    Unit onSetTextAppearance(Context context, Int i) {
        ColorStateList colorStateList
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance)
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false))
        }
        if (Build.VERSION.SDK_INT < 23 && tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(colorStateList)
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f)
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes)
        tintTypedArrayObtainStyledAttributes.recycle()
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle)
        }
    }

    Unit setAllCaps(Boolean z) {
        this.mView.setAllCaps(z)
    }

    Unit setAutoSizeTextTypeUniformWithConfiguration(Int i, Int i2, Int i3, Int i4) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4)
    }

    Unit setAutoSizeTextTypeUniformWithPresetSizes(@NonNull Array<Int> iArr, Int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i)
    }

    Unit setAutoSizeTextTypeWithDefaults(Int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit setTextSize(Int i, Float f) {
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE || isAutoSizeEnabled()) {
            return
        }
        setTextSizeInternal(i, f)
    }
}
