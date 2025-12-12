package org.jf.dexlib2.writer.builder

import com.google.common.base.Functions
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSortedSet
import com.google.common.collect.Iterables
import com.google.common.collect.Iterators
import com.google.common.collect.Lists
import com.google.common.collect.Ordering
import java.util.AbstractCollection
import java.util.Collection
import java.util.Iterator
import java.util.List
import java.util.SortedSet
import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.util.MethodUtil
import org.jf.dexlib2.writer.builder.BuilderEncodedValues

class BuilderClassDef extends BaseTypeReference implements ClassDef {
    public final Int accessFlags
    public final BuilderAnnotationSet annotations
    public final SortedSet<BuilderMethod> directMethods
    public final SortedSet<BuilderField> instanceFields
    public final BuilderTypeList interfaces
    public final BuilderStringReference sourceFile
    public final SortedSet<BuilderField> staticFields
    public final BuilderEncodedValues.BuilderArrayEncodedValue staticInitializers
    public final BuilderTypeReference superclass
    public final BuilderTypeReference type
    public final SortedSet<BuilderMethod> virtualMethods
    public Int classDefIndex = -1
    public Int annotationDirectoryOffset = 0

    constructor(BuilderTypeReference builderTypeReference, Int i, BuilderTypeReference builderTypeReference2, BuilderTypeList builderTypeList, BuilderStringReference builderStringReference, BuilderAnnotationSet builderAnnotationSet, SortedSet<BuilderField> sortedSet, SortedSet<BuilderField> sortedSet2, Iterable<? extends BuilderMethod> iterable, BuilderEncodedValues.BuilderArrayEncodedValue builderArrayEncodedValue) {
        iterable = iterable == null ? ImmutableList.of() : iterable
        sortedSet = sortedSet == null ? ImmutableSortedSet.of() : sortedSet
        sortedSet2 = sortedSet2 == null ? ImmutableSortedSet.of() : sortedSet2
        this.type = builderTypeReference
        this.accessFlags = i
        this.superclass = builderTypeReference2
        this.interfaces = builderTypeList
        this.sourceFile = builderStringReference
        this.annotations = builderAnnotationSet
        this.staticFields = sortedSet
        this.instanceFields = sortedSet2
        this.directMethods = ImmutableSortedSet.copyOf(Iterables.filter(iterable, MethodUtil.METHOD_IS_DIRECT))
        this.virtualMethods = ImmutableSortedSet.copyOf(Iterables.filter(iterable, MethodUtil.METHOD_IS_VIRTUAL))
        this.staticInitializers = builderArrayEncodedValue
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<BuilderMethod> getDirectMethods() {
        return this.directMethods
    }

    public Collection<BuilderField> getFields() {
        return new AbstractCollection<BuilderField>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassDef.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<BuilderField> iterator() {
                return Iterators.mergeSorted(ImmutableList.of(BuilderClassDef.this.staticFields.iterator(), BuilderClassDef.this.instanceFields.iterator()), Ordering.natural())
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            fun size() {
                return BuilderClassDef.this.staticFields.size() + BuilderClassDef.this.instanceFields.size()
            }
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<BuilderField> getInstanceFields() {
        return this.instanceFields
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        return Lists.transform(this.interfaces, Functions.toStringFunction())
    }

    public Collection<BuilderMethod> getMethods() {
        return new AbstractCollection<BuilderMethod>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassDef.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<BuilderMethod> iterator() {
                return Iterators.mergeSorted(ImmutableList.of(BuilderClassDef.this.directMethods.iterator(), BuilderClassDef.this.virtualMethods.iterator()), Ordering.natural())
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            fun size() {
                return BuilderClassDef.this.directMethods.size() + BuilderClassDef.this.virtualMethods.size()
            }
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSourceFile() {
        BuilderStringReference builderStringReference = this.sourceFile
        if (builderStringReference == null) {
            return null
        }
        return builderStringReference.getString()
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<BuilderField> getStaticFields() {
        return this.staticFields
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSuperclass() {
        BuilderTypeReference builderTypeReference = this.superclass
        if (builderTypeReference == null) {
            return null
        }
        return builderTypeReference.getType()
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.type.getType()
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<BuilderMethod> getVirtualMethods() {
        return this.virtualMethods
    }
}
