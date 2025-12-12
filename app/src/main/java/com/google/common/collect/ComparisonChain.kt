package com.google.common.collect

abstract class ComparisonChain {
    public static val ACTIVE = ComparisonChain() { // from class: com.google.common.collect.ComparisonChain.1
        fun classify(Int i) {
            return i < 0 ? ComparisonChain.LESS : i > 0 ? ComparisonChain.GREATER : ComparisonChain.ACTIVE
        }

        @Override // com.google.common.collect.ComparisonChain
        fun compare(Comparable<?> comparable, Comparable<?> comparable2) {
            return classify(comparable.compareTo(comparable2))
        }

        @Override // com.google.common.collect.ComparisonChain
        fun result() {
            return 0
        }
    }
    public static val LESS = InactiveComparisonChain(-1)
    public static val GREATER = InactiveComparisonChain(1)

    public static final class InactiveComparisonChain extends ComparisonChain {
        public final Int result

        constructor(Int i) {
            super()
            this.result = i
        }

        @Override // com.google.common.collect.ComparisonChain
        fun compare(Comparable<?> comparable, Comparable<?> comparable2) {
            return this
        }

        @Override // com.google.common.collect.ComparisonChain
        fun result() {
            return this.result
        }
    }

    constructor() {
    }

    fun start() {
        return ACTIVE
    }

    public abstract ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2)

    public abstract Int result()
}
