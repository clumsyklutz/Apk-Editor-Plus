package jadx.core.dex.attributes

import jadx.core.dex.attributes.annotations.Annotation
import java.util.List

public interface IAttributeNode {
    Unit add(AFlag aFlag)

    Unit addAttr(AType aType, Object obj)

    Unit addAttr(IAttribute iAttribute)

    Unit clearAttributes()

    Boolean contains(AFlag aFlag)

    Boolean contains(AType aType)

    Unit copyAttributesFrom(AttrNode attrNode)

    IAttribute get(AType aType)

    List getAll(AType aType)

    Annotation getAnnotation(String str)

    String getAttributesString()

    List getAttributesStringsList()

    Unit remove(AFlag aFlag)

    Unit remove(AType aType)

    Unit removeAttr(IAttribute iAttribute)
}
