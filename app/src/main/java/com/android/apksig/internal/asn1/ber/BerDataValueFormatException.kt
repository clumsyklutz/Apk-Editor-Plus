package com.android.apksig.internal.asn1.ber

class BerDataValueFormatException extends Exception {
    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
