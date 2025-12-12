package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.writer.AnnotationSection
import org.jf.dexlib2.writer.builder.BuilderEncodedValues

class BuilderAnnotationPool extends BaseBuilderPool implements AnnotationSection<BuilderStringReference, BuilderTypeReference, BuilderAnnotation, BuilderAnnotationElement, BuilderEncodedValues.BuilderEncodedValue> {
    public final ConcurrentMap<Annotation, BuilderAnnotation> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getElementName(BuilderAnnotationElement builderAnnotationElement) {
        return builderAnnotationElement.name
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    public BuilderEncodedValues.BuilderEncodedValue getElementValue(BuilderAnnotationElement builderAnnotationElement) {
        return builderAnnotationElement.value
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    public Collection<? extends BuilderAnnotationElement> getElements(BuilderAnnotation builderAnnotation) {
        return builderAnnotation.elements
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    fun getItemOffset(BuilderAnnotation builderAnnotation) {
        return builderAnnotation.offset
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    public Collection<? extends Map.Entry<? extends BuilderAnnotation, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderAnnotation>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderAnnotationPool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderAnnotation builderAnnotation) {
                return builderAnnotation.offset
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderAnnotation builderAnnotation, Int i) {
                Int i2 = builderAnnotation.offset
                builderAnnotation.offset = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getType(BuilderAnnotation builderAnnotation) {
        return builderAnnotation.type
    }

    @Override // org.jf.dexlib2.writer.AnnotationSection
    fun getVisibility(BuilderAnnotation builderAnnotation) {
        return builderAnnotation.visibility
    }

    fun internAnnotation(Annotation annotation) {
        BuilderAnnotation builderAnnotation = this.internedItems.get(annotation)
        if (builderAnnotation != null) {
            return builderAnnotation
        }
        BuilderAnnotation builderAnnotation2 = BuilderAnnotation(annotation.getVisibility(), ((BuilderTypePool) this.dexBuilder.typeSection).internType(annotation.getType()), this.dexBuilder.internAnnotationElements(annotation.getElements()))
        BuilderAnnotation builderAnnotationPutIfAbsent = this.internedItems.putIfAbsent(builderAnnotation2, builderAnnotation2)
        return builderAnnotationPutIfAbsent == null ? builderAnnotation2 : builderAnnotationPutIfAbsent
    }
}
