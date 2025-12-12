package org.jf.dexlib2.iface.reference

import java.util.List

public interface MethodProtoReference extends Reference, Comparable<MethodProtoReference> {
    Int compareTo(MethodProtoReference methodProtoReference)

    Boolean equals(Object obj)

    List<? extends CharSequence> getParameterTypes()

    String getReturnType()

    Int hashCode()
}
