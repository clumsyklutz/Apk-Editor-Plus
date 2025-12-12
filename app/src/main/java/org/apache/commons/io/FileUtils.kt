package org.apache.commons.io

import android.support.v4.media.session.PlaybackStateCompat
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigInteger
import java.nio.charset.Charset
import java.util.Collection
import java.util.List
import java.util.zip.CRC32
import java.util.zip.CheckedInputStream
import java.util.zip.Checksum
import org.apache.commons.io.output.NullOutputStream

class FileUtils {
    public static final BigInteger ONE_GB_BI
    public static final BigInteger ONE_KB_BI
    public static final BigInteger ONE_MB_BI
    public static final BigInteger ONE_PB_BI
    public static final BigInteger ONE_TB_BI
    public static final BigInteger ONE_ZB

    static {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)
        ONE_KB_BI = bigIntegerValueOf
        BigInteger bigIntegerMultiply = bigIntegerValueOf.multiply(bigIntegerValueOf)
        ONE_MB_BI = bigIntegerMultiply
        BigInteger bigIntegerMultiply2 = bigIntegerValueOf.multiply(bigIntegerMultiply)
        ONE_GB_BI = bigIntegerMultiply2
        BigInteger bigIntegerMultiply3 = bigIntegerValueOf.multiply(bigIntegerMultiply2)
        ONE_TB_BI = bigIntegerMultiply3
        BigInteger bigIntegerMultiply4 = bigIntegerValueOf.multiply(bigIntegerMultiply3)
        ONE_PB_BI = bigIntegerMultiply4
        bigIntegerValueOf.multiply(bigIntegerMultiply4)
        BigInteger bigIntegerMultiply5 = BigInteger.valueOf(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID).multiply(BigInteger.valueOf(1152921504606846976L))
        ONE_ZB = bigIntegerMultiply5
        bigIntegerValueOf.multiply(bigIntegerMultiply5)
    }

    fun checksum(File file, Checksum checksum) throws IOException {
        if (file.isDirectory()) {
            throw IllegalArgumentException("Checksums can't be computed on directories")
        }
        CheckedInputStream checkedInputStream = CheckedInputStream(FileInputStream(file), checksum)
        try {
            IOUtils.copy(checkedInputStream, NullOutputStream())
            return checksum
        } catch (Throwable th) {
            try {
                throw th
            } finally {
                try {
                    checkedInputStream.close()
                } catch (Throwable unused) {
                }
            }
        }
    }

    fun checksumCRC32(File file) throws IOException {
        CRC32 crc32 = CRC32()
        checksum(file, crc32)
        return crc32.getValue()
    }

    fun openInputStream(File file) throws IOException {
        if (!file.exists()) {
            throw FileNotFoundException("File '" + file + "' does not exist")
        }
        if (file.isDirectory()) {
            throw IOException("File '" + file + "' exists but is a directory")
        }
        if (file.canRead()) {
            return FileInputStream(file)
        }
        throw IOException("File '" + file + "' cannot be read")
    }

    fun openOutputStream(File file, Boolean z) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile()
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw IOException("Directory '" + parentFile + "' could not be created")
            }
        } else {
            if (file.isDirectory()) {
                throw IOException("File '" + file + "' exists but is a directory")
            }
            if (!file.canWrite()) {
                throw IOException("File '" + file + "' cannot be written to")
            }
        }
        return FileOutputStream(file, z)
    }

    public static List<String> readLines(File file, String str) throws IOException {
        return readLines(file, Charsets.toCharset(str))
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        FileInputStream fileInputStreamOpenInputStream = openInputStream(file)
        try {
            List<String> lines = IOUtils.readLines(fileInputStreamOpenInputStream, Charsets.toCharset(charset))
            if (fileInputStreamOpenInputStream != null) {
                fileInputStreamOpenInputStream.close()
            }
            return lines
        } catch (Throwable th) {
            try {
                throw th
            } catch (Throwable th2) {
                if (fileInputStreamOpenInputStream != null) {
                    try {
                        fileInputStreamOpenInputStream.close()
                    } catch (Throwable unused) {
                    }
                }
                throw th2
            }
        }
    }

    fun writeLines(File file, String str, Collection<?> collection) throws IOException {
        writeLines(file, str, collection, null, false)
    }

    fun writeLines(File file, String str, Collection<?> collection, String str2, Boolean z) throws IOException {
        BufferedOutputStream bufferedOutputStream = BufferedOutputStream(openOutputStream(file, z))
        try {
            IOUtils.writeLines(collection, str2, bufferedOutputStream, str)
        } catch (Throwable th) {
            try {
                throw th
            } finally {
                try {
                    bufferedOutputStream.close()
                } catch (Throwable unused) {
                }
            }
        }
    }
}
