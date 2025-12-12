package a.a.b.a.a

import java.io.IOException
import org.xmlpull.v1.XmlSerializer

abstract class t extends p implements a.a.b.d.a {

    /* renamed from: a, reason: collision with root package name */
    private String f18a

    /* renamed from: b, reason: collision with root package name */
    protected String f19b
    private String c
    private Boolean d

    protected constructor(String str, Int i, String str2) {
        super(i)
        this.d = false
        this.f18a = str
        this.f19b = str2
    }

    protected constructor(String str, Int i, String str2, String str3, Boolean z) {
        super(i)
        this.d = false
        this.f18a = str
        this.f19b = str2
        this.c = str3
        this.d = z
    }

    protected abstract String a()

    protected fun a(XmlSerializer xmlSerializer) {
    }

    @Override // a.a.b.d.a
    public final Unit a(XmlSerializer xmlSerializer, a.a.b.a.g gVar) throws IllegalStateException, IOException, IllegalArgumentException {
        String strA = gVar.c().h().a()
        Boolean z = ("reference".equals(this.f18a) || strA.equals(this.f18a)) ? false : true
        String strH = h()
        Boolean z2 = (strA.equalsIgnoreCase("color") || !strH.contains("@") || gVar.a().contains("string")) ? z : true
        String str = z2 ? "item" : strA
        xmlSerializer.startTag(null, str)
        if (z2) {
            xmlSerializer.attribute(null, "type", strA)
        }
        xmlSerializer.attribute(null, "name", gVar.c().f())
        a(xmlSerializer)
        if (!strH.isEmpty()) {
            xmlSerializer.ignorableWhitespace(strH)
        }
        xmlSerializer.endTag(null, str)
    }

    fun f() {
        return this.f19b != null ? this.f19b : a()
    }

    fun g() {
        return h()
    }

    fun h() {
        return this.f19b != null ? this.f19b : a()
    }

    public final String i() {
        return h().replace("&amp;", "&").replace("&lt;", "<")
    }

    public final Boolean j() {
        return com.gmail.heagoo.a.c.a.d(this.f19b)
    }

    public final String k() {
        return this.f18a
    }

    public final String l() {
        return this.f19b
    }

    public final String m() {
        return this.c
    }

    public final Boolean n() {
        return this.d
    }

    fun toString() {
        try {
            return h()
        } catch (a.a.a e) {
            return null
        }
    }
}
