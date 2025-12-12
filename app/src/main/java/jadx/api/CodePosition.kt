package jadx.api

class CodePosition {
    private final Int line
    private final JavaNode node
    private final Int offset

    constructor(Int i, Int i2) {
        this.node = null
        this.line = i
        this.offset = i2
    }

    constructor(JavaNode javaNode, Int i, Int i2) {
        this.node = javaNode
        this.line = i
        this.offset = i2
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        CodePosition codePosition = (CodePosition) obj
        return this.line == codePosition.line && this.offset == codePosition.offset
    }

    public final JavaClass getJavaClass() {
        JavaClass declaringClass = this.node.getDeclaringClass()
        return (declaringClass == null && (this.node is JavaClass)) ? (JavaClass) this.node : declaringClass
    }

    public final Int getLine() {
        return this.line
    }

    public final JavaNode getNode() {
        return this.node
    }

    public final Int getOffset() {
        return this.offset
    }

    public final Int hashCode() {
        return this.line + (this.offset * 31)
    }

    public final String toString() {
        return this.line + ":" + this.offset + (this.node != null ? " " + this.node : "")
    }
}
