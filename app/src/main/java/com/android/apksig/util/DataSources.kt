package com.android.apksig.util

import com.android.apksig.internal.util.ByteBufferDataSource
import com.android.apksig.internal.util.FileChannelDataSource
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

abstract class DataSources {
    fun asDataSource(RandomAccessFile randomAccessFile) {
        return asDataSource(randomAccessFile.getChannel())
    }

    fun asDataSource(RandomAccessFile randomAccessFile, Long j, Long j2) {
        return asDataSource(randomAccessFile.getChannel(), j, j2)
    }

    fun asDataSource(ByteBuffer byteBuffer) {
        byteBuffer.getClass()
        return ByteBufferDataSource(byteBuffer)
    }

    fun asDataSource(FileChannel fileChannel) {
        fileChannel.getClass()
        return FileChannelDataSource(fileChannel)
    }

    fun asDataSource(FileChannel fileChannel, Long j, Long j2) {
        fileChannel.getClass()
        return FileChannelDataSource(fileChannel, j, j2)
    }
}
