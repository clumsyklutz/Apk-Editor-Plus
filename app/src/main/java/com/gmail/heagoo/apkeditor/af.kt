package com.gmail.heagoo.apkeditor

import android.view.MenuItem
import java.util.ArrayList
import java.util.List

final class af implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ad f841a

    af(ad adVar) {
        this.f841a = adVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        ArrayList arrayList = ArrayList(1)
        arrayList.add(Integer.valueOf(this.f841a.f838a))
        this.f841a.f839b.a((List) arrayList)
        return true
    }
}
