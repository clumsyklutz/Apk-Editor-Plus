package com.a.b.a.d

import jadx.core.deobf.Deobfuscator
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Iterator
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class d {

    /* renamed from: a, reason: collision with root package name */
    public static val f236a = e()

    /* renamed from: b, reason: collision with root package name */
    private final String f237b
    private final h c
    private val d = false
    private i e

    constructor(String str, Boolean z, i iVar, h hVar) {
        this.f237b = str
        this.c = hVar
        this.e = iVar
    }

    private fun a(File file) throws IOException {
        Boolean zA
        ZipFile zipFile = ZipFile(file)
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream(40000)
        Array<Byte> bArr = new Byte[20000]
        ArrayList list = Collections.list(zipFile.entries())
        if (this.d) {
            Collections.sort(list, g(this))
        }
        this.c.a(file)
        Iterator it = list.iterator()
        Boolean z = false
        while (it.hasNext()) {
            ZipEntry zipEntry = (ZipEntry) it.next()
            if (!zipEntry.isDirectory()) {
                String name = zipEntry.getName()
                if (this.e.a(name)) {
                    InputStream inputStream = zipFile.getInputStream(zipEntry)
                    byteArrayOutputStream.reset()
                    while (true) {
                        Int i = inputStream.read(bArr)
                        if (i < 0) {
                            break
                        }
                        byteArrayOutputStream.write(bArr, 0, i)
                    }
                    inputStream.close()
                    zA = this.c.a(name, zipEntry.getTime(), byteArrayOutputStream.toByteArray()) | z
                } else {
                    zA = z
                }
                z = zA
            }
        }
        zipFile.close()
        return z
    }

    private fun a(File file, Boolean z) {
        try {
            if (!file.isDirectory()) {
                String path = file.getPath()
                if (path.endsWith(".zip") || path.endsWith(".jar") || path.endsWith(".apk")) {
                    return a(file)
                }
                if (!this.e.a(path)) {
                    return false
                }
                return this.c.a(path, file.lastModified(), com.gmail.heagoo.a.c.a.b(file))
            }
            if (z) {
                file = File(file, Deobfuscator.CLASS_NAME_SEPARATOR)
            }
            Array<File> fileArrListFiles = file.listFiles()
            Int length = fileArrListFiles.length
            if (this.d) {
                Arrays.sort(fileArrListFiles, f(this))
            }
            Int i = 0
            Boolean z2 = false
            while (i < length) {
                Boolean zA = a(fileArrListFiles[i], false) | z2
                i++
                z2 = zA
            }
            return z2
        } catch (Exception e) {
            this.c.a(e)
            return false
        }
    }

    public final Boolean a() {
        return a(File(this.f237b), true)
    }
}
