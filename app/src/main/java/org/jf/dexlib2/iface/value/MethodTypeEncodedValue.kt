package org.jf.dexlib2.iface.value

import org.jf.dexlib2.iface.reference.MethodProtoReference

public interface MethodTypeEncodedValue extends EncodedValue {
    MethodProtoReference getValue()
}
