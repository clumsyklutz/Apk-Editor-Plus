package com.gmail.heagoo.apkeditor.e

import android.util.SparseIntArray
import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.Closeable
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.RandomAccessFile
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

final class s extends g {

    /* renamed from: b, reason: collision with root package name */
    private String f1008b
    private SparseIntArray c

    s() {
    }

    private fun a(Map map) {
        Int iIntValue = 0
        Iterator it = map.values().iterator()
        while (true) {
            Int i = iIntValue
            if (!it.hasNext()) {
                return i >> 16
            }
            iIntValue = ((Integer) it.next()).intValue() & 16711680
            if (iIntValue <= i) {
                iIntValue = i
            }
        }
    }

    private fun a(List list, Map map) {
        SparseIntArray sparseIntArray = SparseIntArray()
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                return sparseIntArray
            }
            ad adVar = (ad) list.get(i2)
            Integer num = (Integer) map.get(adVar.f987a)
            if (num != null) {
                Int iIntValue = num.intValue() + 1
                sparseIntArray.put(adVar.c, iIntValue)
                adVar.c = iIntValue
                map.put(adVar.f987a, Integer.valueOf(iIntValue))
            } else {
                Int iA = ((a(map) + 1) << 16) + 2130706432
                sparseIntArray.put(adVar.c, iA)
                adVar.c = iA
                map.put(adVar.f987a, Integer.valueOf(iA))
            }
            i = i2 + 1
        }
    }

    private fun a(InputStream inputStream) throws IOException {
        ArrayList arrayList = ArrayList()
        BufferedReader bufferedReader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            String line = bufferedReader.readLine()
            if (line == null) {
                return arrayList
            }
            ad adVarA = ad.a(line)
            if (adVarA != null) {
                arrayList.add(adVarA)
            }
        }
    }

    private fun a(List list) {
        HashMap map = HashMap()
        Iterator it = list.iterator()
        Int i = 0
        Int i2 = 0
        Int i3 = 0
        while (it.hasNext()) {
            ad adVar = (ad) it.next()
            if ("drawable".equals(adVar.f987a)) {
                if (adVar.c > i3) {
                    i3 = adVar.c
                }
            } else if ("layout".equals(adVar.f987a)) {
                if (adVar.c > i2) {
                    i2 = adVar.c
                }
            } else if (!"string".equals(adVar.f987a)) {
                Integer num = (Integer) map.get(adVar.f987a)
                if (num == null || adVar.c > num.intValue()) {
                    map.put(adVar.f987a, Integer.valueOf(adVar.c))
                }
            } else if (adVar.c > i) {
                i = adVar.c
            }
        }
        map.put("drawable", Integer.valueOf(i3))
        map.put("layout", Integer.valueOf(i2))
        map.put("string", Integer.valueOf(i))
        return map
    }

    private fun a(String str, List list) throws Throwable {
        ArrayList arrayList = ArrayList()
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                b(str, arrayList)
                return
            } else {
                arrayList.add(((ad) list.get(i2)).toString())
                i = i2 + 1
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(String str, List list) throws Throwable {
        RandomAccessFile randomAccessFile
        try {
            randomAccessFile = RandomAccessFile(str, "rw")
            try {
                Long length = randomAccessFile.length()
                if (length < 16) {
                    throw Exception("File is too small!")
                }
                randomAccessFile.seek(length - 16)
                Array<Byte> bArr = new Byte[32]
                Int i = randomAccessFile.read(bArr)
                Int i2 = 0
                while (i2 < i && (bArr[i2] != 60 || bArr[i2 + 1] != 47)) {
                    i2++
                }
                randomAccessFile.seek(i2 + (length - 16))
                StringBuilder sb = StringBuilder()
                for (Int i3 = 0; i3 < list.size(); i3++) {
                    sb.append((String) list.get(i3))
                    sb.append("\n")
                }
                sb.append("</resources>")
                randomAccessFile.write(sb.toString().getBytes())
                a(randomAccessFile)
            } catch (Throwable th) {
                th = th
                a(randomAccessFile)
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            randomAccessFile = null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) throws Throwable {
        InputStream inputStream
        FileOutputStream fileOutputStream
        InputStream inputStream2
        FileInputStream fileInputStream
        InputStream inputStream3
        ZipFile zipFile2
        FileOutputStream fileOutputStream2 = null
        ZipEntry entry = zipFile.getEntry(this.f1008b)
        if (entry == null) {
            bVar.a(R.string.patch_error_no_entry, this.f1008b)
        } else {
            try {
                inputStream = zipFile.getInputStream(entry)
                try {
                    String str = com.gmail.heagoo.a.c.a.d(apkInfoActivity, "tmp") + com.gmail.heagoo.common.s.a(6)
                    fileOutputStream = FileOutputStream(str)
                    try {
                        com.gmail.heagoo.a.c.a.b(inputStream, fileOutputStream)
                        fileOutputStream.close()
                        String str2 = apkInfoActivity.i() + "/res/values/public.xml"
                        try {
                            zipFile2 = ZipFile(str)
                            try {
                                ZipEntry entry2 = zipFile2.getEntry("res/values/public.xml")
                                if (entry2 == null) {
                                    throw Exception(bVar.a(R.string.patch_error_publicxml_notfound))
                                }
                                inputStream3 = zipFile2.getInputStream(entry2)
                                try {
                                    List listA = a(inputStream3)
                                    fileInputStream = FileInputStream(str2)
                                    try {
                                        this.c = a(listA, a(a((InputStream) fileInputStream)))
                                        a(str2, listA)
                                        a((Closeable) fileInputStream)
                                        a((Closeable) inputStream3)
                                        a(zipFile2)
                                        a(apkInfoActivity, str, t(this, apkInfoActivity.i()), bVar)
                                        a((Closeable) inputStream)
                                        a((Closeable) null)
                                    } catch (Throwable th) {
                                        th = th
                                        a((Closeable) fileInputStream)
                                        a((Closeable) inputStream3)
                                        a(zipFile2)
                                        throw th
                                    }
                                } catch (Throwable th2) {
                                    th = th2
                                    fileInputStream = null
                                }
                            } catch (Throwable th3) {
                                th = th3
                                fileInputStream = null
                                inputStream3 = null
                            }
                        } catch (Throwable th4) {
                            th = th4
                            fileInputStream = null
                            inputStream3 = null
                            zipFile2 = null
                        }
                    } catch (Exception e) {
                        e = e
                        inputStream2 = inputStream
                        try {
                            bVar.a(R.string.general_error, e.getMessage())
                            a((Closeable) inputStream2)
                            a(fileOutputStream)
                            return null
                        } catch (Throwable th5) {
                            th = th5
                            fileOutputStream2 = fileOutputStream
                            inputStream = inputStream2
                            a((Closeable) inputStream)
                            a(fileOutputStream2)
                            throw th
                        }
                    } catch (Throwable th6) {
                        th = th6
                        fileOutputStream2 = fileOutputStream
                        a((Closeable) inputStream)
                        a(fileOutputStream2)
                        throw th
                    }
                } catch (Exception e2) {
                    e = e2
                    fileOutputStream = null
                    inputStream2 = inputStream
                } catch (Throwable th7) {
                    th = th7
                }
            } catch (Exception e3) {
                e = e3
                fileOutputStream = null
                inputStream2 = null
            } catch (Throwable th8) {
                th = th8
                inputStream = null
            }
        }
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/MERGE]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("SOURCE:".equals(strTrim)) {
                    this.f1008b = cVar.readLine().trim()
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (this.f1008b != null) {
            return true
        }
        bVar.a(R.string.patch_error_no_source_file, new Object[0])
        return false
    }
}
