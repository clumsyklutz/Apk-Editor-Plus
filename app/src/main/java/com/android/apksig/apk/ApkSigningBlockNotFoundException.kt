package com.android.apksig.apk

class ApkSigningBlockNotFoundException extends Exception {
    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
