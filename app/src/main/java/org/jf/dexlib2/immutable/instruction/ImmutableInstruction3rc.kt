package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.immutable.reference.ImmutableReference
import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction3rc extends ImmutableInstruction implements Instruction3rc {
    public static val FORMAT = Format.Format3rc
    public final ImmutableReference reference
    public final Int registerCount
    public final Int startRegister

    constructor(Opcode opcode, Int i, Int i2, Reference reference) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference)
    }

    fun of(Instruction3rc instruction3rc) {
        return instruction3rc is ImmutableInstruction3rc ? (ImmutableInstruction3rc) instruction3rc : ImmutableInstruction3rc(instruction3rc.getOpcode(), instruction3rc.getStartRegister(), instruction3rc.getRegisterCount(), instruction3rc.getReference())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
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

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    fun getStartRegister() {
        return this.startRegister
    }
}
