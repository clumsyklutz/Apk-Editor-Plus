package com.gmail.heagoo.apkeditor

import java.util.ArrayList
import java.util.Iterator
import java.util.List

class ListenerList<L> {
    private List<L> listenerList = ArrayList()

    public interface FireHandler<L> {
        Unit fireEvent(L l)
    }

    ListenerList() {
    }

    fun add(L l) {
        this.listenerList.add(l)
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun fireEvent(FireHandler<L> fireHandler) {
        Iterator it = ArrayList(this.listenerList).iterator()
        while (it.hasNext()) {
            fireHandler.fireEvent(it.next())
        }
    }

    public List<L> getListenerList() {
        return this.listenerList
    }

    fun remove(L l) {
        this.listenerList.remove(l)
    }
}
