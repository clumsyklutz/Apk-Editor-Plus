package com.gmail.heagoo.apkeditor.se

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class d implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1258a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ c f1259b

    d(c cVar, Int i) {
        this.f1259b = cVar
        this.f1258a = i
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.extract).setOnMenuItemClickListener(e(this))
        contextMenu.add(0, 2, 0, R.string.replace).setOnMenuItemClickListener(f(this))
    }
}
