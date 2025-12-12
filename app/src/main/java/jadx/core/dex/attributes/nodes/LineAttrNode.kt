package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AttrNode

abstract class LineAttrNode extends AttrNode {
    private Int decompiledLine
    private Int sourceLine

    fun copyLines(LineAttrNode lineAttrNode) {
        setSourceLine(lineAttrNode.getSourceLine())
        setDecompiledLine(lineAttrNode.getDecompiledLine())
    }

    fun getDecompiledLine() {
        return this.decompiledLine
    }

    fun getSourceLine() {
        return this.sourceLine
    }

    fun setDecompiledLine(Int i) {
        this.decompiledLine = i
    }

    fun setSourceLine(Int i) {
        this.sourceLine = i
    }
}
