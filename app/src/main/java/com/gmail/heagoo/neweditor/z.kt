package com.gmail.heagoo.neweditor

import java.text.CharacterIterator

class z implements CharSequence, Cloneable, CharacterIterator {

    /* renamed from: a, reason: collision with root package name */
    public Array<Char> f1537a

    /* renamed from: b, reason: collision with root package name */
    public Int f1538b
    public Int c
    private Int d

    constructor() {
        this(null, 0, 0)
    }

    private constructor(Array<Char> cArr, Int i, Int i2) {
        this.f1537a = null
        this.c = 0
        this.f1538b = 0
    }

    @Override // java.lang.CharSequence
    public final Char charAt(Int i) {
        if (i < 0 || i >= this.f1538b) {
            throw StringIndexOutOfBoundsException(i)
        }
        return this.f1537a[this.c + i]
    }

    @Override // java.text.CharacterIterator
    public final Object clone() {
        try {
            return super.clone()
        } catch (CloneNotSupportedException e) {
            return null
        }
    }

    @Override // java.text.CharacterIterator
    public final Char current() {
        if (this.f1538b == 0 || this.d >= this.c + this.f1538b) {
            return (Char) 65535
        }
        return this.f1537a[this.d]
    }

    @Override // java.text.CharacterIterator
    public final Char first() {
        this.d = this.c
        if (this.f1538b != 0) {
            return this.f1537a[this.d]
        }
        return (Char) 65535
    }

    @Override // java.text.CharacterIterator
    public final Int getBeginIndex() {
        return this.c
    }

    @Override // java.text.CharacterIterator
    public final Int getEndIndex() {
        return this.c + this.f1538b
    }

    @Override // java.text.CharacterIterator
    public final Int getIndex() {
        return this.d
    }

    @Override // java.text.CharacterIterator
    public final Char last() {
        this.d = this.c + this.f1538b
        if (this.f1538b == 0) {
            return (Char) 65535
        }
        this.d--
        return this.f1537a[this.d]
    }

    @Override // java.lang.CharSequence
    public final Int length() {
        return this.f1538b
    }

    @Override // java.text.CharacterIterator
    public final Char next() {
        this.d++
        Int i = this.c + this.f1538b
        if (this.d < i) {
            return current()
        }
        this.d = i
        return (Char) 65535
    }

    @Override // java.text.CharacterIterator
    public final Char previous() {
        if (this.d == this.c) {
            return (Char) 65535
        }
        this.d--
        return current()
    }

    @Override // java.text.CharacterIterator
    public final Char setIndex(Int i) {
        Int i2 = this.c + this.f1538b
        if (i < this.c || i > i2) {
            throw IllegalArgumentException("bad position: " + i)
        }
        this.d = i
        if (this.d == i2 || this.f1538b == 0) {
            return (Char) 65535
        }
        return this.f1537a[this.d]
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(Int i, Int i2) {
        if (i < 0) {
            throw StringIndexOutOfBoundsException(i)
        }
        if (i2 > this.f1538b) {
            throw StringIndexOutOfBoundsException(i2)
        }
        if (i > i2) {
            throw StringIndexOutOfBoundsException(i2 - i)
        }
        z zVar = z()
        zVar.f1537a = this.f1537a
        zVar.c = this.c + i
        zVar.f1538b = i2 - i
        return zVar
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return this.f1537a != null ? String(this.f1537a, this.c, this.f1538b) : String()
    }
}
