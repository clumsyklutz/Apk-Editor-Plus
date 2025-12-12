package com.gmail.heagoo.apkeditor

import android.view.ContextMenu
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.b.a
import java.util.ArrayList

final class ad implements View.OnCreateContextMenuListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f838a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ApkInfoActivity f839b
    private /* synthetic */ Boolean c

    ad(ApkInfoActivity apkInfoActivity, Int i, Boolean z) {
        this.f839b = apkInfoActivity
        this.f838a = i
        this.c = z
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final Unit onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        ArrayList arrayList = ArrayList()
        String strA = this.f839b.e.a(arrayList)
        Boolean z = ((a) arrayList.get(this.f838a)).f1425b
        if (!this.c) {
            contextMenu.add(0, 1, 0, R.string.delete).setOnMenuItemClickListener(ae(this))
        }
        if (!this.c || strA.equals(this.f839b.f758b)) {
            contextMenu.add(0, 2, 0, R.string.extract).setOnMenuItemClickListener(af(this))
        }
        if (!this.c || strA.equals(this.f839b.f758b)) {
            contextMenu.add(0, 3, 0, R.string.replace).setOnMenuItemClickListener(z ? ag(this) : ah(this))
        }
        contextMenu.add(0, 4, 0, R.string.add_a_file).setOnMenuItemClickListener(ai(this))
        contextMenu.add(0, 5, 0, R.string.new_folder).setOnMenuItemClickListener(aj(this))
        contextMenu.add(0, 6, 0, R.string.external_editor).setOnMenuItemClickListener(ExtEdCtx(this))
    }
}
