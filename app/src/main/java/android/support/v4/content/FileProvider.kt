package android.support.v4.content

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.support.annotation.GuardedBy
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.text.TextUtils
import android.webkit.MimeTypeMap
import com.gmail.heagoo.apkeditor.gzd
import java.io.File
import java.io.IOException
import java.util.HashMap
import java.util.Map
import org.xmlpull.v1.XmlPullParserException

class FileProvider extends ContentProvider {
    private static val ATTR_NAME = "name"
    private static val ATTR_PATH = "path"
    private static val META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS"
    private static val TAG_CACHE_PATH = "cache-path"
    private static val TAG_EXTERNAL = "external-path"
    private static val TAG_EXTERNAL_CACHE = "external-cache-path"
    private static val TAG_EXTERNAL_FILES = "external-files-path"
    private static val TAG_EXTERNAL_MEDIA = "external-media-path"
    private static val TAG_FILES_PATH = "files-path"
    private static val TAG_ROOT_PATH = "root-path"
    private PathStrategy mStrategy
    private static final Array<String> COLUMNS = {"_display_name", "_size"}
    private static val DEVICE_ROOT = File("/")

    @GuardedBy("sCache")
    private static HashMap sCache = HashMap()

    interface PathStrategy {
        File getFileForUri(Uri uri)

        Uri getUriForFile(File file)
    }

    class SimplePathStrategy implements PathStrategy {
        private final String mAuthority
        private val mRoots = HashMap()

        SimplePathStrategy(String str) {
            this.mAuthority = str
        }

        Unit addRoot(String str, File file) throws IOException {
            if (TextUtils.isEmpty(str)) {
                throw IllegalArgumentException("Name must not be empty")
            }
            try {
                this.mRoots.put(str, file.getCanonicalFile())
            } catch (IOException e) {
                throw IllegalArgumentException("Failed to resolve canonical path for " + file, e)
            }
        }

        @Override // android.support.v4.content.FileProvider.PathStrategy
        fun getFileForUri(Uri uri) throws IOException {
            String encodedPath = uri.getEncodedPath()
            Int iIndexOf = encodedPath.indexOf(47, 1)
            String strDecode = Uri.decode(encodedPath.substring(1, iIndexOf))
            String strDecode2 = Uri.decode(encodedPath.substring(iIndexOf + 1))
            File file = (File) this.mRoots.get(strDecode)
            if (file == null) {
                throw IllegalArgumentException("Unable to find configured root for " + uri)
            }
            File file2 = File(file, strDecode2)
            try {
                File canonicalFile = file2.getCanonicalFile()
                if (canonicalFile.getPath().startsWith(file.getPath())) {
                    return canonicalFile
                }
                throw SecurityException("Resolved path jumped beyond configured root")
            } catch (IOException e) {
                throw IllegalArgumentException("Failed to resolve canonical path for " + file2)
            }
        }

        @Override // android.support.v4.content.FileProvider.PathStrategy
        fun getUriForFile(File file) throws IOException {
            try {
                String canonicalPath = file.getCanonicalPath()
                Map.Entry entry = null
                for (Map.Entry entry2 : this.mRoots.entrySet()) {
                    String path = ((File) entry2.getValue()).getPath()
                    if (!canonicalPath.startsWith(path) || (entry != null && path.length() <= ((File) entry.getValue()).getPath().length())) {
                        entry2 = entry
                    }
                    entry = entry2
                }
                if (entry == null) {
                    throw IllegalArgumentException("Failed to find configured root that contains " + canonicalPath)
                }
                String path2 = ((File) entry.getValue()).getPath()
                return new Uri.Builder().scheme(gzd.COLUMN_CONTENT).authority(this.mAuthority).encodedPath(Uri.encode((String) entry.getKey()) + '/' + Uri.encode(path2.endsWith("/") ? canonicalPath.substring(path2.length()) : canonicalPath.substring(path2.length() + 1), "/")).build()
            } catch (IOException e) {
                throw IllegalArgumentException("Failed to resolve canonical path for " + file)
            }
        }
    }

    private fun buildPath(File file, String... strArr) {
        Int length = strArr.length
        Int i = 0
        File file2 = file
        while (i < length) {
            String str = strArr[i]
            i++
            file2 = str != null ? File(file2, str) : file2
        }
        return file2
    }

    private static Array<Object> copyOf(Array<Object> objArr, Int i) {
        Array<Object> objArr2 = new Object[i]
        System.arraycopy(objArr, 0, objArr2, 0, i)
        return objArr2
    }

    private static Array<String> copyOf(Array<String> strArr, Int i) {
        Array<String> strArr2 = new String[i]
        System.arraycopy(strArr, 0, strArr2, 0, i)
        return strArr2
    }

    private fun getPathStrategy(Context context, String str) {
        PathStrategy pathStrategy
        synchronized (sCache) {
            pathStrategy = (PathStrategy) sCache.get(str)
            if (pathStrategy == null) {
                try {
                    try {
                        pathStrategy = parsePathStrategy(context, str)
                        sCache.put(str, pathStrategy)
                    } catch (XmlPullParserException e) {
                        throw IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e)
                    }
                } catch (IOException e2) {
                    throw IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2)
                }
            }
        }
        return pathStrategy
    }

    fun getUriForFile(@NonNull Context context, @NonNull String str, @NonNull File file) {
        return getPathStrategy(context, str).getUriForFile(file)
    }

    private fun modeToMode(String str) {
        if ("r".equals(str)) {
            return 268435456
        }
        if ("w".equals(str) || "wt".equals(str)) {
            return 738197504
        }
        if ("wa".equals(str)) {
            return 704643072
        }
        if ("rw".equals(str)) {
            return 939524096
        }
        if ("rwt".equals(str)) {
            return 1006632960
        }
        throw IllegalArgumentException("Invalid mode: " + str)
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.support.v4.content.FileProvider.PathStrategy parsePathStrategy(android.content.Context r10, java.lang.String r11) throws java.io.IOException {
        /*
            r9 = 1
            r1 = 0
            r8 = 0
            android.support.v4.content.FileProvider$SimplePathStrategy r2 = new android.support.v4.content.FileProvider$SimplePathStrategy
            r2.<init>(r11)
            android.content.pm.PackageManager r0 = r10.getPackageManager()
            r3 = 128(0x80, Float:1.794E-43)
            android.content.pm.ProviderInfo r0 = r0.resolveContentProvider(r11, r3)
            android.content.pm.PackageManager r3 = r10.getPackageManager()
            java.lang.String r4 = "android.support.FILE_PROVIDER_PATHS"
            android.content.res.XmlResourceParser r3 = r0.loadXmlMetaData(r3, r4)
            if (r3 != 0) goto L26
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Missing android.support.FILE_PROVIDER_PATHS meta-data"
            r0.<init>(r1)
            throw r0
        L26:
            Int r0 = r3.next()
            if (r0 == r9) goto Lba
            r4 = 2
            if (r0 != r4) goto L26
            java.lang.String r0 = r3.getName()
            java.lang.String r4 = "name"
            java.lang.String r4 = r3.getAttributeValue(r1, r4)
            java.lang.String r5 = "path"
            java.lang.String r5 = r3.getAttributeValue(r1, r5)
            java.lang.String r6 = "root-path"
            Boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L57
            java.io.File r0 = android.support.v4.content.FileProvider.DEVICE_ROOT
        L49:
            if (r0 == 0) goto L26
            java.lang.Array<String> r6 = new java.lang.String[r9]
            r6[r8] = r5
            java.io.File r0 = buildPath(r0, r6)
            r2.addRoot(r4, r0)
            goto L26
        L57:
            java.lang.String r6 = "files-path"
            Boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L64
            java.io.File r0 = r10.getFilesDir()
            goto L49
        L64:
            java.lang.String r6 = "cache-path"
            Boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L71
            java.io.File r0 = r10.getCacheDir()
            goto L49
        L71:
            java.lang.String r6 = "external-path"
            Boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L7e
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            goto L49
        L7e:
            java.lang.String r6 = "external-files-path"
            Boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L90
            java.io.Array<File> r0 = android.support.v4.content.ContextCompat.getExternalFilesDirs(r10, r1)
            Int r6 = r0.length
            if (r6 <= 0) goto Lbb
            r0 = r0[r8]
            goto L49
        L90:
            java.lang.String r6 = "external-cache-path"
            Boolean r6 = r6.equals(r0)
            if (r6 == 0) goto La2
            java.io.Array<File> r0 = android.support.v4.content.ContextCompat.getExternalCacheDirs(r10)
            Int r6 = r0.length
            if (r6 <= 0) goto Lbb
            r0 = r0[r8]
            goto L49
        La2:
            Int r6 = android.os.Build.VERSION.SDK_INT
            r7 = 21
            if (r6 < r7) goto Lbb
            java.lang.String r6 = "external-media-path"
            Boolean r0 = r6.equals(r0)
            if (r0 == 0) goto Lbb
            java.io.Array<File> r0 = r10.getExternalMediaDirs()
            Int r6 = r0.length
            if (r6 <= 0) goto Lbb
            r0 = r0[r8]
            goto L49
        Lba:
            return r2
        Lbb:
            r0 = r1
            goto L49
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.content.FileProvider.parsePathStrategy(android.content.Context, java.lang.String):android.support.v4.content.FileProvider$PathStrategy")
    }

    @Override // android.content.ContentProvider
    fun attachInfo(@NonNull Context context, @NonNull ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo)
        if (providerInfo.exported) {
            throw SecurityException("Provider must not be exported")
        }
        if (!providerInfo.grantUriPermissions) {
            throw SecurityException("Provider must grant uri permissions")
        }
        this.mStrategy = getPathStrategy(context, providerInfo.authority)
    }

    @Override // android.content.ContentProvider
    fun delete(@NonNull Uri uri, @Nullable String str, @Nullable Array<String> strArr) {
        return this.mStrategy.getFileForUri(uri).delete() ? 1 : 0
    }

    @Override // android.content.ContentProvider
    fun getType(@NonNull Uri uri) {
        File fileForUri = this.mStrategy.getFileForUri(uri)
        Int iLastIndexOf = fileForUri.getName().lastIndexOf(46)
        if (iLastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileForUri.getName().substring(iLastIndexOf + 1))
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension
            }
        }
        return "application/octet-stream"
    }

    @Override // android.content.ContentProvider
    fun insert(@NonNull Uri uri, ContentValues contentValues) {
        throw UnsupportedOperationException("No external inserts")
    }

    @Override // android.content.ContentProvider
    fun onCreate() {
        return true
    }

    @Override // android.content.ContentProvider
    fun openFile(@NonNull Uri uri, @NonNull String str) {
        return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(uri), modeToMode(str))
    }

    @Override // android.content.ContentProvider
    fun query(@NonNull Uri uri, @Nullable Array<String> strArr, @Nullable String str, @Nullable Array<String> strArr2, @Nullable String str2) {
        Int i
        File fileForUri = this.mStrategy.getFileForUri(uri)
        if (strArr == null) {
            strArr = COLUMNS
        }
        Array<String> strArr3 = new String[strArr.length]
        Array<Object> objArr = new Object[strArr.length]
        Int length = strArr.length
        Int i2 = 0
        Int i3 = 0
        while (i2 < length) {
            String str3 = strArr[i2]
            if ("_display_name".equals(str3)) {
                strArr3[i3] = "_display_name"
                i = i3 + 1
                objArr[i3] = fileForUri.getName()
            } else if ("_size".equals(str3)) {
                strArr3[i3] = "_size"
                i = i3 + 1
                objArr[i3] = Long.valueOf(fileForUri.length())
            } else {
                i = i3
            }
            i2++
            i3 = i
        }
        Array<String> strArrCopyOf = copyOf(strArr3, i3)
        Array<Object> objArrCopyOf = copyOf(objArr, i3)
        MatrixCursor matrixCursor = MatrixCursor(strArrCopyOf, 1)
        matrixCursor.addRow(objArrCopyOf)
        return matrixCursor
    }

    @Override // android.content.ContentProvider
    fun update(@NonNull Uri uri, ContentValues contentValues, @Nullable String str, @Nullable Array<String> strArr) {
        throw UnsupportedOperationException("No external updates")
    }
}
