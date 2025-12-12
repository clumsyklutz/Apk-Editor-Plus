package android.support.v4.provider

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi

@RequiresApi(19)
class SingleDocumentFile extends DocumentFile {
    private Context mContext
    private Uri mUri

    SingleDocumentFile(@Nullable DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile)
        this.mContext = context
        this.mUri = uri
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
    fun createDirectory(String str) {
        throw UnsupportedOperationException()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun createFile(String str, String str2) {
        throw UnsupportedOperationException()
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

    @Override // android.support.v4.provider.DocumentFile
    public Array<DocumentFile> listFiles() {
        throw UnsupportedOperationException()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun renameTo(String str) {
        throw UnsupportedOperationException()
    }
}
