package android.support.v4.content.res

import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Shader
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.util.AttributeSet
import android.util.Log
import android.util.Xml
import java.io.IOException
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ComplexColorCompat {
    private static val LOG_TAG = "ComplexColorCompat"
    private Int mColor
    private final ColorStateList mColorStateList
    private final Shader mShader

    private constructor(Shader shader, ColorStateList colorStateList, @ColorInt Int i) {
        this.mShader = shader
        this.mColorStateList = colorStateList
        this.mColor = i
    }

    @NonNull
    private fun createFromXml(@NonNull Resources resources, @ColorRes Int i, @Nullable Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Int next
        String name
        XmlResourceParser xml = resources.getXml(i)
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml)
        do {
            next = xml.next()
            if (next == 2) {
                break
            }
        } while (next != 1);
        if (next != 2) {
            throw XmlPullParserException("No start tag found")
        }
        name = xml.getName()
        switch (name) {
            case "selector":
                return from(ColorStateListInflaterCompat.createFromXmlInner(resources, xml, attributeSetAsAttributeSet, theme))
            case "gradient":
                return from(GradientColorInflaterCompat.createFromXmlInner(resources, xml, attributeSetAsAttributeSet, theme))
            default:
                throw XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name)
        }
    }

    static ComplexColorCompat from(@ColorInt Int i) {
        return ComplexColorCompat(null, null, i)
    }

    static ComplexColorCompat from(@NonNull ColorStateList colorStateList) {
        return ComplexColorCompat(null, colorStateList, colorStateList.getDefaultColor())
    }

    static ComplexColorCompat from(@NonNull Shader shader) {
        return ComplexColorCompat(shader, null, 0)
    }

    @Nullable
    fun inflate(@NonNull Resources resources, @ColorRes Int i, @Nullable Resources.Theme theme) {
        try {
            return createFromXml(resources, i, theme)
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to inflate ComplexColor.", e)
            return null
        }
    }

    @ColorInt
    public final Int getColor() {
        return this.mColor
    }

    @Nullable
    public final Shader getShader() {
        return this.mShader
    }

    public final Boolean isGradient() {
        return this.mShader != null
    }

    public final Boolean isStateful() {
        return this.mShader == null && this.mColorStateList != null && this.mColorStateList.isStateful()
    }

    public final Boolean onStateChanged(Array<Int> iArr) {
        Int colorForState
        if (!isStateful() || (colorForState = this.mColorStateList.getColorForState(iArr, this.mColorStateList.getDefaultColor())) == this.mColor) {
            return false
        }
        this.mColor = colorForState
        return true
    }

    public final Unit setColor(@ColorInt Int i) {
        this.mColor = i
    }

    public final Boolean willDraw() {
        return isGradient() || this.mColor != 0
    }
}
