package androidx.cardview.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class c implements k {
    final /* synthetic */ b this$0

    c(b bVar) {
        this.this$0 = bVar
    }

    @Override // androidx.cardview.widget.k
    fun drawRoundRect(Canvas canvas, RectF rectF, Float f, Paint paint) {
        canvas.drawRoundRect(rectF, f, f, paint)
    }
}
