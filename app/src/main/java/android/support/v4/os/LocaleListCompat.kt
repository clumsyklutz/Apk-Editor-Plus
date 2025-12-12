package android.support.v4.os

import android.os.Build
import android.os.LocaleList
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.Size
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import java.util.Locale

class LocaleListCompat {
    static final LocaleListInterface IMPL
    private static val sEmptyLocaleList = LocaleListCompat()

    @RequiresApi(24)
    class LocaleListCompatApi24Impl implements LocaleListInterface {
        private LocaleList mLocaleList = LocaleList(new Locale[0])

        LocaleListCompatApi24Impl() {
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun equals(Object obj) {
            return this.mLocaleList.equals(((LocaleListCompat) obj).unwrap())
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun get(Int i) {
            return this.mLocaleList.get(i)
        }

        @Override // android.support.v4.os.LocaleListInterface
        @Nullable
        fun getFirstMatch(Array<String> strArr) {
            if (this.mLocaleList != null) {
                return this.mLocaleList.getFirstMatch(strArr)
            }
            return null
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun getLocaleList() {
            return this.mLocaleList
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun hashCode() {
            return this.mLocaleList.hashCode()
        }

        @Override // android.support.v4.os.LocaleListInterface
        @IntRange(from = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN)
        fun indexOf(Locale locale) {
            return this.mLocaleList.indexOf(locale)
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun isEmpty() {
            return this.mLocaleList.isEmpty()
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun setLocaleList(@NonNull Locale... localeArr) {
            this.mLocaleList = LocaleList(localeArr)
        }

        @Override // android.support.v4.os.LocaleListInterface
        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
        fun size() {
            return this.mLocaleList.size()
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun toLanguageTags() {
            return this.mLocaleList.toLanguageTags()
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun toString() {
            return this.mLocaleList.toString()
        }
    }

    class LocaleListCompatBaseImpl implements LocaleListInterface {
        private LocaleListHelper mLocaleList = LocaleListHelper(new Locale[0])

        LocaleListCompatBaseImpl() {
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun equals(Object obj) {
            return this.mLocaleList.equals(((LocaleListCompat) obj).unwrap())
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun get(Int i) {
            return this.mLocaleList.get(i)
        }

        @Override // android.support.v4.os.LocaleListInterface
        @Nullable
        fun getFirstMatch(Array<String> strArr) {
            if (this.mLocaleList != null) {
                return this.mLocaleList.getFirstMatch(strArr)
            }
            return null
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun getLocaleList() {
            return this.mLocaleList
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun hashCode() {
            return this.mLocaleList.hashCode()
        }

        @Override // android.support.v4.os.LocaleListInterface
        @IntRange(from = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN)
        fun indexOf(Locale locale) {
            return this.mLocaleList.indexOf(locale)
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun isEmpty() {
            return this.mLocaleList.isEmpty()
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun setLocaleList(@NonNull Locale... localeArr) {
            this.mLocaleList = LocaleListHelper(localeArr)
        }

        @Override // android.support.v4.os.LocaleListInterface
        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
        fun size() {
            return this.mLocaleList.size()
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun toLanguageTags() {
            return this.mLocaleList.toLanguageTags()
        }

        @Override // android.support.v4.os.LocaleListInterface
        fun toString() {
            return this.mLocaleList.toString()
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = LocaleListCompatApi24Impl()
        } else {
            IMPL = LocaleListCompatBaseImpl()
        }
    }

    private constructor() {
    }

    fun create(@NonNull Locale... localeArr) {
        LocaleListCompat localeListCompat = LocaleListCompat()
        localeListCompat.setLocaleListArray(localeArr)
        return localeListCompat
    }

    @NonNull
    fun forLanguageTags(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList()
        }
        Array<String> strArrSplit = str.split(",", -1)
        Array<Locale> localeArr = new Locale[strArrSplit.length]
        for (Int i = 0; i < localeArr.length; i++) {
            localeArr[i] = Build.VERSION.SDK_INT >= 21 ? Locale.forLanguageTag(strArrSplit[i]) : LocaleHelper.forLanguageTag(strArrSplit[i])
        }
        LocaleListCompat localeListCompat = LocaleListCompat()
        localeListCompat.setLocaleListArray(localeArr)
        return localeListCompat
    }

    @Size(min = 1)
    @NonNull
    fun getAdjustedDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getAdjustedDefault()) : create(Locale.getDefault())
    }

    @Size(min = 1)
    @NonNull
    fun getDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getDefault()) : create(Locale.getDefault())
    }

    @NonNull
    fun getEmptyLocaleList() {
        return sEmptyLocaleList
    }

    @RequiresApi(24)
    private fun setLocaleList(LocaleList localeList) {
        Int size = localeList.size()
        if (size > 0) {
            Array<Locale> localeArr = new Locale[size]
            for (Int i = 0; i < size; i++) {
                localeArr[i] = localeList.get(i)
            }
            IMPL.setLocaleList(localeArr)
        }
    }

    private fun setLocaleListArray(Locale... localeArr) {
        IMPL.setLocaleList(localeArr)
    }

    @RequiresApi(24)
    fun wrap(Object obj) {
        LocaleListCompat localeListCompat = LocaleListCompat()
        if (obj is LocaleList) {
            localeListCompat.setLocaleList((LocaleList) obj)
        }
        return localeListCompat
    }

    public final Boolean equals(Object obj) {
        return IMPL.equals(obj)
    }

    public final Locale get(Int i) {
        return IMPL.get(i)
    }

    public final Locale getFirstMatch(Array<String> strArr) {
        return IMPL.getFirstMatch(strArr)
    }

    public final Int hashCode() {
        return IMPL.hashCode()
    }

    @IntRange(from = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN)
    public final Int indexOf(Locale locale) {
        return IMPL.indexOf(locale)
    }

    public final Boolean isEmpty() {
        return IMPL.isEmpty()
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    public final Int size() {
        return IMPL.size()
    }

    @NonNull
    public final String toLanguageTags() {
        return IMPL.toLanguageTags()
    }

    public final String toString() {
        return IMPL.toString()
    }

    @Nullable
    public final Object unwrap() {
        return IMPL.getLocaleList()
    }
}
