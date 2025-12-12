package com.gmail.heagoo.apkeditor.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List

final class t extends ArrayAdapter {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ IconPickerPreference f1331a

    /* renamed from: b, reason: collision with root package name */
    private Context f1332b
    private List c
    private Int d

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    constructor(IconPickerPreference iconPickerPreference, Context context, Int i, List list) {
        super(context, i, list)
        this.f1331a = iconPickerPreference
        this.f1332b = context
        this.d = i
        this.c = list
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        w wVar
        if (view == null) {
            view = ((LayoutInflater) this.f1332b.getSystemService("layout_inflater")).inflate(this.d, viewGroup, false)
            wVar = w((Byte) 0)
            wVar.f1338b = (TextView) view.findViewById(R.id.iconName)
            wVar.f1337a = (ImageView) view.findViewById(R.id.iconImage)
            wVar.c = (RadioButton) view.findViewById(R.id.iconRadio)
            view.setTag(wVar)
        } else {
            wVar = (w) view.getTag()
        }
        v vVar = (v) this.c.get(i)
        wVar.f1338b.setText(vVar.c)
        wVar.f1337a.setImageResource(vVar.f1335a)
        wVar.c.setChecked(vVar.f1336b)
        view.setOnClickListener(u(this, i))
        return view
    }
}
