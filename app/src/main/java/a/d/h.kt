package a.d

import androidx.appcompat.R
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Writer
import java.util.Locale
import org.xmlpull.v1.XmlSerializer

class h implements XmlSerializer {

    /* renamed from: b, reason: collision with root package name */
    private Int f76b
    private Writer c
    private Boolean d
    private Int e
    private Int f
    private String k

    /* renamed from: a, reason: collision with root package name */
    private final Array<Char> f75a = new Char[8192]
    private Array<String> g = new String[12]
    private Array<Int> h = new Int[4]
    private Array<String> i = new String[8]
    private Array<Boolean> j = new Boolean[4]

    private final String a(String str, Boolean z, Boolean z2) throws IOException {
        String string
        String str2
        for (Int i = (this.h[this.f + 1] << 1) - 2; i >= 0; i -= 2) {
            if (this.i[i + 1].equals(str) && (z || !this.i[i].isEmpty())) {
                String str3 = this.i[i]
                Int i2 = i + 2
                while (true) {
                    if (i2 >= (this.h[this.f + 1] << 1)) {
                        str2 = str3
                        break
                    }
                    if (this.i[i2].equals(str3)) {
                        str2 = null
                        break
                    }
                    i2++
                }
                if (str2 != null) {
                    return str2
                }
            }
        }
        if (!z2) {
            return null
        }
        if (str.isEmpty()) {
            string = ""
        } else {
            do {
                StringBuilder sb = StringBuilder("n")
                Int i3 = this.e
                this.e = i3 + 1
                string = sb.append(i3).toString()
                Int i4 = (this.h[this.f + 1] << 1) - 2
                while (true) {
                    if (i4 < 0) {
                        break
                    }
                    if (string.equals(this.i[i4])) {
                        string = null
                        break
                    }
                    i4 -= 2
                }
            } while (string == null);
        }
        Boolean z3 = this.d
        this.d = false
        setPrefix(string, str)
        this.d = z3
        return string
    }

    private final Unit a() throws IOException {
        if (this.f76b > 0) {
            this.c.write(this.f75a, 0, this.f76b)
            this.c.flush()
            this.f76b = 0
        }
    }

    private fun a(Char c) throws IOException {
        if (this.f76b >= 8192) {
            a()
        }
        Array<Char> cArr = this.f75a
        Int i = this.f76b
        this.f76b = i + 1
        cArr[i] = c
    }

    private fun a(String str) throws IOException {
        Int length = str.length()
        Int i = 0
        while (length > 0) {
            if (this.f76b == 8192) {
                a()
            }
            Int i2 = 8192 - this.f76b
            if (i2 > length) {
                i2 = length
            }
            str.getChars(i, i + i2, this.f75a, this.f76b)
            i += i2
            length -= i2
            this.f76b = i2 + this.f76b
        }
    }

    private final Unit a(String str, Int i) throws IOException {
        Int i2 = 0
        while (i2 < str.length()) {
            Char cCharAt = str.charAt(i2)
            switch (cCharAt) {
                case '\t':
                case '\n':
                case '\r':
                    if (i != -1) {
                        a("&#" + ((Int) cCharAt) + ';')
                    }
                    i2++
                    break
                case '&':
                    if (i2 + 4 < str.length() && str.charAt(i2 + 1) == 'a' && str.charAt(i2 + 2) == 'm' && str.charAt(i2 + 3) == 'p' && str.charAt(i2 + 4) == ';') {
                        i2 += 4
                        a("&amp;")
                        continue
                    } else if (i2 + 3 < str.length() && str.charAt(i2 + 1) == 'l' && str.charAt(i2 + 2) == 't' && str.charAt(i2 + 3) == ';') {
                        i2 += 3
                        a("&lt;")
                    } else {
                        a("&amp;")
                    }
                    i2++
                    break
                case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                    a("&lt;")
                    continue
                    i2++
                default:
                    if (cCharAt == i) {
                        a(cCharAt == '\"' ? "&quot;" : "&apos;")
                        continue
                    }
                    i2++
                    break
            }
            a(cCharAt)
            i2++
        }
    }

    private final Unit a(Boolean z) throws IOException {
        if (this.d) {
            this.f++
            this.d = false
            if (this.j.length <= this.f) {
                Array<Boolean> zArr = new Boolean[this.f + 4]
                System.arraycopy(this.j, 0, zArr, 0, this.f)
                this.j = zArr
            }
            this.j[this.f] = this.j[this.f - 1]
            for (Int i = this.h[this.f - 1]; i < this.h[this.f]; i++) {
                a(" xmlns")
                if (!this.i[i << 1].isEmpty()) {
                    a(':')
                    a(this.i[i << 1])
                } else if (getNamespace().isEmpty() && !this.i[(i << 1) + 1].isEmpty()) {
                    throw IllegalStateException("Cannot set default namespace for elements in no namespace")
                }
                a("=\"")
                a(this.i[(i << 1) + 1], 34)
                a('\"')
            }
            if (this.h.length <= this.f + 1) {
                Array<Int> iArr = new Int[this.f + 8]
                System.arraycopy(this.h, 0, iArr, 0, this.f + 1)
                this.h = iArr
            }
            this.h[this.f + 1] = this.h[this.f]
            if (z) {
                a(" />")
            } else {
                a('>')
            }
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        if (!this.d) {
            throw IllegalStateException("illegal position for attribute")
        }
        if (str == null) {
            str = ""
        }
        String strA = str.isEmpty() ? "" : a(str, false, true)
        a(' ')
        if (!strA.isEmpty()) {
            a(strA)
            a(':')
        }
        a(str2)
        a('=')
        Char c = str3.indexOf(34) == -1 ? '\"' : '\''
        a(c)
        a(str3, c)
        a(c)
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit cdsect(String str) throws IOException {
        a(false)
        String strReplace = str.replace("]]>", "]]]]><![CDATA[>")
        a("<![CDATA[")
        Int i = 0
        while (i < strReplace.length()) {
            Char cCharAt = strReplace.charAt(i)
            if ((cCharAt >= ' ' && cCharAt <= 55295) || cCharAt == '\t' || cCharAt == '\n' || cCharAt == '\r' || (cCharAt >= 57344 && cCharAt <= 65533)) {
                a(cCharAt)
            } else if (!Character.isHighSurrogate(cCharAt) || i >= strReplace.length() - 1) {
                Log.e("APKEDITOR", "Illegal character (U+" + Integer.toHexString(cCharAt) + ")")
            } else {
                a("]]>")
                i++
                Char cCharAt2 = strReplace.charAt(i)
                if (!Character.isLowSurrogate(cCharAt2)) {
                    throw IllegalArgumentException("Bad surrogate pair (U+" + Integer.toHexString(cCharAt) + " U+" + Integer.toHexString(cCharAt2) + ")")
                }
                a("&#" + Character.toCodePoint(cCharAt, cCharAt2) + ";")
                a("<![CDATA[")
            }
            i++
        }
        a("]]>")
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit comment(String str) throws IOException {
        a(false)
        a("<!--")
        a(str)
        a("-->")
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit docdecl(String str) throws IOException {
        a("<!DOCTYPE")
        a(str)
        a('>')
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit endDocument() throws IOException {
        while (this.f > 0) {
            endTag(this.g[(this.f * 3) - 3], this.g[(this.f * 3) - 1])
        }
        flush()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer endTag(String str, String str2) throws IOException {
        if (!this.d) {
            this.f--
        }
        if ((str == null && this.g[this.f * 3] != null) || ((str != null && !str.equals(this.g[this.f * 3])) || !this.g[(this.f * 3) + 2].equals(str2))) {
            throw IllegalArgumentException("</{" + str + "}" + str2 + "> does not match start")
        }
        if (this.d) {
            a(true)
            this.f--
        } else {
            if (this.j[this.f + 1]) {
                a("\r\n")
                for (Int i = 0; i < this.f; i++) {
                    a("  ")
                }
            }
            a("</")
            String str3 = this.g[(this.f * 3) + 1]
            if (!str3.isEmpty()) {
                a(str3)
                a(':')
            }
            a(str2)
            a('>')
        }
        this.h[this.f + 1] = this.h[this.f]
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit entityRef(String str) throws IOException {
        a(false)
        a('&')
        a(str)
        a(';')
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit flush() throws IOException {
        a(false)
        a()
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Int getDepth() {
        return this.d ? this.f + 1 : this.f
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Boolean getFeature(String str) {
        if ("http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
            return this.j[this.f]
        }
        return false
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final String getName() {
        if (getDepth() == 0) {
            return null
        }
        return this.g[(getDepth() * 3) - 1]
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final String getNamespace() {
        if (getDepth() == 0) {
            return null
        }
        return this.g[(getDepth() * 3) - 3]
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final String getPrefix(String str, Boolean z) {
        try {
            return a(str, false, z)
        } catch (IOException e) {
            throw RuntimeException(e.toString())
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Object getProperty(String str) {
        throw RuntimeException("Unsupported property")
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit ignorableWhitespace(String str) throws IOException {
        a(false)
        this.j[this.f] = false
        a(str)
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit processingInstruction(String str) throws IOException {
        a(false)
        a("<?")
        a(str)
        a("?>")
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setFeature(String str, Boolean z) {
        if (!"http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
            throw RuntimeException("Unsupported Feature")
        }
        this.j[this.f] = z
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setOutput(OutputStream outputStream, String str) {
        if (outputStream == null) {
            throw IllegalArgumentException("os == null")
        }
        setOutput(str == null ? OutputStreamWriter(outputStream) : OutputStreamWriter(outputStream, str))
        this.k = str
        if (str != null) {
            str.toLowerCase(Locale.US).startsWith("utf")
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setOutput(Writer writer) {
        this.c = writer
        this.h[0] = 2
        this.h[1] = 2
        this.i[0] = ""
        this.i[1] = ""
        this.i[2] = "xml"
        this.i[3] = "http://www.w3.org/XML/1998/namespace"
        this.d = false
        this.e = 0
        this.f = 0
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setPrefix(String str, String str2) throws IOException {
        a(false)
        if (str == null) {
            str = ""
        }
        if (str2 == null) {
            str2 = ""
        }
        if (str.equals(a(str2, true, false))) {
            return
        }
        Array<Int> iArr = this.h
        Int i = this.f + 1
        Int i2 = iArr[i]
        iArr[i] = i2 + 1
        Int i3 = i2 << 1
        if (this.i.length < i3 + 1) {
            Array<String> strArr = new String[this.i.length + 16]
            System.arraycopy(this.i, 0, strArr, 0, i3)
            this.i = strArr
        }
        this.i[i3] = str
        this.i[i3 + 1] = str2
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit setProperty(String str, Object obj) {
        throw RuntimeException("Unsupported Property:" + obj)
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final Unit startDocument(String str, Boolean bool) throws IOException {
        a("<?xml version=\"1.0\" ")
        if (str != null) {
            this.k = str
            str.toLowerCase(Locale.US).startsWith("utf")
        }
        if (this.k != null) {
            a("encoding=\"")
            a(this.k)
            a("\" ")
        }
        if (bool != null) {
            a("standalone='")
            a(bool.booleanValue() ? "yes" : "no")
            a("' ")
        }
        a("?>")
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer startTag(String str, String str2) throws IOException {
        a(false)
        if (this.j[this.f]) {
            a("\r\n")
            for (Int i = 0; i < this.f; i++) {
                a("  ")
            }
        }
        Int i2 = this.f * 3
        if (this.g.length < i2 + 3) {
            Array<String> strArr = new String[this.g.length + 12]
            System.arraycopy(this.g, 0, strArr, 0, i2)
            this.g = strArr
        }
        String strA = str == null ? "" : a(str, true, true)
        if (str != null && str.isEmpty()) {
            for (Int i3 = this.h[this.f]; i3 < this.h[this.f + 1]; i3++) {
                if (this.i[i3 << 1].isEmpty() && !this.i[(i3 << 1) + 1].isEmpty()) {
                    throw IllegalStateException("Cannot set default namespace for elements in no namespace")
                }
            }
        }
        Int i4 = i2 + 1
        this.g[i2] = str
        this.g[i4] = strA
        this.g[i4 + 1] = str2
        a('<')
        if (!strA.isEmpty()) {
            a(strA)
            a(':')
        }
        a(str2)
        this.d = true
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer text(String str) throws IOException {
        a(false)
        this.j[this.f] = false
        a(str, -1)
        return this
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public final XmlSerializer text(Array<Char> cArr, Int i, Int i2) throws IOException {
        text(String(cArr, i, i2))
        return this
    }
}
