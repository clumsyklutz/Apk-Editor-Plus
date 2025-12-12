package android.support.v4.os

import android.support.annotation.RestrictTo
import java.util.Locale

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
final class LocaleHelper {
    private constructor() {
    }

    static Locale forLanguageTag(String str) {
        if (str.contains("-")) {
            Array<String> strArrSplit = str.split("-", -1)
            if (strArrSplit.length > 2) {
                return Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2])
            }
            if (strArrSplit.length > 1) {
                return Locale(strArrSplit[0], strArrSplit[1])
            }
            if (strArrSplit.length == 1) {
                return Locale(strArrSplit[0])
            }
        } else {
            if (!str.contains("_")) {
                return Locale(str)
            }
            Array<String> strArrSplit2 = str.split("_", -1)
            if (strArrSplit2.length > 2) {
                return Locale(strArrSplit2[0], strArrSplit2[1], strArrSplit2[2])
            }
            if (strArrSplit2.length > 1) {
                return Locale(strArrSplit2[0], strArrSplit2[1])
            }
            if (strArrSplit2.length == 1) {
                return Locale(strArrSplit2[0])
            }
        }
        throw IllegalArgumentException("Can not parse language tag: [" + str + "]")
    }

    static String toLanguageTag(Locale locale) {
        StringBuilder sb = StringBuilder()
        sb.append(locale.getLanguage())
        String country = locale.getCountry()
        if (country != null && !country.isEmpty()) {
            sb.append("-")
            sb.append(locale.getCountry())
        }
        return sb.toString()
    }
}
