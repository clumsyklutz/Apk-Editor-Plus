package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableSet
import com.google.common.collect.ImmutableSortedSet
import com.google.common.collect.Ordering
import java.util.Collection
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseFieldReference
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory
import org.jf.util.ImmutableConverter

class ImmutableField extends BaseFieldReference implements Field {
    public static final ImmutableConverter<ImmutableField, Field> CONVERTER = new ImmutableConverter<ImmutableField, Field>() { // from class: org.jf.dexlib2.immutable.ImmutableField.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(Field field) {
            return field is ImmutableField
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(Field field) {
            return ImmutableField.of(field)
        }
    }
    public final Int accessFlags
    public final ImmutableSet<? extends ImmutableAnnotation> annotations
    public final String definingClass
    public final ImmutableSet<HiddenApiRestriction> hiddenApiRestrictions
    public final ImmutableEncodedValue initialValue
    public final String name
    public final String type

    constructor(String str, String str2, String str3, Int i, EncodedValue encodedValue, Collection<? extends Annotation> collection, Set<HiddenApiRestriction> set) {
        this.definingClass = str
        this.name = str2
        this.type = str3
        this.accessFlags = i
        this.initialValue = ImmutableEncodedValueFactory.ofNullable(encodedValue)
        this.annotations = ImmutableAnnotation.immutableSetOf(collection)
        this.hiddenApiRestrictions = set == null ? ImmutableSet.of() : ImmutableSet.copyOf((Collection) set)
    }

    public static ImmutableSortedSet<ImmutableField> immutableSetOf(Iterable<? extends Field> iterable) {
        return CONVERTER.toSortedSet(Ordering.natural(), iterable)
    }

    fun of(Field field) {
        return field is ImmutableField ? (ImmutableField) field : ImmutableField(field.getDefiningClass(), field.getName(), field.getType(), field.getAccessFlags(), field.getInitialValue(), field.getAnnotations(), field.getHiddenApiRestrictions())
    }

    @Override // org.jf.dexlib2.iface.Field
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.Field
    public ImmutableSet<? extends ImmutableAnnotation> getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getDefiningClass() {
        return this.definingClass
    }

    @Override // org.jf.dexlib2.iface.Field
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        return this.hiddenApiRestrictions
    }

    @Override // org.jf.dexlib2.iface.Field
    fun getInitialValue() {
        return this.initialValue
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getType() {
        return this.type
    }
}
