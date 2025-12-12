package com.gmail.heagoo.neweditor

final class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1514a

    f(EditorActivity editorActivity) {
        this.f1514a = editorActivity
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f1514a.b(true)
    }
}
