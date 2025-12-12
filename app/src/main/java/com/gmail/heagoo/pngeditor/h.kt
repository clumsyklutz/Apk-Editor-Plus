package com.gmail.heagoo.pngeditor

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.LinearLayout
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

final class h extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PngEditActivity f1554a

    h(PngEditActivity pngEditActivity) {
        this.f1554a = pngEditActivity
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        this.f1554a.r = BitmapFactory.decodeFile(this.f1554a.f1540b)
        return Boolean.valueOf(this.f1554a.r != null)
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        if (!((Boolean) obj).booleanValue()) {
            Toast.makeText(this.f1554a, String.format(this.f1554a.getString(R.string.cannot_open_file), this.f1554a.f1540b), 1).show()
            this.f1554a.finish()
            return
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2)
        this.f1554a.g = new com.c.a.f(this.f1554a, this.f1554a.r)
        this.f1554a.g.a(this.f1554a)
        this.f1554a.g.setLayoutParams(layoutParams)
        this.f1554a.g.b(1.0f)
        ((LinearLayout) this.f1554a.findViewById(R.id.image_layout)).addView(this.f1554a.g)
        this.f1554a.a(this.f1554a.g.e())
    }
}
