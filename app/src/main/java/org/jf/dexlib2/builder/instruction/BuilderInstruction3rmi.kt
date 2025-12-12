package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction3rmi extends BuilderInstruction implements Instruction3rmi {
    public static val FORMAT = Format.Format3rmi
    public final Int inlineIndex
    public final Int registerCount
    public final Int startRegister

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.inlineIndex = i3
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.InlineIndexInstruction
    fun getInlineIndex() {
        return this.inlineIndex
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
