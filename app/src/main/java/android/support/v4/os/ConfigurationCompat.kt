package android.support.v4.os

import android.content.res.Configuration
import android.os.Build

class ConfigurationCompat {
    private constructor() {
    }

    fun getLocales(Configuration configuration) {
        return Build.VERSION.SDK_INT >= 24 ? LocaleListCompat.wrap(configuration.getLocales()) : LocaleListCompat.create(configuration.locale)
    }
}
