package com.a.b.a.b

import androidx.appcompat.R
import java.util.ArrayList

final class aj implements k {

    /* renamed from: a, reason: collision with root package name */
    private final u f191a

    /* renamed from: b, reason: collision with root package name */
    private n f192b = null
    private Int c
    private /* synthetic */ ai d

    constructor(ai aiVar) {
        this.d = aiVar
        this.f191a = aiVar.f189a
    }

    private fun a(com.a.b.f.d.c cVar) {
        com.a.b.f.d.c cVarA = this.f191a.a().a()
        if (!com.gmail.heagoo.a.c.a.b(cVarA, cVar)) {
            throw ah("return type mismatch: prototype indicates " + cVarA.d() + ", but encountered type " + cVar.d())
        }
    }

    @Override // com.a.b.a.b.k
    public final Int a() {
        return this.c
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i) {
        this.c = i
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3) {
        throw ah("invalid opcode " + com.gmail.heagoo.a.c.a.x(i))
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, Int i4) {
        switch (i) {
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
                this.f191a.a(this.f192b, com.a.b.f.d.c.f)
                break
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
                this.f191a.a(this.f192b, com.a.b.f.d.c.f, com.a.b.f.d.c.f)
                break
            case 165:
            case 166:
                this.f191a.a(this.f192b, com.a.b.f.d.c.n, com.a.b.f.d.c.n)
                break
            case 167:
            case 168:
            case 200:
            case 201:
                this.f191a.b()
                break
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
            default:
                a(i, i2, i3)
                return
            case 198:
            case 199:
                this.f191a.a(this.f192b, com.a.b.f.d.c.n)
                break
        }
        this.f191a.b(i4)
        this.f191a.a(this.f192b, i2, i)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, Int i4, com.a.b.f.d.c cVar, Int i5) {
        com.a.b.f.d.c cVar2
        r rVarA = this.d.c.a(i == 54 ? i2 + i3 : i2, i4)
        if (rVarA != null) {
            com.a.b.f.d.c cVarB = rVarA.b()
            if (cVarB.e() != cVar.e()) {
                a.a(cVar, cVarB)
                return
            }
            cVar2 = cVarB
        } else {
            cVar2 = cVar
        }
        switch (i) {
            case 21:
            case 169:
                this.f191a.b(this.f192b, i4)
                this.f191a.a(rVarA != null)
                this.f191a.a(cVar)
                break
            case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
                com.a.b.f.b.m mVarA = rVarA == null ? null : rVarA.a()
                this.f191a.a(this.f192b, cVar)
                this.f191a.a(cVar)
                this.f191a.a(i4, cVar2, mVarA)
                break
            case 132:
                com.a.b.f.b.m mVarA2 = rVarA != null ? rVarA.a() : null
                this.f191a.b(this.f192b, i4)
                this.f191a.a(i4, cVar2, mVarA2)
                this.f191a.a(cVar)
                this.f191a.a(i5)
                this.f191a.a(com.a.b.f.c.n.a(i5))
                break
            default:
                a(i, i2, i3)
                return
        }
        this.f191a.a(this.f192b, i2, i)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, ak akVar, Int i4) {
        this.f191a.a(this.f192b, com.a.b.f.d.c.f)
        this.f191a.a(i4)
        this.f191a.a(akVar)
        this.f191a.a(this.f192b, i2, 171)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.c.a aVar, Int i4) {
        com.a.b.f.c.a aVarJ
        switch (i) {
            case 179:
                this.f191a.a(this.f192b, ((com.a.b.f.c.l) aVar).a())
                break
            case 180:
            case 192:
            case 193:
                this.f191a.a(this.f192b, com.a.b.f.d.c.n)
                break
            case 181:
                this.f191a.a(this.f192b, com.a.b.f.d.c.n, ((com.a.b.f.c.l) aVar).a())
                break
            case 182:
            case 183:
                aVarJ = aVar
                this.f191a.a(this.f192b, ((com.a.b.f.c.v) aVarJ).a(false))
                aVar = aVarJ
                break
            case 184:
                this.f191a.a(this.f192b, ((com.a.b.f.c.v) aVar).a(true))
                break
            case 185:
                aVarJ = ((com.a.b.f.c.o) aVar).j()
                this.f191a.a(this.f192b, ((com.a.b.f.c.v) aVarJ).a(false))
                aVar = aVarJ
                break
            case 186:
            case 187:
            case 188:
            case 190:
            case 191:
            case 194:
            case 195:
            case 196:
            default:
                this.f191a.b()
                break
            case 189:
                this.f191a.a(this.f192b, com.a.b.f.d.c.f)
                break
            case 197:
                this.f191a.a(this.f192b, com.a.b.f.d.a.a(com.a.b.f.d.c.i, i4))
                break
        }
        this.f191a.a(i4)
        this.f191a.a(aVar)
        this.f191a.a(this.f192b, i2, i)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.d.c cVar) {
        Int i4
        switch (i) {
            case 0:
                this.f191a.b()
                break
            case 46:
                com.a.b.f.d.c cVarA = ai.a(cVar, this.f192b.d().c(1))
                cVar = cVarA.s()
                this.f191a.a(this.f192b, cVarA, com.a.b.f.d.c.f)
                break
            case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                m mVarD = this.f192b.d()
                Int i5 = cVar.j() ? 2 : 3
                com.a.b.f.d.c cVarC = mVarD.c(i5)
                Boolean zB = mVarD.b(i5)
                com.a.b.f.d.c cVarA2 = ai.a(cVar, cVarC)
                if (zB) {
                    cVar = cVarA2.s()
                }
                this.f191a.a(this.f192b, cVarA2, com.a.b.f.d.c.f, cVar)
                break
            case R.styleable.AppCompatTheme_colorAccent /* 87 */:
                if (!this.f192b.d().c(0).k()) {
                    this.f191a.a(this.f192b, 1)
                    break
                } else {
                    throw ai.a()
                }
            case R.styleable.AppCompatTheme_colorControlNormal /* 88 */:
            case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                m mVarD2 = this.f192b.d()
                if (mVarD2.c(0).k()) {
                    this.f191a.a(this.f192b, 1)
                    i4 = 17
                } else {
                    if (!mVarD2.c(1).j()) {
                        throw ai.a()
                    }
                    this.f191a.a(this.f192b, 2)
                    i4 = 8481
                }
                if (i == 92) {
                    this.f191a.a(i4)
                    break
                }
                break
            case R.styleable.AppCompatTheme_colorControlActivated /* 89 */:
                if (!this.f192b.d().c(0).k()) {
                    this.f191a.a(this.f192b, 1)
                    this.f191a.a(17)
                    break
                } else {
                    throw ai.a()
                }
            case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
                m mVarD3 = this.f192b.d()
                if (!mVarD3.c(0).j() || !mVarD3.c(1).j()) {
                    throw ai.a()
                }
                this.f191a.a(this.f192b, 2)
                this.f191a.a(530)
                break
                break
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                m mVarD4 = this.f192b.d()
                if (mVarD4.c(0).k()) {
                    throw ai.a()
                }
                if (mVarD4.c(1).k()) {
                    this.f191a.a(this.f192b, 2)
                    this.f191a.a(530)
                    break
                } else {
                    if (!mVarD4.c(2).j()) {
                        throw ai.a()
                    }
                    this.f191a.a(this.f192b, 3)
                    this.f191a.a(12819)
                    break
                }
            case R.styleable.AppCompatTheme_controlBackground /* 93 */:
                m mVarD5 = this.f192b.d()
                if (!mVarD5.c(0).k()) {
                    if (!mVarD5.c(1).k() && !mVarD5.c(2).k()) {
                        this.f191a.a(this.f192b, 3)
                        this.f191a.a(205106)
                        break
                    } else {
                        throw ai.a()
                    }
                } else if (!mVarD5.c(2).k()) {
                    this.f191a.a(this.f192b, 2)
                    this.f191a.a(530)
                    break
                } else {
                    throw ai.a()
                }
                break
            case R.styleable.AppCompatTheme_colorBackgroundFloating /* 94 */:
                m mVarD6 = this.f192b.d()
                if (mVarD6.c(0).k()) {
                    if (mVarD6.c(2).k()) {
                        this.f191a.a(this.f192b, 2)
                        this.f191a.a(530)
                        break
                    } else {
                        if (!mVarD6.c(3).j()) {
                            throw ai.a()
                        }
                        this.f191a.a(this.f192b, 3)
                        this.f191a.a(12819)
                        break
                    }
                } else {
                    if (!mVarD6.c(1).j()) {
                        throw ai.a()
                    }
                    if (mVarD6.c(2).k()) {
                        this.f191a.a(this.f192b, 3)
                        this.f191a.a(205106)
                        break
                    } else {
                        if (!mVarD6.c(3).j()) {
                            throw ai.a()
                        }
                        this.f191a.a(this.f192b, 4)
                        this.f191a.a(4399427)
                        break
                    }
                }
            case R.styleable.AppCompatTheme_alertDialogStyle /* 95 */:
                m mVarD7 = this.f192b.d()
                if (!mVarD7.c(0).j() || !mVarD7.c(1).j()) {
                    throw ai.a()
                }
                this.f191a.a(this.f192b, 2)
                this.f191a.a(18)
                break
                break
            case R.styleable.AppCompatTheme_alertDialogButtonGroupStyle /* 96 */:
            case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /* 100 */:
            case R.styleable.AppCompatTheme_buttonStyle /* 104 */:
            case 108:
            case R.styleable.AppCompatTheme_ratingBarStyleSmall /* 112 */:
            case 126:
            case 128:
            case 130:
                this.f191a.a(this.f192b, cVar, cVar)
                break
            case R.styleable.AppCompatTheme_listMenuViewStyle /* 116 */:
                this.f191a.a(this.f192b, cVar)
                break
            case 120:
            case 122:
            case 124:
                this.f191a.a(this.f192b, cVar, com.a.b.f.d.c.f)
                break
            case 133:
            case 134:
            case 135:
            case 145:
            case 146:
            case 147:
                this.f191a.a(this.f192b, com.a.b.f.d.c.f)
                break
            case 136:
            case 137:
            case 138:
                this.f191a.a(this.f192b, com.a.b.f.d.c.g)
                break
            case 139:
            case 140:
            case 141:
                this.f191a.a(this.f192b, com.a.b.f.d.c.e)
                break
            case 142:
            case 143:
            case 144:
                this.f191a.a(this.f192b, com.a.b.f.d.c.d)
                break
            case 148:
                this.f191a.a(this.f192b, com.a.b.f.d.c.g, com.a.b.f.d.c.g)
                break
            case 149:
            case 150:
                this.f191a.a(this.f192b, com.a.b.f.d.c.e, com.a.b.f.d.c.e)
                break
            case 151:
            case 152:
                this.f191a.a(this.f192b, com.a.b.f.d.c.d, com.a.b.f.d.c.d)
                break
            case 172:
                com.a.b.f.d.c cVarC2 = cVar == com.a.b.f.d.c.n ? this.f192b.d().c(0) : cVar
                this.f191a.a(this.f192b, cVar)
                a(cVarC2)
                break
            case 177:
                this.f191a.b()
                a(com.a.b.f.d.c.i)
                break
            case 190:
                com.a.b.f.d.c cVarC3 = this.f192b.d().c(0)
                if (!(cVarC3.o() || cVarC3.equals(com.a.b.f.d.c.j))) {
                    throw ah("type mismatch: expected array type but encountered " + cVarC3.d())
                }
                this.f191a.a(this.f192b, com.a.b.f.d.c.n)
                break
            case 191:
            case 194:
            case 195:
                this.f191a.a(this.f192b, com.a.b.f.d.c.n)
                break
            default:
                a(i, i2, i3)
                return
        }
        this.f191a.a(cVar)
        this.f191a.a(this.f192b, i2, i)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, com.a.b.f.c.z zVar, ArrayList arrayList) {
        this.f191a.a(this.f192b, com.a.b.f.d.c.f)
        this.f191a.a(arrayList)
        this.f191a.a(zVar)
        this.f191a.a(this.f192b, i, 188)
    }

    public final Unit a(n nVar) {
        if (nVar == null) {
            throw NullPointerException("frame == null")
        }
        this.f192b = nVar
    }
}
