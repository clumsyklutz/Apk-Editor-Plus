package com.gmail.heagoo.common

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

final class d extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private InputStream f1450a

    /* renamed from: b, reason: collision with root package name */
    private Array<String> f1451b
    private Int c

    constructor(InputStream inputStream, Array<String> strArr, Int i) {
        this.f1450a = inputStream
        this.f1451b = strArr
        this.c = i
    }

    public final Unit a() throws IOException {
        interrupt()
        try {
            this.f1450a.close()
        } catch (IOException e) {
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws IOException {
        Int i
        Array<Char> cArr = new Char[128]
        StringBuilder sb = StringBuilder()
        try {
            InputStreamReader inputStreamReader = InputStreamReader(this.f1450a, "UTF-8")
            do {
                i = inputStreamReader.read(cArr, 0, 128)
                if (i > 0) {
                    sb.append(cArr, 0, i)
                }
            } while (i >= 0);
        } catch (Exception e) {
        }
        this.f1451b[this.c] = sb.toString()
    }
}
