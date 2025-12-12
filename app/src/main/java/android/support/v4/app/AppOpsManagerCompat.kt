package android.support.v4.app

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable

class AppOpsManagerCompat {
    public static val MODE_ALLOWED = 0
    public static val MODE_DEFAULT = 3
    public static val MODE_ERRORED = 2
    public static val MODE_IGNORED = 1

    private constructor() {
    }

    fun noteOp(@NonNull Context context, @NonNull String str, Int i, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AppOpsManager) context.getSystemService("appops")).noteOp(str, i, str2)
        }
        return 1
    }

    fun noteOpNoThrow(@NonNull Context context, @NonNull String str, Int i, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AppOpsManager) context.getSystemService("appops")).noteOpNoThrow(str, i, str2)
        }
        return 1
    }

    fun noteProxyOp(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOp(str, str2)
        }
        return 1
    }

    fun noteProxyOpNoThrow(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(str, str2)
        }
        return 1
    }

    @Nullable
    fun permissionToOp(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return AppOpsManager.permissionToOp(str)
        }
        return null
    }
}
