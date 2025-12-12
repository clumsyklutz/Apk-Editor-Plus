package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import java.util.List

final class ep extends BaseExpandableListAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Context f1036a

    /* renamed from: b, reason: collision with root package name */
    private List f1037b

    constructor(Context context, List list) {
        this.f1036a = context
        this.f1037b = list
    }

    @Override // android.widget.ExpandableListAdapter
    public final Object getChild(Int i, Int i2) {
        return ((eq) this.f1037b.get(i)).a(this.f1036a, i2)
    }

    @Override // android.widget.ExpandableListAdapter
    public final Long getChildId(Int i, Int i2) {
        return (i << 16) | i2
    }

    @Override // android.widget.ExpandableListAdapter
    public final View getChildView(Int i, Int i2, Boolean z, View view, ViewGroup viewGroup) {
        String strA = ((eq) this.f1037b.get(i)).a(this.f1036a, i2)
        View textView = view == null ? TextView(this.f1036a) : view
        ((TextView) textView).setPadding(com.gmail.heagoo.common.f.a(this.f1036a, 48.0f), 0, 0, 0)
        ((TextView) textView).setText(strA)
        return textView
    }

    @Override // android.widget.ExpandableListAdapter
    public final Int getChildrenCount(Int i) {
        eq eqVar = (eq) this.f1037b.get(i)
        if (eqVar.f1039b < 0 && eqVar.c < 0 && eqVar.d < 0) {
            return 1
        }
        if (eqVar.f1039b == 0 && eqVar.c == 0 && eqVar.d == 0) {
            return 1
        }
        Int i2 = eqVar.f1039b <= 0 ? 0 : 1
        if (eqVar.c > 0) {
            i2++
        }
        return eqVar.d > 0 ? i2 + 1 : i2
    }

    @Override // android.widget.ExpandableListAdapter
    public final Object getGroup(Int i) {
        return this.f1037b.get(i)
    }

    @Override // android.widget.ExpandableListAdapter
    public final Int getGroupCount() {
        return this.f1037b.size()
    }

    @Override // android.widget.ExpandableListAdapter
    public final Long getGroupId(Int i) {
        return i
    }

    @Override // android.widget.ExpandableListAdapter
    public final View getGroupView(Int i, Boolean z, View view, ViewGroup viewGroup) {
        String str = ((eq) this.f1037b.get(i)).f1038a
        View textView = view == null ? TextView(this.f1036a) : view
        Int iA = com.gmail.heagoo.common.f.a(this.f1036a, 32.0f)
        ((TextView) textView).setPadding(iA, iA / 8, 0, 0)
        ((TextView) textView).setTypeface(null, 1)
        ((TextView) textView).setText(str)
        return textView
    }

    @Override // android.widget.ExpandableListAdapter
    public final Boolean hasStableIds() {
        return false
    }

    @Override // android.widget.ExpandableListAdapter
    public final Boolean isChildSelectable(Int i, Int i2) {
        return false
    }
}
