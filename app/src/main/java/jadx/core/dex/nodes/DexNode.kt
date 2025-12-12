package jadx.core.dex.nodes

import com.a.a.ab
import com.a.a.b
import com.a.a.d
import com.a.a.e
import com.a.a.f
import com.a.a.i
import com.a.a.o
import com.a.a.w
import com.a.a.x
import com.a.a.y
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.InfoStorage
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.utils.files.InputFile
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

class DexNode {
    public static val NO_INDEX = -1
    private final i dexBuf
    private final InputFile file
    private final RootNode root
    private val classes = ArrayList()
    private val clsMap = HashMap()
    private val constFields = HashMap()
    private val infoStorage = InfoStorage()

    constructor(RootNode rootNode, InputFile inputFile) {
        this.root = rootNode
        this.file = inputFile
        this.dexBuf = inputFile.getDexBuffer()
    }

    private fun deepResolveMethod(ClassNode classNode, String str) {
        MethodNode methodNodeDeepResolveMethod
        ClassNode classNodeResolveClass
        MethodNode methodNodeDeepResolveMethod2
        for (MethodNode methodNode : classNode.getMethods()) {
            if (methodNode.getMethodInfo().getShortId().startsWith(str)) {
                return methodNode
            }
        }
        ArgType superClass = classNode.getSuperClass()
        if (superClass != null && (classNodeResolveClass = resolveClass(superClass)) != null && (methodNodeDeepResolveMethod2 = deepResolveMethod(classNodeResolveClass, str)) != null) {
            return methodNodeDeepResolveMethod2
        }
        Iterator it = classNode.getInterfaces().iterator()
        while (it.hasNext()) {
            ClassNode classNodeResolveClass2 = resolveClass((ArgType) it.next())
            if (classNodeResolveClass2 != null && (methodNodeDeepResolveMethod = deepResolveMethod(classNodeResolveClass2, str)) != null) {
                return methodNodeDeepResolveMethod
            }
        }
        return null
    }

    fun deepResolveMethod(MethodInfo methodInfo) {
        ClassNode classNodeResolveClass = resolveClass(methodInfo.getDeclClass())
        if (classNodeResolveClass == null) {
            return null
        }
        return deepResolveMethod(classNodeResolveClass, methodInfo.makeSignature(false))
    }

    fun getClasses() {
        return this.classes
    }

    fun getConstFields() {
        return this.constFields
    }

    fun getFieldId(Int i) {
        return (w) this.dexBuf.i().get(i)
    }

    fun getInfoStorage() {
        return this.infoStorage
    }

    fun getInputFile() {
        return this.file
    }

    fun getMethodId(Int i) {
        return (x) this.dexBuf.j().get(i)
    }

    fun getProtoId(Int i) {
        return (y) this.dexBuf.h().get(i)
    }

    fun getString(Int i) {
        return (String) this.dexBuf.e().get(i)
    }

    fun getType(Int i) {
        return ArgType.parse(getString(((Integer) this.dexBuf.f().get(i)).intValue()))
    }

    Unit initInnerClasses() {
        ArrayList<ClassNode> arrayList = ArrayList()
        for (ClassNode classNode : this.classes) {
            if (classNode.getClassInfo().isInner()) {
                arrayList.add(classNode)
            }
        }
        for (ClassNode classNode2 : arrayList) {
            ClassInfo classInfo = classNode2.getClassInfo()
            ClassNode classNodeResolveClass = resolveClass(classInfo.getParentClass())
            if (classNodeResolveClass == null) {
                this.clsMap.remove(classInfo)
                classInfo.notInner(classNode2.dex())
                this.clsMap.put(classInfo, classNode2)
            } else {
                classNodeResolveClass.addInnerClass(classNode2)
            }
        }
    }

    fun loadClasses() {
        Iterator it = this.dexBuf.k().iterator()
        while (it.hasNext()) {
            ClassNode classNode = ClassNode(this, (e) it.next())
            this.classes.add(classNode)
            this.clsMap.put(classNode.getClassInfo(), classNode)
        }
    }

    fun openSection(Int i) {
        return this.dexBuf.a(i)
    }

    fun readClassData(e eVar) {
        return this.dexBuf.a(eVar)
    }

    fun readCode(d dVar) {
        return this.dexBuf.a(dVar)
    }

    fun readParamList(Int i) {
        ab abVarB = this.dexBuf.b(i)
        ArrayList arrayList = ArrayList(abVarB.a().length)
        Array<Short> sArrA = abVarB.a()
        for (Short s : sArrA) {
            arrayList.add(getType(s))
        }
        return Collections.unmodifiableList(arrayList)
    }

    fun resolveClass(ClassInfo classInfo) {
        return (ClassNode) this.clsMap.get(classInfo)
    }

    fun resolveClass(ArgType argType) {
        if (argType.isGeneric()) {
            argType = ArgType.object(argType.getObject())
        }
        return resolveClass(ClassInfo.fromType(this, argType))
    }

    fun resolveField(FieldInfo fieldInfo) {
        ClassNode classNodeResolveClass = resolveClass(fieldInfo.getDeclClass())
        if (classNodeResolveClass != null) {
            return classNodeResolveClass.searchField(fieldInfo)
        }
        return null
    }

    fun resolveMethod(MethodInfo methodInfo) {
        ClassNode classNodeResolveClass = resolveClass(methodInfo.getDeclClass())
        if (classNodeResolveClass != null) {
            return classNodeResolveClass.searchMethod(methodInfo)
        }
        return null
    }

    fun root() {
        return this.root
    }

    fun toString() {
        return "DEX"
    }
}
