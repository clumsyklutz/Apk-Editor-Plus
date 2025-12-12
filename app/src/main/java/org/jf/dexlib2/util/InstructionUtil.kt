package org.jf.dexlib2.util

import org.jf.dexlib2.Opcode

class InstructionUtil {
    fun isInvokePolymorphic(Opcode opcode) {
        return opcode == Opcode.INVOKE_POLYMORPHIC || opcode == Opcode.INVOKE_POLYMORPHIC_RANGE
    }

    fun isInvokeStatic(Opcode opcode) {
        return opcode == Opcode.INVOKE_STATIC || opcode == Opcode.INVOKE_STATIC_RANGE
    }
}
