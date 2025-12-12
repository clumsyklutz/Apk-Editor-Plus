package org.jf.dexlib2.writer.util

import com.google.common.collect.Lists
import java.util.ArrayList
import java.util.List
import org.jf.dexlib2.base.value.BaseArrayEncodedValue
import org.jf.dexlib2.base.value.BaseMethodHandleEncodedValue
import org.jf.dexlib2.base.value.BaseMethodTypeEncodedValue
import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableStringEncodedValue

class CallSiteUtil {
    fun getEncodedCallSite(final CallSiteReference callSiteReference) {
        return BaseArrayEncodedValue() { // from class: org.jf.dexlib2.writer.util.CallSiteUtil.1
            @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
            public List<? extends EncodedValue> getValue() {
                ArrayList arrayListNewArrayList = Lists.newArrayList()
                arrayListNewArrayList.add(BaseMethodHandleEncodedValue() { // from class: org.jf.dexlib2.writer.util.CallSiteUtil.1.1
                    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
                    fun getValue() {
                        return callSiteReference.getMethodHandle()
                    }
                })
                arrayListNewArrayList.add(ImmutableStringEncodedValue(callSiteReference.getMethodName()))
                arrayListNewArrayList.add(BaseMethodTypeEncodedValue() { // from class: org.jf.dexlib2.writer.util.CallSiteUtil.1.2
                    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
                    fun getValue() {
                        return callSiteReference.getMethodProto()
                    }
                })
                arrayListNewArrayList.addAll(callSiteReference.getExtraArguments())
                return arrayListNewArrayList
            }
        }
    }
}
