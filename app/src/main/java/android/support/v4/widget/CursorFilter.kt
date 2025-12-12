package android.support.v4.widget

import android.database.Cursor
import android.widget.Filter

class CursorFilter extends Filter {
    CursorFilterClient mClient

    interface CursorFilterClient {
        Unit changeCursor(Cursor cursor)

        CharSequence convertToString(Cursor cursor)

        Cursor getCursor()

        Cursor runQueryOnBackgroundThread(CharSequence charSequence)
    }

    CursorFilter(CursorFilterClient cursorFilterClient) {
        this.mClient = cursorFilterClient
    }

    @Override // android.widget.Filter
    fun convertResultToString(Object obj) {
        return this.mClient.convertToString((Cursor) obj)
    }

    @Override // android.widget.Filter
    protected Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor cursorRunQueryOnBackgroundThread = this.mClient.runQueryOnBackgroundThread(charSequence)
        Filter.FilterResults filterResults = new Filter.FilterResults()
        if (cursorRunQueryOnBackgroundThread != null) {
            filterResults.count = cursorRunQueryOnBackgroundThread.getCount()
            filterResults.values = cursorRunQueryOnBackgroundThread
        } else {
            filterResults.count = 0
            filterResults.values = null
        }
        return filterResults
    }

    @Override // android.widget.Filter
    protected fun publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        Cursor cursor = this.mClient.getCursor()
        if (filterResults.values == null || filterResults.values == cursor) {
            return
        }
        this.mClient.changeCursor((Cursor) filterResults.values)
    }
}
