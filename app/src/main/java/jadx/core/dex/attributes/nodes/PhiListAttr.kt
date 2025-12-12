package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.instructions.PhiInsn
import java.util.Iterator
import java.util.LinkedList
import java.util.List

class PhiListAttr implements IAttribute {
    private val list = LinkedList()

    fun getList() {
        return this.list
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.PHI_LIST
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append("PHI: ")
        Iterator it = this.list.iterator()
        while (it.hasNext()) {
            sb.append('r').append(((PhiInsn) it.next()).getResult().getRegNum()).append(" ")
        }
        return sb.toString()
    }
}
