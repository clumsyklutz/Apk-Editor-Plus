package android.support.graphics.drawable

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Path
import android.graphics.PathMeasure
import android.support.annotation.RestrictTo
import android.support.v4.content.res.TypedArrayUtils
import android.support.v4.graphics.PathParser
import android.util.AttributeSet
import android.view.InflateException
import android.view.animation.Interpolator
import org.xmlpull.v1.XmlPullParser

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class PathInterpolatorCompat implements Interpolator {
    public static val EPSILON = 1.0E-5d
    public static val MAX_NUM_POINTS = 3000
    private static val PRECISION = 0.002f
    private Array<Float> mX
    private Array<Float> mY

    constructor(Context context, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        this(context.getResources(), context.getTheme(), attributeSet, xmlPullParser)
    }

    constructor(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PATH_INTERPOLATOR)
        parseInterpolatorFromTypeArray(typedArrayObtainAttributes, xmlPullParser)
        typedArrayObtainAttributes.recycle()
    }

    private fun initCubic(Float f, Float f2, Float f3, Float f4) {
        Path path = Path()
        path.moveTo(0.0f, 0.0f)
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f)
        initPath(path)
    }

    private fun initPath(Path path) {
        Int i = 0
        PathMeasure pathMeasure = PathMeasure(path, false)
        Float length = pathMeasure.getLength()
        Int iMin = Math.min(MAX_NUM_POINTS, ((Int) (length / PRECISION)) + 1)
        if (iMin <= 0) {
            throw IllegalArgumentException("The Path has a invalid length " + length)
        }
        this.mX = new Float[iMin]
        this.mY = new Float[iMin]
        Array<Float> fArr = new Float[2]
        for (Int i2 = 0; i2 < iMin; i2++) {
            pathMeasure.getPosTan((i2 * length) / (iMin - 1), fArr, null)
            this.mX[i2] = fArr[0]
            this.mY[i2] = fArr[1]
        }
        if (Math.abs(this.mX[0]) > 1.0E-5d || Math.abs(this.mY[0]) > 1.0E-5d || Math.abs(this.mX[iMin - 1] - 1.0f) > 1.0E-5d || Math.abs(this.mY[iMin - 1] - 1.0f) > 1.0E-5d) {
            throw IllegalArgumentException("The Path must start at (0,0) and end at (1,1) start: " + this.mX[0] + "," + this.mY[0] + " end:" + this.mX[iMin - 1] + "," + this.mY[iMin - 1])
        }
        Float f = 0.0f
        Int i3 = 0
        while (i3 < iMin) {
            Int i4 = i + 1
            Float f2 = this.mX[i]
            if (f2 < f) {
                throw IllegalArgumentException("The Path cannot loop back on itself, x :" + f2)
            }
            this.mX[i3] = f2
            i3++
            f = f2
            i = i4
        }
        if (pathMeasure.nextContour()) {
            throw IllegalArgumentException("The Path should be continuous, can't have 2+ contours")
        }
    }

    private fun initQuad(Float f, Float f2) {
        Path path = Path()
        path.moveTo(0.0f, 0.0f)
        path.quadTo(f, f2, 1.0f, 1.0f)
        initPath(path)
    }

    private fun parseInterpolatorFromTypeArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
            String namedString = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "pathData", 4)
            Path pathCreatePathFromPathData = PathParser.createPathFromPathData(namedString)
            if (pathCreatePathFromPathData == null) {
                throw InflateException("The path is null, which is created from " + namedString)
            }
            initPath(pathCreatePathFromPathData)
            return
        }
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, "controlX1")) {
            throw InflateException("pathInterpolator requires the controlX1 attribute")
        }
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, "controlY1")) {
            throw InflateException("pathInterpolator requires the controlY1 attribute")
        }
        Float namedFloat = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "controlX1", 0, 0.0f)
        Float namedFloat2 = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "controlY1", 1, 0.0f)
        Boolean zHasAttribute = TypedArrayUtils.hasAttribute(xmlPullParser, "controlX2")
        if (zHasAttribute != TypedArrayUtils.hasAttribute(xmlPullParser, "controlY2")) {
            throw InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.")
        }
        if (zHasAttribute) {
            initCubic(namedFloat, namedFloat2, TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "controlX2", 2, 0.0f), TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "controlY2", 3, 0.0f))
        } else {
            initQuad(namedFloat, namedFloat2)
        }
    }

    @Override // android.animation.TimeInterpolator
    fun getInterpolation(Float f) {
        if (f <= 0.0f) {
            return 0.0f
        }
        if (f >= 1.0f) {
            return 1.0f
        }
        Int length = this.mX.length - 1
        Int i = 0
        while (length - i > 1) {
            Int i2 = (i + length) / 2
            if (f < this.mX[i2]) {
                length = i2
            } else {
                i = i2
            }
        }
        Float f2 = this.mX[length] - this.mX[i]
        if (f2 == 0.0f) {
            return this.mY[i]
        }
        Float f3 = (f - this.mX[i]) / f2
        Float f4 = this.mY[i]
        return (f3 * (this.mY[length] - f4)) + f4
    }
}
