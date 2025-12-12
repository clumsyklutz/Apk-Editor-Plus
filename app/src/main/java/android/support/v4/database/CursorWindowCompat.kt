package android.support.v4.database

import android.database.CursorWindow
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable

class CursorWindowCompat {
    private constructor() {
    }

    @NonNull
    fun create(@Nullable String str, Long j) {
        return Build.VERSION.SDK_INT >= 28 ? CursorWindow(str, j) : Build.VERSION.SDK_INT >= 15 ? CursorWindow(str) : CursorWindow(false)
    }
}
