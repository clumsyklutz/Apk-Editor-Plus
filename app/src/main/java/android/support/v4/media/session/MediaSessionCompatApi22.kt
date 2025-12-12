package android.support.v4.media.session

import android.media.session.MediaSession
import android.support.annotation.RequiresApi

@RequiresApi(22)
class MediaSessionCompatApi22 {
    MediaSessionCompatApi22() {
    }

    fun setRatingType(Object obj, Int i) {
        ((MediaSession) obj).setRatingType(i)
    }
}
