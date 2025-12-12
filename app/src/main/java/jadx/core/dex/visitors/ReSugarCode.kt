package jadx.core.dex.visitors

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.EnumMapAttr
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.FilledNewArrayNode
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.NewArrayNode
import jadx.core.dex.instructions.SwitchNode
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.InstructionRemover
import jadx.core.utils.exceptions.DecodeException
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

@JadxVisitor(desc = "Simplify synthetic or verbose code", name = "ReSugarCode", runAfter = {CodeShrinker.class})
class ReSugarCode extends AbstractVisitor {
    private static val LOG = c.a(ReSugarCode.class)

    class EnumMapInfo {
        private final InsnArg arg
        private final FieldNode mapField

        constructor(InsnArg insnArg, FieldNode fieldNode) {
            this.arg = insnArg
            this.mapField = fieldNode
        }

        fun getArg() {
            return this.arg
        }

        fun getMapField() {
            return this.mapField
        }
    }

    private fun addToEnumMap(MethodNode methodNode, EnumMapAttr enumMapAttr, InsnNode insnNode) {
        EnumMapInfo enumMapInfoCheckEnumMapAccess
        FieldNode fieldNodeResolveField
        InsnArg arg = insnNode.getArg(2)
        if (arg.isLiteral() && (enumMapInfoCheckEnumMapAccess = checkEnumMapAccess(methodNode, insnNode)) != null) {
            InsnArg arg2 = enumMapInfoCheckEnumMapAccess.getArg()
            FieldNode mapField = enumMapInfoCheckEnumMapAccess.getMapField()
            if (mapField == null || !arg2.isInsnWrap()) {
                return
            }
            InsnNode wrapInsn = ((InsnWrapArg) arg2).getWrapInsn()
            if (wrapInsn is IndexInsnNode) {
                Object index = ((IndexInsnNode) wrapInsn).getIndex()
                if (!(index is FieldInfo) || (fieldNodeResolveField = methodNode.dex().resolveField((FieldInfo) index)) == null) {
                    return
                }
                enumMapAttr.add(mapField, Integer.valueOf((Int) ((LiteralArg) arg).getLiteral()), fieldNodeResolveField)
            }
        }
    }

    private fun checkAndHideClass(ClassNode classNode) {
        for (FieldNode fieldNode : classNode.getFields()) {
            AccessInfo accessFlags = fieldNode.getAccessFlags()
            if (accessFlags.isSynthetic() && accessFlags.isStatic() && accessFlags.isFinal() && !fieldNode.contains(AFlag.DONT_GENERATE)) {
                return
            }
        }
        classNode.add(AFlag.DONT_GENERATE)
    }

    fun checkEnumMapAccess(MethodNode methodNode, InsnNode insnNode) {
        InsnArg arg = insnNode.getArg(0)
        InsnArg arg2 = insnNode.getArg(1)
        if (!arg.isInsnWrap() || !arg2.isInsnWrap()) {
            return null
        }
        InsnNode wrapInsn = ((InsnWrapArg) arg2).getWrapInsn()
        InsnNode wrapInsn2 = ((InsnWrapArg) arg).getWrapInsn()
        if (wrapInsn.getType() != InsnType.INVOKE || wrapInsn2.getType() != InsnType.SGET) {
            return null
        }
        InvokeNode invokeNode = (InvokeNode) wrapInsn
        if (!invokeNode.getCallMth().getShortId().equals("ordinal()I")) {
            return null
        }
        ClassNode classNodeResolveClass = methodNode.dex().resolveClass(invokeNode.getCallMth().getDeclClass())
        if (classNodeResolveClass == null || !classNodeResolveClass.isEnum()) {
            return null
        }
        Object index = ((IndexInsnNode) wrapInsn2).getIndex()
        if (!(index is FieldInfo)) {
            return null
        }
        FieldNode fieldNodeResolveField = methodNode.dex().resolveField((FieldInfo) index)
        if (fieldNodeResolveField == null || !fieldNodeResolveField.getAccessFlags().isSynthetic()) {
            return null
        }
        return EnumMapInfo(invokeNode.getArg(0), fieldNodeResolveField)
    }

    private static EnumMapAttr.KeyValueMap getEnumMap(MethodNode methodNode, FieldNode fieldNode) {
        ClassNode parentClass = fieldNode.getParentClass()
        EnumMapAttr enumMapAttr = (EnumMapAttr) parentClass.get(AType.ENUM_MAP)
        if (enumMapAttr != null) {
            return enumMapAttr.getMap(fieldNode)
        }
        EnumMapAttr enumMapAttr2 = EnumMapAttr()
        parentClass.addAttr(enumMapAttr2)
        MethodNode methodNodeSearchMethodByName = parentClass.searchMethodByName("<clinit>()V")
        if (methodNodeSearchMethodByName == null || methodNodeSearchMethodByName.isNoCode()) {
            return null
        }
        if (methodNodeSearchMethodByName.getBasicBlocks() == null) {
            try {
                methodNodeSearchMethodByName.load()
                if (methodNodeSearchMethodByName.getBasicBlocks() == null) {
                    return null
                }
            } catch (DecodeException e) {
                LOG.b("Load failed", (Throwable) e)
                return null
            }
        }
        Iterator it = methodNodeSearchMethodByName.getBasicBlocks().iterator()
        while (it.hasNext()) {
            for (InsnNode insnNode : ((BlockNode) it.next()).getInstructions()) {
                if (insnNode.getType() == InsnType.APUT) {
                    addToEnumMap(methodNode, enumMapAttr2, insnNode)
                }
            }
        }
        return enumMapAttr2.getMap(fieldNode)
    }

    private fun process(MethodNode methodNode, List list, Int i, InstructionRemover instructionRemover) {
        InsnNode insnNode = (InsnNode) list.get(i)
        switch (insnNode.getType()) {
            case NEW_ARRAY:
                return processNewArray(methodNode, list, i, instructionRemover)
            case SWITCH:
                return processEnumSwitch(methodNode, (SwitchNode) insnNode)
            default:
                return null
        }
    }

    private fun processEnumSwitch(MethodNode methodNode, SwitchNode switchNode) {
        EnumMapInfo enumMapInfoCheckEnumMapAccess
        InsnArg arg = switchNode.getArg(0)
        if (arg.isInsnWrap()) {
            InsnNode wrapInsn = ((InsnWrapArg) arg).getWrapInsn()
            if (wrapInsn.getType() == InsnType.AGET && (enumMapInfoCheckEnumMapAccess = checkEnumMapAccess(methodNode, wrapInsn)) != null) {
                FieldNode mapField = enumMapInfoCheckEnumMapAccess.getMapField()
                InsnArg arg2 = enumMapInfoCheckEnumMapAccess.getArg()
                EnumMapAttr.KeyValueMap enumMap = getEnumMap(methodNode, mapField)
                if (enumMap != null) {
                    Array<Object> keys = switchNode.getKeys()
                    Int length = keys.length
                    Int i = 0
                    while (true) {
                        if (i < length) {
                            if (enumMap.get(keys[i]) == null) {
                                break
                            }
                            i++
                        } else if (switchNode.replaceArg(arg, arg2)) {
                            for (Int i2 = 0; i2 < keys.length; i2++) {
                                keys[i2] = enumMap.get(keys[i2])
                            }
                            mapField.add(AFlag.DONT_GENERATE)
                            checkAndHideClass(mapField.getParentClass())
                        }
                    }
                }
            }
        }
        return null
    }

    private fun processNewArray(MethodNode methodNode, List list, Int i, InstructionRemover instructionRemover) {
        NewArrayNode newArrayNode = (NewArrayNode) list.get(i)
        InsnArg arg = newArrayNode.getArg(0)
        if (!arg.isLiteral()) {
            return null
        }
        Int literal = (Int) ((LiteralArg) arg).getLiteral()
        Int size = list.size()
        if (literal <= 0 || i + literal >= size || ((InsnNode) list.get(i + literal)).getType() != InsnType.APUT) {
            return null
        }
        FilledNewArrayNode filledNewArrayNode = FilledNewArrayNode(newArrayNode.getArrayType().getArrayElement(), literal)
        filledNewArrayNode.setResult(newArrayNode.getResult())
        for (Int i2 = 0; i2 < literal; i2++) {
            InsnNode insnNode = (InsnNode) list.get(i + 1 + i2)
            if (insnNode.getType() != InsnType.APUT) {
                LOG.a("Not a APUT in expected new filled array: {}, method: {}", insnNode, methodNode)
                return null
            }
            filledNewArrayNode.addArg(insnNode.getArg(2))
            instructionRemover.add(insnNode)
        }
        return filledNewArrayNode
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        InstructionRemover instructionRemover = InstructionRemover(methodNode)
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            instructionRemover.setBlock(blockNode)
            List instructions = blockNode.getInstructions()
            Int size = instructions.size()
            for (Int i = 0; i < size; i++) {
                InsnNode insnNodeProcess = process(methodNode, instructions, i, instructionRemover)
                if (insnNodeProcess != null) {
                    instructions.set(i, insnNodeProcess)
                }
            }
            instructionRemover.perform()
        }
    }
}
