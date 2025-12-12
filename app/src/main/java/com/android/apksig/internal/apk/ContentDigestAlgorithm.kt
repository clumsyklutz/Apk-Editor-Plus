package com.android.apksig.internal.apk

public enum ContentDigestAlgorithm {
    CHUNKED_SHA256(1, "SHA-256", 32),
    CHUNKED_SHA512(2, "SHA-512", 64),
    VERITY_CHUNKED_SHA256(3, "SHA-256", 32),
    SHA256(4, "SHA-256", 32)

    public final Int mChunkDigestOutputSizeBytes
    public final Int mId
    public final String mJcaMessageDigestAlgorithm

    ContentDigestAlgorithm(Int i, String str, Int i2) {
        this.mId = i
        this.mJcaMessageDigestAlgorithm = str
        this.mChunkDigestOutputSizeBytes = i2
    }

    fun getChunkDigestOutputSizeBytes() {
        return this.mChunkDigestOutputSizeBytes
    }

    fun getId() {
        return this.mId
    }

    fun getJcaMessageDigestAlgorithm() {
        return this.mJcaMessageDigestAlgorithm
    }
}
