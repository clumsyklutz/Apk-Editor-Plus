package com.a.b.c.a

import com.a.b.c.b.j
import com.a.b.f.b.x
import java.io.PrintStream

class c {

    /* renamed from: a, reason: collision with root package name */
    private static Int f266a = 0

    /* renamed from: b, reason: collision with root package name */
    private static Int f267b = 0
    private static Int c = 0
    private static Int d = 0
    private static Int e = 0
    private static Int f = 0
    private static Int g = 0

    fun a(Int i) {
        g += i
    }

    fun a(j jVar, j jVar2) {
        e += jVar2.f().e() - jVar.f().e()
        d += jVar2.f().f() - jVar.f().f()
        f += jVar2.f().e()
    }

    fun a(x xVar, x xVar2) {
        Int iG = xVar.a().g()
        Int iE = xVar.a().e()
        Int iG2 = xVar2.a().g()
        f267b = (iG2 - iG) + f267b
        f266a += xVar2.a().e() - iE
        c += iG2
    }

    fun a(PrintStream printStream) {
        printStream.printf("Optimizer Delta Rop Insns: %d total: %d (%.2f%%) Delta Registers: %d\n", Integer.valueOf(f267b), Integer.valueOf(c), Double.valueOf((f267b / (c + Math.abs(f267b))) * 100.0d), Integer.valueOf(f266a))
        printStream.printf("Optimizer Delta Dex Insns: Insns: %d total: %d (%.2f%%) Delta Registers: %d\n", Integer.valueOf(e), Integer.valueOf(f), Double.valueOf((e / (f + Math.abs(e))) * 100.0d), Integer.valueOf(d))
        printStream.printf("Original bytecode Byte count: %d\n", Integer.valueOf(g))
    }
}
