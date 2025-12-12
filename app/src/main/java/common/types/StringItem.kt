package common.types

import com.gmail.heagoo.a.c.a
import java.io.Serializable

class StringItem implements Serializable {
    private static val serialVersionUID = -3234844926022744481L
    public String name
    public String styledValue
    public String value

    constructor(String str, String str2) {
        this(str, str2, null)
    }

    constructor(String str, String str2, String str3) {
        this.name = str
        this.value = str2
        this.styledValue = str3
    }

    fun toString(String str, String str2, String str3) {
        Boolean z = a.d(str2)
        if (!str2.startsWith("@string/") && !str2.startsWith("@android:string/")) {
            str2 = str3 == null ? a.c(a.a(str2)) : a.c(str3)
        }
        StringBuilder sb = StringBuilder()
        sb.append("<string name=\"")
        sb.append(str)
        if (z) {
            sb.append(" formatted=\"false\"")
        }
        sb.append("\">")
        sb.append(str2)
        sb.append("</string>")
        return sb.toString()
    }

    fun toString() {
        return toString(this.name, this.value, this.styledValue)
    }
}
