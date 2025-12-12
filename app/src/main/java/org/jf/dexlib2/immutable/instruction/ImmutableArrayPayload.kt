package org.jf.dexlib2.immutable.instruction

import com.google.common.collect.ImmutableList
import java.util.Collection
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload
import org.jf.dexlib2.util.Preconditions

class ImmutableArrayPayload extends ImmutableInstruction implements ArrayPayload {
    public static val OPCODE = Opcode.ARRAY_PAYLOAD
    public final ImmutableList<Number> arrayElements
    public final Int elementWidth

    constructor(Int i, List<Number> list) {
        super(OPCODE)
        this.elementWidth = Preconditions.checkArrayPayloadElementWidth(i)
        this.arrayElements = (ImmutableList) Preconditions.checkArrayPayloadElements(i, list == null ? ImmutableList.of() : ImmutableList.copyOf((Collection) list))
    }

    fun of(ArrayPayload arrayPayload) {
        return arrayPayload is ImmutableArrayPayload ? (ImmutableArrayPayload) arrayPayload : ImmutableArrayPayload(arrayPayload.getElementWidth(), arrayPayload.getArrayElements())
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    public List<Number> getArrayElements() {
        return this.arrayElements
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (((this.elementWidth * this.arrayElements.size()) + 1) / 2) + 4
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    fun getElementWidth() {
        return this.elementWidth
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return OPCODE.format
    }
}
