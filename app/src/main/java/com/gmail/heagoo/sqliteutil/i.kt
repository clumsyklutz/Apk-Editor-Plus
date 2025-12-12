package com.gmail.heagoo.sqliteutil

import android.view.View

final class i implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ SqliteTableViewActivity f1575a

    i(SqliteTableViewActivity sqliteTableViewActivity) {
        this.f1575a = sqliteTableViewActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        SqliteTableViewActivity.a(this.f1575a)
    }
}
