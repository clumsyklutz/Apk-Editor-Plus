package com.google.common.base

class Present<T> extends Optional<T> {
    public final T reference

    constructor(T t) {
        this.reference = t
    }

    fun equals(Object obj) {
        if (obj is Present) {
            return this.reference.equals(((Present) obj).reference)
        }
        return false
    }

    fun hashCode() {
        return this.reference.hashCode() + 1502476572
    }

    @Override // com.google.common.base.Optional
    fun or(T t) {
        Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)")
        return this.reference
    }

    fun toString() {
        String strValueOf = String.valueOf(this.reference)
        StringBuilder sb = StringBuilder(strValueOf.length() + 13)
        sb.append("Optional.of(")
        sb.append(strValueOf)
        sb.append(")")
        return sb.toString()
    }
}
