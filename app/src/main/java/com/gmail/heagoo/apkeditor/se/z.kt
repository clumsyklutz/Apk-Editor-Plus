package com.gmail.heagoo.apkeditor.se

import java.io.IOException
import java.util.ArrayList
import java.util.Enumeration
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class z {
    private static final Array<String> f = {"res/drawable", "res/mipmap"}

    /* renamed from: a, reason: collision with root package name */
    List f1286a = ArrayList()

    /* renamed from: b, reason: collision with root package name */
    HashMap f1287b = HashMap()
    Map c = HashMap()
    List d = ArrayList()
    private String e

    constructor(String str) {
        this.e = str
    }

    private fun a(String str, String str2, Boolean z) {
        List arrayList = (List) this.c.get(str)
        if (arrayList == null) {
            arrayList = ArrayList()
            this.c.put(str, arrayList)
        }
        if (!z) {
            arrayList.add(y(str2, false))
        } else if (this.c.get(str + str2 + "/") == null) {
            arrayList.add(y(str2, true))
        }
    }

    private fun a(ZipFile zipFile) throws IOException {
        try {
            zipFile.close()
        } catch (IOException e) {
        }
    }

    fun a(String str) {
        Array<String> strArr = {".wav", ".mp2", ".mp3", ".ogg", ".aac", ".mpg", ".mpeg", ".mid", ".midi", ".smf", ".jet", ".rtttl", ".imy", ".xmf", ".mp4", ".m4a", ".m4v", ".3gp", ".3gpp", ".3g2", ".3gpp2", ".amr", ".awb", ".wma", ".wmv"}
        for (Int i = 0; i < 25; i++) {
            if (str.endsWith(strArr[i])) {
                return true
            }
        }
        return false
    }

    private fun b(String str) {
        Array<String> strArr = f
        for (Int i = 0; i < 2; i++) {
            if (str.startsWith(strArr[i])) {
                return true
            }
        }
        return false
    }

    public final Unit a() throws Throwable {
        ZipFile zipFile
        try {
            zipFile = ZipFile(this.e)
        } catch (Exception e) {
            e = e
            zipFile = null
        } catch (Throwable th) {
            th = th
            zipFile = null
            a(zipFile)
            throw th
        }
        try {
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                    String name = zipEntryNextElement.getName()
                    if (!zipEntryNextElement.isDirectory()) {
                        Array<String> strArrSplit = name.split("/")
                        String str = "/"
                        for (Int i = 0; i < strArrSplit.length - 1; i++) {
                            String str2 = strArrSplit[i]
                            a(str, str2, true)
                            str = str + str2 + "/"
                        }
                        a(str, strArrSplit[strArrSplit.length - 1], false)
                        if (b(name)) {
                            Int iIndexOf = name.indexOf(47, 11)
                            if (iIndexOf != -1 && name.indexOf(47, iIndexOf + 1) == -1 && (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith("bmp"))) {
                                Int iLastIndexOf = name.lastIndexOf(47)
                                String strSubstring = iLastIndexOf != -1 ? name.substring(iLastIndexOf + 1) : name
                                Int length = (name.length() - strSubstring.length()) - 1
                                String strSubstring2 = length > 0 ? name.substring(0, length) : ""
                                g gVar = (g) this.f1287b.get(strSubstring)
                                if (gVar == null) {
                                    this.f1286a.add(strSubstring)
                                    this.f1287b.put(strSubstring, g(strSubstring, strSubstring2))
                                } else {
                                    gVar.a(strSubstring2)
                                }
                            }
                        } else if (a(name)) {
                            this.d.add(name)
                        }
                    }
                }
                a(zipFile)
            } catch (Throwable th2) {
                th = th2
                a(zipFile)
                throw th
            }
        } catch (Exception e2) {
            e = e2
            e.printStackTrace()
            a(zipFile)
        }
    }

    public final String b() {
        return this.e
    }
}
