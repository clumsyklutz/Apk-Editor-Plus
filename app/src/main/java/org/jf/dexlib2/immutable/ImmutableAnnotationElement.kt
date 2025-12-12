package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableSet
import org.jf.dexlib2.base.BaseAnnotationElement
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory
import org.jf.util.ImmutableConverter

class ImmutableAnnotationElement extends BaseAnnotationElement {
    public static final ImmutableConverter<ImmutableAnnotationElement, AnnotationElement> CONVERTER = new ImmutableConverter<ImmutableAnnotationElement, AnnotationElement>() { // from class: org.jf.dexlib2.immutable.ImmutableAnnotationElement.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(AnnotationElement annotationElement) {
            return annotationElement is ImmutableAnnotationElement
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(AnnotationElement annotationElement) {
            return ImmutableAnnotationElement.of(annotationElement)
        }
    }
    public final String name
    public final ImmutableEncodedValue value

    constructor(String str, EncodedValue encodedValue) {
        this.name = str
        this.value = ImmutableEncodedValueFactory.of(encodedValue)
    }

    constructor(String str, ImmutableEncodedValue immutableEncodedValue) {
        this.name = str
        this.value = immutableEncodedValue
    }

    public static ImmutableSet<ImmutableAnnotationElement> immutableSetOf(Iterable<? extends AnnotationElement> iterable) {
        return CONVERTER.toSet(iterable)
    }

    fun of(AnnotationElement annotationElement) {
        return annotationElement is ImmutableAnnotationElement ? (ImmutableAnnotationElement) annotationElement : ImmutableAnnotationElement(annotationElement.getName(), annotationElement.getValue())
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    fun getValue() {
        return this.value
    }
}
