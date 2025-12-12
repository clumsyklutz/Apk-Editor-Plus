package com.a.b.a.b

import androidx.appcompat.R
import java.util.ArrayList

final class ag extends al {

    /* renamed from: a, reason: collision with root package name */
    private static final com.a.b.f.c.z f187a = new com.a.b.f.c.z(com.a.b.f.d.c.c("java/lang/reflect/Array"))

    /* renamed from: b, reason: collision with root package name */
    private static final com.a.b.f.c.v f188b = new com.a.b.f.c.v(f187a, new com.a.b.f.c.w(new com.a.b.f.c.y("newInstance"), new com.a.b.f.c.y("(Ljava/lang/Class;[I)Ljava/lang/Object;")))
    private final x c
    private final l d
    private final com.a.b.a.e.h e
    private final com.a.b.f.b.ad f
    private final Int g
    private final ArrayList h
    private com.a.b.f.d.e i
    private Boolean j
    private Boolean k
    private Int l
    private Int m
    private Boolean n
    private Boolean o
    private w p
    private com.a.b.f.b.w q
    private com.a.b.f.b.z r

    constructor(x xVar, l lVar, com.a.b.f.b.ad adVar, com.a.b.a.e.h hVar) {
        super(lVar.g())
        if (hVar == null) {
            throw NullPointerException("methods == null")
        }
        if (xVar == null) {
            throw NullPointerException("ropper == null")
        }
        if (adVar == null) {
            throw NullPointerException("advice == null")
        }
        this.c = xVar
        this.d = lVar
        this.e = hVar
        this.f = adVar
        this.g = lVar.j()
        this.h = ArrayList(25)
        this.i = null
        this.j = false
        this.k = false
        this.l = -1
        this.m = 0
        this.o = false
        this.q = null
        this.r = null
    }

    private fun a(Int i, com.a.b.f.c.a aVar) {
        switch (i) {
            case 0:
                return 1
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 47:
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
            case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
            case 50:
            case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
            case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
            case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
            case R.styleable.AppCompatTheme_borderlessButtonStyle /* 56 */:
            case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
            case R.styleable.AppCompatTheme_dividerHorizontal /* 58 */:
            case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
            case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
            case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
            case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
            case 63:
            case 64:
            case R.styleable.AppCompatTheme_editTextBackground /* 65 */:
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
            case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
            case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
            case R.styleable.AppCompatTheme_textColorSearchUrl /* 69 */:
            case R.styleable.AppCompatTheme_searchViewStyle /* 70 */:
            case R.styleable.AppCompatTheme_listPreferredItemHeight /* 71 */:
            case R.styleable.AppCompatTheme_listPreferredItemHeightSmall /* 72 */:
            case R.styleable.AppCompatTheme_listPreferredItemHeightLarge /* 73 */:
            case R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /* 74 */:
            case R.styleable.AppCompatTheme_listPreferredItemPaddingRight /* 75 */:
            case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
            case R.styleable.AppCompatTheme_listPopupWindowStyle /* 77 */:
            case R.styleable.AppCompatTheme_textAppearanceListItem /* 78 */:
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
            case 81:
            case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
            case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
            case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
            case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
            case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
            case R.styleable.AppCompatTheme_colorAccent /* 87 */:
            case R.styleable.AppCompatTheme_colorControlNormal /* 88 */:
            case R.styleable.AppCompatTheme_colorControlActivated /* 89 */:
            case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
            case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
            case R.styleable.AppCompatTheme_controlBackground /* 93 */:
            case R.styleable.AppCompatTheme_colorBackgroundFloating /* 94 */:
            case R.styleable.AppCompatTheme_alertDialogStyle /* 95 */:
            case R.styleable.AppCompatTheme_alertDialogCenterButtons /* 97 */:
            case R.styleable.AppCompatTheme_alertDialogTheme /* 98 */:
            case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 99 */:
            case R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle /* 101 */:
            case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /* 102 */:
            case R.styleable.AppCompatTheme_autoCompleteTextViewStyle /* 103 */:
            case R.styleable.AppCompatTheme_buttonStyleSmall /* 105 */:
            case R.styleable.AppCompatTheme_checkboxStyle /* 106 */:
            case R.styleable.AppCompatTheme_checkedTextViewStyle /* 107 */:
            case 109:
            case R.styleable.AppCompatTheme_ratingBarStyle /* 110 */:
            case R.styleable.AppCompatTheme_ratingBarStyleIndicator /* 111 */:
            case R.styleable.AppCompatTheme_seekBarStyle /* 113 */:
            case R.styleable.AppCompatTheme_spinnerStyle /* 114 */:
            case R.styleable.AppCompatTheme_switchStyle /* 115 */:
            case R.styleable.AppCompatTheme_tooltipFrameBackground /* 117 */:
            case R.styleable.AppCompatTheme_tooltipForegroundColor /* 118 */:
            case R.styleable.AppCompatTheme_colorError /* 119 */:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 168:
            case 169:
            case 170:
            case 173:
            case 174:
            case 175:
            case 176:
            case 186:
            case 196:
            case 197:
            default:
                throw RuntimeException("shouldn't happen")
            case 18:
            case 20:
                return 5
            case 21:
            case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
                return 2
            case 46:
                return 38
            case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                return 39
            case R.styleable.AppCompatTheme_alertDialogButtonGroupStyle /* 96 */:
            case 132:
                return 14
            case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /* 100 */:
                return 15
            case R.styleable.AppCompatTheme_buttonStyle /* 104 */:
                return 16
            case 108:
                return 17
            case R.styleable.AppCompatTheme_ratingBarStyleSmall /* 112 */:
                return 18
            case R.styleable.AppCompatTheme_listMenuViewStyle /* 116 */:
                return 19
            case 120:
                return 23
            case 122:
                return 24
            case 124:
                return 25
            case 126:
                return 20
            case 128:
                return 21
            case 130:
                return 22
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
                return 29
            case 145:
                return 30
            case 146:
                return 31
            case 147:
                return 32
            case 148:
            case 149:
            case 151:
                return 27
            case 150:
            case 152:
                return 28
            case 153:
            case 159:
            case 165:
            case 198:
                return 7
            case 154:
            case 160:
            case 166:
            case 199:
                return 8
            case 155:
            case 161:
                return 9
            case 156:
            case 162:
                return 10
            case 157:
            case 163:
                return 12
            case 158:
            case 164:
                return 11
            case 167:
                return 6
            case 171:
                return 13
            case 172:
            case 177:
                return 33
            case 178:
                return 46
            case 179:
                return 48
            case 180:
                return 45
            case 181:
                return 47
            case 182:
                com.a.b.f.c.v vVar = (com.a.b.f.c.v) aVar
                if (vVar.k().equals(this.d.f())) {
                    for (Int i2 = 0; i2 < this.e.d_(); i2++) {
                        com.a.b.a.e.g gVarA = this.e.a(i2)
                        if (com.gmail.heagoo.a.c.a.k(gVarA.d()) && vVar.l().equals(gVarA.a())) {
                            return 52
                        }
                    }
                }
                return 50
            case 183:
                com.a.b.f.c.v vVar2 = (com.a.b.f.c.v) aVar
                return (vVar2.l().e() || vVar2.k().equals(this.d.f()) || !this.d.h()) ? 52 : 51
            case 184:
                return 49
            case 185:
                return 53
            case 187:
                return 40
            case 188:
            case 189:
                return 41
            case 190:
                return 34
            case 191:
                return 35
            case 192:
                return 43
            case 193:
                return 44
            case 194:
                return 36
            case 195:
                return 37
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0446  */
    @Override // com.a.b.a.b.al, com.a.b.a.b.u
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit a(com.a.b.a.b.n r15, Int r16, Int r17) {
        /*
            Method dump skipped, instructions count: 1124
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.a.b.ag.a(com.a.b.a.b.n, Int, Int):Unit")
    }

    public final Unit a(com.a.b.f.d.e eVar) {
        this.i = eVar
        this.h.clear()
        this.j = false
        this.k = false
        this.l = 0
        this.m = 0
        this.o = false
        this.n = false
        this.p = null
    }

    public final ArrayList m() {
        return this.h
    }

    public final com.a.b.f.b.w n() {
        return this.q
    }

    public final com.a.b.f.b.z o() {
        return this.r
    }

    public final Boolean p() {
        return this.j
    }

    public final Boolean q() {
        return this.k
    }

    public final Int r() {
        return this.l
    }

    public final Int s() {
        return this.m
    }

    public final Boolean t() {
        return this.o
    }

    public final Boolean u() {
        return this.n
    }

    public final Boolean v() {
        return this.p != null
    }

    public final w w() {
        return this.p
    }
}
