package android.support.v4.database

import android.text.TextUtils

@Deprecated
class DatabaseUtilsCompat {
    private constructor() {
    }

    @Deprecated
    public static Array<String> appendSelectionArgs(Array<String> strArr, Array<String> strArr2) {
        if (strArr == null || strArr.length == 0) {
            return strArr2
        }
        Array<String> strArr3 = new String[strArr.length + strArr2.length]
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length)
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length)
        return strArr3
    }

    @Deprecated
    fun concatenateWhere(String str, String str2) {
        return TextUtils.isEmpty(str) ? str2 : TextUtils.isEmpty(str2) ? str : "(" + str + ") AND (" + str2 + ")"
    }
}
