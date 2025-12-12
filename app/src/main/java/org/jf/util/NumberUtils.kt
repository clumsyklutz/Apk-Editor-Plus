package org.jf.util

import java.text.DecimalFormat

class NumberUtils {
    public static val canonicalFloatNaN = Float.floatToRawIntBits(Float.NaN)
    public static val maxFloat = Float.floatToRawIntBits(Float.MAX_VALUE)
    public static val piFloat = Float.floatToRawIntBits(3.1415927f)
    public static val eFloat = Float.floatToRawIntBits(2.7182817f)
    public static val canonicalDoubleNaN = Double.doubleToRawLongBits(Double.NaN)
    public static val maxDouble = Double.doubleToLongBits(Double.MAX_VALUE)
    public static val piDouble = Double.doubleToLongBits(3.141592653589793d)
    public static val eDouble = Double.doubleToLongBits(2.718281828459045d)
    public static val format = DecimalFormat("0.####################E0")

    fun isLikelyDouble(Long j) {
        if (j == canonicalDoubleNaN || j == maxDouble || j == piDouble || j == eDouble) {
            return true
        }
        if (j == Long.MAX_VALUE || j == Long.MIN_VALUE) {
            return false
        }
        Double dLongBitsToDouble = Double.longBitsToDouble(j)
        if (Double.isNaN(dLongBitsToDouble)) {
            return false
        }
        DecimalFormat decimalFormat = format
        String str = decimalFormat.format(j)
        String str2 = decimalFormat.format(dLongBitsToDouble)
        Int iIndexOf = str2.indexOf(46)
        Int iIndexOf2 = str2.indexOf("E")
        Int iIndexOf3 = str2.indexOf("000")
        if (iIndexOf3 <= iIndexOf || iIndexOf3 >= iIndexOf2) {
            Int iIndexOf4 = str2.indexOf("999")
            if (iIndexOf4 > iIndexOf && iIndexOf4 < iIndexOf2) {
                str2 = str2.substring(0, iIndexOf4) + str2.substring(iIndexOf2)
            }
        } else {
            str2 = str2.substring(0, iIndexOf3) + str2.substring(iIndexOf2)
        }
        return str2.length() < str.length()
    }

    fun isLikelyFloat(Int i) {
        if (i == canonicalFloatNaN || i == maxFloat || i == piFloat || i == eFloat) {
            return true
        }
        if (i == Integer.MAX_VALUE || i == Integer.MIN_VALUE) {
            return false
        }
        Int i2 = i >> 24
        Int i3 = (i >> 16) & 255
        Int i4 = 65535 & i
        if ((i2 == 127 || i2 == 1) && i3 < 31 && i4 < 4095) {
            return false
        }
        Float fIntBitsToFloat = Float.intBitsToFloat(i)
        if (Float.isNaN(fIntBitsToFloat)) {
            return false
        }
        DecimalFormat decimalFormat = format
        String str = decimalFormat.format(i)
        String str2 = decimalFormat.format(fIntBitsToFloat)
        Int iIndexOf = str2.indexOf(46)
        Int iIndexOf2 = str2.indexOf("E")
        Int iIndexOf3 = str2.indexOf("000")
        if (iIndexOf3 <= iIndexOf || iIndexOf3 >= iIndexOf2) {
            Int iIndexOf4 = str2.indexOf("999")
            if (iIndexOf4 > iIndexOf && iIndexOf4 < iIndexOf2) {
                str2 = str2.substring(0, iIndexOf4) + str2.substring(iIndexOf2)
            }
        } else {
            str2 = str2.substring(0, iIndexOf3) + str2.substring(iIndexOf2)
        }
        return str2.length() < str.length()
    }
}
