package android.support.v4.text

import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.appcompat.R
import android.text.TextUtils
import java.util.Locale

class TextUtilsCompat {
    private static val ARAB_SCRIPT_SUBTAG = "Arab"
    private static val HEBR_SCRIPT_SUBTAG = "Hebr"
    private static val ROOT = Locale("", "")

    private constructor() {
    }

    private fun getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
        switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
            case 1:
            case 2:
                return 1
            default:
                return 0
        }
    }

    fun getLayoutDirectionFromLocale(@Nullable Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale(locale)
        }
        if (locale != null && !locale.equals(ROOT)) {
            String strMaximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale)
            if (strMaximizeAndGetScript == null) {
                return getLayoutDirectionFromFirstChar(locale)
            }
            if (strMaximizeAndGetScript.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || strMaximizeAndGetScript.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) {
                return 1
            }
        }
        return 0
    }

    @NonNull
    fun htmlEncode(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.htmlEncode(str)
        }
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < str.length(); i++) {
            Char cCharAt = str.charAt(i)
            switch (cCharAt) {
                case '\"':
                    sb.append("&quot;")
                    break
                case '&':
                    sb.append("&amp;")
                    break
                case '\'':
                    sb.append("&#39;")
                    break
                case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                    sb.append("&lt;")
                    break
                case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
                    sb.append("&gt;")
                    break
                default:
                    sb.append(cCharAt)
                    break
            }
        }
        return sb.toString()
    }
}
