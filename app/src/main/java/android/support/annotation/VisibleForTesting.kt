package android.support.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.CLASS)
public @interface VisibleForTesting {
    public static val NONE = 5
    public static val PACKAGE_PRIVATE = 3
    public static val PRIVATE = 2
    public static val PROTECTED = 4

    Int otherwise() default 2
}
