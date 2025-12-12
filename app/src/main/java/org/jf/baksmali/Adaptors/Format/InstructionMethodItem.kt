package org.jf.baksmali.Adaptors.Format

import androidx.appcompat.R
import java.io.IOException
import org.jf.baksmali.Adaptors.MethodDefinition
import org.jf.baksmali.Adaptors.MethodItem
import org.jf.baksmali.BaksmaliOptions
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.FieldOffsetInstruction
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
import org.jf.dexlib2.iface.instruction.InlineIndexInstruction
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
import org.jf.dexlib2.iface.instruction.VtableIndexInstruction
import org.jf.dexlib2.iface.instruction.WideLiteralInstruction
import org.jf.util.NumberUtils

class InstructionMethodItem<T extends Instruction> extends MethodItem {
    public final T instruction
    public final MethodDefinition methodDef

    /* renamed from: org.jf.baksmali.Adaptors.Format.InstructionMethodItem$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Format
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Opcode

        static {
            Array<Int> iArr = new Int[Format.values().length]
            $SwitchMap$org$jf$dexlib2$Format = iArr
            try {
                iArr[Format.Format10t.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20t.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format30t.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format10x.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11n.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11x.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format12x.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20bc.ordinal()] = 8
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21c.ordinal()] = 9
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31c.ordinal()] = 10
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21ih.ordinal()] = 11
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21lh.ordinal()] = 12
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21s.ordinal()] = 13
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31i.ordinal()] = 14
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format51l.ordinal()] = 15
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21t.ordinal()] = 16
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31t.ordinal()] = 17
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22b.ordinal()] = 18
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22s.ordinal()] = 19
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22c.ordinal()] = 20
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22cs.ordinal()] = 21
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22t.ordinal()] = 22
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22x.ordinal()] = 23
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format32x.ordinal()] = 24
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format23x.ordinal()] = 25
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35c.ordinal()] = 26
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 27
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 28
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rc.ordinal()] = 29
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rmi.ordinal()] = 30
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rms.ordinal()] = 31
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format45cc.ordinal()] = 32
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format4rcc.ordinal()] = 33
            } catch (NoSuchFieldError unused33) {
            }
            Array<Int> iArr2 = new Int[Opcode.values().length]
            $SwitchMap$org$jf$dexlib2$Opcode = iArr2
            try {
                iArr2[Opcode.PACKED_SWITCH.ordinal()] = 1
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPARSE_SWITCH.ordinal()] = 2
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FILL_ARRAY_DATA.ordinal()] = 3
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    public interface Writable {
        Unit write() throws IOException
    }

    constructor(MethodDefinition methodDefinition, Int i, T t) {
        super(i)
        this.methodDef = methodDefinition
        this.instruction = t
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 100.0d
    }

    public final Boolean isAllowedOdex(Opcode opcode) {
        BaksmaliOptions baksmaliOptions = this.methodDef.classDef.options
        if (baksmaliOptions.allowOdex) {
            return true
        }
        if (baksmaliOptions.apiLevel >= 14) {
            return false
        }
        return opcode.isVolatileFieldAccessor() || opcode == Opcode.THROW_VERIFICATION_ERROR
    }

    fun writeCommentIfLikelyDouble(BaksmaliWriter baksmaliWriter) throws IOException {
        writeCommentIfLikelyDouble(baksmaliWriter, ((WideLiteralInstruction) this.instruction).getWideLiteral())
    }

    fun writeCommentIfLikelyDouble(BaksmaliWriter baksmaliWriter, Long j) throws IOException {
        if (NumberUtils.isLikelyDouble(j)) {
            baksmaliWriter.write("    # ")
            Double dLongBitsToDouble = Double.longBitsToDouble(j)
            if (dLongBitsToDouble == Double.POSITIVE_INFINITY) {
                baksmaliWriter.write("Double.POSITIVE_INFINITY")
                return
            }
            if (dLongBitsToDouble == Double.NEGATIVE_INFINITY) {
                baksmaliWriter.write("Double.NEGATIVE_INFINITY")
                return
            }
            if (Double.isNaN(dLongBitsToDouble)) {
                baksmaliWriter.write("Double.NaN")
                return
            }
            if (dLongBitsToDouble == Double.MAX_VALUE) {
                baksmaliWriter.write("Double.MAX_VALUE")
                return
            }
            if (dLongBitsToDouble == 3.141592653589793d) {
                baksmaliWriter.write("Math.PI")
            } else if (dLongBitsToDouble == 2.718281828459045d) {
                baksmaliWriter.write("Math.E")
            } else {
                baksmaliWriter.write(Double.toString(dLongBitsToDouble))
            }
        }
    }

    fun writeCommentIfLikelyFloat(BaksmaliWriter baksmaliWriter) throws IOException {
        writeCommentIfLikelyFloat(baksmaliWriter, ((NarrowLiteralInstruction) this.instruction).getNarrowLiteral())
    }

    fun writeCommentIfLikelyFloat(BaksmaliWriter baksmaliWriter, Int i) throws IOException {
        if (NumberUtils.isLikelyFloat(i)) {
            baksmaliWriter.write("    # ")
            Float fIntBitsToFloat = Float.intBitsToFloat(i)
            if (fIntBitsToFloat == Float.POSITIVE_INFINITY) {
                baksmaliWriter.write("Float.POSITIVE_INFINITY")
                return
            }
            if (fIntBitsToFloat == Float.NEGATIVE_INFINITY) {
                baksmaliWriter.write("Float.NEGATIVE_INFINITY")
                return
            }
            if (Float.isNaN(fIntBitsToFloat)) {
                baksmaliWriter.write("Float.NaN")
                return
            }
            if (fIntBitsToFloat == Float.MAX_VALUE) {
                baksmaliWriter.write("Float.MAX_VALUE")
                return
            }
            if (fIntBitsToFloat == 3.1415927f) {
                baksmaliWriter.write("(Float)Math.PI")
            } else if (fIntBitsToFloat == 2.7182817f) {
                baksmaliWriter.write("(Float)Math.E")
            } else {
                baksmaliWriter.write(Float.toString(fIntBitsToFloat))
                baksmaliWriter.write(R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle)
            }
        }
    }

    fun writeCommentIfResourceId(BaksmaliWriter baksmaliWriter) throws IOException {
        return writeCommentIfResourceId(baksmaliWriter, ((NarrowLiteralInstruction) this.instruction).getNarrowLiteral())
    }

    fun writeCommentIfResourceId(BaksmaliWriter baksmaliWriter, Int i) throws IOException {
        String str = this.methodDef.classDef.options.resourceIds.get(Integer.valueOf(i))
        if (str == null) {
            return false
        }
        baksmaliWriter.write("    # ")
        baksmaliWriter.write(str)
        return true
    }

    fun writeFieldOffset(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("field@0x")
        baksmaliWriter.writeUnsignedLongAsHex(((FieldOffsetInstruction) this.instruction).getFieldOffset())
    }

    fun writeFirstRegister(BaksmaliWriter baksmaliWriter) throws IOException {
        writeRegister(baksmaliWriter, ((OneRegisterInstruction) this.instruction).getRegisterA())
    }

    fun writeInlineIndex(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("inline@")
        baksmaliWriter.writeSignedIntAsDec(((InlineIndexInstruction) this.instruction).getInlineIndex())
    }

    fun writeInvokeRangeRegisters(BaksmaliWriter baksmaliWriter) throws IOException {
        RegisterRangeInstruction registerRangeInstruction = (RegisterRangeInstruction) this.instruction
        if (registerRangeInstruction.getRegisterCount() == 0) {
            baksmaliWriter.write("{}")
        } else {
            this.methodDef.registerFormatter.writeRegisterRange(baksmaliWriter, registerRangeInstruction.getStartRegister(), (r1 + r0) - 1)
        }
    }

    fun writeInvokeRegisters(BaksmaliWriter baksmaliWriter) throws IOException {
        FiveRegisterInstruction fiveRegisterInstruction = (FiveRegisterInstruction) this.instruction
        Int registerCount = fiveRegisterInstruction.getRegisterCount()
        baksmaliWriter.write(123)
        if (registerCount == 1) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC())
        } else if (registerCount == 2) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD())
        } else if (registerCount == 3) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterE())
        } else if (registerCount == 4) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterE())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterF())
        } else if (registerCount == 5) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterE())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterF())
            baksmaliWriter.write(", ")
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterG())
        }
        baksmaliWriter.write(125)
    }

    fun writeLiteral(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.writeSignedIntOrLongTo(((WideLiteralInstruction) this.instruction).getWideLiteral())
    }

    fun writeOpcode(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(this.instruction.getOpcode().name)
    }

    fun writeRegister(BaksmaliWriter baksmaliWriter, Int i) throws IOException {
        this.methodDef.registerFormatter.writeTo(baksmaliWriter, i)
    }

    fun writeSecondRegister(BaksmaliWriter baksmaliWriter) throws IOException {
        writeRegister(baksmaliWriter, ((TwoRegisterInstruction) this.instruction).getRegisterB())
    }

    fun writeTargetLabel(BaksmaliWriter baksmaliWriter) throws IOException {
        throw RuntimeException()
    }

    fun writeThirdRegister(BaksmaliWriter baksmaliWriter) throws IOException {
        writeRegister(baksmaliWriter, ((ThreeRegisterInstruction) this.instruction).getRegisterC())
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f2  */
    @Override // org.jf.baksmali.Adaptors.MethodItem
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun writeTo(final org.jf.baksmali.formatter.BaksmaliWriter r13) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 834
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.baksmali.Adaptors.Format.InstructionMethodItem.writeTo(org.jf.baksmali.formatter.BaksmaliWriter):Boolean")
    }

    fun writeVtableIndex(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("vtable@")
        baksmaliWriter.writeSignedIntAsDec(((VtableIndexInstruction) this.instruction).getVtableIndex())
    }
}
