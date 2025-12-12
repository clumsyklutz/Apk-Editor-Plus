package a.a.b.a.a

import java.io.IOException
import java.util.HashMap
import java.util.Map
import org.xmlpull.v1.XmlSerializer

class h extends b {

    /* renamed from: b, reason: collision with root package name */
    private final a.d.Array<e> f9b
    private final Map c

    h(s sVar, Int i, Integer num, Integer num2, Boolean bool, a.d.Array<e> eVarArr) {
        super(sVar, i, num, num2, bool)
        this.c = HashMap()
        this.f9b = eVarArr
    }

    @Override // a.a.b.a.a.b
    public final String a(t tVar) {
        s sVar
        if (tVar is q) {
            Int iB = ((q) tVar).b()
            String strF = (String) this.c.get(Integer.valueOf(iB))
            if (strF == null) {
                a.d.Array<e> eVarArr = this.f9b
                Int length = eVarArr.length
                Int i = 0
                while (true) {
                    if (i >= length) {
                        sVar = null
                        break
                    }
                    a.d.e eVar = eVarArr[i]
                    if (((q) eVar.f72b).b() == iB) {
                        sVar = (s) eVar.f71a
                        break
                    }
                    i++
                }
                if (sVar != null) {
                    strF = sVar.d().f()
                    this.c.put(Integer.valueOf(iB), strF)
                }
            }
            if (strF != null) {
                return strF
            }
        }
        return super.a(tVar)
    }

    @Override // a.a.b.a.a.b
    protected final Unit a(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        for (a.d.e eVar : this.f9b) {
            Int iB = ((q) eVar.f72b).b()
            xmlSerializer.startTag(null, "enum")
            xmlSerializer.attribute(null, "name", ((s) eVar.f71a).d().f())
            xmlSerializer.attribute(null, "value", String.valueOf(iB))
            xmlSerializer.endTag(null, "enum")
        }
    }
}
