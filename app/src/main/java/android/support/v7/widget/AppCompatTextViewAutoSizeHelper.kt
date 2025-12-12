package android.support.v7.widget

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.RectF
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import androidx.appcompat.R
import android.support.v7.widget.ActivityChooserView
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap

class AppCompatTextViewAutoSizeHelper {
    private static val DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1
    private static val DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112
    private static val DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12
    private static val TAG = "ACTVAutoSizeHelper"
    static val UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0f
    private static val VERY_WIDE = 1048576
    private final Context mContext
    private TextPaint mTempTextPaint
    private final TextView mTextView
    private static val TEMP_RECTF = RectF()
    private static ConcurrentHashMap sTextViewMethodByNameCache = ConcurrentHashMap()
    private Int mAutoSizeTextType = 0
    private Boolean mNeedsAutoSizeText = false
    private Float mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
    private Float mAutoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
    private Float mAutoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
    private Array<Int> mAutoSizeTextSizesInPx = new Int[0]
    private Boolean mHasPresetAutoSizeValues = false

    AppCompatTextViewAutoSizeHelper(TextView textView) {
        this.mTextView = textView
        this.mContext = this.mTextView.getContext()
    }

    private Array<Int> cleanupAutoSizePresetSizes(Array<Int> iArr) {
        Int length = iArr.length
        if (length != 0) {
            Arrays.sort(iArr)
            ArrayList arrayList = ArrayList()
            for (Int i : iArr) {
                if (i > 0 && Collections.binarySearch(arrayList, Integer.valueOf(i)) < 0) {
                    arrayList.add(Integer.valueOf(i))
                }
            }
            if (length != arrayList.size()) {
                Int size = arrayList.size()
                iArr = new Int[size]
                for (Int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = ((Integer) arrayList.get(i2)).intValue()
                }
            }
        }
        return iArr
    }

    private fun clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0
        this.mAutoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
        this.mAutoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
        this.mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
        this.mAutoSizeTextSizesInPx = new Int[0]
        this.mNeedsAutoSizeText = false
    }

    @RequiresApi(23)
    private fun createStaticLayoutForMeasuring(CharSequence charSequence, Layout.Alignment alignment, Int i, Int i2) {
        TextDirectionHeuristic textDirectionHeuristic = (TextDirectionHeuristic) invokeAndReturnWithDefault(this.mTextView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR)
        StaticLayout.Builder hyphenationFrequency = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), this.mTempTextPaint, i).setAlignment(alignment).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency())
        if (i2 == -1) {
            i2 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        }
        return hyphenationFrequency.setMaxLines(i2).setTextDirection(textDirectionHeuristic).build()
    }

    private fun createStaticLayoutForMeasuringPre23(CharSequence charSequence, Layout.Alignment alignment, Int i) {
        Float fFloatValue
        Float fFloatValue2
        Boolean zBooleanValue
        if (Build.VERSION.SDK_INT >= 16) {
            fFloatValue = this.mTextView.getLineSpacingMultiplier()
            fFloatValue2 = this.mTextView.getLineSpacingExtra()
            zBooleanValue = this.mTextView.getIncludeFontPadding()
        } else {
            fFloatValue = ((Float) invokeAndReturnWithDefault(this.mTextView, "getLineSpacingMultiplier", Float.valueOf(1.0f))).floatValue()
            fFloatValue2 = ((Float) invokeAndReturnWithDefault(this.mTextView, "getLineSpacingExtra", Float.valueOf(0.0f))).floatValue()
            zBooleanValue = ((Boolean) invokeAndReturnWithDefault(this.mTextView, "getIncludeFontPadding", true)).booleanValue()
        }
        return StaticLayout(charSequence, this.mTempTextPaint, i, alignment, fFloatValue, fFloatValue2, zBooleanValue)
    }

    private fun findLargestTextSizeWhichFits(RectF rectF) {
        Int length = this.mAutoSizeTextSizesInPx.length
        if (length == 0) {
            throw IllegalStateException("No available text sizes to choose from.")
        }
        Int i = 0
        Int i2 = 1
        Int i3 = length - 1
        while (i2 <= i3) {
            Int i4 = (i2 + i3) / 2
            if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[i4], rectF)) {
                Int i5 = i4 + 1
                i = i2
                i2 = i5
            } else {
                i = i4 - 1
                i3 = i
            }
        }
        return this.mAutoSizeTextSizesInPx[i]
    }

    @Nullable
    private fun getTextViewMethod(@NonNull String str) throws NoSuchMethodException, SecurityException {
        try {
            Method method = (Method) sTextViewMethodByNameCache.get(str)
            if (method != null) {
                return method
            }
            Method declaredMethod = TextView.class.getDeclaredMethod(str, new Class[0])
            if (declaredMethod == null) {
                return declaredMethod
            }
            declaredMethod.setAccessible(true)
            sTextViewMethodByNameCache.put(str, declaredMethod)
            return declaredMethod
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve TextView#" + str + "() method", e)
            return null
        }
    }

    private fun invokeAndReturnWithDefault(@NonNull Object obj, @NonNull String str, @NonNull Object obj2) {
        try {
            obj2 = getTextViewMethod(str).invoke(obj, new Object[0])
            if (obj2 == null) {
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to invoke TextView#" + str + "() method", e)
        }
        return obj2
    }

    private fun setRawTextSize(Float f) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (f != this.mTextView.getPaint().getTextSize()) {
            this.mTextView.getPaint().setTextSize(f)
            Boolean zIsInLayout = Build.VERSION.SDK_INT >= 18 ? this.mTextView.isInLayout() : false
            if (this.mTextView.getLayout() != null) {
                this.mNeedsAutoSizeText = false
                try {
                    Method textViewMethod = getTextViewMethod("nullLayouts")
                    if (textViewMethod != null) {
                        textViewMethod.invoke(this.mTextView, new Object[0])
                    }
                } catch (Exception e) {
                    Log.w(TAG, "Failed to invoke TextView#nullLayouts() method", e)
                }
                if (zIsInLayout) {
                    this.mTextView.forceLayout()
                } else {
                    this.mTextView.requestLayout()
                }
                this.mTextView.invalidate()
            }
        }
    }

    private fun setupAutoSizeText() {
        if (supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                Float fRound = Math.round(this.mAutoSizeMinTextSizeInPx)
                Int i = 1
                while (Math.round(this.mAutoSizeStepGranularityInPx + fRound) <= Math.round(this.mAutoSizeMaxTextSizeInPx)) {
                    i++
                    fRound += this.mAutoSizeStepGranularityInPx
                }
                Array<Int> iArr = new Int[i]
                Float f = this.mAutoSizeMinTextSizeInPx
                for (Int i2 = 0; i2 < i; i2++) {
                    iArr[i2] = Math.round(f)
                    f += this.mAutoSizeStepGranularityInPx
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr)
            }
            this.mNeedsAutoSizeText = true
        } else {
            this.mNeedsAutoSizeText = false
        }
        return this.mNeedsAutoSizeText
    }

    private fun setupAutoSizeUniformPresetSizes(TypedArray typedArray) {
        Int length = typedArray.length()
        Array<Int> iArr = new Int[length]
        if (length > 0) {
            for (Int i = 0; i < length; i++) {
                iArr[i] = typedArray.getDimensionPixelSize(i, -1)
            }
            this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr)
            setupAutoSizeUniformPresetSizesConfiguration()
        }
    }

    private fun setupAutoSizeUniformPresetSizesConfiguration() {
        this.mHasPresetAutoSizeValues = this.mAutoSizeTextSizesInPx.length > 0
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1
            this.mAutoSizeMinTextSizeInPx = this.mAutoSizeTextSizesInPx[0]
            this.mAutoSizeMaxTextSizeInPx = this.mAutoSizeTextSizesInPx[r3 - 1]
            this.mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
        }
        return this.mHasPresetAutoSizeValues
    }

    private fun suggestedSizeFitsInSpace(Int i, RectF rectF) {
        CharSequence transformation
        CharSequence text = this.mTextView.getText()
        TransformationMethod transformationMethod = this.mTextView.getTransformationMethod()
        if (transformationMethod != null && (transformation = transformationMethod.getTransformation(text, this.mTextView)) != null) {
            text = transformation
        }
        Int maxLines = Build.VERSION.SDK_INT >= 16 ? this.mTextView.getMaxLines() : -1
        if (this.mTempTextPaint == null) {
            this.mTempTextPaint = TextPaint()
        } else {
            this.mTempTextPaint.reset()
        }
        this.mTempTextPaint.set(this.mTextView.getPaint())
        this.mTempTextPaint.setTextSize(i)
        Layout.Alignment alignment = (Layout.Alignment) invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL)
        StaticLayout staticLayoutCreateStaticLayoutForMeasuring = Build.VERSION.SDK_INT >= 23 ? createStaticLayoutForMeasuring(text, alignment, Math.round(rectF.right), maxLines) : createStaticLayoutForMeasuringPre23(text, alignment, Math.round(rectF.right))
        if (maxLines == -1 || (staticLayoutCreateStaticLayoutForMeasuring.getLineCount() <= maxLines && staticLayoutCreateStaticLayoutForMeasuring.getLineEnd(staticLayoutCreateStaticLayoutForMeasuring.getLineCount() - 1) == text.length())) {
            return ((Float) staticLayoutCreateStaticLayoutForMeasuring.getHeight()) <= rectF.bottom
        }
        return false
    }

    private fun supportsAutoSizeText() {
        return !(this.mTextView is AppCompatEditText)
    }

    private fun validateAndSetAutoSizeTextTypeUniformConfiguration(Float f, Float f2, Float f3) {
        if (f <= 0.0f) {
            throw IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)")
        }
        if (f2 <= f) {
            throw IllegalArgumentException("Maximum auto-size text size (" + f2 + "px) is less or equal to minimum auto-size text size (" + f + "px)")
        }
        if (f3 <= 0.0f) {
            throw IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)")
        }
        this.mAutoSizeTextType = 1
        this.mAutoSizeMinTextSizeInPx = f
        this.mAutoSizeMaxTextSizeInPx = f2
        this.mAutoSizeStepGranularityInPx = f3
        this.mHasPresetAutoSizeValues = false
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit autoSizeText() {
        if (isAutoSizeEnabled()) {
            if (this.mNeedsAutoSizeText) {
                if (this.mTextView.getMeasuredHeight() <= 0 || this.mTextView.getMeasuredWidth() <= 0) {
                    return
                }
                Int measuredWidth = ((Boolean) invokeAndReturnWithDefault(this.mTextView, "getHorizontallyScrolling", false)).booleanValue() ? 1048576 : (this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft()) - this.mTextView.getTotalPaddingRight()
                Int height = (this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom()) - this.mTextView.getCompoundPaddingTop()
                if (measuredWidth <= 0 || height <= 0) {
                    return
                }
                synchronized (TEMP_RECTF) {
                    TEMP_RECTF.setEmpty()
                    TEMP_RECTF.right = measuredWidth
                    TEMP_RECTF.bottom = height
                    Float fFindLargestTextSizeWhichFits = findLargestTextSizeWhichFits(TEMP_RECTF)
                    if (fFindLargestTextSizeWhichFits != this.mTextView.getTextSize()) {
                        setTextSizeInternal(0, fFindLargestTextSizeWhichFits)
                    }
                }
            }
            this.mNeedsAutoSizeText = true
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Int getAutoSizeMaxTextSize() {
        return Math.round(this.mAutoSizeMaxTextSizeInPx)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Int getAutoSizeMinTextSize() {
        return Math.round(this.mAutoSizeMinTextSizeInPx)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Int getAutoSizeStepGranularity() {
        return Math.round(this.mAutoSizeStepGranularityInPx)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Array<Int> getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Int getAutoSizeTextType() {
        return this.mAutoSizeTextType
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Boolean isAutoSizeEnabled() {
        return supportsAutoSizeText() && this.mAutoSizeTextType != 0
    }

    Unit loadFromAttributes(AttributeSet attributeSet, Int i) throws Resources.NotFoundException {
        Int resourceId
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.AppCompatTextView, i, 0)
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
            this.mAutoSizeTextType = typedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0)
        }
        Float dimension = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) : -1.0f
        Float dimension2 = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) : -1.0f
        Float dimension3 = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) : -1.0f
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes.getResources().obtainTypedArray(resourceId)
            setupAutoSizeUniformPresetSizes(typedArrayObtainTypedArray)
            typedArrayObtainTypedArray.recycle()
        }
        typedArrayObtainStyledAttributes.recycle()
        if (!supportsAutoSizeText()) {
            this.mAutoSizeTextType = 0
            return
        }
        if (this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues) {
                DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics()
                if (dimension2 == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics)
                }
                if (dimension3 == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
                    dimension3 = TypedValue.applyDimension(2, 112.0f, displayMetrics)
                }
                if (dimension == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
                    dimension = 1.0f
                }
                validateAndSetAutoSizeTextTypeUniformConfiguration(dimension2, dimension3, dimension)
            }
            setupAutoSizeText()
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit setAutoSizeTextTypeUniformWithConfiguration(Int i, Int i2, Int i3, Int i4) {
        if (supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics()
            validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(i4, i, displayMetrics), TypedValue.applyDimension(i4, i2, displayMetrics), TypedValue.applyDimension(i4, i3, displayMetrics))
            if (setupAutoSizeText()) {
                autoSizeText()
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit setAutoSizeTextTypeUniformWithPresetSizes(@NonNull Array<Int> iArr, Int i) {
        if (supportsAutoSizeText()) {
            Int length = iArr.length
            if (length > 0) {
                Array<Int> iArrCopyOf = new Int[length]
                if (i == 0) {
                    iArrCopyOf = Arrays.copyOf(iArr, length)
                } else {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics()
                    for (Int i2 = 0; i2 < length; i2++) {
                        iArrCopyOf[i2] = Math.round(TypedValue.applyDimension(i, iArr[i2], displayMetrics))
                    }
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArrCopyOf)
                if (!setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(iArr))
                }
            } else {
                this.mHasPresetAutoSizeValues = false
            }
            if (setupAutoSizeText()) {
                autoSizeText()
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit setAutoSizeTextTypeWithDefaults(Int i) {
        if (supportsAutoSizeText()) {
            switch (i) {
                case 0:
                    clearAutoSizeConfiguration()
                    return
                case 1:
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics()
                    validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f)
                    if (setupAutoSizeText()) {
                        autoSizeText()
                        return
                    }
                    return
                default:
                    throw IllegalArgumentException("Unknown auto-size text type: " + i)
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit setTextSizeInternal(Int i, Float f) {
        setRawTextSize(TypedValue.applyDimension(i, f, (this.mContext == null ? Resources.getSystem() : this.mContext.getResources()).getDisplayMetrics()))
    }
}
