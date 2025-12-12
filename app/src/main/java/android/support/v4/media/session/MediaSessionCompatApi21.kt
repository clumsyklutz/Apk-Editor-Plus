package android.support.v4.media.session

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaDescription
import android.media.MediaMetadata
import android.media.Rating
import android.media.VolumeProvider
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.os.ResultReceiver
import android.support.annotation.RequiresApi
import android.util.Log
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Iterator
import java.util.List

@RequiresApi(21)
class MediaSessionCompatApi21 {
    static val TAG = "MediaSessionCompatApi21"

    interface Callback {
        Unit onCommand(String str, Bundle bundle, ResultReceiver resultReceiver)

        Unit onCustomAction(String str, Bundle bundle)

        Unit onFastForward()

        Boolean onMediaButtonEvent(Intent intent)

        Unit onPause()

        Unit onPlay()

        Unit onPlayFromMediaId(String str, Bundle bundle)

        Unit onPlayFromSearch(String str, Bundle bundle)

        Unit onRewind()

        Unit onSeekTo(Long j)

        Unit onSetRating(Object obj)

        Unit onSetRating(Object obj, Bundle bundle)

        Unit onSkipToNext()

        Unit onSkipToPrevious()

        Unit onSkipToQueueItem(Long j)

        Unit onStop()
    }

    class CallbackProxy extends MediaSession.Callback {
        protected final Callback mCallback

        constructor(Callback callback) {
            this.mCallback = callback
        }

        @Override // android.media.session.MediaSession.Callback
        fun onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            this.mCallback.onCommand(str, bundle, resultReceiver)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onCustomAction(String str, Bundle bundle) {
            this.mCallback.onCustomAction(str, bundle)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onFastForward() {
            this.mCallback.onFastForward()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onMediaButtonEvent(Intent intent) {
            return this.mCallback.onMediaButtonEvent(intent) || super.onMediaButtonEvent(intent)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPause() {
            this.mCallback.onPause()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPlay() {
            this.mCallback.onPlay()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPlayFromMediaId(String str, Bundle bundle) {
            this.mCallback.onPlayFromMediaId(str, bundle)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPlayFromSearch(String str, Bundle bundle) {
            this.mCallback.onPlayFromSearch(str, bundle)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onRewind() {
            this.mCallback.onRewind()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onSeekTo(Long j) {
            this.mCallback.onSeekTo(j)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onSetRating(Rating rating) {
            this.mCallback.onSetRating(rating)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onSkipToNext() {
            this.mCallback.onSkipToNext()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onSkipToPrevious() {
            this.mCallback.onSkipToPrevious()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onSkipToQueueItem(Long j) {
            this.mCallback.onSkipToQueueItem(j)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onStop() {
            this.mCallback.onStop()
        }
    }

    class QueueItem {
        QueueItem() {
        }

        fun createItem(Object obj, Long j) {
            return new MediaSession.QueueItem((MediaDescription) obj, j)
        }

        fun getDescription(Object obj) {
            return ((MediaSession.QueueItem) obj).getDescription()
        }

        fun getQueueId(Object obj) {
            return ((MediaSession.QueueItem) obj).getQueueId()
        }
    }

    MediaSessionCompatApi21() {
    }

    fun createCallback(Callback callback) {
        return CallbackProxy(callback)
    }

    fun createSession(Context context, String str) {
        return MediaSession(context, str)
    }

    fun getSessionToken(Object obj) {
        return ((MediaSession) obj).getSessionToken()
    }

    fun hasCallback(Object obj) throws NoSuchFieldException {
        try {
            Field declaredField = obj.getClass().getDeclaredField("mCallback")
            if (declaredField != null) {
                declaredField.setAccessible(true)
                return declaredField.get(obj) != null
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.w(TAG, "Failed to get mCallback object.")
        }
        return false
    }

    fun isActive(Object obj) {
        return ((MediaSession) obj).isActive()
    }

    fun release(Object obj) {
        ((MediaSession) obj).release()
    }

    fun sendSessionEvent(Object obj, String str, Bundle bundle) {
        ((MediaSession) obj).sendSessionEvent(str, bundle)
    }

    fun setActive(Object obj, Boolean z) {
        ((MediaSession) obj).setActive(z)
    }

    fun setCallback(Object obj, Object obj2, Handler handler) {
        ((MediaSession) obj).setCallback((MediaSession.Callback) obj2, handler)
    }

    fun setExtras(Object obj, Bundle bundle) {
        ((MediaSession) obj).setExtras(bundle)
    }

    fun setFlags(Object obj, Int i) {
        ((MediaSession) obj).setFlags(i)
    }

    fun setMediaButtonReceiver(Object obj, PendingIntent pendingIntent) {
        ((MediaSession) obj).setMediaButtonReceiver(pendingIntent)
    }

    fun setMetadata(Object obj, Object obj2) {
        ((MediaSession) obj).setMetadata((MediaMetadata) obj2)
    }

    fun setPlaybackState(Object obj, Object obj2) {
        ((MediaSession) obj).setPlaybackState((PlaybackState) obj2)
    }

    fun setPlaybackToLocal(Object obj, Int i) {
        AudioAttributes.Builder builder = new AudioAttributes.Builder()
        builder.setLegacyStreamType(i)
        ((MediaSession) obj).setPlaybackToLocal(builder.build())
    }

    fun setPlaybackToRemote(Object obj, Object obj2) {
        ((MediaSession) obj).setPlaybackToRemote((VolumeProvider) obj2)
    }

    fun setQueue(Object obj, List list) {
        if (list == null) {
            ((MediaSession) obj).setQueue(null)
            return
        }
        ArrayList arrayList = ArrayList()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            arrayList.add((MediaSession.QueueItem) it.next())
        }
        ((MediaSession) obj).setQueue(arrayList)
    }

    fun setQueueTitle(Object obj, CharSequence charSequence) {
        ((MediaSession) obj).setQueueTitle(charSequence)
    }

    fun setSessionActivity(Object obj, PendingIntent pendingIntent) {
        ((MediaSession) obj).setSessionActivity(pendingIntent)
    }

    fun verifySession(Object obj) {
        if (obj is MediaSession) {
            return obj
        }
        throw IllegalArgumentException("mediaSession is not a valid MediaSession object")
    }

    fun verifyToken(Object obj) {
        if (obj is MediaSession.Token) {
            return obj
        }
        throw IllegalArgumentException("token is not a valid MediaSession.Token object")
    }
}
