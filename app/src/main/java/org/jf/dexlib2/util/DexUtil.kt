package org.jf.dexlib2.util

import com.google.common.io.ByteStreams
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexBackedOdexFile
import org.jf.dexlib2.dexbacked.raw.CdexHeaderItem
import org.jf.dexlib2.dexbacked.raw.HeaderItem
import org.jf.dexlib2.dexbacked.raw.OdexHeaderItem

class DexUtil {

    public static class InvalidFile extends RuntimeException {
        constructor(String str) {
            super(str)
        }
    }

    public static class UnsupportedFile extends RuntimeException {
        constructor(String str) {
            super(str)
        }
    }

    fun verifyCdexHeader(Array<Byte> bArr, Int i) {
        Int version = CdexHeaderItem.getVersion(bArr, i)
        if (version == -1) {
            StringBuilder sb = StringBuilder("Not a valid cdex magic value:")
            for (Int i2 = 0; i2 < 8; i2++) {
                sb.append(String.format(" %02x", Byte.valueOf(bArr[i + i2])))
            }
            throw new DexBackedDexFile.NotADexFile(sb.toString())
        }
        if (!CdexHeaderItem.isSupportedCdexVersion(version)) {
            throw UnsupportedFile(String.format("Dex version %03d is not supported", Integer.valueOf(version)))
        }
        Int endian = HeaderItem.getEndian(bArr, i)
        if (endian == 2018915346) {
            throw UnsupportedFile("Big endian dex files are not supported")
        }
        if (endian == 305419896) {
            return version
        }
        throw InvalidFile(String.format("Invalid endian tag: 0x%x", Integer.valueOf(endian)))
    }

    fun verifyDexHeader(InputStream inputStream) throws IOException {
        if (!inputStream.markSupported()) {
            throw IllegalArgumentException("InputStream must support mark")
        }
        inputStream.mark(44)
        Array<Byte> bArr = new Byte[44]
        try {
            try {
                ByteStreams.readFully(inputStream, bArr)
                inputStream.reset()
                return verifyDexHeader(bArr, 0)
            } catch (EOFException unused) {
                throw new DexBackedDexFile.NotADexFile("File is too Short")
            }
        } catch (Throwable th) {
            inputStream.reset()
            throw th
        }
    }

    fun verifyDexHeader(Array<Byte> bArr, Int i) {
        Int version = HeaderItem.getVersion(bArr, i)
        if (version == -1) {
            StringBuilder sb = StringBuilder("Not a valid dex magic value:")
            for (Int i2 = 0; i2 < 8; i2++) {
                sb.append(String.format(" %02x", Byte.valueOf(bArr[i2])))
            }
            throw new DexBackedDexFile.NotADexFile(sb.toString())
        }
        if (!HeaderItem.isSupportedDexVersion(version)) {
            throw UnsupportedFile(String.format("Dex version %03d is not supported", Integer.valueOf(version)))
        }
        Int endian = HeaderItem.getEndian(bArr, i)
        if (endian == 2018915346) {
            throw UnsupportedFile("Big endian dex files are not supported")
        }
        if (endian == 305419896) {
            return version
        }
        throw InvalidFile(String.format("Invalid endian tag: 0x%x", Integer.valueOf(endian)))
    }

    fun verifyOdexHeader(InputStream inputStream) throws IOException {
        if (!inputStream.markSupported()) {
            throw IllegalArgumentException("InputStream must support mark")
        }
        inputStream.mark(8)
        Array<Byte> bArr = new Byte[8]
        try {
            try {
                ByteStreams.readFully(inputStream, bArr)
                inputStream.reset()
                verifyOdexHeader(bArr, 0)
            } catch (EOFException unused) {
                throw new DexBackedOdexFile.NotAnOdexFile("File is too Short")
            }
        } catch (Throwable th) {
            inputStream.reset()
            throw th
        }
    }

    fun verifyOdexHeader(Array<Byte> bArr, Int i) {
        Int version = OdexHeaderItem.getVersion(bArr, i)
        if (version != -1) {
            if (!OdexHeaderItem.isSupportedOdexVersion(version)) {
                throw UnsupportedFile(String.format("Odex version %03d is not supported", Integer.valueOf(version)))
            }
            return
        }
        StringBuilder sb = StringBuilder("Not a valid odex magic value:")
        for (Int i2 = 0; i2 < 8; i2++) {
            sb.append(String.format(" %02x", Byte.valueOf(bArr[i2])))
        }
        throw new DexBackedOdexFile.NotAnOdexFile(sb.toString())
    }
}
