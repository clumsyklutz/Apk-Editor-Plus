package com.gmail.heagoo.apkeditor

final class gj implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1105a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ SettingActivity f1106b

    gj(SettingActivity settingActivity, String str) {
        this.f1106b = settingActivity
        this.f1105a = str
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        this.f1106b.a()
        new com.gmail.heagoo.common.c().a("rm -rf " + this.f1105a + "/decoded\nrm -rf " + this.f1105a + "/temp", (Array<String>) null, (Integer) 8000)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
    }
}
