package com.gmail.heagoo.apkeditor

import android.view.View

class ng implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    public final FileListActivity f1227a

    constructor(FileListActivity fileListActivity) {
        this.f1227a = fileListActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1227a.finish()
    }
}
