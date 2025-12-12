package android.support.v4.content.res

import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.compat.R
import android.util.AttributeSet
import android.util.StateSet
import android.util.Xml
import java.io.IOException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ColorStateListInflaterCompat {
    private static val DEFAULT_COLOR = -65536

    private constructor() {
    }

    @NonNull
    fun createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        Int next
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser)
        do {
            next = xmlPullParser.next()
            if (next == 2) {
                break
            }
        } while (next != 1);
        if (next != 2) {
            throw XmlPullParserException("No start tag found")
        }
        return createFromXmlInner(resources, xmlPullParser, attributeSetAsAttributeSet, theme)
    }

    @NonNull
    fun createFromXmlInner(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException {
        String name = xmlPullParser.getName()
        if (name.equals("selector")) {
            return inflate(resources, xmlPullParser, attributeSet, theme)
        }
        throw XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name)
    }

    private fun inflate(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        Int depth
        Int i
        Int depth2 = xmlPullParser.getDepth() + 1
        Array<Int>[] iArr = new Int[20][]
        Int i2 = 0
        Array<Int> iArr2 = new Int[20]
        while (true) {
            Int next = xmlPullParser.next()
            if (next == 1 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.ColorStateListItem)
                Int color = typedArrayObtainAttributes.getColor(R.styleable.ColorStateListItem_android_color, -65281)
                Float f = 1.0f
                if (typedArrayObtainAttributes.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                    f = typedArrayObtainAttributes.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f)
                } else if (typedArrayObtainAttributes.hasValue(R.styleable.ColorStateListItem_alpha)) {
                    f = typedArrayObtainAttributes.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f)
                }
                typedArrayObtainAttributes.recycle()
                Int i3 = 0
                Int attributeCount = attributeSet.getAttributeCount()
                Array<Int> iArr3 = new Int[attributeCount]
                Int i4 = 0
                while (i4 < attributeCount) {
                    Int attributeNameResource = attributeSet.getAttributeNameResource(i4)
                    if (attributeNameResource == 16843173 || attributeNameResource == 16843551 || attributeNameResource == R.attr.alpha) {
                        i = i3
                    } else {
                        Int i5 = i3 + 1
                        if (!attributeSet.getAttributeBooleanValue(i4, false)) {
                            attributeNameResource = -attributeNameResource
                        }
                        iArr3[i3] = attributeNameResource
                        i = i5
                    }
                    i4++
                    i3 = i
                }
                Array<Int> iArrTrimStateSet = StateSet.trimStateSet(iArr3, i3)
                Array<Int> iArrAppend = GrowingArrayUtils.append(iArr2, i2, modulateColorAlpha(color, f))
                Array<Int>[] iArr4 = (Array<Int>[]) GrowingArrayUtils.append(iArr, i2, iArrTrimStateSet)
                i2++
                iArr = iArr4
                iArr2 = iArrAppend
            }
        }
        Array<Int> iArr5 = new Int[i2]
        Array<Int>[] iArr6 = new Int[i2][]
        System.arraycopy(iArr2, 0, iArr5, 0, i2)
        System.arraycopy(iArr, 0, iArr6, 0, i2)
        return ColorStateList(iArr6, iArr5)
    }

    @ColorInt
    private fun modulateColorAlpha(@ColorInt Int i, @FloatRange(from = 0.0d, to = 1.0d) Float f) {
        return (Math.round(Color.alpha(i) * f) << 24) | (16777215 & i)
    }

    private fun obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, Array<Int> iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0)
    }
}
