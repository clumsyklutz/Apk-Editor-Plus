package org.jf.baksmali.Adaptors

import java.io.IOException
import java.util.BitSet
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.analysis.AnalyzedInstruction
import org.jf.dexlib2.analysis.RegisterType

class PostInstructionRegisterInfoMethodItem extends MethodItem {
    public final AnalyzedInstruction analyzedInstruction
    public final RegisterFormatter registerFormatter

    constructor(RegisterFormatter registerFormatter, AnalyzedInstruction analyzedInstruction, Int i) {
        super(i)
        this.registerFormatter = registerFormatter
        this.analyzedInstruction = analyzedInstruction
    }

    public final Unit addDestRegs(BitSet bitSet, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            if (!this.analyzedInstruction.getPreInstructionRegisterType(i2).equals(this.analyzedInstruction.getPostInstructionRegisterType(i2))) {
                bitSet.set(i2)
            }
        }
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 100.1d
    }

    public final Boolean writeRegisterInfo(BaksmaliWriter baksmaliWriter, BitSet bitSet) throws IOException {
        Int iNextSetBit = bitSet.nextSetBit(0)
        if (iNextSetBit < 0) {
            return false
        }
        baksmaliWriter.write(35)
        while (iNextSetBit >= 0) {
            RegisterType postInstructionRegisterType = this.analyzedInstruction.getPostInstructionRegisterType(iNextSetBit)
            this.registerFormatter.writeTo(baksmaliWriter, iNextSetBit)
            baksmaliWriter.write(61)
            postInstructionRegisterType.writeTo(baksmaliWriter)
            baksmaliWriter.write(59)
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1)
        }
        return true
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        Int i = this.registerFormatter.options.registerInfo
        Int registerCount = this.analyzedInstruction.getRegisterCount()
        BitSet bitSet = BitSet(registerCount)
        if ((i & 1) != 0 || (i & 4) != 0) {
            bitSet.set(0, registerCount)
        } else if ((i & 16) != 0) {
            addDestRegs(bitSet, registerCount)
        }
        return writeRegisterInfo(baksmaliWriter, bitSet)
    }
}
