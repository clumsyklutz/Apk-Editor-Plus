package org.jf.dexlib2.writer.builder

import com.google.common.base.Function
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Iterators
import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.Set
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.writer.AnnotationSetSection

class BuilderAnnotationSetPool extends BaseBuilderPool implements AnnotationSetSection<BuilderAnnotation, BuilderAnnotationSet> {
    public final ConcurrentMap<Set<? extends Annotation>, BuilderAnnotationSet> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.AnnotationSetSection
    public Collection<? extends BuilderAnnotation> getAnnotations(BuilderAnnotationSet builderAnnotationSet) {
        return builderAnnotationSet.annotations
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    fun getItemOffset(BuilderAnnotationSet builderAnnotationSet) {
        return builderAnnotationSet.offset
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    public Collection<? extends Map.Entry<? extends BuilderAnnotationSet, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderAnnotationSet>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderAnnotationSetPool.2
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderAnnotationSet builderAnnotationSet) {
                return builderAnnotationSet.offset
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderAnnotationSet builderAnnotationSet, Int i) {
                Int i2 = builderAnnotationSet.offset
                builderAnnotationSet.offset = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.NullableOffsetSection
    fun getNullableItemOffset(BuilderAnnotationSet builderAnnotationSet) {
        if (builderAnnotationSet == null) {
            return 0
        }
        return builderAnnotationSet.offset
    }

    fun internAnnotationSet(Set<? extends Annotation> set) {
        if (set == null) {
            return BuilderAnnotationSet.EMPTY
        }
        BuilderAnnotationSet builderAnnotationSet = this.internedItems.get(set)
        if (builderAnnotationSet != null) {
            return builderAnnotationSet
        }
        BuilderAnnotationSet builderAnnotationSet2 = BuilderAnnotationSet(ImmutableSet.copyOf(Iterators.transform(set.iterator(), new Function<Annotation, BuilderAnnotation>() { // from class: org.jf.dexlib2.writer.builder.BuilderAnnotationSetPool.1
            @Override // com.google.common.base.Function
            fun apply(Annotation annotation) {
                return ((BuilderAnnotationPool) BuilderAnnotationSetPool.this.dexBuilder.annotationSection).internAnnotation(annotation)
            }
        })))
        BuilderAnnotationSet builderAnnotationSetPutIfAbsent = this.internedItems.putIfAbsent(builderAnnotationSet2, builderAnnotationSet2)
        return builderAnnotationSetPutIfAbsent == null ? builderAnnotationSet2 : builderAnnotationSetPutIfAbsent
    }
}
