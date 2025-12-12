package com.android.apksig.internal.asn1.ber

import java.nio.ByteBuffer

class BerDataValue {
    public final Boolean mConstructed
    public final ByteBuffer mEncoded
    public final ByteBuffer mEncodedContents
    public final Int mTagClass
    public final Int mTagNumber

    public static final class ParsedValueReader implements BerDataValueReader {
        public final BerDataValue mValue
        public Boolean mValueOutput

        constructor(BerDataValue berDataValue) {
            this.mValue = berDataValue
        }

        @Override // com.android.apksig.internal.asn1.ber.BerDataValueReader
        fun readDataValue() throws BerDataValueFormatException {
            if (this.mValueOutput) {
                return null
            }
            this.mValueOutput = true
            return this.mValue
        }
    }

    constructor(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, Int i, Boolean z, Int i2) {
        this.mEncoded = byteBuffer
        this.mEncodedContents = byteBuffer2
        this.mTagClass = i
        this.mConstructed = z
        this.mTagNumber = i2
    }

    fun contentsReader() {
        return ByteBufferBerDataValueReader(getEncodedContents())
    }

    fun dataValueReader() {
        return ParsedValueReader(this)
    }

    fun getEncoded() {
        return this.mEncoded.slice()
    }

    fun getEncodedContents() {
        return this.mEncodedContents.slice()
    }

    fun getTagClass() {
        return this.mTagClass
    }

    fun getTagNumber() {
        return this.mTagNumber
    }

    fun isConstructed() {
        return this.mConstructed
    }
}
