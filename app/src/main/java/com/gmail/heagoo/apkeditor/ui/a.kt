package com.gmail.heagoo.apkeditor.ui

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.cn
import com.gmail.heagoo.apkeditor.cu
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.lang.ref.WeakReference

class a extends Dialog implements View.OnClickListener, cu {

    /* renamed from: a, reason: collision with root package name */
    private final b f1295a

    /* renamed from: b, reason: collision with root package name */
    private final WeakReference f1296b
    private Boolean c
    private View d
    private View e
    private View f
    private View g
    private EditText h
    private EditText i
    private Boolean j

    constructor(@NonNull Context context, b bVar, Boolean z) {
        super(context)
        this.j = true
        this.f1296b = WeakReference(context)
        this.f1295a = bVar
        this.c = z
        setContentView(R.layout.dlg_add_folder)
        TextView textView = (TextView) findViewById(R.id.tv_new_folder)
        TextView textView2 = (TextView) findViewById(R.id.tv_import_folder)
        if (!this.c) {
            textView2.setVisibility(8)
        }
        this.d = findViewById(R.id.divider1)
        this.e = findViewById(R.id.divider2)
        this.f = findViewById(R.id.layout_new)
        this.g = findViewById(R.id.layout_import)
        this.h = (EditText) findViewById(R.id.et_folder_name)
        this.i = (EditText) findViewById(R.id.et_folder_path)
        this.h.setFilters(new Array<InputFilter>{com.gmail.heagoo.common.a.a()})
        textView.setOnClickListener(this)
        textView2.setOnClickListener(this)
        findViewById(R.id.btn_browse).setOnClickListener(this)
        findViewById(R.id.btn_cancel).setOnClickListener(this)
        findViewById(R.id.btn_confirm).setOnClickListener(this)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        this.i.setText(str)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return false
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.tv_new_folder) {
            this.j = true
            this.d.setVisibility(0)
            this.e.setVisibility(4)
            this.f.setVisibility(0)
            this.g.setVisibility(4)
            return
        }
        if (id == R.id.tv_import_folder) {
            this.j = false
            this.d.setVisibility(4)
            this.e.setVisibility(0)
            this.f.setVisibility(4)
            this.g.setVisibility(0)
            return
        }
        if (id == R.id.btn_cancel) {
            dismiss()
            return
        }
        if (id != R.id.btn_confirm) {
            if (id == R.id.btn_browse) {
                Context context = (Context) this.f1296b.get()
                cn(context, this, "", "", context.getString(R.string.select_imported_folder), true, false, false, "import_folder").show()
                return
            }
            return
        }
        if (this.j) {
            String strTrim = this.h.getText().toString().trim()
            if ("".equals(strTrim)) {
                Toast.makeText((Context) this.f1296b.get(), R.string.empty_input_tip, 1).show()
                return
            } else {
                this.f1295a.c(strTrim)
                dismiss()
                return
            }
        }
        String strTrim2 = this.i.getText().toString().trim()
        if ("".equals(strTrim2)) {
            Toast.makeText((Context) this.f1296b.get(), R.string.empty_input_tip, 1).show()
        } else if (!File(strTrim2).exists()) {
            Toast.makeText((Context) this.f1296b.get(), String.format(((Context) this.f1296b.get()).getString(R.string.error_path_xxx_not_exist), strTrim2), 1).show()
        } else {
            this.f1295a.d(strTrim2)
            dismiss()
        }
    }
}
