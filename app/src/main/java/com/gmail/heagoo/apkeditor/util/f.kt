package com.gmail.heagoo.apkeditor.util

import android.content.Context
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.apkeditor.gzd
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.regex.Matcher
import java.util.regex.Pattern

abstract class f {

    /* renamed from: a, reason: collision with root package name */
    protected String f1313a

    /* renamed from: b, reason: collision with root package name */
    protected String f1314b
    HashSet c = HashSet()
    Int d = 0

    f(String str, String str2) {
        this.f1313a = str
        if (!str.endsWith("/")) {
            this.f1313a += "/"
        }
        this.f1314b = str2
    }

    private fun a(File file, List list) {
        String name = file.getName()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            y yVar = (y) it.next()
            if (name.startsWith(yVar.f1342b)) {
                String strSubstring = name.substring(yVar.f1342b.length())
                if (strSubstring.equals("")) {
                    return yVar.c + strSubstring
                }
                if ((strSubstring.charAt(0) != '.' || strSubstring.indexOf(46, 1) != -1) && !strSubstring.equals(".9.png")) {
                }
                return yVar.c + strSubstring
            }
        }
        return null
    }

    private fun a(String str, String str2, Map map) {
        ArrayList arrayList = ArrayList()
        Matcher matcher = Pattern.compile(str2).matcher(str)
        for (Int iEnd = 0; matcher.find(iEnd); iEnd = matcher.end()) {
            String str3 = (String) map.get(matcher.group(1))
            if (str3 != null) {
                arrayList.add(g(matcher.start(1), matcher.end(1), str3))
            }
        }
        if (arrayList.isEmpty()) {
            return null
        }
        return b(str, arrayList)
    }

    protected static Unit a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream
        try {
            fileOutputStream = FileOutputStream(str)
        } catch (IOException e) {
            e = e
            fileOutputStream = null
        } catch (Throwable th) {
            th = th
            fileOutputStream = null
            a(fileOutputStream)
            throw th
        }
        try {
            try {
                fileOutputStream.write(str2.getBytes())
                this.c.add(str)
                a(fileOutputStream)
            } catch (Throwable th2) {
                th = th2
                a(fileOutputStream)
                throw th
            }
        } catch (IOException e2) {
            e = e2
            e.printStackTrace()
            a(fileOutputStream)
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(java.io.File r12, java.util.Map r13) throws java.lang.Throwable {
        /*
            r11 = this
            r10 = 10
            r2 = 1
            r9 = -1
            r1 = 0
            com.gmail.heagoo.common.w r0 = new com.gmail.heagoo.common.w     // Catch: java.io.IOException -> Lb1
            java.lang.String r3 = r12.getPath()     // Catch: java.io.IOException -> Lb1
            r0.<init>(r3)     // Catch: java.io.IOException -> Lb1
            java.lang.String r5 = r0.b()     // Catch: java.io.IOException -> Lb1
            r0 = 64
            r3 = 0
            Int r0 = r5.indexOf(r0, r3)     // Catch: java.io.IOException -> Lb1
            if (r0 == r9) goto Lae
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch: java.io.IOException -> Lb1
            r6.<init>()     // Catch: java.io.IOException -> Lb1
            r3 = r0
        L21:
            if (r3 == r9) goto L96
            Int r0 = r3 + 1
            r4 = r0
        L26:
            Int r0 = r5.length()     // Catch: java.io.IOException -> Lb1
            if (r4 >= r0) goto Lb9
            Char r0 = r5.charAt(r4)     // Catch: java.io.IOException -> Lb1
            r7 = 47
            if (r0 != r7) goto L58
            r0 = r2
        L35:
            if (r0 == 0) goto L8e
            Int r0 = r3 + 1
            java.lang.String r7 = r5.substring(r0, r4)     // Catch: java.io.IOException -> Lb1
            Int r0 = r4 + 1
            r3 = r0
        L40:
            Int r0 = r5.length()     // Catch: java.io.IOException -> Lb1
            if (r3 >= r0) goto L66
            Char r0 = r5.charAt(r3)     // Catch: java.io.IOException -> Lb1
            r8 = 34
            if (r0 == r8) goto L66
            r8 = 60
            if (r0 == r8) goto L66
            if (r0 == r10) goto L66
            Int r0 = r3 + 1
            r3 = r0
            goto L40
        L58:
            r7 = 34
            if (r0 == r7) goto Lb9
            r7 = 60
            if (r0 == r7) goto Lb9
            if (r0 == r10) goto Lb9
            Int r0 = r4 + 1
            r4 = r0
            goto L26
        L66:
            java.lang.Object r0 = r13.get(r7)     // Catch: java.io.IOException -> Lb1
            java.util.Map r0 = (java.util.Map) r0     // Catch: java.io.IOException -> Lb1
            if (r0 == 0) goto L86
            Int r7 = r4 + 1
            java.lang.String r7 = r5.substring(r7, r3)     // Catch: java.io.IOException -> Lb1
            java.lang.Object r0 = r0.get(r7)     // Catch: java.io.IOException -> Lb1
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.io.IOException -> Lb1
            if (r0 == 0) goto L86
            com.gmail.heagoo.apkeditor.util.g r7 = new com.gmail.heagoo.apkeditor.util.g     // Catch: java.io.IOException -> Lb1
            Int r4 = r4 + 1
            r7.<init>(r4, r3, r0)     // Catch: java.io.IOException -> Lb1
            r6.add(r7)     // Catch: java.io.IOException -> Lb1
        L86:
            r0 = 64
            Int r0 = r5.indexOf(r0, r3)     // Catch: java.io.IOException -> Lb1
            r3 = r0
            goto L21
        L8e:
            r0 = 64
            Int r0 = r5.indexOf(r0, r4)     // Catch: java.io.IOException -> Lb1
            r3 = r0
            goto L21
        L96:
            Boolean r0 = r6.isEmpty()     // Catch: java.io.IOException -> Lb1
            if (r0 != 0) goto Lae
            java.lang.String r0 = b(r5, r6)     // Catch: java.io.IOException -> Lb1
            r3 = r0
        La1:
            if (r3 == 0) goto Lb7
            r0 = r2
        La4:
            if (r0 == 0) goto Lad
            java.lang.String r2 = r12.getPath()     // Catch: java.io.IOException -> Lb1
            r11.a(r2, r3)     // Catch: java.io.IOException -> Lb1
        Lad:
            return r0
        Lae:
            r0 = 0
            r3 = r0
            goto La1
        Lb1:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r1
            goto Lad
        Lb7:
            r0 = r1
            goto La4
        Lb9:
            r0 = r1
            goto L35
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.util.f.a(java.io.File, java.util.Map):Boolean")
    }

    private fun a(String str) {
        Array<String> strArr = {"bools.xml", "colors.xml", "dimens.xml", "ids.xml", "integers.xml", "public.xml", "strings.xml"}
        for (Int i = 0; i < 7; i++) {
            if (strArr[i].equals(str)) {
                return true
            }
        }
        return false
    }

    private fun b(String str, List list) {
        StringBuilder sb = StringBuilder()
        Int i = 0
        Iterator it = list.iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                sb.append(str.substring(i2))
                return sb.toString()
            }
            g gVar = (g) it.next()
            sb.append(str.substring(i2, gVar.f1315a))
            sb.append(gVar.c)
            i = gVar.f1316b
        }
    }

    private fun b(List list) throws Throwable {
        File file
        Array<File> fileArrListFiles = File(this.f1313a + "res").listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory() && file2.getName().startsWith("values")) {
                    file = File(file2, "styles.xml")
                    if (!file.exists()) {
                        file = null
                    }
                } else {
                    file = null
                }
                if (file != null) {
                    String path = file.getPath()
                    try {
                        String strB = new com.gmail.heagoo.common.w(path).b()
                        Iterator it = list.iterator()
                        String str = strB
                        Boolean z = false
                        while (it.hasNext()) {
                            y yVar = (y) it.next()
                            Int length = str.length()
                            String strReplace = str.replace("item name=\"" + yVar.f1342b + "\"", "item name=\"" + yVar.c + "\"")
                            z = strReplace.length() != length ? true : z
                            str = strReplace
                        }
                        if (z) {
                            a(path, str)
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    protected final Int a(List list) {
        Array<File> fileArrListFiles = File(this.f1313a + "res").listFiles()
        if (fileArrListFiles == null) {
            return 0
        }
        HashMap map = HashMap()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            y yVar = (y) it.next()
            Map map2 = (Map) map.get(yVar.f1341a)
            if (map2 == null) {
                map2 = HashMap()
                map.put(yVar.f1341a, map2)
            }
            map2.put(yVar.f1342b, yVar.c)
        }
        Int i = 0
        for (File file : fileArrListFiles) {
            String name = file.getName()
            if (!name.startsWith("raw")) {
                Boolean zEquals = "values".equals(name)
                Array<File> fileArrListFiles2 = file.listFiles()
                if (fileArrListFiles2 != null) {
                    for (File file2 : fileArrListFiles2) {
                        if (file2.isFile()) {
                            String name2 = file2.getName()
                            if (name2.endsWith(".xml") && ((!zEquals || !a(name2)) && a(file2, map))) {
                                i++
                            }
                        }
                    }
                }
            }
        }
        File file3 = File(this.f1313a + ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)
        return (file3.exists() && a(file3, map)) ? i + 1 : i
    }

    public abstract String a(Context context)

    protected final Unit a(String str, List list) throws Throwable {
        Array<File> fileArrListFiles
        File file
        String strA
        HashMap map = HashMap()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            y yVar = (y) it.next()
            map.put(yVar.f1342b, yVar.c)
        }
        String str2 = "plurals".equals(str) ? "plurals.xml" : str + "s.xml"
        Array<File> fileArrListFiles2 = File(this.f1313a + "res").listFiles()
        if (fileArrListFiles2 != null) {
            for (File file2 : fileArrListFiles2) {
                if (file2.isDirectory() && file2.getName().startsWith("values")) {
                    File file3 = File(file2, str2)
                    file = !file3.exists() ? null : file3
                } else {
                    file = null
                }
                if (file != null) {
                    try {
                        String strB = new com.gmail.heagoo.common.w(file.getPath()).b()
                        if (gzd.COLUMN_ID.equals(str)) {
                            strA = a(strB, "name=\"(.*?)\"", map)
                        } else if ("drawable".equals(str) || "dimen".equals(str) || "color".equals(str)) {
                            String strA2 = a(strB, "@" + str + "/(.*?)<", map)
                            if (strA2 != null) {
                                strB = strA2
                            }
                            strA = a(strB, "name=\"(.*?)\"", map)
                        } else {
                            strA = a(strB, str + " name=\"(.*?)\"", map)
                        }
                        if (strA != null) {
                            a(file.getPath(), strA)
                        }
                    } catch (IOException e) {
                        e.printStackTrace()
                    }
                }
            }
        }
        if ("attr".equals(str)) {
            b(list)
            return
        }
        Array<File> fileArrListFiles3 = File(this.f1313a + "res").listFiles()
        if (fileArrListFiles3 != null) {
            for (File file4 : fileArrListFiles3) {
                if (!file4.isFile()) {
                    String name = file4.getName()
                    if ((name.startsWith(str) || name.startsWith(str + "-")) && (fileArrListFiles = file4.listFiles()) != null) {
                        for (File file5 : fileArrListFiles) {
                            String strA3 = a(file5, list)
                            if (strA3 != null) {
                                file5.renameTo(File(file4, strA3))
                                this.d++
                            }
                        }
                    }
                }
            }
        }
    }

    public abstract Boolean a()

    public abstract Unit b()

    public abstract Boolean c()

    public abstract Map d()
}
