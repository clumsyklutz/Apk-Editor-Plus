package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import java.util.LinkedHashMap
import java.util.Locale
import java.util.Map

class LruCache {
    private Int createCount
    private Int evictionCount
    private Int hitCount
    private final LinkedHashMap map
    private Int maxSize
    private Int missCount
    private Int putCount
    private Int size

    constructor(Int i) {
        if (i <= 0) {
            throw IllegalArgumentException("maxSize <= 0")
        }
        this.maxSize = i
        this.map = LinkedHashMap(0, 0.75f, true)
    }

    private fun safeSizeOf(Object obj, Object obj2) {
        Int iSizeOf = sizeOf(obj, obj2)
        if (iSizeOf < 0) {
            throw IllegalStateException("Negative size: " + obj + "=" + obj2)
        }
        return iSizeOf
    }

    @Nullable
    protected fun create(@NonNull Object obj) {
        return null
    }

    public final synchronized Int createCount() {
        return this.createCount
    }

    protected fun entryRemoved(Boolean z, @NonNull Object obj, @NonNull Object obj2, @Nullable Object obj3) {
    }

    public final Unit evictAll() {
        trimToSize(-1)
    }

    public final synchronized Int evictionCount() {
        return this.evictionCount
    }

    @Nullable
    public final Object get(@NonNull Object obj) {
        Object objPut
        if (obj == null) {
            throw NullPointerException("key == null")
        }
        synchronized (this) {
            Object obj2 = this.map.get(obj)
            if (obj2 != null) {
                this.hitCount++
                return obj2
            }
            this.missCount++
            Object objCreate = create(obj)
            if (objCreate == null) {
                return null
            }
            synchronized (this) {
                this.createCount++
                objPut = this.map.put(obj, objCreate)
                if (objPut != null) {
                    this.map.put(obj, objPut)
                } else {
                    this.size += safeSizeOf(obj, objCreate)
                }
            }
            if (objPut != null) {
                entryRemoved(false, obj, objCreate, objPut)
                return objPut
            }
            trimToSize(this.maxSize)
            return objCreate
        }
    }

    public final synchronized Int hitCount() {
        return this.hitCount
    }

    public final synchronized Int maxSize() {
        return this.maxSize
    }

    public final synchronized Int missCount() {
        return this.missCount
    }

    @Nullable
    public final Object put(@NonNull Object obj, @NonNull Object obj2) {
        Object objPut
        if (obj == null || obj2 == null) {
            throw NullPointerException("key == null || value == null")
        }
        synchronized (this) {
            this.putCount++
            this.size += safeSizeOf(obj, obj2)
            objPut = this.map.put(obj, obj2)
            if (objPut != null) {
                this.size -= safeSizeOf(obj, objPut)
            }
        }
        if (objPut != null) {
            entryRemoved(false, obj, objPut, obj2)
        }
        trimToSize(this.maxSize)
        return objPut
    }

    public final synchronized Int putCount() {
        return this.putCount
    }

    @Nullable
    public final Object remove(@NonNull Object obj) {
        Object objRemove
        if (obj == null) {
            throw NullPointerException("key == null")
        }
        synchronized (this) {
            objRemove = this.map.remove(obj)
            if (objRemove != null) {
                this.size -= safeSizeOf(obj, objRemove)
            }
        }
        if (objRemove != null) {
            entryRemoved(false, obj, objRemove, null)
        }
        return objRemove
    }

    fun resize(Int i) {
        if (i <= 0) {
            throw IllegalArgumentException("maxSize <= 0")
        }
        synchronized (this) {
            this.maxSize = i
        }
        trimToSize(i)
    }

    public final synchronized Int size() {
        return this.size
    }

    protected fun sizeOf(@NonNull Object obj, @NonNull Object obj2) {
        return 1
    }

    public final synchronized Map snapshot() {
        return LinkedHashMap(this.map)
    }

    public final synchronized String toString() {
        String str
        synchronized (this) {
            Int i = this.hitCount + this.missCount
            str = String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i != 0 ? (this.hitCount * 100) / i : 0))
        }
        return str
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0031, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!")
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun trimToSize(Int r5) {
        /*
            r4 = this
        L0:
            monitor-enter(r4)
            Int r0 = r4.size     // Catch: java.lang.Throwable -> L32
            if (r0 < 0) goto L11
            java.util.LinkedHashMap r0 = r4.map     // Catch: java.lang.Throwable -> L32
            Boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L35
            Int r0 = r4.size     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L35
        L11:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L32
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32
            r1.<init>()     // Catch: java.lang.Throwable -> L32
            java.lang.Class r2 = r4.getClass()     // Catch: java.lang.Throwable -> L32
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L32
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L32
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L32
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L32
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L32
            throw r0     // Catch: java.lang.Throwable -> L32
        L32:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L32
            throw r0
        L35:
            Int r0 = r4.size     // Catch: java.lang.Throwable -> L32
            if (r0 <= r5) goto L41
            java.util.LinkedHashMap r0 = r4.map     // Catch: java.lang.Throwable -> L32
            Boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L43
        L41:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L32
            return
        L43:
            java.util.LinkedHashMap r0 = r4.map     // Catch: java.lang.Throwable -> L32
            java.util.Set r0 = r0.entrySet()     // Catch: java.lang.Throwable -> L32
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L32
            java.lang.Object r0 = r0.next()     // Catch: java.lang.Throwable -> L32
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch: java.lang.Throwable -> L32
            java.lang.Object r1 = r0.getKey()     // Catch: java.lang.Throwable -> L32
            java.lang.Object r0 = r0.getValue()     // Catch: java.lang.Throwable -> L32
            java.util.LinkedHashMap r2 = r4.map     // Catch: java.lang.Throwable -> L32
            r2.remove(r1)     // Catch: java.lang.Throwable -> L32
            Int r2 = r4.size     // Catch: java.lang.Throwable -> L32
            Int r3 = r4.safeSizeOf(r1, r0)     // Catch: java.lang.Throwable -> L32
            Int r2 = r2 - r3
            r4.size = r2     // Catch: java.lang.Throwable -> L32
            Int r2 = r4.evictionCount     // Catch: java.lang.Throwable -> L32
            Int r2 = r2 + 1
            r4.evictionCount = r2     // Catch: java.lang.Throwable -> L32
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L32
            r2 = 1
            r3 = 0
            r4.entryRemoved(r2, r1, r0, r3)
            goto L0
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.trimToSize(Int):Unit")
    }
}
