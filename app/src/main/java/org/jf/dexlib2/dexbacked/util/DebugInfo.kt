package org.jf.dexlib2.dexbacked.util

import com.google.common.collect.ImmutableSet
import java.util.Arrays
import java.util.Iterator
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexBackedMethod
import org.jf.dexlib2.dexbacked.DexBackedMethodImplementation
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.debug.LocalInfo

abstract class DebugInfo implements Iterable<DebugItem> {

    public static class DebugInfoImpl extends DebugInfo {
        public static val EMPTY_LOCAL_INFO = LocalInfo() { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.1
            @Override // org.jf.dexlib2.iface.debug.LocalInfo
            fun getName() {
                return null
            }

            @Override // org.jf.dexlib2.iface.debug.LocalInfo
            fun getSignature() {
                return null
            }

            @Override // org.jf.dexlib2.iface.debug.LocalInfo
            fun getType() {
                return null
            }
        }
        public final Int debugInfoOffset
        public final DexBackedDexFile dexFile
        public final DexBackedMethodImplementation methodImpl

        constructor(DexBackedDexFile dexBackedDexFile, Int i, DexBackedMethodImplementation dexBackedMethodImplementation) {
            this.dexFile = dexBackedDexFile
            this.debugInfoOffset = i
            this.methodImpl = dexBackedMethodImplementation
        }

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public VariableSizeIterator<String> getParameterNames(DexReader dexReader) {
            if (dexReader == null) {
                dexReader = this.dexFile.getDataBuffer().readerAt(this.debugInfoOffset)
                dexReader.skipUleb128()
            }
            return new VariableSizeIterator<String>(dexReader, dexReader.readSmallUleb128()) { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.4
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeIterator
                fun readNextItem(DexReader dexReader2, Int i) {
                    return DebugInfoImpl.this.dexFile.getStringSection().getOptional(dexReader2.readSmallUleb128() - 1)
                }
            }
        }

        @Override // java.lang.Iterable
        public Iterator<DebugItem> iterator() {
            LocalInfo localInfo
            String type
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.debugInfoOffset)
            Int bigUleb128 = dexReader.readBigUleb128()
            Int registerCount = this.methodImpl.getRegisterCount()
            Array<LocalInfo> localInfoArr = new LocalInfo[registerCount]
            Arrays.fill(localInfoArr, EMPTY_LOCAL_INFO)
            DexBackedMethod dexBackedMethod = this.methodImpl.method
            ParameterIterator parameterIterator = ParameterIterator(dexBackedMethod.getParameterTypes(), dexBackedMethod.getParameterAnnotations(), getParameterNames((DexReader) dexReader))
            Int i = 0
            if (!AccessFlags.STATIC.isSet(this.methodImpl.method.getAccessFlags())) {
                localInfoArr[0] = LocalInfo() { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.2
                    @Override // org.jf.dexlib2.iface.debug.LocalInfo
                    fun getName() {
                        return "this"
                    }

                    @Override // org.jf.dexlib2.iface.debug.LocalInfo
                    fun getSignature() {
                        return null
                    }

                    @Override // org.jf.dexlib2.iface.debug.LocalInfo
                    fun getType() {
                        return DebugInfoImpl.this.methodImpl.method.getDefiningClass()
                    }
                }
                i = 1
            }
            while (parameterIterator.hasNext()) {
                localInfoArr[i] = parameterIterator.next()
                i++
            }
            if (i < registerCount) {
                Int i2 = registerCount - 1
                while (true) {
                    i--
                    if (i <= -1 || ((type = (localInfo = localInfoArr[i]).getType()) != null && ((type.equals("J") || type.equals("D")) && i2 - 1 == i))) {
                        break
                    }
                    localInfoArr[i2] = localInfo
                    localInfoArr[i] = EMPTY_LOCAL_INFO
                    i2--
                }
            }
            return new VariableSizeLookaheadIterator<DebugItem>(this.dexFile.getDataBuffer(), dexReader.getOffset(), bigUleb128, localInfoArr) { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.3
                public Int codeAddress = 0
                public Int lineNumber
                public final /* synthetic */ Int val$lineNumberStart
                public final /* synthetic */ Array<LocalInfo> val$locals

                {
                    this.val$lineNumberStart = bigUleb128
                    this.val$locals = localInfoArr
                    this.lineNumber = bigUleb128
                }

                /* JADX WARN: Removed duplicated region for block: B:17:0x005d  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x008f  */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public org.jf.dexlib2.iface.debug.DebugItem readNextItem(org.jf.dexlib2.dexbacked.DexReader r15) {
                    /*
                        Method dump skipped, instructions count: 386
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw UnsupportedOperationException("Method not decompiled: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.AnonymousClass3.readNextItem(org.jf.dexlib2.dexbacked.DexReader):org.jf.dexlib2.iface.debug.DebugItem")
                }
            }
        }
    }

    public static class EmptyDebugInfo extends DebugInfo {
        public static val INSTANCE = EmptyDebugInfo()

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public Iterator<String> getParameterNames(DexReader dexReader) {
            return ImmutableSet.of().iterator()
        }

        @Override // java.lang.Iterable
        public Iterator<DebugItem> iterator() {
            return ImmutableSet.of().iterator()
        }
    }

    fun newOrEmpty(DexBackedDexFile dexBackedDexFile, Int i, DexBackedMethodImplementation dexBackedMethodImplementation) {
        return i == 0 ? EmptyDebugInfo.INSTANCE : DebugInfoImpl(dexBackedDexFile, i, dexBackedMethodImplementation)
    }

    public abstract Iterator<String> getParameterNames(DexReader dexReader)
}
