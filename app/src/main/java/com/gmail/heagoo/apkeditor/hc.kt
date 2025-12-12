package com.gmail.heagoo.apkeditor

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.PopupWindow
import com.gmail.heagoo.apkeditor.pro.R

final class hc implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ er f1137a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ PopupWindow f1138b
    private /* synthetic */ gy c

    hc(gy gyVar, er erVar, PopupWindow popupWindow) {
        this.c = gyVar
        this.f1137a = erVar
        this.f1138b = popupWindow
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        switch (this.f1137a.a(i)) {
            case 0:
                gy.d(this.c)
                break
            case 1:
                new com.b.a.f(this.c, -1, this.c).show()
                break
            case 2:
                this.c.i = 2
                dk().a(this.c, R.string.delete_lines, this.c)
                break
            case 3:
                this.c.startActivity(Intent(this.c, (Class<?>) SettingEditorActivity.class))
                break
            case 4:
                this.c.startActivity(Intent(this.c, (Class<?>) AboutModActivity.class))
                break
            case 5:
                this.c.i = 5
                dk().a(this.c, R.string.comment_lines, this.c)
                break
            case 6:
                this.c.startActivity(Intent(this.c, (Class<?>) TemplatesActivity.class))
                break
            case 7:
                gy.f(this.c)
                break
        }
        if (this.f1138b != null) {
            this.f1138b.dismiss()
        }
    }
}
