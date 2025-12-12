package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class ft implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fq f1084a

    ft(fq fqVar) {
        this.f1084a = fqVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        fo.c(this.f1084a.f1081b, this.f1084a.f1080a)
        return true
    }
}
