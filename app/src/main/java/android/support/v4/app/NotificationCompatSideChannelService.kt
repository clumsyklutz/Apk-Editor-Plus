package android.support.v4.app

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.INotificationSideChannel

abstract class NotificationCompatSideChannelService extends Service {

    class NotificationSideChannelStub extends INotificationSideChannel.Stub {
        NotificationSideChannelStub() {
        }

        @Override // android.support.v4.app.INotificationSideChannel
        fun cancel(String str, Int i, String str2) {
            NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), str)
            Long jClearCallingIdentity = clearCallingIdentity()
            try {
                NotificationCompatSideChannelService.this.cancel(str, i, str2)
            } finally {
                restoreCallingIdentity(jClearCallingIdentity)
            }
        }

        @Override // android.support.v4.app.INotificationSideChannel
        fun cancelAll(String str) {
            NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), str)
            Long jClearCallingIdentity = clearCallingIdentity()
            try {
                NotificationCompatSideChannelService.this.cancelAll(str)
            } finally {
                restoreCallingIdentity(jClearCallingIdentity)
            }
        }

        @Override // android.support.v4.app.INotificationSideChannel
        fun notify(String str, Int i, String str2, Notification notification) {
            NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), str)
            Long jClearCallingIdentity = clearCallingIdentity()
            try {
                NotificationCompatSideChannelService.this.notify(str, i, str2, notification)
            } finally {
                restoreCallingIdentity(jClearCallingIdentity)
            }
        }
    }

    public abstract Unit cancel(String str, Int i, String str2)

    public abstract Unit cancelAll(String str)

    Unit checkPermission(Int i, String str) {
        for (String str2 : getPackageManager().getPackagesForUid(i)) {
            if (str2.equals(str)) {
                return
            }
        }
        throw SecurityException("NotificationSideChannelService: Uid " + i + " is not authorized for package " + str)
    }

    public abstract Unit notify(String str, Int i, String str2, Notification notification)

    @Override // android.app.Service
    fun onBind(Intent intent) {
        if (!intent.getAction().equals(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL) || Build.VERSION.SDK_INT > 19) {
            return null
        }
        return NotificationSideChannelStub()
    }
}
