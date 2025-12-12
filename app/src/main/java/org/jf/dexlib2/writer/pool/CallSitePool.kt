package org.jf.dexlib2.writer.pool

import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.writer.CallSiteSection
import org.jf.dexlib2.writer.util.CallSiteUtil

class CallSitePool extends BaseIndexPool<CallSiteReference> implements CallSiteSection<CallSiteReference, ArrayEncodedValue> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.CallSiteSection
    fun getEncodedCallSite(CallSiteReference callSiteReference) {
        return CallSiteUtil.getEncodedCallSite(callSiteReference)
    }

    fun intern(CallSiteReference callSiteReference) {
        if (((Integer) this.internedItems.put(callSiteReference, 0)) == null) {
            ((EncodedArrayPool) this.dexPool.encodedArraySection).intern(getEncodedCallSite(callSiteReference))
        }
    }
}
