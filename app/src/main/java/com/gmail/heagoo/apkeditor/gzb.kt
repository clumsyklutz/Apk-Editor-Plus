package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List

class gzb extends BaseAdapter {
    private Context context
    private List items

    constructor(Context context, List list) {
        this.context = context
        this.items = list
    }

    fun clear() {
        this.items.clear()
    }

    @Override // android.widget.Adapter
    fun getCount() {
        return this.items.size()
    }

    @Override // android.widget.Adapter
    fun getItem(Int i) {
        return (gzd) this.items.get(i)
    }

    @Override // android.widget.Adapter
    fun getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    fun getView(Int i, View view, ViewGroup viewGroup) {
        gzc gzcVar
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.item_templates, viewGroup, false)
            view.setLongClickable(true)
            gzc gzcVar2 = gzc(this, view)
            view.setTag(gzcVar2)
            gzcVar = gzcVar2
        } else {
            gzcVar = (gzc) view.getTag()
        }
        gzd gzdVar = (gzd) getItem(i)
        gzcVar.itemTitle.setText(gzdVar.getTitle())
        gzcVar.itemDescription.setText(gzdVar.getContent())
        return view
    }
}
