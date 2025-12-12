package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.FieldNode
import java.util.HashMap
import java.util.Map

class EnumMapAttr implements IAttribute {
    private val fieldsMap = HashMap()

    class KeyValueMap {
        private val map = HashMap()

        fun get(Object obj) {
            return this.map.get(obj)
        }

        Unit put(Object obj, Object obj2) {
            this.map.put(obj, obj2)
        }
    }

    fun add(FieldNode fieldNode, Object obj, Object obj2) {
        KeyValueMap map = getMap(fieldNode)
        if (map == null) {
            map = KeyValueMap()
            this.fieldsMap.put(fieldNode, map)
        }
        map.put(obj, obj2)
    }

    fun getMap(FieldNode fieldNode) {
        return (KeyValueMap) this.fieldsMap.get(fieldNode)
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.ENUM_MAP
    }

    fun toString() {
        return "Enum fields map: " + this.fieldsMap
    }
}
