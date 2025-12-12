package org.jf.dexlib2.iface.reference

import java.util.List

public interface MethodReference extends Reference, Comparable<MethodReference> {
    Int compareTo(MethodReference methodReference)

    Boolean equals(Object obj)

    String getDefiningClass()

    String getName()

    List<? extends CharSequence> getParameterTypes()

    String getReturnType()

    Int hashCode()
}
