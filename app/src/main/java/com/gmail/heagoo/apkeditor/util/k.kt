package com.gmail.heagoo.apkeditor.util

import java.util.ArrayList
import java.util.List

final class k {

    /* renamed from: a, reason: collision with root package name */
    String f1321a

    /* renamed from: b, reason: collision with root package name */
    List f1322b
    private List c

    private constructor(String str, String str2, Int i, String str3, String str4) {
        this.f1322b = ArrayList()
        this.c = ArrayList()
        this.f1321a = str2.startsWith("/") ? str2 : str + str2
        this.f1322b.add(j(i > 0 ? i - 1 : 0, str3, str4, (Byte) 0))
    }

    /* synthetic */ k(String str, String str2, Int i, String str3, String str4, Byte b2) {
        this(str, str2, i, str3, str4)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Int i) {
        for (i iVar : this.c) {
            if (iVar.f1317a == i) {
                return iVar
            }
        }
        return null
    }

    public final Unit a() {
        for (j jVar : this.f1322b) {
            i iVarA = a(jVar.f1319a)
            if (iVarA == null) {
                iVarA = i(jVar.f1319a, (Byte) 0)
                this.c.add(iVarA)
            }
            iVarA.f1318b.add(jVar.f1320b)
        }
    }
}
