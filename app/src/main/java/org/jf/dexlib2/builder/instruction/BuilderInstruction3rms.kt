package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction3rms extends BuilderInstruction implements Instruction3rms {
    public static val FORMAT = Format.Format3rms
    public final Int registerCount
    public final Int startRegister
    public final Int vtableIndex

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.vtableIndex = i3
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    fun getStartRegister() {
        return this.startRegister
    }

    @Override // org.jf.dexlib2.iface.instruction.VtableIndexInstruction
    fun getVtableIndex() {
        return this.vtableIndex
    }
}
