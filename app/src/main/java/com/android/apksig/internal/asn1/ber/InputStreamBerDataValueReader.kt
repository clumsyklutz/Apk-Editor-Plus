package com.android.apksig.internal.asn1.ber

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer

class InputStreamBerDataValueReader implements BerDataValueReader {
    public final InputStream mIn

    public static class RecordingInputStream extends InputStream {
        public final ByteArrayOutputStream mBuf
        public final InputStream mIn

        constructor(InputStream inputStream) {
            this.mIn = inputStream
            this.mBuf = ByteArrayOutputStream()
        }

        @Override // java.io.InputStream
        fun available() throws IOException {
            return super.available()
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        fun close() throws IOException {
            super.close()
        }

        fun getReadByteCount() {
            return this.mBuf.size()
        }

        public Array<Byte> getReadBytes() {
            return this.mBuf.toByteArray()
        }

        @Override // java.io.InputStream
        public synchronized Unit mark(Int i) {
        }

        @Override // java.io.InputStream
        fun markSupported() {
            return false
        }

        @Override // java.io.InputStream
        fun read() throws IOException {
            Int i = this.mIn.read()
            if (i != -1) {
                this.mBuf.write(i)
            }
            return i
        }

        @Override // java.io.InputStream
        fun read(Array<Byte> bArr) throws IOException {
            Int i = this.mIn.read(bArr)
            if (i > 0) {
                this.mBuf.write(bArr, 0, i)
            }
            return i
        }

        @Override // java.io.InputStream
        fun read(Array<Byte> bArr, Int i, Int i2) throws IOException {
            Int i3 = this.mIn.read(bArr, i, i2)
            if (i3 > 0) {
                this.mBuf.write(bArr, i, i3)
            }
            return i3
        }

        @Override // java.io.InputStream
        public synchronized Unit reset() throws IOException {
            throw IOException("mark/reset not supported")
        }

        @Override // java.io.InputStream
        fun skip(Long j) throws IOException {
            if (j <= 0) {
                return this.mIn.skip(j)
            }
            Array<Byte> bArr = new Byte[4096]
            Int i = this.mIn.read(bArr, 0, (Int) Math.min(4096, j))
            if (i > 0) {
                this.mBuf.write(bArr, 0, i)
            }
            if (i < 0) {
                return 0L
            }
            return i
        }
    }

    constructor(InputStream inputStream) {
        if (inputStream == null) {
            throw NullPointerException("in == null")
        }
        this.mIn = inputStream
    }

    fun readDataValue(InputStream inputStream) throws BerDataValueFormatException {
        Int readByteCount
        Int iSkipConstructedIndefiniteLengthContents
        RecordingInputStream recordingInputStream = RecordingInputStream(inputStream)
        try {
            Int i = recordingInputStream.read()
            if (i == -1) {
                return null
            }
            Int tagNumber = readTagNumber(recordingInputStream, i)
            Int i2 = recordingInputStream.read()
            if (i2 == -1) {
                throw BerDataValueFormatException("Missing length")
            }
            Byte b2 = (Byte) i
            Boolean zIsConstructed = BerEncoding.isConstructed(b2)
            if ((i2 & 128) == 0) {
                iSkipConstructedIndefiniteLengthContents = readShortFormLength(i2)
                readByteCount = recordingInputStream.getReadByteCount()
                skipDefiniteLengthContents(recordingInputStream, iSkipConstructedIndefiniteLengthContents)
            } else if ((i2 & 255) != 128) {
                iSkipConstructedIndefiniteLengthContents = readLongFormLength(recordingInputStream, i2)
                readByteCount = recordingInputStream.getReadByteCount()
                skipDefiniteLengthContents(recordingInputStream, iSkipConstructedIndefiniteLengthContents)
            } else {
                readByteCount = recordingInputStream.getReadByteCount()
                iSkipConstructedIndefiniteLengthContents = zIsConstructed ? skipConstructedIndefiniteLengthContents(recordingInputStream) : skipPrimitiveIndefiniteLengthContents(recordingInputStream)
            }
            Array<Byte> readBytes = recordingInputStream.getReadBytes()
            return BerDataValue(ByteBuffer.wrap(readBytes), ByteBuffer.wrap(readBytes, readByteCount, iSkipConstructedIndefiniteLengthContents), BerEncoding.getTagClass(b2), zIsConstructed, tagNumber)
        } catch (IOException e) {
            throw BerDataValueFormatException("Failed to read data value", e)
        }
    }

    fun readHighTagNumber(InputStream inputStream) throws IOException, BerDataValueFormatException {
        Int i
        Int i2 = 0
        do {
            i = inputStream.read()
            if (i == -1) {
                throw BerDataValueFormatException("Truncated tag number")
            }
            if (i2 > 16777215) {
                throw BerDataValueFormatException("Tag number too large")
            }
            i2 = (i2 << 7) | (i & 127)
        } while ((i & 128) != 0);
        return i2
    }

    fun readLongFormLength(InputStream inputStream, Int i) throws IOException, BerDataValueFormatException {
        Int i2 = i & 127
        if (i2 > 4) {
            throw BerDataValueFormatException("Length too large: " + i2 + " bytes")
        }
        Int i3 = 0
        for (Int i4 = 0; i4 < i2; i4++) {
            Int i5 = inputStream.read()
            if (i5 == -1) {
                throw BerDataValueFormatException("Truncated length")
            }
            if (i3 > 8388607) {
                throw BerDataValueFormatException("Length too large")
            }
            i3 = (i3 << 8) | (i5 & 255)
        }
        return i3
    }

    fun readShortFormLength(Int i) {
        return i & 127
    }

    fun readTagNumber(InputStream inputStream, Int i) throws IOException, BerDataValueFormatException {
        Int tagNumber = BerEncoding.getTagNumber((Byte) i)
        return tagNumber == 31 ? readHighTagNumber(inputStream) : tagNumber
    }

    fun skipConstructedIndefiniteLengthContents(RecordingInputStream recordingInputStream) throws BerDataValueFormatException {
        Int readByteCount = recordingInputStream.getReadByteCount()
        while (true) {
            BerDataValue dataValue = readDataValue(recordingInputStream)
            if (dataValue == null) {
                throw BerDataValueFormatException("Truncated indefinite-length contents: " + (recordingInputStream.getReadByteCount() - readByteCount) + " bytes read")
            }
            if (recordingInputStream.getReadByteCount() <= 0) {
                throw BerDataValueFormatException("Indefinite-length contents too Long")
            }
            ByteBuffer encoded = dataValue.getEncoded()
            if (encoded.remaining() == 2 && encoded.get(0) == 0 && encoded.get(1) == 0) {
                return (recordingInputStream.getReadByteCount() - readByteCount) - 2
            }
        }
    }

    fun skipDefiniteLengthContents(InputStream inputStream, Int i) throws IOException, BerDataValueFormatException {
        Long j = 0
        while (i > 0) {
            Int iSkip = (Int) inputStream.skip(i)
            if (iSkip <= 0) {
                throw BerDataValueFormatException("Truncated definite-length contents: " + j + " bytes read, " + i + " missing")
            }
            i -= iSkip
            j += iSkip
        }
    }

    fun skipPrimitiveIndefiniteLengthContents(InputStream inputStream) throws IOException, BerDataValueFormatException {
        Int i = 0
        while (true) {
            Boolean z = false
            while (true) {
                Int i2 = inputStream.read()
                if (i2 == -1) {
                    throw BerDataValueFormatException("Truncated indefinite-length contents: " + i + " bytes read")
                }
                i++
                if (i < 0) {
                    throw BerDataValueFormatException("Indefinite-length contents too Long")
                }
                if (i2 == 0) {
                    if (z) {
                        return i - 2
                    }
                    z = true
                }
            }
        }
    }

    @Override // com.android.apksig.internal.asn1.ber.BerDataValueReader
    fun readDataValue() throws BerDataValueFormatException {
        return readDataValue(this.mIn)
    }
}
