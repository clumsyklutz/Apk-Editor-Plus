package org.jf.dexlib2.writer.builder

import java.util.Set
import org.jf.dexlib2.base.BaseAnnotation

class BuilderAnnotation extends BaseAnnotation {
    public final Set<? extends BuilderAnnotationElement> elements
    public Int offset = 0
    public final BuilderTypeReference type
    public Int visibility

    constructor(Int i, BuilderTypeReference builderTypeReference, Set<? extends BuilderAnnotationElement> set) {
        this.visibility = i
        this.type = builderTypeReference
        this.elements = set
    }

    @Override // org.jf.dexlib2.iface.Annotation
    public Set<? extends BuilderAnnotationElement> getElements() {
        return this.elements
    }

    @Override // org.jf.dexlib2.iface.Annotation
    fun getType() {
        return this.type.getType()
    }

    @Override // org.jf.dexlib2.iface.Annotation
    fun getVisibility() {
        return this.visibility
    }
}
