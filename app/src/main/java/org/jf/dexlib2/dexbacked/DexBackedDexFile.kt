package org.jf.dexlib2.dexbacked

import com.google.common.io.ByteStreams
import java.io.IOException
import java.io.InputStream
import java.util.AbstractList
import java.util.List
import java.util.Set
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.raw.HeaderItem
import org.jf.dexlib2.dexbacked.raw.MapItem
import org.jf.dexlib2.dexbacked.reference.DexBackedCallSiteReference
import org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodHandleReference
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodProtoReference
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.dexbacked.util.FixedSizeSet
import org.jf.dexlib2.iface.DexFile
import org.jf.dexlib2.util.DexUtil

class DexBackedDexFile implements DexFile {
    public IndexedSection<DexBackedCallSiteReference> callSiteSection
    public final Int classCount
    public IndexedSection<DexBackedClassDef> classSection
    public final Int classStartOffset
    public final DexBuffer dataBuffer
    public final DexBuffer dexBuffer
    public final Int fieldCount
    public IndexedSection<DexBackedFieldReference> fieldSection
    public final Int fieldStartOffset
    public final Int hiddenApiRestrictionsOffset
    public final Int mapOffset
    public final Int methodCount
    public IndexedSection<DexBackedMethodHandleReference> methodHandleSection
    public IndexedSection<DexBackedMethodReference> methodSection
    public final Int methodStartOffset
    public final Opcodes opcodes
    public final Int protoCount
    public IndexedSection<DexBackedMethodProtoReference> protoSection
    public final Int protoStartOffset
    public final Int stringCount
    public OptionalIndexedSection<String> stringSection
    public final Int stringStartOffset
    public final Int typeCount
    public OptionalIndexedSection<String> typeSection
    public final Int typeStartOffset

    public static abstract class IndexedSection<T> extends AbstractList<T> {
        public abstract Int getOffset(Int i)
    }

    public static class NotADexFile extends RuntimeException {
        constructor(String str) {
            super(str)
        }
    }

    public static abstract class OptionalIndexedSection<T> extends IndexedSection<T> {
        public abstract T getOptional(Int i)
    }

    constructor(Opcodes opcodes, Array<Byte> bArr) {
        this(opcodes, bArr, 0, true)
    }

    constructor(Opcodes opcodes, Array<Byte> bArr, Int i) {
        this(opcodes, bArr, i, false)
    }

    constructor(Opcodes opcodes, Array<Byte> bArr, Int i, Boolean z) {
        this.stringSection = new OptionalIndexedSection<String>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.5
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                DexReader<? extends DexBuffer> dexReader = DexBackedDexFile.this.dataBuffer.readerAt(DexBackedDexFile.this.dexBuffer.readSmallUint(getOffset(i2)))
                return dexReader.readString(dexReader.readSmallUleb128())
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid string index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return DexBackedDexFile.this.stringStartOffset + (i2 * 4)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.OptionalIndexedSection
            fun getOptional(Int i2) {
                if (i2 == -1) {
                    return null
                }
                return get(i2)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedDexFile.this.stringCount
            }
        }
        this.typeSection = new OptionalIndexedSection<String>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.6
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedDexFile.this.getStringSection().get(DexBackedDexFile.this.dexBuffer.readSmallUint(getOffset(i2)))
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid type index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return DexBackedDexFile.this.typeStartOffset + (i2 * 4)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.OptionalIndexedSection
            fun getOptional(Int i2) {
                if (i2 == -1) {
                    return null
                }
                return get(i2)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedDexFile.this.typeCount
            }
        }
        this.fieldSection = new IndexedSection<DexBackedFieldReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.7
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedFieldReference(DexBackedDexFile.this, i2)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid field index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return DexBackedDexFile.this.fieldStartOffset + (i2 * 8)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedDexFile.this.fieldCount
            }
        }
        this.methodSection = new IndexedSection<DexBackedMethodReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.8
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedMethodReference(DexBackedDexFile.this, i2)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid method index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return DexBackedDexFile.this.methodStartOffset + (i2 * 8)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedDexFile.this.methodCount
            }
        }
        this.protoSection = new IndexedSection<DexBackedMethodProtoReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.9
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedMethodProtoReference(DexBackedDexFile.this, i2)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid proto index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return DexBackedDexFile.this.protoStartOffset + (i2 * 12)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedDexFile.this.protoCount
            }
        }
        this.classSection = new IndexedSection<DexBackedClassDef>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.10
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedClassDef(DexBackedDexFile.this, getOffset(i2), DexBackedDexFile.this.readHiddenApiRestrictionsOffset(i2))
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid class index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return DexBackedDexFile.this.classStartOffset + (i2 * 32)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return DexBackedDexFile.this.classCount
            }
        }
        this.callSiteSection = new IndexedSection<DexBackedCallSiteReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.11
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedCallSiteReference(DexBackedDexFile.this, i2)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(7)
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid callsite index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return mapItemForSection.getOffset() + (i2 * 4)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(7)
                if (mapItemForSection == null) {
                    return 0
                }
                return mapItemForSection.getItemCount()
            }
        }
        this.methodHandleSection = new IndexedSection<DexBackedMethodHandleReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.12
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i2) {
                return DexBackedMethodHandleReference(DexBackedDexFile.this, i2)
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            fun getOffset(Int i2) {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(8)
                if (i2 < 0 || i2 >= size()) {
                    throw IndexOutOfBoundsException(String.format("Invalid method handle index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())))
                }
                return mapItemForSection.getOffset() + (i2 * 8)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(8)
                if (mapItemForSection == null) {
                    return 0
                }
                return mapItemForSection.getItemCount()
            }
        }
        DexBuffer dexBuffer = DexBuffer(bArr, i)
        this.dexBuffer = dexBuffer
        this.dataBuffer = DexBuffer(bArr, getBaseDataOffset() + i)
        Int version = getVersion(bArr, i, z)
        if (opcodes == null) {
            this.opcodes = getDefaultOpcodes(version)
        } else {
            this.opcodes = opcodes
        }
        this.stringCount = dexBuffer.readSmallUint(56)
        this.stringStartOffset = dexBuffer.readSmallUint(60)
        this.typeCount = dexBuffer.readSmallUint(64)
        this.typeStartOffset = dexBuffer.readSmallUint(68)
        this.protoCount = dexBuffer.readSmallUint(72)
        this.protoStartOffset = dexBuffer.readSmallUint(76)
        this.fieldCount = dexBuffer.readSmallUint(80)
        this.fieldStartOffset = dexBuffer.readSmallUint(84)
        this.methodCount = dexBuffer.readSmallUint(88)
        this.methodStartOffset = dexBuffer.readSmallUint(92)
        this.classCount = dexBuffer.readSmallUint(96)
        this.classStartOffset = dexBuffer.readSmallUint(100)
        this.mapOffset = dexBuffer.readSmallUint(52)
        MapItem mapItemForSection = getMapItemForSection(61440)
        if (mapItemForSection != null) {
            this.hiddenApiRestrictionsOffset = mapItemForSection.getOffset()
        } else {
            this.hiddenApiRestrictionsOffset = 0
        }
    }

    fun fromInputStream(Opcodes opcodes, InputStream inputStream) throws IOException {
        DexUtil.verifyDexHeader(inputStream)
        return DexBackedDexFile(opcodes, ByteStreams.toByteArray(inputStream), 0, false)
    }

    fun createMethodImplementation(DexBackedDexFile dexBackedDexFile, DexBackedMethod dexBackedMethod, Int i) {
        return DexBackedMethodImplementation(dexBackedDexFile, dexBackedMethod, i)
    }

    fun getBaseDataOffset() {
        return 0
    }

    fun getBuffer() {
        return this.dexBuffer
    }

    public IndexedSection<DexBackedCallSiteReference> getCallSiteSection() {
        return this.callSiteSection
    }

    public IndexedSection<DexBackedClassDef> getClassSection() {
        return this.classSection
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public Set<? extends DexBackedClassDef> getClasses() {
        return new FixedSizeSet<DexBackedClassDef>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeSet
            fun readItem(Int i) {
                return DexBackedDexFile.this.getClassSection().get(i)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            fun size() {
                return DexBackedDexFile.this.classCount
            }
        }
    }

    fun getDataBuffer() {
        return this.dataBuffer
    }

    fun getDefaultOpcodes(Int i) {
        return Opcodes.forDexVersion(i)
    }

    public IndexedSection<DexBackedFieldReference> getFieldSection() {
        return this.fieldSection
    }

    fun getMapItemForSection(Int i) {
        for (MapItem mapItem : getMapItems()) {
            if (mapItem.getType() == i) {
                return mapItem
            }
        }
        return null
    }

    public List<MapItem> getMapItems() {
        val smallUint = this.dataBuffer.readSmallUint(this.mapOffset)
        return new FixedSizeList<MapItem>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.4
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(Int i) {
                return MapItem(DexBackedDexFile.this, DexBackedDexFile.this.mapOffset + 4 + (i * 12))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return smallUint
            }
        }
    }

    public IndexedSection<DexBackedMethodHandleReference> getMethodHandleSection() {
        return this.methodHandleSection
    }

    public IndexedSection<DexBackedMethodReference> getMethodSection() {
        return this.methodSection
    }

    @Override // org.jf.dexlib2.iface.DexFile
    fun getOpcodes() {
        return this.opcodes
    }

    public IndexedSection<DexBackedMethodProtoReference> getProtoSection() {
        return this.protoSection
    }

    public OptionalIndexedSection<String> getStringSection() {
        return this.stringSection
    }

    public OptionalIndexedSection<String> getTypeSection() {
        return this.typeSection
    }

    fun getVersion(Array<Byte> bArr, Int i, Boolean z) {
        return z ? DexUtil.verifyDexHeader(bArr, i) : HeaderItem.getVersion(bArr, i)
    }

    public final Int readHiddenApiRestrictionsOffset(Int i) {
        Int i2
        Int i3 = this.hiddenApiRestrictionsOffset
        if (i3 == 0 || (i2 = this.dexBuffer.readInt(i3 + 4 + (i * 4))) == 0) {
            return 0
        }
        return this.hiddenApiRestrictionsOffset + i2
    }
}
