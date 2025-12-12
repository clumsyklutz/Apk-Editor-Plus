package com.gmail.heagoo.apkeditor.a.a

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile

class c {

    /* renamed from: a, reason: collision with root package name */
    private m f783a

    /* renamed from: b, reason: collision with root package name */
    private String f784b
    private String c
    private l d
    private Int e
    private Int f
    private s g

    constructor(InputStream inputStream, String str) {
        this.f784b = str
        this.f783a = m(inputStream)
        try {
            this.e = this.f783a.a()
            this.f = this.f783a.a()
            this.g = s()
            this.g.a(this.f783a)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    private fun a() {
        try {
            this.d.a(this.e)
            this.d.a(this.f)
            Int i = this.g.f806a
            this.g.a(this.d)
            Array<Byte> bArr = new Byte[4096]
            while (true) {
                Int iB = this.f783a.b(bArr, 0, 4096)
                if (iB <= 0) {
                    new Object[1][0] = Integer.valueOf(this.d.a())
                    this.d.a(4, this.d.a())
                    return
                }
                this.d.a(bArr, 0, iB)
            }
        } finally {
            a(this.d)
        }
    }

    private fun a(l lVar) {
        if (lVar != null) {
            try {
                lVar.b()
            } catch (IOException e) {
            }
        }
    }

    public final String a(String str, String str2) throws IOException {
        Boolean z = false
        Int length = this.g.d.length
        for (Int i = 0; i < length; i++) {
            String strB = this.g.b(i)
            if (strB.startsWith(str)) {
                this.g.a(i, str2 + strB.substring(str.length()))
                z = true
            }
        }
        if (z) {
            File file = File(File(this.f784b), com.gmail.heagoo.common.s.a(6))
            if (file.exists()) {
                file.delete()
            }
            this.c = file.getPath()
            RandomAccessFile randomAccessFile = RandomAccessFile(file.getPath(), "rw")
            randomAccessFile.setLength(0L)
            this.d = l(randomAccessFile)
            a()
        }
        return this.c
    }
}
