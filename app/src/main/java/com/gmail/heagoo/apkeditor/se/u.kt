package com.gmail.heagoo.apkeditor.se

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.cg
import com.gmail.heagoo.apkeditor.cn
import com.gmail.heagoo.apkeditor.cu
import com.gmail.heagoo.apkeditor.ey
import com.gmail.heagoo.apkeditor.fa
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.imageviewlib.ViewZipImageActivity
import java.io.Closeable
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.zip.ZipFile

class u extends BaseAdapter implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, cu, fa {

    /* renamed from: a, reason: collision with root package name */
    private Activity f1280a

    /* renamed from: b, reason: collision with root package name */
    private h f1281b
    private String c
    private List d
    private Map e
    private Map f
    private Boolean g
    private LruCache h
    private Comparator i
    private z j
    private ZipFile k
    private aa l
    private Int m
    private Boolean n
    private String o
    private String p

    constructor(Activity activity, h hVar, z zVar) {
        this.f = HashMap()
        this.g = false
        this.h = v(this, 32)
        this.i = w(this)
        this.f1280a = activity
        this.c = "/"
        this.f1281b = hVar
        this.e = zVar.c
        this.j = zVar
        this.d = (List) this.e.get(this.c)
        Collections.sort(this.d, this.i)
        this.m = R.layout.item_zipfile
        try {
            this.k = ZipFile(zVar.b())
            this.l = aa(this.k)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    constructor(Activity activity, h hVar, z zVar, Boolean z) {
        this(activity, hVar, zVar)
        this.g = true
    }

    fun a(Activity activity, String str, String str2) {
        ArrayList arrayList = ArrayList()
        cg cgVar = cg()
        cgVar.c = true
        cgVar.f931b = false
        cgVar.f930a = str2
        arrayList.add(cgVar)
        cn(activity, x(activity, str, arrayList), null, null, activity.getString(R.string.select_folder), true, false, false, null).show()
    }

    fun a(String str) {
        return str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".gif")
    }

    private fun f() {
        return "/".equals(this.c)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws IOException {
        InputStream inputStream
        this.n = false
        if (this.k != null) {
            String str = (String) this.f.get(this.o)
            if (str != null) {
                inputStream = FileInputStream(str)
            } else {
                inputStream = this.k.getInputStream(this.k.getEntry(this.o))
            }
            this.p = com.gmail.heagoo.a.c.a.d(this.f1280a, "tmp") + this.o.replace('/', '_')
            FileOutputStream fileOutputStream = FileOutputStream(this.p)
            com.gmail.heagoo.a.a aVar = new com.gmail.heagoo.a.a()
            if (inputStream != null) {
                this.n = aVar.a(inputStream, fileOutputStream)
            }
            com.gmail.heagoo.a.c.a.a((Closeable) inputStream)
            com.gmail.heagoo.a.c.a.a(fileOutputStream)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        this.f.put(str2, str)
        this.h.remove(str2)
        notifyDataSetChanged()
        ((SimpleEditActivity) this.f1280a).a()
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        Int iIndexOf = str2.indexOf(46, str2.lastIndexOf(47) + 1)
        if (iIndexOf != -1) {
            return str.endsWith(str2.substring(iIndexOf))
        }
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (!this.n) {
            Toast.makeText(this.f1280a, String.format(this.f1280a.getString(R.string.failed_to_parse_xml), this.o), 1).show()
            return
        }
        Intent intentA = com.gmail.heagoo.a.c.a.a(this.f1280a, this.p, this.j != null ? this.j.b() : null)
        com.gmail.heagoo.a.c.a.a(intentA, "displayFileName", this.o)
        com.gmail.heagoo.a.c.a.a(intentA, "extraString", this.o)
        this.f1280a.startActivityForResult(intentA, 0)
    }

    public final String c() {
        return this.c
    }

    public final Unit c(String str, String str2) {
        this.f.put(str, str2)
    }

    public final Map d() {
        return this.f
    }

    public final Unit e() {
        try {
            if (this.k != null) {
                this.k.close()
            }
        } catch (IOException e) {
        }
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        if (f()) {
            return this.d.size()
        }
        if (this.d != null) {
            return this.d.size() + 1
        }
        return 0
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        if (f()) {
            return this.d.get(i)
        }
        if (this.d != null) {
            return this.d.get(i - 1)
        }
        return null
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        t tVar
        Boolean z
        if (this.d == null) {
            return null
        }
        if (view == null) {
            view = LayoutInflater.from(this.f1280a).inflate(this.m, (ViewGroup) null)
            tVar = t()
            tVar.f1278a = (ImageView) view.findViewById(R.id.file_icon)
            tVar.f1279b = (TextView) view.findViewById(R.id.filename)
            tVar.c = (TextView) view.findViewById(R.id.detail1)
            tVar.d = view.findViewById(R.id.menu_edit)
            tVar.e = view.findViewById(R.id.menu_save)
            view.setTag(tVar)
        } else {
            tVar = (t) view.getTag()
        }
        if (!f()) {
            i--
        }
        if (i >= 0) {
            y yVar = (y) this.d.get(i)
            tVar.f1279b.setText(yVar.f1284a)
            if (yVar.f1285b) {
                tVar.f1278a.setImageResource(R.drawable.ic_file_folder)
                z = false
            } else {
                String str = yVar.f1284a
                if ((str.endsWith(".xml") || str.endsWith(".dex") || str.endsWith(".MF") || str.endsWith(".SF") || str.endsWith(".RSA") || str.endsWith(".so")) || !a(yVar.f1284a)) {
                    tVar.f1278a.setImageResource(R.drawable.ic_file_unknown)
                    z = true
                } else {
                    String str2 = this.c.substring(1) + yVar.f1284a
                    ImageView imageView = tVar.f1278a
                    Bitmap bitmapA = (Bitmap) this.h.get(str2)
                    if (bitmapA == null) {
                        String str3 = (String) this.f.get(str2)
                        bitmapA = str3 == null ? this.l.a(str2, 32, 32) : new com.gmail.heagoo.common.m().a(str3, 32, 32)
                        if (bitmapA != null) {
                            this.h.put(str2, bitmapA)
                        }
                    }
                    imageView.setImageBitmap(bitmapA)
                    z = true
                }
            }
            if (!z || this.g) {
                tVar.d.setVisibility(8)
                tVar.e.setVisibility(8)
            } else {
                tVar.d.setVisibility(0)
                tVar.d.setId(i)
                tVar.d.setOnClickListener(this)
                tVar.e.setVisibility(0)
                tVar.e.setId(this.d.size() + i)
                tVar.e.setOnClickListener(this)
            }
        } else {
            tVar.f1279b.setText("..")
            tVar.f1278a.setImageResource(R.drawable.ic_file_up)
            tVar.d.setVisibility(8)
            tVar.e.setVisibility(8)
        }
        tVar.c.setVisibility(8)
        return view
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int size
        Int id = view.getId()
        if (id < this.d.size()) {
            if (id < this.d.size()) {
                cn(this.f1280a, this, null, this.c.substring(1) + ((y) this.d.get(id)).f1284a, this.f1280a.getString(R.string.select_file_replace)).show()
            }
        } else {
            if (id >= this.d.size() * 2 || (size = id - this.d.size()) >= this.d.size()) {
                return
            }
            a(this.f1280a, this.j.b(), this.c.substring(1) + ((y) this.d.get(size)).f1284a)
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        Boolean z
        Boolean z2 = false
        if (!f()) {
            if (i == 0) {
                String str = this.c
                this.c = str.substring(0, str.lastIndexOf(47, str.length() - 2) + 1)
                this.d = (List) this.e.get(this.c)
                z2 = true
                i = -1
            } else {
                i--
            }
        }
        if (i != -1) {
            y yVar = (y) this.d.get(i)
            if (yVar.f1285b) {
                this.c += yVar.f1284a + "/"
                this.d = (List) this.e.get(this.c)
                z = true
            } else if (a(yVar.f1284a)) {
                String str2 = this.c.substring(1) + yVar.f1284a
                String str3 = (String) this.f.get(str2)
                Intent intent = Intent(this.f1280a, (Class<?>) ViewZipImageActivity.class)
                if (str3 == null) {
                    com.gmail.heagoo.a.c.a.a(intent, "zipFilePath", this.j.b())
                    com.gmail.heagoo.a.c.a.a(intent, "entryName", str2)
                } else {
                    com.gmail.heagoo.a.c.a.a(intent, "imageFilePath", str3)
                }
                this.f1280a.startActivity(intent)
                z = z2
            } else {
                if (this.g && yVar.f1284a.endsWith(".xml")) {
                    this.o = this.c.substring(1) + yVar.f1284a
                    ey(this.f1280a, this, -1).show()
                }
                z = z2
            }
        } else {
            z = z2
        }
        if (z) {
            this.f1281b.a(this.c)
            notifyDataSetChanged()
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        return false
    }
}
