package com.gmail.heagoo.apkeditor.util

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.util.HashMap
import java.util.Map
import java.util.zip.ZipFile

class c implements com.gmail.heagoo.apkeditor.a.d {

    /* renamed from: a, reason: collision with root package name */
    private String f1307a

    /* renamed from: b, reason: collision with root package name */
    private Map f1308b

    constructor(String str, Map map) {
        this.f1307a = str.endsWith("/") ? str : str + "/"
        this.f1308b = map
    }

    private fun a(com.gmail.heagoo.apkeditor.a.a.l lVar) {
        if (lVar != null) {
            try {
                lVar.b()
            } catch (IOException e) {
            }
        }
    }

    private fun a(ZipFile zipFile) throws IOException {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(InputStream inputStream, String str, Map map) throws Throwable {
        com.gmail.heagoo.apkeditor.a.a.l lVar
        HashMap map2 = HashMap()
        for (Map.Entry entry : map.entrySet()) {
            map2.put(entry.getValue(), entry.getKey())
        }
        com.gmail.heagoo.apkeditor.a.a.m mVar = new com.gmail.heagoo.apkeditor.a.a.m(inputStream)
        try {
            RandomAccessFile randomAccessFile = RandomAccessFile(str, "rw")
            randomAccessFile.setLength(0L)
            lVar = new com.gmail.heagoo.apkeditor.a.a.l(randomAccessFile)
        } catch (Throwable th) {
            th = th
            lVar = null
        }
        try {
            Int iA = mVar.a()
            Int iA2 = mVar.a()
            lVar.a(iA)
            lVar.a(iA2)
            com.gmail.heagoo.apkeditor.a.a.s sVar = new com.gmail.heagoo.apkeditor.a.a.s()
            sVar.a(mVar)
            Int iC = sVar.c()
            for (Int i = 0; i < iC; i++) {
                String str2 = (String) map2.get(sVar.b(i))
                if (str2 != null) {
                    sVar.a(i, str2)
                }
            }
            Int i2 = sVar.f806a
            sVar.a(lVar)
            Int i3 = sVar.f806a
            Array<Byte> bArr = new Byte[4096]
            while (true) {
                Int iB = mVar.b(bArr, 0, 4096)
                if (iB == -1) {
                    lVar.a(4, lVar.a())
                    a(lVar)
                    return false
                }
                lVar.a(bArr, 0, iB)
            }
        } catch (Throwable th2) {
            th = th2
            a(lVar)
            throw th
        }
    }

    @Override // com.gmail.heagoo.apkeditor.a.d
    public final Unit a(Context context, String str, Map map, com.gmail.heagoo.apkeditor.a.f fVar) throws Throwable {
        ZipFile zipFile
        ZipFile zipFile2 = null
        try {
            zipFile = ZipFile(str)
        } catch (Throwable th) {
            th = th
        }
        try {
            for (Map.Entry entry : this.f1308b.entrySet()) {
                String str2 = (String) entry.getKey()
                Map map2 = (Map) entry.getValue()
                if (str2.startsWith(this.f1307a)) {
                    String strSubstring = str2.substring(this.f1307a.length())
                    InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(strSubstring))
                    String str3 = str2 + ".bin"
                    a(inputStream, str3, map2)
                    map.put(strSubstring, str3)
                    inputStream.close()
                }
            }
            zipFile.close()
            a(zipFile)
        } catch (Throwable th2) {
            th = th2
            zipFile2 = zipFile
            a(zipFile2)
            throw th
        }
    }
}
