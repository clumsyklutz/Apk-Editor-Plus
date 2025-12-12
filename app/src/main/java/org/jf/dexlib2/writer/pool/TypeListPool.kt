package org.jf.dexlib2.writer.pool

import com.google.common.collect.ImmutableList
import java.util.Collection
import java.util.Iterator
import org.jf.dexlib2.writer.TypeListSection

class TypeListPool extends BaseNullableOffsetPool<Key<? extends Collection<? extends CharSequence>>> implements TypeListSection<CharSequence, Key<? extends Collection<? extends CharSequence>>> {

    public static class Key<TypeCollection extends Collection<? extends CharSequence>> implements Comparable<Key<? extends Collection<? extends CharSequence>>> {
        public TypeCollection types

        constructor(TypeCollection typecollection) {
            this.types = typecollection
        }

        @Override // java.lang.Comparable
        fun compareTo(Key<? extends Collection<? extends CharSequence>> key) {
            Iterator it = key.types.iterator()
            for (CharSequence charSequence : this.types) {
                if (!it.hasNext()) {
                    return 1
                }
                Int iCompareTo = charSequence.toString().compareTo(((CharSequence) it.next()).toString())
                if (iCompareTo != 0) {
                    return iCompareTo
                }
            }
            return it.hasNext() ? -1 : 0
        }

        fun equals(Object obj) {
            if (!(obj is Key)) {
                return false
            }
            Key key = (Key) obj
            if (this.types.size() != key.types.size()) {
                return false
            }
            Iterator it = key.types.iterator()
            Iterator it2 = this.types.iterator()
            while (it2.hasNext()) {
                if (!((CharSequence) it2.next()).toString().equals(((CharSequence) it.next()).toString())) {
                    return false
                }
            }
            return true
        }

        fun hashCode() {
            Iterator it = this.types.iterator()
            Int iHashCode = 1
            while (it.hasNext()) {
                iHashCode = (iHashCode * 31) + ((CharSequence) it.next()).toString().hashCode()
            }
            return iHashCode
        }

        fun toString() {
            StringBuilder sb = StringBuilder()
            Iterator it = this.types.iterator()
            while (it.hasNext()) {
                sb.append(((CharSequence) it.next()).toString())
            }
            return sb.toString()
        }
    }

    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.pool.BaseNullableOffsetPool, org.jf.dexlib2.writer.NullableOffsetSection
    fun getNullableItemOffset(Key<? extends Collection<? extends CharSequence>> key) {
        if (key == null || key.types.size() == 0) {
            return 0
        }
        return super.getNullableItemOffset((TypeListPool) key)
    }

    @Override // org.jf.dexlib2.writer.TypeListSection
    public Collection<? extends CharSequence> getTypes(Key<? extends Collection<? extends CharSequence>> key) {
        return key == null ? ImmutableList.of() : key.types
    }

    fun intern(Collection<? extends CharSequence> collection) {
        if (collection.size() > 0) {
            if (((Integer) this.internedItems.put(Key(collection), 0)) == null) {
                Iterator<? extends CharSequence> it = collection.iterator()
                while (it.hasNext()) {
                    ((TypePool) this.dexPool.typeSection).intern(it.next())
                }
            }
        }
    }
}
