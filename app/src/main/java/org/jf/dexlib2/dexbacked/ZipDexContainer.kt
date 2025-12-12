package org.jf.dexlib2.dexbacked

import com.google.common.collect.Lists
import com.google.common.io.ByteStreams
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Enumeration
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.DexFile
import org.jf.dexlib2.iface.MultiDexContainer
import org.jf.dexlib2.util.DexUtil

class ZipDexContainer implements MultiDexContainer<DexBackedDexFile> {
    public final Opcodes opcodes
    public final File zipFilePath

    public static class NotAZipFileException extends RuntimeException {
    }

    constructor(File file, Opcodes opcodes) {
        this.zipFilePath = file
        this.opcodes = opcodes
    }

    @Override // org.jf.dexlib2.iface.MultiDexContainer
    public List<String> getDexEntryNames() throws IOException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        ZipFile zipFile = getZipFile()
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                if (isDex(zipFile, zipEntryNextElement)) {
                    arrayListNewArrayList.add(zipEntryNextElement.getName())
                }
            }
            return arrayListNewArrayList
        } finally {
            zipFile.close()
        }
    }

    @Override // org.jf.dexlib2.iface.MultiDexContainer
    public MultiDexContainer.DexEntry<DexBackedDexFile> getEntry(String str) throws IOException {
        ZipFile zipFile = getZipFile()
        try {
            ZipEntry entry = zipFile.getEntry(str)
            if (entry == null) {
                return null
            }
            return loadEntry(zipFile, entry)
        } finally {
            zipFile.close()
        }
    }

    fun getZipFile() throws IOException {
        try {
            return ZipFile(this.zipFilePath)
        } catch (IOException unused) {
            throw NotAZipFileException()
        }
    }

    fun isDex(ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        BufferedInputStream bufferedInputStream = BufferedInputStream(zipFile.getInputStream(zipEntry))
        try {
            DexUtil.verifyDexHeader(bufferedInputStream)
            bufferedInputStream.close()
            return true
        } catch (DexBackedDexFile.NotADexFile unused) {
            bufferedInputStream.close()
            return false
        } catch (DexUtil.InvalidFile unused2) {
            bufferedInputStream.close()
            return false
        } catch (DexUtil.UnsupportedFile unused3) {
            bufferedInputStream.close()
            return false
        } catch (Throwable th) {
            bufferedInputStream.close()
            throw th
        }
    }

    public MultiDexContainer.DexEntry loadEntry(ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        InputStream inputStream = zipFile.getInputStream(zipEntry)
        try {
            return new MultiDexContainer.DexEntry(zipEntry, ByteStreams.toByteArray(inputStream)) { // from class: org.jf.dexlib2.dexbacked.ZipDexContainer.1
                public final /* synthetic */ Array<Byte> val$buf

                {
                    this.val$buf = bArr
                }

                @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
                fun getDexFile() {
                    return DexBackedDexFile(ZipDexContainer.this.opcodes, this.val$buf)
                }
            }
        } finally {
            inputStream.close()
        }
    }
}
