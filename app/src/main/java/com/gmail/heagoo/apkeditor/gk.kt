package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

class gk extends Dialog implements View.OnClickListener {
    private static Array<Int> e = {R.string.show_a_toast, R.string.log_a_message, R.string.dump_a_value, R.string.print_stack_trace}
    private static Array<String> f = {"    const-string v0, \"This is a toast.\"\n    # p0 (this object) must be an object of Context\n    invoke-static {p0, v0}, Lapkeditor/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V", "    # use 'adb logcat APKEDITOR:* *:S' to view the log\n    const-string v0, \"I am here.\"\n    invoke-static {v0}, Lapkeditor/Utils;->log(Ljava/lang/String;)V", "    # use 'adb logcat APKEDITOR:* *:S' to view the value\n    invoke-static {v0}, Lapkeditor/Utils;->dumpValue(Ljava/lang/Object;)V", "    # use 'adb logcat APKEDITOR:* *:S' to view the stack trace\n    invoke-static {}, Lapkeditor/Utils;->printCallStack()V"}

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1107a

    /* renamed from: b, reason: collision with root package name */
    private String f1108b
    private Spinner c
    private EditText d

    constructor(Activity activity, String str) {
        super(activity)
        requestWindowFeature(1)
        this.f1107a = WeakReference(activity)
        this.f1108b = a(str)
        a(activity)
        getWindow().setLayout((activity.getResources().getDisplayMetrics().widthPixels * 6) / 7, -2)
    }

    private fun a(String str) {
        String str2 = ""
        for (String str3 : str.split("/")) {
            str2 = str2 + str3 + "/"
            if ("smali".equals(str3) || str3.startsWith("smali_")) {
                break
            }
        }
        return str2
    }

    @SuppressLint({"InflateParams"})
    private fun a(Activity activity) {
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.dlg_smalicode, (ViewGroup) null)
        this.c = (Spinner) viewInflate.findViewById(R.id.spinner_codename)
        Array<String> strArr = new String[e.length]
        for (Int i = 0; i < e.length; i++) {
            strArr[i] = activity.getString(e[i])
        }
        ArrayAdapter arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, strArr)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.c.setAdapter((SpinnerAdapter) arrayAdapter)
        this.c.setOnItemSelectedListener(gl(this))
        this.d = (EditText) viewInflate.findViewById(R.id.et_samplecode)
        ((Button) viewInflate.findViewById(R.id.btn_copy)).setOnClickListener(this)
        ((Button) viewInflate.findViewById(R.id.btn_close)).setOnClickListener(this)
        setContentView(viewInflate)
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e2) {
            }
        }
    }

    protected final Unit a(Int i) {
        if (i < f.length) {
            this.d.setText(f[i])
        }
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) throws Throwable {
        FileOutputStream fileOutputStream
        InputStream inputStreamOpen = null
        Int id = view.getId()
        if (id == R.id.btn_close) {
            dismiss()
            return
        }
        if (id == R.id.btn_copy) {
            Activity activity = (Activity) this.f1107a.get()
            ((ClipboardManager) activity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("code", this.d.getText().toString()))
            String str = this.f1108b + "apkeditor"
            File file = File(str)
            if (!file.exists()) {
                file.mkdir()
            }
            File file2 = File(str + "/Utils.smali")
            if (!file2.exists()) {
                try {
                    fileOutputStream = FileOutputStream(file2)
                } catch (Exception e2) {
                    e = e2
                    fileOutputStream = null
                } catch (Throwable th) {
                    th = th
                    fileOutputStream = null
                    a(fileOutputStream)
                    a(inputStreamOpen)
                    throw th
                }
                try {
                    try {
                        inputStreamOpen = ((Activity) this.f1107a.get()).getAssets().open("smali_patch/Utils.smali")
                        a.b(inputStreamOpen, fileOutputStream)
                        a(fileOutputStream)
                        a(inputStreamOpen)
                    } catch (Throwable th2) {
                        th = th2
                        a(fileOutputStream)
                        a(inputStreamOpen)
                        throw th
                    }
                } catch (Exception e3) {
                    e = e3
                    e.printStackTrace()
                    a(fileOutputStream)
                    a(inputStreamOpen)
                    Toast.makeText(activity, R.string.smali_copied, 0).show()
                }
            }
            Toast.makeText(activity, R.string.smali_copied, 0).show()
        }
    }
}
