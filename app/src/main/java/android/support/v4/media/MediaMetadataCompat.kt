package android.support.v4.media

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RestrictTo
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import android.util.Log
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.Set

class MediaMetadataCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR
    static final ArrayMap METADATA_KEYS_TYPE
    public static val METADATA_KEY_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT"
    public static val METADATA_KEY_ALBUM = "android.media.metadata.ALBUM"
    public static val METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART"
    public static val METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST"
    public static val METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI"
    public static val METADATA_KEY_ART = "android.media.metadata.ART"
    public static val METADATA_KEY_ARTIST = "android.media.metadata.ARTIST"
    public static val METADATA_KEY_ART_URI = "android.media.metadata.ART_URI"
    public static val METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR"
    public static val METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE"
    public static val METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION"
    public static val METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER"
    public static val METADATA_KEY_DATE = "android.media.metadata.DATE"
    public static val METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER"
    public static val METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION"
    public static val METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON"
    public static val METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI"
    public static val METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE"
    public static val METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE"
    public static val METADATA_KEY_DOWNLOAD_STATUS = "android.media.metadata.DOWNLOAD_STATUS"
    public static val METADATA_KEY_DURATION = "android.media.metadata.DURATION"
    public static val METADATA_KEY_GENRE = "android.media.metadata.GENRE"
    public static val METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID"
    public static val METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI"
    public static val METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS"
    public static val METADATA_KEY_RATING = "android.media.metadata.RATING"
    public static val METADATA_KEY_TITLE = "android.media.metadata.TITLE"
    public static val METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER"
    public static val METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING"
    public static val METADATA_KEY_WRITER = "android.media.metadata.WRITER"
    public static val METADATA_KEY_YEAR = "android.media.metadata.YEAR"
    static val METADATA_TYPE_BITMAP = 2
    static val METADATA_TYPE_LONG = 0
    static val METADATA_TYPE_RATING = 3
    static val METADATA_TYPE_TEXT = 1
    private static final Array<String> PREFERRED_BITMAP_ORDER
    private static final Array<String> PREFERRED_DESCRIPTION_ORDER
    private static final Array<String> PREFERRED_URI_ORDER
    private static val TAG = "MediaMetadata"
    final Bundle mBundle
    private MediaDescriptionCompat mDescription
    private Object mMetadataObj

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface BitmapKey {
    }

    class Builder {
        private final Bundle mBundle

        constructor() {
            this.mBundle = Bundle()
        }

        constructor(MediaMetadataCompat mediaMetadataCompat) {
            this.mBundle = Bundle(mediaMetadataCompat.mBundle)
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        constructor(MediaMetadataCompat mediaMetadataCompat, Int i) {
            this(mediaMetadataCompat)
            for (String str : this.mBundle.keySet()) {
                Object obj = this.mBundle.get(str)
                if (obj is Bitmap) {
                    Bitmap bitmap = (Bitmap) obj
                    if (bitmap.getHeight() > i || bitmap.getWidth() > i) {
                        putBitmap(str, scaleBitmap(bitmap, i))
                    }
                }
            }
        }

        private fun scaleBitmap(Bitmap bitmap, Int i) {
            Float f = i
            Float fMin = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight())
            return Bitmap.createScaledBitmap(bitmap, (Int) (fMin * bitmap.getWidth()), (Int) (bitmap.getHeight() * fMin), true)
        }

        public final MediaMetadataCompat build() {
            return MediaMetadataCompat(this.mBundle)
        }

        public final Builder putBitmap(String str, Bitmap bitmap) {
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(str)).intValue() != 2) {
                throw IllegalArgumentException("The " + str + " key cannot be used to put a Bitmap")
            }
            this.mBundle.putParcelable(str, bitmap)
            return this
        }

        public final Builder putLong(String str, Long j) {
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(str)).intValue() != 0) {
                throw IllegalArgumentException("The " + str + " key cannot be used to put a Long")
            }
            this.mBundle.putLong(str, j)
            return this
        }

        public final Builder putRating(String str, RatingCompat ratingCompat) {
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(str)).intValue() != 3) {
                throw IllegalArgumentException("The " + str + " key cannot be used to put a Rating")
            }
            if (Build.VERSION.SDK_INT >= 19) {
                this.mBundle.putParcelable(str, (Parcelable) ratingCompat.getRating())
            } else {
                this.mBundle.putParcelable(str, ratingCompat)
            }
            return this
        }

        public final Builder putString(String str, String str2) {
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw IllegalArgumentException("The " + str + " key cannot be used to put a String")
            }
            this.mBundle.putCharSequence(str, str2)
            return this
        }

        public final Builder putText(String str, CharSequence charSequence) {
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw IllegalArgumentException("The " + str + " key cannot be used to put a CharSequence")
            }
            this.mBundle.putCharSequence(str, charSequence)
            return this
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface LongKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface RatingKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface TextKey {
    }

    static {
        ArrayMap arrayMap = ArrayMap()
        METADATA_KEYS_TYPE = arrayMap
        arrayMap.put(METADATA_KEY_TITLE, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DURATION, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, 2)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, 2)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_USER_RATING, 3)
        METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, 3)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, 2)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_BT_FOLDER_TYPE, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_URI, 1)
        METADATA_KEYS_TYPE.put(METADATA_KEY_ADVERTISEMENT, 0)
        METADATA_KEYS_TYPE.put(METADATA_KEY_DOWNLOAD_STATUS, 0)
        PREFERRED_DESCRIPTION_ORDER = new Array<String>{METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER}
        PREFERRED_BITMAP_ORDER = new Array<String>{METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART}
        PREFERRED_URI_ORDER = new Array<String>{METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI}
        CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.MediaMetadataCompat.1
            @Override // android.os.Parcelable.Creator
            public final MediaMetadataCompat createFromParcel(Parcel parcel) {
                return MediaMetadataCompat(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<MediaMetadataCompat> newArray(Int i) {
                return new MediaMetadataCompat[i]
            }
        }
    }

    MediaMetadataCompat(Bundle bundle) {
        this.mBundle = Bundle(bundle)
        this.mBundle.setClassLoader(MediaMetadataCompat.class.getClassLoader())
    }

    MediaMetadataCompat(Parcel parcel) {
        this.mBundle = parcel.readBundle()
        this.mBundle.setClassLoader(MediaMetadataCompat.class.getClassLoader())
    }

    fun fromMediaMetadata(Object obj) {
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null
        }
        Parcel parcelObtain = Parcel.obtain()
        MediaMetadataCompatApi21.writeToParcel(obj, parcelObtain, 0)
        parcelObtain.setDataPosition(0)
        MediaMetadataCompat mediaMetadataCompat = (MediaMetadataCompat) CREATOR.createFromParcel(parcelObtain)
        parcelObtain.recycle()
        mediaMetadataCompat.mMetadataObj = obj
        return mediaMetadataCompat
    }

    public final Boolean containsKey(String str) {
        return this.mBundle.containsKey(str)
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return 0
    }

    public final Bitmap getBitmap(String str) {
        try {
            return (Bitmap) this.mBundle.getParcelable(str)
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Bitmap.", e)
            return null
        }
    }

    public final Bundle getBundle() {
        return this.mBundle
    }

    public final MediaDescriptionCompat getDescription() {
        Int i
        Bitmap bitmap
        Uri uri
        if (this.mDescription != null) {
            return this.mDescription
        }
        String string = getString(METADATA_KEY_MEDIA_ID)
        Array<CharSequence> charSequenceArr = new CharSequence[3]
        CharSequence text = getText(METADATA_KEY_DISPLAY_TITLE)
        if (TextUtils.isEmpty(text)) {
            Int i2 = 0
            Int i3 = 0
            while (i3 < 3 && i2 < PREFERRED_DESCRIPTION_ORDER.length) {
                Int i4 = i2 + 1
                CharSequence text2 = getText(PREFERRED_DESCRIPTION_ORDER[i2])
                if (TextUtils.isEmpty(text2)) {
                    i = i3
                } else {
                    i = i3 + 1
                    charSequenceArr[i3] = text2
                }
                i3 = i
                i2 = i4
            }
        } else {
            charSequenceArr[0] = text
            charSequenceArr[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE)
            charSequenceArr[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION)
        }
        Int i5 = 0
        while (true) {
            if (i5 >= PREFERRED_BITMAP_ORDER.length) {
                bitmap = null
                break
            }
            Bitmap bitmap2 = getBitmap(PREFERRED_BITMAP_ORDER[i5])
            if (bitmap2 != null) {
                bitmap = bitmap2
                break
            }
            i5++
        }
        Int i6 = 0
        while (true) {
            if (i6 >= PREFERRED_URI_ORDER.length) {
                uri = null
                break
            }
            String string2 = getString(PREFERRED_URI_ORDER[i6])
            if (!TextUtils.isEmpty(string2)) {
                uri = Uri.parse(string2)
                break
            }
            i6++
        }
        String string3 = getString(METADATA_KEY_MEDIA_URI)
        Uri uri2 = TextUtils.isEmpty(string3) ? null : Uri.parse(string3)
        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder()
        builder.setMediaId(string)
        builder.setTitle(charSequenceArr[0])
        builder.setSubtitle(charSequenceArr[1])
        builder.setDescription(charSequenceArr[2])
        builder.setIconBitmap(bitmap)
        builder.setIconUri(uri)
        builder.setMediaUri(uri2)
        Bundle bundle = Bundle()
        if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE))
        }
        if (this.mBundle.containsKey(METADATA_KEY_DOWNLOAD_STATUS)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_DOWNLOAD_STATUS, getLong(METADATA_KEY_DOWNLOAD_STATUS))
        }
        if (!bundle.isEmpty()) {
            builder.setExtras(bundle)
        }
        this.mDescription = builder.build()
        return this.mDescription
    }

    public final Long getLong(String str) {
        return this.mBundle.getLong(str, 0L)
    }

    public final Object getMediaMetadata() {
        if (this.mMetadataObj == null && Build.VERSION.SDK_INT >= 21) {
            Parcel parcelObtain = Parcel.obtain()
            writeToParcel(parcelObtain, 0)
            parcelObtain.setDataPosition(0)
            this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel(parcelObtain)
            parcelObtain.recycle()
        }
        return this.mMetadataObj
    }

    public final RatingCompat getRating(String str) {
        try {
            return Build.VERSION.SDK_INT >= 19 ? RatingCompat.fromRating(this.mBundle.getParcelable(str)) : (RatingCompat) this.mBundle.getParcelable(str)
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Rating.", e)
            return null
        }
    }

    public final String getString(String str) {
        CharSequence charSequence = this.mBundle.getCharSequence(str)
        if (charSequence != null) {
            return charSequence.toString()
        }
        return null
    }

    public final CharSequence getText(String str) {
        return this.mBundle.getCharSequence(str)
    }

    public final Set keySet() {
        return this.mBundle.keySet()
    }

    public final Int size() {
        return this.mBundle.size()
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        parcel.writeBundle(this.mBundle)
    }
}
