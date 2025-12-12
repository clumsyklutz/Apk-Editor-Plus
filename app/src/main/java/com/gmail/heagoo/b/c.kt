package com.gmail.heagoo.b

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List

class c extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    List f1426a = ArrayList()

    /* renamed from: b, reason: collision with root package name */
    private Context f1427b
    private String c
    private String d
    private o e

    constructor(Context context, String str, String str2, o oVar) {
        this.f1427b = context
        this.c = str
        this.d = str2
        this.e = oVar
        c(str2)
    }

    private fun c(String str) {
        synchronized (this.f1426a) {
            File file = File(str)
            if (!file.exists()) {
                str = this.c
                file = File(str)
            }
            Array<File> fileArrListFiles = file.listFiles()
            if (fileArrListFiles != null) {
                this.f1426a.clear()
                for (File file2 : fileArrListFiles) {
                    a aVar = a()
                    aVar.f1424a = file2.getName()
                    aVar.f1425b = file2.isDirectory()
                    if (!aVar.f1425b) {
                        file2.length()
                    }
                    this.f1426a.add(aVar)
                }
                Collections.sort(this.f1426a, b())
                if (!str.equals(this.c)) {
                    a aVar2 = a()
                    aVar2.f1424a = ".."
                    aVar2.f1425b = true
                    this.f1426a.add(0, aVar2)
                }
                this.d = str
            } else if (com.gmail.heagoo.a.c.a.c(str, Environment.getExternalStorageDirectory().getPath())) {
                this.f1426a.clear()
                Environment.getExternalStorageDirectory().getPath()
                a aVar3 = a()
                aVar3.f1424a = com.gmail.heagoo.a.c.a.d(str, Environment.getExternalStorageDirectory().getPath())
                aVar3.f1425b = true
                this.f1426a.add(aVar3)
                if (!str.equals(this.c)) {
                    a aVar4 = a()
                    aVar4.f1424a = ".."
                    aVar4.f1425b = true
                    this.f1426a.add(0, aVar4)
                }
                this.d = str
            }
        }
    }

    public final String a(List list) {
        String str
        synchronized (this.f1426a) {
            if (list != null) {
                list.addAll(this.f1426a)
                str = this.d
            } else {
                str = this.d
            }
        }
        return str
    }

    public final Unit a(String str) {
        if (!this.c.startsWith(str) || str.equals(this.c)) {
            c(str)
            notifyDataSetChanged()
        }
    }

    public final Unit b(String str) {
        synchronized (this.f1426a) {
            Int i = 0
            while (true) {
                Int i2 = i
                if (i2 >= this.f1426a.size()) {
                    break
                }
                if (((a) this.f1426a.get(i2)).f1424a.equals(str)) {
                    this.f1426a.remove(i2)
                    break
                }
                i = i2 + 1
            }
        }
        notifyDataSetChanged()
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f1426a.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f1426a.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        d dVar
        a aVar = (a) this.f1426a.get(i)
        if (view == null) {
            view = LayoutInflater.from(this.f1427b).inflate(R.layout.item_file, (ViewGroup) null)
            d dVar2 = d((Byte) 0)
            dVar2.f1428a = (ImageView) view.findViewById(R.id.file_icon)
            dVar2.f1429b = (TextView) view.findViewById(R.id.filename)
            dVar2.c = (TextView) view.findViewById(R.id.detail1)
            view.setTag(dVar2)
            dVar = dVar2
        } else {
            dVar = (d) view.getTag()
        }
        dVar.f1429b.setText(aVar.f1424a)
        if (aVar.f1424a.equals("..")) {
            dVar.f1428a.setImageResource(R.drawable.ic_file_up)
        } else if (aVar.f1425b) {
            dVar.f1428a.setImageResource(R.drawable.ic_file_folder)
        } else {
            Drawable drawableA = this.e.a(this.d, aVar)
            if (drawableA == null) {
                dVar.f1428a.setImageResource(R.drawable.ic_file_unknown)
            } else {
                dVar.f1428a.setImageDrawable(drawableA)
            }
        }
        String strB = this.e.b(this.d, aVar)
        if (strB != null) {
            dVar.c.setText(strB)
            dVar.c.setVisibility(0)
        } else {
            dVar.c.setVisibility(8)
        }
        return view
    }
}
