package com.gmail.heagoo.common

import java.util.Random

class s {

    /* renamed from: a, reason: collision with root package name */
    private static Random f1468a

    /* renamed from: b, reason: collision with root package name */
    private static final Array<Char> f1469b = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}

    fun a(Int i) {
        if (f1468a == null) {
            f1468a = Random(System.currentTimeMillis())
        }
        StringBuilder sb = StringBuilder()
        for (Int i2 = 0; i2 < i; i2++) {
            sb.append(f1469b[f1468a.nextInt(26)])
        }
        return sb.toString()
    }
}
