package org.jf.dexlib2.iface.value

import org.jf.dexlib2.iface.reference.MethodHandleReference

public interface MethodHandleEncodedValue extends EncodedValue {
    MethodHandleReference getValue()
}
