package com.a.b.d.a

import androidx.core.view.InputDeviceCompat

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
abstract class j {
    public static final j A
    public static final j B
    private static j C
    private static j D
    private static j E
    private static j F
    private static j G
    private static j H

    /* renamed from: a, reason: collision with root package name */
    public static final j f445a

    /* renamed from: b, reason: collision with root package name */
    public static final j f446b
    public static final j c
    public static final j d
    public static final j e
    public static final j f
    public static final j g
    public static final j h
    public static final j i
    public static final j j
    public static final j k
    public static final j l
    public static final j m
    public static final j n
    public static final j o
    public static final j p
    public static final j q
    public static final j r
    public static final j s
    public static final j t
    public static final j u
    public static final j v
    public static final j w
    public static final j x
    public static final j y
    public static final j z

    static {
        val i2 = 4
        val i3 = 3
        val i4 = 2
        val i5 = 1
        val i6 = 0
        val str = "FORMAT_00X"
        f445a = j(str, i6) { // from class: com.a.b.d.a.k
            {
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i7, d dVar) {
                return ba(this, i7, 0, 0, 0, 0L)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(fVar.c())
            }
        }
        val str2 = "FORMAT_10X"
        f446b = j(str2, i5) { // from class: com.a.b.d.a.v
            {
                Int i7 = 1
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i7, d dVar) {
                return ba(this, j.a(i7), 0, 0, 0, j.h(i7))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(fVar.c())
            }
        }
        val str3 = "FORMAT_12X"
        c = j(str3, i4) { // from class: com.a.b.d.a.ag
            {
                Int i7 = 2
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i7, d dVar) {
                return az(this, j.a(i7), 0, 0, 0, 0L, j.c(i7), j.d(i7))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.c(), j.d(fVar.n(), fVar.o())))
            }
        }
        val str4 = "FORMAT_11N"
        d = j(str4, i3) { // from class: com.a.b.d.a.am
            {
                Int i7 = 3
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i7, d dVar) {
                return as(this, j.a(i7), 0, 0, 0, (j.d(i7) << 28) >> 28, j.c(i7))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.c(), j.d(fVar.n(), fVar.l())))
            }
        }
        val str5 = "FORMAT_11X"
        e = j(str5, i2) { // from class: com.a.b.d.a.an
            {
                Int i7 = 4
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i7, d dVar) {
                return as(this, j.a(i7), 0, 0, 0, 0L, j.h(i7))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()))
            }
        }
        val str6 = "FORMAT_10T"
        val i7 = 5
        f = j(str6, i7) { // from class: com.a.b.d.a.ao
            {
                Int i8 = 5
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i8, d dVar) {
                return ba(this, j.a(i8), 0, 0, (dVar.a() - 1) + ((Byte) j.h(i8)), 0L)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.c(eVar.a())))
            }
        }
        val str7 = "FORMAT_20T"
        val i8 = 6
        g = j(str7, i8) { // from class: com.a.b.d.a.ap
            {
                Int i9 = 6
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i9, d dVar) {
                return ba(this, j.a(i9), 0, 0, ((Short) dVar.c()) + (dVar.a() - 1), j.h(i9))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(fVar.c(), fVar.b(eVar.a()))
            }
        }
        val str8 = "FORMAT_20BC"
        val i9 = 7
        C = j(str8, i9) { // from class: com.a.b.d.a.aq
            {
                Int i10 = 7
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i10, d dVar) {
                return ba(this, j.a(i10), dVar.c(), com.a.b.d.d.f449b, 0, j.h(i10))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.k()), fVar.e())
            }
        }
        val str9 = "FORMAT_22X"
        val i10 = 8
        h = j(str9, i10) { // from class: com.a.b.d.a.ar
            {
                Int i11 = 8
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i11, d dVar) {
                return az(this, j.a(i11), 0, 0, 0, 0L, j.h(i11), dVar.c())
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()), fVar.t())
            }
        }
        val str10 = "FORMAT_21T"
        val i11 = 9
        i = j(str10, i11) { // from class: com.a.b.d.a.l
            {
                Int i12 = 9
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i12, d dVar) {
                return as(this, j.a(i12), 0, 0, (dVar.a() - 1) + ((Short) dVar.c()), 0L, j.h(i12))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()), fVar.b(eVar.a()))
            }
        }
        val str11 = "FORMAT_21S"
        val i12 = 10
        j = j(str11, i12) { // from class: com.a.b.d.a.m
            {
                Int i13 = 10
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i13, d dVar) {
                return as(this, j.a(i13), 0, 0, 0, (Short) dVar.c(), j.h(i13))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()), fVar.j())
            }
        }
        val str12 = "FORMAT_21H"
        val i13 = 11
        k = j(str12, i13) { // from class: com.a.b.d.a.n
            {
                Int i14 = 11
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i14, d dVar) {
                Int iA = j.a(i14)
                return as(this, iA, 0, 0, 0, ((Short) dVar.c()) << (iA == 21 ? (Char) 16 : '0'), j.h(i14))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                Int iB = fVar.b()
                eVar.a(j.c(iB, fVar.n()), (Short) (fVar.h() >> (iB == 21 ? (Char) 16 : '0')))
            }
        }
        val str13 = "FORMAT_21C"
        val i14 = 12
        l = j(str13, i14) { // from class: com.a.b.d.a.o
            {
                Int i15 = 12
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i15, d dVar) {
                Int iA = j.a(i15)
                return as(this, iA, dVar.c(), com.a.b.d.e.c(iA), 0, 0L, j.h(i15))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()), fVar.e())
            }
        }
        val str14 = "FORMAT_23X"
        val i15 = 13
        m = j(str14, i15) { // from class: com.a.b.d.a.p
            {
                Int i16 = 13
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i16, d dVar) {
                Int iA = j.a(i16)
                Int iH = j.h(i16)
                Int iC = dVar.c()
                return ay(this, iA, 0, 0, 0, 0L, iH, j.a(iC), j.h(iC))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()), j.c(fVar.o(), fVar.p()))
            }
        }
        val str15 = "FORMAT_22B"
        val i16 = 14
        n = j(str15, i16) { // from class: com.a.b.d.a.q
            {
                Int i17 = 14
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i17, d dVar) {
                return az(this, j.a(i17), 0, 0, 0, (Byte) j.h(r0), j.h(i17), j.a(dVar.c()))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.n()), j.c(fVar.o(), fVar.k()))
            }
        }
        val str16 = "FORMAT_22T"
        val i17 = 15
        o = j(str16, i17) { // from class: com.a.b.d.a.r
            {
                Int i18 = 15
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i18, d dVar) {
                return az(this, j.a(i18), 0, 0, (dVar.a() - 1) + ((Short) dVar.c()), 0L, j.c(i18), j.d(i18))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), j.d(fVar.n(), fVar.o())), fVar.b(eVar.a()))
            }
        }
        val str17 = "FORMAT_22S"
        val i18 = 16
        p = j(str17, i18) { // from class: com.a.b.d.a.s
            {
                Int i19 = 16
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i19, d dVar) {
                return az(this, j.a(i19), 0, 0, 0, (Short) dVar.c(), j.c(i19), j.d(i19))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), j.d(fVar.n(), fVar.o())), fVar.j())
            }
        }
        val str18 = "FORMAT_22C"
        val i19 = 17
        q = j(str18, i19) { // from class: com.a.b.d.a.t
            {
                Int i20 = 17
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i20, d dVar) {
                Int iA = j.a(i20)
                return az(this, iA, dVar.c(), com.a.b.d.e.c(iA), 0, 0L, j.c(i20), j.d(i20))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), j.d(fVar.n(), fVar.o())), fVar.e())
            }
        }
        val str19 = "FORMAT_22CS"
        val i20 = 18
        D = j(str19, i20) { // from class: com.a.b.d.a.u
            {
                Int i21 = 18
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i21, d dVar) {
                return az(this, j.a(i21), dVar.c(), com.a.b.d.d.g, 0, 0L, j.c(i21), j.d(i21))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), j.d(fVar.n(), fVar.o())), fVar.e())
            }
        }
        val str20 = "FORMAT_30T"
        val i21 = 19
        r = j(str20, i21) { // from class: com.a.b.d.a.w
            {
                Int i22 = 19
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i22, d dVar) {
                return ba(this, j.a(i22), 0, 0, dVar.d() + (dVar.a() - 1), j.h(i22))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                Int iA = fVar.a(eVar.a())
                eVar.a(fVar.c(), j.e(iA), j.f(iA))
            }
        }
        val str21 = "FORMAT_32X"
        val i22 = 20
        s = j(str21, i22) { // from class: com.a.b.d.a.x
            {
                Int i23 = 20
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i23, d dVar) {
                return az(this, j.a(i23), 0, 0, 0, j.h(i23), dVar.c(), dVar.c())
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(fVar.c(), fVar.s(), fVar.t())
            }
        }
        val str22 = "FORMAT_31I"
        val i23 = 21
        t = j(str22, i23) { // from class: com.a.b.d.a.y
            {
                Int i24 = 21
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i24, d dVar) {
                return as(this, j.a(i24), 0, 0, 0, dVar.d(), j.h(i24))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                Int i24 = fVar.i()
                eVar.a(j.c(fVar.b(), fVar.n()), j.e(i24), j.f(i24))
            }
        }
        val str23 = "FORMAT_31T"
        val i24 = 22
        u = j(str23, i24) { // from class: com.a.b.d.a.z
            {
                Int i25 = 22
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i25, d dVar) {
                Int iA = dVar.a() - 1
                Int iA2 = j.a(i25)
                Int iH = j.h(i25)
                Int iD = iA + dVar.d()
                switch (iA2) {
                    case 43:
                    case 44:
                        dVar.a(iD, iA)
                        break
                }
                return as(this, iA2, 0, 0, iD, 0L, iH)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                Int iA = fVar.a(eVar.a())
                eVar.a(j.c(fVar.b(), fVar.n()), j.e(iA), j.f(iA))
            }
        }
        val str24 = "FORMAT_31C"
        val i25 = 23
        v = j(str24, i25) { // from class: com.a.b.d.a.aa
            {
                Int i26 = 23
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i26, d dVar) {
                Int iA = j.a(i26)
                return as(this, iA, dVar.d(), com.a.b.d.e.c(iA), 0, 0L, j.h(i26))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                Int iD = fVar.d()
                eVar.a(j.c(fVar.b(), fVar.n()), j.e(iD), j.f(iD))
            }
        }
        val str25 = "FORMAT_35C"
        val i26 = 24
        w = j(str25, i26) { // from class: com.a.b.d.a.ab
            {
                Int i27 = 24
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i27, d dVar) {
                return j.a(this, i27, dVar)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                j.b(fVar, eVar)
            }
        }
        val str26 = "FORMAT_35MS"
        val i27 = 25
        E = j(str26, i27) { // from class: com.a.b.d.a.ac
            {
                Int i28 = 25
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i28, d dVar) {
                return j.a(this, i28, dVar)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                j.b(fVar, eVar)
            }
        }
        val str27 = "FORMAT_35MI"
        val i28 = 26
        F = j(str27, i28) { // from class: com.a.b.d.a.ad
            {
                Int i29 = 26
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i29, d dVar) {
                return j.a(this, i29, dVar)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                j.b(fVar, eVar)
            }
        }
        val str28 = "FORMAT_3RC"
        val i29 = 27
        x = j(str28, i29) { // from class: com.a.b.d.a.ae
            {
                Int i30 = 27
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i30, d dVar) {
                return j.b(this, i30, dVar)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.m()), fVar.e(), fVar.s())
            }
        }
        val str29 = "FORMAT_3RMS"
        val i30 = 28
        G = j(str29, i30) { // from class: com.a.b.d.a.af
            {
                Int i31 = 28
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i31, d dVar) {
                return j.b(this, i31, dVar)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.m()), fVar.e(), fVar.s())
            }
        }
        val str30 = "FORMAT_3RMI"
        val i31 = 29
        H = j(str30, i31) { // from class: com.a.b.d.a.ah
            {
                Int i32 = 29
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i32, d dVar) {
                return j.b(this, i32, dVar)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                eVar.a(j.c(fVar.b(), fVar.m()), fVar.e(), fVar.s())
            }
        }
        val str31 = "FORMAT_51L"
        val i32 = 30
        y = j(str31, i32) { // from class: com.a.b.d.a.ai
            {
                Int i33 = 30
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i33, d dVar) {
                return as(this, j.a(i33), 0, 0, 0, dVar.e(), j.h(i33))
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                Long jH = fVar.h()
                eVar.a(j.c(fVar.b(), fVar.n()), j.a(jH), j.b(jH), j.c(jH), j.d(jH))
            }
        }
        val str32 = "FORMAT_PACKED_SWITCH_PAYLOAD"
        val i33 = 31
        z = j(str32, i33) { // from class: com.a.b.d.a.aj
            {
                Int i34 = 31
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i34, d dVar) {
                Int iB = dVar.b() - 1
                Int iC = dVar.c()
                Int iD = dVar.d()
                Array<Int> iArr = new Int[iC]
                for (Int i35 = 0; i35 < iC; i35++) {
                    iArr[i35] = dVar.d() + iB
                }
                return at(this, i34, iD, iArr)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                at atVar = (at) fVar
                Array<Int> iArrV = atVar.v()
                Int iB = eVar.b()
                eVar.a(atVar.c())
                eVar.a(j.g(iArrV.length))
                eVar.a_(atVar.u())
                for (Int i34 : iArrV) {
                    eVar.a_(i34 - iB)
                }
            }
        }
        val str33 = "FORMAT_SPARSE_SWITCH_PAYLOAD"
        val i34 = 32
        A = j(str33, i34) { // from class: com.a.b.d.a.ak
            {
                Int i35 = 32
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i35, d dVar) {
                Int iB = dVar.b() - 1
                Int iC = dVar.c()
                Array<Int> iArr = new Int[iC]
                Array<Int> iArr2 = new Int[iC]
                for (Int i36 = 0; i36 < iC; i36++) {
                    iArr[i36] = dVar.d()
                }
                for (Int i37 = 0; i37 < iC; i37++) {
                    iArr2[i37] = dVar.d() + iB
                }
                return ax(this, i35, iArr, iArr2)
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                ax axVar = (ax) fVar
                Array<Int> iArrU = axVar.u()
                Array<Int> iArrV = axVar.v()
                Int iB = eVar.b()
                eVar.a(axVar.c())
                eVar.a(j.g(iArrV.length))
                for (Int i35 : iArrU) {
                    eVar.a_(i35)
                }
                for (Int i36 : iArrV) {
                    eVar.a_(i36 - iB)
                }
            }
        }
        val str34 = "FORMAT_FILL_ARRAY_DATA_PAYLOAD"
        val i35 = 33
        B = j(str34, i35) { // from class: com.a.b.d.a.al
            {
                Int i36 = 33
                Byte b2 = 0
            }

            @Override // com.a.b.d.a.j
            public final f a(Int i36, d dVar) {
                Int i37 = 0
                Int iC = dVar.c()
                Int iD = dVar.d()
                switch (iC) {
                    case 1:
                        Array<Byte> bArr = new Byte[iD]
                        Int iC2 = 0
                        Int i38 = 0
                        Boolean z2 = true
                        while (i38 < iD) {
                            if (z2) {
                                iC2 = dVar.c()
                            }
                            bArr[i38] = (Byte) iC2
                            Int i39 = iC2 >> 8
                            i38++
                            z2 = !z2
                            iC2 = i39
                        }
                        return g((j) this, i36, bArr)
                    case 2:
                        Array<Short> sArr = new Short[iD]
                        while (i37 < iD) {
                            sArr[i37] = (Short) dVar.c()
                            i37++
                        }
                        return g((j) this, i36, sArr)
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    default:
                        throw new com.a.a.s("bogus element_width: " + com.gmail.heagoo.a.c.a.v(iC))
                    case 4:
                        Array<Int> iArr = new Int[iD]
                        while (i37 < iD) {
                            iArr[i37] = dVar.d()
                            i37++
                        }
                        return g((j) this, i36, iArr)
                    case 8:
                        Array<Long> jArr = new Long[iD]
                        while (i37 < iD) {
                            jArr[i37] = dVar.e()
                            i37++
                        }
                        return g(this, i36, jArr)
                }
            }

            @Override // com.a.b.d.a.j
            public final Unit a(f fVar, e eVar) {
                g gVar = (g) fVar
                Short sU = gVar.u()
                Object objW = gVar.w()
                eVar.a(gVar.c())
                eVar.a(sU)
                eVar.a_(gVar.v())
                switch (sU) {
                    case 1:
                        eVar.a((Array<Byte>) objW)
                        return
                    case 2:
                        eVar.a((Array<Short>) objW)
                        return
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    default:
                        throw new com.a.a.s("bogus element_width: " + com.gmail.heagoo.a.c.a.v(sU))
                    case 4:
                        eVar.a((Array<Int>) objW)
                        return
                    case 8:
                        eVar.a((Array<Long>) objW)
                        return
                }
            }
        }
        Array<j> jVarArr = {f445a, f446b, c, d, e, f, g, C, h, i, j, k, l, m, n, o, p, q, D, r, s, t, u, v, w, E, F, x, G, H, y, z, A, B}
    }

    private constructor(String str, Int i2) {
    }

    /* synthetic */ j(String str, Int i2, Byte b2) {
        this(str, i2)
    }

    static /* synthetic */ Int a(Int i2) {
        return i2 & 255
    }

    static /* synthetic */ f a(j jVar, Int i2, d dVar) {
        Int i3 = i2 & 255
        Int i4 = (i2 >> 8) & 15
        Int i5 = (i2 >> 12) & 15
        Int iC = dVar.c()
        Int iC2 = dVar.c()
        Int i6 = iC2 & 15
        Int i7 = (iC2 >> 4) & 15
        Int i8 = (iC2 >> 8) & 15
        Int i9 = (iC2 >> 12) & 15
        Int iC3 = com.a.b.d.e.c(i3)
        switch (i5) {
            case 0:
                return ba(jVar, i3, iC, iC3, 0, 0L)
            case 1:
                return as(jVar, i3, iC, iC3, 0, 0L, i6)
            case 2:
                return az(jVar, i3, iC, iC3, 0, 0L, i6, i7)
            case 3:
                return ay(jVar, i3, iC, iC3, 0, 0L, i6, i7, i8)
            case 4:
                return i(jVar, i3, iC, iC3, 0, 0L, i6, i7, i8, i9)
            case 5:
                return h(jVar, i3, iC, iC3, 0, 0L, i6, i7, i8, i9, i4)
            default:
                throw new com.a.a.s("bogus registerCount: " + com.gmail.heagoo.a.c.a.y(i5))
        }
    }

    static /* synthetic */ Short a(Long j2) {
        return (Short) j2
    }

    static /* synthetic */ f b(j jVar, Int i2, d dVar) {
        Int i3 = i2 & 255
        Int iH = h(i2)
        return au(jVar, i3, dVar.c(), com.a.b.d.e.c(i3), 0, 0L, dVar.c(), iH)
    }

    static /* synthetic */ Short b(Long j2) {
        return (Short) (j2 >> 16)
    }

    static /* synthetic */ Unit b(f fVar, e eVar) {
        Short sC = c(fVar.b(), d(fVar.r(), fVar.m()))
        Short sE = fVar.e()
        Int iN = fVar.n()
        Int iO = fVar.o()
        Int iP = fVar.p()
        Int iQ = fVar.q()
        if ((iN & (-16)) != 0) {
            throw IllegalArgumentException("bogus nibble0")
        }
        if ((iO & (-16)) != 0) {
            throw IllegalArgumentException("bogus nibble1")
        }
        if ((iP & (-16)) != 0) {
            throw IllegalArgumentException("bogus nibble2")
        }
        if ((iQ & (-16)) != 0) {
            throw IllegalArgumentException("bogus nibble3")
        }
        eVar.a(sC, sE, (Short) (iN | (iO << 4) | (iP << 8) | (iQ << 12)))
    }

    static /* synthetic */ Int c(Int i2) {
        return (i2 >> 8) & 15
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c(Int i2, Int i3) {
        if ((i2 & InputDeviceCompat.SOURCE_ANY) != 0) {
            throw IllegalArgumentException("bogus lowByte")
        }
        if ((i3 & InputDeviceCompat.SOURCE_ANY) != 0) {
            throw IllegalArgumentException("bogus highByte")
        }
        return (Short) ((i3 << 8) | i2)
    }

    static /* synthetic */ Short c(Long j2) {
        return (Short) (j2 >> 32)
    }

    static /* synthetic */ Int d(Int i2) {
        return (i2 >> 12) & 15
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun d(Int i2, Int i3) {
        if ((i2 & (-16)) != 0) {
            throw IllegalArgumentException("bogus lowNibble")
        }
        if ((i3 & (-16)) != 0) {
            throw IllegalArgumentException("bogus highNibble")
        }
        return (i3 << 4) | i2
    }

    static /* synthetic */ Short d(Long j2) {
        return (Short) (j2 >> 48)
    }

    static /* synthetic */ Short e(Int i2) {
        return (Short) i2
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [Int, Short] */
    static /* synthetic */ Short f(Int i2) {
        return i2 >> 16
    }

    static /* synthetic */ Short g(Int i2) {
        if (((-65536) & i2) != 0) {
            throw IllegalArgumentException("bogus unsigned code unit")
        }
        return (Short) i2
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun h(Int i2) {
        return (i2 >> 8) & 255
    }

    public abstract f a(Int i2, d dVar)

    public abstract Unit a(f fVar, e eVar)
}
