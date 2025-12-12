package android.support.v4.provider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Log

@RequiresApi(19)
class DocumentsContractApi19 {
    private static val FLAG_VIRTUAL_DOCUMENT = 512
    private static val TAG = "DocumentFile"

    private constructor() {
    }

    fun canRead(Context context, Uri uri) {
        return context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(getRawType(context, uri))
    }

    fun canWrite(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false
        }
        String rawType = getRawType(context, uri)
        Int iQueryForInt = queryForInt(context, uri, "flags", 0)
        if (TextUtils.isEmpty(rawType)) {
            return false
        }
        if ((iQueryForInt & 4) != 0) {
            return true
        }
        if (!"vnd.android.document/directory".equals(rawType) || (iQueryForInt & 8) == 0) {
            return (TextUtils.isEmpty(rawType) || (iQueryForInt & 2) == 0) ? false : true
        }
        return true
    }

    private fun closeQuietly(@Nullable AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            try {
                autoCloseable.close()
            } catch (RuntimeException e) {
                throw e
            } catch (Exception e2) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6 */
    fun exists(Context context, Uri uri) throws Exception {
        Cursor cursorQuery = null
        Boolean z
        try {
            try {
                cursorQuery = context.getContentResolver().query(uri, new Array<String>{"document_id"}, null, null, null)
            } catch (Exception e) {
                e = e
                cursorQuery = null
            } catch (Throwable th) {
                th = th
                closeQuietly(cursorQuery)
                throw th
            }
            try {
                z = cursorQuery.getCount() > 0
                closeQuietly(cursorQuery)
            } catch (Exception e2) {
                e = e2
                Log.w(TAG, "Failed query: " + e)
                closeQuietly(cursorQuery)
                z = false
                return z
            }
            return z
        } catch (Throwable th2) {
            th = th2
            closeQuietly(cursorQuery)
            throw th
        }
    }

    fun getFlags(Context context, Uri uri) {
        return queryForLong(context, uri, "flags", 0L)
    }

    @Nullable
    fun getName(Context context, Uri uri) {
        return queryForString(context, uri, "_display_name", null)
    }

    @Nullable
    private fun getRawType(Context context, Uri uri) {
        return queryForString(context, uri, "mime_type", null)
    }

    @Nullable
    fun getType(Context context, Uri uri) {
        String rawType = getRawType(context, uri)
        if ("vnd.android.document/directory".equals(rawType)) {
            return null
        }
        return rawType
    }

    fun isDirectory(Context context, Uri uri) {
        return "vnd.android.document/directory".equals(getRawType(context, uri))
    }

    fun isFile(Context context, Uri uri) {
        String rawType = getRawType(context, uri)
        return ("vnd.android.document/directory".equals(rawType) || TextUtils.isEmpty(rawType)) ? false : true
    }

    fun isVirtual(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri) && (getFlags(context, uri) & 512) != 0
    }

    fun lastModified(Context context, Uri uri) {
        return queryForLong(context, uri, "last_modified", 0L)
    }

    fun length(Context context, Uri uri) {
        return queryForLong(context, uri, "_size", 0L)
    }

    private fun queryForInt(Context context, Uri uri, String str, Int i) {
        return (Int) queryForLong(context, uri, str, i)
    }

    private fun queryForLong(Context context, Uri uri, String str, Long j) throws Exception {
        Cursor cursorQuery
        try {
            cursorQuery = context.getContentResolver().query(uri, new Array<String>{str}, null, null, null)
        } catch (Exception e) {
            e = e
            cursorQuery = null
        } catch (Throwable th) {
            th = th
            cursorQuery = null
            closeQuietly(cursorQuery)
            throw th
        }
        try {
            try {
                if (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) {
                    closeQuietly(cursorQuery)
                } else {
                    j = cursorQuery.getLong(0)
                    closeQuietly(cursorQuery)
                }
            } catch (Throwable th2) {
                th = th2
                closeQuietly(cursorQuery)
                throw th
            }
        } catch (Exception e2) {
            e = e2
            Log.w(TAG, "Failed query: " + e)
            closeQuietly(cursorQuery)
            return j
        }
        return j
    }

    @Nullable
    private fun queryForString(Context context, Uri uri, String str, @Nullable String str2) throws Exception {
        Cursor cursorQuery
        try {
            cursorQuery = context.getContentResolver().query(uri, new Array<String>{str}, null, null, null)
        } catch (Exception e) {
            e = e
            cursorQuery = null
        } catch (Throwable th) {
            th = th
            cursorQuery = null
            closeQuietly(cursorQuery)
            throw th
        }
        try {
            try {
                if (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) {
                    closeQuietly(cursorQuery)
                } else {
                    str2 = cursorQuery.getString(0)
                    closeQuietly(cursorQuery)
                }
            } catch (Throwable th2) {
                th = th2
                closeQuietly(cursorQuery)
                throw th
            }
        } catch (Exception e2) {
            e = e2
            Log.w(TAG, "Failed query: " + e)
            closeQuietly(cursorQuery)
            return str2
        }
        return str2
    }
}
