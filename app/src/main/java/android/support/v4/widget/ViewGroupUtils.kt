package android.support.v4.widget

import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.support.annotation.RestrictTo
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

@RestrictTo({RestrictTo.Scope.LIBRARY})
class ViewGroupUtils {
    private static val sMatrix = ThreadLocal()
    private static val sRectF = ThreadLocal()

    private constructor() {
    }

    fun getDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight())
        offsetDescendantRect(viewGroup, view, rect)
    }

    private fun offsetDescendantMatrix(ViewParent viewParent, View view, Matrix matrix) {
        Object parent = view.getParent()
        if ((parent is View) && parent != viewParent) {
            offsetDescendantMatrix(viewParent, (View) parent, matrix)
            matrix.preTranslate(-r0.getScrollX(), -r0.getScrollY())
        }
        matrix.preTranslate(view.getLeft(), view.getTop())
        if (view.getMatrix().isIdentity()) {
            return
        }
        matrix.preConcat(view.getMatrix())
    }

    static Unit offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        Matrix matrix
        Matrix matrix2 = (Matrix) sMatrix.get()
        if (matrix2 == null) {
            Matrix matrix3 = Matrix()
            sMatrix.set(matrix3)
            matrix = matrix3
        } else {
            matrix2.reset()
            matrix = matrix2
        }
        offsetDescendantMatrix(viewGroup, view, matrix)
        RectF rectF = (RectF) sRectF.get()
        if (rectF == null) {
            rectF = RectF()
            sRectF.set(rectF)
        }
        rectF.set(rect)
        matrix.mapRect(rectF)
        rect.set((Int) (rectF.left + 0.5f), (Int) (rectF.top + 0.5f), (Int) (rectF.right + 0.5f), (Int) (rectF.bottom + 0.5f))
    }
}
