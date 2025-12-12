package com.c.a

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.gmail.heagoo.apkeditor.gzd
import java.io.InputStream
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class f extends ImageView {
    private ColorFilter A
    private Int B
    private Int C
    private i D
    private j E
    private View.OnTouchListener F
    private View.OnClickListener G

    /* renamed from: a, reason: collision with root package name */
    private final Semaphore f702a

    /* renamed from: b, reason: collision with root package name */
    private b f703b
    private Drawable c
    private Float d
    private Float e
    private Boolean f
    private Float g
    private Float h
    private Float i
    private Float j
    private Float k
    private Float l
    private Float m
    private Float n
    private Float o
    private Float p
    private Float q
    private Float r
    private Int s
    private Int t
    private Int u
    private Boolean v
    private Boolean w
    private Int x
    private Int y
    private Int z

    constructor(Context context, Bitmap bitmap) {
        super(context)
        this.f702a = Semaphore(0)
        this.d = 0.0f
        this.e = 0.0f
        this.f = false
        this.g = 1.0f
        this.h = -1.0f
        this.i = 1.0f
        this.j = 8.0f
        this.k = 0.5f
        this.l = 1.0f
        this.m = 1.0f
        this.n = 0.0f
        this.u = -1
        this.v = false
        this.w = false
        this.z = 255
        this.B = -1
        setScaleType(ImageView.ScaleType.CENTER_INSIDE)
        this.c = BitmapDrawable(getResources(), bitmap)
        k()
    }

    private fun k() {
        if (this.c != null) {
            this.c.setAlpha(this.z)
            this.c.setFilterBitmap(true)
            if (this.A != null) {
                this.c.setColorFilter(this.A)
            }
        }
        if (this.f) {
            Int iC = c()
            Int iD = d()
            this.s = Math.round(iC / 2.0f)
            this.t = Math.round(iD / 2.0f)
            this.c.setBounds(-this.s, -this.t, this.s, this.t)
        } else {
            requestLayout()
        }
        postInvalidate()
    }

    public final Float a() {
        return this.l < this.m ? this.l : this.m
    }

    public final Unit a(Float f) {
        this.g = f
    }

    public final Unit a(Float f, Float f2) {
        this.d = f
        this.e = f2
    }

    public final Unit a(a aVar) {
        if (this.f703b != null) {
            this.f703b.a(aVar)
        }
    }

    public final Unit a(i iVar) {
        this.D = iVar
    }

    public final Boolean a(Long j) {
        return this.f702a.tryAcquire(32L, TimeUnit.MILLISECONDS)
    }

    public final Unit b() {
        if (this.f703b != null) {
            this.f703b.b()
        }
    }

    public final Unit b(Float f) {
        this.h = 1.0f
    }

    public final Unit b(Float f, Float f2) {
        this.q = Float.valueOf(f)
        this.r = Float.valueOf(f2)
    }

    public final Int c() {
        if (this.c != null) {
            return this.c.getIntrinsicWidth()
        }
        return 0
    }

    public final Int d() {
        if (this.c != null) {
            return this.c.getIntrinsicHeight()
        }
        return 0
    }

    public final Float e() {
        return this.g
    }

    public final Float f() {
        return this.d
    }

    public final Float g() {
        return this.e
    }

    @Override // android.widget.ImageView
    public final Drawable getDrawable() {
        return this.c
    }

    @Override // android.widget.ImageView
    public final Matrix getImageMatrix() {
        return super.getImageMatrix()
    }

    public final i h() {
        return this.D
    }

    public final Float i() {
        return this.o
    }

    @Override // android.widget.ImageView, android.view.View, android.graphics.drawable.Drawable.Callback
    public final Unit invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable)
    }

    public final Float j() {
        return this.p
    }

    @Override // android.widget.ImageView, android.view.View
    protected final Unit onAttachedToWindow() {
        this.f703b = b(this, "GestureImageViewAnimator")
        this.f703b.start()
        if (this.u >= 0 && this.c == null) {
            setImageResource(this.u)
        }
        super.onAttachedToWindow()
    }

    @Override // android.widget.ImageView, android.view.View
    public final Array<Int> onCreateDrawableState(Int i) {
        return super.onCreateDrawableState(i)
    }

    @Override // android.widget.ImageView, android.view.View
    protected final Unit onDetachedFromWindow() {
        if (this.f703b != null) {
            this.f703b.a()
        }
        super.onDetachedFromWindow()
    }

    @Override // android.widget.ImageView, android.view.View
    protected final Unit onDraw(Canvas canvas) {
        Bitmap bitmap
        if (this.f) {
            if (this.c != null) {
                if (!((this.c == null || !(this.c is BitmapDrawable) || (bitmap = ((BitmapDrawable) this.c).getBitmap()) == null) ? false : bitmap.isRecycled())) {
                    canvas.save()
                    Float f = this.i * this.g
                    canvas.translate(this.d, this.e)
                    if (this.n != 0.0f) {
                        canvas.rotate(this.n)
                    }
                    if (f != 1.0f) {
                        canvas.scale(f, f)
                    }
                    this.c.draw(canvas)
                    canvas.restore()
                }
            }
            if (this.f702a.availablePermits() <= 0) {
                this.f702a.release()
            }
        }
    }

    @Override // android.view.View
    protected final Unit onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        super.onLayout(z, i, i2, i3, i4)
        if (z || !this.f) {
            Int i5 = this.y
            Int i6 = this.x
            Int i7 = getResources().getConfiguration().orientation
            if (this.B != i7) {
                this.f = false
                this.B = i7
            }
            if (this.c == null || this.f) {
                return
            }
            Int iC = c()
            Int iD = d()
            this.s = Math.round(iC / 2.0f)
            this.t = Math.round(iD / 2.0f)
            Int paddingLeft = i5 - (getPaddingLeft() + getPaddingRight())
            Int paddingTop = i6 - (getPaddingTop() + getPaddingBottom())
            this.l = paddingLeft / iC
            this.m = paddingTop / iD
            if (this.h <= 0.0f) {
                switch (h.f705a[getScaleType().ordinal()]) {
                    case 1:
                        this.h = 1.0f
                        break
                    case 2:
                        this.h = Math.max(paddingTop / iD, paddingLeft / iC)
                        break
                    case 3:
                        if (iC / paddingLeft <= iD / paddingTop) {
                            this.h = this.m
                            break
                        } else {
                            this.h = this.l
                            break
                        }
                }
            }
            this.g = this.h
            this.o = paddingLeft / 2.0f
            this.p = paddingTop / 2.0f
            if (this.q == null) {
                this.d = this.o
            } else {
                this.d = this.q.floatValue()
            }
            if (this.r == null) {
                this.e = this.p
            } else {
                this.e = this.r.floatValue()
            }
            this.E = j(this, paddingLeft, paddingTop)
            this.E.b(this.k)
            this.E.a(this.j)
            this.c.setBounds(-this.s, -this.t, this.s, this.t)
            super.setOnTouchListener(g(this))
            this.f = true
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected final Unit onMeasure(Int i, Int i2) {
        if (this.c == null) {
            this.x = View.MeasureSpec.getSize(i2)
            this.y = View.MeasureSpec.getSize(i)
        } else if (getResources().getConfiguration().orientation == 2) {
            this.x = View.MeasureSpec.getSize(i2)
            if (getLayoutParams().width == -2) {
                this.y = Math.round((c() / d()) * this.x)
            } else {
                this.y = View.MeasureSpec.getSize(i)
            }
        } else {
            this.y = View.MeasureSpec.getSize(i)
            if (getLayoutParams().height == -2) {
                this.x = Math.round((d() / c()) * this.y)
            } else {
                this.x = View.MeasureSpec.getSize(i2)
            }
        }
        setMeasuredDimension(this.y, this.x)
    }

    @Override // android.widget.ImageView
    public final Unit setAdjustViewBounds(Boolean z) {
        super.setAdjustViewBounds(z)
    }

    @Override // android.widget.ImageView
    public final Unit setAlpha(Int i) {
        this.z = i
        if (this.c != null) {
            this.c.setAlpha(i)
        }
    }

    @Override // android.widget.ImageView
    public final Unit setColorFilter(ColorFilter colorFilter) {
        this.A = colorFilter
        if (this.c != null) {
            this.c.setColorFilter(colorFilter)
        }
    }

    @Override // android.widget.ImageView
    public final Unit setImageBitmap(Bitmap bitmap) {
        this.c = BitmapDrawable(getResources(), bitmap)
        k()
    }

    @Override // android.widget.ImageView
    public final Unit setImageDrawable(Drawable drawable) {
        this.c = drawable
        k()
    }

    @Override // android.widget.ImageView
    public final Unit setImageLevel(Int i) {
        super.setImageLevel(i)
    }

    @Override // android.widget.ImageView
    public final Unit setImageMatrix(Matrix matrix) {
    }

    @Override // android.widget.ImageView
    public final Unit setImageResource(Int i) {
        if (this.c != null) {
        }
        this.u = i
        setImageDrawable(getContext().getResources().getDrawable(i))
    }

    @Override // android.widget.ImageView
    public final Unit setImageState(Array<Int> iArr, Boolean z) {
    }

    @Override // android.widget.ImageView
    public final Unit setImageURI(Uri uri) throws Throwable {
        InputStream inputStream
        if (gzd.COLUMN_CONTENT.equals(uri.getScheme())) {
            try {
                Array<String> strArr = {"orientation"}
                Cursor cursorQuery = getContext().getContentResolver().query(uri, strArr, null, null, null)
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    this.C = cursorQuery.getInt(cursorQuery.getColumnIndex(strArr[0]))
                }
                try {
                    InputStream inputStreamOpenInputStream = getContext().getContentResolver().openInputStream(uri)
                    try {
                        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream)
                        if (this.C != 0) {
                            Matrix matrix = Matrix()
                            matrix.postRotate(this.C)
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeStream, 0, 0, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight(), matrix, true)
                            bitmapDecodeStream.recycle()
                            setImageDrawable(BitmapDrawable(getResources(), bitmapCreateBitmap))
                        } else {
                            setImageDrawable(BitmapDrawable(getResources(), bitmapDecodeStream))
                        }
                        if (inputStreamOpenInputStream != null) {
                            inputStreamOpenInputStream.close()
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close()
                        }
                    } catch (Throwable th) {
                        th = th
                        inputStream = inputStreamOpenInputStream
                        if (inputStream != null) {
                            inputStream.close()
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close()
                        }
                        throw th
                    }
                } catch (Throwable th2) {
                    th = th2
                    inputStream = null
                }
            } catch (Exception e) {
                Log.w("GestureImageView", "Unable to open content: " + uri, e)
            }
        } else {
            setImageDrawable(Drawable.createFromPath(uri.toString()))
        }
        if (this.c == null) {
            Log.e("GestureImageView", "resolveUri failed on bad bitmap uri: " + uri)
        }
    }

    @Override // android.view.View
    public final Unit setOnClickListener(View.OnClickListener onClickListener) {
        this.G = onClickListener
    }

    @Override // android.view.View
    public final Unit setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.F = onTouchListener
    }

    @Override // android.view.View
    public final Unit setRotation(Float f) {
        this.n = f
    }

    @Override // android.widget.ImageView
    public final Unit setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.CENTER || scaleType == ImageView.ScaleType.CENTER_CROP || scaleType == ImageView.ScaleType.CENTER_INSIDE) {
            super.setScaleType(scaleType)
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final Unit setSelected(Boolean z) {
        super.setSelected(z)
    }
}
