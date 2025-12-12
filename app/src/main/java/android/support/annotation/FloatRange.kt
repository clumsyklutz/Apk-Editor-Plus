package android.support.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface FloatRange {
    Double from() default Double.NEGATIVE_INFINITY

    Boolean fromInclusive() default true

    Double to() default Double.POSITIVE_INFINITY

    Boolean toInclusive() default true
}
