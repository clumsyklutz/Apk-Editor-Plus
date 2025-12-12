package org.jf.dexlib2.analysis

import androidx.appcompat.R
import com.gmail.heagoo.neweditor.Token
import com.google.common.collect.ImmutableList
import java.util.BitSet
import java.util.Iterator
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.MethodParameter
import org.jf.dexlib2.iface.TryBlock
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
import org.jf.dexlib2.iface.instruction.OffsetInstruction
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.ReferenceInstruction
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.SwitchPayload
import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs
import org.jf.dexlib2.iface.instruction.formats.Instruction35c
import org.jf.dexlib2.iface.instruction.formats.Instruction35ms
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction10x
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction35c
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction3rc
import org.jf.dexlib2.util.MethodUtil
import org.jf.dexlib2.util.TypeUtils
import org.jf.dexlib2.writer.util.TryListBuilder
import org.jf.util.BitSetUtils
import org.jf.util.SparseArray

class MethodAnalyzer {
    public AnalysisException analysisException
    public final SparseArray<AnalyzedInstruction> analyzedInstructions
    public final BitSet analyzedState
    public final ClassPath classPath
    public final Method method
    public final MethodImplementation methodImpl
    public final Boolean normalizeVirtualMethods
    public final Int paramRegisterCount
    public final AnalyzedInstruction startOfMethod
    public static val Primitive32BitCategories = BitSetUtils.bitSetOfIndexes(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    public static val WideLowCategories = BitSetUtils.bitSetOfIndexes(12, 14)
    public static val WideHighCategories = BitSetUtils.bitSetOfIndexes(13, 15)
    public static val ReferenceOrUninitCategories = BitSetUtils.bitSetOfIndexes(2, 16, 17, 18)
    public static val BooleanCategories = BitSetUtils.bitSetOfIndexes(2, 3, 4)

    /* renamed from: org.jf.dexlib2.analysis.MethodAnalyzer$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Format
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Opcode

        static {
            Array<Int> iArr = new Int[Opcode.values().length]
            $SwitchMap$org$jf$dexlib2$Opcode = iArr
            try {
                iArr[Opcode.NOP.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_FROM16.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_16.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_WIDE.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_WIDE_FROM16.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_WIDE_16.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_OBJECT.ordinal()] = 8
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_OBJECT_FROM16.ordinal()] = 9
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_OBJECT_16.ordinal()] = 10
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_RESULT.ordinal()] = 11
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_RESULT_WIDE.ordinal()] = 12
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_RESULT_OBJECT.ordinal()] = 13
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MOVE_EXCEPTION.ordinal()] = 14
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RETURN_VOID.ordinal()] = 15
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RETURN.ordinal()] = 16
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RETURN_WIDE.ordinal()] = 17
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RETURN_OBJECT.ordinal()] = 18
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RETURN_VOID_BARRIER.ordinal()] = 19
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RETURN_VOID_NO_BARRIER.ordinal()] = 20
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_4.ordinal()] = 21
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_16.ordinal()] = 22
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST.ordinal()] = 23
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_HIGH16.ordinal()] = 24
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_WIDE_16.ordinal()] = 25
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_WIDE_32.ordinal()] = 26
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_WIDE.ordinal()] = 27
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_WIDE_HIGH16.ordinal()] = 28
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_STRING.ordinal()] = 29
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_STRING_JUMBO.ordinal()] = 30
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CONST_CLASS.ordinal()] = 31
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MONITOR_ENTER.ordinal()] = 32
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MONITOR_EXIT.ordinal()] = 33
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CHECK_CAST.ordinal()] = 34
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INSTANCE_OF.ordinal()] = 35
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ARRAY_LENGTH.ordinal()] = 36
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NEW_INSTANCE.ordinal()] = 37
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NEW_ARRAY.ordinal()] = 38
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FILLED_NEW_ARRAY.ordinal()] = 39
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FILLED_NEW_ARRAY_RANGE.ordinal()] = 40
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FILL_ARRAY_DATA.ordinal()] = 41
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.THROW.ordinal()] = 42
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.GOTO.ordinal()] = 43
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.GOTO_16.ordinal()] = 44
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.GOTO_32.ordinal()] = 45
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.PACKED_SWITCH.ordinal()] = 46
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPARSE_SWITCH.ordinal()] = 47
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CMPL_FLOAT.ordinal()] = 48
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CMPG_FLOAT.ordinal()] = 49
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CMPL_DOUBLE.ordinal()] = 50
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CMPG_DOUBLE.ordinal()] = 51
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.CMP_LONG.ordinal()] = 52
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_EQ.ordinal()] = 53
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_NE.ordinal()] = 54
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_LT.ordinal()] = 55
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_GE.ordinal()] = 56
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_GT.ordinal()] = 57
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_LE.ordinal()] = 58
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_LTZ.ordinal()] = 59
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_GEZ.ordinal()] = 60
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_GTZ.ordinal()] = 61
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_LEZ.ordinal()] = 62
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_EQZ.ordinal()] = 63
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IF_NEZ.ordinal()] = 64
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET.ordinal()] = 65
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET_BOOLEAN.ordinal()] = 66
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET_BYTE.ordinal()] = 67
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET_CHAR.ordinal()] = 68
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET_SHORT.ordinal()] = 69
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET_WIDE.ordinal()] = 70
            } catch (NoSuchFieldError unused70) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AGET_OBJECT.ordinal()] = 71
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT.ordinal()] = 72
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT_BOOLEAN.ordinal()] = 73
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT_BYTE.ordinal()] = 74
            } catch (NoSuchFieldError unused74) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT_CHAR.ordinal()] = 75
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT_SHORT.ordinal()] = 76
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT_WIDE.ordinal()] = 77
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.APUT_OBJECT.ordinal()] = 78
            } catch (NoSuchFieldError unused78) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET.ordinal()] = 79
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_BOOLEAN.ordinal()] = 80
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_BYTE.ordinal()] = 81
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_CHAR.ordinal()] = 82
            } catch (NoSuchFieldError unused82) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_SHORT.ordinal()] = 83
            } catch (NoSuchFieldError unused83) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_WIDE.ordinal()] = 84
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_OBJECT.ordinal()] = 85
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT.ordinal()] = 86
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_BOOLEAN.ordinal()] = 87
            } catch (NoSuchFieldError unused87) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_BYTE.ordinal()] = 88
            } catch (NoSuchFieldError unused88) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_CHAR.ordinal()] = 89
            } catch (NoSuchFieldError unused89) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_SHORT.ordinal()] = 90
            } catch (NoSuchFieldError unused90) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_WIDE.ordinal()] = 91
            } catch (NoSuchFieldError unused91) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_OBJECT.ordinal()] = 92
            } catch (NoSuchFieldError unused92) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET.ordinal()] = 93
            } catch (NoSuchFieldError unused93) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_BOOLEAN.ordinal()] = 94
            } catch (NoSuchFieldError unused94) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_BYTE.ordinal()] = 95
            } catch (NoSuchFieldError unused95) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_CHAR.ordinal()] = 96
            } catch (NoSuchFieldError unused96) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_SHORT.ordinal()] = 97
            } catch (NoSuchFieldError unused97) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_WIDE.ordinal()] = 98
            } catch (NoSuchFieldError unused98) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_OBJECT.ordinal()] = 99
            } catch (NoSuchFieldError unused99) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT.ordinal()] = 100
            } catch (NoSuchFieldError unused100) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_BOOLEAN.ordinal()] = 101
            } catch (NoSuchFieldError unused101) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_BYTE.ordinal()] = 102
            } catch (NoSuchFieldError unused102) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_CHAR.ordinal()] = 103
            } catch (NoSuchFieldError unused103) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_SHORT.ordinal()] = 104
            } catch (NoSuchFieldError unused104) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_WIDE.ordinal()] = 105
            } catch (NoSuchFieldError unused105) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_OBJECT.ordinal()] = 106
            } catch (NoSuchFieldError unused106) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_VIRTUAL.ordinal()] = 107
            } catch (NoSuchFieldError unused107) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_SUPER.ordinal()] = 108
            } catch (NoSuchFieldError unused108) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_DIRECT.ordinal()] = 109
            } catch (NoSuchFieldError unused109) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_STATIC.ordinal()] = 110
            } catch (NoSuchFieldError unused110) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_INTERFACE.ordinal()] = 111
            } catch (NoSuchFieldError unused111) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_VIRTUAL_RANGE.ordinal()] = 112
            } catch (NoSuchFieldError unused112) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_SUPER_RANGE.ordinal()] = 113
            } catch (NoSuchFieldError unused113) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_DIRECT_RANGE.ordinal()] = 114
            } catch (NoSuchFieldError unused114) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_STATIC_RANGE.ordinal()] = 115
            } catch (NoSuchFieldError unused115) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_INTERFACE_RANGE.ordinal()] = 116
            } catch (NoSuchFieldError unused116) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NEG_INT.ordinal()] = 117
            } catch (NoSuchFieldError unused117) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NOT_INT.ordinal()] = 118
            } catch (NoSuchFieldError unused118) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NEG_LONG.ordinal()] = 119
            } catch (NoSuchFieldError unused119) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NOT_LONG.ordinal()] = 120
            } catch (NoSuchFieldError unused120) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NEG_FLOAT.ordinal()] = 121
            } catch (NoSuchFieldError unused121) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.NEG_DOUBLE.ordinal()] = 122
            } catch (NoSuchFieldError unused122) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INT_TO_LONG.ordinal()] = 123
            } catch (NoSuchFieldError unused123) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INT_TO_FLOAT.ordinal()] = 124
            } catch (NoSuchFieldError unused124) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INT_TO_DOUBLE.ordinal()] = 125
            } catch (NoSuchFieldError unused125) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.LONG_TO_INT.ordinal()] = 126
            } catch (NoSuchFieldError unused126) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DOUBLE_TO_INT.ordinal()] = 127
            } catch (NoSuchFieldError unused127) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.LONG_TO_FLOAT.ordinal()] = 128
            } catch (NoSuchFieldError unused128) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DOUBLE_TO_FLOAT.ordinal()] = 129
            } catch (NoSuchFieldError unused129) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.LONG_TO_DOUBLE.ordinal()] = 130
            } catch (NoSuchFieldError unused130) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FLOAT_TO_INT.ordinal()] = 131
            } catch (NoSuchFieldError unused131) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FLOAT_TO_LONG.ordinal()] = 132
            } catch (NoSuchFieldError unused132) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FLOAT_TO_DOUBLE.ordinal()] = 133
            } catch (NoSuchFieldError unused133) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DOUBLE_TO_LONG.ordinal()] = 134
            } catch (NoSuchFieldError unused134) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INT_TO_BYTE.ordinal()] = 135
            } catch (NoSuchFieldError unused135) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INT_TO_CHAR.ordinal()] = 136
            } catch (NoSuchFieldError unused136) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INT_TO_SHORT.ordinal()] = 137
            } catch (NoSuchFieldError unused137) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_INT.ordinal()] = 138
            } catch (NoSuchFieldError unused138) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_INT.ordinal()] = 139
            } catch (NoSuchFieldError unused139) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_INT.ordinal()] = 140
            } catch (NoSuchFieldError unused140) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_INT.ordinal()] = 141
            } catch (NoSuchFieldError unused141) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_INT.ordinal()] = 142
            } catch (NoSuchFieldError unused142) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHL_INT.ordinal()] = 143
            } catch (NoSuchFieldError unused143) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHR_INT.ordinal()] = 144
            } catch (NoSuchFieldError unused144) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.USHR_INT.ordinal()] = 145
            } catch (NoSuchFieldError unused145) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AND_INT.ordinal()] = 146
            } catch (NoSuchFieldError unused146) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.OR_INT.ordinal()] = 147
            } catch (NoSuchFieldError unused147) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.XOR_INT.ordinal()] = 148
            } catch (NoSuchFieldError unused148) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_LONG.ordinal()] = 149
            } catch (NoSuchFieldError unused149) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_LONG.ordinal()] = 150
            } catch (NoSuchFieldError unused150) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_LONG.ordinal()] = 151
            } catch (NoSuchFieldError unused151) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_LONG.ordinal()] = 152
            } catch (NoSuchFieldError unused152) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_LONG.ordinal()] = 153
            } catch (NoSuchFieldError unused153) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AND_LONG.ordinal()] = 154
            } catch (NoSuchFieldError unused154) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.OR_LONG.ordinal()] = 155
            } catch (NoSuchFieldError unused155) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.XOR_LONG.ordinal()] = 156
            } catch (NoSuchFieldError unused156) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHL_LONG.ordinal()] = 157
            } catch (NoSuchFieldError unused157) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHR_LONG.ordinal()] = 158
            } catch (NoSuchFieldError unused158) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.USHR_LONG.ordinal()] = 159
            } catch (NoSuchFieldError unused159) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_FLOAT.ordinal()] = 160
            } catch (NoSuchFieldError unused160) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_FLOAT.ordinal()] = 161
            } catch (NoSuchFieldError unused161) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_FLOAT.ordinal()] = 162
            } catch (NoSuchFieldError unused162) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_FLOAT.ordinal()] = 163
            } catch (NoSuchFieldError unused163) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_FLOAT.ordinal()] = 164
            } catch (NoSuchFieldError unused164) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_DOUBLE.ordinal()] = 165
            } catch (NoSuchFieldError unused165) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_DOUBLE.ordinal()] = 166
            } catch (NoSuchFieldError unused166) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_DOUBLE.ordinal()] = 167
            } catch (NoSuchFieldError unused167) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_DOUBLE.ordinal()] = 168
            } catch (NoSuchFieldError unused168) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_DOUBLE.ordinal()] = 169
            } catch (NoSuchFieldError unused169) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_INT_2ADDR.ordinal()] = 170
            } catch (NoSuchFieldError unused170) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_INT_2ADDR.ordinal()] = 171
            } catch (NoSuchFieldError unused171) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_INT_2ADDR.ordinal()] = 172
            } catch (NoSuchFieldError unused172) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_INT_2ADDR.ordinal()] = 173
            } catch (NoSuchFieldError unused173) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_INT_2ADDR.ordinal()] = 174
            } catch (NoSuchFieldError unused174) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHL_INT_2ADDR.ordinal()] = 175
            } catch (NoSuchFieldError unused175) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHR_INT_2ADDR.ordinal()] = 176
            } catch (NoSuchFieldError unused176) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.USHR_INT_2ADDR.ordinal()] = 177
            } catch (NoSuchFieldError unused177) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AND_INT_2ADDR.ordinal()] = 178
            } catch (NoSuchFieldError unused178) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.OR_INT_2ADDR.ordinal()] = 179
            } catch (NoSuchFieldError unused179) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.XOR_INT_2ADDR.ordinal()] = 180
            } catch (NoSuchFieldError unused180) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_LONG_2ADDR.ordinal()] = 181
            } catch (NoSuchFieldError unused181) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_LONG_2ADDR.ordinal()] = 182
            } catch (NoSuchFieldError unused182) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_LONG_2ADDR.ordinal()] = 183
            } catch (NoSuchFieldError unused183) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_LONG_2ADDR.ordinal()] = 184
            } catch (NoSuchFieldError unused184) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_LONG_2ADDR.ordinal()] = 185
            } catch (NoSuchFieldError unused185) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AND_LONG_2ADDR.ordinal()] = 186
            } catch (NoSuchFieldError unused186) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.OR_LONG_2ADDR.ordinal()] = 187
            } catch (NoSuchFieldError unused187) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.XOR_LONG_2ADDR.ordinal()] = 188
            } catch (NoSuchFieldError unused188) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHL_LONG_2ADDR.ordinal()] = 189
            } catch (NoSuchFieldError unused189) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHR_LONG_2ADDR.ordinal()] = 190
            } catch (NoSuchFieldError unused190) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.USHR_LONG_2ADDR.ordinal()] = 191
            } catch (NoSuchFieldError unused191) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_FLOAT_2ADDR.ordinal()] = 192
            } catch (NoSuchFieldError unused192) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_FLOAT_2ADDR.ordinal()] = 193
            } catch (NoSuchFieldError unused193) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_FLOAT_2ADDR.ordinal()] = 194
            } catch (NoSuchFieldError unused194) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_FLOAT_2ADDR.ordinal()] = 195
            } catch (NoSuchFieldError unused195) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_FLOAT_2ADDR.ordinal()] = 196
            } catch (NoSuchFieldError unused196) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_DOUBLE_2ADDR.ordinal()] = 197
            } catch (NoSuchFieldError unused197) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SUB_DOUBLE_2ADDR.ordinal()] = 198
            } catch (NoSuchFieldError unused198) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_DOUBLE_2ADDR.ordinal()] = 199
            } catch (NoSuchFieldError unused199) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_DOUBLE_2ADDR.ordinal()] = 200
            } catch (NoSuchFieldError unused200) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_DOUBLE_2ADDR.ordinal()] = 201
            } catch (NoSuchFieldError unused201) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_INT_LIT16.ordinal()] = 202
            } catch (NoSuchFieldError unused202) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RSUB_INT.ordinal()] = 203
            } catch (NoSuchFieldError unused203) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_INT_LIT16.ordinal()] = 204
            } catch (NoSuchFieldError unused204) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_INT_LIT16.ordinal()] = 205
            } catch (NoSuchFieldError unused205) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_INT_LIT16.ordinal()] = 206
            } catch (NoSuchFieldError unused206) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AND_INT_LIT16.ordinal()] = 207
            } catch (NoSuchFieldError unused207) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.OR_INT_LIT16.ordinal()] = 208
            } catch (NoSuchFieldError unused208) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.XOR_INT_LIT16.ordinal()] = 209
            } catch (NoSuchFieldError unused209) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ADD_INT_LIT8.ordinal()] = 210
            } catch (NoSuchFieldError unused210) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.RSUB_INT_LIT8.ordinal()] = 211
            } catch (NoSuchFieldError unused211) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.MUL_INT_LIT8.ordinal()] = 212
            } catch (NoSuchFieldError unused212) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.DIV_INT_LIT8.ordinal()] = 213
            } catch (NoSuchFieldError unused213) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.REM_INT_LIT8.ordinal()] = 214
            } catch (NoSuchFieldError unused214) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHL_INT_LIT8.ordinal()] = 215
            } catch (NoSuchFieldError unused215) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.AND_INT_LIT8.ordinal()] = 216
            } catch (NoSuchFieldError unused216) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.OR_INT_LIT8.ordinal()] = 217
            } catch (NoSuchFieldError unused217) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.XOR_INT_LIT8.ordinal()] = 218
            } catch (NoSuchFieldError unused218) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SHR_INT_LIT8.ordinal()] = 219
            } catch (NoSuchFieldError unused219) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.USHR_INT_LIT8.ordinal()] = 220
            } catch (NoSuchFieldError unused220) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_VOLATILE.ordinal()] = 221
            } catch (NoSuchFieldError unused221) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_VOLATILE.ordinal()] = 222
            } catch (NoSuchFieldError unused222) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_VOLATILE.ordinal()] = 223
            } catch (NoSuchFieldError unused223) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_VOLATILE.ordinal()] = 224
            } catch (NoSuchFieldError unused224) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_OBJECT_VOLATILE.ordinal()] = 225
            } catch (NoSuchFieldError unused225) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_WIDE_VOLATILE.ordinal()] = 226
            } catch (NoSuchFieldError unused226) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_WIDE_VOLATILE.ordinal()] = 227
            } catch (NoSuchFieldError unused227) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_WIDE_VOLATILE.ordinal()] = 228
            } catch (NoSuchFieldError unused228) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_WIDE_VOLATILE.ordinal()] = 229
            } catch (NoSuchFieldError unused229) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.THROW_VERIFICATION_ERROR.ordinal()] = 230
            } catch (NoSuchFieldError unused230) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.EXECUTE_INLINE.ordinal()] = 231
            } catch (NoSuchFieldError unused231) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.EXECUTE_INLINE_RANGE.ordinal()] = 232
            } catch (NoSuchFieldError unused232) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_DIRECT_EMPTY.ordinal()] = 233
            } catch (NoSuchFieldError unused233) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_OBJECT_INIT_RANGE.ordinal()] = 234
            } catch (NoSuchFieldError unused234) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_QUICK.ordinal()] = 235
            } catch (NoSuchFieldError unused235) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_WIDE_QUICK.ordinal()] = 236
            } catch (NoSuchFieldError unused236) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_OBJECT_QUICK.ordinal()] = 237
            } catch (NoSuchFieldError unused237) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_QUICK.ordinal()] = 238
            } catch (NoSuchFieldError unused238) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_WIDE_QUICK.ordinal()] = 239
            } catch (NoSuchFieldError unused239) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_OBJECT_QUICK.ordinal()] = 240
            } catch (NoSuchFieldError unused240) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_BOOLEAN_QUICK.ordinal()] = 241
            } catch (NoSuchFieldError unused241) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_BYTE_QUICK.ordinal()] = 242
            } catch (NoSuchFieldError unused242) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_CHAR_QUICK.ordinal()] = 243
            } catch (NoSuchFieldError unused243) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_SHORT_QUICK.ordinal()] = 244
            } catch (NoSuchFieldError unused244) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_BOOLEAN_QUICK.ordinal()] = 245
            } catch (NoSuchFieldError unused245) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_BYTE_QUICK.ordinal()] = 246
            } catch (NoSuchFieldError unused246) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_CHAR_QUICK.ordinal()] = 247
            } catch (NoSuchFieldError unused247) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IGET_SHORT_QUICK.ordinal()] = 248
            } catch (NoSuchFieldError unused248) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_VIRTUAL_QUICK.ordinal()] = 249
            } catch (NoSuchFieldError unused249) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_SUPER_QUICK.ordinal()] = 250
            } catch (NoSuchFieldError unused250) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_VIRTUAL_QUICK_RANGE.ordinal()] = 251
            } catch (NoSuchFieldError unused251) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.INVOKE_SUPER_QUICK_RANGE.ordinal()] = 252
            } catch (NoSuchFieldError unused252) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.IPUT_OBJECT_VOLATILE.ordinal()] = 253
            } catch (NoSuchFieldError unused253) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SGET_OBJECT_VOLATILE.ordinal()] = 254
            } catch (NoSuchFieldError unused254) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_OBJECT_VOLATILE.ordinal()] = 255
            } catch (NoSuchFieldError unused255) {
            }
            Array<Int> iArr2 = new Int[Format.values().length]
            $SwitchMap$org$jf$dexlib2$Format = iArr2
            try {
                iArr2[Format.Format10x.ordinal()] = 1
            } catch (NoSuchFieldError unused256) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21c.ordinal()] = 2
            } catch (NoSuchFieldError unused257) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22c.ordinal()] = 3
            } catch (NoSuchFieldError unused258) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35c.ordinal()] = 4
            } catch (NoSuchFieldError unused259) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rc.ordinal()] = 5
            } catch (NoSuchFieldError unused260) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22cs.ordinal()] = 6
            } catch (NoSuchFieldError unused261) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 7
            } catch (NoSuchFieldError unused262) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 8
            } catch (NoSuchFieldError unused263) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rmi.ordinal()] = 9
            } catch (NoSuchFieldError unused264) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rms.ordinal()] = 10
            } catch (NoSuchFieldError unused265) {
            }
        }
    }

    constructor(ClassPath classPath, Method method, InlineMethodResolver inlineMethodResolver, Boolean z) {
        SparseArray<AnalyzedInstruction> sparseArray = new SparseArray<>(0)
        this.analyzedInstructions = sparseArray
        this.analysisException = null
        this.normalizeVirtualMethods = z
        this.method = method
        MethodImplementation implementation = method.getImplementation()
        if (implementation == null) {
            throw IllegalArgumentException("The method has no implementation")
        }
        this.methodImpl = implementation
        this.startOfMethod = AnalyzedInstruction(this, this, ImmutableInstruction10x(Opcode.NOP), -1, implementation.getRegisterCount()) { // from class: org.jf.dexlib2.analysis.MethodAnalyzer.1
            @Override // org.jf.dexlib2.analysis.AnalyzedInstruction
            fun addPredecessor(AnalyzedInstruction analyzedInstruction) {
                throw UnsupportedOperationException()
            }

            @Override // org.jf.dexlib2.analysis.AnalyzedInstruction
            fun getPredecessorRegisterType(AnalyzedInstruction analyzedInstruction, Int i) {
                throw UnsupportedOperationException()
            }
        }
        buildInstructionList()
        this.analyzedState = BitSet(sparseArray.size())
        this.paramRegisterCount = MethodUtil.getParameterRegisterCount(method)
        analyze()
    }

    fun checkRegister(RegisterType registerType, Int i, BitSet bitSet) {
        if (!bitSet.get(registerType.category)) {
            throw AnalysisException(String.format("Invalid register type %s for register v%d.", registerType.toString(), Integer.valueOf(i)), new Object[0])
        }
    }

    fun checkWidePair(Int i, AnalyzedInstruction analyzedInstruction) {
        if (i + 1 >= analyzedInstruction.postRegisterMap.length) {
            throw AnalysisException(String.format("v%d cannot be used as the first register in a wide registerpair because it is the last register.", Integer.valueOf(i)), new Object[0])
        }
    }

    fun getAndCheckSourceRegister(AnalyzedInstruction analyzedInstruction, Int i, BitSet bitSet) {
        RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(i)
        checkRegister(preInstructionRegisterType, i, bitSet)
        BitSet bitSet2 = WideLowCategories
        if (bitSet == bitSet2) {
            checkRegister(preInstructionRegisterType, i, bitSet2)
            checkWidePair(i, analyzedInstruction)
            Int i2 = i + 1
            checkRegister(analyzedInstruction.getPreInstructionRegisterType(i2), i2, WideHighCategories)
        }
        return preInstructionRegisterType
    }

    public final Unit addPredecessorSuccessor(AnalyzedInstruction analyzedInstruction, AnalyzedInstruction analyzedInstruction2, Array<AnalyzedInstruction>[] analyzedInstructionArr, BitSet bitSet) {
        addPredecessorSuccessor(analyzedInstruction, analyzedInstruction2, analyzedInstructionArr, bitSet, false)
    }

    public final Unit addPredecessorSuccessor(AnalyzedInstruction analyzedInstruction, AnalyzedInstruction analyzedInstruction2, Array<AnalyzedInstruction>[] analyzedInstructionArr, BitSet bitSet, Boolean z) {
        if (!z && analyzedInstruction2.instruction.getOpcode() == Opcode.MOVE_EXCEPTION) {
            throw AnalysisException("Execution can pass from the " + analyzedInstruction.instruction.getOpcode().name + " instruction at code address 0x" + Integer.toHexString(getInstructionAddress(analyzedInstruction)) + " to the move-exception instruction at address 0x" + Integer.toHexString(getInstructionAddress(analyzedInstruction2)), new Object[0])
        }
        if (analyzedInstruction2.addPredecessor(analyzedInstruction)) {
            analyzedInstruction.addSuccessor(analyzedInstruction2)
            bitSet.set(analyzedInstruction2.getInstructionIndex())
            Array<AnalyzedInstruction> analyzedInstructionArr2 = analyzedInstructionArr[analyzedInstruction2.instructionIndex]
            if (analyzedInstructionArr2 != null) {
                for (AnalyzedInstruction analyzedInstruction3 : analyzedInstructionArr2) {
                    addPredecessorSuccessor(analyzedInstruction, analyzedInstruction3, analyzedInstructionArr, bitSet, true)
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x010f A[EDGE_INSN: B:83:0x010f->B:47:0x010f BREAK  A[LOOP:3: B:19:0x0065->B:84:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[LOOP:3: B:19:0x0065->B:84:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit analyze() {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.dexlib2.analysis.MethodAnalyzer.analyze():Unit")
    }

    public final Unit analyze32BitPrimitiveAget(AnalyzedInstruction analyzedInstruction, RegisterType registerType) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyze32BitPrimitiveIgetSget(AnalyzedInstruction analyzedInstruction, RegisterType registerType) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyzeAgetObject(AnalyzedInstruction analyzedInstruction) {
        RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(((ThreeRegisterInstruction) analyzedInstruction.instruction).getRegisterB())
        Byte b2 = preInstructionRegisterType.category
        if (b2 == 2) {
            setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.NULL_TYPE)
            return
        }
        if (b2 == 18) {
            TypeProto typeProto = preInstructionRegisterType.type
            if (typeProto is ArrayProto) {
                ((ArrayProto) typeProto).getImmediateElementType()
                throw null
            }
        }
        throw AnalysisException("aget-object used with non-array register: %s", preInstructionRegisterType.toString())
    }

    public final Unit analyzeAgetWide(AnalyzedInstruction analyzedInstruction) {
        RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(((ThreeRegisterInstruction) analyzedInstruction.instruction).getRegisterB())
        Byte b2 = preInstructionRegisterType.category
        if (b2 == 2) {
            setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.LONG_LO_TYPE)
            return
        }
        if (b2 == 18) {
            TypeProto typeProto = preInstructionRegisterType.type
            if (typeProto is ArrayProto) {
                ArrayProto arrayProto = (ArrayProto) typeProto
                if (arrayProto.dimensions != 1) {
                    throw AnalysisException("aget-wide used with multi-dimensional array: %s", preInstructionRegisterType.toString())
                }
                Char cCharAt = arrayProto.getElementType().charAt(0)
                if (cCharAt == 'J') {
                    setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.LONG_LO_TYPE)
                    return
                } else {
                    if (cCharAt != 'D') {
                        throw AnalysisException("aget-wide used with narrow array: %s", preInstructionRegisterType)
                    }
                    setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE)
                    return
                }
            }
        }
        throw AnalysisException("aget-wide used with non-array register: %s", preInstructionRegisterType.toString())
    }

    public final Unit analyzeArrayLength(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.INTEGER_TYPE)
    }

    public final Unit analyzeBinary2AddrOp(AnalyzedInstruction analyzedInstruction, RegisterType registerType, Boolean z) {
        if (z) {
            TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction) analyzedInstruction.instruction
            RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterA())
            RegisterType preInstructionRegisterType2 = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterB())
            BitSet bitSet = BooleanCategories
            if (bitSet.get(preInstructionRegisterType.category) && bitSet.get(preInstructionRegisterType2.category)) {
                registerType = RegisterType.BOOLEAN_TYPE
            }
        }
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyzeBinaryOp(AnalyzedInstruction analyzedInstruction, RegisterType registerType, Boolean z) {
        if (z) {
            ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction) analyzedInstruction.instruction
            RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB())
            RegisterType preInstructionRegisterType2 = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterC())
            BitSet bitSet = BooleanCategories
            if (bitSet.get(preInstructionRegisterType.category) && bitSet.get(preInstructionRegisterType2.category)) {
                registerType = RegisterType.BOOLEAN_TYPE
            }
        }
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyzeCheckCast(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(this.classPath, (TypeReference) ((ReferenceInstruction) analyzedInstruction.instruction).getReference()))
    }

    public final Unit analyzeConst(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterTypeForLiteral(((NarrowLiteralInstruction) analyzedInstruction.instruction).getNarrowLiteral()))
    }

    public final Unit analyzeConstClass(AnalyzedInstruction analyzedInstruction) {
        throw null
    }

    public final Unit analyzeConstString(AnalyzedInstruction analyzedInstruction) {
        throw null
    }

    public final Unit analyzeExecuteInline(AnalyzedInstruction analyzedInstruction) {
        throw AnalysisException("Cannot analyze an odexed instruction unless we are deodexing", new Object[0])
    }

    public final Unit analyzeExecuteInlineRange(AnalyzedInstruction analyzedInstruction) {
        throw AnalysisException("Cannot analyze an odexed instruction unless we are deodexing", new Object[0])
    }

    public final Unit analyzeFloatWideCmp(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.BYTE_TYPE)
    }

    public final Unit analyzeIfEqzNez(AnalyzedInstruction analyzedInstruction) {
        throw null
    }

    public final Unit analyzeIgetSgetWideObject(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(this.classPath, ((FieldReference) ((ReferenceInstruction) analyzedInstruction.instruction).getReference()).getType()))
    }

    public final Unit analyzeInstanceOf(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.BOOLEAN_TYPE)
    }

    public final Boolean analyzeInstruction(AnalyzedInstruction analyzedInstruction) {
        switch (AnonymousClass3.$SwitchMap$org$jf$dexlib2$Opcode[analyzedInstruction.instruction.getOpcode().ordinal()]) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                analyzeMove(analyzedInstruction)
            case 1:
            default:
                return true
            case 11:
            case 12:
            case 13:
                analyzeMoveResult(analyzedInstruction)
                return true
            case 14:
                analyzeMoveException(analyzedInstruction)
                return true
            case 19:
            case 20:
                analyzeOdexReturnVoid(analyzedInstruction)
            case 15:
            case 16:
            case 17:
            case 18:
                return true
            case 21:
            case 22:
            case 23:
            case 24:
                analyzeConst(analyzedInstruction)
                return true
            case 25:
            case 26:
            case 27:
            case 28:
                analyzeWideConst(analyzedInstruction)
                return true
            case 29:
            case 30:
                analyzeConstString(analyzedInstruction)
                return true
            case 31:
                analyzeConstClass(analyzedInstruction)
                return true
            case 34:
                analyzeCheckCast(analyzedInstruction)
            case 32:
            case 33:
                return true
            case 35:
                analyzeInstanceOf(analyzedInstruction)
                return true
            case 36:
                analyzeArrayLength(analyzedInstruction)
                return true
            case 37:
                analyzeNewInstance(analyzedInstruction)
                return true
            case 38:
                analyzeNewArray(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
            case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
            case 50:
            case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
            case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
                analyzeFloatWideCmp(analyzedInstruction)
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
                return true
            case 63:
            case 64:
                analyzeIfEqzNez(analyzedInstruction)
            case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
            case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
            case R.styleable.AppCompatTheme_borderlessButtonStyle /* 56 */:
            case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
            case R.styleable.AppCompatTheme_dividerHorizontal /* 58 */:
            case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
            case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
            case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
            case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
                return true
            case R.styleable.AppCompatTheme_editTextBackground /* 65 */:
                analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.INTEGER_TYPE)
                return true
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.BOOLEAN_TYPE)
                return true
            case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.BYTE_TYPE)
                return true
            case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.CHAR_TYPE)
                return true
            case R.styleable.AppCompatTheme_textColorSearchUrl /* 69 */:
                analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.SHORT_TYPE)
                return true
            case R.styleable.AppCompatTheme_searchViewStyle /* 70 */:
                analyzeAgetWide(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_listPreferredItemHeight /* 71 */:
                analyzeAgetObject(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.INTEGER_TYPE)
            case R.styleable.AppCompatTheme_listPreferredItemHeightSmall /* 72 */:
            case R.styleable.AppCompatTheme_listPreferredItemHeightLarge /* 73 */:
            case R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /* 74 */:
            case R.styleable.AppCompatTheme_listPreferredItemPaddingRight /* 75 */:
            case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
            case R.styleable.AppCompatTheme_listPopupWindowStyle /* 77 */:
            case R.styleable.AppCompatTheme_textAppearanceListItem /* 78 */:
                return true
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BOOLEAN_TYPE)
                return true
            case 81:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BYTE_TYPE)
                return true
            case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.CHAR_TYPE)
                return true
            case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.SHORT_TYPE)
                return true
            case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
            case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
                analyzeIgetSgetWideObject(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_controlBackground /* 93 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.INTEGER_TYPE)
            case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
            case R.styleable.AppCompatTheme_colorAccent /* 87 */:
            case R.styleable.AppCompatTheme_colorControlNormal /* 88 */:
            case R.styleable.AppCompatTheme_colorControlActivated /* 89 */:
            case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
            case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                return true
            case R.styleable.AppCompatTheme_colorBackgroundFloating /* 94 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BOOLEAN_TYPE)
                return true
            case R.styleable.AppCompatTheme_alertDialogStyle /* 95 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BYTE_TYPE)
                return true
            case R.styleable.AppCompatTheme_alertDialogButtonGroupStyle /* 96 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.CHAR_TYPE)
                return true
            case R.styleable.AppCompatTheme_alertDialogCenterButtons /* 97 */:
                analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.SHORT_TYPE)
                return true
            case R.styleable.AppCompatTheme_alertDialogTheme /* 98 */:
            case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 99 */:
                analyzeIgetSgetWideObject(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_checkedTextViewStyle /* 107 */:
                analyzeInvokeVirtual(analyzedInstruction, false)
            case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /* 100 */:
            case R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle /* 101 */:
            case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /* 102 */:
            case R.styleable.AppCompatTheme_autoCompleteTextViewStyle /* 103 */:
            case R.styleable.AppCompatTheme_buttonStyle /* 104 */:
            case R.styleable.AppCompatTheme_buttonStyleSmall /* 105 */:
            case R.styleable.AppCompatTheme_checkboxStyle /* 106 */:
                return true
            case 108:
                analyzeInvokeVirtual(analyzedInstruction, false)
                return true
            case 109:
                analyzeInvokeDirect(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_ratingBarStyleSmall /* 112 */:
                analyzeInvokeVirtual(analyzedInstruction, true)
            case R.styleable.AppCompatTheme_ratingBarStyle /* 110 */:
            case R.styleable.AppCompatTheme_ratingBarStyleIndicator /* 111 */:
                return true
            case R.styleable.AppCompatTheme_seekBarStyle /* 113 */:
                analyzeInvokeVirtual(analyzedInstruction, true)
                return true
            case R.styleable.AppCompatTheme_spinnerStyle /* 114 */:
                analyzeInvokeDirectRange(analyzedInstruction)
                return true
            case R.styleable.AppCompatTheme_tooltipFrameBackground /* 117 */:
            case R.styleable.AppCompatTheme_tooltipForegroundColor /* 118 */:
                analyzeUnaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE)
            case R.styleable.AppCompatTheme_switchStyle /* 115 */:
            case R.styleable.AppCompatTheme_listMenuViewStyle /* 116 */:
                return true
            case R.styleable.AppCompatTheme_colorError /* 119 */:
            case 120:
                analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE)
                return true
            case 121:
                analyzeUnaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE)
                return true
            case 122:
                analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE)
                return true
            case 123:
                analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE)
                return true
            case 124:
                analyzeUnaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE)
                return true
            case 125:
                analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE)
                return true
            case 126:
            case 127:
                analyzeUnaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE)
                return true
            case 128:
            case 129:
                analyzeUnaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE)
                return true
            case 130:
                analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE)
                return true
            case 131:
                analyzeUnaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE)
                return true
            case 132:
                analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE)
                return true
            case 133:
                analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE)
                return true
            case 134:
                analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE)
                return true
            case 135:
                analyzeUnaryOp(analyzedInstruction, RegisterType.BYTE_TYPE)
                return true
            case 136:
                analyzeUnaryOp(analyzedInstruction, RegisterType.CHAR_TYPE)
                return true
            case 137:
                analyzeUnaryOp(analyzedInstruction, RegisterType.SHORT_TYPE)
                return true
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
                analyzeBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false)
                return true
            case 146:
            case 147:
            case 148:
                analyzeBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true)
                return true
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                analyzeBinaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE, false)
                return true
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
                analyzeBinaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE, false)
                return true
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
                analyzeBinaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE, false)
                return true
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
                analyzeBinary2AddrOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false)
                return true
            case 178:
            case 179:
            case 180:
                analyzeBinary2AddrOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true)
                return true
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
                analyzeBinary2AddrOp(analyzedInstruction, RegisterType.LONG_LO_TYPE, false)
                return true
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
                analyzeBinary2AddrOp(analyzedInstruction, RegisterType.FLOAT_TYPE, false)
                return true
            case 197:
            case 198:
            case 199:
            case 200:
            case 201:
                analyzeBinary2AddrOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE, false)
                return true
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
                analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false)
                return true
            case 207:
            case 208:
            case 209:
                analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true)
                return true
            case 210:
            case 211:
            case 212:
            case 213:
            case 214:
            case 215:
                analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false)
                return true
            case 216:
            case 217:
            case 218:
                analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true)
                return true
            case 219:
                analyzeLiteralBinaryOp(analyzedInstruction, getDestTypeForLiteralShiftRight(analyzedInstruction, true), false)
                return true
            case 220:
                analyzeLiteralBinaryOp(analyzedInstruction, getDestTypeForLiteralShiftRight(analyzedInstruction, false), false)
                return true
            case 221:
            case 222:
            case 223:
            case 224:
            case 225:
            case 226:
            case 227:
            case 228:
            case 229:
                analyzePutGetVolatile(analyzedInstruction)
                return true
            case 231:
                analyzeExecuteInline(analyzedInstruction)
            case 230:
                return true
            case 232:
                analyzeExecuteInlineRange(analyzedInstruction)
                return true
            case 233:
                analyzeInvokeDirectEmpty(analyzedInstruction)
                return true
            case 234:
                analyzeInvokeObjectInitRange(analyzedInstruction)
                return true
            case 235:
            case 236:
            case 237:
            case 238:
            case 239:
            case 240:
            case 241:
            case 242:
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 248:
                return analyzeIputIgetQuick(analyzedInstruction)
            case 249:
                return analyzeInvokeVirtualQuick(analyzedInstruction, false, false)
            case 250:
                return analyzeInvokeVirtualQuick(analyzedInstruction, true, false)
            case 251:
                return analyzeInvokeVirtualQuick(analyzedInstruction, false, true)
            case 252:
                return analyzeInvokeVirtualQuick(analyzedInstruction, true, true)
            case 253:
            case 254:
            case 255:
                analyzePutGetVolatile(analyzedInstruction)
                return true
        }
    }

    public final Unit analyzeInvokeDirect(AnalyzedInstruction analyzedInstruction) {
        analyzeInvokeDirectCommon(analyzedInstruction, ((FiveRegisterInstruction) analyzedInstruction.instruction).getRegisterC())
    }

    public final Unit analyzeInvokeDirectCommon(AnalyzedInstruction analyzedInstruction, Int i) {
        if (analyzedInstruction.isInvokeInit()) {
            RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(i)
            Byte b2 = preInstructionRegisterType.category
            if (b2 == 16 || b2 == 17) {
                RegisterType registerType = RegisterType.getRegisterType(Token.OPERATOR, preInstructionRegisterType.type)
                Iterator<Integer> it = analyzedInstruction.getSetRegisters().iterator()
                while (it.hasNext()) {
                    Int iIntValue = it.next().intValue()
                    RegisterType preInstructionRegisterType2 = analyzedInstruction.getPreInstructionRegisterType(iIntValue)
                    if (preInstructionRegisterType2 == preInstructionRegisterType) {
                        setPostRegisterTypeAndPropagateChanges(analyzedInstruction, iIntValue, registerType)
                    } else {
                        setPostRegisterTypeAndPropagateChanges(analyzedInstruction, iIntValue, preInstructionRegisterType2)
                    }
                }
            }
        }
    }

    public final Unit analyzeInvokeDirectEmpty(AnalyzedInstruction analyzedInstruction) {
        analyzeInvokeDirectEmpty(analyzedInstruction, true)
    }

    public final Unit analyzeInvokeDirectEmpty(AnalyzedInstruction analyzedInstruction, Boolean z) {
        Instruction35c instruction35c = (Instruction35c) analyzedInstruction.instruction
        analyzedInstruction.setDeodexedInstruction(ImmutableInstruction35c(Opcode.INVOKE_DIRECT, instruction35c.getRegisterCount(), instruction35c.getRegisterC(), instruction35c.getRegisterD(), instruction35c.getRegisterE(), instruction35c.getRegisterF(), instruction35c.getRegisterG(), instruction35c.getReference()))
        if (z) {
            analyzeInstruction(analyzedInstruction)
        }
    }

    public final Unit analyzeInvokeDirectRange(AnalyzedInstruction analyzedInstruction) {
        analyzeInvokeDirectCommon(analyzedInstruction, ((RegisterRangeInstruction) analyzedInstruction.instruction).getStartRegister())
    }

    public final Unit analyzeInvokeObjectInitRange(AnalyzedInstruction analyzedInstruction) {
        analyzeInvokeObjectInitRange(analyzedInstruction, true)
    }

    public final Unit analyzeInvokeObjectInitRange(AnalyzedInstruction analyzedInstruction, Boolean z) {
        Instruction3rc instruction3rc = (Instruction3rc) analyzedInstruction.instruction
        Int startRegister = instruction3rc.getStartRegister()
        analyzedInstruction.setDeodexedInstruction(startRegister < 16 ? ImmutableInstruction35c(Opcode.INVOKE_DIRECT, 1, startRegister, 0, 0, 0, 0, instruction3rc.getReference()) : ImmutableInstruction3rc(Opcode.INVOKE_DIRECT_RANGE, startRegister, 1, instruction3rc.getReference()))
        if (z) {
            analyzeInstruction(analyzedInstruction)
        }
    }

    public final Boolean analyzeInvokeVirtual(AnalyzedInstruction analyzedInstruction, Boolean z) {
        Instruction immutableInstruction35c
        if (!this.normalizeVirtualMethods) {
            return true
        }
        MethodReference methodReference = z ? (MethodReference) ((Instruction3rc) analyzedInstruction.instruction).getReference() : (MethodReference) ((Instruction35c) analyzedInstruction.instruction).getReference()
        MethodReference methodReferenceNormalizeMethodReference = normalizeMethodReference(methodReference)
        if (methodReferenceNormalizeMethodReference != null && !methodReferenceNormalizeMethodReference.equals(methodReference)) {
            if (z) {
                Instruction3rc instruction3rc = (Instruction3rc) analyzedInstruction.instruction
                immutableInstruction35c = ImmutableInstruction3rc(instruction3rc.getOpcode(), instruction3rc.getStartRegister(), instruction3rc.getRegisterCount(), methodReferenceNormalizeMethodReference)
            } else {
                Instruction35c instruction35c = (Instruction35c) analyzedInstruction.instruction
                immutableInstruction35c = ImmutableInstruction35c(instruction35c.getOpcode(), instruction35c.getRegisterCount(), instruction35c.getRegisterC(), instruction35c.getRegisterD(), instruction35c.getRegisterE(), instruction35c.getRegisterF(), instruction35c.getRegisterG(), methodReferenceNormalizeMethodReference)
            }
            analyzedInstruction.setDeodexedInstruction(immutableInstruction35c)
        }
        return true
    }

    public final Boolean analyzeInvokeVirtualQuick(AnalyzedInstruction analyzedInstruction, Boolean z, Boolean z2) {
        Int vtableIndex
        Int registerC
        if (z2) {
            Instruction3rms instruction3rms = (Instruction3rms) analyzedInstruction.instruction
            vtableIndex = instruction3rms.getVtableIndex()
            registerC = instruction3rms.getStartRegister()
        } else {
            Instruction35ms instruction35ms = (Instruction35ms) analyzedInstruction.instruction
            vtableIndex = instruction35ms.getVtableIndex()
            registerC = instruction35ms.getRegisterC()
        }
        RegisterType andCheckSourceRegister = getAndCheckSourceRegister(analyzedInstruction, registerC, ReferenceOrUninitCategories)
        TypeProto typeProto = andCheckSourceRegister.type
        if (andCheckSourceRegister.category == 2) {
            return false
        }
        if (z) {
            this.method.getDefiningClass()
            throw null
        }
        if (typeProto.getMethodByVtableIndex(vtableIndex) == null) {
            throw AnalysisException("Could not resolve the method in class %s at index %d", andCheckSourceRegister.type.getType(), Integer.valueOf(vtableIndex))
        }
        this.method.getDefiningClass()
        throw null
    }

    public final Boolean analyzeIputIgetQuick(AnalyzedInstruction analyzedInstruction) {
        Instruction22cs instruction22cs = (Instruction22cs) analyzedInstruction.instruction
        instruction22cs.getFieldOffset()
        RegisterType andCheckSourceRegister = getAndCheckSourceRegister(analyzedInstruction, instruction22cs.getRegisterB(), ReferenceOrUninitCategories)
        if (andCheckSourceRegister.category == 2) {
            return false
        }
        andCheckSourceRegister.type.getType()
        throw null
    }

    public final Unit analyzeLiteralBinaryOp(AnalyzedInstruction analyzedInstruction, RegisterType registerType, Boolean z) {
        Int narrowLiteral
        if (z) {
            if (BooleanCategories.get(analyzedInstruction.getPreInstructionRegisterType(((TwoRegisterInstruction) analyzedInstruction.instruction).getRegisterB()).category) && ((narrowLiteral = ((NarrowLiteralInstruction) analyzedInstruction.instruction).getNarrowLiteral()) == 0 || narrowLiteral == 1)) {
                registerType = RegisterType.BOOLEAN_TYPE
            }
        }
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyzeMove(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, analyzedInstruction.getPreInstructionRegisterType(((TwoRegisterInstruction) analyzedInstruction.instruction).getRegisterB()))
    }

    public final Unit analyzeMoveException(AnalyzedInstruction analyzedInstruction) {
        Int instructionAddress = getInstructionAddress(analyzedInstruction)
        RegisterType registerType = RegisterType.UNKNOWN_TYPE
        Iterator<? extends TryBlock<? extends ExceptionHandler>> it = this.methodImpl.getTryBlocks().iterator()
        while (it.hasNext()) {
            Iterator it2 = it.next().getExceptionHandlers().iterator()
            while (it2.hasNext()) {
                ExceptionHandler exceptionHandler = (ExceptionHandler) it2.next()
                if (exceptionHandler.getHandlerCodeAddress() == instructionAddress) {
                    exceptionHandler.getExceptionType().getClass()
                    throw null
                }
            }
        }
        if (registerType.category == 0) {
            throw AnalysisException("move-exception must be the first instruction in an exception handler block", new Object[0])
        }
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyzeMoveResult(AnalyzedInstruction analyzedInstruction) {
        Int i = analyzedInstruction.instructionIndex
        AnalyzedInstruction analyzedInstructionValueAt = i > 0 ? this.analyzedInstructions.valueAt(i - 1) : null
        if (analyzedInstructionValueAt != null && analyzedInstructionValueAt.instruction.getOpcode().setsResult()) {
            Reference reference = ((ReferenceInstruction) analyzedInstructionValueAt.instruction).getReference()
            setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, reference is MethodReference ? RegisterType.getRegisterType(this.classPath, ((MethodReference) reference).getReturnType()) : RegisterType.getRegisterType(this.classPath, (TypeReference) reference))
        } else {
            throw AnalysisException(analyzedInstruction.instruction.getOpcode().name + " must occur after an invoke-*/fill-new-array instruction", new Object[0])
        }
    }

    public final Unit analyzeNewArray(AnalyzedInstruction analyzedInstruction) {
        TypeReference typeReference = (TypeReference) ((ReferenceInstruction) analyzedInstruction.instruction).getReference()
        if (typeReference.getType().charAt(0) != '[') {
            throw AnalysisException("new-array used with non-array type", new Object[0])
        }
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(this.classPath, typeReference))
    }

    public final Unit analyzeNewInstance(AnalyzedInstruction analyzedInstruction) {
        Instruction instruction = analyzedInstruction.instruction
        ReferenceInstruction referenceInstruction = (ReferenceInstruction) instruction
        if (analyzedInstruction.getPostInstructionRegisterType(((OneRegisterInstruction) instruction).getRegisterA()).category != 0) {
            return
        }
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(Token.LITERAL4, RegisterType.getRegisterType(this.classPath, (TypeReference) referenceInstruction.getReference()).type))
    }

    public final Unit analyzeOdexReturnVoid(AnalyzedInstruction analyzedInstruction) {
        analyzeOdexReturnVoid(analyzedInstruction, true)
    }

    public final Unit analyzeOdexReturnVoid(AnalyzedInstruction analyzedInstruction, Boolean z) {
        analyzedInstruction.setDeodexedInstruction(ImmutableInstruction10x(Opcode.RETURN_VOID))
        if (z) {
            analyzeInstruction(analyzedInstruction)
        }
    }

    public final Boolean analyzePutGetVolatile(AnalyzedInstruction analyzedInstruction) {
        return analyzePutGetVolatile(analyzedInstruction, true)
    }

    public final Boolean analyzePutGetVolatile(AnalyzedInstruction analyzedInstruction, Boolean z) {
        ((FieldReference) ((ReferenceInstruction) analyzedInstruction.instruction).getReference()).getType()
        analyzedInstruction.instruction.getOpcode()
        throw null
    }

    public final Unit analyzeUnaryOp(AnalyzedInstruction analyzedInstruction, RegisterType registerType) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType)
    }

    public final Unit analyzeWideConst(AnalyzedInstruction analyzedInstruction) {
        setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.LONG_LO_TYPE)
    }

    public final Array<AnalyzedInstruction> buildExceptionHandlerArray(TryBlock<? extends ExceptionHandler> tryBlock) {
        List<? extends EH> exceptionHandlers = tryBlock.getExceptionHandlers()
        Array<AnalyzedInstruction> analyzedInstructionArr = new AnalyzedInstruction[exceptionHandlers.size()]
        for (Int i = 0; i < exceptionHandlers.size(); i++) {
            analyzedInstructionArr[i] = this.analyzedInstructions.get(((ExceptionHandler) exceptionHandlers.get(i)).getHandlerCodeAddress())
        }
        return analyzedInstructionArr
    }

    public final Unit buildInstructionList() {
        Int registerCount = this.methodImpl.getRegisterCount()
        ImmutableList immutableListCopyOf = ImmutableList.copyOf(this.methodImpl.getInstructions())
        this.analyzedInstructions.ensureCapacity(immutableListCopyOf.size())
        Int codeUnits = 0
        for (Int i = 0; i < immutableListCopyOf.size(); i++) {
            Instruction instruction = (Instruction) immutableListCopyOf.get(i)
            this.analyzedInstructions.append(codeUnits, AnalyzedInstruction(this, instruction, i, registerCount))
            codeUnits += instruction.getCodeUnits()
        }
        List listMassageTryBlocks = TryListBuilder.massageTryBlocks(this.methodImpl.getTryBlocks())
        Array<AnalyzedInstruction>[] analyzedInstructionArr = new AnalyzedInstruction[immutableListCopyOf.size()][]
        if (listMassageTryBlocks != null) {
            TryBlock<? extends ExceptionHandler> tryBlock = null
            Array<AnalyzedInstruction> analyzedInstructionArrBuildExceptionHandlerArray = null
            Int i2 = 0
            for (Int i3 = 0; i3 < this.analyzedInstructions.size(); i3++) {
                AnalyzedInstruction analyzedInstructionValueAt = this.analyzedInstructions.valueAt(i3)
                Opcode opcode = analyzedInstructionValueAt.instruction.getOpcode()
                Int instructionAddress = getInstructionAddress(analyzedInstructionValueAt)
                if (tryBlock != null && tryBlock.getStartCodeAddress() + tryBlock.getCodeUnitCount() <= instructionAddress) {
                    i2++
                    tryBlock = null
                }
                if (tryBlock == null && i2 < listMassageTryBlocks.size()) {
                    TryBlock<? extends ExceptionHandler> tryBlock2 = (TryBlock) listMassageTryBlocks.get(i2)
                    if (tryBlock2.getStartCodeAddress() <= instructionAddress) {
                        analyzedInstructionArrBuildExceptionHandlerArray = buildExceptionHandlerArray(tryBlock2)
                        tryBlock = tryBlock2
                    }
                }
                if (tryBlock != null && opcode.canThrow()) {
                    analyzedInstructionArr[i3] = analyzedInstructionArrBuildExceptionHandlerArray
                }
            }
        }
        BitSet bitSet = BitSet(immutableListCopyOf.size())
        addPredecessorSuccessor(this.startOfMethod, this.analyzedInstructions.valueAt(0), analyzedInstructionArr, bitSet)
        while (!bitSet.isEmpty()) {
            Int iNextSetBit = bitSet.nextSetBit(0)
            bitSet.clear(iNextSetBit)
            AnalyzedInstruction analyzedInstructionValueAt2 = this.analyzedInstructions.valueAt(iNextSetBit)
            Opcode opcode2 = analyzedInstructionValueAt2.instruction.getOpcode()
            Int instructionAddress2 = getInstructionAddress(analyzedInstructionValueAt2)
            if (analyzedInstructionValueAt2.instruction.getOpcode().canContinue()) {
                if (iNextSetBit == this.analyzedInstructions.size() - 1) {
                    throw AnalysisException("Execution can continue past the last instruction", new Object[0])
                }
                addPredecessorSuccessor(analyzedInstructionValueAt2, this.analyzedInstructions.valueAt(iNextSetBit + 1), analyzedInstructionArr, bitSet)
            }
            Instruction instruction2 = analyzedInstructionValueAt2.instruction
            if (instruction2 is OffsetInstruction) {
                OffsetInstruction offsetInstruction = (OffsetInstruction) instruction2
                if (opcode2 == Opcode.PACKED_SWITCH || opcode2 == Opcode.SPARSE_SWITCH) {
                    AnalyzedInstruction analyzedInstruction = this.analyzedInstructions.get(offsetInstruction.getCodeOffset() + instructionAddress2)
                    if (analyzedInstruction == null) {
                        throw AnalysisException("Invalid switch payload offset", new Object[0])
                    }
                    Iterator<? extends SwitchElement> it = ((SwitchPayload) analyzedInstruction.instruction).getSwitchElements().iterator()
                    while (it.hasNext()) {
                        AnalyzedInstruction analyzedInstruction2 = this.analyzedInstructions.get(it.next().getOffset() + instructionAddress2)
                        if (analyzedInstruction2 == null) {
                            throw AnalysisException("Invalid switch target offset", new Object[0])
                        }
                        addPredecessorSuccessor(analyzedInstructionValueAt2, analyzedInstruction2, analyzedInstructionArr, bitSet)
                    }
                } else if (opcode2 != Opcode.FILL_ARRAY_DATA) {
                    addPredecessorSuccessor(analyzedInstructionValueAt2, this.analyzedInstructions.get(instructionAddress2 + offsetInstruction.getCodeOffset()), analyzedInstructionArr, bitSet)
                }
            }
        }
    }

    fun getAnalysisException() {
        return this.analysisException
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return this.analyzedInstructions.getValues()
    }

    fun getClassPath() {
        return this.classPath
    }

    public final RegisterType getDestTypeForLiteralShiftRight(AnalyzedInstruction analyzedInstruction, Boolean z) {
        RegisterType andCheckSourceRegister = getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction) analyzedInstruction.instruction).getRegisterB(), Primitive32BitCategories)
        Long narrowLiteral = ((NarrowLiteralInstruction) analyzedInstruction.instruction).getNarrowLiteral()
        if (narrowLiteral == 0) {
            return andCheckSourceRegister
        }
        RegisterType registerType = !z ? RegisterType.INTEGER_TYPE : andCheckSourceRegister
        Long j = narrowLiteral & 31
        switch (andCheckSourceRegister.category) {
            case 2:
            case 3:
            case 4:
                return RegisterType.NULL_TYPE
            case 5:
            default:
                return registerType
            case 6:
                return RegisterType.POS_BYTE_TYPE
            case 7:
                return (!z || j < 8) ? registerType : RegisterType.BYTE_TYPE
            case 8:
                return j >= 8 ? RegisterType.POS_BYTE_TYPE : registerType
            case 9:
                return j > 8 ? RegisterType.POS_BYTE_TYPE : registerType
            case 10:
            case 11:
                return !z ? j > 24 ? RegisterType.POS_BYTE_TYPE : j >= 16 ? RegisterType.CHAR_TYPE : registerType : j >= 24 ? RegisterType.BYTE_TYPE : j >= 16 ? RegisterType.SHORT_TYPE : registerType
        }
    }

    fun getInstructionAddress(AnalyzedInstruction analyzedInstruction) {
        return this.analyzedInstructions.keyAt(analyzedInstruction.instructionIndex)
    }

    fun getParamRegisterCount() {
        return this.paramRegisterCount
    }

    public final MethodReference normalizeMethodReference(MethodReference methodReference) {
        methodReference.getDefiningClass()
        throw null
    }

    public final Unit propagateChanges(BitSet bitSet, Int i, Boolean z) {
        while (!bitSet.isEmpty()) {
            Int iNextSetBit = bitSet.nextSetBit(0)
            while (iNextSetBit >= 0) {
                bitSet.clear(iNextSetBit)
                propagateRegisterToSuccessors(this.analyzedInstructions.valueAt(iNextSetBit), i, bitSet, z)
                iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1)
            }
        }
    }

    public final Unit propagateParameterTypes(Int i) {
        Int i2 = 0
        for (MethodParameter methodParameter : this.method.getParameters()) {
            if (TypeUtils.isWideType(methodParameter)) {
                Int i3 = i2 + 1
                setPostRegisterTypeAndPropagateChanges(this.startOfMethod, i2 + i, RegisterType.getWideRegisterType(methodParameter, true))
                setPostRegisterTypeAndPropagateChanges(this.startOfMethod, i3 + i, RegisterType.getWideRegisterType(methodParameter, false))
                i2 = i3 + 1
            } else {
                setPostRegisterTypeAndPropagateChanges(this.startOfMethod, i2 + i, RegisterType.getRegisterType(this.classPath, methodParameter))
                i2++
            }
        }
    }

    public final Unit propagateRegisterToSuccessors(AnalyzedInstruction analyzedInstruction, Int i, BitSet bitSet, Boolean z) {
        RegisterType postInstructionRegisterType = analyzedInstruction.getPostInstructionRegisterType(i)
        Iterator<AnalyzedInstruction> it = analyzedInstruction.successors.iterator()
        while (it.hasNext()) {
            AnalyzedInstruction next = it.next()
            if (next.mergeRegister(i, postInstructionRegisterType, this.analyzedState, z)) {
                bitSet.set(next.instructionIndex)
            }
        }
    }

    public final Unit setDestinationRegisterTypeAndPropagateChanges(AnalyzedInstruction analyzedInstruction, RegisterType registerType) {
        setPostRegisterTypeAndPropagateChanges(analyzedInstruction, analyzedInstruction.getDestinationRegister(), registerType)
    }

    public final Unit setPostRegisterTypeAndPropagateChanges(AnalyzedInstruction analyzedInstruction, Int i, RegisterType registerType) {
        BitSet bitSet = BitSet(this.analyzedInstructions.size())
        if (analyzedInstruction.setPostRegisterType(i, registerType)) {
            propagateRegisterToSuccessors(analyzedInstruction, i, bitSet, false)
            propagateChanges(bitSet, i, false)
            Byte b2 = registerType.category
            if (b2 == 12) {
                checkWidePair(i, analyzedInstruction)
                setPostRegisterTypeAndPropagateChanges(analyzedInstruction, i + 1, RegisterType.LONG_HI_TYPE)
            } else if (b2 == 14) {
                checkWidePair(i, analyzedInstruction)
                setPostRegisterTypeAndPropagateChanges(analyzedInstruction, i + 1, RegisterType.DOUBLE_HI_TYPE)
            }
        }
    }
}
