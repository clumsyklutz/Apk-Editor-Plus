package com.gmail.heagoo.neweditor

import android.widget.ScrollView

final class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ e f1515a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1516b

    g(EditorActivity editorActivity, e eVar) {
        this.f1516b = editorActivity
        this.f1515a = eVar
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        ScrollView scrollView = this.f1516b.K
        e eVar = this.f1515a
        e eVar2 = this.f1515a
        scrollView.scrollTo(0, 0)
    }
}
