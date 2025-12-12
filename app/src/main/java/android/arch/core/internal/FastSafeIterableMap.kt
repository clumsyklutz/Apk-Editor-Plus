package android.arch.core.internal

import android.arch.core.internal.SafeIterableMap
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import java.util.HashMap
import java.util.Map

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class FastSafeIterableMap extends SafeIterableMap {
    private HashMap mHashMap = HashMap()

    public Map.Entry ceil(Object obj) {
        if (contains(obj)) {
            return ((SafeIterableMap.Entry) this.mHashMap.get(obj)).mPrevious
        }
        return null
    }

    fun contains(Object obj) {
        return this.mHashMap.containsKey(obj)
    }

    @Override // android.arch.core.internal.SafeIterableMap
    protected SafeIterableMap.Entry get(Object obj) {
        return (SafeIterableMap.Entry) this.mHashMap.get(obj)
    }

    @Override // android.arch.core.internal.SafeIterableMap
    fun putIfAbsent(@NonNull Object obj, @NonNull Object obj2) {
        SafeIterableMap.Entry entry = get(obj)
        if (entry != null) {
            return entry.mValue
        }
        this.mHashMap.put(obj, put(obj, obj2))
        return null
    }

    @Override // android.arch.core.internal.SafeIterableMap
    fun remove(@NonNull Object obj) {
        Object objRemove = super.remove(obj)
        this.mHashMap.remove(obj)
        return objRemove
    }
}
