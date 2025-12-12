package com.b.a

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.gmail.heagoo.apkeditor.pro.R

class a extends RelativeLayout implements TextWatcher {

    /* renamed from: a, reason: collision with root package name */
    Handler f684a

    /* renamed from: b, reason: collision with root package name */
    private View f685b
    private SeekBar c
    private SeekBar d
    private SeekBar e
    private SeekBar f
    private EditText g
    private e h
    private Int i
    private Int j
    private SeekBar.OnSeekBarChangeListener k

    constructor(Context context) {
        super(context)
        this.f685b = null
        this.c = null
        this.d = null
        this.e = null
        this.f = null
        this.g = null
        this.h = null
        this.f684a = c(this)
        this.k = d(this)
        if (isInEditMode()) {
            return
        }
        (getContext() instanceof Activity ? ((Activity) getContext()).getLayoutInflater() : LayoutInflater.from(getContext())).inflate(R.layout.dlg_colormixer, (ViewGroup) this, true)
        this.f685b = findViewById(R.id.swatch)
        this.g = (EditText) findViewById(R.id.color)
        this.g.addTextChangedListener(this)
        this.g.setFilters(new Array<InputFilter>{b(this), new InputFilter.LengthFilter(8)})
        this.c = (SeekBar) findViewById(R.id.red)
        this.c.setMax(255)
        this.c.setOnSeekBarChangeListener(this.k)
        this.e = (SeekBar) findViewById(R.id.green)
        this.e.setMax(255)
        this.e.setOnSeekBarChangeListener(this.k)
        this.d = (SeekBar) findViewById(R.id.blue)
        this.d.setMax(255)
        this.d.setOnSeekBarChangeListener(this.k)
        this.f = (SeekBar) findViewById(R.id.alpha)
        this.f.setMax(255)
        this.f.setOnSeekBarChangeListener(this.k)
    }

    static /* synthetic */ Unit a(a aVar, Int i) {
        aVar.j = i
        String hexString = Integer.toHexString(i)
        aVar.g.setText(hexString)
        aVar.g.setSelection(hexString.length())
    }

    static /* synthetic */ e c(a aVar) {
        return null
    }

    public final Int a() {
        return Color.argb(this.f.getProgress(), this.c.getProgress(), this.e.getProgress(), this.d.getProgress())
    }

    public final Unit a(Int i) {
        this.c.setProgress(Color.red(i))
        this.e.setProgress(Color.green(i))
        this.d.setProgress(Color.blue(i))
        this.f.setProgress(Color.alpha(i))
    }

    @Override // android.text.TextWatcher
    public final Unit afterTextChanged(Editable editable) {
        try {
            String string = this.g.getText().toString()
            Long lValueOf = Long.valueOf(Long.parseLong(string, 16))
            if (lValueOf.intValue() != this.j) {
                if (string.length() <= 6) {
                    lValueOf = Long.valueOf(lValueOf.longValue() | (-16777216))
                }
                Int iIntValue = lValueOf.intValue()
                this.i = iIntValue
                a(iIntValue)
            }
        } catch (Exception e) {
        }
    }

    @Override // android.text.TextWatcher
    public final Unit beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    @Override // android.view.View
    public final Unit onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable
        super.onRestoreInstanceState(bundle.getParcelable("superState"))
        a(bundle.getInt("color"))
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Bundle bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putInt("color", a())
        return bundle
    }

    @Override // android.text.TextWatcher
    public final Unit onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }
}
