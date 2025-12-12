package com.android.apksig.internal.asn1

class Asn1DecodingException extends Exception {
    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
