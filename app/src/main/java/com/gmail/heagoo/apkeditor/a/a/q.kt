package com.gmail.heagoo.apkeditor.a.a

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.util.Enumeration
import java.util.Map
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class q implements com.gmail.heagoo.apkeditor.a.d, Serializable {

    /* renamed from: a, reason: collision with root package name */
    private String f802a

    /* renamed from: b, reason: collision with root package name */
    private String f803b

    constructor(String str, String str2) {
        this.f802a = str
        this.f803b = str2
    }

    private fun a(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (Throwable th) {
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.a.d
    public final Unit a(Context context, String str, Map map, com.gmail.heagoo.apkeditor.a.f fVar) throws Throwable {
        ZipFile zipFile
        String strD = com.gmail.heagoo.a.c.a.d(context, "tmp")
        try {
            try {
                zipFile = ZipFile(str)
            } catch (IOException e) {
                e = e
                zipFile = null
            } catch (Throwable th) {
                th = th
                a(null)
                throw th
            }
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                    if (!zipEntryNextElement.isDirectory()) {
                        String name = zipEntryNextElement.getName()
                        if (name.endsWith(".xml") && name.startsWith("res/layout")) {
                            InputStream inputStream = zipFile.getInputStream(zipEntryNextElement)
                            String strA = c(inputStream, strD).a(this.f802a, this.f803b)
                            if (strA != null) {
                                map.put(name, strA)
                            }
                            inputStream.close()
                        }
                    }
                }
                a(zipFile)
            } catch (IOException e2) {
                e = e2
                e.printStackTrace()
                a(zipFile)
            }
        } catch (Throwable th2) {
            th = th2
            a(null)
            throw th
        }
    }
}
