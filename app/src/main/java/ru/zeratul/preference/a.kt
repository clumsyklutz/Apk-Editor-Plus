package ru.zeratul.preference

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.core.internal.view.SupportMenu
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

class a extends FrameLayout implements b, TextWatcher, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private c f1620a

    /* renamed from: b, reason: collision with root package name */
    private Int f1621b
    private Int c
    private Int d
    private Int e
    private EditText f
    private SeekBar g
    private SeekBar h
    private SeekBar i
    private SeekBar j
    private TextView k
    private TextView l
    private View m

    constructor(Context context) {
        this(context, (AttributeSet) null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f1620a = c(0)
        LayoutInflater.from(context).inflate(R.layout.cpv, this)
        this.m = findViewById(R.id.color_view)
        this.g = (SeekBar) findViewById(R.id.alpha)
        this.h = (SeekBar) findViewById(R.id.red)
        this.i = (SeekBar) findViewById(R.id.green)
        this.j = (SeekBar) findViewById(R.id.blue)
        b(this.g, -7829368)
        b(this.h, SupportMenu.CATEGORY_MASK)
        b(this.i, -16711936)
        b(this.j, -16776961)
        this.k = (TextView) findViewById(R.id.textView1)
        this.k.setOnClickListener(this)
        this.l = (TextView) findViewById(R.id.textView2)
        this.l.setOnClickListener(this)
        this.f = (EditText) findViewById(R.id.color)
        this.f.addTextChangedListener(this)
        this.f1620a.a(this)
        this.f.setFilters(new Array<InputFilter>{new InputFilter.LengthFilter(8)})
        this.f.setText(this.f.getText())
    }

    private fun b(SeekBar seekBar, Int i) {
        seekBar.getThumb().setColorFilter(i, PorterDuff.Mode.SRC_IN)
        seekBar.getProgressDrawable().setColorFilter(i, PorterDuff.Mode.MULTIPLY)
        seekBar.setOnSeekBarChangeListener(this)
    }

    private fun c(Int i, Int i2, Int i3, Int i4) {
        return String.format("%02x%02x%02x%02x", Integer(d(i)), Integer(d(i2)), Integer(d(i3)), Integer(d(i4)))
    }

    private fun d(Int i) {
        if (i < 0 || i > 255) {
            return 0
        }
        return i
    }

    static Int f(CharSequence charSequence) {
        try {
            return (Int) (Long.parseLong(charSequence.toString(), 16) & 4294967295L)
        } catch (NumberFormatException e) {
            return -7829368
        }
    }

    private fun g(TextView textView) {
        ((ClipboardManager) getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("copied", textView.getText().toString()))
        Toast.makeText(getContext(), textView.getText().toString() + " " + getContext().getString(R.string.toast_clipboard).toLowerCase(), 0).show()
    }

    @Override // ru.zeratul.preference.b
    fun a(c cVar) {
        this.f.setText(String.format("%08x", Integer.valueOf(cVar.c())))
    }

    @Override // android.text.TextWatcher
    fun afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    fun beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    fun e(String str) {
        String()
        if (str.length() == 6) {
            str = "ff" + str
        }
        Int color = Color.parseColor("#" + str)
        return Integer.parseInt(str.substring(0, 2), 16) >= 128 ? "-0x" + Integer.toHexString(color * (-1)) : "0x" + Integer.toHexString(color)
    }

    fun h(Int i) {
        this.f1620a.b(i, (b) null)
    }

    fun i() {
        return this.f1620a.c()
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        switch (view.getId()) {
            case R.id.textView1 /* 2131558467 */:
                g(this.k)
                break
            case R.id.textView2 /* 2131558683 */:
                g(this.l)
                break
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    fun onProgressChanged(SeekBar seekBar, Int i, Boolean z) {
        if (seekBar.getId() == R.id.alpha) {
            this.f1621b = i
        } else if (seekBar.getId() == R.id.red) {
            this.c = i
        } else if (seekBar.getId() == R.id.green) {
            this.d = i
        } else if (seekBar.getId() == R.id.blue) {
            this.e = i
        }
        this.f.setText(c(this.f1621b, this.c, this.d, this.e))
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    fun onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    fun onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.text.TextWatcher
    fun onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
        this.f1620a.b(f(charSequence), this)
        String string = charSequence.toString()
        try {
            if (string.length() == 8) {
                try {
                    Int color = Color.parseColor(StringBuffer().append('#').append(string).toString())
                    this.f1621b = Color.alpha(color)
                    this.c = Color.red(color)
                    this.d = Color.green(color)
                    this.e = Color.blue(color)
                    this.m.setBackgroundColor(i())
                    this.k.setText(e(c(this.f1621b, this.c, this.d, this.e)))
                    this.l.setText(String.valueOf(i()))
                    this.g.setProgress(this.f1621b)
                    this.h.setProgress(this.c)
                    this.i.setProgress(this.d)
                    this.j.setProgress(this.e)
                } catch (IllegalArgumentException e) {
                }
                this.f.setSelection(this.f.getText().length())
                this.f.requestFocus()
            }
        } catch (Exception e2) {
        }
    }
}
