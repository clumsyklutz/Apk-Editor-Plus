package com.gmail.heagoo.common

import android.content.Context
import android.util.AttributeSet
import android.widget.AbsListView
import android.widget.ListAdapter
import android.widget.ListView

class DynamicExpandListView extends ListView implements AbsListView.OnScrollListener {

    /* renamed from: a, reason: collision with root package name */
    private g f1445a

    /* renamed from: b, reason: collision with root package name */
    private Int f1446b

    constructor(Context context) {
        super(context)
        this.f1446b = 30
        setOnScrollListener(this)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f1446b = 30
        setOnScrollListener(this)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.f1446b = 30
        setOnScrollListener(this)
    }

    public final Unit a() {
        if (this.f1445a != null) {
            this.f1445a.notifyDataSetChanged()
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    fun onScroll(AbsListView absListView, Int i, Int i2, Int i3) {
        if (i + i2 < i3 || this.f1445a == null) {
            return
        }
        this.f1445a.a()
    }

    @Override // android.widget.AbsListView.OnScrollListener
    fun onScrollStateChanged(AbsListView absListView, Int i) {
    }

    @Override // android.widget.AdapterView
    fun setAdapter(ListAdapter listAdapter) {
        this.f1445a = g(listAdapter, this.f1446b)
        super.setAdapter((ListAdapter) this.f1445a)
    }
}
