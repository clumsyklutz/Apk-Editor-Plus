package org.apache.commons.io.output

import java.io.Serializable
import java.io.Writer

class StringBuilderWriter extends Writer implements Serializable {
    public final StringBuilder builder

    constructor(Int i) {
        this.builder = StringBuilder(i)
    }

    @Override // java.io.Writer, java.lang.Appendable
    fun append(Char c) {
        this.builder.append(c)
        return this
    }

    @Override // java.io.Writer, java.lang.Appendable
    fun append(CharSequence charSequence) {
        this.builder.append(charSequence)
        return this
    }

    @Override // java.io.Writer, java.lang.Appendable
    fun append(CharSequence charSequence, Int i, Int i2) {
        this.builder.append(charSequence, i, i2)
        return this
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    fun close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    fun flush() {
    }

    fun toString() {
        return this.builder.toString()
    }

    @Override // java.io.Writer
    fun write(String str) {
        if (str != null) {
            this.builder.append(str)
        }
    }

    @Override // java.io.Writer
    fun write(Array<Char> cArr, Int i, Int i2) {
        if (cArr != null) {
            this.builder.append(cArr, i, i2)
        }
    }
}
