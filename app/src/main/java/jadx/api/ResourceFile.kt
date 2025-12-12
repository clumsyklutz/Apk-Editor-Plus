package jadx.api

import jadx.core.xmlgen.ResContainer
import java.io.File

class ResourceFile {
    private final JadxDecompiler decompiler
    private final String name
    private final ResourceType type
    private ZipRef zipRef

    class ZipRef {
        private final String entryName
        private final File zipFile

        constructor(File file, String str) {
            this.zipFile = file
            this.entryName = str
        }

        public final String getEntryName() {
            return this.entryName
        }

        public final File getZipFile() {
            return this.zipFile
        }

        public final String toString() {
            return "ZipRef{" + this.zipFile + ", '" + this.entryName + "'}"
        }
    }

    ResourceFile(JadxDecompiler jadxDecompiler, String str, ResourceType resourceType) {
        this.decompiler = jadxDecompiler
        this.name = str
        this.type = resourceType
    }

    fun getContent() {
        return ResourcesLoader.loadContent(this.decompiler, this)
    }

    fun getName() {
        return this.name
    }

    fun getType() {
        return this.type
    }

    ZipRef getZipRef() {
        return this.zipRef
    }

    Unit setZipRef(ZipRef zipRef) {
        this.zipRef = zipRef
    }

    fun toString() {
        return "ResourceFile{name='" + this.name + "', type=" + this.type + "}"
    }
}
