package jadx.core.dex.instructions.args

import jadx.core.dex.info.FieldInfo

class FieldArg extends RegisterArg {
    private final FieldInfo field
    private final InsnArg instArg

    constructor(FieldInfo fieldInfo, InsnArg insnArg) {
        super(-1)
        this.instArg = insnArg
        this.field = fieldInfo
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg
    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is FieldArg) || !super.equals(obj)) {
            return false
        }
        FieldArg fieldArg = (FieldArg) obj
        if (!this.field.equals(fieldArg.field)) {
            return false
        }
        if (this.instArg != null) {
            if (this.instArg.equals(fieldArg.instArg)) {
                return true
            }
        } else if (fieldArg.instArg == null) {
            return true
        }
        return false
    }

    public final FieldInfo getField() {
        return this.field
    }

    public final InsnArg getInstanceArg() {
        return this.instArg
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg
    public final Int hashCode() {
        return (this.instArg != null ? this.instArg.hashCode() : 0) + (((super.hashCode() * 31) + this.field.hashCode()) * 31)
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    public final Boolean isField() {
        return true
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg, jadx.core.dex.instructions.args.InsnArg
    public final Boolean isRegister() {
        return false
    }

    public final Boolean isStatic() {
        return this.instArg == null
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg, jadx.core.dex.instructions.args.Typed
    public final Unit setType(ArgType argType) {
        this.type = argType
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg
    public final String toString() {
        return "(" + this.field + ")"
    }
}
