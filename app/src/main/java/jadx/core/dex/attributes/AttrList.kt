package jadx.core.dex.attributes

import jadx.core.utils.Utils
import java.util.LinkedList
import java.util.List

class AttrList implements IAttribute {
    private val list = LinkedList()
    private final AType type

    constructor(AType aType) {
        this.type = aType
    }

    fun getList() {
        return this.list
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return this.type
    }

    fun toString() {
        return Utils.listToString(this.list)
    }
}
