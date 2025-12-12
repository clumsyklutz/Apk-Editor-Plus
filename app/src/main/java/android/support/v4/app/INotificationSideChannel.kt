package android.support.v4.app

import android.app.Notification
import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

public interface INotificationSideChannel extends IInterface {

    abstract class Stub extends Binder implements INotificationSideChannel {
        private static val DESCRIPTOR = "android.support.v4.app.INotificationSideChannel"
        static val TRANSACTION_cancel = 2
        static val TRANSACTION_cancelAll = 3
        static val TRANSACTION_notify = 1

        class Proxy implements INotificationSideChannel {
            private IBinder mRemote

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder
            }

            @Override // android.os.IInterface
            fun asBinder() {
                return this.mRemote
            }

            @Override // android.support.v4.app.INotificationSideChannel
            fun cancel(String str, Int i, String str2) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    parcelObtain.writeInt(i)
                    parcelObtain.writeString(str2)
                    this.mRemote.transact(2, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.app.INotificationSideChannel
            fun cancelAll(String str) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    this.mRemote.transact(3, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            fun getInterfaceDescriptor() {
                return Stub.DESCRIPTOR
            }

            @Override // android.support.v4.app.INotificationSideChannel
            fun notify(String str, Int i, String str2, Notification notification) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    parcelObtain.writeInt(i)
                    parcelObtain.writeString(str2)
                    if (notification != null) {
                        parcelObtain.writeInt(1)
                        notification.writeToParcel(parcelObtain, 0)
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
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface is INotificationSideChannel)) ? Proxy(iBinder) : (INotificationSideChannel) iInterfaceQueryLocalInterface
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
                    notify(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt() != 0 ? (Notification) Notification.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 2:
                    parcel.enforceInterface(DESCRIPTOR)
                    cancel(parcel.readString(), parcel.readInt(), parcel.readString())
                    return true
                case 3:
                    parcel.enforceInterface(DESCRIPTOR)
                    cancelAll(parcel.readString())
                    return true
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR)
                    return true
                default:
                    return super.onTransact(i, parcel, parcel2, i2)
            }
        }
    }

    Unit cancel(String str, Int i, String str2)

    Unit cancelAll(String str)

    Unit notify(String str, Int i, String str2, Notification notification)
}
