package org.jf.dexlib2.analysis

import com.google.common.base.Strings
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.util.TypeUtils

class ArrayProto implements TypeProto {
    public static val BRACKETS = Strings.repeat("[", 256)
    public final Int dimensions
    public final String elementType

    fun makeArrayType(String str, Int i) {
        return BRACKETS.substring(0, i) + str
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    fun getCommonSuperclass(TypeProto typeProto) {
        if (!(typeProto is ArrayProto)) {
            if (!(typeProto is ClassProto)) {
                return typeProto.getCommonSuperclass(this)
            }
            try {
                if (!typeProto.isInterface()) {
                    throw null
                }
                if (implementsInterface(typeProto.getType())) {
                    return typeProto
                }
                throw null
            } catch (UnresolvedClassException unused) {
                throw null
            }
        }
        if (!TypeUtils.isPrimitiveType(getElementType())) {
            ArrayProto arrayProto = (ArrayProto) typeProto
            if (!TypeUtils.isPrimitiveType(arrayProto.getElementType())) {
                Int i = this.dimensions
                Int i2 = arrayProto.dimensions
                if (i == i2) {
                    throw null
                }
                makeArrayType("Ljava/lang/Object;", Math.min(i, i2))
                throw null
            }
        }
        ArrayProto arrayProto2 = (ArrayProto) typeProto
        if (this.dimensions == arrayProto2.dimensions && getElementType().equals(arrayProto2.getElementType())) {
            return this
        }
        throw null
    }

    fun getElementType() {
        return this.elementType
    }

    fun getImmediateElementType() {
        Int i = this.dimensions
        return i > 1 ? makeArrayType(this.elementType, i - 1) : this.elementType
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    fun getMethodByVtableIndex(Int i) {
        throw null
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    fun getType() {
        return makeArrayType(this.elementType, this.dimensions)
    }

    fun implementsInterface(String str) {
        return str.equals("Ljava/lang/Cloneable;") || str.equals("Ljava/io/Serializable;")
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    fun isInterface() {
        return false
    }

    fun toString() {
        return getType()
    }
}
