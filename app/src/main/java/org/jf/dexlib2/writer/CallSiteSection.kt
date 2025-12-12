package org.jf.dexlib2.writer

import org.jf.dexlib2.iface.reference.CallSiteReference

public interface CallSiteSection<CallSiteKey extends CallSiteReference, EncodedArrayKey> extends IndexSection<CallSiteKey> {
    EncodedArrayKey getEncodedCallSite(CallSiteKey callsitekey)
}
