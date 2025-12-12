package org.jf.util

import java.io.PrintStream
import java.io.PrintWriter

class ExceptionWithContext extends RuntimeException {
    public StringBuffer context

    constructor(String str, Object... objArr) {
        this(null, str, objArr)
    }

    constructor(Throwable th) {
        this(th, null, new Object[0])
    }

    constructor(Throwable th, String str, Object... objArr) {
        super(str != null ? formatMessage(str, objArr) : th != null ? th.getMessage() : null, th)
        if (!(th is ExceptionWithContext)) {
            this.context = StringBuffer(200)
            return
        }
        String string = ((ExceptionWithContext) th).context.toString()
        StringBuffer stringBuffer = StringBuffer(string.length() + 200)
        this.context = stringBuffer
        stringBuffer.append(string)
    }

    fun formatMessage(String str, Object... objArr) {
        if (str == null) {
            return null
        }
        return String.format(str, objArr)
    }

    fun withContext(Throwable th, String str, Object... objArr) {
        ExceptionWithContext exceptionWithContext = th is ExceptionWithContext ? (ExceptionWithContext) th : ExceptionWithContext(th)
        exceptionWithContext.addContext(String.format(str, objArr))
        return exceptionWithContext
    }

    fun addContext(String str) {
        if (str == null) {
            throw NullPointerException("str == null")
        }
        this.context.append(str)
        if (str.endsWith("\n")) {
            return
        }
        this.context.append('\n')
    }

    @Override // java.lang.Throwable
    fun printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream)
        printStream.println(this.context)
    }

    @Override // java.lang.Throwable
    fun printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter)
        printWriter.println(this.context)
    }
}
