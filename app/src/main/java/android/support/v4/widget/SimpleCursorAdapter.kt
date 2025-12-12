package android.support.v4.widget

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.support.annotation.RestrictTo
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class SimpleCursorAdapter extends ResourceCursorAdapter {
    private CursorToStringConverter mCursorToStringConverter

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Array<Int> mFrom
    Array<String> mOriginalFrom
    private Int mStringConversionColumn

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Array<Int> mTo
    private ViewBinder mViewBinder

    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor)
    }

    public interface ViewBinder {
        Boolean setViewValue(View view, Cursor cursor, Int i)
    }

    @Deprecated
    constructor(Context context, Int i, Cursor cursor, Array<String> strArr, Array<Int> iArr) {
        super(context, i, cursor)
        this.mStringConversionColumn = -1
        this.mTo = iArr
        this.mOriginalFrom = strArr
        findColumns(cursor, strArr)
    }

    constructor(Context context, Int i, Cursor cursor, Array<String> strArr, Array<Int> iArr, Int i2) {
        super(context, i, cursor, i2)
        this.mStringConversionColumn = -1
        this.mTo = iArr
        this.mOriginalFrom = strArr
        findColumns(cursor, strArr)
    }

    private fun findColumns(Cursor cursor, Array<String> strArr) {
        if (cursor == null) {
            this.mFrom = null
            return
        }
        Int length = strArr.length
        if (this.mFrom == null || this.mFrom.length != length) {
            this.mFrom = new Int[length]
        }
        for (Int i = 0; i < length; i++) {
            this.mFrom[i] = cursor.getColumnIndexOrThrow(strArr[i])
        }
    }

    @Override // android.support.v4.widget.CursorAdapter
    fun bindView(View view, Context context, Cursor cursor) {
        ViewBinder viewBinder = this.mViewBinder
        Int length = this.mTo.length
        Array<Int> iArr = this.mFrom
        Array<Int> iArr2 = this.mTo
        for (Int i = 0; i < length; i++) {
            View viewFindViewById = view.findViewById(iArr2[i])
            if (viewFindViewById != null) {
                if (viewBinder != null ? viewBinder.setViewValue(viewFindViewById, cursor, iArr[i]) : false) {
                    continue
                } else {
                    String string = cursor.getString(iArr[i])
                    if (string == null) {
                        string = ""
                    }
                    if (viewFindViewById is TextView) {
                        setViewText((TextView) viewFindViewById, string)
                    } else {
                        if (!(viewFindViewById is ImageView)) {
                            throw IllegalStateException(viewFindViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleCursorAdapter")
                        }
                        setViewImage((ImageView) viewFindViewById, string)
                    }
                }
            }
        }
    }

    fun changeCursorAndColumns(Cursor cursor, Array<String> strArr, Array<Int> iArr) {
        this.mOriginalFrom = strArr
        this.mTo = iArr
        findColumns(cursor, this.mOriginalFrom)
        super.changeCursor(cursor)
    }

    @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
    fun convertToString(Cursor cursor) {
        return this.mCursorToStringConverter != null ? this.mCursorToStringConverter.convertToString(cursor) : this.mStringConversionColumn >= 0 ? cursor.getString(this.mStringConversionColumn) : super.convertToString(cursor)
    }

    fun getCursorToStringConverter() {
        return this.mCursorToStringConverter
    }

    fun getStringConversionColumn() {
        return this.mStringConversionColumn
    }

    fun getViewBinder() {
        return this.mViewBinder
    }

    fun setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.mCursorToStringConverter = cursorToStringConverter
    }

    fun setStringConversionColumn(Int i) {
        this.mStringConversionColumn = i
    }

    fun setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder
    }

    fun setViewImage(ImageView imageView, String str) {
        try {
            imageView.setImageResource(Integer.parseInt(str))
        } catch (NumberFormatException e) {
            imageView.setImageURI(Uri.parse(str))
        }
    }

    fun setViewText(TextView textView, String str) {
        textView.setText(str)
    }

    @Override // android.support.v4.widget.CursorAdapter
    fun swapCursor(Cursor cursor) {
        findColumns(cursor, this.mOriginalFrom)
        return super.swapCursor(cursor)
    }
}
