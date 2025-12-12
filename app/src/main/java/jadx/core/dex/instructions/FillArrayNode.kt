package jadx.core.dex.instructions

import com.a.b.d.a.g
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.List

class FillArrayNode extends InsnNode {
    private final Object data
    private ArgType elemType
    private final Int size

    constructor(Int i, g gVar) {
        ArgType argTypeUnknown
        super(InsnType.FILL_ARRAY, 0)
        switch (gVar.u()) {
            case 1:
                argTypeUnknown = ArgType.unknown(PrimitiveType.BOOLEAN, PrimitiveType.BYTE)
                break
            case 2:
                argTypeUnknown = ArgType.unknown(PrimitiveType.SHORT, PrimitiveType.CHAR)
                break
            case 3:
            case 5:
            case 6:
            case 7:
            default:
                throw JadxRuntimeException("Unknown array element width: " + ((Int) gVar.u()))
            case 4:
                argTypeUnknown = ArgType.unknown(PrimitiveType.INT, PrimitiveType.FLOAT)
                break
            case 8:
                argTypeUnknown = ArgType.unknown(PrimitiveType.LONG, PrimitiveType.DOUBLE)
                break
        }
        setResult(InsnArg.reg(i, ArgType.array(argTypeUnknown)))
        this.data = gVar.w()
        this.size = gVar.v()
        this.elemType = argTypeUnknown
    }

    public final Object getData() {
        return this.data
    }

    public final ArgType getElementType() {
        return this.elemType
    }

    public final List getLiteralArgs() {
        Int i = 0
        ArrayList arrayList = ArrayList(this.size)
        Object obj = this.data
        if (obj is Array<Int>) {
            Int length = ((Array<Int>) obj).length
            while (i < length) {
                arrayList.add(InsnArg.lit(r0[i], this.elemType))
                i++
            }
        } else if (obj is Array<Byte>) {
            Int length2 = ((Array<Byte>) obj).length
            while (i < length2) {
                arrayList.add(InsnArg.lit(r0[i], this.elemType))
                i++
            }
        } else if (obj is Array<Short>) {
            Int length3 = ((Array<Short>) obj).length
            while (i < length3) {
                arrayList.add(InsnArg.lit(r0[i], this.elemType))
                i++
            }
        } else {
            if (!(obj is Array<Long>)) {
                throw JadxRuntimeException("Unknown type: " + this.data.getClass() + ", expected: " + this.elemType)
            }
            Array<Long> jArr = (Array<Long>) obj
            Int length4 = jArr.length
            while (i < length4) {
                arrayList.add(InsnArg.lit(jArr[i], this.elemType))
                i++
            }
        }
        return arrayList
    }

    public final Int getSize() {
        return this.size
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Boolean isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if (!(insnNode is FillArrayNode) || !super.isSame(insnNode)) {
            return false
        }
        FillArrayNode fillArrayNode = (FillArrayNode) insnNode
        return this.elemType.equals(fillArrayNode.elemType) && this.data == fillArrayNode.data
    }

    public final Unit mergeElementType(DexNode dexNode, ArgType argType) {
        ArgType argTypeMerge = ArgType.merge(dexNode, this.elemType, argType)
        if (argTypeMerge != null) {
            this.elemType = argTypeMerge
        }
    }
}
