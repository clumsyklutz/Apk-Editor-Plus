package org.jf.smali

import android.support.v4.media.session.PlaybackStateCompat
import androidx.appcompat.R
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Set
import java.util.Stack
import org.antlr.runtime.BitSet
import org.antlr.runtime.MismatchedSetException
import org.antlr.runtime.NoViableAltException
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.RecognizerSharedState
import org.antlr.runtime.tree.CommonTree
import org.antlr.runtime.tree.TreeNodeStream
import org.antlr.runtime.tree.TreeParser
import org.antlr.runtime.tree.TreeRuleReturnScope
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.AnnotationVisibility
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.MethodHandleType
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.VerificationError
import org.jf.dexlib2.builder.Label
import org.jf.dexlib2.builder.MethodImplementationBuilder
import org.jf.dexlib2.builder.SwitchLabelElement
import org.jf.dexlib2.builder.instruction.BuilderArrayPayload
import org.jf.dexlib2.builder.instruction.BuilderInstruction10t
import org.jf.dexlib2.builder.instruction.BuilderInstruction10x
import org.jf.dexlib2.builder.instruction.BuilderInstruction11n
import org.jf.dexlib2.builder.instruction.BuilderInstruction11x
import org.jf.dexlib2.builder.instruction.BuilderInstruction12x
import org.jf.dexlib2.builder.instruction.BuilderInstruction20bc
import org.jf.dexlib2.builder.instruction.BuilderInstruction20t
import org.jf.dexlib2.builder.instruction.BuilderInstruction21c
import org.jf.dexlib2.builder.instruction.BuilderInstruction21ih
import org.jf.dexlib2.builder.instruction.BuilderInstruction21lh
import org.jf.dexlib2.builder.instruction.BuilderInstruction21s
import org.jf.dexlib2.builder.instruction.BuilderInstruction21t
import org.jf.dexlib2.builder.instruction.BuilderInstruction22b
import org.jf.dexlib2.builder.instruction.BuilderInstruction22c
import org.jf.dexlib2.builder.instruction.BuilderInstruction22s
import org.jf.dexlib2.builder.instruction.BuilderInstruction22t
import org.jf.dexlib2.builder.instruction.BuilderInstruction22x
import org.jf.dexlib2.builder.instruction.BuilderInstruction23x
import org.jf.dexlib2.builder.instruction.BuilderInstruction30t
import org.jf.dexlib2.builder.instruction.BuilderInstruction31c
import org.jf.dexlib2.builder.instruction.BuilderInstruction31i
import org.jf.dexlib2.builder.instruction.BuilderInstruction31t
import org.jf.dexlib2.builder.instruction.BuilderInstruction32x
import org.jf.dexlib2.builder.instruction.BuilderInstruction35c
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc
import org.jf.dexlib2.builder.instruction.BuilderInstruction45cc
import org.jf.dexlib2.builder.instruction.BuilderInstruction4rcc
import org.jf.dexlib2.builder.instruction.BuilderInstruction51l
import org.jf.dexlib2.builder.instruction.BuilderPackedSwitchPayload
import org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.ImmutableAnnotation
import org.jf.dexlib2.immutable.ImmutableAnnotationElement
import org.jf.dexlib2.immutable.reference.ImmutableCallSiteReference
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference
import org.jf.dexlib2.immutable.reference.ImmutableMethodHandleReference
import org.jf.dexlib2.immutable.reference.ImmutableMethodProtoReference
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference
import org.jf.dexlib2.immutable.reference.ImmutableReference
import org.jf.dexlib2.immutable.reference.ImmutableTypeReference
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue
import org.jf.dexlib2.writer.builder.BuilderField
import org.jf.dexlib2.writer.builder.BuilderMethod
import org.jf.dexlib2.writer.builder.DexBuilder
import org.jf.util.LinearSearch

class smaliTreeWalker extends TreeParser {
    public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_annotation3996
    public static final BitSet FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor3548
    public static final BitSet FOLLOW_BOOL_LITERAL_in_bool_literal3912
    public static final BitSet FOLLOW_BYTE_LITERAL_in_byte_literal3833
    public static final BitSet FOLLOW_CHAR_LITERAL_in_char_literal3878
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_array_descriptor3580
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor3606
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor3635
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_subannotation4060
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference2005
    public static final BitSet FOLLOW_DOUBLE_LITERAL_in_double_literal3863
    public static final BitSet FOLLOW_FLOAT_LITERAL_in_float_literal3848
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2316
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2343
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2368
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2397
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2424
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2457
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2486
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2553
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2586
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2616
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2645
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2674
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2703
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2732
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2761
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2790
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2868
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2903
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2938
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2973
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x3006
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t3043
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c3070
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i3099
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t3128
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x3157
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3195
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method3224
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type3253
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3287
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method3316
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type3345
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method3374
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method3405
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type3436
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_integer_literal3788
    public static final BitSet FOLLOW_I_ANNOTATIONS_in_annotations3962
    public static final BitSet FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element4019
    public static final BitSet FOLLOW_I_ANNOTATION_in_annotation3994
    public static final BitSet FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive3466
    public static final BitSet FOLLOW_I_CALL_SITE_EXTRA_ARGUMENTS_in_call_site_extra_arguments1868
    public static final BitSet FOLLOW_I_CATCHALL_in_catchall_directive1588
    public static final BitSet FOLLOW_I_CATCHES_in_catches1540
    public static final BitSet FOLLOW_I_CATCH_in_catch_directive1558
    public static final BitSet FOLLOW_I_ENCODED_ARRAY_in_array_literal3934
    public static final BitSet FOLLOW_I_ENCODED_ENUM_in_enum_literal4156
    public static final BitSet FOLLOW_I_ENCODED_FIELD_in_field_literal4110
    public static final BitSet FOLLOW_I_ENCODED_METHOD_HANDLE_in_method_handle_literal1391
    public static final BitSet FOLLOW_I_ENCODED_METHOD_in_method_literal4133
    public static final BitSet FOLLOW_I_END_LOCAL_in_end_local1768
    public static final BitSet FOLLOW_I_EPILOGUE_in_epilogue1823
    public static final BitSet FOLLOW_I_LABEL_in_label_def1512
    public static final BitSet FOLLOW_I_LINE_in_line1707
    public static final BitSet FOLLOW_I_LOCALS_in_registers_directive1474
    public static final BitSet FOLLOW_I_LOCAL_in_local1727
    public static final BitSet FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1887
    public static final BitSet FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3496
    public static final BitSet FOLLOW_I_PARAMETERS_in_parameters1617
    public static final BitSet FOLLOW_I_PARAMETER_in_parameter1636
    public static final BitSet FOLLOW_I_PROLOGUE_in_prologue1807
    public static final BitSet FOLLOW_I_REGISTERS_in_registers_directive1462
    public static final BitSet FOLLOW_I_REGISTER_LIST_in_register_list1939
    public static final BitSet FOLLOW_I_REGISTER_RANGE_in_register_range1973
    public static final BitSet FOLLOW_I_RESTART_LOCAL_in_restart_local1788
    public static final BitSet FOLLOW_I_SOURCE_in_source1840
    public static final BitSet FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive3463
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2314
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2341
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2366
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2395
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2422
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2455
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2484
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2511
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2548
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2581
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2614
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2643
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2672
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2701
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2730
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2759
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2788
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2823
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2866
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2901
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2936
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2971
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x3004
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t3041
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c3068
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i3097
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t3126
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x3155
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3193
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method3222
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type3251
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3285
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method3314
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type3343
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT45cc_METHOD_in_insn_format45cc_method3372
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT4rcc_METHOD_in_insn_format4rcc_method3403
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type3434
    public static final BitSet FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3493
    public static final BitSet FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3525
    public static final BitSet FOLLOW_I_SUBANNOTATION_in_subannotation4050
    public static final BitSet FOLLOW_LONG_LITERAL_in_long_literal3803
    public static final BitSet FOLLOW_NULL_LITERAL_in_local1733
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_array_descriptor3552
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor3598
    public static final BitSet FOLLOW_REGISTER_in_end_local1770
    public static final BitSet FOLLOW_REGISTER_in_insn_format11n2370
    public static final BitSet FOLLOW_REGISTER_in_insn_format11x2399
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x2428
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x2432
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field2523
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_handle2556
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_type2589
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_string2618
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_type2647
    public static final BitSet FOLLOW_REGISTER_in_insn_format21ih2676
    public static final BitSet FOLLOW_REGISTER_in_insn_format21lh2705
    public static final BitSet FOLLOW_REGISTER_in_insn_format21s2734
    public static final BitSet FOLLOW_REGISTER_in_insn_format21t2763
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b2794
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b2798
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field2837
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field2841
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type2872
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type2876
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s2907
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s2911
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t2942
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t2946
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x2977
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x2981
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x3010
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x3014
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x3018
    public static final BitSet FOLLOW_REGISTER_in_insn_format31c3072
    public static final BitSet FOLLOW_REGISTER_in_insn_format31i3101
    public static final BitSet FOLLOW_REGISTER_in_insn_format31t3130
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x3161
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x3165
    public static final BitSet FOLLOW_REGISTER_in_insn_format51l_type3438
    public static final BitSet FOLLOW_REGISTER_in_local1729
    public static final BitSet FOLLOW_REGISTER_in_parameter1638
    public static final BitSet FOLLOW_REGISTER_in_register_list1948
    public static final BitSet FOLLOW_REGISTER_in_register_range1978
    public static final BitSet FOLLOW_REGISTER_in_register_range1982
    public static final BitSet FOLLOW_REGISTER_in_restart_local1790
    public static final BitSet FOLLOW_SHORT_LITERAL_in_short_literal3818
    public static final BitSet FOLLOW_SIMPLE_NAME_in_annotation_element4021
    public static final BitSet FOLLOW_SIMPLE_NAME_in_field_reference1434
    public static final BitSet FOLLOW_SIMPLE_NAME_in_label_def1514
    public static final BitSet FOLLOW_SIMPLE_NAME_in_label_ref1914
    public static final BitSet FOLLOW_SIMPLE_NAME_in_method_reference1412
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal3893
    public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type2042
    public static final BitSet FOLLOW_VOID_TYPE_in_type_descriptor3663
    public static final BitSet FOLLOW_annotation_element_in_subannotation4071
    public static final BitSet FOLLOW_annotation_in_annotations3965
    public static final BitSet FOLLOW_annotations_in_parameter1643
    public static final BitSet FOLLOW_array_descriptor_in_nonvoid_type_descriptor3614
    public static final BitSet FOLLOW_array_descriptor_in_reference_type_descriptor3643
    public static final BitSet FOLLOW_array_elements_in_insn_array_data_directive3471
    public static final BitSet FOLLOW_byte_literal_in_integral_literal3772
    public static final BitSet FOLLOW_byte_literal_in_short_integral_literal3729
    public static final BitSet FOLLOW_call_site_reference_in_insn_format35c_call_site3199
    public static final BitSet FOLLOW_call_site_reference_in_insn_format3rc_call_site3291
    public static final BitSet FOLLOW_catch_directive_in_catches1542
    public static final BitSet FOLLOW_catchall_directive_in_catches1545
    public static final BitSet FOLLOW_char_literal_in_short_integral_literal3721
    public static final BitSet FOLLOW_debug_directive_in_ordered_method_items1898
    public static final BitSet FOLLOW_end_local_in_debug_directive1672
    public static final BitSet FOLLOW_epilogue_in_debug_directive1690
    public static final BitSet FOLLOW_field_reference_in_enum_literal4158
    public static final BitSet FOLLOW_field_reference_in_field_literal4112
    public static final BitSet FOLLOW_field_reference_in_insn_format21c_field2525
    public static final BitSet FOLLOW_field_reference_in_insn_format22c_field2843
    public static final BitSet FOLLOW_field_reference_in_method_handle_reference1370
    public static final BitSet FOLLOW_field_reference_in_verification_error_reference2015
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21ih2678
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format31i3103
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3498
    public static final BitSet FOLLOW_fixed_64bit_literal_in_insn_format21lh2707
    public static final BitSet FOLLOW_fixed_64bit_literal_in_insn_format51l_type3440
    public static final BitSet FOLLOW_insn_array_data_directive_in_instruction2278
    public static final BitSet FOLLOW_insn_format10t_in_instruction2056
    public static final BitSet FOLLOW_insn_format10x_in_instruction2062
    public static final BitSet FOLLOW_insn_format11n_in_instruction2068
    public static final BitSet FOLLOW_insn_format11x_in_instruction2074
    public static final BitSet FOLLOW_insn_format12x_in_instruction2080
    public static final BitSet FOLLOW_insn_format20bc_in_instruction2086
    public static final BitSet FOLLOW_insn_format20t_in_instruction2092
    public static final BitSet FOLLOW_insn_format21c_field_in_instruction2098
    public static final BitSet FOLLOW_insn_format21c_method_handle_in_instruction2104
    public static final BitSet FOLLOW_insn_format21c_method_type_in_instruction2110
    public static final BitSet FOLLOW_insn_format21c_string_in_instruction2116
    public static final BitSet FOLLOW_insn_format21c_type_in_instruction2122
    public static final BitSet FOLLOW_insn_format21ih_in_instruction2128
    public static final BitSet FOLLOW_insn_format21lh_in_instruction2134
    public static final BitSet FOLLOW_insn_format21s_in_instruction2140
    public static final BitSet FOLLOW_insn_format21t_in_instruction2146
    public static final BitSet FOLLOW_insn_format22b_in_instruction2152
    public static final BitSet FOLLOW_insn_format22c_field_in_instruction2158
    public static final BitSet FOLLOW_insn_format22c_type_in_instruction2164
    public static final BitSet FOLLOW_insn_format22s_in_instruction2170
    public static final BitSet FOLLOW_insn_format22t_in_instruction2176
    public static final BitSet FOLLOW_insn_format22x_in_instruction2182
    public static final BitSet FOLLOW_insn_format23x_in_instruction2188
    public static final BitSet FOLLOW_insn_format30t_in_instruction2194
    public static final BitSet FOLLOW_insn_format31c_in_instruction2200
    public static final BitSet FOLLOW_insn_format31i_in_instruction2206
    public static final BitSet FOLLOW_insn_format31t_in_instruction2212
    public static final BitSet FOLLOW_insn_format32x_in_instruction2218
    public static final BitSet FOLLOW_insn_format35c_call_site_in_instruction2224
    public static final BitSet FOLLOW_insn_format35c_method_in_instruction2230
    public static final BitSet FOLLOW_insn_format35c_type_in_instruction2236
    public static final BitSet FOLLOW_insn_format3rc_call_site_in_instruction2242
    public static final BitSet FOLLOW_insn_format3rc_method_in_instruction2248
    public static final BitSet FOLLOW_insn_format3rc_type_in_instruction2254
    public static final BitSet FOLLOW_insn_format45cc_method_in_instruction2260
    public static final BitSet FOLLOW_insn_format4rcc_method_in_instruction2266
    public static final BitSet FOLLOW_insn_format51l_type_in_instruction2272
    public static final BitSet FOLLOW_insn_packed_switch_directive_in_instruction2284
    public static final BitSet FOLLOW_insn_sparse_switch_directive_in_instruction2290
    public static final BitSet FOLLOW_instruction_in_ordered_method_items1894
    public static final BitSet FOLLOW_integer_literal_in_integral_literal3756
    public static final BitSet FOLLOW_integer_literal_in_short_integral_literal3701
    public static final BitSet FOLLOW_integral_literal_in_line1709
    public static final BitSet FOLLOW_label_def_in_ordered_method_items1890
    public static final BitSet FOLLOW_label_ref_in_catch_directive1564
    public static final BitSet FOLLOW_label_ref_in_catch_directive1568
    public static final BitSet FOLLOW_label_ref_in_catch_directive1572
    public static final BitSet FOLLOW_label_ref_in_catchall_directive1592
    public static final BitSet FOLLOW_label_ref_in_catchall_directive1596
    public static final BitSet FOLLOW_label_ref_in_catchall_directive1600
    public static final BitSet FOLLOW_label_ref_in_insn_format10t2318
    public static final BitSet FOLLOW_label_ref_in_insn_format20t2488
    public static final BitSet FOLLOW_label_ref_in_insn_format21t2765
    public static final BitSet FOLLOW_label_ref_in_insn_format22t2948
    public static final BitSet FOLLOW_label_ref_in_insn_format30t3045
    public static final BitSet FOLLOW_label_ref_in_insn_format31t3132
    public static final BitSet FOLLOW_line_in_debug_directive1660
    public static final BitSet FOLLOW_literal_in_annotation_element4023
    public static final BitSet FOLLOW_literal_in_array_literal3937
    public static final BitSet FOLLOW_literal_in_call_site_extra_arguments1871
    public static final BitSet FOLLOW_local_in_debug_directive1666
    public static final BitSet FOLLOW_long_literal_in_integral_literal3744
    public static final BitSet FOLLOW_long_literal_in_short_integral_literal3689
    public static final BitSet FOLLOW_method_handle_reference_in_insn_format21c_method_handle2558
    public static final BitSet FOLLOW_method_handle_reference_in_method_handle_literal1393
    public static final BitSet FOLLOW_method_handle_type_in_method_handle_reference1367
    public static final BitSet FOLLOW_method_prototype_in_insn_format21c_method_type2591
    public static final BitSet FOLLOW_method_prototype_in_insn_format45cc_method3380
    public static final BitSet FOLLOW_method_prototype_in_insn_format4rcc_method3411
    public static final BitSet FOLLOW_method_prototype_in_method_reference1414
    public static final BitSet FOLLOW_method_reference_in_insn_format35c_method3228
    public static final BitSet FOLLOW_method_reference_in_insn_format3rc_method3320
    public static final BitSet FOLLOW_method_reference_in_insn_format45cc_method3378
    public static final BitSet FOLLOW_method_reference_in_insn_format4rcc_method3409
    public static final BitSet FOLLOW_method_reference_in_method_handle_reference1374
    public static final BitSet FOLLOW_method_reference_in_method_literal4135
    public static final BitSet FOLLOW_method_reference_in_verification_error_reference2025
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_catch_directive1560
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field_reference1436
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2649
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2878
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type3257
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type3349
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_local1742
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_type_descriptor3671
    public static final BitSet FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3501
    public static final BitSet FOLLOW_parameter_in_parameters1620
    public static final BitSet FOLLOW_prologue_in_debug_directive1684
    public static final BitSet FOLLOW_reference_type_descriptor_in_field_reference1431
    public static final BitSet FOLLOW_reference_type_descriptor_in_method_reference1409
    public static final BitSet FOLLOW_register_list_in_insn_format35c_call_site3197
    public static final BitSet FOLLOW_register_list_in_insn_format35c_method3226
    public static final BitSet FOLLOW_register_list_in_insn_format35c_type3255
    public static final BitSet FOLLOW_register_list_in_insn_format45cc_method3376
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_call_site3289
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_method3318
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_type3347
    public static final BitSet FOLLOW_register_range_in_insn_format4rcc_method3407
    public static final BitSet FOLLOW_restart_local_in_debug_directive1678
    public static final BitSet FOLLOW_short_integral_literal_in_insn_array_data_directive3468
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format11n2372
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format21s2736
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format22b2800
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format22s2913
    public static final BitSet FOLLOW_short_integral_literal_in_registers_directive1492
    public static final BitSet FOLLOW_short_literal_in_integral_literal3764
    public static final BitSet FOLLOW_short_literal_in_short_integral_literal3713
    public static final BitSet FOLLOW_source_in_debug_directive1696
    public static final BitSet FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3527
    public static final BitSet FOLLOW_string_literal_in_insn_format21c_string2620
    public static final BitSet FOLLOW_string_literal_in_insn_format31c3074
    public static final BitSet FOLLOW_string_literal_in_local1739
    public static final BitSet FOLLOW_string_literal_in_local1747
    public static final BitSet FOLLOW_string_literal_in_parameter1640
    public static final BitSet FOLLOW_string_literal_in_source1842
    public static final BitSet FOLLOW_subannotation_in_annotation3998
    public static final BitSet FOLLOW_verification_error_reference_in_insn_format20bc2461
    public static final BitSet FOLLOW_verification_error_type_in_insn_format20bc2459
    public Int apiLevel
    public String classType
    public DexBuilder dexBuilder
    public String errMessages
    public Stack<method_scope> method_stack
    public Opcodes opcodes
    public Boolean verboseErrors
    public static final Array<String> tokenNames = {"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACCESS_SPEC", "ANNOTATION_DIRECTIVE", "ANNOTATION_VISIBILITY", "ARRAY_DATA_DIRECTIVE", "ARRAY_TYPE_PREFIX", "ARROW", "AT", "BOOL_LITERAL", "BYTE_LITERAL", "CATCHALL_DIRECTIVE", "CATCH_DIRECTIVE", "CHAR_LITERAL", "CLASS_DESCRIPTOR", "CLASS_DIRECTIVE", "CLOSE_BRACE", "CLOSE_PAREN", "COLON", "COMMA", "DOTDOT", "DOUBLE_LITERAL", "DOUBLE_LITERAL_OR_ID", "END_ANNOTATION_DIRECTIVE", "END_ARRAY_DATA_DIRECTIVE", "END_FIELD_DIRECTIVE", "END_LOCAL_DIRECTIVE", "END_METHOD_DIRECTIVE", "END_PACKED_SWITCH_DIRECTIVE", "END_PARAMETER_DIRECTIVE", "END_SPARSE_SWITCH_DIRECTIVE", "END_SUBANNOTATION_DIRECTIVE", "ENUM_DIRECTIVE", "EPILOGUE_DIRECTIVE", "EQUAL", "FIELD_DIRECTIVE", "FIELD_OFFSET", "FLOAT_LITERAL", "FLOAT_LITERAL_OR_ID", "HIDDENAPI_RESTRICTION", "IMPLEMENTS_DIRECTIVE", "INLINE_INDEX", "INSTRUCTION_FORMAT10t", "INSTRUCTION_FORMAT10x", "INSTRUCTION_FORMAT10x_ODEX", "INSTRUCTION_FORMAT11n", "INSTRUCTION_FORMAT11x", "INSTRUCTION_FORMAT12x", "INSTRUCTION_FORMAT12x_OR_ID", "INSTRUCTION_FORMAT20bc", "INSTRUCTION_FORMAT20t", "INSTRUCTION_FORMAT21c_FIELD", "INSTRUCTION_FORMAT21c_FIELD_ODEX", "INSTRUCTION_FORMAT21c_METHOD_HANDLE", "INSTRUCTION_FORMAT21c_METHOD_TYPE", "INSTRUCTION_FORMAT21c_STRING", "INSTRUCTION_FORMAT21c_TYPE", "INSTRUCTION_FORMAT21ih", "INSTRUCTION_FORMAT21lh", "INSTRUCTION_FORMAT21s", "INSTRUCTION_FORMAT21t", "INSTRUCTION_FORMAT22b", "INSTRUCTION_FORMAT22c_FIELD", "INSTRUCTION_FORMAT22c_FIELD_ODEX", "INSTRUCTION_FORMAT22c_TYPE", "INSTRUCTION_FORMAT22cs_FIELD", "INSTRUCTION_FORMAT22s", "INSTRUCTION_FORMAT22s_OR_ID", "INSTRUCTION_FORMAT22t", "INSTRUCTION_FORMAT22x", "INSTRUCTION_FORMAT23x", "INSTRUCTION_FORMAT30t", "INSTRUCTION_FORMAT31c", "INSTRUCTION_FORMAT31i", "INSTRUCTION_FORMAT31i_OR_ID", "INSTRUCTION_FORMAT31t", "INSTRUCTION_FORMAT32x", "INSTRUCTION_FORMAT35c_CALL_SITE", "INSTRUCTION_FORMAT35c_METHOD", "INSTRUCTION_FORMAT35c_METHOD_ODEX", "INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE", "INSTRUCTION_FORMAT35c_TYPE", "INSTRUCTION_FORMAT35mi_METHOD", "INSTRUCTION_FORMAT35ms_METHOD", "INSTRUCTION_FORMAT3rc_CALL_SITE", "INSTRUCTION_FORMAT3rc_METHOD", "INSTRUCTION_FORMAT3rc_METHOD_ODEX", "INSTRUCTION_FORMAT3rc_TYPE", "INSTRUCTION_FORMAT3rmi_METHOD", "INSTRUCTION_FORMAT3rms_METHOD", "INSTRUCTION_FORMAT45cc_METHOD", "INSTRUCTION_FORMAT4rcc_METHOD", "INSTRUCTION_FORMAT51l", "INTEGER_LITERAL", "INVALID_TOKEN", "I_ACCESS_LIST", "I_ACCESS_OR_RESTRICTION_LIST", "I_ANNOTATION", "I_ANNOTATIONS", "I_ANNOTATION_ELEMENT", "I_ARRAY_ELEMENTS", "I_ARRAY_ELEMENT_SIZE", "I_CALL_SITE_EXTRA_ARGUMENTS", "I_CALL_SITE_REFERENCE", "I_CATCH", "I_CATCHALL", "I_CATCHES", "I_CLASS_DEF", "I_ENCODED_ARRAY", "I_ENCODED_ENUM", "I_ENCODED_FIELD", "I_ENCODED_METHOD", "I_ENCODED_METHOD_HANDLE", "I_END_LOCAL", "I_EPILOGUE", "I_FIELD", "I_FIELDS", "I_FIELD_INITIAL_VALUE", "I_FIELD_TYPE", "I_IMPLEMENTS", "I_LABEL", "I_LINE", "I_LOCAL", "I_LOCALS", "I_METHOD", "I_METHODS", "I_METHOD_PROTOTYPE", "I_METHOD_RETURN_TYPE", "I_ORDERED_METHOD_ITEMS", "I_PACKED_SWITCH_ELEMENTS", "I_PACKED_SWITCH_START_KEY", "I_PARAMETER", "I_PARAMETERS", "I_PARAMETER_NOT_SPECIFIED", "I_PROLOGUE", "I_REGISTERS", "I_REGISTER_LIST", "I_REGISTER_RANGE", "I_RESTART_LOCAL", "I_SOURCE", "I_SPARSE_SWITCH_ELEMENTS", "I_STATEMENT_ARRAY_DATA", "I_STATEMENT_FORMAT10t", "I_STATEMENT_FORMAT10x", "I_STATEMENT_FORMAT11n", "I_STATEMENT_FORMAT11x", "I_STATEMENT_FORMAT12x", "I_STATEMENT_FORMAT20bc", "I_STATEMENT_FORMAT20t", "I_STATEMENT_FORMAT21c_FIELD", "I_STATEMENT_FORMAT21c_METHOD_HANDLE", "I_STATEMENT_FORMAT21c_METHOD_TYPE", "I_STATEMENT_FORMAT21c_STRING", "I_STATEMENT_FORMAT21c_TYPE", "I_STATEMENT_FORMAT21ih", "I_STATEMENT_FORMAT21lh", "I_STATEMENT_FORMAT21s", "I_STATEMENT_FORMAT21t", "I_STATEMENT_FORMAT22b", "I_STATEMENT_FORMAT22c_FIELD", "I_STATEMENT_FORMAT22c_TYPE", "I_STATEMENT_FORMAT22s", "I_STATEMENT_FORMAT22t", "I_STATEMENT_FORMAT22x", "I_STATEMENT_FORMAT23x", "I_STATEMENT_FORMAT30t", "I_STATEMENT_FORMAT31c", "I_STATEMENT_FORMAT31i", "I_STATEMENT_FORMAT31t", "I_STATEMENT_FORMAT32x", "I_STATEMENT_FORMAT35c_CALL_SITE", "I_STATEMENT_FORMAT35c_METHOD", "I_STATEMENT_FORMAT35c_TYPE", "I_STATEMENT_FORMAT3rc_CALL_SITE", "I_STATEMENT_FORMAT3rc_METHOD", "I_STATEMENT_FORMAT3rc_TYPE", "I_STATEMENT_FORMAT45cc_METHOD", "I_STATEMENT_FORMAT4rcc_METHOD", "I_STATEMENT_FORMAT51l", "I_STATEMENT_PACKED_SWITCH", "I_STATEMENT_SPARSE_SWITCH", "I_SUBANNOTATION", "I_SUPER", "LINE_COMMENT", "LINE_DIRECTIVE", "LOCALS_DIRECTIVE", "LOCAL_DIRECTIVE", "LONG_LITERAL", "MEMBER_NAME", "METHOD_DIRECTIVE", "METHOD_HANDLE_TYPE_FIELD", "METHOD_HANDLE_TYPE_METHOD", "NEGATIVE_INTEGER_LITERAL", "NULL_LITERAL", "OPEN_BRACE", "OPEN_PAREN", "PACKED_SWITCH_DIRECTIVE", "PARAMETER_DIRECTIVE", "PARAM_LIST_OR_ID_PRIMITIVE_TYPE", "POSITIVE_INTEGER_LITERAL", "PRIMITIVE_TYPE", "PROLOGUE_DIRECTIVE", "REGISTER", "REGISTERS_DIRECTIVE", "RESTART_LOCAL_DIRECTIVE", "SHORT_LITERAL", "SIMPLE_NAME", "SOURCE_DIRECTIVE", "SPARSE_SWITCH_DIRECTIVE", "STRING_LITERAL", "SUBANNOTATION_DIRECTIVE", "SUPER_DIRECTIVE", "VERIFICATION_ERROR_TYPE", "VOID_TYPE", "VTABLE_INDEX", "WHITE_SPACE"}
    public static val FOLLOW_I_CLASS_DEF_in_smali_file52 = BitSet(new Array<Long>{4})
    public static val FOLLOW_header_in_smali_file54 = BitSet(new Array<Long>{0, Long.MIN_VALUE})
    public static val FOLLOW_methods_in_smali_file56 = BitSet(new Array<Long>{0, 18014398509481984L})
    public static val FOLLOW_fields_in_smali_file58 = BitSet(new Array<Long>{0, 68719476736L})
    public static val FOLLOW_annotations_in_smali_file60 = BitSet(new Array<Long>{8})
    public static val FOLLOW_class_spec_in_header85 = BitSet(new Array<Long>{0, 144115188075855872L, 72057594037936128L})
    public static val FOLLOW_super_spec_in_header87 = BitSet(new Array<Long>{0, 144115188075855872L, PlaybackStateCompat.ACTION_PLAY_FROM_URI})
    public static val FOLLOW_implements_list_in_header90 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_URI})
    public static val FOLLOW_source_spec_in_header92 = BitSet(new Array<Long>{2})
    public static val FOLLOW_CLASS_DESCRIPTOR_in_class_spec110 = BitSet(new Array<Long>{0, 8589934592L})
    public static val FOLLOW_access_list_in_class_spec112 = BitSet(new Array<Long>{2})
    public static val FOLLOW_I_SUPER_in_super_spec130 = BitSet(new Array<Long>{4})
    public static val FOLLOW_CLASS_DESCRIPTOR_in_super_spec132 = BitSet(new Array<Long>{8})
    public static val FOLLOW_I_IMPLEMENTS_in_implements_spec152 = BitSet(new Array<Long>{4})
    public static val FOLLOW_CLASS_DESCRIPTOR_in_implements_spec154 = BitSet(new Array<Long>{8})
    public static val FOLLOW_implements_spec_in_implements_list184 = BitSet(new Array<Long>{2, 144115188075855872L})
    public static val FOLLOW_I_SOURCE_in_source_spec213 = BitSet(new Array<Long>{4})
    public static val FOLLOW_string_literal_in_source_spec215 = BitSet(new Array<Long>{8})
    public static val FOLLOW_I_ACCESS_LIST_in_access_list247 = BitSet(new Array<Long>{4})
    public static val FOLLOW_ACCESS_SPEC_in_access_list265 = BitSet(new Array<Long>{24})
    public static val FOLLOW_I_ACCESS_OR_RESTRICTION_LIST_in_access_or_restriction_list308 = BitSet(new Array<Long>{4})
    public static val FOLLOW_ACCESS_SPEC_in_access_or_restriction_list326 = BitSet(new Array<Long>{2199023255576L})
    public static val FOLLOW_HIDDENAPI_RESTRICTION_in_access_or_restriction_list356 = BitSet(new Array<Long>{2199023255576L})
    public static val FOLLOW_I_FIELDS_in_fields405 = BitSet(new Array<Long>{4})
    public static val FOLLOW_field_in_fields414 = BitSet(new Array<Long>{8, 9007199254740992L})
    public static val FOLLOW_I_METHODS_in_methods446 = BitSet(new Array<Long>{4})
    public static val FOLLOW_method_in_methods455 = BitSet(new Array<Long>{8, 4611686018427387904L})
    public static val FOLLOW_I_FIELD_in_field480 = BitSet(new Array<Long>{4})
    public static val FOLLOW_SIMPLE_NAME_in_field482 = BitSet(new Array<Long>{0, 17179869184L})
    public static val FOLLOW_access_or_restriction_list_in_field484 = BitSet(new Array<Long>{0, 72057594037927936L})
    public static val FOLLOW_I_FIELD_TYPE_in_field487 = BitSet(new Array<Long>{4})
    public static val FOLLOW_nonvoid_type_descriptor_in_field489 = BitSet(new Array<Long>{8})
    public static val FOLLOW_field_initial_value_in_field492 = BitSet(new Array<Long>{8, 68719476736L})
    public static val FOLLOW_annotations_in_field494 = BitSet(new Array<Long>{8})
    public static val FOLLOW_I_FIELD_INITIAL_VALUE_in_field_initial_value515 = BitSet(new Array<Long>{4})
    public static val FOLLOW_literal_in_field_initial_value517 = BitSet(new Array<Long>{8})
    public static val FOLLOW_integer_literal_in_literal539 = BitSet(new Array<Long>{2})
    public static val FOLLOW_long_literal_in_literal547 = BitSet(new Array<Long>{2})
    public static val FOLLOW_short_literal_in_literal555 = BitSet(new Array<Long>{2})
    public static val FOLLOW_byte_literal_in_literal563 = BitSet(new Array<Long>{2})
    public static val FOLLOW_float_literal_in_literal571 = BitSet(new Array<Long>{2})
    public static val FOLLOW_double_literal_in_literal579 = BitSet(new Array<Long>{2})
    public static val FOLLOW_char_literal_in_literal587 = BitSet(new Array<Long>{2})
    public static val FOLLOW_string_literal_in_literal595 = BitSet(new Array<Long>{2})
    public static val FOLLOW_bool_literal_in_literal603 = BitSet(new Array<Long>{2})
    public static val FOLLOW_NULL_LITERAL_in_literal611 = BitSet(new Array<Long>{2})
    public static val FOLLOW_type_descriptor_in_literal619 = BitSet(new Array<Long>{2})
    public static val FOLLOW_array_literal_in_literal627 = BitSet(new Array<Long>{2})
    public static val FOLLOW_subannotation_in_literal635 = BitSet(new Array<Long>{2})
    public static val FOLLOW_field_literal_in_literal643 = BitSet(new Array<Long>{2})
    public static val FOLLOW_method_literal_in_literal651 = BitSet(new Array<Long>{2})
    public static val FOLLOW_enum_literal_in_literal659 = BitSet(new Array<Long>{2})
    public static val FOLLOW_method_handle_literal_in_literal667 = BitSet(new Array<Long>{2})
    public static val FOLLOW_method_prototype_in_literal675 = BitSet(new Array<Long>{2})
    public static val FOLLOW_integer_literal_in_fixed_64bit_literal_number691 = BitSet(new Array<Long>{2})
    public static val FOLLOW_long_literal_in_fixed_64bit_literal_number699 = BitSet(new Array<Long>{2})
    public static val FOLLOW_short_literal_in_fixed_64bit_literal_number707 = BitSet(new Array<Long>{2})
    public static val FOLLOW_byte_literal_in_fixed_64bit_literal_number715 = BitSet(new Array<Long>{2})
    public static val FOLLOW_float_literal_in_fixed_64bit_literal_number723 = BitSet(new Array<Long>{2})
    public static val FOLLOW_double_literal_in_fixed_64bit_literal_number731 = BitSet(new Array<Long>{2})
    public static val FOLLOW_char_literal_in_fixed_64bit_literal_number739 = BitSet(new Array<Long>{2})
    public static val FOLLOW_bool_literal_in_fixed_64bit_literal_number747 = BitSet(new Array<Long>{2})
    public static val FOLLOW_integer_literal_in_fixed_64bit_literal762 = BitSet(new Array<Long>{2})
    public static val FOLLOW_long_literal_in_fixed_64bit_literal770 = BitSet(new Array<Long>{2})
    public static val FOLLOW_short_literal_in_fixed_64bit_literal778 = BitSet(new Array<Long>{2})
    public static val FOLLOW_byte_literal_in_fixed_64bit_literal786 = BitSet(new Array<Long>{2})
    public static val FOLLOW_float_literal_in_fixed_64bit_literal794 = BitSet(new Array<Long>{2})
    public static val FOLLOW_double_literal_in_fixed_64bit_literal802 = BitSet(new Array<Long>{2})
    public static val FOLLOW_char_literal_in_fixed_64bit_literal810 = BitSet(new Array<Long>{2})
    public static val FOLLOW_bool_literal_in_fixed_64bit_literal818 = BitSet(new Array<Long>{2})
    public static val FOLLOW_integer_literal_in_fixed_32bit_literal835 = BitSet(new Array<Long>{2})
    public static val FOLLOW_long_literal_in_fixed_32bit_literal843 = BitSet(new Array<Long>{2})
    public static val FOLLOW_short_literal_in_fixed_32bit_literal851 = BitSet(new Array<Long>{2})
    public static val FOLLOW_byte_literal_in_fixed_32bit_literal859 = BitSet(new Array<Long>{2})
    public static val FOLLOW_float_literal_in_fixed_32bit_literal867 = BitSet(new Array<Long>{2})
    public static val FOLLOW_char_literal_in_fixed_32bit_literal875 = BitSet(new Array<Long>{2})
    public static val FOLLOW_bool_literal_in_fixed_32bit_literal883 = BitSet(new Array<Long>{2})
    public static val FOLLOW_I_ARRAY_ELEMENTS_in_array_elements905 = BitSet(new Array<Long>{4})
    public static val FOLLOW_fixed_64bit_literal_number_in_array_elements914 = BitSet(new Array<Long>{549764241416L, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
    public static val FOLLOW_I_PACKED_SWITCH_ELEMENTS_in_packed_switch_elements950 = BitSet(new Array<Long>{4})
    public static val FOLLOW_label_ref_in_packed_switch_elements959 = BitSet(new Array<Long>{8, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
    public static val FOLLOW_I_SPARSE_SWITCH_ELEMENTS_in_sparse_switch_elements994 = BitSet(new Array<Long>{4})
    public static val FOLLOW_fixed_32bit_literal_in_sparse_switch_elements1004 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
    public static val FOLLOW_label_ref_in_sparse_switch_elements1006 = BitSet(new Array<Long>{549755852808L, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
    public static val FOLLOW_I_METHOD_in_method1058 = BitSet(new Array<Long>{4})
    public static val FOLLOW_method_name_and_prototype_in_method1066 = BitSet(new Array<Long>{0, 17179869184L})
    public static val FOLLOW_access_or_restriction_list_in_method1074 = BitSet(new Array<Long>{0, 2305843009213693952L, 516})
    public static val FOLLOW_registers_directive_in_method1101 = BitSet(new Array<Long>{0, 0, 4})
    public static val FOLLOW_ordered_method_items_in_method1158 = BitSet(new Array<Long>{0, 17592186044416L})
    public static val FOLLOW_catches_in_method1166 = BitSet(new Array<Long>{0, 0, 64})
    public static val FOLLOW_parameters_in_method1174 = BitSet(new Array<Long>{0, 68719476736L})
    public static val FOLLOW_annotations_in_method1183 = BitSet(new Array<Long>{8})
    public static val FOLLOW_I_METHOD_PROTOTYPE_in_method_prototype1207 = BitSet(new Array<Long>{4})
    public static val FOLLOW_I_METHOD_RETURN_TYPE_in_method_prototype1210 = BitSet(new Array<Long>{4})
    public static val FOLLOW_type_descriptor_in_method_prototype1212 = BitSet(new Array<Long>{8})
    public static val FOLLOW_method_type_list_in_method_prototype1215 = BitSet(new Array<Long>{8})
    public static val FOLLOW_SIMPLE_NAME_in_method_name_and_prototype1233 = BitSet(new Array<Long>{0, 0, 1})
    public static val FOLLOW_method_prototype_in_method_name_and_prototype1235 = BitSet(new Array<Long>{2})
    public static val FOLLOW_nonvoid_type_descriptor_in_method_type_list1269 = BitSet(new Array<Long>{65794, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
    public static val FOLLOW_I_CALL_SITE_REFERENCE_in_call_site_reference1300 = BitSet(new Array<Long>{4})
    public static val FOLLOW_SIMPLE_NAME_in_call_site_reference1304 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
    public static val FOLLOW_string_literal_in_call_site_reference1308 = BitSet(new Array<Long>{0, 0, 1})
    public static val FOLLOW_method_prototype_in_call_site_reference1310 = BitSet(new Array<Long>{0, 1099511627776L})
    public static val FOLLOW_call_site_extra_arguments_in_call_site_reference1320 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
    public static val FOLLOW_method_reference_in_call_site_reference1322 = BitSet(new Array<Long>{8})

    public static class access_or_restriction_list_return extends TreeRuleReturnScope {
        public Set<HiddenApiRestriction> hiddenApiRestrictions
        public Int value
    }

    public static class class_spec_return extends TreeRuleReturnScope {
        public Int accessFlags
        public String type
    }

    public static class field_reference_return extends TreeRuleReturnScope {
        public ImmutableFieldReference fieldReference
    }

    public static class header_return extends TreeRuleReturnScope {
        public Int accessFlags
        public String classType
        public List<String> implementsList
        public String sourceSpec
        public String superType
    }

    public static class instruction_return extends TreeRuleReturnScope {
    }

    public static class method_handle_type_return extends TreeRuleReturnScope {
        public Int methodHandleType
    }

    public static class method_name_and_prototype_return extends TreeRuleReturnScope {
        public String name
        public List<SmaliMethodParameter> parameters
        public String returnType
    }

    public static class method_scope {
        public Boolean isStatic
        public MethodImplementationBuilder methodBuilder
        public Int methodParameterRegisters
        public Int totalMethodRegisters
    }

    public static class nonvoid_type_descriptor_return extends TreeRuleReturnScope {
        public String type
    }

    public static class reference_type_descriptor_return extends TreeRuleReturnScope {
        public String type
    }

    public static class register_list_return extends TreeRuleReturnScope {
        public Byte registerCount
        public Array<Byte> registers
    }

    public static class register_range_return extends TreeRuleReturnScope {
        public Int endRegister
        public Int startRegister
    }

    public static class registers_directive_return extends TreeRuleReturnScope {
        public Boolean isLocalsDirective
        public Int registers
    }

    public static class subannotation_return extends TreeRuleReturnScope {
        public String annotationType
        public List<AnnotationElement> elements
    }

    static {
        BitSet(new Array<Long>{2})
        FOLLOW_method_handle_type_in_method_handle_reference1367 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_field_reference_in_method_handle_reference1370 = BitSet(new Array<Long>{2})
        FOLLOW_method_reference_in_method_handle_reference1374 = BitSet(new Array<Long>{2})
        FOLLOW_I_ENCODED_METHOD_HANDLE_in_method_handle_literal1391 = BitSet(new Array<Long>{4})
        FOLLOW_method_handle_reference_in_method_handle_literal1393 = BitSet(new Array<Long>{8})
        FOLLOW_reference_type_descriptor_in_method_reference1409 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_SIMPLE_NAME_in_method_reference1412 = BitSet(new Array<Long>{0, 0, 1})
        FOLLOW_method_prototype_in_method_reference1414 = BitSet(new Array<Long>{2})
        FOLLOW_reference_type_descriptor_in_field_reference1431 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_SIMPLE_NAME_in_field_reference1434 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_field_reference1436 = BitSet(new Array<Long>{2})
        FOLLOW_I_REGISTERS_in_registers_directive1462 = BitSet(new Array<Long>{4})
        FOLLOW_I_LOCALS_in_registers_directive1474 = BitSet(new Array<Long>{4})
        FOLLOW_short_integral_literal_in_registers_directive1492 = BitSet(new Array<Long>{8})
        FOLLOW_I_LABEL_in_label_def1512 = BitSet(new Array<Long>{4})
        FOLLOW_SIMPLE_NAME_in_label_def1514 = BitSet(new Array<Long>{8})
        FOLLOW_I_CATCHES_in_catches1540 = BitSet(new Array<Long>{4})
        FOLLOW_catch_directive_in_catches1542 = BitSet(new Array<Long>{8, 13194139533312L})
        FOLLOW_catchall_directive_in_catches1545 = BitSet(new Array<Long>{8, 8796093022208L})
        FOLLOW_I_CATCH_in_catch_directive1558 = BitSet(new Array<Long>{4})
        FOLLOW_nonvoid_type_descriptor_in_catch_directive1560 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_catch_directive1564 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_catch_directive1568 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_catch_directive1572 = BitSet(new Array<Long>{8})
        FOLLOW_I_CATCHALL_in_catchall_directive1588 = BitSet(new Array<Long>{4})
        FOLLOW_label_ref_in_catchall_directive1592 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_catchall_directive1596 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_catchall_directive1600 = BitSet(new Array<Long>{8})
        FOLLOW_I_PARAMETERS_in_parameters1617 = BitSet(new Array<Long>{4})
        FOLLOW_parameter_in_parameters1620 = BitSet(new Array<Long>{8, 0, 32})
        FOLLOW_I_PARAMETER_in_parameter1636 = BitSet(new Array<Long>{4})
        FOLLOW_REGISTER_in_parameter1638 = BitSet(new Array<Long>{0, 68719476736L, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_string_literal_in_parameter1640 = BitSet(new Array<Long>{0, 68719476736L})
        FOLLOW_annotations_in_parameter1643 = BitSet(new Array<Long>{8})
        FOLLOW_line_in_debug_directive1660 = BitSet(new Array<Long>{2})
        FOLLOW_local_in_debug_directive1666 = BitSet(new Array<Long>{2})
        FOLLOW_end_local_in_debug_directive1672 = BitSet(new Array<Long>{2})
        FOLLOW_restart_local_in_debug_directive1678 = BitSet(new Array<Long>{2})
        FOLLOW_prologue_in_debug_directive1684 = BitSet(new Array<Long>{2})
        FOLLOW_epilogue_in_debug_directive1690 = BitSet(new Array<Long>{2})
        FOLLOW_source_in_debug_directive1696 = BitSet(new Array<Long>{2})
        FOLLOW_I_LINE_in_line1707 = BitSet(new Array<Long>{4})
        FOLLOW_integral_literal_in_line1709 = BitSet(new Array<Long>{8})
        FOLLOW_I_LOCAL_in_local1727 = BitSet(new Array<Long>{4})
        FOLLOW_REGISTER_in_local1729 = BitSet(new Array<Long>{8, 0, 0, 524296})
        FOLLOW_NULL_LITERAL_in_local1733 = BitSet(new Array<Long>{65800, 0, 0, 525312})
        FOLLOW_string_literal_in_local1739 = BitSet(new Array<Long>{65800, 0, 0, 525312})
        FOLLOW_nonvoid_type_descriptor_in_local1742 = BitSet(new Array<Long>{8, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_string_literal_in_local1747 = BitSet(new Array<Long>{8})
        FOLLOW_I_END_LOCAL_in_end_local1768 = BitSet(new Array<Long>{4})
        FOLLOW_REGISTER_in_end_local1770 = BitSet(new Array<Long>{8})
        FOLLOW_I_RESTART_LOCAL_in_restart_local1788 = BitSet(new Array<Long>{4})
        FOLLOW_REGISTER_in_restart_local1790 = BitSet(new Array<Long>{8})
        FOLLOW_I_PROLOGUE_in_prologue1807 = BitSet(new Array<Long>{2})
        FOLLOW_I_EPILOGUE_in_epilogue1823 = BitSet(new Array<Long>{2})
        FOLLOW_I_SOURCE_in_source1840 = BitSet(new Array<Long>{4})
        FOLLOW_string_literal_in_source1842 = BitSet(new Array<Long>{8})
        FOLLOW_I_CALL_SITE_EXTRA_ARGUMENTS_in_call_site_extra_arguments1868 = BitSet(new Array<Long>{4})
        FOLLOW_literal_in_call_site_extra_arguments1871 = BitSet(new Array<Long>{549764307208L, 2181433216991232L, 2341871806232657921L, 8946696})
        FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1887 = BitSet(new Array<Long>{4})
        FOLLOW_label_def_in_ordered_method_items1890 = BitSet(new Array<Long>{8, 2024368032503037952L, 36028797018943744L})
        FOLLOW_instruction_in_ordered_method_items1894 = BitSet(new Array<Long>{8, 2024368032503037952L, 36028797018943744L})
        FOLLOW_debug_directive_in_ordered_method_items1898 = BitSet(new Array<Long>{8, 2024368032503037952L, 36028797018943744L})
        FOLLOW_SIMPLE_NAME_in_label_ref1914 = BitSet(new Array<Long>{2})
        FOLLOW_I_REGISTER_LIST_in_register_list1939 = BitSet(new Array<Long>{4})
        FOLLOW_REGISTER_in_register_list1948 = BitSet(new Array<Long>{8, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_I_REGISTER_RANGE_in_register_range1973 = BitSet(new Array<Long>{4})
        FOLLOW_REGISTER_in_register_range1978 = BitSet(new Array<Long>{8, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_register_range1982 = BitSet(new Array<Long>{8})
        FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference2005 = BitSet(new Array<Long>{2})
        FOLLOW_field_reference_in_verification_error_reference2015 = BitSet(new Array<Long>{2})
        FOLLOW_method_reference_in_verification_error_reference2025 = BitSet(new Array<Long>{2})
        FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type2042 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format10t_in_instruction2056 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format10x_in_instruction2062 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format11n_in_instruction2068 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format11x_in_instruction2074 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format12x_in_instruction2080 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format20bc_in_instruction2086 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format20t_in_instruction2092 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_field_in_instruction2098 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_method_handle_in_instruction2104 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_method_type_in_instruction2110 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_string_in_instruction2116 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_type_in_instruction2122 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21ih_in_instruction2128 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21lh_in_instruction2134 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21s_in_instruction2140 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21t_in_instruction2146 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22b_in_instruction2152 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22c_field_in_instruction2158 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22c_type_in_instruction2164 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22s_in_instruction2170 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22t_in_instruction2176 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22x_in_instruction2182 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format23x_in_instruction2188 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format30t_in_instruction2194 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format31c_in_instruction2200 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format31i_in_instruction2206 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format31t_in_instruction2212 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format32x_in_instruction2218 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_call_site_in_instruction2224 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_method_in_instruction2230 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_type_in_instruction2236 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_call_site_in_instruction2242 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_method_in_instruction2248 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_type_in_instruction2254 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format45cc_method_in_instruction2260 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format4rcc_method_in_instruction2266 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format51l_type_in_instruction2272 = BitSet(new Array<Long>{2})
        FOLLOW_insn_array_data_directive_in_instruction2278 = BitSet(new Array<Long>{2})
        FOLLOW_insn_packed_switch_directive_in_instruction2284 = BitSet(new Array<Long>{2})
        FOLLOW_insn_sparse_switch_directive_in_instruction2290 = BitSet(new Array<Long>{2})
        FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2314 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2316 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_insn_format10t2318 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2341 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2343 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2366 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2368 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format11n2370 = BitSet(new Array<Long>{36864, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_short_integral_literal_in_insn_format11n2372 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2395 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2397 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format11x2399 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2422 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2424 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format12x2428 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format12x2432 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2455 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2457 = BitSet(new Array<Long>{0, 0, 0, 4194304})
        FOLLOW_verification_error_type_in_insn_format20bc2459 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_verification_error_reference_in_insn_format20bc2461 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2484 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2486 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_insn_format20t2488 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2511 = BitSet(new Array<Long>{4})
        BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_field2523 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_field_reference_in_insn_format21c_field2525 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2548 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2553 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_method_handle2556 = BitSet(new Array<Long>{0, PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 3})
        FOLLOW_method_handle_reference_in_insn_format21c_method_handle2558 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2581 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2586 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_method_type2589 = BitSet(new Array<Long>{0, 0, 1})
        FOLLOW_method_prototype_in_insn_format21c_method_type2591 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2614 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2616 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_string2618 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_string_literal_in_insn_format21c_string2620 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2643 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2645 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_type2647 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2649 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2672 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2674 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21ih2676 = BitSet(new Array<Long>{549755852800L, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_fixed_32bit_literal_in_insn_format21ih2678 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2701 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2703 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21lh2705 = BitSet(new Array<Long>{549764241408L, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_fixed_64bit_literal_in_insn_format21lh2707 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2730 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2732 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21s2734 = BitSet(new Array<Long>{36864, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_short_integral_literal_in_insn_format21s2736 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2759 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2761 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21t2763 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_insn_format21t2765 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2788 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2790 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22b2794 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22b2798 = BitSet(new Array<Long>{36864, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_short_integral_literal_in_insn_format22b2800 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2823 = BitSet(new Array<Long>{4})
        BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_field2837 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_field2841 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_field_reference_in_insn_format22c_field2843 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2866 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2868 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_type2872 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_type2876 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2878 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2901 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2903 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22s2907 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22s2911 = BitSet(new Array<Long>{36864, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_short_integral_literal_in_insn_format22s2913 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2936 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2938 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22t2942 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22t2946 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_insn_format22t2948 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2971 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2973 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22x2977 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22x2981 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x3004 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x3006 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format23x3010 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format23x3014 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format23x3018 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t3041 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t3043 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_insn_format30t3045 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c3068 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c3070 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format31c3072 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_string_literal_in_insn_format31c3074 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i3097 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i3099 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format31i3101 = BitSet(new Array<Long>{549755852800L, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_fixed_32bit_literal_in_insn_format31i3103 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t3126 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t3128 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format31t3130 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_label_ref_in_insn_format31t3132 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x3155 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x3157 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format32x3161 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format32x3165 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3193 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3195 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_register_list_in_insn_format35c_call_site3197 = BitSet(new Array<Long>{0, 2199023255552L})
        FOLLOW_call_site_reference_in_insn_format35c_call_site3199 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method3222 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method3224 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_register_list_in_insn_format35c_method3226 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_method_reference_in_insn_format35c_method3228 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type3251 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type3253 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_register_list_in_insn_format35c_type3255 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type3257 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3285 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3287 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH})
        FOLLOW_register_range_in_insn_format3rc_call_site3289 = BitSet(new Array<Long>{0, 2199023255552L})
        FOLLOW_call_site_reference_in_insn_format3rc_call_site3291 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method3314 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method3316 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH})
        FOLLOW_register_range_in_insn_format3rc_method3318 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_method_reference_in_insn_format3rc_method3320 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type3343 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type3345 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH})
        FOLLOW_register_range_in_insn_format3rc_type3347 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type3349 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT45cc_METHOD_in_insn_format45cc_method3372 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method3374 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_register_list_in_insn_format45cc_method3376 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_method_reference_in_insn_format45cc_method3378 = BitSet(new Array<Long>{0, 0, 1})
        FOLLOW_method_prototype_in_insn_format45cc_method3380 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT4rcc_METHOD_in_insn_format4rcc_method3403 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method3405 = BitSet(new Array<Long>{0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH})
        FOLLOW_register_range_in_insn_format4rcc_method3407 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_method_reference_in_insn_format4rcc_method3409 = BitSet(new Array<Long>{0, 0, 1})
        FOLLOW_method_prototype_in_insn_format4rcc_method3411 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type3434 = BitSet(new Array<Long>{4})
        FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type3436 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format51l_type3438 = BitSet(new Array<Long>{549764241408L, 2147483648L, 2305843009213693952L, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID})
        FOLLOW_fixed_64bit_literal_in_insn_format51l_type3440 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive3463 = BitSet(new Array<Long>{4})
        FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive3466 = BitSet(new Array<Long>{4})
        FOLLOW_short_integral_literal_in_insn_array_data_directive3468 = BitSet(new Array<Long>{8})
        FOLLOW_array_elements_in_insn_array_data_directive3471 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3493 = BitSet(new Array<Long>{4})
        FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3496 = BitSet(new Array<Long>{4})
        FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3498 = BitSet(new Array<Long>{8})
        FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3501 = BitSet(new Array<Long>{8})
        FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3525 = BitSet(new Array<Long>{4})
        FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3527 = BitSet(new Array<Long>{8})
        FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor3548 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_PRIMITIVE_TYPE_in_array_descriptor3552 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_array_descriptor3580 = BitSet(new Array<Long>{2})
        FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor3598 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor3606 = BitSet(new Array<Long>{2})
        FOLLOW_array_descriptor_in_nonvoid_type_descriptor3614 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor3635 = BitSet(new Array<Long>{2})
        FOLLOW_array_descriptor_in_reference_type_descriptor3643 = BitSet(new Array<Long>{2})
        FOLLOW_VOID_TYPE_in_type_descriptor3663 = BitSet(new Array<Long>{2})
        FOLLOW_nonvoid_type_descriptor_in_type_descriptor3671 = BitSet(new Array<Long>{2})
        FOLLOW_long_literal_in_short_integral_literal3689 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_short_integral_literal3701 = BitSet(new Array<Long>{2})
        FOLLOW_short_literal_in_short_integral_literal3713 = BitSet(new Array<Long>{2})
        FOLLOW_char_literal_in_short_integral_literal3721 = BitSet(new Array<Long>{2})
        FOLLOW_byte_literal_in_short_integral_literal3729 = BitSet(new Array<Long>{2})
        FOLLOW_long_literal_in_integral_literal3744 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_integral_literal3756 = BitSet(new Array<Long>{2})
        FOLLOW_short_literal_in_integral_literal3764 = BitSet(new Array<Long>{2})
        FOLLOW_byte_literal_in_integral_literal3772 = BitSet(new Array<Long>{2})
        FOLLOW_INTEGER_LITERAL_in_integer_literal3788 = BitSet(new Array<Long>{2})
        FOLLOW_LONG_LITERAL_in_long_literal3803 = BitSet(new Array<Long>{2})
        FOLLOW_SHORT_LITERAL_in_short_literal3818 = BitSet(new Array<Long>{2})
        FOLLOW_BYTE_LITERAL_in_byte_literal3833 = BitSet(new Array<Long>{2})
        FOLLOW_FLOAT_LITERAL_in_float_literal3848 = BitSet(new Array<Long>{2})
        FOLLOW_DOUBLE_LITERAL_in_double_literal3863 = BitSet(new Array<Long>{2})
        FOLLOW_CHAR_LITERAL_in_char_literal3878 = BitSet(new Array<Long>{2})
        FOLLOW_STRING_LITERAL_in_string_literal3893 = BitSet(new Array<Long>{2})
        FOLLOW_BOOL_LITERAL_in_bool_literal3912 = BitSet(new Array<Long>{2})
        FOLLOW_I_ENCODED_ARRAY_in_array_literal3934 = BitSet(new Array<Long>{4})
        FOLLOW_literal_in_array_literal3937 = BitSet(new Array<Long>{549764307208L, 2181433216991232L, 2341871806232657921L, 8946696})
        FOLLOW_I_ANNOTATIONS_in_annotations3962 = BitSet(new Array<Long>{4})
        FOLLOW_annotation_in_annotations3965 = BitSet(new Array<Long>{8, 34359738368L})
        FOLLOW_I_ANNOTATION_in_annotation3994 = BitSet(new Array<Long>{4})
        FOLLOW_ANNOTATION_VISIBILITY_in_annotation3996 = BitSet(new Array<Long>{0, 0, 36028797018963968L})
        FOLLOW_subannotation_in_annotation3998 = BitSet(new Array<Long>{8})
        FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element4019 = BitSet(new Array<Long>{4})
        FOLLOW_SIMPLE_NAME_in_annotation_element4021 = BitSet(new Array<Long>{549764307200L, 2181433216991232L, 2341871806232657921L, 8946696})
        FOLLOW_literal_in_annotation_element4023 = BitSet(new Array<Long>{8})
        FOLLOW_I_SUBANNOTATION_in_subannotation4050 = BitSet(new Array<Long>{4})
        FOLLOW_CLASS_DESCRIPTOR_in_subannotation4060 = BitSet(new Array<Long>{8, 137438953472L})
        FOLLOW_annotation_element_in_subannotation4071 = BitSet(new Array<Long>{8, 137438953472L})
        FOLLOW_I_ENCODED_FIELD_in_field_literal4110 = BitSet(new Array<Long>{4})
        FOLLOW_field_reference_in_field_literal4112 = BitSet(new Array<Long>{8})
        FOLLOW_I_ENCODED_METHOD_in_method_literal4133 = BitSet(new Array<Long>{4})
        FOLLOW_method_reference_in_method_literal4135 = BitSet(new Array<Long>{8})
        FOLLOW_I_ENCODED_ENUM_in_enum_literal4156 = BitSet(new Array<Long>{4})
        FOLLOW_field_reference_in_enum_literal4158 = BitSet(new Array<Long>{8})
    }

    constructor(TreeNodeStream treeNodeStream) {
        this(treeNodeStream, RecognizerSharedState())
    }

    constructor(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(treeNodeStream, recognizerSharedState)
        this.verboseErrors = false
        this.apiLevel = 15
        this.opcodes = Opcodes.forApi(15)
        this.errMessages = ""
        this.method_stack = new Stack<>()
    }

    public final Int access_list() throws RecognitionException {
        Int value = 0
        try {
            match(this.input, 97, FOLLOW_I_ACCESS_LIST_in_access_list247)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    if ((this.input.LA(1) == 4 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    value |= AccessFlags.getAccessFlag(((CommonTree) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_list265)).getText()).getValue()
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return value
    }

    public final access_or_restriction_list_return access_or_restriction_list() throws RecognitionException {
        access_or_restriction_list_return access_or_restriction_list_returnVar = access_or_restriction_list_return()
        access_or_restriction_list_returnVar.start = this.input.LT(1)
        access_or_restriction_list_returnVar.value = 0
        ArrayList arrayList = ArrayList()
        try {
            match(this.input, 98, FOLLOW_I_ACCESS_OR_RESTRICTION_LIST_in_access_or_restriction_list308)
            HiddenApiRestriction hiddenApiRestriction = null
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                HiddenApiRestriction hiddenApiRestriction2 = null
                while (true) {
                    Int iLA = this.input.LA(1)
                    Char c = iLA == 4 ? (Char) 1 : iLA == 41 ? (Char) 2 : (Char) 3
                    if (c == 1) {
                        access_or_restriction_list_returnVar.value = AccessFlags.getAccessFlag(((CommonTree) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_or_restriction_list326)).getText()).getValue() | access_or_restriction_list_returnVar.value
                    } else {
                        if (c != 2) {
                            match(this.input, 3, null)
                            hiddenApiRestriction = hiddenApiRestriction2
                            break
                        }
                        CommonTree commonTree = (CommonTree) match(this.input, 41, FOLLOW_HIDDENAPI_RESTRICTION_in_access_or_restriction_list356)
                        if (this.opcodes.api < 29) {
                            throw SemanticException(this.input, commonTree, "Hidden API restrictions are only supported on api 29 and above.", new Object[0])
                        }
                        HiddenApiRestriction hiddenApiRestrictionForName = HiddenApiRestriction.forName(commonTree.getText())
                        if (hiddenApiRestrictionForName.isDomainSpecificApiFlag()) {
                            arrayList.add(hiddenApiRestrictionForName)
                        } else {
                            if (hiddenApiRestriction2 != null) {
                                throw SemanticException(this.input, commonTree, "Only one hidden api restriction may be specified.", new Object[0])
                            }
                            hiddenApiRestriction2 = hiddenApiRestrictionForName
                        }
                    }
                }
            }
            ImmutableSet.Builder builder = ImmutableSet.builder()
            if (hiddenApiRestriction != null) {
                builder.add((ImmutableSet.Builder) hiddenApiRestriction)
            }
            builder.addAll((Iterable) arrayList)
            access_or_restriction_list_returnVar.hiddenApiRestrictions = builder.build()
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return access_or_restriction_list_returnVar
    }

    public final Annotation annotation() throws RecognitionException {
        try {
            match(this.input, 99, FOLLOW_I_ANNOTATION_in_annotation3994)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_annotation3996)
            pushFollow(FOLLOW_subannotation_in_annotation3998)
            subannotation_return subannotation_returnVarSubannotation = subannotation()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            return ImmutableAnnotation(AnnotationVisibility.getVisibility(commonTree != null ? commonTree.getText() : null), subannotation_returnVarSubannotation != null ? subannotation_returnVarSubannotation.annotationType : null, subannotation_returnVarSubannotation != null ? subannotation_returnVarSubannotation.elements : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final AnnotationElement annotation_element() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle, FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element4019)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_annotation_element4021)
            pushFollow(FOLLOW_literal_in_annotation_element4023)
            ImmutableEncodedValue immutableEncodedValueLiteral = literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            return ImmutableAnnotationElement(commonTree != null ? commonTree.getText() : null, immutableEncodedValueLiteral)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Set<Annotation> annotations() throws RecognitionException {
        Annotation annotation
        try {
            HashMap mapNewHashMap = Maps.newHashMap()
            match(this.input, 100, FOLLOW_I_ANNOTATIONS_in_annotations3962)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                do {
                    if ((this.input.LA(1) == 99 ? (Char) 1 : (Char) 2) != 1) {
                        match(this.input, 3, null)
                    } else {
                        pushFollow(FOLLOW_annotation_in_annotations3965)
                        annotation = annotation()
                        this.state._fsp--
                    }
                } while (((Annotation) mapNewHashMap.put(annotation.getType(), annotation)) == null);
                throw SemanticException(this.input, "Multiple annotations of type %s", annotation.getType())
            }
            return ImmutableSet.copyOf(mapNewHashMap.values())
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final String array_descriptor() throws RecognitionException {
        Char c
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 8, FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor3548)
            Int iLA = this.input.LA(1)
            if (iLA == 202) {
                c = 1
            } else {
                if (iLA != 16) {
                    throw NoViableAltException("", 40, 0, this.input)
                }
                c = 2
            }
            if (c == 1) {
                CommonTree commonTree2 = (CommonTree) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_array_descriptor3552)
                StringBuilder sb = StringBuilder()
                sb.append(commonTree != null ? commonTree.getText() : null)
                sb.append(commonTree2 != null ? commonTree2.getText() : null)
                return sb.toString()
            }
            if (c != 2) {
                return null
            }
            CommonTree commonTree3 = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_array_descriptor3580)
            StringBuilder sb2 = StringBuilder()
            sb2.append(commonTree != null ? commonTree.getText() : null)
            sb2.append(commonTree3 != null ? commonTree3.getText() : null)
            return sb2.toString()
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final List<Number> array_elements() throws RecognitionException {
        ArrayList arrayListNewArrayList
        RecognitionException e
        try {
            arrayListNewArrayList = Lists.newArrayList()
        } catch (RecognitionException e2) {
            arrayListNewArrayList = null
            e = e2
        }
        try {
            match(this.input, R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle, FOLLOW_I_ARRAY_ELEMENTS_in_array_elements905)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    Int iLA = this.input.LA(1)
                    if ((((iLA >= 11 && iLA <= 12) || iLA == 15 || iLA == 23 || iLA == 39 || iLA == 95 || iLA == 189 || iLA == 207) ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_fixed_64bit_literal_number_in_array_elements914)
                    Number numberFixed_64bit_literal_number = fixed_64bit_literal_number()
                    this.state._fsp--
                    arrayListNewArrayList.add(numberFixed_64bit_literal_number)
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e3) {
            e = e3
            reportError(e)
            recover(this.input, e)
            return arrayListNewArrayList
        }
        return arrayListNewArrayList
    }

    public final List<EncodedValue> array_literal() throws RecognitionException {
        ArrayList arrayListNewArrayList
        RecognitionException e
        try {
            arrayListNewArrayList = Lists.newArrayList()
            try {
                match(this.input, R.styleable.AppCompatTheme_ratingBarStyle, FOLLOW_I_ENCODED_ARRAY_in_array_literal3934)
                if (this.input.LA(1) == 2) {
                    match(this.input, 2, null)
                    while (true) {
                        Int iLA = this.input.LA(1)
                        if (((iLA == 8 || (iLA >= 11 && iLA <= 12) || ((iLA >= 15 && iLA <= 16) || iLA == 23 || iLA == 39 || iLA == 95 || ((iLA >= 110 && iLA <= 114) || iLA == 128 || iLA == 183 || iLA == 189 || iLA == 195 || iLA == 202 || iLA == 207 || iLA == 211 || iLA == 215))) ? (Char) 1 : (Char) 2) != 1) {
                            break
                        }
                        pushFollow(FOLLOW_literal_in_array_literal3937)
                        ImmutableEncodedValue immutableEncodedValueLiteral = literal()
                        this.state._fsp--
                        arrayListNewArrayList.add(immutableEncodedValueLiteral)
                    }
                    match(this.input, 3, null)
                }
            } catch (RecognitionException e2) {
                e = e2
                reportError(e)
                recover(this.input, e)
                return arrayListNewArrayList
            }
        } catch (RecognitionException e3) {
            arrayListNewArrayList = null
            e = e3
        }
        return arrayListNewArrayList
    }

    public final Boolean bool_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_bool_literal3912)
            return Boolean.parseBoolean(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return false
        }
    }

    public final Byte byte_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_byte_literal3833)
            return LiteralTools.parseByte(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return (Byte) 0
        }
    }

    public final List<ImmutableEncodedValue> call_site_extra_arguments() throws RecognitionException {
        ArrayList arrayListNewArrayList
        RecognitionException e
        try {
            arrayListNewArrayList = Lists.newArrayList()
            try {
                match(this.input, R.styleable.AppCompatTheme_buttonStyle, FOLLOW_I_CALL_SITE_EXTRA_ARGUMENTS_in_call_site_extra_arguments1868)
                if (this.input.LA(1) == 2) {
                    match(this.input, 2, null)
                    while (true) {
                        Int iLA = this.input.LA(1)
                        if (((iLA == 8 || (iLA >= 11 && iLA <= 12) || ((iLA >= 15 && iLA <= 16) || iLA == 23 || iLA == 39 || iLA == 95 || ((iLA >= 110 && iLA <= 114) || iLA == 128 || iLA == 183 || iLA == 189 || iLA == 195 || iLA == 202 || iLA == 207 || iLA == 211 || iLA == 215))) ? (Char) 1 : (Char) 2) != 1) {
                            break
                        }
                        pushFollow(FOLLOW_literal_in_call_site_extra_arguments1871)
                        ImmutableEncodedValue immutableEncodedValueLiteral = literal()
                        this.state._fsp--
                        arrayListNewArrayList.add(immutableEncodedValueLiteral)
                    }
                    match(this.input, 3, null)
                }
            } catch (RecognitionException e2) {
                e = e2
                reportError(e)
                recover(this.input, e)
                return arrayListNewArrayList
            }
        } catch (RecognitionException e3) {
            arrayListNewArrayList = null
            e = e3
        }
        return arrayListNewArrayList
    }

    public final ImmutableCallSiteReference call_site_reference() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_buttonStyleSmall, FOLLOW_I_CALL_SITE_REFERENCE_in_call_site_reference1300)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_call_site_reference1304)
            pushFollow(FOLLOW_string_literal_in_call_site_reference1308)
            String strString_literal = string_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_method_prototype_in_call_site_reference1310)
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            pushFollow(FOLLOW_call_site_extra_arguments_in_call_site_reference1320)
            List<ImmutableEncodedValue> listCall_site_extra_arguments = call_site_extra_arguments()
            RecognizerSharedState recognizerSharedState3 = this.state
            recognizerSharedState3._fsp--
            pushFollow(FOLLOW_method_reference_in_call_site_reference1322)
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
            RecognizerSharedState recognizerSharedState4 = this.state
            recognizerSharedState4._fsp--
            match(this.input, 3, null)
            return ImmutableCallSiteReference(commonTree != null ? commonTree.getText() : null, ImmutableMethodHandleReference(4, immutableMethodReferenceMethod_reference), strString_literal, immutableMethodProtoReferenceMethod_prototype, listCall_site_extra_arguments)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Unit catch_directive() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_checkboxStyle, FOLLOW_I_CATCH_in_catch_directive1558)
            match(this.input, 2, null)
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_catch_directive1560)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_label_ref_in_catch_directive1564)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            pushFollow(FOLLOW_label_ref_in_catch_directive1568)
            Label labelLabel_ref2 = label_ref()
            RecognizerSharedState recognizerSharedState3 = this.state
            recognizerSharedState3._fsp--
            pushFollow(FOLLOW_label_ref_in_catch_directive1572)
            Label labelLabel_ref3 = label_ref()
            RecognizerSharedState recognizerSharedState4 = this.state
            recognizerSharedState4._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addCatch(this.dexBuilder.internTypeReference(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null), labelLabel_ref, labelLabel_ref2, labelLabel_ref3)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit catchall_directive() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_checkedTextViewStyle, FOLLOW_I_CATCHALL_in_catchall_directive1588)
            match(this.input, 2, null)
            pushFollow(FOLLOW_label_ref_in_catchall_directive1592)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_label_ref_in_catchall_directive1596)
            Label labelLabel_ref2 = label_ref()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            pushFollow(FOLLOW_label_ref_in_catchall_directive1600)
            Label labelLabel_ref3 = label_ref()
            RecognizerSharedState recognizerSharedState3 = this.state
            recognizerSharedState3._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addCatch(labelLabel_ref, labelLabel_ref2, labelLabel_ref3)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final List<Object> catches() throws RecognitionException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        try {
            match(this.input, 108, FOLLOW_I_CATCHES_in_catches1540)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    if ((this.input.LA(1) == 106 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_catch_directive_in_catches1542)
                    catch_directive()
                    this.state._fsp--
                }
                while (true) {
                    if ((this.input.LA(1) == 107 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_catchall_directive_in_catches1545)
                    catchall_directive()
                    this.state._fsp--
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return arrayListNewArrayList
    }

    public final Char char_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_char_literal3878)
            return (commonTree != null ? commonTree.getText() : null).charAt(1)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return (Char) 0
        }
    }

    public final class_spec_return class_spec() throws RecognitionException {
        class_spec_return class_spec_returnVar = class_spec_return()
        class_spec_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_class_spec110)
            pushFollow(FOLLOW_access_list_in_class_spec112)
            Int iAccess_list = access_list()
            this.state._fsp--
            class_spec_returnVar.type = commonTree != null ? commonTree.getText() : null
            class_spec_returnVar.accessFlags = iAccess_list
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return class_spec_returnVar
    }

    public final Unit debug_directive() throws RecognitionException {
        Char c
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 115) {
                c = 3
            } else if (iLA == 116) {
                c = 6
            } else if (iLA == 123) {
                c = 1
            } else if (iLA == 124) {
                c = 2
            } else if (iLA == 136) {
                c = 5
            } else if (iLA == 140) {
                c = 4
            } else {
                if (iLA != 141) {
                    throw NoViableAltException("", 27, 0, this.input)
                }
                c = 7
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_line_in_debug_directive1660)
                    line()
                    this.state._fsp--
                    return
                case 2:
                    pushFollow(FOLLOW_local_in_debug_directive1666)
                    local()
                    this.state._fsp--
                    return
                case 3:
                    pushFollow(FOLLOW_end_local_in_debug_directive1672)
                    end_local()
                    this.state._fsp--
                    return
                case 4:
                    pushFollow(FOLLOW_restart_local_in_debug_directive1678)
                    restart_local()
                    this.state._fsp--
                    return
                case 5:
                    pushFollow(FOLLOW_prologue_in_debug_directive1684)
                    prologue()
                    this.state._fsp--
                    return
                case 6:
                    pushFollow(FOLLOW_epilogue_in_debug_directive1690)
                    epilogue()
                    this.state._fsp--
                    return
                case 7:
                    pushFollow(FOLLOW_source_in_debug_directive1696)
                    source()
                    this.state._fsp--
                    return
                default:
                    return
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Double double_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 23, FOLLOW_DOUBLE_LITERAL_in_double_literal3863)
            return LiteralTools.parseDouble(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0.0d
        }
    }

    public final Unit end_local() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_switchStyle, FOLLOW_I_END_LOCAL_in_end_local1768)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_end_local1770)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addEndLocal(parseRegister_short(commonTree != null ? commonTree.getText() : null))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final ImmutableFieldReference enum_literal() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_ratingBarStyleIndicator, FOLLOW_I_ENCODED_ENUM_in_enum_literal4156)
            match(this.input, 2, null)
            pushFollow(FOLLOW_field_reference_in_enum_literal4158)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            if (field_reference_returnVarField_reference != null) {
                return field_reference_returnVarField_reference.fieldReference
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Unit epilogue() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_listMenuViewStyle, FOLLOW_I_EPILOGUE_in_epilogue1823)
            this.method_stack.peek().methodBuilder.addEpilogue()
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final BuilderField field() throws RecognitionException {
        Set<Annotation> set
        try {
            match(this.input, R.styleable.AppCompatTheme_tooltipFrameBackground, FOLLOW_I_FIELD_in_field480)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_field482)
            pushFollow(FOLLOW_access_or_restriction_list_in_field484)
            access_or_restriction_list_return access_or_restriction_list_returnVarAccess_or_restriction_list = access_or_restriction_list()
            this.state._fsp--
            match(this.input, 120, FOLLOW_I_FIELD_TYPE_in_field487)
            match(this.input, 2, null)
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field489)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            match(this.input, 3, null)
            pushFollow(FOLLOW_field_initial_value_in_field492)
            EncodedValue encodedValueField_initial_value = field_initial_value()
            this.state._fsp--
            if ((this.input.LA(1) == 100 ? (Char) 1 : (Char) 2) != 1) {
                set = null
            } else {
                pushFollow(FOLLOW_annotations_in_field494)
                Set<Annotation> setAnnotations = annotations()
                this.state._fsp--
                set = setAnnotations
            }
            match(this.input, 3, null)
            Int i = access_or_restriction_list_returnVarAccess_or_restriction_list != null ? access_or_restriction_list_returnVarAccess_or_restriction_list.value : 0
            Set<HiddenApiRestriction> set2 = access_or_restriction_list_returnVarAccess_or_restriction_list != null ? access_or_restriction_list_returnVarAccess_or_restriction_list.hiddenApiRestrictions : null
            if (!AccessFlags.STATIC.isSet(i) && encodedValueField_initial_value != null) {
                throw SemanticException(this.input, "Initial field values can only be specified for static fields.", new Object[0])
            }
            return this.dexBuilder.internField(this.classType, commonTree != null ? commonTree.getText() : null, nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null, i, encodedValueField_initial_value, set, set2)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final EncodedValue field_initial_value() throws RecognitionException {
        Char c
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 119) {
                c = 1
            } else {
                if (iLA != 3 && iLA != 100) {
                    throw NoViableAltException("", 9, 0, this.input)
                }
                c = 2
            }
            if (c != 1) {
                return null
            }
            match(this.input, R.styleable.AppCompatTheme_colorError, FOLLOW_I_FIELD_INITIAL_VALUE_in_field_initial_value515)
            match(this.input, 2, null)
            pushFollow(FOLLOW_literal_in_field_initial_value517)
            ImmutableEncodedValue immutableEncodedValueLiteral = literal()
            this.state._fsp--
            match(this.input, 3, null)
            return immutableEncodedValueLiteral
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final ImmutableFieldReference field_literal() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_ratingBarStyleSmall, FOLLOW_I_ENCODED_FIELD_in_field_literal4110)
            match(this.input, 2, null)
            pushFollow(FOLLOW_field_reference_in_field_literal4112)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            if (field_reference_returnVarField_reference != null) {
                return field_reference_returnVarField_reference.fieldReference
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final field_reference_return field_reference() throws RecognitionException {
        reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor
        field_reference_return field_reference_returnVar = field_reference_return()
        field_reference_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (((iLA == 8 || iLA == 16) ? (Char) 1 : (Char) 2) != 1) {
                reference_type_descriptor_returnVarReference_type_descriptor = null
            } else {
                pushFollow(FOLLOW_reference_type_descriptor_in_field_reference1431)
                reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor()
                this.state._fsp--
            }
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_field_reference1434)
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field_reference1436)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            field_reference_returnVar.fieldReference = ImmutableFieldReference((reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null) == null ? this.classType : reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null, commonTree != null ? commonTree.getText() : null, nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return field_reference_returnVar
    }

    public final List<BuilderField> fields() throws RecognitionException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        try {
            match(this.input, R.styleable.AppCompatTheme_tooltipForegroundColor, FOLLOW_I_FIELDS_in_fields405)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    if ((this.input.LA(1) == 117 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_field_in_fields414)
                    BuilderField builderFieldField = field()
                    this.state._fsp--
                    arrayListNewArrayList.add(builderFieldField)
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return arrayListNewArrayList
    }

    public final Int fixed_32bit_literal() throws RecognitionException {
        Char c
        Int i
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 11) {
                c = 7
            } else if (iLA == 12) {
                c = 4
            } else if (iLA == 15) {
                c = 6
            } else if (iLA == 39) {
                c = 5
            } else if (iLA == 95) {
                c = 1
            } else if (iLA == 189) {
                c = 2
            } else {
                if (iLA != 207) {
                    throw NoViableAltException("", 13, 0, this.input)
                }
                c = 3
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_fixed_32bit_literal835)
                    Int iInteger_literal = integer_literal()
                    this.state._fsp--
                    i = iInteger_literal
                    break
                case 2:
                    pushFollow(FOLLOW_long_literal_in_fixed_32bit_literal843)
                    Long jLong_literal = long_literal()
                    this.state._fsp--
                    LiteralTools.checkInt(jLong_literal)
                    return (Int) jLong_literal
                case 3:
                    pushFollow(FOLLOW_short_literal_in_fixed_32bit_literal851)
                    Short sShort_literal = short_literal()
                    this.state._fsp--
                    i = sShort_literal
                    break
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_fixed_32bit_literal859)
                    Byte bByte_literal = byte_literal()
                    this.state._fsp--
                    i = bByte_literal
                    break
                case 5:
                    pushFollow(FOLLOW_float_literal_in_fixed_32bit_literal867)
                    Float fFloat_literal = float_literal()
                    this.state._fsp--
                    return Float.floatToRawIntBits(fFloat_literal)
                case 6:
                    pushFollow(FOLLOW_char_literal_in_fixed_32bit_literal875)
                    Char cChar_literal = char_literal()
                    this.state._fsp--
                    i = cChar_literal
                    break
                case 7:
                    pushFollow(FOLLOW_bool_literal_in_fixed_32bit_literal883)
                    Boolean zBool_literal = bool_literal()
                    this.state._fsp--
                    i = zBool_literal
                    break
                default:
                    return 0
            }
            return i
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0
        }
    }

    public final Long fixed_64bit_literal() throws RecognitionException {
        Char c
        Int iInteger_literal
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 11) {
                c = '\b'
            } else if (iLA == 12) {
                c = 4
            } else if (iLA == 15) {
                c = 7
            } else if (iLA == 23) {
                c = 6
            } else if (iLA == 39) {
                c = 5
            } else if (iLA == 95) {
                c = 1
            } else if (iLA == 189) {
                c = 2
            } else {
                if (iLA != 207) {
                    throw NoViableAltException("", 12, 0, this.input)
                }
                c = 3
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_fixed_64bit_literal762)
                    iInteger_literal = integer_literal()
                    this.state._fsp--
                    break
                case 2:
                    pushFollow(FOLLOW_long_literal_in_fixed_64bit_literal770)
                    Long jLong_literal = long_literal()
                    this.state._fsp--
                    return jLong_literal
                case 3:
                    pushFollow(FOLLOW_short_literal_in_fixed_64bit_literal778)
                    iInteger_literal = short_literal()
                    this.state._fsp--
                    break
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_fixed_64bit_literal786)
                    iInteger_literal = byte_literal()
                    this.state._fsp--
                    break
                case 5:
                    pushFollow(FOLLOW_float_literal_in_fixed_64bit_literal794)
                    Float fFloat_literal = float_literal()
                    this.state._fsp--
                    return Float.floatToRawIntBits(fFloat_literal)
                case 6:
                    pushFollow(FOLLOW_double_literal_in_fixed_64bit_literal802)
                    Double dDouble_literal = double_literal()
                    this.state._fsp--
                    return Double.doubleToRawLongBits(dDouble_literal)
                case 7:
                    pushFollow(FOLLOW_char_literal_in_fixed_64bit_literal810)
                    iInteger_literal = char_literal()
                    this.state._fsp--
                    break
                case '\b':
                    pushFollow(FOLLOW_bool_literal_in_fixed_64bit_literal818)
                    Boolean zBool_literal = bool_literal()
                    this.state._fsp--
                    return zBool_literal ? 1L : 0L
                default:
                    return 0L
            }
            return iInteger_literal
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0L
        }
    }

    public final Number fixed_64bit_literal_number() throws RecognitionException {
        Char c
        try {
            Int i = 1
            Int iLA = this.input.LA(1)
            if (iLA == 11) {
                c = '\b'
            } else if (iLA == 12) {
                c = 4
            } else if (iLA == 15) {
                c = 7
            } else if (iLA == 23) {
                c = 6
            } else if (iLA == 39) {
                c = 5
            } else if (iLA == 95) {
                c = 1
            } else if (iLA == 189) {
                c = 2
            } else {
                if (iLA != 207) {
                    throw NoViableAltException("", 11, 0, this.input)
                }
                c = 3
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_fixed_64bit_literal_number691)
                    Int iInteger_literal = integer_literal()
                    this.state._fsp--
                    return Integer.valueOf(iInteger_literal)
                case 2:
                    pushFollow(FOLLOW_long_literal_in_fixed_64bit_literal_number699)
                    Long jLong_literal = long_literal()
                    this.state._fsp--
                    return Long.valueOf(jLong_literal)
                case 3:
                    pushFollow(FOLLOW_short_literal_in_fixed_64bit_literal_number707)
                    Short sShort_literal = short_literal()
                    this.state._fsp--
                    return Short.valueOf(sShort_literal)
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_fixed_64bit_literal_number715)
                    Byte bByte_literal = byte_literal()
                    this.state._fsp--
                    return Byte.valueOf(bByte_literal)
                case 5:
                    pushFollow(FOLLOW_float_literal_in_fixed_64bit_literal_number723)
                    Float fFloat_literal = float_literal()
                    this.state._fsp--
                    return Integer.valueOf(Float.floatToRawIntBits(fFloat_literal))
                case 6:
                    pushFollow(FOLLOW_double_literal_in_fixed_64bit_literal_number731)
                    Double dDouble_literal = double_literal()
                    this.state._fsp--
                    return Long.valueOf(Double.doubleToRawLongBits(dDouble_literal))
                case 7:
                    pushFollow(FOLLOW_char_literal_in_fixed_64bit_literal_number739)
                    Char cChar_literal = char_literal()
                    this.state._fsp--
                    return Integer.valueOf(cChar_literal)
                case '\b':
                    pushFollow(FOLLOW_bool_literal_in_fixed_64bit_literal_number747)
                    Boolean zBool_literal = bool_literal()
                    this.state._fsp--
                    if (!zBool_literal) {
                        i = 0
                    }
                    return Integer.valueOf(i)
                default:
                    return null
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Float float_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 39, FOLLOW_FLOAT_LITERAL_in_float_literal3848)
            return LiteralTools.parseFloat(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0.0f
        }
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getErrorHeader(RecognitionException recognitionException) {
        return getSourceName() + "[" + recognitionException.line + "," + recognitionException.charPositionInLine + "]"
    }

    @Override // org.antlr.runtime.tree.TreeParser, org.antlr.runtime.BaseRecognizer
    fun getErrorMessage(RecognitionException recognitionException, Array<String> strArr) {
        String message = recognitionException is SemanticException ? recognitionException.getMessage() : super.getErrorMessage(recognitionException, strArr)
        this.errMessages += getErrorHeader(recognitionException) + " " + message + "\n"
        return message
    }

    fun getErrorMessages() {
        return this.errMessages
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public Array<String> getTokenNames() {
        return tokenNames
    }

    public final header_return header() throws RecognitionException {
        String strSuper_spec
        header_return header_returnVar = header_return()
        header_returnVar.start = this.input.LT(1)
        try {
            pushFollow(FOLLOW_class_spec_in_header85)
            class_spec_return class_spec_returnVarClass_spec = class_spec()
            this.state._fsp--
            if ((this.input.LA(1) == 184 ? (Char) 1 : (Char) 2) != 1) {
                strSuper_spec = null
            } else {
                pushFollow(FOLLOW_super_spec_in_header87)
                strSuper_spec = super_spec()
                this.state._fsp--
            }
            pushFollow(FOLLOW_implements_list_in_header90)
            List<String> listImplements_list = implements_list()
            this.state._fsp--
            pushFollow(FOLLOW_source_spec_in_header92)
            String strSource_spec = source_spec()
            this.state._fsp--
            String str = class_spec_returnVarClass_spec != null ? class_spec_returnVarClass_spec.type : null
            this.classType = str
            header_returnVar.classType = str
            header_returnVar.accessFlags = class_spec_returnVarClass_spec != null ? class_spec_returnVarClass_spec.accessFlags : 0
            header_returnVar.superType = strSuper_spec
            header_returnVar.implementsList = listImplements_list
            header_returnVar.sourceSpec = strSource_spec
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return header_returnVar
    }

    public final List<String> implements_list() throws RecognitionException {
        try {
            ArrayList arrayListNewArrayList = Lists.newArrayList()
            while (true) {
                if ((this.input.LA(1) == 121 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_implements_spec_in_implements_list184)
                String strImplements_spec = implements_spec()
                this.state._fsp--
                arrayListNewArrayList.add(strImplements_spec)
            }
            if (arrayListNewArrayList.size() > 0) {
                return arrayListNewArrayList
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final String implements_spec() throws RecognitionException {
        try {
            match(this.input, 121, FOLLOW_I_IMPLEMENTS_in_implements_spec152)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_implements_spec154)
            match(this.input, 3, null)
            if (commonTree != null) {
                return commonTree.getText()
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Unit insn_array_data_directive() throws RecognitionException {
        try {
            match(this.input, 143, FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive3463)
            match(this.input, 2, null)
            match(this.input, R.styleable.AppCompatTheme_autoCompleteTextViewStyle, FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive3466)
            match(this.input, 2, null)
            pushFollow(FOLLOW_short_integral_literal_in_insn_array_data_directive3468)
            Short sShort_integral_literal = short_integral_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            pushFollow(FOLLOW_array_elements_in_insn_array_data_directive3471)
            List<Number> listArray_elements = array_elements()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderArrayPayload(sShort_integral_literal, listArray_elements))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format10t() throws RecognitionException {
        try {
            match(this.input, 144, FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2314)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 44, FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2316)
            pushFollow(FOLLOW_label_ref_in_insn_format10t2318)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction10t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), labelLabel_ref))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format10x() throws RecognitionException {
        try {
            match(this.input, 145, FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2341)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 45, FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2343)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction10x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format11n() throws RecognitionException {
        try {
            match(this.input, 146, FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2366)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 47, FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2368)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11n2370)
            pushFollow(FOLLOW_short_integral_literal_in_insn_format11n2372)
            Short sShort_integral_literal = short_integral_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Byte register_nibble = parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null)
            LiteralTools.checkNibble(sShort_integral_literal)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction11n(opcodeByName, register_nibble, sShort_integral_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format11x() throws RecognitionException {
        try {
            match(this.input, 147, FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2395)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 48, FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2397)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11x2399)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction11x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format12x() throws RecognitionException {
        try {
            match(this.input, 148, FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2422)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 49, FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2424)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x2428)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x2432)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction12x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format20bc() throws RecognitionException {
        try {
            match(this.input, 149, FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2455)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 51, FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2457)
            pushFollow(FOLLOW_verification_error_type_in_insn_format20bc2459)
            Int iVerification_error_type = verification_error_type()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_verification_error_reference_in_insn_format20bc2461)
            ImmutableReference immutableReferenceVerification_error_reference = verification_error_reference()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction20bc(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), iVerification_error_type, this.dexBuilder.internReference(immutableReferenceVerification_error_reference)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format20t() throws RecognitionException {
        try {
            match(this.input, 150, FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2484)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 52, FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2486)
            pushFollow(FOLLOW_label_ref_in_insn_format20t2488)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction20t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), labelLabel_ref))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21c_field() throws RecognitionException {
        try {
            match(this.input, 151, FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2511)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) this.input.LT(1)
            if (this.input.LA(1) < 53 || this.input.LA(1) > 54) {
                throw MismatchedSetException(null, this.input)
            }
            this.input.consume()
            this.state.errorRecovery = false
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_field2523)
            pushFollow(FOLLOW_field_reference_in_insn_format21c_field2525)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internFieldReference(field_reference_returnVarField_reference != null ? field_reference_returnVarField_reference.fieldReference : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21c_method_handle() throws RecognitionException {
        try {
            match(this.input, 152, FOLLOW_I_STATEMENT_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2548)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 55, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2553)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_handle2556)
            pushFollow(FOLLOW_method_handle_reference_in_insn_format21c_method_handle2558)
            ImmutableMethodHandleReference immutableMethodHandleReferenceMethod_handle_reference = method_handle_reference()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internMethodHandle(immutableMethodHandleReferenceMethod_handle_reference)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21c_method_type() throws RecognitionException {
        try {
            match(this.input, 153, FOLLOW_I_STATEMENT_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2581)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 56, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2586)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_type2589)
            pushFollow(FOLLOW_method_prototype_in_insn_format21c_method_type2591)
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internMethodProtoReference(immutableMethodProtoReferenceMethod_prototype)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21c_string() throws RecognitionException {
        try {
            match(this.input, 154, FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2614)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 57, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2616)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_string2618)
            pushFollow(FOLLOW_string_literal_in_insn_format21c_string2620)
            String strString_literal = string_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internStringReference(strString_literal)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21c_type() throws RecognitionException {
        try {
            match(this.input, 155, FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2643)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2645)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_type2647)
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2649)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internTypeReference(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21ih() throws RecognitionException {
        try {
            match(this.input, 156, FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2672)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2674)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21ih2676)
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21ih2678)
            Int iFixed_32bit_literal = fixed_32bit_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21ih(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), iFixed_32bit_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21lh() throws RecognitionException {
        try {
            match(this.input, 157, FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2701)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 60, FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2703)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21lh2705)
            pushFollow(FOLLOW_fixed_64bit_literal_in_insn_format21lh2707)
            Long jFixed_64bit_literal = fixed_64bit_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21lh(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), jFixed_64bit_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21s() throws RecognitionException {
        try {
            match(this.input, 158, FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2730)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 61, FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2732)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21s2734)
            pushFollow(FOLLOW_short_integral_literal_in_insn_format21s2736)
            Short sShort_integral_literal = short_integral_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21s(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), sShort_integral_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format21t() throws RecognitionException {
        try {
            match(this.input, 159, FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2759)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2761)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21t2763)
            pushFollow(FOLLOW_label_ref_in_insn_format21t2765)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction21t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), labelLabel_ref))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format22b() throws RecognitionException {
        try {
            match(this.input, 160, FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2788)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 63, FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2790)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b2794)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b2798)
            pushFollow(FOLLOW_short_integral_literal_in_insn_format22b2800)
            Short sShort_integral_literal = short_integral_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Short register_byte = parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null)
            Short register_byte2 = parseRegister_byte(commonTree3 != null ? commonTree3.getText() : null)
            LiteralTools.checkByte(sShort_integral_literal)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction22b(opcodeByName, register_byte, register_byte2, sShort_integral_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format22c_field() throws RecognitionException {
        try {
            match(this.input, 161, FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2823)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) this.input.LT(1)
            if (this.input.LA(1) < 64 || this.input.LA(1) > 65) {
                throw MismatchedSetException(null, this.input)
            }
            this.input.consume()
            this.state.errorRecovery = false
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field2837)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field2841)
            pushFollow(FOLLOW_field_reference_in_insn_format22c_field2843)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction22c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null), this.dexBuilder.internFieldReference(field_reference_returnVarField_reference != null ? field_reference_returnVarField_reference.fieldReference : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format22c_type() throws RecognitionException {
        try {
            match(this.input, 162, FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2866)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2868)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type2872)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type2876)
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2878)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction22c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null), this.dexBuilder.internTypeReference(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format22s() throws RecognitionException {
        try {
            match(this.input, 163, FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2901)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 68, FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2903)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s2907)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s2911)
            pushFollow(FOLLOW_short_integral_literal_in_insn_format22s2913)
            Short sShort_integral_literal = short_integral_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction22s(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null), sShort_integral_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format22t() throws RecognitionException {
        try {
            match(this.input, 164, FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2936)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2938)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t2942)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t2946)
            pushFollow(FOLLOW_label_ref_in_insn_format22t2948)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction22t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null), labelLabel_ref))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format22x() throws RecognitionException {
        try {
            match(this.input, 165, FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2971)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 71, FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2973)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x2977)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x2981)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction22x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), parseRegister_short(commonTree3 != null ? commonTree3.getText() : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format23x() throws RecognitionException {
        try {
            match(this.input, 166, FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x3004)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x3006)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x3010)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x3014)
            CommonTree commonTree4 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x3018)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction23x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), parseRegister_byte(commonTree3 != null ? commonTree3.getText() : null), parseRegister_byte(commonTree4 != null ? commonTree4.getText() : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format30t() throws RecognitionException {
        try {
            match(this.input, 167, FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t3041)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 73, FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t3043)
            pushFollow(FOLLOW_label_ref_in_insn_format30t3045)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction30t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), labelLabel_ref))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format31c() throws RecognitionException {
        try {
            match(this.input, 168, FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c3068)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c3070)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31c3072)
            pushFollow(FOLLOW_string_literal_in_insn_format31c3074)
            String strString_literal = string_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction31c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internStringReference(strString_literal)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format31i() throws RecognitionException {
        try {
            match(this.input, 169, FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i3097)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 75, FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i3099)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31i3101)
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format31i3103)
            Int iFixed_32bit_literal = fixed_32bit_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction31i(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), iFixed_32bit_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format31t() throws RecognitionException {
        try {
            match(this.input, 170, FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t3126)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t3128)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31t3130)
            pushFollow(FOLLOW_label_ref_in_insn_format31t3132)
            Label labelLabel_ref = label_ref()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction31t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), labelLabel_ref))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format32x() throws RecognitionException {
        try {
            match(this.input, 171, FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x3155)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x3157)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x3161)
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x3165)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction32x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_short(commonTree2 != null ? commonTree2.getText() : null), parseRegister_short(commonTree3 != null ? commonTree3.getText() : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format35c_call_site() throws RecognitionException {
        try {
            match(this.input, 172, FOLLOW_I_STATEMENT_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3193)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3195)
            pushFollow(FOLLOW_register_list_in_insn_format35c_call_site3197)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            pushFollow(FOLLOW_call_site_reference_in_insn_format35c_call_site3199)
            ImmutableCallSiteReference immutableCallSiteReferenceCall_site_reference = call_site_reference()
            this.state._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Array<Byte> bArr = register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registers : null
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction35c(opcodeByName, register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registerCount : (Byte) 0, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internCallSite(immutableCallSiteReferenceCall_site_reference)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format35c_method() throws RecognitionException {
        try {
            match(this.input, 173, FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method3222)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method3224)
            pushFollow(FOLLOW_register_list_in_insn_format35c_method3226)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            pushFollow(FOLLOW_method_reference_in_insn_format35c_method3228)
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
            this.state._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Array<Byte> bArr = register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registers : null
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction35c(opcodeByName, register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registerCount : (Byte) 0, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format35c_type() throws RecognitionException {
        try {
            match(this.input, 174, FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type3251)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type3253)
            pushFollow(FOLLOW_register_list_in_insn_format35c_type3255)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type3257)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Array<Byte> bArr = register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registers : null
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction35c(opcodeByName, register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registerCount : (Byte) 0, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internTypeReference(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format3rc_call_site() throws RecognitionException {
        try {
            match(this.input, 175, FOLLOW_I_STATEMENT_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3285)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 86, FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3287)
            pushFollow(FOLLOW_register_range_in_insn_format3rc_call_site3289)
            register_range_return register_range_returnVarRegister_range = register_range()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_call_site_reference_in_insn_format3rc_call_site3291)
            ImmutableCallSiteReference immutableCallSiteReferenceCall_site_reference = call_site_reference()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Int i = register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.startRegister : 0
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction3rc(opcodeByName, i, ((register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.endRegister : 0) - i) + 1, this.dexBuilder.internCallSite(immutableCallSiteReferenceCall_site_reference)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format3rc_method() throws RecognitionException {
        try {
            match(this.input, 176, FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method3314)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 87, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method3316)
            pushFollow(FOLLOW_register_range_in_insn_format3rc_method3318)
            register_range_return register_range_returnVarRegister_range = register_range()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_method_reference_in_insn_format3rc_method3320)
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Int i = register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.startRegister : 0
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction3rc(opcodeByName, i, ((register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.endRegister : 0) - i) + 1, this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format3rc_type() throws RecognitionException {
        try {
            match(this.input, 177, FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type3343)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type3345)
            pushFollow(FOLLOW_register_range_in_insn_format3rc_type3347)
            register_range_return register_range_returnVarRegister_range = register_range()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type3349)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Int i = register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.startRegister : 0
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction3rc(opcodeByName, i, ((register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.endRegister : 0) - i) + 1, this.dexBuilder.internTypeReference(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format45cc_method() throws RecognitionException {
        try {
            match(this.input, 178, FOLLOW_I_STATEMENT_FORMAT45cc_METHOD_in_insn_format45cc_method3372)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method3374)
            pushFollow(FOLLOW_register_list_in_insn_format45cc_method3376)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            pushFollow(FOLLOW_method_reference_in_insn_format45cc_method3378)
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
            this.state._fsp--
            pushFollow(FOLLOW_method_prototype_in_insn_format45cc_method3380)
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype()
            this.state._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Array<Byte> bArr = register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registers : null
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction45cc(opcodeByName, register_list_returnVarRegister_list != null ? register_list_returnVarRegister_list.registerCount : (Byte) 0, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference), this.dexBuilder.internMethodProtoReference(immutableMethodProtoReferenceMethod_prototype)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format4rcc_method() throws RecognitionException {
        try {
            match(this.input, 179, FOLLOW_I_STATEMENT_FORMAT4rcc_METHOD_in_insn_format4rcc_method3403)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method3405)
            pushFollow(FOLLOW_register_range_in_insn_format4rcc_method3407)
            register_range_return register_range_returnVarRegister_range = register_range()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_method_reference_in_insn_format4rcc_method3409)
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            pushFollow(FOLLOW_method_prototype_in_insn_format4rcc_method3411)
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype()
            RecognizerSharedState recognizerSharedState3 = this.state
            recognizerSharedState3._fsp--
            match(this.input, 3, null)
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)
            Int i = register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.startRegister : 0
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction4rcc(opcodeByName, i, ((register_range_returnVarRegister_range != null ? register_range_returnVarRegister_range.endRegister : 0) - i) + 1, this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference), this.dexBuilder.internMethodProtoReference(immutableMethodProtoReferenceMethod_prototype)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_format51l_type() throws RecognitionException {
        try {
            match(this.input, 180, FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type3434)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type3436)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format51l_type3438)
            pushFollow(FOLLOW_fixed_64bit_literal_in_insn_format51l_type3440)
            Long jFixed_64bit_literal = fixed_64bit_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderInstruction51l(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), jFixed_64bit_literal))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_packed_switch_directive() throws RecognitionException {
        try {
            match(this.input, 181, FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3493)
            match(this.input, 2, null)
            match(this.input, 132, FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3496)
            match(this.input, 2, null)
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3498)
            Int iFixed_32bit_literal = fixed_32bit_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            pushFollow(FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3501)
            List<Label> listPacked_switch_elements = packed_switch_elements()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderPackedSwitchPayload(iFixed_32bit_literal, listPacked_switch_elements))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit insn_sparse_switch_directive() throws RecognitionException {
        try {
            match(this.input, 182, FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3525)
            match(this.input, 2, null)
            pushFollow(FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3527)
            List<SwitchLabelElement> listSparse_switch_elements = sparse_switch_elements()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addInstruction(BuilderSparseSwitchPayload(listSparse_switch_elements))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x009c A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ad A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00be A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cf A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e0 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f1 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0102 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0113 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0124 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0135 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0146 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0157 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0168 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0179 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x018a A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x019b A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01ac A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01bd A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01ce A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01df A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01f0 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0201 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0212 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0223 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0234 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0245 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0256 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0267 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0278 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0289 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x029a A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02ab A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02bc A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02cd A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02de A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02ef A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ff A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x030f A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x031f A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x032f A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #0 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:95:0x000f, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliTreeWalker.instruction_return instruction() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 1038
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliTreeWalker.instruction():org.jf.smali.smaliTreeWalker$instruction_return")
    }

    public final Int integer_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 95, FOLLOW_INTEGER_LITERAL_in_integer_literal3788)
            return LiteralTools.parseInt(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0
        }
    }

    public final Int integral_literal() throws RecognitionException {
        Char c
        Int iInteger_literal
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 12) {
                c = 4
            } else if (iLA == 95) {
                c = 2
            } else if (iLA == 189) {
                c = 1
            } else {
                if (iLA != 207) {
                    throw NoViableAltException("", 45, 0, this.input)
                }
                c = 3
            }
            if (c == 1) {
                pushFollow(FOLLOW_long_literal_in_integral_literal3744)
                Long jLong_literal = long_literal()
                this.state._fsp--
                LiteralTools.checkInt(jLong_literal)
                return (Int) jLong_literal
            }
            if (c == 2) {
                pushFollow(FOLLOW_integer_literal_in_integral_literal3756)
                iInteger_literal = integer_literal()
                this.state._fsp--
            } else if (c == 3) {
                pushFollow(FOLLOW_short_literal_in_integral_literal3764)
                iInteger_literal = short_literal()
                this.state._fsp--
            } else {
                if (c != 4) {
                    return 0
                }
                pushFollow(FOLLOW_byte_literal_in_integral_literal3772)
                iInteger_literal = byte_literal()
                this.state._fsp--
            }
            return iInteger_literal
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0
        }
    }

    public final Unit label_def() throws RecognitionException {
        try {
            match(this.input, 122, FOLLOW_I_LABEL_in_label_def1512)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_label_def1514)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addLabel(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Label label_ref() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_label_ref1914)
            return this.method_stack.peek().methodBuilder.getLabel(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Unit line() throws RecognitionException {
        try {
            match(this.input, 123, FOLLOW_I_LINE_in_line1707)
            match(this.input, 2, null)
            pushFollow(FOLLOW_integral_literal_in_line1709)
            Int iIntegral_literal = integral_literal()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addLineNumber(iIntegral_literal)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.dexlib2.immutable.value.ImmutableEncodedValue literal() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 620
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliTreeWalker.literal():org.jf.dexlib2.immutable.value.ImmutableEncodedValue")
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007b A[Catch: all -> 0x00eb, RecognitionException -> 0x00ed, TryCatch #0 {RecognitionException -> 0x00ed, blocks: (B:2:0x0000, B:39:0x00a8, B:41:0x00b0, B:43:0x00b6, B:45:0x00ce, B:46:0x00d0, B:10:0x0036, B:21:0x0061, B:33:0x008b, B:38:0x0098, B:32:0x007b, B:18:0x0048, B:19:0x0059, B:47:0x00de, B:48:0x00ea), top: B:55:0x0000, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0098 A[Catch: all -> 0x00eb, RecognitionException -> 0x00ed, TryCatch #0 {RecognitionException -> 0x00ed, blocks: (B:2:0x0000, B:39:0x00a8, B:41:0x00b0, B:43:0x00b6, B:45:0x00ce, B:46:0x00d0, B:10:0x0036, B:21:0x0061, B:33:0x008b, B:38:0x0098, B:32:0x007b, B:18:0x0048, B:19:0x0059, B:47:0x00de, B:48:0x00ea), top: B:55:0x0000, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit local() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliTreeWalker.local():Unit")
    }

    public final Long long_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 189, FOLLOW_LONG_LITERAL_in_long_literal3803)
            return LiteralTools.parseLong(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0L
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0287 A[Catch: all -> 0x02c5, RecognitionException -> 0x02c7, TryCatch #1 {RecognitionException -> 0x02c7, blocks: (B:3:0x002a, B:5:0x005f, B:8:0x0066, B:10:0x006b, B:12:0x0085, B:14:0x0089, B:41:0x0128, B:43:0x014d, B:45:0x0151, B:53:0x018b, B:58:0x01a8, B:59:0x01b3, B:64:0x01bd, B:68:0x01c7, B:71:0x01cc, B:72:0x01dd, B:73:0x01de, B:75:0x01e4, B:77:0x01ea, B:78:0x01f3, B:79:0x01f4, B:81:0x01fe, B:102:0x0281, B:104:0x0287, B:107:0x028d, B:110:0x0293, B:112:0x0298, B:84:0x020b, B:85:0x0218, B:86:0x0219, B:87:0x0226, B:91:0x022d, B:94:0x0235, B:96:0x024b, B:98:0x0251, B:100:0x0257, B:101:0x0280, B:113:0x02a1, B:114:0x02ac, B:115:0x02ad, B:116:0x02b8, B:117:0x02b9, B:118:0x02c4, B:49:0x017d, B:28:0x00c0, B:30:0x00d1, B:32:0x00e3, B:34:0x00e7, B:40:0x010f, B:35:0x00ff, B:37:0x0109, B:39:0x010d, B:22:0x00ae, B:23:0x00b9), top: B:128:0x002a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x028d A[Catch: all -> 0x02c5, RecognitionException -> 0x02c7, TryCatch #1 {RecognitionException -> 0x02c7, blocks: (B:3:0x002a, B:5:0x005f, B:8:0x0066, B:10:0x006b, B:12:0x0085, B:14:0x0089, B:41:0x0128, B:43:0x014d, B:45:0x0151, B:53:0x018b, B:58:0x01a8, B:59:0x01b3, B:64:0x01bd, B:68:0x01c7, B:71:0x01cc, B:72:0x01dd, B:73:0x01de, B:75:0x01e4, B:77:0x01ea, B:78:0x01f3, B:79:0x01f4, B:81:0x01fe, B:102:0x0281, B:104:0x0287, B:107:0x028d, B:110:0x0293, B:112:0x0298, B:84:0x020b, B:85:0x0218, B:86:0x0219, B:87:0x0226, B:91:0x022d, B:94:0x0235, B:96:0x024b, B:98:0x0251, B:100:0x0257, B:101:0x0280, B:113:0x02a1, B:114:0x02ac, B:115:0x02ad, B:116:0x02b8, B:117:0x02b9, B:118:0x02c4, B:49:0x017d, B:28:0x00c0, B:30:0x00d1, B:32:0x00e3, B:34:0x00e7, B:40:0x010f, B:35:0x00ff, B:37:0x0109, B:39:0x010d, B:22:0x00ae, B:23:0x00b9), top: B:128:0x002a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0293 A[Catch: all -> 0x02c5, RecognitionException -> 0x02c7, TryCatch #1 {RecognitionException -> 0x02c7, blocks: (B:3:0x002a, B:5:0x005f, B:8:0x0066, B:10:0x006b, B:12:0x0085, B:14:0x0089, B:41:0x0128, B:43:0x014d, B:45:0x0151, B:53:0x018b, B:58:0x01a8, B:59:0x01b3, B:64:0x01bd, B:68:0x01c7, B:71:0x01cc, B:72:0x01dd, B:73:0x01de, B:75:0x01e4, B:77:0x01ea, B:78:0x01f3, B:79:0x01f4, B:81:0x01fe, B:102:0x0281, B:104:0x0287, B:107:0x028d, B:110:0x0293, B:112:0x0298, B:84:0x020b, B:85:0x0218, B:86:0x0219, B:87:0x0226, B:91:0x022d, B:94:0x0235, B:96:0x024b, B:98:0x0251, B:100:0x0257, B:101:0x0280, B:113:0x02a1, B:114:0x02ac, B:115:0x02ad, B:116:0x02b8, B:117:0x02b9, B:118:0x02c4, B:49:0x017d, B:28:0x00c0, B:30:0x00d1, B:32:0x00e3, B:34:0x00e7, B:40:0x010f, B:35:0x00ff, B:37:0x0109, B:39:0x010d, B:22:0x00ae, B:23:0x00b9), top: B:128:0x002a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0227  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.dexlib2.writer.builder.BuilderMethod method() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 734
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliTreeWalker.method():org.jf.dexlib2.writer.builder.BuilderMethod")
    }

    public final ImmutableMethodHandleReference method_handle_literal() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_spinnerStyle, FOLLOW_I_ENCODED_METHOD_HANDLE_in_method_handle_literal1391)
            match(this.input, 2, null)
            pushFollow(FOLLOW_method_handle_reference_in_method_handle_literal1393)
            ImmutableMethodHandleReference immutableMethodHandleReferenceMethod_handle_reference = method_handle_reference()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            return immutableMethodHandleReferenceMethod_handle_reference
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final ImmutableMethodHandleReference method_handle_reference() throws RecognitionException {
        Int iMark
        Char c
        field_reference_return field_reference_returnVarField_reference
        ImmutableReference immutableReference
        try {
            pushFollow(FOLLOW_method_handle_type_in_method_handle_reference1367)
            method_handle_type_return method_handle_type_returnVarMethod_handle_type = method_handle_type()
            this.state._fsp--
            Int iLA = this.input.LA(1)
            if (iLA == 8) {
                Int iLA2 = this.input.LA(2)
                if (iLA2 == 202) {
                    if (this.input.LA(3) == 208) {
                        Int iLA3 = this.input.LA(4)
                        if (iLA3 != 8 && iLA3 != 16 && iLA3 != 202) {
                            if (iLA3 == 128) {
                                c = 2
                            } else {
                                iMark = this.input.mark()
                                while (i < 3) {
                                    try {
                                        this.input.consume()
                                        i++
                                    } finally {
                                    }
                                }
                                throw NoViableAltException("", 19, 3, this.input)
                            }
                        }
                        c = 1
                    } else {
                        iMark = this.input.mark()
                        while (i < 2) {
                            try {
                                this.input.consume()
                                i++
                            } finally {
                            }
                        }
                        throw NoViableAltException("", 19, 4, this.input)
                    }
                } else {
                    if (iLA2 != 16) {
                        iMark = this.input.mark()
                        try {
                            this.input.consume()
                            throw NoViableAltException("", 19, 2, this.input)
                        } finally {
                        }
                    }
                    if (this.input.LA(3) == 208) {
                        Int iLA4 = this.input.LA(4)
                        if (iLA4 != 8 && iLA4 != 16 && iLA4 != 202) {
                            if (iLA4 == 128) {
                                c = 2
                            } else {
                                iMark = this.input.mark()
                                while (i < 3) {
                                    try {
                                        this.input.consume()
                                        i++
                                    } finally {
                                    }
                                }
                                throw NoViableAltException("", 19, 3, this.input)
                            }
                        }
                        c = 1
                    } else {
                        iMark = this.input.mark()
                        while (i < 2) {
                            try {
                                this.input.consume()
                                i++
                            } finally {
                            }
                        }
                        throw NoViableAltException("", 19, 5, this.input)
                    }
                }
            } else if (iLA != 16) {
                if (iLA != 208) {
                    throw NoViableAltException("", 19, 0, this.input)
                }
                Int iLA5 = this.input.LA(2)
                if (iLA5 != 8 && iLA5 != 16 && iLA5 != 202) {
                    if (iLA5 != 128) {
                        iMark = this.input.mark()
                        try {
                            this.input.consume()
                            throw NoViableAltException("", 19, 3, this.input)
                        } finally {
                        }
                    }
                    c = 2
                }
                c = 1
            } else {
                if (this.input.LA(2) != 208) {
                    iMark = this.input.mark()
                    try {
                        this.input.consume()
                        throw NoViableAltException("", 19, 1, this.input)
                    } finally {
                    }
                }
                Int iLA6 = this.input.LA(3)
                if (iLA6 != 8 && iLA6 != 16 && iLA6 != 202) {
                    if (iLA6 == 128) {
                        c = 2
                    } else {
                        iMark = this.input.mark()
                        while (i < 2) {
                            try {
                                this.input.consume()
                                i++
                            } finally {
                            }
                        }
                        throw NoViableAltException("", 19, 3, this.input)
                    }
                }
                c = 1
            }
            if (c == 1) {
                pushFollow(FOLLOW_field_reference_in_method_handle_reference1370)
                field_reference_returnVarField_reference = field_reference()
                this.state._fsp--
                immutableReference = null
            } else if (c != 2) {
                field_reference_returnVarField_reference = null
                immutableReference = null
            } else {
                pushFollow(FOLLOW_method_reference_in_method_handle_reference1374)
                ImmutableReference immutableReferenceMethod_reference = method_reference()
                this.state._fsp--
                immutableReference = immutableReferenceMethod_reference
                field_reference_returnVarField_reference = null
            }
            if ((field_reference_returnVarField_reference != null ? this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(field_reference_returnVarField_reference.start), this.input.getTreeAdaptor().getTokenStopIndex(field_reference_returnVarField_reference.start)) : null) != null) {
                immutableReference = field_reference_returnVarField_reference != null ? field_reference_returnVarField_reference.fieldReference : null
            }
            return ImmutableMethodHandleReference(method_handle_type_returnVarMethod_handle_type != null ? method_handle_type_returnVarMethod_handle_type.methodHandleType : 0, immutableReference)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final method_handle_type_return method_handle_type() throws RecognitionException {
        method_handle_type_return method_handle_type_returnVar = method_handle_type_return()
        method_handle_type_returnVar.start = this.input.LT(1)
        try {
            if (this.input.LA(1) != 82 && (this.input.LA(1) < 192 || this.input.LA(1) > 193)) {
                throw MismatchedSetException(null, this.input)
            }
            this.input.consume()
            this.state.errorRecovery = false
            method_handle_type_returnVar.methodHandleType = MethodHandleType.getMethodHandleType(this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(method_handle_type_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(method_handle_type_returnVar.start)))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return method_handle_type_returnVar
    }

    public final ImmutableMethodReference method_literal() throws RecognitionException {
        try {
            match(this.input, R.styleable.AppCompatTheme_seekBarStyle, FOLLOW_I_ENCODED_METHOD_in_method_literal4133)
            match(this.input, 2, null)
            pushFollow(FOLLOW_method_reference_in_method_literal4135)
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            return immutableMethodReferenceMethod_reference
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final method_name_and_prototype_return method_name_and_prototype() throws RecognitionException {
        method_name_and_prototype_return method_name_and_prototype_returnVar = method_name_and_prototype_return()
        method_name_and_prototype_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_method_name_and_prototype1233)
            pushFollow(FOLLOW_method_prototype_in_method_name_and_prototype1235)
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype()
            this.state._fsp--
            method_name_and_prototype_returnVar.name = commonTree != null ? commonTree.getText() : null
            method_name_and_prototype_returnVar.parameters = Lists.newArrayList()
            Int i = 0
            for (CharSequence charSequence : immutableMethodProtoReferenceMethod_prototype.getParameterTypes()) {
                Int i2 = i + 1
                method_name_and_prototype_returnVar.parameters.add(SmaliMethodParameter(i, charSequence.toString()))
                Char cCharAt = charSequence.charAt(0)
                if (cCharAt == 'D' || cCharAt == 'J') {
                    i2++
                }
                i = i2
            }
            method_name_and_prototype_returnVar.returnType = immutableMethodProtoReferenceMethod_prototype.getReturnType()
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return method_name_and_prototype_returnVar
    }

    public final ImmutableMethodProtoReference method_prototype() throws RecognitionException {
        try {
            match(this.input, 128, FOLLOW_I_METHOD_PROTOTYPE_in_method_prototype1207)
            match(this.input, 2, null)
            match(this.input, 129, FOLLOW_I_METHOD_RETURN_TYPE_in_method_prototype1210)
            match(this.input, 2, null)
            pushFollow(FOLLOW_type_descriptor_in_method_prototype1212)
            String strType_descriptor = type_descriptor()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            match(this.input, 3, null)
            pushFollow(FOLLOW_method_type_list_in_method_prototype1215)
            List<String> listMethod_type_list = method_type_list()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            match(this.input, 3, null)
            return ImmutableMethodProtoReference(listMethod_type_list, strType_descriptor)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final ImmutableMethodReference method_reference() throws RecognitionException {
        reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor
        try {
            Int iLA = this.input.LA(1)
            if (((iLA == 8 || iLA == 16) ? (Char) 1 : (Char) 2) != 1) {
                reference_type_descriptor_returnVarReference_type_descriptor = null
            } else {
                pushFollow(FOLLOW_reference_type_descriptor_in_method_reference1409)
                reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor()
                this.state._fsp--
            }
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_method_reference1412)
            pushFollow(FOLLOW_method_prototype_in_method_reference1414)
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype()
            this.state._fsp--
            return ImmutableMethodReference((reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null) == null ? this.classType : reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null, commonTree != null ? commonTree.getText() : null, immutableMethodProtoReferenceMethod_prototype.getParameterTypes(), immutableMethodProtoReferenceMethod_prototype.getReturnType())
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final List<String> method_type_list() throws RecognitionException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        while (true) {
            try {
                Int iLA = this.input.LA(1)
                if (((iLA == 8 || iLA == 16 || iLA == 202) ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_nonvoid_type_descriptor_in_method_type_list1269)
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
                this.state._fsp--
                arrayListNewArrayList.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null)
            } catch (RecognitionException e) {
                reportError(e)
                recover(this.input, e)
            }
        }
        return arrayListNewArrayList
    }

    public final List<BuilderMethod> methods() throws RecognitionException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        try {
            match(this.input, 127, FOLLOW_I_METHODS_in_methods446)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    if ((this.input.LA(1) == 126 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_method_in_methods455)
                    BuilderMethod builderMethodMethod = method()
                    this.state._fsp--
                    arrayListNewArrayList.add(builderMethodMethod)
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return arrayListNewArrayList
    }

    public final nonvoid_type_descriptor_return nonvoid_type_descriptor() throws RecognitionException {
        Char c
        nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_return()
        nonvoid_type_descriptor_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 8) {
                c = 3
            } else if (iLA == 16) {
                c = 2
            } else {
                if (iLA != 202) {
                    throw NoViableAltException("", 41, 0, this.input)
                }
                c = 1
            }
            if (c == 1) {
                match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor3598)
                nonvoid_type_descriptor_returnVar.type = this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(nonvoid_type_descriptor_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(nonvoid_type_descriptor_returnVar.start))
            } else if (c == 2) {
                match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor3606)
                nonvoid_type_descriptor_returnVar.type = this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(nonvoid_type_descriptor_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(nonvoid_type_descriptor_returnVar.start))
            } else if (c == 3) {
                pushFollow(FOLLOW_array_descriptor_in_nonvoid_type_descriptor3614)
                String strArray_descriptor = array_descriptor()
                this.state._fsp--
                nonvoid_type_descriptor_returnVar.type = strArray_descriptor
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return nonvoid_type_descriptor_returnVar
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0043 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit ordered_method_items() throws org.antlr.runtime.RecognitionException {
        /*
            r7 = this
            org.antlr.runtime.tree.TreeNodeStream r0 = r7.input     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r1 = 130(0x82, Float:1.82E-43)
            org.antlr.runtime.BitSet r2 = org.jf.smali.smaliTreeWalker.FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1887     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.match(r0, r1, r2)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            org.antlr.runtime.tree.TreeNodeStream r0 = r7.input     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r1 = 1
            Int r0 = r0.LA(r1)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r2 = 2
            if (r0 != r2) goto L88
            org.antlr.runtime.tree.TreeNodeStream r0 = r7.input     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r3 = 0
            r7.match(r0, r2, r3)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
        L19:
            r0 = 4
            org.antlr.runtime.tree.TreeNodeStream r4 = r7.input     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r4.LA(r1)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r5 = 115(0x73, Float:1.61E-43)
            r6 = 3
            if (r4 == r5) goto L40
            r5 = 116(0x74, Float:1.63E-43)
            if (r4 == r5) goto L40
            r5 = 136(0x88, Float:1.9E-43)
            if (r4 == r5) goto L40
            r5 = 140(0x8c, Float:1.96E-43)
            if (r4 == r5) goto L40
            r5 = 141(0x8d, Float:1.98E-43)
            if (r4 == r5) goto L40
            switch(r4) {
                case 122: goto L3e
                case 123: goto L40
                case 124: goto L40
                default: goto L38
            }     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
        L38:
            switch(r4) {
                case 143: goto L3c
                case 144: goto L3c
                case 145: goto L3c
                case 146: goto L3c
                case 147: goto L3c
                case 148: goto L3c
                case 149: goto L3c
                case 150: goto L3c
                case 151: goto L3c
                case 152: goto L3c
                case 153: goto L3c
                case 154: goto L3c
                case 155: goto L3c
                case 156: goto L3c
                case 157: goto L3c
                case 158: goto L3c
                case 159: goto L3c
                case 160: goto L3c
                case 161: goto L3c
                case 162: goto L3c
                case 163: goto L3c
                case 164: goto L3c
                case 165: goto L3c
                case 166: goto L3c
                case 167: goto L3c
                case 168: goto L3c
                case 169: goto L3c
                case 170: goto L3c
                case 171: goto L3c
                case 172: goto L3c
                case 173: goto L3c
                case 174: goto L3c
                case 175: goto L3c
                case 176: goto L3c
                case 177: goto L3c
                case 178: goto L3c
                case 179: goto L3c
                case 180: goto L3c
                case 181: goto L3c
                case 182: goto L3c
                default: goto L3b
            }     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
        L3b:
            goto L41
        L3c:
            r0 = 2
            goto L41
        L3e:
            r0 = 1
            goto L41
        L40:
            r0 = 3
        L41:
            if (r0 == r1) goto L6d
            if (r0 == r2) goto L5d
            if (r0 == r6) goto L4d
            org.antlr.runtime.tree.TreeNodeStream r0 = r7.input     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.match(r0, r6, r3)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            goto L88
        L4d:
            org.antlr.runtime.BitSet r0 = org.jf.smali.smaliTreeWalker.FOLLOW_debug_directive_in_ordered_method_items1898     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.pushFollow(r0)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.debug_directive()     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            org.antlr.runtime.RecognizerSharedState r0 = r7.state     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r0._fsp     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r4 - r1
            r0._fsp = r4     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            goto L19
        L5d:
            org.antlr.runtime.BitSet r0 = org.jf.smali.smaliTreeWalker.FOLLOW_instruction_in_ordered_method_items1894     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.pushFollow(r0)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.instruction()     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            org.antlr.runtime.RecognizerSharedState r0 = r7.state     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r0._fsp     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r4 - r1
            r0._fsp = r4     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            goto L19
        L6d:
            org.antlr.runtime.BitSet r0 = org.jf.smali.smaliTreeWalker.FOLLOW_label_def_in_ordered_method_items1890     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.pushFollow(r0)     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            r7.label_def()     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            org.antlr.runtime.RecognizerSharedState r0 = r7.state     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r0._fsp     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            Int r4 = r4 - r1
            r0._fsp = r4     // Catch: java.lang.Throwable -> L7d org.antlr.runtime.RecognitionException -> L7f
            goto L19
        L7d:
            r0 = move-exception
            goto L89
        L7f:
            r0 = move-exception
            r7.reportError(r0)     // Catch: java.lang.Throwable -> L7d
            org.antlr.runtime.tree.TreeNodeStream r1 = r7.input     // Catch: java.lang.Throwable -> L7d
            r7.recover(r1, r0)     // Catch: java.lang.Throwable -> L7d
        L88:
            return
        L89:
            goto L8b
        L8a:
            throw r0
        L8b:
            goto L8a
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliTreeWalker.ordered_method_items():Unit")
    }

    public final List<Label> packed_switch_elements() throws RecognitionException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        try {
            match(this.input, 131, FOLLOW_I_PACKED_SWITCH_ELEMENTS_in_packed_switch_elements950)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    if ((this.input.LA(1) == 208 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_label_ref_in_packed_switch_elements959)
                    Label labelLabel_ref = label_ref()
                    this.state._fsp--
                    arrayListNewArrayList.add(labelLabel_ref)
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return arrayListNewArrayList
    }

    public final Unit parameter(List<SmaliMethodParameter> list) throws RecognitionException {
        String strString_literal
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 133, FOLLOW_I_PARAMETER_in_parameter1636)
            match(this.input, 2, null)
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_parameter1638)
            if ((this.input.LA(1) == 211 ? (Char) 1 : (Char) 2) != 1) {
                strString_literal = null
            } else {
                pushFollow(FOLLOW_string_literal_in_parameter1640)
                strString_literal = string_literal()
                this.state._fsp--
            }
            pushFollow(FOLLOW_annotations_in_parameter1643)
            Set<Annotation> setAnnotations = annotations()
            this.state._fsp--
            match(this.input, 3, null)
            Int register_short = parseRegister_short(commonTree2 != null ? commonTree2.getText() : null)
            Int i = this.method_stack.peek().totalMethodRegisters
            Int i2 = this.method_stack.peek().methodParameterRegisters
            if (register_short >= i) {
                TreeNodeStream treeNodeStream = this.input
                Array<Object> objArr = new Object[2]
                objArr[0] = commonTree2 != null ? commonTree2.getText() : null
                objArr[1] = Integer.valueOf(i - 1)
                throw SemanticException(treeNodeStream, commonTree, "Register %s is larger than the maximum register v%d for this method", objArr)
            }
            val i3 = (register_short - (i - i2)) - (!this.method_stack.peek().isStatic ? 1 : 0)
            if (i3 < 0) {
                TreeNodeStream treeNodeStream2 = this.input
                Array<Object> objArr2 = new Object[1]
                objArr2[0] = commonTree2 != null ? commonTree2.getText() : null
                throw SemanticException(treeNodeStream2, commonTree, "Register %s is not a parameter register.", objArr2)
            }
            Int iLinearSearch = LinearSearch.linearSearch(list, SmaliMethodParameter.COMPARATOR, WithRegister(this) { // from class: org.jf.smali.smaliTreeWalker.1
                @Override // org.jf.smali.WithRegister
                fun getRegister() {
                    return i3
                }
            }, i3)
            if (iLinearSearch < 0) {
                TreeNodeStream treeNodeStream3 = this.input
                Array<Object> objArr3 = new Object[1]
                objArr3[0] = commonTree2 != null ? commonTree2.getText() : null
                throw SemanticException(treeNodeStream3, commonTree, "Register %s is the second half of a wide parameter.", objArr3)
            }
            SmaliMethodParameter smaliMethodParameter = list.get(iLinearSearch)
            smaliMethodParameter.name = strString_literal
            if (setAnnotations == null || setAnnotations.size() <= 0) {
                return
            }
            smaliMethodParameter.annotations = setAnnotations
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Unit parameters(List<SmaliMethodParameter> list) throws RecognitionException {
        try {
            match(this.input, 134, FOLLOW_I_PARAMETERS_in_parameters1617)
            if (this.input.LA(1) != 2) {
                return
            }
            match(this.input, 2, null)
            while (true) {
                if ((this.input.LA(1) == 133 ? (Char) 1 : (Char) 2) != 1) {
                    match(this.input, 3, null)
                    return
                }
                pushFollow(FOLLOW_parameter_in_parameters1620)
                parameter(list)
                this.state._fsp--
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final Short parseRegister_byte(String str) throws SemanticException, NumberFormatException {
        Int i = this.method_stack.peek().totalMethodRegisters
        Int i2 = this.method_stack.peek().methodParameterRegisters
        Int i3 = Short.parseShort(str.substring(1))
        if (str.charAt(0) == 'p') {
            i3 += i - i2
        }
        if (i3 < 512) {
            return (Short) i3
        }
        throw SemanticException(this.input, "The maximum allowed register in this context is v255", new Object[0])
    }

    public final Byte parseRegister_nibble(String str) throws SemanticException, NumberFormatException {
        Int i = this.method_stack.peek().totalMethodRegisters
        Int i2 = this.method_stack.peek().methodParameterRegisters
        Int i3 = Byte.parseByte(str.substring(1))
        if (str.charAt(0) == 'p') {
            i3 += i - i2
        }
        if (i3 < 32) {
            return (Byte) i3
        }
        throw SemanticException(this.input, "The maximum allowed register in this context is list of registers is v15", new Object[0])
    }

    public final Int parseRegister_short(String str) throws SemanticException, NumberFormatException {
        Int i = this.method_stack.peek().totalMethodRegisters
        Int i2 = this.method_stack.peek().methodParameterRegisters
        Int i3 = Integer.parseInt(str.substring(1))
        if (str.charAt(0) == 'p') {
            i3 += i - i2
        }
        if (i3 < 131072) {
            return i3
        }
        throw SemanticException(this.input, "The maximum allowed register in this context is v65535", new Object[0])
    }

    public final Unit prologue() throws RecognitionException {
        try {
            match(this.input, 136, FOLLOW_I_PROLOGUE_in_prologue1807)
            this.method_stack.peek().methodBuilder.addPrologue()
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final reference_type_descriptor_return reference_type_descriptor() throws RecognitionException {
        Char c
        reference_type_descriptor_return reference_type_descriptor_returnVar = reference_type_descriptor_return()
        reference_type_descriptor_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 16) {
                c = 1
            } else {
                if (iLA != 8) {
                    throw NoViableAltException("", 42, 0, this.input)
                }
                c = 2
            }
            if (c == 1) {
                match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor3635)
                reference_type_descriptor_returnVar.type = this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(reference_type_descriptor_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(reference_type_descriptor_returnVar.start))
            } else if (c == 2) {
                pushFollow(FOLLOW_array_descriptor_in_reference_type_descriptor3643)
                String strArray_descriptor = array_descriptor()
                this.state._fsp--
                reference_type_descriptor_returnVar.type = strArray_descriptor
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return reference_type_descriptor_returnVar
    }

    public final register_list_return register_list() throws RecognitionException {
        register_list_return register_list_returnVar = register_list_return()
        register_list_returnVar.start = this.input.LT(1)
        register_list_returnVar.registers = new Byte[5]
        register_list_returnVar.registerCount = (Byte) 0
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 138, FOLLOW_I_REGISTER_LIST_in_register_list1939)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    if ((this.input.LA(1) == 204 ? (Char) 1 : (Char) 2) != 1) {
                        match(this.input, 3, null)
                        break
                    }
                    CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_register_list1948)
                    Byte b2 = register_list_returnVar.registerCount
                    if (b2 == 5) {
                        throw SemanticException(this.input, commonTree, "A list of registers can only have a maximum of 5 registers. Use the <op>/range alternate opcode instead.", new Object[0])
                    }
                    Array<Byte> bArr = register_list_returnVar.registers
                    register_list_returnVar.registerCount = (Byte) (b2 + 1)
                    bArr[b2] = parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null)
                }
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return register_list_returnVar
    }

    public final register_range_return register_range() throws RecognitionException {
        CommonTree commonTree
        CommonTree commonTree2
        register_range_return register_range_returnVar = register_range_return()
        register_range_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree3 = (CommonTree) match(this.input, 139, FOLLOW_I_REGISTER_RANGE_in_register_range1973)
            CommonTree commonTree4 = null
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                if ((this.input.LA(1) == 204 ? (Char) 1 : (Char) 2) != 1) {
                    commonTree2 = null
                    commonTree = null
                } else {
                    commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_register_range1978)
                    commonTree = (this.input.LA(1) == 204 ? (Char) 1 : (Char) 2) != 1 ? null : (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_register_range1982)
                }
                match(this.input, 3, null)
                commonTree4 = commonTree2
            } else {
                commonTree = null
            }
            if (commonTree4 == null) {
                register_range_returnVar.startRegister = 0
                register_range_returnVar.endRegister = -1
            } else {
                Int register_short = parseRegister_short(commonTree4.getText())
                register_range_returnVar.startRegister = register_short
                if (commonTree == null) {
                    register_range_returnVar.endRegister = register_short
                } else {
                    register_range_returnVar.endRegister = parseRegister_short(commonTree.getText())
                }
                if ((register_range_returnVar.endRegister - register_range_returnVar.startRegister) + 1 < 1) {
                    throw SemanticException(this.input, commonTree3, "A register range must have the lower register listed first", new Object[0])
                }
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return register_range_returnVar
    }

    public final registers_directive_return registers_directive() throws RecognitionException {
        Char c
        registers_directive_return registers_directive_returnVar = registers_directive_return()
        registers_directive_returnVar.start = this.input.LT(1)
        try {
            registers_directive_returnVar.registers = 0
            Int iLA = this.input.LA(1)
            if (iLA == 137) {
                c = 1
            } else {
                if (iLA != 125) {
                    throw NoViableAltException("", 22, 0, this.input)
                }
                c = 2
            }
            if (c == 1) {
                match(this.input, 137, FOLLOW_I_REGISTERS_in_registers_directive1462)
                registers_directive_returnVar.isLocalsDirective = false
            } else if (c == 2) {
                match(this.input, 125, FOLLOW_I_LOCALS_in_registers_directive1474)
                registers_directive_returnVar.isLocalsDirective = true
            }
            match(this.input, 2, null)
            pushFollow(FOLLOW_short_integral_literal_in_registers_directive1492)
            Short sShort_integral_literal = short_integral_literal()
            this.state._fsp--
            registers_directive_returnVar.registers = sShort_integral_literal & 65535
            match(this.input, 3, null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return registers_directive_returnVar
    }

    public final Unit restart_local() throws RecognitionException {
        try {
            match(this.input, 140, FOLLOW_I_RESTART_LOCAL_in_restart_local1788)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_restart_local1790)
            match(this.input, 3, null)
            this.method_stack.peek().methodBuilder.addRestartLocal(parseRegister_short(commonTree != null ? commonTree.getText() : null))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    fun setApiLevel(Int i) {
        this.opcodes = Opcodes.forApi(i)
        this.apiLevel = i
    }

    fun setDexBuilder(DexBuilder dexBuilder) {
        this.dexBuilder = dexBuilder
    }

    fun setVerboseErrors(Boolean z) {
        this.verboseErrors = z
    }

    public final Short short_integral_literal() throws RecognitionException {
        Char c
        Int iInteger_literal
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 12) {
                c = 5
            } else if (iLA == 15) {
                c = 4
            } else if (iLA == 95) {
                c = 2
            } else if (iLA == 189) {
                c = 1
            } else {
                if (iLA != 207) {
                    throw NoViableAltException("", 44, 0, this.input)
                }
                c = 3
            }
            if (c == 1) {
                pushFollow(FOLLOW_long_literal_in_short_integral_literal3689)
                Long jLong_literal = long_literal()
                this.state._fsp--
                LiteralTools.checkShort(jLong_literal)
                return (Short) jLong_literal
            }
            if (c == 2) {
                pushFollow(FOLLOW_integer_literal_in_short_integral_literal3701)
                iInteger_literal = integer_literal()
                this.state._fsp--
                LiteralTools.checkShort(iInteger_literal)
            } else {
                if (c == 3) {
                    pushFollow(FOLLOW_short_literal_in_short_integral_literal3713)
                    Short sShort_literal = short_literal()
                    this.state._fsp--
                    return sShort_literal
                }
                if (c == 4) {
                    pushFollow(FOLLOW_char_literal_in_short_integral_literal3721)
                    iInteger_literal = char_literal()
                    this.state._fsp--
                } else {
                    if (c != 5) {
                        return (Short) 0
                    }
                    pushFollow(FOLLOW_byte_literal_in_short_integral_literal3729)
                    iInteger_literal = byte_literal()
                    this.state._fsp--
                }
            }
            return (Short) iInteger_literal
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return (Short) 0
        }
    }

    public final Short short_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_short_literal3818)
            return LiteralTools.parseShort(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return (Short) 0
        }
    }

    public final ClassDef smali_file() throws RecognitionException {
        try {
            match(this.input, 109, FOLLOW_I_CLASS_DEF_in_smali_file52)
            match(this.input, 2, null)
            pushFollow(FOLLOW_header_in_smali_file54)
            header_return header_returnVarHeader = header()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState._fsp--
            pushFollow(FOLLOW_methods_in_smali_file56)
            List<BuilderMethod> listMethods = methods()
            RecognizerSharedState recognizerSharedState2 = this.state
            recognizerSharedState2._fsp--
            pushFollow(FOLLOW_fields_in_smali_file58)
            List<BuilderField> listFields = fields()
            RecognizerSharedState recognizerSharedState3 = this.state
            recognizerSharedState3._fsp--
            pushFollow(FOLLOW_annotations_in_smali_file60)
            Set<Annotation> setAnnotations = annotations()
            RecognizerSharedState recognizerSharedState4 = this.state
            recognizerSharedState4._fsp--
            match(this.input, 3, null)
            return this.dexBuilder.internClassDef(header_returnVarHeader != null ? header_returnVarHeader.classType : null, header_returnVarHeader != null ? header_returnVarHeader.accessFlags : 0, header_returnVarHeader != null ? header_returnVarHeader.superType : null, header_returnVarHeader != null ? header_returnVarHeader.implementsList : null, header_returnVarHeader != null ? header_returnVarHeader.sourceSpec : null, setAnnotations, listFields, listMethods)
        } catch (Exception e) {
            if (this.verboseErrors) {
                e.printStackTrace(System.err)
            }
            reportError(SemanticException(this.input, e))
            return null
        }
    }

    public final Unit source() throws RecognitionException {
        String strString_literal
        try {
            match(this.input, 141, FOLLOW_I_SOURCE_in_source1840)
            String str = null
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                if ((this.input.LA(1) == 211 ? (Char) 1 : (Char) 2) != 1) {
                    strString_literal = null
                } else {
                    pushFollow(FOLLOW_string_literal_in_source1842)
                    strString_literal = string_literal()
                    this.state._fsp--
                }
                match(this.input, 3, null)
                str = strString_literal
            }
            this.method_stack.peek().methodBuilder.addSetSourceFile(this.dexBuilder.internNullableStringReference(str))
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
    }

    public final String source_spec() throws RecognitionException {
        String strString_literal
        RecognitionException e
        Char c
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 141) {
                c = 1
            } else {
                if (iLA != 127) {
                    throw NoViableAltException("", 3, 0, this.input)
                }
                c = 2
            }
        } catch (RecognitionException e2) {
            strString_literal = null
            e = e2
        }
        if (c != 1) {
            return null
        }
        match(this.input, 141, FOLLOW_I_SOURCE_in_source_spec213)
        match(this.input, 2, null)
        pushFollow(FOLLOW_string_literal_in_source_spec215)
        strString_literal = string_literal()
        this.state._fsp--
        try {
            match(this.input, 3, null)
        } catch (RecognitionException e3) {
            e = e3
            reportError(e)
            recover(this.input, e)
            return strString_literal
        }
        return strString_literal
    }

    public final List<SwitchLabelElement> sparse_switch_elements() throws RecognitionException {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        try {
            match(this.input, 142, FOLLOW_I_SPARSE_SWITCH_ELEMENTS_in_sparse_switch_elements994)
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null)
                while (true) {
                    Int iLA = this.input.LA(1)
                    if ((((iLA >= 11 && iLA <= 12) || iLA == 15 || iLA == 39 || iLA == 95 || iLA == 189 || iLA == 207) ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    pushFollow(FOLLOW_fixed_32bit_literal_in_sparse_switch_elements1004)
                    Int iFixed_32bit_literal = fixed_32bit_literal()
                    this.state._fsp--
                    pushFollow(FOLLOW_label_ref_in_sparse_switch_elements1006)
                    Label labelLabel_ref = label_ref()
                    this.state._fsp--
                    arrayListNewArrayList.add(SwitchLabelElement(iFixed_32bit_literal, labelLabel_ref))
                }
                match(this.input, 3, null)
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return arrayListNewArrayList
    }

    public final String string_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 211, FOLLOW_STRING_LITERAL_in_string_literal3893)
            String text = commonTree != null ? commonTree.getText() : null
            return text.substring(1, text.length() - 1)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final subannotation_return subannotation() throws RecognitionException {
        subannotation_return subannotation_returnVar = subannotation_return()
        subannotation_returnVar.start = this.input.LT(1)
        try {
            ArrayList arrayListNewArrayList = Lists.newArrayList()
            match(this.input, 183, FOLLOW_I_SUBANNOTATION_in_subannotation4050)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_subannotation4060)
            while (true) {
                if ((this.input.LA(1) == 101 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_annotation_element_in_subannotation4071)
                AnnotationElement annotationElementAnnotation_element = annotation_element()
                this.state._fsp--
                arrayListNewArrayList.add(annotationElementAnnotation_element)
            }
            match(this.input, 3, null)
            subannotation_returnVar.annotationType = commonTree != null ? commonTree.getText() : null
            subannotation_returnVar.elements = arrayListNewArrayList
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
        }
        return subannotation_returnVar
    }

    public final String super_spec() throws RecognitionException {
        try {
            match(this.input, 184, FOLLOW_I_SUPER_in_super_spec130)
            match(this.input, 2, null)
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_super_spec132)
            match(this.input, 3, null)
            if (commonTree != null) {
                return commonTree.getText()
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final String type_descriptor() throws RecognitionException {
        Char c
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 215) {
                c = 1
            } else {
                if (iLA != 8 && iLA != 16 && iLA != 202) {
                    throw NoViableAltException("", 43, 0, this.input)
                }
                c = 2
            }
            if (c == 1) {
                match(this.input, 215, FOLLOW_VOID_TYPE_in_type_descriptor3663)
                return "V"
            }
            if (c != 2) {
                return null
            }
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_type_descriptor3671)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final ImmutableReference verification_error_reference() throws RecognitionException {
        Int iMark
        Char c
        try {
            Int iLA = this.input.LA(1)
            Int i = 0
            if (iLA == 8) {
                Int iLA2 = this.input.LA(2)
                if (iLA2 == 202) {
                    if (this.input.LA(3) == 208) {
                        Int iLA3 = this.input.LA(4)
                        if (iLA3 != 8 && iLA3 != 16 && iLA3 != 202) {
                            if (iLA3 == 128) {
                                c = 3
                            } else {
                                iMark = this.input.mark()
                                while (i < 3) {
                                    try {
                                        this.input.consume()
                                        i++
                                    } finally {
                                    }
                                }
                                throw NoViableAltException("", 38, 3, this.input)
                            }
                        }
                        c = 2
                    } else {
                        iMark = this.input.mark()
                        while (i < 2) {
                            try {
                                this.input.consume()
                                i++
                            } finally {
                            }
                        }
                        throw NoViableAltException("", 38, 5, this.input)
                    }
                } else {
                    if (iLA2 != 16) {
                        iMark = this.input.mark()
                        try {
                            this.input.consume()
                            throw NoViableAltException("", 38, 2, this.input)
                        } finally {
                        }
                    }
                    if (this.input.LA(3) == 208) {
                        Int iLA4 = this.input.LA(4)
                        if (iLA4 != 8 && iLA4 != 16 && iLA4 != 202) {
                            if (iLA4 == 128) {
                                c = 3
                            } else {
                                iMark = this.input.mark()
                                while (i < 3) {
                                    try {
                                        this.input.consume()
                                        i++
                                    } finally {
                                    }
                                }
                                throw NoViableAltException("", 38, 3, this.input)
                            }
                        }
                        c = 2
                    } else {
                        iMark = this.input.mark()
                        while (i < 2) {
                            try {
                                this.input.consume()
                                i++
                            } finally {
                            }
                        }
                        throw NoViableAltException("", 38, 6, this.input)
                    }
                }
            } else if (iLA == 16) {
                Int iLA5 = this.input.LA(2)
                if (iLA5 == 3) {
                    c = 1
                } else {
                    if (iLA5 != 208) {
                        iMark = this.input.mark()
                        try {
                            this.input.consume()
                            throw NoViableAltException("", 38, 1, this.input)
                        } finally {
                        }
                    }
                    Int iLA6 = this.input.LA(3)
                    if (iLA6 != 8 && iLA6 != 16 && iLA6 != 202) {
                        if (iLA6 == 128) {
                            c = 3
                        } else {
                            iMark = this.input.mark()
                            while (i < 2) {
                                try {
                                    this.input.consume()
                                    i++
                                } finally {
                                }
                            }
                            throw NoViableAltException("", 38, 3, this.input)
                        }
                    }
                    c = 2
                }
            } else {
                if (iLA != 208) {
                    throw NoViableAltException("", 38, 0, this.input)
                }
                Int iLA7 = this.input.LA(2)
                if (iLA7 != 8 && iLA7 != 16 && iLA7 != 202) {
                    if (iLA7 != 128) {
                        iMark = this.input.mark()
                        try {
                            this.input.consume()
                            throw NoViableAltException("", 38, 3, this.input)
                        } finally {
                        }
                    }
                    c = 3
                }
                c = 2
            }
            if (c == 1) {
                CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference2005)
                return ImmutableTypeReference(commonTree != null ? commonTree.getText() : null)
            }
            if (c != 2) {
                if (c != 3) {
                    return null
                }
                pushFollow(FOLLOW_method_reference_in_verification_error_reference2025)
                ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference()
                this.state._fsp--
                return immutableMethodReferenceMethod_reference
            }
            pushFollow(FOLLOW_field_reference_in_verification_error_reference2015)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            if (field_reference_returnVarField_reference != null) {
                return field_reference_returnVarField_reference.fieldReference
            }
            return null
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return null
        }
    }

    public final Int verification_error_type() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 214, FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type2042)
            return VerificationError.getVerificationError(commonTree != null ? commonTree.getText() : null)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            return 0
        }
    }
}
