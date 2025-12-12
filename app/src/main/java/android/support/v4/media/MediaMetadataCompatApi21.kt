package android.support.v4.media

import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.Rating
import android.os.Parcel
import android.support.annotation.RequiresApi
import java.util.Set

@RequiresApi(21)
class MediaMetadataCompatApi21 {

    class Builder {
        fun build(Object obj) {
            return ((MediaMetadata.Builder) obj).build()
        }

        fun newInstance() {
            return new MediaMetadata.Builder()
        }

        fun putBitmap(Object obj, String str, Bitmap bitmap) {
            ((MediaMetadata.Builder) obj).putBitmap(str, bitmap)
        }

        fun putLong(Object obj, String str, Long j) {
            ((MediaMetadata.Builder) obj).putLong(str, j)
        }

        fun putRating(Object obj, String str, Object obj2) {
            ((MediaMetadata.Builder) obj).putRating(str, (Rating) obj2)
        }

        fun putString(Object obj, String str, String str2) {
            ((MediaMetadata.Builder) obj).putString(str, str2)
        }

        fun putText(Object obj, String str, CharSequence charSequence) {
            ((MediaMetadata.Builder) obj).putText(str, charSequence)
        }
    }

    MediaMetadataCompatApi21() {
    }

    fun createFromParcel(Parcel parcel) {
        return MediaMetadata.CREATOR.createFromParcel(parcel)
    }

    fun getBitmap(Object obj, String str) {
        return ((MediaMetadata) obj).getBitmap(str)
    }

    fun getLong(Object obj, String str) {
        return ((MediaMetadata) obj).getLong(str)
    }

    fun getRating(Object obj, String str) {
        return ((MediaMetadata) obj).getRating(str)
    }

    fun getText(Object obj, String str) {
        return ((MediaMetadata) obj).getText(str)
    }

    fun keySet(Object obj) {
        return ((MediaMetadata) obj).keySet()
    }

    fun writeToParcel(Object obj, Parcel parcel, Int i) {
        ((MediaMetadata) obj).writeToParcel(parcel, i)
    }
}
