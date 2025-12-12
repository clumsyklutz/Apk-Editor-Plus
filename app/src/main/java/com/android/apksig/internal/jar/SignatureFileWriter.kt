package com.android.apksig.internal.jar

import java.io.IOException
import java.io.OutputStream
import java.util.SortedMap
import java.util.jar.Attributes

abstract class SignatureFileWriter {
    fun writeIndividualSection(OutputStream outputStream, String str, Attributes attributes) throws IOException {
        ManifestWriter.writeIndividualSection(outputStream, str, attributes)
    }

    fun writeMainSection(OutputStream outputStream, Attributes attributes) throws IOException {
        String value = attributes.getValue(Attributes.Name.SIGNATURE_VERSION)
        if (value == null) {
            throw IllegalArgumentException("Mandatory " + Attributes.Name.SIGNATURE_VERSION + " attribute missing")
        }
        ManifestWriter.writeAttribute(outputStream, Attributes.Name.SIGNATURE_VERSION, value)
        if (attributes.size() > 1) {
            SortedMap<String, String> attributesSortedByName = ManifestWriter.getAttributesSortedByName(attributes)
            attributesSortedByName.remove(Attributes.Name.SIGNATURE_VERSION.toString())
            ManifestWriter.writeAttributes(outputStream, attributesSortedByName)
        }
        writeSectionDelimiter(outputStream)
    }

    fun writeSectionDelimiter(OutputStream outputStream) throws IOException {
        ManifestWriter.writeSectionDelimiter(outputStream)
    }
}
