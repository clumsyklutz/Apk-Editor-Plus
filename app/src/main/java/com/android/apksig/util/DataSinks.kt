package com.android.apksig.util

import com.android.apksig.internal.util.ByteArrayDataSink
import com.android.apksig.internal.util.MessageDigestSink
import com.android.apksig.internal.util.OutputStreamDataSink
import com.android.apksig.internal.util.RandomAccessFileDataSink
import java.io.OutputStream
import java.io.RandomAccessFile
import java.security.MessageDigest

abstract class DataSinks {
    fun asDataSink(OutputStream outputStream) {
        return OutputStreamDataSink(outputStream)
    }

    fun asDataSink(RandomAccessFile randomAccessFile) {
        return RandomAccessFileDataSink(randomAccessFile)
    }

    fun asDataSink(MessageDigest... messageDigestArr) {
        return MessageDigestSink(messageDigestArr)
    }

    fun newInMemoryDataSink() {
        return ByteArrayDataSink()
    }

    fun newInMemoryDataSink(Int i) {
        return ByteArrayDataSink(i)
    }
}
