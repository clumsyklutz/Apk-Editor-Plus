package org.jf.dexlib2.util

import java.util.Arrays
import java.util.List
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.util.ExceptionWithContext

class InstructionOffsetMap {
    public final Array<Int> instructionCodeOffsets

    public static class InvalidInstructionIndex extends ExceptionWithContext {
        constructor(Int i) {
            super("Instruction index out of bounds: %d", Integer.valueOf(i))
        }
    }

    public static class InvalidInstructionOffset extends ExceptionWithContext {
        constructor(Int i) {
            super("No instruction at offset %d", Integer.valueOf(i))
        }
    }

    constructor(List<? extends Instruction> list) {
        this.instructionCodeOffsets = new Int[list.size()]
        Int codeUnits = 0
        for (Int i = 0; i < list.size(); i++) {
            this.instructionCodeOffsets[i] = codeUnits
            codeUnits += list.get(i).getCodeUnits()
        }
    }

    fun getInstructionCodeOffset(Int i) {
        if (i >= 0) {
            Array<Int> iArr = this.instructionCodeOffsets
            if (i < iArr.length) {
                return iArr[i]
            }
        }
        throw InvalidInstructionIndex(i)
    }

    fun getInstructionIndexAtCodeOffset(Int i) {
        return getInstructionIndexAtCodeOffset(i, true)
    }

    fun getInstructionIndexAtCodeOffset(Int i, Boolean z) {
        Int iBinarySearch = Arrays.binarySearch(this.instructionCodeOffsets, i)
        if (iBinarySearch >= 0) {
            return iBinarySearch
        }
        if (z) {
            throw InvalidInstructionOffset(i)
        }
        return (iBinarySearch ^ (-1)) - 1
    }
}
