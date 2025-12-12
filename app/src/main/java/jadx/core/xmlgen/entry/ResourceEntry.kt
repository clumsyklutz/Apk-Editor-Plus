package jadx.core.xmlgen.entry

import jadx.core.deobf.Deobfuscator
import java.util.List

class ResourceEntry {
    private EntryConfig config
    private final Int id
    private final String keyName
    private List namedValues
    private Int parentRef
    private final String pkgName
    private RawValue simpleValue
    private final String typeName

    constructor(Int i) {
        this(i, "", "", "")
    }

    constructor(Int i, String str, String str2, String str3) {
        this.id = i
        this.pkgName = str
        this.typeName = str2
        this.keyName = str3
    }

    public final EntryConfig getConfig() {
        return this.config
    }

    public final Int getId() {
        return this.id
    }

    public final String getKeyName() {
        return this.keyName
    }

    public final List getNamedValues() {
        return this.namedValues
    }

    public final Int getParentRef() {
        return this.parentRef
    }

    public final String getPkgName() {
        return this.pkgName
    }

    public final RawValue getSimpleValue() {
        return this.simpleValue
    }

    public final String getTypeName() {
        return this.typeName
    }

    public final Unit setConfig(EntryConfig entryConfig) {
        this.config = entryConfig
    }

    public final Unit setNamedValues(List list) {
        this.namedValues = list
    }

    public final Unit setParentRef(Int i) {
        this.parentRef = i
    }

    public final Unit setSimpleValue(RawValue rawValue) {
        this.simpleValue = rawValue
    }

    public final String toString() {
        return "  0x" + Integer.toHexString(this.id) + " (" + this.id + ")" + this.config + " = " + this.typeName + Deobfuscator.CLASS_NAME_SEPARATOR + this.keyName
    }
}
