package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class ExtEdCtx implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ad f765a

    ExtEdCtx(ad adVar) {
        this.f765a = adVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        this.f765a.f839b.aa(this.f765a.f838a)
        return true
    }
}
