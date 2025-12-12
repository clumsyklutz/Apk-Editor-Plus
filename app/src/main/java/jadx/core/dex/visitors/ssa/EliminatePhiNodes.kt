package jadx.core.dex.visitors.ssa

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.PhiListAttr
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.BlockUtils
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Iterator
import java.util.List

class EliminatePhiNodes extends AbstractVisitor {
    private fun removeInsn(MethodNode methodNode, BlockNode blockNode, PhiInsn phiInsn) {
        Iterator it = blockNode.getInstructions().iterator()
        while (it.hasNext()) {
            if (((InsnNode) it.next()) == phiInsn) {
                it.remove()
                return
            }
        }
    }

    private fun removePhiInstructions(MethodNode methodNode) {
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            PhiListAttr phiListAttr = (PhiListAttr) blockNode.get(AType.PHI_LIST)
            if (phiListAttr != null) {
                Iterator it = phiListAttr.getList().iterator()
                while (it.hasNext()) {
                    removeInsn(methodNode, blockNode, (PhiInsn) it.next())
                }
            }
        }
    }

    private fun replaceMerge(MethodNode methodNode, BlockNode blockNode, InsnNode insnNode) {
        BlockNode blockNode2
        if (insnNode.getArgsCount() != 2) {
            throw JadxRuntimeException("Unexpected count of arguments in merge insn: " + insnNode)
        }
        RegisterArg registerArg = (RegisterArg) insnNode.getArg(1)
        RegisterArg registerArg2 = (RegisterArg) insnNode.getArg(0)
        Int regNum = registerArg2.getRegNum()
        if (registerArg.getRegNum() == regNum) {
            throw JadxRuntimeException("Unexpected register number in merge insn: " + insnNode)
        }
        SSAVar sVar = registerArg.getSVar()
        RegisterArg assign = sVar.getAssign()
        InsnNode parentInsn = assign.getParentInsn()
        BlockNode blockByInsn = BlockUtils.getBlockByInsn(methodNode, parentInsn)
        if (blockByInsn == null) {
            throw JadxRuntimeException("Unknown assign block for " + parentInsn)
        }
        Iterator it = blockNode.getPredecessors().iterator()
        while (true) {
            if (it.hasNext()) {
                blockNode2 = (BlockNode) it.next()
                if (BlockUtils.isPathExists(blockByInsn, blockNode2)) {
                    break
                }
            } else {
                blockNode2 = null
                break
            }
        }
        if (blockNode2 == null) {
            throw JadxRuntimeException("Assign predecessor not found for " + blockByInsn + " from " + blockNode)
        }
        RegisterArg registerArgDuplicate = registerArg.duplicate(regNum, null)
        SSAVar sSAVarMakeNewSVar = methodNode.makeNewSVar(regNum, methodNode.getNextSVarVersion(regNum), registerArgDuplicate)
        sSAVarMakeNewSVar.setName(sVar.getName())
        sSAVarMakeNewSVar.setType(assign.getType())
        if (parentInsn != null) {
            parentInsn.setResult(registerArgDuplicate)
        }
        for (RegisterArg registerArg3 : sVar.getUseList()) {
            RegisterArg registerArgDuplicate2 = registerArg3.duplicate(regNum, sSAVarMakeNewSVar)
            InsnNode parentInsn2 = registerArg3.getParentInsn()
            if (parentInsn2 != null) {
                sSAVarMakeNewSVar.use(registerArgDuplicate2)
                parentInsn2.replaceArg(registerArg3, registerArgDuplicate2)
            }
        }
        blockNode.getInstructions().remove(0)
        PhiInsn phiInsnAddPhi = SSATransform.addPhi(methodNode, blockNode, regNum)
        phiInsnAddPhi.setResult(insnNode.getResult())
        phiInsnAddPhi.bindArg(registerArgDuplicate.duplicate(), blockNode2)
        phiInsnAddPhi.bindArg(registerArg2.duplicate(), BlockUtils.selectOtherSafe(blockNode2, blockNode.getPredecessors()))
    }

    private fun replaceMergeInstructions(MethodNode methodNode) {
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            List instructions = blockNode.getInstructions()
            if (!instructions.isEmpty()) {
                InsnNode insnNode = (InsnNode) instructions.get(0)
                if (insnNode.getType() == InsnType.MERGE) {
                    replaceMerge(methodNode, blockNode, insnNode)
                }
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        replaceMergeInstructions(methodNode)
        removePhiInstructions(methodNode)
    }
}
