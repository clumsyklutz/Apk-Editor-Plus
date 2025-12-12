package com.gmail.heagoo.common

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.Locale

class e {
    fun l(Context context) {
        Configuration configuration = Configuration()
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("md_lang", "")
        if (string.equals("")) {
            string = Build.VERSION.SDK_INT >= 24 ? Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage() : Resources.getSystem().getConfiguration().locale.getLanguage()
        }
        Locale locale = Locale(string)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= 24) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics())
    }
}
