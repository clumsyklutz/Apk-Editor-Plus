package com.a.a.a

import java.io.PrintStream
import java.io.PrintWriter

class d extends RuntimeException {

    /* renamed from: a, reason: collision with root package name */
    private StringBuffer f112a

    constructor(String str) {
        this(str, null)
    }

    constructor(String str, Throwable th) {
        super(str == null ? th != null ? th.getMessage() : null : str, th)
        if (!(th is d)) {
            this.f112a = StringBuffer(200)
            return
        }
        String string = ((d) th).f112a.toString()
        this.f112a = StringBuffer(string.length() + 200)
        this.f112a.append(string)
    }

    constructor(Throwable th) {
        this(null, th)
    }

    fun a(Throwable th, String str) {
        d dVar = th is d ? (d) th : d(th)
        dVar.a(str)
        return dVar
    }

    public final String a() {
        return this.f112a.toString()
    }

    public final Unit a(PrintStream printStream) {
        printStream.println(getMessage())
        printStream.print(this.f112a)
    }

    public final Unit a(String str) {
        if (str == null) {
            throw NullPointerException("str == null")
        }
        this.f112a.append(str)
        if (str.endsWith("\n")) {
            return
        }
        this.f112a.append('\n')
    }

    @Override // java.lang.Throwable
    fun printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream)
        printStream.println(this.f112a)
    }

    @Override // java.lang.Throwable
    fun printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter)
        printWriter.println(this.f112a)
    }
}
