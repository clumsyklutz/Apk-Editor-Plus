package com.gmail.heagoo.appdm

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class p implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f1397a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ com.gmail.heagoo.appdm.util.e f1398b
    final /* synthetic */ o c

    p(o oVar, String str, com.gmail.heagoo.appdm.util.e eVar) {
        this.c = oVar
        this.f1397a = str
        this.f1398b = eVar
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.appdm_open_in_editor).setOnMenuItemClickListener(q(this))
    }
}
