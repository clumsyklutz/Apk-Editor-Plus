package javax.annotation

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.annotation.meta.TypeQualifier
import javax.annotation.meta.TypeQualifierValidator
import javax.annotation.meta.When

@TypeQualifier(applicableTo = Number.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Nonnegative {

    class Checker implements TypeQualifierValidator {
        @Override // javax.annotation.meta.TypeQualifierValidator
        fun forConstantValue(Nonnegative nonnegative, Object obj) {
            Boolean z = true
            if (!(obj is Number)) {
                return When.NEVER
            }
            Number number = (Number) obj
            if (number is Long) {
                if (number.longValue() >= 0) {
                    z = false
                }
            } else if (number is Double) {
                if (number.doubleValue() >= 0.0d) {
                    z = false
                }
            } else if (number is Float) {
                if (number.floatValue() >= 0.0f) {
                    z = false
                }
            } else if (number.intValue() >= 0) {
                z = false
            }
            return z ? When.NEVER : When.ALWAYS
        }
    }

    When when() default When.ALWAYS
}
