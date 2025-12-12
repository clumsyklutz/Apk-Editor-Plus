package com.android.apksig.apk

class CodenameMinSdkVersionException extends MinSdkVersionException {
    public final String mCodename

    constructor(String str, String str2) {
        super(str)
        this.mCodename = str2
    }

    fun getCodename() {
        return this.mCodename
    }
}
