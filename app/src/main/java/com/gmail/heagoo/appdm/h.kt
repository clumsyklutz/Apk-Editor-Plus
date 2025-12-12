package com.gmail.heagoo.appdm

import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List
import java.util.Map

class h extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private PrefDetailActivity f1384a

    /* renamed from: b, reason: collision with root package name */
    private Int f1385b
    private List c
    private TextView d
    private EditText e
    private EditText f
    private View g
    private View h
    private Boolean i
    private Boolean j
    private Int k

    constructor(PrefDetailActivity prefDetailActivity, Map map, Int i, Boolean z, Int i2) {
        super(prefDetailActivity)
        this.c = ArrayList()
        this.i = false
        this.f1384a = prefDetailActivity
        this.f1385b = i
        this.j = true
        this.k = i2
        a(map)
        View viewInflate = getLayoutInflater().inflate(R.layout.appdm_dialog_keyvalue, (ViewGroup) null)
        this.d = (TextView) viewInflate.findViewById(R.id.tv_type)
        this.e = (EditText) viewInflate.findViewById(R.id.et_key)
        this.f = (EditText) viewInflate.findViewById(R.id.et_valuey)
        this.g = viewInflate.findViewById(R.id.image_next)
        this.h = viewInflate.findViewById(R.id.image_prev)
        this.g.setClickable(true)
        this.g.setOnClickListener(this)
        this.h.setClickable(true)
        this.h.setOnClickListener(this)
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_noteditable)
        Button button = (Button) viewInflate.findViewById(R.id.btn_save)
        if (this.j) {
            button.setOnClickListener(this)
            textView.setVisibility(8)
        } else {
            button.setVisibility(8)
            this.f.setEnabled(false)
            textView.setVisibility(0)
        }
        ((Button) viewInflate.findViewById(R.id.btn_cancel)).setOnClickListener(this)
        a(this.f1385b)
        super.setContentView(viewInflate)
        super.getWindow().setBackgroundDrawableResource(android.R.color.transparent)
        super.setCancelable(false)
        super.setCanceledOnTouchOutside(false)
    }

    private fun a(Int i) {
        i iVar = (i) this.c.get(i)
        this.d.setText("Type: " + iVar.c)
        this.e.setText(iVar.f1386a)
        this.f.setText(iVar.f1387b)
    }

    private fun a(Map map) {
        for (String str : map.keySet()) {
            i iVar = i()
            iVar.f1386a = str
            Object obj = map.get(str)
            if (obj != null) {
                iVar.f1387b = obj.toString()
                iVar.c = obj.getClass().getSimpleName()
            } else {
                iVar.f1387b = ""
                iVar.c = "null"
            }
            this.c.add(iVar)
        }
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) throws Exception {
        Object objValueOf
        Int id = view.getId()
        if (id == R.id.image_next) {
            if (this.f1385b + 1 >= this.c.size()) {
                Toast.makeText(this.f1384a, "No more values!", 0).show()
                return
            } else {
                a(this.f1385b + 1)
                this.f1385b++
                return
            }
        }
        if (id == R.id.image_prev) {
            if (this.f1385b <= 0) {
                Toast.makeText(this.f1384a, "No more values!", 0).show()
                return
            } else {
                a(this.f1385b - 1)
                this.f1385b--
                return
            }
        }
        if (id != R.id.btn_save) {
            if (id == R.id.btn_cancel) {
                cancel()
                if (this.i) {
                    this.f1384a.a()
                    return
                }
                return
            }
            return
        }
        try {
            i iVar = (i) this.c.get(this.f1385b)
            String str = iVar.c
            String string = this.f.getText().toString()
            if ("Integer".equals(str)) {
                objValueOf = Integer.valueOf(string)
            } else if ("Float".equals(str)) {
                objValueOf = Float.valueOf(string)
            } else if ("Long".equals(str)) {
                objValueOf = Long.valueOf(string)
            } else {
                objValueOf = string
                if (!"String".equals(str)) {
                    if (!"Boolean".equals(str)) {
                        throw Exception("Value type not supported!")
                    }
                    objValueOf = Boolean.valueOf(string)
                }
            }
            iVar.f1387b = objValueOf.toString()
            this.f1384a.a(iVar.f1386a, objValueOf)
            Toast.makeText(this.f1384a, "Succeed!", 0).show()
        } catch (Exception e) {
            Toast.makeText(this.f1384a, e.getMessage(), 0).show()
        }
        this.i = true
    }
}
