package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.BaseAnnotationElement
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.writer.builder.BuilderEncodedValues

class BuilderAnnotationElement extends BaseAnnotationElement {
    public final BuilderStringReference name
    public final BuilderEncodedValues.BuilderEncodedValue value

    constructor(BuilderStringReference builderStringReference, BuilderEncodedValues.BuilderEncodedValue builderEncodedValue) {
        this.name = builderStringReference
        this.value = builderEncodedValue
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    fun getName() {
        return this.name.getString()
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    fun getValue() {
        return this.value
    }
}
