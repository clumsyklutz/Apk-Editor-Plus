package com.gmail.heagoo.common

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class h {

    /* renamed from: a, reason: collision with root package name */
    private static final Array<Char> f1456a = {'\"', '/', '\\', ':', '*', '?', '<', '>', '|'}

    fun a(Array<File> fileArr) {
        Long j = 0
        Int i = 0
        while (i < 2) {
            Long jB = b(fileArr[i])
            if (jB <= j) {
                jB = j
            }
            i++
            j = jB
        }
        return j
    }

    fun a(String str) {
        if (str == null) {
            return ""
        }
        StringBuilder sb = StringBuilder()
        Boolean z = false
        for (Int i = 0; i < str.length(); i++) {
            Char cCharAt = str.charAt(i)
            if (a(cCharAt)) {
                sb.append('_')
                z = true
            } else {
                sb.append(cCharAt)
            }
        }
        return z ? sb.toString() : str
    }

    fun a(File file) {
        Array<File> fileArrListFiles
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                a(file2)
            }
        }
        file.delete()
    }

    fun a(File file, File file2) throws Throwable {
        BufferedOutputStream bufferedOutputStream
        BufferedInputStream bufferedInputStream
        BufferedInputStream bufferedInputStream2 = null
        try {
            bufferedInputStream = BufferedInputStream(FileInputStream(file))
            try {
                bufferedOutputStream = BufferedOutputStream(FileOutputStream(file2))
            } catch (Throwable th) {
                th = th
                bufferedOutputStream = null
                bufferedInputStream2 = bufferedInputStream
            }
        } catch (Throwable th2) {
            th = th2
            bufferedOutputStream = null
        }
        try {
            com.gmail.heagoo.a.c.a.b(bufferedInputStream, bufferedOutputStream)
            bufferedInputStream.close()
            bufferedOutputStream.close()
        } catch (Throwable th3) {
            th = th3
            bufferedInputStream2 = bufferedInputStream
            if (bufferedInputStream2 != null) {
                bufferedInputStream2.close()
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close()
            }
            throw th
        }
    }

    fun a(String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream
        FileInputStream fileInputStream = null
        try {
            FileInputStream fileInputStream2 = FileInputStream(str)
            try {
                fileOutputStream = FileOutputStream(str2)
                try {
                    com.gmail.heagoo.a.c.a.b(fileInputStream2, fileOutputStream)
                    fileInputStream2.close()
                    fileOutputStream.close()
                } catch (Throwable th) {
                    th = th
                    fileInputStream = fileInputStream2
                    if (fileInputStream != null) {
                        fileInputStream.close()
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close()
                    }
                    throw th
                }
            } catch (Throwable th2) {
                th = th2
                fileOutputStream = null
                fileInputStream = fileInputStream2
            }
        } catch (Throwable th3) {
            th = th3
            fileOutputStream = null
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun a(java.lang.String r3, java.util.List r4) {
        /*
            r2 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L42
            java.io.FileWriter r0 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L42
            r0.<init>(r3)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L42
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L42
            java.util.Iterator r2 = r4.iterator()     // Catch: java.io.IOException -> L24 java.lang.Throwable -> L40
        Lf:
            Boolean r0 = r2.hasNext()     // Catch: java.io.IOException -> L24 java.lang.Throwable -> L40
            if (r0 == 0) goto L2e
            java.lang.Object r0 = r2.next()     // Catch: java.io.IOException -> L24 java.lang.Throwable -> L40
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.io.IOException -> L24 java.lang.Throwable -> L40
            r1.write(r0)     // Catch: java.io.IOException -> L24 java.lang.Throwable -> L40
            java.lang.String r0 = "\n"
            r1.write(r0)     // Catch: java.io.IOException -> L24 java.lang.Throwable -> L40
            goto Lf
        L24:
            r0 = move-exception
        L25:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L40
            if (r1 == 0) goto L2d
            r1.close()     // Catch: java.io.IOException -> L3c
        L2d:
            return
        L2e:
            r1.close()     // Catch: java.io.IOException -> L32
            goto L2d
        L32:
            r0 = move-exception
            goto L2d
        L34:
            r0 = move-exception
            r1 = r2
        L36:
            if (r1 == 0) goto L3b
            r1.close()     // Catch: java.io.IOException -> L3e
        L3b:
            throw r0
        L3c:
            r0 = move-exception
            goto L2d
        L3e:
            r1 = move-exception
            goto L3b
        L40:
            r0 = move-exception
            goto L36
        L42:
            r0 = move-exception
            r1 = r2
            goto L25
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.common.h.a(java.lang.String, java.util.List):Unit")
    }

    private fun a(Char c) {
        Array<Char> cArr = f1456a
        for (Int i = 0; i < 9; i++) {
            if (c == cArr[i]) {
                return true
            }
        }
        return false
    }

    fun b(File file) {
        Long jLastModified = file.lastModified()
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                Long jB = b(file2)
                if (jB > jLastModified) {
                    jLastModified = jB
                }
            }
        }
        return jLastModified
    }
}
