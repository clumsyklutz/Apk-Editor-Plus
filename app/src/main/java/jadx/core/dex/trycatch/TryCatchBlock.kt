package jadx.core.dex.trycatch

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.BlockUtils
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.Iterator
import java.util.LinkedList
import java.util.List

class TryCatchBlock {
    private val handlers = LinkedList()
    private val insns = ArrayList()
    private val attr = CatchAttr(this)

    private fun removeWholeBlock(MethodNode methodNode) {
        Iterator it = this.handlers.iterator()
        while (it.hasNext()) {
            unbindHandler((ExceptionHandler) it.next())
            it.remove()
        }
        Iterator it2 = this.insns.iterator()
        while (it2.hasNext()) {
            ((InsnNode) it2.next()).removeAttr(this.attr)
        }
        this.insns.clear()
        if (methodNode.getBasicBlocks() != null) {
            Iterator it3 = methodNode.getBasicBlocks().iterator()
            while (it3.hasNext()) {
                ((BlockNode) it3.next()).removeAttr(this.attr)
            }
        }
    }

    private fun unbindHandler(ExceptionHandler exceptionHandler) {
        for (BlockNode blockNode : exceptionHandler.getBlocks()) {
            BlockUtils.skipPredSyntheticPaths(blockNode)
            blockNode.add(AFlag.SKIP)
            ExcHandlerAttr excHandlerAttr = (ExcHandlerAttr) blockNode.get(AType.EXC_HANDLER)
            if (excHandlerAttr != null && excHandlerAttr.getHandler().equals(exceptionHandler)) {
                blockNode.remove(AType.EXC_HANDLER)
            }
            SplitterBlockAttr splitterBlockAttr = (SplitterBlockAttr) exceptionHandler.getHandlerBlock().get(AType.SPLITTER_BLOCK)
            if (splitterBlockAttr != null) {
                splitterBlockAttr.getBlock().remove(AType.SPLITTER_BLOCK)
            }
        }
    }

    fun addHandler(MethodNode methodNode, Int i, ClassInfo classInfo) {
        ExceptionHandler exceptionHandlerAddExceptionHandler = methodNode.addExceptionHandler(ExceptionHandler(i, classInfo))
        this.handlers.add(exceptionHandlerAddExceptionHandler)
        exceptionHandlerAddExceptionHandler.setTryBlock(this)
        return exceptionHandlerAddExceptionHandler
    }

    fun addInsn(InsnNode insnNode) {
        this.insns.add(insnNode)
        insnNode.addAttr(this.attr)
    }

    fun containsAllHandlers(TryCatchBlock tryCatchBlock) {
        return this.handlers.containsAll(tryCatchBlock.handlers)
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return this.handlers.equals(((TryCatchBlock) obj).handlers)
    }

    fun getCatchAttr() {
        return this.attr
    }

    fun getHandlers() {
        return this.handlers
    }

    fun getHandlersCount() {
        return this.handlers.size()
    }

    fun getInsns() {
        return this.insns
    }

    fun hashCode() {
        return this.handlers.hashCode()
    }

    fun merge(MethodNode methodNode, TryCatchBlock tryCatchBlock) {
        if (tryCatchBlock == this) {
            return false
        }
        Iterator it = tryCatchBlock.getInsns().iterator()
        while (it.hasNext()) {
            addInsn((InsnNode) it.next())
        }
        this.handlers.addAll(tryCatchBlock.handlers)
        Iterator it2 = this.handlers.iterator()
        while (it2.hasNext()) {
            ((ExceptionHandler) it2.next()).setTryBlock(this)
        }
        tryCatchBlock.handlers.clear()
        tryCatchBlock.removeWholeBlock(methodNode)
        return true
    }

    fun removeBlock(MethodNode methodNode, BlockNode blockNode) {
        for (InsnNode insnNode : blockNode.getInstructions()) {
            this.insns.remove(insnNode)
            insnNode.remove(AType.CATCH_BLOCK)
        }
        if (this.insns.isEmpty()) {
            removeWholeBlock(methodNode)
        }
    }

    fun removeHandler(MethodNode methodNode, ExceptionHandler exceptionHandler) {
        Iterator it = this.handlers.iterator()
        while (true) {
            if (!it.hasNext()) {
                break
            }
            ExceptionHandler exceptionHandler2 = (ExceptionHandler) it.next()
            if (exceptionHandler2 == exceptionHandler) {
                unbindHandler(exceptionHandler2)
                it.remove()
                break
            }
        }
        if (this.handlers.isEmpty()) {
            removeWholeBlock(methodNode)
        }
    }

    fun removeInsn(MethodNode methodNode, InsnNode insnNode) {
        this.insns.remove(insnNode)
        insnNode.remove(AType.CATCH_BLOCK)
        if (this.insns.isEmpty()) {
            removeWholeBlock(methodNode)
        }
    }

    fun toString() {
        return "Catch:{ " + Utils.listToString(this.handlers) + " }"
    }
}
