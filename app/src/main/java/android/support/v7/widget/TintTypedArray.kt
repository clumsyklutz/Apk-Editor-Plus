package android.support.v7.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.annotation.StyleableRes
import androidx.core.content.res.ResourcesCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.util.TypedValue

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TintTypedArray {
    private final Context mContext
    private TypedValue mTypedValue
    private final TypedArray mWrapped

    private constructor(Context context, TypedArray typedArray) {
        this.mContext = context
        this.mWrapped = typedArray
    }

    fun obtainStyledAttributes(Context context, Int i, Array<Int> iArr) {
        return TintTypedArray(context, context.obtainStyledAttributes(i, iArr))
    }

    fun obtainStyledAttributes(Context context, AttributeSet attributeSet, Array<Int> iArr) {
        return TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr))
    }

    fun obtainStyledAttributes(Context context, AttributeSet attributeSet, Array<Int> iArr, Int i, Int i2) {
        return TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2))
    }

    fun getBoolean(Int i, Boolean z) {
        return this.mWrapped.getBoolean(i, z)
    }

    @RequiresApi(21)
    fun getChangingConfigurations() {
        return this.mWrapped.getChangingConfigurations()
    }

    fun getColor(Int i, Int i2) {
        return this.mWrapped.getColor(i, i2)
    }

    fun getColorStateList(Int i) {
        Int resourceId
        ColorStateList colorStateList
        return (!this.mWrapped.hasValue(i) || (resourceId = this.mWrapped.getResourceId(i, 0)) == 0 || (colorStateList = AppCompatResources.getColorStateList(this.mContext, resourceId)) == null) ? this.mWrapped.getColorStateList(i) : colorStateList
    }

    fun getDimension(Int i, Float f) {
        return this.mWrapped.getDimension(i, f)
    }

    fun getDimensionPixelOffset(Int i, Int i2) {
        return this.mWrapped.getDimensionPixelOffset(i, i2)
    }

    fun getDimensionPixelSize(Int i, Int i2) {
        return this.mWrapped.getDimensionPixelSize(i, i2)
    }

    fun getDrawable(Int i) {
        Int resourceId
        return (!this.mWrapped.hasValue(i) || (resourceId = this.mWrapped.getResourceId(i, 0)) == 0) ? this.mWrapped.getDrawable(i) : AppCompatResources.getDrawable(this.mContext, resourceId)
    }

    fun getDrawableIfKnown(Int i) {
        Int resourceId
        if (!this.mWrapped.hasValue(i) || (resourceId = this.mWrapped.getResourceId(i, 0)) == 0) {
            return null
        }
        return AppCompatDrawableManager.get().getDrawable(this.mContext, resourceId, true)
    }

    fun getFloat(Int i, Float f) {
        return this.mWrapped.getFloat(i, f)
    }

    @Nullable
    fun getFont(@StyleableRes Int i, Int i2, @Nullable ResourcesCompat.FontCallback fontCallback) {
        Int resourceId = this.mWrapped.getResourceId(i, 0)
        if (resourceId == 0) {
            return null
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = TypedValue()
        }
        return ResourcesCompat.getFont(this.mContext, resourceId, this.mTypedValue, i2, fontCallback)
    }

    fun getFraction(Int i, Int i2, Int i3, Float f) {
        return this.mWrapped.getFraction(i, i2, i3, f)
    }

    fun getIndex(Int i) {
        return this.mWrapped.getIndex(i)
    }

    fun getIndexCount() {
        return this.mWrapped.getIndexCount()
    }

    fun getInt(Int i, Int i2) {
        return this.mWrapped.getInt(i, i2)
    }

    fun getInteger(Int i, Int i2) {
        return this.mWrapped.getInteger(i, i2)
    }

    fun getLayoutDimension(Int i, Int i2) {
        return this.mWrapped.getLayoutDimension(i, i2)
    }

    fun getLayoutDimension(Int i, String str) {
        return this.mWrapped.getLayoutDimension(i, str)
    }

    fun getNonResourceString(Int i) {
        return this.mWrapped.getNonResourceString(i)
    }

    fun getPositionDescription() {
        return this.mWrapped.getPositionDescription()
    }

    fun getResourceId(Int i, Int i2) {
        return this.mWrapped.getResourceId(i, i2)
    }

    fun getResources() {
        return this.mWrapped.getResources()
    }

    fun getString(Int i) {
        return this.mWrapped.getString(i)
    }

    fun getText(Int i) {
        return this.mWrapped.getText(i)
    }

    public Array<CharSequence> getTextArray(Int i) {
        return this.mWrapped.getTextArray(i)
    }

    fun getType(Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mWrapped.getType(i)
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = TypedValue()
        }
        this.mWrapped.getValue(i, this.mTypedValue)
        return this.mTypedValue.type
    }

    fun getValue(Int i, TypedValue typedValue) {
        return this.mWrapped.getValue(i, typedValue)
    }

    fun hasValue(Int i) {
        return this.mWrapped.hasValue(i)
    }

    fun length() {
        return this.mWrapped.length()
    }

    fun peekValue(Int i) {
        return this.mWrapped.peekValue(i)
    }

    fun recycle() {
        this.mWrapped.recycle()
    }
}
