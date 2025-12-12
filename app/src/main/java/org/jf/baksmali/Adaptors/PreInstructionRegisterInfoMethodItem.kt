package org.jf.baksmali.Adaptors

import java.io.IOException
import java.util.BitSet
import java.util.Iterator
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.analysis.AnalyzedInstruction
import org.jf.dexlib2.analysis.MethodAnalyzer
import org.jf.dexlib2.analysis.RegisterType
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction

class PreInstructionRegisterInfoMethodItem extends MethodItem {
    public final AnalyzedInstruction analyzedInstruction
    public final MethodAnalyzer methodAnalyzer
    public final RegisterFormatter registerFormatter
    public final Int registerInfo

    constructor(Int i, MethodAnalyzer methodAnalyzer, RegisterFormatter registerFormatter, AnalyzedInstruction analyzedInstruction, Int i2) {
        super(i2)
        this.registerInfo = i
        this.methodAnalyzer = methodAnalyzer
        this.registerFormatter = registerFormatter
        this.analyzedInstruction = analyzedInstruction
    }

    public final Unit addArgsRegs(BitSet bitSet) {
        if (this.analyzedInstruction.getInstruction() instanceof RegisterRangeInstruction) {
            RegisterRangeInstruction registerRangeInstruction = (RegisterRangeInstruction) this.analyzedInstruction.getInstruction()
            bitSet.set(registerRangeInstruction.getStartRegister(), registerRangeInstruction.getStartRegister() + registerRangeInstruction.getRegisterCount())
            return
        }
        if (this.analyzedInstruction.getInstruction() instanceof FiveRegisterInstruction) {
            FiveRegisterInstruction fiveRegisterInstruction = (FiveRegisterInstruction) this.analyzedInstruction.getInstruction()
            Int registerCount = fiveRegisterInstruction.getRegisterCount()
            if (registerCount != 1) {
                if (registerCount != 2) {
                    if (registerCount != 3) {
                        if (registerCount != 4) {
                            if (registerCount != 5) {
                                return
                            } else {
                                bitSet.set(fiveRegisterInstruction.getRegisterG())
                            }
                        }
                        bitSet.set(fiveRegisterInstruction.getRegisterF())
                    }
                    bitSet.set(fiveRegisterInstruction.getRegisterE())
                }
                bitSet.set(fiveRegisterInstruction.getRegisterD())
            }
            bitSet.set(fiveRegisterInstruction.getRegisterC())
            return
        }
        if (this.analyzedInstruction.getInstruction() instanceof ThreeRegisterInstruction) {
            ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction) this.analyzedInstruction.getInstruction()
            bitSet.set(threeRegisterInstruction.getRegisterA())
            bitSet.set(threeRegisterInstruction.getRegisterB())
            bitSet.set(threeRegisterInstruction.getRegisterC())
            return
        }
        if (this.analyzedInstruction.getInstruction() instanceof TwoRegisterInstruction) {
            TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction) this.analyzedInstruction.getInstruction()
            bitSet.set(twoRegisterInstruction.getRegisterA())
            bitSet.set(twoRegisterInstruction.getRegisterB())
        } else if (this.analyzedInstruction.getInstruction() instanceof OneRegisterInstruction) {
            bitSet.set(((OneRegisterInstruction) this.analyzedInstruction.getInstruction()).getRegisterA())
        }
    }

    public final Unit addMergeRegs(BitSet bitSet, Int i) {
        if (this.analyzedInstruction.getPredecessorCount() <= 1) {
            return
        }
        for (Int i2 = 0; i2 < i; i2++) {
            RegisterType preInstructionRegisterType = this.analyzedInstruction.getPreInstructionRegisterType(i2)
            Iterator<AnalyzedInstruction> it = this.analyzedInstruction.getPredecessors().iterator()
            while (it.hasNext()) {
                RegisterType predecessorRegisterType = this.analyzedInstruction.getPredecessorRegisterType(it.next(), i2)
                if (predecessorRegisterType.category != 0 && !predecessorRegisterType.equals(preInstructionRegisterType)) {
                    bitSet.set(i2)
                }
            }
        }
    }

    public final Unit addParamRegs(BitSet bitSet, Int i) {
        bitSet.set(i - this.methodAnalyzer.getParamRegisterCount(), i)
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 99.9d
    }

    public final Unit writeFullMerge(BaksmaliWriter baksmaliWriter, Int i) throws IOException {
        this.registerFormatter.writeTo(baksmaliWriter, i)
        baksmaliWriter.write(61)
        this.analyzedInstruction.getPreInstructionRegisterType(i).writeTo(baksmaliWriter)
        baksmaliWriter.write(":merge{")
        Boolean z = true
        for (AnalyzedInstruction analyzedInstruction : this.analyzedInstruction.getPredecessors()) {
            RegisterType predecessorRegisterType = this.analyzedInstruction.getPredecessorRegisterType(analyzedInstruction, i)
            if (!z) {
                baksmaliWriter.write(44)
            }
            if (analyzedInstruction.getInstructionIndex() == -1) {
                baksmaliWriter.write("Start:")
            } else {
                baksmaliWriter.write("0x")
                baksmaliWriter.writeUnsignedLongAsHex(this.methodAnalyzer.getInstructionAddress(analyzedInstruction))
                baksmaliWriter.write(58)
            }
            predecessorRegisterType.writeTo(baksmaliWriter)
            z = false
        }
        baksmaliWriter.write(125)
    }

    public final Boolean writeRegisterInfo(BaksmaliWriter baksmaliWriter, BitSet bitSet, BitSet bitSet2) throws IOException {
        Int iNextSetBit = bitSet.nextSetBit(0)
        if (iNextSetBit < 0) {
            return false
        }
        baksmaliWriter.write(35)
        Boolean z = false
        Boolean z2 = true
        while (iNextSetBit >= 0) {
            if (bitSet2 != null && bitSet2.get(iNextSetBit)) {
                if (!z2) {
                    baksmaliWriter.write(10)
                    baksmaliWriter.write(35)
                }
                writeFullMerge(baksmaliWriter, iNextSetBit)
                z = true
            } else {
                if (z) {
                    baksmaliWriter.write(10)
                    baksmaliWriter.write(35)
                    z = false
                }
                RegisterType preInstructionRegisterType = this.analyzedInstruction.getPreInstructionRegisterType(iNextSetBit)
                this.registerFormatter.writeTo(baksmaliWriter, iNextSetBit)
                baksmaliWriter.write(61)
                preInstructionRegisterType.writeTo(baksmaliWriter)
                baksmaliWriter.write(59)
            }
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1)
            z2 = false
        }
        return true
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0065  */
    @Override // org.jf.baksmali.Adaptors.MethodItem
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun writeTo(org.jf.baksmali.formatter.BaksmaliWriter r7) throws java.io.IOException {
        /*
            r6 = this
            org.jf.dexlib2.analysis.AnalyzedInstruction r0 = r6.analyzedInstruction
            Int r0 = r0.getRegisterCount()
            java.util.BitSet r1 = new java.util.BitSet
            r1.<init>(r0)
            Int r2 = r6.registerInfo
            r3 = r2 & 1
            r4 = 0
            r5 = 0
            if (r3 == 0) goto L17
            r1.set(r4, r0)
            goto L4f
        L17:
            r3 = r2 & 2
            if (r3 == 0) goto L1f
            r1.set(r4, r0)
            goto L4f
        L1f:
            r2 = r2 & 8
            if (r2 == 0) goto L26
            r6.addArgsRegs(r1)
        L26:
            Int r2 = r6.registerInfo
            r3 = r2 & 32
            if (r3 == 0) goto L40
            org.jf.dexlib2.analysis.AnalyzedInstruction r2 = r6.analyzedInstruction
            Boolean r2 = r2.isBeginningInstruction()
            if (r2 == 0) goto L37
            r6.addParamRegs(r1, r0)
        L37:
            java.util.BitSet r2 = new java.util.BitSet
            r2.<init>(r0)
            r6.addMergeRegs(r2, r0)
            goto L50
        L40:
            r2 = r2 & 64
            if (r2 == 0) goto L4f
            org.jf.dexlib2.analysis.AnalyzedInstruction r2 = r6.analyzedInstruction
            Boolean r2 = r2.isBeginningInstruction()
            if (r2 == 0) goto L4f
            r6.addParamRegs(r1, r0)
        L4f:
            r2 = r5
        L50:
            Int r3 = r6.registerInfo
            r3 = r3 & 64
            if (r3 == 0) goto L65
            if (r2 != 0) goto L60
            java.util.BitSet r2 = new java.util.BitSet
            r2.<init>(r0)
            r6.addMergeRegs(r2, r0)
        L60:
            r5 = r2
            r1.or(r5)
            goto L6c
        L65:
            if (r2 == 0) goto L6b
            r1.or(r2)
            goto L6c
        L6b:
            r5 = r2
        L6c:
            Boolean r7 = r6.writeRegisterInfo(r7, r1, r5)
            return r7
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.baksmali.Adaptors.PreInstructionRegisterInfoMethodItem.writeTo(org.jf.baksmali.formatter.BaksmaliWriter):Boolean")
    }
}
