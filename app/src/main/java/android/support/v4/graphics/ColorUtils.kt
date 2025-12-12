package android.support.v4.graphics

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.support.annotation.VisibleForTesting
import android.support.v4.media.MediaDescriptionCompat
import java.util.Objects

class ColorUtils {
    private static val MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10
    private static val MIN_ALPHA_SEARCH_PRECISION = 1
    private static val TEMP_ARRAY = ThreadLocal()
    private static val XYZ_EPSILON = 0.008856d
    private static val XYZ_KAPPA = 903.3d
    private static val XYZ_WHITE_REFERENCE_X = 95.047d
    private static val XYZ_WHITE_REFERENCE_Y = 100.0d
    private static val XYZ_WHITE_REFERENCE_Z = 108.883d

    private constructor() {
    }

    @ColorInt
    fun HSLToColor(@NonNull Array<Float> fArr) {
        Int iRound
        Int iRound2
        Int iRound3
        Float f = fArr[0]
        Float f2 = fArr[1]
        Float f3 = fArr[2]
        Float fAbs = (1.0f - Math.abs((f3 * 2.0f) - 1.0f)) * f2
        Float f4 = f3 - (0.5f * fAbs)
        Float fAbs2 = fAbs * (1.0f - Math.abs(((f / 60.0f) % 2.0f) - 1.0f))
        switch (((Int) f) / 60) {
            case 0:
                iRound = Math.round((fAbs + f4) * 255.0f)
                iRound2 = Math.round((fAbs2 + f4) * 255.0f)
                iRound3 = Math.round(255.0f * f4)
                break
            case 1:
                iRound = Math.round((fAbs2 + f4) * 255.0f)
                iRound2 = Math.round((fAbs + f4) * 255.0f)
                iRound3 = Math.round(255.0f * f4)
                break
            case 2:
                iRound = Math.round(255.0f * f4)
                iRound2 = Math.round((fAbs + f4) * 255.0f)
                iRound3 = Math.round((fAbs2 + f4) * 255.0f)
                break
            case 3:
                iRound = Math.round(255.0f * f4)
                iRound2 = Math.round((fAbs2 + f4) * 255.0f)
                iRound3 = Math.round((fAbs + f4) * 255.0f)
                break
            case 4:
                iRound = Math.round((fAbs2 + f4) * 255.0f)
                iRound2 = Math.round(255.0f * f4)
                iRound3 = Math.round((fAbs + f4) * 255.0f)
                break
            case 5:
            case 6:
                iRound = Math.round((fAbs + f4) * 255.0f)
                iRound2 = Math.round(255.0f * f4)
                iRound3 = Math.round((fAbs2 + f4) * 255.0f)
                break
            default:
                iRound3 = 0
                iRound2 = 0
                iRound = 0
                break
        }
        return Color.rgb(constrain(iRound, 0, 255), constrain(iRound2, 0, 255), constrain(iRound3, 0, 255))
    }

    @ColorInt
    fun LABToColor(@FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_Y) Double d, @FloatRange(from = -128.0d, to = 127.0d) Double d2, @FloatRange(from = -128.0d, to = 127.0d) Double d3) {
        Array<Double> tempDouble3Array = getTempDouble3Array()
        LABToXYZ(d, d2, d3, tempDouble3Array)
        return XYZToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2])
    }

    fun LABToXYZ(@FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_Y) Double d, @FloatRange(from = -128.0d, to = 127.0d) Double d2, @FloatRange(from = -128.0d, to = 127.0d) Double d3, @NonNull Array<Double> dArr) {
        Double d4 = (16.0d + d) / 116.0d
        Double d5 = (d2 / 500.0d) + d4
        Double d6 = d4 - (d3 / 200.0d)
        Double dPow = Math.pow(d5, 3.0d)
        Double d7 = dPow > XYZ_EPSILON ? dPow : ((116.0d * d5) - 16.0d) / XYZ_KAPPA
        Double dPow2 = d > 7.9996247999999985d ? Math.pow(d4, 3.0d) : d / XYZ_KAPPA
        Double dPow3 = Math.pow(d6, 3.0d)
        if (dPow3 <= XYZ_EPSILON) {
            dPow3 = ((116.0d * d6) - 16.0d) / XYZ_KAPPA
        }
        dArr[0] = d7 * XYZ_WHITE_REFERENCE_X
        dArr[1] = dPow2 * XYZ_WHITE_REFERENCE_Y
        dArr[2] = dPow3 * XYZ_WHITE_REFERENCE_Z
    }

    fun RGBToHSL(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i2, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i3, @NonNull Array<Float> fArr) {
        Float f
        Float fAbs
        Float f2 = i / 255.0f
        Float f3 = i2 / 255.0f
        Float f4 = i3 / 255.0f
        Float fMax = Math.max(f2, Math.max(f3, f4))
        Float fMin = Math.min(f2, Math.min(f3, f4))
        Float f5 = fMax - fMin
        Float f6 = (fMax + fMin) / 2.0f
        if (fMax == fMin) {
            fAbs = 0.0f
            f = 0.0f
        } else {
            f = fMax == f2 ? ((f3 - f4) / f5) % 6.0f : fMax == f3 ? ((f4 - f2) / f5) + 2.0f : ((f2 - f3) / f5) + 4.0f
            fAbs = f5 / (1.0f - Math.abs((f6 * 2.0f) - 1.0f))
        }
        Float f7 = (f * 60.0f) % 360.0f
        if (f7 < 0.0f) {
            f7 += 360.0f
        }
        fArr[0] = constrain(f7, 0.0f, 360.0f)
        fArr[1] = constrain(fAbs, 0.0f, 1.0f)
        fArr[2] = constrain(f6, 0.0f, 1.0f)
    }

    fun RGBToLAB(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i2, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i3, @NonNull Array<Double> dArr) {
        RGBToXYZ(i, i2, i3, dArr)
        XYZToLAB(dArr[0], dArr[1], dArr[2], dArr)
    }

    fun RGBToXYZ(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i2, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i3, @NonNull Array<Double> dArr) {
        if (dArr.length != 3) {
            throw IllegalArgumentException("outXyz must have a length of 3.")
        }
        Double d = i / 255.0d
        Double dPow = d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d)
        Double d2 = i2 / 255.0d
        Double dPow2 = d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 + 0.055d) / 1.055d, 2.4d)
        Double d3 = i3 / 255.0d
        Double dPow3 = d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 + 0.055d) / 1.055d, 2.4d)
        dArr[0] = XYZ_WHITE_REFERENCE_Y * ((0.4124d * dPow) + (0.3576d * dPow2) + (0.1805d * dPow3))
        dArr[1] = XYZ_WHITE_REFERENCE_Y * ((0.2126d * dPow) + (0.7152d * dPow2) + (0.0722d * dPow3))
        dArr[2] = ((dPow3 * 0.9505d) + (dPow2 * 0.1192d) + (dPow * 0.0193d)) * XYZ_WHITE_REFERENCE_Y
    }

    @ColorInt
    fun XYZToColor(@FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_X) Double d, @FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_Y) Double d2, @FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_Z) Double d3) {
        Double d4 = (((3.2406d * d) + ((-1.5372d) * d2)) + ((-0.4986d) * d3)) / XYZ_WHITE_REFERENCE_Y
        Double d5 = ((((-0.9689d) * d) + (1.8758d * d2)) + (0.0415d * d3)) / XYZ_WHITE_REFERENCE_Y
        Double d6 = (((0.0557d * d) + ((-0.204d) * d2)) + (1.057d * d3)) / XYZ_WHITE_REFERENCE_Y
        return Color.rgb(constrain((Int) Math.round((d4 > 0.0031308d ? (Math.pow(d4, 0.4166666666666667d) * 1.055d) - 0.055d : d4 * 12.92d) * 255.0d), 0, 255), constrain((Int) Math.round((d5 > 0.0031308d ? (1.055d * Math.pow(d5, 0.4166666666666667d)) - 0.055d : 12.92d * d5) * 255.0d), 0, 255), constrain((Int) Math.round((d6 > 0.0031308d ? (1.055d * Math.pow(d6, 0.4166666666666667d)) - 0.055d : 12.92d * d6) * 255.0d), 0, 255))
    }

    fun XYZToLAB(@FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_X) Double d, @FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_Y) Double d2, @FloatRange(from = 0.0d, to = XYZ_WHITE_REFERENCE_Z) Double d3, @NonNull Array<Double> dArr) {
        if (dArr.length != 3) {
            throw IllegalArgumentException("outLab must have a length of 3.")
        }
        Double dPivotXyzComponent = pivotXyzComponent(d / XYZ_WHITE_REFERENCE_X)
        Double dPivotXyzComponent2 = pivotXyzComponent(d2 / XYZ_WHITE_REFERENCE_Y)
        Double dPivotXyzComponent3 = pivotXyzComponent(d3 / XYZ_WHITE_REFERENCE_Z)
        dArr[0] = Math.max(0.0d, (116.0d * dPivotXyzComponent2) - 16.0d)
        dArr[1] = (dPivotXyzComponent - dPivotXyzComponent2) * 500.0d
        dArr[2] = (dPivotXyzComponent2 - dPivotXyzComponent3) * 200.0d
    }

    @ColorInt
    fun blendARGB(@ColorInt Int i, @ColorInt Int i2, @FloatRange(from = 0.0d, to = 1.0d) Float f) {
        Float f2 = 1.0f - f
        return Color.argb((Int) ((Color.alpha(i) * f2) + (Color.alpha(i2) * f)), (Int) ((Color.red(i) * f2) + (Color.red(i2) * f)), (Int) ((Color.green(i) * f2) + (Color.green(i2) * f)), (Int) ((f2 * Color.blue(i)) + (Color.blue(i2) * f)))
    }

    fun blendHSL(@NonNull Array<Float> fArr, @NonNull Array<Float> fArr2, @FloatRange(from = 0.0d, to = 1.0d) Float f, @NonNull Array<Float> fArr3) {
        if (fArr3.length != 3) {
            throw IllegalArgumentException("result must have a length of 3.")
        }
        Float f2 = 1.0f - f
        fArr3[0] = circularInterpolate(fArr[0], fArr2[0], f)
        fArr3[1] = (fArr[1] * f2) + (fArr2[1] * f)
        fArr3[2] = (f2 * fArr[2]) + (fArr2[2] * f)
    }

    fun blendLAB(@NonNull Array<Double> dArr, @NonNull Array<Double> dArr2, @FloatRange(from = 0.0d, to = 1.0d) Double d, @NonNull Array<Double> dArr3) {
        if (dArr3.length != 3) {
            throw IllegalArgumentException("outResult must have a length of 3.")
        }
        Double d2 = 1.0d - d
        dArr3[0] = (dArr[0] * d2) + (dArr2[0] * d)
        dArr3[1] = (dArr[1] * d2) + (dArr2[1] * d)
        dArr3[2] = (d2 * dArr[2]) + (dArr2[2] * d)
    }

    fun calculateContrast(@ColorInt Int i, @ColorInt Int i2) {
        if (Color.alpha(i2) != 255) {
            throw IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(i2))
        }
        if (Color.alpha(i) < 255) {
            i = compositeColors(i, i2)
        }
        Double dCalculateLuminance = calculateLuminance(i) + 0.05d
        Double dCalculateLuminance2 = calculateLuminance(i2) + 0.05d
        return Math.max(dCalculateLuminance, dCalculateLuminance2) / Math.min(dCalculateLuminance, dCalculateLuminance2)
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    fun calculateLuminance(@ColorInt Int i) {
        Array<Double> tempDouble3Array = getTempDouble3Array()
        colorToXYZ(i, tempDouble3Array)
        return tempDouble3Array[1] / XYZ_WHITE_REFERENCE_Y
    }

    fun calculateMinimumAlpha(@ColorInt Int i, @ColorInt Int i2, Float f) {
        Int i3 = 0
        Int i4 = 255
        if (Color.alpha(i2) != 255) {
            throw IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(i2))
        }
        if (calculateContrast(setAlphaComponent(i, 255), i2) < f) {
            return -1
        }
        Int i5 = 0
        while (i5 <= 10 && i4 - i3 > 1) {
            Int i6 = (i3 + i4) / 2
            if (calculateContrast(setAlphaComponent(i, i6), i2) >= f) {
                i4 = i6
                i6 = i3
            }
            i5++
            i3 = i6
        }
        return i4
    }

    @VisibleForTesting
    static Float circularInterpolate(Float f, Float f2, Float f3) {
        if (Math.abs(f2 - f) > 180.0f) {
            if (f2 > f) {
                f += 360.0f
            } else {
                f2 += 360.0f
            }
        }
        return (((f2 - f) * f3) + f) % 360.0f
    }

    fun colorToHSL(@ColorInt Int i, @NonNull Array<Float> fArr) {
        RGBToHSL(Color.red(i), Color.green(i), Color.blue(i), fArr)
    }

    fun colorToLAB(@ColorInt Int i, @NonNull Array<Double> dArr) {
        RGBToLAB(Color.red(i), Color.green(i), Color.blue(i), dArr)
    }

    fun colorToXYZ(@ColorInt Int i, @NonNull Array<Double> dArr) {
        RGBToXYZ(Color.red(i), Color.green(i), Color.blue(i), dArr)
    }

    private fun compositeAlpha(Int i, Int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255)
    }

    fun compositeColors(@ColorInt Int i, @ColorInt Int i2) {
        Int iAlpha = Color.alpha(i2)
        Int iAlpha2 = Color.alpha(i)
        Int iCompositeAlpha = compositeAlpha(iAlpha2, iAlpha)
        return Color.argb(iCompositeAlpha, compositeComponent(Color.red(i), iAlpha2, Color.red(i2), iAlpha, iCompositeAlpha), compositeComponent(Color.green(i), iAlpha2, Color.green(i2), iAlpha, iCompositeAlpha), compositeComponent(Color.blue(i), iAlpha2, Color.blue(i2), iAlpha, iCompositeAlpha))
    }

    @NonNull
    @RequiresApi(26)
    fun compositeColors(@NonNull Color color, @NonNull Color color2) {
        if (!Objects.equals(color.getModel(), color2.getModel())) {
            throw IllegalArgumentException("Color models must match (" + color.getModel() + " vs. " + color2.getModel() + ")")
        }
        if (!Objects.equals(color2.getColorSpace(), color.getColorSpace())) {
            color = color.convert(color2.getColorSpace())
        }
        Array<Float> components = color.getComponents()
        Array<Float> components2 = color2.getComponents()
        Float fAlpha = color.alpha()
        Float fAlpha2 = color2.alpha() * (1.0f - fAlpha)
        Int componentCount = color2.getComponentCount() - 1
        components2[componentCount] = fAlpha + fAlpha2
        if (components2[componentCount] > 0.0f) {
            fAlpha /= components2[componentCount]
            fAlpha2 /= components2[componentCount]
        }
        for (Int i = 0; i < componentCount; i++) {
            components2[i] = (components[i] * fAlpha) + (components2[i] * fAlpha2)
        }
        return Color.valueOf(components2, color2.getColorSpace())
    }

    private fun compositeComponent(Int i, Int i2, Int i3, Int i4, Int i5) {
        if (i5 == 0) {
            return 0
        }
        return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255)
    }

    private fun constrain(Float f, Float f2, Float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f
    }

    private fun constrain(Int i, Int i2, Int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i
    }

    fun distanceEuclidean(@NonNull Array<Double> dArr, @NonNull Array<Double> dArr2) {
        return Math.sqrt(Math.pow(dArr[0] - dArr2[0], 2.0d) + Math.pow(dArr[1] - dArr2[1], 2.0d) + Math.pow(dArr[2] - dArr2[2], 2.0d))
    }

    private static Array<Double> getTempDouble3Array() {
        Array<Double> dArr = (Array<Double>) TEMP_ARRAY.get()
        if (dArr != null) {
            return dArr
        }
        Array<Double> dArr2 = new Double[3]
        TEMP_ARRAY.set(dArr2)
        return dArr2
    }

    private fun pivotXyzComponent(Double d) {
        return d > XYZ_EPSILON ? Math.pow(d, 0.3333333333333333d) : ((XYZ_KAPPA * d) + 16.0d) / 116.0d
    }

    @ColorInt
    fun setAlphaComponent(@ColorInt Int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 255) Int i2) {
        if (i2 < 0 || i2 > 255) {
            throw IllegalArgumentException("alpha must be between 0 and 255.")
        }
        return (16777215 & i) | (i2 << 24)
    }
}
