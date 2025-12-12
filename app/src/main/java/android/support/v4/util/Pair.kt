package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable

class Pair {

    @Nullable
    public final Object first

    @Nullable
    public final Object second

    constructor(@Nullable Object obj, @Nullable Object obj2) {
        this.first = obj
        this.second = obj2
    }

    @NonNull
    fun create(@Nullable Object obj, @Nullable Object obj2) {
        return Pair(obj, obj2)
    }

    fun equals(Object obj) {
        if (!(obj is Pair)) {
            return false
        }
        Pair pair = (Pair) obj
        return ObjectsCompat.equals(pair.first, this.first) && ObjectsCompat.equals(pair.second, this.second)
    }

    fun hashCode() {
        return (this.first == null ? 0 : this.first.hashCode()) ^ (this.second != null ? this.second.hashCode() : 0)
    }

    fun toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + "}"
    }
}
