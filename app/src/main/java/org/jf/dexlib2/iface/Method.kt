package org.jf.dexlib2.iface

import java.util.List
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.iface.reference.MethodReference

public interface Method extends MethodReference {
    Int getAccessFlags()

    Set<? extends Annotation> getAnnotations()

    String getDefiningClass()

    Set<HiddenApiRestriction> getHiddenApiRestrictions()

    MethodImplementation getImplementation()

    String getName()

    List<? extends MethodParameter> getParameters()

    String getReturnType()
}
