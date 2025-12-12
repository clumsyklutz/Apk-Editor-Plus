package jadx.api

import jadx.core.codegen.CodeWriter
import jadx.core.xmlgen.ResContainer

class ResourceFileContent extends ResourceFile {
    private final CodeWriter content

    constructor(String str, ResourceType resourceType, CodeWriter codeWriter) {
        super(null, str, resourceType)
        this.content = codeWriter
    }

    @Override // jadx.api.ResourceFile
    fun getContent() {
        return ResContainer.singleFile(getName(), this.content)
    }
}
