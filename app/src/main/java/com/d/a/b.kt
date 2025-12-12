package com.d.a

import java.util.Map

final class b implements j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f719a

    b(a aVar) {
        this.f719a = aVar
    }

    @Override // com.d.a.j
    public final Object a(Object obj, Map map) {
        try {
            return this.f719a.a(obj, map)
        } catch (IllegalAccessException e) {
            throw IllegalStateException(e)
        }
    }
}
