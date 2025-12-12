package jadx.core.dex.visitors.typeinference

import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import java.util.Iterator
import java.util.List

class PostTypeInference {
    private constructor() {
    }

    private fun fixArrayTypes(DexNode dexNode, InsnArg insnArg, InsnArg insnArg2) {
        Boolean z = false
        if (!insnArg2.getType().isTypeKnown() && insnArg2.merge(dexNode, insnArg.getType().getArrayElement())) {
            z = true
        }
        if (insnArg.getType().isTypeKnown() || !insnArg.merge(dexNode, ArgType.array(insnArg2.getType()))) {
            return z
        }
        return true
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun process(MethodNode methodNode, InsnNode insnNode) {
        Int i
        Boolean z
        Int i2 = 0
        DexNode dexNodeDex = methodNode.dex()
        switch (insnNode.getType()) {
            case CONST:
                RegisterArg result = insnNode.getResult()
                LiteralArg literalArg = (LiteralArg) insnNode.getArg(0)
                if (result.getType().isObject()) {
                    Long literal = literalArg.getLiteral()
                    if (literal != 0) {
                        ArgType argType = literal == 1 ? ArgType.BOOLEAN : ArgType.INT
                        literalArg.setType(argType)
                        result.getSVar().setType(argType)
                        return true
                    }
                }
                return literalArg.merge(dexNodeDex, result)
            case MOVE:
                z = insnNode.getResult().merge(dexNodeDex, insnNode.getArg(0))
                if (insnNode.getArg(0).merge(dexNodeDex, insnNode.getResult())) {
                    return true
                }
                break
            case AGET:
                return fixArrayTypes(dexNodeDex, insnNode.getArg(0), insnNode.getResult())
            case APUT:
                return fixArrayTypes(dexNodeDex, insnNode.getArg(0), insnNode.getArg(2))
            case IF:
                z = insnNode.getArg(1).merge(dexNodeDex, insnNode.getArg(0))
                if (insnNode.getArg(0).merge(dexNodeDex, insnNode.getArg(1))) {
                    return true
                }
                break
            case INVOKE:
                InvokeNode invokeNode = (InvokeNode) insnNode
                MethodInfo callMth = invokeNode.getCallMth()
                MethodNode methodNodeResolveMethod = methodNode.dex().resolveMethod(callMth)
                if (methodNodeResolveMethod != null && methodNodeResolveMethod.isArgsOverload()) {
                    List argumentsTypes = callMth.getArgumentsTypes()
                    Int argsCount = invokeNode.getArgsCount() - 1
                    Int size = argumentsTypes.size() - 1
                    while (size >= 0) {
                        ArgType argType2 = (ArgType) argumentsTypes.get(size)
                        Int i3 = argsCount - 1
                        InsnArg arg = invokeNode.getArg(argsCount)
                        if (!arg.isRegister() || argType2.equals(arg.getType())) {
                            i = i2
                        } else {
                            arg.setType(argType2)
                            i = 1
                        }
                        size--
                        argsCount = i3
                        i2 = i
                    }
                }
                return i2
            case CHECK_CAST:
                ArgType argType3 = (ArgType) ((IndexInsnNode) insnNode).getIndex()
                RegisterArg result2 = insnNode.getResult()
                ArgType type = result2.getType()
                if (argType3.isObject() && type.isObject() && argType3.getObject().equals(type.getObject())) {
                    i2 = 1
                }
                if (i2 != 0) {
                    return true
                }
                result2.getSVar().setType(argType3)
                return true
            case PHI:
            case MERGE:
                ArgType type2 = insnNode.getResult().getType()
                if (!type2.isTypeKnown()) {
                    Iterator it = insnNode.getArguments().iterator()
                    while (true) {
                        if (it.hasNext()) {
                            InsnArg insnArg = (InsnArg) it.next()
                            if (insnArg.getType().isTypeKnown()) {
                                type2 = insnArg.getType()
                            }
                        }
                    }
                }
                Boolean z2 = updateType(insnNode.getResult(), type2)
                while (true) {
                    Int i4 = i2
                    Boolean z3 = z2
                    if (i4 >= insnNode.getArgsCount()) {
                        return z3
                    }
                    z2 = updateType((RegisterArg) insnNode.getArg(i4), type2) ? true : z3
                    i2 = i4 + 1
                }
            default:
                return false
        }
        return z
    }

    private fun updateType(RegisterArg registerArg, ArgType argType) {
        ArgType type = registerArg.getType()
        if (type != null && type.equals(argType)) {
            return false
        }
        registerArg.setType(argType)
        return true
    }
}
