package com.a.b.c.c

import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.Iterator
import java.util.Map
import java.util.TreeMap

class al extends at {

    /* renamed from: a, reason: collision with root package name */
    private static val f358a = am()

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList f359b
    private final HashMap c
    private final Int d
    private Int e

    constructor(String str, r rVar, Int i, Int i2) {
        super(str, rVar, i)
        this.f359b = ArrayList(100)
        this.c = HashMap(100)
        this.d = i2
        this.e = -1
    }

    @Override // com.a.b.c.c.at
    public final Int a(ad adVar) {
        return ((ap) adVar).f()
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f359b
    }

    public final Unit a(ap apVar) {
        j()
        try {
            if (apVar.g() > f()) {
                throw IllegalArgumentException("incompatible item alignment")
            }
            this.f359b.add(apVar)
        } catch (NullPointerException e) {
            throw NullPointerException("item == null")
        }
    }

    public final Unit a(com.a.b.h.r rVar, ae aeVar, String str) {
        i()
        TreeMap treeMap = TreeMap()
        Iterator it = this.f359b.iterator()
        while (it.hasNext()) {
            ap apVar = (ap) it.next()
            if (apVar.a() == aeVar) {
                treeMap.put(apVar.b(), apVar)
            }
        }
        if (treeMap.size() == 0) {
            return
        }
        rVar.a(0, str)
        for (Map.Entry entry : treeMap.entrySet()) {
            rVar.a(0, ((ap) entry.getValue()).h() + ' ' + ((String) entry.getKey()) + '\n')
        }
    }

    @Override // com.a.b.c.c.at
    protected final Unit a_(com.a.b.h.r rVar) {
        Boolean zB = rVar.b()
        r rVarE = e()
        Iterator it = this.f359b.iterator()
        Int iE_ = 0
        Boolean z = true
        while (it.hasNext()) {
            ap apVar = (ap) it.next()
            if (zB) {
                if (z) {
                    z = false
                } else {
                    rVar.a(0, "\n")
                }
            }
            Int iG = apVar.g() - 1
            Int i = (iG ^ (-1)) & (iE_ + iG)
            if (iE_ != i) {
                rVar.f(i - iE_)
                iE_ = i
            }
            apVar.a(rVarE, rVar)
            iE_ = apVar.e_() + iE_
        }
        if (iE_ != this.e) {
            throw RuntimeException("output size mismatch")
        }
    }

    public final ap b(ap apVar) {
        j()
        ap apVar2 = (ap) this.c.get(apVar)
        if (apVar2 != null) {
            return apVar2
        }
        a(apVar)
        this.c.put(apVar, apVar)
        return apVar
    }

    @Override // com.a.b.c.c.at
    protected final Unit c() {
        r rVarE = e()
        Int i = 0
        while (true) {
            Int size = this.f359b.size()
            if (i >= size) {
                return
            }
            Int i2 = i
            while (i2 < size) {
                ((ap) this.f359b.get(i2)).a(rVarE)
                i2++
            }
            i = i2
        }
    }

    public final Unit d() {
        i()
        switch (an.f360a[this.d - 1]) {
            case 1:
                Collections.sort(this.f359b)
                break
            case 2:
                Collections.sort(this.f359b, f358a)
                break
        }
        Int size = this.f359b.size()
        Int iE_ = 0
        for (Int i = 0; i < size; i++) {
            ap apVar = (ap) this.f359b.get(i)
            try {
                Int iB = apVar.b(this, iE_)
                if (iB < iE_) {
                    throw RuntimeException("bogus place() result for " + apVar)
                }
                iE_ = iB + apVar.e_()
            } catch (RuntimeException e) {
                throw com.a.a.a.d.a(e, "...while placing " + apVar)
            }
        }
        this.e = iE_
    }

    @Override // com.a.b.c.c.at
    public final Int f_() {
        i()
        return this.e
    }
}
