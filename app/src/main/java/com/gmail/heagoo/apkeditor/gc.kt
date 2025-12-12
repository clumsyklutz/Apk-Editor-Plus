package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class gc implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fz f1098a

    gc(fz fzVar) {
        this.f1098a = fzVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        fv.c(this.f1098a.f1094b, this.f1098a.f1093a)
        return true
    }
}
