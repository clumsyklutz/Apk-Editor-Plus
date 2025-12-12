package com.android.apksig.util

import java.io.IOException
import java.nio.ByteBuffer

public interface DataSource {
    Unit copyTo(Long j, Int i, ByteBuffer byteBuffer) throws IOException

    Unit feed(Long j, Long j2, DataSink dataSink) throws IOException

    ByteBuffer getByteBuffer(Long j, Int i) throws IOException

    Long size()

    DataSource slice(Long j, Long j2)
}
