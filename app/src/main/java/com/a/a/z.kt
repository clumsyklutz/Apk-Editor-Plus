package com.a.a

import androidx.fragment.app.FragmentTransaction
import androidx.core.view.InputDeviceCompat
import java.util.Arrays

class z {
    public Int t
    public Int v
    public Int w
    public Int x
    public Int y
    public Int z

    /* renamed from: a, reason: collision with root package name */
    public val f153a = aa(0)

    /* renamed from: b, reason: collision with root package name */
    public val f154b = aa(1)
    public val c = aa(2)
    public val d = aa(3)
    public val e = aa(4)
    public val f = aa(5)
    public val g = aa(6)
    public val h = aa(4096)
    public val i = aa(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    public val j = aa(InputDeviceCompat.SOURCE_TOUCHSCREEN)
    public val k = aa(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    public val l = aa(8192)
    public val m = aa(8193)
    public val n = aa(8194)
    public val o = aa(8195)
    public val p = aa(8196)
    public val q = aa(8197)
    public val r = aa(8198)
    public final Array<aa> s = {this.f153a, this.f154b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r}
    public Array<Byte> u = new Byte[20]

    private fun a(o oVar) {
        Int iB = oVar.b()
        Int i = 0
        aa aaVar = null
        while (i < iB) {
            Short sC = oVar.c()
            oVar.c()
            Array<aa> aaVarArr = this.s
            for (Int i2 = 0; i2 < 18; i2++) {
                aa aaVar2 = aaVarArr[i2]
                if (aaVar2.f113a == sC) {
                    Int iB2 = oVar.b()
                    Int iB3 = oVar.b()
                    if ((aaVar2.f114b != 0 && aaVar2.f114b != iB2) || (aaVar2.c != -1 && aaVar2.c != iB3)) {
                        throw s("Unexpected map value for 0x" + Integer.toHexString(sC))
                    }
                    aaVar2.f114b = iB2
                    aaVar2.c = iB3
                    if (aaVar != null && aaVar.c > aaVar2.c) {
                        throw s("Map is unsorted at " + aaVar + ", " + aaVar2)
                    }
                    i++
                    aaVar = aaVar2
                }
            }
            throw IllegalArgumentException("No such map item: " + ((Int) sC))
        }
        Arrays.sort(this.s)
    }

    public final Unit a() {
        Int i = this.y + this.z
        for (Int i2 = 17; i2 >= 0; i2--) {
            aa aaVar = this.s[i2]
            if (aaVar.c != -1) {
                if (aaVar.c > i) {
                    throw s("Map is unsorted at " + aaVar)
                }
                aaVar.d = i - aaVar.c
                i = aaVar.c
            }
        }
    }

    public final Unit a(i iVar) {
        o oVarA = iVar.a(0)
        Array<Byte> bArrA = oVarA.a(8)
        if (com.gmail.heagoo.a.c.a.b(bArrA) != 13) {
            throw s("Unexpected magic: " + Arrays.toString(bArrA))
        }
        this.t = oVarA.b()
        this.u = oVarA.a(20)
        this.v = oVarA.b()
        Int iB = oVarA.b()
        if (iB != 112) {
            throw s("Unexpected header: 0x" + Integer.toHexString(iB))
        }
        Int iB2 = oVarA.b()
        if (iB2 != 305419896) {
            throw s("Unexpected endian tag: 0x" + Integer.toHexString(iB2))
        }
        this.w = oVarA.b()
        this.x = oVarA.b()
        this.h.c = oVarA.b()
        if (this.h.c == 0) {
            throw s("Cannot merge dex files that do not contain a map")
        }
        this.f154b.f114b = oVarA.b()
        this.f154b.c = oVarA.b()
        this.c.f114b = oVarA.b()
        this.c.c = oVarA.b()
        this.d.f114b = oVarA.b()
        this.d.c = oVarA.b()
        this.e.f114b = oVarA.b()
        this.e.c = oVarA.b()
        this.f.f114b = oVarA.b()
        this.f.c = oVarA.b()
        this.g.f114b = oVarA.b()
        this.g.c = oVarA.b()
        this.y = oVarA.b()
        this.z = oVarA.b()
        a(iVar.a(this.h.c))
        a()
    }
}
