package com.gmail.heagoo.sqliteutil

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import com.gmail.heagoo.a.c.ax

final class h implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ SqliteTableListActivity f1574a

    h(SqliteTableListActivity sqliteTableListActivity) {
        this.f1574a = sqliteTableListActivity
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        String str = (String) this.f1574a.c.get(i)
        Intent intent = Intent(this.f1574a, (Class<?>) SqliteTableViewActivity.class)
        ax.a_001(intent, "originDbFilePath", this.f1574a.f1557a)
        ax.a_001(intent, "dbFilePath", this.f1574a.f1558b)
        ax.a_001(intent, "tableName", str)
        ax.a_002(intent, "themeId", this.f1574a.e)
        this.f1574a.startActivity(intent)
    }
}
