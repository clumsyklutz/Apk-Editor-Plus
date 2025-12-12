package com.gmail.heagoo.apkeditor

import com.gmail.heagoo.apkeditor.pro.R

class d implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f951a = null

    /* renamed from: b, reason: collision with root package name */
    private String f952b = null
    private /* synthetic */ ApkComposeActivity c

    d(ApkComposeActivity apkComposeActivity) {
        this.c = apkComposeActivity
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws Exception {
        com.gmail.heagoo.apkeditor.util.x xVar = new com.gmail.heagoo.apkeditor.util.x(this.c.w)
        xVar.a(this.c, this.c.v)
        this.f952b = xVar.f1340b
        if (xVar.f1339a != null) {
            this.f951a = xVar.f1339a
            throw Exception(this.f951a)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.f951a != null) {
            this.c.l.setText(this.f951a)
            return
        }
        ApkComposeActivity.c(this.c, true)
        this.c.l.setText(String.format(this.c.getString(R.string.patch_code_cache_done), this.f952b))
        this.c.m.setText(R.string.launch)
    }
}
