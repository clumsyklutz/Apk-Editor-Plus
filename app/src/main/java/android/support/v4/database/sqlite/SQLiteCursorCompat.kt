package android.support.v4.database.sqlite

import android.database.sqlite.SQLiteCursor
import android.os.Build
import android.support.annotation.NonNull

class SQLiteCursorCompat {
    private constructor() {
    }

    fun setFillWindowForwardOnly(@NonNull SQLiteCursor sQLiteCursor, Boolean z) {
        if (Build.VERSION.SDK_INT >= 28) {
            sQLiteCursor.setFillWindowForwardOnly(z)
        }
    }
}
