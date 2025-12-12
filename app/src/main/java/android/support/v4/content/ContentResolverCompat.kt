package android.support.v4.content

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.OperationCanceledException
import android.support.v4.os.CancellationSignal

class ContentResolverCompat {
    private constructor() {
    }

    fun query(ContentResolver contentResolver, Uri uri, Array<String> strArr, String str, Array<String> strArr2, String str2, CancellationSignal cancellationSignal) throws Exception {
        Object cancellationSignalObject
        if (Build.VERSION.SDK_INT < 16) {
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled()
            }
            return contentResolver.query(uri, strArr, str, strArr2, str2)
        }
        if (cancellationSignal != null) {
            try {
                cancellationSignalObject = cancellationSignal.getCancellationSignalObject()
            } catch (Exception e) {
                if (e is OperationCanceledException) {
                    throw new android.support.v4.os.OperationCanceledException()
                }
                throw e
            }
        } else {
            cancellationSignalObject = null
        }
        return contentResolver.query(uri, strArr, str, strArr2, str2, (android.os.CancellationSignal) cancellationSignalObject)
    }
}
