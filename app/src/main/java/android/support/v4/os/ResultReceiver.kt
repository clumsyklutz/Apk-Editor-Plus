package android.support.v4.os

import android.os.Bundle
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.os.RemoteException
import android.support.annotation.RestrictTo
import android.support.v4.os.IResultReceiver

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.os.ResultReceiver.1
        @Override // android.os.Parcelable.Creator
        public final ResultReceiver createFromParcel(Parcel parcel) {
            return ResultReceiver(parcel)
        }

        @Override // android.os.Parcelable.Creator
        public final Array<ResultReceiver> newArray(Int i) {
            return new ResultReceiver[i]
        }
    }
    final Handler mHandler
    final Boolean mLocal
    IResultReceiver mReceiver

    class MyResultReceiver extends IResultReceiver.Stub {
        MyResultReceiver() {
        }

        @Override // android.support.v4.os.IResultReceiver
        fun send(Int i, Bundle bundle) {
            if (ResultReceiver.this.mHandler != null) {
                ResultReceiver.this.mHandler.post(ResultReceiver.this.MyRunnable(i, bundle))
            } else {
                ResultReceiver.this.onReceiveResult(i, bundle)
            }
        }
    }

    class MyRunnable implements Runnable {
        final Int mResultCode
        final Bundle mResultData

        MyRunnable(Int i, Bundle bundle) {
            this.mResultCode = i
            this.mResultData = bundle
        }

        @Override // java.lang.Runnable
        fun run() {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData)
        }
    }

    constructor(Handler handler) {
        this.mLocal = true
        this.mHandler = handler
    }

    ResultReceiver(Parcel parcel) {
        this.mLocal = false
        this.mHandler = null
        this.mReceiver = IResultReceiver.Stub.asInterface(parcel.readStrongBinder())
    }

    @Override // android.os.Parcelable
    fun describeContents() {
        return 0
    }

    protected fun onReceiveResult(Int i, Bundle bundle) {
    }

    fun send(Int i, Bundle bundle) {
        if (this.mLocal) {
            if (this.mHandler != null) {
                this.mHandler.post(MyRunnable(i, bundle))
                return
            } else {
                onReceiveResult(i, bundle)
                return
            }
        }
        if (this.mReceiver != null) {
            try {
                this.mReceiver.send(i, bundle)
            } catch (RemoteException e) {
            }
        }
    }

    @Override // android.os.Parcelable
    fun writeToParcel(Parcel parcel, Int i) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = MyResultReceiver()
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder())
        }
    }
}
