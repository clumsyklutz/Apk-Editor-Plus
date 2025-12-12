package org.jf.dexlib2.dexbacked

import androidx.appcompat.R
import java.io.UnsupportedEncodingException
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.raw.CdexHeaderItem
import org.jf.dexlib2.util.DexUtil

class CDexBackedDexFile extends DexBackedDexFile {
    constructor(Opcodes opcodes, Array<Byte> bArr, Int i) {
        super(opcodes, bArr, i)
    }

    fun isCdex(Array<Byte> bArr, Int i) throws UnsupportedEncodingException {
        if (i + 4 > bArr.length) {
            return false
        }
        try {
            Array<Byte> bytes = "cdex".getBytes("US-ASCII")
            return bArr[i] == bytes[0] && bArr[i + 1] == bytes[1] && bArr[i + 2] == bytes[2] && bArr[i + 3] == bytes[3]
        } catch (UnsupportedEncodingException e) {
            throw RuntimeException(e)
        }
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile
    fun createMethodImplementation(DexBackedDexFile dexBackedDexFile, DexBackedMethod dexBackedMethod, Int i) {
        return CDexBackedMethodImplementation(dexBackedDexFile, dexBackedMethod, i)
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile
    fun getBaseDataOffset() {
        return getBuffer().readSmallUint(108)
    }

    fun getDebugInfoBase() {
        return getBuffer().readSmallUint(124)
    }

    fun getDebugInfoOffsetsPos() {
        return getBuffer().readSmallUint(R.styleable.AppCompatTheme_listMenuViewStyle)
    }

    fun getDebugInfoOffsetsTableOffset() {
        return getBuffer().readSmallUint(120)
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile
    fun getDefaultOpcodes(Int i) {
        return Opcodes.forApi(28)
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile
    fun getVersion(Array<Byte> bArr, Int i, Boolean z) {
        return z ? DexUtil.verifyCdexHeader(bArr, i) : CdexHeaderItem.getVersion(bArr, i)
    }
}
