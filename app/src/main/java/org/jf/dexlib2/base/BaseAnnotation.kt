package org.jf.dexlib2.base

import com.google.common.primitives.Ints
import java.util.Comparator
import org.jf.dexlib2.iface.Annotation
import org.jf.util.CollectionUtils

abstract class BaseAnnotation implements Annotation {
    public static final Comparator<? super Annotation> BY_TYPE = new Comparator<Annotation>() { // from class: org.jf.dexlib2.base.BaseAnnotation.1
        @Override // java.util.Comparator
        fun compare(Annotation annotation, Annotation annotation2) {
            return annotation.getType().compareTo(annotation2.getType())
        }
    }

    @Override // java.lang.Comparable
    fun compareTo(Annotation annotation) {
        Int iCompare = Ints.compare(getVisibility(), annotation.getVisibility())
        if (iCompare != 0) {
            return iCompare
        }
        Int iCompareTo = getType().compareTo(annotation.getType())
        return iCompareTo != 0 ? iCompareTo : CollectionUtils.compareAsSet(getElements(), annotation.getElements())
    }

    fun equals(Object obj) {
        if (!(obj is Annotation)) {
            return false
        }
        Annotation annotation = (Annotation) obj
        return getVisibility() == annotation.getVisibility() && getType().equals(annotation.getType()) && getElements().equals(annotation.getElements())
    }

    fun hashCode() {
        return (((getVisibility() * 31) + getType().hashCode()) * 31) + getElements().hashCode()
    }
}
