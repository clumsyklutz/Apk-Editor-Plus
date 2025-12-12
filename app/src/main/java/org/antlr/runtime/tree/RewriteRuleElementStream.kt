package org.antlr.runtime.tree

import java.util.ArrayList
import java.util.List

abstract class RewriteRuleElementStream {
    public TreeAdaptor adaptor
    public Int cursor
    public Boolean dirty
    public String elementDescription
    public List<Object> elements
    public Object singleElement

    constructor(TreeAdaptor treeAdaptor, String str) {
        this.cursor = 0
        this.dirty = false
        this.elementDescription = str
        this.adaptor = treeAdaptor
    }

    constructor(TreeAdaptor treeAdaptor, String str, Object obj) {
        this(treeAdaptor, str)
        add(obj)
    }

    fun _next() {
        Int size = size()
        if (size == 0) {
            throw RewriteEmptyStreamException(this.elementDescription)
        }
        Int i = this.cursor
        if (i >= size) {
            if (size == 1) {
                return toTree(this.singleElement)
            }
            throw RewriteCardinalityException(this.elementDescription)
        }
        Object obj = this.singleElement
        if (obj != null) {
            this.cursor = i + 1
            return toTree(obj)
        }
        Object tree = toTree(this.elements.get(i))
        this.cursor++
        return tree
    }

    fun add(Object obj) {
        if (obj == null) {
            return
        }
        List<Object> list = this.elements
        if (list != null) {
            list.add(obj)
            return
        }
        if (this.singleElement == null) {
            this.singleElement = obj
            return
        }
        ArrayList arrayList = ArrayList(5)
        this.elements = arrayList
        arrayList.add(this.singleElement)
        this.singleElement = null
        this.elements.add(obj)
    }

    public abstract Object dup(Object obj)

    fun hasNext() {
        if (this.singleElement != null && this.cursor < 1) {
            return true
        }
        List<Object> list = this.elements
        return list != null && this.cursor < list.size()
    }

    fun nextTree() {
        Int size = size()
        return (this.dirty || (this.cursor >= size && size == 1)) ? dup(_next()) : _next()
    }

    fun reset() {
        this.cursor = 0
        this.dirty = true
    }

    fun size() {
        Int i = this.singleElement != null ? 1 : 0
        List<Object> list = this.elements
        return list != null ? list.size() : i
    }

    fun toTree(Object obj) {
        return obj
    }
}
