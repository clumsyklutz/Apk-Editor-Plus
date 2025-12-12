package com.gmail.heagoo.apkeditor

final class w implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Boolean f1347a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f1348b

    w(ApkInfoActivity apkInfoActivity, Boolean z) {
        this.f1348b = apkInfoActivity
        this.f1347a = z
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        if (this.f1347a) {
            this.f1348b.s()
            this.f1348b.a()
        }
        this.f1348b.x()
        this.f1348b.S.setVisibility(0)
        if (ApkInfoActivity.j(this.f1348b)) {
            this.f1348b.U.setVisibility(0)
        } else {
            this.f1348b.U.setVisibility(8)
        }
        this.f1348b.V.setVisibility(0)
    }
}
