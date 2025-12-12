package com.android.apksig.internal.apk

class ApkSupportedSignature {
    public final SignatureAlgorithm algorithm
    public final Array<Byte> signature

    constructor(SignatureAlgorithm signatureAlgorithm, Array<Byte> bArr) {
        this.algorithm = signatureAlgorithm
        this.signature = bArr
    }
}
