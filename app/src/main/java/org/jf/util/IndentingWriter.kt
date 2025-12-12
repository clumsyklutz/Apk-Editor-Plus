package org.jf.util

import java.io.IOException
import java.io.Writer

class IndentingWriter extends Writer {
    public static val newLine = System.getProperty("line.separator")
    public final Writer writer
    public Int indentLevel = 0
    public Boolean beginningOfLine = true

    constructor(Writer writer) {
        this.writer = writer
    }

    @Override // java.io.Writer, java.lang.Appendable
    fun append(Char c) throws IOException {
        write(c)
        return this
    }

    @Override // java.io.Writer, java.lang.Appendable
    fun append(CharSequence charSequence) throws IOException {
        write(charSequence.toString())
        return this
    }

    @Override // java.io.Writer, java.lang.Appendable
    fun append(CharSequence charSequence, Int i, Int i2) throws IOException {
        write(charSequence.subSequence(i, i2).toString())
        return this
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    fun close() throws IOException {
        this.writer.close()
    }

    fun deindent(Int i) {
        Int i2 = this.indentLevel - i
        this.indentLevel = i2
        if (i2 < 0) {
            this.indentLevel = 0
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    fun flush() throws IOException {
        this.writer.flush()
    }

    fun indent(Int i) {
        Int i2 = this.indentLevel + i
        this.indentLevel = i2
        if (i2 < 0) {
            this.indentLevel = 0
        }
    }

    @Override // java.io.Writer
    fun write(Int i) throws IOException {
        if (i == 10) {
            this.writer.write(newLine)
            this.beginningOfLine = true
        } else {
            if (this.beginningOfLine) {
                writeIndent()
            }
            this.beginningOfLine = false
            this.writer.write(i)
        }
    }

    @Override // java.io.Writer
    fun write(String str) throws IOException {
        write(str, 0, str.length())
    }

    @Override // java.io.Writer
    fun write(String str, Int i, Int i2) throws IOException {
        Int i3 = i2 + i
        Int i4 = i
        while (i < i3) {
            i = str.indexOf(10, i4)
            if (i == -1 || i >= i3) {
                writeLine(str, i4, i3 - i4)
                return
            }
            writeLine(str, i4, i - i4)
            this.writer.write(newLine)
            this.beginningOfLine = true
            i4 = i + 1
        }
    }

    @Override // java.io.Writer
    fun write(Array<Char> cArr) throws IOException {
        write(cArr, 0, cArr.length)
    }

    @Override // java.io.Writer
    fun write(Array<Char> cArr, Int i, Int i2) throws IOException {
        Int i3 = i2 + i
        Int i4 = i
        while (i < i3) {
            if (cArr[i] == '\n') {
                writeLine(cArr, i4, i - i4)
                this.writer.write(newLine)
                this.beginningOfLine = true
                i4 = i + 1
                i = i4
            } else {
                i++
            }
        }
        writeLine(cArr, i4, i - i4)
    }

    fun writeIndent() throws IOException {
        for (Int i = 0; i < this.indentLevel; i++) {
            this.writer.write(32)
        }
    }

    public final Unit writeLine(String str, Int i, Int i2) throws IOException {
        if (this.beginningOfLine && i2 > 0) {
            writeIndent()
            this.beginningOfLine = false
        }
        this.writer.write(str, i, i2)
    }

    public final Unit writeLine(Array<Char> cArr, Int i, Int i2) throws IOException {
        if (this.beginningOfLine && i2 > 0) {
            writeIndent()
            this.beginningOfLine = false
        }
        this.writer.write(cArr, i, i2)
    }
}
