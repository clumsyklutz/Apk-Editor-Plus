package com.google.common.io

import android.support.v4.media.session.PlaybackStateCompat
import com.google.common.base.Preconditions
import com.google.common.math.IntMath
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.ArrayDeque
import java.util.Queue

class ByteStreams {
    static {
        OutputStream() { // from class: com.google.common.io.ByteStreams.1
            fun toString() {
                return "ByteStreams.nullOutputStream()"
            }

            @Override // java.io.OutputStream
            fun write(Int i) {
            }

            @Override // java.io.OutputStream
            fun write(Array<Byte> bArr) {
                Preconditions.checkNotNull(bArr)
            }

            @Override // java.io.OutputStream
            fun write(Array<Byte> bArr, Int i, Int i2) {
                Preconditions.checkNotNull(bArr)
            }
        }
    }

    public static Array<Byte> combineBuffers(Queue<Array<Byte>> queue, Int i) {
        Array<Byte> bArr = new Byte[i]
        Int i2 = i
        while (i2 > 0) {
            Array<Byte> bArrRemove = queue.remove()
            Int iMin = Math.min(i2, bArrRemove.length)
            System.arraycopy(bArrRemove, 0, bArr, i - i2, iMin)
            i2 -= iMin
        }
        return bArr
    }

    fun read(InputStream inputStream, Array<Byte> bArr, Int i, Int i2) throws IOException {
        Preconditions.checkNotNull(inputStream)
        Preconditions.checkNotNull(bArr)
        Int i3 = 0
        if (i2 < 0) {
            throw IndexOutOfBoundsException(String.format("len (%s) cannot be negative", Integer.valueOf(i2)))
        }
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length)
        while (i3 < i2) {
            Int i4 = inputStream.read(bArr, i + i3, i2 - i3)
            if (i4 == -1) {
                break
            }
            i3 += i4
        }
        return i3
    }

    fun readFully(InputStream inputStream, Array<Byte> bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length)
    }

    fun readFully(InputStream inputStream, Array<Byte> bArr, Int i, Int i2) throws IOException {
        Int i3 = read(inputStream, bArr, i, i2)
        if (i3 == i2) {
            return
        }
        StringBuilder sb = StringBuilder(81)
        sb.append("reached end of stream after reading ")
        sb.append(i3)
        sb.append(" bytes; ")
        sb.append(i2)
        sb.append(" bytes expected")
        throw EOFException(sb.toString())
    }

    fun skipFully(InputStream inputStream, Long j) throws IOException {
        Long jSkipUpTo = skipUpTo(inputStream, j)
        if (jSkipUpTo >= j) {
            return
        }
        StringBuilder sb = StringBuilder(100)
        sb.append("reached end of stream after skipping ")
        sb.append(jSkipUpTo)
        sb.append(" bytes; ")
        sb.append(j)
        sb.append(" bytes expected")
        throw EOFException(sb.toString())
    }

    fun skipSafely(InputStream inputStream, Long j) throws IOException {
        Int iAvailable = inputStream.available()
        if (iAvailable == 0) {
            return 0L
        }
        return inputStream.skip(Math.min(iAvailable, j))
    }

    fun skipUpTo(InputStream inputStream, Long j) throws IOException {
        Array<Byte> bArr = null
        Long j2 = 0
        while (j2 < j) {
            Long j3 = j - j2
            Long jSkipSafely = skipSafely(inputStream, j3)
            if (jSkipSafely == 0) {
                Int iMin = (Int) Math.min(j3, PlaybackStateCompat.ACTION_PLAY_FROM_URI)
                if (bArr == null) {
                    bArr = new Byte[iMin]
                }
                jSkipSafely = inputStream.read(bArr, 0, iMin)
                if (jSkipSafely == -1) {
                    break
                }
            }
            j2 += jSkipSafely
        }
        return j2
    }

    public static Array<Byte> toByteArray(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream)
        return toByteArrayInternal(inputStream, ArrayDeque(20), 0)
    }

    public static Array<Byte> toByteArrayInternal(InputStream inputStream, Queue<Array<Byte>> queue, Int i) throws IOException {
        Int iSaturatedMultiply = 8192
        while (i < 2147483639) {
            Int iMin = Math.min(iSaturatedMultiply, 2147483639 - i)
            Array<Byte> bArr = new Byte[iMin]
            queue.add(bArr)
            Int i2 = 0
            while (i2 < iMin) {
                Int i3 = inputStream.read(bArr, i2, iMin - i2)
                if (i3 == -1) {
                    return combineBuffers(queue, i)
                }
                i2 += i3
                i += i3
            }
            iSaturatedMultiply = IntMath.saturatedMultiply(iSaturatedMultiply, 2)
        }
        if (inputStream.read() == -1) {
            return combineBuffers(queue, 2147483639)
        }
        throw OutOfMemoryError("input is too large to fit in a Byte array")
    }
}
