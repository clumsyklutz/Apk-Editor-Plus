package org.jf.dexlib2.base

import java.util.Comparator
import org.jf.dexlib2.iface.AnnotationElement

abstract class BaseAnnotationElement implements AnnotationElement {
    public static final Comparator<AnnotationElement> BY_NAME = new Comparator<AnnotationElement>() { // from class: org.jf.dexlib2.base.BaseAnnotationElement.1
        @Override // java.util.Comparator
        fun compare(AnnotationElement annotationElement, AnnotationElement annotationElement2) {
            return annotationElement.getName().compareTo(annotationElement2.getName())
        }
    }

    @Override // java.lang.Comparable
    fun compareTo(AnnotationElement annotationElement) {
        Int iCompareTo = getName().compareTo(annotationElement.getName())
        return iCompareTo != 0 ? iCompareTo : getValue().compareTo(annotationElement.getValue())
    }

    fun equals(Object obj) {
        if (obj == null || !(obj is AnnotationElement)) {
            return false
        }
        AnnotationElement annotationElement = (AnnotationElement) obj
        return getName().equals(annotationElement.getName()) && getValue().equals(annotationElement.getValue())
    }

    fun hashCode() {
        return (getName().hashCode() * 31) + getValue().hashCode()
    }
}
