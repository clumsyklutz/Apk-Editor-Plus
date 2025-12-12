package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class gt extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private View f1121a

    /* renamed from: b, reason: collision with root package name */
    private Context f1122b

    @SuppressLint({"InflateParams"})
    constructor(Context context) throws IOException {
        super(context)
        requestWindowFeature(1)
        this.f1122b = context
        this.f1121a = LayoutInflater.from(context).inflate(R.layout.dlg_smali_license, (ViewGroup) null)
        setContentView(this.f1121a)
        a()
    }

    private fun a() throws IOException {
        InputStream inputStreamOpen = null
        StringBuilder sb = StringBuilder()
        try {
            try {
                inputStreamOpen = this.f1122b.getAssets().open("smali-NOTICE")
                BufferedReader bufferedReader = BufferedReader(InputStreamReader(inputStreamOpen))
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    sb.append(line + "\n")
                }
                if (inputStreamOpen != null) {
                    try {
                        inputStreamOpen.close()
                    } catch (IOException e) {
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace()
                if (inputStreamOpen != null) {
                    try {
                        inputStreamOpen.close()
                    } catch (IOException e3) {
                    }
                }
            }
            ((TextView) this.f1121a.findViewById(R.id.content)).setText(sb.toString())
            ((Button) this.f1121a.findViewById(R.id.close_button)).setOnClickListener(this)
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close()
                } catch (IOException e4) {
                }
            }
            throw th
        }
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (((CheckBox) this.f1121a.findViewById(R.id.cb_show_once)).isChecked()) {
            a.b(this.f1122b, "smali_license_showed", true)
        }
        dismiss()
    }
}
