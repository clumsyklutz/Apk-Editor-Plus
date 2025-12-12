package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.TryBlock
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.immutable.debug.ImmutableDebugItem
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction

class ImmutableMethodImplementation implements MethodImplementation {
    public final ImmutableList<? extends ImmutableDebugItem> debugItems
    public final ImmutableList<? extends ImmutableInstruction> instructions
    public final Int registerCount
    public final ImmutableList<? extends ImmutableTryBlock> tryBlocks

    constructor(Int i, Iterable<? extends Instruction> iterable, List<? extends TryBlock<? extends ExceptionHandler>> list, Iterable<? extends DebugItem> iterable2) {
        this.registerCount = i
        this.instructions = ImmutableInstruction.immutableListOf(iterable)
        this.tryBlocks = ImmutableTryBlock.immutableListOf(list)
        this.debugItems = ImmutableDebugItem.immutableListOf(iterable2)
    }

    fun of(MethodImplementation methodImplementation) {
        if (methodImplementation == null) {
            return null
        }
        return methodImplementation is ImmutableMethodImplementation ? (ImmutableMethodImplementation) methodImplementation : ImmutableMethodImplementation(methodImplementation.getRegisterCount(), methodImplementation.getInstructions(), methodImplementation.getTryBlocks(), methodImplementation.getDebugItems())
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public ImmutableList<? extends ImmutableDebugItem> getDebugItems() {
        return this.debugItems
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public ImmutableList<? extends ImmutableInstruction> getInstructions() {
        return this.instructions
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public ImmutableList<? extends ImmutableTryBlock> getTryBlocks() {
        return this.tryBlocks
    }
}
