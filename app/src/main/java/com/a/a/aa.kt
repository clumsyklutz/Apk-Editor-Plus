package com.a.a

class aa implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    public final Short f113a

    /* renamed from: b, reason: collision with root package name */
    public Int f114b = 0
    public Int c = -1
    public Int d = 0

    constructor(Int i) {
        this.f113a = (Short) i
    }

    public final Boolean a() {
        return this.f114b > 0
    }

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ Int compareTo(Object obj) {
        aa aaVar = (aa) obj
        if (this.c != aaVar.c) {
            return this.c < aaVar.c ? -1 : 1
        }
        return 0
    }

    public final String toString() {
        return String.format("Section[type=%#x,off=%#x,size=%#x]", Short.valueOf(this.f113a), Integer.valueOf(this.c), Integer.valueOf(this.f114b))
    }
}
