package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import common.types.StringItem
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

final class gv extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1123a

    /* renamed from: b, reason: collision with root package name */
    private val f1124b = ArrayList()
    private Map c = HashMap()
    private String d = null
    private Int e = R.layout.item_stringvaluestatic
    private Activity f

    gv(Activity activity) {
        this.f1123a = WeakReference(activity)
        this.f = activity
    }

    final Map a() {
        return this.c
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final Unit a(Int r6, java.lang.String r7) {
        /*
            r5 = this
            r1 = 0
            java.util.List r2 = r5.f1124b
            monitor-enter(r2)
            if (r6 < 0) goto L4c
            java.util.List r0 = r5.f1124b     // Catch: java.lang.Throwable -> L49
            Int r0 = r0.size()     // Catch: java.lang.Throwable -> L49
            if (r6 >= r0) goto L4c
            java.util.List r0 = r5.f1124b     // Catch: java.lang.Throwable -> L49
            java.lang.Object r0 = r0.get(r6)     // Catch: java.lang.Throwable -> L49
            common.types.StringItem r0 = (common.types.StringItem) r0     // Catch: java.lang.Throwable -> L49
            java.lang.String r3 = r0.value     // Catch: java.lang.Throwable -> L49
            Boolean r3 = r3.equals(r7)     // Catch: java.lang.Throwable -> L49
            if (r3 != 0) goto L4c
            java.lang.String r1 = r5.d     // Catch: java.lang.Throwable -> L49
            if (r1 == 0) goto L41
            r0.value = r7     // Catch: java.lang.Throwable -> L49
            java.util.Map r1 = r5.c     // Catch: java.lang.Throwable -> L49
            java.lang.String r3 = r5.d     // Catch: java.lang.Throwable -> L49
            java.lang.Object r1 = r1.get(r3)     // Catch: java.lang.Throwable -> L49
            java.util.Map r1 = (java.util.Map) r1     // Catch: java.lang.Throwable -> L49
            if (r1 != 0) goto L3c
            java.util.HashMap r1 = new java.util.HashMap     // Catch: java.lang.Throwable -> L49
            r1.<init>()     // Catch: java.lang.Throwable -> L49
            java.util.Map r3 = r5.c     // Catch: java.lang.Throwable -> L49
            java.lang.String r4 = r5.d     // Catch: java.lang.Throwable -> L49
            r3.put(r4, r1)     // Catch: java.lang.Throwable -> L49
        L3c:
            java.lang.String r0 = r0.name     // Catch: java.lang.Throwable -> L49
            r1.put(r0, r7)     // Catch: java.lang.Throwable -> L49
        L41:
            r0 = 1
        L42:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto L48
            r5.notifyDataSetChanged()
        L48:
            return
        L49:
            r0 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L49
            throw r0
        L4c:
            r0 = r1
            goto L42
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.gv.a(Int, java.lang.String):Unit")
    }

    final Unit a(String str, List list) {
        synchronized (this.f1124b) {
            this.d = str
            this.f1124b.clear()
            Iterator it = list.iterator()
            while (it.hasNext()) {
                this.f1124b.add((StringItem) it.next())
            }
        }
        notifyDataSetChanged()
    }

    final Unit a(Map map) {
        this.c = map
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        Int size
        synchronized (this.f1124b) {
            size = this.f1124b.size()
        }
        return size
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        Object obj
        synchronized (this.f1124b) {
            obj = this.f1124b.get(i)
        }
        return obj
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        gw gwVar
        synchronized (this.f1124b) {
            StringItem stringItem = (StringItem) this.f1124b.get(i)
            if (view == null) {
                view = LayoutInflater.from((Context) this.f1123a.get()).inflate(this.e, (ViewGroup) null)
                gw gwVar2 = gw((Byte) 0)
                gwVar2.f1125a = (TextView) view.findViewById(R.id.string_name)
                gwVar2.f1126b = (TextView) view.findViewById(R.id.string_value)
                view.setTag(gwVar2)
                gwVar = gwVar2
            } else {
                gwVar = (gw) view.getTag()
            }
            gwVar.f1125a.setText(stringItem.name)
            gwVar.f1126b.setText(stringItem.value)
        }
        return view
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        gx gxVar = gx((Context) this.f1123a.get(), this, i)
        synchronized (this.f1124b) {
            StringItem stringItem = (StringItem) this.f1124b.get(i)
            gxVar.a(stringItem.name, stringItem.value)
        }
        gxVar.show()
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        String str = ((StringItem) this.f1124b.get(i)).name
        a.c(this.f, str)
        Toast.makeText(this.f, String.format(this.f.getString(R.string.copied_to_clipboard), str), 0).show()
        return true
    }
}
