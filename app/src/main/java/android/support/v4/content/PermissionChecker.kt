package android.support.v4.content

import android.content.Context
import android.os.Binder
import android.os.Process
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.app.AppOpsManagerCompat
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class PermissionChecker {
    public static val PERMISSION_DENIED = -1
    public static val PERMISSION_DENIED_APP_OP = -2
    public static val PERMISSION_GRANTED = 0

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface PermissionResult {
    }

    private constructor() {
    }

    fun checkCallingOrSelfPermission(@NonNull Context context, @NonNull String str) {
        return checkPermission(context, str, Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingPid() == Process.myPid() ? context.getPackageName() : null)
    }

    fun checkCallingPermission(@NonNull Context context, @NonNull String str, @Nullable String str2) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return -1
        }
        return checkPermission(context, str, Binder.getCallingPid(), Binder.getCallingUid(), str2)
    }

    fun checkPermission(@NonNull Context context, @NonNull String str, Int i, Int i2, @Nullable String str2) {
        if (context.checkPermission(str, i, i2) == -1) {
            return -1
        }
        String strPermissionToOp = AppOpsManagerCompat.permissionToOp(str)
        if (strPermissionToOp == null) {
            return 0
        }
        if (str2 == null) {
            Array<String> packagesForUid = context.getPackageManager().getPackagesForUid(i2)
            if (packagesForUid == null || packagesForUid.length <= 0) {
                return -1
            }
            str2 = packagesForUid[0]
        }
        return AppOpsManagerCompat.noteProxyOpNoThrow(context, strPermissionToOp, str2) != 0 ? -2 : 0
    }

    fun checkSelfPermission(@NonNull Context context, @NonNull String str) {
        return checkPermission(context, str, Process.myPid(), Process.myUid(), context.getPackageName())
    }
}
