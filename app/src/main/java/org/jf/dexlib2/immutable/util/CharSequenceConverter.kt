package org.jf.dexlib2.immutable.util

import com.google.common.collect.ImmutableList
import org.jf.util.ImmutableConverter

class CharSequenceConverter {
    public static final ImmutableConverter<String, CharSequence> CONVERTER = new ImmutableConverter<String, CharSequence>() { // from class: org.jf.dexlib2.immutable.util.CharSequenceConverter.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(CharSequence charSequence) {
            return charSequence is String
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(CharSequence charSequence) {
            return charSequence.toString()
        }
    }

    public static ImmutableList<String> immutableStringList(Iterable<? extends CharSequence> iterable) {
        return CONVERTER.toList(iterable)
    }
}
