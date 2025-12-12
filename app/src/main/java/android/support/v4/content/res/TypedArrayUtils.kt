package android.support.v4.content.res

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.AnyRes
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.util.TypedValue
import org.xmlpull.v1.XmlPullParser

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypedArrayUtils {
    private static val NAMESPACE = "http://schemas.android.com/apk/res/android"

    private constructor() {
    }

    fun getAttr(@NonNull Context context, Int i, Int i2) {
        TypedValue typedValue = TypedValue()
        context.getTheme().resolveAttribute(i, typedValue, true)
        return typedValue.resourceId != 0 ? i : i2
    }

    fun getBoolean(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2, Boolean z) {
        return typedArray.getBoolean(i, typedArray.getBoolean(i2, z))
    }

    @Nullable
    fun getDrawable(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2) {
        Drawable drawable = typedArray.getDrawable(i)
        return drawable == null ? typedArray.getDrawable(i2) : drawable
    }

    fun getInt(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2, Int i3) {
        return typedArray.getInt(i, typedArray.getInt(i2, i3))
    }

    fun getNamedBoolean(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, @StyleableRes Int i, Boolean z) {
        return !hasAttribute(xmlPullParser, str) ? z : typedArray.getBoolean(i, z)
    }

    @ColorInt
    fun getNamedColor(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, @StyleableRes Int i, @ColorInt Int i2) {
        return !hasAttribute(xmlPullParser, str) ? i2 : typedArray.getColor(i, i2)
    }

    fun getNamedComplexColor(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme, @NonNull String str, @StyleableRes Int i, @ColorInt Int i2) {
        if (hasAttribute(xmlPullParser, str)) {
            TypedValue typedValue = TypedValue()
            typedArray.getValue(i, typedValue)
            if (typedValue.type >= 28 && typedValue.type <= 31) {
                return ComplexColorCompat.from(typedValue.data)
            }
            ComplexColorCompat complexColorCompatInflate = ComplexColorCompat.inflate(typedArray.getResources(), typedArray.getResourceId(i, 0), theme)
            if (complexColorCompatInflate != null) {
                return complexColorCompatInflate
            }
        }
        return ComplexColorCompat.from(i2)
    }

    fun getNamedFloat(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, @StyleableRes Int i, Float f) {
        return !hasAttribute(xmlPullParser, str) ? f : typedArray.getFloat(i, f)
    }

    fun getNamedInt(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, @StyleableRes Int i, Int i2) {
        return !hasAttribute(xmlPullParser, str) ? i2 : typedArray.getInt(i, i2)
    }

    @AnyRes
    fun getNamedResourceId(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, @StyleableRes Int i, @AnyRes Int i2) {
        return !hasAttribute(xmlPullParser, str) ? i2 : typedArray.getResourceId(i, i2)
    }

    @Nullable
    fun getNamedString(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, @StyleableRes Int i) {
        if (hasAttribute(xmlPullParser, str)) {
            return typedArray.getString(i)
        }
        return null
    }

    @AnyRes
    fun getResourceId(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2, @AnyRes Int i3) {
        return typedArray.getResourceId(i, typedArray.getResourceId(i2, i3))
    }

    @Nullable
    fun getString(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2) {
        String string = typedArray.getString(i)
        return string == null ? typedArray.getString(i2) : string
    }

    @Nullable
    fun getText(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2) {
        CharSequence text = typedArray.getText(i)
        return text == null ? typedArray.getText(i2) : text
    }

    @Nullable
    public static Array<CharSequence> getTextArray(@NonNull TypedArray typedArray, @StyleableRes Int i, @StyleableRes Int i2) {
        Array<CharSequence> textArray = typedArray.getTextArray(i)
        return textArray == null ? typedArray.getTextArray(i2) : textArray
    }

    fun hasAttribute(@NonNull XmlPullParser xmlPullParser, @NonNull String str) {
        return xmlPullParser.getAttributeValue(NAMESPACE, str) != null
    }

    @NonNull
    fun obtainAttributes(@NonNull Resources resources, @Nullable Resources.Theme theme, @NonNull AttributeSet attributeSet, @NonNull Array<Int> iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0)
    }

    @Nullable
    fun peekNamedValue(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String str, Int i) {
        if (hasAttribute(xmlPullParser, str)) {
            return typedArray.peekValue(i)
        }
        return null
    }
}
