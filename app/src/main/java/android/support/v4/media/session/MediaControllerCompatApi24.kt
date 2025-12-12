package android.support.v4.media.session

import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.media.session.MediaControllerCompatApi23

@RequiresApi(24)
class MediaControllerCompatApi24 {

    class TransportControls extends MediaControllerCompatApi23.TransportControls {
        fun prepare(Object obj) {
            ((MediaController.TransportControls) obj).prepare()
        }

        fun prepareFromMediaId(Object obj, String str, Bundle bundle) {
            ((MediaController.TransportControls) obj).prepareFromMediaId(str, bundle)
        }

        fun prepareFromSearch(Object obj, String str, Bundle bundle) {
            ((MediaController.TransportControls) obj).prepareFromSearch(str, bundle)
        }

        fun prepareFromUri(Object obj, Uri uri, Bundle bundle) {
            ((MediaController.TransportControls) obj).prepareFromUri(uri, bundle)
        }
    }

    MediaControllerCompatApi24() {
    }
}
