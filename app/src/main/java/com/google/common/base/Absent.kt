package com.google.common.base

class Absent<T> extends Optional<T> {
    public static final Absent<Object> INSTANCE = new Absent<>()

    public static <T> Optional<T> withType() {
        return INSTANCE
    }

    fun equals(Object obj) {
        return obj == this
    }

    fun hashCode() {
        return 2040732332
    }

    @Override // com.google.common.base.Optional
    fun or(T t) {
        return (T) Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)")
    }

    fun toString() {
        return "Optional.absent()"
    }
}
