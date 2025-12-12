package org.jf.dexlib2.writer

import java.io.IOException
import java.lang.CharSequence
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.builder.MutableMethodImplementation
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.TryBlock
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.instruction.Instruction

public interface ClassSection<StringKey extends CharSequence, TypeKey extends CharSequence, TypeListKey, ClassKey, FieldKey, MethodKey, AnnotationSetKey, EncodedArrayKey> extends IndexSection<ClassKey> {
    Int getAccessFlags(ClassKey classkey)

    Int getAnnotationDirectoryOffset(ClassKey classkey)

    Int getAnnotationSetRefListOffset(MethodKey methodkey)

    AnnotationSetKey getClassAnnotations(ClassKey classkey)

    Map.Entry<? extends ClassKey, Integer> getClassEntryByType(TypeKey typekey)

    Int getCodeItemOffset(MethodKey methodkey)

    Iterable<? extends DebugItem> getDebugItems(MethodKey methodkey)

    TypeKey getExceptionType(ExceptionHandler exceptionHandler)

    Int getFieldAccessFlags(FieldKey fieldkey)

    AnnotationSetKey getFieldAnnotations(FieldKey fieldkey)

    Set<HiddenApiRestriction> getFieldHiddenApiRestrictions(FieldKey fieldkey)

    Iterable<? extends Instruction> getInstructions(MethodKey methodkey)

    TypeListKey getInterfaces(ClassKey classkey)

    Int getMethodAccessFlags(MethodKey methodkey)

    AnnotationSetKey getMethodAnnotations(MethodKey methodkey)

    Set<HiddenApiRestriction> getMethodHiddenApiRestrictions(MethodKey methodkey)

    List<? extends AnnotationSetKey> getParameterAnnotations(MethodKey methodkey)

    Iterable<? extends StringKey> getParameterNames(MethodKey methodkey)

    Int getRegisterCount(MethodKey methodkey)

    Collection<? extends ClassKey> getSortedClasses()

    Collection<? extends MethodKey> getSortedDirectMethods(ClassKey classkey)

    Collection<? extends FieldKey> getSortedFields(ClassKey classkey)

    Collection<? extends FieldKey> getSortedInstanceFields(ClassKey classkey)

    Collection<? extends MethodKey> getSortedMethods(ClassKey classkey)

    Collection<? extends FieldKey> getSortedStaticFields(ClassKey classkey)

    Collection<? extends MethodKey> getSortedVirtualMethods(ClassKey classkey)

    StringKey getSourceFile(ClassKey classkey)

    EncodedArrayKey getStaticInitializers(ClassKey classkey)

    TypeKey getSuperclass(ClassKey classkey)

    List<? extends TryBlock<? extends ExceptionHandler>> getTryBlocks(MethodKey methodkey)

    TypeKey getType(ClassKey classkey)

    MutableMethodImplementation makeMutableMethodImplementation(MethodKey methodkey)

    Unit setAnnotationDirectoryOffset(ClassKey classkey, Int i)

    Unit setAnnotationSetRefListOffset(MethodKey methodkey, Int i)

    Unit setCodeItemOffset(MethodKey methodkey, Int i)

    Unit writeDebugItem(DebugWriter<StringKey, TypeKey> debugWriter, DebugItem debugItem) throws IOException
}
