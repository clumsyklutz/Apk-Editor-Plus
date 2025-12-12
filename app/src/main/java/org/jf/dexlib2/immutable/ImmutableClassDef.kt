package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import com.google.common.collect.ImmutableSortedSet
import java.util.Collection
import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.Method
import org.jf.util.ImmutableConverter

class ImmutableClassDef extends BaseTypeReference implements ClassDef {
    public static final ImmutableConverter<ImmutableClassDef, ClassDef> CONVERTER = new ImmutableConverter<ImmutableClassDef, ClassDef>() { // from class: org.jf.dexlib2.immutable.ImmutableClassDef.3
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(ClassDef classDef) {
            return classDef is ImmutableClassDef
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(ClassDef classDef) {
            return ImmutableClassDef.of(classDef)
        }
    }
    public final Int accessFlags
    public final ImmutableSet<? extends ImmutableAnnotation> annotations
    public final ImmutableSortedSet<? extends ImmutableMethod> directMethods
    public final ImmutableSortedSet<? extends ImmutableField> instanceFields
    public final ImmutableList<String> interfaces
    public final String sourceFile
    public final ImmutableSortedSet<? extends ImmutableField> staticFields
    public final String superclass
    public final String type
    public final ImmutableSortedSet<? extends ImmutableMethod> virtualMethods

    constructor(String str, Int i, String str2, Collection<String> collection, String str3, Collection<? extends Annotation> collection2, Iterable<? extends Field> iterable, Iterable<? extends Field> iterable2, Iterable<? extends Method> iterable3, Iterable<? extends Method> iterable4) {
        this.type = str
        this.accessFlags = i
        this.superclass = str2
        this.interfaces = collection == null ? ImmutableList.of() : ImmutableList.copyOf((Collection) collection)
        this.sourceFile = str3
        this.annotations = ImmutableAnnotation.immutableSetOf(collection2)
        this.staticFields = ImmutableField.immutableSetOf(iterable)
        this.instanceFields = ImmutableField.immutableSetOf(iterable2)
        this.directMethods = ImmutableMethod.immutableSetOf(iterable3)
        this.virtualMethods = ImmutableMethod.immutableSetOf(iterable4)
    }

    public static ImmutableSet<ImmutableClassDef> immutableSetOf(Iterable<? extends ClassDef> iterable) {
        return CONVERTER.toSet(iterable)
    }

    fun of(ClassDef classDef) {
        return classDef is ImmutableClassDef ? (ImmutableClassDef) classDef : ImmutableClassDef(classDef.getType(), classDef.getAccessFlags(), classDef.getSuperclass(), classDef.getInterfaces(), classDef.getSourceFile(), classDef.getAnnotations(), classDef.getStaticFields(), classDef.getInstanceFields(), classDef.getDirectMethods(), classDef.getVirtualMethods())
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public ImmutableSet<? extends ImmutableAnnotation> getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public ImmutableSet<? extends ImmutableMethod> getDirectMethods() {
        return this.directMethods
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public ImmutableSet<? extends ImmutableField> getInstanceFields() {
        return this.instanceFields
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public ImmutableList<String> getInterfaces() {
        return this.interfaces
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSourceFile() {
        return this.sourceFile
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public ImmutableSet<? extends ImmutableField> getStaticFields() {
        return this.staticFields
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSuperclass() {
        return this.superclass
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.type
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public ImmutableSet<? extends ImmutableMethod> getVirtualMethods() {
        return this.virtualMethods
    }
}
