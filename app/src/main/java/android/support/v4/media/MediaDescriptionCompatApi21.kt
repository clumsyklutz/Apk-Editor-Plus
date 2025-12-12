package android.support.v4.media

import android.graphics.Bitmap
import android.media.MediaDescription
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.support.annotation.RequiresApi

@RequiresApi(21)
class MediaDescriptionCompatApi21 {

    class Builder {
        Builder() {
        }

        fun build(Object obj) {
            return ((MediaDescription.Builder) obj).build()
        }

        fun newInstance() {
            return new MediaDescription.Builder()
        }

        fun setDescription(Object obj, CharSequence charSequence) {
            ((MediaDescription.Builder) obj).setDescription(charSequence)
        }

        fun setExtras(Object obj, Bundle bundle) {
            ((MediaDescription.Builder) obj).setExtras(bundle)
        }

        fun setIconBitmap(Object obj, Bitmap bitmap) {
            ((MediaDescription.Builder) obj).setIconBitmap(bitmap)
        }

        fun setIconUri(Object obj, Uri uri) {
            ((MediaDescription.Builder) obj).setIconUri(uri)
        }

        fun setMediaId(Object obj, String str) {
            ((MediaDescription.Builder) obj).setMediaId(str)
        }

        fun setSubtitle(Object obj, CharSequence charSequence) {
            ((MediaDescription.Builder) obj).setSubtitle(charSequence)
        }

        fun setTitle(Object obj, CharSequence charSequence) {
            ((MediaDescription.Builder) obj).setTitle(charSequence)
        }
    }

    MediaDescriptionCompatApi21() {
    }

    fun fromParcel(Parcel parcel) {
        return MediaDescription.CREATOR.createFromParcel(parcel)
    }

    fun getDescription(Object obj) {
        return ((MediaDescription) obj).getDescription()
    }

    fun getExtras(Object obj) {
        return ((MediaDescription) obj).getExtras()
    }

    fun getIconBitmap(Object obj) {
        return ((MediaDescription) obj).getIconBitmap()
    }

    fun getIconUri(Object obj) {
        return ((MediaDescription) obj).getIconUri()
    }

    fun getMediaId(Object obj) {
        return ((MediaDescription) obj).getMediaId()
    }

    fun getSubtitle(Object obj) {
        return ((MediaDescription) obj).getSubtitle()
    }

    fun getTitle(Object obj) {
        return ((MediaDescription) obj).getTitle()
    }

    fun writeToParcel(Object obj, Parcel parcel, Int i) {
        ((MediaDescription) obj).writeToParcel(parcel, i)
    }
}
