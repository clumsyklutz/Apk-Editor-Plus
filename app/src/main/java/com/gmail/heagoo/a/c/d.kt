package com.gmail.heagoo.a.c

import java.io.FilterOutputStream
import java.io.IOException
import java.io.OutputStream
import java.security.Signature
import java.security.SignatureException

final class d extends FilterOutputStream {

    /* renamed from: a, reason: collision with root package name */
    private Signature f749a

    /* renamed from: b, reason: collision with root package name */
    private Int f750b

    constructor(OutputStream outputStream, Signature signature) {
        super(outputStream)
        this.f749a = signature
        this.f750b = 0
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final Unit write(Int i) throws SignatureException, IOException {
        try {
            this.f749a.update((Byte) i)
            super.write(i)
            this.f750b++
        } catch (SignatureException e) {
            throw IOException("SignatureException: " + e)
        }
    }
}
