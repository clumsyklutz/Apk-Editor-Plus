package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.b.a
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FilenameFilter
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Collections
import java.util.Enumeration
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class ff extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1064a

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f1065b
    private Int c
    private String d
    private String e
    private String f
    private String g
    private List h
    private FilenameFilter i
    private Map j
    private Set k
    private LruCache l
    private fk m
    private com.gmail.heagoo.apkeditor.se.aa n
    private Map o
    private Map p
    private Set q

    constructor(Context context, String str, String str2, String str3, FilenameFilter filenameFilter) {
        this(context, null, str2, str3, null, filenameFilter, null)
    }

    constructor(Context context, String str, String str2, String str3, Map map, FilenameFilter filenameFilter, fn fnVar) {
        this.h = ArrayList()
        this.k = HashSet()
        this.l = fg(this, 64)
        this.m = fk()
        this.o = HashMap()
        this.p = HashMap()
        this.q = HashSet()
        this.f1064a = WeakReference(context)
        if (fnVar != null) {
            this.f1065b = WeakReference(fnVar)
        }
        this.d = str
        this.e = str3
        this.f = str2
        this.j = map
        this.i = filenameFilter
        this.g = context.getResources().getString(R.string.resolution)
        this.c = fnVar != null ? R.layout.item_file_selectable : R.layout.item_file
        if (str != null) {
            f(str)
        }
        g(str2)
    }

    private fun a(String str, Boolean z) throws Throwable {
        String str2
        String str3
        com.gmail.heagoo.common.m mVar
        Bitmap bitmapA
        Bitmap bitmapA2 = null
        String str4 = (String) this.p.get(str)
        if (str4 == null) {
            str4 = (String) this.o.get(str)
        }
        fi fiVar = (str4 == null || (bitmapA = (mVar = new com.gmail.heagoo.common.m()).a(str4, 32, 32)) == null) ? null : fi(bitmapA, mVar.a(), mVar.b())
        if (fiVar != null) {
            bitmapA2 = fiVar.f1068a
            str2 = this.g + ": " + fiVar.f1069b + " X " + fiVar.c
        } else if (this.d == null) {
            com.gmail.heagoo.common.m mVar2 = new com.gmail.heagoo.common.m()
            bitmapA2 = mVar2.a(this.e + "/" + str, 32, 32)
            str2 = this.g + ": " + mVar2.a() + " X " + mVar2.b()
        } else if (z || !str.endsWith(".9.png")) {
            if (this.j != null && (str3 = (String) this.j.get(str)) != null) {
                str = str3
            }
            fi fiVar2 = (fi) this.l.get(str)
            if (fiVar2 == null) {
                Bitmap bitmapA3 = this.n.a(str, 32, 32)
                if (bitmapA3 != null) {
                    fiVar2 = fi(bitmapA3, this.n.a(), this.n.b())
                }
                if (fiVar2 != null) {
                    this.l.put(str, fiVar2)
                }
            }
            if (fiVar2 != null) {
                bitmapA2 = fiVar2.f1068a
                str2 = this.g + ": " + fiVar2.f1069b + " X " + fiVar2.c
            } else {
                str2 = null
            }
        } else {
            com.gmail.heagoo.common.m mVar3 = new com.gmail.heagoo.common.m()
            bitmapA2 = mVar3.a(this.e + "/" + str, 32, 32)
            str2 = this.g + ": " + mVar3.a() + " X " + mVar3.b()
        }
        return dd(bitmapA2, str2)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(File file) {
        ArrayList arrayList = ArrayList()
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    file2.delete()
                    arrayList.add(file2.getPath())
                } else {
                    arrayList.addAll(a(file2))
                }
            }
        }
        file.delete()
        return arrayList
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(File file, File file2, String str) throws Throwable {
        if (!file2.exists()) {
            file2.mkdirs()
        }
        HashMap map = HashMap()
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file3 : fileArrListFiles) {
                String name = file3.getName()
                if (file3.isFile()) {
                    File file4 = File(file2, file3.getName())
                    com.gmail.heagoo.common.h.a(file3, file4)
                    map.put(str + "/" + name, file4.getPath())
                } else {
                    map.putAll(a(file3, File(file2, file3.getName()), str + "/" + name))
                }
            }
        }
        return map
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    fun b(String str) {
        return str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".webp")
    }

    private fun f(String str) {
        try {
            ZipFile zipFile = ZipFile(str)
            this.n = new com.gmail.heagoo.apkeditor.se.aa(zipFile)
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
            while (enumerationEntries.hasMoreElements()) {
                String name = enumerationEntries.nextElement().getName()
                if (!name.startsWith("res/") && !name.startsWith("r/") && !name.startsWith("META-INF/")) {
                    Array<String> strArrSplit = name.split("/")
                    if (strArrSplit.length != 1 || (!name.equals(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME) && !name.endsWith(".dex") && !name.endsWith(".arsc"))) {
                        this.m.a(strArrSplit, true)
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private fun g(String str) {
        synchronized (this.h) {
            if (File(str).exists()) {
                i(str)
            } else {
                h(str)
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun g(String str, String str2) {
        if (this.q.contains(str)) {
            this.q.remove(str)
            this.o.put(str, str2)
        } else if (this.o.containsKey(str)) {
            Log.d("DEBUG", "error: " + str + " already exist?")
        } else {
            this.p.put(str, str2)
        }
    }

    private fun h(String str) {
        fk fkVarA = this.m.a(str.substring(this.e.length() + 1).split("/"))
        if (fkVarA != null) {
            this.h.clear()
            List<fk> list = fkVarA.f1072a
            if (list != null) {
                for (fk fkVar : list) {
                    a aVar = a()
                    aVar.f1424a = fkVar.f1073b
                    aVar.f1425b = !fkVar.c
                    aVar.c = true
                    this.h.add(aVar)
                }
                Collections.sort(this.h, new com.gmail.heagoo.b.b())
            }
            a aVar2 = a()
            aVar2.f1424a = ".."
            aVar2.f1425b = true
            this.h.add(0, aVar2)
            this.f = str
        }
    }

    private fun h(String str, String str2) {
        if (this.p.containsKey(str)) {
            this.p.put(str, str2)
        } else if (this.q.contains(str)) {
            Log.d("DEBUG", "error: " + str + " is already deleted?")
        } else {
            this.o.put(str, str2)
        }
    }

    private fun i(String str) {
        File file = File(str)
        Boolean zEquals = str.equals(this.e)
        Array<File> fileArrListFiles = (!zEquals || this.i == null) ? file.listFiles() : file.listFiles(this.i)
        if (fileArrListFiles == null) {
            if (com.gmail.heagoo.a.c.a.c(str, Environment.getExternalStorageDirectory().getPath())) {
                this.h.clear()
                Environment.getExternalStorageDirectory().getPath()
                a aVar = a()
                aVar.f1424a = com.gmail.heagoo.a.c.a.d(str, Environment.getExternalStorageDirectory().getPath())
                aVar.f1425b = true
                this.h.add(aVar)
                if (!str.equals(this.e)) {
                    a aVar2 = a()
                    aVar2.f1424a = ".."
                    aVar2.f1425b = true
                    this.h.add(0, aVar2)
                }
                this.f = str
                return
            }
            return
        }
        this.h.clear()
        for (File file2 : fileArrListFiles) {
            a aVar3 = a()
            aVar3.f1424a = file2.getName()
            aVar3.f1425b = file2.isDirectory()
            this.h.add(aVar3)
        }
        try {
            Collections.sort(this.h, new com.gmail.heagoo.b.b())
        } catch (Exception e) {
        }
        if (zEquals) {
            ArrayList arrayList = ArrayList()
            List<fk> list = this.m.f1072a
            if (list != null && !list.isEmpty()) {
                for (fk fkVar : list) {
                    a aVar4 = a()
                    aVar4.f1424a = fkVar.f1073b
                    aVar4.f1425b = !fkVar.c
                    aVar4.c = true
                    arrayList.add(aVar4)
                }
                Collections.sort(arrayList, new com.gmail.heagoo.b.b())
                this.h.addAll(arrayList)
            }
        } else {
            a aVar5 = a()
            aVar5.f1424a = ".."
            aVar5.f1425b = true
            this.h.add(0, aVar5)
        }
        this.f = str
    }

    private fun j(String str) throws Exception {
        if (str.equals("META-INF")) {
            k(str)
        }
        ZipFile zipFile = ZipFile(this.d)
        if (zipFile.getEntry(str) == null) {
            zipFile.close()
        } else {
            zipFile.close()
            k(str)
        }
    }

    private fun k(String str) throws Exception {
        throw Exception(String.format(((Context) this.f1064a.get()).getString(R.string.file_already_exist), str))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun l(String str) {
        if (this.p.containsKey(str)) {
            this.p.remove(str)
        } else if (!this.o.containsKey(str)) {
            this.q.add(str)
        } else {
            this.o.remove(str)
            this.q.add(str)
        }
    }

    public final dd a(String str) {
        return a(str.substring(this.e.length() + 1), false)
    }

    public final a a(String str, InputStream inputStream) throws Exception {
        Boolean z
        Boolean z2
        Int iLastIndexOf = str.lastIndexOf(47)
        String strSubstring = str.substring(0, iLastIndexOf)
        String strSubstring2 = str.substring(iLastIndexOf + 1)
        String strSubstring3 = strSubstring.equals(this.e) ? strSubstring2 : str.substring(this.e.length() + 1)
        if (strSubstring.equals(this.e) && this.d != null) {
            j(strSubstring2)
        }
        File file = File(strSubstring)
        String str2 = this.e + "/res"
        if (strSubstring.equals(str2) || strSubstring.startsWith(str2 + "/")) {
            z = true
        } else {
            String str3 = this.e + "/smali"
            z = strSubstring.equals(str3) || strSubstring.startsWith(StringBuilder().append(str3).append("/").toString()) || strSubstring.startsWith(StringBuilder().append(str3).append("_").toString())
        }
        if (z || (file.exists() && !strSubstring.equals(this.e))) {
            if (!file.exists()) {
                file.mkdirs()
            }
            if (File(str).exists()) {
                k(strSubstring3)
                z2 = false
            } else {
                FileOutputStream fileOutputStream = FileOutputStream(str)
                com.gmail.heagoo.a.c.a.b(inputStream, fileOutputStream)
                fileOutputStream.close()
                z2 = false
            }
        } else {
            Array<String> strArrSplit = strSubstring3.split("/")
            if (this.m.b(strArrSplit) != null) {
                k(strSubstring3)
                z2 = true
            } else {
                str = com.gmail.heagoo.a.c.a.d((Context) this.f1064a.get(), "tmp") + com.gmail.heagoo.common.s.a(8)
                FileOutputStream fileOutputStream2 = FileOutputStream(str)
                com.gmail.heagoo.a.c.a.b(inputStream, fileOutputStream2)
                fileOutputStream2.close()
                this.m.a(strArrSplit, true)
                z2 = true
            }
        }
        g(strSubstring3, str)
        return a(strSubstring2, false, z2)
    }

    public final String a(List list) {
        String str
        synchronized (this.h) {
            if (list != null) {
                list.addAll(this.h)
                str = this.f
            } else {
                str = this.f
            }
        }
        return str
    }

    public final Unit a() {
        c(this.f)
    }

    public final Unit a(Int i) {
        if (i == 0 && !this.h.isEmpty() && "..".equals(((a) this.h.get(0)).f1424a)) {
            return
        }
        if (this.k.contains(Integer.valueOf(i))) {
            this.k.remove(Integer.valueOf(i))
        } else {
            this.k.add(Integer.valueOf(i))
        }
        notifyDataSetChanged()
    }

    public final Unit a(String str, a aVar) {
        synchronized (this.h) {
            if (this.f.equals(str)) {
                this.h.add(aVar)
                notifyDataSetChanged()
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v12, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.gmail.heagoo.apkeditor.ff] */
    public final Unit a(String str, String str2) throws Throwable {
        Exception exc
        Int iLastIndexOf = str.lastIndexOf("/")
        String strSubstring = str.substring(iLastIndexOf + 1)
        String strSubstring2 = str.substring(0, iLastIndexOf)
        String r2 = null
        try {
            try {
                FileInputStream fileInputStream = FileInputStream(str2)
                try {
                    a(strSubstring2, a(str, fileInputStream))
                    r2 = String.format(((Context) this.f1064a.get()).getString(R.string.file_added), strSubstring)
                    Toast.makeText((Context) this.f1064a.get(), (CharSequence) r2, 0).show()
                    try {
                        fileInputStream.close()
                    } catch (IOException e) {
                    }
                } catch (Exception e2) {
                    exc = e2
                    Toast.makeText((Context) this.f1064a.get(), String.format(((Context) this.f1064a.get()).getString(R.string.failed_1), exc.getMessage()), 1).show()
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close()
                        } catch (IOException e3) {
                        }
                    }
                } catch (Throwable th) {
                    th = th
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close()
                        } catch (IOException e4) {
                        }
                    }
                    throw th
                }
            } catch (Exception e5) {
                exc = e5
            }
        } catch (Throwable th2) {
            th = th2
        }
    }

    public final Unit a(String str, String str2, Boolean z) throws Exception {
        if (str.equals(this.e) && this.d != null) {
            j(str2)
        }
        if (!File(str).exists() || str.equals(this.e)) {
            Array<String> strArrSplit = (str.equals(this.e) ? str2 : str.substring(this.e.length() + 1) + "/" + str2).split("/")
            if (this.m.b(strArrSplit) != null) {
                k(str2)
                return
            }
            this.m.a(strArrSplit, false)
            if (z) {
                c(str)
                return
            }
            return
        }
        File file = File(str, str2)
        if (file.exists()) {
            k(str2)
        }
        Boolean zMkdir = file.mkdir()
        if (z) {
            if (zMkdir || this.f1064a.get() == null) {
                c(str)
            } else {
                Toast.makeText((Context) this.f1064a.get(), R.string.failed, 1).show()
            }
        }
    }

    public final Unit a(Map map, Set set, Map map2) {
        if (map != null) {
            this.p = map
        }
        if (set != null) {
            this.q = set
        }
        if (map2 != null) {
            this.o = map2
        }
    }

    public final Unit a(Boolean z) {
        if (z) {
            if (!((a) this.h.get(0)).f1424a.equals("..")) {
                this.k.add(0)
            }
            for (Int i = 1; i < this.h.size(); i++) {
                this.k.add(Integer.valueOf(i))
            }
        } else {
            this.k.clear()
            if (this.f1065b.get() != null) {
                ((fn) this.f1065b.get()).b(this.k)
            }
        }
        notifyDataSetChanged()
    }

    public final Map b() {
        return this.p
    }

    public final Unit b(String str, String str2) {
        try {
            a(str, str2, true)
        } catch (Exception e) {
            Toast.makeText((Context) this.f1064a.get(), e.getMessage(), 1).show()
        }
    }

    protected final Unit b(List list) {
        Iterator it = this.h.iterator()
        Int i = 0
        while (it.hasNext()) {
            it.next()
            if (list.contains(Integer.valueOf(i))) {
                it.remove()
            }
            i++
        }
        this.k.clear()
        notifyDataSetChanged()
        if (this.f1065b.get() != null) {
            ((fn) this.f1065b.get()).b(this.k)
        }
    }

    public final Boolean b(String str, String str2, Boolean z) {
        List listA
        List listC
        ArrayList arrayList = null
        Boolean z2 = true
        if (z) {
            if (!str.equals(this.e)) {
                str2 = str.substring(this.e.length() + 1) + "/" + str2
            }
            listC = this.m.c(str2.split("/"))
        } else {
            String str3 = str + "/" + str2
            File file = File(str3)
            if (!file.exists()) {
                listA = null
            } else if (file.isFile()) {
                file.delete()
                ArrayList arrayList2 = ArrayList()
                arrayList2.add(str3)
                listA = arrayList2
            } else {
                listA = a(file)
            }
            if (listA != null) {
                arrayList = ArrayList()
                Iterator it = listA.iterator()
                while (it.hasNext()) {
                    arrayList.add(((String) it.next()).substring(this.e.length() + 1))
                }
            }
            if (listA != null) {
                listC = arrayList
            } else {
                z2 = false
                listC = arrayList
            }
        }
        if (listC != null) {
            Iterator it2 = listC.iterator()
            while (it2.hasNext()) {
                l((String) it2.next())
            }
        }
        return z2
    }

    public final Map c() {
        return this.o
    }

    public final Unit c(String str) {
        if (!this.e.startsWith(str) || str.equals(this.e)) {
            g(str)
            notifyDataSetChanged()
        }
    }

    public final Unit c(String str, String str2) {
        h(str, str2)
    }

    protected final Unit c(List list) {
        ArrayList arrayList = ArrayList()
        String strA = a(arrayList)
        ArrayList arrayList2 = ArrayList()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            Int iIntValue = ((Integer) it.next()).intValue()
            a aVar = (a) arrayList.get(iIntValue)
            if (aVar != null) {
                b(strA, aVar.f1424a, aVar.c)
                arrayList2.add(Integer.valueOf(iIntValue))
            }
        }
        b(arrayList2)
    }

    public final String d(String str) {
        String str2 = (String) this.o.get(str)
        return str2 == null ? (String) this.p.get(str) : str2
    }

    public final Set d() {
        return this.q
    }

    public final Unit d(String str, String str2) {
        FileOutputStream fileOutputStream
        FileInputStream fileInputStream
        FileInputStream fileInputStream2 = null
        String strSubstring = str.substring(this.e.length() + 1)
        if (File(str).exists()) {
            try {
                fileInputStream = FileInputStream(str2)
                try {
                    fileOutputStream = FileOutputStream(str)
                } catch (IOException e) {
                    e = e
                    fileOutputStream = null
                    fileInputStream2 = fileInputStream
                } catch (Throwable th) {
                    th = th
                    fileOutputStream = null
                }
                try {
                    com.gmail.heagoo.a.c.a.b(fileInputStream, fileOutputStream)
                    h(strSubstring, str)
                    Toast.makeText((Context) this.f1064a.get(), R.string.file_replaced, 0).show()
                    a(fileInputStream)
                    a(fileOutputStream)
                } catch (IOException e2) {
                    e = e2
                    fileInputStream2 = fileInputStream
                    try {
                        e.printStackTrace()
                        a(fileInputStream2)
                        a(fileOutputStream)
                        notifyDataSetChanged()
                    } catch (Throwable th2) {
                        th = th2
                        fileInputStream = fileInputStream2
                        a(fileInputStream)
                        a(fileOutputStream)
                        throw th
                    }
                } catch (Throwable th3) {
                    th = th3
                    a(fileInputStream)
                    a(fileOutputStream)
                    throw th
                }
            } catch (IOException e3) {
                e = e3
                fileOutputStream = null
            } catch (Throwable th4) {
                th = th4
                fileOutputStream = null
                fileInputStream = null
            }
        } else {
            try {
                String str3 = com.gmail.heagoo.a.c.a.d((Context) this.f1064a.get(), "tmp") + com.gmail.heagoo.common.s.a(8)
                com.gmail.heagoo.common.h.a(str2, str3)
                h(strSubstring, str3)
                Toast.makeText((Context) this.f1064a.get(), R.string.file_replaced, 0).show()
            } catch (Exception e4) {
                e4.printStackTrace()
            }
        }
        notifyDataSetChanged()
    }

    public final Set e() {
        return this.k
    }

    public final Unit e(String str, String str2) {
        ey((Activity) this.f1064a.get(), fh(this, str, str2), R.string.folder_replaced).show()
    }

    public final Boolean e(String str) {
        if (File(str).exists() || this.e.equals(str)) {
            return true
        }
        return this.m.a(str.substring(this.e.length() + 1).split("/")) != null
    }

    public final Unit f(String str, String str2) {
        if (this.p != null) {
            for (Map.Entry entry : this.p.entrySet()) {
                if (((String) entry.getValue()).startsWith(str)) {
                    entry.setValue(str2 + ((String) entry.getValue()).substring(str.length()))
                }
            }
        }
        if (this.o != null) {
            for (Map.Entry entry2 : this.o.entrySet()) {
                if (((String) entry2.getValue()).startsWith(str)) {
                    entry2.setValue(str2 + ((String) entry2.getValue()).substring(str.length()))
                }
            }
        }
    }

    public final Boolean f() {
        return this.f.equals(this.e)
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.h.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.h.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) throws Throwable {
        fj fjVar
        String str
        String str2
        a aVar = (a) this.h.get(i)
        if (view == null) {
            view = LayoutInflater.from((Context) this.f1064a.get()).inflate(this.c, (ViewGroup) null)
            fj fjVar2 = fj((Byte) 0)
            fjVar2.f1070a = (ImageView) view.findViewById(R.id.file_icon)
            fjVar2.f1071b = (TextView) view.findViewById(R.id.filename)
            fjVar2.c = (TextView) view.findViewById(R.id.detail1)
            fjVar2.d = (CheckBox) view.findViewById(R.id.checkBox)
            view.setTag(fjVar2)
            fjVar = fjVar2
        } else {
            fjVar = (fj) view.getTag()
        }
        fjVar.f1071b.setText(aVar.f1424a)
        if (aVar.f1424a.equals("..")) {
            fjVar.f1070a.setImageResource(R.drawable.ic_file_up)
            str = null
        } else if (aVar.f1425b) {
            fjVar.f1070a.setImageResource(R.drawable.ic_file_folder)
            str = null
        } else {
            if (aVar.f1424a.endsWith(".xml")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_xml)
            }
            if (aVar.f1424a.endsWith(".smali")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_smali)
            }
            if (aVar.f1424a.endsWith(".htm")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_html)
            }
            if (aVar.f1424a.endsWith(".html")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_html)
            }
            if (aVar.f1424a.endsWith(".rar")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_zip)
            }
            if (aVar.f1424a.endsWith(".zip")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_zip)
            }
            if (aVar.f1424a.endsWith(".dex")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_dex)
            }
            if (aVar.f1424a.endsWith(".properties")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".css")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".java")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".js")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".MF")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".SF")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".txt")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
            }
            if (aVar.f1424a.endsWith(".apk")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_apk)
            }
            if (aVar.f1424a.endsWith(".ttf")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_ttf)
            }
            if (b(aVar.f1424a)) {
                String str3 = this.f
                String str4 = aVar.f1424a
                if (str3.equals(this.e)) {
                    str2 = str4
                } else {
                    str2 = str3.substring(this.e.endsWith("/") ? this.e.length() : this.e.length() + 1) + "/" + str4
                }
                dd ddVarA = a(str2, aVar.c)
                fjVar.f1070a.setImageBitmap(ddVarA.f953a)
                str = ddVarA.f954b
            } else if (aVar.f1424a.endsWith(".xml")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_xml)
                str = null
            } else if (aVar.f1424a.endsWith(".smali")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_smali)
                str = null
            } else if (aVar.f1424a.endsWith(".htm") || aVar.f1424a.endsWith(".html")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_html)
                str = null
            } else if (aVar.f1424a.endsWith(".rar") || aVar.f1424a.endsWith(".zip")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_zip)
                str = null
            } else if (aVar.f1424a.endsWith(".dex")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_dex)
                str = null
            } else if (aVar.f1424a.endsWith(".properties") || aVar.f1424a.endsWith(".css") || aVar.f1424a.endsWith(".java") || aVar.f1424a.endsWith(".js") || aVar.f1424a.endsWith(".MF") || aVar.f1424a.endsWith(".SF") || aVar.f1424a.endsWith(".txt")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_text)
                str = null
            } else if (aVar.f1424a.endsWith(".apk")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_apk)
                str = null
            } else if (aVar.f1424a.endsWith(".ttf")) {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_ttf)
                str = null
            } else {
                fjVar.f1070a.setImageResource(R.drawable.ic_file_unknown)
                str = null
            }
        }
        if (str != null) {
            fjVar.c.setText(str)
            fjVar.c.setVisibility(0)
        } else {
            fjVar.c.setVisibility(8)
        }
        if (fjVar.d != null) {
            if (i == 0 && aVar.f1424a.equals("..")) {
                fjVar.d.setVisibility(4)
            } else {
                fjVar.d.setVisibility(0)
                fjVar.d.setId(i)
                fjVar.d.setOnCheckedChangeListener(this)
                fjVar.d.setChecked(this.k.contains(Integer.valueOf(i)))
            }
        }
        return view
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final Unit onCheckedChanged(CompoundButton compoundButton, Boolean z) {
        Int id = compoundButton.getId()
        if (z) {
            this.k.add(Integer.valueOf(id))
        } else {
            this.k.remove(Integer.valueOf(id))
        }
        if (this.f1065b != null) {
            ((fn) this.f1065b.get()).b(this.k)
        }
    }
}
