package org.jf.dexlib2.util

import com.google.common.base.Predicate
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.iface.Field

class FieldUtil {
    public static Predicate<Field> FIELD_IS_STATIC = new Predicate<Field>() { // from class: org.jf.dexlib2.util.FieldUtil.1
        @Override // com.google.common.base.Predicate
        fun apply(Field field) {
            return field != null && FieldUtil.isStatic(field)
        }
    }
    public static Predicate<Field> FIELD_IS_INSTANCE = new Predicate<Field>() { // from class: org.jf.dexlib2.util.FieldUtil.2
        @Override // com.google.common.base.Predicate
        fun apply(Field field) {
            return (field == null || FieldUtil.isStatic(field)) ? false : true
        }
    }

    fun isStatic(Field field) {
        return AccessFlags.STATIC.isSet(field.getAccessFlags())
    }
}
