package jadx.core.xmlgen.entry

class RawNamedValue {
    private final Int nameRef
    private final RawValue rawValue

    constructor(Int i, RawValue rawValue) {
        this.nameRef = i
        this.rawValue = rawValue
    }

    fun getNameRef() {
        return this.nameRef
    }

    fun getRawValue() {
        return this.rawValue
    }

    fun toString() {
        return "RawNamedValue{nameRef=" + this.nameRef + ", rawValue=" + this.rawValue + '}'
    }
}
