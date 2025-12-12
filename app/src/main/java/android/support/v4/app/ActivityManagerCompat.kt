package android.support.v4.app

import android.app.ActivityManager
import android.os.Build
import android.support.annotation.NonNull

class ActivityManagerCompat {
    private constructor() {
    }

    fun isLowRamDevice(@NonNull ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice()
        }
        return false
    }
}
