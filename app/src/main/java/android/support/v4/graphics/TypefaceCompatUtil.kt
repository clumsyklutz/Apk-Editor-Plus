package android.support.v4.graphics

import android.content.Context
import android.content.res.Resources
import android.os.Process
import android.os.StrictMode
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.util.Log
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatUtil {
    private static val CACHE_FILE_PREFIX = ".font"
    private static val TAG = "TypefaceCompatUtil"

    private constructor() {
    }

    fun closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    @RequiresApi(19)
    @Nullable
    fun copyToDirectBuffer(Context context, Resources resources, Int i) {
        ByteBuffer byteBufferMmap = null
        File tempFile = getTempFile(context)
        if (tempFile != null) {
            try {
                if (copyToFile(tempFile, resources, i)) {
                    byteBufferMmap = mmap(tempFile)
                }
            } finally {
                tempFile.delete()
            }
        }
        return byteBufferMmap
    }

    fun copyToFile(File file, Resources resources, Int i) throws IOException {
        InputStream inputStreamOpenRawResource = null
        try {
            inputStreamOpenRawResource = resources.openRawResource(i)
            return copyToFile(file, inputStreamOpenRawResource)
        } finally {
            closeQuietly(inputStreamOpenRawResource)
        }
    }

    fun copyToFile(File file, InputStream inputStream) throws Throwable {
        FileOutputStream fileOutputStream
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskWrites = StrictMode.allowThreadDiskWrites()
        try {
            try {
                fileOutputStream = FileOutputStream(file, false)
            } catch (IOException e) {
                e = e
                fileOutputStream = null
            } catch (Throwable th) {
                th = th
                closeQuietly(null)
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites)
                throw th
            }
            try {
                Array<Byte> bArr = new Byte[1024]
                while (true) {
                    Int i = inputStream.read(bArr)
                    if (i == -1) {
                        closeQuietly(fileOutputStream)
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites)
                        return true
                    }
                    fileOutputStream.write(bArr, 0, i)
                }
            } catch (IOException e2) {
                e = e2
                Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage())
                closeQuietly(fileOutputStream)
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites)
                return false
            }
        } catch (Throwable th2) {
            th = th2
            closeQuietly(null)
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites)
            throw th
        }
    }

    @Nullable
    fun getTempFile(Context context) {
        String str = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-"
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= 100) {
                return null
            }
            File file = File(context.getCacheDir(), str + i2)
            if (file.createNewFile()) {
                return file
            }
            i = i2 + 1
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005b A[Catch: Throwable -> 0x0045, all -> 0x0058, TRY_LEAVE, TryCatch #5 {all -> 0x0058, blocks: (B:9:0x0014, B:11:0x002d, B:22:0x0041, B:23:0x0044, B:36:0x005b, B:33:0x0054), top: B:51:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[Catch: IOException -> 0x0036, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x0036, blocks: (B:3:0x0005, B:6:0x000f, B:13:0x0032, B:30:0x004f, B:40:0x0064, B:39:0x0060, B:31:0x0052), top: B:46:0x0005, inners: #0 }] */
    @android.support.annotation.RequiresApi(19)
    @android.support.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.nio.ByteBuffer mmap(android.content.Context r10, android.os.CancellationSignal r11, android.net.Uri r12) throws java.lang.Throwable {
        /*
            r6 = 0
            android.content.ContentResolver r0 = r10.getContentResolver()
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r7 = r0.openFileDescriptor(r12, r1, r11)     // Catch: java.io.IOException -> L36
            if (r7 != 0) goto L14
            if (r7 == 0) goto L12
            r7.close()     // Catch: java.io.IOException -> L36
        L12:
            r0 = r6
        L13:
            return r0
        L14:
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
            java.io.FileDescriptor r0 = r7.getFileDescriptor()     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
            r8.<init>(r0)     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
            java.nio.channels.FileChannel r0 = r8.getChannel()     // Catch: java.lang.Throwable -> L39 java.lang.Throwable -> L68
            Long r4 = r0.size()     // Catch: java.lang.Throwable -> L39 java.lang.Throwable -> L68
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L39 java.lang.Throwable -> L68
            r2 = 0
            java.nio.MappedByteBuffer r0 = r0.map(r1, r2, r4)     // Catch: java.lang.Throwable -> L39 java.lang.Throwable -> L68
            r8.close()     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
            if (r7 == 0) goto L13
            r7.close()     // Catch: java.io.IOException -> L36
            goto L13
        L36:
            r0 = move-exception
            r0 = r6
            goto L13
        L39:
            r0 = move-exception
            throw r0     // Catch: java.lang.Throwable -> L3b
        L3b:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
        L3f:
            if (r1 == 0) goto L5b
            r8.close()     // Catch: java.lang.Throwable -> L53 java.lang.Throwable -> L58
        L44:
            throw r0     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
        L45:
            r0 = move-exception
            throw r0     // Catch: java.lang.Throwable -> L47
        L47:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
        L4b:
            if (r7 == 0) goto L52
            if (r1 == 0) goto L64
            r7.close()     // Catch: java.io.IOException -> L36 java.lang.Throwable -> L5f
        L52:
            throw r0     // Catch: java.io.IOException -> L36
        L53:
            r2 = move-exception
            r1.addSuppressed(r2)     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
            goto L44
        L58:
            r0 = move-exception
            r1 = r6
            goto L4b
        L5b:
            r8.close()     // Catch: java.lang.Throwable -> L45 java.lang.Throwable -> L58
            goto L44
        L5f:
            r2 = move-exception
            r1.addSuppressed(r2)     // Catch: java.io.IOException -> L36
            goto L52
        L64:
            r7.close()     // Catch: java.io.IOException -> L36
            goto L52
        L68:
            r0 = move-exception
            r1 = r6
            goto L3f
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer")
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002e A[Catch: IOException -> 0x0026, TRY_LEAVE, TryCatch #2 {IOException -> 0x0026, blocks: (B:3:0x0001, B:5:0x0016, B:12:0x0022, B:13:0x0025, B:18:0x002e, B:17:0x002a), top: B:22:0x0001, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0022 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @android.support.annotation.RequiresApi(19)
    @android.support.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.nio.ByteBuffer mmap(java.io.File r9) throws java.lang.Throwable {
        /*
            r6 = 0
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch: java.io.IOException -> L26
            r7.<init>(r9)     // Catch: java.io.IOException -> L26
            java.nio.channels.FileChannel r0 = r7.getChannel()     // Catch: java.lang.Throwable -> L1a java.lang.Throwable -> L32
            Long r4 = r0.size()     // Catch: java.lang.Throwable -> L1a java.lang.Throwable -> L32
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L1a java.lang.Throwable -> L32
            r2 = 0
            java.nio.MappedByteBuffer r0 = r0.map(r1, r2, r4)     // Catch: java.lang.Throwable -> L1a java.lang.Throwable -> L32
            r7.close()     // Catch: java.io.IOException -> L26
        L19:
            return r0
        L1a:
            r0 = move-exception
            throw r0     // Catch: java.lang.Throwable -> L1c
        L1c:
            r1 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L20:
            if (r1 == 0) goto L2e
            r7.close()     // Catch: java.io.IOException -> L26 java.lang.Throwable -> L29
        L25:
            throw r0     // Catch: java.io.IOException -> L26
        L26:
            r0 = move-exception
            r0 = r6
            goto L19
        L29:
            r2 = move-exception
            r1.addSuppressed(r2)     // Catch: java.io.IOException -> L26
            goto L25
        L2e:
            r7.close()     // Catch: java.io.IOException -> L26
            goto L25
        L32:
            r0 = move-exception
            r1 = r6
            goto L20
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(java.io.File):java.nio.ByteBuffer")
    }
}
