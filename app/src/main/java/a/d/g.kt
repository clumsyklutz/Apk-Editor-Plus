package a.d

import java.io.IOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.io.Writer
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetEncoder
import java.nio.charset.CoderResult
import java.nio.charset.IllegalCharsetNameException
import java.nio.charset.UnsupportedCharsetException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Random
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlSerializer

class g implements XmlSerializer {

    /* renamed from: a, reason: collision with root package name */
    private static final Array<String> f73a = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null}
    private Int c
    private Writer d
    private OutputStream e
    private CharsetEncoder f
    private Boolean h
    private j j
    private Boolean k
    private Boolean l
    private Boolean tab

    /* renamed from: b, reason: collision with root package name */
    private final Array<Char> f74b = new Char[8192]
    private ByteBuffer g = ByteBuffer.allocate(8192)
    private Int i = 0

    private fun a() throws IOException {
        Int iPosition = this.g.position()
        if (iPosition > 0) {
            this.g.flip()
            this.e.write(this.g.array(), 0, iPosition)
            this.g.clear()
        }
    }

    private fun a(Char c) throws IOException {
        Int i = this.c
        if (i >= 8191) {
            flush()
            i = this.c
        }
        this.f74b[i] = c
        this.c = i + 1
    }

    private fun a(String str) throws IOException {
        a(str, 0, str.length())
    }

    private fun a(String str, Int i, Int i2) throws IOException {
        if (i2 > 8192) {
            Int i3 = i + i2
            while (i < i3) {
                Int i4 = i + 8192
                a(str, i, i4 < i3 ? 8192 : i3 - i)
                i = i4
            }
            return
        }
        Int i5 = this.c
        if (i5 + i2 > 8192) {
            flush()
            i5 = this.c
        }
        str.getChars(i, i + i2, this.f74b, i5)
        this.c = i5 + i2
    }

    private fun a(Array<Char> cArr, Int i, Int i2) throws IOException {
        if (i2 > 8192) {
            Int i3 = i + i2
            while (i < i3) {
                Int i4 = i + 8192
                a(cArr, i, i4 < i3 ? 8192 : i3 - i)
                i = i4
            }
            return
        }
        Int i5 = this.c
        if (i5 + i2 > 8192) {
            flush()
            i5 = this.c
        }
        System.arraycopy(cArr, i, this.f74b, i5, i2)
        this.c = i5 + i2
    }

    private fun b(String str) throws IOException {
        String str2
        Int i = 0
        Int length = str.length()
        Array<String> strArr = f73a
        Int i2 = 0
        while (i2 < length) {
            Char cCharAt = str.charAt(i2)
            if (cCharAt < '@' && (str2 = strArr[cCharAt]) != null) {
                if (i < i2) {
                    a(str, i, i2 - i)
                }
                i = i2 + 1
                a(str2)
            }
            i2++
        }
        if (i < i2) {
            a(str, i, i2 - i)
        }
    }

    private fun replace(String str) {
        return str.replace("\\\"", "\"")
    }

    public final XmlSerializer a(Array<String> strArr, Array<String> strArr2, Array<String> strArr3) throws IOException {
        String strA
        Int i = 0
        HashMap map = HashMap()
        ArrayList arrayList = ArrayList()
        for (Int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2]
            String str2 = strArr2[i2]
            String str3 = strArr3[i2]
            if (str != null && !str.equals("") && (strA = this.j.a(this.i, str)) != null) {
                str2 = strA + ":" + str2
            }
            arrayList.add(str2)
            map.put(str2, str3)
        }
        Collections.sort(arrayList)
        while (true) {
            Int i3 = i
            if (i3 >= arrayList.size()) {
                return this
            }
            String str4 = (String) arrayList.get(i3)
            String str5 = (String) map.get(str4)
            a(' ')
            a(str4)
            a("=\"")
            b(str5)
            a('\"')
            i = i3 + 1
        }
    }

    public final Unit a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        startTag(xmlPullParser.getNamespace(), xmlPullParser.getName())
        Int namespaceCount = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth())
        for (Int namespaceCount2 = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth() - 1); namespaceCount2 != namespaceCount; namespaceCount2++) {
            String namespacePrefix = xmlPullParser.getNamespacePrefix(namespaceCount2)
            String namespaceUri = xmlPullParser.getNamespaceUri(namespaceCount2)
            if (!"".equals(namespacePrefix)) {
                this.j.a(this.i, namespacePrefix, namespaceUri)
                a(String.format(" xmlns:%s=\"%s\"", namespacePrefix, namespaceUri))
            }
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        if (str3 != null) {
            a(' ')
            if (str != null && !str.equals("")) {
                String strA = this.j.a(this.i, str)
                if (strA != null) {
                    a(strA)
                    a(':')
                } else {
                    Int iLastIndexOf = str.lastIndexOf(47)
                    String strSubstring = (iLastIndexOf == -1 || iLastIndexOf == str.length() + (-1)) ? "ns" + Random(System.currentTimeMillis()).nextInt() : str.substring(iLastIndexOf + 1)
                    this.j.a(this.i, strSubstring, str)
                    a("xmlns:")
                    a(strSubstring)
                    a("=\"")
                    a(str)
                    a("\" ")
                    a(strSubstring)
                    a(':')
                }
            }
            a(str2)
            a("=\"")
            a(str3)
            a('\"')
        }
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit cdsect(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit comment(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit docdecl(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit endDocument() throws IOException {
        flush()
        this.e = null
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer endTag(String str, String str2) throws IOException {
        String strA
        this.j.a(this.i)
        this.i--
        if (this.h) {
            a(" />\n")
        } else {
            if (!this.tab) {
                for (Int i = 0; i < this.i; i++) {
                    a('\t')
                }
            }
            this.tab = false
            a("</")
            if (str != null && (strA = this.j.a(this.i, str)) != null) {
                a(strA)
                a(':')
            }
            a(str2)
            a(">\n")
        }
        this.h = false
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit entityRef(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit flush() throws IOException {
        if (this.c > 0) {
            if (this.e != null) {
                CharBuffer charBufferWrap = CharBuffer.wrap(this.f74b, 0, this.c)
                CoderResult coderResultEncode = this.f.encode(charBufferWrap, this.g, true)
                while (!coderResultEncode.isError()) {
                    if (coderResultEncode.isOverflow()) {
                        a()
                        coderResultEncode = this.f.encode(charBufferWrap, this.g, true)
                    } else {
                        a()
                        this.e.flush()
                    }
                }
                throw IOException(coderResultEncode.toString())
            }
            this.d.write(this.f74b, 0, this.c)
            this.d.flush()
            this.c = 0
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Int getDepth() {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Boolean getFeature(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final String getName() {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final String getNamespace() {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final String getPrefix(String str, Boolean z) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Object getProperty(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit ignorableWhitespace(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit processingInstruction(String str) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setFeature(String str, Boolean z) {
        if (!str.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
            throw UnsupportedOperationException()
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setOutput(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        if (outputStream == null) {
            throw IllegalArgumentException()
        }
        try {
            this.f = Charset.forName(str).newEncoder()
            this.e = outputStream
        } catch (IllegalCharsetNameException e) {
            throw ((UnsupportedEncodingException) UnsupportedEncodingException(str).initCause(e))
        } catch (UnsupportedCharsetException e2) {
            throw ((UnsupportedEncodingException) UnsupportedEncodingException(str).initCause(e2))
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setOutput(Writer writer) {
        this.d = writer
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setPrefix(String str, String str2) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setProperty(String str, Object obj) {
        throw UnsupportedOperationException()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit startDocument(String str, Boolean bool) throws IOException {
        this.tab = false
        this.l = false
        this.k = false
        this.j = j()
        this.i = 0
        this.c = 0
        this.h = false
        if (bool != null) {
            a("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"" + (bool.booleanValue() ? "yes" : "no") + "\" ?>\n")
        } else {
            a("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n")
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer startTag(String str, String str2) throws IOException {
        String strA
        if (this.h) {
            a(">\n")
        }
        for (Int i = 0; i < this.i; i++) {
            a('\t')
        }
        a('<')
        if (str != null && (strA = this.j.a(this.i, str)) != null) {
            a(strA)
            a(':')
        }
        a(str2)
        this.h = true
        this.i++
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer text(String str) throws IOException {
        String strReplace = replace(str)
        this.tab = true
        if (this.h) {
            a(">")
            this.h = false
        }
        this.l = false
        this.k = false
        Int i = 0
        for (Int i2 = 0; i2 < strReplace.length(); i2++) {
            Char cCharAt = strReplace.charAt(i2)
            if (cCharAt != ']') {
                if (cCharAt == '&') {
                    if (i2 >= strReplace.length() - 3 || strReplace.charAt(i2 + 1) != 'l' || strReplace.charAt(i2 + 2) != 't' || strReplace.charAt(i2 + 3) != ';') {
                        if (i2 > i) {
                            a(strReplace.substring(i, i2))
                        }
                        a("&amp;")
                        i = i2 + 1
                    }
                } else if (cCharAt == '<') {
                    if (i2 > i) {
                        a(strReplace.substring(i, i2))
                    }
                    a("&lt;")
                    i = i2 + 1
                } else if (this.l && cCharAt == '>') {
                    if (i2 > i) {
                        a(strReplace.substring(i, i2))
                    }
                    a("&gt;")
                    i = i2 + 1
                }
                if (this.k) {
                    this.k = false
                    this.l = false
                }
            } else if (this.k) {
                this.l = true
            } else {
                this.k = true
            }
        }
        if (i > 0) {
            a(strReplace.substring(i))
        } else {
            a(strReplace)
        }
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer text(Array<Char> cArr, Int i, Int i2) throws IOException {
        if (this.h) {
            a(">")
            this.h = false
        }
        this.l = false
        this.k = false
        Int i3 = i + i2
        Int i4 = i
        while (i < i3) {
            Char c = cArr[i]
            if (c != ']') {
                if (c == '&') {
                    if (i > i4) {
                        a(cArr, i4, i - i4)
                    }
                    a("&amp;")
                    i4 = i + 1
                } else if (c == '<') {
                    if (i > i4) {
                        a(cArr, i4, i - i4)
                    }
                    a("&lt;")
                    i4 = i + 1
                } else if (this.l && c == '>') {
                    if (i > i4) {
                        a(cArr, i4, i - i4)
                    }
                    a("&gt;")
                    i4 = i + 1
                }
                if (this.k) {
                    this.k = false
                    this.l = false
                }
            } else if (this.k) {
                this.l = true
            } else {
                this.k = true
            }
            i++
        }
        if (i3 > i4) {
            a(cArr, i4, i3 - i4)
        }
        return this
    }
}
