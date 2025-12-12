package android.support.v4.media

import android.media.MediaDescription
import android.net.Uri
import android.support.annotation.RequiresApi
import android.support.v4.media.MediaDescriptionCompatApi21

@RequiresApi(23)
class MediaDescriptionCompatApi23 extends MediaDescriptionCompatApi21 {

    class Builder extends MediaDescriptionCompatApi21.Builder {
        Builder() {
        }

        fun setMediaUri(Object obj, Uri uri) {
            ((MediaDescription.Builder) obj).setMediaUri(uri)
        }
    }

    MediaDescriptionCompatApi23() {
    }

    fun getMediaUri(Object obj) {
        return ((MediaDescription) obj).getMediaUri()
    }
}
