package jadx.core.dex.nodes

import java.util.List

public interface IRegion extends IContainer {
    IRegion getParent()

    List getSubBlocks()

    Boolean replaceSubBlock(IContainer iContainer, IContainer iContainer2)
}
