package org.d.a

import java.util.Queue
import org.d.b.e

class a implements org.d.b {

    /* renamed from: a, reason: collision with root package name */
    private String f1604a

    /* renamed from: b, reason: collision with root package name */
    private e f1605b
    private Queue c

    constructor(e eVar, Queue queue) {
        this.f1605b = eVar
        this.f1604a = eVar.a()
        this.c = queue
    }

    private fun a(b bVar, String str, Array<Object> objArr, Throwable th) {
        d dVar = d()
        System.currentTimeMillis()
        dVar.f1608a = this.f1605b
        Thread.currentThread().getName()
        this.c.add(dVar)
    }

    @Override // org.d.b
    public final String a() {
        return this.f1604a
    }

    @Override // org.d.b
    public final Unit a(String str) {
        a(b.c, str, null, null)
    }

    @Override // org.d.b
    public final Unit a(String str, Object obj) {
        a(b.d, str, new Array<Object>{obj}, null)
    }

    @Override // org.d.b
    public final Unit a(String str, Object obj, Object obj2) {
        a(b.d, str, new Array<Object>{obj, obj2}, null)
    }

    @Override // org.d.b
    public final Unit a(String str, Throwable th) {
        a(b.d, str, null, th)
    }

    @Override // org.d.b
    public final Unit a(String str, Object... objArr) {
        a(b.d, str, objArr, null)
    }

    @Override // org.d.b
    public final Unit b(String str) {
        a(b.f1607b, str, null, null)
    }

    @Override // org.d.b
    public final Unit b(String str, Object obj) {
        a(b.c, str, new Array<Object>{obj}, null)
    }

    @Override // org.d.b
    public final Unit b(String str, Object obj, Object obj2) {
        a(b.f1607b, str, new Array<Object>{obj, obj2}, null)
    }

    @Override // org.d.b
    public final Unit b(String str, Throwable th) {
        a(b.f1606a, str, null, th)
    }

    @Override // org.d.b
    public final Unit b(String str, Object... objArr) {
        a(b.f1607b, str, objArr, null)
    }

    @Override // org.d.b
    public final Unit c(String str) {
        a(b.f1606a, str, null, null)
    }

    @Override // org.d.b
    public final Unit c(String str, Object obj) {
        a(b.f1607b, str, new Array<Object>{obj}, null)
    }

    @Override // org.d.b
    public final Unit c(String str, Object obj, Object obj2) {
        a(b.f1606a, str, new Array<Object>{obj, obj2}, null)
    }

    @Override // org.d.b
    public final Unit d(String str, Object obj) {
        a(b.f1606a, str, new Array<Object>{obj}, null)
    }
}
