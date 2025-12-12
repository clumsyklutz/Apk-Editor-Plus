package com.gmail.heagoo.apkeditor.a.a

import android.content.Context
import java.io.Serializable
import java.util.Map

class p implements com.gmail.heagoo.apkeditor.a.d, Serializable {

    /* renamed from: a, reason: collision with root package name */
    private String f800a

    /* renamed from: b, reason: collision with root package name */
    private String f801b

    constructor(String str, String str2) {
        this.f800a = str
        this.f801b = str2
    }

    @Override // com.gmail.heagoo.apkeditor.a.d
    public final Unit a(Context context, String str, Map map, com.gmail.heagoo.apkeditor.a.f fVar) {
        for (String str2 : a.a.b.a.a.x.c(str, "")) {
            if (str2.endsWith(".dex")) {
                String str3 = com.gmail.heagoo.a.c.a.d(context, "tmp") + com.gmail.heagoo.common.s.a(6) + ".dex"
                if (new com.gmail.heagoo.apkeditor.b.e(str, str2).a(this.f800a, this.f801b, str3)) {
                    map.put(str2, str3)
                }
            }
        }
    }
}
