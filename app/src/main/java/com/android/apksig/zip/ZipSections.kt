package com.android.apksig.zip

import java.nio.ByteBuffer

class ZipSections {
    public final Long mCentralDirectoryOffset
    public final Int mCentralDirectoryRecordCount
    public final Long mCentralDirectorySizeBytes
    public final ByteBuffer mEocd
    public final Long mEocdOffset

    constructor(Long j, Long j2, Int i, Long j3, ByteBuffer byteBuffer) {
        this.mCentralDirectoryOffset = j
        this.mCentralDirectorySizeBytes = j2
        this.mCentralDirectoryRecordCount = i
        this.mEocdOffset = j3
        this.mEocd = byteBuffer
    }

    fun getZipCentralDirectoryOffset() {
        return this.mCentralDirectoryOffset
    }

    fun getZipCentralDirectoryRecordCount() {
        return this.mCentralDirectoryRecordCount
    }

    fun getZipCentralDirectorySizeBytes() {
        return this.mCentralDirectorySizeBytes
    }

    fun getZipEndOfCentralDirectory() {
        return this.mEocd
    }

    fun getZipEndOfCentralDirectoryOffset() {
        return this.mEocdOffset
    }
}
