package org.jf.dexlib2.writer.pool

import java.util.Collection
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.writer.AnnotationSection

class AnnotationPool extends BaseOffsetPool<Annotation> implements AnnotationSection<CharSequence, CharSequence, Annotation, AnnotationElement, EncodedValue> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getElementName(AnnotationElement annotationElement) {
        return annotationElement.getName()
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getElementValue(AnnotationElement annotationElement) {
        return annotationElement.getValue()
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    public Collection<? extends AnnotationElement> getElements(Annotation annotation) {
        return annotation.getElements()
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getType(Annotation annotation) {
        return annotation.getType()
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getVisibility(Annotation annotation) {
        return annotation.getVisibility()
    }

    fun intern(Annotation annotation) {
        if (((Integer) this.internedItems.put(annotation, 0)) == null) {
            ((TypePool) this.dexPool.typeSection).intern(annotation.getType())
            for (AnnotationElement annotationElement : annotation.getElements()) {
                ((StringPool) this.dexPool.stringSection).intern(annotationElement.getName())
                this.dexPool.internEncodedValue(annotationElement.getValue())
            }
        }
    }
}
