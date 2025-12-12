package org.jf.dexlib2

import com.google.common.collect.Maps
import java.util.HashMap
import org.jf.util.ExceptionWithContext

class VerificationError {
    public static final HashMap<String, Integer> verificationErrorNames

    static {
        HashMap<String, Integer> mapNewHashMap = Maps.newHashMap()
        verificationErrorNames = mapNewHashMap
        mapNewHashMap.put("generic-error", 1)
        mapNewHashMap.put("no-such-class", 2)
        mapNewHashMap.put("no-such-field", 3)
        mapNewHashMap.put("no-such-method", 4)
        mapNewHashMap.put("illegal-class-access", 5)
        mapNewHashMap.put("illegal-field-access", 6)
        mapNewHashMap.put("illegal-method-access", 7)
        mapNewHashMap.put("class-change-error", 8)
        mapNewHashMap.put("instantiation-error", 9)
    }

    fun getVerificationError(String str) {
        Integer num = verificationErrorNames.get(str)
        if (num != null) {
            return num.intValue()
        }
        throw ExceptionWithContext("Invalid verification error: %s", str)
    }

    fun getVerificationErrorName(Int i) {
        switch (i) {
            case 1:
                return "generic-error"
            case 2:
                return "no-such-class"
            case 3:
                return "no-such-field"
            case 4:
                return "no-such-method"
            case 5:
                return "illegal-class-access"
            case 6:
                return "illegal-field-access"
            case 7:
                return "illegal-method-access"
            case 8:
                return "class-change-error"
            case 9:
                return "instantiation-error"
            default:
                return null
        }
    }

    fun isValidVerificationError(Int i) {
        return i > 0 && i < 10
    }
}
