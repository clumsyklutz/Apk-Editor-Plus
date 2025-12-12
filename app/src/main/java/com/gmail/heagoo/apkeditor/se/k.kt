package com.gmail.heagoo.apkeditor.se

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class k implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1266a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ i f1267b

    k(i iVar, Int i) {
        this.f1267b = iVar
        this.f1266a = i
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.extract).setOnMenuItemClickListener(l(this))
        contextMenu.add(0, 2, 0, R.string.view).setOnMenuItemClickListener(m(this))
        contextMenu.add(0, 3, 0, R.string.replace).setOnMenuItemClickListener(n(this))
    }
}
