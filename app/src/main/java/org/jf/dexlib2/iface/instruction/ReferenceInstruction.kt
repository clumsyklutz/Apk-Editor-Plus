package org.jf.dexlib2.iface.instruction

import org.jf.dexlib2.iface.reference.Reference

public interface ReferenceInstruction extends Instruction {
    Reference getReference()

    Int getReferenceType()
}
