package com.a.b.a.d

import androidx.appcompat.R
import com.a.b.f.c.t
import com.a.b.f.c.w
import com.a.b.f.c.x
import com.a.b.f.c.y
import com.a.b.f.c.z
import java.io.IOException

class a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.b f232a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.c f233b
    private final com.a.b.a.e.j c
    private final com.a.b.h.d d
    private Int e

    constructor(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (kVar == null) {
            throw NullPointerException("cf == null")
        }
        this.f232a = kVar.g()
        this.c = jVar
        this.f233b = kVar.b().a(i, i + i2)
        this.d = this.f233b.b()
        this.e = 0
    }

    private fun a(Int i) {
        if (this.d.available() < i) {
            throw new com.a.b.a.e.i("truncated annotation attribute")
        }
    }

    private fun a(Int i, String str) {
        this.e += i
    }

    private com.a.b.f.c.a b() {
        Int unsignedByte = this.d.readUnsignedByte()
        if (this.c != null) {
            a(1, "tag: " + y(Character.toString((Char) unsignedByte)).i())
        }
        switch (unsignedByte) {
            case 64:
                return new com.a.b.f.c.c(d(com.a.b.f.a.b.EMBEDDED))
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return com.a.b.f.c.h.a(((com.a.b.f.c.n) c()).j())
            case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                com.a.b.f.c.n nVar = (com.a.b.f.c.n) c()
                nVar.j()
                return com.a.b.f.c.i.a(nVar.j())
            case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                return (com.a.b.f.c.j) c()
            case R.styleable.AppCompatTheme_searchViewStyle /* 70 */:
                return (com.a.b.f.c.m) c()
            case R.styleable.AppCompatTheme_listPreferredItemHeightLarge /* 73 */:
                return (com.a.b.f.c.n) c()
            case R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /* 74 */:
                return (t) c()
            case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
                return x.a(((com.a.b.f.c.n) c()).j())
            case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
                return com.a.b.f.c.g.a(((com.a.b.f.c.n) c()).j())
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                a(2)
                Int unsignedShort = this.d.readUnsignedShort()
                com.a.b.f.c.e eVar = new com.a.b.f.c.e(unsignedShort)
                if (this.c != null) {
                    a(2, "num_values: " + unsignedShort)
                }
                for (Int i = 0; i < unsignedShort; i++) {
                    if (this.c != null) {
                        a(0, "element_value[" + i + "]:")
                    }
                    eVar.a(i, b())
                }
                eVar.b_()
                return new com.a.b.f.c.d(eVar)
            case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 99 */:
                com.a.b.f.d.c cVarB = com.a.b.f.d.c.b(((y) this.f232a.a(this.d.readUnsignedShort())).j())
                if (this.c != null) {
                    a(2, "class_info: " + cVarB.d())
                }
                return z(cVarB)
            case R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle /* 101 */:
                a(4)
                Int unsignedShort2 = this.d.readUnsignedShort()
                Int unsignedShort3 = this.d.readUnsignedShort()
                y yVar = (y) this.f232a.a(unsignedShort2)
                y yVar2 = (y) this.f232a.a(unsignedShort3)
                if (this.c != null) {
                    a(2, "type_name: " + yVar.d())
                    a(2, "const_name: " + yVar2.d())
                }
                return new com.a.b.f.c.k(w(yVar2, yVar))
            case R.styleable.AppCompatTheme_switchStyle /* 115 */:
                return c()
            default:
                throw new com.a.b.a.e.i("unknown annotation tag: " + com.gmail.heagoo.a.c.a.x(unsignedByte))
        }
    }

    private com.a.b.f.a.c c(com.a.b.f.a.b bVar) {
        Int unsignedShort = this.d.readUnsignedShort()
        if (this.c != null) {
            a(2, "num_annotations: " + com.gmail.heagoo.a.c.a.v(unsignedShort))
        }
        com.a.b.f.a.c cVar = new com.a.b.f.a.c()
        for (Int i = 0; i < unsignedShort; i++) {
            if (this.c != null) {
                a(0, "annotations[" + i + "]:")
            }
            cVar.a(d(bVar))
        }
        cVar.b_()
        return cVar
    }

    private com.a.b.f.c.a c() {
        com.a.b.f.c.a aVarA = this.f232a.a(this.d.readUnsignedShort())
        if (this.c != null) {
            a(2, "constant_value: " + (aVarA is y ? ((y) aVarA).i() : aVarA.d()))
        }
        return aVarA
    }

    private com.a.b.f.a.a d(com.a.b.f.a.b bVar) {
        a(4)
        Int unsignedShort = this.d.readUnsignedShort()
        Int unsignedShort2 = this.d.readUnsignedShort()
        z zVar = z(com.a.b.f.d.c.a(((y) this.f232a.a(unsignedShort)).j()))
        if (this.c != null) {
            a(2, "type: " + zVar.d())
            a(2, "num_elements: " + unsignedShort2)
        }
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(zVar, bVar)
        for (Int i = 0; i < unsignedShort2; i++) {
            if (this.c != null) {
                a(0, "elements[" + i + "]:")
            }
            a(5)
            y yVar = (y) this.f232a.a(this.d.readUnsignedShort())
            if (this.c != null) {
                a(2, "element_name: " + yVar.d())
                a(0, "value: ")
            }
            aVar.b(new com.a.b.f.a.e(yVar, b()))
        }
        aVar.b_()
        return aVar
    }

    public final com.a.b.f.a.d a(com.a.b.f.a.b bVar) {
        try {
            Int unsignedByte = this.d.readUnsignedByte()
            if (this.c != null) {
                a(1, "num_parameters: " + com.gmail.heagoo.a.c.a.x(unsignedByte))
            }
            com.a.b.f.a.d dVar = new com.a.b.f.a.d(unsignedByte)
            for (Int i = 0; i < unsignedByte; i++) {
                if (this.c != null) {
                    a(0, "parameter_annotations[" + i + "]:")
                }
                dVar.a(i, c(bVar))
            }
            dVar.b_()
            if (this.d.available() != 0) {
                throw new com.a.b.a.e.i("extra data in attribute")
            }
            return dVar
        } catch (IOException e) {
            throw RuntimeException("shouldn't happen", e)
        }
    }

    public final com.a.b.f.c.a a() {
        try {
            com.a.b.f.c.a aVarB = b()
            if (this.d.available() != 0) {
                throw new com.a.b.a.e.i("extra data in attribute")
            }
            return aVarB
        } catch (IOException e) {
            throw RuntimeException("shouldn't happen", e)
        }
    }

    public final com.a.b.f.a.c b(com.a.b.f.a.b bVar) {
        try {
            com.a.b.f.a.c cVarC = c(bVar)
            if (this.d.available() != 0) {
                throw new com.a.b.a.e.i("extra data in attribute")
            }
            return cVarC
        } catch (IOException e) {
            throw RuntimeException("shouldn't happen", e)
        }
    }
}
