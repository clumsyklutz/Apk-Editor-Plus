package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class fr implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fq f1082a

    fr(fq fqVar) {
        this.f1082a = fqVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        fo.a(this.f1082a.f1081b, this.f1082a.f1080a)
        return true
    }
}
