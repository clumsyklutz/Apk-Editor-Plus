package com.gmail.heagoo.apkeditor

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class fq implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1080a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ fo f1081b

    fq(fo foVar, Int i) {
        this.f1081b = foVar
        this.f1080a = i
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.delete).setOnMenuItemClickListener(fr(this))
        contextMenu.add(0, 2, 0, R.string.extract).setOnMenuItemClickListener(fs(this))
        contextMenu.add(0, 3, 0, R.string.replace).setOnMenuItemClickListener(ft(this))
    }
}
