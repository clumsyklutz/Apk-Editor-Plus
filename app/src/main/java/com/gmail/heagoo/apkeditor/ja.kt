package com.gmail.heagoo.apkeditor

import android.content.Intent
import com.gmail.heagoo.apkeditor.pro.R

class ja implements a.a.b.a.m {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MainActivity f1215a

    ja(MainActivity mainActivity) {
        this.f1215a = mainActivity
    }

    @Override // a.a.b.a.m
    fun a() {
        this.f1215a.finish()
        this.f1215a.overridePendingTransition(R.anim.abc_fade_in_out, R.anim.abc_fade_out)
        this.f1215a.startActivity(Intent(this.f1215a, (Class<?>) MainActivity.class))
        this.f1215a.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_in_out)
    }
}
