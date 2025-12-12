package jadx.core.dex.visitors.typeinference

import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.visitors.AbstractVisitor
import java.util.Iterator
import java.util.List

class TypeInference extends AbstractVisitor {
    private fun processPhiNode(PhiInsn phiInsn) {
        ArgType type
        ArgType type2 = phiInsn.getResult().getType()
        if (type2.isTypeKnown()) {
            type = type2
        } else {
            for (InsnArg insnArg : phiInsn.getArguments()) {
                if (insnArg.getType().isTypeKnown()) {
                    type = insnArg.getType()
                    break
                }
            }
            type = type2
        }
        phiInsn.getResult().setType(type)
        for (Int i = 0; i < phiInsn.getArgsCount(); i++) {
            RegisterArg arg = phiInsn.getArg(i)
            arg.setType(type)
            SSAVar sVar = arg.getSVar()
            if (sVar != null) {
                sVar.setName(phiInsn.getResult().getName())
            }
        }
    }

    private fun processType(DexNode dexNode, SSAVar sSAVar) {
        RegisterArg assign = sSAVar.getAssign()
        List useList = sSAVar.getUseList()
        if (useList.isEmpty() || sSAVar.isTypeImmutable()) {
            return assign.getType()
        }
        ArgType type = assign.getType()
        Iterator it = useList.iterator()
        while (it.hasNext()) {
            ArgType argTypeMerge = ArgType.merge(dexNode, type, ((RegisterArg) it.next()).getType())
            if (argTypeMerge == null) {
                argTypeMerge = type
            }
            type = argTypeMerge
        }
        return type
    }

    private fun processVarName(SSAVar sSAVar) {
        String name = sSAVar.getAssign().getName()
        if (name != null) {
            return name
        }
        Iterator it = sSAVar.getUseList().iterator()
        while (it.hasNext()) {
            String name2 = ((RegisterArg) it.next()).getName()
            if (name2 != null) {
                return name2
            }
        }
        return null
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        DexNode dexNodeDex = methodNode.dex()
        for (SSAVar sSAVar : methodNode.getSVars()) {
            ArgType argTypeProcessType = processType(dexNodeDex, sSAVar)
            if (argTypeProcessType == null) {
                argTypeProcessType = ArgType.UNKNOWN
            }
            sSAVar.setType(argTypeProcessType)
            sSAVar.setName(processVarName(sSAVar))
        }
        Iterator it = methodNode.getSVars().iterator()
        while (it.hasNext()) {
            PhiInsn usedInPhi = ((SSAVar) it.next()).getUsedInPhi()
            if (usedInPhi != null) {
                processPhiNode(usedInPhi)
            }
        }
    }
}
