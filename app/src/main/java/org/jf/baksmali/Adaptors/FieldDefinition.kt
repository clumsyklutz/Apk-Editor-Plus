package org.jf.baksmali.Adaptors

import java.io.IOException
import java.util.Iterator
import java.util.Set
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.util.EncodedValueUtils

class FieldDefinition {
    fun writeAccessFlagsAndRestrictions(BaksmaliWriter baksmaliWriter, Int i, Set<HiddenApiRestriction> set) throws IOException {
        for (AccessFlags accessFlags : AccessFlags.getAccessFlagsForField(i)) {
            baksmaliWriter.write(accessFlags.toString())
            baksmaliWriter.write(32)
        }
        Iterator<HiddenApiRestriction> it = set.iterator()
        while (it.hasNext()) {
            baksmaliWriter.write(it.next().toString())
            baksmaliWriter.write(32)
        }
    }

    fun writeTo(BaksmaliWriter baksmaliWriter, Field field, Boolean z) throws IOException {
        EncodedValue initialValue = field.getInitialValue()
        Int accessFlags = field.getAccessFlags()
        if (z && AccessFlags.STATIC.isSet(accessFlags) && AccessFlags.FINAL.isSet(accessFlags) && initialValue != null) {
            if (EncodedValueUtils.isDefaultValue(initialValue)) {
                initialValue = null
            } else {
                baksmaliWriter.write("# The value of this static final field might be set in the static constructor\n")
            }
        }
        baksmaliWriter.write(".field ")
        writeAccessFlagsAndRestrictions(baksmaliWriter, field.getAccessFlags(), field.getHiddenApiRestrictions())
        baksmaliWriter.writeSimpleName(field.getName())
        baksmaliWriter.write(58)
        baksmaliWriter.writeType(field.getType())
        if (initialValue != null) {
            baksmaliWriter.write(" = ")
            baksmaliWriter.writeEncodedValue(initialValue)
        }
        baksmaliWriter.write(10)
        Set<? extends Annotation> annotations = field.getAnnotations()
        if (annotations.size() > 0) {
            baksmaliWriter.indent(4)
            AnnotationFormatter.writeTo(baksmaliWriter, annotations)
            baksmaliWriter.deindent(4)
            baksmaliWriter.write(".end field\n")
        }
    }
}
