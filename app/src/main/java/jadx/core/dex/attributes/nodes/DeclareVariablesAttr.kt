package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.utils.Utils
import java.util.LinkedList
import java.util.List

class DeclareVariablesAttr implements IAttribute {
    private val vars = LinkedList()

    fun addVar(RegisterArg registerArg) {
        this.vars.add(registerArg)
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.DECLARE_VARIABLES
    }

    fun getVars() {
        return this.vars
    }

    fun toString() {
        return "DECL_VAR: " + Utils.listToString(this.vars)
    }
}
