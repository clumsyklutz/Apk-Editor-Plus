package android.support.v4.provider

import android.net.Uri
import android.support.annotation.Nullable
import android.util.Log
import android.webkit.MimeTypeMap
import jadx.core.deobf.Deobfuscator
import java.io.File
import java.io.IOException
import java.util.ArrayList

class RawDocumentFile extends DocumentFile {
    private File mFile

    RawDocumentFile(@Nullable DocumentFile documentFile, File file) {
        super(documentFile)
        this.mFile = file
    }

    private fun deleteContents(File file) {
        Array<File> fileArrListFiles = file.listFiles()
        Boolean zDeleteContents = true
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    zDeleteContents &= deleteContents(file2)
                }
                if (!file2.delete()) {
                    Log.w("DocumentFile", "Failed to delete " + file2)
                    zDeleteContents = false
                }
            }
        }
        return zDeleteContents
    }

    private fun getTypeForName(String str) {
        Int iLastIndexOf = str.lastIndexOf(46)
        if (iLastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(iLastIndexOf + 1).toLowerCase())
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension
            }
        }
        return "application/octet-stream"
    }

    @Override // android.support.v4.provider.DocumentFile
    fun canRead() {
        return this.mFile.canRead()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun canWrite() {
        return this.mFile.canWrite()
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun createDirectory(String str) {
        File file = File(this.mFile, str)
        if (file.isDirectory() || file.mkdir()) {
            return RawDocumentFile(this, file)
        }
        return null
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun createFile(String str, String str2) throws IOException {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str)
        if (extensionFromMimeType != null) {
            str2 = str2 + Deobfuscator.CLASS_NAME_SEPARATOR + extensionFromMimeType
        }
        File file = File(this.mFile, str2)
        try {
            file.createNewFile()
            return RawDocumentFile(this, file)
        } catch (IOException e) {
            Log.w("DocumentFile", "Failed to createFile: " + e)
            return null
        }
    }

    @Override // android.support.v4.provider.DocumentFile
    fun delete() {
        deleteContents(this.mFile)
        return this.mFile.delete()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun exists() {
        return this.mFile.exists()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun getName() {
        return this.mFile.getName()
    }

    @Override // android.support.v4.provider.DocumentFile
    @Nullable
    fun getType() {
        if (this.mFile.isDirectory()) {
            return null
        }
        return getTypeForName(this.mFile.getName())
    }

    @Override // android.support.v4.provider.DocumentFile
    fun getUri() {
        return Uri.fromFile(this.mFile)
    }

    @Override // android.support.v4.provider.DocumentFile
    fun isDirectory() {
        return this.mFile.isDirectory()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun isFile() {
        return this.mFile.isFile()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun isVirtual() {
        return false
    }

    @Override // android.support.v4.provider.DocumentFile
    fun lastModified() {
        return this.mFile.lastModified()
    }

    @Override // android.support.v4.provider.DocumentFile
    fun length() {
        return this.mFile.length()
    }

    @Override // android.support.v4.provider.DocumentFile
    public Array<DocumentFile> listFiles() {
        ArrayList arrayList = ArrayList()
        Array<File> fileArrListFiles = this.mFile.listFiles()
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                arrayList.add(RawDocumentFile(this, file))
            }
        }
        return (Array<DocumentFile>) arrayList.toArray(new DocumentFile[arrayList.size()])
    }

    @Override // android.support.v4.provider.DocumentFile
    fun renameTo(String str) {
        File file = File(this.mFile.getParentFile(), str)
        if (!this.mFile.renameTo(file)) {
            return false
        }
        this.mFile = file
        return true
    }
}
