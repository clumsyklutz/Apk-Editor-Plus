package com.gmail.heagoo.appdm

import com.gmail.heagoo.apkeditor.fa
import java.util.Collections
import java.util.List

final class r implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1400a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1401b

    r(PrefOverallActivity prefOverallActivity, String str) {
        this.f1401b = prefOverallActivity
        this.f1400a = str
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        List listA = this.f1401b.p.a(this.f1400a, true)
        Collections.sort(listA, new com.gmail.heagoo.appdm.util.f())
        if (listA != null) {
            this.f1401b.p.a(this.f1400a, listA)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
    }
}
