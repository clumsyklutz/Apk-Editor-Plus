package jadx.core.dex.visitors

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.FieldReplaceAttr
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.BlockUtils
import jadx.core.utils.InstructionRemover
import java.util.Iterator
import java.util.List

@JadxVisitor(desc = "Remove synthetic classes, methods and fields", name = "ClassModifier", runAfter = {ModVisitor.class})
class ClassModifier extends AbstractVisitor {
    private fun isMethodUniq(ClassNode classNode, MethodNode methodNode) {
        MethodInfo methodInfo = methodNode.getMethodInfo()
        for (MethodNode methodNode2 : classNode.getMethods()) {
            if (methodNode2 != methodNode) {
                MethodInfo methodInfo2 = methodNode2.getMethodInfo()
                if (methodInfo2.getName().equals(methodInfo.getName()) && methodInfo2.getArgumentsTypes().size() == methodInfo.getArgumentsTypes().size()) {
                    return false
                }
            }
        }
        return true
    }

    private fun markAnonymousClass(ClassNode classNode) {
        if (classNode.isAnonymous()) {
            classNode.add(AFlag.ANONYMOUS_CLASS)
        }
    }

    private fun removeEmptyMethods(ClassNode classNode) {
        List basicBlocks
        for (MethodNode methodNode : classNode.getMethods()) {
            AccessInfo accessFlags = methodNode.getAccessFlags()
            if (accessFlags.isConstructor() && (accessFlags.isPublic() || accessFlags.isStatic())) {
                if (methodNode.getArguments(false).isEmpty() && !methodNode.contains(AType.JADX_ERROR) && ((basicBlocks = methodNode.getBasicBlocks()) == null || basicBlocks.isEmpty() || BlockUtils.isAllBlocksEmpty(basicBlocks))) {
                    methodNode.add(AFlag.DONT_GENERATE)
                }
            }
        }
    }

    private fun removeFieldUsageFromConstructor(MethodNode methodNode, FieldNode fieldNode, ClassNode classNode) {
        if (methodNode.isNoCode() || !methodNode.getAccessFlags().isConstructor()) {
            return false
        }
        List arguments = methodNode.getArguments(false)
        if (arguments.isEmpty() || methodNode.contains(AFlag.SKIP_FIRST_ARG)) {
            return false
        }
        RegisterArg registerArg = (RegisterArg) arguments.get(0)
        if (!registerArg.getType().equals(classNode.getClassInfo().getType())) {
            return false
        }
        BlockNode blockNode = (BlockNode) methodNode.getBasicBlocks().get(0)
        List instructions = blockNode.getInstructions()
        if (instructions.isEmpty()) {
            return false
        }
        InsnNode insnNode = (InsnNode) instructions.get(0)
        if (insnNode.getType() != InsnType.IPUT) {
            return false
        }
        IndexInsnNode indexInsnNode = (IndexInsnNode) insnNode
        FieldInfo fieldInfo = (FieldInfo) indexInsnNode.getIndex()
        if (!fieldInfo.equals(fieldNode.getFieldInfo()) || !indexInsnNode.getArg(0).equals(registerArg)) {
            return false
        }
        methodNode.removeFirstArgument()
        InstructionRemover.remove(methodNode, blockNode, insnNode)
        if (registerArg.getSVar().getUseCount() != 0) {
            IndexInsnNode indexInsnNode2 = IndexInsnNode(InsnType.IGET, fieldInfo, 1)
            indexInsnNode2.addArg(insnNode.getArg(1))
            Iterator it = registerArg.getSVar().getUseList().iterator()
            while (it.hasNext()) {
                ((InsnArg) it.next()).wrapInstruction(indexInsnNode2)
            }
        }
        return true
    }

    private fun removeSyntheticFields(ClassNode classNode) {
        Int i
        if (!classNode.getClassInfo().isInner() || classNode.getAccessFlags().isStatic()) {
            return
        }
        for (FieldNode fieldNode : classNode.getFields()) {
            if (fieldNode.getAccessFlags().isSynthetic() && fieldNode.getType().isObject()) {
                ClassNode classNodeResolveClass = classNode.dex().resolveClass(ClassInfo.fromType(classNode.dex(), fieldNode.getType()))
                ClassInfo parentClass = classNode.getClassInfo().getParentClass()
                if (classNodeResolveClass != null && parentClass.equals(classNodeResolveClass.getClassInfo()) && fieldNode.getName().startsWith("this$")) {
                    Int i2 = 0
                    Iterator it = classNode.getMethods().iterator()
                    while (true) {
                        i = i2
                        if (!it.hasNext()) {
                            break
                        } else {
                            i2 = removeFieldUsageFromConstructor((MethodNode) it.next(), fieldNode, classNodeResolveClass) ? i + 1 : i
                        }
                    }
                    if (i != 0) {
                        fieldNode.addAttr(FieldReplaceAttr(parentClass))
                        fieldNode.add(AFlag.DONT_GENERATE)
                    }
                }
            }
        }
    }

    private fun removeSyntheticMethods(ClassNode classNode) {
        for (MethodNode methodNode : classNode.getMethods()) {
            if (!methodNode.isNoCode()) {
                AccessInfo accessFlags = methodNode.getAccessFlags()
                if (accessFlags.isBridge() && accessFlags.isSynthetic() && !isMethodUniq(classNode, methodNode)) {
                    methodNode.add(AFlag.DONT_GENERATE)
                } else if (accessFlags.isSynthetic() && accessFlags.isConstructor() && methodNode.getBasicBlocks().size() == 2) {
                    List instructions = ((BlockNode) methodNode.getBasicBlocks().get(0)).getInstructions()
                    if (instructions.size() == 1 && ((InsnNode) instructions.get(0)).getType() == InsnType.CONSTRUCTOR) {
                        ConstructorInsn constructorInsn = (ConstructorInsn) instructions.get(0)
                        List<RegisterArg> arguments = methodNode.getArguments(false)
                        if (constructorInsn.isThis() && !arguments.isEmpty()) {
                            if (((RegisterArg) arguments.get(0)).getType().equals(classNode.getParentClass().getClassInfo().getType())) {
                                ((RegisterArg) arguments.get(0)).add(AFlag.SKIP_ARG)
                            }
                            for (RegisterArg registerArg : arguments) {
                                SSAVar sVar = registerArg.getSVar()
                                if (sVar != null && sVar.getUseCount() == 0) {
                                    registerArg.add(AFlag.SKIP_ARG)
                                }
                            }
                            methodNode.add(AFlag.DONT_GENERATE)
                        }
                    }
                }
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        Iterator it = classNode.getInnerClasses().iterator()
        while (it.hasNext()) {
            visit((ClassNode) it.next())
        }
        if (classNode.getAccessFlags().isSynthetic() && classNode.getFields().isEmpty() && classNode.getMethods().isEmpty()) {
            classNode.add(AFlag.DONT_GENERATE)
        } else {
            removeSyntheticFields(classNode)
            removeSyntheticMethods(classNode)
            removeEmptyMethods(classNode)
            markAnonymousClass(classNode)
        }
        return false
    }
}
