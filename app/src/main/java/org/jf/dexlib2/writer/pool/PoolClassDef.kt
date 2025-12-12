package org.jf.dexlib2.writer.pool

import com.google.common.base.Function
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSortedSet
import com.google.common.collect.Iterables
import com.google.common.collect.Iterators
import com.google.common.collect.Ordering
import java.util.AbstractCollection
import java.util.Collection
import java.util.Iterator
import java.util.List
import java.util.Set
import java.util.SortedSet
import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.writer.pool.TypeListPool

class PoolClassDef extends BaseTypeReference implements ClassDef {
    public final ClassDef classDef
    public final ImmutableSortedSet<PoolMethod> directMethods
    public final ImmutableSortedSet<Field> instanceFields
    public final TypeListPool.Key<List<String>> interfaces
    public final ImmutableSortedSet<Field> staticFields
    public final ImmutableSortedSet<PoolMethod> virtualMethods
    public Int classDefIndex = -1
    public Int annotationDirectoryOffset = 0

    constructor(ClassDef classDef) {
        this.classDef = classDef
        this.interfaces = new TypeListPool.Key<>(ImmutableList.copyOf((Collection) classDef.getInterfaces()))
        this.staticFields = ImmutableSortedSet.copyOf(classDef.getStaticFields())
        this.instanceFields = ImmutableSortedSet.copyOf(classDef.getInstanceFields())
        Iterable<? extends Method> directMethods = classDef.getDirectMethods()
        Function<Method, PoolMethod> function = PoolMethod.TRANSFORM
        this.directMethods = ImmutableSortedSet.copyOf(Iterables.transform(directMethods, function))
        this.virtualMethods = ImmutableSortedSet.copyOf(Iterables.transform(classDef.getVirtualMethods(), function))
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getAccessFlags() {
        return this.classDef.getAccessFlags()
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends Annotation> getAnnotations() {
        return this.classDef.getAnnotations()
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<PoolMethod> getDirectMethods() {
        return this.directMethods
    }

    public Collection<Field> getFields() {
        return new AbstractCollection<Field>() { // from class: org.jf.dexlib2.writer.pool.PoolClassDef.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<Field> iterator() {
                return Iterators.mergeSorted(ImmutableList.of(PoolClassDef.this.staticFields.iterator(), PoolClassDef.this.instanceFields.iterator()), Ordering.natural())
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            fun size() {
                return PoolClassDef.this.staticFields.size() + PoolClassDef.this.instanceFields.size()
            }
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<Field> getInstanceFields() {
        return this.instanceFields
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        return (List) this.interfaces.types
    }

    public Collection<PoolMethod> getMethods() {
        return new AbstractCollection<PoolMethod>() { // from class: org.jf.dexlib2.writer.pool.PoolClassDef.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<PoolMethod> iterator() {
                return Iterators.mergeSorted(ImmutableList.of(PoolClassDef.this.directMethods.iterator(), PoolClassDef.this.virtualMethods.iterator()), Ordering.natural())
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            fun size() {
                return PoolClassDef.this.directMethods.size() + PoolClassDef.this.virtualMethods.size()
            }
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSourceFile() {
        return this.classDef.getSourceFile()
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<Field> getStaticFields() {
        return this.staticFields
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSuperclass() {
        return this.classDef.getSuperclass()
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.classDef.getType()
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public SortedSet<PoolMethod> getVirtualMethods() {
        return this.virtualMethods
    }
}
