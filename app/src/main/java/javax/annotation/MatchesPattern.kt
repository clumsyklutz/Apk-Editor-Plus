package javax.annotation

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.regex.Pattern
import javax.annotation.meta.TypeQualifier
import javax.annotation.meta.TypeQualifierValidator
import javax.annotation.meta.When

@TypeQualifier(applicableTo = String.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesPattern {

    class Checker implements TypeQualifierValidator {
        @Override // javax.annotation.meta.TypeQualifierValidator
        fun forConstantValue(MatchesPattern matchesPattern, Object obj) {
            return Pattern.compile(matchesPattern.value(), matchesPattern.flags()).matcher((String) obj).matches() ? When.ALWAYS : When.NEVER
        }
    }

    Int flags() default 0

    @RegEx
    String value()
}
