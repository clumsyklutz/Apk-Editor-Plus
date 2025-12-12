package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

class nb implements DialogInterface.OnClickListener {
    private final SettingActivity this$0

    nb(SettingActivity settingActivity) {
        this.this$0 = settingActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        this.this$0.d2()
    }
}
