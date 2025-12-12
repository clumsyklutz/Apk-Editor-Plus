package com.gmail.heagoo.b

import java.util.Comparator

class b implements Comparator {
    private fun a(String str, String str2) {
        while (!str.isEmpty()) {
            if (str2.isEmpty()) {
                return 1
            }
            Char cCharAt = str.charAt(0)
            Char cCharAt2 = str2.charAt(0)
            if (cCharAt != cCharAt2) {
                if (!a(cCharAt) || !a(cCharAt2)) {
                    return cCharAt < cCharAt2 ? -1 : 1
                }
                Char lowerCase = Character.toLowerCase(cCharAt)
                Char lowerCase2 = Character.toLowerCase(cCharAt2)
                return lowerCase == lowerCase2 ? cCharAt < cCharAt2 ? -1 : 1 : lowerCase < lowerCase2 ? -1 : 1
            }
            str = str.substring(1)
            str2 = str2.substring(1)
        }
        return str2.isEmpty() ? 0 : -1
    }

    private fun a(Char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z')
    }

    @Override // java.util.Comparator
    public final Int compare(Object obj, Object obj2) {
        a aVar = (a) obj
        a aVar2 = (a) obj2
        if (aVar.f1425b) {
            if (aVar2.f1425b) {
                return a(aVar.f1424a, aVar2.f1424a)
            }
            return -1
        }
        if (aVar2.f1425b) {
            return 1
        }
        return a(aVar.f1424a, aVar2.f1424a)
    }
}
