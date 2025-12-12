package com.gmail.heagoo.apkeditor

import android.view.MenuItem

final class ah implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ad f843a

    ah(ad adVar) {
        this.f843a = adVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        this.f843a.f839b.a(this.f843a.f838a)
        return true
    }
}
