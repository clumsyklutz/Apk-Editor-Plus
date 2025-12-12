package android.support.multidex

import android.support.v4.media.session.PlaybackStateCompat
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.util.zip.CRC32
import java.util.zip.ZipException

final class ZipUtil {
    private static val BUFFER_SIZE = 16384
    private static val ENDHDR = 22
    private static val ENDSIG = 101010256

    class CentralDirectory {
        Long offset
        Long size

        CentralDirectory() {
        }
    }

    ZipUtil() {
    }

    static Long computeCrcOfCentralDir(RandomAccessFile randomAccessFile, CentralDirectory centralDirectory) throws IOException {
        CRC32 crc32 = CRC32()
        Long j = centralDirectory.size
        randomAccessFile.seek(centralDirectory.offset)
        Array<Byte> bArr = new Byte[16384]
        Int i = randomAccessFile.read(bArr, 0, (Int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j))
        while (i != -1) {
            crc32.update(bArr, 0, i)
            j -= i
            if (j == 0) {
                break
            }
            i = randomAccessFile.read(bArr, 0, (Int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j))
        }
        return crc32.getValue()
    }

    static CentralDirectory findCentralDirectory(RandomAccessFile randomAccessFile) throws IOException {
        Long length = randomAccessFile.length() - 22
        if (length < 0) {
            throw ZipException("File too Short to be a zip file: " + randomAccessFile.length())
        }
        Long j = length - PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH
        Long j2 = j >= 0 ? j : 0L
        Int iReverseBytes = Integer.reverseBytes(ENDSIG)
        Long j3 = length
        do {
            randomAccessFile.seek(j3)
            if (randomAccessFile.readInt() == iReverseBytes) {
                randomAccessFile.skipBytes(2)
                randomAccessFile.skipBytes(2)
                randomAccessFile.skipBytes(2)
                randomAccessFile.skipBytes(2)
                CentralDirectory centralDirectory = CentralDirectory()
                centralDirectory.size = Integer.reverseBytes(randomAccessFile.readInt()) & 4294967295L
                centralDirectory.offset = Integer.reverseBytes(randomAccessFile.readInt()) & 4294967295L
                return centralDirectory
            }
            j3--
        } while (j3 >= j2);
        throw ZipException("End Of Central Directory signature not found")
    }

    static Long getZipCrc(File file) throws IOException {
        RandomAccessFile randomAccessFile = RandomAccessFile(file, "r")
        try {
            return computeCrcOfCentralDir(randomAccessFile, findCentralDirectory(randomAccessFile))
        } finally {
            randomAccessFile.close()
        }
    }
}
