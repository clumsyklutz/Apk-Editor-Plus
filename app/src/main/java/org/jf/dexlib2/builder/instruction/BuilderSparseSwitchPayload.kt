package org.jf.dexlib2.builder.instruction

import com.google.common.base.Function
import com.google.common.collect.ImmutableList
import com.google.common.collect.Lists
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderSwitchPayload
import org.jf.dexlib2.builder.SwitchLabelElement
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload

class BuilderSparseSwitchPayload extends BuilderSwitchPayload implements SparseSwitchPayload {
    public static val OPCODE = Opcode.SPARSE_SWITCH_PAYLOAD
    public final List<BuilderSwitchElement> switchElements

    constructor(List<? extends SwitchLabelElement> list) {
        super(OPCODE)
        if (list == null) {
            this.switchElements = ImmutableList.of()
        } else {
            this.switchElements = Lists.transform(list, new Function<SwitchLabelElement, BuilderSwitchElement>() { // from class: org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload.1
                @Override // com.google.common.base.Function
                fun apply(SwitchLabelElement switchLabelElement) {
                    return BuilderSwitchElement(BuilderSparseSwitchPayload.this, switchLabelElement.key, switchLabelElement.target)
                }
            })
        }
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (this.switchElements.size() * 4) + 2
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
