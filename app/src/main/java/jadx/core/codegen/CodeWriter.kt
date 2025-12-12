package jadx.core.codegen

import jadx.api.CodePosition
import jadx.core.dex.attributes.nodes.LineAttrNode
import jadx.core.utils.files.FileUtils
import java.io.File
import java.io.PrintWriter
import java.util.Collections
import java.util.HashMap
import java.util.Iterator
import java.util.Map
import java.util.TreeMap

class CodeWriter {
    private static val ADD_LINE_NUMBERS = false
    private static val MAX_FILENAME_LENGTH = 128
    private String code
    public static val NL = System.getProperty("line.separator")
    public static val INDENT = "    "
    private static final Array<String> INDENT_CACHE = {"", INDENT, "        ", "            ", "                ", "                    "}
    private StringBuilder buf = StringBuilder()
    private Int line = 1
    private Int offset = 0
    private Map annotations = Collections.emptyMap()
    private Map lineMap = Collections.emptyMap()
    private Int indent = 0
    private String indentStr = ""

    class DefinitionWrapper {
        private final LineAttrNode node

        private constructor(LineAttrNode lineAttrNode) {
            this.node = lineAttrNode
        }

        fun getNode() {
            return this.node
        }
    }

    private fun addLine() {
        this.buf.append(NL)
        this.line++
        this.offset = 0
    }

    private fun addLineIndent() {
        this.buf.append(this.indentStr)
        this.offset += this.indentStr.length()
        return this
    }

    private fun attachAnnotation(Object obj, CodePosition codePosition) {
        if (this.annotations.isEmpty()) {
            this.annotations = HashMap()
        }
        return this.annotations.put(codePosition, obj)
    }

    private fun attachSourceLine(Int i, Int i2) {
        if (this.lineMap.isEmpty()) {
            this.lineMap = TreeMap()
        }
        this.lineMap.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    private fun removeFirstEmptyLine() {
        if (this.buf.indexOf(NL) == 0) {
            this.buf.delete(0, NL.length())
        }
    }

    private fun updateIndent() {
        Int i = this.indent
        if (i < INDENT_CACHE.length) {
            this.indentStr = INDENT_CACHE[i]
            return
        }
        StringBuilder sb = StringBuilder(i * 4)
        for (Int i2 = 0; i2 < i; i2++) {
            sb.append(INDENT)
        }
        this.indentStr = sb.toString()
    }

    fun add(Char c) {
        this.buf.append(c)
        this.offset++
        return this
    }

    CodeWriter add(CodeWriter codeWriter) {
        this.line--
        for (Map.Entry entry : codeWriter.annotations.entrySet()) {
            CodePosition codePosition = (CodePosition) entry.getKey()
            attachAnnotation(entry.getValue(), CodePosition(this.line + codePosition.getLine(), codePosition.getOffset()))
        }
        for (Map.Entry entry2 : codeWriter.lineMap.entrySet()) {
            attachSourceLine(((Integer) entry2.getKey()).intValue() + this.line, ((Integer) entry2.getValue()).intValue())
        }
        this.line += codeWriter.line
        this.offset = codeWriter.offset
        this.buf.append((CharSequence) codeWriter.buf)
        return this
    }

    fun add(String str) {
        this.buf.append(str)
        this.offset += str.length()
        return this
    }

    fun addIndent() {
        add(INDENT)
        return this
    }

    fun attachAnnotation(Object obj) {
        attachAnnotation(obj, CodePosition(this.line, this.offset + 1))
    }

    fun attachDefinition(LineAttrNode lineAttrNode) {
        attachAnnotation(lineAttrNode)
        attachAnnotation(DefinitionWrapper(lineAttrNode), CodePosition(this.line, this.offset))
    }

    fun attachSourceLine(Int i) {
        if (i == 0) {
            return
        }
        attachSourceLine(this.line, i)
    }

    fun bufLength() {
        return this.buf.length()
    }

    fun decIndent() {
        decIndent(1)
    }

    fun decIndent(Int i) {
        this.indent -= i
        if (this.indent < 0) {
            this.indent = 0
        }
        updateIndent()
    }

    fun finish() {
        removeFirstEmptyLine()
        this.buf.trimToSize()
        this.code = this.buf.toString()
        this.buf = null
        Iterator it = this.annotations.entrySet().iterator()
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next()
            Object value = entry.getValue()
            if (value is DefinitionWrapper) {
                ((DefinitionWrapper) value).getNode().setDecompiledLine(((CodePosition) entry.getKey()).getLine())
                it.remove()
            }
        }
    }

    fun getAnnotations() {
        return this.annotations
    }

    fun getCodeStr() {
        return this.code
    }

    fun getIndent() {
        return this.indent
    }

    fun getLine() {
        return this.line
    }

    fun getLineMapping() {
        return this.lineMap
    }

    fun incIndent() {
        incIndent(1)
    }

    fun incIndent(Int i) {
        this.indent += i
        updateIndent()
    }

    fun newLine() {
        addLine()
        return this
    }

    fun save(File file) throws Throwable {
        PrintWriter printWriter
        if (this.code == null) {
            finish()
        }
        String name = file.getName()
        if (name.length() > 128) {
            Int iIndexOf = name.indexOf(46)
            Int length = ((128 - name.length()) + iIndexOf) - 1
            file = File(file.getParentFile(), length <= 0 ? name.substring(0, 127) : name.substring(0, length) + name.substring(iIndexOf))
        }
        PrintWriter printWriter2 = null
        try {
            FileUtils.makeDirsForFile(file)
            printWriter = PrintWriter(file, "UTF-8")
        } catch (Exception e) {
            printWriter = null
        } catch (Throwable th) {
            th = th
        }
        try {
            printWriter.println(this.code)
            printWriter.close()
        } catch (Exception e2) {
            if (printWriter != null) {
                printWriter.close()
            }
        } catch (Throwable th2) {
            printWriter2 = printWriter
            th = th2
            if (printWriter2 != null) {
                printWriter2.close()
            }
            throw th
        }
    }

    fun save(File file, String str) throws Throwable {
        save(File(file, str))
    }

    fun save(File file, String str, String str2) throws Throwable {
        save(file, File(str, str2).getPath())
    }

    fun startLine() {
        addLine()
        addLineIndent()
        return this
    }

    fun startLine(Char c) {
        addLine()
        addLineIndent()
        add(c)
        return this
    }

    fun startLine(String str) {
        addLine()
        addLineIndent()
        add(str)
        return this
    }

    fun startLineWithNum(Int i) {
        if (i == 0) {
            startLine()
        } else {
            startLine()
            attachSourceLine(i)
        }
        return this
    }

    fun toString() {
        return this.buf == null ? this.code : this.buf.toString()
    }
}
