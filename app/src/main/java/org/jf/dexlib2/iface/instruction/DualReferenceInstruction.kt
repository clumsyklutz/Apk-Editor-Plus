package org.jf.dexlib2.iface.instruction

import org.jf.dexlib2.iface.reference.Reference

public interface DualReferenceInstruction extends ReferenceInstruction {
    Reference getReference2()

    Int getReferenceType2()
}
