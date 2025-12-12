package org.jf.dexlib2.analysis

import org.jf.dexlib2.iface.Method

public interface TypeProto {
    TypeProto getCommonSuperclass(TypeProto typeProto)

    Method getMethodByVtableIndex(Int i)

    String getType()

    Boolean isInterface()
}
