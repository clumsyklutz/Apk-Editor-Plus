package org.jf.dexlib2.iface

import java.util.List
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.instruction.Instruction

public interface MethodImplementation {
    Iterable<? extends DebugItem> getDebugItems()

    Iterable<? extends Instruction> getInstructions()

    Int getRegisterCount()

    List<? extends TryBlock<? extends ExceptionHandler>> getTryBlocks()
}
