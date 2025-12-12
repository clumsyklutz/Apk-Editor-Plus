package org.jf.dexlib2.dexbacked

import com.google.common.io.ByteStreams
import java.io.IOException
import java.io.InputStream
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.raw.OdexHeaderItem
import org.jf.dexlib2.util.DexUtil

class DexBackedOdexFile extends DexBackedDexFile {

    public static class NotAnOdexFile extends RuntimeException {
        constructor(String str) {
            super(str)
        }
    }

    constructor(Opcodes opcodes, Array<Byte> bArr, Array<Byte> bArr2) {
        super(opcodes, bArr2)
    }

    fun fromInputStream(Opcodes opcodes, InputStream inputStream) throws IOException {
        DexUtil.verifyOdexHeader(inputStream)
        inputStream.reset()
        Array<Byte> bArr = new Byte[40]
        ByteStreams.readFully(inputStream, bArr)
        if (OdexHeaderItem.getDexOffset(bArr) > 40) {
            ByteStreams.skipFully(inputStream, r2 - 40)
        }
        return DexBackedOdexFile(opcodes, bArr, ByteStreams.toByteArray(inputStream))
    }
}
