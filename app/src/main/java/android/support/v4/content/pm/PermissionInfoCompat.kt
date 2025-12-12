package android.support.v4.content.pm

import android.annotation.SuppressLint
import android.content.pm.PermissionInfo
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class PermissionInfoCompat {

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Protection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @SuppressLint({"UniqueConstants"})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ProtectionFlags {
    }

    private constructor() {
    }

    @SuppressLint({"WrongConstant"})
    fun getProtection(@NonNull PermissionInfo permissionInfo) {
        return Build.VERSION.SDK_INT >= 28 ? permissionInfo.getProtection() : permissionInfo.protectionLevel & 15
    }

    @SuppressLint({"WrongConstant"})
    fun getProtectionFlags(@NonNull PermissionInfo permissionInfo) {
        return Build.VERSION.SDK_INT >= 28 ? permissionInfo.getProtectionFlags() : permissionInfo.protectionLevel & (-16)
    }
}
