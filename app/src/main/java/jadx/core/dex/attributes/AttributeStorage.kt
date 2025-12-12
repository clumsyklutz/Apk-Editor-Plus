package jadx.core.dex.attributes

import jadx.core.dex.attributes.annotations.Annotation
import jadx.core.dex.attributes.annotations.AnnotationsList
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.Collections
import java.util.EnumSet
import java.util.IdentityHashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set

class AttributeStorage {
    private val flags = EnumSet.noneOf(AFlag.class)
    private val attributes = IdentityHashMap()

    fun add(AFlag aFlag) {
        this.flags.add(aFlag)
    }

    fun add(AType aType, Object obj) {
        AttrList attrList = (AttrList) get(aType)
        if (attrList == null) {
            attrList = AttrList(aType)
            add(attrList)
        }
        attrList.getList().add(obj)
    }

    fun add(IAttribute iAttribute) {
        this.attributes.put(iAttribute.getType(), iAttribute)
    }

    fun addAll(AttributeStorage attributeStorage) {
        this.flags.addAll(attributeStorage.flags)
        this.attributes.putAll(attributeStorage.attributes)
    }

    fun clear() {
        this.flags.clear()
        this.attributes.clear()
    }

    fun contains(AFlag aFlag) {
        return this.flags.contains(aFlag)
    }

    fun contains(AType aType) {
        return this.attributes.containsKey(aType)
    }

    fun get(AType aType) {
        return (IAttribute) this.attributes.get(aType)
    }

    fun getAll(AType aType) {
        AttrList attrList = (AttrList) get(aType)
        return attrList == null ? Collections.emptyList() : Collections.unmodifiableList(attrList.getList())
    }

    fun getAnnotation(String str) {
        AnnotationsList annotationsList = (AnnotationsList) get(AType.ANNOTATION_LIST)
        if (annotationsList == null) {
            return null
        }
        return annotationsList.get(str)
    }

    fun getAttributeStrings() {
        Int size = this.flags.size() + this.attributes.size() + this.attributes.size()
        if (size == 0) {
            return Collections.emptyList()
        }
        ArrayList arrayList = ArrayList(size)
        Iterator it = this.flags.iterator()
        while (it.hasNext()) {
            arrayList.add(((AFlag) it.next()).toString())
        }
        Iterator it2 = this.attributes.values().iterator()
        while (it2.hasNext()) {
            arrayList.add(((IAttribute) it2.next()).toString())
        }
        return arrayList
    }

    fun isEmpty() {
        return this.flags.isEmpty() && this.attributes.isEmpty()
    }

    fun remove(AFlag aFlag) {
        this.flags.remove(aFlag)
    }

    fun remove(AType aType) {
        this.attributes.remove(aType)
    }

    fun remove(IAttribute iAttribute) {
        AType type = iAttribute.getType()
        if (((IAttribute) this.attributes.get(type)) == iAttribute) {
            this.attributes.remove(type)
        }
    }

    fun toString() {
        List attributeStrings = getAttributeStrings()
        return attributeStrings.isEmpty() ? "" : "A:{" + Utils.listToString(attributeStrings) + "}"
    }
}
