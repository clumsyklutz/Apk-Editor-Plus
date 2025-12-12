package jadx.core.codegen

import jadx.api.IJadxArgs
import jadx.core.codegen.InsnGen
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.AttrNode
import jadx.core.dex.attributes.nodes.EnumClassAttr
import jadx.core.dex.attributes.nodes.SourceFileAttr
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.parser.FieldInitAttr
import jadx.core.utils.ErrorsCounter
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set

class ClassGen {
    public static val METHOD_LINE_COMPARATOR = Comparator() { // from class: jadx.core.codegen.ClassGen.1
        @Override // java.util.Comparator
        public final Int compare(MethodNode methodNode, MethodNode methodNode2) {
            return Utils.compare(methodNode.getSourceLine(), methodNode2.getSourceLine())
        }
    }
    private final AnnotationGen annotationGen
    private final ClassNode cls
    private Int clsDeclLine
    private final Boolean fallback
    private final Set imports
    private final ClassGen parentGen
    private final Boolean showInconsistentCode

    constructor(ClassNode classNode, IJadxArgs iJadxArgs) {
        this(classNode, null, iJadxArgs.isFallbackMode(), iJadxArgs.isShowInconsistentCode())
    }

    constructor(ClassNode classNode, ClassGen classGen) {
        this(classNode, classGen, classGen.fallback, classGen.showInconsistentCode)
    }

    constructor(ClassNode classNode, ClassGen classGen, Boolean z, Boolean z2) {
        this.imports = HashSet()
        this.cls = classNode
        this.parentGen = classGen
        this.fallback = z
        this.showInconsistentCode = z2
        this.annotationGen = AnnotationGen(classNode, this)
    }

    private fun addEnumFields(CodeWriter codeWriter) {
        EnumClassAttr enumClassAttr = (EnumClassAttr) this.cls.get(AType.ENUM_CLASS)
        if (enumClassAttr == null) {
            return
        }
        InsnGen insnGenMakeInsnGen = null
        Iterator it = enumClassAttr.getFields().iterator()
        while (it.hasNext()) {
            EnumClassAttr.EnumField enumField = (EnumClassAttr.EnumField) it.next()
            codeWriter.startLine(enumField.getField().getAlias())
            ConstructorInsn constrInsn = enumField.getConstrInsn()
            if (constrInsn.getArgsCount() > enumField.getStartArg()) {
                if (insnGenMakeInsnGen == null) {
                    insnGenMakeInsnGen = makeInsnGen(enumClassAttr.getStaticMethod())
                }
                insnGenMakeInsnGen.generateMethodArguments(codeWriter, constrInsn, enumField.getStartArg(), this.cls.dex().resolveMethod(constrInsn.getCallMth()))
            }
            if (enumField.getCls() != null) {
                codeWriter.add(' ')
                ClassGen(enumField.getCls(), this).addClassBody(codeWriter)
            }
            if (it.hasNext()) {
                codeWriter.add(',')
            }
        }
        if (isMethodsPresents() || isFieldsPresents() || isInnerClassesPresents()) {
            if (enumClassAttr.getFields().isEmpty()) {
                codeWriter.startLine()
            }
            codeWriter.add(';')
            if (isFieldsPresents()) {
                codeWriter.startLine()
            }
        }
    }

    private fun addFields(CodeWriter codeWriter) {
        addEnumFields(codeWriter)
        for (FieldNode fieldNode : this.cls.getFields()) {
            if (!fieldNode.contains(AFlag.DONT_GENERATE)) {
                this.annotationGen.addForField(codeWriter, fieldNode)
                codeWriter.startLine(fieldNode.getAccessFlags().makeString())
                useType(codeWriter, fieldNode.getType())
                codeWriter.add(' ')
                codeWriter.attachDefinition(fieldNode)
                codeWriter.add(fieldNode.getAlias())
                FieldInitAttr fieldInitAttr = (FieldInitAttr) fieldNode.get(AType.FIELD_INIT)
                if (fieldInitAttr != null) {
                    codeWriter.add(" = ")
                    if (fieldInitAttr.getValue() == null) {
                        codeWriter.add(TypeGen.literalToString(0L, fieldNode.getType()))
                    } else if (fieldInitAttr.getValueType() == FieldInitAttr.InitType.CONST) {
                        this.annotationGen.encodeValue(codeWriter, fieldInitAttr.getValue())
                    } else if (fieldInitAttr.getValueType() == FieldInitAttr.InitType.INSN) {
                        addInsnBody(makeInsnGen(fieldInitAttr.getInsnMth()), codeWriter, fieldInitAttr.getInsn())
                    }
                }
                codeWriter.add(';')
            }
        }
    }

    private fun addImport(ClassInfo classInfo) {
        if (this.parentGen != null) {
            this.parentGen.addImport(classInfo.getAlias())
        } else {
            this.imports.add(classInfo)
        }
    }

    private fun addInnerClasses(CodeWriter codeWriter, ClassNode classNode) {
        for (ClassNode classNode2 : classNode.getInnerClasses()) {
            if (!classNode2.contains(AFlag.DONT_GENERATE) && !classNode2.contains(AFlag.ANONYMOUS_CLASS)) {
                ClassGen classGen = ClassGen(classNode2, getParentGen())
                codeWriter.newLine()
                classGen.addClassCode(codeWriter)
                this.imports.addAll(classGen.getImports())
            }
        }
    }

    private fun addInsnBody(InsnGen insnGen, CodeWriter codeWriter, InsnNode insnNode) {
        try {
            insnGen.makeInsn(insnNode, codeWriter, InsnGen.Flags.BODY_ONLY_NOWRAP)
        } catch (Exception e) {
            ErrorsCounter.classError(this.cls, "Failed to generate init code", e)
        }
    }

    private fun addMethod(CodeWriter codeWriter, MethodNode methodNode) {
        Object annotationDefaultValue
        if (methodNode.getAccessFlags().isAbstract() || methodNode.getAccessFlags().isNative()) {
            MethodGen(this, methodNode).addDefinition(codeWriter)
            if (this.cls.getAccessFlags().isAnnotation() && (annotationDefaultValue = this.annotationGen.getAnnotationDefaultValue(methodNode.getName())) != null) {
                codeWriter.add(" default ")
                this.annotationGen.encodeValue(codeWriter, annotationDefaultValue)
            }
            codeWriter.add(';')
            return
        }
        Boolean zContains = methodNode.contains(AFlag.INCONSISTENT_CODE)
        if (zContains) {
            codeWriter.startLine("/* JADX WARNING: inconsistent code. */")
            codeWriter.startLine("/* Code decompiled incorrectly, please refer to instructions dump. */")
            ErrorsCounter.methodError(methodNode, "Inconsistent code")
            if (this.showInconsistentCode) {
                methodNode.remove(AFlag.INCONSISTENT_CODE)
                zContains = false
            }
        }
        MethodGen fallbackMethodGen = (zContains || methodNode.contains(AType.JADX_ERROR) || this.fallback) ? MethodGen.getFallbackMethodGen(methodNode) : MethodGen(this, methodNode)
        if (fallbackMethodGen.addDefinition(codeWriter)) {
            codeWriter.add(' ')
        }
        codeWriter.add('{')
        codeWriter.incIndent()
        insertSourceFileInfo(codeWriter, methodNode)
        if (this.fallback) {
            fallbackMethodGen.addFallbackMethodCode(codeWriter)
        } else {
            fallbackMethodGen.addInstructions(codeWriter)
        }
        codeWriter.decIndent()
        codeWriter.startLine('}')
    }

    private fun addMethods(CodeWriter codeWriter) {
        for (MethodNode methodNode : sortMethodsByLine(this.cls.getMethods())) {
            if (!methodNode.contains(AFlag.DONT_GENERATE)) {
                if (codeWriter.getLine() != this.clsDeclLine) {
                    codeWriter.newLine()
                }
                try {
                    addMethod(codeWriter, methodNode)
                } catch (Exception e) {
                    codeWriter.newLine().add("/*")
                    codeWriter.newLine().add(ErrorsCounter.methodError(methodNode, "Method generation error", e))
                    codeWriter.newLine().add(Utils.getStackTrace(e))
                    codeWriter.newLine().add("*/")
                }
            }
        }
    }

    private fun getImports() {
        while (this.parentGen != null) {
            this = this.parentGen
        }
        return this.imports
    }

    private fun insertRenameInfo(CodeWriter codeWriter, ClassNode classNode) {
        ClassInfo classInfo = classNode.getClassInfo()
        if (!classInfo.isRenamed() || classNode.getShortName().equals(classNode.getAlias().getShortName())) {
            return
        }
        codeWriter.startLine("/* renamed from: ").add(classInfo.getFullName()).add(" */")
    }

    private fun insertSourceFileInfo(CodeWriter codeWriter, AttrNode attrNode) {
        SourceFileAttr sourceFileAttr = (SourceFileAttr) attrNode.get(AType.SOURCE_FILE)
        if (sourceFileAttr != null) {
            codeWriter.startLine("/* compiled from: ").add(sourceFileAttr.getFileName()).add(" */")
        }
    }

    private fun isBothClassesInOneTopClass(ClassInfo classInfo, ClassInfo classInfo2) {
        ClassInfo topParentClass = classInfo.getTopParentClass()
        ClassInfo topParentClass2 = classInfo2.getTopParentClass()
        return topParentClass != null ? topParentClass.equals(topParentClass2) : classInfo.equals(topParentClass2)
    }

    private fun isClassInnerFor(ClassInfo classInfo, ClassInfo classInfo2) {
        if (!classInfo.isInner()) {
            return false
        }
        ClassInfo parentClass = classInfo.getParentClass()
        return parentClass.equals(classInfo2) || isClassInnerFor(parentClass, classInfo2)
    }

    private fun isFieldsPresents() {
        Iterator it = this.cls.getFields().iterator()
        while (it.hasNext()) {
            if (!((FieldNode) it.next()).contains(AFlag.DONT_GENERATE)) {
                return true
            }
        }
        return false
    }

    private fun isInnerClassesPresents() {
        Iterator it = this.cls.getInnerClasses().iterator()
        while (it.hasNext()) {
            if (!((ClassNode) it.next()).contains(AFlag.ANONYMOUS_CLASS)) {
                return true
            }
        }
        return false
    }

    private fun isMethodsPresents() {
        Iterator it = this.cls.getMethods().iterator()
        while (it.hasNext()) {
            if (!((MethodNode) it.next()).contains(AFlag.DONT_GENERATE)) {
                return true
            }
        }
        return false
    }

    private fun makeInsnGen(MethodNode methodNode) {
        return InsnGen(MethodGen(this, methodNode), false)
    }

    private fun searchCollision(DexNode dexNode, ClassInfo classInfo, ClassInfo classInfo2) {
        while (classInfo != null) {
            String shortName = classInfo2.getShortName()
            if (classInfo.getShortName().equals(shortName)) {
                return true
            }
            ClassNode classNodeResolveClass = dexNode.resolveClass(classInfo)
            if (classNodeResolveClass != null) {
                for (ClassNode classNode : classNodeResolveClass.getInnerClasses()) {
                    if (classNode.getShortName().equals(shortName) && !classNode.getAlias().equals(classInfo2)) {
                        return true
                    }
                }
            }
            classInfo = classInfo.getParentClass()
        }
        return false
    }

    private fun sortMethodsByLine(List list) {
        ArrayList arrayList = ArrayList(list)
        Collections.sort(arrayList, METHOD_LINE_COMPARATOR)
        return arrayList
    }

    private fun useClassInternal(ClassInfo classInfo, ClassInfo classInfo2) {
        String fullName = classInfo2.getFullName()
        if (this.fallback) {
            return fullName
        }
        String shortName = classInfo2.getShortName()
        if ((!classInfo2.getPackage().equals("java.lang") || classInfo2.getParentClass() != null) && !isClassInnerFor(classInfo, classInfo2) && !isBothClassesInOneTopClass(classInfo, classInfo2)) {
            if (classInfo2.getPackage().equals(classInfo.getPackage()) && !classInfo2.isInner()) {
                return shortName
            }
            ClassNode classNodeResolveClass = this.cls.dex().resolveClass(classInfo2)
            if (classNodeResolveClass != null && !classNodeResolveClass.getAccessFlags().isPublic()) {
                return shortName
            }
            if (searchCollision(this.cls.dex(), classInfo, classInfo2)) {
                return fullName
            }
            String nameWithoutPackage = classInfo2.getPackage().equals(classInfo.getPackage()) ? classInfo2.getNameWithoutPackage() : fullName
            for (ClassInfo classInfo3 : getImports()) {
                if (!classInfo3.equals(classInfo2) && classInfo3.getShortName().equals(shortName)) {
                    if (classInfo2.isInner()) {
                        return useClassInternal(classInfo, classInfo2.getParentClass().getAlias()) + Deobfuscator.CLASS_NAME_SEPARATOR + shortName
                    }
                    return nameWithoutPackage
                }
            }
            addImport(classInfo2)
            return shortName
        }
        return shortName
    }

    fun addClassBody(CodeWriter codeWriter) {
        codeWriter.add('{')
        this.clsDeclLine = codeWriter.getLine()
        codeWriter.incIndent()
        addFields(codeWriter)
        addInnerClasses(codeWriter, this.cls)
        addMethods(codeWriter)
        codeWriter.decIndent()
        codeWriter.startLine('}')
    }

    fun addClassCode(CodeWriter codeWriter) {
        if (this.cls.contains(AFlag.DONT_GENERATE)) {
            return
        }
        if (this.cls.contains(AFlag.INCONSISTENT_CODE)) {
            codeWriter.startLine("// jadx: inconsistent code")
        }
        addClassDeclaration(codeWriter)
        addClassBody(codeWriter)
    }

    fun addClassDeclaration(CodeWriter codeWriter) {
        AccessInfo accessFlags = this.cls.getAccessFlags()
        if (accessFlags.isInterface()) {
            accessFlags = accessFlags.remove(1024).remove(8)
        } else if (accessFlags.isEnum()) {
            accessFlags = accessFlags.remove(16).remove(1024).remove(8)
        }
        if (!this.cls.getAlias().isInner()) {
            accessFlags = accessFlags.remove(8).remove(2)
        }
        this.annotationGen.addForClass(codeWriter)
        insertSourceFileInfo(codeWriter, this.cls)
        insertRenameInfo(codeWriter, this.cls)
        codeWriter.startLine(accessFlags.makeString())
        if (accessFlags.isInterface()) {
            if (accessFlags.isAnnotation()) {
                codeWriter.add('@')
            }
            codeWriter.add("interface ")
        } else if (accessFlags.isEnum()) {
            codeWriter.add("enum ")
        } else {
            codeWriter.add("class ")
        }
        codeWriter.attachDefinition(this.cls)
        codeWriter.add(this.cls.getShortName())
        addGenericMap(codeWriter, this.cls.getGenericMap())
        codeWriter.add(' ')
        ArgType superClass = this.cls.getSuperClass()
        if (superClass != null && !superClass.equals(ArgType.OBJECT) && !superClass.getObject().equals(ArgType.ENUM.getObject())) {
            codeWriter.add("extends ")
            useClass(codeWriter, superClass)
            codeWriter.add(' ')
        }
        if (this.cls.getInterfaces().isEmpty() || accessFlags.isAnnotation()) {
            return
        }
        if (this.cls.getAccessFlags().isInterface()) {
            codeWriter.add("extends ")
        } else {
            codeWriter.add("implements ")
        }
        Iterator it = this.cls.getInterfaces().iterator()
        while (it.hasNext()) {
            useClass(codeWriter, (ArgType) it.next())
            if (it.hasNext()) {
                codeWriter.add(", ")
            }
        }
        if (this.cls.getInterfaces().isEmpty()) {
            return
        }
        codeWriter.add(' ')
    }

    fun addGenericMap(CodeWriter codeWriter, Map map) {
        Int i = 0
        if (map == null || map.isEmpty()) {
            return false
        }
        codeWriter.add('<')
        Iterator it = map.entrySet().iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                codeWriter.add('>')
                return true
            }
            Map.Entry entry = (Map.Entry) it.next()
            ArgType argType = (ArgType) entry.getKey()
            List list = (List) entry.getValue()
            if (i2 != 0) {
                codeWriter.add(", ")
            }
            if (argType.isGenericType()) {
                codeWriter.add(argType.getObject())
            } else {
                useClass(codeWriter, argType)
            }
            if (list != null && !list.isEmpty()) {
                codeWriter.add(" extends ")
                Iterator it2 = list.iterator()
                while (it2.hasNext()) {
                    ArgType argType2 = (ArgType) it2.next()
                    if (argType2.isGenericType()) {
                        codeWriter.add(argType2.getObject())
                    } else {
                        useClass(codeWriter, argType2)
                    }
                    if (it2.hasNext()) {
                        codeWriter.add(" & ")
                    }
                }
            }
            i = i2 + 1
        }
    }

    fun getAnnotationGen() {
        return this.annotationGen
    }

    fun getClassNode() {
        return this.cls
    }

    fun getParentGen() {
        return this.parentGen == null ? this : this.parentGen
    }

    fun isFallbackMode() {
        return this.fallback
    }

    fun makeClass() {
        CodeWriter codeWriter = CodeWriter()
        addClassCode(codeWriter)
        CodeWriter codeWriter2 = CodeWriter()
        if (!"".equals(this.cls.getPackage())) {
            codeWriter2.add("package ").add(this.cls.getPackage()).add(';')
            codeWriter2.newLine()
        }
        Int size = this.imports.size()
        if (size != 0) {
            ArrayList arrayList = ArrayList(size)
            Iterator it = this.imports.iterator()
            while (it.hasNext()) {
                arrayList.add(((ClassInfo) it.next()).getAlias().getFullName())
            }
            Collections.sort(arrayList)
            Iterator it2 = arrayList.iterator()
            while (it2.hasNext()) {
                codeWriter2.startLine("import ").add((String) it2.next()).add(';')
            }
            codeWriter2.newLine()
            arrayList.clear()
            this.imports.clear()
        }
        codeWriter2.add(codeWriter)
        return codeWriter2
    }

    fun useClass(CodeWriter codeWriter, ClassInfo classInfo) {
        ClassNode classNodeResolveClass = this.cls.dex().resolveClass(classInfo)
        if (classNodeResolveClass != null) {
            codeWriter.attachAnnotation(classNodeResolveClass)
        }
        codeWriter.add(useClassInternal(this.cls.getAlias(), classInfo.getAlias()))
    }

    fun useClass(CodeWriter codeWriter, ArgType argType) {
        useClass(codeWriter, ClassInfo.extCls(this.cls.dex(), argType))
        Array<ArgType> genericTypes = argType.getGenericTypes()
        if (genericTypes != null) {
            codeWriter.add('<')
            Int length = genericTypes.length
            for (Int i = 0; i < length; i++) {
                if (i != 0) {
                    codeWriter.add(", ")
                }
                ArgType argType2 = genericTypes[i]
                ArgType wildcardType = argType2.getWildcardType()
                if (wildcardType != null) {
                    codeWriter.add('?')
                    Int wildcardBounds = argType2.getWildcardBounds()
                    if (wildcardBounds != 0) {
                        codeWriter.add(wildcardBounds == -1 ? " super " : " extends ")
                        useType(codeWriter, wildcardType)
                    }
                } else {
                    useType(codeWriter, argType2)
                }
            }
            codeWriter.add('>')
        }
    }

    fun useType(CodeWriter codeWriter, ArgType argType) {
        PrimitiveType primitiveType = argType.getPrimitiveType()
        if (primitiveType == null) {
            codeWriter.add(argType.toString())
            return
        }
        if (primitiveType == PrimitiveType.OBJECT) {
            if (argType.isGenericType()) {
                codeWriter.add(argType.getObject())
                return
            } else {
                useClass(codeWriter, argType)
                return
            }
        }
        if (primitiveType != PrimitiveType.ARRAY) {
            codeWriter.add(primitiveType.getLongName())
        } else {
            useType(codeWriter, argType.getArrayElement())
            codeWriter.add("[]")
        }
    }
}
