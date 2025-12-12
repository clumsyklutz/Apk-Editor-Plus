package org.jf.dexlib2.dexbacked.instruction

import java.util.List
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload

class DexBackedSparseSwitchPayload extends DexBackedInstruction implements SparseSwitchPayload {
    public final Int elementCount

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        super(dexBackedDexFile, Opcode.SPARSE_SWITCH_PAYLOAD, i)
        this.elementCount = dexBackedDexFile.getDataBuffer().readUshort(i + 2)
    }

    @Override // org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (this.elementCount * 4) + 2
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload, org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        return new FixedSizeList<SwitchElement>() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedSparseSwitchPayload.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(final Int i) {
                return SwitchElement() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedSparseSwitchPayload.1.1
                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    fun getKey() {
                        return DexBackedSparseSwitchPayload.this.dexFile.getDataBuffer().readInt(DexBackedSparseSwitchPayload.this.instructionStart + 4 + (i * 4))
                    }

                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    fun getOffset() {
                        DexBuffer dataBuffer = DexBackedSparseSwitchPayload.this.dexFile.getDataBuffer()
                        DexBackedSparseSwitchPayload dexBackedSparseSwitchPayload = DexBackedSparseSwitchPayload.this
                        return dataBuffer.readInt(dexBackedSparseSwitchPayload.instructionStart + 4 + (dexBackedSparseSwitchPayload.elementCount * 4) + (i * 4))
                    }
                }
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedSparseSwitchPayload.this.elementCount
            }
        }
    }
}
