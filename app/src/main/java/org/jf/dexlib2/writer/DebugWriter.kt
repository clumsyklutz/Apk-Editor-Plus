package org.jf.dexlib2.writer

import java.io.IOException
import java.lang.CharSequence
import org.jf.util.ExceptionWithContext

class DebugWriter<StringKey extends CharSequence, TypeKey extends CharSequence> {
    public Int currentAddress
    public Int currentLine
    public final StringSection<StringKey, ?> stringSection
    public final TypeSection<StringKey, TypeKey, ?> typeSection
    public final DexDataWriter writer

    constructor(StringSection<StringKey, ?> stringSection, TypeSection<StringKey, TypeKey, ?> typeSection, DexDataWriter dexDataWriter) {
        this.stringSection = stringSection
        this.typeSection = typeSection
        this.writer = dexDataWriter
    }

    fun reset(Int i) {
        this.currentAddress = 0
        this.currentLine = i
    }

    public final Unit writeAdvanceLine(Int i) throws IOException {
        Int i2 = i - this.currentLine
        if (i2 != 0) {
            this.writer.write(2)
            this.writer.writeSleb128(i2)
            this.currentLine = i
        }
    }

    public final Unit writeAdvancePC(Int i) throws IOException {
        Int i2 = i - this.currentAddress
        if (i2 > 0) {
            this.writer.write(1)
            this.writer.writeUleb128(i2)
            this.currentAddress = i
        }
    }

    fun writeEndLocal(Int i, Int i2) throws IOException {
        writeAdvancePC(i)
        this.writer.write(5)
        this.writer.writeUleb128(i2)
    }

    fun writeEpilogueBegin(Int i) throws IOException {
        writeAdvancePC(i)
        this.writer.write(8)
    }

    fun writeLineNumber(Int i, Int i2) throws IOException {
        Int i3 = i2 - this.currentLine
        Int i4 = i - this.currentAddress
        if (i4 < 0) {
            throw ExceptionWithContext("debug info items must have non-decreasing code addresses", new Object[0])
        }
        if (i3 < -4 || i3 > 10) {
            writeAdvanceLine(i2)
            i3 = 0
        }
        if ((i3 < 2 && i4 > 16) || (i3 > 1 && i4 > 15)) {
            writeAdvancePC(i)
            i4 = 0
        }
        writeSpecialOpcode(i3, i4)
    }

    fun writePrologueEnd(Int i) throws IOException {
        writeAdvancePC(i)
        this.writer.write(7)
    }

    fun writeRestartLocal(Int i, Int i2) throws IOException {
        writeAdvancePC(i)
        this.writer.write(6)
        this.writer.writeUleb128(i2)
    }

    fun writeSetSourceFile(Int i, StringKey stringkey) throws IOException {
        writeAdvancePC(i)
        this.writer.write(9)
        this.writer.writeUleb128(this.stringSection.getNullableItemIndex(stringkey) + 1)
    }

    public final Unit writeSpecialOpcode(Int i, Int i2) throws IOException {
        this.writer.write((Byte) ((i2 * 15) + 10 + i + 4))
        this.currentLine += i
        this.currentAddress += i2
    }

    fun writeStartLocal(Int i, Int i2, StringKey stringkey, TypeKey typekey, StringKey stringkey2) throws IOException {
        Int nullableItemIndex = this.stringSection.getNullableItemIndex(stringkey)
        Int nullableItemIndex2 = this.typeSection.getNullableItemIndex(typekey)
        Int nullableItemIndex3 = this.stringSection.getNullableItemIndex(stringkey2)
        writeAdvancePC(i)
        if (nullableItemIndex3 == -1) {
            this.writer.write(3)
            this.writer.writeUleb128(i2)
            this.writer.writeUleb128(nullableItemIndex + 1)
            this.writer.writeUleb128(nullableItemIndex2 + 1)
            return
        }
        this.writer.write(4)
        this.writer.writeUleb128(i2)
        this.writer.writeUleb128(nullableItemIndex + 1)
        this.writer.writeUleb128(nullableItemIndex2 + 1)
        this.writer.writeUleb128(nullableItemIndex3 + 1)
    }
}
