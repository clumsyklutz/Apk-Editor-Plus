package org.jf.dexlib2.dexbacked

import android.annotation.TargetApi
import com.gmail.heagoo.neweditor.Token
import com.google.common.base.Function
import com.google.common.base.Predicate
import com.google.common.collect.Iterators
import com.google.common.io.ByteStreams
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.AbstractList
import java.util.Iterator
import java.util.List
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.OatFile
import org.jf.dexlib2.iface.MultiDexContainer
import org.jf.dexlib2.util.DexUtil
import org.jf.util.AbstractForwardSequentialList

class OatFile extends DexBuffer implements MultiDexContainer<DexBackedDexFile> {
    public static final Array<Byte> ELF_MAGIC = {Token.END, 69, 76, 70}
    public static final Array<Byte> OAT_MAGIC = {111, 97, 116, 10}
    public final Boolean is64bit
    public final OatHeader oatHeader
    public final Opcodes opcodes
    public final VdexProvider vdexProvider

    class DexEntryIterator implements Iterator<OatDexEntry> {
        public Int index
        public Int offset

        constructor() {
            this.index = 0
            this.offset = OatFile.this.oatHeader.getDexListStart()
        }

        @Override // java.util.Iterator
        fun hasNext() {
            return this.index < OatFile.this.oatHeader.getDexFileCount()
        }

        @Override // java.util.Iterator
        fun next() {
            Array<Byte> vdex
            while (hasNext()) {
                Int smallUint = OatFile.this.readSmallUint(this.offset)
                Int i = this.offset + 4
                this.offset = i
                String str = String(OatFile.this.buf, i, smallUint, Charset.forName("US-ASCII"))
                Int i2 = this.offset + smallUint
                this.offset = i2
                Int i3 = i2 + 4
                this.offset = i3
                Int smallUint2 = OatFile.this.readSmallUint(i3)
                this.offset += 4
                if (OatFile.this.getOatVersion() < 87 || OatFile.this.vdexProvider == null || OatFile.this.vdexProvider.getVdex() == null) {
                    OatFile oatFile = OatFile.this
                    Array<Byte> bArr = oatFile.buf
                    smallUint2 += oatFile.oatHeader.headerOffset
                    vdex = bArr
                } else {
                    vdex = OatFile.this.vdexProvider.getVdex()
                }
                if (OatFile.this.getOatVersion() >= 75) {
                    this.offset += 4
                }
                if (OatFile.this.getOatVersion() >= 73) {
                    this.offset += 4
                }
                if (OatFile.this.getOatVersion() >= 131) {
                    this.offset += 4
                }
                if (OatFile.this.getOatVersion() >= 127) {
                    this.offset += 4
                }
                if (OatFile.this.getOatVersion() >= 135) {
                    this.offset += 8
                }
                if (OatFile.this.getOatVersion() < 75) {
                    this.offset += OatFile.this.readSmallUint(smallUint2 + 96) * 4
                }
                this.index++
                if (OatFile.this.getOatVersion() < 138 || smallUint2 != 0) {
                    return OatFile.this.OatDexEntry(str, vdex, smallUint2)
                }
            }
            return null
        }

        @Override // java.util.Iterator
        fun remove() {
            throw UnsupportedOperationException()
        }
    }

    public static class InvalidOatFileException extends RuntimeException {
        constructor(String str) {
            super(str)
        }
    }

    public static class NotAnOatFileException extends RuntimeException {
    }

    class OatCDexFile extends CDexBackedDexFile {
        constructor(OatFile oatFile, Array<Byte> bArr, Int i) {
            super(oatFile.opcodes, bArr, i)
        }
    }

    class OatDexEntry implements MultiDexContainer.DexEntry<DexBackedDexFile> {
        public final Array<Byte> buf
        public final Int dexOffset
        public final String entryName

        constructor(String str, Array<Byte> bArr, Int i) {
            this.entryName = str
            this.buf = bArr
            this.dexOffset = i
        }

        @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
        fun getDexFile() {
            if (CDexBackedDexFile.isCdex(this.buf, this.dexOffset)) {
                return OatCDexFile(OatFile.this, this.buf, this.dexOffset)
            }
            try {
                DexUtil.verifyDexHeader(this.buf, this.dexOffset)
                return OatDexFile(OatFile.this, this.buf, this.dexOffset)
            } catch (DexBackedDexFile.NotADexFile e) {
                if (OatFile.this.getOatVersion() >= 87) {
                    throw new DexFileFactory.DexFileNotFoundException(e, "Could not locate the embedded dex file %s. Is the vdex file missing?", this.entryName)
                }
                throw new DexFileFactory.DexFileNotFoundException(e, "The embedded dex file %s does not appear to be a valid dex file.", this.entryName)
            }
        }

        fun getEntryName() {
            return this.entryName
        }
    }

    class OatDexFile extends DexBackedDexFile {
        constructor(OatFile oatFile, Array<Byte> bArr, Int i) {
            super(oatFile.opcodes, bArr, i)
        }
    }

    class OatHeader {
        public final Int headerOffset
        public final Int keyValueStoreOffset

        constructor(Int i) {
            this.headerOffset = i
            if (getVersion() >= 170) {
                this.keyValueStoreOffset = 56
                return
            }
            if (getVersion() >= 166) {
                this.keyValueStoreOffset = 64
                return
            }
            if (getVersion() >= 162) {
                this.keyValueStoreOffset = 68
            } else if (getVersion() >= 127) {
                this.keyValueStoreOffset = 76
            } else {
                this.keyValueStoreOffset = 72
            }
        }

        fun getDexFileCount() {
            return OatFile.this.readSmallUint(this.headerOffset + 20)
        }

        fun getDexListStart() {
            Int i
            Int headerSize
            if (getVersion() >= 127) {
                i = this.headerOffset
                headerSize = OatFile.this.readSmallUint(i + 24)
            } else {
                i = this.headerOffset
                headerSize = getHeaderSize()
            }
            return i + headerSize
        }

        fun getHeaderSize() {
            if (getVersion() >= 56) {
                return this.keyValueStoreOffset + getKeyValueStoreSize()
            }
            throw IllegalStateException("Unsupported oat version")
        }

        fun getKeyValueStoreSize() {
            if (getVersion() < 56) {
                throw IllegalStateException("Unsupported oat version")
            }
            return OatFile.this.readSmallUint(this.headerOffset + (this.keyValueStoreOffset - 4))
        }

        fun getVersion() {
            return Integer.valueOf(String(OatFile.this.buf, this.headerOffset + 4, 3)).intValue()
        }

        fun isValid() {
            for (Int i = 0; i < OatFile.OAT_MAGIC.length; i++) {
                if (OatFile.this.buf[this.headerOffset + i] != OatFile.OAT_MAGIC[i]) {
                    return false
                }
            }
            for (Int i2 = 4; i2 < 7; i2++) {
                Array<Byte> bArr = OatFile.this.buf
                Int i3 = this.headerOffset
                if (bArr[i3 + i2] < 48 || bArr[i3 + i2] > 57) {
                    return false
                }
            }
            return OatFile.this.buf[this.headerOffset + 7] == 0
        }
    }

    abstract class SectionHeader {
        public final Int offset

        constructor(Int i) {
            this.offset = i
        }

        public abstract Long getAddress()

        public abstract Int getEntrySize()

        public abstract Int getLink()

        public abstract Int getOffset()

        public abstract Int getSize()

        fun getType() {
            return OatFile.this.readInt(this.offset + 4)
        }
    }

    class SectionHeader32Bit extends SectionHeader {
        constructor(Int i) {
            super(i)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getAddress() {
            return OatFile.this.readInt(this.offset + 12) & 4294967295L
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getEntrySize() {
            return OatFile.this.readSmallUint(this.offset + 36)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getLink() {
            return OatFile.this.readSmallUint(this.offset + 24)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getOffset() {
            return OatFile.this.readSmallUint(this.offset + 16)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getSize() {
            return OatFile.this.readSmallUint(this.offset + 20)
        }
    }

    class SectionHeader64Bit extends SectionHeader {
        constructor(Int i) {
            super(i)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getAddress() {
            return OatFile.this.readLong(this.offset + 16)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getEntrySize() {
            return OatFile.this.readLongAsSmallUint(this.offset + 56)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getLink() {
            return OatFile.this.readSmallUint(this.offset + 40)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getOffset() {
            return OatFile.this.readLongAsSmallUint(this.offset + 24)
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.SectionHeader
        fun getSize() {
            return OatFile.this.readLongAsSmallUint(this.offset + 32)
        }
    }

    class StringTable {
        public final Int offset
        public final Int size

        constructor(SectionHeader sectionHeader) {
            Int offset = sectionHeader.getOffset()
            this.offset = offset
            Int size = sectionHeader.getSize()
            this.size = size
            if (offset + size > OatFile.this.buf.length) {
                throw InvalidOatFileException("String table extends past end of file")
            }
        }

        fun getString(Int i) {
            if (i >= this.size) {
                throw InvalidOatFileException("String index is out of bounds")
            }
            Int i2 = this.offset + i
            Int i3 = i2
            do {
                Array<Byte> bArr = OatFile.this.buf
                if (bArr[i3] == 0) {
                    return String(bArr, i2, i3 - i2, Charset.forName("US-ASCII"))
                }
                i3++
            } while (i3 < this.offset + this.size);
            throw InvalidOatFileException("String extends past end of string table")
        }
    }

    class SymbolTable {
        public final Int entryCount
        public final Int entrySize
        public final Int offset
        public final StringTable stringTable

        abstract class Symbol {
            public final Int offset

            constructor(Int i) {
                this.offset = i
            }

            fun getFileOffset() {
                try {
                    SectionHeader sectionHeader = (SectionHeader) OatFile.this.getSections().get(getSectionIndex())
                    Long address = sectionHeader.getAddress()
                    Int offset = sectionHeader.getOffset()
                    Int size = sectionHeader.getSize()
                    Long value = getValue()
                    if (value < address || value >= size + address) {
                        throw InvalidOatFileException("symbol address lies outside it's associated section")
                    }
                    return (Int) (offset + (getValue() - address))
                } catch (IndexOutOfBoundsException unused) {
                    throw InvalidOatFileException("Section index for symbol is out of bounds")
                }
            }

            public abstract String getName()

            public abstract Int getSectionIndex()

            public abstract Long getValue()
        }

        class Symbol32 extends Symbol {
            constructor(Int i) {
                super(i)
            }

            @Override // org.jf.dexlib2.dexbacked.OatFile.SymbolTable.Symbol
            fun getName() {
                return SymbolTable.this.stringTable.getString(OatFile.this.readSmallUint(this.offset))
            }

            @Override // org.jf.dexlib2.dexbacked.OatFile.SymbolTable.Symbol
            fun getSectionIndex() {
                return OatFile.this.readUshort(this.offset + 14)
            }

            @Override // org.jf.dexlib2.dexbacked.OatFile.SymbolTable.Symbol
            fun getValue() {
                return OatFile.this.readSmallUint(this.offset + 4)
            }
        }

        class Symbol64 extends Symbol {
            constructor(Int i) {
                super(i)
            }

            @Override // org.jf.dexlib2.dexbacked.OatFile.SymbolTable.Symbol
            fun getName() {
                return SymbolTable.this.stringTable.getString(OatFile.this.readSmallUint(this.offset))
            }

            @Override // org.jf.dexlib2.dexbacked.OatFile.SymbolTable.Symbol
            fun getSectionIndex() {
                return OatFile.this.readUshort(this.offset + 6)
            }

            @Override // org.jf.dexlib2.dexbacked.OatFile.SymbolTable.Symbol
            fun getValue() {
                return OatFile.this.readLong(this.offset + 8)
            }
        }

        constructor(SectionHeader sectionHeader) {
            try {
                this.stringTable = OatFile.this.StringTable((SectionHeader) OatFile.this.getSections().get(sectionHeader.getLink()))
                Int offset = sectionHeader.getOffset()
                this.offset = offset
                Int entrySize = sectionHeader.getEntrySize()
                this.entrySize = entrySize
                Int size = sectionHeader.getSize() / entrySize
                this.entryCount = size
                if (offset + (size * entrySize) > OatFile.this.buf.length) {
                    throw InvalidOatFileException("Symbol table extends past end of file")
                }
            } catch (IndexOutOfBoundsException unused) {
                throw InvalidOatFileException("String table section index is invalid")
            }
        }

        public List<Symbol> getSymbols() {
            return new AbstractList<Symbol>() { // from class: org.jf.dexlib2.dexbacked.OatFile.SymbolTable.1
                @Override // java.util.AbstractList, java.util.List
                fun get(Int i) {
                    if (i < 0 || i >= SymbolTable.this.entryCount) {
                        throw IndexOutOfBoundsException()
                    }
                    if (OatFile.this.is64bit) {
                        SymbolTable symbolTable = SymbolTable.this
                        return symbolTable.Symbol64(symbolTable.offset + (i * SymbolTable.this.entrySize))
                    }
                    SymbolTable symbolTable2 = SymbolTable.this
                    return symbolTable2.Symbol32(symbolTable2.offset + (i * SymbolTable.this.entrySize))
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                fun size() {
                    return SymbolTable.this.entryCount
                }
            }
        }
    }

    public interface VdexProvider {
        Array<Byte> getVdex()
    }

    constructor(Array<Byte> bArr, VdexProvider vdexProvider) {
        super(bArr)
        if (bArr.length < 52) {
            throw NotAnOatFileException()
        }
        verifyMagic(bArr)
        if (bArr[4] == 1) {
            this.is64bit = false
        } else {
            if (bArr[4] != 2) {
                throw InvalidOatFileException(String.format("Invalid word-size value: %x", Byte.valueOf(bArr[5])))
            }
            this.is64bit = true
        }
        OatHeader oatHeader = null
        Iterator<SymbolTable.Symbol> it = getSymbolTable().getSymbols().iterator()
        while (true) {
            if (!it.hasNext()) {
                break
            }
            SymbolTable.Symbol next = it.next()
            if (next.getName().equals("oatdata")) {
                oatHeader = OatHeader(next.getFileOffset())
                break
            }
        }
        if (oatHeader == null) {
            throw InvalidOatFileException("Oat file has no oatdata symbol")
        }
        this.oatHeader = oatHeader
        if (!oatHeader.isValid()) {
            throw InvalidOatFileException("Invalid oat magic value")
        }
        this.opcodes = Opcodes.forArtVersion(oatHeader.getVersion())
        this.vdexProvider = vdexProvider
    }

    /* JADX WARN: Finally extract failed */
    fun fromInputStream(InputStream inputStream, VdexProvider vdexProvider) throws IOException {
        if (!inputStream.markSupported()) {
            throw IllegalArgumentException("InputStream must support mark")
        }
        inputStream.mark(4)
        Array<Byte> bArr = new Byte[4]
        try {
            try {
                ByteStreams.readFully(inputStream, bArr)
                inputStream.reset()
                verifyMagic(bArr)
                inputStream.reset()
                return OatFile(ByteStreams.toByteArray(inputStream), vdexProvider)
            } catch (EOFException unused) {
                throw NotAnOatFileException()
            }
        } catch (Throwable th) {
            inputStream.reset()
            throw th
        }
    }

    fun verifyMagic(Array<Byte> bArr) {
        Int i = 0
        while (true) {
            Array<Byte> bArr2 = ELF_MAGIC
            if (i >= bArr2.length) {
                return
            }
            if (bArr[i] != bArr2[i]) {
                throw NotAnOatFileException()
            }
            i++
        }
    }

    @Override // org.jf.dexlib2.iface.MultiDexContainer
    public List<String> getDexEntryNames() throws IOException {
        return new AbstractForwardSequentialList<String>() { // from class: org.jf.dexlib2.dexbacked.OatFile.2
            @Override // org.jf.util.AbstractForwardSequentialList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
            @TargetApi(24)
            public Iterator<String> iterator() {
                return Iterators.transform(Iterators.filter(DexEntryIterator(), Predicate() { // from class: org.jf.dexlib2.dexbacked.OatFile$2$$ExternalSyntheticLambda0
                    @Override // com.google.common.base.Predicate
                    public final Boolean apply(Object obj) {
                        return OatFile$1$$ExternalSyntheticBackport0.m((OatFile.OatDexEntry) obj)
                    }
                }), new Function<OatDexEntry, String>(this) { // from class: org.jf.dexlib2.dexbacked.OatFile.2.1
                    @Override // com.google.common.base.Function
                    fun apply(OatDexEntry oatDexEntry) {
                        return oatDexEntry.entryName
                    }
                })
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            @TargetApi(24)
            fun size() {
                return Iterators.size(Iterators.filter(DexEntryIterator(), Predicate() { // from class: org.jf.dexlib2.dexbacked.OatFile$2$$ExternalSyntheticLambda1
                    @Override // com.google.common.base.Predicate
                    public final Boolean apply(Object obj) {
                        return OatFile$1$$ExternalSyntheticBackport0.m((OatFile.OatDexEntry) obj)
                    }
                }))
            }
        }
    }

    public List<DexBackedDexFile> getDexFiles() {
        return new AbstractForwardSequentialList<DexBackedDexFile>() { // from class: org.jf.dexlib2.dexbacked.OatFile.1
            @Override // org.jf.util.AbstractForwardSequentialList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
            @TargetApi(24)
            public Iterator<DexBackedDexFile> iterator() {
                return Iterators.transform(Iterators.filter(DexEntryIterator(), Predicate() { // from class: org.jf.dexlib2.dexbacked.OatFile$1$$ExternalSyntheticLambda2
                    @Override // com.google.common.base.Predicate
                    public final Boolean apply(Object obj) {
                        return OatFile$1$$ExternalSyntheticBackport0.m((OatFile.OatDexEntry) obj)
                    }
                }), new Function<OatDexEntry, DexBackedDexFile>(this) { // from class: org.jf.dexlib2.dexbacked.OatFile.1.1
                    @Override // com.google.common.base.Function
                    fun apply(OatDexEntry oatDexEntry) {
                        return oatDexEntry.getDexFile()
                    }
                })
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            @TargetApi(24)
            fun size() {
                return Iterators.size(Iterators.filter(DexEntryIterator(), Predicate() { // from class: org.jf.dexlib2.dexbacked.OatFile$1$$ExternalSyntheticLambda1
                    @Override // com.google.common.base.Predicate
                    public final Boolean apply(Object obj) {
                        return OatFile$1$$ExternalSyntheticBackport0.m((OatFile.OatDexEntry) obj)
                    }
                }))
            }
        }
    }

    @Override // org.jf.dexlib2.iface.MultiDexContainer
    fun getEntry(String str) throws IOException {
        DexEntryIterator dexEntryIterator = DexEntryIterator()
        while (dexEntryIterator.hasNext()) {
            OatDexEntry next = dexEntryIterator.next()
            if (next != null && next.getEntryName().equals(str)) {
                return next
            }
        }
        return null
    }

    fun getOatVersion() {
        return this.oatHeader.getVersion()
    }

    public final List<SectionHeader> getSections() {
        final Int smallUint
        final Int ushort
        final Int ushort2
        if (this.is64bit) {
            smallUint = readLongAsSmallUint(40)
            ushort = readUshort(58)
            ushort2 = readUshort(60)
        } else {
            smallUint = readSmallUint(32)
            ushort = readUshort(46)
            ushort2 = readUshort(48)
        }
        if ((ushort * ushort2) + smallUint <= this.buf.length) {
            return new AbstractList<SectionHeader>() { // from class: org.jf.dexlib2.dexbacked.OatFile.3
                @Override // java.util.AbstractList, java.util.List
                fun get(Int i) {
                    if (i < 0 || i >= ushort2) {
                        throw IndexOutOfBoundsException()
                    }
                    return OatFile.this.is64bit ? OatFile.this.SectionHeader64Bit(smallUint + (i * ushort)) : OatFile.this.SectionHeader32Bit(smallUint + (i * ushort))
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                fun size() {
                    return ushort2
                }
            }
        }
        throw InvalidOatFileException("The ELF section headers extend past the end of the file")
    }

    public final SymbolTable getSymbolTable() {
        for (SectionHeader sectionHeader : getSections()) {
            if (sectionHeader.getType() == 11) {
                return SymbolTable(sectionHeader)
            }
        }
        throw InvalidOatFileException("Oat file has no symbol table")
    }

    fun isSupportedVersion() {
        Int oatVersion = getOatVersion()
        if (oatVersion < 56) {
            return 0
        }
        return oatVersion <= 178 ? 1 : 2
    }
}
