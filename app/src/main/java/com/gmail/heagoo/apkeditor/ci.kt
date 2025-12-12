package com.gmail.heagoo.apkeditor

import android.view.View

final class ci implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ FileListActivity f934a

    ci(FileListActivity fileListActivity) {
        this.f934a = fileListActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        try {
            FileListActivity.a(this.f934a)
        } catch (Exception e) {
        }
    }
}
