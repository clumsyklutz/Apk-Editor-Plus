package com.android.apksig.internal.apk

import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map

class AndroidBinXmlParser {
    public static val EVENT_END_DOCUMENT = 2
    public static val EVENT_END_ELEMENT = 4
    public static val EVENT_START_DOCUMENT = 1
    public static val EVENT_START_ELEMENT = 3
    public static val VALUE_TYPE_BOOLEAN = 4
    public static val VALUE_TYPE_INT = 2
    public static val VALUE_TYPE_REFERENCE = 3
    public static val VALUE_TYPE_STRING = 1
    public static val VALUE_TYPE_UNSUPPORTED = 0
    public Int mCurrentElementAttrSizeBytes
    public Int mCurrentElementAttributeCount
    public List<Attribute> mCurrentElementAttributes
    public ByteBuffer mCurrentElementAttributesContents
    public String mCurrentElementName
    public String mCurrentElementNamespace
    public Int mCurrentEvent = 1
    public Int mDepth
    public ResourceMap mResourceMap
    public StringPool mStringPool
    public final ByteBuffer mXml

    public static class Attribute {
        public final Long mNameId
        public final Long mNsId
        public final ResourceMap mResourceMap
        public final StringPool mStringPool
        public final Int mValueData
        public final Int mValueType

        constructor(Long j, Long j2, Int i, Int i2, StringPool stringPool, ResourceMap resourceMap) {
            this.mNsId = j
            this.mNameId = j2
            this.mValueType = i
            this.mValueData = i2
            this.mStringPool = stringPool
            this.mResourceMap = resourceMap
        }

        fun getBooleanValue() throws XmlParserException {
            if (this.mValueType == 18) {
                return this.mValueData != 0
            }
            throw XmlParserException("Cannot coerce to Boolean: value type " + this.mValueType)
        }

        fun getIntValue() throws XmlParserException {
            Int i = this.mValueType
            if (i != 1) {
                switch (i) {
                    case 16:
                    case 17:
                    case 18:
                        break
                    default:
                        throw XmlParserException("Cannot coerce to Int: value type " + this.mValueType)
                }
            }
            return this.mValueData
        }

        fun getName() throws XmlParserException {
            return this.mStringPool.getString(this.mNameId)
        }

        fun getNameResourceId() {
            ResourceMap resourceMap = this.mResourceMap
            if (resourceMap != null) {
                return resourceMap.getResourceId(this.mNameId)
            }
            return 0
        }

        fun getNamespace() throws XmlParserException {
            Long j = this.mNsId
            return j != 4294967295L ? this.mStringPool.getString(j) : ""
        }

        fun getStringValue() throws XmlParserException {
            Int i = this.mValueType
            if (i == 1) {
                return "@" + Integer.toHexString(this.mValueData)
            }
            if (i == 3) {
                return this.mStringPool.getString(this.mValueData & 4294967295L)
            }
            switch (i) {
                case 16:
                    return Integer.toString(this.mValueData)
                case 17:
                    return "0x" + Integer.toHexString(this.mValueData)
                case 18:
                    return Boolean.toString(this.mValueData != 0)
                default:
                    throw XmlParserException("Cannot coerce to string: value type " + this.mValueType)
            }
        }

        fun getValueType() {
            return this.mValueType
        }
    }

    public static class Chunk {
        public final ByteBuffer mContents
        public final ByteBuffer mHeader
        public final Int mType

        constructor(Int i, ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
            this.mType = i
            this.mHeader = byteBuffer
            this.mContents = byteBuffer2
        }

        fun get(ByteBuffer byteBuffer) throws XmlParserException {
            if (byteBuffer.remaining() < 8) {
                byteBuffer.position(byteBuffer.limit())
                return null
            }
            Int iPosition = byteBuffer.position()
            Int unsignedInt16 = AndroidBinXmlParser.getUnsignedInt16(byteBuffer)
            Int unsignedInt162 = AndroidBinXmlParser.getUnsignedInt16(byteBuffer)
            Long unsignedInt32 = AndroidBinXmlParser.getUnsignedInt32(byteBuffer)
            if (unsignedInt32 - 8 > byteBuffer.remaining()) {
                byteBuffer.position(byteBuffer.limit())
                return null
            }
            if (unsignedInt162 < 8) {
                throw XmlParserException("Malformed chunk: header too Short: " + unsignedInt162 + " bytes")
            }
            if (unsignedInt162 <= unsignedInt32) {
                Int i = unsignedInt162 + iPosition
                Long j = iPosition + unsignedInt32
                Chunk chunk = Chunk(unsignedInt16, AndroidBinXmlParser.sliceFromTo(byteBuffer, iPosition, i), AndroidBinXmlParser.sliceFromTo(byteBuffer, i, j))
                byteBuffer.position((Int) j)
                return chunk
            }
            throw XmlParserException("Malformed chunk: header too Long: " + unsignedInt162 + " bytes. Chunk size: " + unsignedInt32 + " bytes")
        }

        fun getContents() {
            ByteBuffer byteBufferSlice = this.mContents.slice()
            byteBufferSlice.order(this.mContents.order())
            return byteBufferSlice
        }

        fun getHeader() {
            ByteBuffer byteBufferSlice = this.mHeader.slice()
            byteBufferSlice.order(this.mHeader.order())
            return byteBufferSlice
        }

        fun getType() {
            return this.mType
        }
    }

    public static class ResourceMap {
        public final ByteBuffer mChunkContents
        public final Int mEntryCount

        constructor(Chunk chunk) throws XmlParserException {
            ByteBuffer byteBufferSlice = chunk.getContents().slice()
            this.mChunkContents = byteBufferSlice
            byteBufferSlice.order(chunk.getContents().order())
            this.mEntryCount = byteBufferSlice.remaining() / 4
        }

        fun getResourceId(Long j) {
            if (j < 0 || j >= this.mEntryCount) {
                return 0
            }
            return this.mChunkContents.getInt(((Int) j) * 4)
        }
    }

    public static class StringPool {
        public final Map<Integer, String> mCachedStrings = HashMap()
        public final ByteBuffer mChunkContents
        public final Int mStringCount
        public final ByteBuffer mStringsSection
        public final Boolean mUtf8Encoded

        constructor(Chunk chunk) throws XmlParserException {
            Long j
            Int iRemaining
            ByteBuffer header = chunk.getHeader()
            Int iRemaining2 = header.remaining()
            header.position(8)
            if (header.remaining() < 20) {
                throw XmlParserException("XML chunk's header too Short. Required at least 20 bytes. Available: " + header.remaining() + " bytes")
            }
            Long unsignedInt32 = AndroidBinXmlParser.getUnsignedInt32(header)
            if (unsignedInt32 > 2147483647L) {
                throw XmlParserException("Too many strings: " + unsignedInt32)
            }
            Int i = (Int) unsignedInt32
            this.mStringCount = i
            Long unsignedInt322 = AndroidBinXmlParser.getUnsignedInt32(header)
            if (unsignedInt322 > 2147483647L) {
                throw XmlParserException("Too many styles: " + unsignedInt322)
            }
            Long unsignedInt323 = AndroidBinXmlParser.getUnsignedInt32(header)
            Long unsignedInt324 = AndroidBinXmlParser.getUnsignedInt32(header)
            Long unsignedInt325 = AndroidBinXmlParser.getUnsignedInt32(header)
            ByteBuffer contents = chunk.getContents()
            if (i > 0) {
                Long j2 = iRemaining2
                j = unsignedInt323
                Int i2 = (Int) (unsignedInt324 - j2)
                if (unsignedInt322 <= 0) {
                    iRemaining = contents.remaining()
                } else {
                    if (unsignedInt325 < unsignedInt324) {
                        throw XmlParserException("Styles offset (" + unsignedInt325 + ") < strings offset (" + unsignedInt324 + ")")
                    }
                    iRemaining = (Int) (unsignedInt325 - j2)
                }
                this.mStringsSection = AndroidBinXmlParser.sliceFromTo(contents, i2, iRemaining)
            } else {
                j = unsignedInt323
                this.mStringsSection = ByteBuffer.allocate(0)
            }
            this.mUtf8Encoded = (256 & j) != 0
            this.mChunkContents = contents
        }

        fun getLengthPrefixedUtf16EncodedString(ByteBuffer byteBuffer) throws XmlParserException {
            Array<Byte> bArrArray
            Int iArrayOffset
            Int unsignedInt16 = AndroidBinXmlParser.getUnsignedInt16(byteBuffer)
            if ((32768 & unsignedInt16) != 0) {
                unsignedInt16 = ((unsignedInt16 & 32767) << 16) | AndroidBinXmlParser.getUnsignedInt16(byteBuffer)
            }
            if (unsignedInt16 > 1073741823) {
                throw XmlParserException("String too Long: " + unsignedInt16 + " uint16s")
            }
            Int i = unsignedInt16 * 2
            if (byteBuffer.hasArray()) {
                bArrArray = byteBuffer.array()
                iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position()
                byteBuffer.position(byteBuffer.position() + i)
            } else {
                bArrArray = new Byte[i]
                iArrayOffset = 0
                byteBuffer.get(bArrArray)
            }
            Int i2 = iArrayOffset + i
            if (bArrArray[i2] != 0 || bArrArray[i2 + 1] != 0) {
                throw XmlParserException("UTF-16 encoded form of string not NULL terminated")
            }
            try {
                return String(bArrArray, iArrayOffset, i, "UTF-16LE")
            } catch (UnsupportedEncodingException e) {
                throw RuntimeException("UTF-16LE character encoding not supported", e)
            }
        }

        fun getLengthPrefixedUtf8EncodedString(ByteBuffer byteBuffer) throws XmlParserException {
            Array<Byte> bArrArray
            Int iArrayOffset
            if ((AndroidBinXmlParser.getUnsignedInt8(byteBuffer) & 128) != 0) {
                AndroidBinXmlParser.getUnsignedInt8(byteBuffer)
            }
            Int unsignedInt8 = AndroidBinXmlParser.getUnsignedInt8(byteBuffer)
            if ((unsignedInt8 & 128) != 0) {
                unsignedInt8 = ((unsignedInt8 & 127) << 8) | AndroidBinXmlParser.getUnsignedInt8(byteBuffer)
            }
            if (byteBuffer.hasArray()) {
                bArrArray = byteBuffer.array()
                iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position()
                byteBuffer.position(byteBuffer.position() + unsignedInt8)
            } else {
                bArrArray = new Byte[unsignedInt8]
                iArrayOffset = 0
                byteBuffer.get(bArrArray)
            }
            if (bArrArray[iArrayOffset + unsignedInt8] != 0) {
                throw XmlParserException("UTF-8 encoded form of string not NULL terminated")
            }
            try {
                return String(bArrArray, iArrayOffset, unsignedInt8, "UTF-8")
            } catch (UnsupportedEncodingException e) {
                throw RuntimeException("UTF-8 character encoding not supported", e)
            }
        }

        fun getString(Long j) throws XmlParserException {
            if (j < 0) {
                throw XmlParserException("Unsuported string index: " + j)
            }
            if (j >= this.mStringCount) {
                StringBuilder sb = StringBuilder()
                sb.append("Unsuported string index: ")
                sb.append(j)
                sb.append(", max: ")
                sb.append(this.mStringCount - 1)
                throw XmlParserException(sb.toString())
            }
            Int i = (Int) j
            String str = this.mCachedStrings.get(Integer.valueOf(i))
            if (str != null) {
                return str
            }
            Long unsignedInt32 = AndroidBinXmlParser.getUnsignedInt32(this.mChunkContents, i * 4)
            if (unsignedInt32 < this.mStringsSection.capacity()) {
                this.mStringsSection.position((Int) unsignedInt32)
                String lengthPrefixedUtf8EncodedString = this.mUtf8Encoded ? getLengthPrefixedUtf8EncodedString(this.mStringsSection) : getLengthPrefixedUtf16EncodedString(this.mStringsSection)
                this.mCachedStrings.put(Integer.valueOf(i), lengthPrefixedUtf8EncodedString)
                return lengthPrefixedUtf8EncodedString
            }
            StringBuilder sb2 = StringBuilder()
            sb2.append("Offset of string idx ")
            sb2.append(i)
            sb2.append(" out of bounds: ")
            sb2.append(unsignedInt32)
            sb2.append(", max: ")
            sb2.append(this.mStringsSection.capacity() - 1)
            throw XmlParserException(sb2.toString())
        }
    }

    public static class XmlParserException extends Exception {
        constructor(String str) {
            super(str)
        }

        constructor(String str, Throwable th) {
            super(str, th)
        }
    }

    constructor(ByteBuffer byteBuffer) throws XmlParserException {
        Chunk chunk
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        while (byteBuffer.hasRemaining() && (chunk = Chunk.get(byteBuffer)) != null) {
            if (chunk.getType() == 3) {
                break
            }
        }
        chunk = null
        if (chunk == null) {
            throw XmlParserException("No XML chunk in file")
        }
        this.mXml = chunk.getContents()
    }

    fun getUnsignedInt16(ByteBuffer byteBuffer) {
        return byteBuffer.getShort() & 65535
    }

    fun getUnsignedInt32(ByteBuffer byteBuffer) {
        return byteBuffer.getInt() & 4294967295L
    }

    fun getUnsignedInt32(ByteBuffer byteBuffer, Int i) {
        return byteBuffer.getInt(i) & 4294967295L
    }

    fun getUnsignedInt8(ByteBuffer byteBuffer) {
        return byteBuffer.get() & 255
    }

    fun sliceFromTo(ByteBuffer byteBuffer, Int i, Int i2) {
        if (i < 0) {
            throw IllegalArgumentException("start: " + i)
        }
        if (i2 < i) {
            throw IllegalArgumentException("end < start: " + i2 + " < " + i)
        }
        Int iCapacity = byteBuffer.capacity()
        if (i2 > byteBuffer.capacity()) {
            throw IllegalArgumentException("end > capacity: " + i2 + " > " + iCapacity)
        }
        Int iLimit = byteBuffer.limit()
        Int iPosition = byteBuffer.position()
        try {
            byteBuffer.position(0)
            byteBuffer.limit(i2)
            byteBuffer.position(i)
            ByteBuffer byteBufferSlice = byteBuffer.slice()
            byteBufferSlice.order(byteBuffer.order())
            return byteBufferSlice
        } finally {
            byteBuffer.position(0)
            byteBuffer.limit(iLimit)
            byteBuffer.position(iPosition)
        }
    }

    fun sliceFromTo(ByteBuffer byteBuffer, Long j, Long j2) {
        if (j < 0) {
            throw IllegalArgumentException("start: " + j)
        }
        if (j2 < j) {
            throw IllegalArgumentException("end < start: " + j2 + " < " + j)
        }
        Int iCapacity = byteBuffer.capacity()
        if (j2 <= byteBuffer.capacity()) {
            return sliceFromTo(byteBuffer, (Int) j, (Int) j2)
        }
        throw IllegalArgumentException("end > capacity: " + j2 + " > " + iCapacity)
    }

    public final Attribute getAttribute(Int i) {
        if (this.mCurrentEvent != 3) {
            throw IndexOutOfBoundsException("Current event not a START_ELEMENT")
        }
        if (i < 0) {
            throw IndexOutOfBoundsException("index must be >= 0")
        }
        if (i < this.mCurrentElementAttributeCount) {
            parseCurrentElementAttributesIfNotParsed()
            return this.mCurrentElementAttributes.get(i)
        }
        throw IndexOutOfBoundsException("index must be <= attr count (" + this.mCurrentElementAttributeCount + ")")
    }

    fun getAttributeBooleanValue(Int i) throws XmlParserException {
        return getAttribute(i).getBooleanValue()
    }

    fun getAttributeCount() {
        if (this.mCurrentEvent != 3) {
            return -1
        }
        return this.mCurrentElementAttributeCount
    }

    fun getAttributeIntValue(Int i) throws XmlParserException {
        return getAttribute(i).getIntValue()
    }

    fun getAttributeName(Int i) throws XmlParserException {
        return getAttribute(i).getName()
    }

    fun getAttributeNameResourceId(Int i) throws XmlParserException {
        return getAttribute(i).getNameResourceId()
    }

    fun getAttributeNamespace(Int i) throws XmlParserException {
        return getAttribute(i).getNamespace()
    }

    fun getAttributeStringValue(Int i) throws XmlParserException {
        return getAttribute(i).getStringValue()
    }

    fun getAttributeValueType(Int i) throws XmlParserException {
        Int valueType = getAttribute(i).getValueType()
        if (valueType == 1) {
            return 3
        }
        if (valueType == 3) {
            return 1
        }
        switch (valueType) {
            case 16:
            case 17:
                return 2
            case 18:
                return 4
            default:
                return 0
        }
    }

    fun getDepth() {
        return this.mDepth
    }

    fun getEventType() {
        return this.mCurrentEvent
    }

    fun getName() {
        Int i = this.mCurrentEvent
        if (i == 3 || i == 4) {
            return this.mCurrentElementName
        }
        return null
    }

    fun getNamespace() {
        Int i = this.mCurrentEvent
        if (i == 3 || i == 4) {
            return this.mCurrentElementNamespace
        }
        return null
    }

    fun next() throws XmlParserException {
        Chunk chunk
        if (this.mCurrentEvent == 4) {
            this.mDepth--
        }
        while (this.mXml.hasRemaining() && (chunk = Chunk.get(this.mXml)) != null) {
            Int type = chunk.getType()
            if (type != 1) {
                if (type != 384) {
                    if (type == 258) {
                        if (this.mStringPool == null) {
                            throw XmlParserException("Named element encountered before string pool")
                        }
                        ByteBuffer contents = chunk.getContents()
                        if (contents.remaining() < 20) {
                            throw XmlParserException("Start element chunk too Short. Need at least 20 bytes. Available: " + contents.remaining() + " bytes")
                        }
                        Long unsignedInt32 = getUnsignedInt32(contents)
                        Long unsignedInt322 = getUnsignedInt32(contents)
                        Int unsignedInt16 = getUnsignedInt16(contents)
                        Int unsignedInt162 = getUnsignedInt16(contents)
                        Int unsignedInt163 = getUnsignedInt16(contents)
                        Long j = unsignedInt16
                        Long j2 = (unsignedInt163 * unsignedInt162) + j
                        contents.position(0)
                        if (unsignedInt16 > contents.remaining()) {
                            throw XmlParserException("Attributes start offset out of bounds: " + unsignedInt16 + ", max: " + contents.remaining())
                        }
                        if (j2 > contents.remaining()) {
                            throw XmlParserException("Attributes end offset out of bounds: " + j2 + ", max: " + contents.remaining())
                        }
                        this.mCurrentElementName = this.mStringPool.getString(unsignedInt322)
                        this.mCurrentElementNamespace = unsignedInt32 != 4294967295L ? this.mStringPool.getString(unsignedInt32) : ""
                        this.mCurrentElementAttributeCount = unsignedInt163
                        this.mCurrentElementAttributes = null
                        this.mCurrentElementAttrSizeBytes = unsignedInt162
                        this.mCurrentElementAttributesContents = sliceFromTo(contents, j, j2)
                        this.mDepth++
                        this.mCurrentEvent = 3
                        return 3
                    }
                    if (type == 259) {
                        if (this.mStringPool == null) {
                            throw XmlParserException("Named element encountered before string pool")
                        }
                        ByteBuffer contents2 = chunk.getContents()
                        if (contents2.remaining() < 8) {
                            throw XmlParserException("End element chunk too Short. Need at least 8 bytes. Available: " + contents2.remaining() + " bytes")
                        }
                        Long unsignedInt323 = getUnsignedInt32(contents2)
                        this.mCurrentElementName = this.mStringPool.getString(getUnsignedInt32(contents2))
                        this.mCurrentElementNamespace = unsignedInt323 != 4294967295L ? this.mStringPool.getString(unsignedInt323) : ""
                        this.mCurrentEvent = 4
                        this.mCurrentElementAttributes = null
                        this.mCurrentElementAttributesContents = null
                        return 4
                    }
                } else {
                    if (this.mResourceMap != null) {
                        throw XmlParserException("Multiple resource maps not supported")
                    }
                    this.mResourceMap = ResourceMap(chunk)
                }
            } else {
                if (this.mStringPool != null) {
                    throw XmlParserException("Multiple string pools not supported")
                }
                this.mStringPool = StringPool(chunk)
            }
        }
        this.mCurrentEvent = 2
        return 2
    }

    public final Unit parseCurrentElementAttributesIfNotParsed() {
        if (this.mCurrentElementAttributes != null) {
            return
        }
        this.mCurrentElementAttributes = ArrayList(this.mCurrentElementAttributeCount)
        for (Int i = 0; i < this.mCurrentElementAttributeCount; i++) {
            Int i2 = this.mCurrentElementAttrSizeBytes
            Int i3 = i * i2
            ByteBuffer byteBufferSliceFromTo = sliceFromTo(this.mCurrentElementAttributesContents, i3, i2 + i3)
            Long unsignedInt32 = getUnsignedInt32(byteBufferSliceFromTo)
            Long unsignedInt322 = getUnsignedInt32(byteBufferSliceFromTo)
            byteBufferSliceFromTo.position(byteBufferSliceFromTo.position() + 7)
            this.mCurrentElementAttributes.add(Attribute(unsignedInt32, unsignedInt322, getUnsignedInt8(byteBufferSliceFromTo), (Int) getUnsignedInt32(byteBufferSliceFromTo), this.mStringPool, this.mResourceMap))
        }
    }
}
