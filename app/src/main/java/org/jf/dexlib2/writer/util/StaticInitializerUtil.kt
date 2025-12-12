package org.jf.dexlib2.writer.util

import com.google.common.base.Function
import com.google.common.base.Predicate
import com.google.common.collect.FluentIterable
import java.util.Iterator
import java.util.List
import java.util.SortedSet
import org.jf.dexlib2.base.value.BaseArrayEncodedValue
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory
import org.jf.dexlib2.util.EncodedValueUtils
import org.jf.util.AbstractForwardSequentialList
import org.jf.util.CollectionUtils

class StaticInitializerUtil {
    public static final Predicate<Field> HAS_INITIALIZER = new Predicate<Field>() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.2
        @Override // com.google.common.base.Predicate
        fun apply(Field field) {
            EncodedValue initialValue = field.getInitialValue()
            return (initialValue == null || EncodedValueUtils.isDefaultValue(initialValue)) ? false : true
        }
    }
    public static final Function<Field, EncodedValue> GET_INITIAL_VALUE = new Function<Field, EncodedValue>() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.3
        @Override // com.google.common.base.Function
        fun apply(Field field) {
            EncodedValue initialValue = field.getInitialValue()
            return initialValue == null ? ImmutableEncodedValueFactory.defaultValueForType(field.getType()) : initialValue
        }
    }

    fun getStaticInitializers(final SortedSet<? extends Field> sortedSet) {
        val iLastIndexOf = CollectionUtils.lastIndexOf(sortedSet, HAS_INITIALIZER)
        if (iLastIndexOf > -1) {
            return BaseArrayEncodedValue() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.1
                @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
                public List<? extends EncodedValue> getValue() {
                    return new AbstractForwardSequentialList<EncodedValue>() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.1.1
                        @Override // org.jf.util.AbstractForwardSequentialList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
                        public Iterator<EncodedValue> iterator() {
                            return FluentIterable.from(sortedSet).limit(iLastIndexOf + 1).transform(StaticInitializerUtil.GET_INITIAL_VALUE).iterator()
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        fun size() {
                            return iLastIndexOf + 1
                        }
                    }
                }
            }
        }
        return null
    }
}
