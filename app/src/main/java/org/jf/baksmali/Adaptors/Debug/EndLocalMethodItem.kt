package org.jf.baksmali.Adaptors.Debug

import java.io.IOException
import org.jf.baksmali.Adaptors.RegisterFormatter
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.debug.EndLocal

class EndLocalMethodItem extends DebugMethodItem {
    public final EndLocal endLocal
    public final RegisterFormatter registerFormatter

    constructor(Int i, Int i2, RegisterFormatter registerFormatter, EndLocal endLocal) {
        super(i, i2)
        this.endLocal = endLocal
        this.registerFormatter = registerFormatter
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".end local ")
        this.registerFormatter.writeTo(baksmaliWriter, this.endLocal.getRegister())
        String name = this.endLocal.getName()
        String type = this.endLocal.getType()
        String signature = this.endLocal.getSignature()
        if (name == null && type == null && signature == null) {
            return true
        }
        baksmaliWriter.write("    # ")
        LocalFormatter.writeLocal(baksmaliWriter, name, type, signature)
        return true
    }
}
