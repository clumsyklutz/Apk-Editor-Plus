package com.gmail.heagoo.apkeditor

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.preference.PreferenceManager
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set

class m extends bz implements com.gmail.heagoo.apkeditor.f.c {

    /* renamed from: a, reason: collision with root package name */
    private Context f1223a

    /* renamed from: b, reason: collision with root package name */
    private String f1224b
    private String c
    private String d
    private String e
    private String f
    private String g
    private String h
    private Boolean i
    private String j
    private Boolean l
    private Boolean m
    private Boolean n
    private List o
    private Map p
    private Map q
    private Set r
    private Map s
    private com.gmail.heagoo.common.j t
    private com.gmail.heagoo.common.k u
    private Boolean x
    private com.gmail.heagoo.apkeditor.a.d y
    private Map k = HashMap()
    private Long v = 0
    private Boolean w = false

    constructor(Context context, String str, String str2, String str3) {
        this.f1223a = context
        this.f1224b = context.getFilesDir().getAbsolutePath() + "/bin"
        this.c = this.f1224b + "/aapt"
        this.d = this.f1224b + "/android.jar"
        this.e = str
        this.f = str2
        this.g = str3
        this.h = str2
        StringBuilder("aaptPath: ").append(this.c)
        StringBuilder("androidJarPath: ").append(this.d)
        StringBuilder("decodedFilePath: ").append(this.e)
        this.u = new com.gmail.heagoo.common.k()
    }

    private fun a(String str) {
        this.u.f1457a++
        this.u.c = str
        this.t.a(this.u)
    }

    private fun a(Map map) {
        Iterator it = map.entrySet().iterator()
        while (it.hasNext()) {
            String str = (String) ((Map.Entry) it.next()).getKey()
            if (str.equals(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)) {
                it.remove()
            } else if (str.startsWith("res/")) {
                it.remove()
            }
        }
    }

    fun a(Context context) {
        try {
            String packageName = context.getPackageName()
            String strB = b(context, packageName)
            StringBuffer stringBuffer = StringBuffer()
            stringBuffer.append('i')
            stringBuffer.append('n')
            stringBuffer.append('g')
            if (strB.endsWith(stringBuffer.toString())) {
                return packageName.endsWith("pro")
            }
            return false
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }

    protected static Boolean a(Context context, String str) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        Int i = defaultSharedPreferences.getInt("aapt-no-version-vectors", -1)
        if (i == 1) {
            return true
        }
        if (i == 0) {
            return false
        }
        com.gmail.heagoo.common.c cVar = new com.gmail.heagoo.common.c()
        cVar.a((Object) new Array<String>{str}, (Array<String>) null, (Integer) 5000, false)
        String strA = cVar.a()
        String strB = cVar.b()
        Boolean z = (strA != null && strA.contains("--no-version-vectors")) || (strB != null && strB.contains("--no-version-vectors"))
        SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit()
        editorEdit.putInt("aapt-no-version-vectors", z ? 1 : 0)
        editorEdit.apply()
        return z
    }

    fun a(Context context, String str, String str2) throws Exception {
        String str3 = null
        try {
            str3 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
        } catch (PackageManager.NameNotFoundException e) {
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("info", 0)
        Boolean z = sharedPreferences.getBoolean("initialized", false)
        String string = sharedPreferences.getString("version", "")
        if (!z || !string.equals(str3)) {
            b(context, str, str2)
            SharedPreferences.Editor editorEdit = sharedPreferences.edit()
            editorEdit.putBoolean("initialized", true)
            editorEdit.putString("version", str3)
            editorEdit.commit()
        }
        return true
    }

    static ArrayList aaptNoCompress() {
        ArrayList arrayList = ArrayList()
        Set<String> set = null
        try {
            String strD = a.d((Context) null, "tmp")
            if (File(strD, "aaptUncString").exists()) {
                set = (Set) a.j(strD + "aaptUncString")
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        if (set != null && !set.isEmpty()) {
            for (String str : set) {
                arrayList.add("-0")
                arrayList.add(str)
            }
        }
        return arrayList
    }

    private fun b(Context context, String str) {
        String str2
        try {
            StringBuffer stringBuffer = StringBuffer()
            stringBuffer.append('g')
            stringBuffer.append('e')
            stringBuffer.append('t')
            stringBuffer.append("Package")
            stringBuffer.append('M')
            stringBuffer.append('a')
            stringBuffer.append('n')
            stringBuffer.append('a')
            stringBuffer.append('g')
            stringBuffer.append('e')
            stringBuffer.append('r')
            PackageManager packageManager = (PackageManager) Context.class.getMethod(stringBuffer.toString(), new Class[0]).invoke(context, new Object[0])
            StringBuffer stringBuffer2 = StringBuffer()
            stringBuffer2.append('g')
            stringBuffer2.append('e')
            stringBuffer2.append('t')
            stringBuffer2.append("In")
            stringBuffer2.append("stall")
            stringBuffer2.append("er")
            stringBuffer2.append("Package")
            stringBuffer2.append('N')
            stringBuffer2.append('a')
            stringBuffer2.append('m')
            stringBuffer2.append('e')
            str2 = (String) PackageManager.class.getMethod(stringBuffer2.toString(), String.class).invoke(packageManager, str)
        } catch (Exception e) {
            e.printStackTrace()
            str2 = null
        }
        return str2 == null ? "" : str2
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 14 */
    private fun b(Context context, String str, String str2) throws Exception {
        try {
            File file = File(str)
            File parentFile = file.getParentFile()
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            InputStream inputStreamOpen = context.getAssets().open("aapt")
            FileOutputStream fileOutputStream = FileOutputStream(file)
            a.b(inputStreamOpen, fileOutputStream)
            inputStreamOpen.close()
            fileOutputStream.close()
            file.setExecutable(true)
            InputStream inputStreamOpen2 = context.getAssets().open("android.zip")
            String str3 = str2 + "/tmp.zip"
            FileOutputStream fileOutputStream2 = FileOutputStream(str2 + "/tmp.zip")
            a.b(inputStreamOpen2, fileOutputStream2)
            inputStreamOpen2.close()
            fileOutputStream2.close()
            a.a.b.a.a.x.b(str3, str2)
            File(str3).delete()
            a.a.b.a.a.x.b(str2 + "/android.jar", str2)
            return true
        } catch (Exception e) {
            e.printStackTrace()
            throw Exception("Can not copy file: " + e.getMessage())
        }
    }

    private fun d() {
        if (this.s == null || this.s.isEmpty()) {
            return
        }
        if (!this.p.isEmpty()) {
            HashMap map = HashMap()
            for (Map.Entry entry : this.p.entrySet()) {
                String str = (String) this.s.get(entry.getKey())
                if (str != null) {
                    map.put(str, entry.getValue())
                } else {
                    map.put(entry.getKey(), entry.getValue())
                }
            }
            this.p = map
        }
        if (!this.q.isEmpty()) {
            HashMap map2 = HashMap()
            for (Map.Entry entry2 : this.q.entrySet()) {
                String str2 = (String) this.s.get(entry2.getKey())
                if (str2 != null) {
                    map2.put(str2, entry2.getValue())
                } else {
                    map2.put(entry2.getKey(), entry2.getValue())
                }
            }
            this.q = map2
        }
        if (this.r.isEmpty()) {
            return
        }
        HashSet hashSet = HashSet()
        for (String str3 : this.r) {
            String str4 = (String) this.s.get(str3)
            if (str4 != null) {
                hashSet.add(str4)
            } else {
                hashSet.add(str3)
            }
        }
        this.r = hashSet
    }

    private fun e() {
        a(this.p)
        a(this.q)
        Iterator it = this.r.iterator()
        while (it.hasNext()) {
            if (((String) it.next()).startsWith("res/")) {
                it.remove()
            }
        }
    }

    private fun f() {
        Int length = 0
        HashMap map = HashMap()
        if (this.y != null) {
            try {
                this.y.a(this.f1223a, this.h, map, n(this))
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        StringBuilder sb = StringBuilder()
        Int length2 = 0
        for (Map.Entry entry : this.s.entrySet()) {
            String str = (String) entry.getKey()
            String str2 = (String) entry.getValue()
            sb.append(str)
            sb.append('\n')
            sb.append(str2)
            sb.append('\n')
            length2 += str2.getBytes().length + str.getBytes().length + 2
        }
        StringBuilder sb2 = StringBuilder()
        for (Map.Entry entry2 : this.p.entrySet()) {
            String str3 = (String) entry2.getKey()
            String str4 = (String) entry2.getValue()
            if (!str3.startsWith("res/") || !str3.endsWith(".xml")) {
                sb2.append(str3)
                sb2.append('\n')
                sb2.append(str4)
                sb2.append('\n')
                length += str4.getBytes().length + str3.getBytes().length + 2
            }
        }
        for (Map.Entry entry3 : this.q.entrySet()) {
            String str5 = (String) entry3.getKey()
            if (!str5.equals(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)) {
                String str6 = (String) entry3.getValue()
                if (!str5.startsWith("res/") || (!str5.endsWith(".xml") && !str5.endsWith(".9.png"))) {
                    sb2.append(str5)
                    sb2.append('\n')
                    sb2.append(str6)
                    sb2.append('\n')
                    length += str6.getBytes().length + str5.getBytes().length + 2
                }
            }
        }
        Int length3 = length
        for (Map.Entry entry4 : map.entrySet()) {
            String str7 = (String) entry4.getKey()
            String str8 = (String) entry4.getValue()
            sb2.append((String) entry4.getKey())
            sb2.append('\n')
            sb2.append((String) entry4.getValue())
            sb2.append('\n')
            length3 += str7.getBytes().length + str8.getBytes().length + 2
        }
        MainActivity.mg(this.h, this.f, sb2.toString(), length3, sb.toString(), length2)
    }

    private fun g() throws Resources.NotFoundException {
        this.q.putAll(this.k)
        try {
            a.a(this.f1223a, this.h, this.g, (Map<String, String>) this.q, (Map<String, String>) this.p, (Set<String>) this.r)
            return true
        } catch (Exception e) {
            this.j = this.f1223a.getResources().getString(R.string.sign_error) + e.getMessage()
            return false
        }
    }

    private fun h() {
        this.q.putAll(this.k)
        StringBuilder sb = StringBuilder()
        Int length = 0
        for (Map.Entry entry : this.p.entrySet()) {
            String str = (String) entry.getKey()
            String str2 = (String) entry.getValue()
            sb.append(str)
            sb.append('\n')
            sb.append(str2)
            sb.append('\n')
            length += str2.getBytes().length + str.getBytes().length + 2
        }
        StringBuilder sb2 = StringBuilder()
        Int length2 = 0
        for (String str3 : this.r) {
            sb2.append(str3)
            sb2.append('\n')
            length2 += str3.getBytes().length + 1
        }
        StringBuilder sb3 = StringBuilder()
        Int length3 = 0
        for (Map.Entry entry2 : this.q.entrySet()) {
            String str4 = (String) entry2.getKey()
            String str5 = (String) entry2.getValue()
            sb3.append(str4)
            sb3.append('\n')
            sb3.append(str5)
            sb3.append('\n')
            length3 += str5.getBytes().length + str4.getBytes().length + 2
        }
        MainActivity.md(this.g, this.h, sb.toString(), length, sb2.toString(), length2, sb3.toString(), length3)
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.f.c
    public final Unit a(Int i, Int i2) {
        if (System.currentTimeMillis() > 500) {
            this.u.c = String.format(this.f1223a.getString(R.string.assemble_dex_detail), Integer.valueOf(i), Integer.valueOf(i2))
            this.t.a(this.u)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit a(com.gmail.heagoo.apkeditor.a.d dVar) {
        this.y = dVar
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit a(com.gmail.heagoo.common.j jVar) {
        this.t = jVar
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit a(Boolean z, Boolean z2, Boolean z3, List list, Map map, Map map2, Set set, Map map3, Boolean z4) {
        this.l = z
        this.m = z2
        this.n = z3
        this.o = list
        this.p = map
        this.r = set
        this.q = map2
        this.s = map3
        this.x = z4
    }

    public final Boolean a() {
        try {
            return a(this.f1223a, this.c, this.f1224b)
        } catch (Exception e) {
            this.j = e.getMessage()
            return false
        }
    }

    @Override // com.gmail.heagoo.apkeditor.bz
    public final Unit b() {
        this.w = true
        interrupt()
    }

    public final String c() {
        return this.j
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0157 A[PHI: r3
  0x0157: PHI (r3v1 Boolean) = (r3v0 Boolean), (r3v4 Boolean) binds: [B:20:0x0041, B:43:0x0155] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0237  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit run() {
        /*
            Method dump skipped, instructions count: 873
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.m.run():Unit")
    }
}
