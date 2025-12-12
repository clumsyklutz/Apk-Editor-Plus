package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Preconditions
import com.google.common.base.Predicate
import java.io.Serializable
import java.lang.Comparable

/* JADX WARN: Unexpected interfaces in signature: [java.io.Serializable] */
class Range<C extends Comparable> extends RangeGwtSerializationDependencies implements Predicate<C> {
    public static final Range<Comparable> ALL = new Range<>(Cut.belowAll(), Cut.aboveAll())
    public final Cut<C> lowerBound
    public final Cut<C> upperBound

    public static class LowerBoundFn implements Function<Range, Cut> {
        public static val INSTANCE = LowerBoundFn()

        @Override // com.google.common.base.Function
        fun apply(Range range) {
            return range.lowerBound
        }
    }

    public static class RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
        public static final Ordering<Range<?>> INSTANCE = RangeLexOrdering()

        @Override // com.google.common.collect.Ordering, java.util.Comparator
        fun compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare(range.lowerBound, range2.lowerBound).compare(range.upperBound, range2.upperBound).result()
        }
    }

    constructor(Cut<C> cut, Cut<C> cut2) {
        this.lowerBound = (Cut) Preconditions.checkNotNull(cut)
        this.upperBound = (Cut) Preconditions.checkNotNull(cut2)
        if (cut.compareTo((Cut) cut2) > 0 || cut == Cut.aboveAll() || cut2 == Cut.belowAll()) {
            String strValueOf = String.valueOf(toString(cut, cut2))
            throw IllegalArgumentException(strValueOf.length() != 0 ? "Invalid range: ".concat(strValueOf) : String("Invalid range: "))
        }
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return (Range<C>) ALL
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c) {
        return create(Cut.belowValue(c), Cut.aboveAll())
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c) {
        return create(Cut.belowAll(), Cut.aboveValue(c))
    }

    public static <C extends Comparable<?>> Range<C> closed(C c, C c2) {
        return create(Cut.belowValue(c), Cut.aboveValue(c2))
    }

    fun compareOrThrow(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2)
    }

    public static <C extends Comparable<?>> Range<C> create(Cut<C> cut, Cut<C> cut2) {
        return new Range<>(cut, cut2)
    }

    public static <C extends Comparable<?>> Function<Range<C>, Cut<C>> lowerBoundFn() {
        return LowerBoundFn.INSTANCE
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c, C c2) {
        return create(Cut.aboveValue(c), Cut.aboveValue(c2))
    }

    public static <C extends Comparable<?>> Ordering<Range<C>> rangeLexOrdering() {
        return (Ordering<Range<C>>) RangeLexOrdering.INSTANCE
    }

    fun toString(Cut<?> cut, Cut<?> cut2) {
        StringBuilder sb = StringBuilder(16)
        cut.describeAsLowerBound(sb)
        sb.append("..")
        cut2.describeAsUpperBound(sb)
        return sb.toString()
    }

    @Override // com.google.common.base.Predicate
    @Deprecated
    fun apply(C c) {
        return contains(c)
    }

    fun contains(C c) {
        Preconditions.checkNotNull(c)
        return this.lowerBound.isLessThan(c) && !this.upperBound.isLessThan(c)
    }

    fun equals(Object obj) {
        if (!(obj is Range)) {
            return false
        }
        Range range = (Range) obj
        return this.lowerBound.equals(range.lowerBound) && this.upperBound.equals(range.upperBound)
    }

    fun hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode()
    }

    public Range<C> intersection(Range<C> range) {
        Int iCompareTo = this.lowerBound.compareTo((Cut) range.lowerBound)
        Int iCompareTo2 = this.upperBound.compareTo((Cut) range.upperBound)
        if (iCompareTo >= 0 && iCompareTo2 <= 0) {
            return this
        }
        if (iCompareTo > 0 || iCompareTo2 < 0) {
            return create(iCompareTo >= 0 ? this.lowerBound : range.lowerBound, iCompareTo2 <= 0 ? this.upperBound : range.upperBound)
        }
        return range
    }

    fun isConnected(Range<C> range) {
        return this.lowerBound.compareTo((Cut) range.upperBound) <= 0 && range.lowerBound.compareTo((Cut) this.upperBound) <= 0
    }

    fun isEmpty() {
        return this.lowerBound.equals(this.upperBound)
    }

    fun toString() {
        return toString(this.lowerBound, this.upperBound)
    }
}
