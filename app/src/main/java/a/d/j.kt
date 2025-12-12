package a.d

import android.util.Pair
import java.util.ArrayList
import java.util.Iterator

class j {

    /* renamed from: a, reason: collision with root package name */
    private Array<Object> f78a = new Object[4]

    /* renamed from: b, reason: collision with root package name */
    private Int f79b = 0
    private Int c

    private fun a(ArrayList arrayList, String str) {
        Iterator it = arrayList.iterator()
        while (it.hasNext()) {
            Pair pair = (Pair) it.next()
            if (((String) pair.second).equals(str)) {
                return (String) pair.first
            }
        }
        return null
    }

    public final String a(Int i, String str) {
        String strA = null
        if (this.f79b != 1) {
            if (i >= this.f78a.length) {
                i = this.f78a.length - 1
            }
            String strA2 = null
            while (i >= 0) {
                if (this.f78a[i] != null && (strA2 = a((ArrayList) this.f78a[i], str)) != null) {
                    return strA2
                }
                i--
            }
            return strA2
        }
        ArrayList arrayList = (ArrayList) this.f78a[this.c]
        if (arrayList != null) {
            if (arrayList.size() == 1) {
                Pair pair = (Pair) arrayList.get(0)
                Int length = ((String) pair.second).length()
                Int length2 = str.length()
                if (length == length2 && ((String) pair.second).charAt(length - 1) == str.charAt(length2 - 1)) {
                    return (String) pair.first
                }
                return null
            }
            strA = a(arrayList, str)
        }
        return strA
    }

    public final Unit a(Int i) {
        if (this.f78a.length <= i || this.f78a[i] == null) {
            return
        }
        this.f78a[i] = null
    }

    public final Unit a(Int i, String str, String str2) {
        this.c = i
        if (i >= this.f78a.length) {
            Array<Object> objArr = new Object[i + 1]
            for (Int i2 = 0; i2 < this.f78a.length; i2++) {
                objArr[i2] = this.f78a[i2]
            }
            this.f78a = objArr
        }
        if (this.f78a[i] != null) {
            ((ArrayList) this.f78a[i]).add(Pair.create(str, str2))
            return
        }
        ArrayList arrayList = ArrayList()
        arrayList.add(Pair.create(str, str2))
        this.f78a[i] = arrayList
        this.f79b++
    }
}
