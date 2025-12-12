package com.gmail.heagoo.common

import java.io.BufferedReader
import java.io.FileReader
import java.util.ArrayList
import java.util.List

class w {

    /* renamed from: a, reason: collision with root package name */
    private List f1476a = ArrayList()

    constructor(String str) throws Throwable {
        BufferedReader bufferedReader
        try {
            bufferedReader = BufferedReader(FileReader(str))
        } catch (Throwable th) {
            th = th
            bufferedReader = null
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                this.f1476a.add(line)
            }
            bufferedReader.close()
        } catch (Throwable th2) {
            th = th2
            if (bufferedReader != null) {
                bufferedReader.close()
            }
            throw th
        }
    }

    public final List a() {
        return this.f1476a
    }

    public final String b() {
        StringBuilder sb = StringBuilder()
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.f1476a.size()) {
                return sb.toString()
            }
            sb.append((String) this.f1476a.get(i2))
            if (i2 != this.f1476a.size() - 1) {
                sb.append("\n")
            }
            i = i2 + 1
        }
    }
}
