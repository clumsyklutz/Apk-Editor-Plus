package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.cy
import com.gmail.heagoo.apkeditor.pro.R
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class e implements cy {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f991a

    /* renamed from: b, reason: collision with root package name */
    private String f992b
    private b c
    private d d
    private ZipFile e

    constructor(ApkInfoActivity apkInfoActivity, String str, b bVar) {
        this.f991a = WeakReference(apkInfoActivity)
        this.f992b = str
        this.c = bVar
    }

    static /* synthetic */ Int a(e eVar, List list, String str) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                return -1
            }
            if (str.equals(((g) list.get(i2)).b())) {
                return i2
            }
            i = i2 + 1
        }
    }

    private fun a(List list, ZipFile zipFile) {
        f(this, list, zipFile).start()
    }

    @Override // com.gmail.heagoo.apkeditor.cy
    public final Unit a() {
        if (this.d == null || this.d.f990a == null || this.e == null) {
            return
        }
        a(this.d.f990a, this.e)
    }

    public final Unit b() throws IOException {
        Boolean z
        Boolean z2 = false
        try {
            this.e = ZipFile(this.f992b)
            ZipEntry entry = this.e.getEntry("patch.txt")
            if (entry == null) {
                this.e.close()
                this.e = null
                this.c.a(R.string.patch_error_no_entry, "patch.txt")
            }
            InputStream inputStream = this.e.getInputStream(entry)
            this.d = d.a(inputStream, this.c)
            inputStream.close()
            if (!((ApkInfoActivity) this.f991a.get()).k()) {
                Iterator it = this.d.f990a.iterator()
                Boolean zA = false
                while (true) {
                    if (!it.hasNext()) {
                        z = zA
                        break
                    }
                    zA = ((g) it.next()).a()
                    if (zA) {
                        z = zA
                        break
                    }
                }
                if (z) {
                    this.c.a(R.string.decode_dex_file, true, new Object[0])
                    ((ApkInfoActivity) this.f991a.get()).a(this)
                }
                z2 = z
            }
            if (z2) {
                return
            }
            a(this.d.f990a, this.e)
        } catch (Exception e) {
            this.c.a(R.string.general_error, e.getMessage())
            e.printStackTrace()
        }
    }

    public final List c() {
        ArrayList arrayList = ArrayList()
        if (this.d != null && this.d.f990a != null) {
            Iterator it = this.d.f990a.iterator()
            while (it.hasNext()) {
                arrayList.add(((g) it.next()).b())
            }
        }
        return arrayList
    }
}
