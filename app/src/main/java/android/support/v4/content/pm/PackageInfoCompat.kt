package android.support.v4.content.pm

import android.content.pm.PackageInfo
import android.os.Build
import android.support.annotation.NonNull

class PackageInfoCompat {
    private constructor() {
    }

    fun getLongVersionCode(@NonNull PackageInfo packageInfo) {
        return Build.VERSION.SDK_INT >= 28 ? packageInfo.getLongVersionCode() : packageInfo.versionCode
    }
}
