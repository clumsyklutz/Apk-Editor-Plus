package android.support.v4.app

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Build
import android.support.annotation.NonNull

class AlarmManagerCompat {
    private constructor() {
    }

    fun setAlarmClock(@NonNull AlarmManager alarmManager, Long j, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
        if (Build.VERSION.SDK_INT >= 21) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(j, pendingIntent), pendingIntent2)
        } else {
            setExact(alarmManager, 0, j, pendingIntent2)
        }
    }

    fun setAndAllowWhileIdle(@NonNull AlarmManager alarmManager, Int i, Long j, @NonNull PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setAndAllowWhileIdle(i, j, pendingIntent)
        } else {
            alarmManager.set(i, j, pendingIntent)
        }
    }

    fun setExact(@NonNull AlarmManager alarmManager, Int i, Long j, @NonNull PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(i, j, pendingIntent)
        } else {
            alarmManager.set(i, j, pendingIntent)
        }
    }

    fun setExactAndAllowWhileIdle(@NonNull AlarmManager alarmManager, Int i, Long j, @NonNull PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(i, j, pendingIntent)
        } else {
            setExact(alarmManager, i, j, pendingIntent)
        }
    }
}
