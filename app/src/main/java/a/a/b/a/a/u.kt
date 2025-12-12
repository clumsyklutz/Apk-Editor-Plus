package a.a.b.a.a

import java.io.IOException
import java.util.regex.Pattern
import org.xmlpull.v1.XmlSerializer

class u extends t {

    /* renamed from: a, reason: collision with root package name */
    private static Pattern f20a = Pattern.compile("\\d{9,}")

    constructor(String str, Int i) {
        super("string", i, str)
    }

    constructor(String str, Int i, String str2, Boolean z) {
        super("string", i, str, str2, z)
    }

    @Override // a.a.b.a.a.t
    protected final String a() {
        throw UnsupportedOperationException()
    }

    @Override // a.a.b.a.a.t
    protected final Unit a(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        if (com.gmail.heagoo.a.c.a.d(this.f19b)) {
            xmlSerializer.attribute(null, "formatted", "false")
        }
    }

    @Override // a.a.b.a.a.t
    public final String f() {
        String strB = com.gmail.heagoo.a.c.a.b(this.f19b)
        return (strB == null || strB.isEmpty() || !f20a.matcher(strB).matches()) ? strB : "\\ " + strB
    }

    @Override // a.a.b.a.a.t
    public final String g() {
        return com.gmail.heagoo.a.c.a.e(com.gmail.heagoo.a.c.a.c(this.f19b))
    }

    @Override // a.a.b.a.a.t
    public final String h() {
        return com.gmail.heagoo.a.c.a.c(this.f19b)
    }
}
