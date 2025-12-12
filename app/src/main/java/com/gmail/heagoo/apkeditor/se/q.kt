package com.gmail.heagoo.apkeditor.se

import androidx.viewpager.widget.ViewPager
import android.view.animation.TranslateAnimation

class q implements ViewPager.OnPageChangeListener {

    /* renamed from: a, reason: collision with root package name */
    private Int f1273a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ SimpleEditActivity f1274b

    constructor(SimpleEditActivity simpleEditActivity) {
        this.f1274b = simpleEditActivity
        this.f1273a = this.f1274b.o / 3
    }

    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
    public final Unit onPageScrollStateChanged(Int i) {
    }

    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
    public final Unit onPageScrolled(Int i, Float f, Int i2) {
    }

    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
    public final Unit onPageSelected(Int i) {
        TranslateAnimation translateAnimation = TranslateAnimation(this.f1273a * this.f1274b.n, this.f1273a * i, 0.0f, 0.0f)
        this.f1274b.n = i
        translateAnimation.setFillAfter(true)
        translateAnimation.setDuration(200L)
        SimpleEditActivity.f(this.f1274b)
    }
}
