package android.support.v4.graphics

import android.graphics.Path
import android.support.annotation.RestrictTo
import androidx.appcompat.R
import android.util.Log
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class PathParser {
    private static val LOGTAG = "PathParser"

    class ExtractFloatResult {
        Int mEndPosition
        Boolean mEndWithNegOrDot

        ExtractFloatResult() {
        }
    }

    class PathDataNode {

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Array<Float> mParams

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Char mType

        PathDataNode(Char c, Array<Float> fArr) {
            this.mType = c
            this.mParams = fArr
        }

        PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType
            this.mParams = PathParser.copyOfRange(pathDataNode.mParams, 0, pathDataNode.mParams.length)
        }

        private fun addCommand(Path path, Array<Float> fArr, Char c, Char c2, Array<Float> fArr2) {
            Int i
            Float f
            Float f2
            Float f3
            Float f4
            Float f5
            Float f6
            Float f7
            Float f8
            Float f9
            Float f10
            Float f11
            Float f12
            Float f13 = fArr[0]
            Float f14 = fArr[1]
            Float f15 = fArr[2]
            Float f16 = fArr[3]
            Float f17 = fArr[4]
            Float f18 = fArr[5]
            switch (c2) {
                case R.styleable.AppCompatTheme_editTextBackground /* 65 */:
                case R.styleable.AppCompatTheme_alertDialogCenterButtons /* 97 */:
                    i = 7
                    break
                case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 99 */:
                    i = 6
                    break
                case R.styleable.AppCompatTheme_listPreferredItemHeightSmall /* 72 */:
                case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
                case R.styleable.AppCompatTheme_buttonStyle /* 104 */:
                case R.styleable.AppCompatTheme_tooltipForegroundColor /* 118 */:
                    i = 1
                    break
                case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
                case R.styleable.AppCompatTheme_listPopupWindowStyle /* 77 */:
                case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
                case 'l':
                case 'm':
                case R.styleable.AppCompatTheme_listMenuViewStyle /* 116 */:
                    i = 2
                    break
                case 'Q':
                case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
                case R.styleable.AppCompatTheme_seekBarStyle /* 113 */:
                case R.styleable.AppCompatTheme_switchStyle /* 115 */:
                    i = 4
                    break
                case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
                case 'z':
                    path.close()
                    path.moveTo(f17, f18)
                    f16 = f18
                    f15 = f17
                    f14 = f18
                    f13 = f17
                    i = 2
                    break
                default:
                    i = 2
                    break
            }
            Int i2 = 0
            Float f19 = f18
            Float f20 = f17
            Float f21 = f14
            Float f22 = f13
            while (i2 < fArr2.length) {
                switch (c2) {
                    case R.styleable.AppCompatTheme_editTextBackground /* 65 */:
                        drawArc(path, f22, f21, fArr2[i2 + 5], fArr2[i2 + 6], fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f, fArr2[i2 + 4] != 0.0f)
                        Float f23 = fArr2[i2 + 5]
                        Float f24 = fArr2[i2 + 6]
                        f = f19
                        f2 = f20
                        f3 = f23
                        f4 = f24
                        f5 = f23
                        f6 = f24
                        break
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                        path.cubicTo(fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3], fArr2[i2 + 4], fArr2[i2 + 5])
                        Float f25 = fArr2[i2 + 4]
                        Float f26 = fArr2[i2 + 5]
                        f3 = fArr2[i2 + 2]
                        f4 = f26
                        f5 = f25
                        f = f19
                        f2 = f20
                        f6 = fArr2[i2 + 3]
                        break
                    case R.styleable.AppCompatTheme_listPreferredItemHeightSmall /* 72 */:
                        path.lineTo(fArr2[i2], f21)
                        f = f19
                        f3 = f15
                        f4 = f21
                        f5 = fArr2[i2]
                        f6 = f16
                        f2 = f20
                        break
                    case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
                        path.lineTo(fArr2[i2], fArr2[i2 + 1])
                        Float f27 = fArr2[i2]
                        f3 = f15
                        f4 = fArr2[i2 + 1]
                        f5 = f27
                        f = f19
                        f2 = f20
                        f6 = f16
                        break
                    case R.styleable.AppCompatTheme_listPopupWindowStyle /* 77 */:
                        f2 = fArr2[i2]
                        f = fArr2[i2 + 1]
                        if (i2 <= 0) {
                            path.moveTo(fArr2[i2], fArr2[i2 + 1])
                            f3 = f15
                            f4 = f
                            f5 = f2
                            f6 = f16
                            break
                        } else {
                            path.lineTo(fArr2[i2], fArr2[i2 + 1])
                            f3 = f15
                            f4 = f
                            f5 = f2
                            f = f19
                            f2 = f20
                            f6 = f16
                            break
                        }
                    case 'Q':
                        path.quadTo(fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3])
                        Float f28 = fArr2[i2]
                        Float f29 = fArr2[i2 + 1]
                        Float f30 = fArr2[i2 + 2]
                        f3 = f28
                        f4 = fArr2[i2 + 3]
                        f5 = f30
                        f = f19
                        f2 = f20
                        f6 = f29
                        break
                    case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
                        if (c == 'c' || c == 's' || c == 'C' || c == 'S') {
                            f9 = (2.0f * f22) - f15
                            f10 = (2.0f * f21) - f16
                        } else {
                            f10 = f21
                            f9 = f22
                        }
                        path.cubicTo(f9, f10, fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3])
                        Float f31 = fArr2[i2]
                        Float f32 = fArr2[i2 + 1]
                        Float f33 = fArr2[i2 + 2]
                        f3 = f31
                        f4 = fArr2[i2 + 3]
                        f5 = f33
                        f = f19
                        f2 = f20
                        f6 = f32
                        break
                    case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
                        if (c == 'q' || c == 't' || c == 'Q' || c == 'T') {
                            f22 = (2.0f * f22) - f15
                            f21 = (2.0f * f21) - f16
                        }
                        path.quadTo(f22, f21, fArr2[i2], fArr2[i2 + 1])
                        Float f34 = fArr2[i2]
                        f6 = f21
                        f3 = f22
                        f4 = fArr2[i2 + 1]
                        f5 = f34
                        f = f19
                        f2 = f20
                        break
                    case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
                        path.lineTo(f22, fArr2[i2])
                        f2 = f20
                        f3 = f15
                        f4 = fArr2[i2]
                        f5 = f22
                        f6 = f16
                        f = f19
                        break
                    case R.styleable.AppCompatTheme_alertDialogCenterButtons /* 97 */:
                        drawArc(path, f22, f21, fArr2[i2 + 5] + f22, fArr2[i2 + 6] + f21, fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f, fArr2[i2 + 4] != 0.0f)
                        Float f35 = f22 + fArr2[i2 + 5]
                        Float f36 = fArr2[i2 + 6] + f21
                        f = f19
                        f2 = f20
                        f3 = f35
                        f4 = f36
                        f5 = f35
                        f6 = f36
                        break
                    case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 99 */:
                        path.rCubicTo(fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3], fArr2[i2 + 4], fArr2[i2 + 5])
                        Float f37 = f22 + fArr2[i2 + 2]
                        Float f38 = fArr2[i2 + 3] + f21
                        Float f39 = f22 + fArr2[i2 + 4]
                        f3 = f37
                        f4 = fArr2[i2 + 5] + f21
                        f5 = f39
                        f = f19
                        f2 = f20
                        f6 = f38
                        break
                    case R.styleable.AppCompatTheme_buttonStyle /* 104 */:
                        path.rLineTo(fArr2[i2], 0.0f)
                        f = f19
                        f3 = f15
                        f4 = f21
                        f5 = f22 + fArr2[i2]
                        f6 = f16
                        f2 = f20
                        break
                    case 'l':
                        path.rLineTo(fArr2[i2], fArr2[i2 + 1])
                        Float f40 = f22 + fArr2[i2]
                        f3 = f15
                        f4 = fArr2[i2 + 1] + f21
                        f5 = f40
                        f = f19
                        f2 = f20
                        f6 = f16
                        break
                    case 'm':
                        f2 = f22 + fArr2[i2]
                        f = fArr2[i2 + 1] + f21
                        if (i2 <= 0) {
                            path.rMoveTo(fArr2[i2], fArr2[i2 + 1])
                            f3 = f15
                            f4 = f
                            f5 = f2
                            f6 = f16
                            break
                        } else {
                            path.rLineTo(fArr2[i2], fArr2[i2 + 1])
                            f3 = f15
                            f4 = f
                            f5 = f2
                            f = f19
                            f2 = f20
                            f6 = f16
                            break
                        }
                    case R.styleable.AppCompatTheme_seekBarStyle /* 113 */:
                        path.rQuadTo(fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3])
                        Float f41 = f22 + fArr2[i2]
                        Float f42 = fArr2[i2 + 1] + f21
                        Float f43 = f22 + fArr2[i2 + 2]
                        f3 = f41
                        f4 = fArr2[i2 + 3] + f21
                        f5 = f43
                        f = f19
                        f2 = f20
                        f6 = f42
                        break
                    case R.styleable.AppCompatTheme_switchStyle /* 115 */:
                        if (c == 'c' || c == 's' || c == 'C' || c == 'S') {
                            f11 = f22 - f15
                            f12 = f21 - f16
                        } else {
                            f12 = 0.0f
                            f11 = 0.0f
                        }
                        path.rCubicTo(f11, f12, fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3])
                        Float f44 = f22 + fArr2[i2]
                        Float f45 = fArr2[i2 + 1] + f21
                        Float f46 = f22 + fArr2[i2 + 2]
                        f3 = f44
                        f4 = fArr2[i2 + 3] + f21
                        f5 = f46
                        f = f19
                        f2 = f20
                        f6 = f45
                        break
                    case R.styleable.AppCompatTheme_listMenuViewStyle /* 116 */:
                        if (c == 'q' || c == 't' || c == 'Q' || c == 'T') {
                            f7 = f22 - f15
                            f8 = f21 - f16
                        } else {
                            f8 = 0.0f
                            f7 = 0.0f
                        }
                        path.rQuadTo(f7, f8, fArr2[i2], fArr2[i2 + 1])
                        Float f47 = f22 + fArr2[i2]
                        f3 = f22 + f7
                        f4 = fArr2[i2 + 1] + f21
                        f5 = f47
                        f = f19
                        f2 = f20
                        f6 = f8 + f21
                        break
                    case R.styleable.AppCompatTheme_tooltipForegroundColor /* 118 */:
                        path.rLineTo(0.0f, fArr2[i2])
                        f2 = f20
                        f3 = f15
                        f4 = fArr2[i2] + f21
                        f5 = f22
                        f6 = f16
                        f = f19
                        break
                    default:
                        f = f19
                        f2 = f20
                        f3 = f15
                        f4 = f21
                        f5 = f22
                        f6 = f16
                        break
                }
                i2 += i
                f19 = f
                f20 = f2
                f21 = f4
                f22 = f5
                c = c2
                f16 = f6
                f15 = f3
            }
            fArr[0] = f22
            fArr[1] = f21
            fArr[2] = f15
            fArr[3] = f16
            fArr[4] = f20
            fArr[5] = f19
        }

        private fun arcToBezier(Path path, Double d, Double d2, Double d3, Double d4, Double d5, Double d6, Double d7, Double d8, Double d9) {
            Int iCeil = (Int) Math.ceil(Math.abs((4.0d * d9) / 3.141592653589793d))
            Double dCos = Math.cos(d7)
            Double dSin = Math.sin(d7)
            Double dCos2 = Math.cos(d8)
            Double dSin2 = Math.sin(d8)
            Double d10 = (((-d3) * dCos) * dSin2) - ((d4 * dSin) * dCos2)
            Double d11 = (dCos2 * d4 * dCos) + (dSin2 * (-d3) * dSin)
            Double d12 = d9 / iCeil
            Int i = 0
            while (i < iCeil) {
                Double d13 = d8 + d12
                Double dSin3 = Math.sin(d13)
                Double dCos3 = Math.cos(d13)
                Double d14 = (((d3 * dCos) * dCos3) + d) - ((d4 * dSin) * dSin3)
                Double d15 = (d4 * dCos * dSin3) + (d3 * dSin * dCos3) + d2
                Double d16 = (((-d3) * dCos) * dSin3) - ((d4 * dSin) * dCos3)
                Double d17 = (dCos3 * d4 * dCos) + (dSin3 * (-d3) * dSin)
                Double dTan = Math.tan((d13 - d8) / 2.0d)
                Double dSqrt = ((Math.sqrt((dTan * (3.0d * dTan)) + 4.0d) - 1.0d) * Math.sin(d13 - d8)) / 3.0d
                path.rLineTo(0.0f, 0.0f)
                path.cubicTo((Float) ((d10 * dSqrt) + d5), (Float) (d6 + (d11 * dSqrt)), (Float) (d14 - (dSqrt * d16)), (Float) (d15 - (dSqrt * d17)), (Float) d14, (Float) d15)
                d10 = d16
                d8 = d13
                i++
                d6 = d15
                d5 = d14
                d11 = d17
            }
        }

        private fun drawArc(Path path, Float f, Float f2, Float f3, Float f4, Float f5, Float f6, Float f7, Boolean z, Boolean z2) {
            Double d
            Double d2
            while (true) {
                Double radians = Math.toRadians(f7)
                Double dCos = Math.cos(radians)
                Double dSin = Math.sin(radians)
                Double d3 = ((f * dCos) + (f2 * dSin)) / f5
                Double d4 = (((-f) * dSin) + (f2 * dCos)) / f6
                Double d5 = ((f3 * dCos) + (f4 * dSin)) / f5
                Double d6 = (((-f3) * dSin) + (f4 * dCos)) / f6
                Double d7 = d3 - d5
                Double d8 = d4 - d6
                Double d9 = (d3 + d5) / 2.0d
                Double d10 = (d4 + d6) / 2.0d
                Double d11 = (d7 * d7) + (d8 * d8)
                if (d11 == 0.0d) {
                    Log.w(PathParser.LOGTAG, " Points are coincident")
                    return
                }
                Double d12 = (1.0d / d11) - 0.25d
                if (d12 >= 0.0d) {
                    Double dSqrt = Math.sqrt(d12)
                    Double d13 = d7 * dSqrt
                    Double d14 = d8 * dSqrt
                    if (z == z2) {
                        d = d9 - d14
                        d2 = d13 + d10
                    } else {
                        d = d14 + d9
                        d2 = d10 - d13
                    }
                    Double dAtan2 = Math.atan2(d4 - d2, d3 - d)
                    Double dAtan22 = Math.atan2(d6 - d2, d5 - d) - dAtan2
                    if (z2 != (dAtan22 >= 0.0d)) {
                        dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d
                    }
                    Double d15 = d * f5
                    Double d16 = f6 * d2
                    arcToBezier(path, (d15 * dCos) - (d16 * dSin), (d15 * dSin) + (dCos * d16), f5, f6, f, f2, radians, dAtan2, dAtan22)
                    return
                }
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d11)
                Float fSqrt = (Float) (Math.sqrt(d11) / 1.99999d)
                f5 *= fSqrt
                f6 *= fSqrt
            }
        }

        fun nodesToPath(Array<PathDataNode> pathDataNodeArr, Path path) {
            Array<Float> fArr = new Float[6]
            Char c = 'm'
            for (Int i = 0; i < pathDataNodeArr.length; i++) {
                addCommand(path, fArr, c, pathDataNodeArr[i].mType, pathDataNodeArr[i].mParams)
                c = pathDataNodeArr[i].mType
            }
        }

        fun interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, Float f) {
            for (Int i = 0; i < pathDataNode.mParams.length; i++) {
                this.mParams[i] = (pathDataNode.mParams[i] * (1.0f - f)) + (pathDataNode2.mParams[i] * f)
            }
        }
    }

    private constructor() {
    }

    private fun addNode(ArrayList arrayList, Char c, Array<Float> fArr) {
        arrayList.add(PathDataNode(c, fArr))
    }

    fun canMorph(Array<PathDataNode> pathDataNodeArr, Array<PathDataNode> pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false
        }
        for (Int i = 0; i < pathDataNodeArr.length; i++) {
            if (pathDataNodeArr[i].mType != pathDataNodeArr2[i].mType || pathDataNodeArr[i].mParams.length != pathDataNodeArr2[i].mParams.length) {
                return false
            }
        }
        return true
    }

    static Array<Float> copyOfRange(Array<Float> fArr, Int i, Int i2) {
        if (i > i2) {
            throw IllegalArgumentException()
        }
        Int length = fArr.length
        if (i < 0 || i > length) {
            throw ArrayIndexOutOfBoundsException()
        }
        Int i3 = i2 - i
        Int iMin = Math.min(i3, length - i)
        Array<Float> fArr2 = new Float[i3]
        System.arraycopy(fArr, i, fArr2, 0, iMin)
        return fArr2
    }

    public static Array<PathDataNode> createNodesFromPathData(String str) {
        if (str == null) {
            return null
        }
        ArrayList arrayList = ArrayList()
        Int i = 1
        Int i2 = 0
        while (i < str.length()) {
            Int iNextStart = nextStart(str, i)
            String strTrim = str.substring(i2, iNextStart).trim()
            if (strTrim.length() > 0) {
                addNode(arrayList, strTrim.charAt(0), getFloats(strTrim))
            }
            i = iNextStart + 1
            i2 = iNextStart
        }
        if (i - i2 == 1 && i2 < str.length()) {
            addNode(arrayList, str.charAt(i2), new Float[0])
        }
        return (Array<PathDataNode>) arrayList.toArray(new PathDataNode[arrayList.size()])
    }

    fun createPathFromPathData(String str) {
        Path path = Path()
        Array<PathDataNode> pathDataNodeArrCreateNodesFromPathData = createNodesFromPathData(str)
        if (pathDataNodeArrCreateNodesFromPathData == null) {
            return null
        }
        try {
            PathDataNode.nodesToPath(pathDataNodeArrCreateNodesFromPathData, path)
            return path
        } catch (RuntimeException e) {
            throw RuntimeException("Error in parsing " + str, e)
        }
    }

    public static Array<PathDataNode> deepCopyNodes(Array<PathDataNode> pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null
        }
        Array<PathDataNode> pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length]
        for (Int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = PathDataNode(pathDataNodeArr[i])
        }
        return pathDataNodeArr2
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun extract(java.lang.String r7, Int r8, android.support.v4.graphics.PathParser.ExtractFloatResult r9) {
        /*
            r1 = 0
            r5 = 1
            r9.mEndWithNegOrDot = r1
            r0 = r1
            r2 = r1
            r3 = r1
            r4 = r8
        L8:
            Int r6 = r7.length()
            if (r4 >= r6) goto L33
            Char r6 = r7.charAt(r4)
            switch(r6) {
                case 32: goto L1b
                case 44: goto L1b
                case 45: goto L1e
                case 46: goto L27
                case 69: goto L31
                case 101: goto L31
                default: goto L15
            }
        L15:
            r0 = r1
        L16:
            if (r3 != 0) goto L33
            Int r4 = r4 + 1
            goto L8
        L1b:
            r0 = r1
            r3 = r5
            goto L16
        L1e:
            if (r4 == r8) goto L15
            if (r0 != 0) goto L15
            r9.mEndWithNegOrDot = r5
            r0 = r1
            r3 = r5
            goto L16
        L27:
            if (r2 != 0) goto L2c
            r0 = r1
            r2 = r5
            goto L16
        L2c:
            r9.mEndWithNegOrDot = r5
            r0 = r1
            r3 = r5
            goto L16
        L31:
            r0 = r5
            goto L16
        L33:
            r9.mEndPosition = r4
            return
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.PathParser.extract(java.lang.String, Int, android.support.v4.graphics.PathParser$ExtractFloatResult):Unit")
    }

    private static Array<Float> getFloats(String str) {
        Int i
        Int i2 = 0
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new Float[0]
        }
        try {
            Array<Float> fArr = new Float[str.length()]
            ExtractFloatResult extractFloatResult = ExtractFloatResult()
            Int length = str.length()
            Int i3 = 1
            while (i3 < length) {
                extract(str, i3, extractFloatResult)
                Int i4 = extractFloatResult.mEndPosition
                if (i3 < i4) {
                    i = i2 + 1
                    fArr[i2] = Float.parseFloat(str.substring(i3, i4))
                } else {
                    i = i2
                }
                if (extractFloatResult.mEndWithNegOrDot) {
                    i3 = i4
                    i2 = i
                } else {
                    i3 = i4 + 1
                    i2 = i
                }
            }
            return copyOfRange(fArr, 0, i2)
        } catch (NumberFormatException e) {
            throw RuntimeException("error in parsing \"" + str + "\"", e)
        }
    }

    private fun nextStart(String str, Int i) {
        while (i < str.length()) {
            Char cCharAt = str.charAt(i)
            if (((cCharAt - 'A') * (cCharAt - 'Z') <= 0 || (cCharAt - 'a') * (cCharAt - 'z') <= 0) && cCharAt != 'e' && cCharAt != 'E') {
                break
            }
            i++
        }
        return i
    }

    fun updateNodes(Array<PathDataNode> pathDataNodeArr, Array<PathDataNode> pathDataNodeArr2) {
        for (Int i = 0; i < pathDataNodeArr2.length; i++) {
            pathDataNodeArr[i].mType = pathDataNodeArr2[i].mType
            for (Int i2 = 0; i2 < pathDataNodeArr2[i].mParams.length; i2++) {
                pathDataNodeArr[i].mParams[i2] = pathDataNodeArr2[i].mParams[i2]
            }
        }
    }
}
