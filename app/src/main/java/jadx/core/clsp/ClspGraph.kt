package jadx.core.clsp

import jadx.core.dex.nodes.ClassNode
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.WeakHashMap
import org.d.b
import org.d.c

class ClspGraph {
    private static val LOG = c.a(ClspGraph.class)
    private val ancestorCache = WeakHashMap()
    private Map nameMap

    private fun addAncestorsNames(NClass nClass, Set set) {
        set.add(nClass.getName())
        for (NClass nClass2 : nClass.getParents()) {
            addAncestorsNames(nClass2, set)
        }
    }

    private fun addClass(ClassNode classNode) {
        String rawName = classNode.getRawName()
        NClass nClass = NClass(rawName, -1)
        this.nameMap.put(rawName, nClass)
        return nClass
    }

    private fun getAncestors(String str) {
        Set set = (Set) this.ancestorCache.get(str)
        if (set != null) {
            return set
        }
        NClass nClass = (NClass) this.nameMap.get(str)
        if (nClass == null) {
            LOG.a("Missing class: {}", str)
            return Collections.emptySet()
        }
        HashSet hashSet = HashSet()
        addAncestorsNames(nClass, hashSet)
        Set setEmptySet = hashSet.isEmpty() ? Collections.emptySet() : hashSet
        this.ancestorCache.put(str, setEmptySet)
        return setEmptySet
    }

    private fun searchCommonParent(Set set, NClass nClass) {
        for (NClass nClass2 : nClass.getParents()) {
            String name = nClass2.getName()
            if (set.contains(name)) {
                return name
            }
            String strSearchCommonParent = searchCommonParent(set, nClass2)
            if (strSearchCommonParent != null) {
                return strSearchCommonParent
            }
        }
        return null
    }

    fun addApp(List list) {
        if (this.nameMap == null) {
            throw JadxRuntimeException("Classpath must be loaded first")
        }
        Int size = list.size()
        Array<NClass> nClassArr = new NClass[size]
        Iterator it = list.iterator()
        Int i = 0
        while (it.hasNext()) {
            nClassArr[i] = addClass((ClassNode) it.next())
            i++
        }
        for (Int i2 = 0; i2 < size; i2++) {
            nClassArr[i2].setParents(ClsSet.makeParentsArray((ClassNode) list.get(i2), this.nameMap))
        }
    }

    fun addClasspath(ClsSet clsSet) {
        if (this.nameMap != null) {
            throw JadxRuntimeException("Classpath already loaded")
        }
        this.nameMap = HashMap(clsSet.getClassesCount())
        clsSet.addToMap(this.nameMap)
    }

    fun getCommonAncestor(String str, String str2) {
        if (str.equals(str2)) {
            return str
        }
        NClass nClass = (NClass) this.nameMap.get(str2)
        if (nClass != null) {
            return isImplements(str, str2) ? str2 : searchCommonParent(getAncestors(str), nClass)
        }
        LOG.a("Missing class: {}", str2)
        return null
    }

    fun isImplements(String str, String str2) {
        return getAncestors(str).contains(str2)
    }

    fun load() {
        ClsSet clsSet = ClsSet()
        clsSet.load()
        addClasspath(clsSet)
    }
}
