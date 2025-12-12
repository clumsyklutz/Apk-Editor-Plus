package android.support.v4.util

import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class DebugUtils {
    private constructor() {
    }

    fun buildShortClassTag(Object obj, StringBuilder sb) {
        Int iLastIndexOf
        if (obj == null) {
            sb.append("null")
            return
        }
        String simpleName = obj.getClass().getSimpleName()
        if ((simpleName == null || simpleName.length() <= 0) && (iLastIndexOf = (simpleName = obj.getClass().getName()).lastIndexOf(46)) > 0) {
            simpleName = simpleName.substring(iLastIndexOf + 1)
        }
        sb.append(simpleName)
        sb.append('{')
        sb.append(Integer.toHexString(System.identityHashCode(obj)))
    }
}
