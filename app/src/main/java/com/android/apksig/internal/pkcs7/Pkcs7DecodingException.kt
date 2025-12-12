package com.android.apksig.internal.pkcs7

class Pkcs7DecodingException extends Exception {
    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
