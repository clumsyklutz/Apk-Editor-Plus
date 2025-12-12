package org.jf.dexlib2

import org.jf.util.ExceptionWithContext

class AnnotationVisibility {
    public static Array<String> NAMES = {"build", "runtime", "system"}

    fun getVisibility(String str) {
        String lowerCase = str.toLowerCase()
        if (lowerCase.equals("build")) {
            return 0
        }
        if (lowerCase.equals("runtime")) {
            return 1
        }
        if (lowerCase.equals("system")) {
            return 2
        }
        throw ExceptionWithContext("Invalid annotation visibility: %s", lowerCase)
    }

    fun getVisibility(Int i) {
        if (i >= 0) {
            Array<String> strArr = NAMES
            if (i < strArr.length) {
                return strArr[i]
            }
        }
        throw ExceptionWithContext("Invalid annotation visibility %d", Integer.valueOf(i))
    }
}
