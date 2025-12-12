package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute

class SourceFileAttr implements IAttribute {
    private final String fileName

    constructor(String str) {
        this.fileName = str
    }

    fun getFileName() {
        return this.fileName
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.SOURCE_FILE
    }

    fun toString() {
        return "SOURCE:" + this.fileName
    }
}
