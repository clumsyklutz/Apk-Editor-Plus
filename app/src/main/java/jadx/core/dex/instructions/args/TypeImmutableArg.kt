package jadx.core.dex.instructions.args

class TypeImmutableArg extends RegisterArg {
    private Boolean isThis

    constructor(Int i, ArgType argType) {
        super(i, argType)
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg
    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        return (obj is TypeImmutableArg) && super.equals(obj) && this.isThis == ((TypeImmutableArg) obj).isThis
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg, jadx.core.dex.instructions.args.Named
    fun getName() {
        return this.isThis ? "this" : super.getName()
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg
    fun hashCode() {
        return (this.isThis ? 1 : 0) + (super.hashCode() * 31)
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg, jadx.core.dex.instructions.args.InsnArg
    fun isThis() {
        return this.isThis
    }

    @Override // jadx.core.dex.instructions.args.Typed
    fun isTypeImmutable() {
        return true
    }

    fun markAsThis() {
        this.isThis = true
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg
    Unit setSVar(SSAVar sSAVar) {
        if (this.isThis) {
            sSAVar.setName("this")
        }
        sSAVar.setTypeImmutable(this.type)
        super.setSVar(sSAVar)
    }

    @Override // jadx.core.dex.instructions.args.RegisterArg, jadx.core.dex.instructions.args.Typed
    fun setType(ArgType argType) {
    }
}
