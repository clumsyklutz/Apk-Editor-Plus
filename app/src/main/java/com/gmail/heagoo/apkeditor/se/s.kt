package com.gmail.heagoo.apkeditor.se

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.List

class s extends PagerAdapter {

    /* renamed from: a, reason: collision with root package name */
    private List f1277a

    constructor(SimpleEditActivity simpleEditActivity, List list) {
        this.f1277a = list
    }

    @Override // android.support.v4.view.PagerAdapter
    public final Unit destroyItem(ViewGroup viewGroup, Int i, Object obj) {
        viewGroup.removeView((View) this.f1277a.get(i))
    }

    @Override // android.support.v4.view.PagerAdapter
    public final Int getCount() {
        return this.f1277a.size()
    }

    @Override // android.support.v4.view.PagerAdapter
    public final Object instantiateItem(ViewGroup viewGroup, Int i) {
        viewGroup.addView((View) this.f1277a.get(i), 0)
        return this.f1277a.get(i)
    }

    @Override // android.support.v4.view.PagerAdapter
    public final Boolean isViewFromObject(View view, Object obj) {
        return view == obj
    }
}
