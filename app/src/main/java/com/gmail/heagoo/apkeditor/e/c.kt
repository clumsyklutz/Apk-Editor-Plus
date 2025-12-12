package com.gmail.heagoo.apkeditor.e

import java.io.BufferedReader
import java.io.Reader

class c extends BufferedReader {

    /* renamed from: a, reason: collision with root package name */
    private Int f989a

    constructor(Reader reader) {
        super(reader)
        this.f989a = 0
    }

    public final Int a() {
        return this.f989a
    }

    @Override // java.io.BufferedReader
    public final String readLine() {
        this.f989a++
        return super.readLine()
    }
}
