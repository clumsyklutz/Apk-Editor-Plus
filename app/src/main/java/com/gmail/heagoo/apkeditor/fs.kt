package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class fs implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fq f1083a

    fs(fq fqVar) {
        this.f1083a = fqVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        fo.b(this.f1083a.f1081b, this.f1083a.f1080a)
        return true
    }
}
