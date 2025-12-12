package jadx.core.clsp

class NClass {
    private Int id
    private final String name
    private Array<NClass> parents

    constructor(String str, Int i) {
        this.name = str
        this.id = i
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return this.name.equals(((NClass) obj).name)
    }

    fun getId() {
        return this.id
    }

    fun getName() {
        return this.name
    }

    public Array<NClass> getParents() {
        return this.parents
    }

    fun hashCode() {
        return this.name.hashCode()
    }

    fun setId(Int i) {
        this.id = i
    }

    fun setParents(Array<NClass> nClassArr) {
        this.parents = nClassArr
    }

    fun toString() {
        return this.name
    }
}
