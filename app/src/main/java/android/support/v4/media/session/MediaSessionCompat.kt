package android.support.v4.media.session

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.Rating
import android.media.RemoteControlClient
import android.net.Uri
import android.os.BadParcelableException
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Parcel
import android.os.Parcelable
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.os.ResultReceiver
import android.os.SystemClock
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.app.BundleCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.VolumeProviderCompat
import android.support.v4.media.session.IMediaSession
import android.support.v4.media.session.MediaSessionCompatApi21
import android.support.v4.media.session.MediaSessionCompatApi23
import android.support.v4.media.session.MediaSessionCompatApi24
import android.support.v4.media.session.PlaybackStateCompat
import androidx.appcompat.R
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.ViewConfiguration
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.ref.WeakReference
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class MediaSessionCompat {
    static val ACTION_ARGUMENT_CAPTIONING_ENABLED = "android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED"
    static val ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS"
    static val ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID"
    static val ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY"
    static val ACTION_ARGUMENT_RATING = "android.support.v4.media.session.action.ARGUMENT_RATING"
    static val ACTION_ARGUMENT_REPEAT_MODE = "android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE"
    static val ACTION_ARGUMENT_SHUFFLE_MODE = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE"
    static val ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI"
    public static val ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE"
    public static val ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW"
    static val ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI"
    static val ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE"
    static val ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID"
    static val ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH"
    static val ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI"
    static val ACTION_SET_CAPTIONING_ENABLED = "android.support.v4.media.session.action.SET_CAPTIONING_ENABLED"
    static val ACTION_SET_RATING = "android.support.v4.media.session.action.SET_RATING"
    static val ACTION_SET_REPEAT_MODE = "android.support.v4.media.session.action.SET_REPEAT_MODE"
    static val ACTION_SET_SHUFFLE_MODE = "android.support.v4.media.session.action.SET_SHUFFLE_MODE"
    public static val ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD"
    public static val ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW"
    public static val ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE"
    public static val ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE"
    static val EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER"
    public static val FLAG_HANDLES_MEDIA_BUTTONS = 1
    public static val FLAG_HANDLES_QUEUE_COMMANDS = 4
    public static val FLAG_HANDLES_TRANSPORT_CONTROLS = 2
    private static val MAX_BITMAP_SIZE_IN_DP = 320
    public static val MEDIA_ATTRIBUTE_ALBUM = 1
    public static val MEDIA_ATTRIBUTE_ARTIST = 0
    public static val MEDIA_ATTRIBUTE_PLAYLIST = 2
    static val TAG = "MediaSessionCompat"
    static Int sMaxBitmapSize
    private final ArrayList mActiveListeners
    private final MediaControllerCompat mController
    private final MediaSessionImpl mImpl

    abstract class Callback {
        private CallbackHandler mCallbackHandler = null
        final Object mCallbackObj
        private Boolean mMediaPlayPauseKeyPending
        private WeakReference mSessionImpl

        class CallbackHandler extends Handler {
            private static val MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1

            CallbackHandler(Looper looper) {
                super(looper)
            }

            @Override // android.os.Handler
            fun handleMessage(Message message) {
                if (message.what == 1) {
                    Callback.this.handleMediaPlayPauseKeySingleTapIfPending()
                }
            }
        }

        @RequiresApi(21)
        class StubApi21 implements MediaSessionCompatApi21.Callback {
            StubApi21() {
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                try {
                    if (str.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER")) {
                        MediaSessionImplApi21 mediaSessionImplApi21 = (MediaSessionImplApi21) Callback.this.mSessionImpl.get()
                        if (mediaSessionImplApi21 != null) {
                            Bundle bundle2 = Bundle()
                            IMediaSession extraBinder = mediaSessionImplApi21.getSessionToken().getExtraBinder()
                            BundleCompat.putBinder(bundle2, MediaSessionCompat.EXTRA_BINDER, extraBinder != null ? extraBinder.asBinder() : null)
                            resultReceiver.send(0, bundle2)
                            return
                        }
                        return
                    }
                    if (str.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM")) {
                        bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader())
                        Callback.this.onAddQueueItem((MediaDescriptionCompat) bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"))
                        return
                    }
                    if (str.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT")) {
                        bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader())
                        Callback.this.onAddQueueItem((MediaDescriptionCompat) bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"))
                        return
                    }
                    if (str.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
                        bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader())
                        Callback.this.onRemoveQueueItem((MediaDescriptionCompat) bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"))
                        return
                    }
                    if (!str.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                        Callback.this.onCommand(str, bundle, resultReceiver)
                        return
                    }
                    MediaSessionImplApi21 mediaSessionImplApi212 = (MediaSessionImplApi21) Callback.this.mSessionImpl.get()
                    if (mediaSessionImplApi212 == null || mediaSessionImplApi212.mQueue == null) {
                        return
                    }
                    Int i = bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1)
                    QueueItem queueItem = (i < 0 || i >= mediaSessionImplApi212.mQueue.size()) ? null : (QueueItem) mediaSessionImplApi212.mQueue.get(i)
                    if (queueItem != null) {
                        Callback.this.onRemoveQueueItem(queueItem.getDescription())
                    }
                } catch (BadParcelableException e) {
                    Log.e(MediaSessionCompat.TAG, "Could not unparcel the extra data.")
                }
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onCustomAction(String str, Bundle bundle) {
                if (str.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                    Callback.this.onPlayFromUri((Uri) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), (Bundle) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS))
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_PREPARE)) {
                    Callback.this.onPrepare()
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                    Callback.this.onPrepareFromMediaId(bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID), bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS))
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                    Callback.this.onPrepareFromSearch(bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY), bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS))
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                    Callback.this.onPrepareFromUri((Uri) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS))
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_SET_CAPTIONING_ENABLED)) {
                    Callback.this.onSetCaptioningEnabled(bundle.getBoolean(MediaSessionCompat.ACTION_ARGUMENT_CAPTIONING_ENABLED))
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_SET_REPEAT_MODE)) {
                    Callback.this.onSetRepeatMode(bundle.getInt(MediaSessionCompat.ACTION_ARGUMENT_REPEAT_MODE))
                    return
                }
                if (str.equals(MediaSessionCompat.ACTION_SET_SHUFFLE_MODE)) {
                    Callback.this.onSetShuffleMode(bundle.getInt(MediaSessionCompat.ACTION_ARGUMENT_SHUFFLE_MODE))
                } else {
                    if (!str.equals(MediaSessionCompat.ACTION_SET_RATING)) {
                        Callback.this.onCustomAction(str, bundle)
                        return
                    }
                    bundle.setClassLoader(RatingCompat.class.getClassLoader())
                    Callback.this.onSetRating((RatingCompat) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_RATING), bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS))
                }
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onFastForward() {
                Callback.this.onFastForward()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onMediaButtonEvent(Intent intent) {
                return Callback.this.onMediaButtonEvent(intent)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onPause() {
                Callback.this.onPause()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onPlay() {
                Callback.this.onPlay()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onPlayFromMediaId(String str, Bundle bundle) {
                Callback.this.onPlayFromMediaId(str, bundle)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onPlayFromSearch(String str, Bundle bundle) {
                Callback.this.onPlayFromSearch(str, bundle)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onRewind() {
                Callback.this.onRewind()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onSeekTo(Long j) {
                Callback.this.onSeekTo(j)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onSetRating(Object obj) {
                Callback.this.onSetRating(RatingCompat.fromRating(obj))
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onSetRating(Object obj, Bundle bundle) {
                Callback.this.onSetRating(RatingCompat.fromRating(obj), bundle)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onSkipToNext() {
                Callback.this.onSkipToNext()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onSkipToPrevious() {
                Callback.this.onSkipToPrevious()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onSkipToQueueItem(Long j) {
                Callback.this.onSkipToQueueItem(j)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi21.Callback
            fun onStop() {
                Callback.this.onStop()
            }
        }

        @RequiresApi(23)
        class StubApi23 extends StubApi21 implements MediaSessionCompatApi23.Callback {
            StubApi23() {
                super()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi23.Callback
            fun onPlayFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPlayFromUri(uri, bundle)
            }
        }

        @RequiresApi(24)
        class StubApi24 extends StubApi23 implements MediaSessionCompatApi24.Callback {
            StubApi24() {
                super()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24.Callback
            fun onPrepare() {
                Callback.this.onPrepare()
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24.Callback
            fun onPrepareFromMediaId(String str, Bundle bundle) {
                Callback.this.onPrepareFromMediaId(str, bundle)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24.Callback
            fun onPrepareFromSearch(String str, Bundle bundle) {
                Callback.this.onPrepareFromSearch(str, bundle)
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24.Callback
            fun onPrepareFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPrepareFromUri(uri, bundle)
            }
        }

        constructor() {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mCallbackObj = MediaSessionCompatApi24.createCallback(StubApi24())
                return
            }
            if (Build.VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.createCallback(StubApi23())
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaSessionCompatApi21.createCallback(StubApi21())
            } else {
                this.mCallbackObj = null
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun handleMediaPlayPauseKeySingleTapIfPending() {
            if (this.mMediaPlayPauseKeyPending) {
                this.mMediaPlayPauseKeyPending = false
                this.mCallbackHandler.removeMessages(1)
                MediaSessionImpl mediaSessionImpl = (MediaSessionImpl) this.mSessionImpl.get()
                if (mediaSessionImpl != null) {
                    PlaybackStateCompat playbackState = mediaSessionImpl.getPlaybackState()
                    Long actions = playbackState == null ? 0L : playbackState.getActions()
                    Boolean z = playbackState != null && playbackState.getState() == 3
                    Boolean z2 = (516 & actions) != 0
                    Boolean z3 = (actions & 514) != 0
                    if (z && z3) {
                        onPause()
                    } else {
                        if (z || !z2) {
                            return
                        }
                        onPlay()
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun setSessionImpl(MediaSessionImpl mediaSessionImpl, Handler handler) {
            this.mSessionImpl = WeakReference(mediaSessionImpl)
            if (this.mCallbackHandler != null) {
                this.mCallbackHandler.removeCallbacksAndMessages(null)
            }
            this.mCallbackHandler = CallbackHandler(handler.getLooper())
        }

        fun onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        fun onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
        }

        fun onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        fun onCustomAction(String str, Bundle bundle) {
        }

        fun onFastForward() {
        }

        fun onMediaButtonEvent(Intent intent) {
            MediaSessionImpl mediaSessionImpl = (MediaSessionImpl) this.mSessionImpl.get()
            if (mediaSessionImpl == null || this.mCallbackHandler == null) {
                return false
            }
            KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT")
            if (keyEvent == null || keyEvent.getAction() != 0) {
                return false
            }
            switch (keyEvent.getKeyCode()) {
                case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
                    if (keyEvent.getRepeatCount() > 0) {
                        handleMediaPlayPauseKeySingleTapIfPending()
                    } else if (this.mMediaPlayPauseKeyPending) {
                        this.mCallbackHandler.removeMessages(1)
                        this.mMediaPlayPauseKeyPending = false
                        PlaybackStateCompat playbackState = mediaSessionImpl.getPlaybackState()
                        if (((playbackState == null ? 0L : playbackState.getActions()) & 32) != 0) {
                            onSkipToNext()
                        }
                    } else {
                        this.mMediaPlayPauseKeyPending = true
                        this.mCallbackHandler.sendEmptyMessageDelayed(1, ViewConfiguration.getDoubleTapTimeout())
                    }
                    break
                default:
                    handleMediaPlayPauseKeySingleTapIfPending()
                    break
            }
            return false
        }

        fun onPause() {
        }

        fun onPlay() {
        }

        fun onPlayFromMediaId(String str, Bundle bundle) {
        }

        fun onPlayFromSearch(String str, Bundle bundle) {
        }

        fun onPlayFromUri(Uri uri, Bundle bundle) {
        }

        fun onPrepare() {
        }

        fun onPrepareFromMediaId(String str, Bundle bundle) {
        }

        fun onPrepareFromSearch(String str, Bundle bundle) {
        }

        fun onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        fun onRemoveQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        @Deprecated
        fun onRemoveQueueItemAt(Int i) {
        }

        fun onRewind() {
        }

        fun onSeekTo(Long j) {
        }

        fun onSetCaptioningEnabled(Boolean z) {
        }

        fun onSetRating(RatingCompat ratingCompat) {
        }

        fun onSetRating(RatingCompat ratingCompat, Bundle bundle) {
        }

        fun onSetRepeatMode(Int i) {
        }

        fun onSetShuffleMode(Int i) {
        }

        fun onSkipToNext() {
        }

        fun onSkipToPrevious() {
        }

        fun onSkipToQueueItem(Long j) {
        }

        fun onStop() {
        }
    }

    interface MediaSessionImpl {
        String getCallingPackage()

        Object getMediaSession()

        PlaybackStateCompat getPlaybackState()

        Object getRemoteControlClient()

        Token getSessionToken()

        Boolean isActive()

        Unit release()

        Unit sendSessionEvent(String str, Bundle bundle)

        Unit setActive(Boolean z)

        Unit setCallback(Callback callback, Handler handler)

        Unit setCaptioningEnabled(Boolean z)

        Unit setExtras(Bundle bundle)

        Unit setFlags(Int i)

        Unit setMediaButtonReceiver(PendingIntent pendingIntent)

        Unit setMetadata(MediaMetadataCompat mediaMetadataCompat)

        Unit setPlaybackState(PlaybackStateCompat playbackStateCompat)

        Unit setPlaybackToLocal(Int i)

        Unit setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat)

        Unit setQueue(List list)

        Unit setQueueTitle(CharSequence charSequence)

        Unit setRatingType(Int i)

        Unit setRepeatMode(Int i)

        Unit setSessionActivity(PendingIntent pendingIntent)

        Unit setShuffleMode(Int i)
    }

    @RequiresApi(18)
    class MediaSessionImplApi18 extends MediaSessionImplBase {
        private static Boolean sIsMbrPendingIntentSupported = true

        MediaSessionImplApi18(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            super(context, str, componentName, pendingIntent)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase
        Int getRccTransportControlFlagsFromActions(Long j) {
            Int rccTransportControlFlagsFromActions = super.getRccTransportControlFlagsFromActions(j)
            return (256 & j) != 0 ? rccTransportControlFlagsFromActions | 256 : rccTransportControlFlagsFromActions
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase
        Unit registerMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            if (sIsMbrPendingIntentSupported) {
                try {
                    this.mAudioManager.registerMediaButtonEventReceiver(pendingIntent)
                } catch (NullPointerException e) {
                    Log.w(MediaSessionCompat.TAG, "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.")
                    sIsMbrPendingIntentSupported = false
                }
            }
            if (sIsMbrPendingIntentSupported) {
                return
            }
            super.registerMediaButtonEventReceiver(pendingIntent, componentName)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase, android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setCallback(Callback callback, Handler handler) {
            super.setCallback(callback, handler)
            if (callback == null) {
                this.mRcc.setPlaybackPositionUpdateListener(null)
            } else {
                this.mRcc.setPlaybackPositionUpdateListener(new RemoteControlClient.OnPlaybackPositionUpdateListener() { // from class: android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi18.1
                    @Override // android.media.RemoteControlClient.OnPlaybackPositionUpdateListener
                    fun onPlaybackPositionUpdate(Long j) {
                        MediaSessionImplApi18.this.postToHandler(18, Long.valueOf(j))
                    }
                })
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase
        Unit setRccState(PlaybackStateCompat playbackStateCompat) {
            Long j
            Long j2 = 0
            Long position = playbackStateCompat.getPosition()
            Float playbackSpeed = playbackStateCompat.getPlaybackSpeed()
            Long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime()
            Long jElapsedRealtime = SystemClock.elapsedRealtime()
            if (playbackStateCompat.getState() != 3 || position <= 0) {
                j = position
            } else {
                if (lastPositionUpdateTime > 0) {
                    j2 = jElapsedRealtime - lastPositionUpdateTime
                    if (playbackSpeed > 0.0f && playbackSpeed != 1.0f) {
                        j2 = (Long) (j2 * playbackSpeed)
                    }
                }
                j = j2 + position
            }
            this.mRcc.setPlaybackState(getRccStateFromState(playbackStateCompat.getState()), j, playbackSpeed)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase
        Unit unregisterMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            if (sIsMbrPendingIntentSupported) {
                this.mAudioManager.unregisterMediaButtonEventReceiver(pendingIntent)
            } else {
                super.unregisterMediaButtonEventReceiver(pendingIntent, componentName)
            }
        }
    }

    @RequiresApi(19)
    class MediaSessionImplApi19 extends MediaSessionImplApi18 {
        MediaSessionImplApi19(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            super(context, str, componentName, pendingIntent)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase
        RemoteControlClient.MetadataEditor buildRccMetadata(Bundle bundle) throws IllegalArgumentException {
            RemoteControlClient.MetadataEditor metadataEditorBuildRccMetadata = super.buildRccMetadata(bundle)
            if (((this.mState == null ? 0L : this.mState.getActions()) & 128) != 0) {
                metadataEditorBuildRccMetadata.addEditableKey(268435457)
            }
            if (bundle != null) {
                if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_YEAR)) {
                    metadataEditorBuildRccMetadata.putLong(8, bundle.getLong(MediaMetadataCompat.METADATA_KEY_YEAR))
                }
                if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_RATING)) {
                    metadataEditorBuildRccMetadata.putObject(R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle, (Object) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_RATING))
                }
                if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_USER_RATING)) {
                    metadataEditorBuildRccMetadata.putObject(268435457, (Object) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_USER_RATING))
                }
            }
            return metadataEditorBuildRccMetadata
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi18, android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase
        Int getRccTransportControlFlagsFromActions(Long j) {
            Int rccTransportControlFlagsFromActions = super.getRccTransportControlFlagsFromActions(j)
            return (128 & j) != 0 ? rccTransportControlFlagsFromActions | 512 : rccTransportControlFlagsFromActions
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi18, android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase, android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setCallback(Callback callback, Handler handler) {
            super.setCallback(callback, handler)
            if (callback == null) {
                this.mRcc.setMetadataUpdateListener(null)
            } else {
                this.mRcc.setMetadataUpdateListener(new RemoteControlClient.OnMetadataUpdateListener() { // from class: android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi19.1
                    @Override // android.media.RemoteControlClient.OnMetadataUpdateListener
                    fun onMetadataUpdate(Int i, Object obj) {
                        if (i == 268435457 && (obj is Rating)) {
                            MediaSessionImplApi19.this.postToHandler(19, RatingCompat.fromRating(obj))
                        }
                    }
                })
            }
        }
    }

    @RequiresApi(21)
    class MediaSessionImplApi21 implements MediaSessionImpl {
        Boolean mCaptioningEnabled
        private Boolean mDestroyed = false
        private val mExtraControllerCallbacks = RemoteCallbackList()
        private MediaMetadataCompat mMetadata
        private PlaybackStateCompat mPlaybackState
        private List mQueue
        Int mRatingType
        Int mRepeatMode
        private final Object mSessionObj
        Int mShuffleMode
        private final Token mToken

        class ExtraSession extends IMediaSession.Stub {
            ExtraSession() {
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun adjustVolume(Int i, Int i2, String str) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun fastForward() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getExtras() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getFlags() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getLaunchPendingIntent() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getMetadata() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getPackageName() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getPlaybackState() {
                return MediaSessionCompat.getStateWithUpdatedPosition(MediaSessionImplApi21.this.mPlaybackState, MediaSessionImplApi21.this.mMetadata)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getQueue() {
                return null
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getQueueTitle() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getRatingType() {
                return MediaSessionImplApi21.this.mRatingType
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getRepeatMode() {
                return MediaSessionImplApi21.this.mRepeatMode
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getShuffleMode() {
                return MediaSessionImplApi21.this.mShuffleMode
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getTag() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getVolumeAttributes() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isCaptioningEnabled() {
                return MediaSessionImplApi21.this.mCaptioningEnabled
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isShuffleModeEnabledRemoved() {
                return false
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isTransportControlEnabled() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun next() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun pause() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun play() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromMediaId(String str, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromSearch(String str, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromUri(Uri uri, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepare() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromMediaId(String str, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromSearch(String str, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromUri(Uri uri, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun previous() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rate(RatingCompat ratingCompat) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (MediaSessionImplApi21.this.mDestroyed) {
                    return
                }
                MediaSessionImplApi21.this.mExtraControllerCallbacks.register(iMediaControllerCallback)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun removeQueueItemAt(Int i) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rewind() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun seekTo(Long j) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendCustomAction(String str, Bundle bundle) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendMediaButton(KeyEvent keyEvent) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setCaptioningEnabled(Boolean z) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setRepeatMode(Int i) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setShuffleMode(Int i) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setShuffleModeEnabledRemoved(Boolean z) {
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setVolumeTo(Int i, Int i2, String str) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun skipToQueueItem(Long j) {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun stop() {
                throw AssertionError()
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister(iMediaControllerCallback)
            }
        }

        constructor(Context context, String str) {
            this.mSessionObj = MediaSessionCompatApi21.createSession(context, str)
            this.mToken = Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj), ExtraSession())
        }

        constructor(Object obj) {
            this.mSessionObj = MediaSessionCompatApi21.verifySession(obj)
            this.mToken = Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj), ExtraSession())
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getCallingPackage() {
            if (Build.VERSION.SDK_INT < 24) {
                return null
            }
            return MediaSessionCompatApi24.getCallingPackage(this.mSessionObj)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getMediaSession() {
            return this.mSessionObj
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getPlaybackState() {
            return this.mPlaybackState
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getRemoteControlClient() {
            return null
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getSessionToken() {
            return this.mToken
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun isActive() {
            return MediaSessionCompatApi21.isActive(this.mSessionObj)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun release() {
            this.mDestroyed = true
            MediaSessionCompatApi21.release(this.mSessionObj)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun sendSessionEvent(String str, Bundle bundle) {
            if (Build.VERSION.SDK_INT < 23) {
                for (Int iBeginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onEvent(str, bundle)
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast()
            }
            MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, str, bundle)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setActive(Boolean z) {
            MediaSessionCompatApi21.setActive(this.mSessionObj, z)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setCallback(Callback callback, Handler handler) {
            MediaSessionCompatApi21.setCallback(this.mSessionObj, callback == null ? null : callback.mCallbackObj, handler)
            if (callback != null) {
                callback.setSessionImpl(this, handler)
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setCaptioningEnabled(Boolean z) {
            if (this.mCaptioningEnabled != z) {
                this.mCaptioningEnabled = z
                for (Int iBeginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onCaptioningEnabledChanged(z)
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast()
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setExtras(Bundle bundle) {
            MediaSessionCompatApi21.setExtras(this.mSessionObj, bundle)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setFlags(Int i) {
            MediaSessionCompatApi21.setFlags(this.mSessionObj, i)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setMediaButtonReceiver(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, pendingIntent)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setMetadata(MediaMetadataCompat mediaMetadataCompat) {
            this.mMetadata = mediaMetadataCompat
            MediaSessionCompatApi21.setMetadata(this.mSessionObj, mediaMetadataCompat == null ? null : mediaMetadataCompat.getMediaMetadata())
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            this.mPlaybackState = playbackStateCompat
            for (Int iBeginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onPlaybackStateChanged(playbackStateCompat)
                } catch (RemoteException e) {
                }
            }
            this.mExtraControllerCallbacks.finishBroadcast()
            MediaSessionCompatApi21.setPlaybackState(this.mSessionObj, playbackStateCompat == null ? null : playbackStateCompat.getPlaybackState())
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setPlaybackToLocal(Int i) {
            MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, i)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, volumeProviderCompat.getVolumeProvider())
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setQueue(List list) {
            this.mQueue = list
            ArrayList arrayList = null
            if (list != null) {
                ArrayList arrayList2 = ArrayList()
                Iterator it = list.iterator()
                while (it.hasNext()) {
                    arrayList2.add(((QueueItem) it.next()).getQueueItem())
                }
                arrayList = arrayList2
            }
            MediaSessionCompatApi21.setQueue(this.mSessionObj, arrayList)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setQueueTitle(CharSequence charSequence) {
            MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, charSequence)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setRatingType(Int i) {
            if (Build.VERSION.SDK_INT < 22) {
                this.mRatingType = i
            } else {
                MediaSessionCompatApi22.setRatingType(this.mSessionObj, i)
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setRepeatMode(Int i) {
            if (this.mRepeatMode != i) {
                this.mRepeatMode = i
                for (Int iBeginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onRepeatModeChanged(i)
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast()
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setSessionActivity(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, pendingIntent)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setShuffleMode(Int i) {
            if (this.mShuffleMode != i) {
                this.mShuffleMode = i
                for (Int iBeginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onShuffleModeChanged(i)
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast()
            }
        }
    }

    class MediaSessionImplBase implements MediaSessionImpl {
        static val RCC_PLAYSTATE_NONE = 0
        final AudioManager mAudioManager
        volatile Callback mCallback
        Boolean mCaptioningEnabled
        private final Context mContext
        Bundle mExtras
        Int mFlags
        private MessageHandler mHandler
        Int mLocalStream
        private final ComponentName mMediaButtonReceiverComponentName
        private final PendingIntent mMediaButtonReceiverIntent
        MediaMetadataCompat mMetadata
        final String mPackageName
        List mQueue
        CharSequence mQueueTitle
        Int mRatingType
        final RemoteControlClient mRcc
        Int mRepeatMode
        PendingIntent mSessionActivity
        Int mShuffleMode
        PlaybackStateCompat mState
        private final MediaSessionStub mStub
        final String mTag
        private final Token mToken
        VolumeProviderCompat mVolumeProvider
        Int mVolumeType
        val mLock = Object()
        val mControllerCallbacks = RemoteCallbackList()
        Boolean mDestroyed = false
        Boolean mIsActive = false
        private Boolean mIsMbrRegistered = false
        private Boolean mIsRccRegistered = false
        private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback() { // from class: android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase.1
            @Override // android.support.v4.media.VolumeProviderCompat.Callback
            fun onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                if (MediaSessionImplBase.this.mVolumeProvider != volumeProviderCompat) {
                    return
                }
                MediaSessionImplBase.this.sendVolumeInfoChanged(ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume()))
            }
        }

        final class Command {
            public final String command
            public final Bundle extras
            public final ResultReceiver stub

            constructor(String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.command = str
                this.extras = bundle
                this.stub = resultReceiver
            }
        }

        class MediaSessionStub extends IMediaSession.Stub {
            MediaSessionStub() {
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                MediaSessionImplBase.this.postToHandler(25, mediaDescriptionCompat)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, Int i) {
                MediaSessionImplBase.this.postToHandler(26, mediaDescriptionCompat, i)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun adjustVolume(Int i, Int i2, String str) {
                MediaSessionImplBase.this.adjustVolume(i, i2)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun fastForward() {
                MediaSessionImplBase.this.postToHandler(16)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getExtras() {
                Bundle bundle
                synchronized (MediaSessionImplBase.this.mLock) {
                    bundle = MediaSessionImplBase.this.mExtras
                }
                return bundle
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getFlags() {
                Long j
                synchronized (MediaSessionImplBase.this.mLock) {
                    j = MediaSessionImplBase.this.mFlags
                }
                return j
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getLaunchPendingIntent() {
                PendingIntent pendingIntent
                synchronized (MediaSessionImplBase.this.mLock) {
                    pendingIntent = MediaSessionImplBase.this.mSessionActivity
                }
                return pendingIntent
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getMetadata() {
                return MediaSessionImplBase.this.mMetadata
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getPackageName() {
                return MediaSessionImplBase.this.mPackageName
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getPlaybackState() {
                PlaybackStateCompat playbackStateCompat
                MediaMetadataCompat mediaMetadataCompat
                synchronized (MediaSessionImplBase.this.mLock) {
                    playbackStateCompat = MediaSessionImplBase.this.mState
                    mediaMetadataCompat = MediaSessionImplBase.this.mMetadata
                }
                return MediaSessionCompat.getStateWithUpdatedPosition(playbackStateCompat, mediaMetadataCompat)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getQueue() {
                List list
                synchronized (MediaSessionImplBase.this.mLock) {
                    list = MediaSessionImplBase.this.mQueue
                }
                return list
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getQueueTitle() {
                return MediaSessionImplBase.this.mQueueTitle
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getRatingType() {
                return MediaSessionImplBase.this.mRatingType
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getRepeatMode() {
                return MediaSessionImplBase.this.mRepeatMode
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getShuffleMode() {
                return MediaSessionImplBase.this.mShuffleMode
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getTag() {
                return MediaSessionImplBase.this.mTag
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun getVolumeAttributes() {
                Int i
                Int i2
                Int streamMaxVolume
                Int streamVolume
                Int volumeControl = 2
                synchronized (MediaSessionImplBase.this.mLock) {
                    i = MediaSessionImplBase.this.mVolumeType
                    i2 = MediaSessionImplBase.this.mLocalStream
                    VolumeProviderCompat volumeProviderCompat = MediaSessionImplBase.this.mVolumeProvider
                    if (i == 2) {
                        volumeControl = volumeProviderCompat.getVolumeControl()
                        streamMaxVolume = volumeProviderCompat.getMaxVolume()
                        streamVolume = volumeProviderCompat.getCurrentVolume()
                    } else {
                        streamMaxVolume = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(i2)
                        streamVolume = MediaSessionImplBase.this.mAudioManager.getStreamVolume(i2)
                    }
                }
                return ParcelableVolumeInfo(i, i2, volumeControl, streamMaxVolume, streamVolume)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isCaptioningEnabled() {
                return MediaSessionImplBase.this.mCaptioningEnabled
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isShuffleModeEnabledRemoved() {
                return false
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun isTransportControlEnabled() {
                return (MediaSessionImplBase.this.mFlags & 2) != 0
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun next() {
                MediaSessionImplBase.this.postToHandler(14)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun pause() {
                MediaSessionImplBase.this.postToHandler(12)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun play() {
                MediaSessionImplBase.this.postToHandler(7)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromMediaId(String str, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(8, str, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromSearch(String str, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(9, str, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun playFromUri(Uri uri, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(10, uri, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepare() {
                MediaSessionImplBase.this.postToHandler(3)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromMediaId(String str, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(4, str, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromSearch(String str, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(5, str, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun prepareFromUri(Uri uri, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(6, uri, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun previous() {
                MediaSessionImplBase.this.postToHandler(15)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rate(RatingCompat ratingCompat) {
                MediaSessionImplBase.this.postToHandler(19, ratingCompat)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(31, ratingCompat, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (!MediaSessionImplBase.this.mDestroyed) {
                    MediaSessionImplBase.this.mControllerCallbacks.register(iMediaControllerCallback)
                } else {
                    try {
                        iMediaControllerCallback.onSessionDestroyed()
                    } catch (Exception e) {
                    }
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                MediaSessionImplBase.this.postToHandler(27, mediaDescriptionCompat)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun removeQueueItemAt(Int i) {
                MediaSessionImplBase.this.postToHandler(28, i)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun rewind() {
                MediaSessionImplBase.this.postToHandler(17)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun seekTo(Long j) {
                MediaSessionImplBase.this.postToHandler(18, Long.valueOf(j))
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                MediaSessionImplBase.this.postToHandler(1, Command(str, bundle, resultReceiverWrapper.mResultReceiver))
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendCustomAction(String str, Bundle bundle) {
                MediaSessionImplBase.this.postToHandler(20, str, bundle)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun sendMediaButton(KeyEvent keyEvent) {
                Boolean z = (MediaSessionImplBase.this.mFlags & 1) != 0
                if (z) {
                    MediaSessionImplBase.this.postToHandler(21, keyEvent)
                }
                return z
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setCaptioningEnabled(Boolean z) {
                MediaSessionImplBase.this.postToHandler(29, Boolean.valueOf(z))
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setRepeatMode(Int i) {
                MediaSessionImplBase.this.postToHandler(23, i)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setShuffleMode(Int i) {
                MediaSessionImplBase.this.postToHandler(30, i)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setShuffleModeEnabledRemoved(Boolean z) {
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun setVolumeTo(Int i, Int i2, String str) {
                MediaSessionImplBase.this.setVolumeTo(i, i2)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun skipToQueueItem(Long j) {
                MediaSessionImplBase.this.postToHandler(11, Long.valueOf(j))
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun stop() {
                MediaSessionImplBase.this.postToHandler(13)
            }

            @Override // android.support.v4.media.session.IMediaSession
            fun unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplBase.this.mControllerCallbacks.unregister(iMediaControllerCallback)
            }
        }

        class MessageHandler extends Handler {
            private static val KEYCODE_MEDIA_PAUSE = 127
            private static val KEYCODE_MEDIA_PLAY = 126
            private static val MSG_ADD_QUEUE_ITEM = 25
            private static val MSG_ADD_QUEUE_ITEM_AT = 26
            private static val MSG_ADJUST_VOLUME = 2
            private static val MSG_COMMAND = 1
            private static val MSG_CUSTOM_ACTION = 20
            private static val MSG_FAST_FORWARD = 16
            private static val MSG_MEDIA_BUTTON = 21
            private static val MSG_NEXT = 14
            private static val MSG_PAUSE = 12
            private static val MSG_PLAY = 7
            private static val MSG_PLAY_MEDIA_ID = 8
            private static val MSG_PLAY_SEARCH = 9
            private static val MSG_PLAY_URI = 10
            private static val MSG_PREPARE = 3
            private static val MSG_PREPARE_MEDIA_ID = 4
            private static val MSG_PREPARE_SEARCH = 5
            private static val MSG_PREPARE_URI = 6
            private static val MSG_PREVIOUS = 15
            private static val MSG_RATE = 19
            private static val MSG_RATE_EXTRA = 31
            private static val MSG_REMOVE_QUEUE_ITEM = 27
            private static val MSG_REMOVE_QUEUE_ITEM_AT = 28
            private static val MSG_REWIND = 17
            private static val MSG_SEEK_TO = 18
            private static val MSG_SET_CAPTIONING_ENABLED = 29
            private static val MSG_SET_REPEAT_MODE = 23
            private static val MSG_SET_SHUFFLE_MODE = 30
            private static val MSG_SET_VOLUME = 22
            private static val MSG_SKIP_TO_ITEM = 11
            private static val MSG_STOP = 13

            constructor(Looper looper) {
                super(looper)
            }

            private fun onMediaButtonEvent(KeyEvent keyEvent, Callback callback) {
                if (keyEvent == null || keyEvent.getAction() != 0) {
                    return
                }
                Long actions = MediaSessionImplBase.this.mState == null ? 0L : MediaSessionImplBase.this.mState.getActions()
                switch (keyEvent.getKeyCode()) {
                    case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                    case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
                        Log.w(MediaSessionCompat.TAG, "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already")
                        break
                    case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
                        if ((actions & 1) != 0) {
                            callback.onStop()
                            break
                        }
                        break
                    case R.styleable.AppCompatTheme_colorAccent /* 87 */:
                        if ((actions & 32) != 0) {
                            callback.onSkipToNext()
                            break
                        }
                        break
                    case R.styleable.AppCompatTheme_colorControlNormal /* 88 */:
                        if ((actions & 16) != 0) {
                            callback.onSkipToPrevious()
                            break
                        }
                        break
                    case R.styleable.AppCompatTheme_colorControlActivated /* 89 */:
                        if ((actions & 8) != 0) {
                            callback.onRewind()
                            break
                        }
                        break
                    case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
                        if ((actions & 64) != 0) {
                            callback.onFastForward()
                            break
                        }
                        break
                    case KEYCODE_MEDIA_PLAY /* 126 */:
                        if ((actions & 4) != 0) {
                            callback.onPlay()
                            break
                        }
                        break
                    case KEYCODE_MEDIA_PAUSE /* 127 */:
                        if ((actions & 2) != 0) {
                            callback.onPause()
                            break
                        }
                        break
                }
            }

            @Override // android.os.Handler
            fun handleMessage(Message message) {
                Callback callback = MediaSessionImplBase.this.mCallback
                if (callback == null) {
                }
                switch (message.what) {
                    case 1:
                        Command command = (Command) message.obj
                        callback.onCommand(command.command, command.extras, command.stub)
                        break
                    case 2:
                        MediaSessionImplBase.this.adjustVolume(message.arg1, 0)
                        break
                    case 3:
                        callback.onPrepare()
                        break
                    case 4:
                        callback.onPrepareFromMediaId((String) message.obj, message.getData())
                        break
                    case 5:
                        callback.onPrepareFromSearch((String) message.obj, message.getData())
                        break
                    case 6:
                        callback.onPrepareFromUri((Uri) message.obj, message.getData())
                        break
                    case 7:
                        callback.onPlay()
                        break
                    case 8:
                        callback.onPlayFromMediaId((String) message.obj, message.getData())
                        break
                    case 9:
                        callback.onPlayFromSearch((String) message.obj, message.getData())
                        break
                    case 10:
                        callback.onPlayFromUri((Uri) message.obj, message.getData())
                        break
                    case 11:
                        callback.onSkipToQueueItem(((Long) message.obj).longValue())
                        break
                    case 12:
                        callback.onPause()
                        break
                    case 13:
                        callback.onStop()
                        break
                    case 14:
                        callback.onSkipToNext()
                        break
                    case 15:
                        callback.onSkipToPrevious()
                        break
                    case 16:
                        callback.onFastForward()
                        break
                    case 17:
                        callback.onRewind()
                        break
                    case 18:
                        callback.onSeekTo(((Long) message.obj).longValue())
                        break
                    case 19:
                        callback.onSetRating((RatingCompat) message.obj)
                        break
                    case 20:
                        callback.onCustomAction((String) message.obj, message.getData())
                        break
                    case 21:
                        KeyEvent keyEvent = (KeyEvent) message.obj
                        Intent intent = Intent("android.intent.action.MEDIA_BUTTON")
                        intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent)
                        if (!callback.onMediaButtonEvent(intent)) {
                            onMediaButtonEvent(keyEvent, callback)
                            break
                        }
                        break
                    case 22:
                        MediaSessionImplBase.this.setVolumeTo(message.arg1, 0)
                        break
                    case 23:
                        callback.onSetRepeatMode(message.arg1)
                        break
                    case 25:
                        callback.onAddQueueItem((MediaDescriptionCompat) message.obj)
                        break
                    case 26:
                        callback.onAddQueueItem((MediaDescriptionCompat) message.obj, message.arg1)
                        break
                    case 27:
                        callback.onRemoveQueueItem((MediaDescriptionCompat) message.obj)
                        break
                    case 28:
                        if (MediaSessionImplBase.this.mQueue != null) {
                            QueueItem queueItem = (message.arg1 < 0 || message.arg1 >= MediaSessionImplBase.this.mQueue.size()) ? null : (QueueItem) MediaSessionImplBase.this.mQueue.get(message.arg1)
                            if (queueItem != null) {
                                callback.onRemoveQueueItem(queueItem.getDescription())
                                break
                            }
                        }
                        break
                    case 29:
                        callback.onSetCaptioningEnabled(((Boolean) message.obj).booleanValue())
                        break
                    case 30:
                        callback.onSetShuffleMode(message.arg1)
                        break
                    case 31:
                        callback.onSetRating((RatingCompat) message.obj, message.getData())
                        break
                }
            }

            fun post(Int i) {
                post(i, null)
            }

            fun post(Int i, Object obj) {
                obtainMessage(i, obj).sendToTarget()
            }

            fun post(Int i, Object obj, Int i2) {
                obtainMessage(i, i2, 0, obj).sendToTarget()
            }

            fun post(Int i, Object obj, Bundle bundle) {
                Message messageObtainMessage = obtainMessage(i, obj)
                messageObtainMessage.setData(bundle)
                messageObtainMessage.sendToTarget()
            }
        }

        constructor(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            if (componentName == null) {
                throw IllegalArgumentException("MediaButtonReceiver component may not be null.")
            }
            this.mContext = context
            this.mPackageName = context.getPackageName()
            this.mAudioManager = (AudioManager) context.getSystemService("audio")
            this.mTag = str
            this.mMediaButtonReceiverComponentName = componentName
            this.mMediaButtonReceiverIntent = pendingIntent
            this.mStub = MediaSessionStub()
            this.mToken = Token(this.mStub)
            this.mRatingType = 0
            this.mVolumeType = 1
            this.mLocalStream = 3
            this.mRcc = RemoteControlClient(pendingIntent)
        }

        private fun sendCaptioningEnabled(Boolean z) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onCaptioningEnabledChanged(z)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendEvent(String str, Bundle bundle) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onEvent(str, bundle)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendExtras(Bundle bundle) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onExtrasChanged(bundle)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendMetadata(MediaMetadataCompat mediaMetadataCompat) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onMetadataChanged(mediaMetadataCompat)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendQueue(List list) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onQueueChanged(list)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendQueueTitle(CharSequence charSequence) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onQueueTitleChanged(charSequence)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendRepeatMode(Int i) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onRepeatModeChanged(i)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendSessionDestroyed() {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onSessionDestroyed()
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
            this.mControllerCallbacks.kill()
        }

        private fun sendShuffleMode(Int i) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onShuffleModeChanged(i)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        private fun sendState(PlaybackStateCompat playbackStateCompat) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onPlaybackStateChanged(playbackStateCompat)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        Unit adjustVolume(Int i, Int i2) {
            if (this.mVolumeType != 2) {
                this.mAudioManager.adjustStreamVolume(this.mLocalStream, i, i2)
            } else if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onAdjustVolume(i)
            }
        }

        RemoteControlClient.MetadataEditor buildRccMetadata(Bundle bundle) throws IllegalArgumentException {
            RemoteControlClient.MetadataEditor metadataEditorEditMetadata = this.mRcc.editMetadata(true)
            if (bundle == null) {
                return metadataEditorEditMetadata
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ART)) {
                Bitmap bitmapCopy = (Bitmap) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_ART)
                if (bitmapCopy != null) {
                    bitmapCopy = bitmapCopy.copy(bitmapCopy.getConfig(), false)
                }
                metadataEditorEditMetadata.putBitmap(100, bitmapCopy)
            } else if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)) {
                Bitmap bitmapCopy2 = (Bitmap) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)
                if (bitmapCopy2 != null) {
                    bitmapCopy2 = bitmapCopy2.copy(bitmapCopy2.getConfig(), false)
                }
                metadataEditorEditMetadata.putBitmap(100, bitmapCopy2)
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM)) {
                metadataEditorEditMetadata.putString(1, bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST)) {
                metadataEditorEditMetadata.putString(13, bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ARTIST)) {
                metadataEditorEditMetadata.putString(2, bundle.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_AUTHOR)) {
                metadataEditorEditMetadata.putString(3, bundle.getString(MediaMetadataCompat.METADATA_KEY_AUTHOR))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_COMPILATION)) {
                metadataEditorEditMetadata.putString(15, bundle.getString(MediaMetadataCompat.METADATA_KEY_COMPILATION))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_COMPOSER)) {
                metadataEditorEditMetadata.putString(4, bundle.getString(MediaMetadataCompat.METADATA_KEY_COMPOSER))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DATE)) {
                metadataEditorEditMetadata.putString(5, bundle.getString(MediaMetadataCompat.METADATA_KEY_DATE))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER)) {
                metadataEditorEditMetadata.putLong(14, bundle.getLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                metadataEditorEditMetadata.putLong(9, bundle.getLong(MediaMetadataCompat.METADATA_KEY_DURATION))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_GENRE)) {
                metadataEditorEditMetadata.putString(6, bundle.getString(MediaMetadataCompat.METADATA_KEY_GENRE))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_TITLE)) {
                metadataEditorEditMetadata.putString(7, bundle.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)) {
                metadataEditorEditMetadata.putLong(0, bundle.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER))
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_WRITER)) {
                metadataEditorEditMetadata.putString(11, bundle.getString(MediaMetadataCompat.METADATA_KEY_WRITER))
            }
            return metadataEditorEditMetadata
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getCallingPackage() {
            return null
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getMediaSession() {
            return null
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getPlaybackState() {
            PlaybackStateCompat playbackStateCompat
            synchronized (this.mLock) {
                playbackStateCompat = this.mState
            }
            return playbackStateCompat
        }

        Int getRccStateFromState(Int i) {
            switch (i) {
                case 0:
                    return 0
                case 1:
                    return 1
                case 2:
                    return 2
                case 3:
                    return 3
                case 4:
                    return 4
                case 5:
                    return 5
                case 6:
                case 8:
                    return 8
                case 7:
                    return 9
                case 9:
                    return 7
                case 10:
                case 11:
                    return 6
                default:
                    return -1
            }
        }

        Int getRccTransportControlFlagsFromActions(Long j) {
            Int i = (1 & j) != 0 ? 32 : 0
            if ((2 & j) != 0) {
                i |= 16
            }
            if ((4 & j) != 0) {
                i |= 4
            }
            if ((8 & j) != 0) {
                i |= 2
            }
            if ((16 & j) != 0) {
                i |= 1
            }
            if ((32 & j) != 0) {
                i |= 128
            }
            if ((64 & j) != 0) {
                i |= 64
            }
            return (512 & j) != 0 ? i | 8 : i
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getRemoteControlClient() {
            return null
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun getSessionToken() {
            return this.mToken
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun isActive() {
            return this.mIsActive
        }

        Unit postToHandler(Int i) {
            postToHandler(i, (Object) null)
        }

        Unit postToHandler(Int i, Int i2) {
            postToHandler(i, (Object) null, i2)
        }

        Unit postToHandler(Int i, Object obj) {
            postToHandler(i, obj, (Bundle) null)
        }

        Unit postToHandler(Int i, Object obj, Int i2) {
            synchronized (this.mLock) {
                if (this.mHandler != null) {
                    this.mHandler.post(i, obj, i2)
                }
            }
        }

        Unit postToHandler(Int i, Object obj, Bundle bundle) {
            synchronized (this.mLock) {
                if (this.mHandler != null) {
                    this.mHandler.post(i, obj, bundle)
                }
            }
        }

        Unit registerMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            this.mAudioManager.registerMediaButtonEventReceiver(componentName)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun release() {
            this.mIsActive = false
            this.mDestroyed = true
            update()
            sendSessionDestroyed()
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun sendSessionEvent(String str, Bundle bundle) {
            sendEvent(str, bundle)
        }

        Unit sendVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
            for (Int iBeginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(iBeginBroadcast)).onVolumeInfoChanged(parcelableVolumeInfo)
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast()
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setActive(Boolean z) {
            if (z == this.mIsActive) {
                return
            }
            this.mIsActive = z
            if (update()) {
                setMetadata(this.mMetadata)
                setPlaybackState(this.mState)
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setCallback(Callback callback, Handler handler) {
            this.mCallback = callback
            if (callback != null) {
                if (handler == null) {
                    handler = Handler()
                }
                synchronized (this.mLock) {
                    if (this.mHandler != null) {
                        this.mHandler.removeCallbacksAndMessages(null)
                    }
                    this.mHandler = MessageHandler(handler.getLooper())
                    this.mCallback.setSessionImpl(this, handler)
                }
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setCaptioningEnabled(Boolean z) {
            if (this.mCaptioningEnabled != z) {
                this.mCaptioningEnabled = z
                sendCaptioningEnabled(z)
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setExtras(Bundle bundle) {
            this.mExtras = bundle
            sendExtras(bundle)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setFlags(Int i) {
            synchronized (this.mLock) {
                this.mFlags = i
            }
            update()
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setMediaButtonReceiver(PendingIntent pendingIntent) {
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setMetadata(MediaMetadataCompat mediaMetadataCompat) {
            if (mediaMetadataCompat != null) {
                mediaMetadataCompat = new MediaMetadataCompat.Builder(mediaMetadataCompat, MediaSessionCompat.sMaxBitmapSize).build()
            }
            synchronized (this.mLock) {
                this.mMetadata = mediaMetadataCompat
            }
            sendMetadata(mediaMetadataCompat)
            if (this.mIsActive) {
                buildRccMetadata(mediaMetadataCompat == null ? null : mediaMetadataCompat.getBundle()).apply()
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            synchronized (this.mLock) {
                this.mState = playbackStateCompat
            }
            sendState(playbackStateCompat)
            if (this.mIsActive) {
                if (playbackStateCompat == null) {
                    this.mRcc.setPlaybackState(0)
                    this.mRcc.setTransportControlFlags(0)
                } else {
                    setRccState(playbackStateCompat)
                    this.mRcc.setTransportControlFlags(getRccTransportControlFlagsFromActions(playbackStateCompat.getActions()))
                }
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setPlaybackToLocal(Int i) {
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null)
            }
            this.mVolumeType = 1
            sendVolumeInfoChanged(ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, 2, this.mAudioManager.getStreamMaxVolume(this.mLocalStream), this.mAudioManager.getStreamVolume(this.mLocalStream)))
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat == null) {
                throw IllegalArgumentException("volumeProvider may not be null")
            }
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null)
            }
            this.mVolumeType = 2
            this.mVolumeProvider = volumeProviderCompat
            sendVolumeInfoChanged(ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()))
            volumeProviderCompat.setCallback(this.mVolumeCallback)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setQueue(List list) {
            this.mQueue = list
            sendQueue(list)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setQueueTitle(CharSequence charSequence) {
            this.mQueueTitle = charSequence
            sendQueueTitle(charSequence)
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setRatingType(Int i) {
            this.mRatingType = i
        }

        Unit setRccState(PlaybackStateCompat playbackStateCompat) {
            this.mRcc.setPlaybackState(getRccStateFromState(playbackStateCompat.getState()))
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setRepeatMode(Int i) {
            if (this.mRepeatMode != i) {
                this.mRepeatMode = i
                sendRepeatMode(i)
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setSessionActivity(PendingIntent pendingIntent) {
            synchronized (this.mLock) {
                this.mSessionActivity = pendingIntent
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        fun setShuffleMode(Int i) {
            if (this.mShuffleMode != i) {
                this.mShuffleMode = i
                sendShuffleMode(i)
            }
        }

        Unit setVolumeTo(Int i, Int i2) {
            if (this.mVolumeType != 2) {
                this.mAudioManager.setStreamVolume(this.mLocalStream, i, i2)
            } else if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onSetVolumeTo(i)
            }
        }

        Unit unregisterMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            this.mAudioManager.unregisterMediaButtonEventReceiver(componentName)
        }

        Boolean update() {
            if (this.mIsActive) {
                if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                    registerMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName)
                    this.mIsMbrRegistered = true
                } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                    unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName)
                    this.mIsMbrRegistered = false
                }
                if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                    this.mAudioManager.registerRemoteControlClient(this.mRcc)
                    this.mIsRccRegistered = true
                    return true
                }
                if (this.mIsRccRegistered && (this.mFlags & 2) == 0) {
                    this.mRcc.setPlaybackState(0)
                    this.mAudioManager.unregisterRemoteControlClient(this.mRcc)
                    this.mIsRccRegistered = false
                    return false
                }
            } else {
                if (this.mIsMbrRegistered) {
                    unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName)
                    this.mIsMbrRegistered = false
                }
                if (this.mIsRccRegistered) {
                    this.mRcc.setPlaybackState(0)
                    this.mAudioManager.unregisterRemoteControlClient(this.mRcc)
                    this.mIsRccRegistered = false
                }
            }
            return false
        }
    }

    public interface OnActiveChangeListener {
        Unit onActiveChanged()
    }

    class QueueItem implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.MediaSessionCompat.QueueItem.1
            @Override // android.os.Parcelable.Creator
            public final QueueItem createFromParcel(Parcel parcel) {
                return QueueItem(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<QueueItem> newArray(Int i) {
                return new QueueItem[i]
            }
        }
        public static val UNKNOWN_ID = -1
        private final MediaDescriptionCompat mDescription
        private final Long mId
        private Object mItem

        QueueItem(Parcel parcel) {
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel)
            this.mId = parcel.readLong()
        }

        constructor(MediaDescriptionCompat mediaDescriptionCompat, Long j) {
            this(null, mediaDescriptionCompat, j)
        }

        private constructor(Object obj, MediaDescriptionCompat mediaDescriptionCompat, Long j) {
            if (mediaDescriptionCompat == null) {
                throw IllegalArgumentException("Description cannot be null.")
            }
            if (j == -1) {
                throw IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID")
            }
            this.mDescription = mediaDescriptionCompat
            this.mId = j
            this.mItem = obj
        }

        fun fromQueueItem(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null
            }
            return QueueItem(obj, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(obj)), MediaSessionCompatApi21.QueueItem.getQueueId(obj))
        }

        fun fromQueueItemList(List list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null
            }
            ArrayList arrayList = ArrayList()
            Iterator it = list.iterator()
            while (it.hasNext()) {
                arrayList.add(fromQueueItem(it.next()))
            }
            return arrayList
        }

        @Override // android.os.Parcelable
        public final Int describeContents() {
            return 0
        }

        public final MediaDescriptionCompat getDescription() {
            return this.mDescription
        }

        public final Long getQueueId() {
            return this.mId
        }

        public final Object getQueueItem() {
            if (this.mItem != null || Build.VERSION.SDK_INT < 21) {
                return this.mItem
            }
            this.mItem = MediaSessionCompatApi21.QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId)
            return this.mItem
        }

        public final String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }"
        }

        @Override // android.os.Parcelable
        public final Unit writeToParcel(Parcel parcel, Int i) {
            this.mDescription.writeToParcel(parcel, i)
            parcel.writeLong(this.mId)
        }
    }

    final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper.1
            @Override // android.os.Parcelable.Creator
            public final ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return ResultReceiverWrapper(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<ResultReceiverWrapper> newArray(Int i) {
                return new ResultReceiverWrapper[i]
            }
        }
        private ResultReceiver mResultReceiver

        ResultReceiverWrapper(Parcel parcel) {
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel)
        }

        constructor(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver
        }

        @Override // android.os.Parcelable
        public final Int describeContents() {
            return 0
        }

        @Override // android.os.Parcelable
        public final Unit writeToParcel(Parcel parcel, Int i) {
            this.mResultReceiver.writeToParcel(parcel, i)
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface SessionFlags {
    }

    class Token implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.MediaSessionCompat.Token.1
            @Override // android.os.Parcelable.Creator
            public final Token createFromParcel(Parcel parcel) {
                return Token(Build.VERSION.SDK_INT >= 21 ? parcel.readParcelable(null) : parcel.readStrongBinder())
            }

            @Override // android.os.Parcelable.Creator
            public final Array<Token> newArray(Int i) {
                return new Token[i]
            }
        }
        private final IMediaSession mExtraBinder
        private final Object mInner

        Token(Object obj) {
            this(obj, null)
        }

        Token(Object obj, IMediaSession iMediaSession) {
            this.mInner = obj
            this.mExtraBinder = iMediaSession
        }

        fun fromToken(Object obj) {
            return fromToken(obj, null)
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun fromToken(Object obj, IMediaSession iMediaSession) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null
            }
            return Token(MediaSessionCompatApi21.verifyToken(obj), iMediaSession)
        }

        @Override // android.os.Parcelable
        public final Int describeContents() {
            return 0
        }

        public final Boolean equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (!(obj is Token)) {
                return false
            }
            Token token = (Token) obj
            if (this.mInner == null) {
                return token.mInner == null
            }
            if (token.mInner == null) {
                return false
            }
            return this.mInner.equals(token.mInner)
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final IMediaSession getExtraBinder() {
            return this.mExtraBinder
        }

        public final Object getToken() {
            return this.mInner
        }

        public final Int hashCode() {
            if (this.mInner == null) {
                return 0
            }
            return this.mInner.hashCode()
        }

        @Override // android.os.Parcelable
        public final Unit writeToParcel(Parcel parcel, Int i) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable) this.mInner, i)
            } else {
                parcel.writeStrongBinder((IBinder) this.mInner)
            }
        }
    }

    private constructor(Context context, MediaSessionImpl mediaSessionImpl) {
        this.mActiveListeners = ArrayList()
        this.mImpl = mediaSessionImpl
        if (Build.VERSION.SDK_INT >= 21 && !MediaSessionCompatApi21.hasCallback(mediaSessionImpl.getMediaSession())) {
            setCallback(Callback() { // from class: android.support.v4.media.session.MediaSessionCompat.2
            })
        }
        this.mController = MediaControllerCompat(context, this)
    }

    constructor(Context context, String str) {
        this(context, str, null, null)
    }

    constructor(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        this.mActiveListeners = ArrayList()
        if (context == null) {
            throw IllegalArgumentException("context must not be null")
        }
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("tag must not be null or empty")
        }
        if (componentName == null && (componentName = MediaButtonReceiver.getMediaButtonReceiverComponent(context)) == null) {
            Log.w(TAG, "Couldn't find a unique registered media button receiver in the given context.")
        }
        if (componentName != null && pendingIntent == null) {
            Intent intent = Intent("android.intent.action.MEDIA_BUTTON")
            intent.setComponent(componentName)
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = MediaSessionImplApi21(context, str)
            setCallback(Callback() { // from class: android.support.v4.media.session.MediaSessionCompat.1
            })
            this.mImpl.setMediaButtonReceiver(pendingIntent)
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.mImpl = MediaSessionImplApi19(context, str, componentName, pendingIntent)
        } else if (Build.VERSION.SDK_INT >= 18) {
            this.mImpl = MediaSessionImplApi18(context, str, componentName, pendingIntent)
        } else {
            this.mImpl = MediaSessionImplBase(context, str, componentName, pendingIntent)
        }
        this.mController = MediaControllerCompat(context, this)
        if (sMaxBitmapSize == 0) {
            sMaxBitmapSize = (Int) TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics())
        }
    }

    fun fromMediaSession(Context context, Object obj) {
        if (context == null || obj == null || Build.VERSION.SDK_INT < 21) {
            return null
        }
        return MediaSessionCompat(context, MediaSessionImplApi21(obj))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun getStateWithUpdatedPosition(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat) {
        Long j = -1
        if (playbackStateCompat == null || playbackStateCompat.getPosition() == -1) {
            return playbackStateCompat
        }
        if (playbackStateCompat.getState() != 3 && playbackStateCompat.getState() != 4 && playbackStateCompat.getState() != 5) {
            return playbackStateCompat
        }
        if (playbackStateCompat.getLastPositionUpdateTime() <= 0) {
            return playbackStateCompat
        }
        Long jElapsedRealtime = SystemClock.elapsedRealtime()
        Long playbackSpeed = ((Long) (playbackStateCompat.getPlaybackSpeed() * (jElapsedRealtime - r8))) + playbackStateCompat.getPosition()
        if (mediaMetadataCompat != null && mediaMetadataCompat.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
            j = mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)
        }
        if (j < 0 || playbackSpeed <= j) {
            j = playbackSpeed < 0 ? 0L : playbackSpeed
        }
        return new PlaybackStateCompat.Builder(playbackStateCompat).setState(playbackStateCompat.getState(), j, playbackStateCompat.getPlaybackSpeed(), jElapsedRealtime).build()
    }

    fun addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw IllegalArgumentException("Listener may not be null")
        }
        this.mActiveListeners.add(onActiveChangeListener)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getCallingPackage() {
        return this.mImpl.getCallingPackage()
    }

    fun getController() {
        return this.mController
    }

    fun getMediaSession() {
        return this.mImpl.getMediaSession()
    }

    fun getRemoteControlClient() {
        return this.mImpl.getRemoteControlClient()
    }

    fun getSessionToken() {
        return this.mImpl.getSessionToken()
    }

    fun isActive() {
        return this.mImpl.isActive()
    }

    fun release() {
        this.mImpl.release()
    }

    fun removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw IllegalArgumentException("Listener may not be null")
        }
        this.mActiveListeners.remove(onActiveChangeListener)
    }

    fun sendSessionEvent(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("event cannot be null or empty")
        }
        this.mImpl.sendSessionEvent(str, bundle)
    }

    fun setActive(Boolean z) {
        this.mImpl.setActive(z)
        Iterator it = this.mActiveListeners.iterator()
        while (it.hasNext()) {
            ((OnActiveChangeListener) it.next()).onActiveChanged()
        }
    }

    fun setCallback(Callback callback) {
        setCallback(callback, null)
    }

    fun setCallback(Callback callback, Handler handler) {
        MediaSessionImpl mediaSessionImpl = this.mImpl
        if (handler == null) {
            handler = Handler()
        }
        mediaSessionImpl.setCallback(callback, handler)
    }

    fun setCaptioningEnabled(Boolean z) {
        this.mImpl.setCaptioningEnabled(z)
    }

    fun setExtras(Bundle bundle) {
        this.mImpl.setExtras(bundle)
    }

    fun setFlags(Int i) {
        this.mImpl.setFlags(i)
    }

    fun setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.mImpl.setMediaButtonReceiver(pendingIntent)
    }

    fun setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.mImpl.setMetadata(mediaMetadataCompat)
    }

    fun setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.mImpl.setPlaybackState(playbackStateCompat)
    }

    fun setPlaybackToLocal(Int i) {
        this.mImpl.setPlaybackToLocal(i)
    }

    fun setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw IllegalArgumentException("volumeProvider may not be null!")
        }
        this.mImpl.setPlaybackToRemote(volumeProviderCompat)
    }

    fun setQueue(List list) {
        this.mImpl.setQueue(list)
    }

    fun setQueueTitle(CharSequence charSequence) {
        this.mImpl.setQueueTitle(charSequence)
    }

    fun setRatingType(Int i) {
        this.mImpl.setRatingType(i)
    }

    fun setRepeatMode(Int i) {
        this.mImpl.setRepeatMode(i)
    }

    fun setSessionActivity(PendingIntent pendingIntent) {
        this.mImpl.setSessionActivity(pendingIntent)
    }

    fun setShuffleMode(Int i) {
        this.mImpl.setShuffleMode(i)
    }
}
