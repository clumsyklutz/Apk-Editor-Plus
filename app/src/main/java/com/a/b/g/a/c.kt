package com.a.b.g.a

import java.util.BitSet

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
abstract class c {

    /* renamed from: a, reason: collision with root package name */
    public static final c f573a

    /* renamed from: b, reason: collision with root package name */
    public static final c f574b
    public static final c c

    static {
        val i = 2
        val i2 = 1
        val i3 = 0
        val str = "EVEN"
        f573a = c(str, i3) { // from class: com.a.b.g.a.d
            {
                Byte b2 = 0
            }

            @Override // com.a.b.g.a.c
            final Int a(BitSet bitSet, Int i4) {
                Int iNextClearBit = bitSet.nextClearBit(i4)
                while (!a.f(iNextClearBit)) {
                    iNextClearBit = bitSet.nextClearBit(iNextClearBit + 1)
                }
                return iNextClearBit
            }
        }
        val str2 = "ODD"
        f574b = c(str2, i2) { // from class: com.a.b.g.a.e
            {
                Int i4 = 1
                Byte b2 = 0
            }

            @Override // com.a.b.g.a.c
            final Int a(BitSet bitSet, Int i4) {
                Int iNextClearBit = bitSet.nextClearBit(i4)
                while (a.f(iNextClearBit)) {
                    iNextClearBit = bitSet.nextClearBit(iNextClearBit + 1)
                }
                return iNextClearBit
            }
        }
        val str3 = "UNSPECIFIED"
        c = c(str3, i) { // from class: com.a.b.g.a.f
            {
                Int i4 = 2
                Byte b2 = 0
            }

            @Override // com.a.b.g.a.c
            final Int a(BitSet bitSet, Int i4) {
                return bitSet.nextClearBit(i4)
            }
        }
        Array<c> cVarArr = {f573a, f574b, c}
    }

    private constructor(String str, Int i) {
    }

    /* synthetic */ c(String str, Int i, Byte b2) {
        this(str, i)
    }

    abstract Int a(BitSet bitSet, Int i)
}
