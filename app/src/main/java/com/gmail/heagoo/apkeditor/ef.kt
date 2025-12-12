package com.gmail.heagoo.apkeditor

import android.widget.BaseAdapter
import android.widget.CompoundButton
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Set

class ef extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1023a

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f1024b
    private String c
    private ArrayList d
    private Set e = HashSet()
    private Int f = R.layout.item_file_selectable

    constructor(ApkInfoActivity apkInfoActivity, fn fnVar, String str, ArrayList arrayList) {
        this.f1023a = WeakReference(apkInfoActivity)
        this.f1024b = WeakReference(fnVar)
        this.c = str
        this.d = arrayList
    }

    private fun a(List list, Int i) {
        Int i2 = 0
        for (Int i3 = 0; i3 < list.size() && ((Integer) list.get(i3)).intValue() < i; i3++) {
            i2++
        }
        return i2
    }

    public final List a() {
        ArrayList arrayList = ArrayList()
        arrayList.addAll(this.e)
        Collections.sort(arrayList)
        return arrayList
    }

    public final Unit a(ArrayList arrayList, List list) {
        Collections.sort(list)
        HashSet hashSet = HashSet()
        Iterator it = this.e.iterator()
        while (it.hasNext()) {
            Int iIntValue = ((Integer) it.next()).intValue()
            if (!list.contains(Integer.valueOf(iIntValue))) {
                hashSet.add(Integer.valueOf(iIntValue - a(list, iIntValue)))
            }
        }
        this.e = hashSet
        this.d = arrayList
        notifyDataSetChanged()
    }

    public final Unit b() {
        for (Int i = 0; i < this.d.size(); i++) {
            this.e.add(Integer.valueOf(i))
        }
        notifyDataSetChanged()
    }

    public final Unit c() {
        this.e.clear()
        notifyDataSetChanged()
    }

    public final Boolean d() {
        return this.e.size() == this.d.size()
    }

    public final Boolean e() {
        return this.e.isEmpty()
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.d.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.d.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0109  */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View getView(Int r9, android.view.View r10, android.view.ViewGroup r11) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.ef.getView(Int, android.view.View, android.view.ViewGroup):android.view.View")
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final Unit onCheckedChanged(CompoundButton compoundButton, Boolean z) {
        Int id = compoundButton.getId()
        if (z) {
            this.e.add(Integer.valueOf(id))
        } else {
            this.e.remove(Integer.valueOf(id))
        }
        if (this.f1024b != null) {
            ((fn) this.f1024b.get()).b(this.e)
        }
    }
}
