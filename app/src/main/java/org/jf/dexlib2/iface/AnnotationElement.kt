package org.jf.dexlib2.iface

import org.jf.dexlib2.iface.value.EncodedValue

public interface AnnotationElement extends Comparable<AnnotationElement> {
    String getName()

    EncodedValue getValue()
}
