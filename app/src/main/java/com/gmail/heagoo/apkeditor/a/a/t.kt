package com.gmail.heagoo.apkeditor.a.a

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class t {

    /* renamed from: a, reason: collision with root package name */
    private a.a.b.a.b f808a

    /* renamed from: b, reason: collision with root package name */
    private FileOutputStream f809b
    private u c
    private Int d
    private u e
    private Array<Byte> f
    private u g
    private Int h
    private Array<Byte> i
    private v j

    constructor(InputStream inputStream, String str) throws IOException {
        this.f808a = new a.a.b.a.b(inputStream)
        File file = File(str)
        if (file.exists()) {
            file.delete()
        }
        this.f809b = FileOutputStream(file)
        this.c = u()
        this.c.a(this.f808a)
        this.d = this.f808a.readInt()
        this.e = u()
        this.e.a(this.f808a)
        this.f = new Byte[this.e.f810a - 8]
        this.f808a.readFully(this.f)
        this.g = u()
        this.g.a(this.f808a)
        this.h = this.f808a.readInt()
    }

    private fun a(Int i) throws IOException {
        this.f809b.write(new Array<Byte>{(Byte) i, (Byte) (i >> 8), (Byte) (i >> 16), (Byte) (i >>> 24)})
    }

    public final Unit a() throws IOException {
        this.c.a(this.c.f810a + this.j.a())
        this.c.a(this.f809b)
        a(this.d)
        if (this.j != null) {
            this.j.a(this.f809b)
        } else {
            this.e.a(this.f809b)
            this.f809b.write(this.f)
        }
        this.g.a(this.f809b)
        a(this.h)
        if (this.i != null) {
            this.f809b.write(this.i)
            this.f808a.skipBytes(256)
        }
        Array<Byte> bArr = new Byte[4096]
        Int iA = this.f808a.a(bArr, 0, 4096)
        while (iA > 0) {
            this.f809b.write(bArr, 0, iA)
            iA = this.f808a.a(bArr, 0, 4096)
        }
        this.f809b.close()
    }

    public final Boolean a(String str) {
        if (str.length() > 127 || this.h != 127) {
            return false
        }
        this.i = new Byte[256]
        for (Int i = 0; i < str.length(); i++) {
            this.i[i * 2] = (Byte) str.charAt(i)
        }
        return true
    }

    public final Boolean a(String str, String str2, String str3, String str4) {
        this.j = v(this.e, this.f)
        return this.j.a(str, str2, str3, str4)
    }
}
