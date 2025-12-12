package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

class nd implements DialogInterface.OnClickListener {
    private final SettingActivity this$0

    nd(SettingActivity settingActivity) {
        this.this$0 = settingActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        this.this$0.d4()
    }
}
