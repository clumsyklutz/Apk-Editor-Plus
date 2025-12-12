package android.support.v4.media.session

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.RemoteException
import android.os.ResultReceiver
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.support.v4.app.BundleCompat
import android.support.v4.app.SupportActivity
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.IMediaControllerCallback
import android.support.v4.media.session.IMediaSession
import android.support.v4.media.session.MediaControllerCompatApi21
import android.support.v4.media.session.MediaControllerCompatApi23
import android.support.v4.media.session.MediaControllerCompatApi24
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import jadx.core.deobf.Deobfuscator
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List

class MediaControllerCompat {
    static val COMMAND_ADD_QUEUE_ITEM = "android.support.v4.media.session.command.ADD_QUEUE_ITEM"
    static val COMMAND_ADD_QUEUE_ITEM_AT = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT"
    static val COMMAND_ARGUMENT_INDEX = "android.support.v4.media.session.command.ARGUMENT_INDEX"
    static val COMMAND_ARGUMENT_MEDIA_DESCRIPTION = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"
    static val COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER"
    static val COMMAND_REMOVE_QUEUE_ITEM = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM"
    static val COMMAND_REMOVE_QUEUE_ITEM_AT = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT"
    static val TAG = "MediaControllerCompat"
    private final MediaControllerImpl mImpl
    private val mRegisteredCallbacks = HashSet()
    private final MediaSessionCompat.Token mToken

    abstract class Callback implements IBinder.DeathRecipient {
        private final Object mCallbackObj
        MessageHandler mHandler
        Boolean mHasExtraCallback

        class MessageHandler extends Handler {
            private static val MSG_DESTROYED = 8
            private static val MSG_EVENT = 1
            private static val MSG_SESSION_READY = 13
            private static val MSG_UPDATE_CAPTIONING_ENABLED = 11
            private static val MSG_UPDATE_EXTRAS = 7
            private static val MSG_UPDATE_METADATA = 3
            private static val MSG_UPDATE_PLAYBACK_STATE = 2
            private static val MSG_UPDATE_QUEUE = 5
            private static val MSG_UPDATE_QUEUE_TITLE = 6
            private static val MSG_UPDATE_REPEAT_MODE = 9
            private static val MSG_UPDATE_SHUFFLE_MODE = 12
            private static val MSG_UPDATE_VOLUME = 4
            Boolean mRegistered

            MessageHandler(Looper looper) {
                super(looper)
                this.mRegistered = false
            }

            @Override // android.os.Handler
            fun handleMessage(Message message) {
                if (this.mRegistered) {
                    switch (message.what) {
                        case 1:
                            Callback.this.onSessionEvent((String) message.obj, message.getData())
                            break
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj)
                            break
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj)
                            break
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) message.obj)
                            break
                        case 5:
                            Callback.this.onQueueChanged((List) message.obj)
                            break
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) message.obj)
                            break
                        case 7:
                            Callback.this.onExtrasChanged((Bundle) message.obj)
                            break
                        case 8:
                            Callback.this.onSessionDestroyed()
                            break
                        case 9:
                            Callback.this.onRepeatModeChanged(((Integer) message.obj).intValue())
                            break
                        case 11:
                            Callback.this.onCaptioningEnabledChanged(((Boolean) message.obj).booleanValue())
                            break
                        case 12:
                            Callback.this.onShuffleModeChanged(((Integer) message.obj).intValue())
                            break
                        case 13:
                            Callback.this.onSessionReady()
                            break
                    }
                }
            }
        }

        class StubApi21 implements MediaControllerCompatApi21.Callback {
            private final WeakReference mCallback

            StubApi21(Callback callback) {
                this.mCallback = WeakReference(callback)
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onAudioInfoChanged(Int i, Int i2, Int i3, Int i4, Int i5) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.onAudioInfoChanged(PlaybackInfo(i, i2, i3, i4, i5))
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onExtrasChanged(Bundle bundle) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.onExtrasChanged(bundle)
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onMetadataChanged(Object obj) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(obj))
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onPlaybackStateChanged(Object obj) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback == null || callback.mHasExtraCallback) {
                    return
                }
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(obj))
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onQueueChanged(List list) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(list))
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.onQueueTitleChanged(charSequence)
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onSessionDestroyed() {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.onSessionDestroyed()
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            fun onSessionEvent(String str, Bundle bundle) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    if (!callback.mHasExtraCallback || Build.VERSION.SDK_INT >= 23) {
                        callback.onSessionEvent(str, bundle)
                    }
                }
            }
        }

        class StubCompat extends IMediaControllerCallback.Stub {
            private final WeakReference mCallback

            StubCompat(Callback callback) {
                this.mCallback = WeakReference(callback)
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onCaptioningEnabledChanged(Boolean z) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(11, Boolean.valueOf(z), null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onEvent(String str, Bundle bundle) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(1, str, bundle)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onExtrasChanged(Bundle bundle) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(7, bundle, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(3, mediaMetadataCompat, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(2, playbackStateCompat, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onQueueChanged(List list) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(5, list, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(6, charSequence, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onRepeatModeChanged(Int i) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(9, Integer.valueOf(i), null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onSessionDestroyed() {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(8, null, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onSessionReady() {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(13, null, null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onShuffleModeChanged(Int i) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(12, Integer.valueOf(i), null)
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onShuffleModeChangedRemoved(Boolean z) {
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            fun onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                Callback callback = (Callback) this.mCallback.get()
                if (callback != null) {
                    callback.postToHandler(4, parcelableVolumeInfo != null ? PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume) : null, null)
                }
            }
        }

        constructor() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaControllerCompatApi21.createCallback(StubApi21(this))
            } else {
                this.mCallbackObj = StubCompat(this)
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        fun binderDied() {
            onSessionDestroyed()
        }

        fun onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        fun onCaptioningEnabledChanged(Boolean z) {
        }

        fun onExtrasChanged(Bundle bundle) {
        }

        fun onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        fun onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        fun onQueueChanged(List list) {
        }

        fun onQueueTitleChanged(CharSequence charSequence) {
        }

        fun onRepeatModeChanged(Int i) {
        }

        fun onSessionDestroyed() {
        }

        fun onSessionEvent(String str, Bundle bundle) {
        }

        fun onSessionReady() {
        }

        fun onShuffleModeChanged(Int i) {
        }

        Unit postToHandler(Int i, Object obj, Bundle bundle) {
            if (this.mHandler != null) {
                Message messageObtainMessage = this.mHandler.obtainMessage(i, obj)
                messageObtainMessage.setData(bundle)
                messageObtainMessage.sendToTarget()
            }
        }

        Unit setHandler(Handler handler) {
            if (handler != null) {
                this.mHandler = MessageHandler(handler.getLooper())
                this.mHandler.mRegistered = true
            } else if (this.mHandler != null) {
                this.mHandler.mRegistered = false
                this.mHandler.removeCallbacksAndMessages(null)
                this.mHandler = null
            }
        }
    }

    class MediaControllerExtraData extends SupportActivity.ExtraData {
        private final MediaControllerCompat mMediaController

        MediaControllerExtraData(MediaControllerCompat mediaControllerCompat) {
            this.mMediaController = mediaControllerCompat
        }

        MediaControllerCompat getMediaController() {
            return this.mMediaController
        }
    }

    interface MediaControllerImpl {
        Unit addQueueItem(MediaDescriptionCompat mediaDescriptionCompat)

        Unit addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, Int i)

        Unit adjustVolume(Int i, Int i2)

        Boolean dispatchMediaButtonEvent(KeyEvent keyEvent)

        Bundle getExtras()

        Long getFlags()

        Object getMediaController()

        MediaMetadataCompat getMetadata()

        String getPackageName()

        PlaybackInfo getPlaybackInfo()

        PlaybackStateCompat getPlaybackState()

        List getQueue()

        CharSequence getQueueTitle()

        Int getRatingType()

        Int getRepeatMode()

        PendingIntent getSessionActivity()

        Int getShuffleMode()

        TransportControls getTransportControls()

        Boolean isCaptioningEnabled()

        Boolean isSessionReady()

        Unit registerCallback(Callback callback, Handler handler)

        Unit removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat)

        Unit sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver)

        Unit setVolumeTo(Int i, Int i2)

        Unit unregisterCallback(Callback callback)
    }

    @RequiresApi(21)
    class MediaControllerImplApi21 implements MediaControllerImpl {
        protected final Object mControllerObj
        private IMediaSession mExtraBinder
        private val mPendingCallbacks = ArrayList()
        private HashMap mCallbackMap = HashMap()

        class ExtraBinderRequestResultReceiver extends ResultReceiver {
            private WeakReference mMediaControllerImpl

            constructor(MediaControllerImplApi21 mediaControllerImplApi21, Handler handler) {
                super(handler)
                this.mMediaControllerImpl = WeakReference(mediaControllerImplApi21)
            }

            @Override // android.os.ResultReceiver
            protected fun onReceiveResult(Int i, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerImplApi21) this.mMediaControllerImpl.get()
                if (mediaControllerImplApi21 == null || bundle == null) {
                    return
                }
                mediaControllerImplApi21.mExtraBinder = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER"))
                mediaControllerImplApi21.processPendingCallbacks()
            }
        }

        class ExtraCallback extends Callback.StubCompat {
            ExtraCallback(Callback callback) {
                super(callback)
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            fun onExtrasChanged(Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            fun onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            fun onQueueChanged(List list) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            fun onQueueTitleChanged(CharSequence charSequence) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            fun onSessionDestroyed() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            fun onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                throw AssertionError()
            }
        }

        constructor(Context context, MediaSessionCompat.Token token) throws RemoteException {
            this.mControllerObj = MediaControllerCompatApi21.fromToken(context, token.getToken())
            if (this.mControllerObj == null) {
                throw RemoteException()
            }
            this.mExtraBinder = token.getExtraBinder()
            if (this.mExtraBinder == null) {
                requestExtraBinder()
            }
        }

        constructor(Context context, MediaSessionCompat mediaSessionCompat) {
            this.mControllerObj = MediaControllerCompatApi21.fromToken(context, mediaSessionCompat.getSessionToken().getToken())
            this.mExtraBinder = mediaSessionCompat.getSessionToken().getExtraBinder()
            if (this.mExtraBinder == null) {
                requestExtraBinder()
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun processPendingCallbacks() {
            if (this.mExtraBinder == null) {
                return
            }
            synchronized (this.mPendingCallbacks) {
                for (Callback callback : this.mPendingCallbacks) {
                    ExtraCallback extraCallback = ExtraCallback(callback)
                    this.mCallbackMap.put(callback, extraCallback)
                    callback.mHasExtraCallback = true
                    try {
                        this.mExtraBinder.registerCallbackListener(extraCallback)
                        callback.onSessionReady()
                    } catch (RemoteException e) {
                        Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback.", e)
                    }
                }
                this.mPendingCallbacks.clear()
            }
        }

        private fun requestExtraBinder() {
            sendCommand(MediaControllerCompat.COMMAND_GET_EXTRA_BINDER, null, ExtraBinderRequestResultReceiver(this, Handler()))
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((getFlags() & 4) == 0) {
                throw UnsupportedOperationException("This session doesn't support queue management operations")
            }
            Bundle bundle = Bundle()
            bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat)
            sendCommand(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM, bundle, null)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
            if ((getFlags() & 4) == 0) {
                throw UnsupportedOperationException("This session doesn't support queue management operations")
            }
            Bundle bundle = Bundle()
            bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat)
            bundle.putInt(MediaControllerCompat.COMMAND_ARGUMENT_INDEX, i)
            sendCommand(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM_AT, bundle, null)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun adjustVolume(Int i, Int i2) {
            MediaControllerCompatApi21.adjustVolume(this.mControllerObj, i, i2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun dispatchMediaButtonEvent(KeyEvent keyEvent) {
            return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, keyEvent)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getExtras() {
            return MediaControllerCompatApi21.getExtras(this.mControllerObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getFlags() {
            return MediaControllerCompatApi21.getFlags(this.mControllerObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getMediaController() {
            return this.mControllerObj
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getMetadata() {
            Object metadata = MediaControllerCompatApi21.getMetadata(this.mControllerObj)
            if (metadata != null) {
                return MediaMetadataCompat.fromMediaMetadata(metadata)
            }
            return null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getPackageName() {
            return MediaControllerCompatApi21.getPackageName(this.mControllerObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getPlaybackInfo() {
            Object playbackInfo = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj)
            if (playbackInfo != null) {
                return PlaybackInfo(MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(playbackInfo))
            }
            return null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getPlaybackState() {
            if (this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.getPlaybackState()
                } catch (RemoteException e) {
                    Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackState.", e)
                }
            }
            Object playbackState = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj)
            if (playbackState != null) {
                return PlaybackStateCompat.fromPlaybackState(playbackState)
            }
            return null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getQueue() {
            List queue = MediaControllerCompatApi21.getQueue(this.mControllerObj)
            if (queue != null) {
                return MediaSessionCompat.QueueItem.fromQueueItemList(queue)
            }
            return null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getQueueTitle() {
            return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getRatingType() {
            if (Build.VERSION.SDK_INT < 22 && this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.getRatingType()
                } catch (RemoteException e) {
                    Log.e(MediaControllerCompat.TAG, "Dead object in getRatingType.", e)
                }
            }
            return MediaControllerCompatApi21.getRatingType(this.mControllerObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getRepeatMode() {
            if (this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.getRepeatMode()
                } catch (RemoteException e) {
                    Log.e(MediaControllerCompat.TAG, "Dead object in getRepeatMode.", e)
                }
            }
            return -1
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getSessionActivity() {
            return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getShuffleMode() {
            if (this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.getShuffleMode()
                } catch (RemoteException e) {
                    Log.e(MediaControllerCompat.TAG, "Dead object in getShuffleMode.", e)
                }
            }
            return -1
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getTransportControls() {
            Object transportControls = MediaControllerCompatApi21.getTransportControls(this.mControllerObj)
            if (transportControls != null) {
                return TransportControlsApi21(transportControls)
            }
            return null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun isCaptioningEnabled() {
            if (this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.isCaptioningEnabled()
                } catch (RemoteException e) {
                    Log.e(MediaControllerCompat.TAG, "Dead object in isCaptioningEnabled.", e)
                }
            }
            return false
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun isSessionReady() {
            return this.mExtraBinder != null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public final Unit registerCallback(Callback callback, Handler handler) {
            MediaControllerCompatApi21.registerCallback(this.mControllerObj, callback.mCallbackObj, handler)
            if (this.mExtraBinder == null) {
                synchronized (this.mPendingCallbacks) {
                    callback.mHasExtraCallback = false
                    this.mPendingCallbacks.add(callback)
                }
                return
            }
            ExtraCallback extraCallback = ExtraCallback(callback)
            this.mCallbackMap.put(callback, extraCallback)
            callback.mHasExtraCallback = true
            try {
                this.mExtraBinder.registerCallbackListener(extraCallback)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((getFlags() & 4) == 0) {
                throw UnsupportedOperationException("This session doesn't support queue management operations")
            }
            Bundle bundle = Bundle()
            bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat)
            sendCommand(MediaControllerCompat.COMMAND_REMOVE_QUEUE_ITEM, bundle, null)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            MediaControllerCompatApi21.sendCommand(this.mControllerObj, str, bundle, resultReceiver)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun setVolumeTo(Int i, Int i2) {
            MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, i, i2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public final Unit unregisterCallback(Callback callback) {
            MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, callback.mCallbackObj)
            if (this.mExtraBinder == null) {
                synchronized (this.mPendingCallbacks) {
                    this.mPendingCallbacks.remove(callback)
                }
                return
            }
            try {
                ExtraCallback extraCallback = (ExtraCallback) this.mCallbackMap.remove(callback)
                if (extraCallback != null) {
                    callback.mHasExtraCallback = false
                    this.mExtraBinder.unregisterCallbackListener(extraCallback)
                }
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in unregisterCallback.", e)
            }
        }
    }

    @RequiresApi(23)
    class MediaControllerImplApi23 extends MediaControllerImplApi21 {
        constructor(Context context, MediaSessionCompat.Token token) {
            super(context, token)
        }

        constructor(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21, android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getTransportControls() {
            Object transportControls = MediaControllerCompatApi21.getTransportControls(this.mControllerObj)
            if (transportControls != null) {
                return TransportControlsApi23(transportControls)
            }
            return null
        }
    }

    @RequiresApi(24)
    class MediaControllerImplApi24 extends MediaControllerImplApi23 {
        constructor(Context context, MediaSessionCompat.Token token) {
            super(context, token)
        }

        constructor(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi23, android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21, android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getTransportControls() {
            Object transportControls = MediaControllerCompatApi21.getTransportControls(this.mControllerObj)
            if (transportControls != null) {
                return TransportControlsApi24(transportControls)
            }
            return null
        }
    }

    class MediaControllerImplBase implements MediaControllerImpl {
        private IMediaSession mBinder
        private TransportControls mTransportControls

        constructor(MediaSessionCompat.Token token) {
            this.mBinder = IMediaSession.Stub.asInterface((IBinder) token.getToken())
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.mBinder.getFlags() & 4) == 0) {
                    throw UnsupportedOperationException("This session doesn't support queue management operations")
                }
                this.mBinder.addQueueItem(mediaDescriptionCompat)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in addQueueItem.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
            try {
                if ((this.mBinder.getFlags() & 4) == 0) {
                    throw UnsupportedOperationException("This session doesn't support queue management operations")
                }
                this.mBinder.addQueueItemAt(mediaDescriptionCompat, i)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in addQueueItemAt.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun adjustVolume(Int i, Int i2) {
            try {
                this.mBinder.adjustVolume(i, i2, null)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in adjustVolume.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun dispatchMediaButtonEvent(KeyEvent keyEvent) {
            if (keyEvent == null) {
                throw IllegalArgumentException("event may not be null.")
            }
            try {
                this.mBinder.sendMediaButton(keyEvent)
                return false
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in dispatchMediaButtonEvent.", e)
                return false
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getExtras() {
            try {
                return this.mBinder.getExtras()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getExtras.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getFlags() {
            try {
                return this.mBinder.getFlags()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getFlags.", e)
                return 0L
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getMediaController() {
            return null
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getMetadata() {
            try {
                return this.mBinder.getMetadata()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getMetadata.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getPackageName() {
            try {
                return this.mBinder.getPackageName()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getPackageName.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getPlaybackInfo() {
            try {
                ParcelableVolumeInfo volumeAttributes = this.mBinder.getVolumeAttributes()
                return PlaybackInfo(volumeAttributes.volumeType, volumeAttributes.audioStream, volumeAttributes.controlType, volumeAttributes.maxVolume, volumeAttributes.currentVolume)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackInfo.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getPlaybackState() {
            try {
                return this.mBinder.getPlaybackState()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackState.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getQueue() {
            try {
                return this.mBinder.getQueue()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getQueue.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getQueueTitle() {
            try {
                return this.mBinder.getQueueTitle()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getQueueTitle.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getRatingType() {
            try {
                return this.mBinder.getRatingType()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getRatingType.", e)
                return 0
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getRepeatMode() {
            try {
                return this.mBinder.getRepeatMode()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getRepeatMode.", e)
                return -1
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getSessionActivity() {
            try {
                return this.mBinder.getLaunchPendingIntent()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getSessionActivity.", e)
                return null
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getShuffleMode() {
            try {
                return this.mBinder.getShuffleMode()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getShuffleMode.", e)
                return -1
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun getTransportControls() {
            if (this.mTransportControls == null) {
                this.mTransportControls = TransportControlsBase(this.mBinder)
            }
            return this.mTransportControls
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun isCaptioningEnabled() {
            try {
                return this.mBinder.isCaptioningEnabled()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in isCaptioningEnabled.", e)
                return false
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun isSessionReady() {
            return true
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun registerCallback(Callback callback, Handler handler) throws RemoteException {
            if (callback == null) {
                throw IllegalArgumentException("callback may not be null.")
            }
            try {
                this.mBinder.asBinder().linkToDeath(callback, 0)
                this.mBinder.registerCallbackListener((IMediaControllerCallback) callback.mCallbackObj)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback.", e)
                callback.onSessionDestroyed()
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.mBinder.getFlags() & 4) == 0) {
                    throw UnsupportedOperationException("This session doesn't support queue management operations")
                }
                this.mBinder.removeQueueItem(mediaDescriptionCompat)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in removeQueueItem.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            try {
                this.mBinder.sendCommand(str, bundle, new MediaSessionCompat.ResultReceiverWrapper(resultReceiver))
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in sendCommand.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun setVolumeTo(Int i, Int i2) {
            try {
                this.mBinder.setVolumeTo(i, i2, null)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setVolumeTo.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        fun unregisterCallback(Callback callback) {
            if (callback == null) {
                throw IllegalArgumentException("callback may not be null.")
            }
            try {
                this.mBinder.unregisterCallbackListener((IMediaControllerCallback) callback.mCallbackObj)
                this.mBinder.asBinder().unlinkToDeath(callback, 0)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in unregisterCallback.", e)
            }
        }
    }

    class PlaybackInfo {
        public static val PLAYBACK_TYPE_LOCAL = 1
        public static val PLAYBACK_TYPE_REMOTE = 2
        private final Int mAudioStream
        private final Int mCurrentVolume
        private final Int mMaxVolume
        private final Int mPlaybackType
        private final Int mVolumeControl

        PlaybackInfo(Int i, Int i2, Int i3, Int i4, Int i5) {
            this.mPlaybackType = i
            this.mAudioStream = i2
            this.mVolumeControl = i3
            this.mMaxVolume = i4
            this.mCurrentVolume = i5
        }

        public final Int getAudioStream() {
            return this.mAudioStream
        }

        public final Int getCurrentVolume() {
            return this.mCurrentVolume
        }

        public final Int getMaxVolume() {
            return this.mMaxVolume
        }

        public final Int getPlaybackType() {
            return this.mPlaybackType
        }

        public final Int getVolumeControl() {
            return this.mVolumeControl
        }
    }

    abstract class TransportControls {
        public static val EXTRA_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE"

        TransportControls() {
        }

        public abstract Unit fastForward()

        public abstract Unit pause()

        public abstract Unit play()

        public abstract Unit playFromMediaId(String str, Bundle bundle)

        public abstract Unit playFromSearch(String str, Bundle bundle)

        public abstract Unit playFromUri(Uri uri, Bundle bundle)

        public abstract Unit prepare()

        public abstract Unit prepareFromMediaId(String str, Bundle bundle)

        public abstract Unit prepareFromSearch(String str, Bundle bundle)

        public abstract Unit prepareFromUri(Uri uri, Bundle bundle)

        public abstract Unit rewind()

        public abstract Unit seekTo(Long j)

        public abstract Unit sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle)

        public abstract Unit sendCustomAction(String str, Bundle bundle)

        public abstract Unit setCaptioningEnabled(Boolean z)

        public abstract Unit setRating(RatingCompat ratingCompat)

        public abstract Unit setRating(RatingCompat ratingCompat, Bundle bundle)

        public abstract Unit setRepeatMode(Int i)

        public abstract Unit setShuffleMode(Int i)

        public abstract Unit skipToNext()

        public abstract Unit skipToPrevious()

        public abstract Unit skipToQueueItem(Long j)

        public abstract Unit stop()
    }

    class TransportControlsApi21 extends TransportControls {
        protected final Object mControlsObj

        constructor(Object obj) {
            this.mControlsObj = obj
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun fastForward() {
            MediaControllerCompatApi21.TransportControls.fastForward(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun pause() {
            MediaControllerCompatApi21.TransportControls.pause(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun play() {
            MediaControllerCompatApi21.TransportControls.play(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromMediaId(String str, Bundle bundle) {
            MediaControllerCompatApi21.TransportControls.playFromMediaId(this.mControlsObj, str, bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromSearch(String str, Bundle bundle) {
            MediaControllerCompatApi21.TransportControls.playFromSearch(this.mControlsObj, str, bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromUri(Uri uri, Bundle bundle) {
            if (uri == null || Uri.EMPTY.equals(uri)) {
                throw IllegalArgumentException("You must specify a non-empty Uri for playFromUri.")
            }
            Bundle bundle2 = Bundle()
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri)
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle)
            sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepare() {
            sendCustomAction("android.support.v4.media.session.action.PREPARE", (Bundle) null)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromMediaId(String str, Bundle bundle) {
            Bundle bundle2 = Bundle()
            bundle2.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", str)
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle)
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromSearch(String str, Bundle bundle) {
            Bundle bundle2 = Bundle()
            bundle2.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", str)
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle)
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromUri(Uri uri, Bundle bundle) {
            Bundle bundle2 = Bundle()
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri)
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle)
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun rewind() {
            MediaControllerCompatApi21.TransportControls.rewind(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun seekTo(Long j) {
            MediaControllerCompatApi21.TransportControls.seekTo(this.mControlsObj, j)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            MediaControllerCompat.validateCustomAction(customAction.getAction(), bundle)
            MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, customAction.getAction(), bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.validateCustomAction(str, bundle)
            MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, str, bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setCaptioningEnabled(Boolean z) {
            Bundle bundle = Bundle()
            bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED", z)
            sendCustomAction("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED", bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setRating(RatingCompat ratingCompat) {
            MediaControllerCompatApi21.TransportControls.setRating(this.mControlsObj, ratingCompat != null ? ratingCompat.getRating() : null)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setRating(RatingCompat ratingCompat, Bundle bundle) {
            Bundle bundle2 = Bundle()
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_RATING", ratingCompat)
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle)
            sendCustomAction("android.support.v4.media.session.action.SET_RATING", bundle2)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setRepeatMode(Int i) {
            Bundle bundle = Bundle()
            bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", i)
            sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setShuffleMode(Int i) {
            Bundle bundle = Bundle()
            bundle.putInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE", i)
            sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE", bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun skipToNext() {
            MediaControllerCompatApi21.TransportControls.skipToNext(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun skipToPrevious() {
            MediaControllerCompatApi21.TransportControls.skipToPrevious(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun skipToQueueItem(Long j) {
            MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.mControlsObj, j)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun stop() {
            MediaControllerCompatApi21.TransportControls.stop(this.mControlsObj)
        }
    }

    @RequiresApi(23)
    class TransportControlsApi23 extends TransportControlsApi21 {
        constructor(Object obj) {
            super(obj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromUri(Uri uri, Bundle bundle) {
            MediaControllerCompatApi23.TransportControls.playFromUri(this.mControlsObj, uri, bundle)
        }
    }

    @RequiresApi(24)
    class TransportControlsApi24 extends TransportControlsApi23 {
        constructor(Object obj) {
            super(obj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepare() {
            MediaControllerCompatApi24.TransportControls.prepare(this.mControlsObj)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromMediaId(String str, Bundle bundle) {
            MediaControllerCompatApi24.TransportControls.prepareFromMediaId(this.mControlsObj, str, bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromSearch(String str, Bundle bundle) {
            MediaControllerCompatApi24.TransportControls.prepareFromSearch(this.mControlsObj, str, bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromUri(Uri uri, Bundle bundle) {
            MediaControllerCompatApi24.TransportControls.prepareFromUri(this.mControlsObj, uri, bundle)
        }
    }

    class TransportControlsBase extends TransportControls {
        private IMediaSession mBinder

        constructor(IMediaSession iMediaSession) {
            this.mBinder = iMediaSession
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun fastForward() {
            try {
                this.mBinder.fastForward()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in fastForward.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun pause() {
            try {
                this.mBinder.pause()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in pause.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun play() {
            try {
                this.mBinder.play()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in play.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromMediaId(String str, Bundle bundle) {
            try {
                this.mBinder.playFromMediaId(str, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in playFromMediaId.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromSearch(String str, Bundle bundle) {
            try {
                this.mBinder.playFromSearch(str, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in playFromSearch.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun playFromUri(Uri uri, Bundle bundle) {
            try {
                this.mBinder.playFromUri(uri, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in playFromUri.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepare() {
            try {
                this.mBinder.prepare()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in prepare.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromMediaId(String str, Bundle bundle) {
            try {
                this.mBinder.prepareFromMediaId(str, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in prepareFromMediaId.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromSearch(String str, Bundle bundle) {
            try {
                this.mBinder.prepareFromSearch(str, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in prepareFromSearch.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun prepareFromUri(Uri uri, Bundle bundle) {
            try {
                this.mBinder.prepareFromUri(uri, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in prepareFromUri.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun rewind() {
            try {
                this.mBinder.rewind()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in rewind.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun seekTo(Long j) {
            try {
                this.mBinder.seekTo(j)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in seekTo.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            sendCustomAction(customAction.getAction(), bundle)
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.validateCustomAction(str, bundle)
            try {
                this.mBinder.sendCustomAction(str, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in sendCustomAction.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setCaptioningEnabled(Boolean z) {
            try {
                this.mBinder.setCaptioningEnabled(z)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setCaptioningEnabled.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setRating(RatingCompat ratingCompat) {
            try {
                this.mBinder.rate(ratingCompat)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setRating.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setRating(RatingCompat ratingCompat, Bundle bundle) {
            try {
                this.mBinder.rateWithExtras(ratingCompat, bundle)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setRating.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setRepeatMode(Int i) {
            try {
                this.mBinder.setRepeatMode(i)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setRepeatMode.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun setShuffleMode(Int i) {
            try {
                this.mBinder.setShuffleMode(i)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setShuffleMode.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun skipToNext() {
            try {
                this.mBinder.next()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in skipToNext.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun skipToPrevious() {
            try {
                this.mBinder.previous()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in skipToPrevious.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun skipToQueueItem(Long j) {
            try {
                this.mBinder.skipToQueueItem(j)
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in skipToQueueItem.", e)
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        fun stop() {
            try {
                this.mBinder.stop()
            } catch (RemoteException e) {
                Log.e(MediaControllerCompat.TAG, "Dead object in stop.", e)
            }
        }
    }

    constructor(Context context, @NonNull MediaSessionCompat.Token token) {
        if (token == null) {
            throw IllegalArgumentException("sessionToken must not be null")
        }
        this.mToken = token
        if (Build.VERSION.SDK_INT >= 24) {
            this.mImpl = MediaControllerImplApi24(context, token)
            return
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = MediaControllerImplApi23(context, token)
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = MediaControllerImplApi21(context, token)
        } else {
            this.mImpl = MediaControllerImplBase(this.mToken)
        }
    }

    constructor(Context context, @NonNull MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat == null) {
            throw IllegalArgumentException("session must not be null")
        }
        this.mToken = mediaSessionCompat.getSessionToken()
        if (Build.VERSION.SDK_INT >= 24) {
            this.mImpl = MediaControllerImplApi24(context, mediaSessionCompat)
            return
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = MediaControllerImplApi23(context, mediaSessionCompat)
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = MediaControllerImplApi21(context, mediaSessionCompat)
        } else {
            this.mImpl = MediaControllerImplBase(this.mToken)
        }
    }

    fun getMediaController(@NonNull Activity activity) {
        if (activity is SupportActivity) {
            MediaControllerExtraData mediaControllerExtraData = (MediaControllerExtraData) ((SupportActivity) activity).getExtraData(MediaControllerExtraData.class)
            if (mediaControllerExtraData != null) {
                return mediaControllerExtraData.getMediaController()
            }
            return null
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Object mediaController = MediaControllerCompatApi21.getMediaController(activity)
            if (mediaController == null) {
                return null
            }
            try {
                return MediaControllerCompat(activity, MediaSessionCompat.Token.fromToken(MediaControllerCompatApi21.getSessionToken(mediaController)))
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in getMediaController.", e)
            }
        }
        return null
    }

    fun setMediaController(@NonNull Activity activity, MediaControllerCompat mediaControllerCompat) {
        if (activity is SupportActivity) {
            ((SupportActivity) activity).putExtraData(MediaControllerExtraData(mediaControllerCompat))
        }
        if (Build.VERSION.SDK_INT >= 21) {
            MediaControllerCompatApi21.setMediaController(activity, mediaControllerCompat != null ? MediaControllerCompatApi21.fromToken(activity, mediaControllerCompat.getSessionToken().getToken()) : null)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun validateCustomAction(String str, Bundle bundle) {
        if (str == null) {
            return
        }
        switch (str) {
            case "android.support.v4.media.session.action.FOLLOW":
            case "android.support.v4.media.session.action.UNFOLLOW":
                if (bundle == null || !bundle.containsKey(MediaSessionCompat.ARGUMENT_MEDIA_ATTRIBUTE)) {
                    throw IllegalArgumentException("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action " + str + Deobfuscator.CLASS_NAME_SEPARATOR)
                }
                return
            default:
                return
        }
    }

    public final Unit addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.mImpl.addQueueItem(mediaDescriptionCompat)
    }

    public final Unit addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
        this.mImpl.addQueueItem(mediaDescriptionCompat, i)
    }

    public final Unit adjustVolume(Int i, Int i2) {
        this.mImpl.adjustVolume(i, i2)
    }

    public final Boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent == null) {
            throw IllegalArgumentException("KeyEvent may not be null")
        }
        return this.mImpl.dispatchMediaButtonEvent(keyEvent)
    }

    public final Bundle getExtras() {
        return this.mImpl.getExtras()
    }

    public final Long getFlags() {
        return this.mImpl.getFlags()
    }

    public final Object getMediaController() {
        return this.mImpl.getMediaController()
    }

    public final MediaMetadataCompat getMetadata() {
        return this.mImpl.getMetadata()
    }

    public final String getPackageName() {
        return this.mImpl.getPackageName()
    }

    public final PlaybackInfo getPlaybackInfo() {
        return this.mImpl.getPlaybackInfo()
    }

    public final PlaybackStateCompat getPlaybackState() {
        return this.mImpl.getPlaybackState()
    }

    public final List getQueue() {
        return this.mImpl.getQueue()
    }

    public final CharSequence getQueueTitle() {
        return this.mImpl.getQueueTitle()
    }

    public final Int getRatingType() {
        return this.mImpl.getRatingType()
    }

    public final Int getRepeatMode() {
        return this.mImpl.getRepeatMode()
    }

    public final PendingIntent getSessionActivity() {
        return this.mImpl.getSessionActivity()
    }

    public final MediaSessionCompat.Token getSessionToken() {
        return this.mToken
    }

    public final Int getShuffleMode() {
        return this.mImpl.getShuffleMode()
    }

    public final TransportControls getTransportControls() {
        return this.mImpl.getTransportControls()
    }

    public final Boolean isCaptioningEnabled() {
        return this.mImpl.isCaptioningEnabled()
    }

    public final Boolean isSessionReady() {
        return this.mImpl.isSessionReady()
    }

    public final Unit registerCallback(@NonNull Callback callback) {
        registerCallback(callback, null)
    }

    public final Unit registerCallback(@NonNull Callback callback, Handler handler) {
        if (callback == null) {
            throw IllegalArgumentException("callback must not be null")
        }
        if (handler == null) {
            handler = Handler()
        }
        callback.setHandler(handler)
        this.mImpl.registerCallback(callback, handler)
        this.mRegisteredCallbacks.add(callback)
    }

    public final Unit removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.mImpl.removeQueueItem(mediaDescriptionCompat)
    }

    @Deprecated
    public final Unit removeQueueItemAt(Int i) {
        MediaSessionCompat.QueueItem queueItem
        List queue = getQueue()
        if (queue == null || i < 0 || i >= queue.size() || (queueItem = (MediaSessionCompat.QueueItem) queue.get(i)) == null) {
            return
        }
        removeQueueItem(queueItem.getDescription())
    }

    public final Unit sendCommand(@NonNull String str, Bundle bundle, ResultReceiver resultReceiver) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("command must neither be null nor empty")
        }
        this.mImpl.sendCommand(str, bundle, resultReceiver)
    }

    public final Unit setVolumeTo(Int i, Int i2) {
        this.mImpl.setVolumeTo(i, i2)
    }

    public final Unit unregisterCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw IllegalArgumentException("callback must not be null")
        }
        try {
            this.mRegisteredCallbacks.remove(callback)
            this.mImpl.unregisterCallback(callback)
        } finally {
            callback.setHandler(null)
        }
    }
}
