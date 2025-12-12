package com.gmail.heagoo.apkeditor.b

import android.util.Log
import com.gmail.heagoo.neweditor.Token
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.Closeable
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.zip.Adler32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class e {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f873a

    /* renamed from: b, reason: collision with root package name */
    private b f874b
    private List c

    constructor(String str) {
        this(str, "classes.dex")
    }

    /* JADX WARN: Multi-variable type inference failed */
    constructor(String str, String str2) throws Throwable {
        ZipFile zipFile
        ZipFile zipFile2
        ZipFile zipFile3 = null
        this.c = ArrayList()
        try {
            zipFile = ZipFile(str)
            try {
                ZipEntry entry = zipFile.getEntry(str2)
                Int size = (Int) entry.getSize()
                this.f873a = new Byte[size]
                BufferedInputStream bufferedInputStream = BufferedInputStream(zipFile.getInputStream(entry))
                Int i = 0
                while (i < size) {
                    try {
                        i += bufferedInputStream.read(this.f873a, i, size - i)
                    } catch (Exception e) {
                        e = e
                        zipFile3 = zipFile
                        zipFile2 = bufferedInputStream
                        try {
                            e.printStackTrace()
                            com.gmail.heagoo.a.c.a.a((Closeable) zipFile2)
                            com.gmail.heagoo.a.c.a.a(zipFile3)
                            return
                        } catch (Throwable th) {
                            th = th
                            zipFile = zipFile3
                            zipFile3 = zipFile2
                            com.gmail.heagoo.a.c.a.a((Closeable) zipFile3)
                            com.gmail.heagoo.a.c.a.a(zipFile)
                            throw th
                        }
                    } catch (Throwable th2) {
                        th = th2
                        zipFile3 = bufferedInputStream
                        com.gmail.heagoo.a.c.a.a((Closeable) zipFile3)
                        com.gmail.heagoo.a.c.a.a(zipFile)
                        throw th
                    }
                }
                this.f874b = b(this.f873a)
                com.gmail.heagoo.a.c.a.a((Closeable) bufferedInputStream)
                com.gmail.heagoo.a.c.a.a(zipFile)
            } catch (Exception e2) {
                e = e2
                zipFile2 = null
                zipFile3 = zipFile
            } catch (Throwable th3) {
                th = th3
            }
        } catch (Exception e3) {
            e = e3
            zipFile2 = null
        } catch (Throwable th4) {
            th = th4
            zipFile = null
        }
    }

    private fun a(Int i, Int i2) {
        Int i3 = 0
        Int i4 = i
        while (i3 < i2) {
            while ((this.f873a[i4] & 128) != 0) {
                i4++
            }
            i3++
            i4++
        }
        return i4 - i
    }

    private fun a(Int i, Array<Int> iArr) {
        Int i2 = i
        for (Int i3 = 0; i3 < iArr.length; i3++) {
            Int i4 = 0
            Int i5 = i2
            Int i6 = 0
            while ((this.f873a[i5] & 128) != 0) {
                i4 |= (this.f873a[i5] & Token.END) << i6
                i6 += 7
                i5++
            }
            Int i7 = i4 | (this.f873a[i5] << i6)
            i2 = i5 + 1
            iArr[i3] = i7
        }
        return i2 - i
    }

    private fun a(String str) {
        return "L" + str.replace('.', '/')
    }

    private fun a(String str, Map map) {
        for (Map.Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey()
            if (str.startsWith(str2)) {
                return ((String) entry.getValue()) + str.substring(str2.length())
            }
        }
        return null
    }

    private fun a(Int i, Int i2, Int i3) {
        Int iA = b.a(this.f873a, i)
        if (iA >= i2) {
            b.a(iA + i3, this.f873a, i)
        }
    }

    private fun a(List list) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1")
            h hVar = (h) list.get(0)
            messageDigest.update(hVar.f877a, 32, hVar.c - 32)
            for (Int i = 1; i < list.size(); i++) {
                h hVar2 = (h) list.get(i)
                messageDigest.update(hVar2.f877a, hVar2.f878b, hVar2.c)
            }
            Array<Byte> bArrDigest = messageDigest.digest()
            System.arraycopy(bArrDigest, 0, hVar.f877a, 12, bArrDigest.length)
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace()
        }
    }

    private fun a(List list, String str) throws IOException {
        BufferedOutputStream bufferedOutputStream = BufferedOutputStream(FileOutputStream(str))
        Iterator it = list.iterator()
        while (it.hasNext()) {
            h hVar = (h) it.next()
            bufferedOutputStream.write(hVar.f877a, hVar.f878b, hVar.c)
        }
        bufferedOutputStream.close()
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x037a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(java.lang.String r26, java.util.List r27) {
        /*
            Method dump skipped, instructions count: 901
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.b.e.a(java.lang.String, java.util.List):Boolean")
    }

    private fun a(String str, List list, String str2, String str3) {
        String strA = a(str2)
        String str4 = "[" + strA
        String strA2 = a(str3)
        String str5 = "[" + strA2
        String str6 = "content://" + str2
        Int i = 0
        Int i2 = -1
        Int i3 = -1
        Int i4 = -1
        Int i5 = -1
        Int i6 = -1
        Int i7 = -1
        while (true) {
            Int i8 = i
            if (i8 >= list.size()) {
                break
            }
            g gVar = (g) list.get(i8)
            if (gVar.d != null) {
                if (gVar.d.startsWith(strA)) {
                    gVar.a(strA2 + gVar.d.substring(strA.length()))
                    if (i2 == -1) {
                        i2 = i8
                    }
                    i3 = i8 + 1
                } else if (gVar.d.startsWith(str2)) {
                    gVar.a(str3 + gVar.d.substring(str2.length()))
                    if (i4 == -1) {
                        i4 = i8
                    }
                    i5 = i8 + 1
                } else if (gVar.d.startsWith(str4)) {
                    gVar.a(str5 + gVar.d.substring(str4.length()))
                    if (i2 == -1) {
                        i6 = i8
                    }
                    i7 = i8 + 1
                } else if (gVar.d.startsWith(str6)) {
                    gVar.a("content://" + str3 + gVar.d.substring(str6.length()))
                }
            }
            i = i8 + 1
        }
        if (i2 == -1) {
            return false
        }
        if (a(list, i2, i3)) {
            Log.e("DEBUG", "The string order is changed! (as the class name change)")
        }
        if (i4 != -1 && a(list, i4, i5)) {
            Log.e("DEBUG", "The string order is changed! (as the pkg name change)")
        }
        if (i6 != -1 && a(list, i6, i7)) {
            Log.e("DEBUG", "The string order is changed! (as the array class name change)")
        }
        return a(str, list)
    }

    private fun a(List list, Int i, Int i2) {
        Int size = list.size()
        g gVar = i > 0 ? (g) list.get(i - 1) : null
        g gVar2 = i2 < size ? (g) list.get(i2) : null
        return (gVar != null && ((g) list.get(i)).a(gVar) < 0) || (gVar2 != null && ((g) list.get(i2 + (-1))).a(gVar2) > 0)
    }

    private fun b(List list) {
        Array<Byte> bArr = new Byte[4]
        Adler32 adler32 = Adler32()
        h hVar = (h) list.get(0)
        adler32.update(hVar.f877a, 12, hVar.c - 12)
        for (Int i = 1; i < list.size(); i++) {
            h hVar2 = (h) list.get(i)
            adler32.update(hVar2.f877a, hVar2.f878b, hVar2.c)
        }
        Long value = adler32.getValue()
        bArr[0] = (Byte) (value & 255)
        bArr[1] = (Byte) ((value >> 8) & 255)
        bArr[2] = (Byte) ((value >> 16) & 255)
        bArr[3] = (Byte) ((value >> 24) & 255)
        System.arraycopy(bArr, 0, hVar.f877a, 8, 4)
    }

    public final Unit a(Map map, String str) throws Exception {
        Int length = 0
        Array<Byte> bArr = new Byte[256]
        for (Int i = 0; i < 256; i++) {
            bArr[i] = 0
        }
        Iterator it = map.entrySet().iterator()
        while (true) {
            Array<Byte> bArr2 = bArr
            Int i2 = length
            if (!it.hasNext()) {
                b bVar = b(this.f873a)
                Int i3 = 0
                while (i3 < bVar.b()) {
                    Int iA = bVar.a(i3)
                    d dVar = d(this.f873a, (this.f873a[iA + 3] << 24) | (this.f873a[iA] & 255) | ((this.f873a[iA + 1] & 255) << 8) | ((this.f873a[iA + 2] & 255) << 16))
                    dVar.a()
                    if (this.f873a[dVar.b()] == 76) {
                        break
                    } else {
                        i3++
                    }
                }
                String strA = ""
                while (true) {
                    Int i4 = i3
                    String str2 = strA
                    if (i4 >= bVar.b()) {
                        break
                    }
                    Int iA2 = bVar.a(i4)
                    d dVar2 = d(this.f873a, (this.f873a[iA2 + 3] << 24) | (this.f873a[iA2] & 255) | ((this.f873a[iA2 + 1] & 255) << 8) | ((this.f873a[iA2 + 2] & 255) << 16))
                    Int iA3 = dVar2.a()
                    if (this.f873a[dVar2.b()] != 76) {
                        break
                    }
                    Int iB = dVar2.b()
                    String strA2 = dVar2.a(iA3)
                    strA = a(strA2, map)
                    if (strA != null) {
                        System.arraycopy(strA.getBytes(), 0, this.f873a, iB, strA.length())
                    }
                    if (strA == null) {
                        strA = strA2
                    }
                    if (strA.compareTo(str2) <= 0) {
                        Log.e("ERROR", "Order break: " + strA + ", " + str2)
                    }
                    i3 = i4 + 1
                }
                Array<Byte> bArr3 = this.f873a
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1")
                    messageDigest.update(bArr3, 32, bArr3.length - 32)
                    Array<Byte> bArrDigest = messageDigest.digest()
                    System.arraycopy(bArrDigest, 0, bArr3, 12, bArrDigest.length)
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace()
                }
                Array<Byte> bArr4 = this.f873a
                Adler32 adler32 = Adler32()
                adler32.update(bArr4, 12, bArr4.length - 12)
                Long value = adler32.getValue()
                System.arraycopy(new Array<Byte>{(Byte) (255 & value), (Byte) ((value >> 8) & 255), (Byte) ((value >> 16) & 255), (Byte) ((value >> 24) & 255)}, 0, bArr4, 8, 4)
                Array<Byte> bArr5 = this.f873a
                FileOutputStream fileOutputStream = FileOutputStream(str)
                fileOutputStream.write(bArr5)
                fileOutputStream.close()
                return
            }
            Map.Entry entry = (Map.Entry) it.next()
            String str3 = (String) entry.getKey()
            if (str3.length() != ((String) entry.getValue()).length()) {
                throw Exception("Different class name length not supported.")
            }
            if (str3.length() >= bArr2.length) {
                bArr = new Byte[str3.length() + 1]
                System.arraycopy(bArr2, 0, bArr, 0, bArr2.length)
            } else {
                bArr = bArr2
            }
            bArr[str3.length()] = 1
            length = str3.length() > i2 ? str3.length() : i2
        }
    }

    public final Boolean a(String str, String str2, String str3) throws i {
        g gVar
        Int iB = this.f874b.b()
        Array<Int> iArr = new Int[iB]
        ArrayList arrayList = ArrayList(iB)
        for (Int i = 0; i < iB; i++) {
            Int iA = this.f874b.a(i)
            iArr[i] = (this.f873a[iA + 3] << 24) | (this.f873a[iA] & 255) | ((this.f873a[iA + 1] & 255) << 8) | ((this.f873a[iA + 2] & 255) << 16)
        }
        Byte bCharAt = str.charAt(0)
        d dVar = d(this.f873a, iArr[0])
        for (Int i2 = 0; i2 < iB; i2++) {
            dVar.b(iArr[i2])
            Int iA2 = dVar.a()
            Int iB2 = dVar.b()
            String strA = (this.f873a[iB2] == 76 || this.f873a[iB2] == 91 || this.f873a[iB2] == 99 || this.f873a[iB2] == bCharAt) ? dVar.a(iA2) : null
            if (i2 + 1 >= iB || iArr[i2 + 1] <= iArr[i2]) {
                if (strA == null) {
                    strA = dVar.a(iA2)
                }
                gVar = g(i2, strA, this.f873a, iArr[i2], g(i2, strA).a())
            } else {
                gVar = g(i2, strA, this.f873a, iArr[i2], iArr[i2 + 1] - iArr[i2])
            }
            arrayList.add(gVar)
        }
        return a(str3, arrayList, str, str2)
    }
}
