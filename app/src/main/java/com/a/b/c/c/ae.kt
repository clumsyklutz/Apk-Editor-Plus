package com.a.b.c.c

import androidx.fragment.app.FragmentTransaction
import androidx.core.view.InputDeviceCompat

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
class ae implements com.a.b.h.s {
    private final Int w
    private final String x
    private final String y

    /* renamed from: a, reason: collision with root package name */
    public static val f350a = ae("TYPE_HEADER_ITEM", 0, 0, "header_item")

    /* renamed from: b, reason: collision with root package name */
    public static val f351b = ae("TYPE_STRING_ID_ITEM", 1, 1, "string_id_item")
    public static val c = ae("TYPE_TYPE_ID_ITEM", 2, 2, "type_id_item")
    public static val d = ae("TYPE_PROTO_ID_ITEM", 3, 3, "proto_id_item")
    public static val e = ae("TYPE_FIELD_ID_ITEM", 4, 4, "field_id_item")
    public static val f = ae("TYPE_METHOD_ID_ITEM", 5, 5, "method_id_item")
    public static val g = ae("TYPE_CLASS_DEF_ITEM", 6, 6, "class_def_item")
    public static val h = ae("TYPE_MAP_LIST", 7, 4096, "map_list")
    public static val i = ae("TYPE_TYPE_LIST", 8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, "type_list")
    public static val j = ae("TYPE_ANNOTATION_SET_REF_LIST", 9, InputDeviceCompat.SOURCE_TOUCHSCREEN, "annotation_set_ref_list")
    public static val k = ae("TYPE_ANNOTATION_SET_ITEM", 10, FragmentTransaction.TRANSIT_FRAGMENT_FADE, "annotation_set_item")
    public static val l = ae("TYPE_CLASS_DATA_ITEM", 11, 8192, "class_data_item")
    public static val m = ae("TYPE_CODE_ITEM", 12, 8193, "code_item")
    public static val n = ae("TYPE_STRING_DATA_ITEM", 13, 8194, "string_data_item")
    public static val o = ae("TYPE_DEBUG_INFO_ITEM", 14, 8195, "debug_info_item")
    public static val p = ae("TYPE_ANNOTATION_ITEM", 15, 8196, "annotation_item")
    public static val q = ae("TYPE_ENCODED_ARRAY_ITEM", 16, 8197, "encoded_array_item")
    public static val r = ae("TYPE_ANNOTATIONS_DIRECTORY_ITEM", 17, 8198, "annotations_directory_item")
    public static val s = ae("TYPE_MAP_ITEM", 18, -1, "map_item")
    private static ae u = ae("TYPE_TYPE_ITEM", 19, -1, "type_item")
    private static ae v = ae("TYPE_EXCEPTION_HANDLER_ITEM", 20, -1, "exception_handler_item")
    public static val t = ae("TYPE_ANNOTATION_SET_REF_ITEM", 21, -1, "annotation_set_ref_item")

    static {
        Array<ae> aeVarArr = {f350a, f351b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, u, v, t}
    }

    private constructor(String str, Int i2, Int i3, String str2) {
        this.w = i3
        this.x = str2
        this.y = (str2.endsWith("_item") ? str2.substring(0, str2.length() - 5) : str2).replace('_', ' ')
    }

    public final Int a() {
        return this.w
    }

    public final String b() {
        return this.x
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.y
    }
}
