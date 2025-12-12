package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableSet
import java.util.Collection
import org.jf.dexlib2.base.BaseAnnotation
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.util.ImmutableConverter

class ImmutableAnnotation extends BaseAnnotation {
    public static final ImmutableConverter<ImmutableAnnotation, Annotation> CONVERTER = new ImmutableConverter<ImmutableAnnotation, Annotation>() { // from class: org.jf.dexlib2.immutable.ImmutableAnnotation.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(Annotation annotation) {
            return annotation is ImmutableAnnotation
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(Annotation annotation) {
            return ImmutableAnnotation.of(annotation)
        }
    }
    public final ImmutableSet<? extends ImmutableAnnotationElement> elements
    public final String type
    public final Int visibility

    constructor(Int i, String str, Collection<? extends AnnotationElement> collection) {
        this.visibility = i
        this.type = str
        this.elements = ImmutableAnnotationElement.immutableSetOf(collection)
    }

    public static ImmutableSet<ImmutableAnnotation> immutableSetOf(Iterable<? extends Annotation> iterable) {
        return CONVERTER.toSet(iterable)
    }

    fun of(Annotation annotation) {
        return annotation is ImmutableAnnotation ? (ImmutableAnnotation) annotation : ImmutableAnnotation(annotation.getVisibility(), annotation.getType(), annotation.getElements())
    }

    @Override // org.jf.dexlib2.iface.Annotation
    public ImmutableSet<? extends ImmutableAnnotationElement> getElements() {
        return this.elements
    }

    @Override // org.jf.dexlib2.iface.Annotation
    fun getType() {
        return this.type
    }

    @Override // org.jf.dexlib2.iface.Annotation
    fun getVisibility() {
        return this.visibility
    }
}
