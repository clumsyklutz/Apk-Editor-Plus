package jadx.core.dex.visitors

import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.ArithNode
import jadx.core.dex.instructions.ArithOp
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.FieldArg
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.mods.TernaryInsn
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.conditions.IfCondition
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

class SimplifyVisitor extends AbstractVisitor {
    private static val LOG = c.a(SimplifyVisitor.class)

    private fun convertFieldArith(MethodNode methodNode, InsnNode insnNode) {
        InsnArg arg
        InsnArg arg2 = insnNode.getArg(0)
        if (!arg2.isInsnWrap()) {
            return null
        }
        InsnNode wrapInsn = ((InsnWrapArg) arg2).getWrapInsn()
        InsnType type = wrapInsn.getType()
        if ((type != InsnType.ARITH && type != InsnType.STR_CONCAT) || !wrapInsn.getArg(0).isInsnWrap()) {
            return null
        }
        InsnNode wrapInsn2 = ((InsnWrapArg) wrapInsn.getArg(0)).getWrapInsn()
        InsnType type2 = wrapInsn2.getType()
        if (type2 != InsnType.IGET && type2 != InsnType.SGET) {
            return null
        }
        FieldInfo fieldInfo = (FieldInfo) ((IndexInsnNode) insnNode).getIndex()
        if (!fieldInfo.equals((FieldInfo) ((IndexInsnNode) wrapInsn2).getIndex())) {
            return null
        }
        try {
            if (type2 == InsnType.IGET) {
                arg = wrapInsn2.getArg(0)
                if (!arg.equals(insnNode.getArg(1))) {
                    return null
                }
            } else {
                arg = null
            }
            FieldArg fieldArg = FieldArg(fieldInfo, arg)
            if (arg != null) {
                fieldArg.setType(wrapInsn2.getArg(0).getType())
            }
            if (type == InsnType.ARITH) {
                ArithNode arithNode = (ArithNode) wrapInsn
                return ArithNode(arithNode.getOp(), fieldArg, arithNode.getArg(1))
            }
            Int argsCount = wrapInsn.getArgsCount()
            InsnNode insnNode2 = InsnNode(InsnType.STR_CONCAT, argsCount - 1)
            for (Int i = 1; i < argsCount; i++) {
                insnNode2.addArg(wrapInsn.getArg(i))
            }
            return ArithNode(ArithOp.ADD, fieldArg, InsnArg.wrapArg(insnNode2))
        } catch (Exception e) {
            LOG.a("Can't convert field arith insn: {}, mth: {}", insnNode, methodNode, e)
            return null
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00e9 A[Catch: Throwable -> 0x0108, TryCatch #0 {Throwable -> 0x0108, blocks: (B:8:0x0032, B:10:0x003c, B:14:0x004e, B:16:0x0064, B:19:0x0075, B:21:0x0084, B:23:0x0098, B:24:0x00a1, B:26:0x00a7, B:42:0x00ff, B:37:0x00e9, B:39:0x00f4, B:27:0x00b9, B:29:0x00bf, B:32:0x00d1, B:34:0x00d7), top: B:49:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static jadx.core.dex.nodes.InsnNode convertInvoke(jadx.core.dex.nodes.MethodNode r11, jadx.core.dex.nodes.InsnNode r12) {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.core.dex.visitors.SimplifyVisitor.convertInvoke(jadx.core.dex.nodes.MethodNode, jadx.core.dex.nodes.InsnNode):jadx.core.dex.nodes.InsnNode")
    }

    private fun flattenInsnChain(InsnNode insnNode) {
        ArrayList arrayList = ArrayList()
        InsnArg arg = insnNode.getArg(0)
        while (arg.isInsnWrap()) {
            InsnNode wrapInsn = ((InsnWrapArg) arg).getWrapInsn()
            arrayList.add(wrapInsn)
            if (wrapInsn.getArgsCount() == 0) {
                break
            }
            arg = wrapInsn.getArg(0)
        }
        Collections.reverse(arrayList)
        return arrayList
    }

    private fun processCast(MethodNode methodNode, InsnNode insnNode) {
        InsnArg arg = insnNode.getArg(0)
        ArgType type = arg.getType()
        if (arg.isInsnWrap()) {
            InsnNode wrapInsn = ((InsnWrapArg) arg).getWrapInsn()
            if (wrapInsn.getType() == InsnType.INVOKE) {
                type = ((InvokeNode) wrapInsn).getCallMth().getReturnType()
            }
        }
        if (ArgType.isCastNeeded(methodNode.dex(), type, (ArgType) ((IndexInsnNode) insnNode).getIndex())) {
            return null
        }
        InsnNode insnNode2 = InsnNode(InsnType.MOVE, 1)
        insnNode2.setOffset(insnNode.getOffset())
        insnNode2.setResult(insnNode.getResult())
        insnNode2.addArg(arg)
        return insnNode2
    }

    private fun simplifyArith(InsnNode insnNode) {
        InsnArg arg
        ArithNode arithNode = (ArithNode) insnNode
        if (arithNode.getArgsCount() != 2) {
            return null
        }
        InsnArg arg2 = arithNode.getArg(1)
        if (arg2.isInsnWrap()) {
            InsnNode wrapInsn = ((InsnWrapArg) arg2).getWrapInsn()
            arg = wrapInsn.getType() == InsnType.CONST ? wrapInsn.getArg(0) : null
        } else {
            arg = arg2.isLiteral() ? arg2 : null
        }
        if (arg == null) {
            return null
        }
        Long literal = ((LiteralArg) arg).getLiteral()
        if (arithNode.getOp() != ArithOp.ADD || literal >= 0) {
            return null
        }
        return ArithNode(ArithOp.SUB, arithNode.getResult(), insnNode.getArg(0), InsnArg.lit(-literal, arg.getType()))
    }

    private fun simplifyIf(IfNode ifNode) {
        InsnArg arg = ifNode.getArg(0)
        if (arg.isInsnWrap()) {
            InsnNode wrapInsn = ((InsnWrapArg) arg).getWrapInsn()
            if (wrapInsn.getType() == InsnType.CMP_L || wrapInsn.getType() == InsnType.CMP_G) {
                if (ifNode.getArg(1).isLiteral() && ((LiteralArg) ifNode.getArg(1)).getLiteral() == 0) {
                    ifNode.changeCondition(ifNode.getOp(), wrapInsn.getArg(0), wrapInsn.getArg(1))
                } else {
                    LOG.c("TODO: cmp {}", ifNode)
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private fun simplifyInsn(MethodNode methodNode, InsnNode insnNode) {
        InsnNode insnNodeSimplifyInsn
        for (InsnArg insnArg : insnNode.getArguments()) {
            if (insnArg.isInsnWrap() && (insnNodeSimplifyInsn = simplifyInsn(methodNode, ((InsnWrapArg) insnArg).getWrapInsn())) != null) {
                insnArg.wrapInstruction(insnNodeSimplifyInsn)
            }
        }
        switch (insnNode.getType()) {
            case ARITH:
                return simplifyArith(insnNode)
            case IF:
                simplifyIf((IfNode) insnNode)
                return null
            case TERNARY:
                simplifyTernary((TernaryInsn) insnNode)
                return null
            case INVOKE:
                return convertInvoke(methodNode, insnNode)
            case IPUT:
            case SPUT:
                return convertFieldArith(methodNode, insnNode)
            case CHECK_CAST:
                return processCast(methodNode, insnNode)
            case MOVE:
                InsnArg arg = insnNode.getArg(0)
                if (arg.isLiteral()) {
                    InsnNode insnNode2 = InsnNode(InsnType.CONST, 1)
                    insnNode2.setResult(insnNode.getResult())
                    insnNode2.addArg(arg)
                    insnNode2.copyAttributesFrom(insnNode)
                    return insnNode2
                }
                return null
            default:
                return null
        }
    }

    private fun simplifyTernary(TernaryInsn ternaryInsn) {
        IfCondition condition = ternaryInsn.getCondition()
        if (condition.isCompare()) {
            simplifyIf(condition.getCompare().getInsn())
        } else {
            ternaryInsn.simplifyCondition()
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        Iterator it = methodNode.getBasicBlocks().iterator()
        while (it.hasNext()) {
            List instructions = ((BlockNode) it.next()).getInstructions()
            Int i = 0
            while (true) {
                Int i2 = i
                if (i2 < instructions.size()) {
                    InsnNode insnNodeSimplifyInsn = simplifyInsn(methodNode, (InsnNode) instructions.get(i2))
                    if (insnNodeSimplifyInsn != null) {
                        instructions.set(i2, insnNodeSimplifyInsn)
                    }
                    i = i2 + 1
                }
            }
        }
    }
}
