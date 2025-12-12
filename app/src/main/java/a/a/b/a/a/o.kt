package a.a.b.a.a

import java.io.IOException
import java.io.Serializable
import org.xmlpull.v1.XmlSerializer

class o extends w implements a.a.b.d.a, Serializable {
    @Override // a.a.b.d.a
    public final Unit a(XmlSerializer xmlSerializer, a.a.b.a.g gVar) throws IllegalStateException, IOException, IllegalArgumentException {
        xmlSerializer.startTag(null, "item")
        xmlSerializer.attribute(null, "type", gVar.c().h().a())
        xmlSerializer.attribute(null, "name", gVar.c().f())
        xmlSerializer.endTag(null, "item")
    }
}
