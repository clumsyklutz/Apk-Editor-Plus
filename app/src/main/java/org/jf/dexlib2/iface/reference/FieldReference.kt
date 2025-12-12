package org.jf.dexlib2.iface.reference

public interface FieldReference extends Reference, Comparable<FieldReference> {
    Int compareTo(FieldReference fieldReference)

    Boolean equals(Object obj)

    String getDefiningClass()

    String getName()

    String getType()

    Int hashCode()
}
