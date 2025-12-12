package org.jf.dexlib2.dexbacked.util

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import java.util.List
import java.util.Set
import org.jf.dexlib2.dexbacked.DexBackedAnnotation
import org.jf.dexlib2.dexbacked.DexBackedDexFile

abstract class AnnotationsDirectory {
    public static val EMPTY = AnnotationsDirectory() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.1
        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public Set<? extends DexBackedAnnotation> getClassAnnotations() {
            return ImmutableSet.of()
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        fun getFieldAnnotationIterator() {
            return AnnotationIterator.EMPTY
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        fun getMethodAnnotationIterator() {
            return AnnotationIterator.EMPTY
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        fun getParameterAnnotationIterator() {
            return AnnotationIterator.EMPTY
        }
    }

    public interface AnnotationIterator {
        public static val EMPTY = AnnotationIterator() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator.1
            @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator
            fun seekTo(Int i) {
                return 0
            }
        }

        Int seekTo(Int i)
    }

    public static class AnnotationsDirectoryImpl extends AnnotationsDirectory {
        public final DexBackedDexFile dexFile
        public final Int directoryOffset

        class AnnotationIteratorImpl implements AnnotationIterator {
            public Int currentIndex = 0
            public Int currentItemIndex
            public final Int size
            public final Int startOffset

            constructor(Int i, Int i2) {
                this.startOffset = i
                this.size = i2
                this.currentItemIndex = AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(i)
            }

            @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator
            fun seekTo(Int i) {
                Int i2
                while (true) {
                    i2 = this.currentItemIndex
                    if (i2 >= i) {
                        break
                    }
                    Int i3 = this.currentIndex
                    if (i3 + 1 >= this.size) {
                        break
                    }
                    this.currentIndex = i3 + 1
                    this.currentItemIndex = AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(this.startOffset + (this.currentIndex * 8))
                }
                if (i2 == i) {
                    return AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(this.startOffset + (this.currentIndex * 8) + 4)
                }
                return 0
            }
        }

        constructor(DexBackedDexFile dexBackedDexFile, Int i) {
            this.dexFile = dexBackedDexFile
            this.directoryOffset = i
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public Set<? extends DexBackedAnnotation> getClassAnnotations() {
            DexBackedDexFile dexBackedDexFile = this.dexFile
            return AnnotationsDirectory.getAnnotations(dexBackedDexFile, dexBackedDexFile.getDataBuffer().readSmallUint(this.directoryOffset))
        }

        fun getFieldAnnotationCount() {
            return this.dexFile.getDataBuffer().readSmallUint(this.directoryOffset + 4)
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        fun getFieldAnnotationIterator() {
            Int fieldAnnotationCount = getFieldAnnotationCount()
            return fieldAnnotationCount == 0 ? AnnotationIterator.EMPTY : AnnotationIteratorImpl(this.directoryOffset + 16, fieldAnnotationCount)
        }

        fun getMethodAnnotationCount() {
            return this.dexFile.getDataBuffer().readSmallUint(this.directoryOffset + 8)
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        fun getMethodAnnotationIterator() {
            Int methodAnnotationCount = getMethodAnnotationCount()
            if (methodAnnotationCount == 0) {
                return AnnotationIterator.EMPTY
            }
            return AnnotationIteratorImpl(this.directoryOffset + 16 + (getFieldAnnotationCount() * 8), methodAnnotationCount)
        }

        fun getParameterAnnotationCount() {
            return this.dexFile.getDataBuffer().readSmallUint(this.directoryOffset + 12)
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        fun getParameterAnnotationIterator() {
            Int parameterAnnotationCount = getParameterAnnotationCount()
            if (parameterAnnotationCount == 0) {
                return AnnotationIterator.EMPTY
            }
            return AnnotationIteratorImpl(this.directoryOffset + 16 + (getFieldAnnotationCount() * 8) + (getMethodAnnotationCount() * 8), parameterAnnotationCount)
        }
    }

    public static Set<? extends DexBackedAnnotation> getAnnotations(final DexBackedDexFile dexBackedDexFile, final Int i) {
        if (i == 0) {
            return ImmutableSet.of()
        }
        val smallUint = dexBackedDexFile.getDataBuffer().readSmallUint(i)
        return new FixedSizeSet<DexBackedAnnotation>() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.2
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeSet
            fun readItem(Int i2) {
                return DexBackedAnnotation(dexBackedDexFile, dexBackedDexFile.getDataBuffer().readSmallUint(i + 4 + (i2 * 4)))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            fun size() {
                return smallUint
            }
        }
    }

    public static List<Set<? extends DexBackedAnnotation>> getParameterAnnotations(final DexBackedDexFile dexBackedDexFile, final Int i) {
        if (i <= 0) {
            return ImmutableList.of()
        }
        val smallUint = dexBackedDexFile.getDataBuffer().readSmallUint(i)
        return new FixedSizeList<Set<? extends DexBackedAnnotation>>() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.3
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            public Set<? extends DexBackedAnnotation> readItem(Int i2) {
                return AnnotationsDirectory.getAnnotations(dexBackedDexFile, dexBackedDexFile.getDataBuffer().readSmallUint(i + 4 + (i2 * 4)))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return smallUint
            }
        }
    }

    fun newOrEmpty(DexBackedDexFile dexBackedDexFile, Int i) {
        return i == 0 ? EMPTY : AnnotationsDirectoryImpl(dexBackedDexFile, i)
    }

    public abstract Set<? extends DexBackedAnnotation> getClassAnnotations()

    public abstract AnnotationIterator getFieldAnnotationIterator()

    public abstract AnnotationIterator getMethodAnnotationIterator()

    public abstract AnnotationIterator getParameterAnnotationIterator()
}
