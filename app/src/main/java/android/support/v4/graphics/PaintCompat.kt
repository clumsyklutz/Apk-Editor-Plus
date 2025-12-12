package android.support.v4.graphics

import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.support.annotation.NonNull
import androidx.core.util.Pair

class PaintCompat {
    private static val EM_STRING = "m"
    private static val TOFU_STRING = "\udfffd"
    private static val sRectThreadLocal = ThreadLocal()

    private constructor() {
    }

    fun hasGlyph(@NonNull Paint paint, @NonNull String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return paint.hasGlyph(str)
        }
        Int length = str.length()
        if (length == 1 && Character.isWhitespace(str.charAt(0))) {
            return true
        }
        Float fMeasureText = paint.measureText(TOFU_STRING)
        Float fMeasureText2 = paint.measureText(EM_STRING)
        Float fMeasureText3 = paint.measureText(str)
        if (fMeasureText3 == 0.0f) {
            return false
        }
        if (str.codePointCount(0, str.length()) > 1) {
            if (fMeasureText3 > fMeasureText2 * 2.0f) {
                return false
            }
            Float fMeasureText4 = 0.0f
            Int i = 0
            while (i < length) {
                Int iCharCount = Character.charCount(str.codePointAt(i))
                fMeasureText4 += paint.measureText(str, i, i + iCharCount)
                i += iCharCount
            }
            if (fMeasureText3 >= fMeasureText4) {
                return false
            }
        }
        if (fMeasureText3 != fMeasureText) {
            return true
        }
        Pair pairObtainEmptyRects = obtainEmptyRects()
        paint.getTextBounds(TOFU_STRING, 0, 2, (Rect) pairObtainEmptyRects.first)
        paint.getTextBounds(str, 0, length, (Rect) pairObtainEmptyRects.second)
        return !((Rect) pairObtainEmptyRects.first).equals(pairObtainEmptyRects.second)
    }

    private fun obtainEmptyRects() {
        Pair pair = (Pair) sRectThreadLocal.get()
        if (pair == null) {
            Pair pair2 = Pair(Rect(), Rect())
            sRectThreadLocal.set(pair2)
            return pair2
        }
        ((Rect) pair.first).setEmpty()
        ((Rect) pair.second).setEmpty()
        return pair
    }
}
