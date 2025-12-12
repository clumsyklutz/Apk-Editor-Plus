package com.android.apksig.internal.apk.v4

import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class V4Signature {
    public static val CURRENT_VERSION = 2
    public static val HASHING_ALGORITHM_SHA256 = 1
    public static val LOG2_BLOCK_SIZE_4096_BYTES = 12
    public final Array<Byte> hashingInfo
    public final Array<Byte> signingInfo
    public final Int version

    public static class HashingInfo {
        public final Int hashAlgorithm
        public final Byte log2BlockSize
        public final Array<Byte> rawRootHash
        public final Array<Byte> salt

        constructor(Int i, Byte b2, Array<Byte> bArr, Array<Byte> bArr2) {
            this.hashAlgorithm = i
            this.log2BlockSize = b2
            this.salt = bArr
            this.rawRootHash = bArr2
        }

        fun fromByteArray(Array<Byte> bArr) throws IOException {
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN)
            return HashingInfo(byteBufferOrder.getInt(), byteBufferOrder.get(), V4Signature.readBytes(byteBufferOrder), V4Signature.readBytes(byteBufferOrder))
        }

        public Array<Byte> toByteArray() {
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(V4Signature.bytesSize(this.salt) + 5 + V4Signature.bytesSize(this.rawRootHash)).order(ByteOrder.LITTLE_ENDIAN)
            byteBufferOrder.putInt(this.hashAlgorithm)
            byteBufferOrder.put(this.log2BlockSize)
            V4Signature.writeBytes(byteBufferOrder, this.salt)
            V4Signature.writeBytes(byteBufferOrder, this.rawRootHash)
            return byteBufferOrder.array()
        }
    }

    public static class SigningInfo {
        public final Array<Byte> additionalData
        public final Array<Byte> apkDigest
        public final Array<Byte> certificate
        public final Array<Byte> publicKey
        public final Array<Byte> signature
        public final Int signatureAlgorithmId

        constructor(Array<Byte> bArr, Array<Byte> bArr2, Array<Byte> bArr3, Array<Byte> bArr4, Int i, Array<Byte> bArr5) {
            this.apkDigest = bArr
            this.certificate = bArr2
            this.additionalData = bArr3
            this.publicKey = bArr4
            this.signatureAlgorithmId = i
            this.signature = bArr5
        }

        fun fromByteArray(Array<Byte> bArr) throws IOException {
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN)
            return SigningInfo(V4Signature.readBytes(byteBufferOrder), V4Signature.readBytes(byteBufferOrder), V4Signature.readBytes(byteBufferOrder), V4Signature.readBytes(byteBufferOrder), byteBufferOrder.getInt(), V4Signature.readBytes(byteBufferOrder))
        }

        public Array<Byte> toByteArray() {
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(V4Signature.bytesSize(this.apkDigest) + V4Signature.bytesSize(this.certificate) + V4Signature.bytesSize(this.additionalData) + V4Signature.bytesSize(this.publicKey) + 4 + V4Signature.bytesSize(this.signature)).order(ByteOrder.LITTLE_ENDIAN)
            V4Signature.writeBytes(byteBufferOrder, this.apkDigest)
            V4Signature.writeBytes(byteBufferOrder, this.certificate)
            V4Signature.writeBytes(byteBufferOrder, this.additionalData)
            V4Signature.writeBytes(byteBufferOrder, this.publicKey)
            byteBufferOrder.putInt(this.signatureAlgorithmId)
            V4Signature.writeBytes(byteBufferOrder, this.signature)
            return byteBufferOrder.array()
        }
    }

    constructor(Int i, Array<Byte> bArr, Array<Byte> bArr2) {
        this.version = i
        this.hashingInfo = bArr
        this.signingInfo = bArr2
    }

    fun bytesSize(Array<Byte> bArr) {
        return (bArr == null ? 0 : bArr.length) + 4
    }

    public static Array<Byte> getSigningData(Long j, HashingInfo hashingInfo, SigningInfo signingInfo) {
        Int iBytesSize = bytesSize(hashingInfo.salt) + 17 + bytesSize(hashingInfo.rawRootHash) + bytesSize(signingInfo.apkDigest) + bytesSize(signingInfo.certificate) + bytesSize(signingInfo.additionalData)
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(iBytesSize).order(ByteOrder.LITTLE_ENDIAN)
        byteBufferOrder.putInt(iBytesSize)
        byteBufferOrder.putLong(j)
        byteBufferOrder.putInt(hashingInfo.hashAlgorithm)
        byteBufferOrder.put(hashingInfo.log2BlockSize)
        writeBytes(byteBufferOrder, hashingInfo.salt)
        writeBytes(byteBufferOrder, hashingInfo.rawRootHash)
        writeBytes(byteBufferOrder, signingInfo.apkDigest)
        writeBytes(byteBufferOrder, signingInfo.certificate)
        writeBytes(byteBufferOrder, signingInfo.additionalData)
        return byteBufferOrder.array()
    }

    public static Array<Byte> readBytes(InputStream inputStream) throws IOException {
        try {
            Array<Byte> bArr = new Byte[readIntLE(inputStream)]
            readFully(inputStream, bArr)
            return bArr
        } catch (EOFException unused) {
            return null
        }
    }

    public static Array<Byte> readBytes(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() < 4) {
            throw EOFException()
        }
        Int i = byteBuffer.getInt()
        if (byteBuffer.remaining() < i) {
            throw EOFException()
        }
        Array<Byte> bArr = new Byte[i]
        byteBuffer.get(bArr)
        return bArr
    }

    fun readFrom(InputStream inputStream) throws IOException {
        Int intLE = readIntLE(inputStream)
        if (intLE == 2) {
            return V4Signature(intLE, readBytes(inputStream), readBytes(inputStream))
        }
        throw IOException("Invalid signature version.")
    }

    fun readFully(InputStream inputStream, Array<Byte> bArr) throws IOException {
        Int length = bArr.length
        Int i = 0
        while (i < length) {
            Int i2 = inputStream.read(bArr, i, length - i)
            if (i2 < 0) {
                throw EOFException()
            }
            i += i2
        }
    }

    fun readIntLE(InputStream inputStream) throws IOException {
        Array<Byte> bArr = new Byte[4]
        readFully(inputStream, bArr)
        return ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).getInt()
    }

    fun writeBytes(OutputStream outputStream, Array<Byte> bArr) throws IOException {
        if (bArr == null) {
            writeIntLE(outputStream, 0)
        } else {
            writeIntLE(outputStream, bArr.length)
            outputStream.write(bArr)
        }
    }

    fun writeBytes(ByteBuffer byteBuffer, Array<Byte> bArr) {
        if (bArr == null) {
            byteBuffer.putInt(0)
        } else {
            byteBuffer.putInt(bArr.length)
            byteBuffer.put(bArr)
        }
    }

    fun writeIntLE(OutputStream outputStream, Int i) throws IOException {
        outputStream.write(ByteBuffer.wrap(new Byte[4]).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array())
    }

    fun writeTo(OutputStream outputStream) throws IOException {
        writeIntLE(outputStream, this.version)
        writeBytes(outputStream, this.hashingInfo)
        writeBytes(outputStream, this.signingInfo)
    }
}
