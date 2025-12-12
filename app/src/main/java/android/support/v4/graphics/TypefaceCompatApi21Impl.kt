package android.support.v4.graphics

import android.os.ParcelFileDescriptor
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.system.ErrnoException
import android.system.Os
import android.system.OsConstants
import java.io.File

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static val TAG = "TypefaceCompatApi21Impl"

    TypefaceCompatApi21Impl() {
    }

    private fun getFile(ParcelFileDescriptor parcelFileDescriptor) throws ErrnoException {
        try {
            String str = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd())
            if (OsConstants.S_ISREG(Os.stat(str).st_mode)) {
                return File(str)
            }
            return null
        } catch (ErrnoException e) {
            return null
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005b A[Catch: Throwable -> 0x0043, all -> 0x0058, TryCatch #5 {all -> 0x0058, blocks: (B:7:0x0017, B:9:0x001d, B:38:0x005f, B:11:0x0023, B:13:0x0030, B:21:0x003f, B:22:0x0042, B:37:0x005b, B:34:0x0054), top: B:54:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[Catch: IOException -> 0x0051, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #7 {IOException -> 0x0051, blocks: (B:6:0x000d, B:40:0x0065, B:15:0x0035, B:29:0x004d, B:44:0x006f, B:43:0x006b, B:30:0x0050), top: B:55:0x000d, inners: #0 }] */
    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r7, android.os.CancellationSignal r8, @android.support.annotation.NonNull android.support.v4.provider.FontsContractCompat.Array<FontInfo> r9, Int r10) throws java.lang.Throwable {
        /*
            r6 = this
            r0 = 0
            Int r1 = r9.length
            if (r1 > 0) goto L5
        L4:
            return r0
        L5:
            android.support.v4.provider.FontsContractCompat$FontInfo r1 = r6.findBestInfo(r9, r10)
            android.content.ContentResolver r2 = r7.getContentResolver()
            android.net.Uri r1 = r1.getUri()     // Catch: java.io.IOException -> L51
            java.lang.String r3 = "r"
            android.os.ParcelFileDescriptor r3 = r2.openFileDescriptor(r1, r3, r8)     // Catch: java.io.IOException -> L51
            java.io.File r1 = r6.getFile(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            if (r1 == 0) goto L23
            Boolean r2 = r1.canRead()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            if (r2 != 0) goto L5f
        L23:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            java.io.FileDescriptor r1 = r3.getFileDescriptor()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            r4.<init>(r1)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            android.graphics.Typeface r1 = super.createFromInputStream(r7, r4)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L73
            r4.close()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            if (r3 == 0) goto L38
            r3.close()     // Catch: java.io.IOException -> L51
        L38:
            r0 = r1
            goto L4
        L3a:
            r2 = move-exception
            throw r2     // Catch: java.lang.Throwable -> L3c
        L3c:
            r1 = move-exception
        L3d:
            if (r2 == 0) goto L5b
            r4.close()     // Catch: java.lang.Throwable -> L53 java.lang.Throwable -> L58
        L42:
            throw r1     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
        L43:
            r1 = move-exception
            throw r1     // Catch: java.lang.Throwable -> L45
        L45:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
        L49:
            if (r3 == 0) goto L50
            if (r2 == 0) goto L6f
            r3.close()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L6a
        L50:
            throw r1     // Catch: java.io.IOException -> L51
        L51:
            r1 = move-exception
            goto L4
        L53:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            goto L42
        L58:
            r1 = move-exception
            r2 = r0
            goto L49
        L5b:
            r4.close()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            goto L42
        L5f:
            android.graphics.Typeface r1 = android.graphics.Typeface.createFromFile(r1)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L58
            if (r3 == 0) goto L68
            r3.close()     // Catch: java.io.IOException -> L51
        L68:
            r0 = r1
            goto L4
        L6a:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch: java.io.IOException -> L51
            goto L50
        L6f:
            r3.close()     // Catch: java.io.IOException -> L51
            goto L50
        L73:
            r1 = move-exception
            r2 = r0
            goto L3d
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$Array<FontInfo>, Int):android.graphics.Typeface")
    }
}
