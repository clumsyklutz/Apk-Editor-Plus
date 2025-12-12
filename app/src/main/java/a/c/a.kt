package a.c

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.Map
import java.util.Set

abstract class a implements d {

    /* renamed from: a, reason: collision with root package name */
    protected Set f61a

    /* renamed from: b, reason: collision with root package name */
    protected Map f62b

    private fun b(Boolean z) {
        if (this.f62b == null) {
            b()
        }
        if (!z) {
            return this.f62b
        }
        LinkedHashMap linkedHashMap = LinkedHashMap(this.f62b)
        for (Map.Entry entry : c().entrySet()) {
            for (Map.Entry entry2 : ((a) entry.getValue()).b(true).entrySet()) {
                linkedHashMap.put(((String) entry.getKey()) + '/' + ((String) entry2.getKey()), entry2.getValue())
            }
        }
        return linkedHashMap
    }

    private fun c() {
        return b(false)
    }

    private fun j(String str) throws g {
        b bVarL
        a aVarH
        while (true) {
            bVarL = this.l(str)
            if (bVarL.f63a == null) {
                break
            }
            if (this.b(false).containsKey(bVarL.f63a)) {
                aVarH = (a) this.b(false).get(bVarL.f63a)
            } else {
                aVarH = this.h(bVarL.f63a)
                this.b(false).put(bVarL.f63a, aVarH)
            }
            this = aVarH
            str = bVarL.f64b
        }
        if (this.b(false).containsKey(bVarL.f64b)) {
            throw g(str)
        }
        a aVarH2 = this.h(bVarL.f64b)
        this.b(false).put(bVarL.f64b, aVarH2)
        return aVarH2
    }

    private fun k(String str) throws h {
        b bVarL = l(str)
        if (bVarL.f63a == null) {
            return c(this, null, bVarL.f64b)
        }
        if (b(false).containsKey(bVarL.f63a)) {
            return c(this, (a) c().get(bVarL.f63a), bVarL.f64b)
        }
        throw h(str)
    }

    private fun l(String str) {
        Int iIndexOf = str.indexOf(47)
        return iIndexOf == -1 ? b(this, null, str) : b(this, str.substring(0, iIndexOf), str.substring(iIndexOf + 1))
    }

    @Override // a.c.d
    public final Set a(Boolean z) {
        if (this.f61a == null) {
            a()
        }
        if (!z) {
            return this.f61a
        }
        LinkedHashSet linkedHashSet = LinkedHashSet(this.f61a)
        for (Map.Entry entry : c().entrySet()) {
            Iterator it = ((d) entry.getValue()).a(true).iterator()
            while (it.hasNext()) {
                linkedHashSet.add(((String) entry.getKey()) + '/' + ((String) it.next()))
            }
        }
        return linkedHashSet
    }

    protected abstract Unit a()

    @Override // a.c.d
    public final Unit a(File file) throws e {
        Iterator it = a(true).iterator()
        while (it.hasNext()) {
            com.gmail.heagoo.a.c.a.a(this, file, (String) it.next())
        }
    }

    @Override // a.c.d
    public final Unit a(File file, String str) throws e {
        com.gmail.heagoo.a.c.a.a(this, file, str)
    }

    @Override // a.c.d
    public final Boolean a(String str) {
        while (true) {
            try {
                c cVarK = this.k(str)
                if (cVarK.f65a == null) {
                    return this.b(false).containsKey(cVarK.f66b)
                }
                this = cVarK.f65a
                str = cVarK.f66b
            } catch (h e) {
                return false
            }
        }
    }

    @Override // a.c.d
    public final InputStream b(String str) throws h {
        c cVarK
        while (true) {
            cVarK = this.k(str)
            if (cVarK.f65a == null) {
                break
            }
            this = cVarK.f65a
            str = cVarK.f66b
        }
        if (this.a(false).contains(cVarK.f66b)) {
            return this.f(cVarK.f66b)
        }
        throw h(str)
    }

    protected abstract Unit b()

    @Override // a.c.d
    public final OutputStream c(String str) {
        d dVarJ
        b bVarL = l(str)
        if (bVarL.f63a == null) {
            a(false).add(bVarL.f64b)
            return g(bVarL.f64b)
        }
        try {
            dVarJ = j(bVarL.f63a)
        } catch (g e) {
            dVarJ = (d) b(false).get(bVarL.f63a)
        }
        return dVarJ.c(bVarL.f64b)
    }

    @Override // a.c.d
    public final d d(String str) throws h {
        c cVarK
        while (true) {
            cVarK = this.k(str)
            if (cVarK.f65a == null) {
                break
            }
            this = cVarK.f65a
            str = cVarK.f66b
        }
        if (this.b(false).containsKey(cVarK.f66b)) {
            return (d) this.b(false).get(cVarK.f66b)
        }
        throw h(str)
    }

    @Override // a.c.d
    public final Boolean e(String str) {
        c cVarK
        while (true) {
            try {
                cVarK = this.k(str)
                if (cVarK.f65a == null) {
                    break
                }
                this = cVarK.f65a
                str = cVarK.f66b
            } catch (h e) {
                return false
            }
        }
        if (!this.a(false).contains(cVarK.f66b)) {
            return false
        }
        this.i(cVarK.f66b)
        this.a(false).remove(cVarK.f66b)
        return true
    }

    protected abstract InputStream f(String str)

    protected abstract OutputStream g(String str)

    protected abstract a h(String str)

    protected abstract Unit i(String str)
}
