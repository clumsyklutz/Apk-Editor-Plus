package com.a.a.a

class a implements b {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Byte> f110a

    /* renamed from: b, reason: collision with root package name */
    private Int f111b

    constructor(Byte... bArr) {
        this.f110a = bArr
    }

    @Override // com.a.a.a.b
    public final Byte d() {
        Array<Byte> bArr = this.f110a
        Int i = this.f111b
        this.f111b = i + 1
        return bArr[i]
    }
}
