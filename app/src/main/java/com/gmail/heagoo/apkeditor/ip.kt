package com.gmail.heagoo.apkeditor

import android.os.AsyncTask
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.io.IOException

final class ip extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private String f1196a

    /* renamed from: b, reason: collision with root package name */
    private Boolean f1197b
    private String c
    private /* synthetic */ TextEditNormalActivity d

    private constructor(TextEditNormalActivity textEditNormalActivity) {
        this.d = textEditNormalActivity
    }

    /* synthetic */ ip(TextEditNormalActivity textEditNormalActivity, Byte b2) {
        this(textEditNormalActivity)
    }

    private fun a() {
        com.gmail.heagoo.neweditor.e eVar = new com.gmail.heagoo.neweditor.e(this.d, File(this.c), this.f1196a)
        try {
            if (this.d.h != null) {
                eVar.a(this.d, this.d.h, R.string.error_file_too_big)
                eVar.a(true)
                File(this.d.h).delete()
                this.d.h = null
            } else {
                eVar.a(this.d, this.c, R.string.error_file_too_big)
            }
            this.d.j = eVar
            this.f1197b = true
        } catch (IOException e) {
            this.f1197b = false
        }
        if (!gy.e(this.c)) {
            gy.c(this.c)
        }
        return null
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        return a()
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        if (this.f1197b) {
            TextEditNormalActivity.c(this.d, true)
            this.d.l.removeTextChangedListener(this.d.X)
            this.d.l.setText(this.d.j.a())
            this.d.l.b(801639)
            this.d.l.addTextChangedListener(this.d.X)
            Int length = this.d.j.a().split("\n").length + 1
            iq iqVar = this.d.k
            TextEditNormalActivity.a(this.d, true, iq.a(length))
            this.d.a(true)
            this.d.a(-1, -1, false)
            this.d.d()
        } else {
            Toast.makeText(this.d, "Failed to open " + this.c, 1).show()
        }
        this.d.ac.setMaxWidth((((com.gmail.heagoo.common.f.a(this.d) - this.d.ad.getWidth()) - this.d.ae.getWidth()) - this.d.af.getWidth()) - ((Int) (16.0f * this.d.getResources().getDisplayMetrics().density)))
    }

    @Override // android.os.AsyncTask
    protected final Unit onPreExecute() {
        this.c = (String) this.d.f1130b.get(this.d.e)
        this.d.ag = ((Integer) this.d.c.get(this.d.e)).intValue()
        this.f1196a = (String) this.d.d.get(this.d.e)
        this.d.a(this.c)
        this.d.ac.setText(this.d.f(this.c))
        this.d.af.setVisibility((gy.c(this.c) || gy.d(this.c)) ? 0 : 8)
        if (this.d.e >= this.d.f1130b.size() - 1) {
            this.d.ae.setVisibility(8)
        } else {
            this.d.ae.setVisibility(0)
        }
        if (this.d.e == 0) {
            this.d.ad.setVisibility(8)
        } else {
            this.d.ad.setVisibility(0)
        }
    }
}
