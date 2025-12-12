package org.jf.dexlib2.writer.pool

import java.util.Collection
import java.util.Iterator
import java.util.Set
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.writer.AnnotationSetSection

class AnnotationSetPool extends BaseNullableOffsetPool<Set<? extends Annotation>> implements AnnotationSetSection<Annotation, Set<? extends Annotation>> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.AnnotationSetSection
    public Collection<? extends Annotation> getAnnotations(Set<? extends Annotation> set) {
        return set
    }

    fun intern(Set<? extends Annotation> set) {
        if (set.size() <= 0 || ((Integer) this.internedItems.put(set, 0)) != null) {
            return
        }
        Iterator<? extends Annotation> it = set.iterator()
        while (it.hasNext()) {
            ((AnnotationPool) this.dexPool.annotationSection).intern(it.next())
        }
    }
}
