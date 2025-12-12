package android.support.v4.media.session

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaMetadata
import android.media.Rating
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import java.util.ArrayList
import java.util.List

@RequiresApi(21)
class MediaControllerCompatApi21 {

    public interface Callback {
        Unit onAudioInfoChanged(Int i, Int i2, Int i3, Int i4, Int i5)

        Unit onExtrasChanged(Bundle bundle)

        Unit onMetadataChanged(Object obj)

        Unit onPlaybackStateChanged(Object obj)

        Unit onQueueChanged(List list)

        Unit onQueueTitleChanged(CharSequence charSequence)

        Unit onSessionDestroyed()

        Unit onSessionEvent(String str, Bundle bundle)
    }

    class CallbackProxy extends MediaController.Callback {
        protected final Callback mCallback

        constructor(Callback callback) {
            this.mCallback = callback
        }

        @Override // android.media.session.MediaController.Callback
        fun onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            this.mCallback.onAudioInfoChanged(playbackInfo.getPlaybackType(), PlaybackInfo.getLegacyAudioStream(playbackInfo), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume())
        }

        @Override // android.media.session.MediaController.Callback
        fun onExtrasChanged(Bundle bundle) {
            this.mCallback.onExtrasChanged(bundle)
        }

        @Override // android.media.session.MediaController.Callback
        fun onMetadataChanged(MediaMetadata mediaMetadata) {
            this.mCallback.onMetadataChanged(mediaMetadata)
        }

        @Override // android.media.session.MediaController.Callback
        fun onPlaybackStateChanged(PlaybackState playbackState) {
            this.mCallback.onPlaybackStateChanged(playbackState)
        }

        @Override // android.media.session.MediaController.Callback
        fun onQueueChanged(List list) {
            this.mCallback.onQueueChanged(list)
        }

        @Override // android.media.session.MediaController.Callback
        fun onQueueTitleChanged(CharSequence charSequence) {
            this.mCallback.onQueueTitleChanged(charSequence)
        }

        @Override // android.media.session.MediaController.Callback
        fun onSessionDestroyed() {
            this.mCallback.onSessionDestroyed()
        }

        @Override // android.media.session.MediaController.Callback
        fun onSessionEvent(String str, Bundle bundle) {
            this.mCallback.onSessionEvent(str, bundle)
        }
    }

    class PlaybackInfo {
        private static val FLAG_SCO = 4
        private static val STREAM_BLUETOOTH_SCO = 6
        private static val STREAM_SYSTEM_ENFORCED = 7

        fun getAudioAttributes(Object obj) {
            return ((MediaController.PlaybackInfo) obj).getAudioAttributes()
        }

        fun getCurrentVolume(Object obj) {
            return ((MediaController.PlaybackInfo) obj).getCurrentVolume()
        }

        fun getLegacyAudioStream(Object obj) {
            return toLegacyStreamType(getAudioAttributes(obj))
        }

        fun getMaxVolume(Object obj) {
            return ((MediaController.PlaybackInfo) obj).getMaxVolume()
        }

        fun getPlaybackType(Object obj) {
            return ((MediaController.PlaybackInfo) obj).getPlaybackType()
        }

        fun getVolumeControl(Object obj) {
            return ((MediaController.PlaybackInfo) obj).getVolumeControl()
        }

        private fun toLegacyStreamType(AudioAttributes audioAttributes) {
            if ((audioAttributes.getFlags() & 1) == 1) {
                return 7
            }
            if ((audioAttributes.getFlags() & 4) == 4) {
                return 6
            }
            switch (audioAttributes.getUsage()) {
                case 1:
                case 11:
                case 12:
                case 14:
                default:
                    return 3
                case 2:
                    return 0
                case 3:
                    return 8
                case 4:
                    return 4
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                    return 5
                case 6:
                    return 2
                case 13:
                    return 1
            }
        }
    }

    class TransportControls {
        fun fastForward(Object obj) {
            ((MediaController.TransportControls) obj).fastForward()
        }

        fun pause(Object obj) {
            ((MediaController.TransportControls) obj).pause()
        }

        fun play(Object obj) {
            ((MediaController.TransportControls) obj).play()
        }

        fun playFromMediaId(Object obj, String str, Bundle bundle) {
            ((MediaController.TransportControls) obj).playFromMediaId(str, bundle)
        }

        fun playFromSearch(Object obj, String str, Bundle bundle) {
            ((MediaController.TransportControls) obj).playFromSearch(str, bundle)
        }

        fun rewind(Object obj) {
            ((MediaController.TransportControls) obj).rewind()
        }

        fun seekTo(Object obj, Long j) {
            ((MediaController.TransportControls) obj).seekTo(j)
        }

        fun sendCustomAction(Object obj, String str, Bundle bundle) {
            ((MediaController.TransportControls) obj).sendCustomAction(str, bundle)
        }

        fun setRating(Object obj, Object obj2) {
            ((MediaController.TransportControls) obj).setRating((Rating) obj2)
        }

        fun skipToNext(Object obj) {
            ((MediaController.TransportControls) obj).skipToNext()
        }

        fun skipToPrevious(Object obj) {
            ((MediaController.TransportControls) obj).skipToPrevious()
        }

        fun skipToQueueItem(Object obj, Long j) {
            ((MediaController.TransportControls) obj).skipToQueueItem(j)
        }

        fun stop(Object obj) {
            ((MediaController.TransportControls) obj).stop()
        }
    }

    MediaControllerCompatApi21() {
    }

    fun adjustVolume(Object obj, Int i, Int i2) {
        ((MediaController) obj).adjustVolume(i, i2)
    }

    fun createCallback(Callback callback) {
        return CallbackProxy(callback)
    }

    fun dispatchMediaButtonEvent(Object obj, KeyEvent keyEvent) {
        return ((MediaController) obj).dispatchMediaButtonEvent(keyEvent)
    }

    fun fromToken(Context context, Object obj) {
        return MediaController(context, (MediaSession.Token) obj)
    }

    fun getExtras(Object obj) {
        return ((MediaController) obj).getExtras()
    }

    fun getFlags(Object obj) {
        return ((MediaController) obj).getFlags()
    }

    fun getMediaController(Activity activity) {
        return activity.getMediaController()
    }

    fun getMetadata(Object obj) {
        return ((MediaController) obj).getMetadata()
    }

    fun getPackageName(Object obj) {
        return ((MediaController) obj).getPackageName()
    }

    fun getPlaybackInfo(Object obj) {
        return ((MediaController) obj).getPlaybackInfo()
    }

    fun getPlaybackState(Object obj) {
        return ((MediaController) obj).getPlaybackState()
    }

    fun getQueue(Object obj) {
        List<MediaSession.QueueItem> queue = ((MediaController) obj).getQueue()
        if (queue == null) {
            return null
        }
        return ArrayList(queue)
    }

    fun getQueueTitle(Object obj) {
        return ((MediaController) obj).getQueueTitle()
    }

    fun getRatingType(Object obj) {
        return ((MediaController) obj).getRatingType()
    }

    fun getSessionActivity(Object obj) {
        return ((MediaController) obj).getSessionActivity()
    }

    fun getSessionToken(Object obj) {
        return ((MediaController) obj).getSessionToken()
    }

    fun getTransportControls(Object obj) {
        return ((MediaController) obj).getTransportControls()
    }

    fun registerCallback(Object obj, Object obj2, Handler handler) {
        ((MediaController) obj).registerCallback((MediaController.Callback) obj2, handler)
    }

    fun sendCommand(Object obj, String str, Bundle bundle, ResultReceiver resultReceiver) {
        ((MediaController) obj).sendCommand(str, bundle, resultReceiver)
    }

    fun setMediaController(Activity activity, Object obj) {
        activity.setMediaController((MediaController) obj)
    }

    fun setVolumeTo(Object obj, Int i, Int i2) {
        ((MediaController) obj).setVolumeTo(i, i2)
    }

    fun unregisterCallback(Object obj, Object obj2) {
        ((MediaController) obj).unregisterCallback((MediaController.Callback) obj2)
    }
}
