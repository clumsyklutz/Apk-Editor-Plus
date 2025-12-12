package org.jf.dexlib2.dexbacked.reference

import com.google.common.collect.Lists
import java.util.ArrayList
import java.util.List
import org.jf.dexlib2.base.reference.BaseCallSiteReference
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.MethodHandleEncodedValue
import org.jf.dexlib2.iface.value.MethodTypeEncodedValue
import org.jf.dexlib2.iface.value.StringEncodedValue
import org.jf.util.ExceptionWithContext

class DexBackedCallSiteReference extends BaseCallSiteReference {
    public final Int callSiteIdOffset
    public final Int callSiteIndex
    public Int callSiteOffset = -1
    public final DexBackedDexFile dexFile

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.callSiteIndex = i
        this.callSiteIdOffset = dexBackedDexFile.getCallSiteSection().getOffset(i)
    }

    public final EncodedArrayItemIterator getCallSiteIterator() {
        return EncodedArrayItemIterator.newOrEmpty(this.dexFile, getCallSiteOffset())
    }

    public final Int getCallSiteOffset() {
        if (this.callSiteOffset < 0) {
            this.callSiteOffset = this.dexFile.getBuffer().readSmallUint(this.callSiteIdOffset)
        }
        return this.callSiteOffset
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public List<? extends EncodedValue> getExtraArguments() {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        EncodedArrayItemIterator callSiteIterator = getCallSiteIterator()
        if (callSiteIterator.getItemCount() < 3) {
            throw ExceptionWithContext("Invalid call site item: must contain at least 3 entries.", new Object[0])
        }
        if (callSiteIterator.getItemCount() == 3) {
            return arrayListNewArrayList
        }
        callSiteIterator.skipNext()
        callSiteIterator.skipNext()
        callSiteIterator.skipNext()
        for (EncodedValue nextOrNull = callSiteIterator.getNextOrNull(); nextOrNull != null; nextOrNull = callSiteIterator.getNextOrNull()) {
            arrayListNewArrayList.add(nextOrNull)
        }
        return arrayListNewArrayList
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodHandle() {
        if (getCallSiteIterator().getItemCount() < 3) {
            throw ExceptionWithContext("Invalid call site item: must contain at least 3 entries.", new Object[0])
        }
        EncodedValue nextOrNull = getCallSiteIterator().getNextOrNull()
        if (nextOrNull.getValueType() == 22) {
            return ((MethodHandleEncodedValue) nextOrNull).getValue()
        }
        throw ExceptionWithContext("Invalid encoded value type (%d) for the first item in call site %d", Integer.valueOf(nextOrNull.getValueType()), Integer.valueOf(this.callSiteIndex))
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodName() {
        EncodedArrayItemIterator callSiteIterator = getCallSiteIterator()
        if (callSiteIterator.getItemCount() < 3) {
            throw ExceptionWithContext("Invalid call site item: must contain at least 3 entries.", new Object[0])
        }
        callSiteIterator.skipNext()
        EncodedValue nextOrNull = callSiteIterator.getNextOrNull()
        if (nextOrNull.getValueType() == 23) {
            return ((StringEncodedValue) nextOrNull).getValue()
        }
        throw ExceptionWithContext("Invalid encoded value type (%d) for the second item in call site %d", Integer.valueOf(nextOrNull.getValueType()), Integer.valueOf(this.callSiteIndex))
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodProto() {
        EncodedArrayItemIterator callSiteIterator = getCallSiteIterator()
        if (callSiteIterator.getItemCount() < 3) {
            throw ExceptionWithContext("Invalid call site item: must contain at least 3 entries.", new Object[0])
        }
        callSiteIterator.skipNext()
        callSiteIterator.skipNext()
        EncodedValue nextOrNull = callSiteIterator.getNextOrNull()
        if (nextOrNull.getValueType() == 21) {
            return ((MethodTypeEncodedValue) nextOrNull).getValue()
        }
        throw ExceptionWithContext("Invalid encoded value type (%d) for the second item in call site %d", Integer.valueOf(nextOrNull.getValueType()), Integer.valueOf(this.callSiteIndex))
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getName() {
        return String.format("call_site_%d", Integer.valueOf(this.callSiteIndex))
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
        Int i = this.callSiteIndex
        if (i < 0 || i >= this.dexFile.getCallSiteSection().size()) {
            throw new Reference.InvalidReferenceException("callsite@" + this.callSiteIndex)
        }
    }
}
