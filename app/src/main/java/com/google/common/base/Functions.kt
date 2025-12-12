package com.google.common.base

class Functions {

    public enum ToStringFunction implements Function<Object, String> {
        INSTANCE

        @Override // com.google.common.base.Function
        fun apply(Object obj) {
            Preconditions.checkNotNull(obj)
            return obj.toString()
        }

        @Override // java.lang.Enum
        fun toString() {
            return "Functions.toStringFunction()"
        }
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE
    }
}
