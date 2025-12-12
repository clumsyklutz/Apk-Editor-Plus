package jadx.core.utils

import com.a.b.d.a.f
import jadx.core.dex.attributes.AType
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.ConstClassNode
import jadx.core.dex.instructions.ConstStringNode
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.parser.FieldInitAttr
import jadx.core.utils.exceptions.JadxRuntimeException
import org.d.b
import org.d.c

class InsnUtils {
    private static val LOG = c.a(InsnUtils.class)

    private constructor() {
    }

    fun formatOffset(Int i) {
        return i < 0 ? "?" : String.format("0x%04x", Integer.valueOf(i))
    }

    fun getArg(f fVar, Int i) {
        switch (i) {
            case 0:
                return fVar.n()
            case 1:
                return fVar.o()
            case 2:
                return fVar.p()
            case 3:
                return fVar.q()
            case 4:
                return fVar.r()
            default:
                throw JadxRuntimeException("Wrong argument number: " + i)
        }
    }

    fun getConstValueByInsn(DexNode dexNode, InsnNode insnNode) {
        switch (insnNode.getType()) {
            case CONST:
                return insnNode.getArg(0)
            case CONST_STR:
                return ((ConstStringNode) insnNode).getString()
            case CONST_CLASS:
                return ((ConstClassNode) insnNode).getClsType()
            case SGET:
                FieldInfo fieldInfo = (FieldInfo) ((IndexInsnNode) insnNode).getIndex()
                FieldNode fieldNodeResolveField = dexNode.resolveField(fieldInfo)
                if (fieldNodeResolveField != null) {
                    FieldInitAttr fieldInitAttr = (FieldInitAttr) fieldNodeResolveField.get(AType.FIELD_INIT)
                    if (fieldInitAttr != null) {
                        return fieldInitAttr.getValue()
                    }
                } else {
                    LOG.b("Field {} not found in dex {}", fieldInfo, dexNode)
                }
            default:
                return null
        }
    }

    fun indexToString(Object obj) {
        return obj == null ? "" : obj is String ? "\"" + obj + "\"" : obj.toString()
    }

    fun insnTypeToString(InsnType insnType) {
        return insnType.toString() + "  "
    }
}
