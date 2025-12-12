package jadx.core.dex.info

import jadx.core.dex.instructions.args.ArgType
import java.util.HashMap
import java.util.Map

class InfoStorage {
    private val classes = HashMap()
    private val methods = HashMap()
    private val fields = HashMap()

    fun getCls(ArgType argType) {
        return (ClassInfo) this.classes.get(argType)
    }

    fun getField(FieldInfo fieldInfo) {
        synchronized (this.fields) {
            FieldInfo fieldInfo2 = (FieldInfo) this.fields.get(fieldInfo)
            if (fieldInfo2 != null) {
                return fieldInfo2
            }
            this.fields.put(fieldInfo, fieldInfo)
            return fieldInfo
        }
    }

    fun getMethod(Int i) {
        return (MethodInfo) this.methods.get(Integer.valueOf(i))
    }

    fun putCls(ClassInfo classInfo) {
        synchronized (this.classes) {
            ClassInfo classInfo2 = (ClassInfo) this.classes.put(classInfo.getType(), classInfo)
            if (classInfo2 != null) {
                classInfo = classInfo2
            }
        }
        return classInfo
    }

    fun putMethod(Int i, MethodInfo methodInfo) {
        synchronized (this.methods) {
            MethodInfo methodInfo2 = (MethodInfo) this.methods.put(Integer.valueOf(i), methodInfo)
            if (methodInfo2 != null) {
                methodInfo = methodInfo2
            }
        }
        return methodInfo
    }
}
