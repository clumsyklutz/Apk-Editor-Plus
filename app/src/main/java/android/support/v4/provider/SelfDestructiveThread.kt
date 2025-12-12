package android.support.v4.provider

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.support.annotation.GuardedBy
import android.support.annotation.RestrictTo
import android.support.annotation.VisibleForTesting
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SelfDestructiveThread {
    private static val MSG_DESTRUCTION = 0
    private static val MSG_INVOKE_RUNNABLE = 1
    private final Int mDestructAfterMillisec

    @GuardedBy("mLock")
    private Handler mHandler
    private final Int mPriority

    @GuardedBy("mLock")
    private HandlerThread mThread
    private final String mThreadName
    private val mLock = Object()
    private Handler.Callback mCallback = new Handler.Callback() { // from class: android.support.v4.provider.SelfDestructiveThread.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler.Callback
        fun handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    SelfDestructiveThread.this.onDestruction()
                    return true
                case 1:
                    SelfDestructiveThread.this.onInvokeRunnable((Runnable) message.obj)
                    return true
                default:
                    return true
            }
        }
    }

    @GuardedBy("mLock")
    private Int mGeneration = 0

    public interface ReplyCallback {
        Unit onReply(Object obj)
    }

    constructor(String str, Int i, Int i2) {
        this.mThreadName = str
        this.mPriority = i
        this.mDestructAfterMillisec = i2
    }

    private fun post(Runnable runnable) {
        synchronized (this.mLock) {
            if (this.mThread == null) {
                this.mThread = HandlerThread(this.mThreadName, this.mPriority)
                this.mThread.start()
                this.mHandler = Handler(this.mThread.getLooper(), this.mCallback)
                this.mGeneration++
            }
            this.mHandler.removeMessages(0)
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, runnable))
        }
    }

    @VisibleForTesting
    fun getGeneration() {
        Int i
        synchronized (this.mLock) {
            i = this.mGeneration
        }
        return i
    }

    @VisibleForTesting
    fun isRunning() {
        Boolean z
        synchronized (this.mLock) {
            z = this.mThread != null
        }
        return z
    }

    Unit onDestruction() {
        synchronized (this.mLock) {
            if (this.mHandler.hasMessages(1)) {
                return
            }
            this.mThread.quit()
            this.mThread = null
            this.mHandler = null
        }
    }

    Unit onInvokeRunnable(Runnable runnable) {
        runnable.run()
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0)
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), this.mDestructAfterMillisec)
        }
    }

    fun postAndReply(final Callable callable, final ReplyCallback replyCallback) {
        val handler = Handler()
        post(Runnable() { // from class: android.support.v4.provider.SelfDestructiveThread.2
            @Override // java.lang.Runnable
            fun run() throws Exception {
                final Object objCall
                try {
                    objCall = callable.call()
                } catch (Exception e) {
                    objCall = null
                }
                handler.post(Runnable() { // from class: android.support.v4.provider.SelfDestructiveThread.2.1
                    @Override // java.lang.Runnable
                    fun run() {
                        replyCallback.onReply(objCall)
                    }
                })
            }
        })
    }

    fun postAndWait(final Callable callable, Int i) {
        Object obj
        val reentrantLock = ReentrantLock()
        val conditionNewCondition = reentrantLock.newCondition()
        val atomicReference = AtomicReference()
        val atomicBoolean = AtomicBoolean(true)
        post(Runnable() { // from class: android.support.v4.provider.SelfDestructiveThread.3
            @Override // java.lang.Runnable
            fun run() {
                try {
                    atomicReference.set(callable.call())
                } catch (Exception e) {
                }
                reentrantLock.lock()
                try {
                    atomicBoolean.set(false)
                    conditionNewCondition.signal()
                } finally {
                    reentrantLock.unlock()
                }
            }
        })
        reentrantLock.lock()
        try {
            if (atomicBoolean.get()) {
                Long nanos = TimeUnit.MILLISECONDS.toNanos(i)
                do {
                    try {
                        nanos = conditionNewCondition.awaitNanos(nanos)
                    } catch (InterruptedException e) {
                    }
                    if (!atomicBoolean.get()) {
                        obj = atomicReference.get()
                    }
                } while (nanos > 0);
                throw InterruptedException("timeout")
            }
            obj = atomicReference.get()
            return obj
        } finally {
            reentrantLock.unlock()
        }
    }
}
