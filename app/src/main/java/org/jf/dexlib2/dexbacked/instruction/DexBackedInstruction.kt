package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.util.ExceptionWithContext

abstract class DexBackedInstruction implements Instruction {
    public final DexBackedDexFile dexFile
    public final Int instructionStart
    public final Opcode opcode

    /* renamed from: org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Format

        static {
            Array<Int> iArr = new Int[Format.values().length]
            $SwitchMap$org$jf$dexlib2$Format = iArr
            try {
                iArr[Format.Format10t.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format10x.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11n.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11x.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format12x.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20bc.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20t.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21c.ordinal()] = 8
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21ih.ordinal()] = 9
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21lh.ordinal()] = 10
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21s.ordinal()] = 11
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21t.ordinal()] = 12
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22b.ordinal()] = 13
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22c.ordinal()] = 14
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22cs.ordinal()] = 15
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22s.ordinal()] = 16
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22t.ordinal()] = 17
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22x.ordinal()] = 18
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format23x.ordinal()] = 19
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format30t.ordinal()] = 20
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31c.ordinal()] = 21
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31i.ordinal()] = 22
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31t.ordinal()] = 23
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format32x.ordinal()] = 24
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35c.ordinal()] = 25
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 26
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 27
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rc.ordinal()] = 28
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rmi.ordinal()] = 29
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rms.ordinal()] = 30
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format45cc.ordinal()] = 31
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format4rcc.ordinal()] = 32
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format51l.ordinal()] = 33
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.PackedSwitchPayload.ordinal()] = 34
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.SparseSwitchPayload.ordinal()] = 35
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.ArrayPayload.ordinal()] = 36
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        this.dexFile = dexBackedDexFile
        this.opcode = opcode
        this.instructionStart = i
    }

    fun buildInstruction(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        if (opcode == null) {
            return DexBackedUnknownInstruction(dexBackedDexFile, i)
        }
        switch (AnonymousClass1.$SwitchMap$org$jf$dexlib2$Format[opcode.format.ordinal()]) {
            case 1:
                return DexBackedInstruction10t(dexBackedDexFile, opcode, i)
            case 2:
                return DexBackedInstruction10x(dexBackedDexFile, opcode, i)
            case 3:
                return DexBackedInstruction11n(dexBackedDexFile, opcode, i)
            case 4:
                return DexBackedInstruction11x(dexBackedDexFile, opcode, i)
            case 5:
                return DexBackedInstruction12x(dexBackedDexFile, opcode, i)
            case 6:
                return DexBackedInstruction20bc(dexBackedDexFile, opcode, i)
            case 7:
                return DexBackedInstruction20t(dexBackedDexFile, opcode, i)
            case 8:
                return DexBackedInstruction21c(dexBackedDexFile, opcode, i)
            case 9:
                return DexBackedInstruction21ih(dexBackedDexFile, opcode, i)
            case 10:
                return DexBackedInstruction21lh(dexBackedDexFile, opcode, i)
            case 11:
                return DexBackedInstruction21s(dexBackedDexFile, opcode, i)
            case 12:
                return DexBackedInstruction21t(dexBackedDexFile, opcode, i)
            case 13:
                return DexBackedInstruction22b(dexBackedDexFile, opcode, i)
            case 14:
                return DexBackedInstruction22c(dexBackedDexFile, opcode, i)
            case 15:
                return DexBackedInstruction22cs(dexBackedDexFile, opcode, i)
            case 16:
                return DexBackedInstruction22s(dexBackedDexFile, opcode, i)
            case 17:
                return DexBackedInstruction22t(dexBackedDexFile, opcode, i)
            case 18:
                return DexBackedInstruction22x(dexBackedDexFile, opcode, i)
            case 19:
                return DexBackedInstruction23x(dexBackedDexFile, opcode, i)
            case 20:
                return DexBackedInstruction30t(dexBackedDexFile, opcode, i)
            case 21:
                return DexBackedInstruction31c(dexBackedDexFile, opcode, i)
            case 22:
                return DexBackedInstruction31i(dexBackedDexFile, opcode, i)
            case 23:
                return DexBackedInstruction31t(dexBackedDexFile, opcode, i)
            case 24:
                return DexBackedInstruction32x(dexBackedDexFile, opcode, i)
            case 25:
                return DexBackedInstruction35c(dexBackedDexFile, opcode, i)
            case 26:
                return DexBackedInstruction35ms(dexBackedDexFile, opcode, i)
            case 27:
                return DexBackedInstruction35mi(dexBackedDexFile, opcode, i)
            case 28:
                return DexBackedInstruction3rc(dexBackedDexFile, opcode, i)
            case 29:
                return DexBackedInstruction3rmi(dexBackedDexFile, opcode, i)
            case 30:
                return DexBackedInstruction3rms(dexBackedDexFile, opcode, i)
            case 31:
                return DexBackedInstruction45cc(dexBackedDexFile, opcode, i)
            case 32:
                return DexBackedInstruction4rcc(dexBackedDexFile, opcode, i)
            case 33:
                return DexBackedInstruction51l(dexBackedDexFile, opcode, i)
            case 34:
                return DexBackedPackedSwitchPayload(dexBackedDexFile, i)
            case 35:
                return DexBackedSparseSwitchPayload(dexBackedDexFile, i)
            case 36:
                return DexBackedArrayPayload(dexBackedDexFile, i)
            default:
                throw ExceptionWithContext("Unexpected opcode format: %s", opcode.format.toString())
        }
    }

    fun readFrom(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        Int iPeekUbyte = dexReader.peekUbyte()
        if (iPeekUbyte == 0) {
            iPeekUbyte = dexReader.peekUshort()
        }
        DexBackedInstruction dexBackedInstructionBuildInstruction = buildInstruction(dexBackedDexFile, dexBackedDexFile.getOpcodes().getOpcodeByValue(iPeekUbyte), ((dexReader.getOffset() + dexReader.dexBuf.getBaseOffset()) - dexBackedDexFile.getBuffer().getBaseOffset()) - dexBackedDexFile.getBaseDataOffset())
        dexReader.moveRelative(dexBackedInstructionBuildInstruction.getCodeUnits() * 2)
        return dexBackedInstructionBuildInstruction
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return this.opcode.format.size / 2
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getOpcode() {
        return this.opcode
    }
}
