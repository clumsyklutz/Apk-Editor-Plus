package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.utils.Utils

class JadxErrorAttr implements IAttribute {
    private final Throwable cause

    constructor(Throwable th) {
        this.cause = th
    }

    fun getCause() {
        return this.cause
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.JADX_ERROR
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append("JadxError: ")
        if (this.cause == null) {
            sb.append("null")
        } else {
            sb.append(this.cause.getClass())
            sb.append(":")
            sb.append(this.cause.getMessage())
            sb.append("\n")
            sb.append(Utils.getStackTrace(this.cause))
        }
        return sb.toString()
    }
}
