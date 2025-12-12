package jadx.core.dex.visitors

import jadx.api.IJadxArgs
import jadx.core.Consts
import jadx.core.codegen.TypeGen
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.RootNode
import java.io.File
import java.util.HashSet
import java.util.Iterator
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOCase

class RenameVisitor extends AbstractVisitor {
    private static val CASE_SENSITIVE_FS = IOCase.SYSTEM.isCaseSensitive()
    private Deobfuscator deobfuscator

    private fun checkClassName(ClassNode classNode) {
        ClassInfo classInfo = classNode.getClassInfo()
        String shortName = classInfo.getAlias().getShortName()
        String str = null
        Char cCharAt = shortName.charAt(0)
        if (Character.isDigit(cCharAt)) {
            str = Consts.ANONYMOUS_CLASS_PREFIX + shortName
        } else if (cCharAt == '$') {
            str = "C" + shortName
        }
        if (str != null) {
            classInfo.rename(classNode.dex(), classInfo.makeFullClsName(str, true))
        }
        if (classInfo.getAlias().getPackage().isEmpty()) {
            classInfo.rename(classNode.dex(), "defpackage." + classInfo.makeFullClsName(classInfo.getAlias().getShortName(), true))
        }
    }

    private fun checkClasses(RootNode rootNode) {
        HashSet hashSet = HashSet()
        for (ClassNode classNode : rootNode.getClasses(true)) {
            checkClassName(classNode)
            if (!CASE_SENSITIVE_FS) {
                ClassInfo classInfo = classNode.getClassInfo()
                if (!hashSet.add(classInfo.getAlias().getFullPath().toLowerCase())) {
                    classInfo.rename(classNode.dex(), classInfo.makeFullClsName(this.deobfuscator.getClsAlias(classNode), true))
                    hashSet.add(classInfo.getAlias().getFullPath().toLowerCase())
                }
            }
        }
    }

    private fun checkFields(ClassNode classNode) {
        HashSet hashSet = HashSet()
        for (FieldNode fieldNode : classNode.getFields()) {
            FieldInfo fieldInfo = fieldNode.getFieldInfo()
            if (!hashSet.add(fieldInfo.getAlias())) {
                fieldInfo.setAlias(this.deobfuscator.makeFieldAlias(fieldNode))
            }
        }
    }

    private fun checkMethods(ClassNode classNode) {
        HashSet hashSet = HashSet()
        for (MethodNode methodNode : classNode.getMethods()) {
            if (!methodNode.contains(AFlag.DONT_GENERATE)) {
                MethodInfo methodInfo = methodNode.getMethodInfo()
                if (!hashSet.add(makeMethodSignature(methodInfo))) {
                    methodInfo.setAlias(this.deobfuscator.makeMethodAlias(methodNode))
                }
            }
        }
    }

    private fun makeMethodSignature(MethodInfo methodInfo) {
        StringBuilder sb = StringBuilder()
        sb.append(methodInfo.getAlias())
        sb.append('(')
        Iterator it = methodInfo.getArgumentsTypes().iterator()
        while (it.hasNext()) {
            sb.append(TypeGen.signature((ArgType) it.next()))
        }
        sb.append(')')
        return sb.toString()
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun init(RootNode rootNode) {
        IJadxArgs args = rootNode.getArgs()
        String absolutePath = ((DexNode) rootNode.getDexNodes().get(0)).getInputFile().getFile().getAbsolutePath()
        this.deobfuscator = Deobfuscator(args, rootNode.getDexNodes(), File(FilenameUtils.getFullPathNoEndSeparator(absolutePath), FilenameUtils.getBaseName(absolutePath) + ".jobf"))
        if (args.isDeobfuscationOn()) {
            this.deobfuscator.execute()
        }
        checkClasses(rootNode)
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        checkFields(classNode)
        checkMethods(classNode)
        Iterator it = classNode.getInnerClasses().iterator()
        while (it.hasNext()) {
            visit((ClassNode) it.next())
        }
        return false
    }
}
