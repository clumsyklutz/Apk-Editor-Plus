package javax.annotation

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import javax.annotation.meta.TypeQualifierNickname
import javax.annotation.meta.TypeQualifierValidator
import javax.annotation.meta.When

@Syntax("RegEx")
@TypeQualifierNickname
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RegEx {

    class Checker implements TypeQualifierValidator {
        @Override // javax.annotation.meta.TypeQualifierValidator
        fun forConstantValue(RegEx regEx, Object obj) {
            if (!(obj is String)) {
                return When.NEVER
            }
            try {
                Pattern.compile((String) obj)
                return When.ALWAYS
            } catch (PatternSyntaxException e) {
                return When.NEVER
            }
        }
    }

    When when() default When.ALWAYS
}
