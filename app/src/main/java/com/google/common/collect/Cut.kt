package com.google.common.collect

import com.google.common.base.Preconditions
import com.google.common.primitives.Booleans
import java.io.Serializable
import java.lang.Comparable

abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    public final C endpoint

    public static final class AboveAll extends Cut<Comparable<?>> {
        public static val INSTANCE = AboveAll()

        constructor() {
            super("")
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        fun compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : 1
        }

        @Override // com.google.common.collect.Cut
        fun describeAsLowerBound(StringBuilder sb) {
            throw AssertionError()
        }

        @Override // com.google.common.collect.Cut
        fun describeAsUpperBound(StringBuilder sb) {
            sb.append("+∞)")
        }

        @Override // com.google.common.collect.Cut
        fun hashCode() {
            return System.identityHashCode(this)
        }

        @Override // com.google.common.collect.Cut
        fun isLessThan(Comparable<?> comparable) {
            return false
        }

        fun toString() {
            return "+∞"
        }
    }

    public static final class AboveValue<C extends Comparable> extends Cut<C> {
        constructor(C c) {
            super((Comparable) Preconditions.checkNotNull(c))
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public /* bridge */ /* synthetic */ Int compareTo(Object obj) {
            return super.compareTo((Cut) obj)
        }

        @Override // com.google.common.collect.Cut
        fun describeAsLowerBound(StringBuilder sb) {
            sb.append('(')
            sb.append(this.endpoint)
        }

        @Override // com.google.common.collect.Cut
        fun describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint)
            sb.append(']')
        }

        @Override // com.google.common.collect.Cut
        fun hashCode() {
            return this.endpoint.hashCode() ^ (-1)
        }

        @Override // com.google.common.collect.Cut
        fun isLessThan(C c) {
            return Range.compareOrThrow(this.endpoint, c) < 0
        }

        fun toString() {
            String strValueOf = String.valueOf(this.endpoint)
            StringBuilder sb = StringBuilder(strValueOf.length() + 2)
            sb.append("/")
            sb.append(strValueOf)
            sb.append("\\")
            return sb.toString()
        }
    }

    public static final class BelowAll extends Cut<Comparable<?>> {
        public static val INSTANCE = BelowAll()

        constructor() {
            super("")
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        fun compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : -1
        }

        @Override // com.google.common.collect.Cut
        fun describeAsLowerBound(StringBuilder sb) {
            sb.append("(-∞")
        }

        @Override // com.google.common.collect.Cut
        fun describeAsUpperBound(StringBuilder sb) {
            throw AssertionError()
        }

        @Override // com.google.common.collect.Cut
        fun hashCode() {
            return System.identityHashCode(this)
        }

        @Override // com.google.common.collect.Cut
        fun isLessThan(Comparable<?> comparable) {
            return true
        }

        fun toString() {
            return "-∞"
        }
    }

    public static final class BelowValue<C extends Comparable> extends Cut<C> {
        constructor(C c) {
            super((Comparable) Preconditions.checkNotNull(c))
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public /* bridge */ /* synthetic */ Int compareTo(Object obj) {
            return super.compareTo((Cut) obj)
        }

        @Override // com.google.common.collect.Cut
        fun describeAsLowerBound(StringBuilder sb) {
            sb.append('[')
            sb.append(this.endpoint)
        }

        @Override // com.google.common.collect.Cut
        fun describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint)
            sb.append(')')
        }

        @Override // com.google.common.collect.Cut
        fun hashCode() {
            return this.endpoint.hashCode()
        }

        @Override // com.google.common.collect.Cut
        fun isLessThan(C c) {
            return Range.compareOrThrow(this.endpoint, c) <= 0
        }

        fun toString() {
            String strValueOf = String.valueOf(this.endpoint)
            StringBuilder sb = StringBuilder(strValueOf.length() + 2)
            sb.append("\\")
            sb.append(strValueOf)
            sb.append("/")
            return sb.toString()
        }
    }

    constructor(C c) {
        this.endpoint = c
    }

    public static <C extends Comparable> Cut<C> aboveAll() {
        return AboveAll.INSTANCE
    }

    public static <C extends Comparable> Cut<C> aboveValue(C c) {
        return AboveValue(c)
    }

    public static <C extends Comparable> Cut<C> belowAll() {
        return BelowAll.INSTANCE
    }

    public static <C extends Comparable> Cut<C> belowValue(C c) {
        return BelowValue(c)
    }

    @Override // java.lang.Comparable
    fun compareTo(Cut<C> cut) {
        if (cut == belowAll()) {
            return 1
        }
        if (cut == aboveAll()) {
            return -1
        }
        Int iCompareOrThrow = Range.compareOrThrow(this.endpoint, cut.endpoint)
        return iCompareOrThrow != 0 ? iCompareOrThrow : Booleans.compare(this is AboveValue, cut is AboveValue)
    }

    public abstract Unit describeAsLowerBound(StringBuilder sb)

    public abstract Unit describeAsUpperBound(StringBuilder sb)

    fun equals(Object obj) {
        if (!(obj is Cut)) {
            return false
        }
        try {
            return compareTo((Cut) obj) == 0
        } catch (ClassCastException unused) {
            return false
        }
    }

    public abstract Int hashCode()

    public abstract Boolean isLessThan(C c)
}
