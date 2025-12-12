package com.android.apksig.internal.apk

class SignatureNotFoundException extends Exception {
    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
