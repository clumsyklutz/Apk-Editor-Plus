package android.support.v4.widget

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.database.DataSetObserver
import android.os.Handler
import android.support.annotation.RestrictTo
import android.support.v4.widget.CursorFilter
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.FilterQueryProvider
import android.widget.Filterable

abstract class CursorAdapter extends BaseAdapter implements CursorFilter.CursorFilterClient, Filterable {

    @Deprecated
    public static val FLAG_AUTO_REQUERY = 1
    public static val FLAG_REGISTER_CONTENT_OBSERVER = 2

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Boolean mAutoRequery

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected ChangeObserver mChangeObserver

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Context mContext

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Cursor mCursor

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected CursorFilter mCursorFilter

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected DataSetObserver mDataSetObserver

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Boolean mDataValid

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected FilterQueryProvider mFilterQueryProvider

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Int mRowIDColumn

    class ChangeObserver extends ContentObserver {
        ChangeObserver() {
            super(Handler())
        }

        @Override // android.database.ContentObserver
        fun deliverSelfNotifications() {
            return true
        }

        @Override // android.database.ContentObserver
        fun onChange(Boolean z) {
            CursorAdapter.this.onContentChanged()
        }
    }

    class MyDataSetObserver extends DataSetObserver {
        MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        fun onChanged() {
            CursorAdapter.this.mDataValid = true
            CursorAdapter.this.notifyDataSetChanged()
        }

        @Override // android.database.DataSetObserver
        fun onInvalidated() {
            CursorAdapter.this.mDataValid = false
            CursorAdapter.this.notifyDataSetInvalidated()
        }
    }

    @Deprecated
    constructor(Context context, Cursor cursor) {
        init(context, cursor, 1)
    }

    constructor(Context context, Cursor cursor, Int i) {
        init(context, cursor, i)
    }

    constructor(Context context, Cursor cursor, Boolean z) {
        init(context, cursor, z ? 1 : 2)
    }

    public abstract Unit bindView(View view, Context context, Cursor cursor)

    @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
    fun changeCursor(Cursor cursor) {
        Cursor cursorSwapCursor = swapCursor(cursor)
        if (cursorSwapCursor != null) {
            cursorSwapCursor.close()
        }
    }

    @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
    fun convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString()
    }

    @Override // android.widget.Adapter
    fun getCount() {
        if (!this.mDataValid || this.mCursor == null) {
            return 0
        }
        return this.mCursor.getCount()
    }

    @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
    fun getCursor() {
        return this.mCursor
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    fun getDropDownView(Int i, View view, ViewGroup viewGroup) {
        if (!this.mDataValid) {
            return null
        }
        this.mCursor.moveToPosition(i)
        if (view == null) {
            view = newDropDownView(this.mContext, this.mCursor, viewGroup)
        }
        bindView(view, this.mContext, this.mCursor)
        return view
    }

    @Override // android.widget.Filterable
    fun getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = CursorFilter(this)
        }
        return this.mCursorFilter
    }

    fun getFilterQueryProvider() {
        return this.mFilterQueryProvider
    }

    @Override // android.widget.Adapter
    fun getItem(Int i) {
        if (!this.mDataValid || this.mCursor == null) {
            return null
        }
        this.mCursor.moveToPosition(i)
        return this.mCursor
    }

    @Override // android.widget.Adapter
    fun getItemId(Int i) {
        if (this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(i)) {
            return this.mCursor.getLong(this.mRowIDColumn)
        }
        return 0L
    }

    @Override // android.widget.Adapter
    fun getView(Int i, View view, ViewGroup viewGroup) {
        if (!this.mDataValid) {
            throw IllegalStateException("this should only be called when the cursor is valid")
        }
        if (!this.mCursor.moveToPosition(i)) {
            throw IllegalStateException("couldn't move cursor to position " + i)
        }
        if (view == null) {
            view = newView(this.mContext, this.mCursor, viewGroup)
        }
        bindView(view, this.mContext, this.mCursor)
        return view
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    fun hasStableIds() {
        return true
    }

    Unit init(Context context, Cursor cursor, Int i) {
        if ((i & 1) == 1) {
            i |= 2
            this.mAutoRequery = true
        } else {
            this.mAutoRequery = false
        }
        Boolean z = cursor != null
        this.mCursor = cursor
        this.mDataValid = z
        this.mContext = context
        this.mRowIDColumn = z ? cursor.getColumnIndexOrThrow("_id") : -1
        if ((i & 2) == 2) {
            this.mChangeObserver = ChangeObserver()
            this.mDataSetObserver = MyDataSetObserver()
        } else {
            this.mChangeObserver = null
            this.mDataSetObserver = null
        }
        if (z) {
            if (this.mChangeObserver != null) {
                cursor.registerContentObserver(this.mChangeObserver)
            }
            if (this.mDataSetObserver != null) {
                cursor.registerDataSetObserver(this.mDataSetObserver)
            }
        }
    }

    @Deprecated
    protected fun init(Context context, Cursor cursor, Boolean z) {
        init(context, cursor, z ? 1 : 2)
    }

    fun newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return newView(context, cursor, viewGroup)
    }

    public abstract View newView(Context context, Cursor cursor, ViewGroup viewGroup)

    protected fun onContentChanged() {
        if (!this.mAutoRequery || this.mCursor == null || this.mCursor.isClosed()) {
            return
        }
        this.mDataValid = this.mCursor.requery()
    }

    @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
    fun runQueryOnBackgroundThread(CharSequence charSequence) {
        return this.mFilterQueryProvider != null ? this.mFilterQueryProvider.runQuery(charSequence) : this.mCursor
    }

    fun setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider
    }

    fun swapCursor(Cursor cursor) {
        if (cursor == this.mCursor) {
            return null
        }
        Cursor cursor2 = this.mCursor
        if (cursor2 != null) {
            if (this.mChangeObserver != null) {
                cursor2.unregisterContentObserver(this.mChangeObserver)
            }
            if (this.mDataSetObserver != null) {
                cursor2.unregisterDataSetObserver(this.mDataSetObserver)
            }
        }
        this.mCursor = cursor
        if (cursor == null) {
            this.mRowIDColumn = -1
            this.mDataValid = false
            notifyDataSetInvalidated()
            return cursor2
        }
        if (this.mChangeObserver != null) {
            cursor.registerContentObserver(this.mChangeObserver)
        }
        if (this.mDataSetObserver != null) {
            cursor.registerDataSetObserver(this.mDataSetObserver)
        }
        this.mRowIDColumn = cursor.getColumnIndexOrThrow("_id")
        this.mDataValid = true
        notifyDataSetChanged()
        return cursor2
    }
}
