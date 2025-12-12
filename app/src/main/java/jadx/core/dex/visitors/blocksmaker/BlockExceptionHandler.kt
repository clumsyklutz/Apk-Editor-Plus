package jadx.core.dex.visitors.blocksmaker

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.trycatch.CatchAttr
import jadx.core.dex.trycatch.ExcHandlerAttr
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.TryCatchBlock
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.BlockUtils
import jadx.core.utils.InstructionRemover
import java.util.Iterator

class BlockExceptionHandler extends AbstractVisitor {
    private fun connectHandler(MethodNode methodNode, ExceptionHandler exceptionHandler) {
        Int handleOffset = exceptionHandler.getHandleOffset()
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            ExcHandlerAttr excHandlerAttr = (ExcHandlerAttr) blockNode.get(AType.EXC_HANDLER)
            if (excHandlerAttr != null && excHandlerAttr.getHandler().getHandleOffset() == handleOffset) {
                exceptionHandler.setHandlerBlock(blockNode)
                return
            }
        }
    }

    private fun markExceptionHandlers(BlockNode blockNode) {
        if (blockNode.getInstructions().isEmpty()) {
            return
        }
        InsnNode insnNode = (InsnNode) blockNode.getInstructions().get(0)
        ExcHandlerAttr excHandlerAttr = (ExcHandlerAttr) insnNode.get(AType.EXC_HANDLER)
        if (excHandlerAttr == null || insnNode.getType() != InsnType.MOVE_EXCEPTION) {
            return
        }
        ExceptionHandler handler = excHandlerAttr.getHandler()
        blockNode.addAttr(excHandlerAttr)
        RegisterArg registerArgReg = InsnArg.reg(insnNode.getResult().getRegNum(), handler.isCatchAll() ? ArgType.THROWABLE : handler.getCatchType().getType())
        insnNode.setResult(registerArgReg)
        insnNode.add(AFlag.DONT_INLINE)
        handler.setArg(registerArgReg)
    }

    private fun onlyAllHandler(TryCatchBlock tryCatchBlock) {
        if (tryCatchBlock.getHandlersCount() == 1) {
            ExceptionHandler exceptionHandler = (ExceptionHandler) tryCatchBlock.getHandlers().iterator().next()
            if (exceptionHandler.isCatchAll() || exceptionHandler.isFinally()) {
                return true
            }
        }
        return false
    }

    private fun processExceptionHandlers(MethodNode methodNode, BlockNode blockNode) {
        ExcHandlerAttr excHandlerAttr = (ExcHandlerAttr) blockNode.get(AType.EXC_HANDLER)
        if (excHandlerAttr == null) {
            return
        }
        ExceptionHandler handler = excHandlerAttr.getHandler()
        handler.addBlock(blockNode)
        Iterator it = BlockUtils.collectBlocksDominatedBy(blockNode, blockNode).iterator()
        while (it.hasNext()) {
            handler.addBlock((BlockNode) it.next())
        }
        for (BlockNode blockNode2 : handler.getBlocks()) {
            InstructionRemover instructionRemover = InstructionRemover(methodNode, blockNode2)
            for (InsnNode insnNode : blockNode2.getInstructions()) {
                if (insnNode.getType() == InsnType.MONITOR_ENTER) {
                    break
                } else if (insnNode.getType() == InsnType.MONITOR_EXIT) {
                    instructionRemover.add(insnNode)
                }
            }
            instructionRemover.perform()
            for (InsnNode insnNode2 : blockNode2.getInstructions()) {
                CatchAttr catchAttr = (CatchAttr) insnNode2.get(AType.CATCH_BLOCK)
                if (catchAttr != null && (insnNode2.getType() == InsnType.THROW || onlyAllHandler(catchAttr.getTryBlock()))) {
                    excHandlerAttr.getTryBlock().merge(methodNode, catchAttr.getTryBlock())
                }
            }
        }
    }

    private fun processTryCatchBlocks(MethodNode methodNode, BlockNode blockNode) {
        CatchAttr catchAttr = null
        Iterator it = blockNode.getInstructions().iterator()
        CatchAttr catchAttr2 = null
        while (true) {
            if (!it.hasNext()) {
                catchAttr = catchAttr2
                break
            }
            CatchAttr catchAttr3 = (CatchAttr) ((InsnNode) it.next()).get(AType.CATCH_BLOCK)
            if (catchAttr3 != null) {
                if (catchAttr2 == null) {
                    catchAttr2 = catchAttr3
                } else if (catchAttr2 != catchAttr3) {
                    break
                }
            }
        }
        if (catchAttr != null) {
            blockNode.addAttr(catchAttr)
            Iterator it2 = catchAttr.getTryBlock().getHandlers().iterator()
            while (it2.hasNext()) {
                connectHandler(methodNode, (ExceptionHandler) it2.next())
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        Iterator it = methodNode.getBasicBlocks().iterator()
        while (it.hasNext()) {
            markExceptionHandlers((BlockNode) it.next())
        }
        Iterator it2 = methodNode.getBasicBlocks().iterator()
        while (it2.hasNext()) {
            ((BlockNode) it2.next()).updateCleanSuccessors()
        }
        Iterator it3 = methodNode.getBasicBlocks().iterator()
        while (it3.hasNext()) {
            processExceptionHandlers(methodNode, (BlockNode) it3.next())
        }
        Iterator it4 = methodNode.getBasicBlocks().iterator()
        while (it4.hasNext()) {
            processTryCatchBlocks(methodNode, (BlockNode) it4.next())
        }
    }
}
