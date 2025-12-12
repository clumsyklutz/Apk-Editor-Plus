package org.jf.dexlib2.base.reference

import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.reference.CallSiteReference

abstract class BaseCallSiteReference extends BaseReference implements CallSiteReference {
    fun equals(Object obj) {
        if (obj == null || !(obj is CallSiteReference)) {
            return false
        }
        CallSiteReference callSiteReference = (CallSiteReference) obj
        return getMethodHandle().equals(callSiteReference.getMethodHandle()) && getMethodName().equals(callSiteReference.getMethodName()) && getMethodProto().equals(callSiteReference.getMethodProto()) && getExtraArguments().equals(callSiteReference.getExtraArguments())
    }

    fun hashCode() {
        return (((((((getName().hashCode() * 31) + getMethodHandle().hashCode()) * 31) + getMethodName().hashCode()) * 31) + getMethodProto().hashCode()) * 31) + getExtraArguments().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getCallSite(this)
    }
}
