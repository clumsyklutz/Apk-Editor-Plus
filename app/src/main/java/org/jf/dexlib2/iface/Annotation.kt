package org.jf.dexlib2.iface

import java.util.Set

public interface Annotation extends Comparable<Annotation> {
    Set<? extends AnnotationElement> getElements()

    String getType()

    Int getVisibility()
}
