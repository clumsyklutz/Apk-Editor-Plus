package android.support.v4.media.session

import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.text.TextUtils
import java.util.List

public interface IMediaControllerCallback extends IInterface {

    abstract class Stub extends Binder implements IMediaControllerCallback {
        private static val DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback"
        static val TRANSACTION_onCaptioningEnabledChanged = 11
        static val TRANSACTION_onEvent = 1
        static val TRANSACTION_onExtrasChanged = 7
        static val TRANSACTION_onMetadataChanged = 4
        static val TRANSACTION_onPlaybackStateChanged = 3
        static val TRANSACTION_onQueueChanged = 5
        static val TRANSACTION_onQueueTitleChanged = 6
        static val TRANSACTION_onRepeatModeChanged = 9
        static val TRANSACTION_onSessionDestroyed = 2
        static val TRANSACTION_onSessionReady = 13
        static val TRANSACTION_onShuffleModeChanged = 12
        static val TRANSACTION_onShuffleModeChangedRemoved = 10
        static val TRANSACTION_onVolumeInfoChanged = 8

        class Proxy implements IMediaControllerCallback {
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

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onCaptioningEnabledChanged(Boolean z) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(z ? 1 : 0)
                    this.mRemote.transact(11, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onEvent(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
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

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onExtrasChanged(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(7, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (mediaMetadataCompat != null) {
                        parcelObtain.writeInt(1)
                        mediaMetadataCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(4, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (playbackStateCompat != null) {
                        parcelObtain.writeInt(1)
                        playbackStateCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(3, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onQueueChanged(List list) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeTypedList(list)
                    this.mRemote.transact(5, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onQueueTitleChanged(CharSequence charSequence) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (charSequence != null) {
                        parcelObtain.writeInt(1)
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(6, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onRepeatModeChanged(Int i) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    this.mRemote.transact(9, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onSessionDestroyed() {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(2, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onSessionReady() {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(13, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onShuffleModeChanged(Int i) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    this.mRemote.transact(12, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onShuffleModeChangedRemoved(Boolean z) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(z ? 1 : 0)
                    this.mRemote.transact(10, parcelObtain, null, 1)
                } finally {
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                Parcel parcelObtain = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (parcelableVolumeInfo != null) {
                        parcelObtain.writeInt(1)
                        parcelableVolumeInfo.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(8, parcelObtain, null, 1)
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
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface is IMediaControllerCallback)) ? Proxy(iBinder) : (IMediaControllerCallback) iInterfaceQueryLocalInterface
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
                    onEvent(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 2:
                    parcel.enforceInterface(DESCRIPTOR)
                    onSessionDestroyed()
                    return true
                case 3:
                    parcel.enforceInterface(DESCRIPTOR)
                    onPlaybackStateChanged(parcel.readInt() != 0 ? (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 4:
                    parcel.enforceInterface(DESCRIPTOR)
                    onMetadataChanged(parcel.readInt() != 0 ? (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 5:
                    parcel.enforceInterface(DESCRIPTOR)
                    onQueueChanged(parcel.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR))
                    return true
                case 6:
                    parcel.enforceInterface(DESCRIPTOR)
                    onQueueTitleChanged(parcel.readInt() != 0 ? (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null)
                    return true
                case 7:
                    parcel.enforceInterface(DESCRIPTOR)
                    onExtrasChanged(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 8:
                    parcel.enforceInterface(DESCRIPTOR)
                    onVolumeInfoChanged(parcel.readInt() != 0 ? (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel(parcel) : null)
                    return true
                case 9:
                    parcel.enforceInterface(DESCRIPTOR)
                    onRepeatModeChanged(parcel.readInt())
                    return true
                case 10:
                    parcel.enforceInterface(DESCRIPTOR)
                    onShuffleModeChangedRemoved(parcel.readInt() != 0)
                    return true
                case 11:
                    parcel.enforceInterface(DESCRIPTOR)
                    onCaptioningEnabledChanged(parcel.readInt() != 0)
                    return true
                case 12:
                    parcel.enforceInterface(DESCRIPTOR)
                    onShuffleModeChanged(parcel.readInt())
                    return true
                case 13:
                    parcel.enforceInterface(DESCRIPTOR)
                    onSessionReady()
                    return true
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR)
                    return true
                default:
                    return super.onTransact(i, parcel, parcel2, i2)
            }
        }
    }

    Unit onCaptioningEnabledChanged(Boolean z)

    Unit onEvent(String str, Bundle bundle)

    Unit onExtrasChanged(Bundle bundle)

    Unit onMetadataChanged(MediaMetadataCompat mediaMetadataCompat)

    Unit onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat)

    Unit onQueueChanged(List list)

    Unit onQueueTitleChanged(CharSequence charSequence)

    Unit onRepeatModeChanged(Int i)

    Unit onSessionDestroyed()

    Unit onSessionReady()

    Unit onShuffleModeChanged(Int i)

    Unit onShuffleModeChangedRemoved(Boolean z)

    Unit onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo)
}
