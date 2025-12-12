package jadx.core.dex.instructions.args

import androidx.appcompat.R
import jadx.core.Consts
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.parser.SignatureParser
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.Arrays

abstract class ArgType {
    protected Int hash
    public static val INT = primitive(PrimitiveType.INT)
    public static val BOOLEAN = primitive(PrimitiveType.BOOLEAN)
    public static val BYTE = primitive(PrimitiveType.BYTE)
    public static val SHORT = primitive(PrimitiveType.SHORT)
    public static val CHAR = primitive(PrimitiveType.CHAR)
    public static val FLOAT = primitive(PrimitiveType.FLOAT)
    public static val DOUBLE = primitive(PrimitiveType.DOUBLE)
    public static val LONG = primitive(PrimitiveType.LONG)
    public static val VOID = primitive(PrimitiveType.VOID)
    public static val OBJECT = object(Consts.CLASS_OBJECT)
    public static val CLASS = object(Consts.CLASS_CLASS)
    public static val STRING = object(Consts.CLASS_STRING)
    public static val ENUM = object(Consts.CLASS_ENUM)
    public static val THROWABLE = object(Consts.CLASS_THROWABLE)
    public static val UNKNOWN = unknown(PrimitiveType.values())
    public static val UNKNOWN_OBJECT = unknown(PrimitiveType.OBJECT, PrimitiveType.ARRAY)
    public static val NARROW = unknown(PrimitiveType.INT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN, PrimitiveType.SHORT, PrimitiveType.BYTE, PrimitiveType.CHAR, PrimitiveType.OBJECT, PrimitiveType.ARRAY)
    public static val NARROW_NUMBERS = unknown(PrimitiveType.INT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN, PrimitiveType.SHORT, PrimitiveType.BYTE, PrimitiveType.CHAR)
    public static val WIDE = unknown(PrimitiveType.LONG, PrimitiveType.DOUBLE)

    final class ArrayArg extends KnownType {
        public static final Array<PrimitiveType> ARRAY_POSSIBLES = {PrimitiveType.ARRAY}
        private final ArgType arrayElement

        constructor(ArgType argType) {
            super()
            this.arrayElement = argType
            this.hash = argType.hashCode()
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Int getArrayDimension() {
            return this.arrayElement.getArrayDimension() + 1
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final ArgType getArrayElement() {
            return this.arrayElement
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final ArgType getArrayRootElement() {
            return this.arrayElement.getArrayRootElement()
        }

        @Override // jadx.core.dex.instructions.args.ArgType.KnownType, jadx.core.dex.instructions.args.ArgType
        public final Array<PrimitiveType> getPossibleTypes() {
            return ARRAY_POSSIBLES
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final PrimitiveType getPrimitiveType() {
            return PrimitiveType.ARRAY
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        final Boolean internalEquals(Object obj) {
            return this.arrayElement.equals(((ArrayArg) obj).arrayElement)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Boolean isArray() {
            return true
        }

        @Override // jadx.core.dex.instructions.args.ArgType.KnownType, jadx.core.dex.instructions.args.ArgType
        public final Boolean isTypeKnown() {
            return this.arrayElement.isTypeKnown()
        }

        @Override // jadx.core.dex.instructions.args.ArgType.KnownType, jadx.core.dex.instructions.args.ArgType
        public final ArgType selectFirst() {
            return array(this.arrayElement.selectFirst())
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final String toString() {
            return this.arrayElement + "[]"
        }
    }

    class GenericObject extends ObjectType {
        private final Array<ArgType> generics
        private final GenericObject outerType

        constructor(GenericObject genericObject, String str, Array<ArgType> argTypeArr) {
            super(genericObject.getObject() + Deobfuscator.INNER_CLASS_SEPARATOR + str)
            this.outerType = genericObject
            this.generics = argTypeArr
            this.hash = genericObject.hashCode() + (str.hashCode() * 31) + (Arrays.hashCode(argTypeArr) * 961)
        }

        constructor(String str, Array<ArgType> argTypeArr) {
            super(str)
            this.outerType = null
            this.generics = argTypeArr
            this.hash = str.hashCode() + (Arrays.hashCode(argTypeArr) * 31)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public Array<ArgType> getGenericTypes() {
            return this.generics
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun getOuterType() {
            return this.outerType
        }

        @Override // jadx.core.dex.instructions.args.ArgType.ObjectType, jadx.core.dex.instructions.args.ArgType
        Boolean internalEquals(Object obj) {
            return super.internalEquals(obj) && Arrays.equals(this.generics, ((GenericObject) obj).generics)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun isGeneric() {
            return true
        }

        @Override // jadx.core.dex.instructions.args.ArgType.ObjectType, jadx.core.dex.instructions.args.ArgType
        fun toString() {
            return super.toString() + "<" + Utils.arrayToString(this.generics) + ">"
        }
    }

    final class GenericType extends ObjectType {
        constructor(String str) {
            super(str)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Boolean isGenericType() {
            return true
        }
    }

    abstract class KnownType extends ArgType {
        private static final Array<PrimitiveType> EMPTY_POSSIBLES = new PrimitiveType[0]

        private constructor() {
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun contains(PrimitiveType primitiveType) {
            return getPrimitiveType() == primitiveType
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public Array<PrimitiveType> getPossibleTypes() {
            return EMPTY_POSSIBLES
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun isTypeKnown() {
            return true
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun selectFirst() {
            return null
        }
    }

    class ObjectType extends KnownType {
        private final String object

        constructor(String str) {
            super()
            this.object = Utils.cleanObjectName(str)
            this.hash = this.object.hashCode()
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun getObject() {
            return this.object
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun getPrimitiveType() {
            return PrimitiveType.OBJECT
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        Boolean internalEquals(Object obj) {
            return this.object.equals(((ObjectType) obj).object)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun isObject() {
            return true
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        fun toString() {
            return this.object
        }
    }

    final class PrimitiveArg extends KnownType {
        private final PrimitiveType type

        constructor(PrimitiveType primitiveType) {
            super()
            this.type = primitiveType
            this.hash = primitiveType.hashCode()
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final PrimitiveType getPrimitiveType() {
            return this.type
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        final Boolean internalEquals(Object obj) {
            return this.type == ((PrimitiveArg) obj).type
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Boolean isPrimitive() {
            return true
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final String toString() {
            return this.type.toString()
        }
    }

    final class UnknownArg extends ArgType {
        private final Array<PrimitiveType> possibleTypes

        constructor(Array<PrimitiveType> primitiveTypeArr) {
            this.possibleTypes = primitiveTypeArr
            this.hash = Arrays.hashCode(this.possibleTypes)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Boolean contains(PrimitiveType primitiveType) {
            for (PrimitiveType primitiveType2 : this.possibleTypes) {
                if (primitiveType2 == primitiveType) {
                    return true
                }
            }
            return false
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Array<PrimitiveType> getPossibleTypes() {
            return this.possibleTypes
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        final Boolean internalEquals(Object obj) {
            return Arrays.equals(this.possibleTypes, ((UnknownArg) obj).possibleTypes)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Boolean isTypeKnown() {
            return false
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final ArgType selectFirst() {
            return contains(PrimitiveType.OBJECT) ? OBJECT : contains(PrimitiveType.ARRAY) ? array(OBJECT) : ArgType.primitive(this.possibleTypes[0])
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final String toString() {
            return this.possibleTypes.length == PrimitiveType.values().length ? "?" : "?" + Arrays.toString(this.possibleTypes)
        }
    }

    final class WildcardType extends ObjectType {
        private final Int bounds
        private final ArgType type

        constructor(ArgType argType, Int i) {
            super(OBJECT.getObject())
            this.type = argType
            this.bounds = i
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Int getWildcardBounds() {
            return this.bounds
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final ArgType getWildcardType() {
            return this.type
        }

        @Override // jadx.core.dex.instructions.args.ArgType.ObjectType, jadx.core.dex.instructions.args.ArgType
        final Boolean internalEquals(Object obj) {
            return super.internalEquals(obj) && this.bounds == ((WildcardType) obj).bounds && this.type.equals(((WildcardType) obj).type)
        }

        @Override // jadx.core.dex.instructions.args.ArgType
        public final Boolean isGeneric() {
            return true
        }

        @Override // jadx.core.dex.instructions.args.ArgType.ObjectType, jadx.core.dex.instructions.args.ArgType
        public final String toString() {
            if (this.bounds == 0) {
                return "?"
            }
            return "? " + (this.bounds == -1 ? "super" : "extends") + " " + this.type
        }
    }

    fun array(ArgType argType) {
        return ArrayArg(argType)
    }

    fun generic(String str) {
        return SignatureParser(str).consumeType()
    }

    fun generic(String str, Array<ArgType> argTypeArr) {
        return GenericObject(str, argTypeArr)
    }

    fun genericInner(ArgType argType, String str, Array<ArgType> argTypeArr) {
        return GenericObject((GenericObject) argType, str, argTypeArr)
    }

    fun genericType(String str) {
        return GenericType(str)
    }

    fun isCastNeeded(DexNode dexNode, ArgType argType, ArgType argType2) {
        if (argType.equals(argType2)) {
            return false
        }
        return (argType.isObject() && argType2.isObject() && dexNode.root().getClsp().isImplements(argType.getObject(), argType2.getObject())) ? false : true
    }

    fun isInstanceOf(DexNode dexNode, ArgType argType, ArgType argType2) {
        if (argType.equals(argType2)) {
            return true
        }
        if (argType.isObject() && argType2.isObject()) {
            return dexNode.root().getClsp().isImplements(argType.getObject(), argType2.getObject())
        }
        return false
    }

    fun merge(DexNode dexNode, ArgType argType, ArgType argType2) {
        if (argType == null || argType2 == null) {
            return null
        }
        if (argType.equals(argType2)) {
            return argType
        }
        ArgType argTypeMergeInternal = mergeInternal(dexNode, argType, argType2)
        if (argTypeMergeInternal == null) {
            argTypeMergeInternal = mergeInternal(dexNode, argType2, argType)
        }
        return argTypeMergeInternal
    }

    private fun mergeArrays(DexNode dexNode, ArrayArg arrayArg, ArgType argType) {
        if (!argType.isArray()) {
            if (argType.contains(PrimitiveType.ARRAY)) {
                return arrayArg
            }
            if (argType.equals(OBJECT)) {
                return OBJECT
            }
            return null
        }
        ArgType arrayElement = arrayArg.getArrayElement()
        ArgType arrayElement2 = argType.getArrayElement()
        if (arrayElement.isPrimitive() && arrayElement2.isPrimitive()) {
            return OBJECT
        }
        ArgType argTypeMerge = merge(dexNode, arrayElement, arrayElement2)
        if (argTypeMerge == null) {
            return null
        }
        return array(argTypeMerge)
    }

    private fun mergeInternal(DexNode dexNode, ArgType argType, ArgType argType2) {
        String commonAncestor
        if (argType == UNKNOWN) {
            return argType2
        }
        if (argType.isArray()) {
            return mergeArrays(dexNode, (ArrayArg) argType, argType2)
        }
        if (argType2.isArray()) {
            return mergeArrays(dexNode, (ArrayArg) argType2, argType)
        }
        if (!argType.isTypeKnown()) {
            if (argType2.isTypeKnown()) {
                if (argType.contains(argType2.getPrimitiveType())) {
                    return argType2
                }
                return null
            }
            ArrayList arrayList = ArrayList()
            for (PrimitiveType primitiveType : argType.getPossibleTypes()) {
                if (argType2.contains(primitiveType)) {
                    arrayList.add(primitiveType)
                }
            }
            if (arrayList.isEmpty()) {
                return null
            }
            if (arrayList.size() != 1) {
                return unknown((Array<PrimitiveType>) arrayList.toArray(new PrimitiveType[arrayList.size()]))
            }
            PrimitiveType primitiveType2 = (PrimitiveType) arrayList.get(0)
            return (primitiveType2 == PrimitiveType.OBJECT || primitiveType2 == PrimitiveType.ARRAY) ? unknown(primitiveType2) : primitive(primitiveType2)
        }
        if (argType.isGenericType()) {
            return argType
        }
        if (argType2.isGenericType()) {
            return argType2
        }
        if (!argType.isObject() || !argType2.isObject()) {
            if (argType.isPrimitive() && argType2.isPrimitive() && argType.getRegCount() == argType2.getRegCount()) {
                return primitive(PrimitiveType.getSmaller(argType.getPrimitiveType(), argType2.getPrimitiveType()))
            }
            return null
        }
        String object = argType.getObject()
        String object2 = argType2.getObject()
        if (object.equals(object2)) {
            return argType.getGenericTypes() != null ? argType : argType2
        }
        if (object.equals(Consts.CLASS_OBJECT)) {
            return argType2
        }
        if (object2.equals(Consts.CLASS_OBJECT)) {
            return argType
        }
        if (dexNode != null && (commonAncestor = dexNode.root().getClsp().getCommonAncestor(object, object2)) != null) {
            return object(commonAncestor)
        }
        return null
    }

    fun object(String str) {
        return ObjectType(str)
    }

    fun parse(Char c) {
        switch (c) {
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return BYTE
            case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                return CHAR
            case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                return DOUBLE
            case R.styleable.AppCompatTheme_searchViewStyle /* 70 */:
                return FLOAT
            case R.styleable.AppCompatTheme_listPreferredItemHeightLarge /* 73 */:
                return INT
            case R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /* 74 */:
                return LONG
            case R.styleable.AppCompatTheme_panelMenuListTheme /* 83 */:
                return SHORT
            case R.styleable.AppCompatTheme_colorPrimaryDark /* 86 */:
                return VOID
            case R.styleable.AppCompatTheme_colorControlHighlight /* 90 */:
                return BOOLEAN
            default:
                return null
        }
    }

    fun parse(String str) {
        Char cCharAt = str.charAt(0)
        switch (cCharAt) {
            case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
                return object(str)
            case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
                return genericType(str.substring(1, str.length() - 1))
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                return array(parse(str.substring(1)))
            default:
                return parse(cCharAt)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun primitive(PrimitiveType primitiveType) {
        return PrimitiveArg(primitiveType)
    }

    fun unknown(PrimitiveType... primitiveTypeArr) {
        return UnknownArg(primitiveTypeArr)
    }

    fun wildcard() {
        return WildcardType(OBJECT, 0)
    }

    fun wildcard(ArgType argType, Int i) {
        return WildcardType(argType, i)
    }

    public abstract Boolean contains(PrimitiveType primitiveType)

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && this.hash == obj.hashCode() && getClass() == obj.getClass()) {
            return internalEquals(obj)
        }
        return false
    }

    fun getArrayDimension() {
        return 0
    }

    fun getArrayElement() {
        return null
    }

    fun getArrayRootElement() {
        return this
    }

    public Array<ArgType> getGenericTypes() {
        return null
    }

    fun getObject() {
        throw UnsupportedOperationException("ArgType.getObject(), call class: " + getClass())
    }

    fun getOuterType() {
        return null
    }

    public abstract Array<PrimitiveType> getPossibleTypes()

    fun getPrimitiveType() {
        return null
    }

    fun getRegCount() {
        if (!isPrimitive()) {
            return !isTypeKnown() ? 0 : 1
        }
        PrimitiveType primitiveType = getPrimitiveType()
        return (primitiveType == PrimitiveType.LONG || primitiveType == PrimitiveType.DOUBLE) ? 2 : 1
    }

    fun getWildcardBounds() {
        return 0
    }

    fun getWildcardType() {
        return null
    }

    fun hashCode() {
        return this.hash
    }

    abstract Boolean internalEquals(Object obj)

    fun isArray() {
        return false
    }

    fun isGeneric() {
        return false
    }

    fun isGenericType() {
        return false
    }

    fun isObject() {
        return false
    }

    fun isPrimitive() {
        return false
    }

    fun isTypeKnown() {
        return false
    }

    public abstract ArgType selectFirst()

    fun toString() {
        return "ARG_TYPE"
    }
}
