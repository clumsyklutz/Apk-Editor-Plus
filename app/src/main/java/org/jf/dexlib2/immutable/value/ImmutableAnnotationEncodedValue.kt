package org.jf.dexlib2.immutable.value

import com.google.common.collect.ImmutableSet
import java.util.Collection
import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.value.AnnotationEncodedValue
import org.jf.dexlib2.immutable.ImmutableAnnotationElement

class ImmutableAnnotationEncodedValue extends BaseAnnotationEncodedValue implements ImmutableEncodedValue {
    public final ImmutableSet<? extends ImmutableAnnotationElement> elements
    public final String type

    constructor(String str, Collection<? extends AnnotationElement> collection) {
        this.type = str
        this.elements = ImmutableAnnotationElement.immutableSetOf(collection)
    }

    fun of(AnnotationEncodedValue annotationEncodedValue) {
        return annotationEncodedValue is ImmutableAnnotationEncodedValue ? (ImmutableAnnotationEncodedValue) annotationEncodedValue : ImmutableAnnotationEncodedValue(annotationEncodedValue.getType(), annotationEncodedValue.getElements())
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
    public ImmutableSet<? extends ImmutableAnnotationElement> getElements() {
        return this.elements
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
    fun getType() {
        return this.type
    }
}
