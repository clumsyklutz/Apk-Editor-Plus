package android.support.v4.content

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.loader.content.Loader
import android.support.v4.os.CancellationSignal
import android.support.v4.os.OperationCanceledException
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.Arrays

class CursorLoader extends AsyncTaskLoader {
    CancellationSignal mCancellationSignal
    Cursor mCursor
    final Loader.ForceLoadContentObserver mObserver
    Array<String> mProjection
    String mSelection
    Array<String> mSelectionArgs
    String mSortOrder
    Uri mUri

    constructor(@NonNull Context context) {
        super(context)
        this.mObserver = new Loader.ForceLoadContentObserver()
    }

    constructor(@NonNull Context context, @NonNull Uri uri, @Nullable Array<String> strArr, @Nullable String str, @Nullable Array<String> strArr2, @Nullable String str2) {
        super(context)
        this.mObserver = new Loader.ForceLoadContentObserver()
        this.mUri = uri
        this.mProjection = strArr
        this.mSelection = str
        this.mSelectionArgs = strArr2
        this.mSortOrder = str2
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    fun cancelLoadInBackground() {
        super.cancelLoadInBackground()
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel()
            }
        }
    }

    @Override // android.support.v4.content.Loader
    fun deliverResult(Cursor cursor) {
        if (isReset()) {
            if (cursor != null) {
                cursor.close()
                return
            }
            return
        }
        Cursor cursor2 = this.mCursor
        this.mCursor = cursor
        if (isStarted()) {
            super.deliverResult((Object) cursor)
        }
        if (cursor2 == null || cursor2 == cursor || cursor2.isClosed()) {
            return
        }
        cursor2.close()
    }

    @Override // android.support.v4.content.AsyncTaskLoader, android.support.v4.content.Loader
    @Deprecated
    fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr)
        printWriter.print(str)
        printWriter.print("mUri=")
        printWriter.println(this.mUri)
        printWriter.print(str)
        printWriter.print("mProjection=")
        printWriter.println(Arrays.toString(this.mProjection))
        printWriter.print(str)
        printWriter.print("mSelection=")
        printWriter.println(this.mSelection)
        printWriter.print(str)
        printWriter.print("mSelectionArgs=")
        printWriter.println(Arrays.toString(this.mSelectionArgs))
        printWriter.print(str)
        printWriter.print("mSortOrder=")
        printWriter.println(this.mSortOrder)
        printWriter.print(str)
        printWriter.print("mCursor=")
        printWriter.println(this.mCursor)
        printWriter.print(str)
        printWriter.print("mContentChanged=")
        printWriter.println(this.mContentChanged)
    }

    @Nullable
    public Array<String> getProjection() {
        return this.mProjection
    }

    @Nullable
    fun getSelection() {
        return this.mSelection
    }

    @Nullable
    public Array<String> getSelectionArgs() {
        return this.mSelectionArgs
    }

    @Nullable
    fun getSortOrder() {
        return this.mSortOrder
    }

    @NonNull
    fun getUri() {
        return this.mUri
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    fun loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw OperationCanceledException()
            }
            this.mCancellationSignal = CancellationSignal()
        }
        try {
            Cursor cursorQuery = ContentResolverCompat.query(getContext().getContentResolver(), this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal)
            if (cursorQuery != null) {
                try {
                    cursorQuery.getCount()
                    cursorQuery.registerContentObserver(this.mObserver)
                } catch (RuntimeException e) {
                    cursorQuery.close()
                    throw e
                }
            }
            synchronized (this) {
                this.mCancellationSignal = null
            }
            return cursorQuery
        } catch (Throwable th) {
            synchronized (this) {
                this.mCancellationSignal = null
                throw th
            }
        }
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    fun onCanceled(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return
        }
        cursor.close()
    }

    @Override // android.support.v4.content.Loader
    protected fun onReset() {
        super.onReset()
        onStopLoading()
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close()
        }
        this.mCursor = null
    }

    @Override // android.support.v4.content.Loader
    protected fun onStartLoading() {
        if (this.mCursor != null) {
            deliverResult(this.mCursor)
        }
        if (takeContentChanged() || this.mCursor == null) {
            forceLoad()
        }
    }

    @Override // android.support.v4.content.Loader
    protected fun onStopLoading() {
        cancelLoad()
    }

    fun setProjection(@Nullable Array<String> strArr) {
        this.mProjection = strArr
    }

    fun setSelection(@Nullable String str) {
        this.mSelection = str
    }

    fun setSelectionArgs(@Nullable Array<String> strArr) {
        this.mSelectionArgs = strArr
    }

    fun setSortOrder(@Nullable String str) {
        this.mSortOrder = str
    }

    fun setUri(@NonNull Uri uri) {
        this.mUri = uri
    }
}
