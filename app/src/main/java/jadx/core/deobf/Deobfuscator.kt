package jadx.core.deobf

import jadx.api.IJadxArgs
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.SourceFileAttr
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.MethodNode
import java.io.File
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.TreeSet

class Deobfuscator {
    public static val CLASS_NAME_SEPARATOR = "."
    private static val DEBUG = false
    public static val INNER_CLASS_SEPARATOR = "$"
    private final IJadxArgs args
    private final DeobfPresets deobfPresets
    private final List dexNodes
    private final Int maxLength
    private final Int minLength
    private final Boolean useSourceNameAsAlias
    private val clsMap = HashMap()
    private val fldMap = HashMap()
    private val mthMap = HashMap()
    private val ovrdMap = HashMap()
    private val ovrd = ArrayList()
    private val rootPackage = PackageNode("")
    private val pkgSet = TreeSet()
    private Int pkgIndex = 0
    private Int clsIndex = 0
    private Int fldIndex = 0
    private Int mthIndex = 0

    constructor(IJadxArgs iJadxArgs, List list, File file) {
        this.args = iJadxArgs
        this.dexNodes = list
        this.minLength = iJadxArgs.getDeobfuscationMinLength()
        this.maxLength = iJadxArgs.getDeobfuscationMaxLength()
        this.useSourceNameAsAlias = iJadxArgs.useSourceNameAsClassAlias()
        this.deobfPresets = DeobfPresets(this, file)
    }

    private fun doClass(ClassNode classNode) {
        ClassInfo classInfo = classNode.getClassInfo()
        String str = classInfo.getPackage()
        PackageNode packageNode = getPackageNode(str, true)
        doPkg(packageNode, str)
        String forCls = this.deobfPresets.getForCls(classInfo)
        if (forCls != null) {
            this.clsMap.put(classInfo, DeobfClsInfo(this, classNode, packageNode, forCls))
        } else {
            if (this.clsMap.containsKey(classInfo) || !shouldRename(classInfo.getShortName())) {
                return
            }
            makeClsAlias(classNode)
        }
    }

    private fun doPkg(PackageNode packageNode, String str) {
        if (this.pkgSet.contains(str)) {
            return
        }
        this.pkgSet.add(str)
        for (PackageNode parentPackage = packageNode.getParentPackage(); !parentPackage.getName().isEmpty(); parentPackage = parentPackage.getParentPackage()) {
            if (!parentPackage.hasAlias()) {
                doPkg(parentPackage, parentPackage.getFullName())
            }
        }
        String name = packageNode.getName()
        if (packageNode.hasAlias() || !shouldRename(name)) {
            return
        }
        Int i = this.pkgIndex
        this.pkgIndex = i + 1
        packageNode.setAlias(String.format("p%03d%s", Integer.valueOf(i), makeName(name)))
    }

    private fun dumpAlias() {
        Iterator it = this.dexNodes.iterator()
        while (it.hasNext()) {
            Iterator it2 = ((DexNode) it.next()).getClasses().iterator()
            while (it2.hasNext()) {
                dumpClassAlias((ClassNode) it2.next())
            }
        }
    }

    private fun dumpClassAlias(ClassNode classNode) {
        if (getPackageNode(classNode.getPackage(), false) != null) {
            classNode.getFullName().equals(getClassFullName(classNode))
        }
    }

    private fun getAliasFromSourceFile(ClassNode classNode) {
        SourceFileAttr sourceFileAttr = (SourceFileAttr) classNode.get(AType.SOURCE_FILE)
        if (sourceFileAttr == null) {
            return null
        }
        String fileName = sourceFileAttr.getFileName()
        if (fileName.endsWith(".java")) {
            fileName = fileName.substring(0, fileName.length() - 5)
        }
        if (!NameMapper.isValidIdentifier(fileName) || NameMapper.isReserved(fileName)) {
            return null
        }
        classNode.remove(AType.SOURCE_FILE)
        return fileName
    }

    private fun getClassFullName(ClassInfo classInfo) {
        DeobfClsInfo deobfClsInfo = (DeobfClsInfo) this.clsMap.get(classInfo)
        return deobfClsInfo != null ? deobfClsInfo.getFullName() : getPackageName(classInfo.getPackage()) + CLASS_NAME_SEPARATOR + getClassName(classInfo)
    }

    private fun getClassFullName(ClassNode classNode) {
        return getClassFullName(classNode.getClassInfo())
    }

    private fun getClassName(ClassInfo classInfo) {
        DeobfClsInfo deobfClsInfo = (DeobfClsInfo) this.clsMap.get(classInfo)
        return deobfClsInfo != null ? deobfClsInfo.makeNameWithoutPkg() : getNameWithoutPackage(classInfo)
    }

    private fun getPackageName(String str) {
        PackageNode packageNode = getPackageNode(str, false)
        return packageNode != null ? packageNode.getFullAlias() : str
    }

    private fun getPackageNode(String str, Boolean z) {
        String strSubstring
        if (str.isEmpty() || str.equals(CLASS_NAME_SEPARATOR)) {
            return this.rootPackage
        }
        PackageNode packageNode = this.rootPackage
        do {
            Int iIndexOf = str.indexOf(CLASS_NAME_SEPARATOR)
            if (iIndexOf >= 0) {
                strSubstring = str.substring(0, iIndexOf)
                str = str.substring(iIndexOf + 1)
            } else {
                String str2 = str
                str = ""
                strSubstring = str2
            }
            PackageNode innerPackageByName = packageNode.getInnerPackageByName(strSubstring)
            if (innerPackageByName == null && z) {
                PackageNode packageNode2 = PackageNode(strSubstring)
                packageNode.addInnerPackage(packageNode2)
                packageNode = packageNode2
            } else {
                packageNode = innerPackageByName
            }
            if (str.isEmpty()) {
                return packageNode
            }
        } while (packageNode != null);
        return packageNode
    }

    private fun initIndexes() {
        this.pkgIndex = this.pkgSet.size()
        this.clsIndex = this.deobfPresets.getClsPresetMap().size()
        this.fldIndex = this.deobfPresets.getFldPresetMap().size()
        this.mthIndex = this.deobfPresets.getMthPresetMap().size()
    }

    private fun makeClsAlias(ClassNode classNode) {
        ClassInfo classInfo = classNode.getClassInfo()
        String aliasFromSourceFile = this.useSourceNameAsAlias ? getAliasFromSourceFile(classNode) : null
        if (aliasFromSourceFile == null) {
            String shortName = classInfo.getShortName()
            Int i = this.clsIndex
            this.clsIndex = i + 1
            aliasFromSourceFile = String.format("C%04d%s", Integer.valueOf(i), makeName(shortName))
        }
        this.clsMap.put(classInfo, DeobfClsInfo(this, classNode, getPackageNode(classInfo.getPackage(), true), aliasFromSourceFile))
        return aliasFromSourceFile
    }

    private fun makeName(String str) {
        return str.length() > this.maxLength ? "x" + Integer.toHexString(str.hashCode()) : (NameMapper.isReserved(str) || NameMapper.isAllCharsPrintable(str)) ? str : removeInvalidChars(str)
    }

    private fun postProcess() {
        Iterator it = this.ovrd.iterator()
        Int i = 1
        while (it.hasNext()) {
            Iterator it2 = ((OverridedMethodsNode) it.next()).getMethods().iterator()
            if (it2.hasNext()) {
                MethodInfo methodInfo = (MethodInfo) it2.next()
                if (methodInfo.isRenamed() && !methodInfo.isAliasFromPreset()) {
                    methodInfo.setAlias(String.format("mo%d%s", Integer.valueOf(i), makeName(methodInfo.getName())))
                }
                String alias = methodInfo.getAlias()
                while (it2.hasNext()) {
                    MethodInfo methodInfo2 = (MethodInfo) it2.next()
                    if (!methodInfo2.getAlias().equals(alias)) {
                        methodInfo2.setAlias(alias)
                    }
                }
            }
            i++
        }
    }

    private fun preProcess() {
        Iterator it = this.dexNodes.iterator()
        while (it.hasNext()) {
            Iterator it2 = ((DexNode) it.next()).getClasses().iterator()
            while (it2.hasNext()) {
                doClass((ClassNode) it2.next())
            }
        }
    }

    private fun process() {
        preProcess()
        for (DexNode dexNode : this.dexNodes) {
            Iterator it = dexNode.getClasses().iterator()
            while (it.hasNext()) {
                processClass(dexNode, (ClassNode) it.next())
            }
        }
        postProcess()
    }

    private fun processClass(DexNode dexNode, ClassNode classNode) {
        ClassInfo classInfo = classNode.getClassInfo()
        String classFullName = getClassFullName(classInfo)
        if (!classFullName.equals(classInfo.getFullName())) {
            classInfo.rename(dexNode, classFullName)
        }
        for (FieldNode fieldNode : classNode.getFields()) {
            FieldInfo fieldInfo = fieldNode.getFieldInfo()
            String fieldAlias = getFieldAlias(fieldNode)
            if (fieldAlias != null) {
                fieldInfo.setAlias(fieldAlias)
            }
        }
        for (MethodNode methodNode : classNode.getMethods()) {
            MethodInfo methodInfo = methodNode.getMethodInfo()
            String methodAlias = getMethodAlias(methodNode)
            if (methodAlias != null) {
                methodInfo.setAlias(methodAlias)
            }
            if (methodNode.isVirtual()) {
                resolveOverriding(dexNode, classNode, methodNode)
            }
        }
    }

    private fun removeInvalidChars(String str) {
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < str.length(); i++) {
            Char cCharAt = str.charAt(i)
            if (NameMapper.isPrintableChar(cCharAt)) {
                sb.append(cCharAt)
            }
        }
        return sb.toString()
    }

    private fun resolveOverriding(DexNode dexNode, ClassNode classNode, MethodNode methodNode) {
        OverridedMethodsNode overridedMethodsNode
        OverridedMethodsNode overridedMethodsNode2
        HashSet<MethodInfo> hashSet = HashSet()
        resolveOverridingInternal(dexNode, classNode, methodNode.getMethodInfo().makeSignature(false), hashSet, classNode)
        if (hashSet.size() <= 1) {
            hashSet.clear()
            return
        }
        Iterator it = hashSet.iterator()
        while (true) {
            if (!it.hasNext()) {
                overridedMethodsNode = null
                break
            }
            MethodInfo methodInfo = (MethodInfo) it.next()
            if (this.ovrdMap.containsKey(methodInfo)) {
                overridedMethodsNode = (OverridedMethodsNode) this.ovrdMap.get(methodInfo)
                break
            }
        }
        if (overridedMethodsNode == null) {
            OverridedMethodsNode overridedMethodsNode3 = OverridedMethodsNode(hashSet)
            this.ovrd.add(overridedMethodsNode3)
            overridedMethodsNode2 = overridedMethodsNode3
        } else {
            overridedMethodsNode2 = overridedMethodsNode
        }
        for (MethodInfo methodInfo2 : hashSet) {
            if (!this.ovrdMap.containsKey(methodInfo2)) {
                this.ovrdMap.put(methodInfo2, overridedMethodsNode2)
                if (!overridedMethodsNode2.contains(methodInfo2)) {
                    overridedMethodsNode2.add(methodInfo2)
                }
            }
        }
    }

    private fun resolveOverridingInternal(DexNode dexNode, ClassNode classNode, String str, Set set, ClassNode classNode2) {
        ClassNode classNode3
        ClassNode classNodeResolveOverridingInternal
        ClassNode classNodeResolveClass
        ClassNode classNodeResolveOverridingInternal2
        Iterator it = classNode.getMethods().iterator()
        while (true) {
            if (!it.hasNext()) {
                classNode3 = null
                break
            }
            MethodNode methodNode = (MethodNode) it.next()
            if (methodNode.getMethodInfo().getShortId().startsWith(str)) {
                if (set.contains(methodNode.getMethodInfo())) {
                    classNode3 = classNode
                } else {
                    set.add(methodNode.getMethodInfo())
                    classNode3 = classNode
                }
            }
        }
        ArgType superClass = classNode.getSuperClass()
        if (superClass != null && (classNodeResolveClass = dexNode.resolveClass(superClass)) != null && (classNodeResolveOverridingInternal2 = resolveOverridingInternal(dexNode, classNodeResolveClass, str, set, classNode2)) != null) {
            classNode3 = classNodeResolveOverridingInternal2
        }
        Iterator it2 = classNode.getInterfaces().iterator()
        ClassNode classNode4 = classNode3
        while (it2.hasNext()) {
            ClassNode classNodeResolveClass2 = dexNode.resolveClass((ArgType) it2.next())
            if (classNodeResolveClass2 != null && (classNodeResolveOverridingInternal = resolveOverridingInternal(dexNode, classNodeResolveClass2, str, set, classNode2)) != null) {
                if (classNode4 == null || classNode4 == classNode) {
                    classNode4 = classNodeResolveOverridingInternal
                } else if (classNodeResolveOverridingInternal == classNode4) {
                }
            }
            classNodeResolveOverridingInternal = classNode4
            classNode4 = classNodeResolveOverridingInternal
        }
        return classNode4
    }

    private fun shouldRename(String str) {
        return str.length() > this.maxLength || str.length() < this.minLength || NameMapper.isReserved(str) || !NameMapper.isAllCharsPrintable(str)
    }

    fun addPackagePreset(String str, String str2) {
        getPackageNode(str, true).setAlias(str2)
    }

    Unit clear() {
        this.deobfPresets.clear()
        this.clsMap.clear()
        this.fldMap.clear()
        this.mthMap.clear()
        this.ovrd.clear()
        this.ovrdMap.clear()
    }

    fun execute() {
        if (!this.args.isDeobfuscationForceSave()) {
            this.deobfPresets.load()
            initIndexes()
        }
        process()
        this.deobfPresets.save(this.args.isDeobfuscationForceSave())
        clear()
    }

    fun getClsAlias(ClassNode classNode) {
        DeobfClsInfo deobfClsInfo = (DeobfClsInfo) this.clsMap.get(classNode.getClassInfo())
        return deobfClsInfo != null ? deobfClsInfo.getAlias() : makeClsAlias(classNode)
    }

    fun getClsMap() {
        return this.clsMap
    }

    fun getFieldAlias(FieldNode fieldNode) {
        FieldInfo fieldInfo = fieldNode.getFieldInfo()
        String str = (String) this.fldMap.get(fieldInfo)
        if (str != null) {
            return str
        }
        String forFld = this.deobfPresets.getForFld(fieldInfo)
        if (forFld != null) {
            this.fldMap.put(fieldInfo, forFld)
            return forFld
        }
        if (shouldRename(fieldNode.getName())) {
            return makeFieldAlias(fieldNode)
        }
        return null
    }

    fun getFldMap() {
        return this.fldMap
    }

    fun getMethodAlias(MethodNode methodNode) {
        MethodInfo methodInfo = methodNode.getMethodInfo()
        String str = (String) this.mthMap.get(methodInfo)
        if (str != null) {
            return str
        }
        String forMth = this.deobfPresets.getForMth(methodInfo)
        if (forMth != null) {
            this.mthMap.put(methodInfo, forMth)
            methodInfo.setAliasFromPreset(true)
            return forMth
        }
        if (shouldRename(methodNode.getName())) {
            return makeMethodAlias(methodNode)
        }
        return null
    }

    fun getMthMap() {
        return this.mthMap
    }

    String getNameWithoutPackage(ClassInfo classInfo) {
        String str
        ClassInfo parentClass = classInfo.getParentClass()
        if (parentClass != null) {
            DeobfClsInfo deobfClsInfo = (DeobfClsInfo) this.clsMap.get(parentClass)
            str = (deobfClsInfo != null ? deobfClsInfo.makeNameWithoutPkg() : getNameWithoutPackage(parentClass)) + INNER_CLASS_SEPARATOR
        } else {
            str = ""
        }
        return str + classInfo.getShortName()
    }

    fun getRootPackage() {
        return this.rootPackage
    }

    fun makeFieldAlias(FieldNode fieldNode) {
        Int i = this.fldIndex
        this.fldIndex = i + 1
        String str = String.format("f%d%s", Integer.valueOf(i), makeName(fieldNode.getName()))
        this.fldMap.put(fieldNode.getFieldInfo(), str)
        return str
    }

    fun makeMethodAlias(MethodNode methodNode) {
        Int i = this.mthIndex
        this.mthIndex = i + 1
        String str = String.format("m%d%s", Integer.valueOf(i), makeName(methodNode.getName()))
        this.mthMap.put(methodNode.getMethodInfo(), str)
        return str
    }
}
