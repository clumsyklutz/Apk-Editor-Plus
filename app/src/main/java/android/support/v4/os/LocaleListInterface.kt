package android.support.v4.os

import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import java.util.Locale

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
interface LocaleListInterface {
    Boolean equals(Object obj)

    Locale get(Int i)

    @Nullable
    Locale getFirstMatch(Array<String> strArr)

    Object getLocaleList()

    Int hashCode()

    @IntRange(from = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN)
    Int indexOf(Locale locale)

    Boolean isEmpty()

    Unit setLocaleList(@NonNull Locale... localeArr)

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    Int size()

    String toLanguageTags()

    String toString()
}
