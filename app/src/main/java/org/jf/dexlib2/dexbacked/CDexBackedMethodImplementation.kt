package org.jf.dexlib2.dexbacked

import androidx.core.internal.view.SupportMenu
import org.jf.dexlib2.dexbacked.raw.CodeItem

class CDexBackedMethodImplementation extends DexBackedMethodImplementation {
    constructor(DexBackedDexFile dexBackedDexFile, DexBackedMethod dexBackedMethod, Int i) {
        super(dexBackedDexFile, dexBackedMethod, i)
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedMethodImplementation
    fun getDebugOffset() {
        CDexBackedDexFile cDexBackedDexFile = (CDexBackedDexFile) this.dexFile
        Int i = this.method.methodIndex
        Int i2 = (i / 16) * 4
        Int i3 = i % 16
        Int debugInfoOffsetsPos = cDexBackedDexFile.getDebugInfoOffsetsPos()
        DexReader<? extends DexBuffer> dexReader = cDexBackedDexFile.getDataBuffer().readerAt(debugInfoOffsetsPos + cDexBackedDexFile.getDataBuffer().readSmallUint(cDexBackedDexFile.getDebugInfoOffsetsTableOffset() + debugInfoOffsetsPos + i2))
        Int ubyte = (dexReader.readUbyte() << 8) + dexReader.readUbyte()
        if (((1 << i3) & ubyte) == 0) {
            return 0
        }
        Int iBitCount = Integer.bitCount((SupportMenu.USER_MASK >> (16 - i3)) & ubyte)
        Int debugInfoBase = cDexBackedDexFile.getDebugInfoBase()
        for (Int i4 = 0; i4 < iBitCount; i4++) {
            debugInfoBase += dexReader.readBigUleb128()
        }
        return debugInfoBase + dexReader.readBigUleb128()
    }

    fun getInsCount() {
        Int ushort = (this.dexFile.getDataBuffer().readUshort(this.codeOffset) >> CodeItem.CDEX_INS_COUNT_SHIFT) & 15
        if ((getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_INS_COUNT) == 0) {
            return ushort
        }
        Int i = (getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_INSTRUCTIONS_SIZE) != 0 ? 3 : 1
        if ((getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_REGISTER_COUNT) != 0) {
            i++
        }
        return ushort + this.dexFile.getDataBuffer().readUshort(this.codeOffset - (i * 2))
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedMethodImplementation
    fun getInstructionsSize() {
        Int ushort = this.dexFile.getDataBuffer().readUshort(this.codeOffset + CodeItem.CDEX_INSTRUCTIONS_SIZE_AND_PREHEADER_FLAGS_OFFSET) >> CodeItem.CDEX_INSTRUCTIONS_SIZE_SHIFT
        return (getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_INSTRUCTIONS_SIZE) != 0 ? ushort + this.dexFile.getDataBuffer().readUshort(this.codeOffset - 2) + (this.dexFile.getDataBuffer().readUshort(this.codeOffset - 4) << 16) : ushort
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedMethodImplementation
    fun getInstructionsStartOffset() {
        return this.codeOffset + 4
    }

    public final Int getPreheaderFlags() {
        return this.dexFile.getDataBuffer().readUshort(this.codeOffset + CodeItem.CDEX_INSTRUCTIONS_SIZE_AND_PREHEADER_FLAGS_OFFSET) & CodeItem.CDEX_PREHEADER_FLAGS_MASK
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedMethodImplementation, org.jf.dexlib2.iface.MethodImplementation
    fun getRegisterCount() {
        Int ushort = ((this.dexFile.getDataBuffer().readUshort(this.codeOffset) >> CodeItem.CDEX_REGISTER_COUNT_SHIFT) & 15) + getInsCount()
        if ((getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_REGISTER_COUNT) != 0) {
            return ushort + this.dexFile.getDataBuffer().readUshort(this.codeOffset - (((getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_INSTRUCTIONS_SIZE) > 0 ? 3 : 1) * 2))
        }
        return ushort
    }

    @Override // org.jf.dexlib2.dexbacked.DexBackedMethodImplementation
    fun getTriesSize() {
        Int ushort = (this.dexFile.getDataBuffer().readUshort(this.codeOffset) >> CodeItem.CDEX_TRIES_SIZE_SHIFT) & 15
        if ((getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_TRIES_COUNT) == 0) {
            return ushort
        }
        Int iBitCount = Integer.bitCount(getPreheaderFlags())
        if ((getPreheaderFlags() & CodeItem.CDEX_PREHEADER_FLAG_INSTRUCTIONS_SIZE) != 0) {
            iBitCount++
        }
        return ushort + this.dexFile.getDataBuffer().readUshort(this.codeOffset - (iBitCount * 2))
    }
}
