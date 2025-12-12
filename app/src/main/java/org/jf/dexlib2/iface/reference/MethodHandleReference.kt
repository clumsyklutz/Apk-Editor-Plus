package org.jf.dexlib2.iface.reference

public interface MethodHandleReference extends Reference, Comparable<MethodHandleReference> {
    Int compareTo(MethodHandleReference methodHandleReference)

    Boolean equals(Object obj)

    Reference getMemberReference()

    Int getMethodHandleType()

    Int hashCode()
}
