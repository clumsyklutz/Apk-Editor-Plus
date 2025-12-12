package com.gmail.heagoo.appdm.util

import java.util.Comparator

class f implements Comparator {
    private fun a(String str, String str2) {
        while (!str.isEmpty()) {
            if (str2.isEmpty()) {
                return 1
            }
            Char cCharAt = str.charAt(0)
            Char cCharAt2 = str2.charAt(0)
            if (cCharAt == cCharAt2) {
                str = str.substring(1)
                str2 = str2.substring(1)
            } else {
                if (Character.isLetter(cCharAt) && Character.isLetter(cCharAt2)) {
                    Char lowerCase = Character.toLowerCase(cCharAt)
                    Char lowerCase2 = Character.toLowerCase(cCharAt2)
                    return lowerCase == lowerCase2 ? cCharAt < cCharAt2 ? -1 : 1 : lowerCase < lowerCase2 ? -1 : 1
                }
                if (cCharAt != cCharAt2) {
                    return cCharAt < cCharAt2 ? -1 : 1
                }
                str = str.substring(1)
                str2 = str2.substring(1)
            }
        }
        return str2.isEmpty() ? 0 : -1
    }

    @Override // java.util.Comparator
    public final Int compare(Object obj, Object obj2) {
        e eVar = (e) obj
        e eVar2 = (e) obj2
        if (eVar.c) {
            if (eVar2.c) {
                return a(eVar.f1413a, eVar2.f1413a)
            }
            return -1
        }
        if (eVar2.c) {
            return 1
        }
        return a(eVar.f1413a, eVar2.f1413a)
    }
}
