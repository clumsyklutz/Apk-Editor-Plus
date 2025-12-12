package com.gmail.heagoo.sqliteutil

import android.content.DialogInterface
import android.widget.Toast

final class f implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ SqliteRowViewActivity f1572a

    f(SqliteRowViewActivity sqliteRowViewActivity) {
        this.f1572a = sqliteRowViewActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        try {
            SqliteRowViewActivity.a(this.f1572a)
            this.f1572a.finish()
        } catch (Exception e) {
            Toast.makeText(this.f1572a, e.getClass().getSimpleName() + ": " + e.getMessage(), 0).show()
        }
    }
}
