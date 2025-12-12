package jadx.api

import jadx.core.codegen.CodeWriter
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.nodes.LineAttrNode
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.MethodNode
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List
import java.util.Map

class JavaClass implements JavaNode {
    private final ClassNode cls
    private final JadxDecompiler decompiler
    private List fields
    private List innerClasses
    private List methods
    private final JavaClass parent

    JavaClass(ClassNode classNode, JadxDecompiler jadxDecompiler) {
        this.innerClasses = Collections.emptyList()
        this.fields = Collections.emptyList()
        this.methods = Collections.emptyList()
        this.decompiler = jadxDecompiler
        this.cls = classNode
        this.parent = null
    }

    JavaClass(ClassNode classNode, JavaClass javaClass) {
        this.innerClasses = Collections.emptyList()
        this.fields = Collections.emptyList()
        this.methods = Collections.emptyList()
        this.decompiler = null
        this.cls = classNode
        this.parent = javaClass
    }

    private fun convertNode(Object obj) {
        if (!(obj is LineAttrNode)) {
            return null
        }
        if (obj is ClassNode) {
            return (JavaNode) getRootDecompiler().getClassesMap().get(obj)
        }
        if (obj is MethodNode) {
            return (JavaNode) getRootDecompiler().getMethodsMap().get(obj)
        }
        if (obj is FieldNode) {
            return (JavaNode) getRootDecompiler().getFieldsMap().get(obj)
        }
        return null
    }

    private fun getCodeAnnotations() {
        decompile()
        return this.cls.getCode().getAnnotations()
    }

    private fun getRootDecompiler() {
        while (this.parent != null) {
            this = this.parent
        }
        return this.decompiler
    }

    private fun load() {
        JadxDecompiler rootDecompiler = getRootDecompiler()
        Int size = this.cls.getInnerClasses().size()
        if (size != 0) {
            ArrayList arrayList = ArrayList(size)
            for (ClassNode classNode : this.cls.getInnerClasses()) {
                if (!classNode.contains(AFlag.DONT_GENERATE)) {
                    JavaClass javaClass = JavaClass(classNode, this)
                    javaClass.load()
                    arrayList.add(javaClass)
                    rootDecompiler.getClassesMap().put(classNode, javaClass)
                }
            }
            this.innerClasses = Collections.unmodifiableList(arrayList)
        }
        Int size2 = this.cls.getFields().size()
        if (size2 != 0) {
            ArrayList arrayList2 = ArrayList(size2)
            for (FieldNode fieldNode : this.cls.getFields()) {
                if (!fieldNode.contains(AFlag.DONT_GENERATE)) {
                    JavaField javaField = JavaField(fieldNode, this)
                    arrayList2.add(javaField)
                    rootDecompiler.getFieldsMap().put(fieldNode, javaField)
                }
            }
            this.fields = Collections.unmodifiableList(arrayList2)
        }
        Int size3 = this.cls.getMethods().size()
        if (size3 != 0) {
            ArrayList arrayList3 = ArrayList(size3)
            for (MethodNode methodNode : this.cls.getMethods()) {
                if (!methodNode.contains(AFlag.DONT_GENERATE)) {
                    JavaMethod javaMethod = JavaMethod(this, methodNode)
                    arrayList3.add(javaMethod)
                    rootDecompiler.getMethodsMap().put(methodNode, javaMethod)
                }
            }
            Collections.sort(arrayList3, Comparator() { // from class: jadx.api.JavaClass.1
                @Override // java.util.Comparator
                fun compare(JavaMethod javaMethod2, JavaMethod javaMethod3) {
                    return javaMethod2.getName().compareTo(javaMethod3.getName())
                }
            })
            this.methods = Collections.unmodifiableList(arrayList3)
        }
    }

    public final synchronized Unit decompile() {
        if (this.decompiler != null && this.cls.getCode() == null) {
            this.decompiler.processClass(this.cls)
            load()
        }
    }

    public final Boolean equals(Object obj) {
        return this == obj || ((obj is JavaClass) && this.cls.equals(((JavaClass) obj).cls))
    }

    public final AccessInfo getAccessInfo() {
        return this.cls.getAccessFlags()
    }

    final ClassNode getClassNode() {
        return this.cls
    }

    public final String getCode() {
        CodeWriter code = this.cls.getCode()
        if (code == null) {
            decompile()
            code = this.cls.getCode()
            if (code == null) {
                return ""
            }
        }
        return code.getCodeStr()
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getDeclaringClass() {
        return this.parent
    }

    @Override // jadx.api.JavaNode
    public final Int getDecompiledLine() {
        return this.cls.getDecompiledLine()
    }

    public final CodePosition getDefinitionPosition(Int i, Int i2) {
        JavaNode javaNodeAtPosition = getJavaNodeAtPosition(i, i2)
        if (javaNodeAtPosition == null) {
            return null
        }
        return getDefinitionPosition(javaNodeAtPosition)
    }

    public final CodePosition getDefinitionPosition(JavaNode javaNode) {
        JavaClass topParentClass = javaNode.getTopParentClass()
        topParentClass.decompile()
        Int decompiledLine = javaNode.getDecompiledLine()
        if (decompiledLine == 0) {
            return null
        }
        return CodePosition(topParentClass, decompiledLine, 0)
    }

    public final List getFields() {
        decompile()
        return this.fields
    }

    @Override // jadx.api.JavaNode
    public final String getFullName() {
        return this.cls.getFullName()
    }

    public final List getInnerClasses() {
        decompile()
        return this.innerClasses
    }

    public final JavaNode getJavaNodeAtPosition(Int i, Int i2) {
        Object obj
        Map codeAnnotations = getCodeAnnotations()
        if (codeAnnotations.isEmpty() || (obj = codeAnnotations.get(CodePosition(i, i2))) == null) {
            return null
        }
        return convertNode(obj)
    }

    public final List getMethods() {
        decompile()
        return this.methods
    }

    @Override // jadx.api.JavaNode
    public final String getName() {
        return this.cls.getShortName()
    }

    public final String getPackage() {
        return this.cls.getPackage()
    }

    public final Integer getSourceLine(Int i) {
        decompile()
        return (Integer) this.cls.getCode().getLineMapping().get(Integer.valueOf(i))
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getTopParentClass() {
        while (this.parent != null) {
            this = this.parent
        }
        return this
    }

    public final Map getUsageMap() {
        JavaNode javaNodeConvertNode
        Map codeAnnotations = getCodeAnnotations()
        if (codeAnnotations.isEmpty() || this.decompiler == null) {
            return Collections.emptyMap()
        }
        HashMap map = HashMap(codeAnnotations.size())
        for (Map.Entry entry : codeAnnotations.entrySet()) {
            CodePosition codePosition = (CodePosition) entry.getKey()
            Object value = entry.getValue()
            if ((value is LineAttrNode) && (javaNodeConvertNode = convertNode(value)) != null) {
                map.put(codePosition, javaNodeConvertNode)
            }
        }
        return map
    }

    public final Int hashCode() {
        return this.cls.hashCode()
    }

    public final String toString() {
        return this.cls.getFullName() + "[ " + getFullName() + " ]"
    }
}
