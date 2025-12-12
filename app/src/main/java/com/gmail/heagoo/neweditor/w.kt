package com.gmail.heagoo.neweditor

import java.util.Arrays
import java.util.HashSet
import java.util.Iterator
import java.util.regex.Pattern

class w {

    /* renamed from: a, reason: collision with root package name */
    public final Int f1533a

    /* renamed from: b, reason: collision with root package name */
    public x f1534b
    public final Array<Char> c
    public final Int d
    public final w e
    public final Byte f
    public final Array<Char> g
    public final Int h
    public final Pattern i
    public final Byte j
    public final Array<Char> k
    public final Array<Char> l

    private constructor(Int i, String str, Int i2, Array<Char> cArr, Pattern pattern, Int i3, Array<Char> cArr2, Pattern pattern2, x xVar, Byte b2, Byte b3, String str2) {
        w wVarA = null
        this.f1533a = i
        this.k = str == null ? null : str.toUpperCase().toCharArray()
        this.l = null
        this.h = i2
        this.g = cArr
        this.i = pattern
        this.d = i3
        this.c = cArr2
        this.f1534b = xVar
        this.j = b2
        this.f = b3
        if (str2 != null && str2.length() > 0) {
            wVarA = a(str2)
        }
        this.e = wVarA
        if (this.f1534b != null || (i & 255) == 0) {
            return
        }
        this.f1534b = x.a(b2)
    }

    private constructor(Array<Char> cArr, Int i, Int i2, Array<Char> cArr2, Pattern pattern, Int i3, Array<Char> cArr3, Pattern pattern2, x xVar, Byte b2, Byte b3, String str) {
        this.f1533a = i
        this.k = null
        HashSet hashSet = HashSet()
        for (Char c : cArr) {
            hashSet.add(Character.valueOf(Character.toUpperCase(c)))
        }
        this.l = new Char[hashSet.size()]
        Iterator it = hashSet.iterator()
        Int i4 = 0
        while (it.hasNext()) {
            this.l[i4] = ((Character) it.next()).charValue()
            i4++
        }
        Arrays.sort(this.l)
        this.h = i2
        this.g = null
        this.i = pattern
        this.d = i3
        this.c = cArr3
        this.f1534b = xVar
        this.j = b2
        this.f = b3
        this.e = (str == null || str.length() <= 0) ? null : a(str)
        if (this.f1534b != null || (i & 255) == 0) {
            return
        }
        this.f1534b = x.a(b2)
    }

    fun a(Int i, String str, Byte b2, Byte b3) {
        return w(8, str.substring(0, 1), i, str.toCharArray(), (Pattern) null, 0, (Array<Char>) null, (Pattern) null, (x) null, b2, b3, (String) null)
    }

    fun a(Int i, String str, Int i2, String str2, x xVar, Byte b2, Byte b3, Boolean z, Boolean z2, String str3) {
        return w((z2 ? 1024 : 0) | (z ? 512 : 0) | 2, str.substring(0, 1), i, str.toCharArray(), (Pattern) null, i2, str2.toCharArray(), (Pattern) null, xVar, b2, b3, str3)
    }

    fun a(Int i, String str, x xVar, Byte b2) {
        return w(0, str.substring(0, 1), i, str.toCharArray(), (Pattern) null, 0, (Array<Char>) null, (Pattern) null, xVar, b2, (Byte) -1, (String) null)
    }

    fun a(Int i, String str, x xVar, Byte b2, Byte b3) {
        return w(528, str.substring(0, 1), i, str.toCharArray(), (Pattern) null, 0, (Array<Char>) null, (Pattern) null, xVar, b2, b3, (String) null)
    }

    fun a(Int i, Array<Char> cArr, String str, Int i2, String str2, x xVar, Byte b2, Byte b3, Boolean z, Boolean z2, Boolean z3, String str3, Boolean z4) {
        Pattern patternCompile
        Array<Char> charArray
        Int i3 = (z ? 512 : 0) | 8194 | (z2 ? 1024 : 0)
        if (z4) {
            i3 |= 16384
            patternCompile = Pattern.compile(str2, z3 ? 2 : 0)
            charArray = null
        } else {
            patternCompile = null
            charArray = str2.toCharArray()
        }
        return w(cArr, i3, i, (Array<Char>) null, Pattern.compile(str, z3 ? 2 : 0), i2, charArray, patternCompile, xVar, b2, b3, str3)
    }

    fun a(Int i, Array<Char> cArr, String str, x xVar, Byte b2, Byte b3, Boolean z) {
        return w(cArr, 8720, i, (Array<Char>) null, Pattern.compile(str, z ? 2 : 0), 0, (Array<Char>) null, (Pattern) null, xVar, b2, b3, (String) null)
    }

    fun a(Int i, Array<Char> cArr, String str, x xVar, Byte b2, Boolean z) {
        return w(cArr, 8192, i, (Array<Char>) null, Pattern.compile(str, z ? 2 : 0), 0, (Array<Char>) null, (Pattern) null, xVar, b2, (Byte) -1, (String) null)
    }

    fun a(String str) {
        return w(2048, str.substring(0, 1), 0, str.toCharArray(), (Pattern) null, 0, (Array<Char>) null, (Pattern) null, (x) null, (Byte) 0, (Byte) -1, (String) null)
    }

    fun a(String str, Int i, String str2, Int i2, String str3, x xVar, Byte b2, Byte b3, Boolean z, Boolean z2, Boolean z3, String str4, Boolean z4) {
        Pattern patternCompile
        Array<Char> charArray
        Int i3 = (z ? 512 : 0) | 8194 | (z2 ? 1024 : 0)
        if (z4) {
            i3 |= 16384
            patternCompile = Pattern.compile(str3, z3 ? 2 : 0)
            charArray = null
        } else {
            patternCompile = null
            charArray = str3.toCharArray()
        }
        return w(i3, str, i, (Array<Char>) null, Pattern.compile(str2, z3 ? 2 : 0), i2, charArray, patternCompile, xVar, b2, b3, str4)
    }

    fun a(String str, Int i, String str2, x xVar, Byte b2, Byte b3, Boolean z) {
        return w(8720, str, i, (Array<Char>) null, Pattern.compile(str2, z ? 2 : 0), 0, (Array<Char>) null, (Pattern) null, xVar, b2, b3, (String) null)
    }

    fun a(String str, Int i, String str2, x xVar, Byte b2, Boolean z) {
        return w(8192, str, i, (Array<Char>) null, Pattern.compile(str2, z ? 2 : 0), 0, (Array<Char>) null, (Pattern) null, xVar, b2, (Byte) -1, (String) null)
    }

    fun b(Int i, String str, Byte b2, Byte b3) {
        return w(4, str.substring(0, 1), i, str.toCharArray(), (Pattern) null, 0, (Array<Char>) null, (Pattern) null, (x) null, b2, b3, (String) null)
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append(getClass().getName()).append("[action=")
        switch (this.f1533a & 255) {
            case 0:
                sb.append("SEQ")
                break
            case 2:
                sb.append("SPAN")
                break
            case 4:
                sb.append("MARK_PREVIOUS")
                break
            case 8:
                sb.append("MARK_FOLLOWING")
                break
            case 16:
                sb.append("EOL_SPAN")
                break
            default:
                sb.append("UNKNOWN")
                break
        }
        Int i = 65280 & this.f1533a
        sb.append("[matchType=").append(this.f == -1 ? "MATCH_TYPE_CONTEXT" : this.f == -2 ? "MATCH_TYPE_RULE" : Token.tokenToString(this.f))
        sb.append(",NO_LINE_BREAK=").append((i & 512) != 0)
        sb.append(",NO_WORD_BREAK=").append((i & 1024) != 0)
        sb.append(",IS_ESCAPE=").append((i & 2048) != 0)
        sb.append(",REGEXP=").append((i & 8192) != 0)
        sb.append("],upHashChar=").append(String(this.k))
        sb.append(",upHashChars=").append(Arrays.toString(this.l))
        sb.append(",startPosMatch=")
        sb.append("[AT_LINE_START=").append((this.h & 2) != 0)
        sb.append(",AT_WHITESPACE_END=").append((this.h & 4) != 0)
        sb.append(",AT_WORD_START=").append((this.h & 8) != 0)
        sb.append("],start=").append(this.g == null ? null : String.valueOf(this.g))
        sb.append(",startRegexp=").append(this.i)
        sb.append(",endPosMatch=")
        sb.append("[AT_LINE_START=").append((this.d & 2) != 0)
        sb.append(",AT_WHITESPACE_END=").append((this.d & 4) != 0)
        sb.append(",AT_WORD_START=").append((this.d & 8) != 0)
        sb.append("],end=").append(this.c != null ? String.valueOf(this.c) : null)
        sb.append(",delegate=").append(this.f1534b)
        sb.append(",escapeRule=").append(this.e)
        sb.append(",token=").append(Token.tokenToString(this.j)).append(']')
        return sb.toString()
    }
}
