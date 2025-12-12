package org.jf.dexlib2.writer.builder

import androidx.appcompat.R
import java.util.List
import java.util.Set
import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue
import org.jf.dexlib2.base.value.BaseArrayEncodedValue
import org.jf.dexlib2.base.value.BaseBooleanEncodedValue
import org.jf.dexlib2.base.value.BaseEnumEncodedValue
import org.jf.dexlib2.base.value.BaseFieldEncodedValue
import org.jf.dexlib2.base.value.BaseMethodEncodedValue
import org.jf.dexlib2.base.value.BaseMethodHandleEncodedValue
import org.jf.dexlib2.base.value.BaseMethodTypeEncodedValue
import org.jf.dexlib2.base.value.BaseNullEncodedValue
import org.jf.dexlib2.base.value.BaseStringEncodedValue
import org.jf.dexlib2.base.value.BaseTypeEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue
import org.jf.util.ExceptionWithContext

abstract class BuilderEncodedValues {

    public static class BuilderAnnotationEncodedValue extends BaseAnnotationEncodedValue implements BuilderEncodedValue {
        public final Set<? extends BuilderAnnotationElement> elements
        public final BuilderTypeReference typeReference

        constructor(BuilderTypeReference builderTypeReference, Set<? extends BuilderAnnotationElement> set) {
            this.typeReference = builderTypeReference
            this.elements = set
        }

        @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
        public Set<? extends BuilderAnnotationElement> getElements() {
            return this.elements
        }

        @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
        fun getType() {
            return this.typeReference.getType()
        }
    }

    public static class BuilderArrayEncodedValue extends BaseArrayEncodedValue implements BuilderEncodedValue {
        public final List<? extends BuilderEncodedValue> elements
        public Int offset = 0

        constructor(List<? extends BuilderEncodedValue> list) {
            this.elements = list
        }

        @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
        public List<? extends EncodedValue> getValue() {
            return this.elements
        }
    }

    public static class BuilderBooleanEncodedValue extends BaseBooleanEncodedValue implements BuilderEncodedValue {
        public final Boolean value
        public static val TRUE_VALUE = BuilderBooleanEncodedValue(true)
        public static val FALSE_VALUE = BuilderBooleanEncodedValue(false)

        constructor(Boolean z) {
            this.value = z
        }

        @Override // org.jf.dexlib2.iface.value.BooleanEncodedValue
        fun getValue() {
            return this.value
        }
    }

    public static class BuilderByteEncodedValue extends ImmutableByteEncodedValue implements BuilderEncodedValue {
        constructor(Byte b2) {
            super(b2)
        }
    }

    public static class BuilderCharEncodedValue extends ImmutableCharEncodedValue implements BuilderEncodedValue {
        constructor(Char c) {
            super(c)
        }
    }

    public static class BuilderDoubleEncodedValue extends ImmutableDoubleEncodedValue implements BuilderEncodedValue {
        constructor(Double d) {
            super(d)
        }
    }

    public interface BuilderEncodedValue extends EncodedValue {
    }

    public static class BuilderEnumEncodedValue extends BaseEnumEncodedValue implements BuilderEncodedValue {
        public final BuilderFieldReference enumReference

        constructor(BuilderFieldReference builderFieldReference) {
            this.enumReference = builderFieldReference
        }

        @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
        fun getValue() {
            return this.enumReference
        }
    }

    public static class BuilderFieldEncodedValue extends BaseFieldEncodedValue implements BuilderEncodedValue {
        public final BuilderFieldReference fieldReference

        constructor(BuilderFieldReference builderFieldReference) {
            this.fieldReference = builderFieldReference
        }

        @Override // org.jf.dexlib2.iface.value.FieldEncodedValue
        fun getValue() {
            return this.fieldReference
        }
    }

    public static class BuilderFloatEncodedValue extends ImmutableFloatEncodedValue implements BuilderEncodedValue {
        constructor(Float f) {
            super(f)
        }
    }

    public static class BuilderIntEncodedValue extends ImmutableIntEncodedValue implements BuilderEncodedValue {
        constructor(Int i) {
            super(i)
        }
    }

    public static class BuilderLongEncodedValue extends ImmutableLongEncodedValue implements BuilderEncodedValue {
        constructor(Long j) {
            super(j)
        }
    }

    public static class BuilderMethodEncodedValue extends BaseMethodEncodedValue implements BuilderEncodedValue {
        public final BuilderMethodReference methodReference

        constructor(BuilderMethodReference builderMethodReference) {
            this.methodReference = builderMethodReference
        }

        @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
        fun getValue() {
            return this.methodReference
        }
    }

    public static class BuilderMethodHandleEncodedValue extends BaseMethodHandleEncodedValue implements BuilderEncodedValue {
        public final BuilderMethodHandleReference methodHandleReference

        constructor(BuilderMethodHandleReference builderMethodHandleReference) {
            this.methodHandleReference = builderMethodHandleReference
        }

        @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
        fun getValue() {
            return this.methodHandleReference
        }
    }

    public static class BuilderMethodTypeEncodedValue extends BaseMethodTypeEncodedValue implements BuilderEncodedValue {
        public final BuilderMethodProtoReference methodProtoReference

        constructor(BuilderMethodProtoReference builderMethodProtoReference) {
            this.methodProtoReference = builderMethodProtoReference
        }

        @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
        fun getValue() {
            return this.methodProtoReference
        }
    }

    public static class BuilderNullEncodedValue extends BaseNullEncodedValue implements BuilderEncodedValue {
        public static val INSTANCE = BuilderNullEncodedValue()
    }

    public static class BuilderShortEncodedValue extends ImmutableShortEncodedValue implements BuilderEncodedValue {
        constructor(Short s) {
            super(s)
        }
    }

    public static class BuilderStringEncodedValue extends BaseStringEncodedValue implements BuilderEncodedValue {
        public final BuilderStringReference stringReference

        constructor(BuilderStringReference builderStringReference) {
            this.stringReference = builderStringReference
        }

        @Override // org.jf.dexlib2.iface.value.StringEncodedValue
        fun getValue() {
            return this.stringReference.getString()
        }
    }

    public static class BuilderTypeEncodedValue extends BaseTypeEncodedValue implements BuilderEncodedValue {
        public final BuilderTypeReference typeReference

        constructor(BuilderTypeReference builderTypeReference) {
            this.typeReference = builderTypeReference
        }

        @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
        fun getValue() {
            return this.typeReference.getType()
        }
    }

    fun defaultValueForType(String str) {
        Char cCharAt = str.charAt(0)
        if (cCharAt == 'F') {
            return BuilderFloatEncodedValue(0.0f)
        }
        if (cCharAt != 'L') {
            if (cCharAt == 'S') {
                return BuilderShortEncodedValue((Short) 0)
            }
            if (cCharAt == 'I') {
                return BuilderIntEncodedValue(0)
            }
            if (cCharAt == 'J') {
                return BuilderLongEncodedValue(0L)
            }
            if (cCharAt == 'Z') {
                return BuilderBooleanEncodedValue.FALSE_VALUE
            }
            if (cCharAt != '[') {
                switch (cCharAt) {
                    case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                        return BuilderByteEncodedValue((Byte) 0)
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                        return BuilderCharEncodedValue((Char) 0)
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                        return BuilderDoubleEncodedValue(0.0d)
                    default:
                        throw ExceptionWithContext("Unrecognized type: %s", str)
                }
            }
        }
        return BuilderNullEncodedValue.INSTANCE
    }
}
