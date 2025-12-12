package a.c

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Enumeration
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class i extends a {
    private ZipFile c
    private String d

    constructor(File file) {
        this(file, "")
    }

    private constructor(File file, String str) throws e {
        try {
            this.c = ZipFile(file)
            this.d = str
        } catch (IOException e) {
            throw e(e)
        }
    }

    private constructor(ZipFile zipFile, String str) {
        this.c = zipFile
        this.d = str
    }

    private fun c() {
        String strSubstring
        this.f61a = LinkedHashSet()
        this.f62b = LinkedHashMap()
        Int length = this.d.length()
        Enumeration<? extends ZipEntry> enumerationEntries = this.c.entries()
        while (enumerationEntries.hasMoreElements()) {
            ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
            String name = zipEntryNextElement.getName()
            if (!name.equals(this.d) && name.startsWith(this.d)) {
                String strSubstring2 = name.substring(length)
                Int iIndexOf = strSubstring2.indexOf(47)
                if (iIndexOf != -1) {
                    strSubstring = strSubstring2.substring(0, iIndexOf)
                } else if (zipEntryNextElement.isDirectory()) {
                    strSubstring = strSubstring2
                } else {
                    this.f61a.add(strSubstring2)
                }
                if (!this.f62b.containsKey(strSubstring)) {
                    this.f62b.put(strSubstring, i(this.c, this.d + strSubstring + '/'))
                }
            }
        }
    }

    private fun getZipFileEntry(String str) {
        return this.c.getEntry(str)
    }

    @Override // a.c.a
    protected final Unit a() {
        c()
    }

    @Override // a.c.a
    protected final Unit b() {
        c()
    }

    @Override // a.c.a
    protected final InputStream f(String str) throws h {
        try {
            return this.c.getInputStream(ZipEntry(this.d + str))
        } catch (IOException e) {
            throw h(str, e)
        }
    }

    @Override // a.c.a
    protected final OutputStream g(String str) {
        throw UnsupportedOperationException()
    }

    @Override // a.c.d
    fun getCompressionLevel(String str) {
        return getZipFileEntry(str).getMethod()
    }

    @Override // a.c.d
    fun getSize(String str) {
        return getZipFileEntry(str).getSize()
    }

    @Override // a.c.a
    protected final a h(String str) {
        throw UnsupportedOperationException()
    }

    @Override // a.c.a
    protected final Unit i(String str) {
        throw UnsupportedOperationException()
    }
}
