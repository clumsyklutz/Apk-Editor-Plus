package org.jf.dexlib2.formatter

import java.io.IOException
import java.io.StringWriter
import java.io.Writer
import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.value.EncodedValue

class DexFormatter {
    public static val INSTANCE = DexFormatter()

    fun getCallSite(CallSiteReference callSiteReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeCallSite(callSiteReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getEncodedValue(EncodedValue encodedValue) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeEncodedValue(encodedValue)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getFieldDescriptor(FieldReference fieldReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeFieldDescriptor(fieldReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getMethodDescriptor(MethodReference methodReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeMethodDescriptor(methodReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getMethodHandle(MethodHandleReference methodHandleReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeMethodHandle(methodHandleReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getMethodProtoDescriptor(MethodProtoReference methodProtoReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeMethodProtoDescriptor(methodProtoReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getShortFieldDescriptor(FieldReference fieldReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeShortFieldDescriptor(fieldReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getShortMethodDescriptor(MethodReference methodReference) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeShortMethodDescriptor(methodReference)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getType(CharSequence charSequence) {
        StringWriter stringWriter = StringWriter()
        try {
            getWriter(stringWriter).writeType(charSequence)
            return stringWriter.toString()
        } catch (IOException unused) {
            throw AssertionError("Unexpected IOException")
        }
    }

    fun getWriter(Writer writer) {
        return DexFormattedWriter(writer)
    }
}
