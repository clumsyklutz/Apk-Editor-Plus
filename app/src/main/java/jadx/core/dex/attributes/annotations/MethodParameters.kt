package jadx.core.dex.attributes.annotations

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.List

class MethodParameters implements IAttribute {
    private final List paramList

    constructor(Int i) {
        this.paramList = ArrayList(i)
    }

    fun getParamList() {
        return this.paramList
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.ANNOTATION_MTH_PARAMETERS
    }

    fun toString() {
        return Utils.listToString(this.paramList)
    }
}
