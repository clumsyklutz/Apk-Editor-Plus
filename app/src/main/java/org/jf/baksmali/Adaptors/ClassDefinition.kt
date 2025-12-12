package org.jf.baksmali.Adaptors

import java.io.IOException
import java.io.Writer
import java.util.HashSet
import java.util.List
import java.util.Set
import org.jf.baksmali.BaksmaliOptions
import org.jf.baksmali.formatter.BaksmaliFormatter
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedClassDef
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.Reference

class ClassDefinition {
    public final ClassDef classDef
    public final HashSet<String> fieldsSetInStaticConstructor
    public final BaksmaliFormatter formatter
    public final BaksmaliOptions options

    /* renamed from: org.jf.baksmali.Adaptors.ClassDefinition$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Opcode

        static {
            Array<Int> iArr = new Int[Opcode.values().length]
            $SwitchMap$org$jf$dexlib2$Opcode = iArr
            try {
                iArr[Opcode.SPUT.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_BOOLEAN.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_BYTE.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_CHAR.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_OBJECT.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_SHORT.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_WIDE.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    constructor(BaksmaliOptions baksmaliOptions, ClassDef classDef) {
        this.options = baksmaliOptions
        this.classDef = classDef
        this.formatter = BaksmaliFormatter(baksmaliOptions.implicitReferences ? classDef.getType() : null)
        this.fieldsSetInStaticConstructor = findFieldsSetInStaticConstructor(classDef)
    }

    public final HashSet<String> findFieldsSetInStaticConstructor(ClassDef classDef) {
        MethodImplementation implementation
        HashSet<String> hashSet = new HashSet<>()
        for (Method method : classDef.getDirectMethods()) {
            if (method.getName().equals("<clinit>") && (implementation = method.getImplementation()) != null) {
                for (Instruction instruction : implementation.getInstructions()) {
                    switch (AnonymousClass1.$SwitchMap$org$jf$dexlib2$Opcode[instruction.getOpcode().ordinal()]) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                            FieldReference fieldReference = (FieldReference) ((Instruction21c) instruction).getReference()
                            try {
                                fieldReference.validateReference()
                                if (fieldReference.getDefiningClass().equals(classDef.getType())) {
                                    hashSet.add(this.formatter.getShortFieldDescriptor(fieldReference))
                                    break
                                } else {
                                    break
                                }
                            } catch (Reference.InvalidReferenceException unused) {
                                break
                            }
                    }
                }
            }
        }
        return hashSet
    }

    fun getCommentingWriter(BaksmaliWriter baksmaliWriter) {
        return this.formatter.getWriter((Writer) CommentingIndentingWriter(baksmaliWriter.indentingWriter()))
    }

    fun getFormatter() {
        return this.formatter
    }

    public final Unit writeAccessFlags(BaksmaliWriter baksmaliWriter) throws IOException {
        for (AccessFlags accessFlags : AccessFlags.getAccessFlagsForClass(this.classDef.getAccessFlags())) {
            baksmaliWriter.write(accessFlags.toString())
            baksmaliWriter.write(32)
        }
    }

    public final Unit writeAnnotations(BaksmaliWriter baksmaliWriter) throws IOException {
        Set<? extends Annotation> annotations = this.classDef.getAnnotations()
        if (annotations.size() != 0) {
            baksmaliWriter.write("\n\n")
            baksmaliWriter.write("# annotations\n")
            AnnotationFormatter.writeTo(baksmaliWriter, annotations)
        }
    }

    public final Unit writeClass(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".class ")
        writeAccessFlags(baksmaliWriter)
        baksmaliWriter.writeType(this.classDef.getType())
        baksmaliWriter.write(10)
    }

    public final Set<String> writeDirectMethods(BaksmaliWriter baksmaliWriter) throws IOException {
        BaksmaliWriter commentingWriter
        HashSet hashSet = HashSet()
        ClassDef classDef = this.classDef
        Boolean z = false
        for (Method method : classDef is DexBackedClassDef ? ((DexBackedClassDef) classDef).getDirectMethods(false) : classDef.getDirectMethods()) {
            if (!z) {
                baksmaliWriter.write("\n\n")
                baksmaliWriter.write("# direct methods")
                z = true
            }
            baksmaliWriter.write(10)
            if (hashSet.add(this.formatter.getShortMethodDescriptor(method))) {
                commentingWriter = baksmaliWriter
            } else {
                baksmaliWriter.write("# duplicate method ignored\n")
                commentingWriter = getCommentingWriter(baksmaliWriter)
            }
            MethodImplementation implementation = method.getImplementation()
            if (implementation == null) {
                MethodDefinition.writeEmptyMethodTo(commentingWriter, method, this)
            } else {
                MethodDefinition(this, method, implementation).writeTo(commentingWriter)
            }
        }
        return hashSet
    }

    public final Unit writeInstanceFields(BaksmaliWriter baksmaliWriter, Set<String> set) throws IOException {
        BaksmaliWriter commentingWriter
        HashSet hashSet = HashSet()
        ClassDef classDef = this.classDef
        Boolean z = false
        for (Field field : classDef is DexBackedClassDef ? ((DexBackedClassDef) classDef).getInstanceFields(false) : classDef.getInstanceFields()) {
            if (!z) {
                baksmaliWriter.write("\n\n")
                baksmaliWriter.write("# instance fields")
                z = true
            }
            baksmaliWriter.write(10)
            String shortFieldDescriptor = this.formatter.getShortFieldDescriptor(field)
            if (hashSet.add(shortFieldDescriptor)) {
                if (set.contains(shortFieldDescriptor)) {
                    System.err.println(String.format("Duplicate static+instance field found: %s->%s", this.classDef.getType(), shortFieldDescriptor))
                    System.err.println("You will need to rename one of these fields, including all references.")
                    baksmaliWriter.write("# There is both a static and instance field with this signature.\n# You will need to rename one of these fields, including all references.\n")
                }
                commentingWriter = baksmaliWriter
            } else {
                baksmaliWriter.write("# duplicate field ignored\n")
                commentingWriter = getCommentingWriter(baksmaliWriter)
                System.err.println(String.format("Ignoring duplicate field: %s->%s", this.classDef.getType(), shortFieldDescriptor))
            }
            FieldDefinition.writeTo(commentingWriter, field, false)
        }
    }

    public final Unit writeInterfaces(BaksmaliWriter baksmaliWriter) throws IOException {
        List<String> interfaces = this.classDef.getInterfaces()
        if (interfaces.size() != 0) {
            baksmaliWriter.write(10)
            baksmaliWriter.write("# interfaces\n")
            for (String str : interfaces) {
                baksmaliWriter.write(".implements ")
                baksmaliWriter.writeType(str)
                baksmaliWriter.write(10)
            }
        }
    }

    public final Unit writeSourceFile(BaksmaliWriter baksmaliWriter) throws IOException {
        String sourceFile = this.classDef.getSourceFile()
        if (sourceFile != null) {
            baksmaliWriter.write(".source ")
            baksmaliWriter.writeQuotedString(sourceFile)
            baksmaliWriter.write("\n")
        }
    }

    public final Set<String> writeStaticFields(BaksmaliWriter baksmaliWriter) throws IOException {
        Boolean zContains
        BaksmaliWriter commentingWriter
        HashSet hashSet = HashSet()
        ClassDef classDef = this.classDef
        Boolean z = false
        for (Field field : classDef is DexBackedClassDef ? ((DexBackedClassDef) classDef).getStaticFields(false) : classDef.getStaticFields()) {
            if (!z) {
                baksmaliWriter.write("\n\n")
                baksmaliWriter.write("# static fields")
                z = true
            }
            baksmaliWriter.write(10)
            String shortFieldDescriptor = this.formatter.getShortFieldDescriptor(field)
            if (hashSet.add(shortFieldDescriptor)) {
                zContains = this.fieldsSetInStaticConstructor.contains(shortFieldDescriptor)
                commentingWriter = baksmaliWriter
            } else {
                baksmaliWriter.write("# duplicate field ignored\n")
                commentingWriter = getCommentingWriter(baksmaliWriter)
                System.err.println(String.format("Ignoring duplicate field: %s->%s", this.classDef.getType(), shortFieldDescriptor))
                zContains = false
            }
            FieldDefinition.writeTo(commentingWriter, field, zContains)
        }
        return hashSet
    }

    public final Unit writeSuper(BaksmaliWriter baksmaliWriter) throws IOException {
        String superclass = this.classDef.getSuperclass()
        if (superclass != null) {
            baksmaliWriter.write(".super ")
            baksmaliWriter.writeType(superclass)
            baksmaliWriter.write(10)
        }
    }

    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        writeClass(baksmaliWriter)
        writeSuper(baksmaliWriter)
        writeSourceFile(baksmaliWriter)
        writeInterfaces(baksmaliWriter)
        writeAnnotations(baksmaliWriter)
        writeInstanceFields(baksmaliWriter, writeStaticFields(baksmaliWriter))
        writeVirtualMethods(baksmaliWriter, writeDirectMethods(baksmaliWriter))
    }

    public final Unit writeVirtualMethods(BaksmaliWriter baksmaliWriter, Set<String> set) throws IOException {
        BaksmaliWriter commentingWriter
        HashSet hashSet = HashSet()
        ClassDef classDef = this.classDef
        Boolean z = false
        for (Method method : classDef is DexBackedClassDef ? ((DexBackedClassDef) classDef).getVirtualMethods(false) : classDef.getVirtualMethods()) {
            if (!z) {
                baksmaliWriter.write("\n\n")
                baksmaliWriter.write("# virtual methods")
                z = true
            }
            baksmaliWriter.write(10)
            String shortMethodDescriptor = this.formatter.getShortMethodDescriptor(method)
            if (hashSet.add(shortMethodDescriptor)) {
                if (set.contains(shortMethodDescriptor)) {
                    baksmaliWriter.write("# There is both a direct and virtual method with this signature.\n# You will need to rename one of these methods, including all references.\n")
                    System.err.println(String.format("Duplicate direct+virtual method found: %s->%s", this.classDef.getType(), shortMethodDescriptor))
                    System.err.println("You will need to rename one of these methods, including all references.")
                }
                commentingWriter = baksmaliWriter
            } else {
                baksmaliWriter.write("# duplicate method ignored\n")
                commentingWriter = getCommentingWriter(baksmaliWriter)
            }
            MethodImplementation implementation = method.getImplementation()
            if (implementation == null) {
                MethodDefinition.writeEmptyMethodTo(commentingWriter, method, this)
            } else {
                MethodDefinition(this, method, implementation).writeTo(commentingWriter)
            }
        }
    }
}
