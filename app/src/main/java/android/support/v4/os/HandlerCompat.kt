package android.support.v4.os

import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.NonNull
import android.support.annotation.Nullable

class HandlerCompat {
    private constructor() {
    }

    fun postDelayed(@NonNull Handler handler, @NonNull Runnable runnable, @Nullable Object obj, Long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            return handler.postDelayed(runnable, obj, j)
        }
        Message messageObtain = Message.obtain(handler, runnable)
        messageObtain.obj = obj
        return handler.sendMessageDelayed(messageObtain, j)
    }
}
