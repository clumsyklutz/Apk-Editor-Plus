package android.support.v4.net

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresPermission
import android.support.annotation.RestrictTo
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class ConnectivityManagerCompat {
    public static val RESTRICT_BACKGROUND_STATUS_DISABLED = 1
    public static val RESTRICT_BACKGROUND_STATUS_ENABLED = 3
    public static val RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface RestrictBackgroundStatus {
    }

    private constructor() {
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    fun getNetworkInfoFromBroadcast(@NonNull ConnectivityManager connectivityManager, @NonNull Intent intent) {
        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo")
        if (networkInfo != null) {
            return connectivityManager.getNetworkInfo(networkInfo.getType())
        }
        return null
    }

    fun getRestrictBackgroundStatus(@NonNull ConnectivityManager connectivityManager) {
        if (Build.VERSION.SDK_INT >= 24) {
            return connectivityManager.getRestrictBackgroundStatus()
        }
        return 3
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    fun isActiveNetworkMetered(@NonNull ConnectivityManager connectivityManager) {
        if (Build.VERSION.SDK_INT >= 16) {
            return connectivityManager.isActiveNetworkMetered()
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
        if (activeNetworkInfo == null) {
            return true
        }
        switch (activeNetworkInfo.getType()) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
                return true
            case 1:
            case 7:
            case 9:
                return false
        }
    }
}
