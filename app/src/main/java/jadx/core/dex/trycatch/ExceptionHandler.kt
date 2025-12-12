package jadx.core.dex.trycatch

import jadx.core.Consts
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IContainer
import jadx.core.utils.InsnUtils
import java.util.ArrayList
import java.util.List

class ExceptionHandler {
    private InsnArg arg
    private val blocks = ArrayList()
    private final ClassInfo catchType
    private final Int handleOffset
    private BlockNode handlerBlock
    private IContainer handlerRegion
    private Boolean isFinally
    private TryCatchBlock tryBlock

    constructor(Int i, ClassInfo classInfo) {
        this.handleOffset = i
        this.catchType = classInfo
    }

    fun addBlock(BlockNode blockNode) {
        this.blocks.add(blockNode)
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && getClass() == obj.getClass()) {
            ExceptionHandler exceptionHandler = (ExceptionHandler) obj
            if (this.catchType == null) {
                if (exceptionHandler.catchType != null) {
                    return false
                }
            } else if (!this.catchType.equals(exceptionHandler.catchType)) {
                return false
            }
            return this.handleOffset == exceptionHandler.handleOffset
        }
        return false
    }

    fun getArg() {
        return this.arg
    }

    fun getBlocks() {
        return this.blocks
    }

    fun getCatchType() {
        return this.catchType
    }

    fun getHandleOffset() {
        return this.handleOffset
    }

    fun getHandlerBlock() {
        return this.handlerBlock
    }

    fun getHandlerRegion() {
        return this.handlerRegion
    }

    fun getTryBlock() {
        return this.tryBlock
    }

    fun hashCode() {
        return (this.catchType == null ? 0 : this.catchType.hashCode() * 31) + this.handleOffset
    }

    fun isCatchAll() {
        return this.catchType == null || this.catchType.getFullName().equals(Consts.CLASS_THROWABLE)
    }

    fun isFinally() {
        return this.isFinally
    }

    fun setArg(InsnArg insnArg) {
        this.arg = insnArg
    }

    fun setFinally(Boolean z) {
        this.isFinally = z
    }

    fun setHandlerBlock(BlockNode blockNode) {
        this.handlerBlock = blockNode
    }

    fun setHandlerRegion(IContainer iContainer) {
        this.handlerRegion = iContainer
    }

    fun setTryBlock(TryCatchBlock tryCatchBlock) {
        this.tryBlock = tryCatchBlock
    }

    fun toString() {
        return (this.catchType == null ? "all" : this.catchType.getShortName()) + " -> " + InsnUtils.formatOffset(this.handleOffset)
    }
}
