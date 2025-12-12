package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ExpandableListView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.heagoo.apkeditor.ac.EditTextWithTip
import com.gmail.heagoo.apkeditor.ac.a
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.List

class fv extends Dialog implements View.OnClickListener, AdapterView.OnItemLongClickListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    /* renamed from: a, reason: collision with root package name */
    private TextView f1087a

    /* renamed from: b, reason: collision with root package name */
    private EditTextWithTip f1088b
    private ExpandableListView c
    private ei d
    private LinearLayout e
    private WeakReference f
    private String g
    private List h
    private String i
    private Boolean j
    private ArrayList k
    private a l

    constructor(ApkInfoActivity apkInfoActivity, String str, List list, String str2, Boolean z) {
        super(apkInfoActivity, R.style.Dialog_FullWindow)
        this.k = ArrayList()
        requestWindowFeature(1)
        this.f = WeakReference(apkInfoActivity)
        this.g = str
        this.h = list
        this.i = str2
        this.j = z
        if (!this.g.endsWith("/")) {
            StringBuilder().append(str).append("/")
        }
        View viewInflate = LayoutInflater.from(apkInfoActivity).inflate(R.layout.dialog_txt_searchresult, (ViewGroup) null)
        this.f1087a = (TextView) viewInflate.findViewById(R.id.title)
        this.f1088b = (EditTextWithTip) viewInflate.findViewById(R.id.et_replaceall)
        this.c = (ExpandableListView) viewInflate.findViewById(R.id.lv_matchedfiles)
        this.e = (LinearLayout) viewInflate.findViewById(R.id.searching_layout)
        this.c.setVisibility(4)
        gf(this, this.g, this.h, this.i, this.j).execute(new Object[0])
        viewInflate.findViewById(R.id.btn_replaceall).setOnClickListener(this)
        this.l = a(apkInfoActivity, "search_replace_with")
        ((EditTextWithTip) viewInflate.findViewById(R.id.et_replaceall)).setAdapter(this.l)
        setContentView(viewInflate)
    }

    static /* synthetic */ Unit a(fv fvVar, Int i) {
        ff ffVar = ((ApkInfoActivity) fvVar.f.get()).e
        String str = (String) fvVar.k.get(i)
        Int iLastIndexOf = str.lastIndexOf(47)
        ffVar.b(iLastIndexOf != -1 ? str.substring(0, iLastIndexOf) : "", str.substring(iLastIndexOf + 1), false)
        fvVar.d.c(i)
    }

    static /* synthetic */ Unit b(fv fvVar) {
        fvVar.f1087a.setText(String.format(((ApkInfoActivity) fvVar.f.get()).getString(R.string.str_files_found), Integer.valueOf(fvVar.k.size()), fvVar.i))
        fvVar.d = ei(fvVar.f, fvVar.c, fvVar.g, fvVar.k, fvVar.i)
        fvVar.c.setAdapter(fvVar.d)
        fvVar.c.setOnGroupClickListener(fvVar)
        fvVar.c.setOnChildClickListener(fvVar)
        fvVar.c.setOnItemLongClickListener(fvVar)
        fvVar.c.setVisibility(0)
        fvVar.e.setVisibility(4)
    }

    static /* synthetic */ Unit b(fv fvVar, Int i) {
        if (i < fvVar.k.size()) {
            ((ApkInfoActivity) fvVar.f.get()).h((String) fvVar.k.get(i))
        }
    }

    static /* synthetic */ Unit c(fv fvVar, Int i) {
        if (i < fvVar.k.size()) {
            ((ApkInfoActivity) fvVar.f.get()).a((String) fvVar.k.get(i), gd(fvVar, i))
        }
    }

    protected final Unit a(String str) {
        ey((Activity) this.f.get(), fy(this, str), -1)
    }

    public final Unit b(String str) {
        ((ApkInfoActivity) this.f.get()).a(str, (String) null)
    }

    @Override // android.widget.ExpandableListView.OnChildClickListener
    public final Boolean onChildClick(ExpandableListView expandableListView, View view, Int i, Int i2, Long j) {
        Intent intentA
        ArrayList arrayListA = this.d.a()
        eh ehVar = (eh) this.d.getChild(i, i2)
        if (ehVar != null) {
            if (arrayListA.size() > 100) {
                String str = (String) arrayListA.get(i)
                ApkInfoActivity apkInfoActivity = (ApkInfoActivity) this.f.get()
                intentA = com.gmail.heagoo.a.c.a.a(apkInfoActivity, str, apkInfoActivity.l())
                com.gmail.heagoo.a.c.a.a(intentA, "startLine", StringBuilder().append(ehVar.f1027a).toString())
            } else {
                ApkInfoActivity apkInfoActivity2 = (ApkInfoActivity) this.f.get()
                intentA = com.gmail.heagoo.a.c.a.a(apkInfoActivity2, arrayListA, i, apkInfoActivity2.l())
                com.gmail.heagoo.a.c.a.a(intentA, "fileList", arrayListA)
                com.gmail.heagoo.a.c.a.a(intentA, "curFileIndex", i)
                ArrayList<Integer> arrayList = new ArrayList<>(arrayListA.size())
                for (Int i3 = 0; i3 < i; i3++) {
                    arrayList.add(-1)
                }
                arrayList.add(Integer.valueOf(ehVar.f1027a))
                for (Int i4 = i + 1; i4 < arrayListA.size(); i4++) {
                    arrayList.add(-1)
                }
                Bundle bundle = Bundle()
                bundle.putIntegerArrayList("startLineList", arrayList)
                intentA.putExtras(bundle)
            }
            com.gmail.heagoo.a.c.a.a(intentA, "searchString", this.i)
            ((ApkInfoActivity) this.f.get()).startActivityForResult(intentA, 0)
        }
        return false
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (view.getId() == R.id.btn_replaceall) {
            String string = this.f1088b.getText().toString()
            AlertDialog.Builder builder = new AlertDialog.Builder((Context) this.f.get())
            builder.setMessage(String.format(((ApkInfoActivity) this.f.get()).getString(R.string.sure_to_replace_all), this.i, string))
            builder.setPositiveButton(android.R.string.ok, fw(this, string))
            builder.setNegativeButton(android.R.string.cancel, fx(this))
            builder.show()
        }
    }

    @Override // android.widget.ExpandableListView.OnGroupClickListener
    public final Boolean onGroupClick(ExpandableListView expandableListView, View view, Int i, Long j) {
        if (expandableListView.isGroupExpanded(i) || this.d.a(i)) {
            return false
        }
        ge(this, (String) this.d.getGroup(i), this.d.b(), i).execute(new Object[0])
        return true
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        if (ExpandableListView.getPackedPositionType(j) != 0) {
            return true
        }
        adapterView.setOnCreateContextMenuListener(fz(this, ExpandableListView.getPackedPositionGroup(j)))
        return false
    }
}
