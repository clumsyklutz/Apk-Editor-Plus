package com.gmail.heagoo.apkeditor.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.util.AttributeSet
import android.widget.EditText

class LineEditText extends EditText {

    /* renamed from: a, reason: collision with root package name */
    private Rect f1301a

    /* renamed from: b, reason: collision with root package name */
    private Paint f1302b
    private Float c
    private Int d
    private Int e
    private Int f
    private Int g

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.c = context.getResources().getDisplayMetrics().density
        this.d = getPaddingLeft()
        this.e = getPaddingRight()
        this.f = getPaddingBottom()
        this.g = getPaddingTop()
        this.f1301a = Rect()
        this.f1302b = Paint()
        this.f1302b.setStyle(Paint.Style.FILL_AND_STROKE)
        this.f1302b.setColor(-8882056)
        this.f1302b.setTextSize((Int) ((14.0f * this.c) + 0.5f))
        this.d = (Int) this.f1302b.measureText("4455")
        setPadding((Int) (this.d + (2.0f * this.c)), this.g, this.e, this.f)
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onDraw(Canvas canvas) {
        Int i
        Int lineBounds
        Int lineHeight = getLineHeight()
        Int lineCount = getLineCount()
        Rect rect = this.f1301a
        Paint paint = this.f1302b
        String string = getText().toString()
        Int length = string.length()
        Layout layout = getLayout()
        Int i2 = 1
        Int i3 = 0
        Int i4 = 0
        Int i5 = -1
        while (i3 < lineCount) {
            Int lineEnd = layout.getLineEnd(i3)
            if (i2 != i5) {
                String str = i2 < 1000 ? String.format("%03d", Integer.valueOf(i2)) : String.format("%4d", Integer.valueOf(i2))
                lineBounds = getLineBounds(i3, rect)
                canvas.drawText(str, rect.left - getPaddingLeft(), lineBounds + 1, paint)
                i = i2
            } else {
                i = i5
                lineBounds = i4
            }
            i3++
            i2 = (length < lineEnd || string.charAt(lineEnd + (-1)) != '\n') ? i2 : i2 + 1
            i4 = lineBounds
            i5 = i
        }
        canvas.drawLine(this.d, this.g + (lineHeight / 3), this.d, i4 + 1, paint)
        super.onDraw(canvas)
    }
}
