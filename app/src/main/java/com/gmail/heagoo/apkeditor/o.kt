package com.gmail.heagoo.apkeditor

import android.content.Context
import android.content.res.Resources
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set

class o extends bz implements com.gmail.heagoo.apkeditor.f.c {

    /* renamed from: a, reason: collision with root package name */
    private Context f1228a

    /* renamed from: b, reason: collision with root package name */
    private String f1229b
    private String c
    private String d
    private String e
    private String f
    private String g
    private Boolean h
    private String i
    private com.gmail.heagoo.common.j l
    private com.gmail.heagoo.common.k m
    private Set mdUncString
    private Boolean p
    private com.gmail.heagoo.apkeditor.a.d q
    private Map j = HashMap()
    private List k = ArrayList()
    private Long n = 0
    private Boolean o = false

    constructor(Context context, String str, String str2) {
        this.f1228a = context
        this.f1229b = context.getFilesDir().getAbsolutePath() + "/bin"
        this.c = this.f1229b + "/aapt"
        this.d = this.f1229b + "/android.jar"
        this.e = str
        this.f = str2
        this.g = str + "/build/resource.apk"
        StringBuilder("aaptPath: ").append(this.c)
        StringBuilder("androidJarPath: ").append(this.d)
        StringBuilder("decodedFilePath: ").append(this.e)
        this.m = new com.gmail.heagoo.common.k()
    }

    private fun a(File file) {
        if (file.exists()) {
            return file.lastModified()
        }
        return 0L
    }

    private fun a(String str) {
        return "smali".equals(str) ? "classes.dex" : str.startsWith("smali_") ? str.substring(6) + ".dex" : str + ".dex"
    }

    private fun a() {
        for (File file : File(this.e).listFiles()) {
            if (file.isDirectory()) {
                String name = file.getName()
                if (name.equals("smali") || name.startsWith("smali_")) {
                    String strA = a(name)
                    Long jA = a(File(this.e + "/" + strA))
                    Long jA2 = a(File(this.e + "/build/" + strA))
                    if (jA2 <= jA) {
                        jA2 = jA
                    }
                    if (com.gmail.heagoo.common.h.b(file) > jA2) {
                        this.k.add(name)
                    }
                }
            }
        }
    }

    private fun a(Map map) {
        map.putAll(this.j)
        for (File file : File(this.e).listFiles()) {
            String name = file.getName()
            if (file.isFile() && name.endsWith(".dex") && !this.j.containsKey(name)) {
                File file2 = File(this.e + "/build/" + name)
                if (file2.isFile() && file2.exists()) {
                    map.put(name, file2.getPath())
                } else {
                    map.put(name, file.getPath())
                }
            }
        }
    }

    private fun a(Map map, File file, String str) {
        for (File file2 : file.listFiles()) {
            if (file2.isFile()) {
                map.put(str + file2.getName(), file2.getPath())
            } else if (file2.isDirectory()) {
                a(map, file2, str + file2.getName() + "/")
            }
        }
    }

    private fun b(String str) {
        this.m.f1457a++
        this.m.c = str
        this.l.a(this.m)
    }

    private fun c() {
        HashMap map = HashMap()
        com.gmail.heagoo.apkeditor.a.d dVar = this.q
        if (dVar != null) {
            try {
                dVar.a(this.f1228a, this.g, map, p(this))
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        HashMap map2 = HashMap()
        File file = File(this.e + "/assets")
        if (file.exists()) {
            a(map2, file, "assets/")
        }
        File file2 = File(this.e + "/lib")
        if (file2.exists()) {
            a(map2, file2, "lib/")
        }
        File file3 = File(this.e + "/libs")
        if (file3.exists()) {
            a(map2, file3, "libs/")
        }
        File file4 = File(this.e + "/kotlin")
        if (file4.exists()) {
            a(map2, file4, "kotlin/")
        }
        File file5 = File(this.e + "/unknown")
        if (file5.exists()) {
            a(map2, file5, "")
        }
        a(map2)
        mdNoCompress()
        HashMap map3 = HashMap()
        Set set = this.mdUncString
        if (set == null || set.isEmpty()) {
            map3 = map2
        } else {
            for (Map.Entry entry : map2.entrySet()) {
                String str = (String) entry.getKey()
                String str2 = (String) entry.getValue()
                if (this.mdUncString.contains(str)) {
                    map3.put(str + ":0", str2)
                } else {
                    map3.put(str, str2)
                }
            }
        }
        StringBuilder sb = StringBuilder()
        Int length = 0
        for (Map.Entry entry2 : map3.entrySet()) {
            String str3 = (String) entry2.getKey()
            String str4 = (String) entry2.getValue()
            sb.append(str3)
            sb.append('\n')
            sb.append(str4)
            sb.append('\n')
            length += str4.getBytes().length + str3.getBytes().length + 2
        }
        StringBuilder sb2 = StringBuilder()
        Int length2 = 0
        for (Map.Entry entry3 : map.entrySet()) {
            String str5 = (String) entry3.getKey()
            String str6 = (String) entry3.getValue()
            sb2.append(str5)
            sb2.append('\n')
            sb2.append(str6)
            sb2.append('\n')
            length2 += str6.getBytes().length + str5.getBytes().length + 2
        }
        MainActivity.md(this.f, this.g, sb.toString(), length, "", 0, sb2.toString(), length2)
    }

    private fun d() throws Resources.NotFoundException {
        String str = this.f + ".signed"
        try {
            HashMap map = HashMap()
            a.a(this.f1228a, this.f, str, map, map, HashSet())
            File file = File(this.f)
            file.delete()
            File(str).renameTo(file)
            return true
        } catch (Exception e) {
            this.i = this.f1228a.getResources().getString(R.string.sign_error) + e.getMessage()
            return false
        }
    }

    private fun e() {
        try {
            return m.a(this.f1228a, this.c, this.f1229b)
        } catch (Exception e) {
            this.i = e.getMessage()
            return false
        }
    }

    private fun mdNoCompress() {
        this.mdUncString = null
        try {
            String strD = a.d((Context) null, "tmp")
            if (File(strD, "mdUncString").exists()) {
                this.mdUncString = (Set) a.j(strD + "mdUncString")
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.f.c
    public final Unit a(Int i, Int i2) {
        if (System.currentTimeMillis() > 500) {
            this.m.c = String.format(this.f1228a.getString(R.string.assemble_dex_detail), Integer.valueOf(i), Integer.valueOf(i2))
            this.l.a(this.m)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit a(com.gmail.heagoo.apkeditor.a.d dVar) {
        this.q = dVar
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit a(com.gmail.heagoo.common.j jVar) {
        this.l = jVar
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit a(Boolean z, Boolean z2, Boolean z3, List list, Map map, Map map2, Set set, Map map3, Boolean z4) {
        this.p = z4
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit b() {
        this.o = true
        interrupt()
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x031f  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit run() {
        /*
            Method dump skipped, instructions count: 896
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.o.run():Unit")
    }
}
