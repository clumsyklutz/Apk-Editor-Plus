package ru.zeratul.widget

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.util.TypedValue

class SeekBar extends android.widget.SeekBar {

    /* renamed from: a, reason: collision with root package name */
    private Array<Int> f1624a

    /* renamed from: b, reason: collision with root package name */
    private Int f1625b
    private Int c
    private Int d
    private Int e
    private Float f
    private Paint g
    private Rect h
    private String i

    constructor(Context context) {
        super(context)
        this.f1624a = new Array<Int>{R.attr.textSize, R.attr.textColor, R.attr.text}
        this.f1625b = 0
        this.c = 1
        this.d = 2
        init((AttributeSet) null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f1624a = new Array<Int>{R.attr.textSize, R.attr.textColor, R.attr.text}
        this.f1625b = 0
        this.c = 1
        this.d = 2
        init(attributeSet)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.f1624a = new Array<Int>{R.attr.textSize, R.attr.textColor, R.attr.text}
        this.f1625b = 0
        this.c = 1
        this.d = 2
        init(attributeSet)
    }

    private fun init(AttributeSet attributeSet) {
        this.g = Paint(65)
        this.h = Rect()
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, this.f1624a)
            try {
                this.e = typedArrayObtainStyledAttributes.getColor(this.c, ViewCompat.MEASURED_STATE_MASK)
                this.f = typedArrayObtainStyledAttributes.getDimension(this.f1625b, TypedValue.applyDimension(2, 16, getResources().getDisplayMetrics()))
                this.i = typedArrayObtainStyledAttributes.getString(this.d)
            } finally {
                typedArrayObtainStyledAttributes.recycle()
            }
        }
        this.g.setColor(this.e)
        this.g.setTypeface(Typeface.DEFAULT_BOLD)
        this.g.setTextSize(this.f)
        this.g.setTextAlign(Paint.Align.CENTER)
        this.g.getTextBounds("255", 0, 3, this.h)
        setPadding(getPaddingLeft(), (Int) TypedValue.applyDimension(1, (Float) (0.6d * this.h.height()), getResources().getDisplayMetrics()), getPaddingRight(), getPaddingBottom())
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected fun onDraw(Canvas canvas) {
        super.onDraw(canvas)
        canvas.drawText(this.i == null ? String.valueOf(getProgress()) : this.i, getThumb().getBounds().left + getPaddingLeft(), this.h.height() + (getPaddingTop() >> 4), this.g)
    }
}
