package org.jf.dexlib2.dexbacked

import com.google.common.collect.ImmutableList
import java.util.Iterator
import java.util.List
import org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction
import org.jf.dexlib2.dexbacked.util.DebugInfo
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.util.AlignmentUtils
import org.jf.util.ExceptionWithContext

class DexBackedMethodImplementation implements MethodImplementation {
    public final Int codeOffset
    public final DexBackedDexFile dexFile
    public final DexBackedMethod method

    constructor(DexBackedDexFile dexBackedDexFile, DexBackedMethod dexBackedMethod, Int i) {
        this.dexFile = dexBackedDexFile
        this.method = dexBackedMethod
        this.codeOffset = i
    }

    public final DebugInfo getDebugInfo() {
        Int debugOffset = getDebugOffset()
        if (debugOffset == -1 || debugOffset == 0) {
            return DebugInfo.newOrEmpty(this.dexFile, 0, this)
        }
        if (debugOffset < 0) {
            System.err.println(String.format("%s: Invalid debug offset", this.method))
            return DebugInfo.newOrEmpty(this.dexFile, 0, this)
        }
        if (this.dexFile.getBaseDataOffset() + debugOffset < this.dexFile.getBuffer().buf.length) {
            return DebugInfo.newOrEmpty(this.dexFile, debugOffset, this)
        }
        System.err.println(String.format("%s: Invalid debug offset", this.method))
        return DebugInfo.newOrEmpty(this.dexFile, 0, this)
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public Iterable<? extends DebugItem> getDebugItems() {
        return getDebugInfo()
    }

    fun getDebugOffset() {
        return this.dexFile.getDataBuffer().readInt(this.codeOffset + 8)
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public Iterable<? extends Instruction> getInstructions() {
        Int instructionsSize = getInstructionsSize()
        val instructionsStartOffset = getInstructionsStartOffset()
        val i = (instructionsSize * 2) + instructionsStartOffset
        return new Iterable<Instruction>() { // from class: org.jf.dexlib2.dexbacked.DexBackedMethodImplementation.1
            @Override // java.lang.Iterable
            public Iterator<Instruction> iterator() {
                return new VariableSizeLookaheadIterator<Instruction>(DexBackedMethodImplementation.this.dexFile.getDataBuffer(), instructionsStartOffset) { // from class: org.jf.dexlib2.dexbacked.DexBackedMethodImplementation.1.1
                    @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                    fun readNextItem(DexReader dexReader) {
                        Int offset = dexReader.getOffset()
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this
                        if (offset >= i) {
                            return endOfData()
                        }
                        Instruction from = DexBackedInstruction.readFrom(DexBackedMethodImplementation.this.dexFile, dexReader)
                        Int offset2 = dexReader.getOffset()
                        if (offset2 > i || offset2 < 0) {
                            throw ExceptionWithContext("The last instruction in method %s is truncated", DexBackedMethodImplementation.this.method)
                        }
                        return from
                    }
                }
            }
        }
    }

    fun getInstructionsSize() {
        return this.dexFile.getDataBuffer().readSmallUint(this.codeOffset + 12)
    }

    fun getInstructionsStartOffset() {
        return this.codeOffset + 16
    }

    public Iterator<String> getParameterNames(DexReader dexReader) {
        return getDebugInfo().getParameterNames(dexReader)
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    fun getRegisterCount() {
        return this.dexFile.getDataBuffer().readUshort(this.codeOffset)
    }

    fun getTriesSize() {
        return this.dexFile.getDataBuffer().readUshort(this.codeOffset + 6)
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public List<? extends DexBackedTryBlock> getTryBlocks() {
        val triesSize = getTriesSize()
        if (triesSize <= 0) {
            return ImmutableList.of()
        }
        val iAlignOffset = AlignmentUtils.alignOffset(getInstructionsStartOffset() + (getInstructionsSize() * 2), 4)
        val i = (triesSize * 8) + iAlignOffset
        return new FixedSizeList<DexBackedTryBlock>() { // from class: org.jf.dexlib2.dexbacked.DexBackedMethodImplementation.2
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(Int i2) {
                return DexBackedTryBlock(DexBackedMethodImplementation.this.dexFile, iAlignOffset + (i2 * 8), i)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return triesSize
            }
        }
    }
}
