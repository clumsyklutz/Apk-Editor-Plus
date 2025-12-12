package android.support.v4.media

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.media.MediaDescriptionCompatApi21
import android.support.v4.media.MediaDescriptionCompatApi23
import android.text.TextUtils

class MediaDescriptionCompat implements Parcelable {
    public static val BT_FOLDER_TYPE_ALBUMS = 2
    public static val BT_FOLDER_TYPE_ARTISTS = 3
    public static val BT_FOLDER_TYPE_GENRES = 4
    public static val BT_FOLDER_TYPE_MIXED = 0
    public static val BT_FOLDER_TYPE_PLAYLISTS = 5
    public static val BT_FOLDER_TYPE_TITLES = 1
    public static val BT_FOLDER_TYPE_YEARS = 6
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.MediaDescriptionCompat.1
        @Override // android.os.Parcelable.Creator
        public final MediaDescriptionCompat createFromParcel(Parcel parcel) {
            return Build.VERSION.SDK_INT < 21 ? MediaDescriptionCompat(parcel) : MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel))
        }

        @Override // android.os.Parcelable.Creator
        public final Array<MediaDescriptionCompat> newArray(Int i) {
            return new MediaDescriptionCompat[i]
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static val DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI"

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static val DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
    public static val EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE"
    public static val EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS"
    public static val STATUS_DOWNLOADED = 2
    public static val STATUS_DOWNLOADING = 1
    public static val STATUS_NOT_DOWNLOADED = 0
    private final CharSequence mDescription
    private Object mDescriptionObj
    private final Bundle mExtras
    private final Bitmap mIcon
    private final Uri mIconUri
    private final String mMediaId
    private final Uri mMediaUri
    private final CharSequence mSubtitle
    private final CharSequence mTitle

    class Builder {
        private CharSequence mDescription
        private Bundle mExtras
        private Bitmap mIcon
        private Uri mIconUri
        private String mMediaId
        private Uri mMediaUri
        private CharSequence mSubtitle
        private CharSequence mTitle

        public final MediaDescriptionCompat build() {
            return MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri)
        }

        public final Builder setDescription(@Nullable CharSequence charSequence) {
            this.mDescription = charSequence
            return this
        }

        public final Builder setExtras(@Nullable Bundle bundle) {
            this.mExtras = bundle
            return this
        }

        public final Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.mIcon = bitmap
            return this
        }

        public final Builder setIconUri(@Nullable Uri uri) {
            this.mIconUri = uri
            return this
        }

        public final Builder setMediaId(@Nullable String str) {
            this.mMediaId = str
            return this
        }

        public final Builder setMediaUri(@Nullable Uri uri) {
            this.mMediaUri = uri
            return this
        }

        public final Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.mSubtitle = charSequence
            return this
        }

        public final Builder setTitle(@Nullable CharSequence charSequence) {
            this.mTitle = charSequence
            return this
        }
    }

    MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString()
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
        this.mIcon = (Bitmap) parcel.readParcelable(null)
        this.mIconUri = (Uri) parcel.readParcelable(null)
        this.mExtras = parcel.readBundle()
        this.mMediaUri = (Uri) parcel.readParcelable(null)
    }

    MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = str
        this.mTitle = charSequence
        this.mSubtitle = charSequence2
        this.mDescription = charSequence3
        this.mIcon = bitmap
        this.mIconUri = uri
        this.mExtras = bundle
        this.mMediaUri = uri2
    }

    fun fromMediaDescription(Object obj) {
        Bundle bundle
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null
        }
        Builder builder = Builder()
        builder.setMediaId(MediaDescriptionCompatApi21.getMediaId(obj))
        builder.setTitle(MediaDescriptionCompatApi21.getTitle(obj))
        builder.setSubtitle(MediaDescriptionCompatApi21.getSubtitle(obj))
        builder.setDescription(MediaDescriptionCompatApi21.getDescription(obj))
        builder.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(obj))
        builder.setIconUri(MediaDescriptionCompatApi21.getIconUri(obj))
        Bundle extras = MediaDescriptionCompatApi21.getExtras(obj)
        Uri uri = extras == null ? null : (Uri) extras.getParcelable(DESCRIPTION_KEY_MEDIA_URI)
        if (uri == null) {
            bundle = extras
        } else if (extras.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) && extras.size() == 2) {
            bundle = null
        } else {
            extras.remove(DESCRIPTION_KEY_MEDIA_URI)
            extras.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG)
            bundle = extras
        }
        builder.setExtras(bundle)
        if (uri != null) {
            builder.setMediaUri(uri)
        } else if (Build.VERSION.SDK_INT >= 23) {
            builder.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(obj))
        }
        MediaDescriptionCompat mediaDescriptionCompatBuild = builder.build()
        mediaDescriptionCompatBuild.mDescriptionObj = obj
        return mediaDescriptionCompatBuild
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return 0
    }

    @Nullable
    public final CharSequence getDescription() {
        return this.mDescription
    }

    @Nullable
    public final Bundle getExtras() {
        return this.mExtras
    }

    @Nullable
    public final Bitmap getIconBitmap() {
        return this.mIcon
    }

    @Nullable
    public final Uri getIconUri() {
        return this.mIconUri
    }

    public final Object getMediaDescription() {
        if (this.mDescriptionObj != null || Build.VERSION.SDK_INT < 21) {
            return this.mDescriptionObj
        }
        Object objNewInstance = MediaDescriptionCompatApi21.Builder.newInstance()
        MediaDescriptionCompatApi21.Builder.setMediaId(objNewInstance, this.mMediaId)
        MediaDescriptionCompatApi21.Builder.setTitle(objNewInstance, this.mTitle)
        MediaDescriptionCompatApi21.Builder.setSubtitle(objNewInstance, this.mSubtitle)
        MediaDescriptionCompatApi21.Builder.setDescription(objNewInstance, this.mDescription)
        MediaDescriptionCompatApi21.Builder.setIconBitmap(objNewInstance, this.mIcon)
        MediaDescriptionCompatApi21.Builder.setIconUri(objNewInstance, this.mIconUri)
        Bundle bundle = this.mExtras
        if (Build.VERSION.SDK_INT < 23 && this.mMediaUri != null) {
            if (bundle == null) {
                bundle = Bundle()
                bundle.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true)
            }
            bundle.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.mMediaUri)
        }
        MediaDescriptionCompatApi21.Builder.setExtras(objNewInstance, bundle)
        if (Build.VERSION.SDK_INT >= 23) {
            MediaDescriptionCompatApi23.Builder.setMediaUri(objNewInstance, this.mMediaUri)
        }
        this.mDescriptionObj = MediaDescriptionCompatApi21.Builder.build(objNewInstance)
        return this.mDescriptionObj
    }

    @Nullable
    public final String getMediaId() {
        return this.mMediaId
    }

    @Nullable
    public final Uri getMediaUri() {
        return this.mMediaUri
    }

    @Nullable
    public final CharSequence getSubtitle() {
        return this.mSubtitle
    }

    @Nullable
    public final CharSequence getTitle() {
        return this.mTitle
    }

    public final String toString() {
        return ((Object) this.mTitle) + ", " + ((Object) this.mSubtitle) + ", " + ((Object) this.mDescription)
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), parcel, i)
            return
        }
        parcel.writeString(this.mMediaId)
        TextUtils.writeToParcel(this.mTitle, parcel, i)
        TextUtils.writeToParcel(this.mSubtitle, parcel, i)
        TextUtils.writeToParcel(this.mDescription, parcel, i)
        parcel.writeParcelable(this.mIcon, i)
        parcel.writeParcelable(this.mIconUri, i)
        parcel.writeBundle(this.mExtras)
        parcel.writeParcelable(this.mMediaUri, i)
    }
}
