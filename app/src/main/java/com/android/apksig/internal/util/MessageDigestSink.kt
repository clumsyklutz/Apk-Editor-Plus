package com.android.apksig.internal.util

import com.android.apksig.util.DataSink
import java.nio.ByteBuffer
import java.security.MessageDigest

class MessageDigestSink implements DataSink {
    public final Array<MessageDigest> mMessageDigests

    constructor(Array<MessageDigest> messageDigestArr) {
        this.mMessageDigests = messageDigestArr
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(ByteBuffer byteBuffer) {
        Int iPosition = byteBuffer.position()
        for (MessageDigest messageDigest : this.mMessageDigests) {
            byteBuffer.position(iPosition)
            messageDigest.update(byteBuffer)
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(Array<Byte> bArr, Int i, Int i2) {
        for (MessageDigest messageDigest : this.mMessageDigests) {
            messageDigest.update(bArr, i, i2)
        }
    }
}
