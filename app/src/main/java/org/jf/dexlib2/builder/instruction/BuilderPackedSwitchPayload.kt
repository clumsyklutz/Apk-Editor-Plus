package org.jf.dexlib2.builder.instruction

import com.google.common.collect.ImmutableList
import com.google.common.collect.Lists
import java.util.Iterator
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderSwitchPayload
import org.jf.dexlib2.builder.Label
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload

class BuilderPackedSwitchPayload extends BuilderSwitchPayload implements PackedSwitchPayload {
    public static val OPCODE = Opcode.PACKED_SWITCH_PAYLOAD
    public final List<BuilderSwitchElement> switchElements

    constructor(Int i, List<? extends Label> list) {
        super(OPCODE)
        if (list == null) {
            this.switchElements = ImmutableList.of()
            return
        }
        this.switchElements = Lists.newArrayList()
        Iterator<? extends Label> it = list.iterator()
        while (it.hasNext()) {
            this.switchElements.add(BuilderSwitchElement(this, i, it.next()))
            i++
        }
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (this.switchElements.size() * 2) + 4
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return OPCODE.format
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload, org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
    public List<BuilderSwitchElement> getSwitchElements() {
        return this.switchElements
    }
}
