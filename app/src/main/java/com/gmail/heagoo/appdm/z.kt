package com.gmail.heagoo.appdm

import android.content.Intent
import android.support.v4.view.PointerIconCompat
import android.view.View
import android.widget.AdapterView
import com.gmail.heagoo.a.c.ax

final class z implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1423a

    z(PrefOverallActivity prefOverallActivity) {
        this.f1423a = prefOverallActivity
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        this.f1423a.K = System.currentTimeMillis()
        this.f1423a.N++
        Intent intent = Intent(this.f1423a, (Class<?>) PrefDetailActivity.class)
        ax.a_001(intent, "appName", (String) this.f1423a.m.loadLabel(this.f1423a.l))
        ax.a_001(intent, "xmlFilePath", ((com.gmail.heagoo.appdm.util.j) this.f1423a.q.get(i)).f1418b)
        ax.a_001(intent, "packagePath", this.f1423a.c)
        ax.b_012(intent, "isRootMode", this.f1423a.G)
        ax.a_002(intent, "themeId", this.f1423a.O)
        this.f1423a.startActivityForResult(intent, PointerIconCompat.TYPE_CONTEXT_MENU)
    }
}
