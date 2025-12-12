package com.a.b.a.b

class r {

    /* renamed from: a, reason: collision with root package name */
    private final Int f219a

    /* renamed from: b, reason: collision with root package name */
    private final Int f220b
    private final com.a.b.f.c.y c
    private final com.a.b.f.c.y d
    private final com.a.b.f.c.y e
    private final Int f

    constructor(Int i, Int i2, com.a.b.f.c.y yVar, com.a.b.f.c.y yVar2, com.a.b.f.c.y yVar3, Int i3) {
        if (i < 0) {
            throw IllegalArgumentException("startPc < 0")
        }
        if (i2 < 0) {
            throw IllegalArgumentException("length < 0")
        }
        if (yVar == null) {
            throw NullPointerException("name == null")
        }
        if (yVar2 == null && yVar3 == null) {
            throw NullPointerException("(descriptor == null) && (signature == null)")
        }
        if (i3 < 0) {
            throw IllegalArgumentException("index < 0")
        }
        this.f219a = i
        this.f220b = i2
        this.c = yVar
        this.d = yVar2
        this.e = yVar3
        this.f = i3
    }

    public final r a(com.a.b.f.c.y yVar) {
        return r(this.f219a, this.f220b, this.c, this.d, yVar, this.f)
    }

    public final com.a.b.f.b.m a() {
        return com.a.b.f.b.m.a(this.c, this.e)
    }

    public final Boolean a(Int i, Int i2) {
        return i2 == this.f && i >= this.f219a && i < this.f219a + this.f220b
    }

    public final Boolean a(r rVar) {
        return this.f219a == rVar.f219a && this.f220b == rVar.f220b && this.f == rVar.f && this.c.equals(rVar.c)
    }

    public final com.a.b.f.d.c b() {
        return com.a.b.f.d.c.a(this.d.j())
    }
}
