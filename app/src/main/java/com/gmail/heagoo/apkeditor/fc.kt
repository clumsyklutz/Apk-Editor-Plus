package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ExpandableListView
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set

class fc {

    /* renamed from: a, reason: collision with root package name */
    private Context f1061a

    /* renamed from: b, reason: collision with root package name */
    private CheckBox f1062b
    private CheckBox c
    private CheckBox d
    private Boolean e
    private Boolean f
    private Boolean g
    private Int h = 0
    private Int i = 0
    private Int j = 0
    private Int k = 0
    private Int l = 0
    private Int m = 0
    private Map n
    private Map o
    private Map p
    private Set q

    constructor(Context context, Boolean z, Boolean z2, Map map, Map map2, Set set) {
        this.f1061a = context
        this.e = z
        this.f = z2
        a(map, map2, set)
    }

    private fun a(Map map, Map map2, Set set) {
        this.g = false
        this.q = HashSet()
        this.n = HashMap()
        this.o = HashMap()
        this.p = HashMap()
        Iterator it = map.entrySet().iterator()
        while (it.hasNext()) {
            String str = (String) ((Map.Entry) it.next()).getKey()
            if (str.startsWith("res/")) {
                if (!this.g && !ApkInfoActivity.b(str)) {
                    this.g = true
                }
                this.h++
            } else {
                String strA = ApkInfoActivity.a(str, this.q)
                if (strA != null) {
                    Integer num = (Integer) this.n.get(strA)
                    if (num == null) {
                        this.n.put(strA, 1)
                    } else {
                        this.n.put(strA, Integer.valueOf(num.intValue() + 1))
                    }
                } else {
                    this.k++
                }
            }
        }
        Iterator it2 = map2.entrySet().iterator()
        while (it2.hasNext()) {
            String str2 = (String) ((Map.Entry) it2.next()).getKey()
            if (str2.equals(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME) || str2.startsWith("res/")) {
                if (!this.g && !ApkInfoActivity.b(str2)) {
                    this.g = true
                }
                this.j++
            } else {
                String strA2 = ApkInfoActivity.a(str2, this.q)
                if (strA2 != null) {
                    Integer num2 = (Integer) this.p.get(strA2)
                    if (num2 == null) {
                        this.p.put(strA2, 1)
                    } else {
                        this.p.put(strA2, Integer.valueOf(num2.intValue() + 1))
                    }
                } else {
                    this.m++
                }
            }
        }
        Iterator it3 = set.iterator()
        while (it3.hasNext()) {
            String str3 = (String) it3.next()
            if (str3.startsWith("res/")) {
                if (!this.g && !ApkInfoActivity.b(str3)) {
                    this.g = true
                }
                this.i++
            } else {
                String strA3 = ApkInfoActivity.a(str3, this.q)
                if (strA3 != null) {
                    Integer num3 = (Integer) this.o.get(strA3)
                    if (num3 == null) {
                        this.o.put(strA3, 1)
                    } else {
                        this.o.put(strA3, Integer.valueOf(num3.intValue() + 1))
                    }
                } else {
                    this.l++
                }
            }
        }
    }

    private fun b() {
        ArrayList arrayList = ArrayList()
        if (this.e) {
            arrayList.add(eq(this.f1061a.getString(R.string.string), -1, -1, -1))
        } else {
            arrayList.add(eq(this.f1061a.getString(R.string.string), 0, 0, 0))
        }
        arrayList.add(eq(this.f1061a.getString(R.string.resource), this.h, this.i, this.j))
        if (this.f) {
            arrayList.add(eq(this.f1061a.getString(R.string.manifest), -1, -1, -1))
        } else {
            arrayList.add(eq(this.f1061a.getString(R.string.manifest), 0, 0, 0))
        }
        for (String str : this.q) {
            Integer num = (Integer) this.n.get(str)
            Integer num2 = (Integer) this.o.get(str)
            Integer num3 = (Integer) this.p.get(str)
            arrayList.add(eq(str, num == null ? 0 : num.intValue(), num2 == null ? 0 : num2.intValue(), num3 == null ? 0 : num3.intValue()))
        }
        if (this.k > 0 || this.l > 0 || this.m > 0) {
            arrayList.add(eq(this.f1061a.getString(R.string.others), this.k, this.l, this.m))
        }
        return arrayList
    }

    @SuppressLint({"InflateParams"})
    public final Unit a() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.f1061a)
        builder.setTitle(R.string.rebuild_the_apk)
        View viewInflate = LayoutInflater.from(this.f1061a).inflate(R.layout.dlg_rebuild_confirm, (ViewGroup) null)
        ExpandableListView expandableListView = (ExpandableListView) viewInflate.findViewById(R.id.modificationList)
        List listB = b()
        expandableListView.setAdapter(ep(this.f1061a, listB))
        for (Int i = 0; i < listB.size(); i++) {
            expandableListView.expandGroup(i)
        }
        this.f1062b = (CheckBox) viewInflate.findViewById(R.id.cb_rebuild_dex)
        this.c = (CheckBox) viewInflate.findViewById(R.id.cb_rebuild_res)
        this.d = (CheckBox) viewInflate.findViewById(R.id.cb_resign)
        this.f1062b.setChecked(!this.q.isEmpty())
        this.c.setChecked(this.e || this.f || this.g)
        this.f1062b.setEnabled(false)
        this.c.setEnabled(false)
        this.d.setChecked(true)
        builder.setView(viewInflate)
        builder.setPositiveButton(android.R.string.ok, fd(this))
        builder.setNegativeButton(android.R.string.cancel, fe(this))
        builder.show()
    }
}
