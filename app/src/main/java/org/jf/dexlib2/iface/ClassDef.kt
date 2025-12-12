package org.jf.dexlib2.iface

import java.util.List
import java.util.Set
import org.jf.dexlib2.iface.reference.TypeReference

public interface ClassDef extends TypeReference {
    Int getAccessFlags()

    Set<? extends Annotation> getAnnotations()

    Iterable<? extends Method> getDirectMethods()

    Iterable<? extends Field> getInstanceFields()

    List<String> getInterfaces()

    String getSourceFile()

    Iterable<? extends Field> getStaticFields()

    String getSuperclass()

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    String getType()

    Iterable<? extends Method> getVirtualMethods()
}
