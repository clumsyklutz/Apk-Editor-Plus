package org.jf.dexlib2.writer.builder

import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseFieldReference
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.writer.builder.BuilderEncodedValues

class BuilderField extends BaseFieldReference implements Field {
    public final Int accessFlags
    public final BuilderAnnotationSet annotations
    public final BuilderFieldReference fieldReference
    public Set<HiddenApiRestriction> hiddenApiRestrictions
    public final BuilderEncodedValues.BuilderEncodedValue initialValue

    constructor(BuilderFieldReference builderFieldReference, Int i, BuilderEncodedValues.BuilderEncodedValue builderEncodedValue, BuilderAnnotationSet builderAnnotationSet, Set<HiddenApiRestriction> set) {
        this.fieldReference = builderFieldReference
        this.accessFlags = i
        this.initialValue = builderEncodedValue
        this.annotations = builderAnnotationSet
        this.hiddenApiRestrictions = set
    }

    @Override // org.jf.dexlib2.iface.Field
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.Field
    fun getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getDefiningClass() {
        return this.fieldReference.definingClass.getType()
    }

    @Override // org.jf.dexlib2.iface.Field
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        return this.hiddenApiRestrictions
    }

    @Override // org.jf.dexlib2.iface.Field
    public BuilderEncodedValues.BuilderEncodedValue getInitialValue() {
        return this.initialValue
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getName() {
        return this.fieldReference.name.getString()
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getType() {
        return this.fieldReference.fieldType.getType()
    }
}
