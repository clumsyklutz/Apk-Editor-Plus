package com.gmail.heagoo.apkeditor

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class co implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cn f940a

    co(cn cnVar) {
        this.f940a = cnVar
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.new_folder).setOnMenuItemClickListener(cp(this))
    }
}
