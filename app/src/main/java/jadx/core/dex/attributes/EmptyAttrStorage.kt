package jadx.core.dex.attributes

import jadx.core.dex.attributes.annotations.Annotation
import java.util.Collections
import java.util.List

class EmptyAttrStorage extends AttributeStorage {
    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Unit clear() {
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Boolean contains(AFlag aFlag) {
        return false
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Boolean contains(AType aType) {
        return false
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final IAttribute get(AType aType) {
        return null
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final List getAll(AType aType) {
        return Collections.emptyList()
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Annotation getAnnotation(String str) {
        return null
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final List getAttributeStrings() {
        return Collections.emptyList()
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Boolean isEmpty() {
        return true
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Unit remove(AFlag aFlag) {
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Unit remove(AType aType) {
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final Unit remove(IAttribute iAttribute) {
    }

    @Override // jadx.core.dex.attributes.AttributeStorage
    public final String toString() {
        return ""
    }
}
