package org.jf.smali

import android.support.v4.media.session.PlaybackStateCompat
import androidx.appcompat.R
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.Stack
import org.antlr.runtime.BaseRecognizer
import org.antlr.runtime.BitSet
import org.antlr.runtime.CommonToken
import org.antlr.runtime.DFA
import org.antlr.runtime.EarlyExitException
import org.antlr.runtime.FailedPredicateException
import org.antlr.runtime.IntStream
import org.antlr.runtime.MismatchedSetException
import org.antlr.runtime.NoViableAltException
import org.antlr.runtime.Parser
import org.antlr.runtime.ParserRuleReturnScope
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.RecognizerSharedState
import org.antlr.runtime.Token
import org.antlr.runtime.TokenStream
import org.antlr.runtime.tree.CommonTree
import org.antlr.runtime.tree.CommonTreeAdaptor
import org.antlr.runtime.tree.RewriteRuleSubtreeStream
import org.antlr.runtime.tree.RewriteRuleTokenStream
import org.antlr.runtime.tree.TreeAdaptor
import org.jf.dexlib2.Opcodes

class smaliParser extends Parser {
    public static final Array<Short>[] DFA31_transition
    public static final Array<String> DFA31_transitionS
    public static final Array<Short> DFA41_accept
    public static final Array<Short> DFA41_eof
    public static final Array<Short> DFA41_eot
    public static final Array<Char> DFA41_max
    public static final Array<Char> DFA41_min
    public static final Array<Short> DFA41_special
    public static final Array<Short>[] DFA41_transition
    public static final Array<String> DFA41_transitionS
    public static final Array<Short> DFA43_accept
    public static final Array<Short> DFA43_eof
    public static final Array<Short> DFA43_eot
    public static final Array<Char> DFA43_max
    public static final Array<Char> DFA43_min
    public static final Array<Short> DFA43_special
    public static final Array<Short>[] DFA43_transition
    public static final Array<String> DFA43_transitionS
    public static final BitSet FOLLOW_ACCESS_SPEC_in_access_list1381
    public static final BitSet FOLLOW_ACCESS_SPEC_in_simple_name1895
    public static final BitSet FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2963
    public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_annotation2965
    public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_simple_name2037
    public static final BitSet FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6222
    public static final BitSet FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor2495
    public static final BitSet FOLLOW_ARROW_in_field_reference3336
    public static final BitSet FOLLOW_ARROW_in_insn_sparse_switch_directive6391
    public static final BitSet FOLLOW_ARROW_in_method_reference3306
    public static final BitSet FOLLOW_ARROW_in_type_field_method_literal3076
    public static final BitSet FOLLOW_AT_in_call_site_reference3185
    public static final BitSet FOLLOW_AT_in_method_handle_reference3233
    public static final BitSet FOLLOW_AT_in_method_handle_reference3249
    public static final BitSet FOLLOW_AT_in_method_handle_reference3265
    public static final BitSet FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2832
    public static final BitSet FOLLOW_BOOL_LITERAL_in_fixed_literal2884
    public static final BitSet FOLLOW_BOOL_LITERAL_in_literal2695
    public static final BitSet FOLLOW_BOOL_LITERAL_in_simple_name1972
    public static final BitSet FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2814
    public static final BitSet FOLLOW_BYTE_LITERAL_in_fixed_literal2860
    public static final BitSet FOLLOW_BYTE_LITERAL_in_integral_literal2786
    public static final BitSet FOLLOW_BYTE_LITERAL_in_literal2665
    public static final BitSet FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3548
    public static final BitSet FOLLOW_CATCH_DIRECTIVE_in_catch_directive3496
    public static final BitSet FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2826
    public static final BitSet FOLLOW_CHAR_LITERAL_in_fixed_literal2878
    public static final BitSet FOLLOW_CHAR_LITERAL_in_integral_literal2780
    public static final BitSet FOLLOW_CHAR_LITERAL_in_literal2683
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_annotation2967
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_class_spec1300
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1341
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor2547
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor2563
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_subannotation3011
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_super_spec1320
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_type_descriptor2525
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3478
    public static final BitSet FOLLOW_CLASS_DIRECTIVE_in_class_spec1296
    public static final BitSet FOLLOW_CLOSE_BRACE_in_array_literal2910
    public static final BitSet FOLLOW_CLOSE_BRACE_in_catch_directive3512
    public static final BitSet FOLLOW_CLOSE_BRACE_in_catchall_directive3562
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_call_site5656
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_method5698
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5782
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_type5740
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5813
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5844
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_call_site5880
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5922
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5964
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5995
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method6037
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rms_method6068
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format45cc_method6099
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format4rcc_method6147
    public static final BitSet FOLLOW_CLOSE_PAREN_in_call_site_reference3183
    public static final BitSet FOLLOW_CLOSE_PAREN_in_method_prototype2430
    public static final BitSet FOLLOW_COLON_in_field1455
    public static final BitSet FOLLOW_COLON_in_field_reference3342
    public static final BitSet FOLLOW_COLON_in_label3365
    public static final BitSet FOLLOW_COLON_in_label_ref3386
    public static final BitSet FOLLOW_COLON_in_local_directive3797
    public static final BitSet FOLLOW_COLON_in_type_field_method_literal3090
    public static final BitSet FOLLOW_COMMA_in_array_literal2900
    public static final BitSet FOLLOW_COMMA_in_call_site_reference3172
    public static final BitSet FOLLOW_COMMA_in_call_site_reference3177
    public static final BitSet FOLLOW_COMMA_in_insn_format11n4488
    public static final BitSet FOLLOW_COMMA_in_insn_format12x4558
    public static final BitSet FOLLOW_COMMA_in_insn_format20bc4596
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_field4671
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_field_odex4709
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_method_handle4753
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_method_type4805
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_string4855
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_type4893
    public static final BitSet FOLLOW_COMMA_in_insn_format21ih4931
    public static final BitSet FOLLOW_COMMA_in_insn_format21lh4969
    public static final BitSet FOLLOW_COMMA_in_insn_format21s5007
    public static final BitSet FOLLOW_COMMA_in_insn_format21t5045
    public static final BitSet FOLLOW_COMMA_in_insn_format22b5083
    public static final BitSet FOLLOW_COMMA_in_insn_format22b5087
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field5127
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field5131
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field_odex5171
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field_odex5175
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_type5221
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_type5225
    public static final BitSet FOLLOW_COMMA_in_insn_format22cs_field5265
    public static final BitSet FOLLOW_COMMA_in_insn_format22cs_field5269
    public static final BitSet FOLLOW_COMMA_in_insn_format22s5296
    public static final BitSet FOLLOW_COMMA_in_insn_format22s5300
    public static final BitSet FOLLOW_COMMA_in_insn_format22t5340
    public static final BitSet FOLLOW_COMMA_in_insn_format22t5344
    public static final BitSet FOLLOW_COMMA_in_insn_format22x5384
    public static final BitSet FOLLOW_COMMA_in_insn_format23x5422
    public static final BitSet FOLLOW_COMMA_in_insn_format23x5426
    public static final BitSet FOLLOW_COMMA_in_insn_format31c5498
    public static final BitSet FOLLOW_COMMA_in_insn_format31i5535
    public static final BitSet FOLLOW_COMMA_in_insn_format31t5573
    public static final BitSet FOLLOW_COMMA_in_insn_format32x5611
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_call_site5658
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_method5700
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_method_odex5784
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_type5742
    public static final BitSet FOLLOW_COMMA_in_insn_format35mi_method5815
    public static final BitSet FOLLOW_COMMA_in_insn_format35ms_method5846
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_call_site5882
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_method5924
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_method_odex5966
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_type5997
    public static final BitSet FOLLOW_COMMA_in_insn_format3rmi_method6039
    public static final BitSet FOLLOW_COMMA_in_insn_format3rms_method6070
    public static final BitSet FOLLOW_COMMA_in_insn_format45cc_method6101
    public static final BitSet FOLLOW_COMMA_in_insn_format45cc_method6105
    public static final BitSet FOLLOW_COMMA_in_insn_format4rcc_method6149
    public static final BitSet FOLLOW_COMMA_in_insn_format4rcc_method6153
    public static final BitSet FOLLOW_COMMA_in_insn_format51l6193
    public static final BitSet FOLLOW_COMMA_in_local_directive3785
    public static final BitSet FOLLOW_COMMA_in_local_directive3838
    public static final BitSet FOLLOW_COMMA_in_parameter_directive3610
    public static final BitSet FOLLOW_COMMA_in_register_list3405
    public static final BitSet FOLLOW_DOTDOT_in_catch_directive3506
    public static final BitSet FOLLOW_DOTDOT_in_catchall_directive3556
    public static final BitSet FOLLOW_DOTDOT_in_register_range3445
    public static final BitSet FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2626
    public static final BitSet FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1961
    public static final BitSet FOLLOW_DOUBLE_LITERAL_in_double_literal2637
    public static final BitSet FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2976
    public static final BitSet FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6243
    public static final BitSet FOLLOW_END_FIELD_DIRECTIVE_in_field1489
    public static final BitSet FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3884
    public static final BitSet FOLLOW_END_METHOD_DIRECTIVE_in_method1614
    public static final BitSet FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6308
    public static final BitSet FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3636
    public static final BitSet FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6401
    public static final BitSet FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation3016
    public static final BitSet FOLLOW_ENUM_DIRECTIVE_in_enum_literal3043
    public static final BitSet FOLLOW_EOF_in_smali_file1209
    public static final BitSet FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3955
    public static final BitSet FOLLOW_EQUAL_in_annotation_element2936
    public static final BitSet FOLLOW_EQUAL_in_field1460
    public static final BitSet FOLLOW_FIELD_DIRECTIVE_in_field1449
    public static final BitSet FOLLOW_FIELD_OFFSET_in_insn_format22cs_field5271
    public static final BitSet FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2605
    public static final BitSet FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1950
    public static final BitSet FOLLOW_FLOAT_LITERAL_in_float_literal2616
    public static final BitSet FOLLOW_HIDDENAPI_RESTRICTION_in_simple_name1906
    public static final BitSet FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1339
    public static final BitSet FOLLOW_INLINE_INDEX_in_insn_format35mi_method5817
    public static final BitSet FOLLOW_INLINE_INDEX_in_insn_format3rmi_method6041
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4403
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name2060
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4463
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2082
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4435
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name2071
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4484
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4522
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2093
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x4009
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2104
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x4003
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4592
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4635
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4705
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2126
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4667
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2115
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle4749
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_simple_name2137
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type4801
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_simple_name2148
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4851
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2159
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4889
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2170
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4927
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4965
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s5003
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t5041
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2181
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b5079
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex5167
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2203
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field5123
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2192
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type5217
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2214
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field5261
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2225
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s4030
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2236
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s4024
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t5336
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2247
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x5380
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x5418
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2258
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t5462
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5494
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i4051
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2269
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i4045
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5569
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2280
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5607
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site5650
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_simple_name2291
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5776
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2313
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_instruction_format35c_method4074
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_method_handle_reference3263
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_simple_name2324
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_instruction_format35c_method4068
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2302
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5734
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2335
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5807
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2346
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5838
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2357
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site5874
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5958
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5916
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5989
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method6031
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method6062
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method6093
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_simple_name2368
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method6141
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_simple_name2379
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l6189
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2390
    public static final BitSet FOLLOW_LINE_DIRECTIVE_in_line_directive3755
    public static final BitSet FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1840
    public static final BitSet FOLLOW_LOCAL_DIRECTIVE_in_local_directive3780
    public static final BitSet FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2796
    public static final BitSet FOLLOW_LONG_LITERAL_in_fixed_literal2848
    public static final BitSet FOLLOW_LONG_LITERAL_in_integral_literal2762
    public static final BitSet FOLLOW_LONG_LITERAL_in_literal2647
    public static final BitSet FOLLOW_MEMBER_NAME_in_member_name2411
    public static final BitSet FOLLOW_METHOD_DIRECTIVE_in_method1600
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_method_handle_reference3231
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_simple_name2048
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_method_handle_reference3247
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_simple_name2054
    public static final BitSet FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2590
    public static final BitSet FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1939
    public static final BitSet FOLLOW_NULL_LITERAL_in_literal2701
    public static final BitSet FOLLOW_NULL_LITERAL_in_local_directive3788
    public static final BitSet FOLLOW_NULL_LITERAL_in_simple_name1983
    public static final BitSet FOLLOW_OPEN_BRACE_in_array_literal2894
    public static final BitSet FOLLOW_OPEN_BRACE_in_catch_directive3500
    public static final BitSet FOLLOW_OPEN_BRACE_in_catchall_directive3550
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_call_site5652
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_method5694
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5778
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_type5736
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35mi_method5809
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35ms_method5840
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_call_site5876
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_method5918
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5960
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_type5991
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rmi_method6033
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rms_method6064
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format45cc_method6095
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format4rcc_method6143
    public static final BitSet FOLLOW_OPEN_PAREN_in_call_site_reference3168
    public static final BitSet FOLLOW_OPEN_PAREN_in_method_prototype2426
    public static final BitSet FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6289
    public static final BitSet FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3605
    public static final BitSet FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id1876
    public static final BitSet FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id_primitive_type2462
    public static final BitSet FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2579
    public static final BitSet FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1928
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor2541
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_simple_name2015
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_type_descriptor2519
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal3150
    public static final BitSet FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3934
    public static final BitSet FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1816
    public static final BitSet FOLLOW_REGISTER_in_end_local_directive3886
    public static final BitSet FOLLOW_REGISTER_in_insn_format11n4486
    public static final BitSet FOLLOW_REGISTER_in_insn_format11x4524
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x4556
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x4560
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field4669
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field_odex4707
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_handle4751
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_type4803
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_string4853
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_type4891
    public static final BitSet FOLLOW_REGISTER_in_insn_format21ih4929
    public static final BitSet FOLLOW_REGISTER_in_insn_format21lh4967
    public static final BitSet FOLLOW_REGISTER_in_insn_format21s5005
    public static final BitSet FOLLOW_REGISTER_in_insn_format21t5043
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b5081
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b5085
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field5125
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field5129
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field_odex5169
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field_odex5173
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type5219
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type5223
    public static final BitSet FOLLOW_REGISTER_in_insn_format22cs_field5263
    public static final BitSet FOLLOW_REGISTER_in_insn_format22cs_field5267
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s5294
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s5298
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t5338
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t5342
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x5382
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x5386
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x5420
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x5424
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x5428
    public static final BitSet FOLLOW_REGISTER_in_insn_format31c5496
    public static final BitSet FOLLOW_REGISTER_in_insn_format31i5533
    public static final BitSet FOLLOW_REGISTER_in_insn_format31t5571
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x5609
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x5613
    public static final BitSet FOLLOW_REGISTER_in_insn_format51l6191
    public static final BitSet FOLLOW_REGISTER_in_local_directive3782
    public static final BitSet FOLLOW_REGISTER_in_parameter_directive3607
    public static final BitSet FOLLOW_REGISTER_in_register_list3402
    public static final BitSet FOLLOW_REGISTER_in_register_list3407
    public static final BitSet FOLLOW_REGISTER_in_register_range3442
    public static final BitSet FOLLOW_REGISTER_in_register_range3449
    public static final BitSet FOLLOW_REGISTER_in_restart_local_directive3911
    public static final BitSet FOLLOW_REGISTER_in_simple_name1994
    public static final BitSet FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3909
    public static final BitSet FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2808
    public static final BitSet FOLLOW_SHORT_LITERAL_in_fixed_literal2854
    public static final BitSet FOLLOW_SHORT_LITERAL_in_integral_literal2774
    public static final BitSet FOLLOW_SHORT_LITERAL_in_literal2659
    public static final BitSet FOLLOW_SIMPLE_NAME_in_simple_name1889
    public static final BitSet FOLLOW_SOURCE_DIRECTIVE_in_source_directive3976
    public static final BitSet FOLLOW_SOURCE_DIRECTIVE_in_source_spec1360
    public static final BitSet FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6382
    public static final BitSet FOLLOW_STRING_LITERAL_in_call_site_reference3170
    public static final BitSet FOLLOW_STRING_LITERAL_in_insn_format21c_string4857
    public static final BitSet FOLLOW_STRING_LITERAL_in_insn_format31c5500
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal2689
    public static final BitSet FOLLOW_STRING_LITERAL_in_local_directive3794
    public static final BitSet FOLLOW_STRING_LITERAL_in_local_directive3842
    public static final BitSet FOLLOW_STRING_LITERAL_in_parameter_directive3612
    public static final BitSet FOLLOW_STRING_LITERAL_in_source_directive3978
    public static final BitSet FOLLOW_STRING_LITERAL_in_source_spec1362
    public static final BitSet FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation3009
    public static final BitSet FOLLOW_SUPER_DIRECTIVE_in_super_spec1318
    public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4594
    public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1917
    public static final BitSet FOLLOW_VOID_TYPE_in_local_directive3800
    public static final BitSet FOLLOW_VOID_TYPE_in_simple_name2026
    public static final BitSet FOLLOW_VOID_TYPE_in_type_descriptor2513
    public static final BitSet FOLLOW_VOID_TYPE_in_type_field_method_literal3156
    public static final BitSet FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5848
    public static final BitSet FOLLOW_VTABLE_INDEX_in_insn_format3rms_method6072
    public static final BitSet FOLLOW_access_list_in_class_spec1298
    public static final BitSet FOLLOW_access_or_restriction_in_access_or_restriction_list1416
    public static final BitSet FOLLOW_access_or_restriction_list_in_field1451
    public static final BitSet FOLLOW_access_or_restriction_list_in_method1602
    public static final BitSet FOLLOW_annotation_element_in_annotation2973
    public static final BitSet FOLLOW_annotation_element_in_subannotation3013
    public static final BitSet FOLLOW_annotation_in_field1475
    public static final BitSet FOLLOW_annotation_in_parameter_directive3623
    public static final BitSet FOLLOW_annotation_in_smali_file1198
    public static final BitSet FOLLOW_annotation_in_statements_and_directives1699
    public static final BitSet FOLLOW_array_descriptor_in_nonvoid_type_descriptor2553
    public static final BitSet FOLLOW_array_descriptor_in_reference_type_descriptor2569
    public static final BitSet FOLLOW_array_descriptor_in_type_descriptor2531
    public static final BitSet FOLLOW_array_literal_in_literal2707
    public static final BitSet FOLLOW_call_site_reference_in_insn_format35c_call_site5660
    public static final BitSet FOLLOW_call_site_reference_in_insn_format3rc_call_site5884
    public static final BitSet FOLLOW_catch_directive_in_statements_and_directives1675
    public static final BitSet FOLLOW_catchall_directive_in_statements_and_directives1683
    public static final BitSet FOLLOW_class_spec_in_smali_file1150
    public static final BitSet FOLLOW_debug_directive_in_ordered_method_item1796
    public static final BitSet FOLLOW_double_literal_in_fixed_literal2872
    public static final BitSet FOLLOW_double_literal_in_literal2677
    public static final BitSet FOLLOW_end_local_directive_in_debug_directive3721
    public static final BitSet FOLLOW_enum_literal_in_literal2725
    public static final BitSet FOLLOW_epilogue_directive_in_debug_directive3739
    public static final BitSet FOLLOW_field_in_smali_file1192
    public static final BitSet FOLLOW_field_reference_in_enum_literal3045
    public static final BitSet FOLLOW_field_reference_in_insn_format21c_field4673
    public static final BitSet FOLLOW_field_reference_in_insn_format21c_field_odex4711
    public static final BitSet FOLLOW_field_reference_in_insn_format22c_field5133
    public static final BitSet FOLLOW_field_reference_in_insn_format22c_field_odex5177
    public static final BitSet FOLLOW_field_reference_in_method_handle_reference3235
    public static final BitSet FOLLOW_field_reference_in_verification_error_reference3482
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21ih4933
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21lh4971
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format31i5537
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive6295
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive6389
    public static final BitSet FOLLOW_fixed_literal_in_insn_array_data_directive6240
    public static final BitSet FOLLOW_fixed_literal_in_insn_format51l6195
    public static final BitSet FOLLOW_float_literal_in_fixed_32bit_literal2820
    public static final BitSet FOLLOW_float_literal_in_fixed_literal2866
    public static final BitSet FOLLOW_float_literal_in_literal2671
    public static final BitSet FOLLOW_implements_spec_in_smali_file1169
    public static final BitSet FOLLOW_insn_array_data_directive_in_instruction4371
    public static final BitSet FOLLOW_insn_format10t_in_instruction4089
    public static final BitSet FOLLOW_insn_format10x_in_instruction4095
    public static final BitSet FOLLOW_insn_format10x_odex_in_instruction4101
    public static final BitSet FOLLOW_insn_format11n_in_instruction4107
    public static final BitSet FOLLOW_insn_format11x_in_instruction4113
    public static final BitSet FOLLOW_insn_format12x_in_instruction4119
    public static final BitSet FOLLOW_insn_format20bc_in_instruction4125
    public static final BitSet FOLLOW_insn_format20t_in_instruction4131
    public static final BitSet FOLLOW_insn_format21c_field_in_instruction4137
    public static final BitSet FOLLOW_insn_format21c_field_odex_in_instruction4143
    public static final BitSet FOLLOW_insn_format21c_method_handle_in_instruction4149
    public static final BitSet FOLLOW_insn_format21c_method_type_in_instruction4155
    public static final BitSet FOLLOW_insn_format21c_string_in_instruction4161
    public static final BitSet FOLLOW_insn_format21c_type_in_instruction4167
    public static final BitSet FOLLOW_insn_format21ih_in_instruction4173
    public static final BitSet FOLLOW_insn_format21lh_in_instruction4179
    public static final BitSet FOLLOW_insn_format21s_in_instruction4185
    public static final BitSet FOLLOW_insn_format21t_in_instruction4191
    public static final BitSet FOLLOW_insn_format22b_in_instruction4197
    public static final BitSet FOLLOW_insn_format22c_field_in_instruction4203
    public static final BitSet FOLLOW_insn_format22c_field_odex_in_instruction4209
    public static final BitSet FOLLOW_insn_format22c_type_in_instruction4215
    public static final BitSet FOLLOW_insn_format22cs_field_in_instruction4221
    public static final BitSet FOLLOW_insn_format22s_in_instruction4227
    public static final BitSet FOLLOW_insn_format22t_in_instruction4233
    public static final BitSet FOLLOW_insn_format22x_in_instruction4239
    public static final BitSet FOLLOW_insn_format23x_in_instruction4245
    public static final BitSet FOLLOW_insn_format30t_in_instruction4251
    public static final BitSet FOLLOW_insn_format31c_in_instruction4257
    public static final BitSet FOLLOW_insn_format31i_in_instruction4263
    public static final BitSet FOLLOW_insn_format31t_in_instruction4269
    public static final BitSet FOLLOW_insn_format32x_in_instruction4275
    public static final BitSet FOLLOW_insn_format35c_call_site_in_instruction4281
    public static final BitSet FOLLOW_insn_format35c_method_in_instruction4287
    public static final BitSet FOLLOW_insn_format35c_method_odex_in_instruction4299
    public static final BitSet FOLLOW_insn_format35c_type_in_instruction4293
    public static final BitSet FOLLOW_insn_format35mi_method_in_instruction4305
    public static final BitSet FOLLOW_insn_format35ms_method_in_instruction4311
    public static final BitSet FOLLOW_insn_format3rc_call_site_in_instruction4317
    public static final BitSet FOLLOW_insn_format3rc_method_in_instruction4323
    public static final BitSet FOLLOW_insn_format3rc_method_odex_in_instruction4329
    public static final BitSet FOLLOW_insn_format3rc_type_in_instruction4335
    public static final BitSet FOLLOW_insn_format3rmi_method_in_instruction4341
    public static final BitSet FOLLOW_insn_format3rms_method_in_instruction4347
    public static final BitSet FOLLOW_insn_format45cc_method_in_instruction4353
    public static final BitSet FOLLOW_insn_format4rcc_method_in_instruction4359
    public static final BitSet FOLLOW_insn_format51l_in_instruction4365
    public static final BitSet FOLLOW_insn_packed_switch_directive_in_instruction4377
    public static final BitSet FOLLOW_insn_sparse_switch_directive_in_instruction4383
    public static final BitSet FOLLOW_instruction_format12x_in_insn_format12x4554
    public static final BitSet FOLLOW_instruction_format22s_in_insn_format22s5292
    public static final BitSet FOLLOW_instruction_format31i_in_insn_format31i5531
    public static final BitSet FOLLOW_instruction_format35c_method_in_insn_format35c_method5692
    public static final BitSet FOLLOW_instruction_in_ordered_method_item1790
    public static final BitSet FOLLOW_integer_literal_in_fixed_32bit_literal2802
    public static final BitSet FOLLOW_integer_literal_in_fixed_literal2842
    public static final BitSet FOLLOW_integer_literal_in_integral_literal2768
    public static final BitSet FOLLOW_integer_literal_in_literal2653
    public static final BitSet FOLLOW_integer_literal_in_parsed_integer_literal2750
    public static final BitSet FOLLOW_integral_literal_in_insn_format11n4490
    public static final BitSet FOLLOW_integral_literal_in_insn_format21s5009
    public static final BitSet FOLLOW_integral_literal_in_insn_format22b5089
    public static final BitSet FOLLOW_integral_literal_in_insn_format22s5302
    public static final BitSet FOLLOW_integral_literal_in_line_directive3757
    public static final BitSet FOLLOW_integral_literal_in_registers_directive1820
    public static final BitSet FOLLOW_integral_literal_in_registers_directive1844
    public static final BitSet FOLLOW_label_in_ordered_method_item1784
    public static final BitSet FOLLOW_label_ref_in_catch_directive3504
    public static final BitSet FOLLOW_label_ref_in_catch_directive3510
    public static final BitSet FOLLOW_label_ref_in_catch_directive3516
    public static final BitSet FOLLOW_label_ref_in_catchall_directive3554
    public static final BitSet FOLLOW_label_ref_in_catchall_directive3560
    public static final BitSet FOLLOW_label_ref_in_catchall_directive3566
    public static final BitSet FOLLOW_label_ref_in_insn_format10t4405
    public static final BitSet FOLLOW_label_ref_in_insn_format20t4637
    public static final BitSet FOLLOW_label_ref_in_insn_format21t5047
    public static final BitSet FOLLOW_label_ref_in_insn_format22t5346
    public static final BitSet FOLLOW_label_ref_in_insn_format30t5464
    public static final BitSet FOLLOW_label_ref_in_insn_format31t5575
    public static final BitSet FOLLOW_label_ref_in_insn_packed_switch_directive6301
    public static final BitSet FOLLOW_label_ref_in_insn_sparse_switch_directive6393
    public static final BitSet FOLLOW_line_directive_in_debug_directive3709
    public static final BitSet FOLLOW_literal_in_annotation_element2938
    public static final BitSet FOLLOW_literal_in_array_literal2897
    public static final BitSet FOLLOW_literal_in_array_literal2902
    public static final BitSet FOLLOW_literal_in_call_site_reference3179
    public static final BitSet FOLLOW_literal_in_field1462
    public static final BitSet FOLLOW_local_directive_in_debug_directive3715
    public static final BitSet FOLLOW_member_name_in_field1453
    public static final BitSet FOLLOW_member_name_in_field_reference3340
    public static final BitSet FOLLOW_member_name_in_method1604
    public static final BitSet FOLLOW_member_name_in_method_reference3310
    public static final BitSet FOLLOW_member_name_in_type_field_method_literal3088
    public static final BitSet FOLLOW_member_name_in_type_field_method_literal3115
    public static final BitSet FOLLOW_method_handle_literal_in_literal2731
    public static final BitSet FOLLOW_method_handle_reference_in_insn_format21c_method_handle4755
    public static final BitSet FOLLOW_method_handle_reference_in_method_handle_literal3283
    public static final BitSet FOLLOW_method_in_smali_file1186
    public static final BitSet FOLLOW_method_prototype_in_call_site_reference3174
    public static final BitSet FOLLOW_method_prototype_in_insn_format21c_method_type4807
    public static final BitSet FOLLOW_method_prototype_in_insn_format45cc_method6107
    public static final BitSet FOLLOW_method_prototype_in_insn_format4rcc_method6155
    public static final BitSet FOLLOW_method_prototype_in_literal2737
    public static final BitSet FOLLOW_method_prototype_in_method1606
    public static final BitSet FOLLOW_method_prototype_in_method_reference3312
    public static final BitSet FOLLOW_method_prototype_in_type_field_method_literal3117
    public static final BitSet FOLLOW_method_reference_in_call_site_reference3187
    public static final BitSet FOLLOW_method_reference_in_insn_format35c_method5702
    public static final BitSet FOLLOW_method_reference_in_insn_format35c_method_odex5786
    public static final BitSet FOLLOW_method_reference_in_insn_format3rc_method5926
    public static final BitSet FOLLOW_method_reference_in_insn_format3rc_method_odex5968
    public static final BitSet FOLLOW_method_reference_in_insn_format45cc_method6103
    public static final BitSet FOLLOW_method_reference_in_insn_format4rcc_method6151
    public static final BitSet FOLLOW_method_reference_in_method_handle_reference3251
    public static final BitSet FOLLOW_method_reference_in_method_handle_reference3267
    public static final BitSet FOLLOW_method_reference_in_verification_error_reference3486
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_catch_directive3498
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field1457
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field_reference3344
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4895
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type5227
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5744
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5999
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_local_directive3804
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_param_list2484
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal3092
    public static final BitSet FOLLOW_ordered_method_item_in_statements_and_directives1659
    public static final BitSet FOLLOW_param_list_in_method_prototype2428
    public static final BitSet FOLLOW_param_list_or_id_in_simple_name2005
    public static final BitSet FOLLOW_param_list_or_id_primitive_type_in_param_list2477
    public static final BitSet FOLLOW_parameter_directive_in_statements_and_directives1691
    public static final BitSet FOLLOW_parsed_integer_literal_in_insn_array_data_directive6228
    public static final BitSet FOLLOW_prologue_directive_in_debug_directive3733
    public static final BitSet FOLLOW_reference_type_descriptor_in_field_reference3334
    public static final BitSet FOLLOW_reference_type_descriptor_in_method_reference3304
    public static final BitSet FOLLOW_reference_type_descriptor_in_type_field_method_literal3065
    public static final BitSet FOLLOW_reference_type_descriptor_in_type_field_method_literal3074
    public static final BitSet FOLLOW_register_list_in_insn_format35c_call_site5654
    public static final BitSet FOLLOW_register_list_in_insn_format35c_method5696
    public static final BitSet FOLLOW_register_list_in_insn_format35c_method_odex5780
    public static final BitSet FOLLOW_register_list_in_insn_format35c_type5738
    public static final BitSet FOLLOW_register_list_in_insn_format35mi_method5811
    public static final BitSet FOLLOW_register_list_in_insn_format35ms_method5842
    public static final BitSet FOLLOW_register_list_in_insn_format3rc_method_odex5962
    public static final BitSet FOLLOW_register_list_in_insn_format45cc_method6097
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_call_site5878
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_method5920
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_type5993
    public static final BitSet FOLLOW_register_range_in_insn_format3rmi_method6035
    public static final BitSet FOLLOW_register_range_in_insn_format3rms_method6066
    public static final BitSet FOLLOW_register_range_in_insn_format4rcc_method6145
    public static final BitSet FOLLOW_registers_directive_in_statements_and_directives1667
    public static final BitSet FOLLOW_restart_local_directive_in_debug_directive3727
    public static final BitSet FOLLOW_simple_name_in_annotation_element2934
    public static final BitSet FOLLOW_simple_name_in_call_site_reference3166
    public static final BitSet FOLLOW_simple_name_in_label3367
    public static final BitSet FOLLOW_simple_name_in_label_ref3388
    public static final BitSet FOLLOW_simple_name_in_member_name2405
    public static final BitSet FOLLOW_source_directive_in_debug_directive3745
    public static final BitSet FOLLOW_source_spec_in_smali_file1178
    public static final BitSet FOLLOW_statements_and_directives_in_method1608
    public static final BitSet FOLLOW_subannotation_in_literal2713
    public static final BitSet FOLLOW_super_spec_in_smali_file1161
    public static final BitSet FOLLOW_type_descriptor_in_method_prototype2432
    public static final BitSet FOLLOW_type_field_method_literal_in_literal2719
    public static final BitSet FOLLOW_verification_error_reference_in_insn_format20bc4598
    public TreeAdaptor adaptor
    public Boolean allowOdex
    public Int apiLevel
    public DFA31 dfa31
    public DFA41 dfa41
    public DFA43 dfa43
    public String errMessages
    public Opcodes opcodes
    public Stack<smali_file_scope> smali_file_stack
    public Stack<statements_and_directives_scope> statements_and_directives_stack
    public Boolean verboseErrors
    public static final Array<String> tokenNames = {"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACCESS_SPEC", "ANNOTATION_DIRECTIVE", "ANNOTATION_VISIBILITY", "ARRAY_DATA_DIRECTIVE", "ARRAY_TYPE_PREFIX", "ARROW", "AT", "BOOL_LITERAL", "BYTE_LITERAL", "CATCHALL_DIRECTIVE", "CATCH_DIRECTIVE", "CHAR_LITERAL", "CLASS_DESCRIPTOR", "CLASS_DIRECTIVE", "CLOSE_BRACE", "CLOSE_PAREN", "COLON", "COMMA", "DOTDOT", "DOUBLE_LITERAL", "DOUBLE_LITERAL_OR_ID", "END_ANNOTATION_DIRECTIVE", "END_ARRAY_DATA_DIRECTIVE", "END_FIELD_DIRECTIVE", "END_LOCAL_DIRECTIVE", "END_METHOD_DIRECTIVE", "END_PACKED_SWITCH_DIRECTIVE", "END_PARAMETER_DIRECTIVE", "END_SPARSE_SWITCH_DIRECTIVE", "END_SUBANNOTATION_DIRECTIVE", "ENUM_DIRECTIVE", "EPILOGUE_DIRECTIVE", "EQUAL", "FIELD_DIRECTIVE", "FIELD_OFFSET", "FLOAT_LITERAL", "FLOAT_LITERAL_OR_ID", "HIDDENAPI_RESTRICTION", "IMPLEMENTS_DIRECTIVE", "INLINE_INDEX", "INSTRUCTION_FORMAT10t", "INSTRUCTION_FORMAT10x", "INSTRUCTION_FORMAT10x_ODEX", "INSTRUCTION_FORMAT11n", "INSTRUCTION_FORMAT11x", "INSTRUCTION_FORMAT12x", "INSTRUCTION_FORMAT12x_OR_ID", "INSTRUCTION_FORMAT20bc", "INSTRUCTION_FORMAT20t", "INSTRUCTION_FORMAT21c_FIELD", "INSTRUCTION_FORMAT21c_FIELD_ODEX", "INSTRUCTION_FORMAT21c_METHOD_HANDLE", "INSTRUCTION_FORMAT21c_METHOD_TYPE", "INSTRUCTION_FORMAT21c_STRING", "INSTRUCTION_FORMAT21c_TYPE", "INSTRUCTION_FORMAT21ih", "INSTRUCTION_FORMAT21lh", "INSTRUCTION_FORMAT21s", "INSTRUCTION_FORMAT21t", "INSTRUCTION_FORMAT22b", "INSTRUCTION_FORMAT22c_FIELD", "INSTRUCTION_FORMAT22c_FIELD_ODEX", "INSTRUCTION_FORMAT22c_TYPE", "INSTRUCTION_FORMAT22cs_FIELD", "INSTRUCTION_FORMAT22s", "INSTRUCTION_FORMAT22s_OR_ID", "INSTRUCTION_FORMAT22t", "INSTRUCTION_FORMAT22x", "INSTRUCTION_FORMAT23x", "INSTRUCTION_FORMAT30t", "INSTRUCTION_FORMAT31c", "INSTRUCTION_FORMAT31i", "INSTRUCTION_FORMAT31i_OR_ID", "INSTRUCTION_FORMAT31t", "INSTRUCTION_FORMAT32x", "INSTRUCTION_FORMAT35c_CALL_SITE", "INSTRUCTION_FORMAT35c_METHOD", "INSTRUCTION_FORMAT35c_METHOD_ODEX", "INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE", "INSTRUCTION_FORMAT35c_TYPE", "INSTRUCTION_FORMAT35mi_METHOD", "INSTRUCTION_FORMAT35ms_METHOD", "INSTRUCTION_FORMAT3rc_CALL_SITE", "INSTRUCTION_FORMAT3rc_METHOD", "INSTRUCTION_FORMAT3rc_METHOD_ODEX", "INSTRUCTION_FORMAT3rc_TYPE", "INSTRUCTION_FORMAT3rmi_METHOD", "INSTRUCTION_FORMAT3rms_METHOD", "INSTRUCTION_FORMAT45cc_METHOD", "INSTRUCTION_FORMAT4rcc_METHOD", "INSTRUCTION_FORMAT51l", "INTEGER_LITERAL", "INVALID_TOKEN", "I_ACCESS_LIST", "I_ACCESS_OR_RESTRICTION_LIST", "I_ANNOTATION", "I_ANNOTATIONS", "I_ANNOTATION_ELEMENT", "I_ARRAY_ELEMENTS", "I_ARRAY_ELEMENT_SIZE", "I_CALL_SITE_EXTRA_ARGUMENTS", "I_CALL_SITE_REFERENCE", "I_CATCH", "I_CATCHALL", "I_CATCHES", "I_CLASS_DEF", "I_ENCODED_ARRAY", "I_ENCODED_ENUM", "I_ENCODED_FIELD", "I_ENCODED_METHOD", "I_ENCODED_METHOD_HANDLE", "I_END_LOCAL", "I_EPILOGUE", "I_FIELD", "I_FIELDS", "I_FIELD_INITIAL_VALUE", "I_FIELD_TYPE", "I_IMPLEMENTS", "I_LABEL", "I_LINE", "I_LOCAL", "I_LOCALS", "I_METHOD", "I_METHODS", "I_METHOD_PROTOTYPE", "I_METHOD_RETURN_TYPE", "I_ORDERED_METHOD_ITEMS", "I_PACKED_SWITCH_ELEMENTS", "I_PACKED_SWITCH_START_KEY", "I_PARAMETER", "I_PARAMETERS", "I_PARAMETER_NOT_SPECIFIED", "I_PROLOGUE", "I_REGISTERS", "I_REGISTER_LIST", "I_REGISTER_RANGE", "I_RESTART_LOCAL", "I_SOURCE", "I_SPARSE_SWITCH_ELEMENTS", "I_STATEMENT_ARRAY_DATA", "I_STATEMENT_FORMAT10t", "I_STATEMENT_FORMAT10x", "I_STATEMENT_FORMAT11n", "I_STATEMENT_FORMAT11x", "I_STATEMENT_FORMAT12x", "I_STATEMENT_FORMAT20bc", "I_STATEMENT_FORMAT20t", "I_STATEMENT_FORMAT21c_FIELD", "I_STATEMENT_FORMAT21c_METHOD_HANDLE", "I_STATEMENT_FORMAT21c_METHOD_TYPE", "I_STATEMENT_FORMAT21c_STRING", "I_STATEMENT_FORMAT21c_TYPE", "I_STATEMENT_FORMAT21ih", "I_STATEMENT_FORMAT21lh", "I_STATEMENT_FORMAT21s", "I_STATEMENT_FORMAT21t", "I_STATEMENT_FORMAT22b", "I_STATEMENT_FORMAT22c_FIELD", "I_STATEMENT_FORMAT22c_TYPE", "I_STATEMENT_FORMAT22s", "I_STATEMENT_FORMAT22t", "I_STATEMENT_FORMAT22x", "I_STATEMENT_FORMAT23x", "I_STATEMENT_FORMAT30t", "I_STATEMENT_FORMAT31c", "I_STATEMENT_FORMAT31i", "I_STATEMENT_FORMAT31t", "I_STATEMENT_FORMAT32x", "I_STATEMENT_FORMAT35c_CALL_SITE", "I_STATEMENT_FORMAT35c_METHOD", "I_STATEMENT_FORMAT35c_TYPE", "I_STATEMENT_FORMAT3rc_CALL_SITE", "I_STATEMENT_FORMAT3rc_METHOD", "I_STATEMENT_FORMAT3rc_TYPE", "I_STATEMENT_FORMAT45cc_METHOD", "I_STATEMENT_FORMAT4rcc_METHOD", "I_STATEMENT_FORMAT51l", "I_STATEMENT_PACKED_SWITCH", "I_STATEMENT_SPARSE_SWITCH", "I_SUBANNOTATION", "I_SUPER", "LINE_COMMENT", "LINE_DIRECTIVE", "LOCALS_DIRECTIVE", "LOCAL_DIRECTIVE", "LONG_LITERAL", "MEMBER_NAME", "METHOD_DIRECTIVE", "METHOD_HANDLE_TYPE_FIELD", "METHOD_HANDLE_TYPE_METHOD", "NEGATIVE_INTEGER_LITERAL", "NULL_LITERAL", "OPEN_BRACE", "OPEN_PAREN", "PACKED_SWITCH_DIRECTIVE", "PARAMETER_DIRECTIVE", "PARAM_LIST_OR_ID_PRIMITIVE_TYPE", "POSITIVE_INTEGER_LITERAL", "PRIMITIVE_TYPE", "PROLOGUE_DIRECTIVE", "REGISTER", "REGISTERS_DIRECTIVE", "RESTART_LOCAL_DIRECTIVE", "SHORT_LITERAL", "SIMPLE_NAME", "SOURCE_DIRECTIVE", "SPARSE_SWITCH_DIRECTIVE", "STRING_LITERAL", "SUBANNOTATION_DIRECTIVE", "SUPER_DIRECTIVE", "VERIFICATION_ERROR_TYPE", "VOID_TYPE", "VTABLE_INDEX", "WHITE_SPACE"}
    public static final Array<Short> DFA31_eot = DFA.unpackEncodedString("4\uffff")
    public static final Array<Short> DFA31_eof = DFA.unpackEncodedString("4\uffff")
    public static final Array<Char> DFA31_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u00041\u0014\u0002\uffff")
    public static final Array<Char> DFA31_max = DFA.unpackEncodedStringToUnsignedChars("\u0001×\u000bÅ\u0001È%Å\u0002\uffff")
    public static final Array<Short> DFA31_accept = DFA.unpackEncodedString("2\uffff\u0001\u0001\u0001\u0002")
    public static final Array<Short> DFA31_special = DFA.unpackEncodedString("4\uffff}>")

    class DFA31 extends DFA {
        constructor(smaliParser smaliparser, BaseRecognizer baseRecognizer) {
            this.recognizer = baseRecognizer
            this.decisionNumber = 31
            this.eot = smaliParser.DFA31_eot
            this.eof = smaliParser.DFA31_eof
            this.min = smaliParser.DFA31_min
            this.max = smaliParser.DFA31_max
            this.accept = smaliParser.DFA31_accept
            this.special = smaliParser.DFA31_special
            this.transition = smaliParser.DFA31_transition
        }

        @Override // org.antlr.runtime.DFA
        fun getDescription() {
            return "725:7: ( member_name COLON nonvoid_type_descriptor -> ^( I_ENCODED_FIELD ( reference_type_descriptor )? member_name nonvoid_type_descriptor ) | member_name method_prototype -> ^( I_ENCODED_METHOD ( reference_type_descriptor )? member_name method_prototype ) )"
        }
    }

    class DFA41 extends DFA {
        constructor(smaliParser smaliparser, BaseRecognizer baseRecognizer) {
            this.recognizer = baseRecognizer
            this.decisionNumber = 41
            this.eot = smaliParser.DFA41_eot
            this.eof = smaliParser.DFA41_eof
            this.min = smaliParser.DFA41_min
            this.max = smaliParser.DFA41_max
            this.accept = smaliParser.DFA41_accept
            this.special = smaliParser.DFA41_special
            this.transition = smaliParser.DFA41_transition
        }

        @Override // org.antlr.runtime.DFA
        fun getDescription() {
            return "767:1: verification_error_reference : ( CLASS_DESCRIPTOR | field_reference | method_reference );"
        }
    }

    class DFA43 extends DFA {
        constructor(smaliParser smaliparser, BaseRecognizer baseRecognizer) {
            this.recognizer = baseRecognizer
            this.decisionNumber = 43
            this.eot = smaliParser.DFA43_eot
            this.eof = smaliParser.DFA43_eof
            this.min = smaliParser.DFA43_min
            this.max = smaliParser.DFA43_max
            this.accept = smaliParser.DFA43_accept
            this.special = smaliParser.DFA43_special
            this.transition = smaliParser.DFA43_transition
        }

        @Override // org.antlr.runtime.DFA
        fun getDescription() {
            return "()* loopback of 785:5: ({...}? annotation )*"
        }

        @Override // org.antlr.runtime.DFA
        fun specialStateTransition(Int i, IntStream intStream) throws NoViableAltException {
            TokenStream tokenStream = (TokenStream) intStream
            if (i == 0) {
                tokenStream.LA(1)
                Int iIndex = tokenStream.index()
                tokenStream.rewind()
                Int i2 = tokenStream.LA(1) == 5 ? 71 : 1
                tokenStream.seek(iIndex)
                if (i2 >= 0) {
                    return i2
                }
            }
            NoViableAltException noViableAltException = NoViableAltException(getDescription(), 43, i, tokenStream)
            error(noViableAltException)
            throw noViableAltException
        }
    }

    public static class access_list_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class access_or_restriction_list_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class access_or_restriction_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class annotation_element_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class annotation_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class array_descriptor_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class array_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class call_site_reference_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class catch_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class catchall_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class class_spec_return extends ParserRuleReturnScope {
        public String className
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class debug_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class double_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class end_local_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class enum_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class epilogue_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class field_reference_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class field_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class fixed_32bit_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class fixed_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class float_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class implements_spec_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_array_data_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format10t_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format10x_odex_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format10x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format11n_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format11x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format12x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format20bc_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format20t_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21c_field_odex_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21c_field_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21c_method_handle_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21c_method_type_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21c_string_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21c_type_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21ih_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21lh_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21s_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format21t_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22b_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22c_field_odex_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22c_field_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22c_type_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22cs_field_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22s_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22t_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format22x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format23x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format30t_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format31c_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format31i_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format31t_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format32x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format35c_call_site_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format35c_method_odex_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format35c_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format35c_type_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format35mi_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format35ms_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format3rc_call_site_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format3rc_method_odex_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format3rc_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format3rc_type_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format3rmi_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format3rms_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format45cc_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format4rcc_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_format51l_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_packed_switch_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class insn_sparse_switch_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class instruction_format12x_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class instruction_format22s_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class instruction_format31i_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class instruction_format35c_method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class instruction_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class integer_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class integral_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class label_ref_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class label_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class line_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class local_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class member_name_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class method_handle_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class method_handle_reference_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class method_prototype_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class method_reference_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class method_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class nonvoid_type_descriptor_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class ordered_method_item_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class param_list_or_id_primitive_type_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class param_list_or_id_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class param_list_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class parameter_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class parsed_integer_literal_return extends ParserRuleReturnScope {
        public CommonTree tree
        public Int value

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class prologue_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class reference_type_descriptor_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class register_list_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class register_range_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class registers_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class restart_local_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class simple_name_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class smali_file_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class smali_file_scope {
        public List<CommonTree> classAnnotations
        public Boolean hasClassSpec
        public Boolean hasSourceSpec
        public Boolean hasSuperSpec
    }

    public static class source_directive_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class source_spec_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class statements_and_directives_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class statements_and_directives_scope {
        public Boolean hasRegistersDirective
        public List<CommonTree> methodAnnotations
    }

    public static class subannotation_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class super_spec_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class type_descriptor_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class type_field_method_literal_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    public static class verification_error_reference_return extends ParserRuleReturnScope {
        public CommonTree tree

        @Override // org.antlr.runtime.ParserRuleReturnScope
        fun getTree() {
            return this.tree
        }
    }

    static {
        Array<String> strArr = {"\u0001\u0002\u0001\uffff\u0001\u000f\u0004\uffff\u0001\t\f\uffff\u0001\b\u000f\uffff\u0001\u0007\u0001\u0003\u0002\uffff\u0001\u0012\u0001\u0013\u0001\u0014\u0001\uffff\u0001\u0015\u0001\uffff\u0001\u0016\u0002\uffff\u0001\u0017\u0001\u0018\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0003\uffff\u0001\u001d\u0001\uffff\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\uffff\u0001\"\u0001#\u0001\uffff\u0001$\u0003\uffff\u0001%\u0001&\u0001\uffff\u0001'\u0001(\u0001)\u0001*\u0001+\u0001,\u0001-\u0006\uffff\u0001.\u0001/\u00010_\uffff\u00011\u0001\uffff\u0001\u0010\u0001\u0011\u0001\u0006\u0001\n\u0004\uffff\u0001\f\u0001\u0005\u0001\r\u0001\uffff\u0001\u000b\u0003\uffff\u0001\u0001\u0005\uffff\u0001\u0004\u0001\u000e", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013\u0002\uffff\u0001\f", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "", ""}
        DFA31_transitionS = strArr
        Int length = strArr.length
        DFA31_transition = new Short[length][]
        for (Int i = 0; i < length; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i])
        }
        Array<String> strArr2 = {"\u0001\u0004\u0001\uffff\u0001\u0011\u0001\uffff\u0001\u0002\u0002\uffff\u0001\u000b\u0004\uffff\u0001\u0001\u0007\uffff\u0001\n\u000f\uffff\u0001\t\u0001\u0005\u0002\uffff\u0001\u0014\u0001\u0015\u0001\u0016\u0001\uffff\u0001\u0017\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0001\u001e\u0003\uffff\u0001\u001f\u0001\uffff\u0001 \u0001!\u0001\"\u0001#\u0001\uffff\u0001$\u0001%\u0001\uffff\u0001&\u0003\uffff\u0001'\u0001(\u0001\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u0006\uffff\u00010\u00011\u00012_\uffff\u00013\u0001\uffff\u0001\u0012\u0001\u0013\u0001\b\u0001\f\u0004\uffff\u0001\u000e\u0001\u0007\u0001\u000f\u0001\uffff\u0001\r\u0003\uffff\u0001\u0003\u0005\uffff\u0001\u0006\u0001\u0010", "\u00014\u0001\uffff\u00014\u0001\uffff\u00015\u0003\uffff\u00024\u0005\uffff\u00014\u0007\uffff\u00024\u0005\uffff\u00014\b\uffff34[\uffff\u00034\t\uffff\u00024\u0003\uffff\u00014\u0001\uffff\u00024\u0002\uffff\u00024", "\u00016¹\uffff\u00016", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018\u0002\uffff\u0001\u000e", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "", "\u0001\u0004\u0001\uffff\u0001\u0011\u0004\uffff\u0001\u000b\f\uffff\u0001\n\u000f\uffff\u0001\t\u0001\u0005\u0002\uffff\u0001\u0014\u0001\u0015\u0001\u0016\u0001\uffff\u0001\u0017\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0001\u001e\u0003\uffff\u0001\u001f\u0001\uffff\u0001 \u0001!\u0001\"\u0001#\u0001\uffff\u0001$\u0001%\u0001\uffff\u0001&\u0003\uffff\u0001'\u0001(\u0001\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u0006\uffff\u00010\u00011\u00012_\uffff\u00013\u0001\uffff\u0001\u0012\u0001\u0013\u0001\b\u0001\f\u0004\uffff\u0001\u000e\u0001\u0007\u0001\u000f\u0001\uffff\u0001\r\u0003\uffff\u0001\u0003\u0005\uffff\u0001\u0006\u0001\u0010", "\u00015", "", ""}
        DFA41_transitionS = strArr2
        DFA41_eot = DFA.unpackEncodedString("9\uffff")
        DFA41_eof = DFA.unpackEncodedString("9\uffff")
        DFA41_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0004\u0001\u0005\u0001\u00101\u0014\u0001\uffff\u0001\u0004\u0001\t\u0002\uffff")
        DFA41_max = DFA.unpackEncodedStringToUnsignedChars("\u0001×\u0001Ò\u0001Ê\u000bÅ\u0001È%Å\u0001\uffff\u0001×\u0001\t\u0002\uffff")
        DFA41_accept = DFA.unpackEncodedString("4\uffff\u0001\u0001\u0002\uffff\u0001\u0002\u0001\u0003")
        DFA41_special = DFA.unpackEncodedString("9\uffff}>")
        Int length2 = strArr2.length
        DFA41_transition = new Short[length2][]
        for (Int i2 = 0; i2 < length2; i2++) {
            DFA41_transition[i2] = DFA.unpackEncodedString(DFA41_transitionS[i2])
        }
        Array<String> strArr3 = {"\u0001F\u0001\uffff\u0001\u0001\u0005\uffff\u0002\u0001\u0005\uffff\u0001\u0001\u0007\uffff\u0002\u0001\u0001\uffff\u0001\u0001\u0003\uffff\u0001\u0001\b\uffff3\u0001[\uffff\u0003\u0001\t\uffff\u0002\u0001\u0003\uffff\u0001\u0001\u0001\uffff\u0002\u0001\u0002\uffff\u0002\u0001", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "\u0001\uffff", ""}
        DFA43_transitionS = strArr3
        DFA43_eot = DFA.unpackEncodedString("H\uffff")
        DFA43_eof = DFA.unpackEncodedString("H\uffff")
        DFA43_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0005E\uffff\u0001\u0000\u0001\uffff")
        DFA43_max = DFA.unpackEncodedStringToUnsignedChars("\u0001ÒE\uffff\u0001\u0000\u0001\uffff")
        DFA43_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0002E\uffff\u0001\u0001")
        DFA43_special = DFA.unpackEncodedString("F\uffff\u0001\u0000\u0001\uffff}>")
        Int length3 = strArr3.length
        DFA43_transition = new Short[length3][]
        for (Int i3 = 0; i3 < length3; i3++) {
            DFA43_transition[i3] = DFA.unpackEncodedString(DFA43_transitionS[i3])
        }
        FOLLOW_class_spec_in_smali_file1150 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_super_spec_in_smali_file1161 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_implements_spec_in_smali_file1169 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_source_spec_in_smali_file1178 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_method_in_smali_file1186 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_field_in_smali_file1192 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_annotation_in_smali_file1198 = BitSet(new Array<Long>{4535485595680L, 0, Long.MIN_VALUE, 2228224})
        FOLLOW_EOF_in_smali_file1209 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DIRECTIVE_in_class_spec1296 = BitSet(new Array<Long>{65552})
        FOLLOW_access_list_in_class_spec1298 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_CLASS_DESCRIPTOR_in_class_spec1300 = BitSet(new Array<Long>{2})
        FOLLOW_SUPER_DIRECTIVE_in_super_spec1318 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_CLASS_DESCRIPTOR_in_super_spec1320 = BitSet(new Array<Long>{2})
        FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1339 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1341 = BitSet(new Array<Long>{2})
        FOLLOW_SOURCE_DIRECTIVE_in_source_spec1360 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_source_spec1362 = BitSet(new Array<Long>{2})
        FOLLOW_ACCESS_SPEC_in_access_list1381 = BitSet(new Array<Long>{18})
        FOLLOW_access_or_restriction_in_access_or_restriction_list1416 = BitSet(new Array<Long>{2199023255570L})
        FOLLOW_FIELD_DIRECTIVE_in_field1449 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_access_or_restriction_list_in_field1451 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_member_name_in_field1453 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_COLON_in_field1455 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_field1457 = BitSet(new Array<Long>{68853694498L})
        FOLLOW_EQUAL_in_field1460 = BitSet(new Array<Long>{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031})
        FOLLOW_literal_in_field1462 = BitSet(new Array<Long>{134217762})
        FOLLOW_annotation_in_field1475 = BitSet(new Array<Long>{134217762})
        FOLLOW_END_FIELD_DIRECTIVE_in_field1489 = BitSet(new Array<Long>{2})
        FOLLOW_METHOD_DIRECTIVE_in_method1600 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_access_or_restriction_list_in_method1602 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_member_name_in_method1604 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_method1606 = BitSet(new Array<Long>{-17557019926368L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_statements_and_directives_in_method1608 = BitSet(new Array<Long>{536870912})
        FOLLOW_END_METHOD_DIRECTIVE_in_method1614 = BitSet(new Array<Long>{2})
        FOLLOW_ordered_method_item_in_statements_and_directives1659 = BitSet(new Array<Long>{-17557556797278L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_registers_directive_in_statements_and_directives1667 = BitSet(new Array<Long>{-17557556797278L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_catch_directive_in_statements_and_directives1675 = BitSet(new Array<Long>{-17557556797278L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_catchall_directive_in_statements_and_directives1683 = BitSet(new Array<Long>{-17557556797278L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_parameter_directive_in_statements_and_directives1691 = BitSet(new Array<Long>{-17557556797278L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_annotation_in_statements_and_directives1699 = BitSet(new Array<Long>{-17557556797278L, 2147483647L, 2017612633061982208L, 420032})
        FOLLOW_label_in_ordered_method_item1784 = BitSet(new Array<Long>{2})
        FOLLOW_instruction_in_ordered_method_item1790 = BitSet(new Array<Long>{2})
        FOLLOW_debug_directive_in_ordered_method_item1796 = BitSet(new Array<Long>{2})
        FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1816 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_registers_directive1820 = BitSet(new Array<Long>{2})
        FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1840 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_registers_directive1844 = BitSet(new Array<Long>{2})
        FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id1876 = BitSet(new Array<Long>{2, 0, 0, 256})
        FOLLOW_SIMPLE_NAME_in_simple_name1889 = BitSet(new Array<Long>{2})
        FOLLOW_ACCESS_SPEC_in_simple_name1895 = BitSet(new Array<Long>{2})
        FOLLOW_HIDDENAPI_RESTRICTION_in_simple_name1906 = BitSet(new Array<Long>{2})
        FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1917 = BitSet(new Array<Long>{2})
        FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1928 = BitSet(new Array<Long>{2})
        FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1939 = BitSet(new Array<Long>{2})
        FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1950 = BitSet(new Array<Long>{2})
        FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1961 = BitSet(new Array<Long>{2})
        FOLLOW_BOOL_LITERAL_in_simple_name1972 = BitSet(new Array<Long>{2})
        FOLLOW_NULL_LITERAL_in_simple_name1983 = BitSet(new Array<Long>{2})
        FOLLOW_REGISTER_in_simple_name1994 = BitSet(new Array<Long>{2})
        FOLLOW_param_list_or_id_in_simple_name2005 = BitSet(new Array<Long>{2})
        FOLLOW_PRIMITIVE_TYPE_in_simple_name2015 = BitSet(new Array<Long>{2})
        FOLLOW_VOID_TYPE_in_simple_name2026 = BitSet(new Array<Long>{2})
        FOLLOW_ANNOTATION_VISIBILITY_in_simple_name2037 = BitSet(new Array<Long>{2})
        FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_simple_name2048 = BitSet(new Array<Long>{2})
        FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_simple_name2054 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name2060 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name2071 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2082 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2093 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2104 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2115 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2126 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_simple_name2137 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_simple_name2148 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2159 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2170 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2181 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2192 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2203 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2214 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2225 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2236 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2247 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2258 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2269 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2280 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_simple_name2291 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2302 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2313 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_simple_name2324 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2335 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2346 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2357 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_simple_name2368 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_simple_name2379 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2390 = BitSet(new Array<Long>{2})
        FOLLOW_simple_name_in_member_name2405 = BitSet(new Array<Long>{2})
        FOLLOW_MEMBER_NAME_in_member_name2411 = BitSet(new Array<Long>{2})
        FOLLOW_OPEN_PAREN_in_method_prototype2426 = BitSet(new Array<Long>{590080, 0, 0, 1280})
        FOLLOW_param_list_in_method_prototype2428 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_CLOSE_PAREN_in_method_prototype2430 = BitSet(new Array<Long>{65792, 0, 0, 8389632})
        FOLLOW_type_descriptor_in_method_prototype2432 = BitSet(new Array<Long>{2})
        FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id_primitive_type2462 = BitSet(new Array<Long>{2})
        FOLLOW_param_list_or_id_primitive_type_in_param_list2477 = BitSet(new Array<Long>{2, 0, 0, 256})
        FOLLOW_nonvoid_type_descriptor_in_param_list2484 = BitSet(new Array<Long>{65794, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor2495 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        BitSet(new Array<Long>{2})
        FOLLOW_VOID_TYPE_in_type_descriptor2513 = BitSet(new Array<Long>{2})
        FOLLOW_PRIMITIVE_TYPE_in_type_descriptor2519 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_type_descriptor2525 = BitSet(new Array<Long>{2})
        FOLLOW_array_descriptor_in_type_descriptor2531 = BitSet(new Array<Long>{2})
        FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor2541 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor2547 = BitSet(new Array<Long>{2})
        FOLLOW_array_descriptor_in_nonvoid_type_descriptor2553 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor2563 = BitSet(new Array<Long>{2})
        FOLLOW_array_descriptor_in_reference_type_descriptor2569 = BitSet(new Array<Long>{2})
        FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2579 = BitSet(new Array<Long>{2})
        FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2590 = BitSet(new Array<Long>{2})
        FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2605 = BitSet(new Array<Long>{2})
        FOLLOW_FLOAT_LITERAL_in_float_literal2616 = BitSet(new Array<Long>{2})
        FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2626 = BitSet(new Array<Long>{2})
        FOLLOW_DOUBLE_LITERAL_in_double_literal2637 = BitSet(new Array<Long>{2})
        FOLLOW_LONG_LITERAL_in_literal2647 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_literal2653 = BitSet(new Array<Long>{2})
        FOLLOW_SHORT_LITERAL_in_literal2659 = BitSet(new Array<Long>{2})
        FOLLOW_BYTE_LITERAL_in_literal2665 = BitSet(new Array<Long>{2})
        FOLLOW_float_literal_in_literal2671 = BitSet(new Array<Long>{2})
        FOLLOW_double_literal_in_literal2677 = BitSet(new Array<Long>{2})
        FOLLOW_CHAR_LITERAL_in_literal2683 = BitSet(new Array<Long>{2})
        FOLLOW_STRING_LITERAL_in_literal2689 = BitSet(new Array<Long>{2})
        FOLLOW_BOOL_LITERAL_in_literal2695 = BitSet(new Array<Long>{2})
        FOLLOW_NULL_LITERAL_in_literal2701 = BitSet(new Array<Long>{2})
        FOLLOW_array_literal_in_literal2707 = BitSet(new Array<Long>{2})
        FOLLOW_subannotation_in_literal2713 = BitSet(new Array<Long>{2})
        FOLLOW_type_field_method_literal_in_literal2719 = BitSet(new Array<Long>{2})
        FOLLOW_enum_literal_in_literal2725 = BitSet(new Array<Long>{2})
        FOLLOW_method_handle_literal_in_literal2731 = BitSet(new Array<Long>{2})
        FOLLOW_method_prototype_in_literal2737 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_parsed_integer_literal2750 = BitSet(new Array<Long>{2})
        FOLLOW_LONG_LITERAL_in_integral_literal2762 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_integral_literal2768 = BitSet(new Array<Long>{2})
        FOLLOW_SHORT_LITERAL_in_integral_literal2774 = BitSet(new Array<Long>{2})
        FOLLOW_CHAR_LITERAL_in_integral_literal2780 = BitSet(new Array<Long>{2})
        FOLLOW_BYTE_LITERAL_in_integral_literal2786 = BitSet(new Array<Long>{2})
        FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2796 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_fixed_32bit_literal2802 = BitSet(new Array<Long>{2})
        FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2808 = BitSet(new Array<Long>{2})
        FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2814 = BitSet(new Array<Long>{2})
        FOLLOW_float_literal_in_fixed_32bit_literal2820 = BitSet(new Array<Long>{2})
        FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2826 = BitSet(new Array<Long>{2})
        FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2832 = BitSet(new Array<Long>{2})
        FOLLOW_integer_literal_in_fixed_literal2842 = BitSet(new Array<Long>{2})
        FOLLOW_LONG_LITERAL_in_fixed_literal2848 = BitSet(new Array<Long>{2})
        FOLLOW_SHORT_LITERAL_in_fixed_literal2854 = BitSet(new Array<Long>{2})
        FOLLOW_BYTE_LITERAL_in_fixed_literal2860 = BitSet(new Array<Long>{2})
        FOLLOW_float_literal_in_fixed_literal2866 = BitSet(new Array<Long>{2})
        FOLLOW_double_literal_in_fixed_literal2872 = BitSet(new Array<Long>{2})
        FOLLOW_CHAR_LITERAL_in_fixed_literal2878 = BitSet(new Array<Long>{2})
        FOLLOW_BOOL_LITERAL_in_fixed_literal2884 = BitSet(new Array<Long>{2})
        FOLLOW_OPEN_BRACE_in_array_literal2894 = BitSet(new Array<Long>{5180673957158033744L, 1883222383, 6917529027641081856L, 14260031})
        FOLLOW_literal_in_array_literal2897 = BitSet(new Array<Long>{2359296})
        FOLLOW_COMMA_in_array_literal2900 = BitSet(new Array<Long>{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031})
        FOLLOW_literal_in_array_literal2902 = BitSet(new Array<Long>{2359296})
        FOLLOW_CLOSE_BRACE_in_array_literal2910 = BitSet(new Array<Long>{2})
        FOLLOW_simple_name_in_annotation_element2934 = BitSet(new Array<Long>{68719476736L})
        FOLLOW_EQUAL_in_annotation_element2936 = BitSet(new Array<Long>{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031})
        FOLLOW_literal_in_annotation_element2938 = BitSet(new Array<Long>{2})
        FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2963 = BitSet(new Array<Long>{64})
        FOLLOW_ANNOTATION_VISIBILITY_in_annotation2965 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_CLASS_DESCRIPTOR_in_annotation2967 = BitSet(new Array<Long>{5180673390247151696L, 1883222383, 0, 12654351})
        FOLLOW_annotation_element_in_annotation2973 = BitSet(new Array<Long>{5180673390247151696L, 1883222383, 0, 12654351})
        FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2976 = BitSet(new Array<Long>{2})
        FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation3009 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH})
        FOLLOW_CLASS_DESCRIPTOR_in_subannotation3011 = BitSet(new Array<Long>{5180673398803531856L, 1883222383, 0, 12654351})
        FOLLOW_annotation_element_in_subannotation3013 = BitSet(new Array<Long>{5180673398803531856L, 1883222383, 0, 12654351})
        FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation3016 = BitSet(new Array<Long>{2})
        FOLLOW_ENUM_DIRECTIVE_in_enum_literal3043 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_field_reference_in_enum_literal3045 = BitSet(new Array<Long>{2})
        FOLLOW_reference_type_descriptor_in_type_field_method_literal3065 = BitSet(new Array<Long>{2})
        FOLLOW_reference_type_descriptor_in_type_field_method_literal3074 = BitSet(new Array<Long>{512})
        FOLLOW_ARROW_in_type_field_method_literal3076 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_member_name_in_type_field_method_literal3088 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_COLON_in_type_field_method_literal3090 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal3092 = BitSet(new Array<Long>{2})
        FOLLOW_member_name_in_type_field_method_literal3115 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_type_field_method_literal3117 = BitSet(new Array<Long>{2})
        FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal3150 = BitSet(new Array<Long>{2})
        FOLLOW_VOID_TYPE_in_type_field_method_literal3156 = BitSet(new Array<Long>{2})
        FOLLOW_simple_name_in_call_site_reference3166 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_OPEN_PAREN_in_call_site_reference3168 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_call_site_reference3170 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_call_site_reference3172 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_call_site_reference3174 = BitSet(new Array<Long>{2621440})
        FOLLOW_COMMA_in_call_site_reference3177 = BitSet(new Array<Long>{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031})
        FOLLOW_literal_in_call_site_reference3179 = BitSet(new Array<Long>{2621440})
        FOLLOW_CLOSE_PAREN_in_call_site_reference3183 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_AT_in_call_site_reference3185 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_call_site_reference3187 = BitSet(new Array<Long>{2})
        FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_method_handle_reference3231 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_AT_in_method_handle_reference3233 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_field_reference_in_method_handle_reference3235 = BitSet(new Array<Long>{2})
        FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_method_handle_reference3247 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_AT_in_method_handle_reference3249 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_method_handle_reference3251 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_method_handle_reference3263 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_AT_in_method_handle_reference3265 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_method_handle_reference3267 = BitSet(new Array<Long>{2})
        FOLLOW_method_handle_reference_in_method_handle_literal3283 = BitSet(new Array<Long>{2})
        FOLLOW_reference_type_descriptor_in_method_reference3304 = BitSet(new Array<Long>{512})
        FOLLOW_ARROW_in_method_reference3306 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_member_name_in_method_reference3310 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_method_reference3312 = BitSet(new Array<Long>{2})
        FOLLOW_reference_type_descriptor_in_field_reference3334 = BitSet(new Array<Long>{512})
        FOLLOW_ARROW_in_field_reference3336 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_member_name_in_field_reference3340 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_COLON_in_field_reference3342 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_field_reference3344 = BitSet(new Array<Long>{2})
        FOLLOW_COLON_in_label3365 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 0, 12654351})
        FOLLOW_simple_name_in_label3367 = BitSet(new Array<Long>{2})
        FOLLOW_COLON_in_label_ref3386 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 0, 12654351})
        FOLLOW_simple_name_in_label_ref3388 = BitSet(new Array<Long>{2})
        FOLLOW_REGISTER_in_register_list3402 = BitSet(new Array<Long>{2097154})
        FOLLOW_COMMA_in_register_list3405 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_register_list3407 = BitSet(new Array<Long>{2097154})
        FOLLOW_REGISTER_in_register_range3442 = BitSet(new Array<Long>{4194306})
        FOLLOW_DOTDOT_in_register_range3445 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_register_range3449 = BitSet(new Array<Long>{2})
        FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3478 = BitSet(new Array<Long>{2})
        FOLLOW_field_reference_in_verification_error_reference3482 = BitSet(new Array<Long>{2})
        FOLLOW_method_reference_in_verification_error_reference3486 = BitSet(new Array<Long>{2})
        FOLLOW_CATCH_DIRECTIVE_in_catch_directive3496 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_catch_directive3498 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_catch_directive3500 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_catch_directive3504 = BitSet(new Array<Long>{4194304})
        FOLLOW_DOTDOT_in_catch_directive3506 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_catch_directive3510 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_catch_directive3512 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_catch_directive3516 = BitSet(new Array<Long>{2})
        FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3548 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_catchall_directive3550 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_catchall_directive3554 = BitSet(new Array<Long>{4194304})
        FOLLOW_DOTDOT_in_catchall_directive3556 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_catchall_directive3560 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_catchall_directive3562 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_catchall_directive3566 = BitSet(new Array<Long>{2})
        FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3605 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_parameter_directive3607 = BitSet(new Array<Long>{2149580834L})
        FOLLOW_COMMA_in_parameter_directive3610 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_parameter_directive3612 = BitSet(new Array<Long>{2147483682L})
        FOLLOW_annotation_in_parameter_directive3623 = BitSet(new Array<Long>{2147483682L})
        FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3636 = BitSet(new Array<Long>{2})
        FOLLOW_line_directive_in_debug_directive3709 = BitSet(new Array<Long>{2})
        FOLLOW_local_directive_in_debug_directive3715 = BitSet(new Array<Long>{2})
        FOLLOW_end_local_directive_in_debug_directive3721 = BitSet(new Array<Long>{2})
        FOLLOW_restart_local_directive_in_debug_directive3727 = BitSet(new Array<Long>{2})
        FOLLOW_prologue_directive_in_debug_directive3733 = BitSet(new Array<Long>{2})
        FOLLOW_epilogue_directive_in_debug_directive3739 = BitSet(new Array<Long>{2})
        FOLLOW_source_directive_in_debug_directive3745 = BitSet(new Array<Long>{2})
        FOLLOW_LINE_DIRECTIVE_in_line_directive3755 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_line_directive3757 = BitSet(new Array<Long>{2})
        FOLLOW_LOCAL_DIRECTIVE_in_local_directive3780 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_local_directive3782 = BitSet(new Array<Long>{2097154})
        FOLLOW_COMMA_in_local_directive3785 = BitSet(new Array<Long>{0, 0, 0, 524296})
        FOLLOW_NULL_LITERAL_in_local_directive3788 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_STRING_LITERAL_in_local_directive3794 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_COLON_in_local_directive3797 = BitSet(new Array<Long>{65792, 0, 0, 8389632})
        FOLLOW_VOID_TYPE_in_local_directive3800 = BitSet(new Array<Long>{2097154})
        FOLLOW_nonvoid_type_descriptor_in_local_directive3804 = BitSet(new Array<Long>{2097154})
        FOLLOW_COMMA_in_local_directive3838 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_local_directive3842 = BitSet(new Array<Long>{2})
        FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3884 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_end_local_directive3886 = BitSet(new Array<Long>{2})
        FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3909 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_restart_local_directive3911 = BitSet(new Array<Long>{2})
        FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3934 = BitSet(new Array<Long>{2})
        FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3955 = BitSet(new Array<Long>{2})
        FOLLOW_SOURCE_DIRECTIVE_in_source_directive3976 = BitSet(new Array<Long>{2, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_source_directive3978 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x4003 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x4009 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s4024 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s4030 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i4045 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i4051 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_instruction_format35c_method4068 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_instruction_format35c_method4074 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format10t_in_instruction4089 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format10x_in_instruction4095 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format10x_odex_in_instruction4101 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format11n_in_instruction4107 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format11x_in_instruction4113 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format12x_in_instruction4119 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format20bc_in_instruction4125 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format20t_in_instruction4131 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_field_in_instruction4137 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_field_odex_in_instruction4143 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_method_handle_in_instruction4149 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_method_type_in_instruction4155 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_string_in_instruction4161 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21c_type_in_instruction4167 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21ih_in_instruction4173 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21lh_in_instruction4179 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21s_in_instruction4185 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format21t_in_instruction4191 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22b_in_instruction4197 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22c_field_in_instruction4203 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22c_field_odex_in_instruction4209 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22c_type_in_instruction4215 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22cs_field_in_instruction4221 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22s_in_instruction4227 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22t_in_instruction4233 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format22x_in_instruction4239 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format23x_in_instruction4245 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format30t_in_instruction4251 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format31c_in_instruction4257 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format31i_in_instruction4263 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format31t_in_instruction4269 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format32x_in_instruction4275 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_call_site_in_instruction4281 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_method_in_instruction4287 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_type_in_instruction4293 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35c_method_odex_in_instruction4299 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35mi_method_in_instruction4305 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format35ms_method_in_instruction4311 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_call_site_in_instruction4317 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_method_in_instruction4323 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_method_odex_in_instruction4329 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rc_type_in_instruction4335 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rmi_method_in_instruction4341 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format3rms_method_in_instruction4347 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format45cc_method_in_instruction4353 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format4rcc_method_in_instruction4359 = BitSet(new Array<Long>{2})
        FOLLOW_insn_format51l_in_instruction4365 = BitSet(new Array<Long>{2})
        FOLLOW_insn_array_data_directive_in_instruction4371 = BitSet(new Array<Long>{2})
        FOLLOW_insn_packed_switch_directive_in_instruction4377 = BitSet(new Array<Long>{2})
        FOLLOW_insn_sparse_switch_directive_in_instruction4383 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4403 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_format10t4405 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4435 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4463 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4484 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format11n4486 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format11n4488 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_insn_format11n4490 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4522 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format11x4524 = BitSet(new Array<Long>{2})
        FOLLOW_instruction_format12x_in_insn_format12x4554 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format12x4556 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format12x4558 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format12x4560 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4592 = BitSet(new Array<Long>{0, 0, 0, 4194304})
        FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4594 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format20bc4596 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_verification_error_reference_in_insn_format20bc4598 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4635 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_format20t4637 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4667 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_field4669 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21c_field4671 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_field_reference_in_insn_format21c_field4673 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4705 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_field_odex4707 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21c_field_odex4709 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_field_reference_in_insn_format21c_field_odex4711 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle4749 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_method_handle4751 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21c_method_handle4753 = BitSet(new Array<Long>{0, PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 3})
        FOLLOW_method_handle_reference_in_insn_format21c_method_handle4755 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type4801 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_method_type4803 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21c_method_type4805 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_insn_format21c_method_type4807 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4851 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_string4853 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21c_string4855 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_insn_format21c_string4857 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4889 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21c_type4891 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21c_type4893 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4895 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4927 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21ih4929 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21ih4931 = BitSet(new Array<Long>{1649267480576L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_32bit_literal_in_insn_format21ih4933 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4965 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21lh4967 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21lh4969 = BitSet(new Array<Long>{1649267480576L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_32bit_literal_in_insn_format21lh4971 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s5003 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21s5005 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21s5007 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_insn_format21s5009 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t5041 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format21t5043 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format21t5045 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_format21t5047 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b5079 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22b5081 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22b5083 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22b5085 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22b5087 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_insn_format22b5089 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field5123 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_field5125 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22c_field5127 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_field5129 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22c_field5131 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_field_reference_in_insn_format22c_field5133 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex5167 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_field_odex5169 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22c_field_odex5171 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_field_odex5173 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22c_field_odex5175 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_field_reference_in_insn_format22c_field_odex5177 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type5217 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_type5219 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22c_type5221 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22c_type5223 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22c_type5225 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type5227 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field5261 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22cs_field5263 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22cs_field5265 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22cs_field5267 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22cs_field5269 = BitSet(new Array<Long>{274877906944L})
        FOLLOW_FIELD_OFFSET_in_insn_format22cs_field5271 = BitSet(new Array<Long>{2})
        FOLLOW_instruction_format22s_in_insn_format22s5292 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22s5294 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22s5296 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22s5298 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22s5300 = BitSet(new Array<Long>{36864, 0, 2305843009213693952L, 33284})
        FOLLOW_integral_literal_in_insn_format22s5302 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t5336 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22t5338 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22t5340 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22t5342 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22t5344 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_format22t5346 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x5380 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22x5382 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format22x5384 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format22x5386 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x5418 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format23x5420 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format23x5422 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format23x5424 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format23x5426 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format23x5428 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t5462 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_format30t5464 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5494 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format31c5496 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format31c5498 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED})
        FOLLOW_STRING_LITERAL_in_insn_format31c5500 = BitSet(new Array<Long>{2})
        FOLLOW_instruction_format31i_in_insn_format31i5531 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format31i5533 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format31i5535 = BitSet(new Array<Long>{1649267480576L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_32bit_literal_in_insn_format31i5537 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5569 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format31t5571 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format31t5573 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_format31t5575 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5607 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format32x5609 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format32x5611 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format32x5613 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site5650 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format35c_call_site5652 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format35c_call_site5654 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format35c_call_site5656 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format35c_call_site5658 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 0, 12654351})
        FOLLOW_call_site_reference_in_insn_format35c_call_site5660 = BitSet(new Array<Long>{2})
        FOLLOW_instruction_format35c_method_in_insn_format35c_method5692 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format35c_method5694 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format35c_method5696 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format35c_method5698 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format35c_method5700 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_insn_format35c_method5702 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5734 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format35c_type5736 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format35c_type5738 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format35c_type5740 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format35c_type5742 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5744 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5776 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5778 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format35c_method_odex5780 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5782 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format35c_method_odex5784 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_insn_format35c_method_odex5786 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5807 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format35mi_method5809 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format35mi_method5811 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5813 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format35mi_method5815 = BitSet(new Array<Long>{8796093022208L})
        FOLLOW_INLINE_INDEX_in_insn_format35mi_method5817 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5838 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format35ms_method5840 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format35ms_method5842 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5844 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format35ms_method5846 = BitSet(new Array<Long>{0, 0, 0, 16777216})
        FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5848 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site5874 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format3rc_call_site5876 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_range_in_insn_format3rc_call_site5878 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_call_site5880 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format3rc_call_site5882 = BitSet(new Array<Long>{5180673390213597264L, 1883222383, 0, 12654351})
        FOLLOW_call_site_reference_in_insn_format3rc_call_site5884 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5916 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format3rc_method5918 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_range_in_insn_format3rc_method5920 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5922 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format3rc_method5924 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_insn_format3rc_method5926 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5958 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5960 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format3rc_method_odex5962 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5964 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format3rc_method_odex5966 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_insn_format3rc_method_odex5968 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5989 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format3rc_type5991 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_range_in_insn_format3rc_type5993 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5995 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format3rc_type5997 = BitSet(new Array<Long>{65792, 0, 0, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID})
        FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5999 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method6031 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format3rmi_method6033 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_range_in_insn_format3rmi_method6035 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method6037 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format3rmi_method6039 = BitSet(new Array<Long>{8796093022208L})
        FOLLOW_INLINE_INDEX_in_insn_format3rmi_method6041 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method6062 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format3rms_method6064 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_range_in_insn_format3rms_method6066 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format3rms_method6068 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format3rms_method6070 = BitSet(new Array<Long>{0, 0, 0, 16777216})
        FOLLOW_VTABLE_INDEX_in_insn_format3rms_method6072 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method6093 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format45cc_method6095 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_list_in_insn_format45cc_method6097 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format45cc_method6099 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format45cc_method6101 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_insn_format45cc_method6103 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format45cc_method6105 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_insn_format45cc_method6107 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method6141 = BitSet(new Array<Long>{0, 0, 0, 16})
        FOLLOW_OPEN_BRACE_in_insn_format4rcc_method6143 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_register_range_in_insn_format4rcc_method6145 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_REPEAT_MODE})
        FOLLOW_CLOSE_BRACE_in_insn_format4rcc_method6147 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format4rcc_method6149 = BitSet(new Array<Long>{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351})
        FOLLOW_method_reference_in_insn_format4rcc_method6151 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format4rcc_method6153 = BitSet(new Array<Long>{0, 0, 0, 32})
        FOLLOW_method_prototype_in_insn_format4rcc_method6155 = BitSet(new Array<Long>{2})
        FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l6189 = BitSet(new Array<Long>{0, 0, 0, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM})
        FOLLOW_REGISTER_in_insn_format51l6191 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE})
        FOLLOW_COMMA_in_insn_format51l6193 = BitSet(new Array<Long>{1649292646400L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_literal_in_insn_format51l6195 = BitSet(new Array<Long>{2})
        FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6222 = BitSet(new Array<Long>{0, 0, 0, 516})
        FOLLOW_parsed_integer_literal_in_insn_array_data_directive6228 = BitSet(new Array<Long>{1649359755264L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_literal_in_insn_array_data_directive6240 = BitSet(new Array<Long>{1649359755264L, 0, 2305843009213693952L, 33284})
        FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6243 = BitSet(new Array<Long>{2})
        FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6289 = BitSet(new Array<Long>{1649267480576L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive6295 = BitSet(new Array<Long>{1074790400})
        FOLLOW_label_ref_in_insn_packed_switch_directive6301 = BitSet(new Array<Long>{1074790400})
        FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6308 = BitSet(new Array<Long>{2})
        FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6382 = BitSet(new Array<Long>{1653562447872L, 0, 2305843009213693952L, 33284})
        FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive6389 = BitSet(new Array<Long>{512})
        FOLLOW_ARROW_in_insn_sparse_switch_directive6391 = BitSet(new Array<Long>{PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED})
        FOLLOW_label_ref_in_insn_sparse_switch_directive6393 = BitSet(new Array<Long>{1653562447872L, 0, 2305843009213693952L, 33284})
        FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6401 = BitSet(new Array<Long>{2})
    }

    constructor(TokenStream tokenStream) {
        this(tokenStream, RecognizerSharedState())
    }

    constructor(TokenStream tokenStream, RecognizerSharedState recognizerSharedState) {
        super(tokenStream, recognizerSharedState)
        this.adaptor = CommonTreeAdaptor()
        this.verboseErrors = false
        this.allowOdex = false
        this.apiLevel = 15
        this.opcodes = Opcodes.forApi(15)
        this.smali_file_stack = new Stack<>()
        this.statements_and_directives_stack = new Stack<>()
        this.dfa31 = DFA31(this, this)
        this.dfa41 = DFA41(this, this)
        this.dfa43 = DFA43(this, this)
        this.errMessages = ""
    }

    public final access_list_return access_list() throws RecognitionException {
        access_list_return access_list_returnVar = access_list_return()
        access_list_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token ACCESS_SPEC")
        while (true) {
            try {
                if ((this.input.LA(1) == 4 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                rewriteRuleTokenStream.add((Token) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_list1381))
            } catch (RecognitionException e) {
                reportError(e)
                recover(this.input, e)
                TreeAdaptor treeAdaptor = this.adaptor
                TokenStream tokenStream = this.input
                access_list_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, access_list_returnVar.start, tokenStream.LT(-1), e)
            }
        }
        access_list_returnVar.tree = null
        RewriteRuleSubtreeStream(this.adaptor, "rule retval", access_list_returnVar.getTree())
        CommonTree commonTree = (CommonTree) this.adaptor.nil()
        CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
        TreeAdaptor treeAdaptor2 = this.adaptor
        CommonTree commonTree3 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(97, access_list_returnVar.start, "I_ACCESS_LIST"), commonTree2)
        while (rewriteRuleTokenStream.hasNext()) {
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
        }
        rewriteRuleTokenStream.reset()
        this.adaptor.addChild(commonTree, commonTree3)
        access_list_returnVar.tree = commonTree
        access_list_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
        access_list_returnVar.tree = commonTree4
        this.adaptor.setTokenBoundaries(commonTree4, access_list_returnVar.start, access_list_returnVar.stop)
        return access_list_returnVar
    }

    public final access_or_restriction_return access_or_restriction() throws RecognitionException {
        access_or_restriction_return access_or_restriction_returnVar = access_or_restriction_return()
        access_or_restriction_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token tokenLT = this.input.LT(1)
            if (this.input.LA(1) != 4 && this.input.LA(1) != 41) {
                throw MismatchedSetException(null, this.input)
            }
            this.input.consume()
            TreeAdaptor treeAdaptor = this.adaptor
            treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(tokenLT))
            this.state.errorRecovery = false
            access_or_restriction_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            access_or_restriction_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, access_or_restriction_returnVar.start, access_or_restriction_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            access_or_restriction_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, access_or_restriction_returnVar.start, tokenStream.LT(-1), e)
        }
        return access_or_restriction_returnVar
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x0194 A[Catch: all -> 0x01af, RecognitionException -> 0x01b1, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x01b1, blocks: (B:3:0x0019, B:5:0x0036, B:122:0x0129, B:123:0x015d, B:125:0x0163, B:126:0x016d, B:127:0x0194, B:63:0x00af), top: B:137:0x0019, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0129 A[EDGE_INSN: B:140:0x0129->B:122:0x0129 BREAK  A[LOOP:0: B:137:0x0019->B:128:0x01ac], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.access_or_restriction_list_return access_or_restriction_list() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.access_or_restriction_list():org.jf.smali.smaliParser$access_or_restriction_list_return")
    }

    public final annotation_return annotation() throws RecognitionException {
        annotation_return annotation_returnVar = annotation_return()
        annotation_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_VISIBILITY")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token END_ANNOTATION_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule annotation_element")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 5, FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2963))
            rewriteRuleTokenStream2.add((Token) match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_annotation2965))
            rewriteRuleTokenStream3.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_annotation2967))
            while (true) {
                Char c = 2
                Int iLA = this.input.LA(1)
                if (iLA == 4 || iLA == 6 || iLA == 11 || iLA == 24 || ((iLA >= 40 && iLA <= 41) || ((iLA >= 44 && iLA <= 46) || iLA == 48 || iLA == 50 || ((iLA >= 53 && iLA <= 58) || iLA == 62 || ((iLA >= 64 && iLA <= 67) || ((iLA >= 69 && iLA <= 70) || iLA == 72 || ((iLA >= 76 && iLA <= 77) || ((iLA >= 79 && iLA <= 85) || ((iLA >= 92 && iLA <= 94) || ((iLA >= 192 && iLA <= 195) || ((iLA >= 200 && iLA <= 202) || iLA == 204 || iLA == 208 || (iLA >= 214 && iLA <= 215)))))))))))) {
                    c = 1
                }
                if (c != 1) {
                    break
                }
                pushFollow(FOLLOW_annotation_element_in_annotation2973)
                annotation_element_return annotation_element_returnVarAnnotation_element = annotation_element()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(annotation_element_returnVarAnnotation_element.getTree())
            }
            rewriteRuleTokenStream4.add((Token) match(this.input, 25, FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2976))
            annotation_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", annotation_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(99, annotation_returnVar.start, "I_ANNOTATION"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(183, annotation_returnVar.start, "I_SUBANNOTATION"), commonTree4)
            this.adaptor.addChild(commonTree5, rewriteRuleTokenStream3.nextNode())
            while (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree())
            }
            rewriteRuleSubtreeStream.reset()
            this.adaptor.addChild(commonTree3, commonTree5)
            this.adaptor.addChild(commonTree, commonTree3)
            annotation_returnVar.tree = commonTree
            annotation_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree6 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            annotation_returnVar.tree = commonTree6
            this.adaptor.setTokenBoundaries(commonTree6, annotation_returnVar.start, annotation_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            annotation_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, annotation_returnVar.start, tokenStream.LT(-1), e)
        }
        return annotation_returnVar
    }

    public final annotation_element_return annotation_element() throws RecognitionException {
        annotation_element_return annotation_element_returnVar = annotation_element_return()
        annotation_element_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token EQUAL")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule simple_name")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule literal")
        try {
            pushFollow(FOLLOW_simple_name_in_annotation_element2934)
            simple_name_return simple_name_returnVarSimple_name = simple_name()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(simple_name_returnVarSimple_name.getTree())
            rewriteRuleTokenStream.add((Token) match(this.input, 36, FOLLOW_EQUAL_in_annotation_element2936))
            pushFollow(FOLLOW_literal_in_annotation_element2938)
            literal_return literal_returnVarLiteral = literal()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(literal_returnVarLiteral.getTree())
            annotation_element_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", annotation_element_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle, annotation_element_returnVar.start, "I_ANNOTATION_ELEMENT"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            annotation_element_returnVar.tree = commonTree
            annotation_element_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            annotation_element_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, annotation_element_returnVar.start, annotation_element_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            annotation_element_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, annotation_element_returnVar.start, tokenStream.LT(-1), e)
        }
        return annotation_element_returnVar
    }

    public final array_descriptor_return array_descriptor() throws RecognitionException {
        array_descriptor_return array_descriptor_returnVar = array_descriptor_return()
        array_descriptor_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 8, FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor2495)))
            Token tokenLT = this.input.LT(1)
            if (this.input.LA(1) != 16 && this.input.LA(1) != 202) {
                throw MismatchedSetException(null, this.input)
            }
            this.input.consume()
            TreeAdaptor treeAdaptor = this.adaptor
            treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(tokenLT))
            this.state.errorRecovery = false
            array_descriptor_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            array_descriptor_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, array_descriptor_returnVar.start, array_descriptor_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            array_descriptor_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, array_descriptor_returnVar.start, tokenStream.LT(-1), e)
        }
        return array_descriptor_returnVar
    }

    public final array_literal_return array_literal() throws RecognitionException {
        Char c
        array_literal_return array_literal_returnVar = array_literal_return()
        array_literal_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule literal")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_array_literal2894))
            Int iLA = this.input.LA(1)
            if (iLA == 4 || iLA == 6 || iLA == 8 || ((iLA >= 11 && iLA <= 12) || ((iLA >= 15 && iLA <= 16) || ((iLA >= 23 && iLA <= 24) || iLA == 34 || ((iLA >= 39 && iLA <= 41) || ((iLA >= 44 && iLA <= 46) || iLA == 48 || iLA == 50 || ((iLA >= 53 && iLA <= 58) || iLA == 62 || ((iLA >= 64 && iLA <= 67) || ((iLA >= 69 && iLA <= 70) || iLA == 72 || ((iLA >= 76 && iLA <= 77) || ((iLA >= 79 && iLA <= 85) || ((iLA >= 92 && iLA <= 94) || ((iLA >= 189 && iLA <= 190) || ((iLA >= 192 && iLA <= 197) || ((iLA >= 200 && iLA <= 202) || iLA == 204 || ((iLA >= 207 && iLA <= 208) || ((iLA >= 211 && iLA <= 212) || (iLA >= 214 && iLA <= 215)))))))))))))))))) {
                c = 1
            } else {
                if (iLA != 18) {
                    throw NoViableAltException("", 27, 0, this.input)
                }
                c = 2
            }
            if (c == 1) {
                pushFollow(FOLLOW_literal_in_array_literal2897)
                literal_return literal_returnVarLiteral = literal()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(literal_returnVarLiteral.getTree())
                while (true) {
                    if ((this.input.LA(1) == 21 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_array_literal2900))
                    pushFollow(FOLLOW_literal_in_array_literal2902)
                    literal_return literal_returnVarLiteral2 = literal()
                    this.state._fsp--
                    rewriteRuleSubtreeStream.add(literal_returnVarLiteral2.getTree())
                }
            }
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_array_literal2910))
            array_literal_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", array_literal_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_ratingBarStyle, array_literal_returnVar.start, "I_ENCODED_ARRAY"), commonTree2)
            while (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            }
            rewriteRuleSubtreeStream.reset()
            this.adaptor.addChild(commonTree, commonTree3)
            array_literal_returnVar.tree = commonTree
            array_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            array_literal_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, array_literal_returnVar.start, array_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            array_literal_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, array_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return array_literal_returnVar
    }

    public final CommonTree buildTree(Int i, String str, List<CommonTree> list) {
        CommonTree commonTree = CommonTree(CommonToken(i, str))
        Iterator<CommonTree> it = list.iterator()
        while (it.hasNext()) {
            commonTree.addChild(it.next())
        }
        return commonTree
    }

    public final call_site_reference_return call_site_reference() throws RecognitionException {
        call_site_reference_return call_site_reference_returnVar = call_site_reference_return()
        call_site_reference_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_PAREN")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token AT")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL")
        RewriteRuleTokenStream rewriteRuleTokenStream5 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_PAREN")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_reference")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule simple_name")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = RewriteRuleSubtreeStream(this.adaptor, "rule literal")
        try {
            pushFollow(FOLLOW_simple_name_in_call_site_reference3166)
            simple_name_return simple_name_returnVarSimple_name = simple_name()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(simple_name_returnVarSimple_name.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 197, FOLLOW_OPEN_PAREN_in_call_site_reference3168))
            rewriteRuleTokenStream4.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_call_site_reference3170))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_call_site_reference3172))
            pushFollow(FOLLOW_method_prototype_in_call_site_reference3174)
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype()
            this.state._fsp--
            rewriteRuleSubtreeStream3.add(method_prototype_returnVarMethod_prototype.getTree())
            while (true) {
                if ((this.input.LA(1) == 21 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_call_site_reference3177))
                pushFollow(FOLLOW_literal_in_call_site_reference3179)
                literal_return literal_returnVarLiteral = literal()
                this.state._fsp--
                rewriteRuleSubtreeStream4.add(literal_returnVarLiteral.getTree())
            }
            rewriteRuleTokenStream5.add((Token) match(this.input, 19, FOLLOW_CLOSE_PAREN_in_call_site_reference3183))
            rewriteRuleTokenStream3.add((Token) match(this.input, 10, FOLLOW_AT_in_call_site_reference3185))
            pushFollow(FOLLOW_method_reference_in_call_site_reference3187)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree())
            call_site_reference_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", call_site_reference_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_buttonStyleSmall, "I_CALL_SITE_REFERENCE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(R.styleable.AppCompatTheme_buttonStyle, "I_CALL_SITE_EXTRA_ARGUMENTS"), commonTree4)
            while (rewriteRuleSubtreeStream4.hasNext()) {
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream4.nextTree())
            }
            rewriteRuleSubtreeStream4.reset()
            this.adaptor.addChild(commonTree3, commonTree5)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            call_site_reference_returnVar.tree = commonTree
            call_site_reference_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree6 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            call_site_reference_returnVar.tree = commonTree6
            this.adaptor.setTokenBoundaries(commonTree6, call_site_reference_returnVar.start, call_site_reference_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            call_site_reference_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, call_site_reference_returnVar.start, tokenStream.LT(-1), e)
        }
        return call_site_reference_returnVar
    }

    public final catch_directive_return catch_directive() throws RecognitionException {
        catch_directive_return catch_directive_returnVar = catch_directive_return()
        catch_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token DOTDOT")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token CATCH_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 14, FOLLOW_CATCH_DIRECTIVE_in_catch_directive3496))
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_catch_directive3498)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            rewriteRuleTokenStream.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_catch_directive3500))
            pushFollow(FOLLOW_label_ref_in_catch_directive3504)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 22, FOLLOW_DOTDOT_in_catch_directive3506))
            pushFollow(FOLLOW_label_ref_in_catch_directive3510)
            label_ref_return label_ref_returnVarLabel_ref2 = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref2.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_catch_directive3512))
            pushFollow(FOLLOW_label_ref_in_catch_directive3516)
            label_ref_return label_ref_returnVarLabel_ref3 = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref3.getTree())
            catch_directive_returnVar.tree = null
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule using", label_ref_returnVarLabel_ref3.getTree())
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = RewriteRuleSubtreeStream(this.adaptor, "rule from", label_ref_returnVarLabel_ref.getTree())
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream5 = RewriteRuleSubtreeStream(this.adaptor, "rule to", label_ref_returnVarLabel_ref2.getTree())
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", catch_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_checkboxStyle, catch_directive_returnVar.start, "I_CATCH"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream4.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream5.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            catch_directive_returnVar.tree = commonTree
            catch_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            catch_directive_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, catch_directive_returnVar.start, catch_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            catch_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, catch_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return catch_directive_returnVar
    }

    public final catchall_directive_return catchall_directive() throws RecognitionException {
        catchall_directive_return catchall_directive_returnVar = catchall_directive_return()
        catchall_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token DOTDOT")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token CATCHALL_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 13, FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3548))
            rewriteRuleTokenStream.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_catchall_directive3550))
            pushFollow(FOLLOW_label_ref_in_catchall_directive3554)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 22, FOLLOW_DOTDOT_in_catchall_directive3556))
            pushFollow(FOLLOW_label_ref_in_catchall_directive3560)
            label_ref_return label_ref_returnVarLabel_ref2 = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref2.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_catchall_directive3562))
            pushFollow(FOLLOW_label_ref_in_catchall_directive3566)
            label_ref_return label_ref_returnVarLabel_ref3 = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref3.getTree())
            catchall_directive_returnVar.tree = null
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule using", label_ref_returnVarLabel_ref3.getTree())
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule from", label_ref_returnVarLabel_ref.getTree())
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = RewriteRuleSubtreeStream(this.adaptor, "rule to", label_ref_returnVarLabel_ref2.getTree())
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", catchall_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_checkedTextViewStyle, catchall_directive_returnVar.start, "I_CATCHALL"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream4.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            catchall_directive_returnVar.tree = commonTree
            catchall_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            catchall_directive_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, catchall_directive_returnVar.start, catchall_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            catchall_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, catchall_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return catchall_directive_returnVar
    }

    public final class_spec_return class_spec() throws RecognitionException {
        class_spec_return class_spec_returnVar = class_spec_return()
        class_spec_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token CLASS_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule access_list")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 17, FOLLOW_CLASS_DIRECTIVE_in_class_spec1296))
            pushFollow(FOLLOW_access_list_in_class_spec1298)
            access_list_return access_list_returnVarAccess_list = access_list()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(access_list_returnVarAccess_list.getTree())
            Token token = (Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_class_spec1300)
            rewriteRuleTokenStream.add(token)
            class_spec_returnVar.className = token != null ? token.getText() : null
            class_spec_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", class_spec_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            this.adaptor.addChild(commonTree, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree())
            class_spec_returnVar.tree = commonTree
            class_spec_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            class_spec_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, class_spec_returnVar.start, class_spec_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            class_spec_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, class_spec_returnVar.start, tokenStream.LT(-1), e)
        }
        return class_spec_returnVar
    }

    public final debug_directive_return debug_directive() throws RecognitionException {
        Char c
        CommonTree commonTree
        debug_directive_return debug_directive_returnVar = debug_directive_return()
        debug_directive_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 28) {
                c = 3
            } else if (iLA == 35) {
                c = 6
            } else if (iLA == 186) {
                c = 1
            } else if (iLA == 188) {
                c = 2
            } else if (iLA == 203) {
                c = 5
            } else if (iLA == 206) {
                c = 4
            } else {
                if (iLA != 209) {
                    throw NoViableAltException("", 45, 0, this.input)
                }
                c = 7
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_line_directive_in_debug_directive3709)
                    line_directive_return line_directive_returnVarLine_directive = line_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, line_directive_returnVarLine_directive.getTree())
                    break
                case 2:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_local_directive_in_debug_directive3715)
                    local_directive_return local_directive_returnVarLocal_directive = local_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, local_directive_returnVarLocal_directive.getTree())
                    break
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_end_local_directive_in_debug_directive3721)
                    end_local_directive_return end_local_directive_returnVarEnd_local_directive = end_local_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, end_local_directive_returnVarEnd_local_directive.getTree())
                    break
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_restart_local_directive_in_debug_directive3727)
                    restart_local_directive_return restart_local_directive_returnVarRestart_local_directive = restart_local_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, restart_local_directive_returnVarRestart_local_directive.getTree())
                    break
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_prologue_directive_in_debug_directive3733)
                    prologue_directive_return prologue_directive_returnVarPrologue_directive = prologue_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, prologue_directive_returnVarPrologue_directive.getTree())
                    break
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_epilogue_directive_in_debug_directive3739)
                    epilogue_directive_return epilogue_directive_returnVarEpilogue_directive = epilogue_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, epilogue_directive_returnVarEpilogue_directive.getTree())
                    break
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_source_directive_in_debug_directive3745)
                    source_directive_return source_directive_returnVarSource_directive = source_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, source_directive_returnVarSource_directive.getTree())
                    break
                default:
                    commonTree = null
                    break
            }
            debug_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            debug_directive_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, debug_directive_returnVar.start, debug_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            debug_directive_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, debug_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return debug_directive_returnVar
    }

    public final double_literal_return double_literal() throws RecognitionException {
        Char c
        double_literal_return double_literal_returnVar = double_literal_return()
        double_literal_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token DOUBLE_LITERAL_OR_ID")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 24) {
                c = 1
            } else {
                if (iLA != 23) {
                    throw NoViableAltException("", 21, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                Token token = (Token) match(this.input, 24, FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2626)
                rewriteRuleTokenStream.add(token)
                double_literal_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", double_literal_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(23, token))
                double_literal_returnVar.tree = commonTree
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 23, FOLLOW_DOUBLE_LITERAL_in_double_literal2637)))
            }
            double_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            double_literal_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, double_literal_returnVar.start, double_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            double_literal_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, double_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return double_literal_returnVar
    }

    public final end_local_directive_return end_local_directive() throws RecognitionException {
        end_local_directive_return end_local_directive_returnVar = end_local_directive_return()
        end_local_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        try {
            RewriteRuleTokenStream(this.adaptor, "token END_LOCAL_DIRECTIVE").add((Token) match(this.input, 28, FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3884))
            rewriteRuleTokenStream.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_end_local_directive3886))
            end_local_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", end_local_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_switchStyle, end_local_directive_returnVar.start, "I_END_LOCAL"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            end_local_directive_returnVar.tree = commonTree
            end_local_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            end_local_directive_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, end_local_directive_returnVar.start, end_local_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            end_local_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, end_local_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return end_local_directive_returnVar
    }

    public final enum_literal_return enum_literal() throws RecognitionException {
        enum_literal_return enum_literal_returnVar = enum_literal_return()
        enum_literal_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token ENUM_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule field_reference")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 34, FOLLOW_ENUM_DIRECTIVE_in_enum_literal3043))
            pushFollow(FOLLOW_field_reference_in_enum_literal3045)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree())
            enum_literal_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", enum_literal_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_ratingBarStyleIndicator, "I_ENCODED_ENUM"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            enum_literal_returnVar.tree = commonTree
            enum_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            enum_literal_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, enum_literal_returnVar.start, enum_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            enum_literal_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, enum_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return enum_literal_returnVar
    }

    public final epilogue_directive_return epilogue_directive() throws RecognitionException {
        epilogue_directive_return epilogue_directive_returnVar = epilogue_directive_return()
        epilogue_directive_returnVar.start = this.input.LT(1)
        try {
            RewriteRuleTokenStream(this.adaptor, "token EPILOGUE_DIRECTIVE").add((Token) match(this.input, 35, FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3955))
            epilogue_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", epilogue_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            this.adaptor.addChild(commonTree, (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_listMenuViewStyle, epilogue_directive_returnVar.start, "I_EPILOGUE"), commonTree2))
            epilogue_directive_returnVar.tree = commonTree
            epilogue_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            epilogue_directive_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, epilogue_directive_returnVar.start, epilogue_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            epilogue_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, epilogue_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return epilogue_directive_returnVar
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.field_return field() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 900
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.field():org.jf.smali.smaliParser$field_return")
    }

    public final field_reference_return field_reference() throws RecognitionException {
        field_reference_return field_reference_returnVar = field_reference_return()
        field_reference_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token ARROW")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token COLON")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule member_name")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor")
        try {
            Int iLA = this.input.LA(1)
            if (((iLA == 8 || iLA == 16) ? (Char) 1 : (Char) 2) == 1) {
                pushFollow(FOLLOW_reference_type_descriptor_in_field_reference3334)
                reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor()
                this.state._fsp--
                rewriteRuleSubtreeStream3.add(reference_type_descriptor_returnVarReference_type_descriptor.getTree())
                rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_field_reference3336))
            }
            pushFollow(FOLLOW_member_name_in_field_reference3340)
            member_name_return member_name_returnVarMember_name = member_name()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(member_name_returnVarMember_name.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 20, FOLLOW_COLON_in_field_reference3342))
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field_reference3344)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            field_reference_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", field_reference_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            if (rewriteRuleSubtreeStream3.hasNext()) {
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream3.nextTree())
            }
            rewriteRuleSubtreeStream3.reset()
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree())
            field_reference_returnVar.tree = commonTree
            field_reference_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            field_reference_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, field_reference_returnVar.start, field_reference_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            field_reference_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, field_reference_returnVar.start, tokenStream.LT(-1), e)
        }
        return field_reference_returnVar
    }

    public final fixed_32bit_literal_return fixed_32bit_literal() throws RecognitionException {
        Char c
        CommonTree commonTree
        CommonTree commonTree2
        fixed_32bit_literal_return fixed_32bit_literal_returnVar = fixed_32bit_literal_return()
        fixed_32bit_literal_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 11) {
                c = 7
            } else if (iLA == 12) {
                c = 4
            } else if (iLA == 15) {
                c = 6
            } else if (iLA == 189) {
                c = 1
            } else if (iLA == 194 || iLA == 201) {
                c = 2
            } else if (iLA != 207) {
                if (iLA != 39 && iLA != 40) {
                    throw NoViableAltException("", 24, 0, this.input)
                }
                c = 5
            } else {
                c = 3
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2796)))
                    break
                case 2:
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_integer_literal_in_fixed_32bit_literal2802)
                    integer_literal_return integer_literal_returnVarInteger_literal = integer_literal()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree2, integer_literal_returnVarInteger_literal.getTree())
                    commonTree = commonTree2
                    break
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2808)))
                    break
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2814)))
                    break
                case 5:
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_float_literal_in_fixed_32bit_literal2820)
                    float_literal_return float_literal_returnVarFloat_literal = float_literal()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree2, float_literal_returnVarFloat_literal.getTree())
                    commonTree = commonTree2
                    break
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2826)))
                    break
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2832)))
                    break
                default:
                    commonTree = null
                    break
            }
            fixed_32bit_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            fixed_32bit_literal_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, fixed_32bit_literal_returnVar.start, fixed_32bit_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            fixed_32bit_literal_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, fixed_32bit_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return fixed_32bit_literal_returnVar
    }

    public final fixed_literal_return fixed_literal() throws RecognitionException {
        Char c
        CommonTree commonTree
        CommonTree commonTree2
        fixed_literal_return fixed_literal_returnVar = fixed_literal_return()
        fixed_literal_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 11) {
                c = '\b'
            } else if (iLA == 12) {
                c = 4
            } else if (iLA == 15) {
                c = 7
            } else if (iLA == 189) {
                c = 2
            } else if (iLA == 194 || iLA == 201) {
                c = 1
            } else if (iLA == 207) {
                c = 3
            } else if (iLA == 23 || iLA == 24) {
                c = 6
            } else {
                if (iLA != 39 && iLA != 40) {
                    throw NoViableAltException("", 25, 0, this.input)
                }
                c = 5
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_integer_literal_in_fixed_literal2842)
                    integer_literal_return integer_literal_returnVarInteger_literal = integer_literal()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, integer_literal_returnVarInteger_literal.getTree())
                    commonTree2 = commonTree
                    break
                case 2:
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_fixed_literal2848)))
                    break
                case 3:
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_fixed_literal2854)))
                    break
                case 4:
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_fixed_literal2860)))
                    break
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_float_literal_in_fixed_literal2866)
                    float_literal_return float_literal_returnVarFloat_literal = float_literal()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, float_literal_returnVarFloat_literal.getTree())
                    commonTree2 = commonTree
                    break
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_double_literal_in_fixed_literal2872)
                    double_literal_return double_literal_returnVarDouble_literal = double_literal()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, double_literal_returnVarDouble_literal.getTree())
                    commonTree2 = commonTree
                    break
                case 7:
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_fixed_literal2878)))
                    break
                case '\b':
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_fixed_literal2884)))
                    break
                default:
                    commonTree2 = null
                    break
            }
            fixed_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree2)
            fixed_literal_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, fixed_literal_returnVar.start, fixed_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            fixed_literal_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, fixed_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return fixed_literal_returnVar
    }

    public final float_literal_return float_literal() throws RecognitionException {
        Char c
        float_literal_return float_literal_returnVar = float_literal_return()
        float_literal_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token FLOAT_LITERAL_OR_ID")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 40) {
                c = 1
            } else {
                if (iLA != 39) {
                    throw NoViableAltException("", 20, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                Token token = (Token) match(this.input, 40, FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2605)
                rewriteRuleTokenStream.add(token)
                float_literal_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", float_literal_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(39, token))
                float_literal_returnVar.tree = commonTree
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 39, FOLLOW_FLOAT_LITERAL_in_float_literal2616)))
            }
            float_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            float_literal_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, float_literal_returnVar.start, float_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            float_literal_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, float_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return float_literal_returnVar
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getErrorHeader(RecognitionException recognitionException) {
        return getSourceName() + "[" + recognitionException.line + "," + recognitionException.charPositionInLine + "]"
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getErrorMessage(RecognitionException recognitionException, Array<String> strArr) {
        String errorMessage
        if (!this.verboseErrors) {
            String errorMessage2 = super.getErrorMessage(recognitionException, strArr)
            this.errMessages += getErrorHeader(recognitionException) + " " + errorMessage2 + "\n"
            return errorMessage2
        }
        List<String> ruleInvocationStack = BaseRecognizer.getRuleInvocationStack(recognitionException, smaliParser.class.getName())
        if (recognitionException is NoViableAltException) {
            NoViableAltException noViableAltException = (NoViableAltException) recognitionException
            errorMessage = " no viable alt; token=" + getTokenErrorDisplay(recognitionException.token) + " (decision=" + noViableAltException.decisionNumber + " state " + noViableAltException.stateNumber + ") decision=<<" + noViableAltException.grammarDecisionDescription + ">>"
        } else {
            errorMessage = super.getErrorMessage(recognitionException, strArr)
        }
        return ruleInvocationStack + " " + errorMessage
    }

    fun getErrorMessages() {
        return this.errMessages
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getTokenErrorDisplay(Token token) {
        String str
        if (!this.verboseErrors) {
            String text = token.getText()
            if (text == null) {
                if (token.getType() == -1) {
                    text = "<EOF>"
                } else {
                    text = "<" + tokenNames[token.getType()] + ">"
                }
            }
            return "'" + text.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t") + "'"
        }
        CommonToken commonToken = (CommonToken) token
        if (token.getChannel() > 0) {
            str = ",channel=" + token.getChannel()
        } else {
            str = ""
        }
        String text2 = token.getText()
        return "[@" + token.getTokenIndex() + "," + commonToken.getStartIndex() + ":" + commonToken.getStopIndex() + "='" + (text2 != null ? text2.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t") : "<no text>") + "',<" + tokenNames[token.getType()] + ">" + str + "," + token.getLine() + ":" + token.getCharPositionInLine() + "]"
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public Array<String> getTokenNames() {
        return tokenNames
    }

    public final implements_spec_return implements_spec() throws RecognitionException {
        implements_spec_return implements_spec_returnVar = implements_spec_return()
        implements_spec_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token IMPLEMENTS_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 42, FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1339))
            rewriteRuleTokenStream2.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1341))
            implements_spec_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", implements_spec_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(121, implements_spec_returnVar.start, "I_IMPLEMENTS"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            implements_spec_returnVar.tree = commonTree
            implements_spec_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            implements_spec_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, implements_spec_returnVar.start, implements_spec_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            implements_spec_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, implements_spec_returnVar.start, tokenStream.LT(-1), e)
        }
        return implements_spec_returnVar
    }

    public final insn_array_data_directive_return insn_array_data_directive() throws RecognitionException {
        insn_array_data_directive_return insn_array_data_directive_returnVar = insn_array_data_directive_return()
        insn_array_data_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token END_ARRAY_DATA_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token ARRAY_DATA_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule parsed_integer_literal")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_literal")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 7, FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6222))
            pushFollow(FOLLOW_parsed_integer_literal_in_insn_array_data_directive6228)
            parsed_integer_literal_return parsed_integer_literal_returnVar = parsed_integer_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(parsed_integer_literal_returnVar.getTree())
            Int i = parsed_integer_literal_returnVar.value
            if (i != 4 && i != 8 && i != 1 && i != 2) {
                throw SemanticException(this.input, insn_array_data_directive_returnVar.start, "Invalid element width: %d. Must be 1, 2, 4 or 8", Integer.valueOf(i))
            }
            while (true) {
                Int iLA = this.input.LA(1)
                if ((((iLA < 11 || iLA > 12) && iLA != 15 && (iLA < 23 || iLA > 24) && !((iLA >= 39 && iLA <= 40) || iLA == 189 || iLA == 194 || iLA == 201 || iLA == 207)) ? (Char) 2 : (Char) 1) != 1) {
                    break
                }
                pushFollow(FOLLOW_fixed_literal_in_insn_array_data_directive6240)
                fixed_literal_return fixed_literal_returnVarFixed_literal = fixed_literal()
                this.state._fsp--
                rewriteRuleSubtreeStream2.add(fixed_literal_returnVarFixed_literal.getTree())
            }
            rewriteRuleTokenStream.add((Token) match(this.input, 26, FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6243))
            insn_array_data_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_array_data_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(143, insn_array_data_directive_returnVar.start, "I_STATEMENT_ARRAY_DATA"), commonTree2)
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(R.styleable.AppCompatTheme_autoCompleteTextViewStyle, "I_ARRAY_ELEMENT_SIZE"), commonTree4)
            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, commonTree5)
            CommonTree commonTree6 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor3 = this.adaptor
            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle, "I_ARRAY_ELEMENTS"), commonTree6)
            while (rewriteRuleSubtreeStream2.hasNext()) {
                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream2.nextTree())
            }
            rewriteRuleSubtreeStream2.reset()
            this.adaptor.addChild(commonTree3, commonTree7)
            this.adaptor.addChild(commonTree, commonTree3)
            insn_array_data_directive_returnVar.tree = commonTree
            insn_array_data_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree8 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_array_data_directive_returnVar.tree = commonTree8
            this.adaptor.setTokenBoundaries(commonTree8, insn_array_data_directive_returnVar.start, insn_array_data_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor4 = this.adaptor
            TokenStream tokenStream = this.input
            insn_array_data_directive_returnVar.tree = (CommonTree) treeAdaptor4.errorNode(tokenStream, insn_array_data_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_array_data_directive_returnVar
    }

    public final insn_format10t_return insn_format10t() throws RecognitionException {
        insn_format10t_return insn_format10t_returnVar = insn_format10t_return()
        insn_format10t_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10t")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 44, FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4403))
            pushFollow(FOLLOW_label_ref_in_insn_format10t4405)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            insn_format10t_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format10t_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(144, insn_format10t_returnVar.start, "I_STATEMENT_FORMAT10t"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format10t_returnVar.tree = commonTree
            insn_format10t_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format10t_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format10t_returnVar.start, insn_format10t_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format10t_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format10t_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format10t_returnVar
    }

    public final insn_format10x_return insn_format10x() throws RecognitionException {
        insn_format10x_return insn_format10x_returnVar = insn_format10x_return()
        insn_format10x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 45, FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4435))
            insn_format10x_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format10x_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(145, insn_format10x_returnVar.start, "I_STATEMENT_FORMAT10x"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format10x_returnVar.tree = commonTree
            insn_format10x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format10x_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format10x_returnVar.start, insn_format10x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format10x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format10x_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format10x_returnVar
    }

    public final insn_format10x_odex_return insn_format10x_odex() throws RecognitionException {
        insn_format10x_odex_return insn_format10x_odex_returnVar = insn_format10x_odex_return()
        insn_format10x_odex_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 46, FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4463)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format10x_odex_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format10x_odex_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format10x_odex_returnVar.start, insn_format10x_odex_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format10x_odex_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format10x_odex_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format10x_odex_returnVar
    }

    public final insn_format11n_return insn_format11n() throws RecognitionException {
        insn_format11n_return insn_format11n_returnVar = insn_format11n_return()
        insn_format11n_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11n")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 47, FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4484))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11n4486))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format11n4488))
            pushFollow(FOLLOW_integral_literal_in_insn_format11n4490)
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree())
            insn_format11n_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format11n_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(146, insn_format11n_returnVar.start, "I_STATEMENT_FORMAT11n"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format11n_returnVar.tree = commonTree
            insn_format11n_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format11n_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format11n_returnVar.start, insn_format11n_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format11n_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format11n_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format11n_returnVar
    }

    public final insn_format11x_return insn_format11x() throws RecognitionException {
        insn_format11x_return insn_format11x_returnVar = insn_format11x_return()
        insn_format11x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11x")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 48, FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4522))
            rewriteRuleTokenStream.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11x4524))
            insn_format11x_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format11x_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(147, insn_format11x_returnVar.start, "I_STATEMENT_FORMAT11x"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format11x_returnVar.tree = commonTree
            insn_format11x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format11x_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format11x_returnVar.start, insn_format11x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format11x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format11x_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format11x_returnVar
    }

    public final insn_format12x_return insn_format12x() throws RecognitionException {
        insn_format12x_return insn_format12x_returnVar = insn_format12x_return()
        insn_format12x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format12x")
        try {
            pushFollow(FOLLOW_instruction_format12x_in_insn_format12x4554)
            instruction_format12x_return instruction_format12x_returnVarInstruction_format12x = instruction_format12x()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(instruction_format12x_returnVarInstruction_format12x.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x4556))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format12x4558))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x4560))
            insn_format12x_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format12x_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(148, insn_format12x_returnVar.start, "I_STATEMENT_FORMAT12x"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format12x_returnVar.tree = commonTree
            insn_format12x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format12x_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format12x_returnVar.start, insn_format12x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format12x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format12x_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format12x_returnVar
    }

    public final insn_format20bc_return insn_format20bc() throws RecognitionException {
        Token token
        insn_format20bc_return insn_format20bc_returnVar = insn_format20bc_return()
        insn_format20bc_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token VERIFICATION_ERROR_TYPE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT20bc")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule verification_error_reference")
        try {
            token = (Token) match(this.input, 51, FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4592)
            rewriteRuleTokenStream3.add(token)
            rewriteRuleTokenStream2.add((Token) match(this.input, 214, FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4594))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format20bc4596))
            pushFollow(FOLLOW_verification_error_reference_in_insn_format20bc4598)
            verification_error_reference_return verification_error_reference_returnVarVerification_error_reference = verification_error_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(verification_error_reference_returnVarVerification_error_reference.getTree())
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format20bc_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format20bc_returnVar.start, tokenStream.LT(-1), e)
        }
        if (this.allowOdex) {
            if (this.opcodes.getOpcodeByName(token != null ? token.getText() : null) == null || this.apiLevel >= 14) {
            }
            insn_format20bc_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format20bc_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(149, "I_STATEMENT_FORMAT20bc"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format20bc_returnVar.tree = commonTree
            insn_format20bc_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format20bc_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format20bc_returnVar.start, insn_format20bc_returnVar.stop)
            return insn_format20bc_returnVar
        }
        throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
        insn_format20bc_returnVar.tree = null
        RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format20bc_returnVar.getTree())
        CommonTree commonTree5 = (CommonTree) this.adaptor.nil()
        CommonTree commonTree22 = (CommonTree) this.adaptor.nil()
        TreeAdaptor treeAdaptor22 = this.adaptor
        CommonTree commonTree32 = (CommonTree) treeAdaptor22.becomeRoot((CommonTree) treeAdaptor22.create(149, "I_STATEMENT_FORMAT20bc"), commonTree22)
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream3.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream2.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleSubtreeStream.nextTree())
        this.adaptor.addChild(commonTree5, commonTree32)
        insn_format20bc_returnVar.tree = commonTree5
        insn_format20bc_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree42 = (CommonTree) this.adaptor.rulePostProcessing(commonTree5)
        insn_format20bc_returnVar.tree = commonTree42
        this.adaptor.setTokenBoundaries(commonTree42, insn_format20bc_returnVar.start, insn_format20bc_returnVar.stop)
        return insn_format20bc_returnVar
    }

    public final insn_format20t_return insn_format20t() throws RecognitionException {
        insn_format20t_return insn_format20t_returnVar = insn_format20t_return()
        insn_format20t_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT20t")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 52, FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4635))
            pushFollow(FOLLOW_label_ref_in_insn_format20t4637)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            insn_format20t_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format20t_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(150, insn_format20t_returnVar.start, "I_STATEMENT_FORMAT20t"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format20t_returnVar.tree = commonTree
            insn_format20t_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format20t_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format20t_returnVar.start, insn_format20t_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format20t_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format20t_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format20t_returnVar
    }

    public final insn_format21c_field_return insn_format21c_field() throws RecognitionException {
        insn_format21c_field_return insn_format21c_field_returnVar = insn_format21c_field_return()
        insn_format21c_field_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule field_reference")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 53, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4667))
            rewriteRuleTokenStream3.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_field4669))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_field4671))
            pushFollow(FOLLOW_field_reference_in_insn_format21c_field4673)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree())
            insn_format21c_field_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_field_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(151, insn_format21c_field_returnVar.start, "I_STATEMENT_FORMAT21c_FIELD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21c_field_returnVar.tree = commonTree
            insn_format21c_field_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21c_field_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21c_field_returnVar.start, insn_format21c_field_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21c_field_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21c_field_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21c_field_returnVar
    }

    public final insn_format21c_field_odex_return insn_format21c_field_odex() throws RecognitionException {
        Token token
        insn_format21c_field_odex_return insn_format21c_field_odex_returnVar = insn_format21c_field_odex_return()
        insn_format21c_field_odex_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD_ODEX")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule field_reference")
        try {
            token = (Token) match(this.input, 54, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4705)
            rewriteRuleTokenStream3.add(token)
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_field_odex4707))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_field_odex4709))
            pushFollow(FOLLOW_field_reference_in_insn_format21c_field_odex4711)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree())
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21c_field_odex_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format21c_field_odex_returnVar.start, tokenStream.LT(-1), e)
        }
        if (this.allowOdex) {
            if (this.opcodes.getOpcodeByName(token != null ? token.getText() : null) == null || this.apiLevel >= 14) {
            }
            insn_format21c_field_odex_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_field_odex_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(151, insn_format21c_field_odex_returnVar.start, "I_STATEMENT_FORMAT21c_FIELD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21c_field_odex_returnVar.tree = commonTree
            insn_format21c_field_odex_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21c_field_odex_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21c_field_odex_returnVar.start, insn_format21c_field_odex_returnVar.stop)
            return insn_format21c_field_odex_returnVar
        }
        throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
        insn_format21c_field_odex_returnVar.tree = null
        RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_field_odex_returnVar.getTree())
        CommonTree commonTree5 = (CommonTree) this.adaptor.nil()
        CommonTree commonTree22 = (CommonTree) this.adaptor.nil()
        TreeAdaptor treeAdaptor22 = this.adaptor
        CommonTree commonTree32 = (CommonTree) treeAdaptor22.becomeRoot((CommonTree) treeAdaptor22.create(151, insn_format21c_field_odex_returnVar.start, "I_STATEMENT_FORMAT21c_FIELD"), commonTree22)
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream3.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream2.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleSubtreeStream.nextTree())
        this.adaptor.addChild(commonTree5, commonTree32)
        insn_format21c_field_odex_returnVar.tree = commonTree5
        insn_format21c_field_odex_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree42 = (CommonTree) this.adaptor.rulePostProcessing(commonTree5)
        insn_format21c_field_odex_returnVar.tree = commonTree42
        this.adaptor.setTokenBoundaries(commonTree42, insn_format21c_field_odex_returnVar.start, insn_format21c_field_odex_returnVar.stop)
        return insn_format21c_field_odex_returnVar
    }

    public final insn_format21c_method_handle_return insn_format21c_method_handle() throws RecognitionException {
        insn_format21c_method_handle_return insn_format21c_method_handle_returnVar = insn_format21c_method_handle_return()
        insn_format21c_method_handle_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_METHOD_HANDLE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_handle_reference")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 55, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle4749))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_handle4751))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_method_handle4753))
            pushFollow(FOLLOW_method_handle_reference_in_insn_format21c_method_handle4755)
            method_handle_reference_return method_handle_reference_returnVarMethod_handle_reference = method_handle_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_handle_reference_returnVarMethod_handle_reference.getTree())
            insn_format21c_method_handle_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_method_handle_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(152, insn_format21c_method_handle_returnVar.start, "I_STATEMENT_FORMAT21c_METHOD_HANDLE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21c_method_handle_returnVar.tree = commonTree
            insn_format21c_method_handle_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21c_method_handle_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21c_method_handle_returnVar.start, insn_format21c_method_handle_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21c_method_handle_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21c_method_handle_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21c_method_handle_returnVar
    }

    public final insn_format21c_method_type_return insn_format21c_method_type() throws RecognitionException {
        insn_format21c_method_type_return insn_format21c_method_type_returnVar = insn_format21c_method_type_return()
        insn_format21c_method_type_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_METHOD_TYPE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 56, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type4801))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_type4803))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_method_type4805))
            pushFollow(FOLLOW_method_prototype_in_insn_format21c_method_type4807)
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_prototype_returnVarMethod_prototype.getTree())
            insn_format21c_method_type_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_method_type_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(153, insn_format21c_method_type_returnVar.start, "I_STATEMENT_FORMAT21c_METHOD_TYPE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21c_method_type_returnVar.tree = commonTree
            insn_format21c_method_type_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21c_method_type_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21c_method_type_returnVar.start, insn_format21c_method_type_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21c_method_type_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21c_method_type_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21c_method_type_returnVar
    }

    public final insn_format21c_string_return insn_format21c_string() throws RecognitionException {
        insn_format21c_string_return insn_format21c_string_returnVar = insn_format21c_string_return()
        insn_format21c_string_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_STRING")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 57, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4851))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_string4853))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_string4855))
            rewriteRuleTokenStream3.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_insn_format21c_string4857))
            insn_format21c_string_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_string_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(154, insn_format21c_string_returnVar.start, "I_STATEMENT_FORMAT21c_STRING"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21c_string_returnVar.tree = commonTree
            insn_format21c_string_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21c_string_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21c_string_returnVar.start, insn_format21c_string_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21c_string_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21c_string_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21c_string_returnVar
    }

    public final insn_format21c_type_return insn_format21c_type() throws RecognitionException {
        insn_format21c_type_return insn_format21c_type_returnVar = insn_format21c_type_return()
        insn_format21c_type_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_TYPE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4889))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_type4891))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_type4893))
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4895)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            insn_format21c_type_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_type_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(155, insn_format21c_type_returnVar.start, "I_STATEMENT_FORMAT21c"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21c_type_returnVar.tree = commonTree
            insn_format21c_type_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21c_type_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21c_type_returnVar.start, insn_format21c_type_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21c_type_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21c_type_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21c_type_returnVar
    }

    public final insn_format21ih_return insn_format21ih() throws RecognitionException {
        insn_format21ih_return insn_format21ih_returnVar = insn_format21ih_return()
        insn_format21ih_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21ih")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4927))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21ih4929))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21ih4931))
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21ih4933)
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree())
            insn_format21ih_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21ih_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(156, insn_format21ih_returnVar.start, "I_STATEMENT_FORMAT21ih"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21ih_returnVar.tree = commonTree
            insn_format21ih_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21ih_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21ih_returnVar.start, insn_format21ih_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21ih_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21ih_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21ih_returnVar
    }

    public final insn_format21lh_return insn_format21lh() throws RecognitionException {
        insn_format21lh_return insn_format21lh_returnVar = insn_format21lh_return()
        insn_format21lh_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21lh")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 60, FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4965))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21lh4967))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21lh4969))
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21lh4971)
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree())
            insn_format21lh_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21lh_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(157, insn_format21lh_returnVar.start, "I_STATEMENT_FORMAT21lh"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21lh_returnVar.tree = commonTree
            insn_format21lh_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21lh_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21lh_returnVar.start, insn_format21lh_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21lh_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21lh_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21lh_returnVar
    }

    public final insn_format21s_return insn_format21s() throws RecognitionException {
        insn_format21s_return insn_format21s_returnVar = insn_format21s_return()
        insn_format21s_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21s")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 61, FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s5003))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21s5005))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21s5007))
            pushFollow(FOLLOW_integral_literal_in_insn_format21s5009)
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree())
            insn_format21s_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21s_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(158, insn_format21s_returnVar.start, "I_STATEMENT_FORMAT21s"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21s_returnVar.tree = commonTree
            insn_format21s_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21s_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21s_returnVar.start, insn_format21s_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21s_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21s_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21s_returnVar
    }

    public final insn_format21t_return insn_format21t() throws RecognitionException {
        insn_format21t_return insn_format21t_returnVar = insn_format21t_return()
        insn_format21t_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21t")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t5041))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21t5043))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21t5045))
            pushFollow(FOLLOW_label_ref_in_insn_format21t5047)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            insn_format21t_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21t_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(159, insn_format21t_returnVar.start, "I_STATEMENT_FORMAT21t"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format21t_returnVar.tree = commonTree
            insn_format21t_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format21t_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format21t_returnVar.start, insn_format21t_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format21t_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format21t_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format21t_returnVar
    }

    public final insn_format22b_return insn_format22b() throws RecognitionException {
        insn_format22b_return insn_format22b_returnVar = insn_format22b_return()
        insn_format22b_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22b")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 63, FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b5079))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b5081))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22b5083))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b5085))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22b5087))
            pushFollow(FOLLOW_integral_literal_in_insn_format22b5089)
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree())
            insn_format22b_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22b_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(160, insn_format22b_returnVar.start, "I_STATEMENT_FORMAT22b"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22b_returnVar.tree = commonTree
            insn_format22b_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22b_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22b_returnVar.start, insn_format22b_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22b_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format22b_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22b_returnVar
    }

    public final insn_format22c_field_return insn_format22c_field() throws RecognitionException {
        insn_format22c_field_return insn_format22c_field_returnVar = insn_format22c_field_return()
        insn_format22c_field_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule field_reference")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 64, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field5123))
            rewriteRuleTokenStream3.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field5125))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field5127))
            rewriteRuleTokenStream3.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field5129))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field5131))
            pushFollow(FOLLOW_field_reference_in_insn_format22c_field5133)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree())
            insn_format22c_field_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_field_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(161, insn_format22c_field_returnVar.start, "I_STATEMENT_FORMAT22c_FIELD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22c_field_returnVar.tree = commonTree
            insn_format22c_field_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22c_field_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22c_field_returnVar.start, insn_format22c_field_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22c_field_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format22c_field_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22c_field_returnVar
    }

    public final insn_format22c_field_odex_return insn_format22c_field_odex() throws RecognitionException {
        Token token
        insn_format22c_field_odex_return insn_format22c_field_odex_returnVar = insn_format22c_field_odex_return()
        insn_format22c_field_odex_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD_ODEX")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule field_reference")
        try {
            token = (Token) match(this.input, 65, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex5167)
            rewriteRuleTokenStream3.add(token)
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field_odex5169))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field_odex5171))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field_odex5173))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field_odex5175))
            pushFollow(FOLLOW_field_reference_in_insn_format22c_field_odex5177)
            field_reference_return field_reference_returnVarField_reference = field_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree())
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22c_field_odex_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format22c_field_odex_returnVar.start, tokenStream.LT(-1), e)
        }
        if (this.allowOdex) {
            if (this.opcodes.getOpcodeByName(token != null ? token.getText() : null) == null || this.apiLevel >= 14) {
            }
            insn_format22c_field_odex_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_field_odex_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(161, insn_format22c_field_odex_returnVar.start, "I_STATEMENT_FORMAT22c_FIELD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22c_field_odex_returnVar.tree = commonTree
            insn_format22c_field_odex_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22c_field_odex_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22c_field_odex_returnVar.start, insn_format22c_field_odex_returnVar.stop)
            return insn_format22c_field_odex_returnVar
        }
        throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
        insn_format22c_field_odex_returnVar.tree = null
        RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_field_odex_returnVar.getTree())
        CommonTree commonTree5 = (CommonTree) this.adaptor.nil()
        CommonTree commonTree22 = (CommonTree) this.adaptor.nil()
        TreeAdaptor treeAdaptor22 = this.adaptor
        CommonTree commonTree32 = (CommonTree) treeAdaptor22.becomeRoot((CommonTree) treeAdaptor22.create(161, insn_format22c_field_odex_returnVar.start, "I_STATEMENT_FORMAT22c_FIELD"), commonTree22)
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream3.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream2.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleTokenStream2.nextNode())
        this.adaptor.addChild(commonTree32, rewriteRuleSubtreeStream.nextTree())
        this.adaptor.addChild(commonTree5, commonTree32)
        insn_format22c_field_odex_returnVar.tree = commonTree5
        insn_format22c_field_odex_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree42 = (CommonTree) this.adaptor.rulePostProcessing(commonTree5)
        insn_format22c_field_odex_returnVar.tree = commonTree42
        this.adaptor.setTokenBoundaries(commonTree42, insn_format22c_field_odex_returnVar.start, insn_format22c_field_odex_returnVar.stop)
        return insn_format22c_field_odex_returnVar
    }

    public final insn_format22c_type_return insn_format22c_type() throws RecognitionException {
        insn_format22c_type_return insn_format22c_type_returnVar = insn_format22c_type_return()
        insn_format22c_type_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_TYPE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type5217))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type5219))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_type5221))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type5223))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_type5225))
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type5227)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            insn_format22c_type_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_type_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(162, insn_format22c_type_returnVar.start, "I_STATEMENT_FORMAT22c_TYPE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22c_type_returnVar.tree = commonTree
            insn_format22c_type_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22c_type_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22c_type_returnVar.start, insn_format22c_type_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22c_type_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format22c_type_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22c_type_returnVar
    }

    public final insn_format22cs_field_return insn_format22cs_field() throws RecognitionException {
        insn_format22cs_field_return insn_format22cs_field_returnVar = insn_format22cs_field_return()
        insn_format22cs_field_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 67, FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field5261)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22cs_field5263)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22cs_field5265)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22cs_field5267)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22cs_field5269)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 38, FOLLOW_FIELD_OFFSET_in_insn_format22cs_field5271)))
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format22cs_field_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22cs_field_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format22cs_field_returnVar.start, insn_format22cs_field_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22cs_field_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format22cs_field_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22cs_field_returnVar
    }

    public final insn_format22s_return insn_format22s() throws RecognitionException {
        insn_format22s_return insn_format22s_returnVar = insn_format22s_return()
        insn_format22s_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format22s")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal")
        try {
            pushFollow(FOLLOW_instruction_format22s_in_insn_format22s5292)
            instruction_format22s_return instruction_format22s_returnVarInstruction_format22s = instruction_format22s()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(instruction_format22s_returnVarInstruction_format22s.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s5294))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22s5296))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s5298))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22s5300))
            pushFollow(FOLLOW_integral_literal_in_insn_format22s5302)
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(integral_literal_returnVarIntegral_literal.getTree())
            insn_format22s_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22s_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(163, insn_format22s_returnVar.start, "I_STATEMENT_FORMAT22s"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22s_returnVar.tree = commonTree
            insn_format22s_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22s_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22s_returnVar.start, insn_format22s_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22s_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format22s_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22s_returnVar
    }

    public final insn_format22t_return insn_format22t() throws RecognitionException {
        insn_format22t_return insn_format22t_returnVar = insn_format22t_return()
        insn_format22t_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22t")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t5336))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t5338))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22t5340))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t5342))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22t5344))
            pushFollow(FOLLOW_label_ref_in_insn_format22t5346)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            insn_format22t_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22t_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(164, insn_format22t_returnVar.start, "I_STATEMENT_FFORMAT22t"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22t_returnVar.tree = commonTree
            insn_format22t_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22t_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22t_returnVar.start, insn_format22t_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22t_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format22t_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22t_returnVar
    }

    public final insn_format22x_return insn_format22x() throws RecognitionException {
        insn_format22x_return insn_format22x_returnVar = insn_format22x_return()
        insn_format22x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22x")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 71, FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x5380))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x5382))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22x5384))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x5386))
            insn_format22x_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22x_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(165, insn_format22x_returnVar.start, "I_STATEMENT_FORMAT22x"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format22x_returnVar.tree = commonTree
            insn_format22x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format22x_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format22x_returnVar.start, insn_format22x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format22x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format22x_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format22x_returnVar
    }

    public final insn_format23x_return insn_format23x() throws RecognitionException {
        insn_format23x_return insn_format23x_returnVar = insn_format23x_return()
        insn_format23x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT23x")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x5418))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x5420))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format23x5422))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x5424))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format23x5426))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x5428))
            insn_format23x_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format23x_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(166, insn_format23x_returnVar.start, "I_STATEMENT_FORMAT23x"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format23x_returnVar.tree = commonTree
            insn_format23x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format23x_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format23x_returnVar.start, insn_format23x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format23x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format23x_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format23x_returnVar
    }

    public final insn_format30t_return insn_format30t() throws RecognitionException {
        insn_format30t_return insn_format30t_returnVar = insn_format30t_return()
        insn_format30t_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT30t")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 73, FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t5462))
            pushFollow(FOLLOW_label_ref_in_insn_format30t5464)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            insn_format30t_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format30t_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(167, insn_format30t_returnVar.start, "I_STATEMENT_FORMAT30t"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format30t_returnVar.tree = commonTree
            insn_format30t_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format30t_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format30t_returnVar.start, insn_format30t_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format30t_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format30t_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format30t_returnVar
    }

    public final insn_format31c_return insn_format31c() throws RecognitionException {
        insn_format31c_return insn_format31c_returnVar = insn_format31c_return()
        insn_format31c_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31c")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5494))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31c5496))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format31c5498))
            rewriteRuleTokenStream4.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_insn_format31c5500))
            insn_format31c_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format31c_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(168, insn_format31c_returnVar.start, "I_STATEMENT_FORMAT31c"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format31c_returnVar.tree = commonTree
            insn_format31c_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format31c_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format31c_returnVar.start, insn_format31c_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format31c_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format31c_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format31c_returnVar
    }

    public final insn_format31i_return insn_format31i() throws RecognitionException {
        insn_format31i_return insn_format31i_returnVar = insn_format31i_return()
        insn_format31i_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format31i")
        try {
            pushFollow(FOLLOW_instruction_format31i_in_insn_format31i5531)
            instruction_format31i_return instruction_format31i_returnVarInstruction_format31i = instruction_format31i()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(instruction_format31i_returnVarInstruction_format31i.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31i5533))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format31i5535))
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format31i5537)
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree())
            insn_format31i_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format31i_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(169, insn_format31i_returnVar.start, "I_STATEMENT_FORMAT31i"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format31i_returnVar.tree = commonTree
            insn_format31i_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format31i_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format31i_returnVar.start, insn_format31i_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format31i_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format31i_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format31i_returnVar
    }

    public final insn_format31t_return insn_format31t() throws RecognitionException {
        insn_format31t_return insn_format31t_returnVar = insn_format31t_return()
        insn_format31t_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31t")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5569))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31t5571))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format31t5573))
            pushFollow(FOLLOW_label_ref_in_insn_format31t5575)
            label_ref_return label_ref_returnVarLabel_ref = label_ref()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree())
            insn_format31t_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format31t_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(170, insn_format31t_returnVar.start, "I_STATEMENT_FORMAT31t"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format31t_returnVar.tree = commonTree
            insn_format31t_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format31t_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format31t_returnVar.start, insn_format31t_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format31t_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format31t_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format31t_returnVar
    }

    public final insn_format32x_return insn_format32x() throws RecognitionException {
        insn_format32x_return insn_format32x_returnVar = insn_format32x_return()
        insn_format32x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT32x")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5607))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x5609))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format32x5611))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x5613))
            insn_format32x_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format32x_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(171, insn_format32x_returnVar.start, "I_STATEMENT_FORMAT32x"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format32x_returnVar.tree = commonTree
            insn_format32x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format32x_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format32x_returnVar.start, insn_format32x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format32x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format32x_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format32x_returnVar
    }

    public final insn_format35c_call_site_return insn_format35c_call_site() throws RecognitionException {
        insn_format35c_call_site_return insn_format35c_call_site_returnVar = insn_format35c_call_site_return()
        insn_format35c_call_site_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_CALL_SITE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule register_list")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule call_site_reference")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site5650))
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_call_site5652))
            pushFollow(FOLLOW_register_list_in_insn_format35c_call_site5654)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(register_list_returnVarRegister_list.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_call_site5656))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_call_site5658))
            pushFollow(FOLLOW_call_site_reference_in_insn_format35c_call_site5660)
            call_site_reference_return call_site_reference_returnVarCall_site_reference = call_site_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(call_site_reference_returnVarCall_site_reference.getTree())
            insn_format35c_call_site_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format35c_call_site_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(172, insn_format35c_call_site_returnVar.start, "I_STATEMENT_FORMAT35c_CALL_SITE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format35c_call_site_returnVar.tree = commonTree
            insn_format35c_call_site_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format35c_call_site_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format35c_call_site_returnVar.start, insn_format35c_call_site_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format35c_call_site_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format35c_call_site_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format35c_call_site_returnVar
    }

    public final insn_format35c_method_return insn_format35c_method() throws RecognitionException {
        insn_format35c_method_return insn_format35c_method_returnVar = insn_format35c_method_return()
        insn_format35c_method_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format35c_method")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule method_reference")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule register_list")
        try {
            pushFollow(FOLLOW_instruction_format35c_method_in_insn_format35c_method5692)
            instruction_format35c_method_return instruction_format35c_method_returnVarInstruction_format35c_method = instruction_format35c_method()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(instruction_format35c_method_returnVarInstruction_format35c_method.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_method5694))
            pushFollow(FOLLOW_register_list_in_insn_format35c_method5696)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            rewriteRuleSubtreeStream3.add(register_list_returnVarRegister_list.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_method5698))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_method5700))
            pushFollow(FOLLOW_method_reference_in_insn_format35c_method5702)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(method_reference_returnVarMethod_reference.getTree())
            insn_format35c_method_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format35c_method_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(173, insn_format35c_method_returnVar.start, "I_STATEMENT_FORMAT35c_METHOD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format35c_method_returnVar.tree = commonTree
            insn_format35c_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format35c_method_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format35c_method_returnVar.start, insn_format35c_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format35c_method_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format35c_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format35c_method_returnVar
    }

    public final insn_format35c_method_odex_return insn_format35c_method_odex() throws RecognitionException {
        insn_format35c_method_odex_return insn_format35c_method_odex_returnVar = insn_format35c_method_odex_return()
        insn_format35c_method_odex_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 81, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5776)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5778)))
            pushFollow(FOLLOW_register_list_in_insn_format35c_method_odex5780)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree())
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5782)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_method_odex5784)))
            pushFollow(FOLLOW_method_reference_in_insn_format35c_method_odex5786)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            this.adaptor.addChild(commonTree, method_reference_returnVarMethod_reference.getTree())
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format35c_method_odex_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format35c_method_odex_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format35c_method_odex_returnVar.start, insn_format35c_method_odex_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format35c_method_odex_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format35c_method_odex_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format35c_method_odex_returnVar
    }

    public final insn_format35c_type_return insn_format35c_type() throws RecognitionException {
        insn_format35c_type_return insn_format35c_type_returnVar = insn_format35c_type_return()
        insn_format35c_type_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_TYPE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule register_list")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5734))
            rewriteRuleTokenStream3.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_type5736))
            pushFollow(FOLLOW_register_list_in_insn_format35c_type5738)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(register_list_returnVarRegister_list.getTree())
            rewriteRuleTokenStream4.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_type5740))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_type5742))
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5744)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            insn_format35c_type_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format35c_type_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(174, insn_format35c_type_returnVar.start, "I_STATEMENT_FORMAT35c_TYPE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format35c_type_returnVar.tree = commonTree
            insn_format35c_type_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format35c_type_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format35c_type_returnVar.start, insn_format35c_type_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format35c_type_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format35c_type_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format35c_type_returnVar
    }

    public final insn_format35mi_method_return insn_format35mi_method() throws RecognitionException {
        insn_format35mi_method_return insn_format35mi_method_returnVar = insn_format35mi_method_return()
        insn_format35mi_method_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 84, FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5807)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35mi_method5809)))
            pushFollow(FOLLOW_register_list_in_insn_format35mi_method5811)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree())
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5813)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35mi_method5815)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 43, FOLLOW_INLINE_INDEX_in_insn_format35mi_method5817)))
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format35mi_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format35mi_method_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format35mi_method_returnVar.start, insn_format35mi_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format35mi_method_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format35mi_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format35mi_method_returnVar
    }

    public final insn_format35ms_method_return insn_format35ms_method() throws RecognitionException {
        insn_format35ms_method_return insn_format35ms_method_returnVar = insn_format35ms_method_return()
        insn_format35ms_method_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 85, FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5838)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35ms_method5840)))
            pushFollow(FOLLOW_register_list_in_insn_format35ms_method5842)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree())
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5844)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35ms_method5846)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 216, FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5848)))
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format35ms_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format35ms_method_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format35ms_method_returnVar.start, insn_format35ms_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format35ms_method_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format35ms_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format35ms_method_returnVar
    }

    public final insn_format3rc_call_site_return insn_format3rc_call_site() throws RecognitionException {
        insn_format3rc_call_site_return insn_format3rc_call_site_returnVar = insn_format3rc_call_site_return()
        insn_format3rc_call_site_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_CALL_SITE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule register_range")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule call_site_reference")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 86, FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site5874))
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_call_site5876))
            pushFollow(FOLLOW_register_range_in_insn_format3rc_call_site5878)
            register_range_return register_range_returnVarRegister_range = register_range()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(register_range_returnVarRegister_range.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_call_site5880))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_call_site5882))
            pushFollow(FOLLOW_call_site_reference_in_insn_format3rc_call_site5884)
            call_site_reference_return call_site_reference_returnVarCall_site_reference = call_site_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(call_site_reference_returnVarCall_site_reference.getTree())
            insn_format3rc_call_site_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format3rc_call_site_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(175, insn_format3rc_call_site_returnVar.start, "I_STATEMENT_FORMAT3rc_CALL_SITE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format3rc_call_site_returnVar.tree = commonTree
            insn_format3rc_call_site_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format3rc_call_site_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format3rc_call_site_returnVar.start, insn_format3rc_call_site_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format3rc_call_site_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format3rc_call_site_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format3rc_call_site_returnVar
    }

    public final insn_format3rc_method_return insn_format3rc_method() throws RecognitionException {
        insn_format3rc_method_return insn_format3rc_method_returnVar = insn_format3rc_method_return()
        insn_format3rc_method_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_METHOD")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_reference")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule register_range")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 87, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5916))
            rewriteRuleTokenStream3.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_method5918))
            pushFollow(FOLLOW_register_range_in_insn_format3rc_method5920)
            register_range_return register_range_returnVarRegister_range = register_range()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(register_range_returnVarRegister_range.getTree())
            rewriteRuleTokenStream4.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5922))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_method5924))
            pushFollow(FOLLOW_method_reference_in_insn_format3rc_method5926)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree())
            insn_format3rc_method_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format3rc_method_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(176, insn_format3rc_method_returnVar.start, "I_STATEMENT_FORMAT3rc_METHOD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format3rc_method_returnVar.tree = commonTree
            insn_format3rc_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format3rc_method_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format3rc_method_returnVar.start, insn_format3rc_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format3rc_method_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format3rc_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format3rc_method_returnVar
    }

    public final insn_format3rc_method_odex_return insn_format3rc_method_odex() throws RecognitionException {
        insn_format3rc_method_odex_return insn_format3rc_method_odex_returnVar = insn_format3rc_method_odex_return()
        insn_format3rc_method_odex_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 88, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5958)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5960)))
            pushFollow(FOLLOW_register_list_in_insn_format3rc_method_odex5962)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree())
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5964)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_method_odex5966)))
            pushFollow(FOLLOW_method_reference_in_insn_format3rc_method_odex5968)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            this.adaptor.addChild(commonTree, method_reference_returnVarMethod_reference.getTree())
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format3rc_method_odex_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format3rc_method_odex_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format3rc_method_odex_returnVar.start, insn_format3rc_method_odex_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format3rc_method_odex_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format3rc_method_odex_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format3rc_method_odex_returnVar
    }

    public final insn_format3rc_type_return insn_format3rc_type() throws RecognitionException {
        insn_format3rc_type_return insn_format3rc_type_returnVar = insn_format3rc_type_return()
        insn_format3rc_type_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_TYPE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule register_range")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5989))
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_type5991))
            pushFollow(FOLLOW_register_range_in_insn_format3rc_type5993)
            register_range_return register_range_returnVarRegister_range = register_range()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(register_range_returnVarRegister_range.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5995))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_type5997))
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5999)
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            insn_format3rc_type_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format3rc_type_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(177, insn_format3rc_type_returnVar.start, "I_STATEMENT_FORMAT3rc_TYPE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format3rc_type_returnVar.tree = commonTree
            insn_format3rc_type_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format3rc_type_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format3rc_type_returnVar.start, insn_format3rc_type_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format3rc_type_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format3rc_type_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format3rc_type_returnVar
    }

    public final insn_format3rmi_method_return insn_format3rmi_method() throws RecognitionException {
        insn_format3rmi_method_return insn_format3rmi_method_returnVar = insn_format3rmi_method_return()
        insn_format3rmi_method_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 90, FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method6031)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rmi_method6033)))
            pushFollow(FOLLOW_register_range_in_insn_format3rmi_method6035)
            register_range_return register_range_returnVarRegister_range = register_range()
            this.state._fsp--
            this.adaptor.addChild(commonTree, register_range_returnVarRegister_range.getTree())
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method6037)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rmi_method6039)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 43, FOLLOW_INLINE_INDEX_in_insn_format3rmi_method6041)))
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format3rmi_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format3rmi_method_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format3rmi_method_returnVar.start, insn_format3rmi_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format3rmi_method_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format3rmi_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format3rmi_method_returnVar
    }

    public final insn_format3rms_method_return insn_format3rms_method() throws RecognitionException {
        insn_format3rms_method_return insn_format3rms_method_returnVar = insn_format3rms_method_return()
        insn_format3rms_method_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            Token token = (Token) match(this.input, 91, FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method6062)
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rms_method6064)))
            pushFollow(FOLLOW_register_range_in_insn_format3rms_method6066)
            register_range_return register_range_returnVarRegister_range = register_range()
            this.state._fsp--
            this.adaptor.addChild(commonTree, register_range_returnVarRegister_range.getTree())
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rms_method6068)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rms_method6070)))
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 216, FOLLOW_VTABLE_INDEX_in_insn_format3rms_method6072)))
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null)
            insn_format3rms_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format3rms_method_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, insn_format3rms_method_returnVar.start, insn_format3rms_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            insn_format3rms_method_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, insn_format3rms_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format3rms_method_returnVar
    }

    public final insn_format45cc_method_return insn_format45cc_method() throws RecognitionException {
        insn_format45cc_method_return insn_format45cc_method_returnVar = insn_format45cc_method_return()
        insn_format45cc_method_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT45cc_METHOD")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_reference")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule register_list")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method6093))
            rewriteRuleTokenStream3.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format45cc_method6095))
            pushFollow(FOLLOW_register_list_in_insn_format45cc_method6097)
            register_list_return register_list_returnVarRegister_list = register_list()
            this.state._fsp--
            rewriteRuleSubtreeStream3.add(register_list_returnVarRegister_list.getTree())
            rewriteRuleTokenStream4.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format45cc_method6099))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format45cc_method6101))
            pushFollow(FOLLOW_method_reference_in_insn_format45cc_method6103)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree())
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format45cc_method6105))
            pushFollow(FOLLOW_method_prototype_in_insn_format45cc_method6107)
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(method_prototype_returnVarMethod_prototype.getTree())
            insn_format45cc_method_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format45cc_method_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(178, insn_format45cc_method_returnVar.start, "I_STATEMENT_FORMAT45cc_METHOD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format45cc_method_returnVar.tree = commonTree
            insn_format45cc_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format45cc_method_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format45cc_method_returnVar.start, insn_format45cc_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format45cc_method_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format45cc_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format45cc_method_returnVar
    }

    public final insn_format4rcc_method_return insn_format4rcc_method() throws RecognitionException {
        insn_format4rcc_method_return insn_format4rcc_method_returnVar = insn_format4rcc_method_return()
        insn_format4rcc_method_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT4rcc_METHOD")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_reference")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule register_range")
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method6141))
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format4rcc_method6143))
            pushFollow(FOLLOW_register_range_in_insn_format4rcc_method6145)
            register_range_return register_range_returnVarRegister_range = register_range()
            this.state._fsp--
            rewriteRuleSubtreeStream3.add(register_range_returnVarRegister_range.getTree())
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format4rcc_method6147))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format4rcc_method6149))
            pushFollow(FOLLOW_method_reference_in_insn_format4rcc_method6151)
            method_reference_return method_reference_returnVarMethod_reference = method_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree())
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format4rcc_method6153))
            pushFollow(FOLLOW_method_prototype_in_insn_format4rcc_method6155)
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(method_prototype_returnVarMethod_prototype.getTree())
            insn_format4rcc_method_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format4rcc_method_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(179, insn_format4rcc_method_returnVar.start, "I_STATEMENT_FORMAT4rcc_METHOD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format4rcc_method_returnVar.tree = commonTree
            insn_format4rcc_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format4rcc_method_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format4rcc_method_returnVar.start, insn_format4rcc_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format4rcc_method_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format4rcc_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format4rcc_method_returnVar
    }

    public final insn_format51l_return insn_format51l() throws RecognitionException {
        insn_format51l_return insn_format51l_returnVar = insn_format51l_return()
        insn_format51l_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT51l")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_literal")
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l6189))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format51l6191))
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format51l6193))
            pushFollow(FOLLOW_fixed_literal_in_insn_format51l6195)
            fixed_literal_return fixed_literal_returnVarFixed_literal = fixed_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(fixed_literal_returnVarFixed_literal.getTree())
            insn_format51l_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format51l_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(180, insn_format51l_returnVar.start, "I_STATEMENT_FORMAT51l"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            insn_format51l_returnVar.tree = commonTree
            insn_format51l_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_format51l_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, insn_format51l_returnVar.start, insn_format51l_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            insn_format51l_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, insn_format51l_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_format51l_returnVar
    }

    public final insn_packed_switch_directive_return insn_packed_switch_directive() throws RecognitionException {
        insn_packed_switch_directive_return insn_packed_switch_directive_returnVar = insn_packed_switch_directive_return()
        insn_packed_switch_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token END_PACKED_SWITCH_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token PACKED_SWITCH_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 198, FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6289))
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive6295)
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree())
            while (true) {
                if ((this.input.LA(1) == 20 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_label_ref_in_insn_packed_switch_directive6301)
                label_ref_return label_ref_returnVarLabel_ref = label_ref()
                this.state._fsp--
                rewriteRuleSubtreeStream2.add(label_ref_returnVarLabel_ref.getTree())
            }
            rewriteRuleTokenStream.add((Token) match(this.input, 30, FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6308))
            insn_packed_switch_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_packed_switch_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(181, insn_packed_switch_directive_returnVar.start, "I_STATEMENT_PACKED_SWITCH"), commonTree2)
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(132, insn_packed_switch_directive_returnVar.start, "I_PACKED_SWITCH_START_KEY"), commonTree4)
            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, commonTree5)
            CommonTree commonTree6 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor3 = this.adaptor
            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(131, insn_packed_switch_directive_returnVar.start, "I_PACKED_SWITCH_ELEMENTS"), commonTree6)
            while (rewriteRuleSubtreeStream2.hasNext()) {
                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream2.nextTree())
            }
            rewriteRuleSubtreeStream2.reset()
            this.adaptor.addChild(commonTree3, commonTree7)
            this.adaptor.addChild(commonTree, commonTree3)
            insn_packed_switch_directive_returnVar.tree = commonTree
            insn_packed_switch_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree8 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_packed_switch_directive_returnVar.tree = commonTree8
            this.adaptor.setTokenBoundaries(commonTree8, insn_packed_switch_directive_returnVar.start, insn_packed_switch_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor4 = this.adaptor
            TokenStream tokenStream = this.input
            insn_packed_switch_directive_returnVar.tree = (CommonTree) treeAdaptor4.errorNode(tokenStream, insn_packed_switch_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_packed_switch_directive_returnVar
    }

    public final insn_sparse_switch_directive_return insn_sparse_switch_directive() throws RecognitionException {
        insn_sparse_switch_directive_return insn_sparse_switch_directive_returnVar = insn_sparse_switch_directive_return()
        insn_sparse_switch_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token ARROW")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token SPARSE_SWITCH_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token END_SPARSE_SWITCH_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule label_ref")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 210, FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6382))
            while (true) {
                Int iLA = this.input.LA(1)
                if ((((iLA >= 11 && iLA <= 12) || iLA == 15 || (iLA >= 39 && iLA <= 40) || iLA == 189 || iLA == 194 || iLA == 201 || iLA == 207) ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive6389)
                fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree())
                rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_insn_sparse_switch_directive6391))
                pushFollow(FOLLOW_label_ref_in_insn_sparse_switch_directive6393)
                label_ref_return label_ref_returnVarLabel_ref = label_ref()
                this.state._fsp--
                rewriteRuleSubtreeStream2.add(label_ref_returnVarLabel_ref.getTree())
            }
            rewriteRuleTokenStream3.add((Token) match(this.input, 32, FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6401))
            insn_sparse_switch_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_sparse_switch_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(182, insn_sparse_switch_directive_returnVar.start, "I_STATEMENT_SPARSE_SWITCH"), commonTree2)
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(142, insn_sparse_switch_directive_returnVar.start, "I_SPARSE_SWITCH_ELEMENTS"), commonTree4)
            while (true) {
                if (!rewriteRuleSubtreeStream.hasNext() && !rewriteRuleSubtreeStream2.hasNext()) {
                    break
                }
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree())
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream2.nextTree())
            }
            rewriteRuleSubtreeStream.reset()
            rewriteRuleSubtreeStream2.reset()
            this.adaptor.addChild(commonTree3, commonTree5)
            this.adaptor.addChild(commonTree, commonTree3)
            insn_sparse_switch_directive_returnVar.tree = commonTree
            insn_sparse_switch_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree6 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            insn_sparse_switch_directive_returnVar.tree = commonTree6
            this.adaptor.setTokenBoundaries(commonTree6, insn_sparse_switch_directive_returnVar.start, insn_sparse_switch_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            insn_sparse_switch_directive_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, insn_sparse_switch_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return insn_sparse_switch_directive_returnVar
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final instruction_return instruction() throws RecognitionException {
        CommonTree commonTree
        instruction_return instruction_returnVar = instruction_return()
        instruction_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            Char c = 7
            if (iLA == 7) {
                c = '0'
            } else if (iLA == 198) {
                c = '1'
            } else if (iLA != 210) {
                switch (iLA) {
                    case 44:
                        c = 1
                        break
                    case 45:
                        c = 2
                        break
                    case 46:
                        c = 3
                        break
                    case 47:
                        c = 4
                        break
                    case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                        c = 5
                        break
                    case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
                    case 50:
                        c = 6
                        break
                    case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
                        break
                    case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
                        c = '\b'
                        break
                    case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
                        c = '\t'
                        break
                    case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
                        c = '\n'
                        break
                    case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
                        c = 11
                        break
                    case R.styleable.AppCompatTheme_borderlessButtonStyle /* 56 */:
                        c = '\f'
                        break
                    case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
                        c = '\r'
                        break
                    case R.styleable.AppCompatTheme_dividerHorizontal /* 58 */:
                        c = 14
                        break
                    case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
                        c = 15
                        break
                    case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                        c = 16
                        break
                    case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
                        c = 17
                        break
                    case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
                        c = 18
                        break
                    case 63:
                        c = 19
                        break
                    case 64:
                        c = 20
                        break
                    case R.styleable.AppCompatTheme_editTextBackground /* 65 */:
                        c = 21
                        break
                    case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                        c = 22
                        break
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                        c = 23
                        break
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                    case R.styleable.AppCompatTheme_textColorSearchUrl /* 69 */:
                        c = 24
                        break
                    case R.styleable.AppCompatTheme_searchViewStyle /* 70 */:
                        c = 25
                        break
                    case R.styleable.AppCompatTheme_listPreferredItemHeight /* 71 */:
                        c = 26
                        break
                    case R.styleable.AppCompatTheme_listPreferredItemHeightSmall /* 72 */:
                        c = 27
                        break
                    case R.styleable.AppCompatTheme_listPreferredItemHeightLarge /* 73 */:
                        c = 28
                        break
                    case R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /* 74 */:
                        c = 29
                        break
                    case R.styleable.AppCompatTheme_listPreferredItemPaddingRight /* 75 */:
                    case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
                        c = 30
                        break
                    case R.styleable.AppCompatTheme_listPopupWindowStyle /* 77 */:
                        c = 31
                        break
                    case R.styleable.AppCompatTheme_textAppearanceListItem /* 78 */:
                        c = ' '
                        break
                    case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                        c = '!'
                        break
                    case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                    case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
                        c = '\"'
                        break
                    case 81:
                        c = '$'
                        break
                    case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
                        c = '#'
                        break
                    case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
                        c = '%'
                        break
                    case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
                        c = '&'
                        break
                    case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
                        c = '\''
                        break
                    case R.styleable.AppCompatTheme_colorAccent /* 87 */:
                        c = '('
                        break
                    case R.styleable.AppCompatTheme_colorControlNormal /* 88 */:
                        c = ')'
                        break
                    case R.styleable.AppCompatTheme_colorControlActivated /* 89 */:
                        c = '*'
                        break
                    case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
                        c = '+'
                        break
                    case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                        c = ','
                        break
                    case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                        c = '-'
                        break
                    case R.styleable.AppCompatTheme_controlBackground /* 93 */:
                        c = '.'
                        break
                    case R.styleable.AppCompatTheme_colorBackgroundFloating /* 94 */:
                        c = '/'
                        break
                    default:
                        throw NoViableAltException("", 55, 0, this.input)
                }
            } else {
                c = '2'
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format10t_in_instruction4089)
                    insn_format10t_return insn_format10t_returnVarInsn_format10t = insn_format10t()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format10t_returnVarInsn_format10t.getTree())
                    break
                case 2:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format10x_in_instruction4095)
                    insn_format10x_return insn_format10x_returnVarInsn_format10x = insn_format10x()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format10x_returnVarInsn_format10x.getTree())
                    break
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format10x_odex_in_instruction4101)
                    insn_format10x_odex_return insn_format10x_odex_returnVarInsn_format10x_odex = insn_format10x_odex()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format10x_odex_returnVarInsn_format10x_odex.getTree())
                    break
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format11n_in_instruction4107)
                    insn_format11n_return insn_format11n_returnVarInsn_format11n = insn_format11n()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format11n_returnVarInsn_format11n.getTree())
                    break
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format11x_in_instruction4113)
                    insn_format11x_return insn_format11x_returnVarInsn_format11x = insn_format11x()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format11x_returnVarInsn_format11x.getTree())
                    break
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format12x_in_instruction4119)
                    insn_format12x_return insn_format12x_returnVarInsn_format12x = insn_format12x()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format12x_returnVarInsn_format12x.getTree())
                    break
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format20bc_in_instruction4125)
                    insn_format20bc_return insn_format20bc_returnVarInsn_format20bc = insn_format20bc()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format20bc_returnVarInsn_format20bc.getTree())
                    break
                case '\b':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format20t_in_instruction4131)
                    insn_format20t_return insn_format20t_returnVarInsn_format20t = insn_format20t()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format20t_returnVarInsn_format20t.getTree())
                    break
                case '\t':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21c_field_in_instruction4137)
                    insn_format21c_field_return insn_format21c_field_returnVarInsn_format21c_field = insn_format21c_field()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21c_field_returnVarInsn_format21c_field.getTree())
                    break
                case '\n':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21c_field_odex_in_instruction4143)
                    insn_format21c_field_odex_return insn_format21c_field_odex_returnVarInsn_format21c_field_odex = insn_format21c_field_odex()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21c_field_odex_returnVarInsn_format21c_field_odex.getTree())
                    break
                case 11:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction4149)
                    insn_format21c_method_handle_return insn_format21c_method_handle_returnVarInsn_format21c_method_handle = insn_format21c_method_handle()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21c_method_handle_returnVarInsn_format21c_method_handle.getTree())
                    break
                case '\f':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21c_method_type_in_instruction4155)
                    insn_format21c_method_type_return insn_format21c_method_type_returnVarInsn_format21c_method_type = insn_format21c_method_type()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21c_method_type_returnVarInsn_format21c_method_type.getTree())
                    break
                case '\r':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21c_string_in_instruction4161)
                    insn_format21c_string_return insn_format21c_string_returnVarInsn_format21c_string = insn_format21c_string()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21c_string_returnVarInsn_format21c_string.getTree())
                    break
                case 14:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21c_type_in_instruction4167)
                    insn_format21c_type_return insn_format21c_type_returnVarInsn_format21c_type = insn_format21c_type()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21c_type_returnVarInsn_format21c_type.getTree())
                    break
                case 15:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21ih_in_instruction4173)
                    insn_format21ih_return insn_format21ih_returnVarInsn_format21ih = insn_format21ih()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21ih_returnVarInsn_format21ih.getTree())
                    break
                case 16:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21lh_in_instruction4179)
                    insn_format21lh_return insn_format21lh_returnVarInsn_format21lh = insn_format21lh()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21lh_returnVarInsn_format21lh.getTree())
                    break
                case 17:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21s_in_instruction4185)
                    insn_format21s_return insn_format21s_returnVarInsn_format21s = insn_format21s()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21s_returnVarInsn_format21s.getTree())
                    break
                case 18:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format21t_in_instruction4191)
                    insn_format21t_return insn_format21t_returnVarInsn_format21t = insn_format21t()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format21t_returnVarInsn_format21t.getTree())
                    break
                case 19:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22b_in_instruction4197)
                    insn_format22b_return insn_format22b_returnVarInsn_format22b = insn_format22b()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22b_returnVarInsn_format22b.getTree())
                    break
                case 20:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22c_field_in_instruction4203)
                    insn_format22c_field_return insn_format22c_field_returnVarInsn_format22c_field = insn_format22c_field()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22c_field_returnVarInsn_format22c_field.getTree())
                    break
                case 21:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22c_field_odex_in_instruction4209)
                    insn_format22c_field_odex_return insn_format22c_field_odex_returnVarInsn_format22c_field_odex = insn_format22c_field_odex()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22c_field_odex_returnVarInsn_format22c_field_odex.getTree())
                    break
                case 22:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22c_type_in_instruction4215)
                    insn_format22c_type_return insn_format22c_type_returnVarInsn_format22c_type = insn_format22c_type()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22c_type_returnVarInsn_format22c_type.getTree())
                    break
                case 23:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22cs_field_in_instruction4221)
                    insn_format22cs_field_return insn_format22cs_field_returnVarInsn_format22cs_field = insn_format22cs_field()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22cs_field_returnVarInsn_format22cs_field.getTree())
                    break
                case 24:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22s_in_instruction4227)
                    insn_format22s_return insn_format22s_returnVarInsn_format22s = insn_format22s()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22s_returnVarInsn_format22s.getTree())
                    break
                case 25:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22t_in_instruction4233)
                    insn_format22t_return insn_format22t_returnVarInsn_format22t = insn_format22t()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22t_returnVarInsn_format22t.getTree())
                    break
                case 26:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format22x_in_instruction4239)
                    insn_format22x_return insn_format22x_returnVarInsn_format22x = insn_format22x()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format22x_returnVarInsn_format22x.getTree())
                    break
                case 27:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format23x_in_instruction4245)
                    insn_format23x_return insn_format23x_returnVarInsn_format23x = insn_format23x()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format23x_returnVarInsn_format23x.getTree())
                    break
                case 28:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format30t_in_instruction4251)
                    insn_format30t_return insn_format30t_returnVarInsn_format30t = insn_format30t()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format30t_returnVarInsn_format30t.getTree())
                    break
                case 29:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format31c_in_instruction4257)
                    insn_format31c_return insn_format31c_returnVarInsn_format31c = insn_format31c()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format31c_returnVarInsn_format31c.getTree())
                    break
                case 30:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format31i_in_instruction4263)
                    insn_format31i_return insn_format31i_returnVarInsn_format31i = insn_format31i()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format31i_returnVarInsn_format31i.getTree())
                    break
                case 31:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format31t_in_instruction4269)
                    insn_format31t_return insn_format31t_returnVarInsn_format31t = insn_format31t()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format31t_returnVarInsn_format31t.getTree())
                    break
                case ' ':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format32x_in_instruction4275)
                    insn_format32x_return insn_format32x_returnVarInsn_format32x = insn_format32x()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format32x_returnVarInsn_format32x.getTree())
                    break
                case '!':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format35c_call_site_in_instruction4281)
                    insn_format35c_call_site_return insn_format35c_call_site_returnVarInsn_format35c_call_site = insn_format35c_call_site()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format35c_call_site_returnVarInsn_format35c_call_site.getTree())
                    break
                case '\"':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format35c_method_in_instruction4287)
                    insn_format35c_method_return insn_format35c_method_returnVarInsn_format35c_method = insn_format35c_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format35c_method_returnVarInsn_format35c_method.getTree())
                    break
                case '#':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format35c_type_in_instruction4293)
                    insn_format35c_type_return insn_format35c_type_returnVarInsn_format35c_type = insn_format35c_type()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format35c_type_returnVarInsn_format35c_type.getTree())
                    break
                case '$':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format35c_method_odex_in_instruction4299)
                    insn_format35c_method_odex_return insn_format35c_method_odex_returnVarInsn_format35c_method_odex = insn_format35c_method_odex()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format35c_method_odex_returnVarInsn_format35c_method_odex.getTree())
                    break
                case '%':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format35mi_method_in_instruction4305)
                    insn_format35mi_method_return insn_format35mi_method_returnVarInsn_format35mi_method = insn_format35mi_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format35mi_method_returnVarInsn_format35mi_method.getTree())
                    break
                case '&':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format35ms_method_in_instruction4311)
                    insn_format35ms_method_return insn_format35ms_method_returnVarInsn_format35ms_method = insn_format35ms_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format35ms_method_returnVarInsn_format35ms_method.getTree())
                    break
                case '\'':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction4317)
                    insn_format3rc_call_site_return insn_format3rc_call_site_returnVarInsn_format3rc_call_site = insn_format3rc_call_site()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format3rc_call_site_returnVarInsn_format3rc_call_site.getTree())
                    break
                case '(':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format3rc_method_in_instruction4323)
                    insn_format3rc_method_return insn_format3rc_method_returnVarInsn_format3rc_method = insn_format3rc_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format3rc_method_returnVarInsn_format3rc_method.getTree())
                    break
                case ')':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format3rc_method_odex_in_instruction4329)
                    insn_format3rc_method_odex_return insn_format3rc_method_odex_returnVarInsn_format3rc_method_odex = insn_format3rc_method_odex()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format3rc_method_odex_returnVarInsn_format3rc_method_odex.getTree())
                    break
                case '*':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format3rc_type_in_instruction4335)
                    insn_format3rc_type_return insn_format3rc_type_returnVarInsn_format3rc_type = insn_format3rc_type()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format3rc_type_returnVarInsn_format3rc_type.getTree())
                    break
                case '+':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format3rmi_method_in_instruction4341)
                    insn_format3rmi_method_return insn_format3rmi_method_returnVarInsn_format3rmi_method = insn_format3rmi_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format3rmi_method_returnVarInsn_format3rmi_method.getTree())
                    break
                case ',':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format3rms_method_in_instruction4347)
                    insn_format3rms_method_return insn_format3rms_method_returnVarInsn_format3rms_method = insn_format3rms_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format3rms_method_returnVarInsn_format3rms_method.getTree())
                    break
                case '-':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format45cc_method_in_instruction4353)
                    insn_format45cc_method_return insn_format45cc_method_returnVarInsn_format45cc_method = insn_format45cc_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format45cc_method_returnVarInsn_format45cc_method.getTree())
                    break
                case '.':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format4rcc_method_in_instruction4359)
                    insn_format4rcc_method_return insn_format4rcc_method_returnVarInsn_format4rcc_method = insn_format4rcc_method()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format4rcc_method_returnVarInsn_format4rcc_method.getTree())
                    break
                case '/':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_format51l_in_instruction4365)
                    insn_format51l_return insn_format51l_returnVarInsn_format51l = insn_format51l()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_format51l_returnVarInsn_format51l.getTree())
                    break
                case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_array_data_directive_in_instruction4371)
                    insn_array_data_directive_return insn_array_data_directive_returnVarInsn_array_data_directive = insn_array_data_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_array_data_directive_returnVarInsn_array_data_directive.getTree())
                    break
                case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction4377)
                    insn_packed_switch_directive_return insn_packed_switch_directive_returnVarInsn_packed_switch_directive = insn_packed_switch_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_packed_switch_directive_returnVarInsn_packed_switch_directive.getTree())
                    break
                case '2':
                    commonTree = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction4383)
                    insn_sparse_switch_directive_return insn_sparse_switch_directive_returnVarInsn_sparse_switch_directive = insn_sparse_switch_directive()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree, insn_sparse_switch_directive_returnVarInsn_sparse_switch_directive.getTree())
                    break
                default:
                    commonTree = null
                    break
            }
            instruction_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            instruction_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, instruction_returnVar.start, instruction_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            instruction_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, instruction_returnVar.start, tokenStream.LT(-1), e)
        }
        return instruction_returnVar
    }

    public final instruction_format12x_return instruction_format12x() throws RecognitionException {
        Char c
        instruction_format12x_return instruction_format12x_returnVar = instruction_format12x_return()
        instruction_format12x_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT12x_OR_ID")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 49) {
                c = 1
            } else {
                if (iLA != 50) {
                    throw NoViableAltException("", 51, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 49, FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x4003)))
            } else if (c == 2) {
                Token token = (Token) match(this.input, 50, FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x4009)
                rewriteRuleTokenStream.add(token)
                instruction_format12x_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format12x_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(49, token))
                instruction_format12x_returnVar.tree = commonTree
            }
            instruction_format12x_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            instruction_format12x_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, instruction_format12x_returnVar.start, instruction_format12x_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            instruction_format12x_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, instruction_format12x_returnVar.start, tokenStream.LT(-1), e)
        }
        return instruction_format12x_returnVar
    }

    public final instruction_format22s_return instruction_format22s() throws RecognitionException {
        Char c
        instruction_format22s_return instruction_format22s_returnVar = instruction_format22s_return()
        instruction_format22s_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22s_OR_ID")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 68) {
                c = 1
            } else {
                if (iLA != 69) {
                    throw NoViableAltException("", 52, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 68, FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s4024)))
            } else if (c == 2) {
                Token token = (Token) match(this.input, 69, FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s4030)
                rewriteRuleTokenStream.add(token)
                instruction_format22s_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format22s_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(68, token))
                instruction_format22s_returnVar.tree = commonTree
            }
            instruction_format22s_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            instruction_format22s_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, instruction_format22s_returnVar.start, instruction_format22s_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            instruction_format22s_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, instruction_format22s_returnVar.start, tokenStream.LT(-1), e)
        }
        return instruction_format22s_returnVar
    }

    public final instruction_format31i_return instruction_format31i() throws RecognitionException {
        Char c
        instruction_format31i_return instruction_format31i_returnVar = instruction_format31i_return()
        instruction_format31i_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31i_OR_ID")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 75) {
                c = 1
            } else {
                if (iLA != 76) {
                    throw NoViableAltException("", 53, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 75, FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i4045)))
            } else if (c == 2) {
                Token token = (Token) match(this.input, 76, FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i4051)
                rewriteRuleTokenStream.add(token)
                instruction_format31i_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format31i_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(75, token))
                instruction_format31i_returnVar.tree = commonTree
            }
            instruction_format31i_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            instruction_format31i_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, instruction_format31i_returnVar.start, instruction_format31i_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            instruction_format31i_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, instruction_format31i_returnVar.start, tokenStream.LT(-1), e)
        }
        return instruction_format31i_returnVar
    }

    public final instruction_format35c_method_return instruction_format35c_method() throws RecognitionException {
        Char c
        instruction_format35c_method_return instruction_format35c_method_returnVar = instruction_format35c_method_return()
        instruction_format35c_method_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 80) {
                c = 1
            } else {
                if (iLA != 82) {
                    throw NoViableAltException("", 54, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_instruction_format35c_method4068)))
            } else if (c == 2) {
                Token token = (Token) match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_instruction_format35c_method4074)
                rewriteRuleTokenStream.add(token)
                instruction_format35c_method_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format35c_method_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(80, token))
                instruction_format35c_method_returnVar.tree = commonTree
            }
            instruction_format35c_method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            instruction_format35c_method_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, instruction_format35c_method_returnVar.start, instruction_format35c_method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            instruction_format35c_method_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, instruction_format35c_method_returnVar.start, tokenStream.LT(-1), e)
        }
        return instruction_format35c_method_returnVar
    }

    public final integer_literal_return integer_literal() throws RecognitionException {
        Char c
        integer_literal_return integer_literal_returnVar = integer_literal_return()
        integer_literal_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token NEGATIVE_INTEGER_LITERAL")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token POSITIVE_INTEGER_LITERAL")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 201) {
                c = 1
            } else {
                if (iLA != 194) {
                    throw NoViableAltException("", 19, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                Token token = (Token) match(this.input, 201, FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2579)
                rewriteRuleTokenStream2.add(token)
                integer_literal_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", integer_literal_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(95, token))
                integer_literal_returnVar.tree = commonTree
            } else if (c == 2) {
                Token token2 = (Token) match(this.input, 194, FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2590)
                rewriteRuleTokenStream.add(token2)
                integer_literal_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", integer_literal_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor2 = this.adaptor
                treeAdaptor2.addChild(commonTree, (CommonTree) treeAdaptor2.create(95, token2))
                integer_literal_returnVar.tree = commonTree
            }
            integer_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            integer_literal_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, integer_literal_returnVar.start, integer_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            integer_literal_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, integer_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return integer_literal_returnVar
    }

    public final integral_literal_return integral_literal() throws RecognitionException {
        Char c
        CommonTree commonTree
        integral_literal_return integral_literal_returnVar = integral_literal_return()
        integral_literal_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 12) {
                c = 5
            } else if (iLA == 15) {
                c = 4
            } else if (iLA == 189) {
                c = 1
            } else if (iLA == 194 || iLA == 201) {
                c = 2
            } else {
                if (iLA != 207) {
                    throw NoViableAltException("", 23, 0, this.input)
                }
                c = 3
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_integral_literal2762)))
            } else if (c == 2) {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                pushFollow(FOLLOW_integer_literal_in_integral_literal2768)
                integer_literal_return integer_literal_returnVarInteger_literal = integer_literal()
                this.state._fsp--
                this.adaptor.addChild(commonTree2, integer_literal_returnVarInteger_literal.getTree())
                commonTree = commonTree2
            } else if (c == 3) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_integral_literal2774)))
            } else if (c == 4) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_integral_literal2780)))
            } else if (c != 5) {
                commonTree = null
            } else {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_integral_literal2786)))
            }
            integral_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            integral_literal_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, integral_literal_returnVar.start, integral_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            integral_literal_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, integral_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return integral_literal_returnVar
    }

    public final label_return label() throws RecognitionException {
        label_return label_returnVar = label_return()
        label_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COLON")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule simple_name")
        try {
            Token token = (Token) match(this.input, 20, FOLLOW_COLON_in_label3365)
            rewriteRuleTokenStream.add(token)
            pushFollow(FOLLOW_simple_name_in_label3367)
            simple_name_return simple_name_returnVarSimple_name = simple_name()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(simple_name_returnVarSimple_name.getTree())
            label_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", label_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(122, token, "I_LABEL"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            label_returnVar.tree = commonTree
            label_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            label_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, label_returnVar.start, label_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            label_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, label_returnVar.start, tokenStream.LT(-1), e)
        }
        return label_returnVar
    }

    public final label_ref_return label_ref() throws RecognitionException {
        label_ref_return label_ref_returnVar = label_ref_return()
        label_ref_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COLON")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule simple_name")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 20, FOLLOW_COLON_in_label_ref3386))
            pushFollow(FOLLOW_simple_name_in_label_ref3388)
            simple_name_return simple_name_returnVarSimple_name = simple_name()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(simple_name_returnVarSimple_name.getTree())
            label_ref_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", label_ref_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree())
            label_ref_returnVar.tree = commonTree
            label_ref_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            label_ref_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, label_ref_returnVar.start, label_ref_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            label_ref_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, label_ref_returnVar.start, tokenStream.LT(-1), e)
        }
        return label_ref_returnVar
    }

    public final line_directive_return line_directive() throws RecognitionException {
        line_directive_return line_directive_returnVar = line_directive_return()
        line_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token LINE_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 186, FOLLOW_LINE_DIRECTIVE_in_line_directive3755))
            pushFollow(FOLLOW_integral_literal_in_line_directive3757)
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree())
            line_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", line_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(123, line_directive_returnVar.start, "I_LINE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            line_directive_returnVar.tree = commonTree
            line_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            line_directive_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, line_directive_returnVar.start, line_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            line_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, line_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return line_directive_returnVar
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0062 A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:619:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:620:0x05ce A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:621:0x05f2 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:622:0x0616 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:623:0x063a A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:624:0x065e A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:625:0x0682 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:626:0x06a6 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:627:0x06c9 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:628:0x06ec A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:629:0x070f A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:630:0x0732 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:631:0x0756 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:632:0x077a A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:633:0x079c A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:634:0x07be A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:635:0x07e1 A[Catch: all -> 0x081e, RecognitionException -> 0x0821, TryCatch #3 {RecognitionException -> 0x0821, blocks: (B:3:0x0011, B:27:0x0059, B:28:0x0061, B:30:0x0066, B:111:0x010b, B:116:0x0120, B:117:0x0125, B:123:0x0135, B:204:0x01da, B:209:0x01ef, B:210:0x01f4, B:214:0x0200, B:295:0x02a5, B:300:0x02bc, B:301:0x02c1, B:303:0x02c8, B:384:0x0370, B:389:0x0386, B:390:0x038b, B:391:0x038c, B:399:0x03a8, B:404:0x03bf, B:405:0x03c4, B:406:0x03c5, B:414:0x03dc, B:419:0x03f3, B:420:0x03f8, B:422:0x03fc, B:430:0x0413, B:435:0x042a, B:436:0x042f, B:438:0x0434, B:519:0x04db, B:524:0x04f2, B:525:0x04f7, B:618:0x05c8, B:636:0x0802, B:620:0x05ce, B:621:0x05f2, B:622:0x0616, B:623:0x063a, B:624:0x065e, B:625:0x0682, B:626:0x06a6, B:627:0x06c9, B:628:0x06ec, B:629:0x070f, B:630:0x0732, B:631:0x0756, B:632:0x077a, B:633:0x079c, B:634:0x07be, B:635:0x07e1, B:528:0x04fe, B:609:0x05a4, B:614:0x05bb, B:615:0x05c0), top: B:653:0x0011, outer: #10 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.literal_return literal() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 2340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.literal():org.jf.smali.smaliParser$literal_return")
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x011e A[Catch: all -> 0x021f, RecognitionException -> 0x0221, TryCatch #0 {RecognitionException -> 0x0221, blocks: (B:3:0x0058, B:48:0x015b, B:50:0x01af, B:51:0x01b8, B:53:0x01c1, B:54:0x01ca, B:56:0x01d3, B:57:0x01dc, B:59:0x01e5, B:60:0x01ee, B:9:0x008b, B:18:0x00b1, B:21:0x00cd, B:40:0x012c, B:47:0x013e, B:38:0x0106, B:39:0x011e, B:31:0x00f5, B:32:0x00fe, B:19:0x00bf, B:61:0x0215, B:62:0x021e), top: B:71:0x0058, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x013e A[Catch: all -> 0x021f, RecognitionException -> 0x0221, TryCatch #0 {RecognitionException -> 0x0221, blocks: (B:3:0x0058, B:48:0x015b, B:50:0x01af, B:51:0x01b8, B:53:0x01c1, B:54:0x01ca, B:56:0x01d3, B:57:0x01dc, B:59:0x01e5, B:60:0x01ee, B:9:0x008b, B:18:0x00b1, B:21:0x00cd, B:40:0x012c, B:47:0x013e, B:38:0x0106, B:39:0x011e, B:31:0x00f5, B:32:0x00fe, B:19:0x00bf, B:61:0x0215, B:62:0x021e), top: B:71:0x0058, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.local_directive_return local_directive() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 577
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.local_directive():org.jf.smali.smaliParser$local_directive_return")
    }

    public final member_name_return member_name() throws RecognitionException {
        Char c
        member_name_return member_name_returnVar = member_name_return()
        member_name_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token MEMBER_NAME")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 4 || iLA == 6 || iLA == 11 || iLA == 24 || ((iLA >= 40 && iLA <= 41) || ((iLA >= 44 && iLA <= 46) || iLA == 48 || iLA == 50 || ((iLA >= 53 && iLA <= 58) || iLA == 62 || ((iLA >= 64 && iLA <= 67) || ((iLA >= 69 && iLA <= 70) || iLA == 72 || ((iLA >= 76 && iLA <= 77) || ((iLA >= 79 && iLA <= 85) || ((iLA >= 92 && iLA <= 94) || ((iLA >= 192 && iLA <= 195) || ((iLA >= 200 && iLA <= 202) || iLA == 204 || iLA == 208 || (iLA >= 214 && iLA <= 215)))))))))))) {
                c = 1
            } else {
                if (iLA != 190) {
                    throw NoViableAltException("", 12, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                pushFollow(FOLLOW_simple_name_in_member_name2405)
                simple_name_return simple_name_returnVarSimple_name = simple_name()
                this.state._fsp--
                this.adaptor.addChild(commonTree, simple_name_returnVarSimple_name.getTree())
            } else if (c == 2) {
                Token token = (Token) match(this.input, 190, FOLLOW_MEMBER_NAME_in_member_name2411)
                rewriteRuleTokenStream.add(token)
                member_name_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", member_name_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(208, token))
                member_name_returnVar.tree = commonTree
            }
            member_name_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            member_name_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, member_name_returnVar.start, member_name_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            member_name_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, member_name_returnVar.start, tokenStream.LT(-1), e)
        }
        return member_name_returnVar
    }

    public final method_return method() throws RecognitionException {
        method_return method_returnVar = method_return()
        method_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token END_METHOD_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token METHOD_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule access_or_restriction_list")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule member_name")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = RewriteRuleSubtreeStream(this.adaptor, "rule statements_and_directives")
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 191, FOLLOW_METHOD_DIRECTIVE_in_method1600))
            pushFollow(FOLLOW_access_or_restriction_list_in_method1602)
            access_or_restriction_list_return access_or_restriction_list_returnVarAccess_or_restriction_list = access_or_restriction_list()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(access_or_restriction_list_returnVarAccess_or_restriction_list.getTree())
            pushFollow(FOLLOW_member_name_in_method1604)
            member_name_return member_name_returnVarMember_name = member_name()
            this.state._fsp--
            rewriteRuleSubtreeStream3.add(member_name_returnVarMember_name.getTree())
            pushFollow(FOLLOW_method_prototype_in_method1606)
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(method_prototype_returnVarMethod_prototype.getTree())
            pushFollow(FOLLOW_statements_and_directives_in_method1608)
            statements_and_directives_return statements_and_directives_returnVarStatements_and_directives = statements_and_directives()
            this.state._fsp--
            rewriteRuleSubtreeStream4.add(statements_and_directives_returnVarStatements_and_directives.getTree())
            rewriteRuleTokenStream.add((Token) match(this.input, 29, FOLLOW_END_METHOD_DIRECTIVE_in_method1614))
            method_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(126, method_returnVar.start, "I_METHOD"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream4.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            method_returnVar.tree = commonTree
            method_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            method_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, method_returnVar.start, method_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            method_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, method_returnVar.start, tokenStream.LT(-1), e)
        }
        return method_returnVar
    }

    public final method_handle_literal_return method_handle_literal() throws RecognitionException {
        method_handle_literal_return method_handle_literal_returnVar = method_handle_literal_return()
        method_handle_literal_returnVar.start = this.input.LT(1)
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_handle_reference")
        try {
            pushFollow(FOLLOW_method_handle_reference_in_method_handle_literal3283)
            method_handle_reference_return method_handle_reference_returnVarMethod_handle_reference = method_handle_reference()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_handle_reference_returnVarMethod_handle_reference.getTree())
            method_handle_literal_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_literal_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(R.styleable.AppCompatTheme_spinnerStyle, "I_ENCODED_METHOD_HANDLE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree, commonTree3)
            method_handle_literal_returnVar.tree = commonTree
            method_handle_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            method_handle_literal_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, method_handle_literal_returnVar.start, method_handle_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            method_handle_literal_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, method_handle_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return method_handle_literal_returnVar
    }

    public final method_handle_reference_return method_handle_reference() throws RecognitionException {
        Char c
        method_handle_reference_return method_handle_reference_returnVar = method_handle_reference_return()
        method_handle_reference_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token AT")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token METHOD_HANDLE_TYPE_FIELD")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token METHOD_HANDLE_TYPE_METHOD")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_reference")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule field_reference")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 82) {
                c = 3
            } else if (iLA == 192) {
                c = 1
            } else {
                if (iLA != 193) {
                    throw NoViableAltException("", 34, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                rewriteRuleTokenStream3.add((Token) match(this.input, 192, FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_method_handle_reference3231))
                rewriteRuleTokenStream.add((Token) match(this.input, 10, FOLLOW_AT_in_method_handle_reference3233))
                pushFollow(FOLLOW_field_reference_in_method_handle_reference3235)
                field_reference_return field_reference_returnVarField_reference = field_reference()
                this.state._fsp--
                rewriteRuleSubtreeStream2.add(field_reference_returnVarField_reference.getTree())
                method_handle_reference_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_reference_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, rewriteRuleTokenStream3.nextNode())
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream2.nextTree())
                method_handle_reference_returnVar.tree = commonTree
            } else if (c == 2) {
                rewriteRuleTokenStream4.add((Token) match(this.input, 193, FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_method_handle_reference3247))
                rewriteRuleTokenStream.add((Token) match(this.input, 10, FOLLOW_AT_in_method_handle_reference3249))
                pushFollow(FOLLOW_method_reference_in_method_handle_reference3251)
                method_reference_return method_reference_returnVarMethod_reference = method_reference()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree())
                method_handle_reference_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_reference_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, rewriteRuleTokenStream4.nextNode())
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree())
                method_handle_reference_returnVar.tree = commonTree
            } else if (c == 3) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_method_handle_reference3263))
                rewriteRuleTokenStream.add((Token) match(this.input, 10, FOLLOW_AT_in_method_handle_reference3265))
                pushFollow(FOLLOW_method_reference_in_method_handle_reference3267)
                method_reference_return method_reference_returnVarMethod_reference2 = method_reference()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference2.getTree())
                method_handle_reference_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_reference_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, rewriteRuleTokenStream2.nextNode())
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree())
                method_handle_reference_returnVar.tree = commonTree
            }
            method_handle_reference_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            method_handle_reference_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, method_handle_reference_returnVar.start, method_handle_reference_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            method_handle_reference_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, method_handle_reference_returnVar.start, tokenStream.LT(-1), e)
        }
        return method_handle_reference_returnVar
    }

    public final method_prototype_return method_prototype() throws RecognitionException {
        method_prototype_return method_prototype_returnVar = method_prototype_return()
        method_prototype_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token OPEN_PAREN")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token CLOSE_PAREN")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule type_descriptor")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule param_list")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 197, FOLLOW_OPEN_PAREN_in_method_prototype2426))
            pushFollow(FOLLOW_param_list_in_method_prototype2428)
            param_list_return param_list_returnVarParam_list = param_list()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(param_list_returnVarParam_list.getTree())
            rewriteRuleTokenStream2.add((Token) match(this.input, 19, FOLLOW_CLOSE_PAREN_in_method_prototype2430))
            pushFollow(FOLLOW_type_descriptor_in_method_prototype2432)
            type_descriptor_return type_descriptor_returnVarType_descriptor = type_descriptor()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(type_descriptor_returnVarType_descriptor.getTree())
            method_prototype_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_prototype_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(128, method_prototype_returnVar.start, "I_METHOD_PROTOTYPE"), commonTree2)
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor2 = this.adaptor
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(129, "I_METHOD_RETURN_TYPE"), commonTree4)
            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree())
            this.adaptor.addChild(commonTree3, commonTree5)
            if (rewriteRuleSubtreeStream2.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree())
            }
            rewriteRuleSubtreeStream2.reset()
            this.adaptor.addChild(commonTree, commonTree3)
            method_prototype_returnVar.tree = commonTree
            method_prototype_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree6 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            method_prototype_returnVar.tree = commonTree6
            this.adaptor.setTokenBoundaries(commonTree6, method_prototype_returnVar.start, method_prototype_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            method_prototype_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, method_prototype_returnVar.start, tokenStream.LT(-1), e)
        }
        return method_prototype_returnVar
    }

    public final method_reference_return method_reference() throws RecognitionException {
        method_reference_return method_reference_returnVar = method_reference_return()
        method_reference_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token ARROW")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule member_name")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor")
        try {
            Int iLA = this.input.LA(1)
            if (((iLA == 8 || iLA == 16) ? (Char) 1 : (Char) 2) == 1) {
                pushFollow(FOLLOW_reference_type_descriptor_in_method_reference3304)
                reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor()
                this.state._fsp--
                rewriteRuleSubtreeStream3.add(reference_type_descriptor_returnVarReference_type_descriptor.getTree())
                rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_method_reference3306))
            }
            pushFollow(FOLLOW_member_name_in_method_reference3310)
            member_name_return member_name_returnVarMember_name = member_name()
            this.state._fsp--
            rewriteRuleSubtreeStream2.add(member_name_returnVarMember_name.getTree())
            pushFollow(FOLLOW_method_prototype_in_method_reference3312)
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype()
            this.state._fsp--
            rewriteRuleSubtreeStream.add(method_prototype_returnVarMethod_prototype.getTree())
            method_reference_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_reference_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            if (rewriteRuleSubtreeStream3.hasNext()) {
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream3.nextTree())
            }
            rewriteRuleSubtreeStream3.reset()
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream2.nextTree())
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree())
            method_reference_returnVar.tree = commonTree
            method_reference_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            method_reference_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, method_reference_returnVar.start, method_reference_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            method_reference_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, method_reference_returnVar.start, tokenStream.LT(-1), e)
        }
        return method_reference_returnVar
    }

    public final nonvoid_type_descriptor_return nonvoid_type_descriptor() throws RecognitionException {
        Char c
        CommonTree commonTree
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
                    throw NoViableAltException("", 17, 0, this.input)
                }
                c = 1
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor2541)))
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor2547)))
            } else if (c != 3) {
                commonTree = null
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                pushFollow(FOLLOW_array_descriptor_in_nonvoid_type_descriptor2553)
                array_descriptor_return array_descriptor_returnVarArray_descriptor = array_descriptor()
                this.state._fsp--
                this.adaptor.addChild(commonTree2, array_descriptor_returnVarArray_descriptor.getTree())
                commonTree = commonTree2
            }
            nonvoid_type_descriptor_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            nonvoid_type_descriptor_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, nonvoid_type_descriptor_returnVar.start, nonvoid_type_descriptor_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            nonvoid_type_descriptor_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, nonvoid_type_descriptor_returnVar.start, tokenStream.LT(-1), e)
        }
        return nonvoid_type_descriptor_returnVar
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a4 A[Catch: all -> 0x00e2, RecognitionException -> 0x00e4, TryCatch #0 {RecognitionException -> 0x00e4, blocks: (B:3:0x000f, B:25:0x0042, B:26:0x0045, B:27:0x0051, B:39:0x00c6, B:35:0x005f, B:37:0x0082, B:38:0x00a4), top: B:49:0x000f, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.ordered_method_item_return ordered_method_item() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.ordered_method_item():org.jf.smali.smaliParser$ordered_method_item_return")
    }

    public final param_list_return param_list() throws RecognitionException {
        Int i
        Char c
        CommonTree commonTree
        CommonTree commonTree2
        param_list_return param_list_returnVar = param_list_return()
        param_list_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            i = 0
            if (iLA == 200) {
                c = 1
            } else {
                if (iLA != 8 && iLA != 16 && iLA != 19 && iLA != 202) {
                    throw NoViableAltException("", 15, 0, this.input)
                }
                c = 2
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            param_list_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, param_list_returnVar.start, tokenStream.LT(-1), e)
        }
        if (c == 1) {
            commonTree = (CommonTree) this.adaptor.nil()
            while (true) {
                if ((this.input.LA(1) == 200 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_param_list_or_id_primitive_type_in_param_list2477)
                param_list_or_id_primitive_type_return param_list_or_id_primitive_type_returnVarParam_list_or_id_primitive_type = param_list_or_id_primitive_type()
                this.state._fsp--
                this.adaptor.addChild(commonTree, param_list_or_id_primitive_type_returnVarParam_list_or_id_primitive_type.getTree())
                i++
            }
            if (i < 1) {
                throw EarlyExitException(13, this.input)
            }
        } else {
            if (c != 2) {
                commonTree2 = null
                param_list_returnVar.stop = this.input.LT(-1)
                CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree2)
                param_list_returnVar.tree = commonTree3
                this.adaptor.setTokenBoundaries(commonTree3, param_list_returnVar.start, param_list_returnVar.stop)
                return param_list_returnVar
            }
            commonTree = (CommonTree) this.adaptor.nil()
            while (true) {
                Int iLA2 = this.input.LA(1)
                if (((iLA2 == 8 || iLA2 == 16 || iLA2 == 202) ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                pushFollow(FOLLOW_nonvoid_type_descriptor_in_param_list2484)
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor()
                this.state._fsp--
                this.adaptor.addChild(commonTree, nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree())
            }
        }
        commonTree2 = commonTree
        param_list_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree32 = (CommonTree) this.adaptor.rulePostProcessing(commonTree2)
        param_list_returnVar.tree = commonTree32
        this.adaptor.setTokenBoundaries(commonTree32, param_list_returnVar.start, param_list_returnVar.stop)
        return param_list_returnVar
    }

    public final param_list_or_id_return param_list_or_id() throws RecognitionException {
        CommonTree commonTree
        Int i
        param_list_or_id_return param_list_or_id_returnVar = param_list_or_id_return()
        param_list_or_id_returnVar.start = this.input.LT(1)
        try {
            commonTree = (CommonTree) this.adaptor.nil()
            i = 0
            while (true) {
                if ((this.input.LA(1) == 200 ? (Char) 1 : (Char) 2) != 1) {
                    break
                }
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 200, FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id1876)))
                i++
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            param_list_or_id_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, param_list_or_id_returnVar.start, tokenStream.LT(-1), e)
        }
        if (i < 1) {
            throw EarlyExitException(10, this.input)
        }
        param_list_or_id_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
        param_list_or_id_returnVar.tree = commonTree2
        this.adaptor.setTokenBoundaries(commonTree2, param_list_or_id_returnVar.start, param_list_or_id_returnVar.stop)
        return param_list_or_id_returnVar
    }

    public final param_list_or_id_primitive_type_return param_list_or_id_primitive_type() throws RecognitionException {
        param_list_or_id_primitive_type_return param_list_or_id_primitive_type_returnVar = param_list_or_id_primitive_type_return()
        param_list_or_id_primitive_type_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token PARAM_LIST_OR_ID_PRIMITIVE_TYPE")
        try {
            Token token = (Token) match(this.input, 200, FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id_primitive_type2462)
            rewriteRuleTokenStream.add(token)
            param_list_or_id_primitive_type_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", param_list_or_id_primitive_type_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(202, token))
            param_list_or_id_primitive_type_returnVar.tree = commonTree
            param_list_or_id_primitive_type_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            param_list_or_id_primitive_type_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, param_list_or_id_primitive_type_returnVar.start, param_list_or_id_primitive_type_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            param_list_or_id_primitive_type_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, param_list_or_id_primitive_type_returnVar.start, tokenStream.LT(-1), e)
        }
        return param_list_or_id_primitive_type_returnVar
    }

    public final parameter_directive_return parameter_directive() throws RecognitionException {
        Char c
        parameter_directive_return parameter_directive_returnVar = parameter_directive_return()
        parameter_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token PARAMETER_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL")
        RewriteRuleTokenStream rewriteRuleTokenStream5 = RewriteRuleTokenStream(this.adaptor, "token END_PARAMETER_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule annotation")
        ArrayList arrayList = ArrayList()
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 199, FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3605))
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_parameter_directive3607))
            if ((this.input.LA(1) == 21 ? (Char) 1 : (Char) 2) == 1) {
                rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_parameter_directive3610))
                rewriteRuleTokenStream4.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_parameter_directive3612))
            }
            while (this.dfa43.predict(this.input) == 1) {
                if (this.input.LA(1) != 5) {
                    throw FailedPredicateException(this.input, "parameter_directive", "input.LA(1) == ANNOTATION_DIRECTIVE")
                }
                pushFollow(FOLLOW_annotation_in_parameter_directive3623)
                annotation_return annotation_returnVarAnnotation = annotation()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(annotation_returnVarAnnotation.getTree())
                arrayList.add((CommonTree) annotation_returnVarAnnotation.getTree())
            }
            Int iLA = this.input.LA(1)
            if (iLA == 31) {
                c = 1
            } else {
                if (iLA != 5 && iLA != 7 && ((iLA < 13 || iLA > 14) && iLA != 20 && ((iLA < 28 || iLA > 29) && iLA != 35 && ((iLA < 44 || iLA > 94) && ((iLA < 186 || iLA > 188) && ((iLA < 198 || iLA > 199) && iLA != 203 && ((iLA < 205 || iLA > 206) && (iLA < 209 || iLA > 210)))))))) {
                    throw NoViableAltException("", 44, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                rewriteRuleTokenStream5.add((Token) match(this.input, 31, FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3636))
                parameter_directive_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", parameter_directive_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(133, parameter_directive_returnVar.start, "I_PARAMETER"), commonTree2)
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
                if (rewriteRuleTokenStream4.hasNext()) {
                    this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
                }
                rewriteRuleTokenStream4.reset()
                CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor2 = this.adaptor
                CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(100, "I_ANNOTATIONS"), commonTree4)
                while (rewriteRuleSubtreeStream.hasNext()) {
                    this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree())
                }
                rewriteRuleSubtreeStream.reset()
                this.adaptor.addChild(commonTree3, commonTree5)
                this.adaptor.addChild(commonTree, commonTree3)
                parameter_directive_returnVar.tree = commonTree
            } else if (c == 2) {
                this.statements_and_directives_stack.peek().methodAnnotations.addAll(arrayList)
                parameter_directive_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", parameter_directive_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                CommonTree commonTree6 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor3 = this.adaptor
                CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(133, parameter_directive_returnVar.start, "I_PARAMETER"), commonTree6)
                this.adaptor.addChild(commonTree7, rewriteRuleTokenStream2.nextNode())
                if (rewriteRuleTokenStream4.hasNext()) {
                    this.adaptor.addChild(commonTree7, rewriteRuleTokenStream4.nextNode())
                }
                rewriteRuleTokenStream4.reset()
                CommonTree commonTree8 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor4 = this.adaptor
                this.adaptor.addChild(commonTree7, (CommonTree) treeAdaptor4.becomeRoot((CommonTree) treeAdaptor4.create(100, "I_ANNOTATIONS"), commonTree8))
                this.adaptor.addChild(commonTree, commonTree7)
                parameter_directive_returnVar.tree = commonTree
            }
            parameter_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree9 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            parameter_directive_returnVar.tree = commonTree9
            this.adaptor.setTokenBoundaries(commonTree9, parameter_directive_returnVar.start, parameter_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor5 = this.adaptor
            TokenStream tokenStream = this.input
            parameter_directive_returnVar.tree = (CommonTree) treeAdaptor5.errorNode(tokenStream, parameter_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return parameter_directive_returnVar
    }

    public final parsed_integer_literal_return parsed_integer_literal() throws RecognitionException {
        parsed_integer_literal_return parsed_integer_literal_returnVar = parsed_integer_literal_return()
        parsed_integer_literal_returnVar.start = this.input.LT(1)
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            pushFollow(FOLLOW_integer_literal_in_parsed_integer_literal2750)
            integer_literal_return integer_literal_returnVarInteger_literal = integer_literal()
            this.state._fsp--
            this.adaptor.addChild(commonTree, integer_literal_returnVarInteger_literal.getTree())
            parsed_integer_literal_returnVar.value = LiteralTools.parseInt(this.input.toString(integer_literal_returnVarInteger_literal.start, integer_literal_returnVarInteger_literal.stop))
            parsed_integer_literal_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree2 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            parsed_integer_literal_returnVar.tree = commonTree2
            this.adaptor.setTokenBoundaries(commonTree2, parsed_integer_literal_returnVar.start, parsed_integer_literal_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            parsed_integer_literal_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, parsed_integer_literal_returnVar.start, tokenStream.LT(-1), e)
        }
        return parsed_integer_literal_returnVar
    }

    public final prologue_directive_return prologue_directive() throws RecognitionException {
        prologue_directive_return prologue_directive_returnVar = prologue_directive_return()
        prologue_directive_returnVar.start = this.input.LT(1)
        try {
            RewriteRuleTokenStream(this.adaptor, "token PROLOGUE_DIRECTIVE").add((Token) match(this.input, 203, FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3934))
            prologue_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", prologue_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            this.adaptor.addChild(commonTree, (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(136, prologue_directive_returnVar.start, "I_PROLOGUE"), commonTree2))
            prologue_directive_returnVar.tree = commonTree
            prologue_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            prologue_directive_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, prologue_directive_returnVar.start, prologue_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            prologue_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, prologue_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return prologue_directive_returnVar
    }

    public final reference_type_descriptor_return reference_type_descriptor() throws RecognitionException {
        Char c
        CommonTree commonTree
        reference_type_descriptor_return reference_type_descriptor_returnVar = reference_type_descriptor_return()
        reference_type_descriptor_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 16) {
                c = 1
            } else {
                if (iLA != 8) {
                    throw NoViableAltException("", 18, 0, this.input)
                }
                c = 2
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor2563)))
            } else if (c != 2) {
                commonTree = null
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                pushFollow(FOLLOW_array_descriptor_in_reference_type_descriptor2569)
                array_descriptor_return array_descriptor_returnVarArray_descriptor = array_descriptor()
                this.state._fsp--
                this.adaptor.addChild(commonTree2, array_descriptor_returnVarArray_descriptor.getTree())
                commonTree = commonTree2
            }
            reference_type_descriptor_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            reference_type_descriptor_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, reference_type_descriptor_returnVar.start, reference_type_descriptor_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            reference_type_descriptor_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, reference_type_descriptor_returnVar.start, tokenStream.LT(-1), e)
        }
        return reference_type_descriptor_returnVar
    }

    public final register_list_return register_list() throws RecognitionException {
        Char c
        register_list_return register_list_returnVar = register_list_return()
        register_list_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token COMMA")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 204) {
                c = 1
            } else {
                if (iLA != 18) {
                    throw NoViableAltException("", 38, 0, this.input)
                }
                c = 2
            }
            CommonTree commonTree = null
            if (c == 1) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_register_list3402))
                while (true) {
                    if ((this.input.LA(1) == 21 ? (Char) 1 : (Char) 2) != 1) {
                        break
                    }
                    rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_register_list3405))
                    rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_register_list3407))
                }
                register_list_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", register_list_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(138, register_list_returnVar.start, "I_REGISTER_LIST"), commonTree2)
                while (rewriteRuleTokenStream2.hasNext()) {
                    this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
                }
                rewriteRuleTokenStream2.reset()
                this.adaptor.addChild(commonTree, commonTree3)
                register_list_returnVar.tree = commonTree
            } else if (c == 2) {
                register_list_returnVar.tree = null
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", register_list_returnVar.getTree())
                commonTree = (CommonTree) this.adaptor.nil()
                CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor2 = this.adaptor
                this.adaptor.addChild(commonTree, (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(138, register_list_returnVar.start, "I_REGISTER_LIST"), commonTree4))
                register_list_returnVar.tree = commonTree
            }
            register_list_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree5 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            register_list_returnVar.tree = commonTree5
            this.adaptor.setTokenBoundaries(commonTree5, register_list_returnVar.start, register_list_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            register_list_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, register_list_returnVar.start, tokenStream.LT(-1), e)
        }
        return register_list_returnVar
    }

    public final register_range_return register_range() throws RecognitionException {
        Token token
        Token token2
        register_range_return register_range_returnVar = register_range_return()
        register_range_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token DOTDOT")
        try {
            if ((this.input.LA(1) == 204 ? (Char) 1 : (Char) 2) != 1) {
                token2 = null
                token = null
            } else {
                token = (Token) match(this.input, 204, FOLLOW_REGISTER_in_register_range3442)
                rewriteRuleTokenStream.add(token)
                if ((this.input.LA(1) == 22 ? (Char) 1 : (Char) 2) != 1) {
                    token2 = null
                } else {
                    rewriteRuleTokenStream2.add((Token) match(this.input, 22, FOLLOW_DOTDOT_in_register_range3445))
                    token2 = (Token) match(this.input, 204, FOLLOW_REGISTER_in_register_range3449)
                    rewriteRuleTokenStream.add(token2)
                }
            }
            register_range_returnVar.tree = null
            RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token endreg", token2)
            RewriteRuleTokenStream rewriteRuleTokenStream4 = RewriteRuleTokenStream(this.adaptor, "token startreg", token)
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", register_range_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(139, register_range_returnVar.start, "I_REGISTER_RANGE"), commonTree2)
            if (rewriteRuleTokenStream4.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode())
            }
            rewriteRuleTokenStream4.reset()
            if (rewriteRuleTokenStream3.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode())
            }
            rewriteRuleTokenStream3.reset()
            this.adaptor.addChild(commonTree, commonTree3)
            register_range_returnVar.tree = commonTree
            register_range_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            register_range_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, register_range_returnVar.start, register_range_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            register_range_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, register_range_returnVar.start, tokenStream.LT(-1), e)
        }
        return register_range_returnVar
    }

    public final registers_directive_return registers_directive() throws RecognitionException {
        Char c
        Token token
        CommonTree commonTree
        registers_directive_return registers_directive_returnVar = registers_directive_return()
        registers_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token LOCALS_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token REGISTERS_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal")
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 205) {
                c = 1
            } else {
                if (iLA != 187) {
                    throw NoViableAltException("", 9, 0, this.input)
                }
                c = 2
            }
            token = null
            if (c == 1) {
                Token token2 = (Token) match(this.input, 205, FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1816)
                rewriteRuleTokenStream2.add(token2)
                pushFollow(FOLLOW_integral_literal_in_registers_directive1820)
                integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree())
                registers_directive_returnVar.tree = null
                RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule regCount", integral_literal_returnVarIntegral_literal.getTree())
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", registers_directive_returnVar.getTree())
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                CommonTree commonTree3 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor = this.adaptor
                CommonTree commonTree4 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(137, token2, "I_REGISTERS"), commonTree3)
                this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream2.nextTree())
                this.adaptor.addChild(commonTree2, commonTree4)
                registers_directive_returnVar.tree = commonTree2
                token = token2
                commonTree = commonTree2
            } else if (c != 2) {
                commonTree = null
            } else {
                Token token3 = (Token) match(this.input, 187, FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1840)
                rewriteRuleTokenStream.add(token3)
                pushFollow(FOLLOW_integral_literal_in_registers_directive1844)
                integral_literal_return integral_literal_returnVarIntegral_literal2 = integral_literal()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal2.getTree())
                registers_directive_returnVar.tree = null
                RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule regCount2", integral_literal_returnVarIntegral_literal2.getTree())
                RewriteRuleSubtreeStream(this.adaptor, "rule retval", registers_directive_returnVar.getTree())
                CommonTree commonTree5 = (CommonTree) this.adaptor.nil()
                CommonTree commonTree6 = (CommonTree) this.adaptor.nil()
                TreeAdaptor treeAdaptor2 = this.adaptor
                CommonTree commonTree7 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(125, token3, "I_LOCALS"), commonTree6)
                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream3.nextTree())
                this.adaptor.addChild(commonTree5, commonTree7)
                registers_directive_returnVar.tree = commonTree5
                commonTree = commonTree5
                token = token3
            }
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor3 = this.adaptor
            TokenStream tokenStream = this.input
            registers_directive_returnVar.tree = (CommonTree) treeAdaptor3.errorNode(tokenStream, registers_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        if (this.statements_and_directives_stack.peek().hasRegistersDirective) {
            throw SemanticException(this.input, token, "There can only be a single .registers or .locals directive in a method", new Object[0])
        }
        this.statements_and_directives_stack.peek().hasRegistersDirective = true
        registers_directive_returnVar.stop = this.input.LT(-1)
        CommonTree commonTree8 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
        registers_directive_returnVar.tree = commonTree8
        this.adaptor.setTokenBoundaries(commonTree8, registers_directive_returnVar.start, registers_directive_returnVar.stop)
        return registers_directive_returnVar
    }

    public final restart_local_directive_return restart_local_directive() throws RecognitionException {
        restart_local_directive_return restart_local_directive_returnVar = restart_local_directive_return()
        restart_local_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token REGISTER")
        try {
            RewriteRuleTokenStream(this.adaptor, "token RESTART_LOCAL_DIRECTIVE").add((Token) match(this.input, 206, FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3909))
            rewriteRuleTokenStream.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_restart_local_directive3911))
            restart_local_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", restart_local_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(140, restart_local_directive_returnVar.start, "I_RESTART_LOCAL"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            restart_local_directive_returnVar.tree = commonTree
            restart_local_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            restart_local_directive_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, restart_local_directive_returnVar.start, restart_local_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            restart_local_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, restart_local_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return restart_local_directive_returnVar
    }

    fun setAllowOdex(Boolean z) {
        this.allowOdex = z
    }

    fun setApiLevel(Int i) {
        this.opcodes = Opcodes.forApi(i)
        this.apiLevel = i
    }

    fun setVerboseErrors(Boolean z) {
        this.verboseErrors = z
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x02cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.simple_name_return simple_name() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 3654
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.simple_name():org.jf.smali.smaliParser$simple_name_return")
    }

    public final smali_file_return smali_file() throws RecognitionException {
        this.smali_file_stack.push(smali_file_scope())
        smali_file_return smali_file_returnVar = smali_file_return()
        smali_file_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token EOF")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule class_spec")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = RewriteRuleSubtreeStream(this.adaptor, "rule annotation")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = RewriteRuleSubtreeStream(this.adaptor, "rule method")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = RewriteRuleSubtreeStream(this.adaptor, "rule field")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream5 = RewriteRuleSubtreeStream(this.adaptor, "rule super_spec")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream6 = RewriteRuleSubtreeStream(this.adaptor, "rule implements_spec")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream7 = RewriteRuleSubtreeStream(this.adaptor, "rule source_spec")
        smali_file_scope smali_file_scopeVarPeek = this.smali_file_stack.peek()
        smali_file_scope smali_file_scopeVarPeek2 = this.smali_file_stack.peek()
        this.smali_file_stack.peek().hasSourceSpec = false
        smali_file_scopeVarPeek2.hasSuperSpec = false
        smali_file_scopeVarPeek.hasClassSpec = false
        this.smali_file_stack.peek().classAnnotations = ArrayList()
        Int i = 0
        class_spec_return class_spec_returnVarClass_spec = null
        while (true) {
            try {
                try {
                    Int iLA = this.input.LA(1)
                    Char c = 5
                    if (iLA == 17 && !this.smali_file_stack.peek().hasClassSpec) {
                        c = 1
                    } else if (iLA == 213 && !this.smali_file_stack.peek().hasSuperSpec) {
                        c = 2
                    } else if (iLA == 42) {
                        c = 3
                    } else if (iLA == 209 && !this.smali_file_stack.peek().hasSourceSpec) {
                        c = 4
                    } else if (iLA != 191) {
                        c = iLA == 37 ? (Char) 6 : iLA == 5 ? (Char) 7 : '\b'
                    }
                    switch (c) {
                        case 1:
                            if (this.smali_file_stack.peek().hasClassSpec) {
                                throw FailedPredicateException(this.input, "smali_file", "!$smali_file::hasClassSpec")
                            }
                            pushFollow(FOLLOW_class_spec_in_smali_file1150)
                            class_spec_returnVarClass_spec = class_spec()
                            this.state._fsp--
                            rewriteRuleSubtreeStream.add(class_spec_returnVarClass_spec.getTree())
                            this.smali_file_stack.peek().hasClassSpec = true
                            continue
                            i++
                        case 2:
                            if (this.smali_file_stack.peek().hasSuperSpec) {
                                throw FailedPredicateException(this.input, "smali_file", "!$smali_file::hasSuperSpec")
                            }
                            pushFollow(FOLLOW_super_spec_in_smali_file1161)
                            super_spec_return super_spec_returnVarSuper_spec = super_spec()
                            this.state._fsp--
                            rewriteRuleSubtreeStream5.add(super_spec_returnVarSuper_spec.getTree())
                            this.smali_file_stack.peek().hasSuperSpec = true
                            continue
                            i++
                        case 3:
                            pushFollow(FOLLOW_implements_spec_in_smali_file1169)
                            implements_spec_return implements_spec_returnVarImplements_spec = implements_spec()
                            this.state._fsp--
                            rewriteRuleSubtreeStream6.add(implements_spec_returnVarImplements_spec.getTree())
                            continue
                            i++
                        case 4:
                            if (this.smali_file_stack.peek().hasSourceSpec) {
                                throw FailedPredicateException(this.input, "smali_file", "!$smali_file::hasSourceSpec")
                            }
                            pushFollow(FOLLOW_source_spec_in_smali_file1178)
                            source_spec_return source_spec_returnVarSource_spec = source_spec()
                            this.state._fsp--
                            rewriteRuleSubtreeStream7.add(source_spec_returnVarSource_spec.getTree())
                            this.smali_file_stack.peek().hasSourceSpec = true
                            continue
                            i++
                        case 5:
                            pushFollow(FOLLOW_method_in_smali_file1186)
                            method_return method_returnVarMethod = method()
                            this.state._fsp--
                            rewriteRuleSubtreeStream3.add(method_returnVarMethod.getTree())
                            continue
                            i++
                        case 6:
                            pushFollow(FOLLOW_field_in_smali_file1192)
                            field_return field_returnVarField = field()
                            this.state._fsp--
                            rewriteRuleSubtreeStream4.add(field_returnVarField.getTree())
                            continue
                            i++
                        case 7:
                            pushFollow(FOLLOW_annotation_in_smali_file1198)
                            annotation_return annotation_returnVarAnnotation = annotation()
                            this.state._fsp--
                            rewriteRuleSubtreeStream2.add(annotation_returnVarAnnotation.getTree())
                            this.smali_file_stack.peek().classAnnotations.add((CommonTree) annotation_returnVarAnnotation.getTree())
                            continue
                            i++
                        default:
                            if (i < 1) {
                                throw EarlyExitException(1, this.input)
                            }
                            rewriteRuleTokenStream.add((Token) match(this.input, -1, FOLLOW_EOF_in_smali_file1209))
                            if (!this.smali_file_stack.peek().hasClassSpec) {
                                throw SemanticException(this.input, "The file must contain a .class directive", new Object[0])
                            }
                            if (!this.smali_file_stack.peek().hasSuperSpec) {
                                if (!(class_spec_returnVarClass_spec != null ? class_spec_returnVarClass_spec.className : null).equals("Ljava/lang/Object;")) {
                                    throw SemanticException(this.input, "The file must contain a .super directive", new Object[0])
                                }
                            }
                            smali_file_returnVar.tree = null
                            RewriteRuleSubtreeStream(this.adaptor, "rule retval", smali_file_returnVar.getTree())
                            CommonTree commonTree = (CommonTree) this.adaptor.nil()
                            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                            TreeAdaptor treeAdaptor = this.adaptor
                            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(109, "I_CLASS_DEF"), commonTree2)
                            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
                            if (rewriteRuleSubtreeStream5.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream5.nextTree())
                            }
                            rewriteRuleSubtreeStream5.reset()
                            while (rewriteRuleSubtreeStream6.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream6.nextTree())
                            }
                            rewriteRuleSubtreeStream6.reset()
                            if (rewriteRuleSubtreeStream7.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream7.nextTree())
                            }
                            rewriteRuleSubtreeStream7.reset()
                            CommonTree commonTree4 = (CommonTree) this.adaptor.nil()
                            TreeAdaptor treeAdaptor2 = this.adaptor
                            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(127, "I_METHODS"), commonTree4)
                            while (rewriteRuleSubtreeStream3.hasNext()) {
                                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream3.nextTree())
                            }
                            rewriteRuleSubtreeStream3.reset()
                            this.adaptor.addChild(commonTree3, commonTree5)
                            CommonTree commonTree6 = (CommonTree) this.adaptor.nil()
                            TreeAdaptor treeAdaptor3 = this.adaptor
                            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(R.styleable.AppCompatTheme_tooltipForegroundColor, "I_FIELDS"), commonTree6)
                            while (rewriteRuleSubtreeStream4.hasNext()) {
                                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream4.nextTree())
                            }
                            rewriteRuleSubtreeStream4.reset()
                            this.adaptor.addChild(commonTree3, commonTree7)
                            this.adaptor.addChild(commonTree3, buildTree(100, "I_ANNOTATIONS", this.smali_file_stack.peek().classAnnotations))
                            this.adaptor.addChild(commonTree, commonTree3)
                            smali_file_returnVar.tree = commonTree
                            smali_file_returnVar.stop = this.input.LT(-1)
                            CommonTree commonTree8 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
                            smali_file_returnVar.tree = commonTree8
                            this.adaptor.setTokenBoundaries(commonTree8, smali_file_returnVar.start, smali_file_returnVar.stop)
                            break
                    }
                } catch (RecognitionException e) {
                    reportError(e)
                    recover(this.input, e)
                    TreeAdaptor treeAdaptor4 = this.adaptor
                    TokenStream tokenStream = this.input
                    smali_file_returnVar.tree = (CommonTree) treeAdaptor4.errorNode(tokenStream, smali_file_returnVar.start, tokenStream.LT(-1), e)
                }
            } finally {
                this.smali_file_stack.pop()
            }
        }
        return smali_file_returnVar
    }

    public final source_directive_return source_directive() throws RecognitionException {
        source_directive_return source_directive_returnVar = source_directive_return()
        source_directive_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token SOURCE_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 209, FOLLOW_SOURCE_DIRECTIVE_in_source_directive3976))
            if ((this.input.LA(1) == 211 ? (Char) 1 : (Char) 2) == 1) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_source_directive3978))
            }
            source_directive_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", source_directive_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(141, source_directive_returnVar.start, "I_SOURCE"), commonTree2)
            if (rewriteRuleTokenStream2.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            }
            rewriteRuleTokenStream2.reset()
            this.adaptor.addChild(commonTree, commonTree3)
            source_directive_returnVar.tree = commonTree
            source_directive_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            source_directive_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, source_directive_returnVar.start, source_directive_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            source_directive_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, source_directive_returnVar.start, tokenStream.LT(-1), e)
        }
        return source_directive_returnVar
    }

    public final source_spec_return source_spec() throws RecognitionException {
        source_spec_return source_spec_returnVar = source_spec_return()
        source_spec_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token SOURCE_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 209, FOLLOW_SOURCE_DIRECTIVE_in_source_spec1360))
            rewriteRuleTokenStream2.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_source_spec1362))
            source_spec_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", source_spec_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(141, source_spec_returnVar.start, "I_SOURCE"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            source_spec_returnVar.tree = commonTree
            source_spec_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            source_spec_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, source_spec_returnVar.start, source_spec_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            source_spec_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, source_spec_returnVar.start, tokenStream.LT(-1), e)
        }
        return source_spec_returnVar
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b3 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.statements_and_directives_return statements_and_directives() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 794
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.statements_and_directives():org.jf.smali.smaliParser$statements_and_directives_return")
    }

    public final subannotation_return subannotation() throws RecognitionException {
        subannotation_return subannotation_returnVar = subannotation_return()
        subannotation_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token SUBANNOTATION_DIRECTIVE")
        RewriteRuleTokenStream rewriteRuleTokenStream2 = RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR")
        RewriteRuleTokenStream rewriteRuleTokenStream3 = RewriteRuleTokenStream(this.adaptor, "token END_SUBANNOTATION_DIRECTIVE")
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = RewriteRuleSubtreeStream(this.adaptor, "rule annotation_element")
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 212, FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation3009))
            rewriteRuleTokenStream2.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_subannotation3011))
            while (true) {
                Char c = 2
                Int iLA = this.input.LA(1)
                if (iLA == 4 || iLA == 6 || iLA == 11 || iLA == 24 || ((iLA >= 40 && iLA <= 41) || ((iLA >= 44 && iLA <= 46) || iLA == 48 || iLA == 50 || ((iLA >= 53 && iLA <= 58) || iLA == 62 || ((iLA >= 64 && iLA <= 67) || ((iLA >= 69 && iLA <= 70) || iLA == 72 || ((iLA >= 76 && iLA <= 77) || ((iLA >= 79 && iLA <= 85) || ((iLA >= 92 && iLA <= 94) || ((iLA >= 192 && iLA <= 195) || ((iLA >= 200 && iLA <= 202) || iLA == 204 || iLA == 208 || (iLA >= 214 && iLA <= 215)))))))))))) {
                    c = 1
                }
                if (c != 1) {
                    break
                }
                pushFollow(FOLLOW_annotation_element_in_subannotation3013)
                annotation_element_return annotation_element_returnVarAnnotation_element = annotation_element()
                this.state._fsp--
                rewriteRuleSubtreeStream.add(annotation_element_returnVarAnnotation_element.getTree())
            }
            rewriteRuleTokenStream3.add((Token) match(this.input, 33, FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation3016))
            subannotation_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", subannotation_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(183, subannotation_returnVar.start, "I_SUBANNOTATION"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode())
            while (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree())
            }
            rewriteRuleSubtreeStream.reset()
            this.adaptor.addChild(commonTree, commonTree3)
            subannotation_returnVar.tree = commonTree
            subannotation_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            subannotation_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, subannotation_returnVar.start, subannotation_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            subannotation_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, subannotation_returnVar.start, tokenStream.LT(-1), e)
        }
        return subannotation_returnVar
    }

    public final super_spec_return super_spec() throws RecognitionException {
        super_spec_return super_spec_returnVar = super_spec_return()
        super_spec_returnVar.start = this.input.LT(1)
        RewriteRuleTokenStream rewriteRuleTokenStream = RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR")
        try {
            RewriteRuleTokenStream(this.adaptor, "token SUPER_DIRECTIVE").add((Token) match(this.input, 213, FOLLOW_SUPER_DIRECTIVE_in_super_spec1318))
            rewriteRuleTokenStream.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_super_spec1320))
            super_spec_returnVar.tree = null
            RewriteRuleSubtreeStream(this.adaptor, "rule retval", super_spec_returnVar.getTree())
            CommonTree commonTree = (CommonTree) this.adaptor.nil()
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
            TreeAdaptor treeAdaptor = this.adaptor
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(184, super_spec_returnVar.start, "I_SUPER"), commonTree2)
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode())
            this.adaptor.addChild(commonTree, commonTree3)
            super_spec_returnVar.tree = commonTree
            super_spec_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree4 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            super_spec_returnVar.tree = commonTree4
            this.adaptor.setTokenBoundaries(commonTree4, super_spec_returnVar.start, super_spec_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor2 = this.adaptor
            TokenStream tokenStream = this.input
            super_spec_returnVar.tree = (CommonTree) treeAdaptor2.errorNode(tokenStream, super_spec_returnVar.start, tokenStream.LT(-1), e)
        }
        return super_spec_returnVar
    }

    public final Unit throwOdexedInstructionException(IntStream intStream, String str) throws OdexedInstructionException {
        throw OdexedInstructionException(intStream, str)
    }

    public final type_descriptor_return type_descriptor() throws RecognitionException {
        Char c
        CommonTree commonTree
        type_descriptor_return type_descriptor_returnVar = type_descriptor_return()
        type_descriptor_returnVar.start = this.input.LT(1)
        try {
            Int iLA = this.input.LA(1)
            if (iLA == 8) {
                c = 4
            } else if (iLA == 16) {
                c = 3
            } else if (iLA == 202) {
                c = 2
            } else {
                if (iLA != 215) {
                    throw NoViableAltException("", 16, 0, this.input)
                }
                c = 1
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 215, FOLLOW_VOID_TYPE_in_type_descriptor2513)))
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_type_descriptor2519)))
            } else if (c == 3) {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_type_descriptor2525)))
            } else if (c != 4) {
                commonTree = null
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil()
                pushFollow(FOLLOW_array_descriptor_in_type_descriptor2531)
                array_descriptor_return array_descriptor_returnVarArray_descriptor = array_descriptor()
                this.state._fsp--
                this.adaptor.addChild(commonTree2, array_descriptor_returnVarArray_descriptor.getTree())
                commonTree = commonTree2
            }
            type_descriptor_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            type_descriptor_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, type_descriptor_returnVar.start, type_descriptor_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            type_descriptor_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, type_descriptor_returnVar.start, tokenStream.LT(-1), e)
        }
        return type_descriptor_returnVar
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:121:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x015a A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:409:0x03ec  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x05b7 A[Catch: all -> 0x05f6, RecognitionException -> 0x05f8, TryCatch #5 {RecognitionException -> 0x05f8, blocks: (B:3:0x0047, B:28:0x0087, B:29:0x008f, B:30:0x0090, B:114:0x013d, B:118:0x0151, B:119:0x0156, B:442:0x05da, B:415:0x03f6, B:416:0x041a, B:417:0x043e, B:427:0x047b, B:432:0x048b, B:434:0x04f2, B:435:0x04fb, B:437:0x051a, B:439:0x0590, B:440:0x0599, B:426:0x0454, B:441:0x05b7, B:122:0x015d, B:206:0x020a, B:210:0x021f, B:211:0x0224, B:213:0x0228, B:296:0x02d2, B:300:0x02e7, B:301:0x02ec, B:303:0x02f0, B:308:0x0300, B:312:0x0315, B:313:0x031a, B:314:0x031b, B:397:0x03c6, B:405:0x03e2, B:406:0x03e7), top: B:460:0x0047, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.jf.smali.smaliParser.type_field_method_literal_return type_field_method_literal() throws org.antlr.runtime.RecognitionException {
        /*
            Method dump skipped, instructions count: 1716
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.smali.smaliParser.type_field_method_literal():org.jf.smali.smaliParser$type_field_method_literal_return")
    }

    public final verification_error_reference_return verification_error_reference() throws RecognitionException {
        CommonTree commonTree
        CommonTree commonTree2
        verification_error_reference_return verification_error_reference_returnVar = verification_error_reference_return()
        verification_error_reference_returnVar.start = this.input.LT(1)
        try {
            Int iPredict = this.dfa41.predict(this.input)
            if (iPredict != 1) {
                if (iPredict == 2) {
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_field_reference_in_verification_error_reference3482)
                    field_reference_return field_reference_returnVarField_reference = field_reference()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree2, field_reference_returnVarField_reference.getTree())
                } else if (iPredict != 3) {
                    commonTree = null
                } else {
                    commonTree2 = (CommonTree) this.adaptor.nil()
                    pushFollow(FOLLOW_method_reference_in_verification_error_reference3486)
                    method_reference_return method_reference_returnVarMethod_reference = method_reference()
                    this.state._fsp--
                    this.adaptor.addChild(commonTree2, method_reference_returnVarMethod_reference.getTree())
                }
                commonTree = commonTree2
            } else {
                commonTree = (CommonTree) this.adaptor.nil()
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3478)))
            }
            verification_error_reference_returnVar.stop = this.input.LT(-1)
            CommonTree commonTree3 = (CommonTree) this.adaptor.rulePostProcessing(commonTree)
            verification_error_reference_returnVar.tree = commonTree3
            this.adaptor.setTokenBoundaries(commonTree3, verification_error_reference_returnVar.start, verification_error_reference_returnVar.stop)
        } catch (RecognitionException e) {
            reportError(e)
            recover(this.input, e)
            TreeAdaptor treeAdaptor = this.adaptor
            TokenStream tokenStream = this.input
            verification_error_reference_returnVar.tree = (CommonTree) treeAdaptor.errorNode(tokenStream, verification_error_reference_returnVar.start, tokenStream.LT(-1), e)
        }
        return verification_error_reference_returnVar
    }
}
