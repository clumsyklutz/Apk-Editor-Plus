package com.gmail.heagoo.apkeditor

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Set

class fo extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, fn {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1077a

    /* renamed from: b, reason: collision with root package name */
    private TextView f1078b
    private View c
    private TextView d
    private ListView e
    private ef f
    private LinearLayout g
    private Button h
    private Button i
    private View j
    private View k
    private String l
    private List m
    private String n
    private Boolean o
    private ArrayList p

    constructor(ApkInfoActivity apkInfoActivity, String str, List list, String str2, Boolean z) {
        super(apkInfoActivity, R.style.Dialog_FullWindow)
        this.p = ArrayList()
        HashSet()
        requestWindowFeature(1)
        this.f1077a = WeakReference(apkInfoActivity)
        this.l = str
        this.m = list
        this.n = str2
        this.o = z
        if (!this.l.endsWith("/")) {
            StringBuilder().append(str).append("/")
        }
        View viewInflate = LayoutInflater.from(apkInfoActivity).inflate(R.layout.dlg_filename_searchret, (ViewGroup) null)
        this.f1078b = (TextView) viewInflate.findViewById(R.id.title)
        this.c = viewInflate.findViewById(R.id.res_header_selection)
        this.d = (TextView) viewInflate.findViewById(R.id.selection_tip)
        this.e = (ListView) viewInflate.findViewById(R.id.file_list)
        this.g = (LinearLayout) viewInflate.findViewById(R.id.searching_layout)
        this.j = viewInflate.findViewById(R.id.menu_done)
        this.k = viewInflate.findViewById(R.id.menu_select)
        this.j.setOnClickListener(this)
        this.k.setOnClickListener(this)
        this.h = (Button) viewInflate.findViewById(R.id.btn_close)
        this.i = (Button) viewInflate.findViewById(R.id.btn_delete)
        this.h.setOnClickListener(this)
        this.i.setOnClickListener(this)
        this.e.setVisibility(4)
        fu(this, this.l, this.m, this.n).execute(new Object[0])
        setContentView(viewInflate)
    }

    private fun a() {
        this.f1078b.setVisibility(0)
        this.c.setVisibility(8)
        this.i.setVisibility(8)
        this.f1078b.setText(String.format(((ApkInfoActivity) this.f1077a.get()).getString(R.string.str_files_found), Integer.valueOf(this.p.size()), this.n))
    }

    static /* synthetic */ Unit a(fo foVar, Int i) {
        ArrayList arrayList = ArrayList()
        arrayList.add(Integer.valueOf(i))
        foVar.a(arrayList)
    }

    private fun a(List list) {
        ff ffVar = ((ApkInfoActivity) this.f1077a.get()).e
        Iterator it = list.iterator()
        while (it.hasNext()) {
            String str = (String) this.p.get(((Integer) it.next()).intValue())
            Int iLastIndexOf = str.lastIndexOf(47)
            ffVar.b(iLastIndexOf != -1 ? str.substring(0, iLastIndexOf) : "", str.substring(iLastIndexOf + 1), false)
        }
        ArrayList arrayList = ArrayList()
        for (Int i = 0; i < this.p.size(); i++) {
            if (!list.contains(Integer.valueOf(i))) {
                arrayList.add(this.p.get(i))
            }
        }
        this.p = arrayList
        this.f.a(this.p, list)
        if (this.f.e()) {
            a()
        }
    }

    static /* synthetic */ Unit b(fo foVar, Int i) {
        if (i < foVar.p.size()) {
            ((ApkInfoActivity) foVar.f1077a.get()).h((String) foVar.p.get(i))
        }
    }

    static /* synthetic */ Unit c(fo foVar) {
        foVar.f1078b.setText(String.format(((ApkInfoActivity) foVar.f1077a.get()).getString(R.string.str_files_found), Integer.valueOf(foVar.p.size()), foVar.n))
        foVar.f = ef((ApkInfoActivity) foVar.f1077a.get(), foVar, foVar.l, foVar.p)
        foVar.e.setAdapter((ListAdapter) foVar.f)
        foVar.e.setOnItemClickListener(foVar)
        foVar.e.setOnItemLongClickListener(foVar)
        foVar.e.setVisibility(0)
        foVar.g.setVisibility(4)
    }

    static /* synthetic */ Unit c(fo foVar, Int i) {
        if (i < foVar.p.size()) {
            ((ApkInfoActivity) foVar.f1077a.get()).a((String) foVar.p.get(i), fp(foVar))
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fn
    public final Unit b(Set set) {
        if (set.isEmpty()) {
            a()
            return
        }
        this.d.setText(String.format(((ApkInfoActivity) this.f1077a.get()).getString(R.string.num_items_selected), Integer.valueOf(set.size())))
        this.f1078b.setVisibility(8)
        this.c.setVisibility(0)
        this.i.setVisibility(0)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_close) {
            dismiss()
            return
        }
        if (id == R.id.btn_delete) {
            a(this.f.a())
            return
        }
        if (id == R.id.menu_done) {
            this.f.c()
            a()
        } else if (id == R.id.menu_select) {
            if (!this.f.d()) {
                this.f.b()
            } else {
                this.f.c()
                a()
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        String str
        Int iLastIndexOf
        if (i < this.p.size() && (iLastIndexOf = (str = (String) this.p.get(i)).lastIndexOf("/")) != -1) {
            ((ApkInfoActivity) this.f1077a.get()).a(str.substring(0, iLastIndexOf), str.substring(iLastIndexOf + 1), false)
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        adapterView.setOnCreateContextMenuListener(fq(this, i))
        return false
    }
}
