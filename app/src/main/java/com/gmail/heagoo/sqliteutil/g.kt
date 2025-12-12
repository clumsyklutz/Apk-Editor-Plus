package com.gmail.heagoo.sqliteutil

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List

final class g extends ArrayAdapter {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ SqliteTableListActivity f1573a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(SqliteTableListActivity sqliteTableListActivity, Context context, Int i, List list) {
        super(context, R.layout.item_sql_table, list)
        this.f1573a = sqliteTableListActivity
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        return (TextView) super.getView(i, view, viewGroup)
    }
}
