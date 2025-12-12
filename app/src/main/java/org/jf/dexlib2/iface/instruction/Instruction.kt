package org.jf.dexlib2.iface.instruction

import org.jf.dexlib2.Opcode

public interface Instruction {
    Int getCodeUnits()

    Opcode getOpcode()
}
