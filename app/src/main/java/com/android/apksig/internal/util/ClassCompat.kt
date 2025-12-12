package com.android.apksig.internal.util

import java.lang.annotation.Annotation

class ClassCompat {
    public static <A extends Annotation> A getDeclaredAnnotation(Class<?> cls, Class<A> cls2) {
        cls2.getClass()
        cls.getClass()
        for (Annotation annotation : cls.getDeclaredAnnotations()) {
            if (cls2.equals(annotation.annotationType())) {
                return cls2.cast(annotation)
            }
        }
        return null
    }
}
