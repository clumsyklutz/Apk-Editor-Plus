package org.jf.dexlib2.iface.value

import org.jf.dexlib2.iface.reference.MethodReference

public interface MethodEncodedValue extends EncodedValue {
    MethodReference getValue()
}
