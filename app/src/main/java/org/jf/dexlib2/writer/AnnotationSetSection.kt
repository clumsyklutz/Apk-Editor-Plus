package org.jf.dexlib2.writer

import java.util.Collection
import org.jf.dexlib2.iface.Annotation

public interface AnnotationSetSection<AnnotationKey extends Annotation, AnnotationSetKey> extends NullableOffsetSection<AnnotationSetKey> {
    Collection<? extends AnnotationKey> getAnnotations(AnnotationSetKey annotationsetkey)
}
