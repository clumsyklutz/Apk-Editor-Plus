package com.gmail.heagoo.sqliteutil.a

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List

class a extends SimpleAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Int f1563a

    /* renamed from: b, reason: collision with root package name */
    private Int f1564b

    constructor(Context context, List list, Int i, Array<String> strArr, Array<Int> iArr, Boolean z) {
        super(context, list, R.layout.item_sql_raw, strArr, iArr)
        if (z) {
            this.f1563a = -3355444
            this.f1564b = -5592406
        } else {
            this.f1563a = -13421773
            this.f1564b = -11184811
        }
    }

    @Override // android.widget.SimpleAdapter, android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup)
        return view2
    }
}
