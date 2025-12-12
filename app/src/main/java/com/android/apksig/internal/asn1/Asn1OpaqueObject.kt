package com.android.apksig.internal.asn1

import java.nio.ByteBuffer

class Asn1OpaqueObject {
    public final ByteBuffer mEncoded

    constructor(ByteBuffer byteBuffer) {
        this.mEncoded = byteBuffer.slice()
    }

    constructor(Array<Byte> bArr) {
        this.mEncoded = ByteBuffer.wrap(bArr)
    }

    fun getEncoded() {
        return this.mEncoded.slice()
    }
}
