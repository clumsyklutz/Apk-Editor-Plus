package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Enumeration
import java.util.Iterator
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

abstract class g {

    /* renamed from: a, reason: collision with root package name */
    protected Int f995a

    /* renamed from: b, reason: collision with root package name */
    private String f996b

    fun a(b bVar, String str) {
        Int i
        Int i2 = 0
        ArrayList arrayList = ArrayList()
        Int iIndexOf = str.indexOf("${", 0)
        while (iIndexOf != -1) {
            Int i3 = iIndexOf + 2
            Int iIndexOf2 = str.indexOf("}", i3)
            if (iIndexOf2 == -1) {
                break
            }
            String strA = bVar.a(str.substring(i3, iIndexOf2))
            if (strA != null) {
                arrayList.add(h(i3 - 2, iIndexOf2 + 1, strA))
            }
            iIndexOf = str.indexOf("${", iIndexOf2)
        }
        if (arrayList.isEmpty()) {
            return null
        }
        StringBuilder sb = StringBuilder()
        Iterator it = arrayList.iterator()
        while (true) {
            i = i2
            if (!it.hasNext()) {
                break
            }
            h hVar = (h) it.next()
            Int i4 = hVar.f997a
            if (i4 > i) {
                sb.append(str.substring(i, i4))
            }
            sb.append(hVar.c)
            i2 = hVar.f998b
        }
        if (i < str.length()) {
            sb.append(str.substring(i))
        }
        return sb.toString()
    }

    static String a(BufferedReader bufferedReader, List list, Boolean z, List list2) throws IOException {
        String line = bufferedReader.readLine()
        while (line != null) {
            if (z) {
                line = line.trim()
            }
            if (list2.contains(line)) {
                break
            }
            list.add(line)
            line = bufferedReader.readLine()
        }
        return line
    }

    protected static Unit a(b bVar, List list) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                return
            }
            String strA = a(bVar, (String) list.get(i2))
            if (strA != null) {
                list.set(i2, strA)
            }
            i = i2 + 1
        }
    }

    protected static Unit a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    protected static Unit a(ZipFile zipFile) throws IOException {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, ZipEntry zipEntry, String str, b bVar) throws IOException {
        InputStream inputStream
        Boolean z = false
        String str2 = str + "/" + zipEntry.getName()
        String strD = d(str2)
        while (!apkInfoActivity.j().e(strD)) {
            strD = d(strD)
        }
        Array<String> strArrSplit = str2.substring(strD.length() + 1).split("/")
        if (strArrSplit.length > 1) {
            String str3 = strD
            for (Int i = 0; i < strArrSplit.length - 1; i++) {
                try {
                    apkInfoActivity.j().a(str3, strArrSplit[i], false)
                    str3 = str3 + "/" + strArrSplit[i]
                } catch (Exception e) {
                    bVar.a(R.string.failed_create_dir, e.getMessage())
                }
            }
            inputStream = null
            try {
                try {
                    inputStream = zipFile.getInputStream(zipEntry)
                    apkInfoActivity.j().a(str2, inputStream)
                    a(inputStream)
                    z = true
                } catch (Exception e2) {
                    bVar.a(R.string.general_error, e2.getMessage())
                    a(inputStream)
                }
            } catch (Throwable th) {
                a(inputStream)
                throw th
            }
        } else {
            inputStream = null
            inputStream = zipFile.getInputStream(zipEntry)
            apkInfoActivity.j().a(str2, inputStream)
            a(inputStream)
            z = true
        }
        return z
    }

    static Boolean c(String str) {
        Int iLastIndexOf
        if (str == null || (iLastIndexOf = str.lastIndexOf(47)) == -1) {
            return false
        }
        String strSubstring = str.substring(0, iLastIndexOf)
        return "smali".equals(strSubstring) || strSubstring.startsWith("smali_")
    }

    private fun d(String str) {
        Int iLastIndexOf = str.lastIndexOf("/")
        if (iLastIndexOf > 0) {
            return str.substring(0, iLastIndexOf)
        }
        return null
    }

    public abstract String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar)

    final String a(String str) throws IOException {
        File file = File(str)
        StringBuilder sb = StringBuilder(((Int) file.length()) + 32)
        BufferedReader bufferedReader = BufferedReader(InputStreamReader(FileInputStream(file)))
        try {
            String line = bufferedReader.readLine()
            if (line != null) {
                sb.append(line)
            }
            while (true) {
                String line2 = bufferedReader.readLine()
                if (line2 == null) {
                    return sb.toString()
                }
                sb.append("\n")
                sb.append(line2)
            }
        } finally {
            a(bufferedReader)
        }
    }

    final Unit a(ApkInfoActivity apkInfoActivity, String str, a aVar, b bVar) throws Throwable {
        ZipFile zipFile
        String strI = apkInfoActivity.i()
        try {
            zipFile = ZipFile(str)
        } catch (Throwable th) {
            th = th
            zipFile = null
        }
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                if (!zipEntryNextElement.isDirectory()) {
                    if (!(aVar != null ? aVar.a(apkInfoActivity, zipFile, zipEntryNextElement) : false)) {
                        a(apkInfoActivity, zipFile, zipEntryNextElement, strI, bVar)
                    }
                }
            }
            zipFile.close()
        } catch (Throwable th2) {
            th = th2
            if (zipFile != null) {
                try {
                    zipFile.close()
                } catch (IOException e) {
                }
            }
            throw th
        }
    }

    public abstract Unit a(c cVar, b bVar)

    public abstract Boolean a()

    public abstract Boolean a(b bVar)

    final Boolean a(String str, c cVar) {
        if (!"NAME:".equals(str)) {
            return false
        }
        this.f996b = cVar.readLine()
        if (this.f996b != null) {
            this.f996b = this.f996b.trim()
        }
        return true
    }

    public final String b() {
        return this.f996b
    }

    final List b(String str) throws IOException {
        ArrayList arrayList = ArrayList()
        BufferedReader bufferedReader = BufferedReader(InputStreamReader(FileInputStream(str)))
        while (true) {
            try {
                String line = bufferedReader.readLine()
                if (line == null) {
                    return arrayList
                }
                if (!"".equals(line.trim())) {
                    arrayList.add(line)
                }
            } finally {
                a(bufferedReader)
            }
        }
    }
}
