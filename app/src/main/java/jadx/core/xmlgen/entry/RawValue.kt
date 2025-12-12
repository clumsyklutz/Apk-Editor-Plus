package jadx.core.xmlgen.entry

class RawValue {
    private final Int data
    private final Int dataType

    constructor(Int i, Int i2) {
        this.dataType = i
        this.data = i2
    }

    public final Int getData() {
        return this.data
    }

    public final Int getDataType() {
        return this.dataType
    }

    public final String toString() {
        return "RawValue: type=0x" + Integer.toHexString(this.dataType) + ", value=" + this.data
    }
}
