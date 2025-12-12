package jadx.core.utils

import jadx.api.JadxDecompiler
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.Arrays
import java.util.Iterator

class Utils {
    public static val JADX_API_PACKAGE = JadxDecompiler.class.getPackage().getName()

    private constructor() {
    }

    fun arrayToString(Array<Object> objArr) {
        if (objArr == null) {
            return ""
        }
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < objArr.length; i++) {
            if (i != 0) {
                sb.append(", ")
            }
            sb.append(objArr[i])
        }
        return sb.toString()
    }

    fun cleanObjectName(String str) {
        Int length = str.length() - 1
        return (str.charAt(0) == 'L' && str.charAt(length) == ';') ? str.substring(1, length).replace('/', '.') : str
    }

    fun compare(Int i, Int i2) {
        if (i < i2) {
            return -1
        }
        return i == i2 ? 0 : 1
    }

    private fun filter(Throwable th) {
        Array<StackTraceElement> stackTrace = th.getStackTrace()
        Int i = -1
        Int length = stackTrace.length
        Int i2 = 0
        while (true) {
            if (i2 >= length) {
                i2 = i
                break
            }
            if (stackTrace[i2].getClassName().startsWith(JADX_API_PACKAGE)) {
                i = i2
            } else if (i > 0) {
                break
            }
            i2++
        }
        if (i2 <= 0 || i2 >= length) {
            return
        }
        th.setStackTrace((Array<StackTraceElement>) Arrays.copyOfRange(stackTrace, 0, i2))
    }

    private fun filterRecursive(Throwable th) {
        do {
            try {
                filter(th)
            } catch (Exception e) {
            }
            th = th.getCause()
        } while (th != null);
    }

    fun getStackTrace(Throwable th) {
        if (th == null) {
            return ""
        }
        StringWriter stringWriter = StringWriter()
        PrintWriter printWriter = PrintWriter((Writer) stringWriter, true)
        filterRecursive(th)
        th.printStackTrace(printWriter)
        return stringWriter.getBuffer().toString()
    }

    fun listToString(Iterable iterable) {
        if (iterable == null) {
            return ""
        }
        StringBuilder sb = StringBuilder()
        Iterator it = iterable.iterator()
        while (it.hasNext()) {
            sb.append(it.next())
            if (it.hasNext()) {
                sb.append(", ")
            }
        }
        return sb.toString()
    }

    fun makeQualifiedObjectName(String str) {
        return "L" + str.replace('.', '/') + ';'
    }
}
