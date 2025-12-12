package com.a.b.a.a

class a extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.a f155a

    /* renamed from: b, reason: collision with root package name */
    private final Int f156b

    constructor(com.a.b.f.c.a aVar, Int i) {
        super("AnnotationDefault")
        if (aVar == null) {
            throw NullPointerException("value == null")
        }
        this.f155a = aVar
        this.f156b = i
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return this.f156b + 6
    }

    public final com.a.b.f.c.a b() {
        return this.f155a
    }
}
