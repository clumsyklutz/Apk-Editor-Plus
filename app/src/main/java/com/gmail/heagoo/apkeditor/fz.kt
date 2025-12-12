package com.gmail.heagoo.apkeditor

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class fz implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1093a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ fv f1094b

    fz(fv fvVar, Int i) {
        this.f1094b = fvVar
        this.f1093a = i
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.delete).setOnMenuItemClickListener(ga(this))
        contextMenu.add(0, 2, 0, R.string.extract).setOnMenuItemClickListener(gb(this))
        contextMenu.add(0, 3, 0, R.string.replace).setOnMenuItemClickListener(gc(this))
    }
}
