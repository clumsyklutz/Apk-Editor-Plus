package android.support.v4.os

import android.content.Context
import android.os.Build
import android.os.UserManager

class UserManagerCompat {
    private constructor() {
    }

    fun isUserUnlocked(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((UserManager) context.getSystemService(UserManager.class)).isUserUnlocked()
        }
        return true
    }
}
