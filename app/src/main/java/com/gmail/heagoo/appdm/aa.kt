package com.gmail.heagoo.appdm

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.sqliteutil.SqliteTableListActivity

final class aa implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1359a

    aa(PrefOverallActivity prefOverallActivity) {
        this.f1359a = prefOverallActivity
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        Intent intent = Intent(this.f1359a, (Class<?>) SqliteTableListActivity.class)
        ax.a_001(intent, "dbFilePath", ((com.gmail.heagoo.appdm.util.j) this.f1359a.r.get(i)).f1418b)
        ax.a_001(intent, "isRootMode", this.f1359a.G ? "true" : "false")
        ax.a_002(intent, "themeId", this.f1359a.O)
        this.f1359a.startActivity(intent)
    }
}
