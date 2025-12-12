package com.gmail.heagoo.apkeditor.se

import java.io.Closeable
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.zip.ZipFile

final class a extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1250a

    /* renamed from: b, reason: collision with root package name */
    private String f1251b
    private String c

    a(ApkCreateActivity apkCreateActivity) {
        this.f1250a = WeakReference(apkCreateActivity)
    }

    private fun a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (Throwable th) {
            }
        }
    }

    private fun a(ZipFile zipFile) throws IOException {
        try {
            zipFile.close()
        } catch (IOException e) {
        }
    }

    public final String a() {
        return this.c
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x012f A[Catch: Throwable -> 0x0039, TryCatch #6 {Throwable -> 0x0039, blocks: (B:3:0x000b, B:5:0x0011, B:6:0x0019, B:8:0x001f, B:16:0x0053, B:18:0x0059, B:19:0x0089, B:21:0x008f, B:23:0x0095, B:25:0x009b, B:41:0x0123, B:42:0x012c, B:43:0x012f, B:44:0x013f, B:55:0x0181, B:56:0x0198, B:40:0x011d, B:53:0x017a, B:54:0x0180, B:50:0x0170), top: B:71:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0181 A[Catch: Throwable -> 0x0039, TryCatch #6 {Throwable -> 0x0039, blocks: (B:3:0x000b, B:5:0x0011, B:6:0x0019, B:8:0x001f, B:16:0x0053, B:18:0x0059, B:19:0x0089, B:21:0x008f, B:23:0x0095, B:25:0x009b, B:41:0x0123, B:42:0x012c, B:43:0x012f, B:44:0x013f, B:55:0x0181, B:56:0x0198, B:40:0x011d, B:53:0x017a, B:54:0x0180, B:50:0x0170), top: B:71:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0198 A[Catch: Throwable -> 0x0039, TRY_LEAVE, TryCatch #6 {Throwable -> 0x0039, blocks: (B:3:0x000b, B:5:0x0011, B:6:0x0019, B:8:0x001f, B:16:0x0053, B:18:0x0059, B:19:0x0089, B:21:0x008f, B:23:0x0095, B:25:0x009b, B:41:0x0123, B:42:0x012c, B:43:0x012f, B:44:0x013f, B:55:0x0181, B:56:0x0198, B:40:0x011d, B:53:0x017a, B:54:0x0180, B:50:0x0170), top: B:71:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit run() {
        /*
            Method dump skipped, instructions count: 486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.se.a.run():Unit")
    }
}
