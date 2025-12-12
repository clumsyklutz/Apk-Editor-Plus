package com.gmail.heagoo.sqliteutil

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.List

class k extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1577a

    /* renamed from: b, reason: collision with root package name */
    private Int f1578b
    private List c
    private List d
    private List e
    private List f
    private TextView g
    private TextView h
    private EditText i
    private EditText j
    private View k
    private View l
    private Button m
    private Button n
    private Boolean o

    constructor(SqliteRowViewActivity sqliteRowViewActivity, List list, List list2, List list3, List list4, Int i, Boolean z, Int i2) {
        super(sqliteRowViewActivity)
        this.f1577a = WeakReference(sqliteRowViewActivity)
        this.f1578b = i
        this.c = list
        this.d = list2
        this.e = list4
        this.f = list3
        this.o = z
        View viewInflate = getLayoutInflater().inflate(R.layout.sql_dialog_tablerecord, (ViewGroup) null)
        this.g = (TextView) viewInflate.findViewById(R.id.tv_type)
        this.i = (EditText) viewInflate.findViewById(R.id.et_name)
        this.j = (EditText) viewInflate.findViewById(R.id.et_valuey)
        this.k = viewInflate.findViewById(R.id.image_next)
        this.l = viewInflate.findViewById(R.id.image_prev)
        this.h = (TextView) viewInflate.findViewById(R.id.tv_pkflag)
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_noteditable)
        if (this.o) {
            textView.setVisibility(4)
        } else {
            textView.setVisibility(0)
            this.j.setEnabled(false)
        }
        this.k.setClickable(true)
        this.k.setOnClickListener(l(this))
        this.l.setClickable(true)
        this.l.setOnClickListener(m(this))
        this.m = (Button) viewInflate.findViewById(R.id.btn_save)
        if (this.o) {
            this.m.setOnClickListener(n(this))
        } else {
            this.m.setVisibility(8)
        }
        this.n = (Button) viewInflate.findViewById(R.id.btn_cancel)
        this.n.setOnClickListener(o(this))
        b(this.f1578b)
        super.setContentView(viewInflate)
    }

    private fun a(Int i) {
        return !"0".equals(this.f.get(i))
    }

    private fun b(Int i) {
        Boolean zA = a(i)
        this.g.setText("Type: " + ((String) this.c.get(i)))
        this.h.setText("Primary Key: " + zA)
        this.i.setText((CharSequence) this.d.get(i))
        this.j.setText((CharSequence) this.e.get(i))
        if (this.o) {
            return
        }
        if (zA) {
            this.j.setEnabled(false)
            this.m.setVisibility(8)
        } else {
            this.j.setEnabled(true)
            this.m.setVisibility(0)
        }
    }

    @SuppressLint({"DefaultLocale"})
    protected final Unit a() throws Exception {
        Object objValueOf
        try {
            if (a(this.f1578b)) {
                throw Exception("Can not edit primary key!")
            }
            String upperCase = ((String) this.c.get(this.f1578b)).toUpperCase()
            String string = this.j.getText().toString()
            if (SqliteTableViewActivity.f(upperCase)) {
                objValueOf = string
            } else if (SqliteTableViewActivity.c(upperCase)) {
                objValueOf = Long.valueOf(string)
            } else if (SqliteTableViewActivity.d(upperCase)) {
                objValueOf = Boolean.valueOf(string)
            } else if (SqliteTableViewActivity.b(upperCase)) {
                objValueOf = Float.valueOf(string)
            } else if (SqliteTableViewActivity.a(upperCase)) {
                objValueOf = Double.valueOf(string)
            } else {
                if (SqliteTableViewActivity.e(upperCase)) {
                    throw Exception("Value type not supported!")
                }
                objValueOf = string
            }
            ((SqliteRowViewActivity) this.f1577a.get()).a(this.f1578b, objValueOf)
            Toast.makeText((Context) this.f1577a.get(), "Succeed!", 0).show()
        } catch (Exception e) {
            Toast.makeText((Context) this.f1577a.get(), e.getMessage(), 0).show()
        }
    }

    protected final Unit b() {
        if (this.f1578b <= 0) {
            Toast.makeText((Context) this.f1577a.get(), R.string.toast_no_values, 0).show()
        } else {
            b(this.f1578b - 1)
            this.f1578b--
        }
    }

    protected final Unit c() {
        if (this.f1578b + 1 >= this.e.size()) {
            Toast.makeText((Context) this.f1577a.get(), R.string.toast_no_values, 0).show()
        } else {
            b(this.f1578b + 1)
            this.f1578b++
        }
    }
}
