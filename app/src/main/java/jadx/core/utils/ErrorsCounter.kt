package jadx.core.utils

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.IAttributeNode
import jadx.core.dex.attributes.nodes.JadxErrorAttr
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.exceptions.JadxOverflowException
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashSet
import java.util.Set
import org.d.b
import org.d.c

class ErrorsCounter {
    private static val LOG = c.a(ErrorsCounter.class)
    private val errorNodes = HashSet()
    private Int errorsCount

    private fun addError(IAttributeNode iAttributeNode, String str, Throwable th) {
        this.errorNodes.add(iAttributeNode)
        this.errorsCount++
        if (th == null) {
            iAttributeNode.add(AFlag.INCONSISTENT_CODE)
            LOG.c(str)
            return
        }
        if (th.getClass() == JadxOverflowException.class) {
            JadxOverflowException jadxOverflowException = JadxOverflowException(th.getMessage())
            LOG.c("{}, message: {}", str, jadxOverflowException.getMessage())
            th = jadxOverflowException
        } else {
            LOG.b(str, th)
        }
        iAttributeNode.addAttr(JadxErrorAttr(th))
    }

    fun classError(ClassNode classNode, String str) {
        return classError(classNode, str, null)
    }

    fun classError(ClassNode classNode, String str, Throwable th) {
        String errorMsg = formatErrorMsg(classNode, str)
        classNode.dex().root().getErrorsCounter().addError(classNode, errorMsg, th)
        return errorMsg
    }

    fun formatErrorMsg(ClassNode classNode, String str) {
        return str + " in class: " + classNode
    }

    fun formatErrorMsg(MethodNode methodNode, String str) {
        return str + " in method: " + methodNode
    }

    private fun formatException(Throwable th) {
        return (th == null || th.getMessage() == null) ? "" : "\n error: " + th.getMessage()
    }

    fun methodError(MethodNode methodNode, String str) {
        return methodError(methodNode, str, null)
    }

    fun methodError(MethodNode methodNode, String str, Throwable th) {
        String errorMsg = formatErrorMsg(methodNode, str)
        methodNode.dex().root().getErrorsCounter().addError(methodNode, errorMsg, th)
        return errorMsg
    }

    fun formatErrorMsg(ClassNode classNode, String str, Throwable th) {
        return formatErrorMsg(classNode, str) + formatException(th)
    }

    fun formatErrorMsg(MethodNode methodNode, String str, Throwable th) {
        return formatErrorMsg(methodNode, str) + formatException(th)
    }

    fun getErrorCount() {
        return this.errorsCount
    }

    fun printReport() {
        if (getErrorCount() > 0) {
            LOG.d("{} errors occurred in following nodes:", Integer.valueOf(getErrorCount()))
            ArrayList arrayList = ArrayList(this.errorNodes)
            Collections.sort(arrayList, Comparator() { // from class: jadx.core.utils.ErrorsCounter.1
                @Override // java.util.Comparator
                fun compare(Object obj, Object obj2) {
                    return String.valueOf(obj).compareTo(String.valueOf(obj2))
                }
            })
            for (Object obj : arrayList) {
                LOG.c("  {}: {}", obj.getClass().getSimpleName().replace("Node", ""), obj)
            }
        }
    }

    fun reset() {
        this.errorNodes.clear()
        this.errorsCount = 0
    }
}
