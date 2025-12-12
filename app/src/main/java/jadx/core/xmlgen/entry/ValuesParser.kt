package jadx.core.xmlgen.entry

import androidx.core.view.InputDeviceCompat
import jadx.core.xmlgen.ParserConstants
import java.text.NumberFormat
import java.util.ArrayList
import java.util.List
import java.util.Map

class ValuesParser extends ParserConstants {
    private final Map resMap
    private final Array<String> strings

    constructor(Array<String> strArr, Map map) {
        this.strings = strArr
        this.resMap = map
    }

    private fun decodeComplex(Int i, Boolean z) {
        String str
        Double d = (i & InputDeviceCompat.SOURCE_ANY) * RADIX_MULTS[(i >> 4) & 3]
        Int i2 = i & 15
        if (!z) {
            switch (i2) {
                case 0:
                    str = "px"
                    break
                case 1:
                    str = "dp"
                    break
                case 2:
                    str = "sp"
                    break
                case 3:
                    str = "pt"
                    break
                case 4:
                    str = "in"
                    break
                case 5:
                    str = "mm"
                    break
                default:
                    str = "?d" + Integer.toHexString(i2)
                    break
            }
        } else {
            d *= 100.0d
            switch (i2) {
                case 0:
                    str = "%"
                    break
                case 1:
                    str = "%p"
                    break
                default:
                    str = "?f" + Integer.toHexString(i2)
                    break
            }
        }
        return doubleToString(d) + str
    }

    private fun decodeNameRef(Int i) {
        Int i2
        if (isResInternalId(i)) {
            i2 = 65535 & i
            if (i2 == 0) {
                return null
            }
        } else {
            i2 = i
        }
        String str = (String) this.resMap.get(Integer.valueOf(i2))
        return str != null ? str.replace('/', '.') : "?0x" + Integer.toHexString(i)
    }

    private fun doubleToString(Double d) {
        if (d == Math.ceil(d)) {
            return Integer.toString((Int) d)
        }
        NumberFormat numberFormat = NumberFormat.getInstance()
        numberFormat.setMaximumFractionDigits(4)
        numberFormat.setMinimumIntegerDigits(1)
        return numberFormat.format(d)
    }

    fun decodeValue(Int i, Int i2) {
        switch (i) {
            case 0:
                return null
            case 1:
                String str = (String) this.resMap.get(Integer.valueOf(i2))
                return str == null ? "?unknown_ref: " + Integer.toHexString(i2) : "@" + str
            case 2:
                String str2 = (String) this.resMap.get(Integer.valueOf(i2))
                return str2 == null ? "?unknown_attr_ref: " + Integer.toHexString(i2) : "?" + str2
            case 3:
                return this.strings[i2]
            case 4:
                return Float.toString(Float.intBitsToFloat(i2))
            case 5:
                return decodeComplex(i2, false)
            case 6:
                return decodeComplex(i2, true)
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
                return "  ?0x" + Integer.toHexString(i) + " " + i2
            case 16:
                return Integer.toString(i2)
            case 17:
                return Integer.toHexString(i2)
            case 18:
                return i2 == 0 ? "false" : "true"
            case 28:
                return String.format("#%08x", Integer.valueOf(i2))
            case 29:
                return String.format("#%06x", Integer.valueOf(16777215 & i2))
            case 30:
                return String.format("#%04x", Integer.valueOf(65535 & i2))
            case 31:
                return String.format("#%03x", Integer.valueOf(i2 & 4095))
        }
    }

    fun decodeValue(RawValue rawValue) {
        return decodeValue(rawValue.getDataType(), rawValue.getData())
    }

    fun getValueString(ResourceEntry resourceEntry) {
        RawValue simpleValue = resourceEntry.getSimpleValue()
        if (simpleValue != null) {
            return decodeValue(simpleValue)
        }
        List<RawNamedValue> namedValues = resourceEntry.getNamedValues()
        ArrayList arrayList = ArrayList(namedValues.size())
        for (RawNamedValue rawNamedValue : namedValues) {
            String strDecodeNameRef = decodeNameRef(rawNamedValue.getNameRef())
            String strDecodeValue = decodeValue(rawNamedValue.getRawValue())
            if (strDecodeNameRef == null) {
                arrayList.add(strDecodeValue)
            } else {
                arrayList.add(strDecodeNameRef + "=" + strDecodeValue)
            }
        }
        return arrayList.toString()
    }
}
