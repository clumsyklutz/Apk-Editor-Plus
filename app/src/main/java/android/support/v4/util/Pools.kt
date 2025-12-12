package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable

class Pools {

    public interface Pool {
        @Nullable
        Object acquire()

        Boolean release(@NonNull Object obj)
    }

    class SimplePool implements Pool {
        private final Array<Object> mPool
        private Int mPoolSize

        constructor(Int i) {
            if (i <= 0) {
                throw IllegalArgumentException("The max pool size must be > 0")
            }
            this.mPool = new Object[i]
        }

        private fun isInPool(@NonNull Object obj) {
            for (Int i = 0; i < this.mPoolSize; i++) {
                if (this.mPool[i] == obj) {
                    return true
                }
            }
            return false
        }

        @Override // android.support.v4.util.Pools.Pool
        fun acquire() {
            if (this.mPoolSize <= 0) {
                return null
            }
            Int i = this.mPoolSize - 1
            Object obj = this.mPool[i]
            this.mPool[i] = null
            this.mPoolSize--
            return obj
        }

        @Override // android.support.v4.util.Pools.Pool
        fun release(@NonNull Object obj) {
            if (isInPool(obj)) {
                throw IllegalStateException("Already in the pool!")
            }
            if (this.mPoolSize >= this.mPool.length) {
                return false
            }
            this.mPool[this.mPoolSize] = obj
            this.mPoolSize++
            return true
        }
    }

    class SynchronizedPool extends SimplePool {
        private final Object mLock

        constructor(Int i) {
            super(i)
            this.mLock = Object()
        }

        @Override // android.support.v4.util.Pools.SimplePool, android.support.v4.util.Pools.Pool
        fun acquire() {
            Object objAcquire
            synchronized (this.mLock) {
                objAcquire = super.acquire()
            }
            return objAcquire
        }

        @Override // android.support.v4.util.Pools.SimplePool, android.support.v4.util.Pools.Pool
        fun release(@NonNull Object obj) {
            Boolean zRelease
            synchronized (this.mLock) {
                zRelease = super.release(obj)
            }
            return zRelease
        }
    }

    private constructor() {
    }
}
