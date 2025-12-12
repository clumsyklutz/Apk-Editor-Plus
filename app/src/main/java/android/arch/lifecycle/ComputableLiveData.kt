package android.arch.lifecycle

import android.arch.core.executor.ArchTaskExecutor
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import android.support.annotation.VisibleForTesting
import android.support.annotation.WorkerThread
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicBoolean

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class ComputableLiveData {
    private AtomicBoolean mComputing
    private final Executor mExecutor
    private AtomicBoolean mInvalid

    @VisibleForTesting
    final Runnable mInvalidationRunnable
    private final LiveData mLiveData

    @VisibleForTesting
    final Runnable mRefreshRunnable

    constructor() {
        this(ArchTaskExecutor.getIOThreadExecutor())
    }

    constructor(@NonNull Executor executor) {
        this.mInvalid = AtomicBoolean(true)
        this.mComputing = AtomicBoolean(false)
        this.mRefreshRunnable = Runnable() { // from class: android.arch.lifecycle.ComputableLiveData.2
            @Override // java.lang.Runnable
            @WorkerThread
            fun run() {
                Boolean z
                do {
                    if (ComputableLiveData.this.mComputing.compareAndSet(false, true)) {
                        Object objCompute = null
                        z = false
                        while (ComputableLiveData.this.mInvalid.compareAndSet(true, false)) {
                            try {
                                objCompute = ComputableLiveData.this.compute()
                                z = true
                            } finally {
                                ComputableLiveData.this.mComputing.set(false)
                            }
                        }
                        if (z) {
                            ComputableLiveData.this.mLiveData.postValue(objCompute)
                        }
                    } else {
                        z = false
                    }
                    if (!z) {
                        return
                    }
                } while (ComputableLiveData.this.mInvalid.get());
            }
        }
        this.mInvalidationRunnable = Runnable() { // from class: android.arch.lifecycle.ComputableLiveData.3
            @Override // java.lang.Runnable
            @MainThread
            fun run() {
                Boolean zHasActiveObservers = ComputableLiveData.this.mLiveData.hasActiveObservers()
                if (ComputableLiveData.this.mInvalid.compareAndSet(false, true) && zHasActiveObservers) {
                    ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable)
                }
            }
        }
        this.mExecutor = executor
        this.mLiveData = LiveData() { // from class: android.arch.lifecycle.ComputableLiveData.1
            @Override // android.arch.lifecycle.LiveData
            protected fun onActive() {
                ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable)
            }
        }
    }

    @WorkerThread
    protected abstract Object compute()

    @NonNull
    fun getLiveData() {
        return this.mLiveData
    }

    fun invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(this.mInvalidationRunnable)
    }
}
