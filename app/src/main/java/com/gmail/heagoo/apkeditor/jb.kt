package com.gmail.heagoo.apkeditor

import android.content.Intent
import com.gmail.heagoo.apkeditor.pro.R

class jb implements a.a.b.a.p {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SettingHideActivity f1216a

    jb(SettingHideActivity settingHideActivity) {
        this.f1216a = settingHideActivity
    }

    @Override // a.a.b.a.p
    fun a() {
        this.f1216a.finish()
        this.f1216a.overridePendingTransition(R.anim.abc_fade_in_out, R.anim.abc_fade_out)
        this.f1216a.startActivity(Intent(this.f1216a, (Class<?>) SettingHideActivity.class))
        this.f1216a.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_in_out)
    }
}
