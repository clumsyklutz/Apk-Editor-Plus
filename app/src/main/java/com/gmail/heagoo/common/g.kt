package com.gmail.heagoo.common

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter

final class g extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Int f1454a

    /* renamed from: b, reason: collision with root package name */
    private Int f1455b = 30
    private Boolean c
    private ListAdapter d
    private Int e

    constructor(ListAdapter listAdapter, Int i) {
        this.c = false
        this.d = listAdapter
        this.f1454a = i
        if (listAdapter.getCount() > this.f1454a) {
            this.e = this.f1454a
            this.c = false
        } else {
            this.e = listAdapter.getCount()
            this.c = true
        }
    }

    public final Unit a() {
        if (this.c) {
            return
        }
        this.f1454a += this.f1455b
        if (this.d.getCount() > this.f1454a) {
            this.c = false
            this.e = this.f1454a
        } else {
            this.c = true
            this.e = this.d.getCount()
        }
        notifyDataSetChanged()
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.e
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.d.getItem(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return this.d.getItemId(i)
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        return this.d.getView(i, view, viewGroup)
    }
}
