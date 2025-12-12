package org.jf.dexlib2.iface.value

import java.util.Set
import org.jf.dexlib2.iface.AnnotationElement

public interface AnnotationEncodedValue extends EncodedValue {
    Set<? extends AnnotationElement> getElements()

    String getType()
}
