package org.jf.dexlib2.base

import java.util.Iterator
import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.MethodParameter
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.StringEncodedValue

abstract class BaseMethodParameter extends BaseTypeReference implements MethodParameter {
    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getSignature() {
        Annotation next
        ArrayEncodedValue arrayEncodedValue
        Iterator<? extends Annotation> it = getAnnotations().iterator()
        while (true) {
            if (!it.hasNext()) {
                next = null
                break
            }
            next = it.next()
            if (next.getType().equals("Ldalvik/annotation/Signature;")) {
                break
            }
        }
        if (next == null) {
            return null
        }
        Iterator<? extends AnnotationElement> it2 = next.getElements().iterator()
        while (true) {
            if (!it2.hasNext()) {
                arrayEncodedValue = null
                break
            }
            AnnotationElement next2 = it2.next()
            if (next2.getName().equals("value")) {
                EncodedValue value = next2.getValue()
                if (value.getValueType() != 28) {
                    return null
                }
                arrayEncodedValue = (ArrayEncodedValue) value
            }
        }
        if (arrayEncodedValue == null) {
            return null
        }
        StringBuilder sb = StringBuilder()
        for (EncodedValue encodedValue : arrayEncodedValue.getValue()) {
            if (encodedValue.getValueType() != 23) {
                return null
            }
            sb.append(((StringEncodedValue) encodedValue).getValue())
        }
        return sb.toString()
    }
}
