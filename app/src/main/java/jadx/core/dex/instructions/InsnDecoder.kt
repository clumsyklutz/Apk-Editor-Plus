package jadx.core.dex.instructions

import androidx.appcompat.R
import com.a.b.d.a.at
import com.a.b.d.a.av
import com.a.b.d.a.ax
import com.a.b.d.a.f
import com.a.b.d.a.g
import com.a.b.d.e
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.InsnUtils
import jadx.core.utils.exceptions.DecodeException

class InsnDecoder {
    private final DexNode dex
    private Array<f> insnArr
    private final MethodNode method

    constructor(MethodNode methodNode) {
        this.method = methodNode
        this.dex = this.method.dex()
    }

    private fun arith(f fVar, ArithOp arithOp, ArgType argType) {
        return ArithNode(fVar, arithOp, argType, false)
    }

    private fun arithLit(f fVar, ArithOp arithOp, ArgType argType) {
        return ArithNode(fVar, arithOp, argType, true)
    }

    private fun arrayGet(f fVar, ArgType argType) {
        InsnNode insnNode = InsnNode(InsnType.AGET, 2)
        insnNode.setResult(InsnArg.reg(fVar, 0, argType))
        insnNode.addArg(InsnArg.reg(fVar, 1, ArgType.unknown(PrimitiveType.ARRAY)))
        insnNode.addArg(InsnArg.reg(fVar, 2, ArgType.INT))
        return insnNode
    }

    private fun arrayPut(f fVar, ArgType argType) {
        InsnNode insnNode = InsnNode(InsnType.APUT, 3)
        insnNode.addArg(InsnArg.reg(fVar, 1, ArgType.unknown(PrimitiveType.ARRAY)))
        insnNode.addArg(InsnArg.reg(fVar, 2, ArgType.INT))
        insnNode.addArg(InsnArg.reg(fVar, 0, argType))
        return insnNode
    }

    private fun cast(f fVar, ArgType argType, ArgType argType2) {
        IndexInsnNode indexInsnNode = IndexInsnNode(InsnType.CAST, argType2, 1)
        indexInsnNode.setResult(InsnArg.reg(fVar, 0, argType2))
        indexInsnNode.addArg(InsnArg.reg(fVar, 1, argType))
        return indexInsnNode
    }

    private fun cmp(f fVar, InsnType insnType, ArgType argType) {
        InsnNode insnNode = InsnNode(insnType, 2)
        insnNode.setResult(InsnArg.reg(fVar, 0, ArgType.INT))
        insnNode.addArg(InsnArg.reg(fVar, 1, argType))
        insnNode.addArg(InsnArg.reg(fVar, 2, argType))
        return insnNode
    }

    private fun decode(f fVar, Int i) throws DecodeException {
        switch (fVar.b()) {
            case 0:
            case 256:
            case 512:
            case 768:
                return null
            case 1:
            case 2:
            case 3:
                return insn(InsnType.MOVE, InsnArg.reg(fVar, 0, ArgType.NARROW), InsnArg.reg(fVar, 1, ArgType.NARROW))
            case 4:
            case 5:
            case 6:
                return insn(InsnType.MOVE, InsnArg.reg(fVar, 0, ArgType.WIDE), InsnArg.reg(fVar, 1, ArgType.WIDE))
            case 7:
            case 8:
            case 9:
                return insn(InsnType.MOVE, InsnArg.reg(fVar, 0, ArgType.UNKNOWN_OBJECT), InsnArg.reg(fVar, 1, ArgType.UNKNOWN_OBJECT))
            case 10:
            case 11:
            case 12:
                return InsnNode(InsnType.NOP, 0)
            case 13:
                return insn(InsnType.MOVE_EXCEPTION, InsnArg.reg(fVar, 0, ArgType.unknown(PrimitiveType.OBJECT)))
            case 14:
                return InsnNode(InsnType.RETURN, 0)
            case 15:
            case 16:
            case 17:
                return insn(InsnType.RETURN, null, InsnArg.reg(fVar, 0, this.method.getReturnType()))
            case 18:
            case 19:
            case 20:
            case 21:
                return insn(InsnType.CONST, InsnArg.reg(fVar, 0, ArgType.NARROW), InsnArg.lit(fVar, ArgType.NARROW))
            case 22:
            case 23:
            case 24:
            case 25:
                return insn(InsnType.CONST, InsnArg.reg(fVar, 0, ArgType.WIDE), InsnArg.lit(fVar, ArgType.WIDE))
            case 26:
            case 27:
                ConstStringNode constStringNode = ConstStringNode(this.dex.getString(fVar.d()))
                constStringNode.setResult(InsnArg.reg(fVar, 0, ArgType.STRING))
                return constStringNode
            case 28:
                ConstClassNode constClassNode = ConstClassNode(this.dex.getType(fVar.d()))
                constClassNode.setResult(InsnArg.reg(fVar, 0, ArgType.CLASS))
                return constClassNode
            case 29:
                return insn(InsnType.MONITOR_ENTER, null, InsnArg.reg(fVar, 0, ArgType.UNKNOWN_OBJECT))
            case 30:
                return insn(InsnType.MONITOR_EXIT, null, InsnArg.reg(fVar, 0, ArgType.UNKNOWN_OBJECT))
            case 31:
                ArgType type = this.dex.getType(fVar.d())
                IndexInsnNode indexInsnNode = IndexInsnNode(InsnType.CHECK_CAST, type, 1)
                indexInsnNode.setResult(InsnArg.reg(fVar, 0, type))
                indexInsnNode.addArg(InsnArg.reg(fVar, 0, ArgType.UNKNOWN_OBJECT))
                return indexInsnNode
            case 32:
                IndexInsnNode indexInsnNode2 = IndexInsnNode(InsnType.INSTANCE_OF, this.dex.getType(fVar.d()), 1)
                indexInsnNode2.setResult(InsnArg.reg(fVar, 0, ArgType.BOOLEAN))
                indexInsnNode2.addArg(InsnArg.reg(fVar, 1, ArgType.UNKNOWN_OBJECT))
                return indexInsnNode2
            case 33:
                InsnNode insnNode = InsnNode(InsnType.ARRAY_LENGTH, 1)
                insnNode.setResult(InsnArg.reg(fVar, 0, ArgType.INT))
                insnNode.addArg(InsnArg.reg(fVar, 1, ArgType.array(ArgType.UNKNOWN)))
                return insnNode
            case 34:
                return insn(InsnType.NEW_INSTANCE, InsnArg.reg(fVar, 0, this.dex.getType(fVar.d())))
            case 35:
                ArgType type2 = this.dex.getType(fVar.d())
                return NewArrayNode(type2, InsnArg.reg(fVar, 0, type2), InsnArg.reg(fVar, 1, ArgType.INT))
            case 36:
                return filledNewArray(fVar, i, false)
            case 37:
                return filledNewArray(fVar, i, true)
            case 38:
                return fillArray(fVar)
            case 39:
                return insn(InsnType.THROW, null, InsnArg.reg(fVar, 0, ArgType.unknown(PrimitiveType.OBJECT)))
            case 40:
            case 41:
            case 42:
                return GotoNode(fVar.g())
            case 43:
                return decodeSwitch(fVar, i, true)
            case 44:
                return decodeSwitch(fVar, i, false)
            case 45:
                return cmp(fVar, InsnType.CMP_L, ArgType.FLOAT)
            case 46:
                return cmp(fVar, InsnType.CMP_G, ArgType.FLOAT)
            case 47:
                return cmp(fVar, InsnType.CMP_L, ArgType.DOUBLE)
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                return cmp(fVar, InsnType.CMP_G, ArgType.DOUBLE)
            case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
                return cmp(fVar, InsnType.CMP_L, ArgType.LONG)
            case 50:
            case R.styleable.AppCompatTheme_borderlessButtonStyle /* 56 */:
                return IfNode(fVar, IfOp.EQ)
            case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
            case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
                return IfNode(fVar, IfOp.NE)
            case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
            case R.styleable.AppCompatTheme_dividerHorizontal /* 58 */:
                return IfNode(fVar, IfOp.LT)
            case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
            case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
                return IfNode(fVar, IfOp.GE)
            case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
            case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                return IfNode(fVar, IfOp.GT)
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
            case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
                return IfNode(fVar, IfOp.LE)
            case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                return arrayGet(fVar, ArgType.NARROW)
            case R.styleable.AppCompatTheme_textColorSearchUrl /* 69 */:
                return arrayGet(fVar, ArgType.WIDE)
            case R.styleable.AppCompatTheme_searchViewStyle /* 70 */:
                return arrayGet(fVar, ArgType.UNKNOWN_OBJECT)
            case R.styleable.AppCompatTheme_listPreferredItemHeight /* 71 */:
                return arrayGet(fVar, ArgType.BOOLEAN)
            case R.styleable.AppCompatTheme_listPreferredItemHeightSmall /* 72 */:
                return arrayGet(fVar, ArgType.BYTE)
            case R.styleable.AppCompatTheme_listPreferredItemHeightLarge /* 73 */:
                return arrayGet(fVar, ArgType.CHAR)
            case R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /* 74 */:
                return arrayGet(fVar, ArgType.SHORT)
            case R.styleable.AppCompatTheme_listPreferredItemPaddingRight /* 75 */:
                return arrayPut(fVar, ArgType.NARROW)
            case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
                return arrayPut(fVar, ArgType.WIDE)
            case R.styleable.AppCompatTheme_listPopupWindowStyle /* 77 */:
                return arrayPut(fVar, ArgType.UNKNOWN_OBJECT)
            case R.styleable.AppCompatTheme_textAppearanceListItem /* 78 */:
                return arrayPut(fVar, ArgType.BOOLEAN)
            case R.styleable.AppCompatTheme_textAppearanceListItemSecondary /* 79 */:
                return arrayPut(fVar, ArgType.BYTE)
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                return arrayPut(fVar, ArgType.CHAR)
            case 81:
                return arrayPut(fVar, ArgType.SHORT)
            case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
            case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
            case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
            case R.styleable.AppCompatTheme_colorPrimary /* 85 */:
            case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
            case R.styleable.AppCompatTheme_colorAccent /* 87 */:
            case R.styleable.AppCompatTheme_colorControlNormal /* 88 */:
                FieldInfo fieldInfoFromDex = FieldInfo.fromDex(this.dex, fVar.d())
                IndexInsnNode indexInsnNode3 = IndexInsnNode(InsnType.IGET, fieldInfoFromDex, 1)
                indexInsnNode3.setResult(InsnArg.reg(fVar, 0, fieldInfoFromDex.getType()))
                indexInsnNode3.addArg(InsnArg.reg(fVar, 1, fieldInfoFromDex.getDeclClass().getType()))
                return indexInsnNode3
            case R.styleable.AppCompatTheme_colorControlActivated /* 89 */:
            case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
            case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
            case R.styleable.AppCompatTheme_controlBackground /* 93 */:
            case R.styleable.AppCompatTheme_colorBackgroundFloating /* 94 */:
            case R.styleable.AppCompatTheme_alertDialogStyle /* 95 */:
                FieldInfo fieldInfoFromDex2 = FieldInfo.fromDex(this.dex, fVar.d())
                IndexInsnNode indexInsnNode4 = IndexInsnNode(InsnType.IPUT, fieldInfoFromDex2, 2)
                indexInsnNode4.addArg(InsnArg.reg(fVar, 0, fieldInfoFromDex2.getType()))
                indexInsnNode4.addArg(InsnArg.reg(fVar, 1, fieldInfoFromDex2.getDeclClass().getType()))
                return indexInsnNode4
            case R.styleable.AppCompatTheme_alertDialogButtonGroupStyle /* 96 */:
            case R.styleable.AppCompatTheme_alertDialogCenterButtons /* 97 */:
            case R.styleable.AppCompatTheme_alertDialogTheme /* 98 */:
            case R.styleable.AppCompatTheme_textColorAlertDialogListItem /* 99 */:
            case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /* 100 */:
            case R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle /* 101 */:
            case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /* 102 */:
                FieldInfo fieldInfoFromDex3 = FieldInfo.fromDex(this.dex, fVar.d())
                IndexInsnNode indexInsnNode5 = IndexInsnNode(InsnType.SGET, fieldInfoFromDex3, 0)
                indexInsnNode5.setResult(InsnArg.reg(fVar, 0, fieldInfoFromDex3.getType()))
                return indexInsnNode5
            case R.styleable.AppCompatTheme_autoCompleteTextViewStyle /* 103 */:
            case R.styleable.AppCompatTheme_buttonStyle /* 104 */:
            case R.styleable.AppCompatTheme_buttonStyleSmall /* 105 */:
            case R.styleable.AppCompatTheme_checkboxStyle /* 106 */:
            case R.styleable.AppCompatTheme_checkedTextViewStyle /* 107 */:
            case 108:
            case 109:
                FieldInfo fieldInfoFromDex4 = FieldInfo.fromDex(this.dex, fVar.d())
                IndexInsnNode indexInsnNode6 = IndexInsnNode(InsnType.SPUT, fieldInfoFromDex4, 1)
                indexInsnNode6.addArg(InsnArg.reg(fVar, 0, fieldInfoFromDex4.getType()))
                return indexInsnNode6
            case R.styleable.AppCompatTheme_ratingBarStyle /* 110 */:
                return invoke(fVar, i, InvokeType.VIRTUAL, false)
            case R.styleable.AppCompatTheme_ratingBarStyleIndicator /* 111 */:
                return invoke(fVar, i, InvokeType.SUPER, false)
            case R.styleable.AppCompatTheme_ratingBarStyleSmall /* 112 */:
                return invoke(fVar, i, InvokeType.DIRECT, false)
            case R.styleable.AppCompatTheme_seekBarStyle /* 113 */:
                return invoke(fVar, i, InvokeType.STATIC, false)
            case R.styleable.AppCompatTheme_spinnerStyle /* 114 */:
                return invoke(fVar, i, InvokeType.INTERFACE, false)
            case R.styleable.AppCompatTheme_listMenuViewStyle /* 116 */:
                return invoke(fVar, i, InvokeType.VIRTUAL, true)
            case R.styleable.AppCompatTheme_tooltipFrameBackground /* 117 */:
                return invoke(fVar, i, InvokeType.SUPER, true)
            case R.styleable.AppCompatTheme_tooltipForegroundColor /* 118 */:
                return invoke(fVar, i, InvokeType.DIRECT, true)
            case R.styleable.AppCompatTheme_colorError /* 119 */:
                return invoke(fVar, i, InvokeType.STATIC, true)
            case 120:
                return invoke(fVar, i, InvokeType.INTERFACE, true)
            case 123:
                return neg(fVar, ArgType.INT)
            case 125:
                return neg(fVar, ArgType.LONG)
            case 127:
                return neg(fVar, ArgType.FLOAT)
            case 128:
                return neg(fVar, ArgType.DOUBLE)
            case 129:
                return cast(fVar, ArgType.INT, ArgType.LONG)
            case 130:
                return cast(fVar, ArgType.INT, ArgType.FLOAT)
            case 131:
                return cast(fVar, ArgType.INT, ArgType.DOUBLE)
            case 132:
                return cast(fVar, ArgType.LONG, ArgType.INT)
            case 133:
                return cast(fVar, ArgType.LONG, ArgType.FLOAT)
            case 134:
                return cast(fVar, ArgType.LONG, ArgType.DOUBLE)
            case 135:
                return cast(fVar, ArgType.FLOAT, ArgType.INT)
            case 136:
                return cast(fVar, ArgType.FLOAT, ArgType.LONG)
            case 137:
                return cast(fVar, ArgType.FLOAT, ArgType.DOUBLE)
            case 138:
                return cast(fVar, ArgType.DOUBLE, ArgType.INT)
            case 139:
                return cast(fVar, ArgType.DOUBLE, ArgType.LONG)
            case 140:
                return cast(fVar, ArgType.DOUBLE, ArgType.FLOAT)
            case 141:
                return cast(fVar, ArgType.INT, ArgType.BYTE)
            case 142:
                return cast(fVar, ArgType.INT, ArgType.CHAR)
            case 143:
                return cast(fVar, ArgType.INT, ArgType.SHORT)
            case 144:
            case 176:
                return arith(fVar, ArithOp.ADD, ArgType.INT)
            case 145:
            case 177:
                return arith(fVar, ArithOp.SUB, ArgType.INT)
            case 146:
            case 178:
                return arith(fVar, ArithOp.MUL, ArgType.INT)
            case 147:
            case 179:
                return arith(fVar, ArithOp.DIV, ArgType.INT)
            case 148:
            case 180:
                return arith(fVar, ArithOp.REM, ArgType.INT)
            case 149:
            case 181:
                return arith(fVar, ArithOp.AND, ArgType.INT)
            case 150:
            case 182:
                return arith(fVar, ArithOp.OR, ArgType.INT)
            case 151:
            case 183:
                return arith(fVar, ArithOp.XOR, ArgType.INT)
            case 152:
            case 184:
                return arith(fVar, ArithOp.SHL, ArgType.INT)
            case 153:
            case 185:
                return arith(fVar, ArithOp.SHR, ArgType.INT)
            case 154:
            case 186:
                return arith(fVar, ArithOp.USHR, ArgType.INT)
            case 155:
            case 187:
                return arith(fVar, ArithOp.ADD, ArgType.LONG)
            case 156:
            case 188:
                return arith(fVar, ArithOp.SUB, ArgType.LONG)
            case 157:
            case 189:
                return arith(fVar, ArithOp.MUL, ArgType.LONG)
            case 158:
            case 190:
                return arith(fVar, ArithOp.DIV, ArgType.LONG)
            case 159:
            case 191:
                return arith(fVar, ArithOp.REM, ArgType.LONG)
            case 160:
            case 192:
                return arith(fVar, ArithOp.AND, ArgType.LONG)
            case 161:
            case 193:
                return arith(fVar, ArithOp.OR, ArgType.LONG)
            case 162:
            case 194:
                return arith(fVar, ArithOp.XOR, ArgType.LONG)
            case 163:
            case 195:
                return arith(fVar, ArithOp.SHL, ArgType.LONG)
            case 164:
            case 196:
                return arith(fVar, ArithOp.SHR, ArgType.LONG)
            case 165:
            case 197:
                return arith(fVar, ArithOp.USHR, ArgType.LONG)
            case 166:
            case 198:
                return arith(fVar, ArithOp.ADD, ArgType.FLOAT)
            case 167:
            case 199:
                return arith(fVar, ArithOp.SUB, ArgType.FLOAT)
            case 168:
            case 200:
                return arith(fVar, ArithOp.MUL, ArgType.FLOAT)
            case 169:
            case 201:
                return arith(fVar, ArithOp.DIV, ArgType.FLOAT)
            case 170:
            case 202:
                return arith(fVar, ArithOp.REM, ArgType.FLOAT)
            case 171:
            case 203:
                return arith(fVar, ArithOp.ADD, ArgType.DOUBLE)
            case 172:
            case 204:
                return arith(fVar, ArithOp.SUB, ArgType.DOUBLE)
            case 173:
            case 205:
                return arith(fVar, ArithOp.MUL, ArgType.DOUBLE)
            case 174:
            case 206:
                return arith(fVar, ArithOp.DIV, ArgType.DOUBLE)
            case 175:
            case 207:
                return arith(fVar, ArithOp.REM, ArgType.DOUBLE)
            case 208:
            case 216:
                return arithLit(fVar, ArithOp.ADD, ArgType.INT)
            case 209:
            case 217:
                return ArithNode(ArithOp.SUB, InsnArg.reg(fVar, 0, ArgType.INT), InsnArg.lit(fVar, ArgType.INT), InsnArg.reg(fVar, 1, ArgType.INT))
            case 210:
            case 218:
                return arithLit(fVar, ArithOp.MUL, ArgType.INT)
            case 211:
            case 219:
                return arithLit(fVar, ArithOp.DIV, ArgType.INT)
            case 212:
            case 220:
                return arithLit(fVar, ArithOp.REM, ArgType.INT)
            case 213:
            case 221:
                return arithLit(fVar, ArithOp.AND, ArgType.INT)
            case 214:
            case 222:
                return arithLit(fVar, ArithOp.OR, ArgType.INT)
            case 215:
            case 223:
                return arithLit(fVar, ArithOp.XOR, ArgType.INT)
            case 224:
                return arithLit(fVar, ArithOp.SHL, ArgType.INT)
            case 225:
                return arithLit(fVar, ArithOp.SHR, ArgType.INT)
            case 226:
                return arithLit(fVar, ArithOp.USHR, ArgType.INT)
            default:
                throw DecodeException("Unknown instruction: " + e.a(fVar.b()))
        }
    }

    private fun decodeSwitch(f fVar, Int i, Boolean z) {
        Array<Int> iArr
        Array<Object> objArr
        Int iG = fVar.g()
        f fVar2 = this.insnArr[iG]
        if (z) {
            at atVar = (at) fVar2
            Array<Int> iArrV = atVar.v()
            Array<Object> objArr2 = new Object[iArrV.length]
            Int iU = atVar.u()
            Int i2 = 0
            while (i2 < objArr2.length) {
                objArr2[i2] = Integer.valueOf(iU)
                i2++
                iU++
            }
            iArr = iArrV
            objArr = objArr2
        } else {
            ax axVar = (ax) fVar2
            Array<Int> iArrV2 = axVar.v()
            Array<Object> objArr3 = new Object[iArrV2.length]
            for (Int i3 = 0; i3 < objArr3.length; i3++) {
                objArr3[i3] = Integer.valueOf(axVar.u()[i3])
            }
            iArr = iArrV2
            objArr = objArr3
        }
        for (Int i4 = 0; i4 < iArr.length; i4++) {
            iArr[i4] = (iArr[i4] - iG) + i
        }
        return SwitchNode(InsnArg.reg(fVar, 0, ArgType.NARROW), objArr, iArr, getNextInsnOffset(this.insnArr, i))
    }

    private fun fillArray(f fVar) {
        return FillArrayNode(fVar.n(), (g) this.insnArr[fVar.g()])
    }

    private fun filledNewArray(f fVar, Int i, Boolean z) {
        Int moveResultRegister = getMoveResultRegister(this.insnArr, i)
        ArgType type = this.dex.getType(fVar.d())
        ArgType arrayElement = type.getArrayElement()
        Boolean zIsPrimitive = arrayElement.isPrimitive()
        Int iM = fVar.m()
        Array<InsnArg> insnArgArr = new InsnArg[iM]
        if (z) {
            Int iN = fVar.n()
            for (Int i2 = 0; i2 < iM; i2++) {
                insnArgArr[i2] = InsnArg.reg(iN, arrayElement, zIsPrimitive)
                iN++
            }
        } else {
            for (Int i3 = 0; i3 < iM; i3++) {
                insnArgArr[i3] = InsnArg.reg(InsnUtils.getArg(fVar, i3), arrayElement, zIsPrimitive)
            }
        }
        FilledNewArrayNode filledNewArrayNode = FilledNewArrayNode(arrayElement, insnArgArr.length)
        filledNewArrayNode.setResult(moveResultRegister == -1 ? null : InsnArg.reg(moveResultRegister, type))
        for (InsnArg insnArg : insnArgArr) {
            filledNewArrayNode.addArg(insnArg)
        }
        return filledNewArrayNode
    }

    private fun getMoveResultRegister(Array<f> fVarArr, Int i) {
        f fVar
        Int iB
        Int nextInsnOffset = getNextInsnOffset(fVarArr, i)
        if (nextInsnOffset < 0 || !((iB = (fVar = fVarArr[nextInsnOffset]).b()) == 10 || iB == 11 || iB == 12)) {
            return -1
        }
        return fVar.n()
    }

    fun getNextInsnOffset(Array<Object> objArr, Int i) {
        Int i2 = i + 1
        while (i2 < objArr.length && objArr[i2] == null) {
            i2++
        }
        if (i2 >= objArr.length) {
            return -1
        }
        return i2
    }

    fun getPrevInsnOffset(Array<Object> objArr, Int i) {
        Int i2 = i - 1
        while (i2 >= 0 && objArr[i2] == null) {
            i2--
        }
        if (i2 < 0) {
            return -1
        }
        return i2
    }

    private fun insn(InsnType insnType, RegisterArg registerArg) {
        InsnNode insnNode = InsnNode(insnType, 0)
        insnNode.setResult(registerArg)
        return insnNode
    }

    private fun insn(InsnType insnType, RegisterArg registerArg, InsnArg insnArg) {
        InsnNode insnNode = InsnNode(insnType, 1)
        insnNode.setResult(registerArg)
        insnNode.addArg(insnArg)
        return insnNode
    }

    private fun invoke(f fVar, Int i, InvokeType invokeType, Boolean z) {
        return InvokeNode(MethodInfo.fromDex(this.dex, fVar.d()), fVar, invokeType, z, getMoveResultRegister(this.insnArr, i))
    }

    private fun neg(f fVar, ArgType argType) {
        InsnNode insnNode = InsnNode(InsnType.NEG, 1)
        insnNode.setResult(InsnArg.reg(fVar, 0, argType))
        insnNode.addArg(InsnArg.reg(fVar, 1, argType))
        return insnNode
    }

    fun decodeInsns(com.a.a.f fVar) throws DecodeException {
        Array<Short> sArrE = fVar.e()
        Array<f> fVarArr = new f[sArrE.length]
        av avVar = av(sArrE)
        while (avVar.f()) {
            try {
                fVarArr[avVar.a()] = f.a(avVar)
            } catch (Exception e) {
                throw DecodeException(this.method, "", e)
            }
        }
        this.insnArr = fVarArr
    }

    public Array<InsnNode> process() throws DecodeException {
        Array<InsnNode> insnNodeArr = new InsnNode[this.insnArr.length]
        for (Int i = 0; i < this.insnArr.length; i++) {
            f fVar = this.insnArr[i]
            if (fVar != null) {
                InsnNode insnNodeDecode = decode(fVar, i)
                if (insnNodeDecode != null) {
                    insnNodeDecode.setOffset(i)
                }
                insnNodeArr[i] = insnNodeDecode
            } else {
                insnNodeArr[i] = null
            }
        }
        this.insnArr = null
        return insnNodeArr
    }
}
