package org.jf.dexlib2.immutable.instruction

import com.google.common.collect.ImmutableList
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload
import org.jf.dexlib2.iface.instruction.formats.Instruction10t
import org.jf.dexlib2.iface.instruction.formats.Instruction10x
import org.jf.dexlib2.iface.instruction.formats.Instruction11n
import org.jf.dexlib2.iface.instruction.formats.Instruction11x
import org.jf.dexlib2.iface.instruction.formats.Instruction12x
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc
import org.jf.dexlib2.iface.instruction.formats.Instruction20t
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.instruction.formats.Instruction21ih
import org.jf.dexlib2.iface.instruction.formats.Instruction21lh
import org.jf.dexlib2.iface.instruction.formats.Instruction21s
import org.jf.dexlib2.iface.instruction.formats.Instruction21t
import org.jf.dexlib2.iface.instruction.formats.Instruction22b
import org.jf.dexlib2.iface.instruction.formats.Instruction22c
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs
import org.jf.dexlib2.iface.instruction.formats.Instruction22s
import org.jf.dexlib2.iface.instruction.formats.Instruction22t
import org.jf.dexlib2.iface.instruction.formats.Instruction22x
import org.jf.dexlib2.iface.instruction.formats.Instruction23x
import org.jf.dexlib2.iface.instruction.formats.Instruction30t
import org.jf.dexlib2.iface.instruction.formats.Instruction31c
import org.jf.dexlib2.iface.instruction.formats.Instruction31i
import org.jf.dexlib2.iface.instruction.formats.Instruction31t
import org.jf.dexlib2.iface.instruction.formats.Instruction32x
import org.jf.dexlib2.iface.instruction.formats.Instruction35c
import org.jf.dexlib2.iface.instruction.formats.Instruction35mi
import org.jf.dexlib2.iface.instruction.formats.Instruction35ms
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc
import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms
import org.jf.dexlib2.iface.instruction.formats.Instruction45cc
import org.jf.dexlib2.iface.instruction.formats.Instruction4rcc
import org.jf.dexlib2.iface.instruction.formats.Instruction51l
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload
import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction
import org.jf.dexlib2.util.Preconditions
import org.jf.util.ImmutableConverter

abstract class ImmutableInstruction implements Instruction {
    public static final ImmutableConverter<ImmutableInstruction, Instruction> CONVERTER = new ImmutableConverter<ImmutableInstruction, Instruction>() { // from class: org.jf.dexlib2.immutable.instruction.ImmutableInstruction.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(Instruction instruction) {
            return instruction is ImmutableInstruction
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(Instruction instruction) {
            return ImmutableInstruction.of(instruction)
        }
    }
    public final Opcode opcode

    /* renamed from: org.jf.dexlib2.immutable.instruction.ImmutableInstruction$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
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
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 26
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 27
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

    constructor(Opcode opcode) {
        Preconditions.checkFormat(opcode, getFormat())
        this.opcode = opcode
    }

    public static ImmutableList<ImmutableInstruction> immutableListOf(Iterable<? extends Instruction> iterable) {
        return CONVERTER.toList(iterable)
    }

    fun of(Instruction instruction) {
        if (instruction is ImmutableInstruction) {
            return (ImmutableInstruction) instruction
        }
        switch (AnonymousClass2.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
            case 1:
                return ImmutableInstruction10t.of((Instruction10t) instruction)
            case 2:
                return instruction is UnknownInstruction ? ImmutableUnknownInstruction.of((UnknownInstruction) instruction) : ImmutableInstruction10x.of((Instruction10x) instruction)
            case 3:
                return ImmutableInstruction11n.of((Instruction11n) instruction)
            case 4:
                return ImmutableInstruction11x.of((Instruction11x) instruction)
            case 5:
                return ImmutableInstruction12x.of((Instruction12x) instruction)
            case 6:
                return ImmutableInstruction20bc.of((Instruction20bc) instruction)
            case 7:
                return ImmutableInstruction20t.of((Instruction20t) instruction)
            case 8:
                return ImmutableInstruction21c.of((Instruction21c) instruction)
            case 9:
                return ImmutableInstruction21ih.of((Instruction21ih) instruction)
            case 10:
                return ImmutableInstruction21lh.of((Instruction21lh) instruction)
            case 11:
                return ImmutableInstruction21s.of((Instruction21s) instruction)
            case 12:
                return ImmutableInstruction21t.of((Instruction21t) instruction)
            case 13:
                return ImmutableInstruction22b.of((Instruction22b) instruction)
            case 14:
                return ImmutableInstruction22c.of((Instruction22c) instruction)
            case 15:
                return ImmutableInstruction22cs.of((Instruction22cs) instruction)
            case 16:
                return ImmutableInstruction22s.of((Instruction22s) instruction)
            case 17:
                return ImmutableInstruction22t.of((Instruction22t) instruction)
            case 18:
                return ImmutableInstruction22x.of((Instruction22x) instruction)
            case 19:
                return ImmutableInstruction23x.of((Instruction23x) instruction)
            case 20:
                return ImmutableInstruction30t.of((Instruction30t) instruction)
            case 21:
                return ImmutableInstruction31c.of((Instruction31c) instruction)
            case 22:
                return ImmutableInstruction31i.of((Instruction31i) instruction)
            case 23:
                return ImmutableInstruction31t.of((Instruction31t) instruction)
            case 24:
                return ImmutableInstruction32x.of((Instruction32x) instruction)
            case 25:
                return ImmutableInstruction35c.of((Instruction35c) instruction)
            case 26:
                return ImmutableInstruction35mi.of((Instruction35mi) instruction)
            case 27:
                return ImmutableInstruction35ms.of((Instruction35ms) instruction)
            case 28:
                return ImmutableInstruction3rc.of((Instruction3rc) instruction)
            case 29:
                return ImmutableInstruction3rmi.of((Instruction3rmi) instruction)
            case 30:
                return ImmutableInstruction3rms.of((Instruction3rms) instruction)
            case 31:
                return ImmutableInstruction45cc.of((Instruction45cc) instruction)
            case 32:
                return ImmutableInstruction4rcc.of((Instruction4rcc) instruction)
            case 33:
                return ImmutableInstruction51l.of((Instruction51l) instruction)
            case 34:
                return ImmutablePackedSwitchPayload.of((PackedSwitchPayload) instruction)
            case 35:
                return ImmutableSparseSwitchPayload.of((SparseSwitchPayload) instruction)
            case 36:
                return ImmutableArrayPayload.of((ArrayPayload) instruction)
            default:
                throw RuntimeException("Unexpected instruction type")
        }
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return getFormat().size / 2
    }

    public abstract Format getFormat()

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getOpcode() {
        return this.opcode
    }
}
