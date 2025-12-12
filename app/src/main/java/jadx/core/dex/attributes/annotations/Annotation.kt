package jadx.core.dex.attributes.annotations

import jadx.core.dex.instructions.args.ArgType
import java.util.Map

class Annotation {
    private final ArgType atype
    private final Map values
    private final Visibility visibility

    public enum Visibility {
        BUILD,
        RUNTIME,
        SYSTEM
    }

    constructor(Visibility visibility, ArgType argType, Map map) {
        this.visibility = visibility
        this.atype = argType
        this.values = map
    }

    fun getAnnotationClass() {
        return this.atype.getObject()
    }

    fun getDefaultValue() {
        return this.values.get("value")
    }

    fun getType() {
        return this.atype
    }

    fun getValues() {
        return this.values
    }

    fun getVisibility() {
        return this.visibility
    }

    fun toString() {
        return "Annotation[" + this.visibility + ", " + this.atype + ", " + this.values + "]"
    }
}
