package jadx.core.dex.nodes

import androidx.fragment.app.FragmentTransaction
import com.a.a.b
import com.a.a.c
import com.a.a.d
import com.a.a.e
import jadx.core.Consts
import jadx.core.codegen.CodeWriter
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.annotations.Annotation
import jadx.core.dex.attributes.nodes.JadxErrorAttr
import jadx.core.dex.attributes.nodes.LineAttrNode
import jadx.core.dex.attributes.nodes.SourceFileAttr
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.dex.nodes.parser.AnnotationsParser
import jadx.core.dex.nodes.parser.FieldInitAttr
import jadx.core.dex.nodes.parser.SignatureParser
import jadx.core.dex.nodes.parser.StaticValuesParser
import jadx.core.utils.exceptions.DecodeException
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import java.util.Set

class ClassNode extends LineAttrNode implements ILoadable {
    private final AccessInfo accessFlags
    private final ClassInfo clsInfo
    private CodeWriter code
    private Map constFields
    private final Set dependencies
    private final DexNode dex
    private final List fields
    private Map genericMap
    private List innerClasses
    private List interfaces
    private final List methods
    private Map mthInfoMap
    private ClassNode parentClass
    private ProcessState state
    private ArgType superClass

    constructor(DexNode dexNode, e eVar) throws DecodeException {
        this.constFields = Collections.emptyMap()
        this.innerClasses = Collections.emptyList()
        this.state = ProcessState.NOT_LOADED
        this.dependencies = HashSet()
        this.mthInfoMap = Collections.emptyMap()
        this.dex = dexNode
        this.clsInfo = ClassInfo.fromDex(dexNode, eVar.b())
        try {
            if (eVar.c() == -1) {
                this.superClass = null
            } else {
                this.superClass = dexNode.getType(eVar.c())
            }
            this.interfaces = ArrayList(eVar.e().length)
            for (Short s : eVar.e()) {
                this.interfaces.add(dexNode.getType(s))
            }
            if (eVar.i() != 0) {
                b classData = dexNode.readClassData(eVar)
                Int length = classData.c().length + classData.d().length
                Int length2 = classData.a().length + classData.b().length
                this.methods = ArrayList(length)
                this.fields = ArrayList(length2)
                for (d dVar : classData.c()) {
                    this.methods.add(MethodNode(this, dVar, false))
                }
                for (d dVar2 : classData.d()) {
                    this.methods.add(MethodNode(this, dVar2, true))
                }
                for (c cVar : classData.a()) {
                    this.fields.add(FieldNode(this, cVar))
                }
                loadStaticValues(eVar, this.fields)
                for (c cVar2 : classData.b()) {
                    this.fields.add(FieldNode(this, cVar2))
                }
            } else {
                this.methods = Collections.emptyList()
                this.fields = Collections.emptyList()
            }
            loadAnnotations(eVar)
            parseClassSignature()
            setFieldsTypesFromSignature()
            Int iG = eVar.g()
            if (iG != -1) {
                addSourceFilenameAttr(dexNode.getString(iG))
            }
            Annotation annotation = getAnnotation(Consts.DALVIK_INNER_CLASS)
            this.accessFlags = AccessInfo(annotation != null ? ((Integer) annotation.getValues().get("accessFlags")).intValue() : eVar.f(), AccessInfo.AFType.CLASS)
            buildCache()
        } catch (Exception e) {
            throw DecodeException("Error decode class: " + this.clsInfo, e)
        }
    }

    constructor(DexNode dexNode, ClassInfo classInfo) {
        this.constFields = Collections.emptyMap()
        this.innerClasses = Collections.emptyList()
        this.state = ProcessState.NOT_LOADED
        this.dependencies = HashSet()
        this.mthInfoMap = Collections.emptyMap()
        this.dex = dexNode
        this.clsInfo = classInfo
        this.interfaces = Collections.emptyList()
        this.methods = Collections.emptyList()
        this.fields = Collections.emptyList()
        this.accessFlags = AccessInfo(FragmentTransaction.TRANSIT_FRAGMENT_OPEN, AccessInfo.AFType.CLASS)
        this.parentClass = this
    }

    private fun addSourceFilenameAttr(String str) {
        if (str == null) {
            return
        }
        if (str.endsWith(".java")) {
            str = str.substring(0, str.length() - 5)
        }
        if (str.isEmpty() || str.equals("SourceFile") || str.equals("\"")) {
            return
        }
        if (this.clsInfo != null) {
            String shortName = this.clsInfo.getShortName()
            if (str.equals(shortName)) {
                return
            }
            if (str.contains(Deobfuscator.INNER_CLASS_SEPARATOR) && str.endsWith(Deobfuscator.INNER_CLASS_SEPARATOR + shortName)) {
                return
            }
            ClassInfo topParentClass = this.clsInfo.getTopParentClass()
            if (topParentClass != null && str.equals(topParentClass.getShortName())) {
                return
            }
        }
        addAttr(SourceFileAttr(str))
    }

    private fun buildCache() {
        this.mthInfoMap = HashMap(this.methods.size())
        for (MethodNode methodNode : this.methods) {
            this.mthInfoMap.put(methodNode.getMethodInfo(), methodNode)
        }
    }

    private fun loadAnnotations(e eVar) {
        Int iH = eVar.h()
        if (iH != 0) {
            try {
                AnnotationsParser(this).parse(iH)
            } catch (Exception e) {
            }
        }
    }

    private fun loadStaticValues(e eVar, List list) {
        Int iProcessFields
        FieldInitAttr fieldInitAttr
        Iterator it = list.iterator()
        while (it.hasNext()) {
            FieldNode fieldNode = (FieldNode) it.next()
            if (fieldNode.getAccessFlags().isFinal()) {
                fieldNode.addAttr(FieldInitAttr.NULL_VALUE)
            }
        }
        Int iJ = eVar.j()
        if (iJ == 0 || (iProcessFields = StaticValuesParser(this.dex, this.dex.openSection(iJ)).processFields(list)) == 0) {
            return
        }
        this.constFields = LinkedHashMap(iProcessFields)
        Iterator it2 = list.iterator()
        while (it2.hasNext()) {
            FieldNode fieldNode2 = (FieldNode) it2.next()
            AccessInfo accessFlags = fieldNode2.getAccessFlags()
            if (accessFlags.isStatic() && accessFlags.isFinal() && (fieldInitAttr = (FieldInitAttr) fieldNode2.get(AType.FIELD_INIT)) != null && fieldInitAttr.getValue() != null && fieldInitAttr.getValueType() == FieldInitAttr.InitType.CONST) {
                if (accessFlags.isPublic()) {
                    this.dex.getConstFields().put(fieldInitAttr.getValue(), fieldNode2)
                }
                this.constFields.put(fieldInitAttr.getValue(), fieldNode2)
            }
        }
    }

    private fun parseClassSignature() {
        ArgType argTypeConsumeType
        SignatureParser signatureParserFromNode = SignatureParser.fromNode(this)
        if (signatureParserFromNode == null) {
            return
        }
        try {
            this.genericMap = signatureParserFromNode.consumeGenericMap()
            this.superClass = signatureParserFromNode.consumeType()
            for (Int i = 0; i < this.interfaces.size() && (argTypeConsumeType = signatureParserFromNode.consumeType()) != null; i++) {
                this.interfaces.set(i, argTypeConsumeType)
            }
        } catch (JadxRuntimeException e) {
        }
    }

    private fun setFieldsTypesFromSignature() {
        for (FieldNode fieldNode : this.fields) {
            SignatureParser signatureParserFromNode = SignatureParser.fromNode(fieldNode)
            if (signatureParserFromNode != null) {
                try {
                    ArgType argTypeConsumeType = signatureParserFromNode.consumeType()
                    if (argTypeConsumeType != null) {
                        fieldNode.setType(argTypeConsumeType)
                    }
                } catch (JadxRuntimeException e) {
                }
            }
        }
    }

    fun addInnerClass(ClassNode classNode) {
        if (this.innerClasses.isEmpty()) {
            this.innerClasses = ArrayList(3)
        }
        this.innerClasses.add(classNode)
    }

    fun dex() {
        return this.dex
    }

    fun getAccessFlags() {
        return this.accessFlags
    }

    fun getAlias() {
        return this.clsInfo.getAlias()
    }

    fun getClassInfo() {
        return this.clsInfo
    }

    fun getClassInitMth() {
        return searchMethodByName("<clinit>()V")
    }

    fun getCode() {
        return this.code
    }

    fun getConstField(Object obj) {
        return getConstField(obj, true)
    }

    fun getConstField(Object obj, Boolean z) {
        FieldNode fieldNode
        String str
        ClassNode classNodeResolveClass = this
        do {
            fieldNode = (FieldNode) classNodeResolveClass.constFields.get(obj)
            if (fieldNode != null || classNodeResolveClass.clsInfo.getParentClass() == null) {
                break
            }
            classNodeResolveClass = this.dex.resolveClass(classNodeResolveClass.clsInfo.getParentClass())
        } while (classNodeResolveClass != null);
        FieldNode fieldNode2 = (fieldNode == null && z) ? (FieldNode) this.dex.getConstFields().get(obj) : fieldNode
        if ((obj is Integer) && (str = (String) this.dex.root().getResourcesNames().get(obj)) != null) {
            ResRefField resRefField = ResRefField(this.dex, str.replace('/', '.'))
            if (fieldNode2 == null) {
                return resRefField
            }
            if (!fieldNode2.getName().equals(resRefField.getName())) {
                fieldNode2 = resRefField
            }
        }
        return fieldNode2
    }

    fun getConstFieldByLiteralArg(LiteralArg literalArg) {
        PrimitiveType primitiveType = literalArg.getType().getPrimitiveType()
        if (primitiveType == null) {
            return null
        }
        Long literal = literalArg.getLiteral()
        switch (primitiveType) {
            case BOOLEAN:
                break
            case CHAR:
                break
            case BYTE:
                break
            case SHORT:
                break
            case INT:
                break
            case LONG:
                break
            case FLOAT:
                Float fIntBitsToFloat = Float.intBitsToFloat((Int) literal)
                break
            case DOUBLE:
                Double dLongBitsToDouble = Double.longBitsToDouble(literal)
                break
        }
        return null
    }

    fun getDefaultConstructor() {
        for (MethodNode methodNode : this.methods) {
            if (methodNode.isDefaultConstructor()) {
                return methodNode
            }
        }
        return null
    }

    fun getDependencies() {
        return this.dependencies
    }

    fun getFields() {
        return this.fields
    }

    fun getFullName() {
        return this.clsInfo.getAlias().getFullName()
    }

    fun getGenericMap() {
        return this.genericMap
    }

    fun getInnerClasses() {
        return this.innerClasses
    }

    fun getInterfaces() {
        return this.interfaces
    }

    fun getMethods() {
        return this.methods
    }

    fun getPackage() {
        return this.clsInfo.getAlias().getPackage()
    }

    fun getParentClass() {
        if (this.parentClass == null) {
            if (this.clsInfo.isInner()) {
                ClassNode classNodeResolveClass = dex().resolveClass(this.clsInfo.getParentClass())
                if (classNodeResolveClass == null) {
                    classNodeResolveClass = this
                }
                this.parentClass = classNodeResolveClass
            } else {
                this.parentClass = this
            }
        }
        return this.parentClass
    }

    fun getRawName() {
        return this.clsInfo.getRawName()
    }

    fun getShortName() {
        return this.clsInfo.getAlias().getShortName()
    }

    fun getState() {
        return this.state
    }

    fun getSuperClass() {
        return this.superClass
    }

    fun getTopParentClass() {
        ClassNode parentClass = getParentClass()
        return parentClass == this ? this : parentClass.getParentClass()
    }

    fun isAnonymous() {
        return this.clsInfo.isInner() && this.clsInfo.getAlias().getShortName().startsWith(Consts.ANONYMOUS_CLASS_PREFIX) && getDefaultConstructor() != null
    }

    fun isEnum() {
        return getAccessFlags().isEnum() && getSuperClass() != null && getSuperClass().getObject().equals(ArgType.ENUM.getObject())
    }

    @Override // jadx.core.dex.nodes.ILoadable
    fun load() {
        for (MethodNode methodNode : getMethods()) {
            try {
                methodNode.load()
            } catch (Exception e) {
                methodNode.addAttr(JadxErrorAttr(e))
            }
        }
        Iterator it = getInnerClasses().iterator()
        while (it.hasNext()) {
            ((ClassNode) it.next()).load()
        }
    }

    fun searchField(FieldInfo fieldInfo) {
        for (FieldNode fieldNode : this.fields) {
            if (fieldNode.getFieldInfo().equals(fieldInfo)) {
                return fieldNode
            }
        }
        return null
    }

    fun searchFieldById(Int i) {
        return searchField(FieldInfo.fromDex(this.dex, i))
    }

    fun searchFieldByName(String str) {
        for (FieldNode fieldNode : this.fields) {
            if (fieldNode.getName().equals(str)) {
                return fieldNode
            }
        }
        return null
    }

    fun searchMethod(MethodInfo methodInfo) {
        return (MethodNode) this.mthInfoMap.get(methodInfo)
    }

    fun searchMethodById(Int i) {
        return searchMethodByName(MethodInfo.fromDex(this.dex, i).getShortId())
    }

    fun searchMethodByName(String str) {
        for (MethodNode methodNode : this.methods) {
            if (methodNode.getMethodInfo().getShortId().equals(str)) {
                return methodNode
            }
        }
        return null
    }

    fun setCode(CodeWriter codeWriter) {
        this.code = codeWriter
    }

    fun setState(ProcessState processState) {
        this.state = processState
    }

    fun toString() {
        return this.clsInfo.getFullName()
    }

    @Override // jadx.core.dex.nodes.ILoadable
    fun unload() {
        Iterator it = getMethods().iterator()
        while (it.hasNext()) {
            ((MethodNode) it.next()).unload()
        }
        Iterator it2 = getInnerClasses().iterator()
        while (it2.hasNext()) {
            ((ClassNode) it2.next()).unload()
        }
    }
}
