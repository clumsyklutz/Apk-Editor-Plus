package com.gmail.heagoo.apkeditor

import android.os.AsyncTask
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.io.IOException

final class hr extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private String f1158a

    /* renamed from: b, reason: collision with root package name */
    private Boolean f1159b
    private String c
    private /* synthetic */ TextEditBigActivity d

    private constructor(TextEditBigActivity textEditBigActivity) {
        this.d = textEditBigActivity
    }

    /* synthetic */ hr(TextEditBigActivity textEditBigActivity, Byte b2) {
        this(textEditBigActivity)
    }

    private fun a() {
        com.gmail.heagoo.neweditor.e eVar = new com.gmail.heagoo.neweditor.e(this.d, File(this.c), this.f1158a)
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
            this.f1159b = true
        } catch (IOException e) {
            this.f1159b = false
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
        if (this.f1159b) {
            this.d.K.a(this.d.j)
            this.d.K.a(this.d.j.a())
            TextEditBigActivity.f(this.d)
            this.d.d()
        } else {
            Toast.makeText(this.d, "Failed to open " + this.c, 1).show()
        }
        this.d.F.setMaxWidth((((com.gmail.heagoo.common.f.a(this.d) - this.d.G.getWidth()) - this.d.H.getWidth()) - this.d.I.getWidth()) - ((Int) (16.0f * this.d.getResources().getDisplayMetrics().density)))
    }

    @Override // android.os.AsyncTask
    protected final Unit onPreExecute() {
        this.c = (String) this.d.f1130b.get(this.d.e)
        this.d.J = ((Integer) this.d.c.get(this.d.e)).intValue()
        this.f1158a = (String) this.d.d.get(this.d.e)
        this.d.a(this.c)
        this.d.F.setText(this.d.f(this.c))
        this.d.I.setVisibility((gy.c(this.c) || gy.d(this.c)) ? 0 : 8)
        if (this.d.e >= this.d.f1130b.size() - 1) {
            this.d.H.setVisibility(8)
        } else {
            this.d.H.setVisibility(0)
        }
        if (this.d.e == 0) {
            this.d.G.setVisibility(8)
        } else {
            this.d.G.setVisibility(0)
        }
    }
}
