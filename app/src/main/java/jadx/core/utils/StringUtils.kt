package jadx.core.utils

import androidx.appcompat.R

class StringUtils {
    private constructor() {
    }

    fun escape(String str) {
        Int length = str.length()
        StringBuilder sb = StringBuilder(length)
        for (Int i = 0; i < length; i++) {
            Char cCharAt = str.charAt(i)
            switch (cCharAt) {
                case ' ':
                case '$':
                case ',':
                case '.':
                case '/':
                case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
                case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                    sb.append('_')
                    break
                case '*':
                case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
                case '?':
                case R.styleable.AppCompatTheme_controlBackground /* 93 */:
                    break
                case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                    sb.append('A')
                    break
                default:
                    sb.append(cCharAt)
                    break
            }
        }
        return sb.toString()
    }

    fun escapeResValue(String str) {
        Int length = str.length()
        StringBuilder sb = StringBuilder(length)
        for (Int i = 0; i < length; i++) {
            Char cCharAt = str.charAt(i)
            switch (cCharAt) {
                case '\b':
                    sb.append("\\b")
                    break
                case '\t':
                    sb.append("\\t")
                    break
                case '\n':
                    sb.append("\\n")
                    break
                case '\f':
                    sb.append("\\f")
                    break
                case '\r':
                    sb.append("\\r")
                    break
                case '\"':
                    sb.append("&quot;")
                    break
                case '&':
                    sb.append("&amp;")
                    break
                case '\'':
                    sb.append("&apos;")
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

    fun escapeXML(String str) {
        Int length = str.length()
        StringBuilder sb = StringBuilder(length)
        for (Int i = 0; i < length; i++) {
            Char cCharAt = str.charAt(i)
            switch (cCharAt) {
                case '\"':
                    sb.append("&quot;")
                    break
                case '&':
                    sb.append("&amp;")
                    break
                case '\'':
                    sb.append("&apos;")
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

    private fun processChar(Int i, StringBuilder sb) {
        switch (i) {
            case 8:
                sb.append("\\b")
                break
            case 9:
                sb.append("\\t")
                break
            case 10:
                sb.append("\\n")
                break
            case 12:
                sb.append("\\f")
                break
            case 13:
                sb.append("\\r")
                break
            case 34:
                sb.append("\\\"")
                break
            case 39:
                sb.append('\'')
                break
            case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                sb.append("\\\\")
                break
            default:
                if (32 <= i && i <= 126) {
                    sb.append((Char) i)
                    break
                } else {
                    sb.append("\\u").append(String.format("%04x", Integer.valueOf(i)))
                    break
                }
                break
        }
    }

    fun unescapeChar(Char c) {
        if (c == '\'') {
            return "'\\''"
        }
        StringBuilder sb = StringBuilder()
        processChar(c, sb)
        return "'" + sb.toString() + '\''
    }

    fun unescapeString(String str) {
        Int length = str.length()
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < length; i++) {
            processChar(str.charAt(i) & 65535, sb)
        }
        return "\"" + sb.toString() + '\"'
    }
}
