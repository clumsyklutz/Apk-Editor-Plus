package com.gmail.heagoo.apkeditor

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.List
import java.util.Map

class iy extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private iz f1211a

    /* renamed from: b, reason: collision with root package name */
    private Int f1212b
    private String c
    private LinkedHashMap d
    private Boolean e
    private List f
    private String g
    private Context h
    private Dialog i
    private View j
    private LinearLayout k
    private Int l

    /* JADX WARN: Removed duplicated region for block: B:21:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0094 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    constructor(android.content.Context r10, com.gmail.heagoo.apkeditor.iz r11, Int r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.iy.<init>(android.content.Context, com.gmail.heagoo.apkeditor.iz, Int, java.lang.String):Unit")
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int i = 0
        Int id = view.getId()
        if (id == R.id.btn_dlgclose) {
            dismiss()
            return
        }
        if (id == R.id.btn_dlgsave) {
            if (this.f1211a != null) {
                iz izVar = this.f1211a
                Int i2 = this.f1212b
                StringBuffer stringBuffer = StringBuffer()
                stringBuffer.append("<" + this.c)
                Iterator it = this.d.entrySet().iterator()
                while (it.hasNext()) {
                    stringBuffer.append(" " + ((String) ((Map.Entry) it.next()).getKey()) + "=\"" + ((EditText) this.f.get(i)).getText().toString() + "\"")
                    i++
                }
                if (this.e) {
                    stringBuffer.append(" />")
                } else {
                    stringBuffer.append(">")
                }
                stringBuffer.append(this.g)
                izVar.a(i2, stringBuffer.toString())
            }
            dismiss()
            return
        }
        if (id == R.id.hidden_image) {
            this.i = Dialog(this.h)
            View viewInflate = LayoutInflater.from(this.h).inflate(R.layout.dlg_addkeyvalue, (ViewGroup) null)
            ((Button) viewInflate.findViewById(R.id.btn_addkeyvalue_ok)).setOnClickListener(this)
            ((Button) viewInflate.findViewById(R.id.btn_addkeyvalue_cancel)).setOnClickListener(this)
            this.j = viewInflate
            this.i.requestWindowFeature(1)
            this.i.setContentView(viewInflate)
            this.i.show()
            return
        }
        if (id != R.id.btn_addkeyvalue_ok) {
            if (id == R.id.btn_addkeyvalue_cancel) {
                this.i.dismiss()
                return
            }
            return
        }
        EditText editText = (EditText) this.j.findViewById(R.id.key)
        EditText editText2 = (EditText) this.j.findViewById(R.id.value)
        String strTrim = editText.getText().toString().trim()
        String strTrim2 = editText2.getText().toString().trim()
        if (strTrim.equals("")) {
            Toast.makeText(this.h, R.string.empty_key_tip, 0).show()
            return
        }
        this.d.put(strTrim, strTrim2)
        View viewInflate2 = LayoutInflater.from(this.h).inflate(this.l, (ViewGroup) null)
        ((TextView) viewInflate2.findViewById(R.id.string_name)).setText(strTrim)
        EditText editText3 = (EditText) viewInflate2.findViewById(R.id.string_value)
        this.f.add(editText3)
        editText3.setText(strTrim2)
        this.k.addView(viewInflate2, this.d.size() - 1)
        this.i.dismiss()
    }
}
