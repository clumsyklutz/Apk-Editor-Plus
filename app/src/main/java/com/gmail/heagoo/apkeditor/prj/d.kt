package com.gmail.heagoo.apkeditor.prj

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.ApkInfoExActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Iterator
import java.util.List
import java.util.Map

final class d extends BaseAdapter implements AdapterView.OnItemClickListener {

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f1236b
    private val c = ArrayList()
    private SimpleDateFormat d

    d(ProjectListActivity projectListActivity, List list) {
        this.f1236b = WeakReference(projectListActivity)
        Iterator it = list.iterator()
        while (it.hasNext()) {
            this.c.add((f) it.next())
        }
    }

    private fun a(f fVar) {
        return !File(fVar.f1240b).exists() ? String.format(((ProjectListActivity) this.f1236b.get()).getString(R.string.prj_error_apk_notfound), fVar.f1240b) : File(fVar.c).exists() ? null : String.format(((ProjectListActivity) this.f1236b.get()).getString(R.string.prj_error_decode_dir_notfound), fVar.c)
    }

    final Int a() {
        return this.c.size()
    }

    final Unit a(List list) {
        Iterator it = this.c.iterator()
        while (it.hasNext()) {
            if (!list.contains(it.next())) {
                it.remove()
            }
        }
        Iterator it2 = list.iterator()
        while (it2.hasNext()) {
            f fVar = (f) it2.next()
            if (!this.c.contains(fVar)) {
                this.c.add(fVar)
            }
        }
    }

    final Unit a(Map map) {
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey()
            for (f fVar : this.c) {
                if (str.equals(fVar.f1240b)) {
                    fVar.e = (Drawable) entry.getValue()
                }
            }
        }
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.c.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.c.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        e eVar
        f fVar = (f) this.c.get(i)
        if (view == null) {
            view = LayoutInflater.from((Context) this.f1236b.get()).inflate(R.layout.item_project, (ViewGroup) null)
            eVar = e((Byte) 0)
            eVar.f1237a = (ImageView) view.findViewById(R.id.app_icon)
            eVar.f1238b = (TextView) view.findViewById(R.id.app_name)
            eVar.c = (TextView) view.findViewById(R.id.app_desc1)
            eVar.d = (TextView) view.findViewById(R.id.app_desc2)
            eVar.e = view.findViewById(R.id.menu_delete)
            view.setTag(eVar)
        } else {
            eVar = (e) view.getTag()
        }
        if (fVar.e != null) {
            eVar.f1237a.setImageDrawable(fVar.e)
        }
        eVar.f1238b.setText(fVar.f1239a)
        String strA = a(fVar)
        String str = strA != null ? strA : fVar.f1240b
        if ("".equals(str)) {
            eVar.c.setVisibility(8)
        } else {
            eVar.c.setVisibility(0)
            eVar.c.setText(str)
        }
        if (strA != null) {
            if (eVar.c.getTag() == null) {
                eVar.c.setTag(Integer.valueOf(eVar.c.getCurrentTextColor()))
            }
            eVar.c.setTextColor(-48060)
        } else {
            Integer num = (Integer) eVar.c.getTag()
            if (num != null) {
                eVar.c.setTextColor(num.intValue())
            }
        }
        TextView textView = eVar.d
        Long j = fVar.d
        Calendar calendar = Calendar.getInstance()
        calendar.setTimeInMillis(j)
        if (this.d == null) {
            this.d = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        }
        textView.setText(this.d.format(calendar.getTime()))
        eVar.d.setVisibility(0)
        eVar.e.setVisibility(0)
        eVar.e.setTag(Integer.valueOf(i))
        eVar.e.setOnClickListener((View.OnClickListener) this.f1236b.get())
        return view
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        String strA = a((f) this.c.get(i))
        if (strA != null) {
            Toast.makeText((Context) this.f1236b.get(), strA, 1).show()
            return
        }
        Intent intent = Intent((Context) this.f1236b.get(), (Class<?>) ApkInfoExActivity.class)
        com.gmail.heagoo.a.c.a.a(intent, "projectName", ((f) this.c.get(i)).f1239a)
        ((ProjectListActivity) this.f1236b.get()).startActivity(intent)
    }
}
