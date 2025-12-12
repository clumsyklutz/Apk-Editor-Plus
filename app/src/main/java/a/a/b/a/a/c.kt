package a.a.b.a.a

import java.io.IOException
import java.io.Serializable
import org.xmlpull.v1.XmlSerializer

class c extends w implements a.a.b.d.a, Serializable {

    /* renamed from: a, reason: collision with root package name */
    protected final s f7a

    constructor(s sVar) {
        this.f7a = sVar
    }

    fun a(XmlSerializer xmlSerializer, a.a.b.a.g gVar) throws IllegalStateException, IOException, IllegalArgumentException {
        String strA = gVar.c().h().a()
        if ("style".equals(strA)) {
            v(this.f7a, new a.d.e[0], null).a(xmlSerializer, gVar)
            return
        }
        if ("array".equals(strA)) {
            a(this.f7a, new a.d.e[0]).a(xmlSerializer, gVar)
            return
        }
        if ("plurals".equals(strA)) {
            r(this.f7a, new a.d.e[0]).a(xmlSerializer, gVar)
            return
        }
        xmlSerializer.startTag(null, "item")
        xmlSerializer.attribute(null, "type", strA)
        xmlSerializer.attribute(null, "name", gVar.c().f())
        xmlSerializer.endTag(null, "item")
    }
}
