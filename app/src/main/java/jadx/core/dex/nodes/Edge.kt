package jadx.core.dex.nodes

class Edge {
    private final BlockNode source
    private final BlockNode target

    constructor(BlockNode blockNode, BlockNode blockNode2) {
        this.source = blockNode
        this.target = blockNode2
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        Edge edge = (Edge) obj
        return this.source.equals(edge.source) && this.target.equals(edge.target)
    }

    fun getSource() {
        return this.source
    }

    fun getTarget() {
        return this.target
    }

    fun hashCode() {
        return this.source.hashCode() + (this.target.hashCode() * 31)
    }

    fun toString() {
        return "Edge: " + this.source + " -> " + this.target
    }
}
