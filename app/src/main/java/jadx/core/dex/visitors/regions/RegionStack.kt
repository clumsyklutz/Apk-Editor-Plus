package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.exceptions.JadxOverflowException
import java.util.ArrayDeque
import java.util.Collection
import java.util.Deque
import java.util.HashSet
import java.util.Iterator
import java.util.Set

final class RegionStack {
    private static val DEBUG = false
    private static val REGIONS_STACK_LIMIT = 1000
    private val stack = ArrayDeque()
    private State curState = State()

    final class State {
        final Set exits
        IRegion region

        constructor() {
            this.exits = HashSet(4)
        }

        private constructor(State state) {
            this.exits = HashSet(state.exits)
        }

        public final State copy() {
            return State(this)
        }

        public final String toString() {
            return "Region: " + this.region + ", exits: " + this.exits
        }
    }

    constructor(MethodNode methodNode) {
    }

    public final Unit addExit(BlockNode blockNode) {
        if (blockNode != null) {
            this.curState.exits.add(blockNode)
        }
    }

    public final Unit addExits(Collection collection) {
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            addExit((BlockNode) it.next())
        }
    }

    public final Boolean containsExit(BlockNode blockNode) {
        return this.curState.exits.contains(blockNode)
    }

    public final IRegion peekRegion() {
        return this.curState.region
    }

    public final Unit pop() {
        this.curState = (State) this.stack.pop()
    }

    public final Unit push(IRegion iRegion) {
        this.stack.push(this.curState)
        if (this.stack.size() > 1000) {
            throw JadxOverflowException("Regions stack size limit reached")
        }
        this.curState = this.curState.copy()
        this.curState.region = iRegion
    }

    public final Unit removeExit(BlockNode blockNode) {
        if (blockNode != null) {
            this.curState.exits.remove(blockNode)
        }
    }

    public final Int size() {
        return this.stack.size()
    }

    public final String toString() {
        return "Region stack size: " + size() + ", last: " + this.curState
    }
}
