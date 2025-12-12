package org.jf.dexlib2.iface.value

import java.util.List

public interface ArrayEncodedValue extends EncodedValue {
    List<? extends EncodedValue> getValue()
}
