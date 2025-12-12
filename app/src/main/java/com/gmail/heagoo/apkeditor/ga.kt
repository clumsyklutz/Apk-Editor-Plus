package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class ga implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fz f1096a

    ga(fz fzVar) {
        this.f1096a = fzVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        fv.a(this.f1096a.f1094b, this.f1096a.f1093a)
        return true
    }
}
