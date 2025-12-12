package android.support.v4.media.session

import android.media.session.MediaSession
import android.net.Uri
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.media.session.MediaSessionCompatApi23
import android.util.Log
import java.lang.reflect.InvocationTargetException

@RequiresApi(24)
class MediaSessionCompatApi24 {
    private static val TAG = "MediaSessionCompatApi24"

    public interface Callback extends MediaSessionCompatApi23.Callback {
        Unit onPrepare()

        Unit onPrepareFromMediaId(String str, Bundle bundle)

        Unit onPrepareFromSearch(String str, Bundle bundle)

        Unit onPrepareFromUri(Uri uri, Bundle bundle)
    }

    class CallbackProxy extends MediaSessionCompatApi23.CallbackProxy {
        constructor(Callback callback) {
            super(callback)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPrepare() {
            ((Callback) this.mCallback).onPrepare()
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPrepareFromMediaId(String str, Bundle bundle) {
            ((Callback) this.mCallback).onPrepareFromMediaId(str, bundle)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPrepareFromSearch(String str, Bundle bundle) {
            ((Callback) this.mCallback).onPrepareFromSearch(str, bundle)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPrepareFromUri(Uri uri, Bundle bundle) {
            ((Callback) this.mCallback).onPrepareFromUri(uri, bundle)
        }
    }

    MediaSessionCompatApi24() {
    }

    fun createCallback(Callback callback) {
        return CallbackProxy(callback)
    }

    fun getCallingPackage(Object obj) {
        MediaSession mediaSession = (MediaSession) obj
        try {
            return (String) mediaSession.getClass().getMethod("getCallingPackage", new Class[0]).invoke(mediaSession, new Object[0])
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "Cannot execute MediaSession.getCallingPackage()", e)
            return null
        }
    }
}
