package android.support.v4.os

import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

public interface IResultReceiver extends IInterface {

    abstract class Stub extends Binder implements IResultReceiver {
        private static val DESCRIPTOR = "android.support.v4.os.IResultReceiver"
        static val TRANSACTION_send = 1

        class Proxy implements IResultReceiver {
            private IBinder mRemote

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder
            }

            @Override // android.os.IInterface
            fun asBinder() {
                return this.mRemote
            }

            fun getInterfaceDescriptor() {
                return Stub.DESCRIPTOR
            }

            @Override // android.support.v4.os.IResultReceiver
            fun send(Int i, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(1, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }
        }

        constructor() {
            attachInterface(this, DESCRIPTOR)
        }

        fun asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR)
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface is IResultReceiver)) ? Proxy(iBinder) : (IResultReceiver) iInterfaceQueryLocalInterface
        }

        @Override // android.os.IInterface
        fun asBinder() {
            return this
        }

        @Override // android.os.Binder
        fun onTransact(Int i, Parcel parcel, Parcel parcel2, Int i2) {
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR)
                    send(parcel.readInt(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR)
                    return true
                default:
                    return super.onTransact(i, parcel, parcel2, i2)
            }
        }
    }

    Unit send(Int i, Bundle bundle)
}
