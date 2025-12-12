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
import com.gmail.heagoo.apkeditor.cn
import com.gmail.heagoo.apkeditor.cu
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.DynamicExpandListView
import com.gmail.heagoo.imageviewlib.ViewZipImageActivity
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.zip.ZipFile

class i extends BaseAdapter implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, cu {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1264a

    /* renamed from: b, reason: collision with root package name */
    private Activity f1265b
    private List c
    private HashMap d
    private z e
    private aa f
    private ZipFile g
    private Map h = HashMap()
    private LruCache i = j(this, 32)
    private Int j = R.layout.item_zipfile

    constructor(DynamicExpandListView dynamicExpandListView, Activity activity, z zVar) {
        this.f1264a = WeakReference(dynamicExpandListView)
        this.f1265b = activity
        this.e = zVar
        this.c = zVar.f1286a
        this.d = zVar.f1287b
        try {
            this.g = ZipFile(zVar.b())
            this.f = aa(this.g)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Int i) {
        String str = (String) this.c.get(i)
        g gVar = (g) this.d.get(str)
        Intent intent = Intent(this.f1265b, (Class<?>) ViewZipImageActivity.class)
        if (gVar.f1263b == null) {
            com.gmail.heagoo.a.c.a.a(intent, "zipFilePath", this.e.b())
            com.gmail.heagoo.a.c.a.a(intent, "entryName", gVar.c + "/" + str)
        } else {
            com.gmail.heagoo.a.c.a.a(intent, "imageFilePath", gVar.f1263b)
        }
        this.f1265b.startActivity(intent)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Int i) {
        String str = (String) this.c.get(i)
        u.a(this.f1265b, this.e.b(), ((g) this.d.get(str)).c + "/" + str)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c(Int i) {
        cn(this.f1265b, this, null, (String) this.c.get(i), this.f1265b.getString(R.string.select_file_replace)).show()
    }

    public final Map a() {
        return this.h
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        g gVar = (g) this.d.get(str2)
        gVar.f1263b = str
        Iterator it = gVar.f1262a.iterator()
        while (it.hasNext()) {
            this.h.put(((String) it.next()) + "/" + str2, str)
        }
        this.i.remove(str2)
        DynamicExpandListView dynamicExpandListView = (DynamicExpandListView) this.f1264a.get()
        if (dynamicExpandListView != null) {
            dynamicExpandListView.a()
        }
        ((SimpleEditActivity) this.f1265b).a()
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return u.a(str)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    protected final Unit finalize() throws Throwable {
        if (this.g != null) {
            try {
                this.g.close()
            } catch (IOException e) {
            }
        }
        super.finalize()
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.d.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.d.get(Integer.valueOf(i))
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        t tVar
        Bitmap bitmapA
        String str = (String) this.c.get(i)
        if (view == null) {
            view = LayoutInflater.from(this.f1265b).inflate(this.j, (ViewGroup) null)
            t tVar2 = t()
            tVar2.f1278a = (ImageView) view.findViewById(R.id.file_icon)
            tVar2.f1279b = (TextView) view.findViewById(R.id.filename)
            tVar2.c = (TextView) view.findViewById(R.id.detail1)
            tVar2.d = view.findViewById(R.id.menu_edit)
            tVar2.e = view.findViewById(R.id.menu_save)
            view.setTag(tVar2)
            tVar = tVar2
        } else {
            tVar = (t) view.getTag()
        }
        tVar.f1279b.setText(str)
        tVar.d.setId(i)
        tVar.d.setOnClickListener(this)
        tVar.e.setId(this.c.size() + i)
        tVar.e.setOnClickListener(this)
        g gVar = (g) this.d.get(str)
        o oVar = (o) this.i.get(str)
        if (oVar != null) {
            bitmapA = oVar.f1271a
        } else {
            bitmapA = gVar.f1263b == null ? this.f.a(gVar.c + "/" + str, 32, 32) : new com.gmail.heagoo.common.m().a(gVar.f1263b, 32, 32)
            o oVar2 = o()
            oVar2.f1271a = bitmapA
            this.i.put(str, oVar2)
        }
        tVar.f1278a.setImageBitmap(bitmapA)
        tVar.c.setText(gVar.a())
        return view
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id < this.c.size()) {
            c(id)
        } else if (id < (this.c.size() << 1)) {
            b(id - this.c.size())
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        a(i)
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        adapterView.setOnCreateContextMenuListener(k(this, i))
        return false
    }
}
