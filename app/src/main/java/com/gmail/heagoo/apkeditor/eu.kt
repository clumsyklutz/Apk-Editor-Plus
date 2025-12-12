package com.gmail.heagoo.apkeditor

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import jadx.core.deobf.Deobfuscator
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class eu extends Dialog implements View.OnClickListener, com.gmail.heagoo.apkeditor.e.b {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1046a

    /* renamed from: b, reason: collision with root package name */
    private String f1047b
    private String c
    private View d
    private TextView e
    private TextView f
    private Button g
    private View i
    private TextView j
    private com.gmail.heagoo.apkeditor.a.g k
    private com.gmail.heagoo.apkeditor.e.e l
    private Map m
    private ProgressBar n

    constructor(ApkInfoActivity apkInfoActivity) {
        super(apkInfoActivity)
        this.f1047b = null
        this.c = null
        this.m = HashMap()
        requestWindowFeature(1)
        this.f1046a = WeakReference(apkInfoActivity)
        View viewInflate = LayoutInflater.from(apkInfoActivity).inflate(R.layout.dlg_patch, (ViewGroup) null)
        this.d = viewInflate.findViewById(R.id.tv_curpatch)
        this.d.setOnClickListener(this)
        this.e = (TextView) viewInflate.findViewById(R.id.tv_patch_path)
        ((Button) viewInflate.findViewById(R.id.btn_close)).setOnClickListener(this)
        this.g = (Button) viewInflate.findViewById(R.id.btn_select_apply)
        this.g.setOnClickListener(this)
        this.n = (ProgressBar) viewInflate.findViewById(R.id.progress_patch)
        this.f = (TextView) viewInflate.findViewById(R.id.tv_save_patches)
        this.f.setOnClickListener(this)
        this.i = viewInflate.findViewById(R.id.log_layout)
        this.j = (TextView) viewInflate.findViewById(R.id.tv_patchlog)
        setContentView(viewInflate)
        Int i = apkInfoActivity.getResources().getDisplayMetrics().widthPixels
        Window window = getWindow()
        window.setLayout((i * 7) / 8, -2)
        window.setFlags(128, 128)
    }

    private fun a(com.gmail.heagoo.apkeditor.a.g gVar, Int i) {
        if (gVar.u != null && gVar.u.containsKey(Integer.valueOf(i))) {
            i = ((Integer) gVar.u.get(Integer.valueOf(i))).intValue()
        }
        if (i >= gVar.o.size()) {
            return null
        }
        String str = (String) gVar.o.get(i)
        return str.startsWith(Deobfuscator.CLASS_NAME_SEPARATOR) ? gVar.e + str : !str.contains(Deobfuscator.CLASS_NAME_SEPARATOR) ? gVar.e + Deobfuscator.CLASS_NAME_SEPARATOR + str : str
    }

    static /* synthetic */ Unit a(eu euVar, String str) throws Throwable {
        euVar.i.setVisibility(0)
        String strC = euVar.c(str)
        if (strC != null) {
            euVar.j.setText(strC)
        }
        euVar.c = str
        euVar.e.setText(euVar.c)
        euVar.g.setText(R.string.apply_the_patch)
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(String str, Boolean z, Boolean z2) {
        ((ApkInfoActivity) this.f1046a.get()).runOnUiThread(ew(this, z2, str, z))
    }

    private fun a(ZipFile zipFile) throws IOException {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (IOException e) {
            }
        }
    }

    private fun b(String str) throws Throwable {
        InputStream inputStreamOpen
        IOException iOException
        Boolean z
        FileOutputStream fileOutputStream = null
        g()
        String str2 = this.f1047b + str
        try {
            try {
                inputStreamOpen = ((ApkInfoActivity) this.f1046a.get()).getAssets().open(str)
                try {
                    FileOutputStream fileOutputStream2 = FileOutputStream(str2)
                    try {
                        a.b(inputStreamOpen, fileOutputStream2)
                        a(inputStreamOpen)
                        a(fileOutputStream2)
                        z = true
                    } catch (IOException e) {
                        fileOutputStream = fileOutputStream2
                        iOException = e
                        Toast.makeText((Context) this.f1046a.get(), iOException.getMessage(), 0).show()
                        a(inputStreamOpen)
                        a(fileOutputStream)
                        z = false
                        return z
                    } catch (Throwable th) {
                        th = th
                        fileOutputStream = fileOutputStream2
                        a(inputStreamOpen)
                        a(fileOutputStream)
                        throw th
                    }
                } catch (IOException e2) {
                    iOException = e2
                }
            } catch (IOException e3) {
                iOException = e3
                inputStreamOpen = null
            } catch (Throwable th2) {
                th = th2
                inputStreamOpen = null
            }
            return z
        } catch (Throwable th3) {
            th = th3
        }
    }

    private fun c(String str) throws Throwable {
        InputStream inputStream
        ZipFile zipFile
        Throwable th
        String strA = null
        try {
            try {
                zipFile = ZipFile(str)
            } catch (Exception e) {
                e = e
                inputStream = null
                zipFile = null
            } catch (Throwable th2) {
                inputStream = null
                zipFile = null
                th = th2
            }
            try {
                ZipEntry entry = zipFile.getEntry("patch.txt")
                if (entry == null) {
                    a(R.string.patch_error_no_entry, "patch.txt")
                }
                inputStream = zipFile.getInputStream(entry)
            } catch (Exception e2) {
                e = e2
                inputStream = null
            } catch (Throwable th3) {
                inputStream = null
                th = th3
                a(inputStream)
                a(zipFile)
                throw th
            }
            try {
                strA = a.a(inputStream)
                a(inputStream)
                a(zipFile)
            } catch (Exception e3) {
                e = e3
                a(R.string.general_error, e.getMessage())
                a(inputStream)
                a(zipFile)
                return strA
            }
            return strA
        } catch (Throwable th4) {
            th = th4
        }
    }

    private fun g() {
        if (this.f1047b == null) {
            try {
                this.f1047b = a.d((Context) this.f1046a.get(), "patches")
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    private fun h() {
        g()
        cn((Context) this.f1046a.get(), ev(this), ".zip", null, ((ApkInfoActivity) this.f1046a.get()).getString(R.string.select_patch), false, false, false, "patch", File(this.f1047b).exists() ? this.f1047b : null).show()
    }

    private fun i() throws Throwable {
        ZipFile zipFile
        InputStream inputStream = null
        try {
            try {
                zipFile = ZipFile(((ApkInfoActivity) this.f1046a.get()).l())
            } catch (Exception e) {
                e = e
                zipFile = null
            } catch (Throwable th) {
                th = th
                zipFile = null
                a(inputStream)
                a(zipFile)
                throw th
            }
            try {
                inputStream = zipFile.getInputStream(zipFile.getEntry(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME))
                com.gmail.heagoo.apkeditor.a.i iVar = new com.gmail.heagoo.apkeditor.a.i(inputStream)
                iVar.b()
                this.k = iVar.a()
                a(inputStream)
                a(zipFile)
            } catch (Exception e2) {
                e = e2
                e.printStackTrace()
                a(inputStream)
                a(zipFile)
            }
        } catch (Throwable th2) {
            th = th2
            a(inputStream)
            a(zipFile)
            throw th
        }
    }

    static ProgressBar progressPatch(eu euVar) {
        return euVar.n
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final String a(Int i) {
        return ((ApkInfoActivity) this.f1046a.get()).getString(i)
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final String a(String str) {
        return (String) this.m.get(str)
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final Unit a() {
        ((ApkInfoActivity) this.f1046a.get()).runOnUiThread(ex(this))
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final Unit a(Int i, Boolean z, Object... objArr) {
        String str = String.format(((ApkInfoActivity) this.f1046a.get()).getString(i), objArr)
        a(z ? "\n" + str + "\n" : str + "\n", z, false)
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final Unit a(Int i, Object... objArr) {
        a(String.format(((ApkInfoActivity) this.f1046a.get()).getString(i), objArr) + "\n", false, true)
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final Unit a(String str, String str2) {
        this.m.put(str, str2)
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final Unit a(String str, Boolean z, Object... objArr) {
        a(String.format(str, objArr) + "\n", false, false)
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final String b() {
        return ((ApkInfoActivity) this.f1046a.get()).i()
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final String c() throws Throwable {
        String str
        if (this.k == null) {
            i()
        }
        if (this.k != null && (str = this.k.f) != null) {
            return str.startsWith(Deobfuscator.CLASS_NAME_SEPARATOR) ? this.k.e + str : !str.contains(Deobfuscator.CLASS_NAME_SEPARATOR) ? this.k.e + Deobfuscator.CLASS_NAME_SEPARATOR + str : str
        }
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final List d() throws Throwable {
        if (this.k == null) {
            i()
        }
        if (this.k == null) {
            return null
        }
        ArrayList arrayList = ArrayList()
        for (Map.Entry entry : this.k.q.entrySet()) {
            if (((Integer) entry.getValue()).intValue() == 0) {
                String strA = a(this.k, ((Integer) entry.getKey()).intValue())
                if (!arrayList.contains(strA)) {
                    arrayList.add(strA)
                }
            }
        }
        return arrayList
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final List e() throws Throwable {
        if (this.k == null) {
            i()
        }
        if (this.k == null) {
            return null
        }
        ArrayList arrayList = ArrayList()
        Iterator it = this.k.t.iterator()
        while (it.hasNext()) {
            String strA = a(this.k, ((Integer) it.next()).intValue())
            if (!arrayList.contains(strA)) {
                arrayList.add(strA)
            }
        }
        return arrayList
    }

    @Override // com.gmail.heagoo.apkeditor.e.b
    public final List f() {
        if (this.l != null) {
            return this.l.c()
        }
        return null
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) throws IOException {
        Int id = view.getId()
        if (id == R.id.btn_close) {
            dismiss()
            return
        }
        if (id == R.id.tv_curpatch) {
            h()
            return
        }
        if (id != R.id.btn_select_apply) {
            if (id != R.id.tv_save_patches || (!(b("patch_app_rename.zip") | b("patch_data_editor.zip") | b("patch_new_entrance.zip") | b("patch_my_font.zip") | b("patch_mem_editor.zip") | b("patch_bypass_sigcheck.zip") | b("patch_launcher_toast.zip")) && !b("patch_script_example.zip"))) {
                return
            }
            Toast.makeText((Context) this.f1046a.get(), String.format(((ApkInfoActivity) this.f1046a.get()).getString(R.string.patch_examples_copied), this.f1047b), 0).show()
            return
        }
        if (this.c == null) {
            h()
            return
        }
        this.j.setText("")
        this.g.setEnabled(false)
        this.g.setVisibility(8)
        this.n.setVisibility(0)
        this.l = new com.gmail.heagoo.apkeditor.e.e((ApkInfoActivity) this.f1046a.get(), this.c, this)
        this.l.b()
    }
}
