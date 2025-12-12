package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class gb implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fz f1097a

    gb(fz fzVar) {
        this.f1097a = fzVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        fv.b(this.f1097a.f1094b, this.f1097a.f1093a)
        return true
    }
}
