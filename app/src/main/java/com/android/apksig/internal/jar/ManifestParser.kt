package com.android.apksig.internal.jar

import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import java.util.jar.Attributes

class ManifestParser {
    public static final Array<Byte> EMPTY_BYTE_ARRAY = new Byte[0]
    public Array<Byte> mBufferedLine
    public Int mEndOffset
    public final Array<Byte> mManifest
    public Int mOffset

    public static class Attribute {
        public final String mName
        public final String mValue

        constructor(String str, String str2) {
            this.mName = str
            this.mValue = str2
        }

        fun getName() {
            return this.mName
        }

        fun getValue() {
            return this.mValue
        }
    }

    public static class Section {
        public final List<Attribute> mAttributes
        public final String mName
        public final Int mSizeBytes
        public final Int mStartOffset

        /* JADX WARN: Removed duplicated region for block: B:7:0x0025  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        constructor(Int r2, Int r3, java.util.List<com.android.apksig.internal.jar.ManifestParser.Attribute> r4) {
            /*
                r1 = this
                r1.<init>()
                r1.mStartOffset = r2
                r1.mSizeBytes = r3
                Boolean r2 = r4.isEmpty()
                if (r2 != 0) goto L25
                r2 = 0
                java.lang.Object r2 = r4.get(r2)
                com.android.apksig.internal.jar.ManifestParser$Attribute r2 = (com.android.apksig.internal.jar.ManifestParser.Attribute) r2
                java.lang.String r3 = r2.getName()
                java.lang.String r0 = "Name"
                Boolean r3 = r0.equalsIgnoreCase(r3)
                if (r3 == 0) goto L25
                java.lang.String r2 = r2.getValue()
                goto L26
            L25:
                r2 = 0
            L26:
                r1.mName = r2
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>(r4)
                java.util.List r2 = java.util.Collections.unmodifiableList(r2)
                r1.mAttributes = r2
                return
            */
            throw UnsupportedOperationException("Method not decompiled: com.android.apksig.internal.jar.ManifestParser.Section.<init>(Int, Int, java.util.List):Unit")
        }

        fun getAttributeValue(String str) {
            for (Attribute attribute : this.mAttributes) {
                if (attribute.getName().equalsIgnoreCase(str)) {
                    return attribute.getValue()
                }
            }
            return null
        }

        fun getAttributeValue(Attributes.Name name) {
            return getAttributeValue(name.toString())
        }

        public List<Attribute> getAttributes() {
            return this.mAttributes
        }

        fun getName() {
            return this.mName
        }

        fun getSizeBytes() {
            return this.mSizeBytes
        }

        fun getStartOffset() {
            return this.mStartOffset
        }
    }

    constructor(Array<Byte> bArr) {
        this(bArr, 0, bArr.length)
    }

    constructor(Array<Byte> bArr, Int i, Int i2) {
        this.mManifest = bArr
        this.mOffset = i
        this.mEndOffset = i + i2
    }

    public static Array<Byte> concat(Array<Byte> bArr, Array<Byte> bArr2, Int i, Int i2) {
        Array<Byte> bArr3 = new Byte[bArr.length + i2]
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length)
        System.arraycopy(bArr2, i, bArr3, bArr.length, i2)
        return bArr3
    }

    fun parseAttr(String str) {
        Int iIndexOf = str.indexOf(": ")
        return iIndexOf == -1 ? Attribute(str, "") : Attribute(str.substring(0, iIndexOf), str.substring(iIndexOf + 2))
    }

    public List<Section> readAllSections() {
        ArrayList arrayList = ArrayList()
        while (true) {
            Section section = readSection()
            if (section == null) {
                return arrayList
            }
            arrayList.add(section)
        }
    }

    public final String readAttribute() {
        Array<Byte> attributeBytes = readAttributeBytes()
        if (attributeBytes == null) {
            return null
        }
        return attributeBytes.length == 0 ? "" : String(attributeBytes, Charset.forName("UTF-8"))
    }

    public final Array<Byte> readAttributeBytes() {
        Array<Byte> bArr = this.mBufferedLine
        if (bArr != null && bArr.length == 0) {
            this.mBufferedLine = null
            return EMPTY_BYTE_ARRAY
        }
        Array<Byte> line = readLine()
        if (line == null) {
            Array<Byte> bArr2 = this.mBufferedLine
            if (bArr2 == null) {
                return null
            }
            this.mBufferedLine = null
            return bArr2
        }
        if (line.length == 0) {
            Array<Byte> bArr3 = this.mBufferedLine
            if (bArr3 == null) {
                return EMPTY_BYTE_ARRAY
            }
            this.mBufferedLine = EMPTY_BYTE_ARRAY
            return bArr3
        }
        Array<Byte> bArr4 = this.mBufferedLine
        if (bArr4 != null) {
            if (line.length == 0 || line[0] != 32) {
                this.mBufferedLine = line
                return bArr4
            }
            this.mBufferedLine = null
            line = concat(bArr4, line, 1, line.length - 1)
        }
        while (true) {
            Array<Byte> line2 = readLine()
            if (line2 == null) {
                return line
            }
            if (line2.length == 0) {
                this.mBufferedLine = EMPTY_BYTE_ARRAY
                return line
            }
            if (line2[0] != 32) {
                this.mBufferedLine = line2
                return line
            }
            line = concat(line, line2, 1, line2.length - 1)
        }
    }

    public final Array<Byte> readLine() {
        Int i
        Int i2
        Int i3 = this.mOffset
        if (i3 >= this.mEndOffset) {
            return null
        }
        Int i4 = i3
        while (true) {
            i = this.mEndOffset
            if (i4 >= i) {
                i4 = -1
                i2 = -1
                break
            }
            Array<Byte> bArr = this.mManifest
            Byte b2 = bArr[i4]
            if (b2 == 13) {
                i2 = i4 + 1
                if (i2 < i && bArr[i2] == 10) {
                    i2++
                }
            } else {
                if (b2 == 10) {
                    i2 = i4 + 1
                    break
                }
                i4++
            }
        }
        if (i4 == -1) {
            i4 = i
        } else {
            i = i2
        }
        this.mOffset = i
        return i4 == i3 ? EMPTY_BYTE_ARRAY : Arrays.copyOfRange(this.mManifest, i3, i4)
    }

    fun readSection() {
        Int i
        String attribute
        do {
            i = this.mOffset
            attribute = readAttribute()
            if (attribute == null) {
                return null
            }
        } while (attribute.length() == 0);
        ArrayList arrayList = ArrayList()
        arrayList.add(parseAttr(attribute))
        while (true) {
            String attribute2 = readAttribute()
            if (attribute2 == null || attribute2.length() == 0) {
                break
            }
            arrayList.add(parseAttr(attribute2))
        }
        return Section(i, this.mOffset - i, arrayList)
    }
}
