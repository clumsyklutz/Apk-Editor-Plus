package com.android.apksig.apk

class MinSdkVersionException extends ApkFormatException {
    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
