package com.gmail.heagoo.b

import android.view.MenuItem

final class g implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ f f1434a

    g(f fVar) {
        this.f1434a = fVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        e.a(this.f1434a.f1433b, this.f1434a.f1432a)
        return true
    }
}
