package android.arch.lifecycle

import android.arch.core.executor.ArchTaskExecutor
import android.arch.core.internal.SafeIterableMap
import android.arch.lifecycle.Lifecycle
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import java.util.Iterator
import java.util.Map

abstract class LiveData {
    private static val NOT_SET = Object()
    static val START_VERSION = -1
    private Boolean mDispatchInvalidated
    private Boolean mDispatchingValue
    private val mDataLock = Object()
    private SafeIterableMap mObservers = SafeIterableMap()
    private Int mActiveCount = 0
    private volatile Object mData = NOT_SET
    private volatile Object mPendingData = NOT_SET
    private Int mVersion = -1
    private val mPostValueRunnable = Runnable() { // from class: android.arch.lifecycle.LiveData.1
        @Override // java.lang.Runnable
        fun run() {
            Object obj
            synchronized (LiveData.this.mDataLock) {
                obj = LiveData.this.mPendingData
                LiveData.this.mPendingData = LiveData.NOT_SET
            }
            LiveData.this.setValue(obj)
        }
    }

    class AlwaysActiveObserver extends ObserverWrapper {
        AlwaysActiveObserver(Observer observer) {
            super(observer)
        }

        @Override // android.arch.lifecycle.LiveData.ObserverWrapper
        Boolean shouldBeActive() {
            return true
        }
    }

    class LifecycleBoundObserver extends ObserverWrapper implements GenericLifecycleObserver {

        @NonNull
        final LifecycleOwner mOwner

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, @NonNull Observer observer) {
            super(observer)
            this.mOwner = lifecycleOwner
        }

        @Override // android.arch.lifecycle.LiveData.ObserverWrapper
        Unit detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this)
        }

        @Override // android.arch.lifecycle.LiveData.ObserverWrapper
        Boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return this.mOwner == lifecycleOwner
        }

        @Override // android.arch.lifecycle.GenericLifecycleObserver
        fun onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (this.mOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver)
            } else {
                activeStateChanged(shouldBeActive())
            }
        }

        @Override // android.arch.lifecycle.LiveData.ObserverWrapper
        Boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)
        }
    }

    abstract class ObserverWrapper {
        Boolean mActive
        Int mLastVersion = -1
        final Observer mObserver

        ObserverWrapper(Observer observer) {
            this.mObserver = observer
        }

        Unit activeStateChanged(Boolean z) {
            if (z == this.mActive) {
                return
            }
            this.mActive = z
            Boolean z2 = LiveData.this.mActiveCount == 0
            LiveData liveData = LiveData.this
            liveData.mActiveCount = (this.mActive ? 1 : -1) + liveData.mActiveCount
            if (z2 && this.mActive) {
                LiveData.this.onActive()
            }
            if (LiveData.this.mActiveCount == 0 && !this.mActive) {
                LiveData.this.onInactive()
            }
            if (this.mActive) {
                LiveData.this.dispatchingValue(this)
            }
        }

        Unit detachObserver() {
        }

        Boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return false
        }

        abstract Boolean shouldBeActive()
    }

    private fun assertMainThread(String str) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            throw IllegalStateException("Cannot invoke " + str + " on a background thread")
        }
    }

    private fun considerNotify(ObserverWrapper observerWrapper) {
        if (observerWrapper.mActive) {
            if (!observerWrapper.shouldBeActive()) {
                observerWrapper.activeStateChanged(false)
            } else if (observerWrapper.mLastVersion < this.mVersion) {
                observerWrapper.mLastVersion = this.mVersion
                observerWrapper.mObserver.onChanged(this.mData)
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun dispatchingValue(@Nullable ObserverWrapper observerWrapper) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true
            return
        }
        this.mDispatchingValue = true
        do {
            this.mDispatchInvalidated = false
            if (observerWrapper != null) {
                considerNotify(observerWrapper)
                observerWrapper = null
            } else {
                SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mObservers.iteratorWithAdditions()
                while (iteratorWithAdditions.hasNext()) {
                    considerNotify((ObserverWrapper) ((Map.Entry) iteratorWithAdditions.next()).getValue())
                    if (this.mDispatchInvalidated) {
                        break
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false
    }

    @Nullable
    fun getValue() {
        Object obj = this.mData
        if (obj != NOT_SET) {
            return obj
        }
        return null
    }

    Int getVersion() {
        return this.mVersion
    }

    fun hasActiveObservers() {
        return this.mActiveCount > 0
    }

    fun hasObservers() {
        return this.mObservers.size() > 0
    }

    @MainThread
    fun observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer observer) {
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return
        }
        LifecycleBoundObserver lifecycleBoundObserver = LifecycleBoundObserver(lifecycleOwner, observer)
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.putIfAbsent(observer, lifecycleBoundObserver)
        if (observerWrapper != null && !observerWrapper.isAttachedTo(lifecycleOwner)) {
            throw IllegalArgumentException("Cannot add the same observer with different lifecycles")
        }
        if (observerWrapper == null) {
            lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver)
        }
    }

    @MainThread
    fun observeForever(@NonNull Observer observer) {
        AlwaysActiveObserver alwaysActiveObserver = AlwaysActiveObserver(observer)
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.putIfAbsent(observer, alwaysActiveObserver)
        if (observerWrapper != null && (observerWrapper is LifecycleBoundObserver)) {
            throw IllegalArgumentException("Cannot add the same observer with different lifecycles")
        }
        if (observerWrapper != null) {
            return
        }
        alwaysActiveObserver.activeStateChanged(true)
    }

    protected fun onActive() {
    }

    protected fun onInactive() {
    }

    protected fun postValue(Object obj) {
        Boolean z
        synchronized (this.mDataLock) {
            z = this.mPendingData == NOT_SET
            this.mPendingData = obj
        }
        if (z) {
            ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable)
        }
    }

    @MainThread
    fun removeObserver(@NonNull Observer observer) {
        assertMainThread("removeObserver")
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.remove(observer)
        if (observerWrapper == null) {
            return
        }
        observerWrapper.detachObserver()
        observerWrapper.activeStateChanged(false)
    }

    @MainThread
    fun removeObservers(@NonNull LifecycleOwner lifecycleOwner) {
        assertMainThread("removeObservers")
        Iterator it = this.mObservers.iterator()
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next()
            if (((ObserverWrapper) entry.getValue()).isAttachedTo(lifecycleOwner)) {
                removeObserver((Observer) entry.getKey())
            }
        }
    }

    @MainThread
    protected fun setValue(Object obj) {
        assertMainThread("setValue")
        this.mVersion++
        this.mData = obj
        dispatchingValue(null)
    }
}
