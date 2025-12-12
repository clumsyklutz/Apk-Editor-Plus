package jadx.core.dex.info

import com.gmail.heagoo.a.c.a

class AccessInfo {
    private final Int accFlags
    private final AFType type

    public enum AFType {
        CLASS,
        FIELD,
        METHOD
    }

    constructor(Int i, AFType aFType) {
        this.accFlags = i
        this.type = aFType
    }

    fun containsFlag(Int i) {
        return (this.accFlags & i) != 0
    }

    fun getType() {
        return this.type
    }

    fun getVisibility() {
        return AccessInfo((this.accFlags & 1) | (this.accFlags & 4) | (this.accFlags & 2), this.type)
    }

    fun isAbstract() {
        return (this.accFlags & 1024) != 0
    }

    fun isAnnotation() {
        return (this.accFlags & 8192) != 0
    }

    fun isBridge() {
        return (this.accFlags & 64) != 0
    }

    fun isConstructor() {
        return (this.accFlags & 65536) != 0
    }

    fun isEnum() {
        return (this.accFlags & 16384) != 0
    }

    fun isFinal() {
        return (this.accFlags & 16) != 0
    }

    fun isInterface() {
        return (this.accFlags & 512) != 0
    }

    fun isNative() {
        return (this.accFlags & 256) != 0
    }

    fun isPrivate() {
        return (this.accFlags & 2) != 0
    }

    fun isProtected() {
        return (this.accFlags & 4) != 0
    }

    fun isPublic() {
        return (this.accFlags & 1) != 0
    }

    fun isStatic() {
        return (this.accFlags & 8) != 0
    }

    fun isSynchronized() {
        return (this.accFlags & 131104) != 0
    }

    fun isSynthetic() {
        return (this.accFlags & 4096) != 0
    }

    fun isTransient() {
        return (this.accFlags & 128) != 0
    }

    fun isVarArgs() {
        return (this.accFlags & 128) != 0
    }

    fun isVolatile() {
        return (this.accFlags & 64) != 0
    }

    fun makeString() {
        StringBuilder sb = StringBuilder()
        if (isPublic()) {
            sb.append("public ")
        }
        if (isPrivate()) {
            sb.append("private ")
        }
        if (isProtected()) {
            sb.append("protected ")
        }
        if (isStatic()) {
            sb.append("static ")
        }
        if (isFinal()) {
            sb.append("final ")
        }
        if (isAbstract()) {
            sb.append("abstract ")
        }
        if (isNative()) {
            sb.append("native ")
        }
        switch (this.type) {
            case METHOD:
                if (isSynchronized()) {
                    sb.append("synchronized ")
                }
                if (isBridge()) {
                    sb.append("/* bridge */ ")
                    break
                }
                break
            case FIELD:
                if (isVolatile()) {
                    sb.append("volatile ")
                }
                if (isTransient()) {
                    sb.append("transient ")
                    break
                }
                break
            case CLASS:
                if ((this.accFlags & 2048) != 0) {
                    sb.append("strict ")
                    break
                }
                break
        }
        if (isSynthetic()) {
            sb.append("/* synthetic */ ")
        }
        return sb.toString()
    }

    fun rawString() {
        switch (this.type) {
            case METHOD:
                return a.j(this.accFlags)
            case FIELD:
                return a.i(this.accFlags)
            case CLASS:
                return a.g(this.accFlags)
            default:
                return "?"
        }
    }

    fun remove(Int i) {
        return containsFlag(i) ? AccessInfo(this.accFlags & (i ^ (-1)), this.type) : this
    }

    fun toString() {
        return "AccessInfo: " + this.type + " 0x" + Integer.toHexString(this.accFlags) + " (" + rawString() + ")"
    }
}
