package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.AnnotationEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.util.CollectionUtils

abstract class BaseAnnotationEncodedValue implements AnnotationEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        if (iCompare != 0) {
            return iCompare
        }
        AnnotationEncodedValue annotationEncodedValue = (AnnotationEncodedValue) encodedValue
        Int iCompareTo = getType().compareTo(annotationEncodedValue.getType())
        return iCompareTo != 0 ? iCompareTo : CollectionUtils.compareAsSet(getElements(), annotationEncodedValue.getElements())
    }

    fun equals(Object obj) {
        if (!(obj is AnnotationEncodedValue)) {
            return false
        }
        AnnotationEncodedValue annotationEncodedValue = (AnnotationEncodedValue) obj
        return getType().equals(annotationEncodedValue.getType()) && getElements().equals(annotationEncodedValue.getElements())
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 29
    }

    fun hashCode() {
        return (getType().hashCode() * 31) + getElements().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}
