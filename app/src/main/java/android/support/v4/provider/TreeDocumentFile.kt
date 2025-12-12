package android.support.v4.provider

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import java.io.FileNotFoundException

@RequiresApi(21)
class TreeDocumentFile extends DocumentFile {
    private Context mContext
    private Uri mUri

    TreeDocumentFile(@Nullable DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile)
        this.mContext = context
        this.mUri = uri
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

    @Nullable
    private fun createFile(Context context, Uri uri, String str, String str2) {
        try {
            return DocumentsContract.createDocument(context.getContentResolver(), uri, str, str2)
        } catch (Exception e) {
            return null
        }
    }

    @Override // android.support.v4.provider.DocumentFile
    fun canRead() {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun canWrite() {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun createDirectory(String str) {
        Uri uriCreateFile = createFile(this.mContext, this.mUri, "vnd.android.document/directory", str)
        if (uriCreateFile != null) {
            return TreeDocumentFile(this, this.mContext, uriCreateFile)
        }
        return null
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun createFile(String str, String str2) {
        Uri uriCreateFile = createFile(this.mContext, this.mUri, str, str2)
        if (uriCreateFile != null) {
            return TreeDocumentFile(this, this.mContext, uriCreateFile)
        }
        return null
    }

    @Override // android.support.v4.provider.DocumentFile
    fun delete() {
        try {
            return DocumentsContract.deleteDocument(this.mContext.getContentResolver(), this.mUri)
        } catch (Exception e) {
            return false
        }
    }

    @Override // android.support.v4.provider.DocumentFile
    fun exists() {
        return DocumentsContractApi19.exists(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun getName() {
        return DocumentsContractApi19.getName(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun getType() {
        return DocumentsContractApi19.getType(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun getUri() {
        return this.mUri
    }

    @Override // android.support.v4.provider.DocumentFile
    fun isDirectory() {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun isFile() {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun isVirtual() {
        return DocumentsContractApi19.isVirtual(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun lastModified() {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun length() {
        return DocumentsContractApi19.length(this.mContext, this.mUri)
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0068 A[LOOP:1: B:12:0x0065->B:14:0x0068, LOOP_END] */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.AutoCloseable] */
    @Override // android.support.v4.provider.DocumentFile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.support.v4.provider.Array<DocumentFile> listFiles() throws java.lang.Exception {
        /*
            r9 = this
            r6 = 0
            r7 = 0
            android.content.Context r0 = r9.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            android.net.Uri r1 = r9.mUri
            android.net.Uri r2 = r9.mUri
            java.lang.String r2 = android.provider.DocumentsContract.getDocumentId(r2)
            android.net.Uri r1 = android.provider.DocumentsContract.buildChildDocumentsUriUsingTree(r1, r2)
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r2 = 1
            java.lang.Array<String> r2 = new java.lang.String[r2]     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L83
            r3 = 0
            java.lang.String r4 = "document_id"
            r2[r3] = r4     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L83
            r3 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L83
        L28:
            Boolean r0 = r1.moveToNext()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L81
            if (r0 == 0) goto L76
            r0 = 0
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L81
            android.net.Uri r2 = r9.mUri     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L81
            android.net.Uri r0 = android.provider.DocumentsContract.buildDocumentUriUsingTree(r2, r0)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L81
            r8.add(r0)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L81
            goto L28
        L3d:
            r0 = move-exception
        L3e:
            java.lang.String r2 = "DocumentFile"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L81
            java.lang.String r4 = "Failed query: "
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L81
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch: java.lang.Throwable -> L81
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L81
            android.util.Log.w(r2, r0)     // Catch: java.lang.Throwable -> L81
            closeQuietly(r1)
        L55:
            Int r0 = r8.size()
            android.net.Array<Uri> r0 = new android.net.Uri[r0]
            java.lang.Array<Object> r0 = r8.toArray(r0)
            android.net.Array<Uri> r0 = (android.net.Array<Uri>) r0
            Int r1 = r0.length
            android.support.v4.provider.Array<DocumentFile> r2 = new android.support.v4.provider.DocumentFile[r1]
            r1 = r6
        L65:
            Int r3 = r0.length
            if (r1 >= r3) goto L80
            android.support.v4.provider.TreeDocumentFile r3 = new android.support.v4.provider.TreeDocumentFile
            android.content.Context r4 = r9.mContext
            r5 = r0[r1]
            r3.<init>(r9, r4, r5)
            r2[r1] = r3
            Int r1 = r1 + 1
            goto L65
        L76:
            closeQuietly(r1)
            goto L55
        L7a:
            r0 = move-exception
            r1 = r7
        L7c:
            closeQuietly(r1)
            throw r0
        L80:
            return r2
        L81:
            r0 = move-exception
            goto L7c
        L83:
            r0 = move-exception
            r1 = r7
            goto L3e
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.provider.TreeDocumentFile.listFiles():android.support.v4.provider.Array<DocumentFile>")
    }

    @Override // android.support.v4.provider.DocumentFile
    fun renameTo(String str) throws FileNotFoundException {
        try {
            Uri uriRenameDocument = DocumentsContract.renameDocument(this.mContext.getContentResolver(), this.mUri, str)
            if (uriRenameDocument == null) {
                return false
            }
            this.mUri = uriRenameDocument
            return true
        } catch (Exception e) {
            return false
        }
    }
}
