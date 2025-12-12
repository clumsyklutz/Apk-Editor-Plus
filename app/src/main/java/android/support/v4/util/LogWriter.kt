package android.support.v4.util

import android.support.annotation.RestrictTo
import android.util.Log
import java.io.Writer

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class LogWriter extends Writer {
    private StringBuilder mBuilder = StringBuilder(128)
    private final String mTag

    constructor(String str) {
        this.mTag = str
    }

    private fun flushBuilder() {
        if (this.mBuilder.length() > 0) {
            Log.d(this.mTag, this.mBuilder.toString())
            this.mBuilder.delete(0, this.mBuilder.length())
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    fun close() {
        flushBuilder()
    }

    @Override // java.io.Writer, java.io.Flushable
    fun flush() {
        flushBuilder()
    }

    @Override // java.io.Writer
    fun write(Array<Char> cArr, Int i, Int i2) {
        for (Int i3 = 0; i3 < i2; i3++) {
            Char c = cArr[i + i3]
            if (c == '\n') {
                flushBuilder()
            } else {
                this.mBuilder.append(c)
            }
        }
    }
}
