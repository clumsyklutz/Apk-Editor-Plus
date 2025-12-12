package com.gmail.heagoo.apkeditor.e

import android.util.Log
import com.gmail.heagoo.apkeditor.ApkInfoActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.ArrayList
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

final class t implements a {

    /* renamed from: a, reason: collision with root package name */
    private String f1009a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ s f1010b

    t(s sVar, String str) {
        this.f1010b = sVar
        this.f1009a = str
    }

    private fun a(String str) {
        Boolean z = false
        String strReplace = str
        for (Int iIndexOf = str.indexOf("0x7f"); iIndexOf != -1 && iIndexOf + 10 <= strReplace.length(); iIndexOf = strReplace.indexOf("0x7f", iIndexOf + 10)) {
            String strSubstring = strReplace.substring(iIndexOf, iIndexOf + 10)
            Int i = this.f1010b.c.get(ad.b(strSubstring))
            if (i != 0) {
                strReplace = strReplace.replace(strSubstring, "0x" + Integer.toHexString(i))
                z = true
            } else {
                Log.e("DEBUG", "Cannot find id " + strSubstring)
            }
        }
        return (z && strReplace.trim().startsWith("const/high16 v")) ? strReplace.replace("const/high16 v", "const v") : strReplace
    }

    private fun a(ZipFile zipFile, ZipEntry zipEntry) throws Throwable {
        BufferedReader bufferedReader
        ArrayList arrayList = ArrayList()
        try {
            bufferedReader = BufferedReader(InputStreamReader(zipFile.getInputStream(zipEntry)))
            while (true) {
                try {
                    String line = bufferedReader.readLine()
                    if (line == null) {
                        s.a(bufferedReader)
                        return arrayList
                    }
                    arrayList.add(line)
                } catch (Throwable th) {
                    th = th
                    s.a(bufferedReader)
                    throw th
                }
            }
        } catch (Throwable th2) {
            th = th2
            bufferedReader = null
        }
    }

    private fun a(String str, ZipFile zipFile, ZipEntry zipEntry) throws Throwable {
        List listA = a(zipFile, zipEntry)
        BufferedWriter bufferedWriter = null
        try {
            File parentFile = File(str).getParentFile()
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            BufferedWriter bufferedWriter2 = BufferedWriter(OutputStreamWriter(FileOutputStream(str)))
            Int i = 0
            while (true) {
                try {
                    Int i2 = i
                    if (i2 >= listA.size()) {
                        s.a(bufferedWriter2)
                        return
                    } else {
                        bufferedWriter2.write(a((String) listA.get(i2)))
                        bufferedWriter2.write(10)
                        i = i2 + 1
                    }
                } catch (Throwable th) {
                    th = th
                    bufferedWriter = bufferedWriter2
                    s.a(bufferedWriter)
                    throw th
                }
            }
        } catch (Throwable th2) {
            th = th2
        }
    }

    private fun b(String str, ZipFile zipFile, ZipEntry zipEntry) throws Throwable {
        BufferedReader bufferedReader
        try {
            bufferedReader = BufferedReader(InputStreamReader(zipFile.getInputStream(zipEntry)))
            try {
                ArrayList arrayList = ArrayList()
                while (true) {
                    String line = bufferedReader.readLine()
                    if (line == null) {
                        this.f1010b.b(str, arrayList)
                        s.a(bufferedReader)
                        return
                    } else {
                        String strTrim = line.trim()
                        if (!strTrim.startsWith("<?xml") && !strTrim.startsWith("<resources>") && !strTrim.startsWith("</resources>")) {
                            arrayList.add(strTrim)
                        }
                    }
                }
            } catch (Throwable th) {
                th = th
                s.a(bufferedReader)
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            bufferedReader = null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.a
    public final Boolean a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, ZipEntry zipEntry) throws Throwable {
        Int iIndexOf
        String name = zipEntry.getName()
        String str = this.f1009a + "/" + name
        if ("res/values/public.xml".equals(name)) {
            return true
        }
        if (this.f1010b.c != null && name.endsWith(".smali") && ((name.startsWith("smali/") || name.startsWith("smali_")) && (iIndexOf = name.indexOf(47)) != -1)) {
            a(str, zipFile, zipEntry)
            apkInfoActivity.j().c(name.substring(0, iIndexOf + 1) + "a.smali", str)
            return true
        }
        File file = File(str)
        if (!file.exists()) {
            if (name.startsWith("res/")) {
                file.getParentFile().mkdirs()
            }
            return false
        }
        if (name.endsWith(".xml")) {
            Array<String> strArrSplit = name.split("/")
            if (strArrSplit.length == 3 && strArrSplit[0].equals("res") && (strArrSplit[1].equals("values") || strArrSplit[1].startsWith("values-"))) {
                b(str, zipFile, zipEntry)
                return true
            }
        }
        return false
    }
}
