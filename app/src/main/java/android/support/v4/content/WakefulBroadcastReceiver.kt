package android.support.v4.content

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.util.SparseArray

@Deprecated
abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static val EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid"
    private static val sActiveWakeLocks = SparseArray()
    private static Int mNextId = 1

    fun completeWakefulIntent(Intent intent) {
        Boolean z = false
        Int intExtra = intent.getIntExtra(EXTRA_WAKE_LOCK_ID, 0)
        if (intExtra != 0) {
            synchronized (sActiveWakeLocks) {
                PowerManager.WakeLock wakeLock = (PowerManager.WakeLock) sActiveWakeLocks.get(intExtra)
                if (wakeLock != null) {
                    wakeLock.release()
                    sActiveWakeLocks.remove(intExtra)
                    z = true
                } else {
                    Log.w("WakefulBroadcastReceiv.", "No active wake lock id #" + intExtra)
                    z = true
                }
            }
        }
        return z
    }

    fun startWakefulService(Context context, Intent intent) {
        synchronized (sActiveWakeLocks) {
            Int i = mNextId
            Int i2 = mNextId + 1
            mNextId = i2
            if (i2 <= 0) {
                mNextId = 1
            }
            intent.putExtra(EXTRA_WAKE_LOCK_ID, i)
            ComponentName componentNameStartService = context.startService(intent)
            if (componentNameStartService == null) {
                return null
            }
            PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "androidx.core:wake:" + componentNameStartService.flattenToShortString())
            wakeLockNewWakeLock.setReferenceCounted(false)
            wakeLockNewWakeLock.acquire(60000L)
            sActiveWakeLocks.put(i, wakeLockNewWakeLock)
            return componentNameStartService
        }
    }
}
