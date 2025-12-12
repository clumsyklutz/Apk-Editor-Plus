package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction35mi
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction35mi extends ImmutableInstruction implements Instruction35mi {
    public static val FORMAT = Format.Format35mi
    public final Int inlineIndex
    public final Int registerC
    public final Int registerCount
    public final Int registerD
    public final Int registerE
    public final Int registerF
    public final Int registerG

    constructor(Opcode opcode, Int i, Int i2, Int i3, Int i4, Int i5, Int i6, Int i7) {
        super(opcode)
        this.registerCount = Preconditions.check35cAnd45ccRegisterCount(i)
        this.registerC = i > 0 ? Preconditions.checkNibbleRegister(i2) : 0
        this.registerD = i > 1 ? Preconditions.checkNibbleRegister(i3) : 0
        this.registerE = i > 2 ? Preconditions.checkNibbleRegister(i4) : 0
        this.registerF = i > 3 ? Preconditions.checkNibbleRegister(i5) : 0
        this.registerG = i > 4 ? Preconditions.checkNibbleRegister(i6) : 0
        this.inlineIndex = Preconditions.checkInlineIndex(i7)
    }

    fun of(Instruction35mi instruction35mi) {
        return instruction35mi is ImmutableInstruction35mi ? (ImmutableInstruction35mi) instruction35mi : ImmutableInstruction35mi(instruction35mi.getOpcode(), instruction35mi.getRegisterCount(), instruction35mi.getRegisterC(), instruction35mi.getRegisterD(), instruction35mi.getRegisterE(), instruction35mi.getRegisterF(), instruction35mi.getRegisterG(), instruction35mi.getInlineIndex())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.InlineIndexInstruction
    fun getInlineIndex() {
        return this.inlineIndex
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterC() {
        return this.registerC
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterD() {
        return this.registerD
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterE() {
        return this.registerE
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterF() {
        return this.registerF
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterG() {
        return this.registerG
    }
}
