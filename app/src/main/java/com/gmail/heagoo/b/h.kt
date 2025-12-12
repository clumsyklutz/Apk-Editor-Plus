package com.gmail.heagoo.b

import android.view.MenuItem

final class h implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ f f1435a

    h(f fVar) {
        this.f1435a = fVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        e.b(this.f1435a.f1433b, this.f1435a.f1432a)
        return true
    }
}
