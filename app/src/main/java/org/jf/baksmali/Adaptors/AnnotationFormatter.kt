package org.jf.baksmali.Adaptors

import java.io.IOException
import java.util.Collection
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.AnnotationVisibility
import org.jf.dexlib2.iface.Annotation

class AnnotationFormatter {
    fun writeTo(BaksmaliWriter baksmaliWriter, Collection<? extends Annotation> collection) throws IOException {
        Boolean z = true
        for (Annotation annotation : collection) {
            if (!z) {
                baksmaliWriter.write(10)
            }
            z = false
            writeTo(baksmaliWriter, annotation)
        }
    }

    fun writeTo(BaksmaliWriter baksmaliWriter, Annotation annotation) throws IOException {
        baksmaliWriter.write(".annotation ")
        baksmaliWriter.write(AnnotationVisibility.getVisibility(annotation.getVisibility()))
        baksmaliWriter.write(32)
        baksmaliWriter.write(annotation.getType())
        baksmaliWriter.write(10)
        baksmaliWriter.writeAnnotationElements(annotation.getElements())
        baksmaliWriter.write(".end annotation\n")
    }
}
