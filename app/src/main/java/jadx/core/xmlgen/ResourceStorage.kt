package jadx.core.xmlgen

import jadx.core.utils.Utils
import jadx.core.xmlgen.entry.ResourceEntry
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List
import java.util.Map

class ResourceStorage {
    private static val COMPARATOR = Comparator() { // from class: jadx.core.xmlgen.ResourceStorage.1
        @Override // java.util.Comparator
        public final Int compare(ResourceEntry resourceEntry, ResourceEntry resourceEntry2) {
            return Utils.compare(resourceEntry.getId(), resourceEntry2.getId())
        }
    }
    private String appPackage
    private val list = ArrayList()

    fun add(ResourceEntry resourceEntry) {
        this.list.add(resourceEntry)
    }

    fun finish() {
        Collections.sort(this.list, COMPARATOR)
    }

    fun getAppPackage() {
        return this.appPackage
    }

    fun getByRef(Int i) {
        Int iBinarySearch = Collections.binarySearch(this.list, ResourceEntry(i), COMPARATOR)
        if (iBinarySearch < 0) {
            return null
        }
        return (ResourceEntry) this.list.get(iBinarySearch)
    }

    fun getResources() {
        return this.list
    }

    fun getResourcesNames() {
        HashMap map = HashMap()
        for (ResourceEntry resourceEntry : this.list) {
            map.put(Integer.valueOf(resourceEntry.getId()), resourceEntry.getTypeName() + "/" + resourceEntry.getKeyName())
        }
        return map
    }

    fun setAppPackage(String str) {
        this.appPackage = str
    }
}
