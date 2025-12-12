package com.android.apksig.internal.util

import android.annotation.TargetApi
import android.support.v4.media.session.PlaybackStateCompat
import com.android.apksig.internal.zip.ZipUtils
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import com.android.apksig.util.DataSources
import java.io.Closeable
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Phaser
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class VerityTreeBuilder implements Closeable {
    public static val DIGEST_PARALLELISM = Math.min(32, Runtime.getRuntime().availableProcessors())
    public final ExecutorService mExecutor
    public final MessageDigest mMd
    public final Array<Byte> mSalt

    constructor(Array<Byte> bArr) throws NoSuchAlgorithmException {
        Int i = DIGEST_PARALLELISM
        this.mExecutor = ThreadPoolExecutor(i, i, 0L, TimeUnit.MILLISECONDS, ArrayBlockingQueue(4), new ThreadPoolExecutor.CallerRunsPolicy())
        this.mSalt = bArr
        this.mMd = getNewMessageDigest()
    }

    public static Array<Int> calculateLevelOffset(Long j, Int i) {
        ArrayList arrayList = ArrayList()
        do {
            j = divideRoundup(j, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) * i
            arrayList.add(Long.valueOf(divideRoundup(j, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) * PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM))
        } while (j > PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
        Array<Int> iArr = new Int[arrayList.size() + 1]
        Int i2 = 0
        iArr[0] = 0
        while (i2 < arrayList.size()) {
            Int i3 = i2 + 1
            iArr[i3] = iArr[i2] + MathCompat.toIntExact(((Long) arrayList.get((arrayList.size() - i2) - 1)).longValue())
            i2 = i3
        }
        return iArr
    }

    fun divideRoundup(Long j, Long j2) {
        return ((j + j2) - 1) / j2
    }

    fun getNewMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256")
    }

    fun slice(ByteBuffer byteBuffer, Int i, Int i2) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate()
        byteBufferDuplicate.position(0)
        byteBufferDuplicate.limit(i2)
        byteBufferDuplicate.position(i)
        return byteBufferDuplicate.slice()
    }

    public final MessageDigest cloneMessageDigest() {
        try {
            try {
                return (MessageDigest) this.mMd.clone()
            } catch (NoSuchAlgorithmException e) {
                throw IllegalStateException("Failed to obtain an instance of a previously available message digest", e)
            }
        } catch (CloneNotSupportedException unused) {
            return getNewMessageDigest()
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    fun close() {
        this.mExecutor.shutdownNow()
    }

    @TargetApi(21)
    public final Unit digestDataByChunks(DataSource dataSource, DataSink dataSink) throws IOException {
        Long size = dataSource.size()
        Long j = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM
        Int iDivideRoundup = (Int) divideRoundup(size, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)
        final Array<Byte>[] bArr = new Byte[iDivideRoundup][]
        val phaser = Phaser(1)
        Long j2 = 0
        Int i = 0
        while (j2 < size) {
            Int iMin = (Int) (Math.min(4194304 + j2, size) - j2)
            Long j3 = iMin
            Int iDivideRoundup2 = (Int) divideRoundup(j3, j)
            val byteBufferAllocate = ByteBuffer.allocate(iDivideRoundup2 * 4096)
            dataSource.copyTo(j2, iMin, byteBufferAllocate)
            byteBufferAllocate.rewind()
            val i2 = i
            Runnable runnable = Runnable() { // from class: com.android.apksig.internal.util.VerityTreeBuilder.1
                @Override // java.lang.Runnable
                fun run() {
                    MessageDigest messageDigestCloneMessageDigest = VerityTreeBuilder.this.cloneMessageDigest()
                    Int iCapacity = byteBufferAllocate.capacity()
                    Int i3 = i2
                    Int i4 = 0
                    while (i4 < iCapacity) {
                        Int i5 = i4 + 4096
                        bArr[i3] = VerityTreeBuilder.this.saltedDigest(messageDigestCloneMessageDigest, VerityTreeBuilder.slice(byteBufferAllocate, i4, i5))
                        i3++
                        i4 = i5
                    }
                    phaser.arriveAndDeregister()
                }
            }
            phaser.register()
            this.mExecutor.execute(runnable)
            i += iDivideRoundup2
            j2 += j3
            j = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM
        }
        phaser.arriveAndAwaitAdvance()
        for (Int i3 = 0; i3 < iDivideRoundup; i3++) {
            Array<Byte> bArr2 = bArr[i3]
            dataSink.consume(bArr2, 0, bArr2.length)
        }
    }

    fun generateVerityTree(DataSource dataSource) throws IOException {
        DataSource dataSourceAsDataSource
        Int digestLength = this.mMd.getDigestLength()
        Array<Int> iArrCalculateLevelOffset = calculateLevelOffset(dataSource.size(), digestLength)
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iArrCalculateLevelOffset[iArrCalculateLevelOffset.length - 1])
        for (Int length = iArrCalculateLevelOffset.length - 2; length >= 0; length--) {
            Int i = length + 1
            ByteBufferSink byteBufferSink = ByteBufferSink(slice(byteBufferAllocate, iArrCalculateLevelOffset[length], iArrCalculateLevelOffset[i]))
            if (length == iArrCalculateLevelOffset.length - 2) {
                digestDataByChunks(dataSource, byteBufferSink)
                dataSourceAsDataSource = dataSource
            } else {
                dataSourceAsDataSource = DataSources.asDataSource(slice(byteBufferAllocate.asReadOnlyBuffer(), iArrCalculateLevelOffset[i], iArrCalculateLevelOffset[length + 2]))
                digestDataByChunks(dataSourceAsDataSource, byteBufferSink)
            }
            Int iDivideRoundup = (Int) ((divideRoundup(dataSourceAsDataSource.size(), PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) * digestLength) % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)
            if (iDivideRoundup > 0) {
                Int i2 = 4096 - iDivideRoundup
                byteBufferSink.consume(new Byte[i2], 0, i2)
            }
        }
        return byteBufferAllocate
    }

    public Array<Byte> generateVerityTreeRootHash(DataSource dataSource) throws IOException {
        return getRootHashFromTree(generateVerityTree(dataSource))
    }

    public Array<Byte> generateVerityTreeRootHash(DataSource dataSource, DataSource dataSource2, DataSource dataSource3) throws IOException {
        if (dataSource.size() % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM != 0) {
            throw IllegalStateException("APK Signing Block size not a multiple of 4096: " + dataSource.size())
        }
        Long size = dataSource.size()
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((Int) dataSource3.size())
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        dataSource3.copyTo(0L, (Int) dataSource3.size(), byteBufferAllocate)
        byteBufferAllocate.flip()
        ZipUtils.setZipEocdCentralDirectoryOffset(byteBufferAllocate, size)
        return generateVerityTreeRootHash(ChainedDataSource(dataSource, dataSource2, DataSources.asDataSource(byteBufferAllocate)))
    }

    public Array<Byte> getRootHashFromTree(ByteBuffer byteBuffer) throws IOException {
        return saltedDigest(slice(byteBuffer.asReadOnlyBuffer(), 0, 4096))
    }

    public final Array<Byte> saltedDigest(ByteBuffer byteBuffer) {
        return saltedDigest(this.mMd, byteBuffer)
    }

    public final Array<Byte> saltedDigest(MessageDigest messageDigest, ByteBuffer byteBuffer) {
        messageDigest.reset()
        Array<Byte> bArr = this.mSalt
        if (bArr != null) {
            messageDigest.update(bArr)
        }
        messageDigest.update(byteBuffer)
        return messageDigest.digest()
    }
}
