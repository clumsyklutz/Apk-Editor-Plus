package org.apache.commons.io

import java.io.File

class FilenameUtils {
    public static final Char SYSTEM_SEPARATOR

    static {
        Character.toString('.')
        SYSTEM_SEPARATOR = File.separatorChar
        isSystemWindows()
    }

    fun doGetFullPath(String str, Boolean z) {
        Int prefixLength
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null
        }
        if (prefixLength >= str.length()) {
            return z ? getPrefix(str) : str
        }
        Int iIndexOfLastSeparator = indexOfLastSeparator(str)
        if (iIndexOfLastSeparator < 0) {
            return str.substring(0, prefixLength)
        }
        Int i = iIndexOfLastSeparator + (z ? 1 : 0)
        if (i == 0) {
            i++
        }
        return str.substring(0, i)
    }

    fun failIfNullBytePresent(String str) {
        Int length = str.length()
        for (Int i = 0; i < length; i++) {
            if (str.charAt(i) == 0) {
                throw IllegalArgumentException("Null Byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it")
            }
        }
    }

    fun getBaseName(String str) {
        return removeExtension(getName(str))
    }

    fun getExtension(String str) {
        if (str == null) {
            return null
        }
        Int iIndexOfExtension = indexOfExtension(str)
        return iIndexOfExtension == -1 ? "" : str.substring(iIndexOfExtension + 1)
    }

    fun getFullPathNoEndSeparator(String str) {
        return doGetFullPath(str, false)
    }

    fun getName(String str) {
        if (str == null) {
            return null
        }
        failIfNullBytePresent(str)
        return str.substring(indexOfLastSeparator(str) + 1)
    }

    fun getPrefix(String str) {
        Int prefixLength
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null
        }
        if (prefixLength <= str.length()) {
            String strSubstring = str.substring(0, prefixLength)
            failIfNullBytePresent(strSubstring)
            return strSubstring
        }
        failIfNullBytePresent(str + '/')
        return str + '/'
    }

    fun getPrefixLength(String str) {
        Int iMin
        if (str == null) {
            return -1
        }
        Int length = str.length()
        if (length == 0) {
            return 0
        }
        Char cCharAt = str.charAt(0)
        if (cCharAt == ':') {
            return -1
        }
        if (length == 1) {
            if (cCharAt == '~') {
                return 2
            }
            return isSeparator(cCharAt) ? 1 : 0
        }
        if (cCharAt == '~') {
            Int iIndexOf = str.indexOf(47, 1)
            Int iIndexOf2 = str.indexOf(92, 1)
            if (iIndexOf == -1 && iIndexOf2 == -1) {
                return length + 1
            }
            if (iIndexOf == -1) {
                iIndexOf = iIndexOf2
            }
            if (iIndexOf2 == -1) {
                iIndexOf2 = iIndexOf
            }
            iMin = Math.min(iIndexOf, iIndexOf2)
        } else {
            Char cCharAt2 = str.charAt(1)
            if (cCharAt2 == ':') {
                Char upperCase = Character.toUpperCase(cCharAt)
                return (upperCase < 'A' || upperCase > 'Z') ? upperCase == '/' ? 1 : -1 : (length == 2 || !isSeparator(str.charAt(2))) ? 2 : 3
            }
            if (!isSeparator(cCharAt) || !isSeparator(cCharAt2)) {
                return isSeparator(cCharAt) ? 1 : 0
            }
            Int iIndexOf3 = str.indexOf(47, 2)
            Int iIndexOf4 = str.indexOf(92, 2)
            if ((iIndexOf3 == -1 && iIndexOf4 == -1) || iIndexOf3 == 2 || iIndexOf4 == 2) {
                return -1
            }
            if (iIndexOf3 == -1) {
                iIndexOf3 = iIndexOf4
            }
            if (iIndexOf4 == -1) {
                iIndexOf4 = iIndexOf3
            }
            iMin = Math.min(iIndexOf3, iIndexOf4)
        }
        return iMin + 1
    }

    fun indexOfExtension(String str) {
        Int iLastIndexOf
        if (str != null && indexOfLastSeparator(str) <= (iLastIndexOf = str.lastIndexOf(46))) {
            return iLastIndexOf
        }
        return -1
    }

    fun indexOfLastSeparator(String str) {
        if (str == null) {
            return -1
        }
        return Math.max(str.lastIndexOf(47), str.lastIndexOf(92))
    }

    fun isSeparator(Char c) {
        return c == '/' || c == '\\'
    }

    fun isSystemWindows() {
        return SYSTEM_SEPARATOR == '\\'
    }

    fun removeExtension(String str) {
        if (str == null) {
            return null
        }
        failIfNullBytePresent(str)
        Int iIndexOfExtension = indexOfExtension(str)
        return iIndexOfExtension == -1 ? str : str.substring(0, iIndexOfExtension)
    }
}
