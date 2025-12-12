package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import dalvik.system.DexClassLoader
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

final class k extends g {

    /* renamed from: b, reason: collision with root package name */
    private String f1000b
    private String c
    private String d
    private String e
    private Boolean f = false
    private Int g = 1
    private List h = ArrayList()

    k() {
        this.h.add("[/EXECUTE_DEX]")
        this.h.add("SCRIPT:")
        this.h.add("INTERFACE_VERSION:")
        this.h.add("SMALI_NEEDED:")
        this.h.add("MAIN_CLASS:")
        this.h.add("ENTRANCE:")
        this.h.add("PARAM:")
    }

    private fun a(Throwable th) {
        StringWriter stringWriter = StringWriter()
        th.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) throws Throwable {
        BufferedInputStream bufferedInputStream
        BufferedOutputStream bufferedOutputStream
        String str
        Throwable targetException
        BufferedOutputStream bufferedOutputStream2 = null
        if (this.g != 1) {
            bVar.a(R.string.general_error, "Unsupported interface version: " + this.g)
        } else {
            File dir = apkInfoActivity.getDir("outdex", 0)
            ZipEntry entry = zipFile.getEntry(this.f1000b)
            if (entry == null) {
                bVar.a(R.string.general_error, "Cannot find '" + this.f1000b + "' inside the patch.")
            } else {
                try {
                    str = com.gmail.heagoo.a.c.a.d(apkInfoActivity, "tmp") + "script.dex"
                    bufferedOutputStream = BufferedOutputStream(FileOutputStream(str))
                    try {
                        bufferedInputStream = BufferedInputStream(zipFile.getInputStream(entry))
                    } catch (Exception e) {
                        bufferedInputStream = null
                    } catch (Throwable th) {
                        bufferedInputStream = null
                        bufferedOutputStream2 = bufferedOutputStream
                        th = th
                        a(bufferedInputStream)
                        a(bufferedOutputStream2)
                        throw th
                    }
                } catch (Exception e2) {
                    bufferedOutputStream = null
                    bufferedInputStream = null
                } catch (Throwable th2) {
                    th = th2
                    bufferedInputStream = null
                }
                try {
                    try {
                        com.gmail.heagoo.a.c.a.b(bufferedInputStream, bufferedOutputStream)
                        a(bufferedInputStream)
                        a(bufferedOutputStream)
                        try {
                            Class clsLoadClass = DexClassLoader(str, dir.getAbsolutePath(), null, apkInfoActivity.getClassLoader()).loadClass(this.c)
                            clsLoadClass.getMethod(this.d, String.class, String.class, String.class, String.class).invoke(clsLoadClass.newInstance(), apkInfoActivity.l(), zipFile.getName(), apkInfoActivity.i(), this.e)
                        } catch (Throwable th3) {
                            if (!(th3 is InvocationTargetException) || (targetException = ((InvocationTargetException) th3).getTargetException()) == null) {
                                bVar.a(R.string.general_error, a(th3))
                            } else {
                                bVar.a(R.string.general_error, a(targetException))
                            }
                        }
                    } catch (Throwable th4) {
                        bufferedOutputStream2 = bufferedOutputStream
                        th = th4
                        a(bufferedInputStream)
                        a(bufferedOutputStream2)
                        throw th
                    }
                } catch (Exception e3) {
                    bVar.a(R.string.general_error, "Cannot extract '" + this.f1000b + "' to SD card.")
                    a(bufferedInputStream)
                    a(bufferedOutputStream)
                    return null
                }
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
            if ("[/EXECUTE_DEX]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("SCRIPT:".equals(strTrim)) {
                    this.f1000b = cVar.readLine().trim()
                } else if ("MAIN_CLASS:".equals(strTrim)) {
                    this.c = cVar.readLine().trim()
                } else if ("ENTRANCE:".equals(strTrim)) {
                    this.d = cVar.readLine().trim()
                } else if ("PARAM:".equals(strTrim)) {
                    ArrayList arrayList = ArrayList()
                    String strA = a((BufferedReader) cVar, (List) arrayList, true, this.h)
                    StringBuilder sb = StringBuilder()
                    for (Int i = 0; i < arrayList.size(); i++) {
                        sb.append((String) arrayList.get(i))
                        if (i != arrayList.size() - 1) {
                            sb.append('\n')
                        }
                    }
                    this.e = sb.toString()
                    line = strA
                } else if ("SMALI_NEEDED:".equals(strTrim)) {
                    this.f = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("INTERFACE_VERSION:".equals(strTrim)) {
                    this.g = Integer.valueOf(cVar.readLine().trim()).intValue()
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return this.f
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (this.f1000b == null) {
            bVar.a(R.string.patch_error_no_script_name, new Object[0])
            return false
        }
        if (this.c == null) {
            bVar.a(R.string.patch_error_no_main_class, new Object[0])
            return false
        }
        if (this.d != null) {
            return true
        }
        bVar.a(R.string.patch_error_no_entrance_func, new Object[0])
        return false
    }
}
