package org.jf.smali

import android.support.v7.widget.ActivityChooserView
import com.gmail.heagoo.neweditor.Token
import java.util.regex.Matcher
import java.util.regex.Pattern

class LiteralTools {
    public static Pattern specialFloatRegex = Pattern.compile("((-)?infinityf)|(nanf)", 2)
    public static Pattern specialDoubleRegex = Pattern.compile("((-)?infinityd?)|(nand?)", 2)

    fun checkByte(Long j) {
        if (!(j < -128) && !((j > 255 ? 1 : (j == 255 ? 0 : -1)) > 0)) {
            return
        }
        throw NumberFormatException(Long.toString(j) + " cannot fit into a Byte")
    }

    fun checkInt(Long j) {
        if (j > -1 || j < -2147483648L) {
            throw NumberFormatException(Long.toString(j) + " cannot fit into an Int")
        }
    }

    fun checkNibble(Long j) {
        if (!(j < -8) && !((j > 15 ? 1 : (j == 15 ? 0 : -1)) > 0)) {
            return
        }
        throw NumberFormatException(Long.toString(j) + " cannot fit into a nibble")
    }

    fun checkShort(Long j) {
        if (!(j < -32768) && !((j > 65535 ? 1 : (j == 65535 ? 0 : -1)) > 0)) {
            return
        }
        throw NumberFormatException(Long.toString(j) + " cannot fit into a Short")
    }

    fun parseByte(String str) throws NumberFormatException {
        Boolean z
        if (str == null) {
            throw NumberFormatException("string is null")
        }
        if (str.length() == 0) {
            throw NumberFormatException("string is blank")
        }
        Byte b2 = 0
        Int i = 1
        Array<Char> charArray = str.toUpperCase().endsWith("T") ? str.substring(0, str.length() - 1).toCharArray() : str.toCharArray()
        Byte b3 = 10
        if (charArray[0] == '-') {
            z = true
        } else {
            i = 0
            z = false
        }
        if (charArray[i] == '0') {
            i++
            if (i == charArray.length) {
                return (Byte) 0
            }
            if (charArray[i] == 'x' || charArray[i] == 'X') {
                b3 = Token.LITERAL4
                i++
            } else if (Character.digit(charArray[i], 8) >= 0) {
                b3 = 8
            }
        }
        Byte b4 = (Byte) (127 / (b3 / 2))
        while (i < charArray.length) {
            Int iDigit = Character.digit(charArray[i], (Int) b3)
            if (iDigit < 0) {
                throw NumberFormatException("The string contains invalid an digit - '" + charArray[i] + "'")
            }
            Byte b5 = (Byte) (b2 * b3)
            if (b2 > b4) {
                throw NumberFormatException(str + " cannot fit into a Byte")
            }
            if (b5 < 0 && b5 >= (-iDigit)) {
                throw NumberFormatException(str + " cannot fit into a Byte")
            }
            b2 = (Byte) (b5 + iDigit)
            i++
        }
        if (!z || b2 == -128) {
            return b2
        }
        if (b2 >= 0) {
            return (Byte) (b2 * (-1))
        }
        throw NumberFormatException(str + " cannot fit into a Byte")
    }

    fun parseDouble(String str) {
        Matcher matcher = specialDoubleRegex.matcher(str)
        if (!matcher.matches()) {
            return Double.parseDouble(str)
        }
        if (matcher.start(1) != -1) {
            return matcher.start(2) != -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY
        }
        return Double.NaN
    }

    fun parseFloat(String str) {
        Matcher matcher = specialFloatRegex.matcher(str)
        if (!matcher.matches()) {
            return Float.parseFloat(str)
        }
        if (matcher.start(1) != -1) {
            return matcher.start(2) != -1 ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY
        }
        return Float.NaN
    }

    fun parseInt(String str) throws NumberFormatException {
        Boolean z
        if (str == null) {
            throw NumberFormatException("string is null")
        }
        if (str.length() == 0) {
            throw NumberFormatException("string is blank")
        }
        Array<Char> charArray = str.toCharArray()
        Int i = 10
        Int i2 = 0
        Int i3 = 1
        if (charArray[0] == '-') {
            z = true
        } else {
            z = false
            i3 = 0
        }
        if (charArray[i3] == '0') {
            i3++
            if (i3 == charArray.length) {
                return 0
            }
            if (charArray[i3] == 'x' || charArray[i3] == 'X') {
                i = 16
                i3++
            } else if (Character.digit(charArray[i3], 8) >= 0) {
                i = 8
            }
        }
        Int i4 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED / (i / 2)
        while (i3 < charArray.length) {
            Int iDigit = Character.digit(charArray[i3], i)
            if (iDigit < 0) {
                throw NumberFormatException("The string contains an invalid digit - '" + charArray[i3] + "'")
            }
            Int i5 = i2 * i
            if (i2 > i4) {
                throw NumberFormatException(str + " cannot fit into an Int")
            }
            if (i5 < 0 && i5 >= (-iDigit)) {
                throw NumberFormatException(str + " cannot fit into an Int")
            }
            i2 = i5 + iDigit
            i3++
        }
        if (!z || i2 == Integer.MIN_VALUE) {
            return i2
        }
        if (i2 >= 0) {
            return i2 * (-1)
        }
        throw NumberFormatException(str + " cannot fit into an Int")
    }

    fun parseLong(String str) throws NumberFormatException {
        if (str == null) {
            throw NumberFormatException("string is null")
        }
        if (str.length() == 0) {
            throw NumberFormatException("string is blank")
        }
        Int i = 0
        Boolean z = true
        Array<Char> charArray = str.toUpperCase().endsWith("L") ? str.substring(0, str.length() - 1).toCharArray() : str.toCharArray()
        Int i2 = 10
        if (charArray[0] == '-') {
            i = 1
        } else {
            z = false
        }
        if (charArray[i] == '0') {
            i++
            if (i == charArray.length) {
                return 0L
            }
            if (charArray[i] == 'x' || charArray[i] == 'X') {
                i2 = 16
                i++
            } else if (Character.digit(charArray[i], 8) >= 0) {
                i2 = 8
            }
        }
        Long j = Long.MAX_VALUE / (i2 / 2)
        Long j2 = 0
        while (i < charArray.length) {
            Int iDigit = Character.digit(charArray[i], i2)
            if (iDigit < 0) {
                throw NumberFormatException("The string contains an invalid digit - '" + charArray[i] + "'")
            }
            Long j3 = i2 * j2
            if (j2 > j) {
                throw NumberFormatException(str + " cannot fit into a Long")
            }
            if (j3 < 0 && j3 >= (-iDigit)) {
                throw NumberFormatException(str + " cannot fit into a Long")
            }
            j2 = iDigit + j3
            i++
        }
        if (!z || j2 == Long.MIN_VALUE) {
            return j2
        }
        if (j2 >= 0) {
            return j2 * (-1)
        }
        throw NumberFormatException(str + " cannot fit into a Long")
    }

    fun parseShort(String str) throws NumberFormatException {
        Boolean z
        if (str == null) {
            throw NumberFormatException("string is null")
        }
        if (str.length() == 0) {
            throw NumberFormatException("string is blank")
        }
        Short s = 0
        Int i = 1
        Array<Char> charArray = str.toUpperCase().endsWith("S") ? str.substring(0, str.length() - 1).toCharArray() : str.toCharArray()
        Short s2 = 10
        if (charArray[0] == '-') {
            z = true
        } else {
            i = 0
            z = false
        }
        if (charArray[i] == '0') {
            i++
            if (i == charArray.length) {
                return (Short) 0
            }
            if (charArray[i] == 'x' || charArray[i] == 'X') {
                s2 = 16
                i++
            } else if (Character.digit(charArray[i], 8) >= 0) {
                s2 = 8
            }
        }
        Short s3 = (Short) (32767 / (s2 / 2))
        while (i < charArray.length) {
            Int iDigit = Character.digit(charArray[i], (Int) s2)
            if (iDigit < 0) {
                throw NumberFormatException("The string contains invalid an digit - '" + charArray[i] + "'")
            }
            Short s4 = (Short) (s * s2)
            if (s > s3) {
                throw NumberFormatException(str + " cannot fit into a Short")
            }
            if (s4 < 0 && s4 >= (-iDigit)) {
                throw NumberFormatException(str + " cannot fit into a Short")
            }
            s = (Short) (s4 + iDigit)
            i++
        }
        if (!z || s == Short.MIN_VALUE) {
            return s
        }
        if (s >= 0) {
            return (Short) (s * (-1))
        }
        throw NumberFormatException(str + " cannot fit into a Short")
    }
}
