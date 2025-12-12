package android.support.v4.app

import android.app.Service
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class ServiceCompat {
    public static val START_STICKY = 1
    public static val STOP_FOREGROUND_DETACH = 2
    public static val STOP_FOREGROUND_REMOVE = 1

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface StopForegroundFlags {
    }

    private constructor() {
    }

    fun stopForeground(@NonNull Service service, Int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            service.stopForeground(i)
        } else {
            service.stopForeground((i & 1) != 0)
        }
    }
}
