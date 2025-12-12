package android.support.v4.content

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.util.DebugUtils
import java.io.FileDescriptor
import java.io.PrintWriter

class Loader {
    Context mContext
    Int mId
    OnLoadCompleteListener mListener
    OnLoadCanceledListener mOnLoadCanceledListener
    Boolean mStarted = false
    Boolean mAbandoned = false
    Boolean mReset = true
    Boolean mContentChanged = false
    Boolean mProcessingChange = false

    class ForceLoadContentObserver extends ContentObserver {
        constructor() {
            super(Handler())
        }

        @Override // android.database.ContentObserver
        public final Boolean deliverSelfNotifications() {
            return true
        }

        @Override // android.database.ContentObserver
        public final Unit onChange(Boolean z) {
            Loader.this.onContentChanged()
        }
    }

    public interface OnLoadCanceledListener {
        Unit onLoadCanceled(@NonNull Loader loader)
    }

    public interface OnLoadCompleteListener {
        Unit onLoadComplete(@NonNull Loader loader, @Nullable Object obj)
    }

    constructor(@NonNull Context context) {
        this.mContext = context.getApplicationContext()
    }

    @MainThread
    fun abandon() {
        this.mAbandoned = true
        onAbandon()
    }

    @MainThread
    fun cancelLoad() {
        return onCancelLoad()
    }

    fun commitContentChanged() {
        this.mProcessingChange = false
    }

    @NonNull
    fun dataToString(@Nullable Object obj) {
        StringBuilder sb = StringBuilder(64)
        DebugUtils.buildShortClassTag(obj, sb)
        sb.append("}")
        return sb.toString()
    }

    @MainThread
    fun deliverCancellation() {
        if (this.mOnLoadCanceledListener != null) {
            this.mOnLoadCanceledListener.onLoadCanceled(this)
        }
    }

    @MainThread
    fun deliverResult(@Nullable Object obj) {
        if (this.mListener != null) {
            this.mListener.onLoadComplete(this, obj)
        }
    }

    @Deprecated
    fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        printWriter.print(str)
        printWriter.print("mId=")
        printWriter.print(this.mId)
        printWriter.print(" mListener=")
        printWriter.println(this.mListener)
        if (this.mStarted || this.mContentChanged || this.mProcessingChange) {
            printWriter.print(str)
            printWriter.print("mStarted=")
            printWriter.print(this.mStarted)
            printWriter.print(" mContentChanged=")
            printWriter.print(this.mContentChanged)
            printWriter.print(" mProcessingChange=")
            printWriter.println(this.mProcessingChange)
        }
        if (this.mAbandoned || this.mReset) {
            printWriter.print(str)
            printWriter.print("mAbandoned=")
            printWriter.print(this.mAbandoned)
            printWriter.print(" mReset=")
            printWriter.println(this.mReset)
        }
    }

    @MainThread
    fun forceLoad() {
        onForceLoad()
    }

    @NonNull
    fun getContext() {
        return this.mContext
    }

    fun getId() {
        return this.mId
    }

    fun isAbandoned() {
        return this.mAbandoned
    }

    fun isReset() {
        return this.mReset
    }

    fun isStarted() {
        return this.mStarted
    }

    @MainThread
    protected fun onAbandon() {
    }

    @MainThread
    protected fun onCancelLoad() {
        return false
    }

    @MainThread
    fun onContentChanged() {
        if (this.mStarted) {
            forceLoad()
        } else {
            this.mContentChanged = true
        }
    }

    @MainThread
    protected fun onForceLoad() {
    }

    @MainThread
    protected fun onReset() {
    }

    @MainThread
    protected fun onStartLoading() {
    }

    @MainThread
    protected fun onStopLoading() {
    }

    @MainThread
    fun registerListener(Int i, @NonNull OnLoadCompleteListener onLoadCompleteListener) {
        if (this.mListener != null) {
            throw IllegalStateException("There is already a listener registered")
        }
        this.mListener = onLoadCompleteListener
        this.mId = i
    }

    @MainThread
    fun registerOnLoadCanceledListener(@NonNull OnLoadCanceledListener onLoadCanceledListener) {
        if (this.mOnLoadCanceledListener != null) {
            throw IllegalStateException("There is already a listener registered")
        }
        this.mOnLoadCanceledListener = onLoadCanceledListener
    }

    @MainThread
    fun reset() {
        onReset()
        this.mReset = true
        this.mStarted = false
        this.mAbandoned = false
        this.mContentChanged = false
        this.mProcessingChange = false
    }

    fun rollbackContentChanged() {
        if (this.mProcessingChange) {
            onContentChanged()
        }
    }

    @MainThread
    public final Unit startLoading() {
        this.mStarted = true
        this.mReset = false
        this.mAbandoned = false
        onStartLoading()
    }

    @MainThread
    fun stopLoading() {
        this.mStarted = false
        onStopLoading()
    }

    fun takeContentChanged() {
        Boolean z = this.mContentChanged
        this.mContentChanged = false
        this.mProcessingChange |= z
        return z
    }

    fun toString() {
        StringBuilder sb = StringBuilder(64)
        DebugUtils.buildShortClassTag(this, sb)
        sb.append(" id=")
        sb.append(this.mId)
        sb.append("}")
        return sb.toString()
    }

    @MainThread
    fun unregisterListener(@NonNull OnLoadCompleteListener onLoadCompleteListener) {
        if (this.mListener == null) {
            throw IllegalStateException("No listener register")
        }
        if (this.mListener != onLoadCompleteListener) {
            throw IllegalArgumentException("Attempting to unregister the wrong listener")
        }
        this.mListener = null
    }

    @MainThread
    fun unregisterOnLoadCanceledListener(@NonNull OnLoadCanceledListener onLoadCanceledListener) {
        if (this.mOnLoadCanceledListener == null) {
            throw IllegalStateException("No listener register")
        }
        if (this.mOnLoadCanceledListener != onLoadCanceledListener) {
            throw IllegalArgumentException("Attempting to unregister the wrong listener")
        }
        this.mOnLoadCanceledListener = null
    }
}
