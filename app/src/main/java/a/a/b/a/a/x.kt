package a.a.b.a.a

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.ArrayList
import java.util.Enumeration
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class x {

    /* renamed from: a, reason: collision with root package name */
    private final a.a.b.a.e f22a

    constructor(a.a.b.a.e eVar) {
        this.f22a = eVar
    }

    private fun a(String str) {
        Int i = 0
        Int i2 = 0
        while (true) {
            Int iIndexOf = str.indexOf(47, i)
            if (iIndexOf == -1) {
                return i2
            }
            i2++
            i = iIndexOf + 1
        }
    }

    private fun a(Int i, String str, Boolean z) {
        return s(this.f22a, i, str, z)
    }

    fun a(Int i) {
        Array<Char> cArr = new Char[8]
        for (Int i2 = 0; i2 < 8; i2++) {
            cArr[7 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    private fun a(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (Throwable th) {
            }
        }
    }

    private fun a(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close()
            } catch (Throwable th) {
            }
        }
    }

    fun a(String str, String str2) throws Throwable {
        BufferedInputStream bufferedInputStream
        ZipFile zipFile
        String str3
        BufferedOutputStream bufferedOutputStream = null
        try {
            ZipFile zipFile2 = ZipFile(str)
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile2.entries()
                Array<Byte> bArr = new Byte[4096]
                BufferedInputStream bufferedInputStream2 = null
                while (enumerationEntries.hasMoreElements()) {
                    try {
                        ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                        if (zipEntryNextElement.isDirectory()) {
                            if (str2.endsWith("/")) {
                                str3 = str2
                            } else {
                                str3 = (str2 + "/") + zipEntryNextElement.getName()
                            }
                            File(str3).mkdirs()
                        } else {
                            BufferedOutputStream bufferedOutputStream2 = BufferedOutputStream(FileOutputStream(d(str2, zipEntryNextElement.getName())))
                            try {
                                BufferedInputStream bufferedInputStream3 = BufferedInputStream(zipFile2.getInputStream(zipEntryNextElement))
                                while (true) {
                                    try {
                                        Int i = bufferedInputStream3.read(bArr, 0, 4096)
                                        if (i == -1) {
                                            break
                                        } else {
                                            bufferedOutputStream2.write(bArr, 0, i)
                                        }
                                    } catch (Throwable th) {
                                        th = th
                                        zipFile = zipFile2
                                        bufferedOutputStream = bufferedOutputStream2
                                        bufferedInputStream = bufferedInputStream3
                                        a(bufferedInputStream)
                                        a(bufferedOutputStream)
                                        if (zipFile != null) {
                                            try {
                                                zipFile.close()
                                            } catch (IOException e) {
                                            }
                                        }
                                        throw th
                                    }
                                }
                                a(bufferedInputStream3)
                                a(bufferedOutputStream2)
                                bufferedInputStream2 = bufferedInputStream3
                                bufferedOutputStream = bufferedOutputStream2
                            } catch (Throwable th2) {
                                th = th2
                                bufferedOutputStream = bufferedOutputStream2
                                bufferedInputStream = bufferedInputStream2
                                zipFile = zipFile2
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3
                        bufferedInputStream = bufferedInputStream2
                        zipFile = zipFile2
                    }
                }
                zipFile2.close()
                a(bufferedInputStream2)
                a(bufferedOutputStream)
                if (zipFile2 != null) {
                    try {
                        zipFile2.close()
                    } catch (IOException e2) {
                    }
                }
            } catch (Throwable th4) {
                th = th4
                bufferedInputStream = null
                zipFile = zipFile2
            }
        } catch (Throwable th5) {
            th = th5
            bufferedInputStream = null
            zipFile = null
        }
    }

    fun a(String str, String str2, String str3) throws Throwable {
        BufferedInputStream bufferedInputStream
        ZipFile zipFile
        BufferedOutputStream bufferedOutputStream = null
        if (!str2.endsWith("/")) {
            str2 = str2 + "/"
        }
        try {
            ZipFile zipFile2 = ZipFile(str)
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile2.entries()
                Array<Byte> bArr = new Byte[4096]
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                    if (zipEntryNextElement.getName().startsWith(str2)) {
                        String strSubstring = zipEntryNextElement.getName().substring(str2.length())
                        if (zipEntryNextElement.isDirectory()) {
                            File(str3 + "/" + strSubstring).mkdirs()
                        } else {
                            BufferedOutputStream bufferedOutputStream2 = BufferedOutputStream(FileOutputStream(d(str3, strSubstring)))
                            try {
                                BufferedInputStream bufferedInputStream2 = BufferedInputStream(zipFile2.getInputStream(zipEntryNextElement))
                                while (true) {
                                    try {
                                        Int i = bufferedInputStream2.read(bArr, 0, 4096)
                                        if (i == -1) {
                                            break
                                        } else {
                                            bufferedOutputStream2.write(bArr, 0, i)
                                        }
                                    } catch (Throwable th) {
                                        th = th
                                        bufferedOutputStream = bufferedOutputStream2
                                        bufferedInputStream = bufferedInputStream2
                                        zipFile = zipFile2
                                        a(bufferedInputStream)
                                        a(bufferedOutputStream)
                                        a(zipFile)
                                        throw th
                                    }
                                }
                                a(bufferedInputStream2)
                                a(bufferedOutputStream2)
                            } catch (Throwable th2) {
                                th = th2
                                zipFile = zipFile2
                                bufferedOutputStream = bufferedOutputStream2
                                bufferedInputStream = null
                            }
                        }
                    }
                }
                a((InputStream) null)
                a((OutputStream) null)
                a(zipFile2)
            } catch (Throwable th3) {
                th = th3
                bufferedInputStream = null
                zipFile = zipFile2
            }
        } catch (Throwable th4) {
            th = th4
            bufferedInputStream = null
            zipFile = null
        }
    }

    private fun a(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (Throwable th) {
            }
        }
    }

    fun b(Int i) {
        Array<Char> cArr = new Char[2]
        for (Int i2 = 0; i2 < 2; i2++) {
            cArr[1 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x00c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun b(java.lang.String r10, java.lang.String r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: a.a.b.a.a.x.b(java.lang.String, java.lang.String):Unit")
    }

    fun b(String str, String str2, String str3) {
        BufferedOutputStream bufferedOutputStream
        ZipFile zipFile
        BufferedInputStream bufferedInputStream = null
        try {
            ZipFile zipFile2 = ZipFile(str)
            try {
                ZipEntry entry = zipFile2.getEntry(str2)
                Array<Byte> bArr = new Byte[4096]
                bufferedOutputStream = BufferedOutputStream(FileOutputStream(str3))
                try {
                    BufferedInputStream bufferedInputStream2 = BufferedInputStream(zipFile2.getInputStream(entry))
                    while (true) {
                        try {
                            Int i = bufferedInputStream2.read(bArr, 0, 4096)
                            if (i == -1) {
                                a(bufferedInputStream2)
                                a(bufferedOutputStream)
                                a(zipFile2)
                                return
                            }
                            bufferedOutputStream.write(bArr, 0, i)
                        } catch (Throwable th) {
                            th = th
                            bufferedInputStream = bufferedInputStream2
                            zipFile = zipFile2
                            a(bufferedInputStream)
                            a(bufferedOutputStream)
                            a(zipFile)
                            throw th
                        }
                    }
                } catch (Throwable th2) {
                    th = th2
                    zipFile = zipFile2
                }
            } catch (Throwable th3) {
                th = th3
                bufferedOutputStream = null
                zipFile = zipFile2
            }
        } catch (Throwable th4) {
            th = th4
            bufferedOutputStream = null
            zipFile = null
        }
    }

    fun c(String str, String str2) throws Throwable {
        ZipFile zipFile
        ArrayList arrayList = ArrayList()
        Int iA = a(str2)
        try {
            zipFile = ZipFile(str)
        } catch (IOException e) {
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
                    if (!zipEntryNextElement.isDirectory()) {
                        String name = zipEntryNextElement.getName()
                        if (name.startsWith(str2) && a(name) == iA) {
                            arrayList.add(name)
                        }
                    }
                }
                a(zipFile)
            } catch (Throwable th2) {
                th = th2
                a(zipFile)
                throw th
            }
        } catch (IOException e2) {
            e = e2
            e.printStackTrace()
            a(zipFile)
            return arrayList
        }
        return arrayList
    }

    private fun d(String str, String str2) {
        Array<String> strArrSplit = str2.split("/")
        File file = File(str)
        if (!file.exists()) {
            file.mkdirs()
        }
        if (strArrSplit.length > 1) {
            Int i = 0
            while (i < strArrSplit.length - 1) {
                File file2 = File(file, strArrSplit[i])
                i++
                file = file2
            }
            if (!file.exists()) {
                file.mkdirs()
            }
        }
        return File(file, strArrSplit[strArrSplit.length - 1])
    }

    public final c a(Int i, a.d.Array<e> eVarArr, a.a.b.a.i iVar) throws a.a.ExceptionA {
        s sVarA = a(i, (String) null, false)
        if (eVarArr.length == 0) {
            return c(sVarA)
        }
        Int iIntValue = ((Integer) eVarArr[0].f71a).intValue()
        if (iIntValue == 16777216) {
            return b.a(sVarA, eVarArr, this, this.f22a)
        }
        String strA = iVar.a()
        if ("array".equals(strA) || iIntValue == 33554432 || iIntValue == 0) {
            return a(sVarA, eVarArr)
        }
        if ("plurals".equals(strA) || (iIntValue >= 16777220 && iIntValue <= 16777225)) {
            return r(sVarA, eVarArr)
        }
        if ("style".equals(strA)) {
            return v(sVarA, eVarArr, this)
        }
        throw new a.a.ExceptionA("unsupported res type name for bags. Found: " + strA)
    }

    public final s a(Int i, String str) {
        return a(i, str, false)
    }

    public final t a(Int i, Int i2, String str) throws a.a.ExceptionA {
        switch (i) {
            case 0:
                return i2 == 0 ? u(null, i2) : i2 == 1 ? g(i2, str, i) : s(this.f22a, 0, null)
            case 1:
                return a(i2, str, false)
            case 2:
                return a(i2, str, true)
            case 3:
                return u(str, i2)
            case 4:
                return m(Float.intBitsToFloat(i2), i2, str)
            case 5:
                return f(i2, str)
            case 6:
                return n(i2, str)
            case 7:
                return a(i2, str, false)
            case 8:
                return a(i2, str, true)
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
                if (i >= 28 && i <= 31) {
                    return e(i2, str)
                }
                if (i < 16 || i > 31) {
                    throw new a.a.ExceptionA("Invalid value type: " + i + ", value: " + i2)
                }
                return q(i2, str, i)
            case 18:
                return d(i2 != 0, i2, str)
        }
    }
}
