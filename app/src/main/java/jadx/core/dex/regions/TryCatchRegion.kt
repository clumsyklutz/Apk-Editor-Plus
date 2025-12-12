package jadx.core.dex.regions

import jadx.core.dex.nodes.IBranchRegion
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.TryCatchBlock
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashMap
import java.util.List
import java.util.Map

class TryCatchRegion extends AbstractRegion implements IBranchRegion {
    private Map catchRegions
    private IContainer finallyRegion
    private TryCatchBlock tryCatchBlock
    private final IContainer tryRegion

    constructor(IRegion iRegion, IContainer iContainer) {
        super(iRegion)
        this.catchRegions = Collections.emptyMap()
        this.tryRegion = iContainer
    }

    @Override // jadx.core.dex.nodes.IContainer
    public final String baseString() {
        return this.tryRegion.baseString()
    }

    @Override // jadx.core.dex.nodes.IBranchRegion
    public final List getBranches() {
        return getSubBlocks()
    }

    public final Map getCatchRegions() {
        return this.catchRegions
    }

    public final IContainer getFinallyRegion() {
        return this.finallyRegion
    }

    @Override // jadx.core.dex.nodes.IRegion
    public final List getSubBlocks() {
        ArrayList arrayList = ArrayList(this.catchRegions.size() + 2)
        arrayList.add(this.tryRegion)
        arrayList.addAll(this.catchRegions.values())
        if (this.finallyRegion != null) {
            arrayList.add(this.finallyRegion)
        }
        return Collections.unmodifiableList(arrayList)
    }

    public final TryCatchBlock getTryCatchBlock() {
        return this.tryCatchBlock
    }

    public final IContainer getTryRegion() {
        return this.tryRegion
    }

    public final Unit setFinallyRegion(IContainer iContainer) {
        this.finallyRegion = iContainer
    }

    public final Unit setTryCatchBlock(TryCatchBlock tryCatchBlock) {
        this.tryCatchBlock = tryCatchBlock
        this.catchRegions = LinkedHashMap(tryCatchBlock.getHandlersCount())
        for (ExceptionHandler exceptionHandler : tryCatchBlock.getHandlers()) {
            IContainer handlerRegion = exceptionHandler.getHandlerRegion()
            if (handlerRegion != null) {
                if (exceptionHandler.isFinally()) {
                    this.finallyRegion = handlerRegion
                } else {
                    this.catchRegions.put(exceptionHandler, handlerRegion)
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append("Try: ").append(this.tryRegion)
        if (!this.catchRegions.isEmpty()) {
            sb.append(" catches: ").append(Utils.listToString(this.catchRegions.values()))
        }
        if (this.finallyRegion != null) {
            sb.append(" finally: ").append(this.finallyRegion)
        }
        return sb.toString()
    }
}
