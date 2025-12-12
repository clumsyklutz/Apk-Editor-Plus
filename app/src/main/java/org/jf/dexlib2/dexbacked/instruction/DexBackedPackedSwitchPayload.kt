package org.jf.dexlib2.dexbacked.instruction

import java.util.List
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload

class DexBackedPackedSwitchPayload extends DexBackedInstruction implements PackedSwitchPayload {
    public final Int elementCount

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        super(dexBackedDexFile, Opcode.PACKED_SWITCH_PAYLOAD, i)
        this.elementCount = dexBackedDexFile.getDataBuffer().readUshort(i + 2)
    }

    @Override // org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (this.elementCount * 2) + 4
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        val i = this.dexFile.getDataBuffer().readInt(this.instructionStart + 4)
        return new FixedSizeList<SwitchElement>() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedPackedSwitchPayload.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(final Int i2) {
                return SwitchElement() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedPackedSwitchPayload.1.1
                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    fun getKey() {
                        return i + i2
                    }

                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    fun getOffset() {
                        return DexBackedPackedSwitchPayload.this.dexFile.getDataBuffer().readInt(DexBackedPackedSwitchPayload.this.instructionStart + 8 + (i2 * 4))
                    }
                }
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedPackedSwitchPayload.this.elementCount
            }
        }
    }
}
