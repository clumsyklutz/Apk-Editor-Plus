package com.gmail.heagoo.apkeditor

final class bh implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f890a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkSearchActivity f891b

    bh(ApkSearchActivity apkSearchActivity, String str) {
        this.f891b = apkSearchActivity
        this.f890a = str
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f891b.d.add(this.f890a)
        this.f891b.a(this.f891b.d.size())
        bc bcVar = this.f891b.g
        bcVar.f884a.add(this.f890a)
        bcVar.notifyDataSetChanged()
    }
}
