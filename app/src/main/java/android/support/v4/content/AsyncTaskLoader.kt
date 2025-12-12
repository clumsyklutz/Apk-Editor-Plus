package android.support.v4.content

import android.content.Context
import android.os.Handler
import android.os.SystemClock
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.os.OperationCanceledException
import android.support.v4.util.TimeUtils
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor

abstract class AsyncTaskLoader extends Loader {
    static val DEBUG = false
    static val TAG = "AsyncTaskLoader"
    volatile LoadTask mCancellingTask
    private final Executor mExecutor
    Handler mHandler
    Long mLastLoadCompleteTime
    volatile LoadTask mTask
    Long mUpdateThrottle

    final class LoadTask extends ModernAsyncTask implements Runnable {
        private val mDone = CountDownLatch(1)
        Boolean waiting

        LoadTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.support.v4.content.ModernAsyncTask
        public final Object doInBackground(Void... voidArr) {
            try {
                return AsyncTaskLoader.this.onLoadInBackground()
            } catch (OperationCanceledException e) {
                if (isCancelled()) {
                    return null
                }
                throw e
            }
        }

        @Override // android.support.v4.content.ModernAsyncTask
        protected final Unit onCancelled(Object obj) {
            try {
                AsyncTaskLoader.this.dispatchOnCancelled(this, obj)
            } finally {
                this.mDone.countDown()
            }
        }

        @Override // android.support.v4.content.ModernAsyncTask
        protected final Unit onPostExecute(Object obj) {
            try {
                AsyncTaskLoader.this.dispatchOnLoadComplete(this, obj)
            } finally {
                this.mDone.countDown()
            }
        }

        @Override // java.lang.Runnable
        public final Unit run() {
            this.waiting = false
            AsyncTaskLoader.this.executePendingTask()
        }

        public final Unit waitForLoader() throws InterruptedException {
            try {
                this.mDone.await()
            } catch (InterruptedException e) {
            }
        }
    }

    constructor(@NonNull Context context) {
        this(context, ModernAsyncTask.THREAD_POOL_EXECUTOR)
    }

    private constructor(@NonNull Context context, @NonNull Executor executor) {
        super(context)
        this.mLastLoadCompleteTime = -10000L
        this.mExecutor = executor
    }

    fun cancelLoadInBackground() {
    }

    Unit dispatchOnCancelled(LoadTask loadTask, Object obj) {
        onCanceled(obj)
        if (this.mCancellingTask == loadTask) {
            rollbackContentChanged()
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis()
            this.mCancellingTask = null
            deliverCancellation()
            executePendingTask()
        }
    }

    Unit dispatchOnLoadComplete(LoadTask loadTask, Object obj) {
        if (this.mTask != loadTask) {
            dispatchOnCancelled(loadTask, obj)
            return
        }
        if (isAbandoned()) {
            onCanceled(obj)
            return
        }
        commitContentChanged()
        this.mLastLoadCompleteTime = SystemClock.uptimeMillis()
        this.mTask = null
        deliverResult(obj)
    }

    @Override // android.support.v4.content.Loader
    @Deprecated
    fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr)
        if (this.mTask != null) {
            printWriter.print(str)
            printWriter.print("mTask=")
            printWriter.print(this.mTask)
            printWriter.print(" waiting=")
            printWriter.println(this.mTask.waiting)
        }
        if (this.mCancellingTask != null) {
            printWriter.print(str)
            printWriter.print("mCancellingTask=")
            printWriter.print(this.mCancellingTask)
            printWriter.print(" waiting=")
            printWriter.println(this.mCancellingTask.waiting)
        }
        if (this.mUpdateThrottle != 0) {
            printWriter.print(str)
            printWriter.print("mUpdateThrottle=")
            TimeUtils.formatDuration(this.mUpdateThrottle, printWriter)
            printWriter.print(" mLastLoadCompleteTime=")
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), printWriter)
            printWriter.println()
        }
    }

    Unit executePendingTask() {
        if (this.mCancellingTask != null || this.mTask == null) {
            return
        }
        if (this.mTask.waiting) {
            this.mTask.waiting = false
            this.mHandler.removeCallbacks(this.mTask)
        }
        if (this.mUpdateThrottle <= 0 || SystemClock.uptimeMillis() >= this.mLastLoadCompleteTime + this.mUpdateThrottle) {
            this.mTask.executeOnExecutor(this.mExecutor, null)
        } else {
            this.mTask.waiting = true
            this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle)
        }
    }

    fun isLoadInBackgroundCanceled() {
        return this.mCancellingTask != null
    }

    @Nullable
    public abstract Object loadInBackground()

    @Override // android.support.v4.content.Loader
    protected fun onCancelLoad() {
        Boolean zCancel = false
        if (this.mTask != null) {
            if (!this.mStarted) {
                this.mContentChanged = true
            }
            if (this.mCancellingTask != null) {
                if (this.mTask.waiting) {
                    this.mTask.waiting = false
                    this.mHandler.removeCallbacks(this.mTask)
                }
                this.mTask = null
            } else if (this.mTask.waiting) {
                this.mTask.waiting = false
                this.mHandler.removeCallbacks(this.mTask)
                this.mTask = null
            } else {
                zCancel = this.mTask.cancel(false)
                if (zCancel) {
                    this.mCancellingTask = this.mTask
                    cancelLoadInBackground()
                }
                this.mTask = null
            }
        }
        return zCancel
    }

    fun onCanceled(@Nullable Object obj) {
    }

    @Override // android.support.v4.content.Loader
    protected fun onForceLoad() {
        super.onForceLoad()
        cancelLoad()
        this.mTask = LoadTask()
        executePendingTask()
    }

    @Nullable
    protected fun onLoadInBackground() {
        return loadInBackground()
    }

    fun setUpdateThrottle(Long j) {
        this.mUpdateThrottle = j
        if (j != 0) {
            this.mHandler = Handler()
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun waitForLoader() throws InterruptedException {
        LoadTask loadTask = this.mTask
        if (loadTask != null) {
            loadTask.waitForLoader()
        }
    }
}
