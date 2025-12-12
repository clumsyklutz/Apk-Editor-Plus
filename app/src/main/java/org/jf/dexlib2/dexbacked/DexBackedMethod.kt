package org.jf.dexlib2.dexbacked

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import java.util.Collection
import java.util.EnumSet
import java.util.Iterator
import java.util.List
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.dexbacked.util.ParameterIterator
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.MethodParameter
import org.jf.util.AbstractForwardSequentialList

class DexBackedMethod extends BaseMethodReference implements Method {
    public final Int accessFlags
    public final DexBackedClassDef classDef
    public final Int codeOffset
    public final DexBackedDexFile dexFile
    public final Int hiddenApiRestrictions
    public final Int methodAnnotationSetOffset
    public Int methodIdItemOffset
    public final Int methodIndex
    public final Int parameterAnnotationSetListOffset
    public Int parametersOffset = -1
    public Int protoIdItemOffset

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, DexBackedClassDef dexBackedClassDef, Int i, AnnotationsDirectory.AnnotationIterator annotationIterator, AnnotationsDirectory.AnnotationIterator annotationIterator2, Int i2) {
        this.dexFile = dexBackedDexFile
        this.classDef = dexBackedClassDef
        dexReader.getOffset()
        Int largeUleb128 = dexReader.readLargeUleb128() + i
        this.methodIndex = largeUleb128
        this.accessFlags = dexReader.readSmallUleb128()
        this.codeOffset = dexReader.readSmallUleb128()
        this.hiddenApiRestrictions = i2
        this.methodAnnotationSetOffset = annotationIterator.seekTo(largeUleb128)
        this.parameterAnnotationSetListOffset = annotationIterator2.seekTo(largeUleb128)
    }

    fun skipMethods(DexReader dexReader, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            dexReader.skipUleb128()
            dexReader.skipUleb128()
            dexReader.skipUleb128()
        }
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.Method
    public Set<? extends Annotation> getAnnotations() {
        return AnnotationsDirectory.getAnnotations(this.dexFile, this.methodAnnotationSetOffset)
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.classDef.getType()
    }

    @Override // org.jf.dexlib2.iface.Method
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        Int i = this.hiddenApiRestrictions
        return i == 7 ? ImmutableSet.of() : EnumSet.copyOf((Collection) HiddenApiRestriction.getAllFlags(i))
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getImplementation() {
        Int i = this.codeOffset
        if (i <= 0) {
            return null
        }
        DexBackedDexFile dexBackedDexFile = this.dexFile
        return dexBackedDexFile.createMethodImplementation(dexBackedDexFile, this, i)
    }

    public final Int getMethodIdItemOffset() {
        if (this.methodIdItemOffset == 0) {
            this.methodIdItemOffset = this.dexFile.getMethodSection().getOffset(this.methodIndex)
        }
        return this.methodIdItemOffset
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getName() {
        return this.dexFile.getStringSection().get(this.dexFile.getBuffer().readSmallUint(getMethodIdItemOffset() + 4))
    }

    public List<? extends Set<? extends DexBackedAnnotation>> getParameterAnnotations() {
        return AnnotationsDirectory.getParameterAnnotations(this.dexFile, this.parameterAnnotationSetListOffset)
    }

    public Iterator<String> getParameterNames() {
        DexBackedMethodImplementation implementation = getImplementation()
        return implementation != null ? implementation.getParameterNames(null) : ImmutableSet.of().iterator()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public List<String> getParameterTypes() {
        Int parametersOffset = getParametersOffset()
        if (parametersOffset <= 0) {
            return ImmutableList.of()
        }
        val smallUint = this.dexFile.getDataBuffer().readSmallUint(parametersOffset + 0)
        val i = parametersOffset + 4
        return new FixedSizeList<String>() { // from class: org.jf.dexlib2.dexbacked.DexBackedMethod.2
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(Int i2) {
                return DexBackedMethod.this.dexFile.getTypeSection().get(DexBackedMethod.this.dexFile.getDataBuffer().readUshort(i + (i2 * 2)))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return smallUint
            }
        }
    }

    @Override // org.jf.dexlib2.iface.Method
    public List<? extends MethodParameter> getParameters() {
        if (getParametersOffset() <= 0) {
            return ImmutableList.of()
        }
        final List<String> parameterTypes = getParameterTypes()
        return new AbstractForwardSequentialList<MethodParameter>() { // from class: org.jf.dexlib2.dexbacked.DexBackedMethod.1
            @Override // org.jf.util.AbstractForwardSequentialList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
            public Iterator<MethodParameter> iterator() {
                return ParameterIterator(parameterTypes, DexBackedMethod.this.getParameterAnnotations(), DexBackedMethod.this.getParameterNames())
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return parameterTypes.size()
            }
        }
    }

    public final Int getParametersOffset() {
        if (this.parametersOffset == -1) {
            this.parametersOffset = this.dexFile.getBuffer().readSmallUint(getProtoIdItemOffset() + 8)
        }
        return this.parametersOffset
    }

    public final Int getProtoIdItemOffset() {
        if (this.protoIdItemOffset == 0) {
            this.protoIdItemOffset = this.dexFile.getProtoSection().getOffset(this.dexFile.getBuffer().readUshort(getMethodIdItemOffset() + 2))
        }
        return this.protoIdItemOffset
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readSmallUint(getProtoIdItemOffset() + 4))
    }
}
