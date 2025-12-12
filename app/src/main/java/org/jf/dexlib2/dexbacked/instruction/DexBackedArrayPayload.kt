package org.jf.dexlib2.dexbacked.instruction

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload
import org.jf.util.ExceptionWithContext

class DexBackedArrayPayload extends DexBackedInstruction implements ArrayPayload {
    public static val OPCODE = Opcode.ARRAY_PAYLOAD
    public final Int elementCount
    public final Int elementWidth

    /* renamed from: org.jf.dexlib2.dexbacked.instruction.DexBackedArrayPayload$1ReturnedList, reason: invalid class name */
    abstract class C1ReturnedList extends FixedSizeList<Number> {
        constructor() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return DexBackedArrayPayload.this.elementCount
        }
    }

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        super(dexBackedDexFile, OPCODE, i)
        Int ushort = dexBackedDexFile.getDataBuffer().readUshort(i + 2)
        if (ushort == 0) {
            this.elementWidth = 1
            this.elementCount = 0
            return
        }
        this.elementWidth = ushort
        Int smallUint = dexBackedDexFile.getDataBuffer().readSmallUint(i + 4)
        this.elementCount = smallUint
        if (ushort * smallUint > 2147483647L) {
            throw ExceptionWithContext("Invalid array-payload instruction: element width*count overflows", new Object[0])
        }
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    public List<Number> getArrayElements() {
        val i = this.instructionStart + 8
        if (this.elementCount == 0) {
            return ImmutableList.of()
        }
        Int i2 = this.elementWidth
        if (i2 == 1) {
            return C1ReturnedList() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedArrayPayload.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
                fun readItem(Int i3) {
                    return Integer.valueOf(DexBackedArrayPayload.this.dexFile.getDataBuffer().readByte(i + i3))
                }
            }
        }
        if (i2 == 2) {
            return C1ReturnedList() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedArrayPayload.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
                fun readItem(Int i3) {
                    return Integer.valueOf(DexBackedArrayPayload.this.dexFile.getDataBuffer().readShort(i + (i3 * 2)))
                }
            }
        }
        if (i2 == 4) {
            return C1ReturnedList() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedArrayPayload.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
                fun readItem(Int i3) {
                    return Integer.valueOf(DexBackedArrayPayload.this.dexFile.getDataBuffer().readInt(i + (i3 * 4)))
                }
            }
        }
        if (i2 == 8) {
            return C1ReturnedList() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedArrayPayload.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
                fun readItem(Int i3) {
                    return Long.valueOf(DexBackedArrayPayload.this.dexFile.getDataBuffer().readLong(i + (i3 * 8)))
                }
            }
        }
        throw ExceptionWithContext("Invalid element width: %d", Integer.valueOf(this.elementWidth))
    }

    @Override // org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction, org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return (((this.elementWidth * this.elementCount) + 1) / 2) + 4
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    fun getElementWidth() {
        return this.elementWidth
    }
}
