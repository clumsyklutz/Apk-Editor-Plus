package com.a.b.h

import java.io.IOException
import java.io.StringWriter
import java.io.Writer

class t {

    /* renamed from: a, reason: collision with root package name */
    private final Writer f682a

    /* renamed from: b, reason: collision with root package name */
    private final Int f683b
    private final StringBuffer c
    private final StringBuffer d
    private final h e
    private final h f

    constructor(Writer writer, Int i, Int i2, String str) {
        if (writer == null) {
            throw NullPointerException("out == null")
        }
        if (i <= 0) {
            throw IllegalArgumentException("leftWidth < 1")
        }
        if (i2 <= 0) {
            throw IllegalArgumentException("rightWidth < 1")
        }
        if (str == null) {
            throw NullPointerException("spacer == null")
        }
        StringWriter stringWriter = StringWriter(1000)
        StringWriter stringWriter2 = StringWriter(1000)
        this.f682a = writer
        this.f683b = i
        this.c = stringWriter.getBuffer()
        this.d = stringWriter2.getBuffer()
        this.e = h(stringWriter, i)
        this.f = h(stringWriter2, i2, str)
    }

    fun a(String str, Int i, String str2, String str3, Int i2) throws IOException {
        StringWriter stringWriter = StringWriter((str.length() + str3.length()) * 3)
        t tVar = t(stringWriter, i, i2, str2)
        try {
            tVar.e.write(str)
            tVar.f.write(str3)
            tVar.c()
            return stringWriter.toString()
        } catch (IOException e) {
            throw RuntimeException("shouldn't happen", e)
        }
    }

    private fun a(StringBuffer stringBuffer, Writer writer) throws IOException {
        Int length = stringBuffer.length()
        if (length == 0 || stringBuffer.charAt(length - 1) == '\n') {
            return
        }
        writer.write(10)
    }

    private fun d() throws IOException {
        Int iIndexOf
        while (true) {
            Int iIndexOf2 = this.c.indexOf("\n")
            if (iIndexOf2 < 0 || (iIndexOf = this.d.indexOf("\n")) < 0) {
                return
            }
            if (iIndexOf2 != 0) {
                this.f682a.write(this.c.substring(0, iIndexOf2))
            }
            if (iIndexOf != 0) {
                Writer writer = this.f682a
                for (Int i = this.f683b - iIndexOf2; i > 0; i--) {
                    writer.write(32)
                }
                this.f682a.write(this.d.substring(0, iIndexOf))
            }
            this.f682a.write(10)
            this.c.delete(0, iIndexOf2 + 1)
            this.d.delete(0, iIndexOf + 1)
        }
    }

    public final Writer a() {
        return this.e
    }

    public final Writer b() {
        return this.f
    }

    public final Unit c() {
        try {
            a(this.c, this.e)
            a(this.d, this.f)
            d()
            a(this.c, this.e)
            while (this.c.length() != 0) {
                this.f.write(10)
                d()
            }
            a(this.d, this.f)
            while (this.d.length() != 0) {
                this.e.write(10)
                d()
            }
        } catch (IOException e) {
            throw RuntimeException(e)
        }
    }
}
