package jadx.core.utils

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

class InstructionRemover {
    private static val LOG = c.a(InstructionRemover.class)
    private List instrList
    private final MethodNode mth
    private final List toRemove

    constructor(MethodNode methodNode) {
        this(methodNode, null)
    }

    constructor(MethodNode methodNode, BlockNode blockNode) {
        this.mth = methodNode
        this.toRemove = ArrayList()
        if (blockNode != null) {
            this.instrList = blockNode.getInstructions()
        }
    }

    fun fixUsedInPhiFlag(RegisterArg registerArg) {
        PhiInsn phiInsn = null
        Iterator it = registerArg.getSVar().getUseList().iterator()
        while (it.hasNext()) {
            InsnNode parentInsn = ((RegisterArg) it.next()).getParentInsn()
            phiInsn = (parentInsn != null && parentInsn.getType() == InsnType.PHI && parentInsn.containsArg(registerArg)) ? (PhiInsn) parentInsn : phiInsn
        }
        registerArg.getSVar().setUsedInPhi(phiInsn)
    }

    fun remove(MethodNode methodNode, BlockNode blockNode, Int i) {
        List instructions = blockNode.getInstructions()
        unbindInsn(methodNode, (InsnNode) instructions.get(i))
        instructions.remove(i)
    }

    fun remove(MethodNode methodNode, BlockNode blockNode, InsnNode insnNode) {
        unbindInsn(methodNode, insnNode)
        Iterator it = blockNode.getInstructions().iterator()
        while (it.hasNext()) {
            if (((InsnNode) it.next()) == insnNode) {
                it.remove()
                return
            }
        }
    }

    fun remove(MethodNode methodNode, InsnNode insnNode) {
        BlockNode blockByInsn = BlockUtils.getBlockByInsn(methodNode, insnNode)
        if (blockByInsn != null) {
            remove(methodNode, blockByInsn, insnNode)
        }
    }

    fun removeAll(MethodNode methodNode, BlockNode blockNode, List list) {
        if (list.isEmpty()) {
            return
        }
        removeAll(methodNode, blockNode.getInstructions(), list)
    }

    private fun removeAll(MethodNode methodNode, List list, List list2) {
        Iterator it = list2.iterator()
        while (it.hasNext()) {
            InsnNode insnNode = (InsnNode) it.next()
            unbindInsn(methodNode, insnNode)
            Int size = list.size()
            Int i = 0
            while (true) {
                if (i >= size) {
                    break
                }
                if (list.get(i) == insnNode) {
                    list.remove(i)
                    break
                }
                i++
            }
        }
    }

    fun unbindArgUsage(MethodNode methodNode, InsnArg insnArg) {
        if (!(insnArg is RegisterArg)) {
            if (insnArg is InsnWrapArg) {
                unbindInsn(methodNode, ((InsnWrapArg) insnArg).getWrapInsn())
            }
        } else {
            RegisterArg registerArg = (RegisterArg) insnArg
            SSAVar sVar = registerArg.getSVar()
            if (sVar != null) {
                sVar.removeUse(registerArg)
            }
        }
    }

    fun unbindInsn(MethodNode methodNode, InsnNode insnNode) {
        unbindResult(methodNode, insnNode)
        Iterator it = insnNode.getArguments().iterator()
        while (it.hasNext()) {
            unbindArgUsage(methodNode, (InsnArg) it.next())
        }
        if (insnNode.getType() == InsnType.PHI) {
            for (InsnArg insnArg : insnNode.getArguments()) {
                if (insnArg is RegisterArg) {
                    fixUsedInPhiFlag((RegisterArg) insnArg)
                }
            }
        }
        insnNode.add(AFlag.INCONSISTENT_CODE)
    }

    fun unbindInsnList(MethodNode methodNode, List list) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            unbindInsn(methodNode, (InsnNode) it.next())
        }
    }

    fun unbindResult(MethodNode methodNode, InsnNode insnNode) {
        RegisterArg result = insnNode.getResult()
        if (result == null || result.getSVar() == null) {
            return
        }
        methodNode.removeSVar(result.getSVar())
    }

    fun add(InsnNode insnNode) {
        this.toRemove.add(insnNode)
    }

    fun perform() {
        if (this.toRemove.isEmpty()) {
            return
        }
        removeAll(this.mth, this.instrList, this.toRemove)
        this.toRemove.clear()
    }

    fun setBlock(BlockNode blockNode) {
        this.instrList = blockNode.getInstructions()
    }
}
