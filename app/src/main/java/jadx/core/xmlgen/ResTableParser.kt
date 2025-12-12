package jadx.core.xmlgen

import jadx.core.codegen.CodeWriter
import jadx.core.xmlgen.entry.EntryConfig
import jadx.core.xmlgen.entry.RawNamedValue
import jadx.core.xmlgen.entry.RawValue
import jadx.core.xmlgen.entry.ResourceEntry
import jadx.core.xmlgen.entry.ValuesParser
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class ResTableParser extends CommonBinaryParser {
    private val resStorage = ResourceStorage()
    private Array<String> strings

    final class PackageChunk {
        private final Int id
        private final Array<String> keyStrings
        private final String name
        private final Array<String> typeStrings

        private constructor(Int i, String str, Array<String> strArr, Array<String> strArr2) {
            this.id = i
            this.name = str
            this.typeStrings = strArr
            this.keyStrings = strArr2
        }

        public final Int getId() {
            return this.id
        }

        public final Array<String> getKeyStrings() {
            return this.keyStrings
        }

        public final String getName() {
            return this.name
        }

        public final Array<String> getTypeStrings() {
            return this.typeStrings
        }
    }

    private fun parseConfig() throws IOException {
        Long pos = this.is.getPos()
        Int int32 = this.is.readInt32()
        EntryConfig entryConfig = EntryConfig()
        this.is.readInt16()
        this.is.readInt16()
        entryConfig.setLanguage(parseLocale())
        entryConfig.setCountry(parseLocale())
        this.is.readInt8()
        this.is.readInt8()
        this.is.readInt16()
        this.is.skipToPos(pos + int32, "Skip config parsing")
        return entryConfig
    }

    private fun parseEntry(PackageChunk packageChunk, Int i, Int i2, EntryConfig entryConfig) throws IOException {
        this.is.readInt16()
        Int int16 = this.is.readInt16()
        ResourceEntry resourceEntry = ResourceEntry((packageChunk.getId() << 24) | (i << 16) | i2, packageChunk.getName(), packageChunk.getTypeStrings()[i - 1], packageChunk.getKeyStrings()[this.is.readInt32()])
        resourceEntry.setConfig(entryConfig)
        if ((int16 & 1) == 0) {
            resourceEntry.setSimpleValue(parseValue())
        } else {
            resourceEntry.setParentRef(this.is.readInt32())
            Int int32 = this.is.readInt32()
            ArrayList arrayList = ArrayList(int32)
            for (Int i3 = 0; i3 < int32; i3++) {
                arrayList.add(parseValueMap())
            }
            resourceEntry.setNamedValues(arrayList)
        }
        this.resStorage.add(resourceEntry)
    }

    private fun parseLocale() {
        Int int8 = this.is.readInt8()
        Int int82 = this.is.readInt8()
        if (int8 == 0 || int82 == 0 || (int8 & 128) != 0) {
            return null
        }
        return String(new Array<Char>{(Char) int8, (Char) int82})
    }

    private fun parsePackage() throws IOException {
        Long pos = this.is.getPos()
        this.is.checkInt16(512, "Not a table chunk")
        Int int16 = this.is.readInt16()
        if (int16 != 284 && int16 != 288) {
            die("Unexpected package header size")
        }
        Long uInt32 = pos + this.is.readUInt32()
        Int int32 = this.is.readInt32()
        String string16Fixed = this.is.readString16Fixed(128)
        Long int322 = this.is.readInt32() + pos
        this.is.readInt32()
        Long int323 = this.is.readInt32() + pos
        this.is.readInt32()
        if (int16 == 288) {
            this.is.readInt32()
        }
        Array<String> stringPool = null
        if (int322 != 0) {
            this.is.skipToPos(int322, "Expected typeStrings string pool")
            stringPool = parseStringPool()
        }
        Array<String> stringPool2 = null
        if (int323 != 0) {
            this.is.skipToPos(int323, "Expected keyStrings string pool")
            stringPool2 = parseStringPool()
        }
        PackageChunk packageChunk = PackageChunk(int32, string16Fixed, stringPool, stringPool2)
        if (int32 == 127) {
            this.resStorage.setAppPackage(string16Fixed)
        }
        while (this.is.getPos() < uInt32) {
            Long pos2 = this.is.getPos()
            Int int162 = this.is.readInt16()
            if (int162 != 0) {
                if (int162 == 514) {
                    parseTypeSpecChunk()
                } else if (int162 == 513) {
                    parseTypeChunk(pos2, packageChunk)
                }
            }
        }
        return packageChunk
    }

    private fun parseTypeChunk(Long j, PackageChunk packageChunk) throws IOException {
        this.is.readInt16()
        this.is.readInt32()
        Int int8 = this.is.readInt8()
        this.is.checkInt8(0, "type chunk, res0")
        this.is.checkInt16(0, "type chunk, res1")
        Int int32 = this.is.readInt32()
        Long int322 = this.is.readInt32() + j
        EntryConfig config = parseConfig()
        Array<Int> iArr = new Int[int32]
        for (Int i = 0; i < int32; i++) {
            iArr[i] = this.is.readInt32()
        }
        this.is.checkPos(int322, "Expected entry start")
        for (Int i2 = 0; i2 < int32; i2++) {
            if (iArr[i2] != -1) {
                parseEntry(packageChunk, int8, i2, config)
            }
        }
    }

    private fun parseTypeSpecChunk() throws IOException {
        this.is.checkInt16(16, "Unexpected type spec header size")
        this.is.readInt32()
        this.is.readInt8()
        this.is.skip(3L)
        Int int32 = this.is.readInt32()
        for (Int i = 0; i < int32; i++) {
            this.is.readInt32()
        }
    }

    private fun parseValue() throws IOException {
        this.is.checkInt16(8, "value size")
        this.is.checkInt8(0, "value res0 not 0")
        return RawValue(this.is.readInt8(), this.is.readInt32())
    }

    private fun parseValueMap() {
        return RawNamedValue(this.is.readInt32(), parseValue())
    }

    fun decode(InputStream inputStream) {
        this.is = ParserStream(inputStream)
        decodeTableChunk()
        this.resStorage.finish()
    }

    fun decodeFiles(InputStream inputStream) {
        decode(inputStream)
        ResXmlGen resXmlGen = ResXmlGen(this.resStorage, ValuesParser(this.strings, this.resStorage.getResourcesNames()))
        ResContainer resContainerMultiFile = ResContainer.multiFile("res")
        resContainerMultiFile.setContent(makeDump())
        resContainerMultiFile.getSubFiles().addAll(resXmlGen.makeResourcesXml())
        return resContainerMultiFile
    }

    Unit decodeTableChunk() throws IOException {
        this.is.checkInt16(2, "Not a table chunk")
        this.is.checkInt16(12, "Unexpected table header size")
        this.is.readInt32()
        Int int32 = this.is.readInt32()
        this.strings = parseStringPool()
        for (Int i = 0; i < int32; i++) {
            parsePackage()
        }
    }

    fun getResStorage() {
        return this.resStorage
    }

    fun makeDump() {
        CodeWriter codeWriter = CodeWriter()
        codeWriter.add("app package: ").add(this.resStorage.getAppPackage())
        codeWriter.startLine()
        ValuesParser valuesParser = ValuesParser(this.strings, this.resStorage.getResourcesNames())
        for (ResourceEntry resourceEntry : this.resStorage.getResources()) {
            codeWriter.startLine(resourceEntry + ": " + valuesParser.getValueString(resourceEntry))
        }
        codeWriter.finish()
        return codeWriter
    }
}
