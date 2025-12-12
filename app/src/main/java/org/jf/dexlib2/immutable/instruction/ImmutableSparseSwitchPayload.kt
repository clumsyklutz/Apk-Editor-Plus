package org.jf.dexlib2.immutable.instruction

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload

class ImmutableSparseSwitchPayload extends ImmutableInstruction implements SparseSwitchPayload {
    public static val OPCODE = Opcode.SPARSE_SWITCH_PAYLOAD
    public final ImmutableList<? extends ImmutableSwitchElement> switchElements

    constructor(List<? extends SwitchElement> list) {
        super(OPCODE)
        this.switchElements = ImmutableSwitchElement.immutableListOf(list)
    }

    fun of(SparseSwitchPayload sparseSwitchPayload) {
        return sparseSwitchPayload is ImmutableSparseSwitchPayload ? (ImmutableSparseSwitchPayload) sparseSwitchPayload : ImmutableSparseSwitchPayload(sparseSwitchPayload.getSwitchElements())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (this.switchElements.size() * 4) + 2
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return OPCODE.format
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload, org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        return this.switchElements
    }
}
