package android.support.v4.media.session

import android.net.Uri
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.media.session.MediaSessionCompatApi21

@RequiresApi(23)
class MediaSessionCompatApi23 {

    public interface Callback extends MediaSessionCompatApi21.Callback {
        Unit onPlayFromUri(Uri uri, Bundle bundle)
    }

    class CallbackProxy extends MediaSessionCompatApi21.CallbackProxy {
        constructor(Callback callback) {
            super(callback)
        }

        @Override // android.media.session.MediaSession.Callback
        fun onPlayFromUri(Uri uri, Bundle bundle) {
            ((Callback) this.mCallback).onPlayFromUri(uri, bundle)
        }
    }

    MediaSessionCompatApi23() {
    }

    fun createCallback(Callback callback) {
        return CallbackProxy(callback)
    }
}
