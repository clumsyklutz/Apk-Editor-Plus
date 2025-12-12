package android.support.annotation

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Dimension {
    public static val DP = 0
    public static val PX = 1
    public static val SP = 2

    Int unit() default 1
}
