package android.support.v4.widget

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class ResourceCursorAdapter extends CursorAdapter {
    private Int mDropDownLayout
    private LayoutInflater mInflater
    private Int mLayout

    @Deprecated
    constructor(Context context, Int i, Cursor cursor) {
        super(context, cursor)
        this.mDropDownLayout = i
        this.mLayout = i
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater")
    }

    constructor(Context context, Int i, Cursor cursor, Int i2) {
        super(context, cursor, i2)
        this.mDropDownLayout = i
        this.mLayout = i
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater")
    }

    @Deprecated
    constructor(Context context, Int i, Cursor cursor, Boolean z) {
        super(context, cursor, z)
        this.mDropDownLayout = i
        this.mLayout = i
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater")
    }

    @Override // android.support.v4.widget.CursorAdapter
    fun newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.mInflater.inflate(this.mDropDownLayout, viewGroup, false)
    }

    @Override // android.support.v4.widget.CursorAdapter
    fun newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.mInflater.inflate(this.mLayout, viewGroup, false)
    }

    fun setDropDownViewResource(Int i) {
        this.mDropDownLayout = i
    }

    fun setViewResource(Int i) {
        this.mLayout = i
    }
}
