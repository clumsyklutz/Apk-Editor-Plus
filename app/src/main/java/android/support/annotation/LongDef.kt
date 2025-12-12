package android.support.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface LongDef {
    Boolean flag() default false

    Array<Long> value() default {}
}
