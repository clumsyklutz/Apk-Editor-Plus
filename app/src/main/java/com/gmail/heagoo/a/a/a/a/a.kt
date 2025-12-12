package com.gmail.heagoo.a.a.a.a

import android.content.res.XmlResourceParser
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import org.xmlpull.v1.XmlPullParserException

class a implements XmlResourceParser {

    /* renamed from: a, reason: collision with root package name */
    private c f724a
    private d c
    private Array<Int> d
    private Boolean f
    private Int g
    private Int h
    private Int i
    private Int j
    private Array<Int> k
    private Int l
    private Int m
    private Int n

    /* renamed from: b, reason: collision with root package name */
    private Boolean f725b = false
    private b e = b()

    constructor() {
        a()
    }

    private final Int a(String str, String str2) {
        Int iA
        if (this.c == null || str2 == null || (iA = this.c.a(str2)) == -1) {
            return -1
        }
        Int iA2 = str != null ? this.c.a(str) : -1
        for (Int i = 0; i != this.k.length; i++) {
            if (iA == this.k[i + 1] && (iA2 == -1 || iA2 == this.k[i])) {
                return i / 5
            }
        }
        return -1
    }

    private final Unit a() {
        this.g = -1
        this.h = -1
        this.i = -1
        this.j = -1
        this.k = null
        this.l = -1
        this.m = -1
        this.n = -1
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b6, code lost:
    
        throw new java.io.IOException("Invalid resource ids size (" + r1 + ").")
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ec, code lost:
    
        throw new java.io.IOException("Invalid chunk type (" + r1 + ").")
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final Unit b() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 467
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.a.a.a.a.a.b():Unit")
    }

    private final Int c(Int i) {
        if (this.g != 2) {
            throw IndexOutOfBoundsException("Current event is not START_TAG.")
        }
        Int i2 = i * 5
        if (i2 >= this.k.length) {
            throw IndexOutOfBoundsException("Invalid attribute index (" + i + ").")
        }
        return i2
    }

    public final Int a(Int i) {
        return this.k[c(i) + 3]
    }

    public final Unit a(InputStream inputStream) throws IOException {
        close()
        if (inputStream != null) {
            this.f724a = c(inputStream, false)
        }
    }

    public final Int b(Int i) {
        return this.k[c(i) + 4]
    }

    @Override // android.content.res.XmlResourceParser, java.lang.AutoCloseable
    public final Unit close() throws IOException {
        if (this.f725b) {
            this.f725b = false
            this.f724a.a()
            this.f724a = null
            this.c = null
            this.d = null
            this.e.a()
            a()
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Unit defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // android.util.AttributeSet
    public final Boolean getAttributeBooleanValue(Int i, Boolean z) {
        return getAttributeIntValue(i, z ? 1 : 0) != 0
    }

    @Override // android.util.AttributeSet
    public final Boolean getAttributeBooleanValue(String str, String str2, Boolean z) {
        Int iA = a(str, str2)
        return iA == -1 ? z : getAttributeBooleanValue(iA, z)
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public final Int getAttributeCount() {
        if (this.g != 2) {
            return -1
        }
        return this.k.length / 5
    }

    @Override // android.util.AttributeSet
    public final Float getAttributeFloatValue(Int i, Float f) {
        Int iC = c(i)
        return this.k[iC + 3] == 4 ? Float.intBitsToFloat(this.k[iC + 4]) : f
    }

    @Override // android.util.AttributeSet
    public final Float getAttributeFloatValue(String str, String str2, Float f) {
        Int iA = a(str, str2)
        return iA == -1 ? f : getAttributeFloatValue(iA, f)
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeIntValue(Int i, Int i2) {
        Int iC = c(i)
        Int i3 = this.k[iC + 3]
        return (i3 < 16 || i3 > 31) ? i2 : this.k[iC + 4]
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeIntValue(String str, String str2, Int i) {
        Int iA = a(str, str2)
        return iA == -1 ? i : getAttributeIntValue(iA, i)
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeListValue(Int i, Array<String> strArr, Int i2) {
        return 0
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeListValue(String str, String str2, Array<String> strArr, Int i) {
        return 0
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public final String getAttributeName(Int i) {
        Int i2 = this.k[c(i) + 1]
        return i2 == -1 ? "" : this.c.a(i2)
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeNameResource(Int i) {
        Int i2 = this.k[c(i) + 1]
        if (this.d == null || i2 < 0 || i2 >= this.d.length) {
            return 0
        }
        return this.d[i2]
    }

    @Override // android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public final String getAttributeNamespace(Int i) {
        Int i2 = this.k[c(i)]
        return i2 == -1 ? "" : this.c.a(i2)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getAttributePrefix(Int i) {
        Int iD = this.e.d(this.k[c(i)])
        return iD == -1 ? "" : this.c.a(iD)
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeResourceValue(Int i, Int i2) {
        Int iC = c(i)
        return this.k[iC + 3] == 1 ? this.k[iC + 4] : i2
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeResourceValue(String str, String str2, Int i) {
        Int iA = a(str, str2)
        return iA == -1 ? i : getAttributeResourceValue(iA, i)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getAttributeType(Int i) {
        return "CDATA"
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeUnsignedIntValue(Int i, Int i2) {
        return getAttributeIntValue(i, i2)
    }

    @Override // android.util.AttributeSet
    public final Int getAttributeUnsignedIntValue(String str, String str2, Int i) {
        Int iA = a(str, str2)
        return iA == -1 ? i : getAttributeUnsignedIntValue(iA, i)
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public final String getAttributeValue(Int i) {
        Int iC = c(i)
        if (this.k[iC + 3] != 3) {
            return ""
        }
        return this.c.a(this.k[iC + 2])
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public final String getAttributeValue(String str, String str2) {
        Int iA = a(str, str2)
        if (iA == -1) {
            return null
        }
        return getAttributeValue(iA)
    }

    @Override // android.util.AttributeSet
    public final String getClassAttribute() {
        if (this.m == -1) {
            return null
        }
        return this.c.a(this.k[c(this.m) + 2])
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int getColumnNumber() {
        return -1
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int getDepth() {
        return this.e.d() - 1
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int getEventType() {
        return this.g
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Boolean getFeature(String str) {
        return false
    }

    @Override // android.util.AttributeSet
    public final String getIdAttribute() {
        if (this.l == -1) {
            return null
        }
        return this.c.a(this.k[c(this.l) + 2])
    }

    @Override // android.util.AttributeSet
    public final Int getIdAttributeResourceValue(Int i) {
        if (this.l == -1) {
            return i
        }
        Int iC = c(this.l)
        return this.k[iC + 3] == 1 ? this.k[iC + 4] : i
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getInputEncoding() {
        return null
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int getLineNumber() {
        return this.h
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getName() {
        if (this.i == -1 || !(this.g == 2 || this.g == 3)) {
            return null
        }
        return this.c.a(this.i)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getNamespace() {
        return this.c.a(this.j)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getNamespace(String str) {
        throw RuntimeException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int getNamespaceCount(Int i) {
        return this.e.a(i)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getNamespacePrefix(Int i) {
        return this.c.a(this.e.b(i))
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getNamespaceUri(Int i) {
        return this.c.a(this.e.c(i))
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public final String getPositionDescription() {
        return "XML line #" + getLineNumber()
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getPrefix() {
        return this.c.a(this.e.d(this.j))
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Object getProperty(String str) {
        return null
    }

    @Override // android.util.AttributeSet
    public final Int getStyleAttribute() {
        if (this.n == -1) {
            return 0
        }
        return this.k[c(this.n) + 4]
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String getText() {
        if (this.i == -1 || this.g != 4) {
            return null
        }
        return this.c.a(this.i)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Array<Char> getTextCharacters(Array<Int> iArr) {
        String text = getText()
        if (text == null) {
            return null
        }
        iArr[0] = 0
        iArr[1] = text.length()
        Array<Char> cArr = new Char[text.length()]
        text.getChars(0, text.length(), cArr, 0)
        return cArr
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Boolean isAttributeDefault(Int i) {
        return false
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Boolean isEmptyElementTag() {
        return false
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Boolean isWhitespace() {
        return false
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int next() throws XmlPullParserException, IOException {
        if (this.f724a == null) {
            throw XmlPullParserException("Parser is not opened.", this, null)
        }
        try {
            b()
            return this.g
        } catch (IOException e) {
            close()
            throw e
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int nextTag() throws XmlPullParserException, IOException {
        Int next = next()
        if (next == 4 && isWhitespace()) {
            next = next()
        }
        if (next == 2 || next == 3) {
            return next
        }
        throw XmlPullParserException("Expected start or end tag.", this, null)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final String nextText() throws XmlPullParserException, IOException {
        if (getEventType() != 2) {
            throw XmlPullParserException("Parser must be on START_TAG to read next text.", this, null)
        }
        Int next = next()
        if (next != 4) {
            if (next == 3) {
                return ""
            }
            throw XmlPullParserException("Parser must be on START_TAG or TEXT to read text.", this, null)
        }
        String text = getText()
        if (next() != 3) {
            throw XmlPullParserException("Event TEXT must be immediately followed by END_TAG.", this, null)
        }
        return text
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Int nextToken() {
        return next()
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Unit require(Int i, String str, String str2) throws XmlPullParserException {
        if (i != getEventType() || ((str != null && !str.equals(getNamespace())) || (str2 != null && !str2.equals(getName())))) {
            throw XmlPullParserException(TYPES[i] + " is expected.", this, null)
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Unit setFeature(String str, Boolean z) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Unit setInput(InputStream inputStream, String str) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Unit setInput(Reader reader) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public final Unit setProperty(String str, Object obj) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }
}
