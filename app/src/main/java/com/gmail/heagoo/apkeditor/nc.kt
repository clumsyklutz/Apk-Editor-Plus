package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

class nc implements DialogInterface.OnClickListener {
    private final SettingActivity this$0

    nc(SettingActivity settingActivity) {
        this.this$0 = settingActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        this.this$0.d3()
    }
}
