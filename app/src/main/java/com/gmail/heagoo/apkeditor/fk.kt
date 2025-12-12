package com.gmail.heagoo.apkeditor

import com.gmail.heagoo.a.c.a
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class fk {

    /* renamed from: a, reason: collision with root package name */
    List f1072a

    /* renamed from: b, reason: collision with root package name */
    String f1073b
    Boolean c

    constructor() {
        this.f1073b = ""
        this.c = false
    }

    private constructor(String str, Boolean z) {
        this.f1073b = str
        this.c = z
    }

    private fun a(String str) {
        if (this.f1072a != null) {
            for (fk fkVar : this.f1072a) {
                if (str.equals(fkVar.f1073b)) {
                    return fkVar
                }
            }
        }
        return null
    }

    private fun a(String str, Boolean z) {
        if (this.f1072a == null) {
            if (!z) {
                return null
            }
            this.f1072a = ArrayList()
        }
        for (fk fkVar : this.f1072a) {
            if (!fkVar.c && str.equals(fkVar.f1073b)) {
                return fkVar
            }
        }
        return null
    }

    private fun a() {
        if (this.f1072a == null) {
            return null
        }
        ArrayList arrayList = ArrayList()
        for (fk fkVar : this.f1072a) {
            if (fkVar.c) {
                arrayList.add(fkVar.f1073b)
            } else {
                List listA = fkVar.a()
                if (listA != null) {
                    Iterator it = listA.iterator()
                    while (it.hasNext()) {
                        arrayList.add(fkVar.f1073b + "/" + ((String) it.next()))
                    }
                }
            }
        }
        return arrayList
    }

    private fun a(fk fkVar) {
        if (this.f1072a == null) {
            this.f1072a = ArrayList()
        }
        this.f1072a.add(fkVar)
    }

    private fun b(fk fkVar) {
        if (this.f1072a != null) {
            return this.f1072a.remove(fkVar)
        }
        return false
    }

    public final fk a(Array<String> strArr) {
        for (String str : strArr) {
            this = this.a(str, false)
            if (this == null) {
                return null
            }
        }
        return this
    }

    public final Unit a(Array<String> strArr, Boolean z) {
        for (Int i = 0; i < strArr.length - 1; i++) {
            fk fkVarA = this.a(strArr[i], true)
            if (fkVarA == null) {
                fkVarA = fk(strArr[i], false)
                this.a(fkVarA)
            }
            this = fkVarA
        }
        this.a(fk(strArr[strArr.length - 1], z))
    }

    public final fk b(Array<String> strArr) {
        for (Int i = 0; i < strArr.length - 1; i++) {
            this = this.a(strArr[i], false)
            if (this == null) {
                return null
            }
        }
        return this.a(strArr[strArr.length - 1])
    }

    public final List c(Array<String> strArr) {
        fk fkVarA
        if (strArr.length > 1) {
            Array<String> strArr2 = new String[strArr.length - 1]
            for (Int i = 0; i < strArr.length - 1; i++) {
                strArr2[i] = strArr[i]
            }
            this = a(strArr2)
        }
        if (this != null && (fkVarA = this.a(strArr[strArr.length - 1])) != null) {
            if (fkVarA.c) {
                this.b(fkVarA)
                ArrayList arrayList = ArrayList()
                arrayList.add(a.a("/", strArr))
                return arrayList
            }
            List listA = fkVarA.a()
            if (listA != null) {
                String strA = a.a("/", strArr)
                for (Int i2 = 0; i2 < listA.size(); i2++) {
                    listA.set(i2, strA + "/" + ((String) listA.get(i2)))
                }
                this.b(fkVarA)
                return listA
            }
            this.b(fkVarA)
        }
        return null
    }
}
