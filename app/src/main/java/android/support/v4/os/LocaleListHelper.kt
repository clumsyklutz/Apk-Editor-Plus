package android.support.v4.os

import android.os.Build
import android.support.annotation.GuardedBy
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.Size
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.widget.ActivityChooserView
import java.util.Arrays
import java.util.Collection
import java.util.HashSet
import java.util.Locale

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
final class LocaleListHelper {
    private static val NUM_PSEUDO_LOCALES = 2
    private static val STRING_AR_XB = "ar-XB"
    private static val STRING_EN_XA = "en-XA"
    private final Array<Locale> mList

    @NonNull
    private final String mStringRepresentation
    private static final Array<Locale> sEmptyList = new Locale[0]
    private static val sEmptyLocaleList = LocaleListHelper(new Locale[0])
    private static val LOCALE_EN_XA = Locale("en", "XA")
    private static val LOCALE_AR_XB = Locale("ar", "XB")
    private static val EN_LATN = LocaleHelper.forLanguageTag("en-Latn")
    private static val sLock = Object()

    @GuardedBy("sLock")
    private static LocaleListHelper sLastExplicitlySetLocaleList = null

    @GuardedBy("sLock")
    private static LocaleListHelper sDefaultLocaleList = null

    @GuardedBy("sLock")
    private static LocaleListHelper sDefaultAdjustedLocaleList = null

    @GuardedBy("sLock")
    private static Locale sLastDefaultLocale = null

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    LocaleListHelper(@NonNull Locale locale, LocaleListHelper localeListHelper) {
        Int i
        if (locale == null) {
            throw NullPointerException("topLocale is null")
        }
        Int length = localeListHelper == null ? 0 : localeListHelper.mList.length
        Int i2 = 0
        while (true) {
            if (i2 >= length) {
                i = -1
                break
            } else {
                if (locale.equals(localeListHelper.mList[i2])) {
                    i = i2
                    break
                }
                i2++
            }
        }
        Int i3 = length + (i == -1 ? 1 : 0)
        Array<Locale> localeArr = new Locale[i3]
        localeArr[0] = (Locale) locale.clone()
        if (i == -1) {
            for (Int i4 = 0; i4 < length; i4++) {
                localeArr[i4 + 1] = (Locale) localeListHelper.mList[i4].clone()
            }
        } else {
            for (Int i5 = 0; i5 < i; i5++) {
                localeArr[i5 + 1] = (Locale) localeListHelper.mList[i5].clone()
            }
            for (Int i6 = i + 1; i6 < length; i6++) {
                localeArr[i6] = (Locale) localeListHelper.mList[i6].clone()
            }
        }
        StringBuilder sb = StringBuilder()
        for (Int i7 = 0; i7 < i3; i7++) {
            sb.append(LocaleHelper.toLanguageTag(localeArr[i7]))
            if (i7 < i3 - 1) {
                sb.append(',')
            }
        }
        this.mList = localeArr
        this.mStringRepresentation = sb.toString()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    LocaleListHelper(@NonNull Locale... localeArr) {
        if (localeArr.length == 0) {
            this.mList = sEmptyList
            this.mStringRepresentation = ""
            return
        }
        Array<Locale> localeArr2 = new Locale[localeArr.length]
        HashSet hashSet = HashSet()
        StringBuilder sb = StringBuilder()
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= localeArr.length) {
                this.mList = localeArr2
                this.mStringRepresentation = sb.toString()
                return
            }
            Locale locale = localeArr[i2]
            if (locale == null) {
                throw NullPointerException("list[" + i2 + "] is null")
            }
            if (hashSet.contains(locale)) {
                throw IllegalArgumentException("list[" + i2 + "] is a repetition")
            }
            Locale locale2 = (Locale) locale.clone()
            localeArr2[i2] = locale2
            sb.append(LocaleHelper.toLanguageTag(locale2))
            if (i2 < localeArr.length - 1) {
                sb.append(',')
            }
            hashSet.add(locale2)
            i = i2 + 1
        }
    }

    private fun computeFirstMatch(Collection collection, Boolean z) {
        Int iComputeFirstMatchIndex = computeFirstMatchIndex(collection, z)
        if (iComputeFirstMatchIndex == -1) {
            return null
        }
        return this.mList[iComputeFirstMatchIndex]
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun computeFirstMatchIndex(java.util.Collection r6, Boolean r7) {
        /*
            r5 = this
            r1 = 2147483647(0x7fffffff, Float:NaN)
            r3 = 0
            java.util.Array<Locale> r0 = r5.mList
            Int r0 = r0.length
            r2 = 1
            if (r0 != r2) goto Lc
            r2 = r3
        Lb:
            return r2
        Lc:
            java.util.Array<Locale> r0 = r5.mList
            Int r0 = r0.length
            if (r0 != 0) goto L13
            r2 = -1
            goto Lb
        L13:
            if (r7 == 0) goto L48
            java.util.Locale r0 = android.support.v4.os.LocaleListHelper.EN_LATN
            Int r0 = r5.findFirstMatchIndex(r0)
            if (r0 != 0) goto L1f
            r2 = r3
            goto Lb
        L1f:
            if (r0 >= r1) goto L48
        L21:
            java.util.Iterator r4 = r6.iterator()
            r2 = r0
        L26:
            Boolean r0 = r4.hasNext()
            if (r0 == 0) goto L42
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            java.util.Locale r0 = android.support.v4.os.LocaleHelper.forLanguageTag(r0)
            Int r0 = r5.findFirstMatchIndex(r0)
            if (r0 != 0) goto L3e
            r2 = r3
            goto Lb
        L3e:
            if (r0 >= r2) goto L46
        L40:
            r2 = r0
            goto L26
        L42:
            if (r2 != r1) goto Lb
            r2 = r3
            goto Lb
        L46:
            r0 = r2
            goto L40
        L48:
            r0 = r1
            goto L21
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.os.LocaleListHelper.computeFirstMatchIndex(java.util.Collection, Boolean):Int")
    }

    private fun findFirstMatchIndex(Locale locale) {
        for (Int i = 0; i < this.mList.length; i++) {
            if (matchScore(locale, this.mList[i]) > 0) {
                return i
            }
        }
        return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static LocaleListHelper forLanguageTags(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList()
        }
        Array<String> strArrSplit = str.split(",", -1)
        Array<Locale> localeArr = new Locale[strArrSplit.length]
        for (Int i = 0; i < localeArr.length; i++) {
            localeArr[i] = LocaleHelper.forLanguageTag(strArrSplit[i])
        }
        return LocaleListHelper(localeArr)
    }

    @Size(min = 1)
    @NonNull
    static LocaleListHelper getAdjustedDefault() {
        LocaleListHelper localeListHelper
        getDefault()
        synchronized (sLock) {
            localeListHelper = sDefaultAdjustedLocaleList
        }
        return localeListHelper
    }

    @Size(min = 1)
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static LocaleListHelper getDefault() {
        LocaleListHelper localeListHelper
        Locale locale = Locale.getDefault()
        synchronized (sLock) {
            if (locale.equals(sLastDefaultLocale)) {
                localeListHelper = sDefaultLocaleList
            } else {
                sLastDefaultLocale = locale
                if (sDefaultLocaleList == null || !locale.equals(sDefaultLocaleList.get(0))) {
                    LocaleListHelper localeListHelper2 = LocaleListHelper(locale, sLastExplicitlySetLocaleList)
                    sDefaultLocaleList = localeListHelper2
                    sDefaultAdjustedLocaleList = localeListHelper2
                    localeListHelper = sDefaultLocaleList
                } else {
                    localeListHelper = sDefaultLocaleList
                }
            }
        }
        return localeListHelper
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static LocaleListHelper getEmptyLocaleList() {
        return sEmptyLocaleList
    }

    private fun getLikelyScript(Locale locale) {
        if (Build.VERSION.SDK_INT < 21) {
            return ""
        }
        String script = locale.getScript()
        return !script.isEmpty() ? script : ""
    }

    private fun isPseudoLocale(String str) {
        return STRING_EN_XA.equals(str) || STRING_AR_XB.equals(str)
    }

    private fun isPseudoLocale(Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static Boolean isPseudoLocalesOnly(@Nullable Array<String> strArr) {
        if (strArr == null) {
            return true
        }
        if (strArr.length > 3) {
            return false
        }
        for (String str : strArr) {
            if (!str.isEmpty() && !isPseudoLocale(str)) {
                return false
            }
        }
        return true
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 1)
    private fun matchScore(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return 1
        }
        if (!locale.getLanguage().equals(locale2.getLanguage())) {
            return 0
        }
        if (isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return 0
        }
        String likelyScript = getLikelyScript(locale)
        if (!likelyScript.isEmpty()) {
            return !likelyScript.equals(getLikelyScript(locale2)) ? 0 : 1
        }
        String country = locale.getCountry()
        return (country.isEmpty() || country.equals(locale2.getCountry())) ? 1 : 0
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static Unit setDefault(@Size(min = 1) @NonNull LocaleListHelper localeListHelper) {
        setDefault(localeListHelper, 0)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static Unit setDefault(@Size(min = 1) @NonNull LocaleListHelper localeListHelper, Int i) {
        if (localeListHelper == null) {
            throw NullPointerException("locales is null")
        }
        if (localeListHelper.isEmpty()) {
            throw IllegalArgumentException("locales is empty")
        }
        synchronized (sLock) {
            Locale locale = localeListHelper.get(i)
            sLastDefaultLocale = locale
            Locale.setDefault(locale)
            sLastExplicitlySetLocaleList = localeListHelper
            sDefaultLocaleList = localeListHelper
            if (i == 0) {
                sDefaultAdjustedLocaleList = sDefaultLocaleList
            } else {
                sDefaultAdjustedLocaleList = LocaleListHelper(sLastDefaultLocale, sDefaultLocaleList)
            }
        }
    }

    public final Boolean equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (!(obj is LocaleListHelper)) {
            return false
        }
        Array<Locale> localeArr = ((LocaleListHelper) obj).mList
        if (this.mList.length != localeArr.length) {
            return false
        }
        for (Int i = 0; i < this.mList.length; i++) {
            if (!this.mList[i].equals(localeArr[i])) {
                return false
            }
        }
        return true
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Locale get(Int i) {
        if (i < 0 || i >= this.mList.length) {
            return null
        }
        return this.mList[i]
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Locale getFirstMatch(Array<String> strArr) {
        return computeFirstMatch(Arrays.asList(strArr), false)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Int getFirstMatchIndex(Array<String> strArr) {
        return computeFirstMatchIndex(Arrays.asList(strArr), false)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Int getFirstMatchIndexWithEnglishSupported(Collection collection) {
        return computeFirstMatchIndex(collection, true)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Int getFirstMatchIndexWithEnglishSupported(Array<String> strArr) {
        return getFirstMatchIndexWithEnglishSupported(Arrays.asList(strArr))
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Locale getFirstMatchWithEnglishSupported(Array<String> strArr) {
        return computeFirstMatch(Arrays.asList(strArr), true)
    }

    public final Int hashCode() {
        Int iHashCode = 1
        for (Int i = 0; i < this.mList.length; i++) {
            iHashCode = (iHashCode * 31) + this.mList[i].hashCode()
        }
        return iHashCode
    }

    @IntRange(from = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Int indexOf(Locale locale) {
        for (Int i = 0; i < this.mList.length; i++) {
            if (this.mList[i].equals(locale)) {
                return i
            }
        }
        return -1
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Boolean isEmpty() {
        return this.mList.length == 0
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final Int size() {
        return this.mList.length
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    final String toLanguageTags() {
        return this.mStringRepresentation
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append("[")
        for (Int i = 0; i < this.mList.length; i++) {
            sb.append(this.mList[i])
            if (i < this.mList.length - 1) {
                sb.append(',')
            }
        }
        sb.append("]")
        return sb.toString()
    }
}
