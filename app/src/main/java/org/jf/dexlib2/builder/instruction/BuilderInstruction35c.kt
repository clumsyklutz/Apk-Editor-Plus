package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction35c
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction35c extends BuilderInstruction implements Instruction35c {
    public static val FORMAT = Format.Format35c
    public final Reference reference
    public final Int registerC
    public final Int registerCount
    public final Int registerD
    public final Int registerE
    public final Int registerF
    public final Int registerG

    constructor(Opcode opcode, Int i, Int i2, Int i3, Int i4, Int i5, Int i6, Reference reference) {
        super(opcode)
        this.registerCount = Preconditions.check35cAnd45ccRegisterCount(i)
        this.registerC = i > 0 ? Preconditions.checkNibbleRegister(i2) : 0
        this.registerD = i > 1 ? Preconditions.checkNibbleRegister(i3) : 0
        this.registerE = i > 2 ? Preconditions.checkNibbleRegister(i4) : 0
        this.registerF = i > 3 ? Preconditions.checkNibbleRegister(i5) : 0
        this.registerG = i > 4 ? Preconditions.checkNibbleRegister(i6) : 0
        this.reference = reference
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReference() {
        return this.reference
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReferenceType() {
        return this.opcode.referenceType
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
