package jadx.core.dex.attributes.annotations

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.utils.Utils
import java.util.Collection
import java.util.Collections
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

class AnnotationsList implements IAttribute {
    public static val EMPTY = AnnotationsList(Collections.emptyList())
    private final Map map

    constructor(List list) {
        this.map = HashMap(list.size())
        Iterator it = list.iterator()
        while (it.hasNext()) {
            Annotation annotation = (Annotation) it.next()
            this.map.put(annotation.getAnnotationClass(), annotation)
        }
    }

    fun get(String str) {
        return (Annotation) this.map.get(str)
    }

    fun getAll() {
        return this.map.values()
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.ANNOTATION_LIST
    }

    fun isEmpty() {
        return this.map.isEmpty()
    }

    fun size() {
        return this.map.size()
    }

    fun toString() {
        return Utils.listToString(this.map.values())
    }
}
