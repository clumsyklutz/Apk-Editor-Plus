package android.support.v4.view

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.UiThread
import android.support.v4.util.Pools
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.ArrayBlockingQueue

class AsyncLayoutInflater {
    private static val TAG = "AsyncLayoutInflater"
    LayoutInflater mInflater
    private Handler.Callback mHandlerCallback = new Handler.Callback() { // from class: android.support.v4.view.AsyncLayoutInflater.1
        @Override // android.os.Handler.Callback
        fun handleMessage(Message message) {
            InflateRequest inflateRequest = (InflateRequest) message.obj
            if (inflateRequest.view == null) {
                inflateRequest.view = AsyncLayoutInflater.this.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false)
            }
            inflateRequest.callback.onInflateFinished(inflateRequest.view, inflateRequest.resid, inflateRequest.parent)
            AsyncLayoutInflater.this.mInflateThread.releaseRequest(inflateRequest)
            return true
        }
    }
    Handler mHandler = Handler(this.mHandlerCallback)
    InflateThread mInflateThread = InflateThread.getInstance()

    class BasicInflater extends LayoutInflater {
        private static final Array<String> sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."}

        BasicInflater(Context context) {
            super(context)
        }

        @Override // android.view.LayoutInflater
        fun cloneInContext(Context context) {
            return BasicInflater(context)
        }

        @Override // android.view.LayoutInflater
        protected fun onCreateView(String str, AttributeSet attributeSet) {
            View viewCreateView
            for (String str2 : sClassPrefixList) {
                try {
                    viewCreateView = createView(str, str2, attributeSet)
                } catch (ClassNotFoundException e) {
                }
                if (viewCreateView != null) {
                    return viewCreateView
                }
            }
            return super.onCreateView(str, attributeSet)
        }
    }

    class InflateRequest {
        OnInflateFinishedListener callback
        AsyncLayoutInflater inflater
        ViewGroup parent
        Int resid
        View view

        InflateRequest() {
        }
    }

    class InflateThread extends Thread {
        private static final InflateThread sInstance
        private ArrayBlockingQueue mQueue = ArrayBlockingQueue(10)
        private Pools.SynchronizedPool mRequestPool = new Pools.SynchronizedPool(10)

        static {
            InflateThread inflateThread = InflateThread()
            sInstance = inflateThread
            inflateThread.start()
        }

        private constructor() {
        }

        fun getInstance() {
            return sInstance
        }

        fun enqueue(InflateRequest inflateRequest) throws InterruptedException {
            try {
                this.mQueue.put(inflateRequest)
            } catch (InterruptedException e) {
                throw RuntimeException("Failed to enqueue async inflate request", e)
            }
        }

        fun obtainRequest() {
            InflateRequest inflateRequest = (InflateRequest) this.mRequestPool.acquire()
            return inflateRequest == null ? InflateRequest() : inflateRequest
        }

        fun releaseRequest(InflateRequest inflateRequest) {
            inflateRequest.callback = null
            inflateRequest.inflater = null
            inflateRequest.parent = null
            inflateRequest.resid = 0
            inflateRequest.view = null
            this.mRequestPool.release(inflateRequest)
        }

        @Override // java.lang.Thread, java.lang.Runnable
        fun run() {
            while (true) {
                runInner()
            }
        }

        fun runInner() {
            try {
                InflateRequest inflateRequest = (InflateRequest) this.mQueue.take()
                try {
                    inflateRequest.view = inflateRequest.inflater.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false)
                } catch (RuntimeException e) {
                    Log.w(AsyncLayoutInflater.TAG, "Failed to inflate resource in the background! Retrying on the UI thread", e)
                }
                Message.obtain(inflateRequest.inflater.mHandler, 0, inflateRequest).sendToTarget()
            } catch (InterruptedException e2) {
                Log.w(AsyncLayoutInflater.TAG, e2)
            }
        }
    }

    public interface OnInflateFinishedListener {
        Unit onInflateFinished(@NonNull View view, @LayoutRes Int i, @Nullable ViewGroup viewGroup)
    }

    constructor(@NonNull Context context) {
        this.mInflater = BasicInflater(context)
    }

    @UiThread
    public final Unit inflate(@LayoutRes Int i, @Nullable ViewGroup viewGroup, @NonNull OnInflateFinishedListener onInflateFinishedListener) throws InterruptedException {
        if (onInflateFinishedListener == null) {
            throw NullPointerException("callback argument may not be null!")
        }
        InflateRequest inflateRequestObtainRequest = this.mInflateThread.obtainRequest()
        inflateRequestObtainRequest.inflater = this
        inflateRequestObtainRequest.resid = i
        inflateRequestObtainRequest.parent = viewGroup
        inflateRequestObtainRequest.callback = onInflateFinishedListener
        this.mInflateThread.enqueue(inflateRequestObtainRequest)
    }
}
