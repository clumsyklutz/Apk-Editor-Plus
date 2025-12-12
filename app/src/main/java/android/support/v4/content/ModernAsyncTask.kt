package android.support.v4.content

import android.os.Binder
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Process
import android.support.annotation.RestrictTo
import android.util.Log
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.FutureTask
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

abstract class ModernAsyncTask {
    private static val CORE_POOL_SIZE = 5
    private static val KEEP_ALIVE = 1
    private static val LOG_TAG = "AsyncTask"
    private static val MAXIMUM_POOL_SIZE = 128
    private static val MESSAGE_POST_PROGRESS = 2
    private static val MESSAGE_POST_RESULT = 1
    public static final Executor THREAD_POOL_EXECUTOR
    private static volatile Executor sDefaultExecutor
    private static InternalHandler sHandler
    private static val sThreadFactory = ThreadFactory() { // from class: android.support.v4.content.ModernAsyncTask.1
        private val mCount = AtomicInteger(1)

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return Thread(runnable, "ModernAsyncTask #" + this.mCount.getAndIncrement())
        }
    }
    private static val sPoolWorkQueue = LinkedBlockingQueue(10)
    private volatile Status mStatus = Status.PENDING
    val mCancelled = AtomicBoolean()
    val mTaskInvoked = AtomicBoolean()
    private val mWorker = WorkerRunnable() { // from class: android.support.v4.content.ModernAsyncTask.2
        @Override // java.util.concurrent.Callable
        fun call() {
            ModernAsyncTask.this.mTaskInvoked.set(true)
            Object objDoInBackground = null
            try {
                try {
                    Process.setThreadPriority(10)
                    objDoInBackground = ModernAsyncTask.this.doInBackground(this.mParams)
                    Binder.flushPendingCommands()
                    return objDoInBackground
                } finally {
                }
            } finally {
                ModernAsyncTask.this.postResult(objDoInBackground)
            }
        }
    }
    private val mFuture = FutureTask(this.mWorker) { // from class: android.support.v4.content.ModernAsyncTask.3
        @Override // java.util.concurrent.FutureTask
        protected fun done() {
            try {
                ModernAsyncTask.this.postResultIfNotInvoked(get())
            } catch (InterruptedException e) {
                Log.w(ModernAsyncTask.LOG_TAG, e)
            } catch (CancellationException e2) {
                ModernAsyncTask.this.postResultIfNotInvoked(null)
            } catch (ExecutionException e3) {
                throw RuntimeException("An error occurred while executing doInBackground()", e3.getCause())
            } catch (Throwable th) {
                throw RuntimeException("An error occurred while executing doInBackground()", th)
            }
        }
    }

    class AsyncTaskResult {
        final Array<Object> mData
        final ModernAsyncTask mTask

        AsyncTaskResult(ModernAsyncTask modernAsyncTask, Object... objArr) {
            this.mTask = modernAsyncTask
            this.mData = objArr
        }
    }

    class InternalHandler extends Handler {
        InternalHandler() {
            super(Looper.getMainLooper())
        }

        @Override // android.os.Handler
        fun handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj
            switch (message.what) {
                case 1:
                    asyncTaskResult.mTask.finish(asyncTaskResult.mData[0])
                    break
                case 2:
                    asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData)
                    break
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    abstract class WorkerRunnable implements Callable {
        Array<Object> mParams

        WorkerRunnable() {
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, (BlockingQueue<Runnable>) sPoolWorkQueue, sThreadFactory)
        THREAD_POOL_EXECUTOR = threadPoolExecutor
        sDefaultExecutor = threadPoolExecutor
    }

    ModernAsyncTask() {
    }

    fun execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable)
    }

    private fun getHandler() {
        InternalHandler internalHandler
        synchronized (ModernAsyncTask.class) {
            if (sHandler == null) {
                sHandler = InternalHandler()
            }
            internalHandler = sHandler
        }
        return internalHandler
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setDefaultExecutor(Executor executor) {
        sDefaultExecutor = executor
    }

    public final Boolean cancel(Boolean z) {
        this.mCancelled.set(true)
        return this.mFuture.cancel(z)
    }

    protected abstract Object doInBackground(Object... objArr)

    public final ModernAsyncTask execute(Object... objArr) {
        return executeOnExecutor(sDefaultExecutor, objArr)
    }

    public final ModernAsyncTask executeOnExecutor(Executor executor, Object... objArr) {
        if (this.mStatus != Status.PENDING) {
            switch (this.mStatus) {
                case RUNNING:
                    throw IllegalStateException("Cannot execute task: the task is already running.")
                case FINISHED:
                    throw IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)")
                default:
                    throw IllegalStateException("We should never reach this state")
            }
        }
        this.mStatus = Status.RUNNING
        onPreExecute()
        this.mWorker.mParams = objArr
        executor.execute(this.mFuture)
        return this
    }

    Unit finish(Object obj) {
        if (isCancelled()) {
            onCancelled(obj)
        } else {
            onPostExecute(obj)
        }
        this.mStatus = Status.FINISHED
    }

    public final Object get() {
        return this.mFuture.get()
    }

    public final Object get(Long j, TimeUnit timeUnit) {
        return this.mFuture.get(j, timeUnit)
    }

    public final Status getStatus() {
        return this.mStatus
    }

    public final Boolean isCancelled() {
        return this.mCancelled.get()
    }

    protected fun onCancelled() {
    }

    protected fun onCancelled(Object obj) {
        onCancelled()
    }

    protected fun onPostExecute(Object obj) {
    }

    protected fun onPreExecute() {
    }

    protected fun onProgressUpdate(Object... objArr) {
    }

    Object postResult(Object obj) {
        getHandler().obtainMessage(1, AsyncTaskResult(this, obj)).sendToTarget()
        return obj
    }

    Unit postResultIfNotInvoked(Object obj) {
        if (this.mTaskInvoked.get()) {
            return
        }
        postResult(obj)
    }

    protected final Unit publishProgress(Object... objArr) {
        if (isCancelled()) {
            return
        }
        getHandler().obtainMessage(2, AsyncTaskResult(this, objArr)).sendToTarget()
    }
}
