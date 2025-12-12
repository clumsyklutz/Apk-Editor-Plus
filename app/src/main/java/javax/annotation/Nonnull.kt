package javax.annotation

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.annotation.meta.TypeQualifier
import javax.annotation.meta.TypeQualifierValidator
import javax.annotation.meta.When

@TypeQualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Nonnull {

    class Checker implements TypeQualifierValidator {
        @Override // javax.annotation.meta.TypeQualifierValidator
        fun forConstantValue(Nonnull nonnull, Object obj) {
            return obj == null ? When.NEVER : When.ALWAYS
        }
    }

    When when() default When.ALWAYS
}
