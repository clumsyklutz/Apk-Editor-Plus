package org.jf.dexlib2.iface

import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.value.EncodedValue

public interface Field extends FieldReference {
    Int getAccessFlags()

    Set<? extends Annotation> getAnnotations()

    String getDefiningClass()

    Set<HiddenApiRestriction> getHiddenApiRestrictions()

    EncodedValue getInitialValue()

    String getName()

    String getType()
}
