package android.arch.lifecycle

import android.support.annotation.RestrictTo
import java.util.HashMap
import java.util.Map

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MethodCallsLogger {
    private Map mCalledMethods = HashMap()

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun approveCall(String str, Int i) {
        Integer num = (Integer) this.mCalledMethods.get(str)
        Int iIntValue = num != null ? num.intValue() : 0
        Boolean z = (iIntValue & i) != 0
        this.mCalledMethods.put(str, Integer.valueOf(iIntValue | i))
        return !z
    }
}
