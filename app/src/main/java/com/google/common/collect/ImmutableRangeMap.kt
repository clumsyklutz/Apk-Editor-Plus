package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableList
import com.google.common.collect.SortedLists
import java.io.Serializable
import java.lang.Comparable
import java.util.Collections
import java.util.List
import java.util.Map

class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
    public final transient ImmutableList<Range<K>> ranges
    public final transient ImmutableList<V> values

    public static final class Builder<K extends Comparable<?>, V> {
        public final List<Map.Entry<Range<K>, V>> entries = Lists.newArrayList()

        public ImmutableRangeMap<K, V> build() {
            Collections.sort(this.entries, Range.rangeLexOrdering().onKeys())
            ImmutableList.Builder builder = new ImmutableList.Builder(this.entries.size())
            ImmutableList.Builder builder2 = new ImmutableList.Builder(this.entries.size())
            for (Int i = 0; i < this.entries.size(); i++) {
                Range<K> key = this.entries.get(i).getKey()
                if (i > 0) {
                    Range<K> key2 = this.entries.get(i - 1).getKey()
                    if (key.isConnected(key2) && !key.intersection(key2).isEmpty()) {
                        String strValueOf = String.valueOf(key2)
                        String strValueOf2 = String.valueOf(key)
                        StringBuilder sb = StringBuilder(strValueOf.length() + 47 + strValueOf2.length())
                        sb.append("Overlapping ranges: range ")
                        sb.append(strValueOf)
                        sb.append(" overlaps with entry ")
                        sb.append(strValueOf2)
                        throw IllegalArgumentException(sb.toString())
                    }
                }
                builder.add((ImmutableList.Builder) key)
                builder2.add((ImmutableList.Builder) this.entries.get(i).getValue())
            }
            return new ImmutableRangeMap<>(builder.build(), builder2.build())
        }

        public Builder<K, V> put(Range<K> range, V v) {
            Preconditions.checkNotNull(range)
            Preconditions.checkNotNull(v)
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range)
            this.entries.add(Maps.immutableEntry(range, v))
            return this
        }
    }

    static {
        ImmutableRangeMap(ImmutableList.of(), ImmutableList.of())
    }

    constructor(ImmutableList<Range<K>> immutableList, ImmutableList<V> immutableList2) {
        this.ranges = immutableList
        this.values = immutableList2
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder<>()
    }

    @Override // com.google.common.collect.RangeMap
    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        return this.ranges.isEmpty() ? ImmutableMap.of() : ImmutableSortedMap(RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering()), this.values)
    }

    fun equals(Object obj) {
        if (obj is RangeMap) {
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges())
        }
        return false
    }

    @Override // com.google.common.collect.RangeMap
    fun get(K k) {
        Int iBinarySearch = SortedLists.binarySearch(this.ranges, (Function<? super E, Cut>) Range.lowerBoundFn(), Cut.belowValue(k), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER)
        if (iBinarySearch != -1 && this.ranges.get(iBinarySearch).contains(k)) {
            return this.values.get(iBinarySearch)
        }
        return null
    }

    fun hashCode() {
        return asMapOfRanges().hashCode()
    }

    fun toString() {
        return asMapOfRanges().toString()
    }
}
