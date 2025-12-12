package com.a.b.g

import java.util.HashSet

class w {

    /* renamed from: a, reason: collision with root package name */
    private final an f655a

    private constructor(an anVar) {
        this.f655a = anVar
    }

    fun a(an anVar) {
        w wVar = w(anVar)
        com.a.b.f.b.Array<r> rVarArr = new com.a.b.f.b.r[wVar.f655a.h()]
        HashSet hashSet = HashSet()
        wVar.f655a.a(x(wVar, rVarArr, hashSet))
        wVar.f655a.a(hashSet)
    }
}
