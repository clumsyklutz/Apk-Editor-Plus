package android.support.v4.util

import android.os.Build
import android.support.annotation.Nullable
import java.util.Arrays
import java.util.Objects

class ObjectsCompat {
    private constructor() {
    }

    fun equals(@Nullable Object obj, @Nullable Object obj2) {
        return Build.VERSION.SDK_INT >= 19 ? Objects.equals(obj, obj2) : obj == obj2 || (obj != null && obj.equals(obj2))
    }

    fun hash(@Nullable Object... objArr) {
        return Build.VERSION.SDK_INT >= 19 ? Objects.hash(objArr) : Arrays.hashCode(objArr)
    }

    fun hashCode(@Nullable Object obj) {
        if (obj != null) {
            return obj.hashCode()
        }
        return 0
    }
}
