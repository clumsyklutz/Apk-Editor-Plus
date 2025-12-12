package com.a.a

class u implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Byte> f144a

    constructor(Array<Byte> bArr) {
        this.f144a = bArr
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(u uVar) {
        Int iMin = Math.min(this.f144a.length, uVar.f144a.length)
        for (Int i = 0; i < iMin; i++) {
            if (this.f144a[i] != uVar.f144a[i]) {
                return (this.f144a[i] & 255) - (uVar.f144a[i] & 255)
            }
        }
        return this.f144a.length - uVar.f144a.length
    }

    public final com.a.a.a.b a() {
        return new com.a.a.a.a(this.f144a)
    }

    public final Unit a(o oVar) {
        oVar.a(this.f144a)
    }

    public final String toString() {
        return Integer.toHexString(this.f144a[0] & 255) + "...(" + this.f144a.length + ")"
    }
}
