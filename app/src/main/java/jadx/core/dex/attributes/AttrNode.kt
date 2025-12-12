package jadx.core.dex.attributes

import jadx.core.dex.attributes.annotations.Annotation
import java.util.List

abstract class AttrNode implements IAttributeNode {
    private static val EMPTY_ATTR_STORAGE = EmptyAttrStorage()
    private AttributeStorage storage = EMPTY_ATTR_STORAGE

    private fun initStorage() {
        AttributeStorage attributeStorage = this.storage
        if (attributeStorage != EMPTY_ATTR_STORAGE) {
            return attributeStorage
        }
        AttributeStorage attributeStorage2 = AttributeStorage()
        this.storage = attributeStorage2
        return attributeStorage2
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun add(AFlag aFlag) {
        initStorage().add(aFlag)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun addAttr(AType aType, Object obj) {
        initStorage().add(aType, obj)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun addAttr(IAttribute iAttribute) {
        initStorage().add(iAttribute)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun clearAttributes() {
        this.storage.clear()
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun contains(AFlag aFlag) {
        return this.storage.contains(aFlag)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun contains(AType aType) {
        return this.storage.contains(aType)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun copyAttributesFrom(AttrNode attrNode) {
        AttributeStorage attributeStorage = attrNode.storage
        if (attributeStorage.isEmpty()) {
            return
        }
        initStorage().addAll(attributeStorage)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun get(AType aType) {
        return this.storage.get(aType)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun getAll(AType aType) {
        return this.storage.getAll(aType)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun getAnnotation(String str) {
        return this.storage.getAnnotation(str)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun getAttributesString() {
        return this.storage.toString()
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun getAttributesStringsList() {
        return this.storage.getAttributeStrings()
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun remove(AFlag aFlag) {
        this.storage.remove(aFlag)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun remove(AType aType) {
        this.storage.remove(aType)
    }

    @Override // jadx.core.dex.attributes.IAttributeNode
    fun removeAttr(IAttribute iAttribute) {
        this.storage.remove(iAttribute)
    }
}
