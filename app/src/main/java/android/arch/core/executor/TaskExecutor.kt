package android.arch.core.executor

import android.support.annotation.NonNull
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class TaskExecutor {
    public abstract Unit executeOnDiskIO(@NonNull Runnable runnable)

    fun executeOnMainThread(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run()
        } else {
            postToMainThread(runnable)
        }
    }

    public abstract Boolean isMainThread()

    public abstract Unit postToMainThread(@NonNull Runnable runnable)
}
