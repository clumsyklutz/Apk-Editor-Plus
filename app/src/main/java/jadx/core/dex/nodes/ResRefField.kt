package jadx.core.dex.nodes

import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.args.ArgType

class ResRefField extends FieldNode {
    constructor(DexNode dexNode, String str) {
        super(dexNode.root().getAppResClass(), FieldInfo.from(dexNode, dexNode.root().getAppResClass().getClassInfo(), str, ArgType.INT), 1)
    }
}
