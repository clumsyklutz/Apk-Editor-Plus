package jadx.core.dex.visitors

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.InvokeType
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.visitors.typeinference.PostTypeInference
import jadx.core.utils.InstructionRemover
import java.util.ArrayList
import java.util.List

class ConstInlineVisitor extends AbstractVisitor {
    private fun checkInsn(MethodNode methodNode, InsnNode insnNode) {
        InsnNode assignInsn
        if (insnNode.getType() != InsnType.CONST || insnNode.contains(AFlag.DONT_INLINE)) {
            return false
        }
        InsnArg arg = insnNode.getArg(0)
        if (!arg.isLiteral()) {
            return false
        }
        Long literal = ((LiteralArg) arg).getLiteral()
        SSAVar sVar = insnNode.getResult().getSVar()
        if (literal == 0 && checkObjectInline(sVar)) {
            if (sVar.getUseCount() == 1 && (assignInsn = insnNode.getResult().getAssignInsn()) != null) {
                assignInsn.add(AFlag.DONT_INLINE)
            }
            return false
        }
        ArgType type = insnNode.getResult().getType()
        if (!arg.getType().isTypeKnown()) {
            arg.merge(methodNode.dex(), type)
        }
        return replaceConst(methodNode, insnNode, literal)
    }

    private fun checkObjectInline(SSAVar sSAVar) {
        for (InsnArg insnArg : sSAVar.getUseList()) {
            InsnNode parentInsn = insnArg.getParentInsn()
            if (parentInsn != null) {
                InsnType type = parentInsn.getType()
                if (type == InsnType.INVOKE) {
                    InvokeNode invokeNode = (InvokeNode) parentInsn
                    if (invokeNode.getInvokeType() != InvokeType.STATIC && invokeNode.getArg(0) == insnArg) {
                        return true
                    }
                } else if (type == InsnType.ARRAY_LENGTH && parentInsn.getArg(0) == insnArg) {
                    return true
                }
            }
        }
        return false
    }

    private fun fixTypes(MethodNode methodNode, InsnNode insnNode, LiteralArg literalArg) {
        Int i = 0
        DexNode dexNodeDex = methodNode.dex()
        PostTypeInference.process(methodNode, insnNode)
        switch (insnNode.getType()) {
            case CONST:
                insnNode.getArg(0).merge(dexNodeDex, insnNode.getResult())
                break
            case MOVE:
                insnNode.getResult().merge(dexNodeDex, insnNode.getArg(0))
                insnNode.getArg(0).merge(dexNodeDex, insnNode.getResult())
                break
            case IPUT:
            case SPUT:
                insnNode.getArg(0).merge(dexNodeDex, ((FieldInfo) ((IndexInsnNode) insnNode).getIndex()).getType())
                break
            case IF:
                InsnArg arg = insnNode.getArg(0)
                InsnArg arg2 = insnNode.getArg(1)
                if (arg != literalArg) {
                    arg2.merge(dexNodeDex, arg)
                    break
                } else {
                    arg.merge(dexNodeDex, arg2)
                    break
                }
            case CMP_G:
            case CMP_L:
                InsnArg arg3 = insnNode.getArg(0)
                InsnArg arg4 = insnNode.getArg(1)
                if (arg3 != literalArg) {
                    arg4.merge(dexNodeDex, arg3)
                    break
                } else {
                    arg3.merge(dexNodeDex, arg4)
                    break
                }
            case RETURN:
                if (insnNode.getArgsCount() != 0) {
                    insnNode.getArg(0).merge(dexNodeDex, methodNode.getReturnType())
                    break
                }
                break
            case INVOKE:
                List argumentsTypes = ((InvokeNode) insnNode).getCallMth().getArgumentsTypes()
                Int argsCount = insnNode.getArgsCount()
                Int i2 = argumentsTypes.size() == argsCount ? 0 : -1
                while (i < argsCount) {
                    InsnArg arg5 = insnNode.getArg(i)
                    if (!arg5.getType().isTypeKnown()) {
                        arg5.merge(dexNodeDex, i2 >= 0 ? (ArgType) argumentsTypes.get(i2) : methodNode.getParentClass().getClassInfo().getType())
                    }
                    i++
                    i2++
                }
                break
            case ARITH:
                literalArg.merge(dexNodeDex, insnNode.getResult())
                break
            case APUT:
            case AGET:
                if (literalArg == insnNode.getArg(1)) {
                    literalArg.merge(dexNodeDex, ArgType.INT)
                    break
                }
                break
            case NEW_ARRAY:
                if (literalArg == insnNode.getArg(0)) {
                    literalArg.merge(dexNodeDex, ArgType.INT)
                    break
                }
                break
        }
    }

    private fun replaceConst(MethodNode methodNode, InsnNode insnNode, Long j) {
        Int i
        ArrayList<InsnArg> arrayList = ArrayList(insnNode.getResult().getSVar().getUseList())
        Int i2 = 0
        for (InsnArg insnArg : arrayList) {
            InsnNode parentInsn = insnArg.getParentInsn()
            if (parentInsn != null && parentInsn.getType() != InsnType.PHI && parentInsn.getType() != InsnType.MERGE) {
                ArgType type = insnArg.getType()
                if (type.isObject() && j != 0) {
                    type = ArgType.NARROW_NUMBERS
                }
                LiteralArg literalArgLit = (arrayList.size() == 1 || insnArg.isTypeImmutable()) ? InsnArg.lit(j, type) : (parentInsn.getType() != InsnType.MOVE || parentInsn.getResult().getType().isTypeKnown()) ? InsnArg.lit(j, ArgType.UNKNOWN) : InsnArg.lit(j, type)
                if (parentInsn.replaceArg(insnArg, literalArgLit)) {
                    fixTypes(methodNode, parentInsn, literalArgLit)
                    i = i2 + 1
                    if (parentInsn.getType() == InsnType.RETURN) {
                        parentInsn.setSourceLine(insnNode.getSourceLine())
                    }
                    FieldNode constField = null
                    ArgType type2 = literalArgLit.getType()
                    if (type2.isTypeKnown()) {
                        constField = methodNode.getParentClass().getConstFieldByLiteralArg(literalArgLit)
                    } else if (type2.contains(PrimitiveType.INT)) {
                        constField = methodNode.getParentClass().getConstField(Integer.valueOf((Int) j), false)
                    }
                    if (constField != null) {
                        literalArgLit.wrapInstruction(IndexInsnNode(InsnType.SGET, constField.getFieldInfo(), 0))
                    }
                } else {
                    i = i2
                }
                i2 = i
            }
        }
        return i2 == arrayList.size()
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        ArrayList arrayList = ArrayList()
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            arrayList.clear()
            for (InsnNode insnNode : blockNode.getInstructions()) {
                if (checkInsn(methodNode, insnNode)) {
                    arrayList.add(insnNode)
                }
            }
            InstructionRemover.removeAll(methodNode, blockNode, arrayList)
        }
    }
}
