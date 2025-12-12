package android.support.v4.content.res

import android.content.res.Resources
import android.os.Build
import android.support.annotation.NonNull

class ConfigurationHelper {
    private constructor() {
    }

    fun getDensityDpi(@NonNull Resources resources) {
        return Build.VERSION.SDK_INT >= 17 ? resources.getConfiguration().densityDpi : resources.getDisplayMetrics().densityDpi
    }
}
