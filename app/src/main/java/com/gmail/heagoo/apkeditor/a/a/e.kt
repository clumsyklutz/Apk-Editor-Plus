package com.gmail.heagoo.apkeditor.a.a

import android.R
import android.util.SparseArray
import jadx.core.deobf.Deobfuscator
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.Map
import java.util.Random

class e {

    /* renamed from: a, reason: collision with root package name */
    private m f785a

    /* renamed from: b, reason: collision with root package name */
    private l f786b
    private Boolean c
    private String d
    private Int e
    private Int f
    private s g
    private r h
    private a i
    private com.gmail.heagoo.apkeditor.a.g j
    private com.gmail.heagoo.apkeditor.a.g k
    private Map l = HashMap()

    constructor(InputStream inputStream, String str, Boolean z) throws IOException {
        this.c = z
        this.f785a = m(inputStream)
        File file = File(str)
        if (file.exists()) {
            file.delete()
        }
        RandomAccessFile randomAccessFile = RandomAccessFile(str, "rw")
        randomAccessFile.setLength(0L)
        this.f786b = l(randomAccessFile)
        try {
            this.e = this.f785a.a()
            this.f = this.f785a.a()
            this.g = s()
            this.g.a(this.f785a)
            this.h = r()
            this.h.a(this.f785a)
            this.i = a(this.g, this.h)
            this.i.a(this.f785a)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    protected static Int a(Array<Byte> bArr, Int i) {
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24)
    }

    private fun a(String str, String str2) {
        return str2.startsWith(Deobfuscator.CLASS_NAME_SEPARATOR) ? str + str2 : !str2.contains(Deobfuscator.CLASS_NAME_SEPARATOR) ? str + Deobfuscator.CLASS_NAME_SEPARATOR + str2 : str2
    }

    private fun a(String str, String str2, String str3) {
        return str3.startsWith(StringBuilder().append(str).append(Deobfuscator.CLASS_NAME_SEPARATOR).toString()) ? str2 + str3.substring(str.length()) : str3
    }

    private fun a(l lVar) {
        if (lVar != null) {
            try {
                lVar.b()
            } catch (IOException e) {
            }
        }
    }

    private fun a(Boolean z, Boolean z2) {
        if (z) {
            ArrayList arrayList = ArrayList()
            SparseArray sparseArray = SparseArray()
            for (com.gmail.heagoo.apkeditor.a.h hVar : this.j.s) {
                if (!arrayList.contains(Integer.valueOf(hVar.c))) {
                    arrayList.add(Integer.valueOf(hVar.c))
                    sparseArray.put(hVar.c, hVar.f825b)
                }
            }
            Collections.sort(arrayList)
            for (Int size = arrayList.size() - 1; size >= 0; size--) {
                Int iIntValue = ((Integer) arrayList.get(size)).intValue()
                String str = (String) sparseArray.get(iIntValue)
                if (str.startsWith(this.j.e)) {
                    this.g.a(this.k.e + str.substring(this.j.e.length()), iIntValue + 1)
                } else {
                    this.g.a(str + this.d, iIntValue + 1)
                }
            }
        }
        if (z2) {
            this.g.a("installLocation", 0)
        }
    }

    protected static Unit a(Array<Byte> bArr, Int i, Int i2) {
        bArr[i] = (Byte) i2
        bArr[i + 1] = (Byte) (i2 >> 8)
        bArr[i + 2] = (Byte) (i2 >> 16)
        bArr[i + 3] = (Byte) (i2 >>> 24)
    }

    protected static Unit b(Array<Byte> bArr, Int i, Int i2) {
        bArr[i] = (Byte) i2
        bArr[i + 1] = (Byte) (i2 >> 8)
    }

    public final Unit a() {
        try {
            this.f786b.a(this.e)
            this.f786b.a(this.f)
            Int i = this.g.f806a
            this.g.a(this.f786b)
            this.h.a(this.f786b)
            this.i.a(this.f786b)
            new Object[1][0] = Integer.valueOf(this.f786b.a())
            this.f786b.a(4, this.f786b.a())
        } finally {
            a(this.f786b)
        }
    }

    public final Unit a(com.gmail.heagoo.apkeditor.a.g gVar, com.gmail.heagoo.apkeditor.a.g gVar2) {
        this.j = gVar
        this.k = gVar2
        Boolean z = this.j.f823b == -1 && this.k.f823b != -1
        Boolean z2 = (this.j.k == -1 || this.k.d == null || this.k.d.equals(this.j.d)) ? false : true
        Boolean z3 = (this.k.e == null || this.k.e.equals(this.j.e)) ? false : true
        Boolean z4 = (this.j.j < 0 || this.k.c == null || this.k.c.equals(this.j.c)) ? false : true
        Boolean z5 = this.c
        HashSet hashSet = HashSet()
        if (z3) {
            Random random = Random(System.currentTimeMillis())
            this.d = StringBuilder().append((Char) (random.nextInt(26) + 97)).append((Char) (random.nextInt(26) + 97)).toString()
        }
        if (z3 && this.c) {
            for (Int i = 0; i < this.g.d.length; i++) {
                String strB = this.g.b(i)
                if (strB.startsWith(this.j.e)) {
                    this.g.a(i, this.k.e + strB.substring(this.j.e.length()))
                    hashSet.add(Integer.valueOf(i))
                }
            }
        }
        if (z3) {
            for (Map.Entry entry : this.j.r.entrySet()) {
                Int iIntValue = ((Integer) entry.getKey()).intValue()
                if (!hashSet.contains(Integer.valueOf(iIntValue))) {
                    this.g.a(iIntValue, ((String) entry.getValue()) + this.d)
                }
            }
        }
        Int i2 = this.j.k
        if (z2 && i2 != -1) {
            this.g.a(i2, this.k.d)
        }
        Int i3 = this.j.l
        if (z3 && i3 != -1 && !hashSet.contains(Integer.valueOf(i3))) {
            this.g.a(i3, this.k.e)
        }
        if (z3) {
            if (this.j.p != null && !this.j.p.isEmpty()) {
                Collections.sort(this.j.p)
                Iterator it = this.j.p.iterator()
                Int i4 = -1
                while (it.hasNext()) {
                    Int iIntValue2 = ((Integer) it.next()).intValue()
                    if (iIntValue2 != i4) {
                        if (!hashSet.contains(Integer.valueOf(iIntValue2))) {
                            String strB2 = this.g.b(iIntValue2)
                            String strA = z5 ? a(this.j.e, this.k.e, strB2) : a(this.j.e, strB2)
                            if (!strA.equals(strB2)) {
                                this.g.a(iIntValue2, strA)
                            }
                        }
                    }
                    i4 = iIntValue2
                }
            }
            if (!this.j.u.isEmpty()) {
                Iterator it2 = this.j.u.values().iterator()
                while (it2.hasNext()) {
                    Int iIntValue3 = ((Integer) it2.next()).intValue()
                    if (!hashSet.contains(Integer.valueOf(iIntValue3))) {
                        String strB3 = this.g.b(iIntValue3)
                        String strA2 = z5 ? a(this.j.e, this.k.e, strB3) : a(this.j.e, strB3)
                        if (!strA2.equals(strB3)) {
                            this.g.a(iIntValue3, strA2)
                        }
                    }
                }
            }
        }
        if (z4) {
            this.g.a(this.j.j, this.k.c)
        }
        a(z3, z)
        if (z) {
            this.h.a(R.attr.installLocation, 0)
        }
        this.i.a()
        if (z) {
            this.i.a("manifest", 0, -1, 268435464, this.k.f823b)
        } else if (this.k.f823b != -1) {
            this.i.a("manifest", "installLocation", f(this))
        }
        if (z3 && !this.j.s.isEmpty()) {
            this.i.a("manifest/application/provider", "authorities", g(this))
        }
        if (this.k.f822a != this.j.f822a) {
            this.i.a("manifest", "versionCode", h(this))
        }
        if (this.j.g != -1 && this.k.g != this.j.g) {
            this.i.a("manifest/uses-sdk", "minSdkVersion", i(this))
        }
        if (this.j.h != -1 && this.k.h != this.j.h) {
            this.i.a("manifest/uses-sdk", "targetSdkVersion", j(this))
        }
        if (this.j.i == -1 || this.k.i == this.j.i) {
            return
        }
        this.i.a("manifest/uses-sdk", "minSdkVersion", k(this))
    }

    public final Map b() {
        return this.l
    }
}
