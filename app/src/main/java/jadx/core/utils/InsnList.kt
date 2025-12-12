package jadx.core.utils

import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import java.util.Iterator
import java.util.List

class InsnList implements Iterable {
    private final List list

    constructor(List list) {
        this.list = list
    }

    fun getIndex(List list, InsnNode insnNode) {
        Int size = list.size()
        for (Int i = 0; i < size; i++) {
            if (list.get(i) == insnNode) {
                return i
            }
        }
        return -1
    }

    fun remove(BlockNode blockNode, InsnNode insnNode) {
        remove(blockNode.getInstructions(), insnNode)
    }

    fun remove(List list, InsnNode insnNode) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            if (((InsnNode) it.next()) == insnNode) {
                it.remove()
                return
            }
        }
    }

    public final Boolean contains(InsnNode insnNode) {
        return getIndex(insnNode) != -1
    }

    public final InsnNode get(Int i) {
        return (InsnNode) this.list.get(i)
    }

    public final Int getIndex(InsnNode insnNode) {
        return getIndex(this.list, insnNode)
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.list.iterator()
    }

    public final Unit remove(InsnNode insnNode) {
        remove(this.list, insnNode)
    }

    public final Int size() {
        return this.list.size()
    }
}
