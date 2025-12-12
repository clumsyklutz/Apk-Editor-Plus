package com.gmail.heagoo.apkeditor

import android.view.MenuItem
import java.util.ArrayList

final class ae implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ad f840a

    ae(ad adVar) {
        this.f840a = adVar
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final Boolean onMenuItemClick(MenuItem menuItem) {
        ArrayList arrayList = ArrayList(1)
        arrayList.add(Integer.valueOf(this.f840a.f838a))
        this.f840a.f839b.e.c(arrayList)
        return true
    }
}
