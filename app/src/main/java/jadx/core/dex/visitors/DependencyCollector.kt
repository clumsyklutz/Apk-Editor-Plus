package jadx.core.dex.visitors

import jadx.core.dex.attributes.AType
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import java.util.Iterator
import java.util.Set

class DependencyCollector extends AbstractVisitor {
    private fun addDep(DexNode dexNode, Set set, ClassInfo classInfo) {
        if (classInfo != null) {
            addDep(dexNode, set, dexNode.resolveClass(classInfo))
        }
    }

    private fun addDep(DexNode dexNode, Set set, ArgType argType) {
        while (argType != null) {
            if (argType.isObject()) {
                addDep(dexNode, set, ClassInfo.fromName(dexNode, argType.getObject()))
                Array<ArgType> genericTypes = argType.getGenericTypes()
                if (!argType.isGeneric() || genericTypes == null) {
                    return
                }
                for (ArgType argType2 : genericTypes) {
                    addDep(dexNode, set, argType2)
                }
                return
            }
            if (!argType.isArray()) {
                return
            } else {
                argType = argType.getArrayRootElement()
            }
        }
    }

    private fun addDep(DexNode dexNode, Set set, ClassNode classNode) {
        if (classNode != null) {
            set.add(classNode.getTopParentClass())
        }
    }

    private fun processClass(ClassNode classNode, DexNode dexNode, Set set) {
        addDep(dexNode, set, classNode.getSuperClass())
        Iterator it = classNode.getInterfaces().iterator()
        while (it.hasNext()) {
            addDep(dexNode, set, (ArgType) it.next())
        }
        Iterator it2 = classNode.getFields().iterator()
        while (it2.hasNext()) {
            addDep(dexNode, set, ((FieldNode) it2.next()).getType())
        }
        for (MethodNode methodNode : classNode.getMethods()) {
            if (!methodNode.isNoCode() && !methodNode.contains(AType.JADX_ERROR)) {
                processMethod(dexNode, set, methodNode)
            }
        }
    }

    private fun processCustomInsn(DexNode dexNode, Set set, InsnNode insnNode) {
        if (!(insnNode is IndexInsnNode)) {
            if (insnNode is InvokeNode) {
                addDep(dexNode, set, ((InvokeNode) insnNode).getCallMth().getDeclClass())
                return
            }
            return
        }
        Object index = ((IndexInsnNode) insnNode).getIndex()
        if (index is FieldInfo) {
            addDep(dexNode, set, ((FieldInfo) index).getDeclClass())
        } else if (index is ArgType) {
            addDep(dexNode, set, (ArgType) index)
        }
    }

    private fun processInsn(DexNode dexNode, Set set, InsnNode insnNode) {
        RegisterArg result = insnNode.getResult()
        if (result != null) {
            addDep(dexNode, set, result.getType())
        }
        for (InsnArg insnArg : insnNode.getArguments()) {
            if (insnArg.isInsnWrap()) {
                processInsn(dexNode, set, ((InsnWrapArg) insnArg).getWrapInsn())
            } else {
                addDep(dexNode, set, insnArg.getType())
            }
        }
        processCustomInsn(dexNode, set, insnNode)
    }

    private fun processMethod(DexNode dexNode, Set set, MethodNode methodNode) {
        addDep(dexNode, set, methodNode.getParentClass())
        addDep(dexNode, set, methodNode.getReturnType())
        Iterator it = methodNode.getMethodInfo().getArgumentsTypes().iterator()
        while (it.hasNext()) {
            addDep(dexNode, set, (ArgType) it.next())
        }
        Iterator it2 = methodNode.getBasicBlocks().iterator()
        while (it2.hasNext()) {
            Iterator it3 = ((BlockNode) it2.next()).getInstructions().iterator()
            while (it3.hasNext()) {
                processInsn(dexNode, set, (InsnNode) it3.next())
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        DexNode dexNodeDex = classNode.dex()
        Set dependencies = classNode.getDependencies()
        processClass(classNode, dexNodeDex, dependencies)
        Iterator it = classNode.getInnerClasses().iterator()
        while (it.hasNext()) {
            processClass((ClassNode) it.next(), dexNodeDex, dependencies)
        }
        dependencies.remove(classNode)
        return false
    }
}
