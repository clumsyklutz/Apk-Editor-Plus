package android.support.v4.provider

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import java.io.File

abstract class DocumentFile {
    static val TAG = "DocumentFile"

    @Nullable
    private final DocumentFile mParent

    DocumentFile(@Nullable DocumentFile documentFile) {
        this.mParent = documentFile
    }

    @NonNull
    fun fromFile(@NonNull File file) {
        return RawDocumentFile(null, file)
    }

    @Nullable
    fun fromSingleUri(@NonNull Context context, @NonNull Uri uri) {
        if (Build.VERSION.SDK_INT >= 19) {
            return SingleDocumentFile(null, context, uri)
        }
        return null
    }

    @Nullable
    fun fromTreeUri(@NonNull Context context, @NonNull Uri uri) {
        if (Build.VERSION.SDK_INT >= 21) {
            return TreeDocumentFile(null, context, DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri)))
        }
        return null
    }

    fun isDocumentUri(@NonNull Context context, @Nullable Uri uri) {
        if (Build.VERSION.SDK_INT >= 19) {
            return DocumentsContract.isDocumentUri(context, uri)
        }
        return false
    }

    public abstract Boolean canRead()

    public abstract Boolean canWrite()

    @Nullable
    public abstract DocumentFile createDirectory(@NonNull String str)

    @Nullable
    public abstract DocumentFile createFile(@NonNull String str, @NonNull String str2)

    public abstract Boolean delete()

    public abstract Boolean exists()

    @Nullable
    fun findFile(@NonNull String str) {
        for (DocumentFile documentFile : listFiles()) {
            if (str.equals(documentFile.getName())) {
                return documentFile
            }
        }
        return null
    }

    @Nullable
    public abstract String getName()

    @Nullable
    fun getParentFile() {
        return this.mParent
    }

    @Nullable
    public abstract String getType()

    @NonNull
    public abstract Uri getUri()

    public abstract Boolean isDirectory()

    public abstract Boolean isFile()

    public abstract Boolean isVirtual()

    public abstract Long lastModified()

    public abstract Long length()

    @NonNull
    public abstract Array<DocumentFile> listFiles()

    public abstract Boolean renameTo(@NonNull String str)
}
