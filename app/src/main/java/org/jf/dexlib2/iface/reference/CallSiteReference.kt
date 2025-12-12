package org.jf.dexlib2.iface.reference

import java.util.List
import org.jf.dexlib2.iface.value.EncodedValue

public interface CallSiteReference extends Reference {
    List<? extends EncodedValue> getExtraArguments()

    MethodHandleReference getMethodHandle()

    String getMethodName()

    MethodProtoReference getMethodProto()

    String getName()
}
