package com.gmail.heagoo.apkeditor

import android.view.View

class nf implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    public final FileListActivity f1226a

    constructor(FileListActivity fileListActivity) {
        this.f1226a = fileListActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        FileListActivity.d(this.f1226a)
    }
}
