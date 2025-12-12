package com.a.b.a.b

import androidx.appcompat.R
import java.util.ArrayList

class b implements k {

    /* renamed from: a, reason: collision with root package name */
    private final l f195a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Int> f196b
    private final Array<Int> c
    private final Array<Int> d
    private final com.a.b.h.Array<j> e
    private final Array<e> f
    private Int g

    private constructor(l lVar) {
        if (lVar == null) {
            throw NullPointerException("method == null")
        }
        this.f195a = lVar
        Int iB = lVar.k().b() + 1
        this.f196b = com.gmail.heagoo.a.c.a.s(iB)
        this.c = com.gmail.heagoo.a.c.a.s(iB)
        this.d = com.gmail.heagoo.a.c.a.s(iB)
        this.e = new com.a.b.h.j[iB]
        this.f = new e[iB]
        this.g = -1
    }

    fun a(l lVar) {
        b bVar = b(lVar)
        h hVarK = bVar.f195a.k()
        e eVarL = bVar.f195a.l()
        Int iD_ = eVarL.d_()
        com.gmail.heagoo.a.c.a.b(bVar.f196b, 0)
        com.gmail.heagoo.a.c.a.b(bVar.d, 0)
        while (!com.gmail.heagoo.a.c.a.b(bVar.f196b)) {
            try {
                Array<Int> iArr = bVar.f196b
                if (bVar == null) {
                    throw NullPointerException("visitor == null")
                }
                while (true) {
                    Int iD = com.gmail.heagoo.a.c.a.d(iArr, 0)
                    if (iD < 0) {
                        break
                    }
                    com.gmail.heagoo.a.c.a.c(iArr, iD)
                    hVarK.a(iD, bVar)
                    bVar.a(iD)
                }
                for (Int i = 0; i < iD_; i++) {
                    f fVarA = eVarL.a(i)
                    Int iA = fVarA.a()
                    Int iB = fVarA.b()
                    if (com.gmail.heagoo.a.c.a.a(bVar.c, iA, iB)) {
                        com.gmail.heagoo.a.c.a.b(bVar.d, iA)
                        com.gmail.heagoo.a.c.a.b(bVar.d, iB)
                        bVar.a(fVarA.c(), true)
                    }
                }
            } catch (IllegalArgumentException e) {
                throw ah("flow of control falls off end of method", e)
            }
        }
        return bVar.b()
    }

    private fun a(Int i, Int i2, Boolean z) {
        com.gmail.heagoo.a.c.a.b(this.c, i)
        if (z) {
            a(i + i2, false)
        } else {
            com.gmail.heagoo.a.c.a.b(this.d, i + i2)
        }
    }

    private fun a(Int i, Boolean z) {
        if (!com.gmail.heagoo.a.c.a.a(this.c, i)) {
            com.gmail.heagoo.a.c.a.b(this.f196b, i)
        }
        if (z) {
            com.gmail.heagoo.a.c.a.b(this.d, i)
        }
    }

    private fun b() {
        Int i
        e eVar
        Array<c> cVarArr = new c[this.f195a.k().b()]
        Int i2 = 0
        Int i3 = 0
        while (true) {
            Int iD = com.gmail.heagoo.a.c.a.d(this.d, i2 + 1)
            if (iD < 0) {
                break
            }
            if (com.gmail.heagoo.a.c.a.a(this.c, i2)) {
                com.a.b.h.j jVarA = null
                Int i4 = iD - 1
                while (true) {
                    if (i4 < i2) {
                        i4 = -1
                        break
                    }
                    jVarA = this.e[i4]
                    if (jVarA != null) {
                        break
                    }
                    i4--
                }
                if (jVarA == null) {
                    jVarA = com.a.b.h.j.a(iD)
                    eVar = e.f199a
                } else {
                    eVar = this.f[i4]
                    if (eVar == null) {
                        eVar = e.f199a
                    }
                }
                cVarArr[i3] = c(i2, i2, iD, jVarA, eVar)
                i = i3 + 1
            } else {
                i = i3
            }
            i2 = iD
            i3 = i
        }
        d dVar = d(i3)
        for (Int i5 = 0; i5 < i3; i5++) {
            dVar.a(i5, cVarArr[i5])
        }
        return dVar
    }

    private fun b(Int i, Int i2, Boolean z) {
        com.a.b.h.j jVarA
        Int i3 = i + i2
        if (z) {
            a(i3, true)
        }
        e eVarB = this.f195a.l().b(i)
        this.f[i] = eVarB
        com.a.b.h.Array<j> jVarArr = this.e
        Int i4 = z ? i3 : -1
        if (i4 < -1) {
            throw IllegalArgumentException("noException < -1")
        }
        Boolean z2 = i4 >= 0
        Int iD_ = eVarB.d_()
        if (iD_ == 0) {
            jVarA = z2 ? com.a.b.h.j.a(i4) : com.a.b.h.j.f673a
        } else {
            com.a.b.h.j jVar = new com.a.b.h.j((z2 ? 1 : 0) + iD_)
            for (Int i5 = 0; i5 < iD_; i5++) {
                jVar.c(eVarB.a(i5).c())
            }
            if (z2) {
                jVar.c(i4)
            }
            jVar.b_()
            jVarA = jVar
        }
        jVarArr[i] = jVarA
    }

    @Override // com.a.b.a.b.k
    public final Int a() {
        return this.g
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i) {
        this.g = i
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3) {
        a(i2, i3, true)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, Int i4) {
        switch (i) {
            case 167:
                a(i2, i3, false)
                this.e[i2] = com.a.b.h.j.a(i4)
                break
            case 168:
                a(i2, true)
            default:
                Int i5 = i2 + i3
                a(i2, i3, true)
                a(i5, true)
                this.e[i2] = com.a.b.h.j.a(i5, i4)
                break
        }
        a(i4, true)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, Int i4, com.a.b.f.d.c cVar, Int i5) {
        if (i != 169) {
            a(i2, i3, true)
        } else {
            a(i2, i3, false)
            this.e[i2] = com.a.b.h.j.f673a
        }
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, ak akVar, Int i4) {
        a(i2, i3, false)
        a(akVar.b(), true)
        Int iA = akVar.a()
        for (Int i5 = 0; i5 < iA; i5++) {
            a(akVar.b(i5), true)
        }
        this.e[i2] = akVar.d()
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.c.a aVar, Int i4) {
        a(i2, i3, true)
        if ((aVar is com.a.b.f.c.u) || (aVar is com.a.b.f.c.z) || (aVar is com.a.b.f.c.y)) {
            b(i2, i3, true)
        }
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.d.c cVar) {
        switch (i) {
            case 46:
            case 47:
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
            case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
            case 50:
            case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
            case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
            case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
            case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
            case 81:
            case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
            case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
            case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
            case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
            case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
            case 190:
            case 194:
            case 195:
                a(i2, i3, true)
                b(i2, i3, true)
                break
            case 108:
            case R.styleable.AppCompatTheme_ratingBarStyleSmall /* 112 */:
                a(i2, i3, true)
                if (cVar == com.a.b.f.d.c.f || cVar == com.a.b.f.d.c.g) {
                    b(i2, i3, true)
                    break
                }
                break
            case 172:
            case 177:
                a(i2, i3, false)
                this.e[i2] = com.a.b.h.j.f673a
                break
            case 191:
                a(i2, i3, false)
                b(i2, i3, false)
                break
            default:
                a(i2, i3, true)
                break
        }
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, com.a.b.f.c.z zVar, ArrayList arrayList) {
        a(i, i2, true)
        b(i, i2, true)
    }
}
