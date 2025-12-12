package com.gmail.heagoo.pngeditor

import a.a.b.a.k
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.support.v7.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.c.a.i
import com.gmail.heagoo.apkeditor.AboutModActivity
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.l

class PngEditActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, i {

    /* renamed from: b, reason: collision with root package name */
    private String f1540b
    private String c
    private TextView d
    private View e
    private View f
    private com.c.a.f g
    private View h
    private View i
    private View j
    private View k
    private TextView l
    private SeekBar m
    private TextView n
    private SeekBar o
    private TextView p
    private TextView q
    private Bitmap r
    private Bitmap s
    private a u
    private PopupWindow v
    private PopupWindow w

    /* renamed from: a, reason: collision with root package name */
    private Int f1539a = 1
    private Boolean t = false

    private fun a() {
        if (this.u == null) {
            return
        }
        if (this.u.a()) {
            new AlertDialog.Builder(this).setMessage(R.string.image_modified_tip).setPositiveButton(android.R.string.yes, d(this)).setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null).show()
        } else {
            c()
        }
    }

    private fun a(Int i) {
        View viewInflate = View.inflate(this, R.layout.pngeditor_dlg_size_input, null)
        EditText editText = (EditText) viewInflate.findViewById(R.id.et_width)
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.et_height)
        editText.setText(this.p.getText())
        editText2.setText(this.q.getText())
        switch (i) {
            case 0:
                editText.requestFocus()
                break
            case 1:
                editText2.requestFocus()
                break
        }
        new AlertDialog.Builder(this).setTitle(R.string.input_new_size).setView(viewInflate).setPositiveButton(android.R.string.ok, g(this, editText, editText2)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
    }

    private fun a(View view, Int i, Int i2, Int i3, Int i4) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(i, 0, 0, 0)
            view.requestLayout()
        }
    }

    private fun a(a aVar, View view) {
        this.u = aVar
        view.setVisibility(0)
        this.h.setVisibility(4)
        this.f.setVisibility(0)
        this.e.setVisibility(4)
        this.p.setText(String.valueOf(this.r.getWidth()))
        this.q.setText(String.valueOf(this.r.getHeight()))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b() {
        if (l.a(this.r, this.f1540b)) {
            Toast.makeText(this, R.string.image_saved, 0).show()
        } else {
            Toast.makeText(this, R.string.image_save_failed, 1).show()
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c() {
        this.u = null
        this.i.setVisibility(4)
        this.j.setVisibility(4)
        this.k.setVisibility(4)
        this.h.setVisibility(0)
        this.f.setVisibility(4)
        this.e.setVisibility(0)
    }

    @Override // com.c.a.i
    @SuppressLint({"DefaultLocale"})
    public final Unit a(Float f) {
        this.d.setText(String.format("%d%%", Integer.valueOf((Int) (100.0f * f))))
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    fun onBackPressed() {
        if (this.h.getVisibility() != 0) {
            a()
        } else if (this.t) {
            new AlertDialog.Builder(this).setMessage(R.string.image_save_tip).setPositiveButton(R.string.save, c(this)).setNegativeButton(R.string.discard, b(this)).setNeutralButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
        } else {
            finish()
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_remove_bg) {
            a(new com.gmail.heagoo.pngeditor.a.a(), this.i)
            return
        }
        if (id == R.id.btn_remove_it) {
            this.u.a("tolerance", Integer.valueOf(this.m.getProgress()))
            this.s = this.u.a(this.r)
            if (this.g == null || this.s == null) {
                return
            }
            this.g.setImageBitmap(this.s)
            return
        }
        if (id == R.id.btn_confirm) {
            if (!this.u.a()) {
                Toast.makeText(this, R.string.no_change, 0).show()
                return
            }
            this.r = this.s
            this.t = true
            c()
            return
        }
        if (id == R.id.btn_save) {
            if (this.t) {
                b()
                return
            } else {
                Toast.makeText(this, R.string.no_change, 0).show()
                return
            }
        }
        if (id == R.id.btn_cancel) {
            a()
            return
        }
        if (id == R.id.btn_resize) {
            a(new com.gmail.heagoo.pngeditor.a.b(), this.j)
            return
        }
        if (id == R.id.btn_do_resize) {
            String string = this.p.getText().toString()
            String string2 = this.q.getText().toString()
            Boolean zIsChecked = ((CheckBox) findViewById(R.id.cb_without_zoom)).isChecked()
            try {
                Int iIntValue = Integer.valueOf(string).intValue()
                try {
                    Int iIntValue2 = Integer.valueOf(string2).intValue()
                    this.u.a("width", Integer.valueOf(iIntValue))
                    this.u.a("height", Integer.valueOf(iIntValue2))
                    this.u.a("zooming", Boolean.valueOf(!zIsChecked))
                    this.s = this.u.a(this.r)
                    if (this.g == null || this.s == null) {
                        return
                    }
                    this.g.setImageBitmap(this.s)
                    return
                } catch (Exception e) {
                    return
                }
            } catch (Exception e2) {
                return
            }
        }
        if (id == R.id.btn_transparency) {
            a(new com.gmail.heagoo.pngeditor.a.c(), this.k)
            return
        }
        if (id == R.id.btn_transparency_preview) {
            this.u.a("transparency", Integer.valueOf((this.o.getProgress() * 255) / this.o.getMax()))
            this.s = this.u.a(this.r)
            if (this.g == null || this.s == null) {
                return
            }
            this.g.setImageBitmap(this.s)
            return
        }
        if (id == R.id.width_labelvalue) {
            a(0)
            return
        }
        if (id == R.id.height_labelvalue) {
            a(1)
            return
        }
        if (id == R.id.tv_scale) {
            if (this.v == null) {
                View viewInflate = View.inflate(this, R.layout.pngeditor_scale_options, null)
                e eVar = e(this)
                viewInflate.findViewById(R.id.btn_scale_fit).setOnClickListener(eVar)
                viewInflate.findViewById(R.id.btn_scale_100).setOnClickListener(eVar)
                viewInflate.findViewById(R.id.btn_scale_200).setOnClickListener(eVar)
                viewInflate.findViewById(R.id.btn_scale_400).setOnClickListener(eVar)
                this.v = PopupWindow(viewInflate, com.gmail.heagoo.common.f.a(this), findViewById(R.id.btn_remove_bg).getHeight())
            }
            this.v.setFocusable(true)
            this.v.setOutsideTouchable(true)
            this.v.showAsDropDown(view)
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(k.md(k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.pngeditor_activity)
        this.f1540b = getIntent().getStringExtra("filePath")
        this.c = this.f1540b.substring(this.f1540b.lastIndexOf(47) + 1)
        getActionBar().hide()
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.pngeditor_actionbar, (ViewGroup) null)
        ActionBar supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setDisplayShowTitleEnabled(false)
            supportActionBar.setDisplayShowCustomEnabled(true)
            supportActionBar.setCustomView(viewGroup)
        }
        this.d = (TextView) findViewById(R.id.tv_scale)
        this.d.setPaintFlags(this.d.getPaintFlags() | 8)
        this.d.setOnClickListener(this)
        findViewById(R.id.btn_save)
        this.e = findViewById(R.id.normal_action_layout)
        this.f = findViewById(R.id.edit_action_layout)
        findViewById(R.id.btn_save).setOnClickListener(this)
        findViewById(R.id.btn_confirm).setOnClickListener(this)
        findViewById(R.id.btn_cancel).setOnClickListener(this)
        this.h = findViewById(R.id.tools_layout)
        this.i = findViewById(R.id.remove_bg_layout)
        this.j = findViewById(R.id.resize_layout)
        this.k = findViewById(R.id.transparency_layout)
        this.l = (TextView) findViewById(R.id.tv_tolerance)
        this.m = (SeekBar) findViewById(R.id.seekbar_tolerance)
        this.m.setOnSeekBarChangeListener(this)
        this.n = (TextView) findViewById(R.id.tv_transparency)
        this.o = (SeekBar) findViewById(R.id.seekbar_transparency)
        this.o.setOnSeekBarChangeListener(this)
        this.p = (TextView) findViewById(R.id.tv_width_value)
        this.q = (TextView) findViewById(R.id.tv_height_value)
        this.p.setPaintFlags(this.p.getPaintFlags() | 8)
        this.q.setPaintFlags(this.q.getPaintFlags() | 8)
        findViewById(R.id.width_labelvalue).setOnClickListener(this)
        findViewById(R.id.height_labelvalue).setOnClickListener(this)
        findViewById(R.id.btn_remove_bg).setOnClickListener(this)
        findViewById(R.id.btn_remove_it).setOnClickListener(this)
        findViewById(R.id.btn_resize).setOnClickListener(this)
        findViewById(R.id.btn_do_resize).setOnClickListener(this)
        findViewById(R.id.btn_transparency).setOnClickListener(this)
        findViewById(R.id.btn_transparency_preview).setOnClickListener(this)
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new Array<String>{"android.permission.WRITE_EXTERNAL_STORAGE"}, this.f1539a)
        }
        h(this).execute(new Void[0])
    }

    @Override // android.app.Activity
    fun onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pngeditor_main, menu)
        return true
    }

    @Override // android.app.Activity
    fun onOptionsItemSelected(MenuItem menuItem) {
        Int itemId = menuItem.getItemId()
        if (itemId == 16908332) {
            onBackPressed()
            return true
        }
        if (itemId != R.id.action_background) {
            if (itemId != R.id.action_help) {
                return super.onOptionsItemSelected(menuItem)
            }
            startActivity(Intent(this, (Class<?>) AboutModActivity.class))
            return true
        }
        if (this.w == null) {
            View viewInflate = View.inflate(this, R.layout.pngeditor_bgcolor, null)
            viewInflate.findViewById(R.id.btn_close).setOnClickListener(f(this))
            ((SeekBar) viewInflate.findViewById(R.id.seekbar_bgcolor)).setOnSeekBarChangeListener(this)
            viewInflate.measure(0, 0)
            this.w = PopupWindow(viewInflate, com.gmail.heagoo.common.f.a(this), viewInflate.getMeasuredHeight())
        }
        this.w.setFocusable(true)
        this.w.setOutsideTouchable(true)
        this.w.showAsDropDown(this.d)
        return true
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    fun onProgressChanged(SeekBar seekBar, Int i, Boolean z) {
        Int id = seekBar.getId()
        Int paddingLeft = seekBar.getPaddingLeft()
        Int width = paddingLeft + ((((seekBar.getWidth() - paddingLeft) - seekBar.getPaddingRight()) * seekBar.getProgress()) / seekBar.getMax())
        if (id == R.id.seekbar_tolerance) {
            this.l.setText(String.valueOf(i))
            this.l.measure(0, 0)
            a(this.l, width - (this.l.getMeasuredWidth() / 2), 0, 0, 0)
        } else if (id == R.id.seekbar_transparency) {
            this.n.setText(String.valueOf(i))
            this.n.measure(0, 0)
            a(this.n, width - (this.n.getMeasuredWidth() / 2), 0, 0, 0)
        } else if (id == R.id.seekbar_bgcolor) {
            Int i2 = i >= 0 ? i : 0
            Int i3 = 255 - (i2 <= 255 ? i2 : 255)
            findViewById(R.id.overall_layout).setBackgroundColor(i3 | (-16777216) | (i3 << 16) | (i3 << 8))
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    fun onRequestPermissionsResult(Int i, Array<String> strArr, Array<Int> iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr)
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    fun onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    fun onStopTrackingTouch(SeekBar seekBar) {
    }
}
