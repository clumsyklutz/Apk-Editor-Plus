package com.gmail.heagoo.apkeditor.pro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.gmail.heagoo.appdm.PrefOverallActivity

class appdm {
    fun de(Context context, String str) {
        Intent intent = Intent(context, (Class<?>) PrefOverallActivity.class)
        Bundle bundle = Bundle()
        bundle.putString("packagePath", str)
        bundle.putBoolean("backup", false)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}
