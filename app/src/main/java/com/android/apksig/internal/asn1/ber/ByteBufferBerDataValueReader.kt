package com.android.apksig.internal.asn1.ber

import com.gmail.heagoo.neweditor.Token
import java.nio.ByteBuffer

class ByteBufferBerDataValueReader implements BerDataValueReader {
    public final ByteBuffer mBuf

    constructor(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw NullPointerException("buf == null")
        }
        this.mBuf = byteBuffer
    }

    @Override // com.android.apksig.internal.asn1.ber.BerDataValueReader
    fun readDataValue() throws BerDataValueFormatException {
        Int iPosition
        Int iSkipConstructedIndefiniteLengthContents
        Int iPosition2 = this.mBuf.position()
        if (!this.mBuf.hasRemaining()) {
            return null
        }
        Byte b2 = this.mBuf.get()
        Int tagNumber = readTagNumber(b2)
        Boolean zIsConstructed = BerEncoding.isConstructed(b2)
        if (!this.mBuf.hasRemaining()) {
            throw BerDataValueFormatException("Missing length")
        }
        Int i = this.mBuf.get() & 255
        if ((i & 128) == 0) {
            iSkipConstructedIndefiniteLengthContents = readShortFormLength(i)
            iPosition = this.mBuf.position() - iPosition2
            skipDefiniteLengthContents(iSkipConstructedIndefiniteLengthContents)
        } else if (i != 128) {
            iSkipConstructedIndefiniteLengthContents = readLongFormLength(i)
            iPosition = this.mBuf.position() - iPosition2
            skipDefiniteLengthContents(iSkipConstructedIndefiniteLengthContents)
        } else {
            iPosition = this.mBuf.position() - iPosition2
            iSkipConstructedIndefiniteLengthContents = zIsConstructed ? skipConstructedIndefiniteLengthContents() : skipPrimitiveIndefiniteLengthContents()
        }
        Int iPosition3 = this.mBuf.position()
        this.mBuf.position(iPosition2)
        Int iLimit = this.mBuf.limit()
        this.mBuf.limit(iPosition3)
        ByteBuffer byteBufferSlice = this.mBuf.slice()
        ByteBuffer byteBuffer = this.mBuf
        byteBuffer.position(byteBuffer.limit())
        this.mBuf.limit(iLimit)
        byteBufferSlice.position(iPosition)
        byteBufferSlice.limit(iPosition + iSkipConstructedIndefiniteLengthContents)
        ByteBuffer byteBufferSlice2 = byteBufferSlice.slice()
        byteBufferSlice.clear()
        return BerDataValue(byteBufferSlice, byteBufferSlice2, BerEncoding.getTagClass(b2), zIsConstructed, tagNumber)
    }

    public final Int readHighTagNumber() throws BerDataValueFormatException {
        Int i = 0
        while (this.mBuf.hasRemaining()) {
            Byte b2 = this.mBuf.get()
            if (i > 16777215) {
                throw BerDataValueFormatException("Tag number too large")
            }
            i = (i << 7) | (b2 & Token.END)
            if ((b2 & 128) == 0) {
                return i
            }
        }
        throw BerDataValueFormatException("Truncated tag number")
    }

    public final Int readLongFormLength(Int i) throws BerDataValueFormatException {
        Int i2 = i & 127
        if (i2 > 4) {
            throw BerDataValueFormatException("Length too large: " + i2 + " bytes")
        }
        Int i3 = 0
        for (Int i4 = 0; i4 < i2; i4++) {
            if (!this.mBuf.hasRemaining()) {
                throw BerDataValueFormatException("Truncated length")
            }
            Byte b2 = this.mBuf.get()
            if (i3 > 8388607) {
                throw BerDataValueFormatException("Length too large")
            }
            i3 = (i3 << 8) | (b2 & 255)
        }
        return i3
    }

    public final Int readShortFormLength(Int i) {
        return i & 127
    }

    public final Int readTagNumber(Byte b2) throws BerDataValueFormatException {
        Int tagNumber = BerEncoding.getTagNumber(b2)
        return tagNumber == 31 ? readHighTagNumber() : tagNumber
    }

    public final Int skipConstructedIndefiniteLengthContents() throws BerDataValueFormatException {
        Int iPosition = this.mBuf.position()
        while (this.mBuf.hasRemaining()) {
            if (this.mBuf.remaining() > 1) {
                ByteBuffer byteBuffer = this.mBuf
                if (byteBuffer.getShort(byteBuffer.position()) == 0) {
                    Int iPosition2 = this.mBuf.position() - iPosition
                    ByteBuffer byteBuffer2 = this.mBuf
                    byteBuffer2.position(byteBuffer2.position() + 2)
                    return iPosition2
                }
            }
            readDataValue()
        }
        throw BerDataValueFormatException("Truncated indefinite-length contents: " + (this.mBuf.position() - iPosition) + " bytes read")
    }

    public final Unit skipDefiniteLengthContents(Int i) throws BerDataValueFormatException {
        if (this.mBuf.remaining() >= i) {
            ByteBuffer byteBuffer = this.mBuf
            byteBuffer.position(byteBuffer.position() + i)
            return
        }
        throw BerDataValueFormatException("Truncated contents. Need: " + i + " bytes, available: " + this.mBuf.remaining())
    }

    public final Int skipPrimitiveIndefiniteLengthContents() throws BerDataValueFormatException {
        Int i = 0
        while (true) {
            Boolean z = false
            while (this.mBuf.hasRemaining()) {
                Byte b2 = this.mBuf.get()
                i++
                if (i < 0) {
                    throw BerDataValueFormatException("Indefinite-length contents too Long")
                }
                if (b2 == 0) {
                    if (z) {
                        return i - 2
                    }
                    z = true
                }
            }
            throw BerDataValueFormatException("Truncated indefinite-length contents: " + i + " bytes read")
        }
    }
}
