package com.gmail.heagoo.apkeditor

import android.content.Intent
import com.gmail.heagoo.apkeditor.pro.R

class jc implements a.a.b.a.m {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SettingActivity f1217a

    jc(SettingActivity settingActivity) {
        this.f1217a = settingActivity
    }

    @Override // a.a.b.a.m
    fun a() {
        this.f1217a.finish()
        this.f1217a.overridePendingTransition(R.anim.abc_fade_in_out, R.anim.abc_fade_out)
        this.f1217a.startActivity(Intent(this.f1217a, (Class<?>) SettingActivity.class))
        this.f1217a.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_in_out)
    }
}
