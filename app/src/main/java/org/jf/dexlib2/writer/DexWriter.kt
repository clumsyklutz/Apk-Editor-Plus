package org.jf.dexlib2.writer

import androidx.fragment.app.FragmentTransaction
import androidx.core.view.InputDeviceCompat
import androidx.appcompat.R
import com.google.common.collect.ImmutableList
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.common.collect.Ordering
import com.google.common.primitives.Ints
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.CharSequence
import java.lang.Comparable
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.zip.Adler32
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.Format
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.base.BaseAnnotation
import org.jf.dexlib2.base.BaseAnnotationElement
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.builder.MutableMethodImplementation
import org.jf.dexlib2.builder.instruction.BuilderInstruction31c
import org.jf.dexlib2.dexbacked.raw.HeaderItem
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.TryBlock
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.debug.LineNumber
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.ReferenceInstruction
import org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload
import org.jf.dexlib2.iface.instruction.formats.Instruction10t
import org.jf.dexlib2.iface.instruction.formats.Instruction10x
import org.jf.dexlib2.iface.instruction.formats.Instruction11n
import org.jf.dexlib2.iface.instruction.formats.Instruction11x
import org.jf.dexlib2.iface.instruction.formats.Instruction12x
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc
import org.jf.dexlib2.iface.instruction.formats.Instruction20t
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.instruction.formats.Instruction21ih
import org.jf.dexlib2.iface.instruction.formats.Instruction21lh
import org.jf.dexlib2.iface.instruction.formats.Instruction21s
import org.jf.dexlib2.iface.instruction.formats.Instruction21t
import org.jf.dexlib2.iface.instruction.formats.Instruction22b
import org.jf.dexlib2.iface.instruction.formats.Instruction22c
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs
import org.jf.dexlib2.iface.instruction.formats.Instruction22s
import org.jf.dexlib2.iface.instruction.formats.Instruction22t
import org.jf.dexlib2.iface.instruction.formats.Instruction22x
import org.jf.dexlib2.iface.instruction.formats.Instruction23x
import org.jf.dexlib2.iface.instruction.formats.Instruction30t
import org.jf.dexlib2.iface.instruction.formats.Instruction31c
import org.jf.dexlib2.iface.instruction.formats.Instruction31i
import org.jf.dexlib2.iface.instruction.formats.Instruction31t
import org.jf.dexlib2.iface.instruction.formats.Instruction32x
import org.jf.dexlib2.iface.instruction.formats.Instruction35c
import org.jf.dexlib2.iface.instruction.formats.Instruction35mi
import org.jf.dexlib2.iface.instruction.formats.Instruction35ms
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc
import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms
import org.jf.dexlib2.iface.instruction.formats.Instruction45cc
import org.jf.dexlib2.iface.instruction.formats.Instruction4rcc
import org.jf.dexlib2.iface.instruction.formats.Instruction51l
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload
import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.dexlib2.util.InstructionUtil
import org.jf.dexlib2.util.MethodUtil
import org.jf.dexlib2.writer.AnnotationSection
import org.jf.dexlib2.writer.AnnotationSetSection
import org.jf.dexlib2.writer.CallSiteSection
import org.jf.dexlib2.writer.ClassSection
import org.jf.dexlib2.writer.EncodedArraySection
import org.jf.dexlib2.writer.FieldSection
import org.jf.dexlib2.writer.MethodHandleSection
import org.jf.dexlib2.writer.MethodSection
import org.jf.dexlib2.writer.ProtoSection
import org.jf.dexlib2.writer.StringSection
import org.jf.dexlib2.writer.TypeListSection
import org.jf.dexlib2.writer.TypeSection
import org.jf.dexlib2.writer.io.DeferredOutputStreamFactory
import org.jf.dexlib2.writer.io.DexDataStore
import org.jf.dexlib2.writer.io.MemoryDeferredOutputStream
import org.jf.dexlib2.writer.util.TryListBuilder
import org.jf.util.ExceptionWithContext

abstract class DexWriter<StringKey extends CharSequence, StringRef extends StringReference, TypeKey extends CharSequence, TypeRef extends TypeReference, ProtoRefKey extends MethodProtoReference, FieldRefKey extends FieldReference, MethodRefKey extends MethodReference, ClassKey extends Comparable<? super ClassKey>, CallSiteKey extends CallSiteReference, MethodHandleKey extends MethodHandleReference, AnnotationKey extends Annotation, AnnotationSetKey, TypeListKey, FieldKey, MethodKey, EncodedArrayKey, EncodedValue, AnnotationElement extends AnnotationElement, StringSectionType extends StringSection<StringKey, StringRef>, TypeSectionType extends TypeSection<StringKey, TypeKey, TypeRef>, ProtoSectionType extends ProtoSection<StringKey, TypeKey, ProtoRefKey, TypeListKey>, FieldSectionType extends FieldSection<StringKey, TypeKey, FieldRefKey, FieldKey>, MethodSectionType extends MethodSection<StringKey, TypeKey, ProtoRefKey, MethodRefKey, MethodKey>, ClassSectionType extends ClassSection<StringKey, TypeKey, TypeListKey, ClassKey, FieldKey, MethodKey, AnnotationSetKey, EncodedArrayKey>, CallSiteSectionType extends CallSiteSection<CallSiteKey, EncodedArrayKey>, MethodHandleSectionType extends MethodHandleSection<MethodHandleKey, FieldRefKey, MethodRefKey>, TypeListSectionType extends TypeListSection<TypeKey, TypeListKey>, AnnotationSectionType extends AnnotationSection<StringKey, TypeKey, AnnotationKey, AnnotationElement, EncodedValue>, AnnotationSetSectionType extends AnnotationSetSection<AnnotationKey, AnnotationSetKey>, EncodedArraySectionType extends EncodedArraySection<EncodedArrayKey, EncodedValue>> {
    public static Comparator<Map.Entry> toStringKeyComparator = new Comparator<Map.Entry>() { // from class: org.jf.dexlib2.writer.DexWriter.2
        @Override // java.util.Comparator
        fun compare(Map.Entry entry, Map.Entry entry2) {
            return entry.getKey().toString().compareTo(entry2.getKey().toString())
        }
    }
    public final AnnotationSectionType annotationSection
    public final AnnotationSetSectionType annotationSetSection
    public final CallSiteSectionType callSiteSection
    public final ClassSectionType classSection
    public final EncodedArraySectionType encodedArraySection
    public final FieldSectionType fieldSection
    public final MethodHandleSectionType methodHandleSection
    public final MethodSectionType methodSection
    public final Opcodes opcodes
    public final ProtoSectionType protoSection
    public final StringSectionType stringSection
    public final TypeListSectionType typeListSection
    public final TypeSectionType typeSection
    public Int stringIndexSectionOffset = 0
    public Int typeSectionOffset = 0
    public Int protoSectionOffset = 0
    public Int fieldSectionOffset = 0
    public Int methodSectionOffset = 0
    public Int classIndexSectionOffset = 0
    public Int callSiteSectionOffset = 0
    public Int methodHandleSectionOffset = 0
    public Int stringDataSectionOffset = 0
    public Int classDataSectionOffset = 0
    public Int typeListSectionOffset = 0
    public Int encodedArraySectionOffset = 0
    public Int annotationSectionOffset = 0
    public Int annotationSetSectionOffset = 0
    public Int annotationSetRefSectionOffset = 0
    public Int annotationDirectorySectionOffset = 0
    public Int debugSectionOffset = 0
    public Int codeSectionOffset = 0
    public Int hiddenApiRestrictionsOffset = 0
    public Int mapSectionOffset = 0
    public Boolean hasHiddenApiRestrictions = false
    public Int numAnnotationSetRefItems = 0
    public Int numAnnotationDirectoryItems = 0
    public Int numDebugInfoItems = 0
    public Int numCodeItemItems = 0
    public Int numClassDataItems = 0
    public Comparator<Map.Entry<? extends CallSiteKey, Integer>> callSiteComparator = new Comparator<Map.Entry<CallSiteReference, Integer>>() { // from class: org.jf.dexlib2.writer.DexWriter.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Comparator
        fun compare(Map.Entry<CallSiteReference, Integer> entry, Map.Entry<CallSiteReference, Integer> entry2) {
            DexWriter dexWriter = DexWriter.this
            Int itemOffset = dexWriter.encodedArraySection.getItemOffset(dexWriter.callSiteSection.getEncodedCallSite(entry.getKey()))
            DexWriter dexWriter2 = DexWriter.this
            return Ints.compare(itemOffset, dexWriter2.encodedArraySection.getItemOffset(dexWriter2.callSiteSection.getEncodedCallSite(entry2.getKey())))
        }
    }

    /* renamed from: org.jf.dexlib2.writer.DexWriter$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Format

        static {
            Array<Int> iArr = new Int[Format.values().length]
            $SwitchMap$org$jf$dexlib2$Format = iArr
            try {
                iArr[Format.Format10t.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format10x.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11n.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11x.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format12x.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20bc.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20t.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21c.ordinal()] = 8
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21ih.ordinal()] = 9
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21lh.ordinal()] = 10
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21s.ordinal()] = 11
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21t.ordinal()] = 12
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22b.ordinal()] = 13
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22c.ordinal()] = 14
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22cs.ordinal()] = 15
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22s.ordinal()] = 16
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22t.ordinal()] = 17
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22x.ordinal()] = 18
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format23x.ordinal()] = 19
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format30t.ordinal()] = 20
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31c.ordinal()] = 21
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31i.ordinal()] = 22
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31t.ordinal()] = 23
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format32x.ordinal()] = 24
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35c.ordinal()] = 25
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 26
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 27
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rc.ordinal()] = 28
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rmi.ordinal()] = 29
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rms.ordinal()] = 30
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format45cc.ordinal()] = 31
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format4rcc.ordinal()] = 32
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format51l.ordinal()] = 33
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.ArrayPayload.ordinal()] = 34
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.PackedSwitchPayload.ordinal()] = 35
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.SparseSwitchPayload.ordinal()] = 36
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    public static class CodeItemOffset<MethodKey> {
        public Int codeOffset
        public MethodKey method

        constructor(MethodKey methodkey, Int i) {
            this.codeOffset = i
            this.method = methodkey
        }
    }

    class InternalEncodedValueWriter extends EncodedValueWriter<StringKey, TypeKey, FieldRefKey, MethodRefKey, AnnotationElement, ProtoRefKey, MethodHandleKey, EncodedValue> {
        constructor(DexDataWriter dexDataWriter) {
            super(dexDataWriter, DexWriter.this.stringSection, DexWriter.this.typeSection, DexWriter.this.fieldSection, DexWriter.this.methodSection, DexWriter.this.protoSection, DexWriter.this.methodHandleSection, DexWriter.this.annotationSection)
        }

        @Override // org.jf.dexlib2.writer.EncodedValueWriter
        fun writeEncodedValue(EncodedValue encodedvalue) throws IOException {
            DexWriter.this.writeEncodedValue(this, encodedvalue)
        }
    }

    public static class RestrictionsWriter {
        public final DexDataStore dataStore
        public final DexDataWriter offsetsWriter
        public final DexDataWriter restrictionsWriter
        public final Int startOffset
        public Boolean writeRestrictionsForClass = false
        public Int pendingBlankEntries = 0

        constructor(DexDataStore dexDataStore, DexDataWriter dexDataWriter, Int i) throws IOException {
            this.startOffset = dexDataWriter.getPosition()
            this.dataStore = dexDataStore
            this.restrictionsWriter = dexDataWriter
            Int i2 = i * 4
            dexDataWriter.writeInt(0)
            this.offsetsWriter = DexWriter.outputAt(dexDataStore, dexDataWriter.getPosition())
            for (Int i3 = 0; i3 < i2; i3++) {
                this.restrictionsWriter.write(0)
            }
            this.restrictionsWriter.flush()
        }

        public final Unit addBlankEntry() throws IOException {
            if (this.writeRestrictionsForClass) {
                this.restrictionsWriter.writeUleb128(HiddenApiRestriction.WHITELIST.getValue())
            } else {
                this.pendingBlankEntries++
            }
        }

        fun close() throws IOException {
            this.offsetsWriter.close()
            DexDataWriter dexDataWriterOutputAt = null
            try {
                dexDataWriterOutputAt = DexWriter.outputAt(this.dataStore, this.startOffset)
                dexDataWriterOutputAt.writeInt(this.restrictionsWriter.getPosition() - this.startOffset)
                dexDataWriterOutputAt.close()
            } catch (Throwable th) {
                if (dexDataWriterOutputAt != null) {
                    dexDataWriterOutputAt.close()
                }
                throw th
            }
        }

        fun finishClass() throws IOException {
            if (!this.writeRestrictionsForClass) {
                this.offsetsWriter.writeInt(0)
            }
            this.writeRestrictionsForClass = false
            this.pendingBlankEntries = 0
        }

        fun writeRestriction(Set<HiddenApiRestriction> set) throws IOException {
            if (set.isEmpty()) {
                addBlankEntry()
                return
            }
            if (!this.writeRestrictionsForClass) {
                this.writeRestrictionsForClass = true
                this.offsetsWriter.writeInt(this.restrictionsWriter.getPosition() - this.startOffset)
                for (Int i = 0; i < this.pendingBlankEntries; i++) {
                    this.restrictionsWriter.writeUleb128(HiddenApiRestriction.WHITELIST.getValue())
                }
                this.pendingBlankEntries = 0
            }
            this.restrictionsWriter.writeUleb128(HiddenApiRestriction.combineFlags(set))
        }
    }

    abstract class SectionProvider {
        constructor(DexWriter dexWriter) {
        }

        public abstract AnnotationSectionType getAnnotationSection()

        public abstract AnnotationSetSectionType getAnnotationSetSection()

        public abstract CallSiteSectionType getCallSiteSection()

        public abstract ClassSectionType getClassSection()

        public abstract EncodedArraySectionType getEncodedArraySection()

        public abstract FieldSectionType getFieldSection()

        public abstract MethodHandleSectionType getMethodHandleSection()

        public abstract MethodSectionType getMethodSection()

        public abstract ProtoSectionType getProtoSection()

        public abstract StringSectionType getStringSection()

        public abstract TypeListSectionType getTypeListSection()

        public abstract TypeSectionType getTypeSection()
    }

    constructor(Opcodes opcodes) {
        this.opcodes = opcodes
        DexWriter<StringKey, StringRef, TypeKey, TypeRef, ProtoRefKey, FieldRefKey, MethodRefKey, ClassKey, CallSiteKey, MethodHandleKey, AnnotationKey, AnnotationSetKey, TypeListKey, FieldKey, MethodKey, EncodedArrayKey, EncodedValue, AnnotationElement, StringSectionType, TypeSectionType, ProtoSectionType, FieldSectionType, MethodSectionType, ClassSectionType, CallSiteSectionType, MethodHandleSectionType, TypeListSectionType, AnnotationSectionType, AnnotationSetSectionType, EncodedArraySectionType>.SectionProvider sectionProvider = getSectionProvider()
        this.stringSection = (StringSectionType) sectionProvider.getStringSection()
        this.typeSection = (TypeSectionType) sectionProvider.getTypeSection()
        this.protoSection = (ProtoSectionType) sectionProvider.getProtoSection()
        this.fieldSection = (FieldSectionType) sectionProvider.getFieldSection()
        this.methodSection = (MethodSectionType) sectionProvider.getMethodSection()
        this.classSection = (ClassSectionType) sectionProvider.getClassSection()
        this.callSiteSection = (CallSiteSectionType) sectionProvider.getCallSiteSection()
        this.methodHandleSection = (MethodHandleSectionType) sectionProvider.getMethodHandleSection()
        this.typeListSection = (TypeListSectionType) sectionProvider.getTypeListSection()
        this.annotationSection = (AnnotationSectionType) sectionProvider.getAnnotationSection()
        this.annotationSetSection = (AnnotationSetSectionType) sectionProvider.getAnnotationSetSection()
        this.encodedArraySection = (EncodedArraySectionType) sectionProvider.getEncodedArraySection()
    }

    public static <T extends Comparable<? super T>> Comparator<Map.Entry<? extends T, ?>> comparableKeyComparator() {
        return (Comparator<Map.Entry<? extends T, ?>>) new Comparator<Map.Entry<? extends T, ?>>() { // from class: org.jf.dexlib2.writer.DexWriter.3
            @Override // java.util.Comparator
            fun compare(Map.Entry<? extends T, ?> entry, Map.Entry<? extends T, ?> entry2) {
                return ((Comparable) entry.getKey()).compareTo(entry2.getKey())
            }
        }
    }

    fun outputAt(DexDataStore dexDataStore, Int i) throws IOException {
        return DexDataWriter(dexDataStore.outputAt(i), i)
    }

    public final Int calcNumItems() {
        Int i = this.stringSection.getItems().size() > 0 ? 3 : 1
        if (this.typeSection.getItems().size() > 0) {
            i++
        }
        if (this.protoSection.getItems().size() > 0) {
            i++
        }
        if (this.fieldSection.getItems().size() > 0) {
            i++
        }
        if (this.methodSection.getItems().size() > 0) {
            i++
        }
        if (this.callSiteSection.getItems().size() > 0) {
            i++
        }
        if (this.methodHandleSection.getItems().size() > 0) {
            i++
        }
        if (this.typeListSection.getItems().size() > 0) {
            i++
        }
        if (this.encodedArraySection.getItems().size() > 0) {
            i++
        }
        if (this.annotationSection.getItems().size() > 0) {
            i++
        }
        if (this.annotationSetSection.getItems().size() > 0 || shouldCreateEmptyAnnotationSet()) {
            i++
        }
        if (this.numAnnotationSetRefItems > 0) {
            i++
        }
        if (this.numAnnotationDirectoryItems > 0) {
            i++
        }
        if (this.numDebugInfoItems > 0) {
            i++
        }
        if (this.numCodeItemItems > 0) {
            i++
        }
        if (this.classSection.getItems().size() > 0) {
            i++
        }
        if (this.numClassDataItems > 0) {
            i++
        }
        if (shouldWriteHiddenApiRestrictions()) {
            i++
        }
        return i + 1
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit fixInstructions(MutableMethodImplementation mutableMethodImplementation) {
        List<BuilderInstruction> instructions = mutableMethodImplementation.getInstructions()
        for (Int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i)
            if (instruction.getOpcode() == Opcode.CONST_STRING) {
                ReferenceInstruction referenceInstruction = (ReferenceInstruction) instruction
                if (this.stringSection.getItemIndex((StringReference) referenceInstruction.getReference()) >= 65536) {
                    mutableMethodImplementation.replaceInstruction(i, BuilderInstruction31c(Opcode.CONST_STRING_JUMBO, ((OneRegisterInstruction) instruction).getRegisterA(), referenceInstruction.getReference()))
                }
            }
        }
    }

    public final Int getDataSectionOffset() {
        return (this.stringSection.getItemCount() * 4) + R.styleable.AppCompatTheme_ratingBarStyleSmall + (this.typeSection.getItemCount() * 4) + (this.protoSection.getItemCount() * 12) + (this.fieldSection.getItemCount() * 8) + (this.methodSection.getItemCount() * 8) + (this.classSection.getItemCount() * 32) + (this.callSiteSection.getItemCount() * 4) + (this.methodHandleSection.getItemCount() * 8)
    }

    public abstract DexWriter<StringKey, StringRef, TypeKey, TypeRef, ProtoRefKey, FieldRefKey, MethodRefKey, ClassKey, CallSiteKey, MethodHandleKey, AnnotationKey, AnnotationSetKey, TypeListKey, FieldKey, MethodKey, EncodedArrayKey, EncodedValue, AnnotationElement, StringSectionType, TypeSectionType, ProtoSectionType, FieldSectionType, MethodSectionType, ClassSectionType, CallSiteSectionType, MethodHandleSectionType, TypeListSectionType, AnnotationSectionType, AnnotationSetSectionType, EncodedArraySectionType>.SectionProvider getSectionProvider()

    public final Boolean shouldCreateEmptyAnnotationSet() {
        return this.opcodes.api < 17
    }

    public final Boolean shouldWriteHiddenApiRestrictions() {
        return this.hasHiddenApiRestrictions && this.opcodes.api >= 29
    }

    public final Unit updateChecksum(DexDataStore dexDataStore) throws IOException {
        Adler32 adler32 = Adler32()
        Array<Byte> bArr = new Byte[4096]
        InputStream at = dexDataStore.readAt(12)
        for (Int i = at.read(bArr); i >= 0; i = at.read(bArr)) {
            adler32.update(bArr, 0, i)
        }
        OutputStream outputStreamOutputAt = dexDataStore.outputAt(8)
        DexDataWriter.writeInt(outputStreamOutputAt, (Int) adler32.getValue())
        outputStreamOutputAt.close()
    }

    public final Unit updateSignature(DexDataStore dexDataStore) throws NoSuchAlgorithmException, IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1")
            Array<Byte> bArr = new Byte[4096]
            InputStream at = dexDataStore.readAt(32)
            for (Int i = at.read(bArr); i >= 0; i = at.read(bArr)) {
                messageDigest.update(bArr, 0, i)
            }
            Array<Byte> bArrDigest = messageDigest.digest()
            if (bArrDigest.length != 20) {
                throw RuntimeException("unexpected digest write: " + bArrDigest.length + " bytes")
            }
            OutputStream outputStreamOutputAt = dexDataStore.outputAt(12)
            outputStreamOutputAt.write(bArrDigest)
            outputStreamOutputAt.close()
        } catch (NoSuchAlgorithmException e) {
            throw RuntimeException(e)
        }
    }

    public final Unit writeAnnotationDirectories(DexDataWriter dexDataWriter) throws IOException {
        dexDataWriter.align()
        this.annotationDirectorySectionOffset = dexDataWriter.getPosition()
        HashMap mapNewHashMap = Maps.newHashMap()
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(65536)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        for (Comparable comparable : this.classSection.getSortedClasses()) {
            Collection<? extends FieldKey> sortedFields = this.classSection.getSortedFields(comparable)
            Collection<? extends MethodKey> sortedMethods = this.classSection.getSortedMethods(comparable)
            Int size = (sortedFields.size() * 8) + (sortedMethods.size() * 16)
            if (size > byteBufferAllocate.capacity()) {
                byteBufferAllocate = ByteBuffer.allocate(size)
                byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
            }
            byteBufferAllocate.clear()
            Int i = 0
            for (FieldKey fieldkey : sortedFields) {
                Object fieldAnnotations = this.classSection.getFieldAnnotations(fieldkey)
                if (fieldAnnotations != null) {
                    i++
                    byteBufferAllocate.putInt(this.fieldSection.getFieldIndex(fieldkey))
                    byteBufferAllocate.putInt(this.annotationSetSection.getItemOffset(fieldAnnotations))
                }
            }
            Int i2 = 0
            for (MethodKey methodkey : sortedMethods) {
                Object methodAnnotations = this.classSection.getMethodAnnotations(methodkey)
                if (methodAnnotations != null) {
                    i2++
                    byteBufferAllocate.putInt(this.methodSection.getMethodIndex(methodkey))
                    byteBufferAllocate.putInt(this.annotationSetSection.getItemOffset(methodAnnotations))
                }
            }
            Int i3 = 0
            for (MethodKey methodkey2 : sortedMethods) {
                Int annotationSetRefListOffset = this.classSection.getAnnotationSetRefListOffset(methodkey2)
                if (annotationSetRefListOffset != 0) {
                    i3++
                    byteBufferAllocate.putInt(this.methodSection.getMethodIndex(methodkey2))
                    byteBufferAllocate.putInt(annotationSetRefListOffset)
                }
            }
            Object classAnnotations = this.classSection.getClassAnnotations(comparable)
            if (i == 0 && i2 == 0 && i3 == 0) {
                if (classAnnotations != null) {
                    Integer num = (Integer) mapNewHashMap.get(classAnnotations)
                    if (num != null) {
                        this.classSection.setAnnotationDirectoryOffset(comparable, num.intValue())
                    } else {
                        mapNewHashMap.put(classAnnotations, Integer.valueOf(dexDataWriter.getPosition()))
                    }
                }
            }
            this.numAnnotationDirectoryItems++
            this.classSection.setAnnotationDirectoryOffset(comparable, dexDataWriter.getPosition())
            dexDataWriter.writeInt(this.annotationSetSection.getNullableItemOffset(classAnnotations))
            dexDataWriter.writeInt(i)
            dexDataWriter.writeInt(i2)
            dexDataWriter.writeInt(i3)
            dexDataWriter.write(byteBufferAllocate.array(), 0, byteBufferAllocate.position())
        }
    }

    public final Unit writeAnnotationSetRefs(DexDataWriter dexDataWriter) throws IOException {
        dexDataWriter.align()
        this.annotationSetRefSectionOffset = dexDataWriter.getPosition()
        HashMap mapNewHashMap = Maps.newHashMap()
        Iterator it = this.classSection.getSortedClasses().iterator()
        while (it.hasNext()) {
            for (MethodKey methodkey : this.classSection.getSortedMethods((Comparable) it.next())) {
                List<? extends AnnotationSetKey> parameterAnnotations = this.classSection.getParameterAnnotations(methodkey)
                if (parameterAnnotations != null) {
                    Integer num = (Integer) mapNewHashMap.get(parameterAnnotations)
                    if (num != null) {
                        this.classSection.setAnnotationSetRefListOffset(methodkey, num.intValue())
                    } else {
                        dexDataWriter.align()
                        Int position = dexDataWriter.getPosition()
                        this.classSection.setAnnotationSetRefListOffset(methodkey, position)
                        mapNewHashMap.put(parameterAnnotations, Integer.valueOf(position))
                        this.numAnnotationSetRefItems++
                        dexDataWriter.writeInt(parameterAnnotations.size())
                        for (AnnotationSetKey annotationsetkey : parameterAnnotations) {
                            if (this.annotationSetSection.getAnnotations(annotationsetkey).size() > 0) {
                                dexDataWriter.writeInt(this.annotationSetSection.getItemOffset(annotationsetkey))
                            } else if (shouldCreateEmptyAnnotationSet()) {
                                dexDataWriter.writeInt(this.annotationSetSectionOffset)
                            } else {
                                dexDataWriter.writeInt(0)
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeAnnotationSets(DexDataWriter dexDataWriter) throws IOException {
        dexDataWriter.align()
        this.annotationSetSectionOffset = dexDataWriter.getPosition()
        if (shouldCreateEmptyAnnotationSet()) {
            dexDataWriter.writeInt(0)
        }
        for (Map.Entry entry : this.annotationSetSection.getItems()) {
            ImmutableList immutableListImmutableSortedCopy = Ordering.from(BaseAnnotation.BY_TYPE).immutableSortedCopy(this.annotationSetSection.getAnnotations(entry.getKey()))
            dexDataWriter.align()
            entry.setValue(Integer.valueOf(dexDataWriter.getPosition()))
            dexDataWriter.writeInt(immutableListImmutableSortedCopy.size())
            Iterator<E> it = immutableListImmutableSortedCopy.iterator()
            while (it.hasNext()) {
                dexDataWriter.writeInt(this.annotationSection.getItemOffset((Annotation) it.next()))
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeAnnotations(DexDataWriter dexDataWriter) throws IOException {
        InternalEncodedValueWriter internalEncodedValueWriter = InternalEncodedValueWriter(dexDataWriter)
        this.annotationSectionOffset = dexDataWriter.getPosition()
        for (Map.Entry entry : this.annotationSection.getItems()) {
            entry.setValue(Integer.valueOf(dexDataWriter.getPosition()))
            Annotation annotation = (Annotation) entry.getKey()
            dexDataWriter.writeUbyte(this.annotationSection.getVisibility(annotation))
            dexDataWriter.writeUleb128(this.typeSection.getItemIndex((CharSequence) this.annotationSection.getType(annotation)))
            ImmutableList<AnnotationElement> immutableListImmutableSortedCopy = Ordering.from(BaseAnnotationElement.BY_NAME).immutableSortedCopy(this.annotationSection.getElements(annotation))
            dexDataWriter.writeUleb128(immutableListImmutableSortedCopy.size())
            for (AnnotationElement annotationElement : immutableListImmutableSortedCopy) {
                dexDataWriter.writeUleb128(this.stringSection.getItemIndex((CharSequence) this.annotationSection.getElementName(annotationElement)))
                writeEncodedValue(internalEncodedValueWriter, this.annotationSection.getElementValue(annotationElement))
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeCallSites(DexDataWriter dexDataWriter) throws IOException {
        this.callSiteSectionOffset = dexDataWriter.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.callSiteSection.getItems())
        Collections.sort(arrayListNewArrayList, this.callSiteComparator)
        Int i = 0
        for (Map.Entry entry : arrayListNewArrayList) {
            entry.setValue(Integer.valueOf(i))
            dexDataWriter.writeInt(this.encodedArraySection.getItemOffset(this.callSiteSection.getEncodedCallSite((CallSiteReference) entry.getKey())))
            i++
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Int writeClass(DexDataWriter dexDataWriter, DexDataWriter dexDataWriter2, Int i, Map.Entry<? extends ClassKey, Integer> entry) throws IOException {
        if (entry == null || entry.getValue().intValue() != -1) {
            return i
        }
        ClassKey key = entry.getKey()
        entry.setValue(0)
        ClassSectionType classsectiontype = this.classSection
        Int iWriteClass = writeClass(dexDataWriter, dexDataWriter2, i, classsectiontype.getClassEntryByType(classsectiontype.getSuperclass(key)))
        Iterator it = this.typeListSection.getTypes(this.classSection.getInterfaces(key)).iterator()
        while (it.hasNext()) {
            iWriteClass = writeClass(dexDataWriter, dexDataWriter2, iWriteClass, this.classSection.getClassEntryByType((CharSequence) it.next()))
        }
        Int i2 = iWriteClass + 1
        entry.setValue(Integer.valueOf(iWriteClass))
        dexDataWriter.writeInt(this.typeSection.getItemIndex(this.classSection.getType(key)))
        dexDataWriter.writeInt(this.classSection.getAccessFlags(key))
        dexDataWriter.writeInt(this.typeSection.getNullableItemIndex(this.classSection.getSuperclass(key)))
        dexDataWriter.writeInt(this.typeListSection.getNullableItemOffset(this.classSection.getInterfaces(key)))
        dexDataWriter.writeInt(this.stringSection.getNullableItemIndex(this.classSection.getSourceFile(key)))
        dexDataWriter.writeInt(this.classSection.getAnnotationDirectoryOffset(key))
        Collection<? extends FieldKey> sortedStaticFields = this.classSection.getSortedStaticFields(key)
        Collection<? extends FieldKey> sortedInstanceFields = this.classSection.getSortedInstanceFields(key)
        Collection<? extends MethodKey> sortedDirectMethods = this.classSection.getSortedDirectMethods(key)
        Collection<? extends MethodKey> sortedVirtualMethods = this.classSection.getSortedVirtualMethods(key)
        Boolean z = sortedStaticFields.size() > 0 || sortedInstanceFields.size() > 0 || sortedDirectMethods.size() > 0 || sortedVirtualMethods.size() > 0
        if (z) {
            dexDataWriter.writeInt(dexDataWriter2.getPosition())
        } else {
            dexDataWriter.writeInt(0)
        }
        Object staticInitializers = this.classSection.getStaticInitializers(key)
        if (staticInitializers != null) {
            dexDataWriter.writeInt(this.encodedArraySection.getItemOffset(staticInitializers))
        } else {
            dexDataWriter.writeInt(0)
        }
        if (z) {
            this.numClassDataItems++
            dexDataWriter2.writeUleb128(sortedStaticFields.size())
            dexDataWriter2.writeUleb128(sortedInstanceFields.size())
            dexDataWriter2.writeUleb128(sortedDirectMethods.size())
            dexDataWriter2.writeUleb128(sortedVirtualMethods.size())
            writeEncodedFields(dexDataWriter2, sortedStaticFields)
            writeEncodedFields(dexDataWriter2, sortedInstanceFields)
            writeEncodedMethods(dexDataWriter2, sortedDirectMethods)
            writeEncodedMethods(dexDataWriter2, sortedVirtualMethods)
        }
        return i2
    }

    public final Unit writeClasses(DexDataStore dexDataStore, DexDataWriter dexDataWriter, DexDataWriter dexDataWriter2) throws IOException {
        this.classIndexSectionOffset = dexDataWriter.getPosition()
        this.classDataSectionOffset = dexDataWriter2.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.classSection.getItems())
        Collections.sort(arrayListNewArrayList, comparableKeyComparator())
        Iterator it = arrayListNewArrayList.iterator()
        Int iWriteClass = 0
        while (it.hasNext()) {
            iWriteClass = writeClass(dexDataWriter, dexDataWriter2, iWriteClass, (Map.Entry) it.next())
        }
        if (shouldWriteHiddenApiRestrictions()) {
            this.hiddenApiRestrictionsOffset = dexDataWriter2.getPosition()
            RestrictionsWriter restrictionsWriter = RestrictionsWriter(dexDataStore, dexDataWriter2, arrayListNewArrayList.size())
            try {
                for (Map.Entry entry : arrayListNewArrayList) {
                    Iterator<? extends FieldKey> it2 = this.classSection.getSortedStaticFields((Comparable) entry.getKey()).iterator()
                    while (it2.hasNext()) {
                        restrictionsWriter.writeRestriction(this.classSection.getFieldHiddenApiRestrictions(it2.next()))
                    }
                    Iterator<? extends FieldKey> it3 = this.classSection.getSortedInstanceFields((Comparable) entry.getKey()).iterator()
                    while (it3.hasNext()) {
                        restrictionsWriter.writeRestriction(this.classSection.getFieldHiddenApiRestrictions(it3.next()))
                    }
                    Iterator<? extends MethodKey> it4 = this.classSection.getSortedDirectMethods((Comparable) entry.getKey()).iterator()
                    while (it4.hasNext()) {
                        restrictionsWriter.writeRestriction(this.classSection.getMethodHiddenApiRestrictions(it4.next()))
                    }
                    Iterator<? extends MethodKey> it5 = this.classSection.getSortedVirtualMethods((Comparable) entry.getKey()).iterator()
                    while (it5.hasNext()) {
                        restrictionsWriter.writeRestriction(this.classSection.getMethodHiddenApiRestrictions(it5.next()))
                    }
                    restrictionsWriter.finishClass()
                }
            } finally {
                restrictionsWriter.close()
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Int writeCodeItem(DexDataWriter dexDataWriter, ByteArrayOutputStream byteArrayOutputStream, MethodKey methodkey, List<? extends TryBlock<? extends ExceptionHandler>> list, Iterable<? extends Instruction> iterable, Int i) throws IOException {
        if (iterable == null && i == 0) {
            return -1
        }
        this.numCodeItemItems++
        dexDataWriter.align()
        Int position = dexDataWriter.getPosition()
        dexDataWriter.writeUshort(this.classSection.getRegisterCount(methodkey))
        dexDataWriter.writeUshort(MethodUtil.getParameterRegisterCount((Collection<? extends CharSequence>) this.typeListSection.getTypes(this.protoSection.getParameters(this.methodSection.getPrototype(methodkey))), AccessFlags.STATIC.isSet(this.classSection.getMethodAccessFlags(methodkey))))
        if (iterable != null) {
            List<TryBlock> listMassageTryBlocks = TryListBuilder.massageTryBlocks(list)
            Int i2 = 0
            Int codeUnits = 0
            for (Instruction instruction : iterable) {
                codeUnits += instruction.getCodeUnits()
                if (instruction.getOpcode().referenceType == 3) {
                    MethodReference methodReference = (MethodReference) ((ReferenceInstruction) instruction).getReference()
                    Opcode opcode = instruction.getOpcode()
                    Int registerCount = InstructionUtil.isInvokePolymorphic(opcode) ? ((VariableRegisterInstruction) instruction).getRegisterCount() : MethodUtil.getParameterRegisterCount(methodReference, InstructionUtil.isInvokeStatic(opcode))
                    if (registerCount > i2) {
                        i2 = registerCount
                    }
                }
            }
            dexDataWriter.writeUshort(i2)
            dexDataWriter.writeUshort(listMassageTryBlocks.size())
            dexDataWriter.writeInt(i)
            InstructionWriter instructionWriterMakeInstructionWriter = InstructionWriter.makeInstructionWriter(this.opcodes, dexDataWriter, this.stringSection, this.typeSection, this.fieldSection, this.methodSection, this.protoSection, this.methodHandleSection, this.callSiteSection)
            dexDataWriter.writeInt(codeUnits)
            Int codeUnits2 = 0
            for (Instruction instruction2 : iterable) {
                try {
                    switch (AnonymousClass4.$SwitchMap$org$jf$dexlib2$Format[instruction2.getOpcode().format.ordinal()]) {
                        case 1:
                            instructionWriterMakeInstructionWriter.write((Instruction10t) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 2:
                            instructionWriterMakeInstructionWriter.write((Instruction10x) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 3:
                            instructionWriterMakeInstructionWriter.write((Instruction11n) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 4:
                            instructionWriterMakeInstructionWriter.write((Instruction11x) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 5:
                            instructionWriterMakeInstructionWriter.write((Instruction12x) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 6:
                            instructionWriterMakeInstructionWriter.write((Instruction20bc) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 7:
                            instructionWriterMakeInstructionWriter.write((Instruction20t) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 8:
                            instructionWriterMakeInstructionWriter.write((Instruction21c) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 9:
                            instructionWriterMakeInstructionWriter.write((Instruction21ih) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 10:
                            instructionWriterMakeInstructionWriter.write((Instruction21lh) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 11:
                            instructionWriterMakeInstructionWriter.write((Instruction21s) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 12:
                            instructionWriterMakeInstructionWriter.write((Instruction21t) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 13:
                            instructionWriterMakeInstructionWriter.write((Instruction22b) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 14:
                            instructionWriterMakeInstructionWriter.write((Instruction22c) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 15:
                            instructionWriterMakeInstructionWriter.write((Instruction22cs) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 16:
                            instructionWriterMakeInstructionWriter.write((Instruction22s) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 17:
                            instructionWriterMakeInstructionWriter.write((Instruction22t) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 18:
                            instructionWriterMakeInstructionWriter.write((Instruction22x) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 19:
                            instructionWriterMakeInstructionWriter.write((Instruction23x) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 20:
                            instructionWriterMakeInstructionWriter.write((Instruction30t) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 21:
                            instructionWriterMakeInstructionWriter.write((Instruction31c) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 22:
                            instructionWriterMakeInstructionWriter.write((Instruction31i) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 23:
                            instructionWriterMakeInstructionWriter.write((Instruction31t) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 24:
                            instructionWriterMakeInstructionWriter.write((Instruction32x) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 25:
                            instructionWriterMakeInstructionWriter.write((Instruction35c) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 26:
                            instructionWriterMakeInstructionWriter.write((Instruction35mi) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 27:
                            instructionWriterMakeInstructionWriter.write((Instruction35ms) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 28:
                            instructionWriterMakeInstructionWriter.write((Instruction3rc) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 29:
                            instructionWriterMakeInstructionWriter.write((Instruction3rmi) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 30:
                            instructionWriterMakeInstructionWriter.write((Instruction3rms) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 31:
                            instructionWriterMakeInstructionWriter.write((Instruction45cc) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 32:
                            instructionWriterMakeInstructionWriter.write((Instruction4rcc) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 33:
                            instructionWriterMakeInstructionWriter.write((Instruction51l) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 34:
                            instructionWriterMakeInstructionWriter.write((ArrayPayload) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 35:
                            instructionWriterMakeInstructionWriter.write((PackedSwitchPayload) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        case 36:
                            instructionWriterMakeInstructionWriter.write((SparseSwitchPayload) instruction2)
                            continue
                            codeUnits2 += instruction2.getCodeUnits()
                        default:
                            throw ExceptionWithContext("Unsupported instruction format: %s", instruction2.getOpcode().format)
                    }
                } catch (RuntimeException e) {
                    throw ExceptionWithContext(e, "Error while writing instruction at code offset 0x%x", Integer.valueOf(codeUnits2))
                }
                throw ExceptionWithContext(e, "Error while writing instruction at code offset 0x%x", Integer.valueOf(codeUnits2))
            }
            if (listMassageTryBlocks.size() > 0) {
                dexDataWriter.align()
                HashMap mapNewHashMap = Maps.newHashMap()
                Iterator it = listMassageTryBlocks.iterator()
                while (it.hasNext()) {
                    mapNewHashMap.put(((TryBlock) it.next()).getExceptionHandlers(), 0)
                }
                DexDataWriter.writeUleb128(byteArrayOutputStream, mapNewHashMap.size())
                for (TryBlock tryBlock : listMassageTryBlocks) {
                    Int startCodeAddress = tryBlock.getStartCodeAddress()
                    Int codeUnitCount = (tryBlock.getCodeUnitCount() + startCodeAddress) - startCodeAddress
                    dexDataWriter.writeInt(startCodeAddress)
                    dexDataWriter.writeUshort(codeUnitCount)
                    if (tryBlock.getExceptionHandlers().size() == 0) {
                        throw ExceptionWithContext("No exception handlers for the try block!", new Object[0])
                    }
                    Integer num = (Integer) mapNewHashMap.get(tryBlock.getExceptionHandlers())
                    if (num.intValue() != 0) {
                        dexDataWriter.writeUshort(num.intValue())
                    } else {
                        Integer numValueOf = Integer.valueOf(byteArrayOutputStream.size())
                        dexDataWriter.writeUshort(numValueOf.intValue())
                        mapNewHashMap.put(tryBlock.getExceptionHandlers(), numValueOf)
                        Int size = tryBlock.getExceptionHandlers().size()
                        if (((ExceptionHandler) tryBlock.getExceptionHandlers().get(size - 1)).getExceptionType() == null) {
                            size = (size * (-1)) + 1
                        }
                        DexDataWriter.writeSleb128(byteArrayOutputStream, size)
                        for (ExceptionHandler exceptionHandler : tryBlock.getExceptionHandlers()) {
                            CharSequence exceptionType = this.classSection.getExceptionType(exceptionHandler)
                            Int handlerCodeAddress = exceptionHandler.getHandlerCodeAddress()
                            if (exceptionType != null) {
                                DexDataWriter.writeUleb128(byteArrayOutputStream, this.typeSection.getItemIndex(exceptionType))
                                DexDataWriter.writeUleb128(byteArrayOutputStream, handlerCodeAddress)
                            } else {
                                DexDataWriter.writeUleb128(byteArrayOutputStream, handlerCodeAddress)
                            }
                        }
                    }
                }
                if (byteArrayOutputStream.size() > 0) {
                    byteArrayOutputStream.writeTo(dexDataWriter)
                    byteArrayOutputStream.reset()
                }
            }
        } else {
            dexDataWriter.writeUshort(0)
            dexDataWriter.writeUshort(0)
            dexDataWriter.writeInt(i)
            dexDataWriter.writeInt(0)
        }
        return position
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit writeDebugAndCodeItems(org.jf.dexlib2.writer.DexDataWriter r19, org.jf.dexlib2.writer.io.DeferredOutputStream r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.dexlib2.writer.DexWriter.writeDebugAndCodeItems(org.jf.dexlib2.writer.DexDataWriter, org.jf.dexlib2.writer.io.DeferredOutputStream):Unit")
    }

    public final Int writeDebugItem(DexDataWriter dexDataWriter, DebugWriter<StringKey, TypeKey> debugWriter, Iterable<? extends StringKey> iterable, Iterable<? extends DebugItem> iterable2) throws IOException {
        Int size
        Int i
        Int lineNumber
        if (iterable != null) {
            size = Iterables.size(iterable)
            Iterator<? extends StringKey> it = iterable.iterator()
            i = -1
            Int i2 = 0
            while (it.hasNext()) {
                if (it.next() != null) {
                    i = i2
                }
                i2++
            }
        } else {
            size = 0
            i = -1
        }
        if (i == -1 && (iterable2 == null || Iterables.isEmpty(iterable2))) {
            return 0
        }
        this.numDebugInfoItems++
        Int position = dexDataWriter.getPosition()
        if (iterable2 != null) {
            for (DebugItem debugItem : iterable2) {
                if (debugItem is LineNumber) {
                    lineNumber = ((LineNumber) debugItem).getLineNumber()
                    break
                }
            }
            lineNumber = 0
        } else {
            lineNumber = 0
        }
        dexDataWriter.writeUleb128(lineNumber)
        dexDataWriter.writeUleb128(size)
        if (iterable != null) {
            Int i3 = 0
            for (StringKey stringkey : iterable) {
                if (i3 == size) {
                    break
                }
                i3++
                dexDataWriter.writeUleb128(this.stringSection.getNullableItemIndex(stringkey) + 1)
            }
        }
        if (iterable2 != null) {
            debugWriter.reset(lineNumber)
            Iterator<? extends DebugItem> it2 = iterable2.iterator()
            while (it2.hasNext()) {
                this.classSection.writeDebugItem(debugWriter, it2.next())
            }
        }
        dexDataWriter.write(0)
        return position
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeEncodedArrays(DexDataWriter dexDataWriter) throws IOException {
        InternalEncodedValueWriter internalEncodedValueWriter = InternalEncodedValueWriter(dexDataWriter)
        this.encodedArraySectionOffset = dexDataWriter.getPosition()
        for (Map.Entry entry : this.encodedArraySection.getItems()) {
            entry.setValue(Integer.valueOf(dexDataWriter.getPosition()))
            List encodedValueList = this.encodedArraySection.getEncodedValueList(entry.getKey())
            dexDataWriter.writeUleb128(encodedValueList.size())
            Iterator it = encodedValueList.iterator()
            while (it.hasNext()) {
                writeEncodedValue(internalEncodedValueWriter, it.next())
            }
        }
    }

    public final Unit writeEncodedFields(DexDataWriter dexDataWriter, Collection<? extends FieldKey> collection) throws IOException {
        Int i = 0
        for (FieldKey fieldkey : collection) {
            Int fieldIndex = this.fieldSection.getFieldIndex(fieldkey)
            if (!this.classSection.getFieldHiddenApiRestrictions(fieldkey).isEmpty()) {
                this.hasHiddenApiRestrictions = true
            }
            dexDataWriter.writeUleb128(fieldIndex - i)
            dexDataWriter.writeUleb128(this.classSection.getFieldAccessFlags(fieldkey))
            i = fieldIndex
        }
    }

    public final Unit writeEncodedMethods(DexDataWriter dexDataWriter, Collection<? extends MethodKey> collection) throws IOException {
        Int i = 0
        for (MethodKey methodkey : collection) {
            Int methodIndex = this.methodSection.getMethodIndex(methodkey)
            if (!this.classSection.getMethodHiddenApiRestrictions(methodkey).isEmpty()) {
                this.hasHiddenApiRestrictions = true
            }
            dexDataWriter.writeUleb128(methodIndex - i)
            dexDataWriter.writeUleb128(this.classSection.getMethodAccessFlags(methodkey))
            dexDataWriter.writeUleb128(this.classSection.getCodeItemOffset(methodkey))
            i = methodIndex
        }
    }

    public abstract Unit writeEncodedValue(DexWriter<StringKey, StringRef, TypeKey, TypeRef, ProtoRefKey, FieldRefKey, MethodRefKey, ClassKey, CallSiteKey, MethodHandleKey, AnnotationKey, AnnotationSetKey, TypeListKey, FieldKey, MethodKey, EncodedArrayKey, EncodedValue, AnnotationElement, StringSectionType, TypeSectionType, ProtoSectionType, FieldSectionType, MethodSectionType, ClassSectionType, CallSiteSectionType, MethodHandleSectionType, TypeListSectionType, AnnotationSectionType, AnnotationSetSectionType, EncodedArraySectionType>.InternalEncodedValueWriter internalEncodedValueWriter, EncodedValue encodedvalue) throws IOException

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeFields(DexDataWriter dexDataWriter) throws IOException {
        this.fieldSectionOffset = dexDataWriter.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.fieldSection.getItems())
        Collections.sort(arrayListNewArrayList, comparableKeyComparator())
        Int i = 0
        for (Map.Entry entry : arrayListNewArrayList) {
            Int i2 = i + 1
            entry.setValue(Integer.valueOf(i))
            FieldReference fieldReference = (FieldReference) entry.getKey()
            dexDataWriter.writeUshort(this.typeSection.getItemIndex((CharSequence) this.fieldSection.getDefiningClass(fieldReference)))
            dexDataWriter.writeUshort(this.typeSection.getItemIndex((CharSequence) this.fieldSection.getFieldType(fieldReference)))
            dexDataWriter.writeInt(this.stringSection.getItemIndex((CharSequence) this.fieldSection.getName(fieldReference)))
            i = i2
        }
    }

    public final Unit writeHeader(DexDataWriter dexDataWriter, Int i, Int i2) throws IOException {
        dexDataWriter.write(HeaderItem.getMagicForApi(this.opcodes.api))
        dexDataWriter.writeInt(0)
        dexDataWriter.write(new Byte[20])
        dexDataWriter.writeInt(i2)
        dexDataWriter.writeInt(R.styleable.AppCompatTheme_ratingBarStyleSmall)
        dexDataWriter.writeInt(305419896)
        dexDataWriter.writeInt(0)
        dexDataWriter.writeInt(0)
        dexDataWriter.writeInt(this.mapSectionOffset)
        writeSectionInfo(dexDataWriter, this.stringSection.getItems().size(), this.stringIndexSectionOffset)
        writeSectionInfo(dexDataWriter, this.typeSection.getItems().size(), this.typeSectionOffset)
        writeSectionInfo(dexDataWriter, this.protoSection.getItems().size(), this.protoSectionOffset)
        writeSectionInfo(dexDataWriter, this.fieldSection.getItems().size(), this.fieldSectionOffset)
        writeSectionInfo(dexDataWriter, this.methodSection.getItems().size(), this.methodSectionOffset)
        writeSectionInfo(dexDataWriter, this.classSection.getItems().size(), this.classIndexSectionOffset)
        dexDataWriter.writeInt(i2 - i)
        dexDataWriter.writeInt(i)
    }

    public final Unit writeMapItem(DexDataWriter dexDataWriter) throws IOException {
        dexDataWriter.align()
        this.mapSectionOffset = dexDataWriter.getPosition()
        dexDataWriter.writeInt(calcNumItems())
        writeMapItem(dexDataWriter, 0, 1, 0)
        writeMapItem(dexDataWriter, 1, this.stringSection.getItems().size(), this.stringIndexSectionOffset)
        writeMapItem(dexDataWriter, 2, this.typeSection.getItems().size(), this.typeSectionOffset)
        writeMapItem(dexDataWriter, 3, this.protoSection.getItems().size(), this.protoSectionOffset)
        writeMapItem(dexDataWriter, 4, this.fieldSection.getItems().size(), this.fieldSectionOffset)
        writeMapItem(dexDataWriter, 5, this.methodSection.getItems().size(), this.methodSectionOffset)
        writeMapItem(dexDataWriter, 6, this.classSection.getItems().size(), this.classIndexSectionOffset)
        writeMapItem(dexDataWriter, 7, this.callSiteSection.getItems().size(), this.callSiteSectionOffset)
        writeMapItem(dexDataWriter, 8, this.methodHandleSection.getItems().size(), this.methodHandleSectionOffset)
        writeMapItem(dexDataWriter, 8194, this.stringSection.getItems().size(), this.stringDataSectionOffset)
        writeMapItem(dexDataWriter, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, this.typeListSection.getItems().size(), this.typeListSectionOffset)
        writeMapItem(dexDataWriter, 8197, this.encodedArraySection.getItems().size(), this.encodedArraySectionOffset)
        writeMapItem(dexDataWriter, 8196, this.annotationSection.getItems().size(), this.annotationSectionOffset)
        writeMapItem(dexDataWriter, FragmentTransaction.TRANSIT_FRAGMENT_FADE, this.annotationSetSection.getItems().size() + (shouldCreateEmptyAnnotationSet() ? 1 : 0), this.annotationSetSectionOffset)
        writeMapItem(dexDataWriter, InputDeviceCompat.SOURCE_TOUCHSCREEN, this.numAnnotationSetRefItems, this.annotationSetRefSectionOffset)
        writeMapItem(dexDataWriter, 8198, this.numAnnotationDirectoryItems, this.annotationDirectorySectionOffset)
        writeMapItem(dexDataWriter, 8195, this.numDebugInfoItems, this.debugSectionOffset)
        writeMapItem(dexDataWriter, 8193, this.numCodeItemItems, this.codeSectionOffset)
        writeMapItem(dexDataWriter, 8192, this.numClassDataItems, this.classDataSectionOffset)
        if (shouldWriteHiddenApiRestrictions()) {
            writeMapItem(dexDataWriter, 61440, 1, this.hiddenApiRestrictionsOffset)
        }
        writeMapItem(dexDataWriter, 4096, 1, this.mapSectionOffset)
    }

    public final Unit writeMapItem(DexDataWriter dexDataWriter, Int i, Int i2, Int i3) throws IOException {
        if (i2 > 0) {
            dexDataWriter.writeUshort(i)
            dexDataWriter.writeUshort(0)
            dexDataWriter.writeInt(i2)
            dexDataWriter.writeInt(i3)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeMethodHandles(DexDataWriter dexDataWriter) throws IOException {
        Int itemIndex
        this.methodHandleSectionOffset = dexDataWriter.getPosition()
        Int i = 0
        for (Map.Entry entry : this.methodHandleSection.getItems()) {
            Int i2 = i + 1
            entry.setValue(Integer.valueOf(i))
            MethodHandleReference methodHandleReference = (MethodHandleReference) entry.getKey()
            dexDataWriter.writeUshort(methodHandleReference.getMethodHandleType())
            dexDataWriter.writeUshort(0)
            switch (methodHandleReference.getMethodHandleType()) {
                case 0:
                case 1:
                case 2:
                case 3:
                    itemIndex = this.fieldSection.getItemIndex(this.methodHandleSection.getFieldReference(methodHandleReference))
                    break
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    itemIndex = this.methodSection.getItemIndex(this.methodHandleSection.getMethodReference(methodHandleReference))
                    break
                default:
                    throw ExceptionWithContext("Invalid method handle type: %d", Integer.valueOf(methodHandleReference.getMethodHandleType()))
            }
            dexDataWriter.writeUshort(itemIndex)
            dexDataWriter.writeUshort(0)
            i = i2
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeMethods(DexDataWriter dexDataWriter) throws IOException {
        this.methodSectionOffset = dexDataWriter.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.methodSection.getItems())
        Collections.sort(arrayListNewArrayList, comparableKeyComparator())
        Int i = 0
        for (Map.Entry entry : arrayListNewArrayList) {
            Int i2 = i + 1
            entry.setValue(Integer.valueOf(i))
            MethodReference methodReference = (MethodReference) entry.getKey()
            dexDataWriter.writeUshort(this.typeSection.getItemIndex((CharSequence) this.methodSection.getDefiningClass(methodReference)))
            dexDataWriter.writeUshort(this.protoSection.getItemIndex(this.methodSection.getPrototype(methodReference)))
            dexDataWriter.writeInt(this.stringSection.getItemIndex((CharSequence) this.methodSection.getName(methodReference)))
            i = i2
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeProtos(DexDataWriter dexDataWriter) throws IOException {
        this.protoSectionOffset = dexDataWriter.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.protoSection.getItems())
        Collections.sort(arrayListNewArrayList, comparableKeyComparator())
        Int i = 0
        for (Map.Entry entry : arrayListNewArrayList) {
            Int i2 = i + 1
            entry.setValue(Integer.valueOf(i))
            MethodProtoReference methodProtoReference = (MethodProtoReference) entry.getKey()
            dexDataWriter.writeInt(this.stringSection.getItemIndex((CharSequence) this.protoSection.getShorty(methodProtoReference)))
            dexDataWriter.writeInt(this.typeSection.getItemIndex((CharSequence) this.protoSection.getReturnType(methodProtoReference)))
            dexDataWriter.writeInt(this.typeListSection.getNullableItemOffset(this.protoSection.getParameters(methodProtoReference)))
            i = i2
        }
    }

    public final Unit writeSectionInfo(DexDataWriter dexDataWriter, Int i, Int i2) throws IOException {
        dexDataWriter.writeInt(i)
        if (i > 0) {
            dexDataWriter.writeInt(i2)
        } else {
            dexDataWriter.writeInt(0)
        }
    }

    public final Unit writeStrings(DexDataWriter dexDataWriter, DexDataWriter dexDataWriter2) throws IOException {
        this.stringIndexSectionOffset = dexDataWriter.getPosition()
        this.stringDataSectionOffset = dexDataWriter2.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.stringSection.getItems())
        Collections.sort(arrayListNewArrayList, toStringKeyComparator)
        Int i = 0
        for (Map.Entry entry : arrayListNewArrayList) {
            Int i2 = i + 1
            entry.setValue(Integer.valueOf(i))
            dexDataWriter.writeInt(dexDataWriter2.getPosition())
            String string = ((CharSequence) entry.getKey()).toString()
            dexDataWriter2.writeUleb128(string.length())
            dexDataWriter2.writeString(string)
            dexDataWriter2.write(0)
            i = i2
        }
    }

    fun writeTo(DexDataStore dexDataStore) throws IOException {
        writeTo(dexDataStore, MemoryDeferredOutputStream.getFactory())
    }

    /* JADX WARN: Finally extract failed */
    fun writeTo(DexDataStore dexDataStore, DeferredOutputStreamFactory deferredOutputStreamFactory) throws IOException {
        try {
            Int dataSectionOffset = getDataSectionOffset()
            DexDataWriter dexDataWriterOutputAt = outputAt(dexDataStore, 0)
            DexDataWriter dexDataWriterOutputAt2 = outputAt(dexDataStore, R.styleable.AppCompatTheme_ratingBarStyleSmall)
            DexDataWriter dexDataWriterOutputAt3 = outputAt(dexDataStore, dataSectionOffset)
            try {
                writeStrings(dexDataWriterOutputAt2, dexDataWriterOutputAt3)
                writeTypes(dexDataWriterOutputAt2)
                writeTypeLists(dexDataWriterOutputAt3)
                writeProtos(dexDataWriterOutputAt2)
                writeFields(dexDataWriterOutputAt2)
                writeMethods(dexDataWriterOutputAt2)
                DexDataWriter dexDataWriterOutputAt4 = outputAt(dexDataStore, dexDataWriterOutputAt2.getPosition() + (this.classSection.getItemCount() * 32) + (this.callSiteSection.getItemCount() * 4))
                try {
                    writeMethodHandles(dexDataWriterOutputAt4)
                    dexDataWriterOutputAt4.close()
                    writeEncodedArrays(dexDataWriterOutputAt3)
                    DexDataWriter dexDataWriterOutputAt5 = outputAt(dexDataStore, dexDataWriterOutputAt2.getPosition() + (this.classSection.getItemCount() * 32))
                    try {
                        writeCallSites(dexDataWriterOutputAt5)
                        dexDataWriterOutputAt5.close()
                        writeAnnotations(dexDataWriterOutputAt3)
                        writeAnnotationSets(dexDataWriterOutputAt3)
                        writeAnnotationSetRefs(dexDataWriterOutputAt3)
                        writeAnnotationDirectories(dexDataWriterOutputAt3)
                        writeDebugAndCodeItems(dexDataWriterOutputAt3, deferredOutputStreamFactory.makeDeferredOutputStream())
                        writeClasses(dexDataStore, dexDataWriterOutputAt2, dexDataWriterOutputAt3)
                        writeMapItem(dexDataWriterOutputAt3)
                        writeHeader(dexDataWriterOutputAt, dataSectionOffset, dexDataWriterOutputAt3.getPosition())
                        dexDataWriterOutputAt.close()
                        dexDataWriterOutputAt2.close()
                        dexDataWriterOutputAt3.close()
                        updateSignature(dexDataStore)
                        updateChecksum(dexDataStore)
                    } catch (Throwable th) {
                        dexDataWriterOutputAt5.close()
                        throw th
                    }
                } catch (Throwable th2) {
                    dexDataWriterOutputAt4.close()
                    throw th2
                }
            } catch (Throwable th3) {
                dexDataWriterOutputAt.close()
                dexDataWriterOutputAt2.close()
                dexDataWriterOutputAt3.close()
                throw th3
            }
        } finally {
            dexDataStore.close()
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Unit writeTypeLists(DexDataWriter dexDataWriter) throws IOException {
        dexDataWriter.align()
        this.typeListSectionOffset = dexDataWriter.getPosition()
        for (Map.Entry entry : this.typeListSection.getItems()) {
            dexDataWriter.align()
            entry.setValue(Integer.valueOf(dexDataWriter.getPosition()))
            Collection types = this.typeListSection.getTypes(entry.getKey())
            dexDataWriter.writeInt(types.size())
            Iterator it = types.iterator()
            while (it.hasNext()) {
                dexDataWriter.writeUshort(this.typeSection.getItemIndex((CharSequence) it.next()))
            }
        }
    }

    public final Unit writeTypes(DexDataWriter dexDataWriter) throws IOException {
        this.typeSectionOffset = dexDataWriter.getPosition()
        ArrayList<Map.Entry> arrayListNewArrayList = Lists.newArrayList(this.typeSection.getItems())
        Collections.sort(arrayListNewArrayList, toStringKeyComparator)
        Int i = 0
        for (Map.Entry entry : arrayListNewArrayList) {
            entry.setValue(Integer.valueOf(i))
            dexDataWriter.writeInt(this.stringSection.getItemIndex((CharSequence) this.typeSection.getString((CharSequence) entry.getKey())))
            i++
        }
    }
}
