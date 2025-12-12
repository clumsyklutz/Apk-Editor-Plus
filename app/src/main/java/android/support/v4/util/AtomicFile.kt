package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.SyncFailedException

class AtomicFile {
    private final File mBackupName
    private final File mBaseName

    constructor(@NonNull File file) {
        this.mBaseName = file
        this.mBackupName = File(file.getPath() + ".bak")
    }

    private fun sync(@NonNull FileOutputStream fileOutputStream) throws SyncFailedException {
        try {
            fileOutputStream.getFD().sync()
            return true
        } catch (IOException e) {
            return false
        }
    }

    fun delete() {
        this.mBaseName.delete()
        this.mBackupName.delete()
    }

    fun failWrite(@Nullable FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            sync(fileOutputStream)
            try {
                fileOutputStream.close()
                this.mBaseName.delete()
                this.mBackupName.renameTo(this.mBaseName)
            } catch (IOException e) {
                Log.w("AtomicFile", "failWrite: Got exception:", e)
            }
        }
    }

    fun finishWrite(@Nullable FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            sync(fileOutputStream)
            try {
                fileOutputStream.close()
                this.mBackupName.delete()
            } catch (IOException e) {
                Log.w("AtomicFile", "finishWrite: Got exception:", e)
            }
        }
    }

    @NonNull
    fun getBaseFile() {
        return this.mBaseName
    }

    @NonNull
    fun openRead() {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete()
            this.mBackupName.renameTo(this.mBaseName)
        }
        return FileInputStream(this.mBaseName)
    }

    @NonNull
    public Array<Byte> readFully() throws IOException {
        Array<Byte> bArr
        Int i = 0
        FileInputStream fileInputStreamOpenRead = openRead()
        try {
            Array<Byte> bArr2 = new Byte[fileInputStreamOpenRead.available()]
            while (true) {
                Int i2 = fileInputStreamOpenRead.read(bArr2, i, bArr2.length - i)
                if (i2 <= 0) {
                    return bArr2
                }
                Int i3 = i2 + i
                Int iAvailable = fileInputStreamOpenRead.available()
                if (iAvailable > bArr2.length - i3) {
                    bArr = new Byte[iAvailable + i3]
                    System.arraycopy(bArr2, 0, bArr, 0, i3)
                } else {
                    bArr = bArr2
                }
                bArr2 = bArr
                i = i3
            }
        } finally {
            fileInputStreamOpenRead.close()
        }
    }

    @NonNull
    fun startWrite() throws IOException {
        if (this.mBaseName.exists()) {
            if (this.mBackupName.exists()) {
                this.mBaseName.delete()
            } else if (!this.mBaseName.renameTo(this.mBackupName)) {
                Log.w("AtomicFile", "Couldn't rename file " + this.mBaseName + " to backup file " + this.mBackupName)
            }
        }
        try {
            return FileOutputStream(this.mBaseName)
        } catch (FileNotFoundException e) {
            if (!this.mBaseName.getParentFile().mkdirs()) {
                throw IOException("Couldn't create directory " + this.mBaseName)
            }
            try {
                return FileOutputStream(this.mBaseName)
            } catch (FileNotFoundException e2) {
                throw IOException("Couldn't create " + this.mBaseName)
            }
        }
    }
}
