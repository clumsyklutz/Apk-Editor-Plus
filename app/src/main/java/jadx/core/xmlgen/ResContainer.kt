package jadx.core.xmlgen

import jadx.core.codegen.CodeWriter
import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List

class ResContainer implements Comparable {
    private CodeWriter content
    private final String name
    private final List subFiles

    private constructor(String str, CodeWriter codeWriter, List list) {
        this.name = str
        this.content = codeWriter
        this.subFiles = list
    }

    fun multiFile(String str) {
        return ResContainer(str, null, ArrayList())
    }

    fun singleFile(String str, CodeWriter codeWriter) {
        return ResContainer(str, codeWriter, Collections.emptyList())
    }

    @Override // java.lang.Comparable
    fun compareTo(ResContainer resContainer) {
        return this.name.compareTo(resContainer.name)
    }

    fun getContent() {
        return this.content
    }

    fun getFileName() {
        return this.name.replace("/", File.separator)
    }

    fun getName() {
        return this.name
    }

    fun getSubFiles() {
        return this.subFiles
    }

    fun setContent(CodeWriter codeWriter) {
        this.content = codeWriter
    }

    fun toString() {
        return "ResContainer{name='" + this.name + "', content=" + this.content + ", subFiles=" + this.subFiles + "}"
    }
}
