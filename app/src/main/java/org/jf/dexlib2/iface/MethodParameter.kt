package org.jf.dexlib2.iface

import java.util.Set
import org.jf.dexlib2.iface.debug.LocalInfo
import org.jf.dexlib2.iface.reference.TypeReference

public interface MethodParameter extends TypeReference, LocalInfo {
    Set<? extends Annotation> getAnnotations()

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    String getName()

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    String getType()
}
