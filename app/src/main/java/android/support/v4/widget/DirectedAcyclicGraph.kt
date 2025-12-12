package android.support.v4.widget

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.util.Pools
import android.support.v4.util.SimpleArrayMap
import java.util.ArrayList
import java.util.HashSet
import java.util.List

@RestrictTo({RestrictTo.Scope.LIBRARY})
class DirectedAcyclicGraph {
    private final Pools.Pool mListPool = new Pools.SimplePool(10)
    private val mGraph = SimpleArrayMap()
    private val mSortResult = ArrayList()
    private val mSortTmpMarked = HashSet()

    private fun dfs(Object obj, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(obj)) {
            return
        }
        if (hashSet.contains(obj)) {
            throw RuntimeException("This graph contains cyclic dependencies")
        }
        hashSet.add(obj)
        ArrayList arrayList2 = (ArrayList) this.mGraph.get(obj)
        if (arrayList2 != null) {
            Int size = arrayList2.size()
            for (Int i = 0; i < size; i++) {
                dfs(arrayList2.get(i), arrayList, hashSet)
            }
        }
        hashSet.remove(obj)
        arrayList.add(obj)
    }

    @NonNull
    private fun getEmptyList() {
        ArrayList arrayList = (ArrayList) this.mListPool.acquire()
        return arrayList == null ? ArrayList() : arrayList
    }

    private fun poolList(@NonNull ArrayList arrayList) {
        arrayList.clear()
        this.mListPool.release(arrayList)
    }

    public final Unit addEdge(@NonNull Object obj, @NonNull Object obj2) {
        if (!this.mGraph.containsKey(obj) || !this.mGraph.containsKey(obj2)) {
            throw IllegalArgumentException("All nodes must be present in the graph before being added as an edge")
        }
        ArrayList emptyList = (ArrayList) this.mGraph.get(obj)
        if (emptyList == null) {
            emptyList = getEmptyList()
            this.mGraph.put(obj, emptyList)
        }
        emptyList.add(obj2)
    }

    public final Unit addNode(@NonNull Object obj) {
        if (this.mGraph.containsKey(obj)) {
            return
        }
        this.mGraph.put(obj, null)
    }

    public final Unit clear() {
        Int size = this.mGraph.size()
        for (Int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i)
            if (arrayList != null) {
                poolList(arrayList)
            }
        }
        this.mGraph.clear()
    }

    public final Boolean contains(@NonNull Object obj) {
        return this.mGraph.containsKey(obj)
    }

    @Nullable
    public final List getIncomingEdges(@NonNull Object obj) {
        return (List) this.mGraph.get(obj)
    }

    @Nullable
    public final List getOutgoingEdges(@NonNull Object obj) {
        ArrayList arrayList = null
        Int size = this.mGraph.size()
        for (Int i = 0; i < size; i++) {
            ArrayList arrayList2 = (ArrayList) this.mGraph.valueAt(i)
            if (arrayList2 != null && arrayList2.contains(obj)) {
                ArrayList arrayList3 = arrayList == null ? ArrayList() : arrayList
                arrayList3.add(this.mGraph.keyAt(i))
                arrayList = arrayList3
            }
        }
        return arrayList
    }

    @NonNull
    public final ArrayList getSortedList() {
        this.mSortResult.clear()
        this.mSortTmpMarked.clear()
        Int size = this.mGraph.size()
        for (Int i = 0; i < size; i++) {
            dfs(this.mGraph.keyAt(i), this.mSortResult, this.mSortTmpMarked)
        }
        return this.mSortResult
    }

    public final Boolean hasOutgoingEdges(@NonNull Object obj) {
        Int size = this.mGraph.size()
        for (Int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i)
            if (arrayList != null && arrayList.contains(obj)) {
                return true
            }
        }
        return false
    }

    final Int size() {
        return this.mGraph.size()
    }
}
