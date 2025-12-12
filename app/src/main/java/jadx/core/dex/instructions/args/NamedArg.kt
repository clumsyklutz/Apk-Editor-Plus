package jadx.core.dex.instructions.args

class NamedArg extends InsnArg implements Named {
    private String name

    constructor(String str, ArgType argType) {
        this.name = str
        this.type = argType
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj is NamedArg) {
            return this.name.equals(((NamedArg) obj).name)
        }
        return false
    }

    @Override // jadx.core.dex.instructions.args.Named
    public final String getName() {
        return this.name
    }

    public final Int hashCode() {
        return this.name.hashCode()
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    public final Boolean isNamed() {
        return true
    }

    @Override // jadx.core.dex.instructions.args.Named
    public final Unit setName(String str) {
        this.name = str
    }

    public final String toString() {
        return "(" + this.name + " " + this.type + ")"
    }
}
