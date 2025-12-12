package com.gmail.heagoo.b

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class f implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1432a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f1433b

    f(e eVar, Int i) {
        this.f1433b = eVar
        this.f1432a = i
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 1, 0, R.string.delete).setOnMenuItemClickListener(g(this))
        contextMenu.add(0, 2, 0, R.string.rename).setOnMenuItemClickListener(h(this))
        contextMenu.add(0, 3, 0, R.string.new_file).setOnMenuItemClickListener(i(this))
        contextMenu.add(0, 4, 0, R.string.install).setOnMenuItemClickListener(p(this))
        contextMenu.add(0, 5, 0, R.string.sign).setOnMenuItemClickListener(q(this))
        contextMenu.add(0, 6, 0, R.string.action_verify).setOnMenuItemClickListener(s(this))
    }
}
