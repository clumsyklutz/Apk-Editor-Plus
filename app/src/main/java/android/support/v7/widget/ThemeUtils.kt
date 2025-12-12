package android.support.v7.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.graphics.ColorUtils
import android.util.AttributeSet
import android.util.TypedValue

class ThemeUtils {
    private static val TL_TYPED_VALUE = ThreadLocal()
    static final Array<Int> DISABLED_STATE_SET = {-16842910}
    static final Array<Int> FOCUSED_STATE_SET = {R.attr.state_focused}
    static final Array<Int> ACTIVATED_STATE_SET = {R.attr.state_activated}
    static final Array<Int> PRESSED_STATE_SET = {R.attr.state_pressed}
    static final Array<Int> CHECKED_STATE_SET = {R.attr.state_checked}
    static final Array<Int> SELECTED_STATE_SET = {R.attr.state_selected}
    static final Array<Int> NOT_PRESSED_OR_FOCUSED_STATE_SET = {-16842919, -16842908}
    static final Array<Int> EMPTY_STATE_SET = new Int[0]
    private static final Array<Int> TEMP_ARRAY = new Int[1]

    private constructor() {
    }

    fun createDisabledStateList(Int i, Int i2) {
        return ColorStateList(new Array<Int>[]{DISABLED_STATE_SET, EMPTY_STATE_SET}, new Array<Int>{i2, i})
    }

    fun getDisabledThemeAttrColor(Context context, Int i) {
        ColorStateList themeAttrColorStateList = getThemeAttrColorStateList(context, i)
        if (themeAttrColorStateList != null && themeAttrColorStateList.isStateful()) {
            return themeAttrColorStateList.getColorForState(DISABLED_STATE_SET, themeAttrColorStateList.getDefaultColor())
        }
        TypedValue typedValue = getTypedValue()
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true)
        return getThemeAttrColor(context, i, typedValue.getFloat())
    }

    fun getThemeAttrColor(Context context, Int i) {
        TEMP_ARRAY[0] = i
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, TEMP_ARRAY)
        try {
            return tintTypedArrayObtainStyledAttributes.getColor(0, 0)
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle()
        }
    }

    static Int getThemeAttrColor(Context context, Int i, Float f) {
        return ColorUtils.setAlphaComponent(getThemeAttrColor(context, i), Math.round(Color.alpha(r0) * f))
    }

    fun getThemeAttrColorStateList(Context context, Int i) {
        TEMP_ARRAY[0] = i
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, TEMP_ARRAY)
        try {
            return tintTypedArrayObtainStyledAttributes.getColorStateList(0)
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle()
        }
    }

    private fun getTypedValue() {
        TypedValue typedValue = (TypedValue) TL_TYPED_VALUE.get()
        if (typedValue != null) {
            return typedValue
        }
        TypedValue typedValue2 = TypedValue()
        TL_TYPED_VALUE.set(typedValue2)
        return typedValue2
    }
}
