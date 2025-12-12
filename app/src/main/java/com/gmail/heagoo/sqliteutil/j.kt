package com.gmail.heagoo.sqliteutil

import android.view.View

final class j implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ SqliteTableViewActivity f1576a

    j(SqliteTableViewActivity sqliteTableViewActivity) {
        this.f1576a = sqliteTableViewActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        SqliteTableViewActivity.b(this.f1576a)
    }
}
