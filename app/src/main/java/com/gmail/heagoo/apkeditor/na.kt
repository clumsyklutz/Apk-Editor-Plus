package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

class na implements DialogInterface.OnClickListener {
    private final SettingActivity this$0

    na(SettingActivity settingActivity) {
        this.this$0 = settingActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        this.this$0.d1()
    }
}
