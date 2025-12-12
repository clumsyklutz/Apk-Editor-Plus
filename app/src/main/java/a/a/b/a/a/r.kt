package a.a.b.a.a

import org.xmlpull.v1.XmlSerializer

class r extends c implements a.a.b.d.a {
    private static final Array<String> c = {"other", "zero", "one", "two", "few", "many"}

    /* renamed from: b, reason: collision with root package name */
    private final Array<t> f17b

    r(s sVar, a.d.Array<e> eVarArr) {
        super(sVar)
        this.f17b = new t[6]
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= eVarArr.length) {
                return
            }
            this.f17b[((Integer) eVarArr[i2].f71a).intValue() - 16777220] = (t) eVarArr[i2].f72b
            i = i2 + 1
        }
    }

    @Override // a.a.b.a.a.c, a.a.b.d.a
    public final Unit a(XmlSerializer xmlSerializer, a.a.b.a.g gVar) {
        xmlSerializer.startTag(null, "plurals")
        xmlSerializer.attribute(null, "name", gVar.c().f())
        for (Int i = 0; i < 6; i++) {
            t tVar = this.f17b[i]
            if (tVar != null) {
                xmlSerializer.startTag(null, "item")
                xmlSerializer.attribute(null, "quantity", c[i])
                xmlSerializer.text(com.gmail.heagoo.a.c.a.e(tVar.i()))
                xmlSerializer.endTag(null, "item")
            }
        }
        xmlSerializer.endTag(null, "plurals")
    }
}
