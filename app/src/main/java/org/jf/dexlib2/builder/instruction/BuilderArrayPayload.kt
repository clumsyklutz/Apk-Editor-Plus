package org.jf.dexlib2.builder.instruction

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload

class BuilderArrayPayload extends BuilderInstruction implements ArrayPayload {
    public static val OPCODE = Opcode.ARRAY_PAYLOAD
    public final List<Number> arrayElements
    public final Int elementWidth

    constructor(Int i, List<Number> list) {
        super(OPCODE)
        this.elementWidth = i
        this.arrayElements = list == null ? ImmutableList.of() : list
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    public List<Number> getArrayElements() {
        return this.arrayElements
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (((this.elementWidth * this.arrayElements.size()) + 1) / 2) + 4
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    fun getElementWidth() {
        return this.elementWidth
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return OPCODE.format
    }
}
