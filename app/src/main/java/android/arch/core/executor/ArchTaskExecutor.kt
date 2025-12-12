package android.arch.core.executor

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import java.util.concurrent.Executor

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor sInstance

    @NonNull
    private TaskExecutor mDefaultTaskExecutor = DefaultTaskExecutor()

    @NonNull
    private TaskExecutor mDelegate = this.mDefaultTaskExecutor

    @NonNull
    private static val sMainThreadExecutor = Executor() { // from class: android.arch.core.executor.ArchTaskExecutor.1
        @Override // java.util.concurrent.Executor
        public final Unit execute(Runnable runnable) {
            ArchTaskExecutor.getInstance().postToMainThread(runnable)
        }
    }

    @NonNull
    private static val sIOThreadExecutor = Executor() { // from class: android.arch.core.executor.ArchTaskExecutor.2
        @Override // java.util.concurrent.Executor
        public final Unit execute(Runnable runnable) {
            ArchTaskExecutor.getInstance().executeOnDiskIO(runnable)
        }
    }

    private constructor() {
    }

    @NonNull
    fun getIOThreadExecutor() {
        return sIOThreadExecutor
    }

    @NonNull
    fun getInstance() {
        if (sInstance != null) {
            return sInstance
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = ArchTaskExecutor()
            }
        }
        return sInstance
    }

    @NonNull
    fun getMainThreadExecutor() {
        return sMainThreadExecutor
    }

    @Override // android.arch.core.executor.TaskExecutor
    fun executeOnDiskIO(Runnable runnable) {
        this.mDelegate.executeOnDiskIO(runnable)
    }

    @Override // android.arch.core.executor.TaskExecutor
    fun isMainThread() {
        return this.mDelegate.isMainThread()
    }

    @Override // android.arch.core.executor.TaskExecutor
    fun postToMainThread(Runnable runnable) {
        this.mDelegate.postToMainThread(runnable)
    }

    fun setDelegate(@Nullable TaskExecutor taskExecutor) {
        if (taskExecutor == null) {
            taskExecutor = this.mDefaultTaskExecutor
        }
        this.mDelegate = taskExecutor
    }
}
