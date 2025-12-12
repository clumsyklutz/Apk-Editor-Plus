package jadx.core.dex.regions.loops

import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg

class ForEachLoop extends LoopType {
    private final InsnArg iterableArg
    private final RegisterArg varArg

    constructor(RegisterArg registerArg, InsnArg insnArg) {
        this.varArg = registerArg
        this.iterableArg = insnArg
    }

    public final InsnArg getIterableArg() {
        return this.iterableArg
    }

    public final RegisterArg getVarArg() {
        return this.varArg
    }
}
