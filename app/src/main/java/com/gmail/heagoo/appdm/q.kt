package com.gmail.heagoo.appdm

import android.view.MenuItem

final class q implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ p f1399a

    q(p pVar) {
        this.f1399a = pVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        this.f1399a.c.f1396a.a(this.f1399a.f1397a + "/" + this.f1399a.f1398b.f1413a, (String) null)
        return true
    }
}
