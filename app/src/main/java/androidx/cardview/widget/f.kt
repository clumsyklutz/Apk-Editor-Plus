package androidx.cardview.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class f implements k {
    final /* synthetic */ e this$0

    f(e eVar) {
        this.this$0 = eVar
    }

    @Override // androidx.cardview.widget.k
    fun drawRoundRect(Canvas canvas, RectF rectF, Float f, Paint paint) {
        Float f2 = 2.0f * f
        Float fWidth = (rectF.width() - f2) - 1.0f
        Float fHeight = (rectF.height() - f2) - 1.0f
        if (f >= 1.0f) {
            Float f3 = f + 0.5f
            Float f4 = -f3
            this.this$0.mCornerRect.set(f4, f4, f3, f3)
            Int iSave = canvas.save()
            canvas.translate(rectF.left + f3, rectF.top + f3)
            canvas.drawArc(this.this$0.mCornerRect, 180.0f, 90.0f, true, paint)
            canvas.translate(fWidth, 0.0f)
            canvas.rotate(90.0f)
            canvas.drawArc(this.this$0.mCornerRect, 180.0f, 90.0f, true, paint)
            canvas.translate(fHeight, 0.0f)
            canvas.rotate(90.0f)
            canvas.drawArc(this.this$0.mCornerRect, 180.0f, 90.0f, true, paint)
            canvas.translate(fWidth, 0.0f)
            canvas.rotate(90.0f)
            canvas.drawArc(this.this$0.mCornerRect, 180.0f, 90.0f, true, paint)
            canvas.restoreToCount(iSave)
            canvas.drawRect((rectF.left + f3) - 1.0f, rectF.top, (rectF.right - f3) + 1.0f, rectF.top + f3, paint)
            canvas.drawRect((rectF.left + f3) - 1.0f, rectF.bottom - f3, (rectF.right - f3) + 1.0f, rectF.bottom, paint)
        }
        canvas.drawRect(rectF.left, rectF.top + f, rectF.right, rectF.bottom - f, paint)
    }
}
