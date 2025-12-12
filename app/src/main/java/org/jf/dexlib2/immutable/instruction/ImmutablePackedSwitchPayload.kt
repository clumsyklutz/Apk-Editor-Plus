package org.jf.dexlib2.immutable.instruction

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
import org.jf.dexlib2.util.Preconditions

class ImmutablePackedSwitchPayload extends ImmutableInstruction implements PackedSwitchPayload {
    public static val OPCODE = Opcode.PACKED_SWITCH_PAYLOAD
    public final ImmutableList<? extends ImmutableSwitchElement> switchElements

    constructor(List<? extends SwitchElement> list) {
        super(OPCODE)
        this.switchElements = (ImmutableList) Preconditions.checkSequentialOrderedKeys(ImmutableSwitchElement.immutableListOf(list))
    }

    fun of(PackedSwitchPayload packedSwitchPayload) {
        return packedSwitchPayload is ImmutablePackedSwitchPayload ? (ImmutablePackedSwitchPayload) packedSwitchPayload : ImmutablePackedSwitchPayload(packedSwitchPayload.getSwitchElements())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (this.switchElements.size() * 2) + 4
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return OPCODE.format
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        return this.switchElements
    }
}
