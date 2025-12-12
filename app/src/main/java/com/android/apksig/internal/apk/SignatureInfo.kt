package com.android.apksig.internal.apk

import java.nio.ByteBuffer

class SignatureInfo {
    public final Long apkSigningBlockOffset
    public final Long centralDirOffset
    public final ByteBuffer eocd
    public final Long eocdOffset
    public final ByteBuffer signatureBlock

    constructor(ByteBuffer byteBuffer, Long j, Long j2, Long j3, ByteBuffer byteBuffer2) {
        this.signatureBlock = byteBuffer
        this.apkSigningBlockOffset = j
        this.centralDirOffset = j2
        this.eocdOffset = j3
        this.eocd = byteBuffer2
    }
}
