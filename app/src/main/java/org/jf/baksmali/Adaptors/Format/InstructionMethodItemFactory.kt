package org.jf.baksmali.Adaptors.Format

import org.jf.baksmali.Adaptors.MethodDefinition
import org.jf.dexlib2.Format
import org.jf.dexlib2.analysis.UnresolvedOdexInstruction
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.OffsetInstruction
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload

class InstructionMethodItemFactory {

    /* renamed from: org.jf.baksmali.Adaptors.Format.InstructionMethodItemFactory$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Format

        static {
            Array<Int> iArr = new Int[Format.values().length]
            $SwitchMap$org$jf$dexlib2$Format = iArr
            try {
                iArr[Format.ArrayPayload.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.PackedSwitchPayload.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.SparseSwitchPayload.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    fun makeInstructionFormatMethodItem(MethodDefinition methodDefinition, Int i, Instruction instruction) {
        if (instruction is OffsetInstruction) {
            return OffsetInstructionFormatMethodItem(methodDefinition.classDef.options, methodDefinition, i, (OffsetInstruction) instruction)
        }
        if (instruction is UnresolvedOdexInstruction) {
            return UnresolvedOdexInstructionMethodItem(methodDefinition, i, (UnresolvedOdexInstruction) instruction)
        }
        Int i2 = AnonymousClass1.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? InstructionMethodItem(methodDefinition, i, instruction) : SparseSwitchMethodItem(methodDefinition, i, (SparseSwitchPayload) instruction) : PackedSwitchMethodItem(methodDefinition, i, (PackedSwitchPayload) instruction) : ArrayDataMethodItem(methodDefinition, i, (ArrayPayload) instruction)
    }
}
