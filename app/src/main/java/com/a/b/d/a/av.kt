package com.a.b.d.a

import java.io.EOFException

class av extends b implements d {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Short> f427a

    constructor(Array<Short> sArr) {
        if (sArr == null) {
            throw NullPointerException("array == null")
        }
        this.f427a = sArr
    }

    @Override // com.a.b.d.a.d
    public final Int c() throws EOFException {
        try {
            Short s = this.f427a[a()]
            a(1)
            return s & 65535
        } catch (ArrayIndexOutOfBoundsException e) {
            throw EOFException()
        }
    }

    @Override // com.a.b.d.a.d
    public final Int d() {
        return c() | (c() << 16)
    }

    @Override // com.a.b.d.a.d
    public final Long e() {
        return c() | (c() << 16) | (c() << 32) | (c() << 48)
    }

    public final Boolean f() {
        return a() < this.f427a.length
    }
}
