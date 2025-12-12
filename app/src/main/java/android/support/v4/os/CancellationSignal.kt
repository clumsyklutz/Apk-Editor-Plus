package android.support.v4.os

import android.os.Build

class CancellationSignal {
    private Boolean mCancelInProgress
    private Object mCancellationSignalObj
    private Boolean mIsCanceled
    private OnCancelListener mOnCancelListener

    public interface OnCancelListener {
        Unit onCancel()
    }

    private fun waitForCancelFinishedLocked() throws InterruptedException {
        while (this.mCancelInProgress) {
            try {
                wait()
            } catch (InterruptedException e) {
            }
        }
    }

    public final Unit cancel() {
        synchronized (this) {
            if (this.mIsCanceled) {
                return
            }
            this.mIsCanceled = true
            this.mCancelInProgress = true
            OnCancelListener onCancelListener = this.mOnCancelListener
            Object obj = this.mCancellationSignalObj
            if (onCancelListener != null) {
                try {
                    onCancelListener.onCancel()
                } catch (Throwable th) {
                    synchronized (this) {
                        this.mCancelInProgress = false
                        notifyAll()
                        throw th
                    }
                }
            }
            if (obj != null && Build.VERSION.SDK_INT >= 16) {
                ((android.os.CancellationSignal) obj).cancel()
            }
            synchronized (this) {
                this.mCancelInProgress = false
                notifyAll()
            }
        }
    }

    public final Object getCancellationSignalObject() {
        Object obj
        if (Build.VERSION.SDK_INT < 16) {
            return null
        }
        synchronized (this) {
            if (this.mCancellationSignalObj == null) {
                this.mCancellationSignalObj = new android.os.CancellationSignal()
                if (this.mIsCanceled) {
                    ((android.os.CancellationSignal) this.mCancellationSignalObj).cancel()
                }
            }
            obj = this.mCancellationSignalObj
        }
        return obj
    }

    public final Boolean isCanceled() {
        Boolean z
        synchronized (this) {
            z = this.mIsCanceled
        }
        return z
    }

    public final Unit setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            waitForCancelFinishedLocked()
            if (this.mOnCancelListener == onCancelListener) {
                return
            }
            this.mOnCancelListener = onCancelListener
            if (!this.mIsCanceled || onCancelListener == null) {
                return
            }
            onCancelListener.onCancel()
        }
    }

    public final Unit throwIfCanceled() {
        if (isCanceled()) {
            throw OperationCanceledException()
        }
    }
}
