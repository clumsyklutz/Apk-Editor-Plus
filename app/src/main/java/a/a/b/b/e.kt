package a.a.b.b

import android.content.res.XmlResourceParser
import android.util.TypedValue
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.util.logging.Level
import java.util.logging.Logger
import org.xmlpull.v1.XmlPullParserException

class e implements XmlResourceParser {
    private static val t = Logger.getLogger(e.class.getName())

    /* renamed from: b, reason: collision with root package name */
    private String f51b
    private a.d.f c
    private i d
    private a.a.a e
    private n g
    private Array<Int> h
    private Boolean k
    private Int l
    private Int m
    private Int n
    private Int o
    private Array<Int> p
    private Int q
    private Int r
    private Int s

    /* renamed from: a, reason: collision with root package name */
    private Boolean f50a = false
    private Boolean f = false
    private f i = f()
    private String j = "http://schemas.android.com/apk/res/android"

    constructor() {
        a()
    }

    private final Int a(Int i) {
        if (this.l != 2) {
            throw IndexOutOfBoundsException("Current event is not START_TAG.")
        }
        Int i2 = i * 5
        if (i2 >= this.p.length) {
            throw IndexOutOfBoundsException("Invalid attribute index (" + i + ").")
        }
        return i2
    }

    private final Int a(String str, String str2) {
        Int iA
        if (this.g == null || str2 == null || (iA = this.g.a(str2)) == -1) {
            return -1
        }
        Int iA2 = str != null ? this.g.a(str) : -1
        for (Int i = 0; i != this.p.length; i += 5) {
            if (iA == this.p[i + 1] && (iA2 == -1 || iA2 == this.p[i])) {
                return i / 5
            }
        }
        return -1
    }

    private final Unit a() {
        this.l = -1
        this.m = -1
        this.n = -1
        this.o = -1
        this.p = null
        this.q = -1
        this.r = -1
        this.s = -1
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a4, code lost:
    
        throw new java.io.IOException("Invalid resource ids size (" + r1 + ").")
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00da, code lost:
    
        throw new java.io.IOException("Invalid chunk type (" + r1 + ").")
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final Unit b() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: a.a.b.b.e.b():Unit")
    }

    public final Unit a(i iVar) {
        this.d = iVar
    }

    public final Unit a(Boolean z) {
        this.f50a = z
    }

    @Override // android.content.res.XmlResourceParser, java.lang.AutoCloseable
    fun close() {
        if (this.f) {
            this.f = false
            this.c = null
            this.g = null
            this.h = null
            this.i.a()
            a()
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // android.util.AttributeSet
    fun getAttributeBooleanValue(Int i, Boolean z) {
        return getAttributeIntValue(i, z ? 1 : 0) != 0
    }

    @Override // android.util.AttributeSet
    fun getAttributeBooleanValue(String str, String str2, Boolean z) {
        Int iA = a(str, str2)
        return iA == -1 ? z : getAttributeBooleanValue(iA, z)
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    fun getAttributeCount() {
        if (this.l != 2) {
            return -1
        }
        return this.p.length / 5
    }

    @Override // android.util.AttributeSet
    fun getAttributeFloatValue(Int i, Float f) {
        Int iA = a(i)
        return this.p[iA + 3] == 4 ? Float.intBitsToFloat(this.p[iA + 4]) : f
    }

    @Override // android.util.AttributeSet
    fun getAttributeFloatValue(String str, String str2, Float f) {
        Int iA = a(str, str2)
        return iA == -1 ? f : getAttributeFloatValue(iA, f)
    }

    @Override // android.util.AttributeSet
    fun getAttributeIntValue(Int i, Int i2) {
        Int iA = a(i)
        Int i3 = this.p[iA + 3]
        return (i3 < 16 || i3 > 31) ? i2 : this.p[iA + 4]
    }

    @Override // android.util.AttributeSet
    fun getAttributeIntValue(String str, String str2, Int i) {
        Int iA = a(str, str2)
        return iA == -1 ? i : getAttributeIntValue(iA, i)
    }

    @Override // android.util.AttributeSet
    fun getAttributeListValue(Int i, Array<String> strArr, Int i2) {
        return 0
    }

    @Override // android.util.AttributeSet
    fun getAttributeListValue(String str, String str2, Array<String> strArr, Int i) {
        return 0
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    fun getAttributeName(Int i) {
        Int i2 = this.p[a(i) + 1]
        if (i2 == -1) {
            return ""
        }
        try {
            Int attributeNameResource = getAttributeNameResource(i)
            if (attributeNameResource != 0) {
                return this.d.a(attributeNameResource)
            }
        } catch (a.a.a e) {
        } catch (NullPointerException e2) {
        }
        return this.g.a(i2)
    }

    @Override // android.util.AttributeSet
    fun getAttributeNameResource(Int i) {
        Int i2 = this.p[a(i) + 1]
        if (this.h == null || i2 < 0 || i2 >= this.h.length) {
            return 0
        }
        return this.h[i2]
    }

    @Override // android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    fun getAttributeNamespace(Int i) {
        Int iA = a(i)
        Int i2 = this.p[iA]
        if (i2 == -1) {
            return ""
        }
        String strA = this.g.a(i2)
        if (strA != null && strA.length() != 0) {
            return strA
        }
        Int i3 = this.p[iA + 1]
        if (this.h == null || i3 < 0 || i3 >= this.h.length || (this.h[i3] & 2130706432) != 2130706432) {
            return this.g.a(i3).length() == 0 ? this.j : strA
        }
        if (this.f51b == null) {
            Int i4 = 0
            while (true) {
                if (i4 < this.g.a()) {
                    String strA2 = this.g.a(i4)
                    if (strA2 != null && strA2.startsWith("http://") && !strA2.equals(this.j)) {
                        this.f51b = strA2
                        break
                    }
                    i4++
                } else {
                    break
                }
            }
        }
        String str = this.f51b
        if (str == null) {
            str = strA
        }
        return str
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getAttributePrefix(Int i) {
        Int iD = this.i.d(this.p[a(i)])
        return iD == -1 ? "" : this.g.a(iD)
    }

    @Override // android.util.AttributeSet
    fun getAttributeResourceValue(Int i, Int i2) {
        Int iA = a(i)
        return this.p[iA + 3] == 1 ? this.p[iA + 4] : i2
    }

    @Override // android.util.AttributeSet
    fun getAttributeResourceValue(String str, String str2, Int i) {
        Int iA = a(str, str2)
        return iA == -1 ? i : getAttributeResourceValue(iA, i)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getAttributeType(Int i) {
        return "CDATA"
    }

    @Override // android.util.AttributeSet
    fun getAttributeUnsignedIntValue(Int i, Int i2) {
        return getAttributeIntValue(i, i2)
    }

    @Override // android.util.AttributeSet
    fun getAttributeUnsignedIntValue(String str, String str2, Int i) {
        Int iA = a(str, str2)
        return iA == -1 ? i : getAttributeUnsignedIntValue(iA, i)
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    fun getAttributeValue(Int i) {
        String strA
        Int iA = a(i)
        Int i2 = this.p[iA + 3]
        Int i3 = this.p[iA + 4]
        Int i4 = this.p[iA + 2]
        if (this.d != null) {
            if (i4 == -1) {
                strA = null
            } else {
                try {
                    strA = com.gmail.heagoo.a.c.a.a(this.g.a(i4))
                } catch (a.a.a e) {
                    if (this.e == null) {
                        this.e = e
                    }
                    t.log(Level.WARNING, String.format("Could not decode attr value, using raw value instead: ns=%s, name=%s, value=0x%08x", getAttributePrefix(i), getAttributeName(i), Integer.valueOf(i3)), (Throwable) e)
                }
            }
            return this.d.a(i2, i3, (this.f50a && (i3 & 2130706432) == 2130706432) ? null : strA, getAttributeNameResource(i))
        }
        return TypedValue.coerceToString(i2, i3)
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    fun getAttributeValue(String str, String str2) {
        Int iA = a(str, str2)
        return iA == -1 ? "" : getAttributeValue(iA)
    }

    @Override // android.util.AttributeSet
    fun getClassAttribute() {
        if (this.r == -1) {
            return null
        }
        return this.g.a(this.p[a(this.r) + 2])
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getColumnNumber() {
        return -1
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getDepth() {
        return this.i.d() - 1
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getEventType() {
        return this.l
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getFeature(String str) {
        return false
    }

    @Override // android.util.AttributeSet
    fun getIdAttribute() {
        if (this.q == -1) {
            return null
        }
        return this.g.a(this.p[a(this.q) + 2])
    }

    @Override // android.util.AttributeSet
    fun getIdAttributeResourceValue(Int i) {
        if (this.q == -1) {
            return i
        }
        Int iA = a(this.q)
        return this.p[iA + 3] == 1 ? this.p[iA + 4] : i
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getInputEncoding() {
        return null
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getLineNumber() {
        return this.m
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getName() {
        if (this.n == -1 || !(this.l == 2 || this.l == 3)) {
            return null
        }
        return this.g.a(this.n)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getNamespace() {
        return this.g.a(this.o)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getNamespace(String str) {
        throw RuntimeException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getNamespaceCount(Int i) {
        return this.i.a(i)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getNamespacePrefix(Int i) {
        return this.g.a(this.i.b(i))
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getNamespaceUri(Int i) {
        return this.g.a(this.i.c(i))
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    fun getPositionDescription() {
        return "XML line #" + getLineNumber()
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getPrefix() {
        return this.g.a(this.i.d(this.o))
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getProperty(String str) {
        return null
    }

    @Override // android.util.AttributeSet
    fun getStyleAttribute() {
        if (this.s == -1) {
            return 0
        }
        return this.p[a(this.s) + 4]
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun getText() {
        if (this.n == -1 || this.l != 4) {
            return null
        }
        return this.g.a(this.n)
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public Array<Char> getTextCharacters(Array<Int> iArr) {
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
    fun isAttributeDefault(Int i) {
        return false
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun isEmptyElementTag() {
        return false
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun isWhitespace() {
        return false
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun next() throws XmlPullParserException, IOException {
        if (this.c == null) {
            throw XmlPullParserException("Parser is null.", this, null)
        }
        try {
            b()
            return this.l
        } catch (IOException e) {
            close()
            throw e
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun nextTag() throws XmlPullParserException, IOException {
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
    fun nextText() throws XmlPullParserException, IOException {
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
    fun nextToken() {
        return next()
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun require(Int i, String str, String str2) throws XmlPullParserException {
        if (i != getEventType() || ((str != null && !str.equals(getNamespace())) || (str2 != null && !str2.equals(getName())))) {
            throw XmlPullParserException(TYPES[i] + " is expected.", this, null)
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun setFeature(String str, Boolean z) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun setInput(InputStream inputStream, String str) {
        close()
        if (inputStream != null) {
            this.c = new a.d.f(new a.a.b.a.b(inputStream))
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun setInput(Reader reader) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }

    @Override // org.xmlpull.v1.XmlPullParser
    fun setProperty(String str, Object obj) throws XmlPullParserException {
        throw XmlPullParserException("Method is not supported.")
    }
}
