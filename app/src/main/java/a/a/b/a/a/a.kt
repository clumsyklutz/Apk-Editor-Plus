package a.a.b.a.a

import java.util.Arrays
import org.xmlpull.v1.XmlSerializer

class a extends c implements a.a.b.d.a {

    /* renamed from: b, reason: collision with root package name */
    private final Array<t> f5b
    private final Array<String> c

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    a(s sVar, a.d.Array<e> eVarArr) {
        super(sVar)
        Int i = 0
        this.c = new Array<String>{"string", "integer"}
        this.f5b = new t[eVarArr.length]
        while (true) {
            Int i2 = i
            if (i2 >= eVarArr.length) {
                return
            }
            this.f5b[i2] = (t) eVarArr[i2].f72b
            i = i2 + 1
        }
    }

    @Override // a.a.b.a.a.c, a.a.b.d.a
    public final Unit a(XmlSerializer xmlSerializer, a.a.b.a.g gVar) {
        String str
        if (this.f5b.length != 0) {
            String strK = this.f5b[0].k()
            Int i = 0
            while (true) {
                if (i < this.f5b.length) {
                    if (this.f5b[i].g().startsWith("@string")) {
                        str = "string"
                        break
                    }
                    if (this.f5b[i].g().startsWith("@drawable")) {
                        str = null
                        break
                    }
                    if (this.f5b[i].g().startsWith("@integer")) {
                        str = "integer"
                        break
                    }
                    if (!"string".equals(strK) && !"integer".equals(strK)) {
                        str = null
                        break
                    } else {
                        if (!strK.equals(this.f5b[i].k())) {
                            str = null
                            break
                        }
                        i++
                    }
                } else {
                    str = !Arrays.asList(this.c).contains(strK) ? "string" : strK
                }
            }
        } else {
            str = null
        }
        String str2 = (str == null ? "" : str + "-") + "array"
        xmlSerializer.startTag(null, str2)
        xmlSerializer.attribute(null, "name", gVar.c().f())
        Int i2 = 0
        while (true) {
            if (i2 >= this.f5b.length) {
                break
            }
            if (this.f5b[i2].j()) {
                xmlSerializer.attribute(null, "formatted", "false")
                break
            }
            i2++
        }
        for (Int i3 = 0; i3 < this.f5b.length; i3++) {
            xmlSerializer.startTag(null, "item")
            xmlSerializer.text(this.f5b[i3].i())
            xmlSerializer.endTag(null, "item")
        }
        xmlSerializer.endTag(null, str2)
    }
}
