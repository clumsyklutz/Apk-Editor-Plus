package com.gmail.heagoo.appdm.util

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
import org.xmlpull.v1.XmlSerializer

class d implements XmlSerializer {

    /* renamed from: a, reason: collision with root package name */
    private static final Array<String> f1411a = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null}
    private Int c
    private Writer d
    private OutputStream e
    private CharsetEncoder f
    private Boolean h

    /* renamed from: b, reason: collision with root package name */
    private final Array<Char> f1412b = new Char[8192]
    private ByteBuffer g = ByteBuffer.allocate(8192)

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
        this.f1412b[i] = c
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
        str.getChars(i, i + i2, this.f1412b, i5)
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
        System.arraycopy(cArr, i, this.f1412b, i5, i2)
        this.c = i5 + i2
    }

    private fun b(String str) throws IOException {
        String str2
        Int i = 0
        Int length = str.length()
        Array<String> strArr = f1411a
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

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        a(' ')
        if (str != null) {
            a(str)
            a(':')
        }
        a(str2)
        a("=\"")
        b(str3)
        a('\"')
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
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer endTag(String str, String str2) throws IOException {
        if (this.h) {
            a(" />\n")
        } else {
            a("</")
            if (str != null) {
                a(str)
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
                CharBuffer charBufferWrap = CharBuffer.wrap(this.f1412b, 0, this.c)
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
            this.d.write(this.f1412b, 0, this.c)
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
        a("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"" + (bool.booleanValue() ? "yes" : "no") + "\" ?>\n")
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer startTag(String str, String str2) throws IOException {
        if (this.h) {
            a(">\n")
        }
        a('<')
        if (str != null) {
            a(str)
            a(':')
        }
        a(str2)
        this.h = true
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer text(String str) throws IOException {
        if (this.h) {
            a(">")
            this.h = false
        }
        b(str)
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer text(Array<Char> cArr, Int i, Int i2) throws IOException {
        String str
        if (this.h) {
            a(">")
            this.h = false
        }
        Array<String> strArr = f1411a
        Int i3 = i + i2
        Int i4 = i
        while (i < i3) {
            Char c = cArr[i]
            if (c < '@' && (str = strArr[c]) != null) {
                if (i4 < i) {
                    a(cArr, i4, i - i4)
                }
                i4 = i + 1
                a(str)
            }
            i++
        }
        if (i4 < i) {
            a(cArr, i4, i - i4)
        }
        return this
    }
}
