package android.support.v4.content.res

import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.compat.R
import android.util.AttributeSet
import android.util.Xml
import java.io.IOException
import java.util.List
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
final class GradientColorInflaterCompat {
    private static val TILE_MODE_CLAMP = 0
    private static val TILE_MODE_MIRROR = 2
    private static val TILE_MODE_REPEAT = 1

    final class ColorStops {
        final Array<Int> mColors
        final Array<Float> mOffsets

        ColorStops(@ColorInt Int i, @ColorInt Int i2) {
            this.mColors = new Array<Int>{i, i2}
            this.mOffsets = new Array<Float>{0.0f, 1.0f}
        }

        ColorStops(@ColorInt Int i, @ColorInt Int i2, @ColorInt Int i3) {
            this.mColors = new Array<Int>{i, i2, i3}
            this.mOffsets = new Array<Float>{0.0f, 0.5f, 1.0f}
        }

        ColorStops(@NonNull List list, @NonNull List list2) {
            Int size = list.size()
            this.mColors = new Int[size]
            this.mOffsets = new Float[size]
            for (Int i = 0; i < size; i++) {
                this.mColors[i] = ((Integer) list.get(i)).intValue()
                this.mOffsets[i] = ((Float) list2.get(i)).floatValue()
            }
        }
    }

    private constructor() {
    }

    private fun checkColors(@Nullable ColorStops colorStops, @ColorInt Int i, @ColorInt Int i2, Boolean z, @ColorInt Int i3) {
        return colorStops != null ? colorStops : z ? ColorStops(i, i3, i2) : ColorStops(i, i2)
    }

    static Shader createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
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

    static Shader createFromXmlInner(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException {
        String name = xmlPullParser.getName()
        if (!name.equals("gradient")) {
            throw XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid gradient color tag " + name)
        }
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientColor)
        Float namedFloat = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "startX", R.styleable.GradientColor_android_startX, 0.0f)
        Float namedFloat2 = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "startY", R.styleable.GradientColor_android_startY, 0.0f)
        Float namedFloat3 = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "endX", R.styleable.GradientColor_android_endX, 0.0f)
        Float namedFloat4 = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "endY", R.styleable.GradientColor_android_endY, 0.0f)
        Float namedFloat5 = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "centerX", R.styleable.GradientColor_android_centerX, 0.0f)
        Float namedFloat6 = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "centerY", R.styleable.GradientColor_android_centerY, 0.0f)
        Int namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "type", R.styleable.GradientColor_android_type, 0)
        Int namedColor = TypedArrayUtils.getNamedColor(typedArrayObtainAttributes, xmlPullParser, "startColor", R.styleable.GradientColor_android_startColor, 0)
        Boolean zHasAttribute = TypedArrayUtils.hasAttribute(xmlPullParser, "centerColor")
        Int namedColor2 = TypedArrayUtils.getNamedColor(typedArrayObtainAttributes, xmlPullParser, "centerColor", R.styleable.GradientColor_android_centerColor, 0)
        Int namedColor3 = TypedArrayUtils.getNamedColor(typedArrayObtainAttributes, xmlPullParser, "endColor", R.styleable.GradientColor_android_endColor, 0)
        Int namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "tileMode", R.styleable.GradientColor_android_tileMode, 0)
        Float namedFloat7 = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "gradientRadius", R.styleable.GradientColor_android_gradientRadius, 0.0f)
        typedArrayObtainAttributes.recycle()
        ColorStops colorStopsCheckColors = checkColors(inflateChildElements(resources, xmlPullParser, attributeSet, theme), namedColor, namedColor3, zHasAttribute, namedColor2)
        switch (namedInt) {
            case 1:
                if (namedFloat7 <= 0.0f) {
                    throw XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type")
                }
                return RadialGradient(namedFloat5, namedFloat6, namedFloat7, colorStopsCheckColors.mColors, colorStopsCheckColors.mOffsets, parseTileMode(namedInt2))
            case 2:
                return SweepGradient(namedFloat5, namedFloat6, colorStopsCheckColors.mColors, colorStopsCheckColors.mOffsets)
            default:
                return LinearGradient(namedFloat, namedFloat2, namedFloat3, namedFloat4, colorStopsCheckColors.mColors, colorStopsCheckColors.mOffsets, parseTileMode(namedInt2))
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x008a, code lost:
    
        if (r2.size() <= 0) goto L24
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0091, code lost:
    
        return new android.support.v4.content.res.GradientColorInflaterCompat.ColorStops(r2, r1)
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0092, code lost:
    
        return null
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.support.v4.content.res.GradientColorInflaterCompat.ColorStops inflateChildElements(@android.support.annotation.NonNull android.content.res.Resources r7, @android.support.annotation.NonNull org.xmlpull.v1.XmlPullParser r8, @android.support.annotation.NonNull android.util.AttributeSet r9, @android.support.annotation.Nullable android.content.res.Resources.Theme r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r3 = 20
            Int r0 = r8.getDepth()
            Int r0 = r0 + 1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r3)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r3)
        L12:
            Int r3 = r8.next()
            r4 = 1
            if (r3 == r4) goto L86
            Int r4 = r8.getDepth()
            if (r4 >= r0) goto L22
            r5 = 3
            if (r3 == r5) goto L86
        L22:
            r5 = 2
            if (r3 != r5) goto L12
            if (r4 > r0) goto L12
            java.lang.String r3 = r8.getName()
            java.lang.String r4 = "item"
            Boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L12
            Array<Int> r3 = android.support.compat.R.styleable.GradientColorItem
            android.content.res.TypedArray r3 = android.support.v4.content.res.TypedArrayUtils.obtainAttributes(r7, r10, r9, r3)
            Int r4 = android.support.compat.R.styleable.GradientColorItem_android_color
            Boolean r4 = r3.hasValue(r4)
            Int r5 = android.support.compat.R.styleable.GradientColorItem_android_offset
            Boolean r5 = r3.hasValue(r5)
            if (r4 == 0) goto L49
            if (r5 != 0) goto L66
        L49:
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r8.getPositionDescription()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ": <item> tag requires a 'color' attribute and a 'offset' attribute!"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L66:
            Int r4 = android.support.compat.R.styleable.GradientColorItem_android_color
            r5 = 0
            Int r4 = r3.getColor(r4, r5)
            Int r5 = android.support.compat.R.styleable.GradientColorItem_android_offset
            r6 = 0
            Float r5 = r3.getFloat(r5, r6)
            r3.recycle()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
            r2.add(r3)
            java.lang.Float r3 = java.lang.Float.valueOf(r5)
            r1.add(r3)
            goto L12
        L86:
            Int r0 = r2.size()
            if (r0 <= 0) goto L92
            android.support.v4.content.res.GradientColorInflaterCompat$ColorStops r0 = new android.support.v4.content.res.GradientColorInflaterCompat$ColorStops
            r0.<init>(r2, r1)
        L91:
            return r0
        L92:
            r0 = 0
            goto L91
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.content.res.GradientColorInflaterCompat.inflateChildElements(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.support.v4.content.res.GradientColorInflaterCompat$ColorStops")
    }

    private static Shader.TileMode parseTileMode(Int i) {
        switch (i) {
            case 1:
                return Shader.TileMode.REPEAT
            case 2:
                return Shader.TileMode.MIRROR
            default:
                return Shader.TileMode.CLAMP
        }
    }
}
