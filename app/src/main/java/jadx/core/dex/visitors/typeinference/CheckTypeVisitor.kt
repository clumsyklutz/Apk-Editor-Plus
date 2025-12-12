package jadx.core.dex.visitors.typeinference

import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.ErrorsCounter
import java.util.Iterator

class CheckTypeVisitor {
    private fun error(String str, MethodNode methodNode, InsnNode insnNode) {
        ErrorsCounter.methodError(methodNode, str + ": " + insnNode)
    }

    fun visit(MethodNode methodNode, InsnNode insnNode) {
        if (insnNode.getResult() != null && !insnNode.getResult().getType().isTypeKnown()) {
            error("Wrong return type", methodNode, insnNode)
            return
        }
        Iterator it = insnNode.getArguments().iterator()
        while (it.hasNext()) {
            if (!((InsnArg) it.next()).getType().isTypeKnown()) {
                error("Wrong type", methodNode, insnNode)
                return
            }
        }
    }
}
