package jadx.core.xmlgen

import java.util.HashMap
import java.util.Map

class ParserConstants {
    protected static val ATTR_L10N_NOT_REQUIRED = 0
    protected static val ATTR_L10N_SUGGESTED = 1
    protected static val ATTR_TYPE_ANY = 65535
    protected static val ATTR_TYPE_BOOLEAN = 8
    protected static val ATTR_TYPE_COLOR = 16
    protected static val ATTR_TYPE_DIMENSION = 64
    protected static val ATTR_TYPE_ENUM = 65536
    protected static val ATTR_TYPE_FLAGS = 131072
    protected static val ATTR_TYPE_FLOAT = 32
    protected static val ATTR_TYPE_FRACTION = 128
    protected static val ATTR_TYPE_INTEGER = 4
    protected static val ATTR_TYPE_REFERENCE = 1
    protected static val ATTR_TYPE_STRING = 2
    protected static val COMPLEX_MANTISSA_MASK = 16777215
    protected static val COMPLEX_MANTISSA_SHIFT = 8
    protected static val COMPLEX_RADIX_0p23 = 3
    protected static val COMPLEX_RADIX_16p7 = 1
    protected static val COMPLEX_RADIX_23p0 = 0
    protected static val COMPLEX_RADIX_8p15 = 2
    protected static val COMPLEX_RADIX_MASK = 3
    protected static val COMPLEX_RADIX_SHIFT = 4
    protected static val COMPLEX_UNIT_DIP = 1
    protected static val COMPLEX_UNIT_FRACTION = 0
    protected static val COMPLEX_UNIT_FRACTION_PARENT = 1
    protected static val COMPLEX_UNIT_IN = 4
    protected static val COMPLEX_UNIT_MASK = 15
    protected static val COMPLEX_UNIT_MM = 5
    protected static val COMPLEX_UNIT_PT = 3
    protected static val COMPLEX_UNIT_PX = 0
    protected static val COMPLEX_UNIT_SHIFT = 0
    protected static val COMPLEX_UNIT_SP = 2
    protected static val FLAG_COMPLEX = 1
    protected static val FLAG_PUBLIC = 2
    protected static val NO_ENTRY = -1
    protected static val RES_NULL_TYPE = 0
    protected static val RES_STRING_POOL_TYPE = 1
    protected static val RES_TABLE_PACKAGE_TYPE = 512
    protected static val RES_TABLE_TYPE = 2
    protected static val RES_TABLE_TYPE_SPEC_TYPE = 514
    protected static val RES_TABLE_TYPE_TYPE = 513
    protected static val RES_XML_CDATA_TYPE = 260
    protected static val RES_XML_END_ELEMENT_TYPE = 259
    protected static val RES_XML_END_NAMESPACE_TYPE = 257
    protected static val RES_XML_FIRST_CHUNK_TYPE = 256
    protected static val RES_XML_LAST_CHUNK_TYPE = 383
    protected static val RES_XML_RESOURCE_MAP_TYPE = 384
    protected static val RES_XML_START_ELEMENT_TYPE = 258
    protected static val RES_XML_START_NAMESPACE_TYPE = 256
    protected static val RES_XML_TYPE = 3
    protected static val SORTED_FLAG = 1
    protected static val TYPE_ATTRIBUTE = 2
    protected static val TYPE_DIMENSION = 5
    protected static val TYPE_FIRST_COLOR_INT = 28
    protected static val TYPE_FIRST_INT = 16
    protected static val TYPE_FLOAT = 4
    protected static val TYPE_FRACTION = 6
    protected static val TYPE_INT_BOOLEAN = 18
    protected static val TYPE_INT_COLOR_ARGB4 = 30
    protected static val TYPE_INT_COLOR_ARGB8 = 28
    protected static val TYPE_INT_COLOR_RGB4 = 31
    protected static val TYPE_INT_COLOR_RGB8 = 29
    protected static val TYPE_INT_DEC = 16
    protected static val TYPE_INT_HEX = 17
    protected static val TYPE_LAST_COLOR_INT = 31
    protected static val TYPE_LAST_INT = 31
    protected static val TYPE_NULL = 0
    protected static val TYPE_REFERENCE = 1
    protected static val TYPE_STRING = 3
    protected static val UTF8_FLAG = 256
    protected static val MANTISSA_MULT = 0.00390625d
    protected static final Array<Double> RADIX_MULTS = {MANTISSA_MULT, 3.0517578125E-5d, 1.1920928955078125E-7d, 4.6566128730773926E-10d}
    protected static val ATTR_TYPE = ResMakeInternal(0)
    protected static val ATTR_MIN = ResMakeInternal(1)
    protected static val ATTR_MAX = ResMakeInternal(2)
    protected static val ATTR_L10N = ResMakeInternal(3)
    protected static val ATTR_OTHER = ResMakeInternal(4)
    protected static val ATTR_ZERO = ResMakeInternal(5)
    protected static val ATTR_ONE = ResMakeInternal(6)
    protected static val ATTR_TWO = ResMakeInternal(7)
    protected static val ATTR_FEW = ResMakeInternal(8)
    protected static val ATTR_MANY = ResMakeInternal(9)
    protected static val PLURALS_MAP = HashMap() { // from class: jadx.core.xmlgen.ParserConstants.1
        {
            put(Integer.valueOf(ParserConstants.ATTR_OTHER), "other")
            put(Integer.valueOf(ParserConstants.ATTR_ZERO), "zero")
            put(Integer.valueOf(ParserConstants.ATTR_ONE), "one")
            put(Integer.valueOf(ParserConstants.ATTR_TWO), "two")
            put(Integer.valueOf(ParserConstants.ATTR_FEW), "few")
            put(Integer.valueOf(ParserConstants.ATTR_MANY), "many")
        }
    }

    private fun ResMakeInternal(Int i) {
        return 16777216 | (65535 & i)
    }

    protected static Boolean isResInternalId(Int i) {
        return ((-65536) & i) != 0 && (16711680 & i) == 0
    }
}
