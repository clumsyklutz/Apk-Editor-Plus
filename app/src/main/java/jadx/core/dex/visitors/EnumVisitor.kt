package jadx.core.dex.visitors

import jadx.core.codegen.TypeGen
import jadx.core.deobf.NameMapper
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.nodes.EnumClassAttr
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.ErrorsCounter
import jadx.core.utils.InsnUtils
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

@JadxVisitor(desc = "Restore enum classes", name = "EnumVisitor", runAfter = {CodeShrinker.class, ModVisitor.class})
class EnumVisitor extends AbstractVisitor {
    private static val LOG = c.a(EnumVisitor.class)

    private fun getConstString(DexNode dexNode, InsnArg insnArg) {
        if (insnArg.isInsnWrap()) {
            Object constValueByInsn = InsnUtils.getConstValueByInsn(dexNode, ((InsnWrapArg) insnArg).getWrapInsn())
            if (constValueByInsn is String) {
                return (String) constValueByInsn
            }
        }
        return null
    }

    private fun getConstructorInsn(InsnNode insnNode) {
        if (insnNode.getArgsCount() != 1) {
            return null
        }
        InsnArg arg = insnNode.getArg(0)
        if (arg.isInsnWrap()) {
            InsnNode wrapInsn = ((InsnWrapArg) arg).getWrapInsn()
            if (wrapInsn.getType() == InsnType.CONSTRUCTOR) {
                return (ConstructorInsn) wrapInsn
            }
        }
        return null
    }

    private fun isEnumArrayField(ClassInfo classInfo, FieldNode fieldNode) {
        if (fieldNode.getAccessFlags().isSynthetic()) {
            ArgType type = fieldNode.getType()
            if (type.isArray() && type.getArrayRootElement().equals(classInfo.getType())) {
                return true
            }
        }
        return false
    }

    private fun processEnumInnerCls(ConstructorInsn constructorInsn, EnumClassAttr.EnumField enumField, ClassNode classNode) {
        if (classNode.getClassInfo().equals(constructorInsn.getClassType())) {
            for (MethodNode methodNode : classNode.getMethods()) {
                if (methodNode.getAccessFlags().isConstructor()) {
                    methodNode.add(AFlag.DONT_GENERATE)
                }
            }
            enumField.setCls(classNode)
            classNode.add(AFlag.DONT_GENERATE)
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        MethodNode methodNode
        ClassInfo classType
        ClassNode classNodeResolveClass
        if (!classNode.isEnum()) {
            return true
        }
        Iterator it = classNode.getMethods().iterator()
        while (true) {
            if (!it.hasNext()) {
                methodNode = null
                break
            }
            MethodNode methodNode2 = (MethodNode) it.next()
            if (methodNode2.getMethodInfo().isClassInit()) {
                methodNode = methodNode2
                break
            }
        }
        if (methodNode == null) {
            ErrorsCounter.classError(classNode, "Enum class init method not found")
            return true
        }
        ArgType type = classNode.getClassInfo().getType()
        String str = "valueOf(Ljava/lang/String;)" + TypeGen.signature(type)
        String str2 = "values()" + TypeGen.signature(ArgType.array(type))
        ArrayList arrayList = ArrayList()
        for (FieldNode fieldNode : classNode.getFields()) {
            if (fieldNode.getAccessFlags().isEnum()) {
                arrayList.add(fieldNode)
                fieldNode.add(AFlag.DONT_GENERATE)
            } else if (fieldNode.getAccessFlags().isSynthetic()) {
                fieldNode.add(AFlag.DONT_GENERATE)
            }
        }
        for (MethodNode methodNode3 : classNode.getMethods()) {
            MethodInfo methodInfo = methodNode3.getMethodInfo()
            if (!methodInfo.isClassInit()) {
                String shortId = methodInfo.getShortId()
                Boolean zIsSynthetic = methodNode3.getAccessFlags().isSynthetic()
                if (!methodInfo.isConstructor() || zIsSynthetic) {
                    if (zIsSynthetic || shortId.equals(str2) || shortId.equals(str)) {
                        methodNode3.add(AFlag.DONT_GENERATE)
                    }
                } else if (shortId.equals("<init>(Ljava/lang/String;I)V")) {
                    methodNode3.add(AFlag.DONT_GENERATE)
                }
            }
        }
        EnumClassAttr enumClassAttr = EnumClassAttr(arrayList.size())
        classNode.addAttr(enumClassAttr)
        enumClassAttr.setStaticMethod(methodNode)
        ClassInfo classInfo = classNode.getClassInfo()
        BlockNode blockNode = (BlockNode) methodNode.getBasicBlocks().get(0)
        ArrayList<InsnNode> arrayList2 = ArrayList()
        List instructions = blockNode.getInstructions()
        Int size = instructions.size()
        Int i = 0
        while (true) {
            if (i >= size) {
                break
            }
            InsnNode insnNode = (InsnNode) instructions.get(i)
            if (insnNode.getType() == InsnType.SPUT) {
                FieldInfo fieldInfo = (FieldInfo) ((IndexInsnNode) insnNode).getIndex()
                if (fieldInfo.getDeclClass().equals(classInfo)) {
                    FieldNode fieldNodeSearchField = classNode.searchField(fieldInfo)
                    if (fieldNodeSearchField == null || !isEnumArrayField(classInfo, fieldNodeSearchField)) {
                        arrayList2.add(insnNode)
                    } else if (i == size - 1) {
                        methodNode.add(AFlag.DONT_GENERATE)
                    } else {
                        instructions.subList(0, i + 1).clear()
                    }
                } else {
                    continue
                }
            }
            i++
        }
        for (InsnNode insnNode2 : arrayList2) {
            ConstructorInsn constructorInsn = getConstructorInsn(insnNode2)
            if (constructorInsn != null && constructorInsn.getArgsCount() >= 2 && (classNodeResolveClass = classNode.dex().resolveClass((classType = constructorInsn.getClassType()))) != null && (classType.equals(classInfo) || classNodeResolveClass.getAccessFlags().isEnum())) {
                FieldInfo fieldInfo2 = (FieldInfo) ((IndexInsnNode) insnNode2).getIndex()
                String constString = getConstString(classNode.dex(), constructorInsn.getArg(0))
                if (constString != null && !fieldInfo2.getAlias().equals(constString) && NameMapper.isValidIdentifier(constString)) {
                    fieldInfo2.setAlias(constString)
                }
                EnumClassAttr.EnumField enumField = new EnumClassAttr.EnumField(fieldInfo2, constructorInsn, 2)
                enumClassAttr.getFields().add(enumField)
                if (!constructorInsn.getClassType().equals(classInfo)) {
                    Iterator it2 = classNode.getInnerClasses().iterator()
                    while (it2.hasNext()) {
                        processEnumInnerCls(constructorInsn, enumField, (ClassNode) it2.next())
                    }
                }
            }
        }
        return false
    }
}
