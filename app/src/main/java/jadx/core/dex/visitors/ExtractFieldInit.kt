package jadx.core.dex.visitors

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.parser.FieldInitAttr
import jadx.core.utils.BlockUtils
import jadx.core.utils.InstructionRemover
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.Iterator
import java.util.List

@JadxVisitor(desc = "Move duplicated field initialization from constructors", name = "ExtractFieldInit", runAfter = {ModVisitor.class}, runBefore = {ClassModifier.class})
class ExtractFieldInit extends AbstractVisitor {

    class InitInfo {
        private final MethodNode constrMth
        private final List putInsns

        private constructor(MethodNode methodNode) {
            this.putInsns = ArrayList()
            this.constrMth = methodNode
        }

        fun getConstrMth() {
            return this.constrMth
        }

        fun getPutInsns() {
            return this.putInsns
        }
    }

    private fun addFieldInitAttr(MethodNode methodNode, FieldNode fieldNode, InsnNode insnNode) {
        fieldNode.addAttr(FieldInitAttr.insnValue(methodNode, InsnNode.wrapArg(insnNode.getArg(0))))
    }

    private fun checkInsn(InsnNode insnNode) {
        InsnArg arg = insnNode.getArg(0)
        if (!arg.isInsnWrap()) {
            return arg.isLiteral() || arg.isThis()
        }
        if (!((InsnWrapArg) arg).getWrapInsn().canReorderRecursive() && insnNode.contains(AType.CATCH_BLOCK)) {
            return false
        }
        HashSet hashSet = HashSet()
        insnNode.getRegisterArgs(hashSet)
        if (!hashSet.isEmpty()) {
            Iterator it = hashSet.iterator()
            while (it.hasNext()) {
                if (!((RegisterArg) it.next()).isThis()) {
                    return false
                }
            }
        }
        return true
    }

    private fun checkStaticFieldsInit(ClassNode classNode) {
        MethodNode classInitMth = classNode.getClassInitMth()
        if (classInitMth == null || !classInitMth.getAccessFlags().isStatic() || classInitMth.isNoCode()) {
            return
        }
        Iterator it = classInitMth.getBasicBlocks().iterator()
        while (it.hasNext()) {
            for (InsnNode insnNode : ((BlockNode) it.next()).getInstructions()) {
                if (insnNode.getType() == InsnType.SPUT) {
                    processStaticFieldAssign(classNode, (IndexInsnNode) insnNode)
                }
            }
        }
    }

    private fun compareInsns(List list, List list2) {
        if (list.size() != list2.size()) {
            return false
        }
        Int size = list.size()
        for (Int i = 0; i < size; i++) {
            if (!((InsnNode) list.get(i)).isSame((InsnNode) list2.get(i))) {
                return false
            }
        }
        return true
    }

    private fun getConstructorsList(ClassNode classNode) {
        ArrayList arrayList = ArrayList()
        for (MethodNode methodNode : classNode.getMethods()) {
            AccessInfo accessFlags = methodNode.getAccessFlags()
            if (!accessFlags.isStatic() && accessFlags.isConstructor()) {
                arrayList.add(methodNode)
                if (BlockUtils.isAllBlocksEmpty(methodNode.getBasicBlocks())) {
                    return Collections.emptyList()
                }
            }
        }
        return arrayList
    }

    private fun getFieldAssigns(MethodNode methodNode, FieldNode fieldNode, InsnType insnType) {
        if (methodNode.isNoCode()) {
            return Collections.emptyList()
        }
        ArrayList arrayList = ArrayList()
        Iterator it = methodNode.getBasicBlocks().iterator()
        while (it.hasNext()) {
            for (InsnNode insnNode : ((BlockNode) it.next()).getInstructions()) {
                if (insnNode.getType() == insnType && ((FieldInfo) ((IndexInsnNode) insnNode).getIndex()).equals(fieldNode.getFieldInfo())) {
                    arrayList.add(insnNode)
                }
            }
        }
        return arrayList
    }

    private fun moveCommonFieldsInit(ClassNode classNode) {
        List<MethodNode> constructorsList = getConstructorsList(classNode)
        if (constructorsList.isEmpty()) {
            return
        }
        ArrayList<InitInfo> arrayList = ArrayList(constructorsList.size())
        for (MethodNode methodNode : constructorsList) {
            if (methodNode.isNoCode() || methodNode.getBasicBlocks().isEmpty()) {
                return
            }
            InitInfo initInfo = InitInfo(methodNode)
            arrayList.add(initInfo)
            for (InsnNode insnNode : ((BlockNode) methodNode.getBasicBlocks().get(0)).getInstructions()) {
                if (insnNode.getType() == InsnType.IPUT && checkInsn(insnNode)) {
                    initInfo.getPutInsns().add(insnNode)
                } else if (initInfo.getPutInsns().isEmpty()) {
                }
            }
        }
        InitInfo initInfo2 = null
        for (InitInfo initInfo3 : arrayList) {
            if (initInfo2 == null) {
                initInfo2 = initInfo3
            } else if (!compareInsns(initInfo2.getPutInsns(), initInfo3.getPutInsns())) {
                return
            }
        }
        if (initInfo2 != null) {
            HashSet hashSet = HashSet()
            Iterator it = initInfo2.getPutInsns().iterator()
            while (it.hasNext()) {
                FieldInfo fieldInfo = (FieldInfo) ((IndexInsnNode) ((InsnNode) it.next())).getIndex()
                if (classNode.dex().resolveField(fieldInfo) == null || !hashSet.add(fieldInfo)) {
                    return
                }
            }
            for (InitInfo initInfo4 : arrayList) {
                Iterator it2 = initInfo4.getPutInsns().iterator()
                while (it2.hasNext()) {
                    InstructionRemover.remove(initInfo4.getConstrMth(), (InsnNode) it2.next())
                }
            }
            for (InsnNode insnNode2 : initInfo2.getPutInsns()) {
                addFieldInitAttr(initInfo2.getConstrMth(), classNode.dex().resolveField((FieldInfo) ((IndexInsnNode) insnNode2).getIndex()), insnNode2)
            }
        }
    }

    private fun moveStaticFieldsInit(ClassNode classNode) {
        MethodNode classInitMth = classNode.getClassInitMth()
        if (classInitMth == null) {
            return
        }
        for (FieldNode fieldNode : classNode.getFields()) {
            if (!fieldNode.contains(AFlag.DONT_GENERATE) && fieldNode.getAccessFlags().isStatic()) {
                List fieldAssigns = getFieldAssigns(classInitMth, fieldNode, InsnType.SPUT)
                if (fieldAssigns.size() == 1) {
                    InsnNode insnNode = (InsnNode) fieldAssigns.get(0)
                    if (checkInsn(insnNode)) {
                        InstructionRemover.remove(classInitMth, insnNode)
                        addFieldInitAttr(classInitMth, fieldNode, insnNode)
                    }
                }
            }
        }
    }

    private fun processStaticFieldAssign(ClassNode classNode, IndexInsnNode indexInsnNode) {
        FieldNode fieldNodeSearchField
        FieldInfo fieldInfo = (FieldInfo) indexInsnNode.getIndex()
        if (fieldInfo.getDeclClass().getFullName().equals(classNode.getClassInfo().getFullName()) && (fieldNodeSearchField = classNode.searchField(fieldInfo)) != null && fieldNodeSearchField.getAccessFlags().isFinal()) {
            fieldNodeSearchField.remove(AType.FIELD_INIT)
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        if (!classNode.isEnum()) {
            Iterator it = classNode.getInnerClasses().iterator()
            while (it.hasNext()) {
                visit((ClassNode) it.next())
            }
            checkStaticFieldsInit(classNode)
            moveStaticFieldsInit(classNode)
            moveCommonFieldsInit(classNode)
        }
        return false
    }
}
