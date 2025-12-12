package com.gmail.heagoo.httpserver

import java.io.File
import java.util.Comparator

class a implements Comparator {
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
        File file = (File) obj
        File file2 = (File) obj2
        if (file.isDirectory()) {
            if (file2.isDirectory()) {
                return a(file.getName(), file2.getName())
            }
            return -1
        }
        if (file2.isDirectory()) {
            return 1
        }
        return a(file.getName(), file2.getName())
    }
}
