package org.jf.dexlib2.writer.builder

import com.google.common.base.Function
import com.google.common.base.Predicate
import com.google.common.collect.FluentIterable
import com.google.common.collect.ImmutableList
import com.google.common.collect.Iterables
import com.google.common.collect.Maps
import com.google.common.collect.Ordering
import java.io.IOException
import java.util.Collection
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.builder.MutableMethodImplementation
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.TryBlock
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.debug.EndLocal
import org.jf.dexlib2.iface.debug.LineNumber
import org.jf.dexlib2.iface.debug.RestartLocal
import org.jf.dexlib2.iface.debug.SetSourceFile
import org.jf.dexlib2.iface.debug.StartLocal
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.util.EncodedValueUtils
import org.jf.dexlib2.writer.ClassSection
import org.jf.dexlib2.writer.DebugWriter
import org.jf.dexlib2.writer.builder.BuilderEncodedValues
import org.jf.util.AbstractForwardSequentialList
import org.jf.util.ExceptionWithContext

class BuilderClassPool extends BaseBuilderPool implements ClassSection<BuilderStringReference, BuilderTypeReference, BuilderTypeList, BuilderClassDef, BuilderField, BuilderMethod, BuilderAnnotationSet, BuilderEncodedValues.BuilderArrayEncodedValue> {
    public static final Predicate<BuilderMethodParameter> HAS_PARAMETER_ANNOTATIONS
    public static final Function<BuilderMethodParameter, BuilderAnnotationSet> PARAMETER_ANNOTATIONS
    public final ConcurrentMap<String, BuilderClassDef> internedItems
    public ImmutableList<BuilderClassDef> sortedClasses

    static {
        new Predicate<Field>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.2
            @Override // com.google.common.base.Predicate
            fun apply(Field field) {
                EncodedValue initialValue = field.getInitialValue()
                return (initialValue == null || EncodedValueUtils.isDefaultValue(initialValue)) ? false : true
            }
        }
        new Function<BuilderField, BuilderEncodedValues.BuilderEncodedValue>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.3
            @Override // com.google.common.base.Function
            public BuilderEncodedValues.BuilderEncodedValue apply(BuilderField builderField) {
                BuilderEncodedValues.BuilderEncodedValue initialValue = builderField.getInitialValue()
                return initialValue == null ? BuilderEncodedValues.defaultValueForType(builderField.getType()) : initialValue
            }
        }
        HAS_PARAMETER_ANNOTATIONS = new Predicate<BuilderMethodParameter>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.4
            @Override // com.google.common.base.Predicate
            fun apply(BuilderMethodParameter builderMethodParameter) {
                return builderMethodParameter.getAnnotations().size() > 0
            }
        }
        PARAMETER_ANNOTATIONS = new Function<BuilderMethodParameter, BuilderAnnotationSet>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.5
            @Override // com.google.common.base.Function
            fun apply(BuilderMethodParameter builderMethodParameter) {
                return builderMethodParameter.getAnnotations()
            }
        }
    }

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
        this.sortedClasses = null
    }

    public final BuilderStringReference checkStringReference(StringReference stringReference) {
        if (stringReference == null) {
            return null
        }
        try {
            return (BuilderStringReference) stringReference
        } catch (ClassCastException unused) {
            throw IllegalStateException("Only StringReference instances returned by DexBuilder.internStringReference or DexBuilder.internNullableStringReference may be used.")
        }
    }

    public final BuilderTypeReference checkTypeReference(TypeReference typeReference) {
        if (typeReference == null) {
            return null
        }
        try {
            return (BuilderTypeReference) typeReference
        } catch (ClassCastException unused) {
            throw IllegalStateException("Only TypeReference instances returned by DexBuilder.internTypeReference or DexBuilder.internNullableTypeReference may be used.")
        }
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getAccessFlags(BuilderClassDef builderClassDef) {
        return builderClassDef.accessFlags
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getAnnotationDirectoryOffset(BuilderClassDef builderClassDef) {
        return builderClassDef.annotationDirectoryOffset
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getAnnotationSetRefListOffset(BuilderMethod builderMethod) {
        return builderMethod.annotationSetRefListOffset
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getClassAnnotations(BuilderClassDef builderClassDef) {
        if (builderClassDef.annotations.isEmpty()) {
            return null
        }
        return builderClassDef.annotations
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Map.Entry<? extends BuilderClassDef, Integer> getClassEntryByType(BuilderTypeReference builderTypeReference) {
        final BuilderClassDef builderClassDef
        if (builderTypeReference == null || (builderClassDef = this.internedItems.get(builderTypeReference.getType())) == null) {
            return null
        }
        return new Map.Entry<BuilderClassDef, Integer>(this) { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.1
            @Override // java.util.Map.Entry
            fun getKey() {
                return builderClassDef
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Map.Entry
            fun getValue() {
                return Integer.valueOf(builderClassDef.classDefIndex)
            }

            @Override // java.util.Map.Entry
            fun setValue(Integer num) {
                BuilderClassDef builderClassDef2 = builderClassDef
                Int iIntValue = num.intValue()
                builderClassDef2.classDefIndex = iIntValue
                return Integer.valueOf(iIntValue)
            }
        }
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getCodeItemOffset(BuilderMethod builderMethod) {
        return builderMethod.codeItemOffset
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Iterable<? extends DebugItem> getDebugItems(BuilderMethod builderMethod) {
        MethodImplementation implementation = builderMethod.getImplementation()
        if (implementation == null) {
            return null
        }
        return implementation.getDebugItems()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getExceptionType(ExceptionHandler exceptionHandler) {
        return checkTypeReference(exceptionHandler.getExceptionTypeReference())
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getFieldAccessFlags(BuilderField builderField) {
        return builderField.accessFlags
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getFieldAnnotations(BuilderField builderField) {
        if (builderField.annotations.isEmpty()) {
            return null
        }
        return builderField.annotations
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Set<HiddenApiRestriction> getFieldHiddenApiRestrictions(BuilderField builderField) {
        return builderField.getHiddenApiRestrictions()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Iterable<? extends Instruction> getInstructions(BuilderMethod builderMethod) {
        MethodImplementation implementation = builderMethod.getImplementation()
        if (implementation == null) {
            return null
        }
        return implementation.getInstructions()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getInterfaces(BuilderClassDef builderClassDef) {
        return builderClassDef.interfaces
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderClassDef, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderClassDef>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.8
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderClassDef builderClassDef) {
                return builderClassDef.classDefIndex
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderClassDef builderClassDef, Int i) {
                Int i2 = builderClassDef.classDefIndex
                builderClassDef.classDefIndex = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getMethodAccessFlags(BuilderMethod builderMethod) {
        return builderMethod.accessFlags
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getMethodAnnotations(BuilderMethod builderMethod) {
        if (builderMethod.annotations.isEmpty()) {
            return null
        }
        return builderMethod.annotations
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Set<HiddenApiRestriction> getMethodHiddenApiRestrictions(BuilderMethod builderMethod) {
        return builderMethod.getHiddenApiRestrictions()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public List<? extends BuilderAnnotationSet> getParameterAnnotations(BuilderMethod builderMethod) {
        final List<? extends BuilderMethodParameter> parameters = builderMethod.getParameters()
        if (Iterables.any(parameters, HAS_PARAMETER_ANNOTATIONS)) {
            return new AbstractForwardSequentialList<BuilderAnnotationSet>(this) { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.6
                @Override // org.jf.util.AbstractForwardSequentialList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
                public Iterator<BuilderAnnotationSet> iterator() {
                    return FluentIterable.from(parameters).transform(BuilderClassPool.PARAMETER_ANNOTATIONS).iterator()
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                fun size() {
                    return parameters.size()
                }
            }
        }
        return null
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Iterable<? extends BuilderStringReference> getParameterNames(BuilderMethod builderMethod) {
        return Iterables.transform(builderMethod.getParameters(), new Function<BuilderMethodParameter, BuilderStringReference>(this) { // from class: org.jf.dexlib2.writer.builder.BuilderClassPool.7
            @Override // com.google.common.base.Function
            fun apply(BuilderMethodParameter builderMethodParameter) {
                return builderMethodParameter.name
            }
        })
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getRegisterCount(BuilderMethod builderMethod) {
        MethodImplementation implementation = builderMethod.getImplementation()
        if (implementation == null) {
            return 0
        }
        return implementation.getRegisterCount()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderClassDef> getSortedClasses() {
        if (this.sortedClasses == null) {
            this.sortedClasses = Ordering.natural().immutableSortedCopy(this.internedItems.values())
        }
        return this.sortedClasses
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderMethod> getSortedDirectMethods(BuilderClassDef builderClassDef) {
        return builderClassDef.getDirectMethods()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderField> getSortedFields(BuilderClassDef builderClassDef) {
        return builderClassDef.getFields()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderField> getSortedInstanceFields(BuilderClassDef builderClassDef) {
        return builderClassDef.getInstanceFields()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderMethod> getSortedMethods(BuilderClassDef builderClassDef) {
        return builderClassDef.getMethods()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderField> getSortedStaticFields(BuilderClassDef builderClassDef) {
        return builderClassDef.getStaticFields()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public Collection<? extends BuilderMethod> getSortedVirtualMethods(BuilderClassDef builderClassDef) {
        return builderClassDef.getVirtualMethods()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getSourceFile(BuilderClassDef builderClassDef) {
        return builderClassDef.sourceFile
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public BuilderEncodedValues.BuilderArrayEncodedValue getStaticInitializers(BuilderClassDef builderClassDef) {
        return builderClassDef.staticInitializers
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getSuperclass(BuilderClassDef builderClassDef) {
        return builderClassDef.superclass
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    public List<? extends TryBlock<? extends ExceptionHandler>> getTryBlocks(BuilderMethod builderMethod) {
        MethodImplementation implementation = builderMethod.getImplementation()
        return implementation == null ? ImmutableList.of() : implementation.getTryBlocks()
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun getType(BuilderClassDef builderClassDef) {
        return builderClassDef.type
    }

    fun internClass(BuilderClassDef builderClassDef) {
        if (this.internedItems.put(builderClassDef.getType(), builderClassDef) == null) {
            return builderClassDef
        }
        throw ExceptionWithContext("Class %s has already been interned", builderClassDef.getType())
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun makeMutableMethodImplementation(BuilderMethod builderMethod) {
        MethodImplementation implementation = builderMethod.getImplementation()
        return implementation is MutableMethodImplementation ? (MutableMethodImplementation) implementation : MutableMethodImplementation(implementation)
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun setAnnotationDirectoryOffset(BuilderClassDef builderClassDef, Int i) {
        builderClassDef.annotationDirectoryOffset = i
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun setAnnotationSetRefListOffset(BuilderMethod builderMethod, Int i) {
        builderMethod.annotationSetRefListOffset = i
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun setCodeItemOffset(BuilderMethod builderMethod, Int i) {
        builderMethod.codeItemOffset = i
    }

    @Override // org.jf.dexlib2.writer.ClassSection
    fun writeDebugItem(DebugWriter<BuilderStringReference, BuilderTypeReference> debugWriter, DebugItem debugItem) throws IOException {
        switch (debugItem.getDebugItemType()) {
            case 3:
                StartLocal startLocal = (StartLocal) debugItem
                debugWriter.writeStartLocal(startLocal.getCodeAddress(), startLocal.getRegister(), checkStringReference(startLocal.getNameReference()), checkTypeReference(startLocal.getTypeReference()), checkStringReference(startLocal.getSignatureReference()))
                return
            case 4:
            default:
                throw ExceptionWithContext("Unexpected debug item type: %d", Integer.valueOf(debugItem.getDebugItemType()))
            case 5:
                EndLocal endLocal = (EndLocal) debugItem
                debugWriter.writeEndLocal(endLocal.getCodeAddress(), endLocal.getRegister())
                return
            case 6:
                RestartLocal restartLocal = (RestartLocal) debugItem
                debugWriter.writeRestartLocal(restartLocal.getCodeAddress(), restartLocal.getRegister())
                return
            case 7:
                debugWriter.writePrologueEnd(debugItem.getCodeAddress())
                return
            case 8:
                debugWriter.writeEpilogueBegin(debugItem.getCodeAddress())
                return
            case 9:
                SetSourceFile setSourceFile = (SetSourceFile) debugItem
                debugWriter.writeSetSourceFile(setSourceFile.getCodeAddress(), checkStringReference(setSourceFile.getSourceFileReference()))
                return
            case 10:
                LineNumber lineNumber = (LineNumber) debugItem
                debugWriter.writeLineNumber(lineNumber.getCodeAddress(), lineNumber.getLineNumber())
                return
        }
    }
}
