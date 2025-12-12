package com.google.common.base

import java.util.logging.Level
import java.util.logging.Logger

class Strings {
    fun lenientFormat(String str, Object... objArr) {
        Int iIndexOf
        String strValueOf = String.valueOf(str)
        Int i = 0
        if (objArr == null) {
            objArr = new Array<Object>{"(Array<Object>)null"}
        } else {
            for (Int i2 = 0; i2 < objArr.length; i2++) {
                objArr[i2] = lenientToString(objArr[i2])
            }
        }
        StringBuilder sb = StringBuilder(strValueOf.length() + (objArr.length * 16))
        Int i3 = 0
        while (i < objArr.length && (iIndexOf = strValueOf.indexOf("%s", i3)) != -1) {
            sb.append((CharSequence) strValueOf, i3, iIndexOf)
            sb.append(objArr[i])
            i3 = iIndexOf + 2
            i++
        }
        sb.append((CharSequence) strValueOf, i3, strValueOf.length())
        if (i < objArr.length) {
            sb.append(" [")
            sb.append(objArr[i])
            for (Int i4 = i + 1; i4 < objArr.length; i4++) {
                sb.append(", ")
                sb.append(objArr[i4])
            }
            sb.append(']')
        }
        return sb.toString()
    }

    fun lenientToString(Object obj) {
        if (obj == null) {
            return "null"
        }
        try {
            return obj.toString()
        } catch (Exception e) {
            String name = obj.getClass().getName()
            String hexString = Integer.toHexString(System.identityHashCode(obj))
            StringBuilder sb = StringBuilder(name.length() + 1 + String.valueOf(hexString).length())
            sb.append(name)
            sb.append('@')
            sb.append(hexString)
            String string = sb.toString()
            Logger logger = Logger.getLogger("com.google.common.base.Strings")
            Level level = Level.WARNING
            String strValueOf = String.valueOf(string)
            logger.log(level, strValueOf.length() != 0 ? "Exception during lenientFormat for ".concat(strValueOf) : String("Exception during lenientFormat for "), (Throwable) e)
            String name2 = e.getClass().getName()
            StringBuilder sb2 = StringBuilder(String.valueOf(string).length() + 9 + name2.length())
            sb2.append("<")
            sb2.append(string)
            sb2.append(" threw ")
            sb2.append(name2)
            sb2.append(">")
            return sb2.toString()
        }
    }

    fun repeat(String str, Int i) {
        Preconditions.checkNotNull(str)
        if (i <= 1) {
            Preconditions.checkArgument(i >= 0, "invalid count: %s", i)
            return i == 0 ? "" : str
        }
        Int length = str.length()
        Long j = length * i
        Int i2 = (Int) j
        if (i2 != j) {
            StringBuilder sb = StringBuilder(51)
            sb.append("Required array size too large: ")
            sb.append(j)
            throw ArrayIndexOutOfBoundsException(sb.toString())
        }
        Array<Char> cArr = new Char[i2]
        str.getChars(0, length, cArr, 0)
        while (true) {
            Int i3 = i2 - length
            if (length >= i3) {
                System.arraycopy(cArr, 0, cArr, length, i3)
                return String(cArr)
            }
            System.arraycopy(cArr, 0, cArr, length, length)
            length <<= 1
        }
    }
}
