package android.support.v4.media.session

import android.app.PendingIntent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.IMediaControllerCallback
import android.support.v4.media.session.MediaSessionCompat
import android.text.TextUtils
import android.view.KeyEvent
import java.util.List

public interface IMediaSession extends IInterface {

    abstract class Stub extends Binder implements IMediaSession {
        private static val DESCRIPTOR = "android.support.v4.media.session.IMediaSession"
        static val TRANSACTION_addQueueItem = 41
        static val TRANSACTION_addQueueItemAt = 42
        static val TRANSACTION_adjustVolume = 11
        static val TRANSACTION_fastForward = 22
        static val TRANSACTION_getExtras = 31
        static val TRANSACTION_getFlags = 9
        static val TRANSACTION_getLaunchPendingIntent = 8
        static val TRANSACTION_getMetadata = 27
        static val TRANSACTION_getPackageName = 6
        static val TRANSACTION_getPlaybackState = 28
        static val TRANSACTION_getQueue = 29
        static val TRANSACTION_getQueueTitle = 30
        static val TRANSACTION_getRatingType = 32
        static val TRANSACTION_getRepeatMode = 37
        static val TRANSACTION_getShuffleMode = 47
        static val TRANSACTION_getTag = 7
        static val TRANSACTION_getVolumeAttributes = 10
        static val TRANSACTION_isCaptioningEnabled = 45
        static val TRANSACTION_isShuffleModeEnabledRemoved = 38
        static val TRANSACTION_isTransportControlEnabled = 5
        static val TRANSACTION_next = 20
        static val TRANSACTION_pause = 18
        static val TRANSACTION_play = 13
        static val TRANSACTION_playFromMediaId = 14
        static val TRANSACTION_playFromSearch = 15
        static val TRANSACTION_playFromUri = 16
        static val TRANSACTION_prepare = 33
        static val TRANSACTION_prepareFromMediaId = 34
        static val TRANSACTION_prepareFromSearch = 35
        static val TRANSACTION_prepareFromUri = 36
        static val TRANSACTION_previous = 21
        static val TRANSACTION_rate = 25
        static val TRANSACTION_rateWithExtras = 51
        static val TRANSACTION_registerCallbackListener = 3
        static val TRANSACTION_removeQueueItem = 43
        static val TRANSACTION_removeQueueItemAt = 44
        static val TRANSACTION_rewind = 23
        static val TRANSACTION_seekTo = 24
        static val TRANSACTION_sendCommand = 1
        static val TRANSACTION_sendCustomAction = 26
        static val TRANSACTION_sendMediaButton = 2
        static val TRANSACTION_setCaptioningEnabled = 46
        static val TRANSACTION_setRepeatMode = 39
        static val TRANSACTION_setShuffleMode = 48
        static val TRANSACTION_setShuffleModeEnabledRemoved = 40
        static val TRANSACTION_setVolumeTo = 12
        static val TRANSACTION_skipToQueueItem = 17
        static val TRANSACTION_stop = 19
        static val TRANSACTION_unregisterCallbackListener = 4

        class Proxy implements IMediaSession {
            private IBinder mRemote

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (mediaDescriptionCompat != null) {
                        parcelObtain.writeInt(1)
                        mediaDescriptionCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (mediaDescriptionCompat != null) {
                        parcelObtain.writeInt(1)
                        mediaDescriptionCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    parcelObtain.writeInt(i)
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun adjustVolume(Int i, Int i2, String str) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    parcelObtain.writeInt(i2)
                    parcelObtain.writeString(str)
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.os.IInterface
            fun asBinder() {
                return this.mRemote
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun fastForward() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getExtras() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2) : null
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getFlags() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readLong()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            fun getInterfaceDescriptor() {
                return Stub.DESCRIPTOR
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getLaunchPendingIntent() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcelObtain2) : null
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getMetadata() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0 ? (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel(parcelObtain2) : null
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getPackageName() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readString()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getPlaybackState() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0 ? (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel(parcelObtain2) : null
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getQueue() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR)
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getQueueTitle() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0 ? (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcelObtain2) : null
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getRatingType() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getRepeatMode() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getShuffleMode() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getTag() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readString()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getVolumeAttributes() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0 ? (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel(parcelObtain2) : null
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isCaptioningEnabled() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isShuffleModeEnabledRemoved() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isTransportControlEnabled() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun next() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun pause() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun play() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromMediaId(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromSearch(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromUri(Uri uri, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (uri != null) {
                        parcelObtain.writeInt(1)
                        uri.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepare() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromMediaId(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromSearch(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromUri(Uri uri, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (uri != null) {
                        parcelObtain.writeInt(1)
                        uri.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun previous() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rate(RatingCompat ratingCompat) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (ratingCompat != null) {
                        parcelObtain.writeInt(1)
                        ratingCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (ratingCompat != null) {
                        parcelObtain.writeInt(1)
                        ratingCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeStrongBinder(iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null)
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (mediaDescriptionCompat != null) {
                        parcelObtain.writeInt(1)
                        mediaDescriptionCompat.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun removeQueueItemAt(Int i) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rewind() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun seekTo(Long j) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeLong(j)
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendCommand(String str, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    if (resultReceiverWrapper != null) {
                        parcelObtain.writeInt(1)
                        resultReceiverWrapper.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendCustomAction(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeString(str)
                    if (bundle != null) {
                        parcelObtain.writeInt(1)
                        bundle.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendMediaButton(KeyEvent keyEvent) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    if (keyEvent != null) {
                        parcelObtain.writeInt(1)
                        keyEvent.writeToParcel(parcelObtain, 0)
                    } else {
                        parcelObtain.writeInt(0)
                    }
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                    return parcelObtain2.readInt() != 0
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setCaptioningEnabled(Boolean z) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(z ? 1 : 0)
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setRepeatMode(Int i) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setShuffleMode(Int i) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setShuffleModeEnabledRemoved(Boolean z) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(z ? 1 : 0)
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setVolumeTo(Int i, Int i2, String str) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeInt(i)
                    parcelObtain.writeInt(i2)
                    parcelObtain.writeString(str)
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun skipToQueueItem(Long j) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeLong(j)
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun stop() {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
                    parcelObtain.recycle()
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                Parcel parcelObtain = Parcel.obtain()
                Parcel parcelObtain2 = Parcel.obtain()
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR)
                    parcelObtain.writeStrongBinder(iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null)
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)
                    parcelObtain2.readException()
                } finally {
                    parcelObtain2.recycle()
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
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface is IMediaSession)) ? Proxy(iBinder) : (IMediaSession) iInterfaceQueryLocalInterface
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
                    sendCommand(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (MediaSessionCompat.ResultReceiverWrapper) MediaSessionCompat.ResultReceiverWrapper.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 2:
                    parcel.enforceInterface(DESCRIPTOR)
                    Boolean zSendMediaButton = sendMediaButton(parcel.readInt() != 0 ? (KeyEvent) KeyEvent.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    parcel2.writeInt(zSendMediaButton ? 1 : 0)
                    return true
                case 3:
                    parcel.enforceInterface(DESCRIPTOR)
                    registerCallbackListener(IMediaControllerCallback.Stub.asInterface(parcel.readStrongBinder()))
                    parcel2.writeNoException()
                    return true
                case 4:
                    parcel.enforceInterface(DESCRIPTOR)
                    unregisterCallbackListener(IMediaControllerCallback.Stub.asInterface(parcel.readStrongBinder()))
                    parcel2.writeNoException()
                    return true
                case 5:
                    parcel.enforceInterface(DESCRIPTOR)
                    Boolean zIsTransportControlEnabled = isTransportControlEnabled()
                    parcel2.writeNoException()
                    parcel2.writeInt(zIsTransportControlEnabled ? 1 : 0)
                    return true
                case 6:
                    parcel.enforceInterface(DESCRIPTOR)
                    String packageName = getPackageName()
                    parcel2.writeNoException()
                    parcel2.writeString(packageName)
                    return true
                case 7:
                    parcel.enforceInterface(DESCRIPTOR)
                    String tag = getTag()
                    parcel2.writeNoException()
                    parcel2.writeString(tag)
                    return true
                case 8:
                    parcel.enforceInterface(DESCRIPTOR)
                    PendingIntent launchPendingIntent = getLaunchPendingIntent()
                    parcel2.writeNoException()
                    if (launchPendingIntent == null) {
                        parcel2.writeInt(0)
                        return true
                    }
                    parcel2.writeInt(1)
                    launchPendingIntent.writeToParcel(parcel2, 1)
                    return true
                case 9:
                    parcel.enforceInterface(DESCRIPTOR)
                    Long flags = getFlags()
                    parcel2.writeNoException()
                    parcel2.writeLong(flags)
                    return true
                case 10:
                    parcel.enforceInterface(DESCRIPTOR)
                    ParcelableVolumeInfo volumeAttributes = getVolumeAttributes()
                    parcel2.writeNoException()
                    if (volumeAttributes == null) {
                        parcel2.writeInt(0)
                        return true
                    }
                    parcel2.writeInt(1)
                    volumeAttributes.writeToParcel(parcel2, 1)
                    return true
                case 11:
                    parcel.enforceInterface(DESCRIPTOR)
                    adjustVolume(parcel.readInt(), parcel.readInt(), parcel.readString())
                    parcel2.writeNoException()
                    return true
                case 12:
                    parcel.enforceInterface(DESCRIPTOR)
                    setVolumeTo(parcel.readInt(), parcel.readInt(), parcel.readString())
                    parcel2.writeNoException()
                    return true
                case 13:
                    parcel.enforceInterface(DESCRIPTOR)
                    play()
                    parcel2.writeNoException()
                    return true
                case 14:
                    parcel.enforceInterface(DESCRIPTOR)
                    playFromMediaId(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 15:
                    parcel.enforceInterface(DESCRIPTOR)
                    playFromSearch(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 16:
                    parcel.enforceInterface(DESCRIPTOR)
                    playFromUri(parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 17:
                    parcel.enforceInterface(DESCRIPTOR)
                    skipToQueueItem(parcel.readLong())
                    parcel2.writeNoException()
                    return true
                case 18:
                    parcel.enforceInterface(DESCRIPTOR)
                    pause()
                    parcel2.writeNoException()
                    return true
                case 19:
                    parcel.enforceInterface(DESCRIPTOR)
                    stop()
                    parcel2.writeNoException()
                    return true
                case 20:
                    parcel.enforceInterface(DESCRIPTOR)
                    next()
                    parcel2.writeNoException()
                    return true
                case 21:
                    parcel.enforceInterface(DESCRIPTOR)
                    previous()
                    parcel2.writeNoException()
                    return true
                case 22:
                    parcel.enforceInterface(DESCRIPTOR)
                    fastForward()
                    parcel2.writeNoException()
                    return true
                case 23:
                    parcel.enforceInterface(DESCRIPTOR)
                    rewind()
                    parcel2.writeNoException()
                    return true
                case 24:
                    parcel.enforceInterface(DESCRIPTOR)
                    seekTo(parcel.readLong())
                    parcel2.writeNoException()
                    return true
                case 25:
                    parcel.enforceInterface(DESCRIPTOR)
                    rate(parcel.readInt() != 0 ? (RatingCompat) RatingCompat.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 26:
                    parcel.enforceInterface(DESCRIPTOR)
                    sendCustomAction(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 27:
                    parcel.enforceInterface(DESCRIPTOR)
                    MediaMetadataCompat metadata = getMetadata()
                    parcel2.writeNoException()
                    if (metadata == null) {
                        parcel2.writeInt(0)
                        return true
                    }
                    parcel2.writeInt(1)
                    metadata.writeToParcel(parcel2, 1)
                    return true
                case 28:
                    parcel.enforceInterface(DESCRIPTOR)
                    PlaybackStateCompat playbackState = getPlaybackState()
                    parcel2.writeNoException()
                    if (playbackState == null) {
                        parcel2.writeInt(0)
                        return true
                    }
                    parcel2.writeInt(1)
                    playbackState.writeToParcel(parcel2, 1)
                    return true
                case 29:
                    parcel.enforceInterface(DESCRIPTOR)
                    List queue = getQueue()
                    parcel2.writeNoException()
                    parcel2.writeTypedList(queue)
                    return true
                case 30:
                    parcel.enforceInterface(DESCRIPTOR)
                    CharSequence queueTitle = getQueueTitle()
                    parcel2.writeNoException()
                    if (queueTitle == null) {
                        parcel2.writeInt(0)
                        return true
                    }
                    parcel2.writeInt(1)
                    TextUtils.writeToParcel(queueTitle, parcel2, 1)
                    return true
                case 31:
                    parcel.enforceInterface(DESCRIPTOR)
                    Bundle extras = getExtras()
                    parcel2.writeNoException()
                    if (extras == null) {
                        parcel2.writeInt(0)
                        return true
                    }
                    parcel2.writeInt(1)
                    extras.writeToParcel(parcel2, 1)
                    return true
                case 32:
                    parcel.enforceInterface(DESCRIPTOR)
                    Int ratingType = getRatingType()
                    parcel2.writeNoException()
                    parcel2.writeInt(ratingType)
                    return true
                case 33:
                    parcel.enforceInterface(DESCRIPTOR)
                    prepare()
                    parcel2.writeNoException()
                    return true
                case 34:
                    parcel.enforceInterface(DESCRIPTOR)
                    prepareFromMediaId(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 35:
                    parcel.enforceInterface(DESCRIPTOR)
                    prepareFromSearch(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 36:
                    parcel.enforceInterface(DESCRIPTOR)
                    prepareFromUri(parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 37:
                    parcel.enforceInterface(DESCRIPTOR)
                    Int repeatMode = getRepeatMode()
                    parcel2.writeNoException()
                    parcel2.writeInt(repeatMode)
                    return true
                case 38:
                    parcel.enforceInterface(DESCRIPTOR)
                    Boolean zIsShuffleModeEnabledRemoved = isShuffleModeEnabledRemoved()
                    parcel2.writeNoException()
                    parcel2.writeInt(zIsShuffleModeEnabledRemoved ? 1 : 0)
                    return true
                case 39:
                    parcel.enforceInterface(DESCRIPTOR)
                    setRepeatMode(parcel.readInt())
                    parcel2.writeNoException()
                    return true
                case 40:
                    parcel.enforceInterface(DESCRIPTOR)
                    setShuffleModeEnabledRemoved(parcel.readInt() != 0)
                    parcel2.writeNoException()
                    return true
                case 41:
                    parcel.enforceInterface(DESCRIPTOR)
                    addQueueItem(parcel.readInt() != 0 ? (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 42:
                    parcel.enforceInterface(DESCRIPTOR)
                    addQueueItemAt(parcel.readInt() != 0 ? (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel) : null, parcel.readInt())
                    parcel2.writeNoException()
                    return true
                case 43:
                    parcel.enforceInterface(DESCRIPTOR)
                    removeQueueItem(parcel.readInt() != 0 ? (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 44:
                    parcel.enforceInterface(DESCRIPTOR)
                    removeQueueItemAt(parcel.readInt())
                    parcel2.writeNoException()
                    return true
                case 45:
                    parcel.enforceInterface(DESCRIPTOR)
                    Boolean zIsCaptioningEnabled = isCaptioningEnabled()
                    parcel2.writeNoException()
                    parcel2.writeInt(zIsCaptioningEnabled ? 1 : 0)
                    return true
                case 46:
                    parcel.enforceInterface(DESCRIPTOR)
                    setCaptioningEnabled(parcel.readInt() != 0)
                    parcel2.writeNoException()
                    return true
                case 47:
                    parcel.enforceInterface(DESCRIPTOR)
                    Int shuffleMode = getShuffleMode()
                    parcel2.writeNoException()
                    parcel2.writeInt(shuffleMode)
                    return true
                case 48:
                    parcel.enforceInterface(DESCRIPTOR)
                    setShuffleMode(parcel.readInt())
                    parcel2.writeNoException()
                    return true
                case 51:
                    parcel.enforceInterface(DESCRIPTOR)
                    rateWithExtras(parcel.readInt() != 0 ? (RatingCompat) RatingCompat.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null)
                    parcel2.writeNoException()
                    return true
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR)
                    return true
                default:
                    return super.onTransact(i, parcel, parcel2, i2)
            }
        }
    }

    Unit addQueueItem(MediaDescriptionCompat mediaDescriptionCompat)

    Unit addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, Int i)

    Unit adjustVolume(Int i, Int i2, String str)

    Unit fastForward()

    Bundle getExtras()

    Long getFlags()

    PendingIntent getLaunchPendingIntent()

    MediaMetadataCompat getMetadata()

    String getPackageName()

    PlaybackStateCompat getPlaybackState()

    List getQueue()

    CharSequence getQueueTitle()

    Int getRatingType()

    Int getRepeatMode()

    Int getShuffleMode()

    String getTag()

    ParcelableVolumeInfo getVolumeAttributes()

    Boolean isCaptioningEnabled()

    Boolean isShuffleModeEnabledRemoved()

    Boolean isTransportControlEnabled()

    Unit next()

    Unit pause()

    Unit play()

    Unit playFromMediaId(String str, Bundle bundle)

    Unit playFromSearch(String str, Bundle bundle)

    Unit playFromUri(Uri uri, Bundle bundle)

    Unit prepare()

    Unit prepareFromMediaId(String str, Bundle bundle)

    Unit prepareFromSearch(String str, Bundle bundle)

    Unit prepareFromUri(Uri uri, Bundle bundle)

    Unit previous()

    Unit rate(RatingCompat ratingCompat)

    Unit rateWithExtras(RatingCompat ratingCompat, Bundle bundle)

    Unit registerCallbackListener(IMediaControllerCallback iMediaControllerCallback)

    Unit removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat)

    Unit removeQueueItemAt(Int i)

    Unit rewind()

    Unit seekTo(Long j)

    Unit sendCommand(String str, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper)

    Unit sendCustomAction(String str, Bundle bundle)

    Boolean sendMediaButton(KeyEvent keyEvent)

    Unit setCaptioningEnabled(Boolean z)

    Unit setRepeatMode(Int i)

    Unit setShuffleMode(Int i)

    Unit setShuffleModeEnabledRemoved(Boolean z)

    Unit setVolumeTo(Int i, Int i2, String str)

    Unit skipToQueueItem(Long j)

    Unit stop()

    Unit unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback)
}
