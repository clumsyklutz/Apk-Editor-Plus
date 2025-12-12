package org.jf.dexlib2.iface.value

import org.jf.dexlib2.iface.reference.FieldReference

public interface EnumEncodedValue extends EncodedValue {
    FieldReference getValue()
}
