package org.jf.dexlib2

import androidx.core.view.InputDeviceCompat
import androidx.appcompat.R
import com.google.common.collect.ImmutableRangeMap
import com.google.common.collect.Lists
import com.google.common.collect.Range
import com.google.common.collect.RangeMap
import java.util.ArrayList
import java.util.Iterator
import java.util.List

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'NOP' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
class Opcode {
    public static final /* synthetic */ Array<Opcode> $VALUES
    public static final Opcode ADD_DOUBLE
    public static final Opcode ADD_DOUBLE_2ADDR
    public static final Opcode ADD_FLOAT
    public static final Opcode ADD_FLOAT_2ADDR
    public static final Opcode ADD_INT
    public static final Opcode ADD_INT_2ADDR
    public static final Opcode ADD_INT_LIT16
    public static final Opcode ADD_INT_LIT8
    public static final Opcode ADD_LONG
    public static final Opcode ADD_LONG_2ADDR
    public static final Opcode AGET
    public static final Opcode AGET_BOOLEAN
    public static final Opcode AGET_BYTE
    public static final Opcode AGET_CHAR
    public static final Opcode AGET_OBJECT
    public static final Opcode AGET_SHORT
    public static final Opcode AGET_WIDE
    public static final Opcode AND_INT
    public static final Opcode AND_INT_2ADDR
    public static final Opcode AND_INT_LIT16
    public static final Opcode AND_INT_LIT8
    public static final Opcode AND_LONG
    public static final Opcode AND_LONG_2ADDR
    public static final Opcode APUT
    public static final Opcode APUT_BOOLEAN
    public static final Opcode APUT_BYTE
    public static final Opcode APUT_CHAR
    public static final Opcode APUT_OBJECT
    public static final Opcode APUT_SHORT
    public static final Opcode APUT_WIDE
    public static final Opcode ARRAY_LENGTH
    public static final Opcode ARRAY_PAYLOAD
    public static final Opcode CHECK_CAST
    public static final Opcode CMPG_DOUBLE
    public static final Opcode CMPG_FLOAT
    public static final Opcode CMPL_DOUBLE
    public static final Opcode CMPL_FLOAT
    public static final Opcode CMP_LONG
    public static final Opcode CONST
    public static final Opcode CONST_16
    public static final Opcode CONST_4
    public static final Opcode CONST_CLASS
    public static final Opcode CONST_HIGH16
    public static final Opcode CONST_METHOD_HANDLE
    public static final Opcode CONST_METHOD_TYPE
    public static final Opcode CONST_STRING
    public static final Opcode CONST_STRING_JUMBO
    public static final Opcode CONST_WIDE
    public static final Opcode CONST_WIDE_16
    public static final Opcode CONST_WIDE_32
    public static final Opcode CONST_WIDE_HIGH16
    public static final Opcode DIV_DOUBLE
    public static final Opcode DIV_DOUBLE_2ADDR
    public static final Opcode DIV_FLOAT
    public static final Opcode DIV_FLOAT_2ADDR
    public static final Opcode DIV_INT
    public static final Opcode DIV_INT_2ADDR
    public static final Opcode DIV_INT_LIT16
    public static final Opcode DIV_INT_LIT8
    public static final Opcode DIV_LONG
    public static final Opcode DIV_LONG_2ADDR
    public static final Opcode DOUBLE_TO_FLOAT
    public static final Opcode DOUBLE_TO_INT
    public static final Opcode DOUBLE_TO_LONG
    public static final Opcode EXECUTE_INLINE
    public static final Opcode EXECUTE_INLINE_RANGE
    public static final Opcode FILLED_NEW_ARRAY
    public static final Opcode FILLED_NEW_ARRAY_RANGE
    public static final Opcode FILL_ARRAY_DATA
    public static final Opcode FLOAT_TO_DOUBLE
    public static final Opcode FLOAT_TO_INT
    public static final Opcode FLOAT_TO_LONG
    public static final Opcode GOTO
    public static final Opcode GOTO_16
    public static final Opcode GOTO_32
    public static final Opcode IF_EQ
    public static final Opcode IF_EQZ
    public static final Opcode IF_GE
    public static final Opcode IF_GEZ
    public static final Opcode IF_GT
    public static final Opcode IF_GTZ
    public static final Opcode IF_LE
    public static final Opcode IF_LEZ
    public static final Opcode IF_LT
    public static final Opcode IF_LTZ
    public static final Opcode IF_NE
    public static final Opcode IF_NEZ
    public static final Opcode IGET
    public static final Opcode IGET_BOOLEAN
    public static final Opcode IGET_BOOLEAN_QUICK
    public static final Opcode IGET_BYTE
    public static final Opcode IGET_BYTE_QUICK
    public static final Opcode IGET_CHAR
    public static final Opcode IGET_CHAR_QUICK
    public static final Opcode IGET_OBJECT
    public static final Opcode IGET_OBJECT_QUICK
    public static final Opcode IGET_OBJECT_VOLATILE
    public static final Opcode IGET_QUICK
    public static final Opcode IGET_SHORT
    public static final Opcode IGET_SHORT_QUICK
    public static final Opcode IGET_VOLATILE
    public static final Opcode IGET_WIDE
    public static final Opcode IGET_WIDE_QUICK
    public static final Opcode IGET_WIDE_VOLATILE
    public static final Opcode INSTANCE_OF
    public static final Opcode INT_TO_BYTE
    public static final Opcode INT_TO_CHAR
    public static final Opcode INT_TO_DOUBLE
    public static final Opcode INT_TO_FLOAT
    public static final Opcode INT_TO_LONG
    public static final Opcode INT_TO_SHORT
    public static final Opcode INVOKE_CUSTOM
    public static final Opcode INVOKE_CUSTOM_RANGE
    public static final Opcode INVOKE_DIRECT
    public static final Opcode INVOKE_DIRECT_EMPTY
    public static final Opcode INVOKE_DIRECT_RANGE
    public static final Opcode INVOKE_INTERFACE
    public static final Opcode INVOKE_INTERFACE_RANGE
    public static final Opcode INVOKE_OBJECT_INIT_RANGE
    public static final Opcode INVOKE_POLYMORPHIC
    public static final Opcode INVOKE_POLYMORPHIC_RANGE
    public static final Opcode INVOKE_STATIC
    public static final Opcode INVOKE_STATIC_RANGE
    public static final Opcode INVOKE_SUPER
    public static final Opcode INVOKE_SUPER_QUICK
    public static final Opcode INVOKE_SUPER_QUICK_RANGE
    public static final Opcode INVOKE_SUPER_RANGE
    public static final Opcode INVOKE_VIRTUAL
    public static final Opcode INVOKE_VIRTUAL_QUICK
    public static final Opcode INVOKE_VIRTUAL_QUICK_RANGE
    public static final Opcode INVOKE_VIRTUAL_RANGE
    public static final Opcode IPUT
    public static final Opcode IPUT_BOOLEAN
    public static final Opcode IPUT_BOOLEAN_QUICK
    public static final Opcode IPUT_BYTE
    public static final Opcode IPUT_BYTE_QUICK
    public static final Opcode IPUT_CHAR
    public static final Opcode IPUT_CHAR_QUICK
    public static final Opcode IPUT_OBJECT
    public static final Opcode IPUT_OBJECT_QUICK
    public static final Opcode IPUT_OBJECT_VOLATILE
    public static final Opcode IPUT_QUICK
    public static final Opcode IPUT_SHORT
    public static final Opcode IPUT_SHORT_QUICK
    public static final Opcode IPUT_VOLATILE
    public static final Opcode IPUT_WIDE
    public static final Opcode IPUT_WIDE_QUICK
    public static final Opcode IPUT_WIDE_VOLATILE
    public static final Opcode LONG_TO_DOUBLE
    public static final Opcode LONG_TO_FLOAT
    public static final Opcode LONG_TO_INT
    public static final Opcode MONITOR_ENTER
    public static final Opcode MONITOR_EXIT
    public static final Opcode MOVE
    public static final Opcode MOVE_16
    public static final Opcode MOVE_EXCEPTION
    public static final Opcode MOVE_FROM16
    public static final Opcode MOVE_OBJECT
    public static final Opcode MOVE_OBJECT_16
    public static final Opcode MOVE_OBJECT_FROM16
    public static final Opcode MOVE_RESULT
    public static final Opcode MOVE_RESULT_OBJECT
    public static final Opcode MOVE_RESULT_WIDE
    public static final Opcode MOVE_WIDE
    public static final Opcode MOVE_WIDE_16
    public static final Opcode MOVE_WIDE_FROM16
    public static final Opcode MUL_DOUBLE
    public static final Opcode MUL_DOUBLE_2ADDR
    public static final Opcode MUL_FLOAT
    public static final Opcode MUL_FLOAT_2ADDR
    public static final Opcode MUL_INT
    public static final Opcode MUL_INT_2ADDR
    public static final Opcode MUL_INT_LIT16
    public static final Opcode MUL_INT_LIT8
    public static final Opcode MUL_LONG
    public static final Opcode MUL_LONG_2ADDR
    public static final Opcode NEG_DOUBLE
    public static final Opcode NEG_FLOAT
    public static final Opcode NEG_INT
    public static final Opcode NEG_LONG
    public static final Opcode NEW_ARRAY
    public static final Opcode NEW_INSTANCE
    public static final Opcode NOP
    public static final Opcode NOT_INT
    public static final Opcode NOT_LONG
    public static final Opcode OR_INT
    public static final Opcode OR_INT_2ADDR
    public static final Opcode OR_INT_LIT16
    public static final Opcode OR_INT_LIT8
    public static final Opcode OR_LONG
    public static final Opcode OR_LONG_2ADDR
    public static final Opcode PACKED_SWITCH
    public static final Opcode PACKED_SWITCH_PAYLOAD
    public static final Opcode REM_DOUBLE
    public static final Opcode REM_DOUBLE_2ADDR
    public static final Opcode REM_FLOAT
    public static final Opcode REM_FLOAT_2ADDR
    public static final Opcode REM_INT
    public static final Opcode REM_INT_2ADDR
    public static final Opcode REM_INT_LIT16
    public static final Opcode REM_INT_LIT8
    public static final Opcode REM_LONG
    public static final Opcode REM_LONG_2ADDR
    public static final Opcode RETURN
    public static final Opcode RETURN_OBJECT
    public static final Opcode RETURN_VOID
    public static final Opcode RETURN_VOID_BARRIER
    public static final Opcode RETURN_VOID_NO_BARRIER
    public static final Opcode RETURN_WIDE
    public static final Opcode RSUB_INT
    public static final Opcode RSUB_INT_LIT8
    public static final Opcode SGET
    public static final Opcode SGET_BOOLEAN
    public static final Opcode SGET_BYTE
    public static final Opcode SGET_CHAR
    public static final Opcode SGET_OBJECT
    public static final Opcode SGET_OBJECT_VOLATILE
    public static final Opcode SGET_SHORT
    public static final Opcode SGET_VOLATILE
    public static final Opcode SGET_WIDE
    public static final Opcode SGET_WIDE_VOLATILE
    public static final Opcode SHL_INT
    public static final Opcode SHL_INT_2ADDR
    public static final Opcode SHL_INT_LIT8
    public static final Opcode SHL_LONG
    public static final Opcode SHL_LONG_2ADDR
    public static final Opcode SHR_INT
    public static final Opcode SHR_INT_2ADDR
    public static final Opcode SHR_INT_LIT8
    public static final Opcode SHR_LONG
    public static final Opcode SHR_LONG_2ADDR
    public static final Opcode SPARSE_SWITCH
    public static final Opcode SPARSE_SWITCH_PAYLOAD
    public static final Opcode SPUT
    public static final Opcode SPUT_BOOLEAN
    public static final Opcode SPUT_BYTE
    public static final Opcode SPUT_CHAR
    public static final Opcode SPUT_OBJECT
    public static final Opcode SPUT_OBJECT_VOLATILE
    public static final Opcode SPUT_SHORT
    public static final Opcode SPUT_VOLATILE
    public static final Opcode SPUT_WIDE
    public static final Opcode SPUT_WIDE_VOLATILE
    public static final Opcode SUB_DOUBLE
    public static final Opcode SUB_DOUBLE_2ADDR
    public static final Opcode SUB_FLOAT
    public static final Opcode SUB_FLOAT_2ADDR
    public static final Opcode SUB_INT
    public static final Opcode SUB_INT_2ADDR
    public static final Opcode SUB_LONG
    public static final Opcode SUB_LONG_2ADDR
    public static final Opcode THROW
    public static final Opcode THROW_VERIFICATION_ERROR
    public static final Opcode USHR_INT
    public static final Opcode USHR_INT_2ADDR
    public static final Opcode USHR_INT_LIT8
    public static final Opcode USHR_LONG
    public static final Opcode USHR_LONG_2ADDR
    public static final Opcode XOR_INT
    public static final Opcode XOR_INT_2ADDR
    public static final Opcode XOR_INT_LIT16
    public static final Opcode XOR_INT_LIT8
    public static final Opcode XOR_LONG
    public static final Opcode XOR_LONG_2ADDR
    public final RangeMap<Integer, Short> apiToValueMap
    public final RangeMap<Integer, Short> artVersionToValueMap
    public final Int flags
    public final Format format
    public final String name
    public final Int referenceType
    public final Int referenceType2

    public static class VersionConstraint {
        public final Range<Integer> apiRange
        public final Range<Integer> artVersionRange
        public final Int opcodeValue

        constructor(Range<Integer> range, Range<Integer> range2, Int i) {
            this.apiRange = range
            this.artVersionRange = range2
            this.opcodeValue = i
        }
    }

    static {
        Format format = Format.Format10x
        Opcode opcode = Opcode("NOP", 0, 0, "nop", 7, format, 4)
        NOP = opcode
        Format format2 = Format.Format12x
        Opcode opcode2 = Opcode("MOVE", 1, 1, "move", 7, format2, 20)
        MOVE = opcode2
        Format format3 = Format.Format22x
        Opcode opcode3 = Opcode("MOVE_FROM16", 2, 2, "move/from16", 7, format3, 20)
        MOVE_FROM16 = opcode3
        Format format4 = Format.Format32x
        Opcode opcode4 = Opcode("MOVE_16", 3, 3, "move/16", 7, format4, 20)
        MOVE_16 = opcode4
        Opcode opcode5 = Opcode("MOVE_WIDE", 4, 4, "move-wide", 7, format2, 52)
        MOVE_WIDE = opcode5
        Opcode opcode6 = Opcode("MOVE_WIDE_FROM16", 5, 5, "move-wide/from16", 7, format3, 52)
        MOVE_WIDE_FROM16 = opcode6
        Opcode opcode7 = Opcode("MOVE_WIDE_16", 6, 6, "move-wide/16", 7, format4, 52)
        MOVE_WIDE_16 = opcode7
        Opcode opcode8 = Opcode("MOVE_OBJECT", 7, 7, "move-object", 7, format2, 20)
        MOVE_OBJECT = opcode8
        Opcode opcode9 = Opcode("MOVE_OBJECT_FROM16", 8, 8, "move-object/from16", 7, format3, 20)
        MOVE_OBJECT_FROM16 = opcode9
        Opcode opcode10 = Opcode("MOVE_OBJECT_16", 9, 9, "move-object/16", 7, format4, 20)
        MOVE_OBJECT_16 = opcode10
        Format format5 = Format.Format11x
        Opcode opcode11 = Opcode("MOVE_RESULT", 10, 10, "move-result", 7, format5, 20)
        MOVE_RESULT = opcode11
        Opcode opcode12 = Opcode("MOVE_RESULT_WIDE", 11, 11, "move-result-wide", 7, format5, 52)
        MOVE_RESULT_WIDE = opcode12
        Opcode opcode13 = Opcode("MOVE_RESULT_OBJECT", 12, 12, "move-result-object", 7, format5, 20)
        MOVE_RESULT_OBJECT = opcode13
        Opcode opcode14 = Opcode("MOVE_EXCEPTION", 13, 13, "move-exception", 7, format5, 20)
        MOVE_EXCEPTION = opcode14
        Opcode opcode15 = Opcode("RETURN_VOID", 14, 14, "return-Unit", 7, format)
        RETURN_VOID = opcode15
        Opcode opcode16 = Opcode("RETURN", 15, 15, "return", 7, format5)
        RETURN = opcode16
        Opcode opcode17 = Opcode("RETURN_WIDE", 16, 16, "return-wide", 7, format5)
        RETURN_WIDE = opcode17
        Opcode opcode18 = Opcode("RETURN_OBJECT", 17, 17, "return-object", 7, format5)
        RETURN_OBJECT = opcode18
        Opcode opcode19 = Opcode("CONST_4", 18, 18, "const/4", 7, Format.Format11n, 20)
        CONST_4 = opcode19
        Format format6 = Format.Format21s
        Opcode opcode20 = Opcode("CONST_16", 19, 19, "const/16", 7, format6, 20)
        CONST_16 = opcode20
        Format format7 = Format.Format31i
        Opcode opcode21 = Opcode("CONST", 20, 20, "const", 7, format7, 20)
        CONST = opcode21
        Opcode opcode22 = Opcode("CONST_HIGH16", 21, 21, "const/high16", 7, Format.Format21ih, 20)
        CONST_HIGH16 = opcode22
        Opcode opcode23 = Opcode("CONST_WIDE_16", 22, 22, "const-wide/16", 7, format6, 52)
        CONST_WIDE_16 = opcode23
        Opcode opcode24 = Opcode("CONST_WIDE_32", 23, 23, "const-wide/32", 7, format7, 52)
        CONST_WIDE_32 = opcode24
        Opcode opcode25 = Opcode("CONST_WIDE", 24, 24, "const-wide", 7, Format.Format51l, 52)
        CONST_WIDE = opcode25
        Opcode opcode26 = Opcode("CONST_WIDE_HIGH16", 25, 25, "const-wide/high16", 7, Format.Format21lh, 52)
        CONST_WIDE_HIGH16 = opcode26
        Format format8 = Format.Format21c
        Opcode opcode27 = Opcode("CONST_STRING", 26, 26, "const-string", 0, format8, 21)
        CONST_STRING = opcode27
        Opcode opcode28 = Opcode("CONST_STRING_JUMBO", 27, 27, "const-string/jumbo", 0, Format.Format31c, 21)
        CONST_STRING_JUMBO = opcode28
        Opcode opcode29 = Opcode("CONST_CLASS", 28, 28, "const-class", 1, format8, 21)
        CONST_CLASS = opcode29
        Opcode opcode30 = Opcode("MONITOR_ENTER", 29, 29, "monitor-enter", 7, format5, 5)
        MONITOR_ENTER = opcode30
        Opcode opcode31 = Opcode("MONITOR_EXIT", 30, 30, "monitor-exit", 7, format5, 5)
        MONITOR_EXIT = opcode31
        Opcode opcode32 = Opcode("CHECK_CAST", 31, 31, "check-cast", 1, format8, 21)
        CHECK_CAST = opcode32
        Format format9 = Format.Format22c
        Opcode opcode33 = Opcode("INSTANCE_OF", 32, 32, "instance-of", 1, format9, 21)
        INSTANCE_OF = opcode33
        Opcode opcode34 = Opcode("ARRAY_LENGTH", 33, 33, "array-length", 7, format2, 21)
        ARRAY_LENGTH = opcode34
        Opcode opcode35 = Opcode("NEW_INSTANCE", 34, 34, "new-instance", 1, format8, 21)
        NEW_INSTANCE = opcode35
        Opcode opcode36 = Opcode("NEW_ARRAY", 35, 35, "new-array", 1, format9, 21)
        NEW_ARRAY = opcode36
        Format format10 = Format.Format35c
        Opcode opcode37 = Opcode("FILLED_NEW_ARRAY", 36, 36, "filled-new-array", 1, format10, 13)
        FILLED_NEW_ARRAY = opcode37
        Format format11 = Format.Format3rc
        Opcode opcode38 = Opcode("FILLED_NEW_ARRAY_RANGE", 37, 37, "filled-new-array/range", 1, format11, 13)
        FILLED_NEW_ARRAY_RANGE = opcode38
        Format format12 = Format.Format31t
        Opcode opcode39 = Opcode("FILL_ARRAY_DATA", 38, 38, "fill-array-data", 7, format12, 4)
        FILL_ARRAY_DATA = opcode39
        Opcode opcode40 = Opcode("THROW", 39, 39, "throw", 7, format5, 1)
        THROW = opcode40
        Opcode opcode41 = Opcode("GOTO", 40, 40, "goto", 7, Format.Format10t)
        GOTO = opcode41
        Opcode opcode42 = Opcode("GOTO_16", 41, 41, "goto/16", 7, Format.Format20t)
        GOTO_16 = opcode42
        Opcode opcode43 = Opcode("GOTO_32", 42, 42, "goto/32", 7, Format.Format30t)
        GOTO_32 = opcode43
        Opcode opcode44 = Opcode("PACKED_SWITCH", 43, 43, "packed-switch", 7, format12, 4)
        PACKED_SWITCH = opcode44
        Opcode opcode45 = Opcode("SPARSE_SWITCH", 44, 44, "sparse-switch", 7, format12, 4)
        SPARSE_SWITCH = opcode45
        Format format13 = Format.Format23x
        Opcode opcode46 = Opcode("CMPL_FLOAT", 45, 45, "cmpl-Float", 7, format13, 20)
        CMPL_FLOAT = opcode46
        Opcode opcode47 = Opcode("CMPG_FLOAT", 46, 46, "cmpg-Float", 7, format13, 20)
        CMPG_FLOAT = opcode47
        Opcode opcode48 = Opcode("CMPL_DOUBLE", 47, 47, "cmpl-Double", 7, format13, 20)
        CMPL_DOUBLE = opcode48
        Opcode opcode49 = Opcode("CMPG_DOUBLE", 48, 48, "cmpg-Double", 7, format13, 20)
        CMPG_DOUBLE = opcode49
        Opcode opcode50 = Opcode("CMP_LONG", 49, 49, "cmp-Long", 7, format13, 20)
        CMP_LONG = opcode50
        Format format14 = Format.Format22t
        Opcode opcode51 = Opcode("IF_EQ", 50, 50, "if-eq", 7, format14, 4)
        IF_EQ = opcode51
        Opcode opcode52 = Opcode("IF_NE", 51, 51, "if-ne", 7, format14, 4)
        IF_NE = opcode52
        Opcode opcode53 = Opcode("IF_LT", 52, 52, "if-lt", 7, format14, 4)
        IF_LT = opcode53
        Opcode opcode54 = Opcode("IF_GE", 53, 53, "if-ge", 7, format14, 4)
        IF_GE = opcode54
        Opcode opcode55 = Opcode("IF_GT", 54, 54, "if-gt", 7, format14, 4)
        IF_GT = opcode55
        Opcode opcode56 = Opcode("IF_LE", 55, 55, "if-le", 7, format14, 4)
        IF_LE = opcode56
        Format format15 = Format.Format21t
        Opcode opcode57 = Opcode("IF_EQZ", 56, 56, "if-eqz", 7, format15, 4)
        IF_EQZ = opcode57
        Opcode opcode58 = Opcode("IF_NEZ", 57, 57, "if-nez", 7, format15, 4)
        IF_NEZ = opcode58
        Opcode opcode59 = Opcode("IF_LTZ", 58, 58, "if-ltz", 7, format15, 4)
        IF_LTZ = opcode59
        Opcode opcode60 = Opcode("IF_GEZ", 59, 59, "if-gez", 7, format15, 4)
        IF_GEZ = opcode60
        Opcode opcode61 = Opcode("IF_GTZ", 60, 60, "if-gtz", 7, format15, 4)
        IF_GTZ = opcode61
        Opcode opcode62 = Opcode("IF_LEZ", 61, 61, "if-lez", 7, format15, 4)
        IF_LEZ = opcode62
        Opcode opcode63 = Opcode("AGET", 62, 68, "aget", 7, format13, 21)
        AGET = opcode63
        Opcode opcode64 = Opcode("AGET_WIDE", 63, 69, "aget-wide", 7, format13, 53)
        AGET_WIDE = opcode64
        Opcode opcode65 = Opcode("AGET_OBJECT", 64, 70, "aget-object", 7, format13, 21)
        AGET_OBJECT = opcode65
        Opcode opcode66 = Opcode("AGET_BOOLEAN", 65, 71, "aget-Boolean", 7, format13, 21)
        AGET_BOOLEAN = opcode66
        Opcode opcode67 = Opcode("AGET_BYTE", 66, 72, "aget-Byte", 7, format13, 21)
        AGET_BYTE = opcode67
        Opcode opcode68 = Opcode("AGET_CHAR", 67, 73, "aget-Char", 7, format13, 21)
        AGET_CHAR = opcode68
        Opcode opcode69 = Opcode("AGET_SHORT", 68, 74, "aget-Short", 7, format13, 21)
        AGET_SHORT = opcode69
        Opcode opcode70 = Opcode("APUT", 69, 75, "aput", 7, format13, 5)
        APUT = opcode70
        Opcode opcode71 = Opcode("APUT_WIDE", 70, 76, "aput-wide", 7, format13, 5)
        APUT_WIDE = opcode71
        Opcode opcode72 = Opcode("APUT_OBJECT", 71, 77, "aput-object", 7, format13, 5)
        APUT_OBJECT = opcode72
        Opcode opcode73 = Opcode("APUT_BOOLEAN", 72, 78, "aput-Boolean", 7, format13, 5)
        APUT_BOOLEAN = opcode73
        Opcode opcode74 = Opcode("APUT_BYTE", 73, 79, "aput-Byte", 7, format13, 5)
        APUT_BYTE = opcode74
        Opcode opcode75 = Opcode("APUT_CHAR", 74, 80, "aput-Char", 7, format13, 5)
        APUT_CHAR = opcode75
        Opcode opcode76 = Opcode("APUT_SHORT", 75, 81, "aput-Short", 7, format13, 5)
        APUT_SHORT = opcode76
        Opcode opcode77 = Opcode("IGET", 76, 82, "iget", 2, format9, 21)
        IGET = opcode77
        Opcode opcode78 = Opcode("IGET_WIDE", 77, 83, "iget-wide", 2, format9, 53)
        IGET_WIDE = opcode78
        Opcode opcode79 = Opcode("IGET_OBJECT", 78, 84, "iget-object", 2, format9, 21)
        IGET_OBJECT = opcode79
        Opcode opcode80 = Opcode("IGET_BOOLEAN", 79, 85, "iget-Boolean", 2, format9, 21)
        IGET_BOOLEAN = opcode80
        Opcode opcode81 = Opcode("IGET_BYTE", 80, 86, "iget-Byte", 2, format9, 21)
        IGET_BYTE = opcode81
        Opcode opcode82 = Opcode("IGET_CHAR", 81, 87, "iget-Char", 2, format9, 21)
        IGET_CHAR = opcode82
        Opcode opcode83 = Opcode("IGET_SHORT", 82, 88, "iget-Short", 2, format9, 21)
        IGET_SHORT = opcode83
        Opcode opcode84 = Opcode("IPUT", 83, 89, "iput", 2, format9, 5)
        IPUT = opcode84
        Opcode opcode85 = Opcode("IPUT_WIDE", 84, 90, "iput-wide", 2, format9, 5)
        IPUT_WIDE = opcode85
        Opcode opcode86 = Opcode("IPUT_OBJECT", 85, 91, "iput-object", 2, format9, 5)
        IPUT_OBJECT = opcode86
        Opcode opcode87 = Opcode("IPUT_BOOLEAN", 86, 92, "iput-Boolean", 2, format9, 5)
        IPUT_BOOLEAN = opcode87
        Opcode opcode88 = Opcode("IPUT_BYTE", 87, 93, "iput-Byte", 2, format9, 5)
        IPUT_BYTE = opcode88
        Opcode opcode89 = Opcode("IPUT_CHAR", 88, 94, "iput-Char", 2, format9, 5)
        IPUT_CHAR = opcode89
        Opcode opcode90 = Opcode("IPUT_SHORT", 89, 95, "iput-Short", 2, format9, 5)
        IPUT_SHORT = opcode90
        Opcode opcode91 = Opcode("SGET", 90, 96, "sget", 2, format8, 277)
        SGET = opcode91
        Opcode opcode92 = Opcode("SGET_WIDE", 91, 97, "sget-wide", 2, format8, 309)
        SGET_WIDE = opcode92
        Opcode opcode93 = Opcode("SGET_OBJECT", 92, 98, "sget-object", 2, format8, 277)
        SGET_OBJECT = opcode93
        Opcode opcode94 = Opcode("SGET_BOOLEAN", 93, 99, "sget-Boolean", 2, format8, 277)
        SGET_BOOLEAN = opcode94
        Opcode opcode95 = Opcode("SGET_BYTE", 94, 100, "sget-Byte", 2, format8, 277)
        SGET_BYTE = opcode95
        Opcode opcode96 = Opcode("SGET_CHAR", 95, R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle, "sget-Char", 2, format8, 277)
        SGET_CHAR = opcode96
        Opcode opcode97 = Opcode("SGET_SHORT", 96, R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle, "sget-Short", 2, format8, 277)
        SGET_SHORT = opcode97
        Opcode opcode98 = Opcode("SPUT", 97, R.styleable.AppCompatTheme_autoCompleteTextViewStyle, "sput", 2, format8, 261)
        SPUT = opcode98
        Opcode opcode99 = Opcode("SPUT_WIDE", 98, R.styleable.AppCompatTheme_buttonStyle, "sput-wide", 2, format8, 261)
        SPUT_WIDE = opcode99
        Opcode opcode100 = Opcode("SPUT_OBJECT", 99, R.styleable.AppCompatTheme_buttonStyleSmall, "sput-object", 2, format8, 261)
        SPUT_OBJECT = opcode100
        Opcode opcode101 = Opcode("SPUT_BOOLEAN", 100, R.styleable.AppCompatTheme_checkboxStyle, "sput-Boolean", 2, format8, 261)
        SPUT_BOOLEAN = opcode101
        Opcode opcode102 = Opcode("SPUT_BYTE", R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle, R.styleable.AppCompatTheme_checkedTextViewStyle, "sput-Byte", 2, format8, 261)
        SPUT_BYTE = opcode102
        Opcode opcode103 = Opcode("SPUT_CHAR", R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle, 108, "sput-Char", 2, format8, 261)
        SPUT_CHAR = opcode103
        Opcode opcode104 = Opcode("SPUT_SHORT", R.styleable.AppCompatTheme_autoCompleteTextViewStyle, 109, "sput-Short", 2, format8, 261)
        SPUT_SHORT = opcode104
        Opcode opcode105 = Opcode("INVOKE_VIRTUAL", R.styleable.AppCompatTheme_buttonStyle, R.styleable.AppCompatTheme_ratingBarStyle, "invoke-virtual", 3, format10, 13)
        INVOKE_VIRTUAL = opcode105
        Opcode opcode106 = Opcode("INVOKE_SUPER", R.styleable.AppCompatTheme_buttonStyleSmall, R.styleable.AppCompatTheme_ratingBarStyleIndicator, "invoke-super", 3, format10, 13)
        INVOKE_SUPER = opcode106
        Opcode opcode107 = Opcode("INVOKE_DIRECT", R.styleable.AppCompatTheme_checkboxStyle, R.styleable.AppCompatTheme_ratingBarStyleSmall, "invoke-direct", 3, format10, 1037)
        INVOKE_DIRECT = opcode107
        Opcode opcode108 = Opcode("INVOKE_STATIC", R.styleable.AppCompatTheme_checkedTextViewStyle, R.styleable.AppCompatTheme_seekBarStyle, "invoke-static", 3, format10, 13)
        INVOKE_STATIC = opcode108
        Opcode opcode109 = Opcode("INVOKE_INTERFACE", 108, R.styleable.AppCompatTheme_spinnerStyle, "invoke-interface", 3, format10, 13)
        INVOKE_INTERFACE = opcode109
        Opcode opcode110 = Opcode("INVOKE_VIRTUAL_RANGE", 109, R.styleable.AppCompatTheme_listMenuViewStyle, "invoke-virtual/range", 3, format11, 13)
        INVOKE_VIRTUAL_RANGE = opcode110
        Opcode opcode111 = Opcode("INVOKE_SUPER_RANGE", R.styleable.AppCompatTheme_ratingBarStyle, R.styleable.AppCompatTheme_tooltipFrameBackground, "invoke-super/range", 3, format11, 13)
        INVOKE_SUPER_RANGE = opcode111
        Opcode opcode112 = Opcode("INVOKE_DIRECT_RANGE", R.styleable.AppCompatTheme_ratingBarStyleIndicator, R.styleable.AppCompatTheme_tooltipForegroundColor, "invoke-direct/range", 3, format11, 1037)
        INVOKE_DIRECT_RANGE = opcode112
        Opcode opcode113 = Opcode("INVOKE_STATIC_RANGE", R.styleable.AppCompatTheme_ratingBarStyleSmall, R.styleable.AppCompatTheme_colorError, "invoke-static/range", 3, format11, 13)
        INVOKE_STATIC_RANGE = opcode113
        Opcode opcode114 = Opcode("INVOKE_INTERFACE_RANGE", R.styleable.AppCompatTheme_seekBarStyle, 120, "invoke-interface/range", 3, format11, 13)
        INVOKE_INTERFACE_RANGE = opcode114
        Opcode opcode115 = Opcode("NEG_INT", R.styleable.AppCompatTheme_spinnerStyle, 123, "neg-Int", 7, format2, 20)
        NEG_INT = opcode115
        Opcode opcode116 = Opcode("NOT_INT", R.styleable.AppCompatTheme_switchStyle, 124, "not-Int", 7, format2, 20)
        NOT_INT = opcode116
        Opcode opcode117 = Opcode("NEG_LONG", R.styleable.AppCompatTheme_listMenuViewStyle, 125, "neg-Long", 7, format2, 52)
        NEG_LONG = opcode117
        Opcode opcode118 = Opcode("NOT_LONG", R.styleable.AppCompatTheme_tooltipFrameBackground, 126, "not-Long", 7, format2, 52)
        NOT_LONG = opcode118
        Opcode opcode119 = Opcode("NEG_FLOAT", R.styleable.AppCompatTheme_tooltipForegroundColor, 127, "neg-Float", 7, format2, 20)
        NEG_FLOAT = opcode119
        Opcode opcode120 = Opcode("NEG_DOUBLE", R.styleable.AppCompatTheme_colorError, 128, "neg-Double", 7, format2, 52)
        NEG_DOUBLE = opcode120
        Opcode opcode121 = Opcode("INT_TO_LONG", 120, 129, "Int-to-Long", 7, format2, 52)
        INT_TO_LONG = opcode121
        Opcode opcode122 = Opcode("INT_TO_FLOAT", 121, 130, "Int-to-Float", 7, format2, 20)
        INT_TO_FLOAT = opcode122
        Opcode opcode123 = Opcode("INT_TO_DOUBLE", 122, 131, "Int-to-Double", 7, format2, 52)
        INT_TO_DOUBLE = opcode123
        Opcode opcode124 = Opcode("LONG_TO_INT", 123, 132, "Long-to-Int", 7, format2, 20)
        LONG_TO_INT = opcode124
        Opcode opcode125 = Opcode("LONG_TO_FLOAT", 124, 133, "Long-to-Float", 7, format2, 20)
        LONG_TO_FLOAT = opcode125
        Opcode opcode126 = Opcode("LONG_TO_DOUBLE", 125, 134, "Long-to-Double", 7, format2, 52)
        LONG_TO_DOUBLE = opcode126
        Opcode opcode127 = Opcode("FLOAT_TO_INT", 126, 135, "Float-to-Int", 7, format2, 20)
        FLOAT_TO_INT = opcode127
        Opcode opcode128 = Opcode("FLOAT_TO_LONG", 127, 136, "Float-to-Long", 7, format2, 52)
        FLOAT_TO_LONG = opcode128
        Opcode opcode129 = Opcode("FLOAT_TO_DOUBLE", 128, 137, "Float-to-Double", 7, format2, 52)
        FLOAT_TO_DOUBLE = opcode129
        Opcode opcode130 = Opcode("DOUBLE_TO_INT", 129, 138, "Double-to-Int", 7, format2, 20)
        DOUBLE_TO_INT = opcode130
        Opcode opcode131 = Opcode("DOUBLE_TO_LONG", 130, 139, "Double-to-Long", 7, format2, 52)
        DOUBLE_TO_LONG = opcode131
        Opcode opcode132 = Opcode("DOUBLE_TO_FLOAT", 131, 140, "Double-to-Float", 7, format2, 20)
        DOUBLE_TO_FLOAT = opcode132
        Opcode opcode133 = Opcode("INT_TO_BYTE", 132, 141, "Int-to-Byte", 7, format2, 20)
        INT_TO_BYTE = opcode133
        Opcode opcode134 = Opcode("INT_TO_CHAR", 133, 142, "Int-to-Char", 7, format2, 20)
        INT_TO_CHAR = opcode134
        Opcode opcode135 = Opcode("INT_TO_SHORT", 134, 143, "Int-to-Short", 7, format2, 20)
        INT_TO_SHORT = opcode135
        Opcode opcode136 = Opcode("ADD_INT", 135, 144, "add-Int", 7, format13, 20)
        ADD_INT = opcode136
        Opcode opcode137 = Opcode("SUB_INT", 136, 145, "sub-Int", 7, format13, 20)
        SUB_INT = opcode137
        Opcode opcode138 = Opcode("MUL_INT", 137, 146, "mul-Int", 7, format13, 20)
        MUL_INT = opcode138
        Opcode opcode139 = Opcode("DIV_INT", 138, 147, "div-Int", 7, format13, 21)
        DIV_INT = opcode139
        Opcode opcode140 = Opcode("REM_INT", 139, 148, "rem-Int", 7, format13, 21)
        REM_INT = opcode140
        Opcode opcode141 = Opcode("AND_INT", 140, 149, "and-Int", 7, format13, 20)
        AND_INT = opcode141
        Opcode opcode142 = Opcode("OR_INT", 141, 150, "or-Int", 7, format13, 20)
        OR_INT = opcode142
        Opcode opcode143 = Opcode("XOR_INT", 142, 151, "xor-Int", 7, format13, 20)
        XOR_INT = opcode143
        Opcode opcode144 = Opcode("SHL_INT", 143, 152, "shl-Int", 7, format13, 20)
        SHL_INT = opcode144
        Opcode opcode145 = Opcode("SHR_INT", 144, 153, "shr-Int", 7, format13, 20)
        SHR_INT = opcode145
        Opcode opcode146 = Opcode("USHR_INT", 145, 154, "ushr-Int", 7, format13, 20)
        USHR_INT = opcode146
        Opcode opcode147 = Opcode("ADD_LONG", 146, 155, "add-Long", 7, format13, 52)
        ADD_LONG = opcode147
        Opcode opcode148 = Opcode("SUB_LONG", 147, 156, "sub-Long", 7, format13, 52)
        SUB_LONG = opcode148
        Opcode opcode149 = Opcode("MUL_LONG", 148, 157, "mul-Long", 7, format13, 52)
        MUL_LONG = opcode149
        Opcode opcode150 = Opcode("DIV_LONG", 149, 158, "div-Long", 7, format13, 53)
        DIV_LONG = opcode150
        Opcode opcode151 = Opcode("REM_LONG", 150, 159, "rem-Long", 7, format13, 53)
        REM_LONG = opcode151
        Opcode opcode152 = Opcode("AND_LONG", 151, 160, "and-Long", 7, format13, 52)
        AND_LONG = opcode152
        Opcode opcode153 = Opcode("OR_LONG", 152, 161, "or-Long", 7, format13, 52)
        OR_LONG = opcode153
        Opcode opcode154 = Opcode("XOR_LONG", 153, 162, "xor-Long", 7, format13, 52)
        XOR_LONG = opcode154
        Opcode opcode155 = Opcode("SHL_LONG", 154, 163, "shl-Long", 7, format13, 52)
        SHL_LONG = opcode155
        Opcode opcode156 = Opcode("SHR_LONG", 155, 164, "shr-Long", 7, format13, 52)
        SHR_LONG = opcode156
        Opcode opcode157 = Opcode("USHR_LONG", 156, 165, "ushr-Long", 7, format13, 52)
        USHR_LONG = opcode157
        Opcode opcode158 = Opcode("ADD_FLOAT", 157, 166, "add-Float", 7, format13, 20)
        ADD_FLOAT = opcode158
        Opcode opcode159 = Opcode("SUB_FLOAT", 158, 167, "sub-Float", 7, format13, 20)
        SUB_FLOAT = opcode159
        Opcode opcode160 = Opcode("MUL_FLOAT", 159, 168, "mul-Float", 7, format13, 20)
        MUL_FLOAT = opcode160
        Opcode opcode161 = Opcode("DIV_FLOAT", 160, 169, "div-Float", 7, format13, 20)
        DIV_FLOAT = opcode161
        Opcode opcode162 = Opcode("REM_FLOAT", 161, 170, "rem-Float", 7, format13, 20)
        REM_FLOAT = opcode162
        Opcode opcode163 = Opcode("ADD_DOUBLE", 162, 171, "add-Double", 7, format13, 52)
        ADD_DOUBLE = opcode163
        Opcode opcode164 = Opcode("SUB_DOUBLE", 163, 172, "sub-Double", 7, format13, 52)
        SUB_DOUBLE = opcode164
        Opcode opcode165 = Opcode("MUL_DOUBLE", 164, 173, "mul-Double", 7, format13, 52)
        MUL_DOUBLE = opcode165
        Opcode opcode166 = Opcode("DIV_DOUBLE", 165, 174, "div-Double", 7, format13, 52)
        DIV_DOUBLE = opcode166
        Opcode opcode167 = Opcode("REM_DOUBLE", 166, 175, "rem-Double", 7, format13, 52)
        REM_DOUBLE = opcode167
        Opcode opcode168 = Opcode("ADD_INT_2ADDR", 167, 176, "add-Int/2addr", 7, format2, 20)
        ADD_INT_2ADDR = opcode168
        Opcode opcode169 = Opcode("SUB_INT_2ADDR", 168, 177, "sub-Int/2addr", 7, format2, 20)
        SUB_INT_2ADDR = opcode169
        Opcode opcode170 = Opcode("MUL_INT_2ADDR", 169, 178, "mul-Int/2addr", 7, format2, 20)
        MUL_INT_2ADDR = opcode170
        Opcode opcode171 = Opcode("DIV_INT_2ADDR", 170, 179, "div-Int/2addr", 7, format2, 21)
        DIV_INT_2ADDR = opcode171
        Opcode opcode172 = Opcode("REM_INT_2ADDR", 171, 180, "rem-Int/2addr", 7, format2, 21)
        REM_INT_2ADDR = opcode172
        Opcode opcode173 = Opcode("AND_INT_2ADDR", 172, 181, "and-Int/2addr", 7, format2, 20)
        AND_INT_2ADDR = opcode173
        Opcode opcode174 = Opcode("OR_INT_2ADDR", 173, 182, "or-Int/2addr", 7, format2, 20)
        OR_INT_2ADDR = opcode174
        Opcode opcode175 = Opcode("XOR_INT_2ADDR", 174, 183, "xor-Int/2addr", 7, format2, 20)
        XOR_INT_2ADDR = opcode175
        Opcode opcode176 = Opcode("SHL_INT_2ADDR", 175, 184, "shl-Int/2addr", 7, format2, 20)
        SHL_INT_2ADDR = opcode176
        Opcode opcode177 = Opcode("SHR_INT_2ADDR", 176, 185, "shr-Int/2addr", 7, format2, 20)
        SHR_INT_2ADDR = opcode177
        Opcode opcode178 = Opcode("USHR_INT_2ADDR", 177, 186, "ushr-Int/2addr", 7, format2, 20)
        USHR_INT_2ADDR = opcode178
        Opcode opcode179 = Opcode("ADD_LONG_2ADDR", 178, 187, "add-Long/2addr", 7, format2, 52)
        ADD_LONG_2ADDR = opcode179
        Opcode opcode180 = Opcode("SUB_LONG_2ADDR", 179, 188, "sub-Long/2addr", 7, format2, 52)
        SUB_LONG_2ADDR = opcode180
        Opcode opcode181 = Opcode("MUL_LONG_2ADDR", 180, 189, "mul-Long/2addr", 7, format2, 52)
        MUL_LONG_2ADDR = opcode181
        Opcode opcode182 = Opcode("DIV_LONG_2ADDR", 181, 190, "div-Long/2addr", 7, format2, 53)
        DIV_LONG_2ADDR = opcode182
        Opcode opcode183 = Opcode("REM_LONG_2ADDR", 182, 191, "rem-Long/2addr", 7, format2, 53)
        REM_LONG_2ADDR = opcode183
        Opcode opcode184 = Opcode("AND_LONG_2ADDR", 183, 192, "and-Long/2addr", 7, format2, 52)
        AND_LONG_2ADDR = opcode184
        Opcode opcode185 = Opcode("OR_LONG_2ADDR", 184, 193, "or-Long/2addr", 7, format2, 52)
        OR_LONG_2ADDR = opcode185
        Opcode opcode186 = Opcode("XOR_LONG_2ADDR", 185, 194, "xor-Long/2addr", 7, format2, 52)
        XOR_LONG_2ADDR = opcode186
        Opcode opcode187 = Opcode("SHL_LONG_2ADDR", 186, 195, "shl-Long/2addr", 7, format2, 52)
        SHL_LONG_2ADDR = opcode187
        Opcode opcode188 = Opcode("SHR_LONG_2ADDR", 187, 196, "shr-Long/2addr", 7, format2, 52)
        SHR_LONG_2ADDR = opcode188
        Opcode opcode189 = Opcode("USHR_LONG_2ADDR", 188, 197, "ushr-Long/2addr", 7, format2, 52)
        USHR_LONG_2ADDR = opcode189
        Opcode opcode190 = Opcode("ADD_FLOAT_2ADDR", 189, 198, "add-Float/2addr", 7, format2, 20)
        ADD_FLOAT_2ADDR = opcode190
        Opcode opcode191 = Opcode("SUB_FLOAT_2ADDR", 190, 199, "sub-Float/2addr", 7, format2, 20)
        SUB_FLOAT_2ADDR = opcode191
        Opcode opcode192 = Opcode("MUL_FLOAT_2ADDR", 191, 200, "mul-Float/2addr", 7, format2, 20)
        MUL_FLOAT_2ADDR = opcode192
        Opcode opcode193 = Opcode("DIV_FLOAT_2ADDR", 192, 201, "div-Float/2addr", 7, format2, 20)
        DIV_FLOAT_2ADDR = opcode193
        Opcode opcode194 = Opcode("REM_FLOAT_2ADDR", 193, 202, "rem-Float/2addr", 7, format2, 20)
        REM_FLOAT_2ADDR = opcode194
        Opcode opcode195 = Opcode("ADD_DOUBLE_2ADDR", 194, 203, "add-Double/2addr", 7, format2, 52)
        ADD_DOUBLE_2ADDR = opcode195
        Opcode opcode196 = Opcode("SUB_DOUBLE_2ADDR", 195, 204, "sub-Double/2addr", 7, format2, 52)
        SUB_DOUBLE_2ADDR = opcode196
        Opcode opcode197 = Opcode("MUL_DOUBLE_2ADDR", 196, 205, "mul-Double/2addr", 7, format2, 52)
        MUL_DOUBLE_2ADDR = opcode197
        Opcode opcode198 = Opcode("DIV_DOUBLE_2ADDR", 197, 206, "div-Double/2addr", 7, format2, 52)
        DIV_DOUBLE_2ADDR = opcode198
        Opcode opcode199 = Opcode("REM_DOUBLE_2ADDR", 198, 207, "rem-Double/2addr", 7, format2, 52)
        REM_DOUBLE_2ADDR = opcode199
        Format format16 = Format.Format22s
        Opcode opcode200 = Opcode("ADD_INT_LIT16", 199, 208, "add-Int/lit16", 7, format16, 20)
        ADD_INT_LIT16 = opcode200
        Opcode opcode201 = Opcode("RSUB_INT", 200, 209, "rsub-Int", 7, format16, 20)
        RSUB_INT = opcode201
        Opcode opcode202 = Opcode("MUL_INT_LIT16", 201, 210, "mul-Int/lit16", 7, format16, 20)
        MUL_INT_LIT16 = opcode202
        Opcode opcode203 = Opcode("DIV_INT_LIT16", 202, 211, "div-Int/lit16", 7, format16, 21)
        DIV_INT_LIT16 = opcode203
        Opcode opcode204 = Opcode("REM_INT_LIT16", 203, 212, "rem-Int/lit16", 7, format16, 21)
        REM_INT_LIT16 = opcode204
        Opcode opcode205 = Opcode("AND_INT_LIT16", 204, 213, "and-Int/lit16", 7, format16, 20)
        AND_INT_LIT16 = opcode205
        Opcode opcode206 = Opcode("OR_INT_LIT16", 205, 214, "or-Int/lit16", 7, format16, 20)
        OR_INT_LIT16 = opcode206
        Opcode opcode207 = Opcode("XOR_INT_LIT16", 206, 215, "xor-Int/lit16", 7, format16, 20)
        XOR_INT_LIT16 = opcode207
        Format format17 = Format.Format22b
        Opcode opcode208 = Opcode("ADD_INT_LIT8", 207, 216, "add-Int/lit8", 7, format17, 20)
        ADD_INT_LIT8 = opcode208
        Opcode opcode209 = Opcode("RSUB_INT_LIT8", 208, 217, "rsub-Int/lit8", 7, format17, 20)
        RSUB_INT_LIT8 = opcode209
        Opcode opcode210 = Opcode("MUL_INT_LIT8", 209, 218, "mul-Int/lit8", 7, format17, 20)
        MUL_INT_LIT8 = opcode210
        Opcode opcode211 = Opcode("DIV_INT_LIT8", 210, 219, "div-Int/lit8", 7, format17, 21)
        DIV_INT_LIT8 = opcode211
        Opcode opcode212 = Opcode("REM_INT_LIT8", 211, 220, "rem-Int/lit8", 7, format17, 21)
        REM_INT_LIT8 = opcode212
        Opcode opcode213 = Opcode("AND_INT_LIT8", 212, 221, "and-Int/lit8", 7, format17, 20)
        AND_INT_LIT8 = opcode213
        Opcode opcode214 = Opcode("OR_INT_LIT8", 213, 222, "or-Int/lit8", 7, format17, 20)
        OR_INT_LIT8 = opcode214
        Opcode opcode215 = Opcode("XOR_INT_LIT8", 214, 223, "xor-Int/lit8", 7, format17, 20)
        XOR_INT_LIT8 = opcode215
        Opcode opcode216 = Opcode("SHL_INT_LIT8", 215, 224, "shl-Int/lit8", 7, format17, 20)
        SHL_INT_LIT8 = opcode216
        Opcode opcode217 = Opcode("SHR_INT_LIT8", 216, 225, "shr-Int/lit8", 7, format17, 20)
        SHR_INT_LIT8 = opcode217
        Opcode opcode218 = Opcode("USHR_INT_LIT8", 217, 226, "ushr-Int/lit8", 7, format17, 20)
        USHR_INT_LIT8 = opcode218
        Opcode opcode219 = Opcode("IGET_VOLATILE", 218, firstApi(227, 9), "iget-volatile", 2, format9, 151)
        IGET_VOLATILE = opcode219
        Opcode opcode220 = Opcode("IPUT_VOLATILE", 219, firstApi(228, 9), "iput-volatile", 2, format9, 135)
        IPUT_VOLATILE = opcode220
        Opcode opcode221 = Opcode("SGET_VOLATILE", 220, firstApi(229, 9), "sget-volatile", 2, format8, 407)
        SGET_VOLATILE = opcode221
        Opcode opcode222 = Opcode("SPUT_VOLATILE", 221, firstApi(230, 9), "sput-volatile", 2, format8, 391)
        SPUT_VOLATILE = opcode222
        Opcode opcode223 = Opcode("IGET_OBJECT_VOLATILE", 222, firstApi(231, 9), "iget-object-volatile", 2, format9, 151)
        IGET_OBJECT_VOLATILE = opcode223
        Opcode opcode224 = Opcode("IGET_WIDE_VOLATILE", 223, firstApi(232, 9), "iget-wide-volatile", 2, format9, 183)
        IGET_WIDE_VOLATILE = opcode224
        Opcode opcode225 = Opcode("IPUT_WIDE_VOLATILE", 224, firstApi(233, 9), "iput-wide-volatile", 2, format9, 135)
        IPUT_WIDE_VOLATILE = opcode225
        Opcode opcode226 = Opcode("SGET_WIDE_VOLATILE", 225, firstApi(234, 9), "sget-wide-volatile", 2, format8, 439)
        SGET_WIDE_VOLATILE = opcode226
        Opcode opcode227 = Opcode("SPUT_WIDE_VOLATILE", 226, firstApi(235, 9), "sput-wide-volatile", 2, format8, 391)
        SPUT_WIDE_VOLATILE = opcode227
        Opcode opcode228 = Opcode("THROW_VERIFICATION_ERROR", 227, firstApi(237, 5), "throw-verification-error", 7, Format.Format20bc, 3)
        THROW_VERIFICATION_ERROR = opcode228
        Opcode opcode229 = Opcode("EXECUTE_INLINE", 228, allApis(238), "execute-inline", 7, Format.Format35mi, 15)
        EXECUTE_INLINE = opcode229
        Opcode opcode230 = Opcode("EXECUTE_INLINE_RANGE", 229, firstApi(239, 8), "execute-inline/range", 7, Format.Format3rmi, 15)
        EXECUTE_INLINE_RANGE = opcode230
        Opcode opcode231 = Opcode("INVOKE_DIRECT_EMPTY", 230, lastApi(240, 13), "invoke-direct-empty", 3, format10, 1039)
        INVOKE_DIRECT_EMPTY = opcode231
        Opcode opcode232 = Opcode("INVOKE_OBJECT_INIT_RANGE", 231, firstApi(240, 14), "invoke-object-init/range", 3, format11, 1039)
        INVOKE_OBJECT_INIT_RANGE = opcode232
        Opcode opcode233 = Opcode("RETURN_VOID_BARRIER", 232, combine(firstApi(241, 11), lastArtVersion(R.styleable.AppCompatTheme_switchStyle, 59)), "return-Unit-barrier", 7, format, 2)
        RETURN_VOID_BARRIER = opcode233
        Opcode opcode234 = Opcode("RETURN_VOID_NO_BARRIER", 233, firstArtVersion(R.styleable.AppCompatTheme_switchStyle, 60), "return-Unit-no-barrier", 7, format, 2)
        RETURN_VOID_NO_BARRIER = opcode234
        List<VersionConstraint> listCombine = combine(allApis(242), allArtVersions(227))
        Format format18 = Format.Format22cs
        Opcode opcode235 = Opcode("IGET_QUICK", 234, listCombine, "iget-quick", 7, format18, 87)
        IGET_QUICK = opcode235
        Opcode opcode236 = Opcode("IGET_WIDE_QUICK", 235, combine(allApis(243), allArtVersions(228)), "iget-wide-quick", 7, format18, R.styleable.AppCompatTheme_colorError)
        IGET_WIDE_QUICK = opcode236
        Opcode opcode237 = Opcode("IGET_OBJECT_QUICK", 236, combine(allApis(244), allArtVersions(229)), "iget-object-quick", 7, format18, 87)
        IGET_OBJECT_QUICK = opcode237
        Opcode opcode238 = Opcode("IPUT_QUICK", 237, combine(allApis(245), allArtVersions(230)), "iput-quick", 7, format18, 71)
        IPUT_QUICK = opcode238
        Opcode opcode239 = Opcode("IPUT_WIDE_QUICK", 238, combine(allApis(246), allArtVersions(231)), "iput-wide-quick", 7, format18, 71)
        IPUT_WIDE_QUICK = opcode239
        Opcode opcode240 = Opcode("IPUT_OBJECT_QUICK", 239, combine(allApis(247), allArtVersions(232)), "iput-object-quick", 7, format18, 71)
        IPUT_OBJECT_QUICK = opcode240
        Opcode opcode241 = Opcode("IPUT_BOOLEAN_QUICK", 240, allArtVersions(235), "iput-Boolean-quick", 7, format18, 71)
        IPUT_BOOLEAN_QUICK = opcode241
        Opcode opcode242 = Opcode("IPUT_BYTE_QUICK", 241, allArtVersions(236), "iput-Byte-quick", 7, format18, 71)
        IPUT_BYTE_QUICK = opcode242
        Opcode opcode243 = Opcode("IPUT_CHAR_QUICK", 242, allArtVersions(237), "iput-Char-quick", 7, format18, 71)
        IPUT_CHAR_QUICK = opcode243
        Opcode opcode244 = Opcode("IPUT_SHORT_QUICK", 243, allArtVersions(238), "iput-Short-quick", 7, format18, 71)
        IPUT_SHORT_QUICK = opcode244
        Opcode opcode245 = Opcode("IGET_BOOLEAN_QUICK", 244, allArtVersions(239), "iget-Boolean-quick", 7, format18, 87)
        IGET_BOOLEAN_QUICK = opcode245
        Opcode opcode246 = Opcode("IGET_BYTE_QUICK", 245, allArtVersions(240), "iget-Byte-quick", 7, format18, 87)
        IGET_BYTE_QUICK = opcode246
        Opcode opcode247 = Opcode("IGET_CHAR_QUICK", 246, allArtVersions(241), "iget-Char-quick", 7, format18, 87)
        IGET_CHAR_QUICK = opcode247
        Opcode opcode248 = Opcode("IGET_SHORT_QUICK", 247, allArtVersions(242), "iget-Short-quick", 7, format18, 87)
        IGET_SHORT_QUICK = opcode248
        List<VersionConstraint> listCombine2 = combine(allApis(248), allArtVersions(233))
        Format format19 = Format.Format35ms
        Opcode opcode249 = Opcode("INVOKE_VIRTUAL_QUICK", 248, listCombine2, "invoke-virtual-quick", 7, format19, 15)
        INVOKE_VIRTUAL_QUICK = opcode249
        List<VersionConstraint> listCombine3 = combine(allApis(249), allArtVersions(234))
        Format format20 = Format.Format3rms
        Opcode opcode250 = Opcode("INVOKE_VIRTUAL_QUICK_RANGE", 249, listCombine3, "invoke-virtual-quick/range", 7, format20, 15)
        INVOKE_VIRTUAL_QUICK_RANGE = opcode250
        Opcode opcode251 = Opcode("INVOKE_SUPER_QUICK", 250, lastApi(250, 25), "invoke-super-quick", 7, format19, 15)
        INVOKE_SUPER_QUICK = opcode251
        Opcode opcode252 = Opcode("INVOKE_SUPER_QUICK_RANGE", 251, lastApi(251, 25), "invoke-super-quick/range", 7, format20, 15)
        INVOKE_SUPER_QUICK_RANGE = opcode252
        Opcode opcode253 = Opcode("IPUT_OBJECT_VOLATILE", 252, firstApi(252, 9), "iput-object-volatile", 2, format9, 135)
        IPUT_OBJECT_VOLATILE = opcode253
        Opcode opcode254 = Opcode("SGET_OBJECT_VOLATILE", 253, firstApi(253, 9), "sget-object-volatile", 2, format8, 407)
        SGET_OBJECT_VOLATILE = opcode254
        Opcode opcode255 = Opcode("SPUT_OBJECT_VOLATILE", 254, betweenApi(254, 9, 19), "sput-object-volatile", 2, format8, 391)
        SPUT_OBJECT_VOLATILE = opcode255
        Opcode opcode256 = Opcode("PACKED_SWITCH_PAYLOAD", 255, 256, "packed-switch-payload", 7, Format.PackedSwitchPayload, 0)
        PACKED_SWITCH_PAYLOAD = opcode256
        Opcode opcode257 = Opcode("SPARSE_SWITCH_PAYLOAD", 256, 512, "sparse-switch-payload", 7, Format.SparseSwitchPayload, 0)
        SPARSE_SWITCH_PAYLOAD = opcode257
        Opcode opcode258 = Opcode("ARRAY_PAYLOAD", InputDeviceCompat.SOURCE_KEYBOARD, 768, "array-payload", 7, Format.ArrayPayload, 0)
        ARRAY_PAYLOAD = opcode258
        Opcode opcode259 = Opcode("INVOKE_POLYMORPHIC", 258, firstArtVersion(250, 87), "invoke-polymorphic", 3, 4, Format.Format45cc, 13)
        INVOKE_POLYMORPHIC = opcode259
        Opcode opcode260 = Opcode("INVOKE_POLYMORPHIC_RANGE", 259, firstArtVersion(251, 87), "invoke-polymorphic/range", 3, 4, Format.Format4rcc, 13)
        INVOKE_POLYMORPHIC_RANGE = opcode260
        Opcode opcode261 = Opcode("INVOKE_CUSTOM", 260, firstArtVersion(252, R.styleable.AppCompatTheme_ratingBarStyleIndicator), "invoke-custom", 5, format10, 13)
        INVOKE_CUSTOM = opcode261
        Opcode opcode262 = Opcode("INVOKE_CUSTOM_RANGE", 261, firstArtVersion(253, R.styleable.AppCompatTheme_ratingBarStyleIndicator), "invoke-custom/range", 5, format11, 13)
        INVOKE_CUSTOM_RANGE = opcode262
        Opcode opcode263 = Opcode("CONST_METHOD_HANDLE", 262, firstArtVersion(254, 134), "const-method-handle", 6, format8, 21)
        CONST_METHOD_HANDLE = opcode263
        Opcode opcode264 = Opcode("CONST_METHOD_TYPE", 263, firstArtVersion(255, 134), "const-method-type", 4, format8, 21)
        CONST_METHOD_TYPE = opcode264
        $VALUES = new Array<Opcode>{opcode, opcode2, opcode3, opcode4, opcode5, opcode6, opcode7, opcode8, opcode9, opcode10, opcode11, opcode12, opcode13, opcode14, opcode15, opcode16, opcode17, opcode18, opcode19, opcode20, opcode21, opcode22, opcode23, opcode24, opcode25, opcode26, opcode27, opcode28, opcode29, opcode30, opcode31, opcode32, opcode33, opcode34, opcode35, opcode36, opcode37, opcode38, opcode39, opcode40, opcode41, opcode42, opcode43, opcode44, opcode45, opcode46, opcode47, opcode48, opcode49, opcode50, opcode51, opcode52, opcode53, opcode54, opcode55, opcode56, opcode57, opcode58, opcode59, opcode60, opcode61, opcode62, opcode63, opcode64, opcode65, opcode66, opcode67, opcode68, opcode69, opcode70, opcode71, opcode72, opcode73, opcode74, opcode75, opcode76, opcode77, opcode78, opcode79, opcode80, opcode81, opcode82, opcode83, opcode84, opcode85, opcode86, opcode87, opcode88, opcode89, opcode90, opcode91, opcode92, opcode93, opcode94, opcode95, opcode96, opcode97, opcode98, opcode99, opcode100, opcode101, opcode102, opcode103, opcode104, opcode105, opcode106, opcode107, opcode108, opcode109, opcode110, opcode111, opcode112, opcode113, opcode114, opcode115, opcode116, opcode117, opcode118, opcode119, opcode120, opcode121, opcode122, opcode123, opcode124, opcode125, opcode126, opcode127, opcode128, opcode129, opcode130, opcode131, opcode132, opcode133, opcode134, opcode135, opcode136, opcode137, opcode138, opcode139, opcode140, opcode141, opcode142, opcode143, opcode144, opcode145, opcode146, opcode147, opcode148, opcode149, opcode150, opcode151, opcode152, opcode153, opcode154, opcode155, opcode156, opcode157, opcode158, opcode159, opcode160, opcode161, opcode162, opcode163, opcode164, opcode165, opcode166, opcode167, opcode168, opcode169, opcode170, opcode171, opcode172, opcode173, opcode174, opcode175, opcode176, opcode177, opcode178, opcode179, opcode180, opcode181, opcode182, opcode183, opcode184, opcode185, opcode186, opcode187, opcode188, opcode189, opcode190, opcode191, opcode192, opcode193, opcode194, opcode195, opcode196, opcode197, opcode198, opcode199, opcode200, opcode201, opcode202, opcode203, opcode204, opcode205, opcode206, opcode207, opcode208, opcode209, opcode210, opcode211, opcode212, opcode213, opcode214, opcode215, opcode216, opcode217, opcode218, opcode219, opcode220, opcode221, opcode222, opcode223, opcode224, opcode225, opcode226, opcode227, opcode228, opcode229, opcode230, opcode231, opcode232, opcode233, opcode234, opcode235, opcode236, opcode237, opcode238, opcode239, opcode240, opcode241, opcode242, opcode243, opcode244, opcode245, opcode246, opcode247, opcode248, opcode249, opcode250, opcode251, opcode252, opcode253, opcode254, opcode255, opcode256, opcode257, opcode258, opcode259, opcode260, opcode261, opcode262, opcode263, opcode264}
    }

    constructor(String str, Int i, Int i2, String str2, Int i3, Format format) {
        this(str, i, i2, str2, i3, format, 0)
    }

    constructor(String str, Int i, Int i2, String str2, Int i3, Format format, Int i4) {
        this(str, i, allVersions(i2), str2, i3, format, i4)
    }

    constructor(String str, Int i, List list, String str2, Int i2, Int i3, Format format, Int i4) {
        ImmutableRangeMap.Builder builder = ImmutableRangeMap.builder()
        ImmutableRangeMap.Builder builder2 = ImmutableRangeMap.builder()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            VersionConstraint versionConstraint = (VersionConstraint) it.next()
            if (!versionConstraint.apiRange.isEmpty()) {
                builder.put(versionConstraint.apiRange, Short.valueOf((Short) versionConstraint.opcodeValue))
            }
            if (!versionConstraint.artVersionRange.isEmpty()) {
                builder2.put(versionConstraint.artVersionRange, Short.valueOf((Short) versionConstraint.opcodeValue))
            }
        }
        this.apiToValueMap = builder.build()
        this.artVersionToValueMap = builder2.build()
        this.name = str2
        this.referenceType = i2
        this.referenceType2 = i3
        this.format = format
        this.flags = i4
    }

    constructor(String str, Int i, List list, String str2, Int i2, Format format, Int i3) {
        this(str, i, list, str2, i2, -1, format, i3)
    }

    public static List<VersionConstraint> allApis(Int i) {
        return Lists.newArrayList(VersionConstraint(Range.all(), Range.openClosed(0, 0), i))
    }

    public static List<VersionConstraint> allArtVersions(Int i) {
        return Lists.newArrayList(VersionConstraint(Range.openClosed(0, 0), Range.all(), i))
    }

    public static List<VersionConstraint> allVersions(Int i) {
        return Lists.newArrayList(VersionConstraint(Range.all(), Range.all(), i))
    }

    public static List<VersionConstraint> betweenApi(Int i, Int i2, Int i3) {
        return Lists.newArrayList(VersionConstraint(Range.closed(Integer.valueOf(i2), Integer.valueOf(i3)), Range.openClosed(0, 0), i))
    }

    public static List<VersionConstraint> combine(List<VersionConstraint>... listArr) {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        for (List<VersionConstraint> list : listArr) {
            arrayListNewArrayList.addAll(list)
        }
        return arrayListNewArrayList
    }

    public static List<VersionConstraint> firstApi(Int i, Int i2) {
        return Lists.newArrayList(VersionConstraint(Range.atLeast(Integer.valueOf(i2)), Range.openClosed(0, 0), i))
    }

    public static List<VersionConstraint> firstArtVersion(Int i, Int i2) {
        return Lists.newArrayList(VersionConstraint(Range.openClosed(0, 0), Range.atLeast(Integer.valueOf(i2)), i))
    }

    public static List<VersionConstraint> lastApi(Int i, Int i2) {
        return Lists.newArrayList(VersionConstraint(Range.atMost(Integer.valueOf(i2)), Range.openClosed(0, 0), i))
    }

    public static List<VersionConstraint> lastArtVersion(Int i, Int i2) {
        return Lists.newArrayList(VersionConstraint(Range.openClosed(0, 0), Range.atMost(Integer.valueOf(i2)), i))
    }

    fun valueOf(String str) {
        return (Opcode) Enum.valueOf(Opcode.class, str)
    }

    public static Array<Opcode> values() {
        return (Array<Opcode>) $VALUES.clone()
    }

    public final Boolean canContinue() {
        return (this.flags & 4) != 0
    }

    public final Boolean canInitializeReference() {
        return (this.flags & 1024) != 0
    }

    public final Boolean canThrow() {
        return (this.flags & 1) != 0
    }

    public final Boolean isVolatileFieldAccessor() {
        return (this.flags & 128) != 0
    }

    public final Boolean odexOnly() {
        return (this.flags & 2) != 0
    }

    public final Boolean setsRegister() {
        return (this.flags & 16) != 0
    }

    public final Boolean setsResult() {
        return (this.flags & 8) != 0
    }

    public final Boolean setsWideRegister() {
        return (this.flags & 32) != 0
    }
}
