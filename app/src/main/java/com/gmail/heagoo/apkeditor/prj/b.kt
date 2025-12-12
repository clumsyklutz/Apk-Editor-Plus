package com.gmail.heagoo.apkeditor.prj

final class b extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1232a = false

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ProjectListActivity f1233b

    b(ProjectListActivity projectListActivity) {
        this.f1233b = projectListActivity
    }

    final Unit a() {
        this.f1232a = true
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        new com.gmail.heagoo.common.a()
        Int i = 0
        while (true) {
            Int i2 = i
            if (this.f1232a || i2 >= this.f1233b.c.size()) {
                return
            }
            f fVar = (f) this.f1233b.c.get(i2)
            try {
                this.f1233b.e.a(fVar.f1240b, com.gmail.heagoo.common.a.a(this.f1233b, fVar.f1240b).c)
            } catch (Exception e) {
            }
            i = i2 + 1
        }
    }
}
