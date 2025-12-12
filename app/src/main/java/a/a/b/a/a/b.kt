package a.a.b.a.a

import androidx.core.internal.view.SupportMenu
import java.io.IOException
import org.xmlpull.v1.XmlSerializer

class b extends c implements a.a.b.d.a {

    /* renamed from: b, reason: collision with root package name */
    private final Int f6b
    private final Integer c
    private final Integer d
    private final Boolean e

    b(s sVar, Int i, Integer num, Integer num2, Boolean bool) {
        super(sVar)
        this.f6b = i
        this.c = num
        this.d = num2
        this.e = bool
    }

    fun a(s sVar, a.d.Array<e> eVarArr, x xVar, a.a.b.a.e eVar) throws a.a.ExceptionA {
        Int i
        Int iB = ((q) eVarArr[0].f72b).b()
        Int i2 = iB & SupportMenu.USER_MASK
        Integer numValueOf = null
        Integer numValueOf2 = null
        Boolean boolValueOf = null
        Int i3 = 1
        while (true) {
            i = i3
            if (i < eVarArr.length) {
                switch (((Integer) eVarArr[i].f71a).intValue()) {
                    case 16777217:
                        numValueOf = Integer.valueOf(((q) eVarArr[i].f72b).b())
                        continue
                        i3 = i + 1
                    case 16777218:
                        numValueOf2 = Integer.valueOf(((q) eVarArr[i].f72b).b())
                        continue
                        i3 = i + 1
                    case 16777219:
                        boolValueOf = Boolean.valueOf(((q) eVarArr[i].f72b).b() != 0)
                        continue
                        i3 = i + 1
                }
            }
        }
        if (i == eVarArr.length) {
            return b(sVar, i2, numValueOf, numValueOf2, boolValueOf)
        }
        a.d.Array<e> eVarArr2 = new a.d.e[eVarArr.length - i]
        Int i4 = i
        Int i5 = 0
        while (i4 < eVarArr.length) {
            Int iIntValue = ((Integer) eVarArr[i4].f71a).intValue()
            eVar.a(iIntValue)
            eVarArr2[i5] = new a.d.e(xVar.a(iIntValue, (String) null), (q) eVarArr[i4].f72b)
            i4++
            i5++
        }
        switch (16711680 & iB) {
            case 65536:
                return h(sVar, i2, numValueOf, numValueOf2, boolValueOf, eVarArr2)
            case 131072:
                return j(sVar, i2, numValueOf, numValueOf2, boolValueOf, eVarArr2)
            default:
                throw new a.a.ExceptionA("Could not decode attr value")
        }
    }

    fun a(t tVar) {
        return null
    }

    protected fun a(XmlSerializer xmlSerializer) {
    }

    @Override // a.a.b.a.a.c, a.a.b.d.a
    public final Unit a(XmlSerializer xmlSerializer, a.a.b.a.g gVar) throws IllegalStateException, IOException, IllegalArgumentException {
        String str = (this.f6b & 1) != 0 ? "|reference" : ""
        if ((this.f6b & 2) != 0) {
            str = str + "|string"
        }
        if ((this.f6b & 4) != 0) {
            str = str + "|integer"
        }
        if ((this.f6b & 8) != 0) {
            str = str + "|Boolean"
        }
        if ((this.f6b & 16) != 0) {
            str = str + "|color"
        }
        if ((this.f6b & 32) != 0) {
            str = str + "|Float"
        }
        if ((this.f6b & 64) != 0) {
            str = str + "|dimension"
        }
        if ((this.f6b & 128) != 0) {
            str = str + "|fraction"
        }
        String strSubstring = str.isEmpty() ? null : str.substring(1)
        xmlSerializer.startTag(null, "attr")
        xmlSerializer.attribute(null, "name", gVar.c().f())
        if (strSubstring != null) {
            xmlSerializer.attribute(null, "format", strSubstring)
        }
        if (this.c != null) {
            xmlSerializer.attribute(null, "min", this.c.toString())
        }
        if (this.d != null) {
            xmlSerializer.attribute(null, "max", this.d.toString())
        }
        if (this.e != null && this.e.booleanValue()) {
            xmlSerializer.attribute(null, "localization", "suggested")
        }
        a(xmlSerializer)
        xmlSerializer.endTag(null, "attr")
    }
}
