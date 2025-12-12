package jadx.core.dex.nodes.parser

import com.a.a.o
import com.gmail.heagoo.a.c.a
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import java.util.List

class StaticValuesParser extends EncValueParser {
    constructor(DexNode dexNode, o oVar) {
        super(dexNode, oVar)
    }

    fun processFields(List list) {
        Int iB = a.b(this.in)
        for (Int i = 0; i < iB; i++) {
            Object value = parseValue()
            if (i < list.size()) {
                ((FieldNode) list.get(i)).addAttr(FieldInitAttr.constValue(value))
            }
        }
        return iB
    }
}
